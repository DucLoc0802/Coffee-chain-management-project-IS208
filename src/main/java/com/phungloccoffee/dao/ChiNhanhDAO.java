package com.phungloccoffee.dao;

import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.model.ChiNhanh;
import com.phungloccoffee.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ChiNhanhDAO {
    public boolean existsById(String chiNhanhId) throws DatabaseException {
        String sql = """
                SELECT COUNT(*)
                FROM chi_nhanh
                WHERE chi_nhanh_id = ?
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, chiNhanhId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Không thể kiểm tra chi nhánh.", e);
        }
    }

    public List<ChiNhanh> findAllActive() throws DatabaseException {
        String sql = """
                SELECT chi_nhanh_id, ten_chi_nhanh, phone, dia_chi, trang_thai, created_at, updated_at
                FROM chi_nhanh
                WHERE trang_thai = 1
                ORDER BY ten_chi_nhanh
                """;
        List<ChiNhanh> branches = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                branches.add(map(rs));
            }
            return branches;
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải danh sách chi nhánh.", e);
        }
    }

    public ChiNhanh findById(String chiNhanhId) throws DatabaseException {
        String sql = """
                SELECT chi_nhanh_id, ten_chi_nhanh, phone, dia_chi, trang_thai, created_at, updated_at
                FROM chi_nhanh
                WHERE chi_nhanh_id = ?
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, chiNhanhId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải chi nhánh.", e);
        }
    }

    private ChiNhanh map(ResultSet rs) throws SQLException {
        return new ChiNhanh(
                rs.getString("chi_nhanh_id"),
                rs.getString("ten_chi_nhanh"),
                rs.getString("dia_chi"),
                rs.getString("phone"),
                rs.getInt("trang_thai"),
                toLocalDateTime(rs.getTimestamp("created_at")),
                toLocalDateTime(rs.getTimestamp("updated_at"))
        );
    }

    private java.time.LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
