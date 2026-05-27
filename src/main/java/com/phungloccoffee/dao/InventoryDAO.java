package com.phungloccoffee.dao;

import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.model.InventoryItem;
import com.phungloccoffee.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InventoryDAO {
    public List<InventoryItem> findAll() throws DatabaseException {
        return findByKho(null);
    }

    public List<InventoryItem> findByKho(String khoId) throws DatabaseException {
        String sql = """
                SELECT tk.kho_id, tk.san_pham_id, sp.ten_san_pham, sp.don_vi_tinh,
                       tk.so_luong_ton, tk.muc_ton_toi_thieu
                FROM ton_kho tk
                JOIN san_pham sp ON sp.san_pham_id = tk.san_pham_id
                WHERE (? IS NULL OR tk.kho_id = ?)
                ORDER BY sp.ten_san_pham
                """;
        List<InventoryItem> items = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, khoId);
            stmt.setString(2, khoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(new InventoryItem(
                            0,
                            rs.getString("san_pham_id"),
                            rs.getString("ten_san_pham"),
                            rs.getString("don_vi_tinh"),
                            rs.getBigDecimal("so_luong_ton"),
                            rs.getBigDecimal("muc_ton_toi_thieu")
                    ));
                }
            }
            return items;
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải tồn kho.", e);
        }
    }

    public Optional<InventoryItem> findByKhoAndSanPham(String khoId, String sanPhamId) throws DatabaseException {
        return findByKho(khoId).stream().filter(item -> sanPhamId.equals(item.getItemCode())).findFirst();
    }

    public void updateSoLuongTon(String khoId, String sanPhamId, BigDecimal newQuantity) throws DatabaseException {
        String sql = "UPDATE ton_kho SET so_luong_ton = ?, last_updated = SYSTIMESTAMP WHERE kho_id = ? AND san_pham_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, newQuantity);
            stmt.setString(2, khoId);
            stmt.setString(3, sanPhamId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể cập nhật tồn kho.", e);
        }
    }

    public boolean hasEnoughStock(String khoId, String sanPhamId, BigDecimal quantity) throws DatabaseException {
        return findByKhoAndSanPham(khoId, sanPhamId)
                .map(item -> item.getQuantityOnHand().compareTo(quantity) >= 0)
                .orElse(false);
    }
}
