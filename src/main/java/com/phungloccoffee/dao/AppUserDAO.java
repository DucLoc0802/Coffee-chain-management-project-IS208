package com.phungloccoffee.dao;

import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.model.AppUser;
import com.phungloccoffee.model.NhanVien;
import com.phungloccoffee.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AppUserDAO {
    private static final String INSERT_SQL = """
            INSERT INTO app_user (
                user_id,
                ten_dang_nhap,
                mat_khau,
                vai_tro,
                trang_thai
            )
            VALUES (?, ?, ?, ?, ?)
            """;

    public Optional<AppUser> findByUsername(String username) throws DatabaseException {
        String cleanUsername = username == null ? "" : username.trim();
        String sql = """
                SELECT user_id, ten_dang_nhap, mat_khau, vai_tro, trang_thai, last_login, created_at, updated_at
                FROM app_user
                WHERE TRIM(LOWER(ten_dang_nhap)) = TRIM(LOWER(?))
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            System.out.println("=== DEBUG LOGIN ===");
            System.out.println("Login username input = [" + username + "]");
            System.out.println("Login username clean = [" + cleanUsername + "]");
            System.out.println("Connected DB user = " + conn.getMetaData().getUserName());

            stmt.setString(1, cleanUsername);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Found user in app_user = " + rs.getString("ten_dang_nhap"));
                    return Optional.of(mapUser(rs));
                }
            }
            System.out.println("User not found in app_user: [" + cleanUsername + "]");
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải tài khoản người dùng.", e);
        }
    }

    public boolean existsById(String userId) throws DatabaseException {
        String sql = """
                SELECT COUNT(*)
                FROM app_user
                WHERE user_id = ?
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Không thể kiểm tra tài khoản người dùng.", e);
        }
    }

    public void insert(AppUser user) throws DatabaseException {
        try (Connection conn = DBConnection.getConnection()) {
            insert(conn, user);
        } catch (SQLException e) {
            throw new DatabaseException("Không thể thêm tài khoản.", e);
        }
    }

    public void insert(Connection conn, AppUser user) throws DatabaseException {
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getTenDangNhap());
            stmt.setString(3, user.getMatKhau());
            stmt.setString(4, user.getVaiTro());
            stmt.setInt(5, user.getTrangThai());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể thêm tài khoản.", e);
        }
    }

    public void createAccountWithEmployee(AppUser user, NhanVien employee) throws DatabaseException {
        NhanVienDAO nhanVienDAO = new NhanVienDAO();
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);
            insert(conn, user);
            nhanVienDAO.insert(conn, employee);
            conn.commit();
        } catch (Exception e) {
            rollbackQuietly(conn);
            if (e instanceof DatabaseException databaseException) {
                throw databaseException;
            }
            throw new DatabaseException("Không thể tạo tài khoản và nhân viên.", e);
        } finally {
            closeQuietly(conn);
        }
    }

    public void updateLastLogin(String userId) throws DatabaseException {
        String sql = "UPDATE app_user SET last_login = SYSTIMESTAMP, updated_at = SYSTIMESTAMP WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể cập nhật thời gian đăng nhập.", e);
        }
    }

    public List<AppUser> findAll() throws DatabaseException {
        String sql = """
                SELECT u.user_id, u.ten_dang_nhap, u.mat_khau, u.vai_tro, u.trang_thai,
                       u.last_login, u.created_at, u.updated_at,
                       nv.nhan_vien_id, nv.ho_ten, nv.chi_nhanh_id
                FROM app_user u
                LEFT JOIN nhan_vien nv ON nv.user_id = u.user_id
                ORDER BY u.ten_dang_nhap
                """;
        List<AppUser> users = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AppUser user = mapUser(rs);
                user.setNhanVienId(rs.getString("nhan_vien_id"));
                user.setHoTen(rs.getString("ho_ten"));
                user.setChiNhanhId(rs.getString("chi_nhanh_id"));
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải danh sách tài khoản.", e);
        }
    }

    public void updateStatus(String userId, int status) throws DatabaseException {
        String sql = "UPDATE app_user SET trang_thai = ?, updated_at = SYSTIMESTAMP WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, status);
            stmt.setString(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể cập nhật trạng thái tài khoản.", e);
        }
    }

    public void update(AppUser user) throws DatabaseException {
        String sql = "UPDATE app_user SET vai_tro = ?, trang_thai = ?, updated_at = SYSTIMESTAMP WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getVaiTro());
            stmt.setInt(2, user.getTrangThai());
            stmt.setString(3, user.getUserId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể cập nhật tài khoản.", e);
        }
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

    private AppUser mapUser(ResultSet rs) throws SQLException {
        return new AppUser(
                rs.getString("user_id"),
                rs.getString("ten_dang_nhap"),
                rs.getString("mat_khau"),
                rs.getString("vai_tro"),
                rs.getInt("trang_thai"),
                toLocalDateTime(rs.getTimestamp("last_login")),
                toLocalDateTime(rs.getTimestamp("created_at")),
                toLocalDateTime(rs.getTimestamp("updated_at"))
        );
    }

    private java.time.LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
