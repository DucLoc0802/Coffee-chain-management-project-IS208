package com.phungloccoffee.dao;

import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.model.PhieuXuatKho;
import com.phungloccoffee.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PhieuXuatKhoDAO {
    public void insert(PhieuXuatKho phieu) throws DatabaseException {
        String sql = """
                INSERT INTO phieu_xuat_kho (phieu_xuat_id, kho_id, nhan_vien_id, ngay_xuat,
                                            ly_do_xuat, so_luong_mat_hang, tong_tien, trang_thai, ghi_chu)
                VALUES (?, ?, ?, SYSTIMESTAMP, ?, ?, ?, ?, ?)
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phieu.getPhieuXuatId());
            stmt.setString(2, phieu.getKhoId());
            stmt.setString(3, phieu.getNhanVienId());
            stmt.setString(4, phieu.getLyDoXuat());
            stmt.setInt(5, phieu.getSoLuongMatHang());
            stmt.setBigDecimal(6, phieu.getTongTien());
            stmt.setString(7, phieu.getTrangThai());
            stmt.setString(8, phieu.getGhiChu());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể thêm phiếu xuất kho.", e);
        }
    }
}
