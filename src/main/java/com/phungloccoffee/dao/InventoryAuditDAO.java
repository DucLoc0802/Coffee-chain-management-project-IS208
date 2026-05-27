package com.phungloccoffee.dao;

import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.model.InventoryAudit;
import com.phungloccoffee.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryAuditDAO {
    public List<InventoryAudit> findRecentAudits() throws DatabaseException {
        String sql = """
                SELECT kk.kiem_ke_id, kk.kho_id, kk.nhan_vien_id, kk.ngay_kiem_ke, kk.trang_thai
                FROM kiem_ke_kho kk
                ORDER BY kk.ngay_kiem_ke DESC
                """;
        List<InventoryAudit> audits = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                audits.add(new InventoryAudit(
                        0,
                        rs.getString("kiem_ke_id"),
                        rs.getString("kho_id"),
                        0,
                        rs.getTimestamp("ngay_kiem_ke") == null ? null : rs.getTimestamp("ngay_kiem_ke").toLocalDateTime(),
                        rs.getString("trang_thai")
                ));
            }
            return audits;
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải lịch sử kiểm kê.", e);
        }
    }
}
