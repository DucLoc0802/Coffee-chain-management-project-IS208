package com.phungloccoffee.dao;

import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.model.PhieuNhapKho;
import com.phungloccoffee.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PhieuNhapKhoDAO {
    public void insert(PhieuNhapKho phieu) throws DatabaseException {
        String sql = """
                INSERT INTO phieu_nhap_kho (phieu_nhap_id, kho_id, nha_cung_cap_id, nhan_vien_id,
                                            ngay_nhap, tong_so_luong, so_luong_mat_hang, trang_thai, ghi_chu)
                VALUES (?, ?, ?, ?, SYSTIMESTAMP, ?, ?, ?, ?)
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, phieu.getPhieuNhapId());
            stmt.setString(2, phieu.getKhoId());
            stmt.setString(3, phieu.getNhaCungCapId());
            stmt.setString(4, phieu.getNhanVienId());
            stmt.setBigDecimal(5, phieu.getTongSoLuong());
            stmt.setInt(6, phieu.getSoLuongMatHang());
            stmt.setString(7, phieu.getTrangThai());
            stmt.setString(8, phieu.getGhiChu());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể thêm phiếu nhập kho.", e);
        }
    }
}
