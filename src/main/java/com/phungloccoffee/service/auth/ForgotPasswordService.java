package com.phungloccoffee.service.auth;

import com.phungloccoffee.util.DBConnection;
import com.phungloccoffee.util.PasswordUtil;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.UUID;

public class ForgotPasswordService {
    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final EmailService emailService = new EmailService();
    private final SmsService smsService = new SmsService();

    public FindAccountResult findAccount(String identifier) {
        String cleanIdentifier = normalize(identifier);
        if (cleanIdentifier.isEmpty()) {
            return FindAccountResult.failure("Vui lòng nhập tài khoản, email hoặc số điện thoại.");
        }

        String sql = """
                SELECT u.user_id, u.ten_dang_nhap, nv.email, nv.phone
                FROM app_user u
                LEFT JOIN nhan_vien nv ON nv.user_id = u.user_id
                WHERE TRIM(LOWER(u.ten_dang_nhap)) = TRIM(LOWER(?))
                   OR TRIM(LOWER(nv.email)) = TRIM(LOWER(?))
                   OR TRIM(nv.phone) = TRIM(?)
                FETCH FIRST 1 ROWS ONLY
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cleanIdentifier);
            stmt.setString(2, cleanIdentifier);
            stmt.setString(3, cleanIdentifier);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    // Demo-friendly message. For production, prefer a generic non-enumerating message.
                    return FindAccountResult.failure("Không tìm thấy tài khoản phù hợp.");
                }

                String email = rs.getString("email");
                String phone = rs.getString("phone");
                return FindAccountResult.success(
                        rs.getString("user_id"),
                        maskEmail(email),
                        maskPhone(phone),
                        hasText(email),
                        hasText(phone)
                );
            }
        } catch (SQLException e) {
            return FindAccountResult.failure("Không thể tìm tài khoản lúc này. Vui lòng thử lại sau.");
        }
    }

    public SendOtpResult sendOtp(String userId, String method) {
        String cleanMethod = normalizeMethod(method);
        if (!hasText(userId) || cleanMethod.isEmpty()) {
            return SendOtpResult.failure("Vui lòng chọn phương thức nhận mã.");
        }

        try (Connection conn = DBConnection.getConnection()) {
            AccountDestination destination = findDestination(conn, userId);
            String target = "SMS".equals(cleanMethod) ? destination.phone() : destination.email();
            if (!hasText(target)) {
                return SendOtpResult.failure("Tài khoản chưa có thông tin nhận mã theo phương thức này.");
            }
            if (hasRecentOtp(conn, userId, cleanMethod)) {
                return SendOtpResult.failure("Vui lòng chờ 60 giây trước khi gửi lại mã.");
            }

            String otpCode = generateOtp();
            String otpId = nextOtpId(conn);
            String sql = """
                    INSERT INTO password_reset_otp (
                        otp_id,
                        user_id,
                        otp_code_hash,
                        phuong_thuc,
                        dia_chi_nhan,
                        thoi_gian_het_han,
                        da_xac_thuc,
                        da_su_dung,
                        so_lan_sai
                    )
                    VALUES (?, ?, ?, ?, ?, SYSTIMESTAMP + INTERVAL '5' MINUTE, 0, 0, 0)
                    """;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, otpId);
                stmt.setString(2, userId);
                stmt.setString(3, PasswordUtil.hashOtp(otpCode));
                stmt.setString(4, cleanMethod);
                stmt.setString(5, target);
                stmt.executeUpdate();
            }

            if ("SMS".equals(cleanMethod)) {
                smsService.sendPasswordResetOtp(target, otpCode);
            } else {
                emailService.sendPasswordResetOtp(target, otpCode);
            }
            return SendOtpResult.success("Mã OTP đã được tạo. Vui lòng xem console demo để lấy mã.");
        } catch (SQLException e) {
            return SendOtpResult.failure("Không thể tạo mã OTP. Vui lòng kiểm tra bảng password_reset_otp.");
        }
    }

    public VerifyOtpResult verifyOtp(String userId, String method, String otp) {
        String cleanMethod = normalizeMethod(method);
        if (!hasText(userId) || cleanMethod.isEmpty() || !hasText(otp)) {
            return VerifyOtpResult.failure("Vui lòng nhập mã xác nhận.");
        }

        String sql = """
                SELECT otp_id, otp_code_hash, so_lan_sai
                FROM password_reset_otp
                WHERE user_id = ?
                  AND phuong_thuc = ?
                  AND da_su_dung = 0
                  AND thoi_gian_het_han > SYSTIMESTAMP
                  AND so_lan_sai < ?
                ORDER BY created_at DESC
                FETCH FIRST 1 ROWS ONLY
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, cleanMethod);
            stmt.setInt(3, MAX_FAILED_ATTEMPTS);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return VerifyOtpResult.failure("Không tìm thấy mã hợp lệ. Vui lòng gửi lại mã.");
                }

                String otpId = rs.getString("otp_id");
                boolean matched = PasswordUtil.verifyOtp(otp, rs.getString("otp_code_hash"));
                if (!matched) {
                    increaseFailedAttempts(conn, otpId);
                    return VerifyOtpResult.failure("Mã xác nhận không đúng.");
                }

                String resetToken = UUID.randomUUID().toString();
                try (PreparedStatement update = conn.prepareStatement("""
                        UPDATE password_reset_otp
                        SET da_xac_thuc = 1,
                            reset_token = ?,
                            verified_at = SYSTIMESTAMP
                        WHERE otp_id = ?
                        """)) {
                    update.setString(1, resetToken);
                    update.setString(2, otpId);
                    update.executeUpdate();
                }
                return VerifyOtpResult.success(resetToken, "Xác nhận mã thành công.");
            }
        } catch (SQLException e) {
            return VerifyOtpResult.failure("Không thể xác nhận mã lúc này. Vui lòng thử lại sau.");
        }
    }

    public ResetPasswordResult resetPassword(String userId, String resetToken, String newPassword) {
        if (!hasText(userId) || !hasText(resetToken)) {
            return ResetPasswordResult.failure("Phiên khôi phục không hợp lệ. Vui lòng xác nhận lại mã.");
        }
        if (!hasText(newPassword) || newPassword.length() < 8) {
            return ResetPasswordResult.failure("Mật khẩu mới phải có tối thiểu 8 ký tự.");
        }

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String otpId = findValidResetOtp(conn, userId, resetToken);
            if (otpId == null) {
                rollbackQuietly(conn);
                return ResetPasswordResult.failure("Phiên khôi phục đã hết hạn. Vui lòng gửi lại mã.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE app_user SET mat_khau = ?, updated_at = SYSTIMESTAMP WHERE user_id = ?")) {
                stmt.setString(1, PasswordUtil.hashPassword(newPassword));
                stmt.setString(2, userId);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement("""
                    UPDATE password_reset_otp
                    SET da_su_dung = 1,
                        used_at = SYSTIMESTAMP
                    WHERE otp_id = ?
                    """)) {
                stmt.setString(1, otpId);
                stmt.executeUpdate();
            }

            conn.commit();
            return ResetPasswordResult.success("Đổi mật khẩu thành công.");
        } catch (SQLException e) {
            rollbackQuietly(conn);
            return ResetPasswordResult.failure("Không thể cập nhật mật khẩu lúc này. Vui lòng thử lại sau.");
        } finally {
            closeQuietly(conn);
        }
    }

    private AccountDestination findDestination(Connection conn, String userId) throws SQLException {
        String sql = """
                SELECT nv.email, nv.phone
                FROM app_user u
                LEFT JOIN nhan_vien nv ON nv.user_id = u.user_id
                WHERE u.user_id = ?
                """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new AccountDestination(rs.getString("email"), rs.getString("phone"));
                }
            }
        }
        return new AccountDestination(null, null);
    }

    private boolean hasRecentOtp(Connection conn, String userId, String method) throws SQLException {
        String sql = """
                SELECT COUNT(*)
                FROM password_reset_otp
                WHERE user_id = ?
                  AND phuong_thuc = ?
                  AND created_at > SYSTIMESTAMP - INTERVAL '60' SECOND
                  AND da_su_dung = 0
                """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, method);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private void increaseFailedAttempts(Connection conn, String otpId) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE password_reset_otp SET so_lan_sai = so_lan_sai + 1 WHERE otp_id = ?")) {
            stmt.setString(1, otpId);
            stmt.executeUpdate();
        }
    }

    private String findValidResetOtp(Connection conn, String userId, String resetToken) throws SQLException {
        String sql = """
                SELECT otp_id
                FROM password_reset_otp
                WHERE user_id = ?
                  AND reset_token = ?
                  AND da_xac_thuc = 1
                  AND da_su_dung = 0
                  AND thoi_gian_het_han > SYSTIMESTAMP
                ORDER BY created_at DESC
                FETCH FIRST 1 ROWS ONLY
                """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.setString(2, resetToken);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getString("otp_id") : null;
            }
        }
    }

    private String nextOtpId(Connection conn) throws SQLException {
        String sql = """
                SELECT NVL(MAX(TO_NUMBER(SUBSTR(otp_id, 4))), 0) + 1
                FROM password_reset_otp
                WHERE REGEXP_LIKE(otp_id, '^OTP[0-9]+$')
                """;
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            int next = rs.next() ? rs.getInt(1) : 1;
            if (next > 9999999) {
                throw new SQLException("OTP id sequence exceeded VARCHAR2(10).");
            }
            return "OTP" + String.format("%03d", next);
        }
    }

    private String generateOtp() {
        return String.format("%06d", RANDOM.nextInt(1_000_000));
    }

    private String normalizeMethod(String method) {
        String normalized = normalize(method).toUpperCase(Locale.ROOT);
        return "EMAIL".equals(normalized) || "SMS".equals(normalized) ? normalized : "";
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String maskEmail(String email) {
        if (!hasText(email) || !email.contains("@")) {
            return "";
        }
        String[] parts = email.split("@", 2);
        String name = parts[0];
        if (name.length() <= 2) {
            return name.charAt(0) + "*@" + parts[1];
        }
        return name.charAt(0) + "*".repeat(Math.max(1, name.length() - 2))
                + name.charAt(name.length() - 1) + "@" + parts[1];
    }

    private String maskPhone(String phone) {
        if (!hasText(phone)) {
            return "";
        }
        String cleanPhone = phone.trim();
        if (cleanPhone.length() <= 4) {
            return "*".repeat(cleanPhone.length());
        }
        return "*".repeat(cleanPhone.length() - 4) + cleanPhone.substring(cleanPhone.length() - 4);
    }

    private void rollbackQuietly(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.rollback();
        } catch (SQLException ignored) {
        }
    }

    private void closeQuietly(Connection conn) {
        if (conn == null) {
            return;
        }
        try {
            conn.close();
        } catch (SQLException ignored) {
        }
    }

    private record AccountDestination(String email, String phone) {
    }

    public record FindAccountResult(boolean success, String message, String userId, String maskedEmail,
                                    String maskedPhone, boolean emailAvailable, boolean phoneAvailable) {
        public static FindAccountResult success(String userId, String maskedEmail, String maskedPhone,
                                                boolean emailAvailable, boolean phoneAvailable) {
            return new FindAccountResult(true, "", userId, maskedEmail, maskedPhone, emailAvailable, phoneAvailable);
        }

        public static FindAccountResult failure(String message) {
            return new FindAccountResult(false, message, null, "", "", false, false);
        }
    }

    public record SendOtpResult(boolean success, String message) {
        public static SendOtpResult success(String message) {
            return new SendOtpResult(true, message);
        }

        public static SendOtpResult failure(String message) {
            return new SendOtpResult(false, message);
        }
    }

    public record VerifyOtpResult(boolean success, String message, String resetToken) {
        public static VerifyOtpResult success(String resetToken, String message) {
            return new VerifyOtpResult(true, message, resetToken);
        }

        public static VerifyOtpResult failure(String message) {
            return new VerifyOtpResult(false, message, null);
        }
    }

    public record ResetPasswordResult(boolean success, String message) {
        public static ResetPasswordResult success(String message) {
            return new ResetPasswordResult(true, message);
        }

        public static ResetPasswordResult failure(String message) {
            return new ResetPasswordResult(false, message);
        }
    }
}
