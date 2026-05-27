package com.phungloccoffee.gui.service;

import com.phungloccoffee.gui.model.AppRole;
import com.phungloccoffee.gui.model.AppUserSession;
import com.phungloccoffee.util.DBConnection;
import com.phungloccoffee.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class RealAuthService {
    public Optional<AppUserSession> login(String username, String password) {
        String cleanUsername = username == null ? "" : username.trim();
        String cleanPassword = password == null ? "" : password;
        if (cleanUsername.isEmpty() || cleanPassword.isEmpty()) {
            return Optional.empty();
        }

        String sql = """
                SELECT
                    u.user_id,
                    u.ten_dang_nhap,
                    u.mat_khau,
                    u.vai_tro,
                    u.trang_thai AS user_trang_thai,
                    nv.nhan_vien_id,
                    nv.ho_ten,
                    nv.email,
                    nv.phone,
                    nv.trang_thai AS nv_trang_thai,
                    cn.chi_nhanh_id,
                    cn.ten_chi_nhanh
                FROM app_user u
                LEFT JOIN nhan_vien nv ON nv.user_id = u.user_id
                LEFT JOIN chi_nhanh cn ON cn.chi_nhanh_id = nv.chi_nhanh_id
                WHERE LOWER(u.ten_dang_nhap) = LOWER(?)
                """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cleanUsername);
            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                if (rs.getInt("user_trang_thai") != 1) {
                    return Optional.empty();
                }
                int employeeStatus = rs.getInt("nv_trang_thai");
                if (!rs.wasNull() && employeeStatus != 1) {
                    return Optional.empty();
                }
                if (!PasswordUtil.verifyPassword(cleanPassword, rs.getString("mat_khau"))) {
                    return Optional.empty();
                }

                AppRole role = parseRole(rs.getString("vai_tro"));
                return Optional.of(new AppUserSession(
                        rs.getString("user_id"),
                        rs.getString("ten_dang_nhap"),
                        rs.getString("nhan_vien_id"),
                        rs.getString("ho_ten"),
                        role,
                        role.getDisplayName(),
                        rs.getString("chi_nhanh_id"),
                        rs.getString("ten_chi_nhanh"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Không thể đăng nhập. Vui lòng kiểm tra kết nối dữ liệu.", e);
        }
    }

    private AppRole parseRole(String value) {
        try {
            return AppRole.valueOf(value == null ? "" : value.trim());
        } catch (Exception e) {
            throw new IllegalStateException("Vai trò tài khoản không hợp lệ.");
        }
    }
}
