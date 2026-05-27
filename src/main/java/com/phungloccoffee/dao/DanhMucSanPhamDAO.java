package com.phungloccoffee.dao;

import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.model.ProductCategory;
import com.phungloccoffee.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DanhMucSanPhamDAO {
    public List<ProductCategory> findAll() throws DatabaseException {
        String sql = "SELECT danh_muc_id, ten_danh_muc, mo_ta, created_at, updated_at FROM danh_muc_san_pham ORDER BY ten_danh_muc";
        List<ProductCategory> categories = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categories.add(map(rs));
            }
            return categories;
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải danh mục sản phẩm.", e);
        }
    }

    public Optional<ProductCategory> findById(String danhMucId) throws DatabaseException {
        String sql = "SELECT danh_muc_id, ten_danh_muc, mo_ta, created_at, updated_at FROM danh_muc_san_pham WHERE danh_muc_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, danhMucId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải danh mục sản phẩm.", e);
        }
    }

    private ProductCategory map(ResultSet rs) throws SQLException {
        return new ProductCategory(
                rs.getString("danh_muc_id"),
                rs.getString("ten_danh_muc"),
                rs.getString("mo_ta"),
                toLocalDateTime(rs.getTimestamp("created_at")),
                toLocalDateTime(rs.getTimestamp("updated_at"))
        );
    }

    private java.time.LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
