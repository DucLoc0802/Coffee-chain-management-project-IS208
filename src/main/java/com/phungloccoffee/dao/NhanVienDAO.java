package com.phungloccoffee.dao;

import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.model.NhanVien;
import com.phungloccoffee.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NhanVienDAO {
    private static final String INSERT_SQL = """
            INSERT INTO nhan_vien (
                nhan_vien_id,
                user_id,
                chi_nhanh_id,
                ho_ten,
                cccd,
                email,
                phone,
                chuc_vu,
                trang_thai,
                ngay_vao_lam,
                ghi_chu
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    public Optional<NhanVien> findByUserId(String userId) throws DatabaseException {
        String sql = "SELECT * FROM nhan_vien WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải nhân viên theo tài khoản.", e);
        }
    }

    public List<NhanVien> findAll() throws DatabaseException {
        String sql = "SELECT * FROM nhan_vien ORDER BY ho_ten";
        List<NhanVien> employees = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                employees.add(map(rs));
            }
            return employees;
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải danh sách nhân viên.", e);
        }
    }

    public List<NhanVien> searchByKeyword(String keyword) throws DatabaseException {
        String sql = """
                SELECT *
                FROM nhan_vien
                WHERE LOWER(ho_ten) LIKE ? OR LOWER(email) LIKE ? OR phone LIKE ?
                ORDER BY ho_ten
                """;
        List<NhanVien> employees = new ArrayList<>();
        String like = "%" + (keyword == null ? "" : keyword.toLowerCase()) + "%";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setString(3, like);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(map(rs));
                }
            }
            return employees;
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tìm kiếm nhân viên.", e);
        }
    }

    public void insert(NhanVien employee) throws DatabaseException {
        try (Connection conn = DBConnection.getConnection()) {
            insert(conn, employee);
        } catch (SQLException e) {
            throw new DatabaseException("Không thể thêm nhân viên.", e);
        }
    }

    public void insert(Connection conn, NhanVien employee) throws DatabaseException {
        logCreatingEmployee(employee);
        try (PreparedStatement stmt = conn.prepareStatement(INSERT_SQL)) {
            bindForInsert(stmt, employee);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể thêm nhân viên.", e);
        }
    }

    public void update(NhanVien employee) throws DatabaseException {
        String sql = """
                UPDATE nhan_vien
                SET user_id = ?, chi_nhanh_id = ?, ho_ten = ?, cccd = ?, email = ?, phone = ?,
                    chuc_vu = ?, trang_thai = ?, ngay_vao_lam = ?, ghi_chu = ?, updated_at = SYSTIMESTAMP
                WHERE nhan_vien_id = ?
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, employee.getUserId());
            stmt.setString(2, employee.getChiNhanhId());
            stmt.setString(3, employee.getHoTen());
            stmt.setString(4, employee.getCccd());
            stmt.setString(5, employee.getEmail());
            stmt.setString(6, employee.getPhone());
            stmt.setString(7, employee.getChucVu());
            stmt.setInt(8, employee.getTrangThai());
            stmt.setDate(9, employee.getNgayVaoLam() == null ? null : Date.valueOf(employee.getNgayVaoLam()));
            stmt.setString(10, employee.getGhiChu());
            stmt.setString(11, employee.getNhanVienId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể cập nhật nhân viên.", e);
        }
    }

    private void bindForInsert(PreparedStatement stmt, NhanVien employee) throws SQLException {
        stmt.setString(1, employee.getNhanVienId());
        stmt.setString(2, employee.getUserId());
        stmt.setString(3, employee.getChiNhanhId());
        stmt.setString(4, employee.getHoTen());
        stmt.setString(5, employee.getCccd());
        stmt.setString(6, employee.getEmail());
        stmt.setString(7, employee.getPhone());
        stmt.setString(8, employee.getChucVu());
        stmt.setInt(9, employee.getTrangThai());
        stmt.setDate(10, employee.getNgayVaoLam() == null ? null : Date.valueOf(employee.getNgayVaoLam()));
        stmt.setString(11, employee.getGhiChu());
    }

    private void logCreatingEmployee(NhanVien employee) {
        System.out.println("Creating employee:");
        System.out.println("nhan_vien_id = " + employee.getNhanVienId());
        System.out.println("user_id = " + employee.getUserId());
        System.out.println("chi_nhanh_id = " + employee.getChiNhanhId());
    }

    private NhanVien map(ResultSet rs) throws SQLException {
        Date ngayVaoLam = rs.getDate("ngay_vao_lam");
        return new NhanVien(
                rs.getString("nhan_vien_id"),
                rs.getString("user_id"),
                rs.getString("chi_nhanh_id"),
                rs.getString("ho_ten"),
                rs.getString("cccd"),
                rs.getString("email"),
                rs.getString("phone"),
                rs.getString("chuc_vu"),
                rs.getInt("trang_thai"),
                ngayVaoLam == null ? null : ngayVaoLam.toLocalDate(),
                rs.getString("ghi_chu"),
                toLocalDateTime(rs.getTimestamp("created_at")),
                toLocalDateTime(rs.getTimestamp("updated_at"))
        );
    }

    private java.time.LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
