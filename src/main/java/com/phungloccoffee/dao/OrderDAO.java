package com.phungloccoffee.dao;

import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.model.Order;
import com.phungloccoffee.model.OrderDetail;
import com.phungloccoffee.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDAO {
    public void createOrder(Order order, List<OrderDetail> details) throws DatabaseException {
        insert(order);
        ChiTietDonHangDAO detailDAO = new ChiTietDonHangDAO();
        for (OrderDetail detail : details) {
            detail.setDonHangId(order.getDonHangId());
            detailDAO.insert(detail);
        }
    }

    public void insert(Order order) throws DatabaseException {
        String sql = """
                INSERT INTO don_hang (don_hang_id, khach_hang_id, chi_nhanh_id, nhan_vien_id, trang_thai,
                                      tam_tinh, giam_gia, tong_tien, trang_thai_thanh_toan, created_at, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, SYSTIMESTAMP, SYSTIMESTAMP)
                """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getDonHangId());
            stmt.setString(2, order.getKhachHangId());
            stmt.setString(3, order.getChiNhanhId());
            stmt.setString(4, order.getNhanVienId());
            stmt.setString(5, order.getTrangThai());
            stmt.setBigDecimal(6, order.getTamTinh());
            stmt.setBigDecimal(7, order.getGiamGia());
            stmt.setBigDecimal(8, order.getTongTien());
            stmt.setString(9, order.getTrangThaiThanhToan());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tạo đơn hàng.", e);
        }
    }

    public Optional<Order> findById(String donHangId) throws DatabaseException {
        String sql = "SELECT * FROM don_hang WHERE don_hang_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, donHangId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải đơn hàng.", e);
        }
    }

    public void updatePaymentStatus(String donHangId, String status) throws DatabaseException {
        String sql = "UPDATE don_hang SET trang_thai_thanh_toan = ?, updated_at = SYSTIMESTAMP WHERE don_hang_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, donHangId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Không thể cập nhật thanh toán đơn hàng.", e);
        }
    }

    public List<Order> findRecentOrders() throws DatabaseException {
        String sql = "SELECT * FROM don_hang ORDER BY created_at DESC FETCH FIRST 50 ROWS ONLY";
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                orders.add(map(rs));
            }
            return orders;
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải đơn hàng gần đây.", e);
        }
    }

    private Order map(ResultSet rs) throws SQLException {
        return new Order(
                rs.getString("don_hang_id"),
                rs.getString("khach_hang_id"),
                rs.getString("chi_nhanh_id"),
                rs.getString("nhan_vien_id"),
                rs.getString("trang_thai"),
                rs.getBigDecimal("tam_tinh"),
                rs.getBigDecimal("giam_gia"),
                rs.getBigDecimal("tong_tien"),
                rs.getString("trang_thai_thanh_toan"),
                toLocalDateTime(rs.getTimestamp("created_at")),
                toLocalDateTime(rs.getTimestamp("updated_at"))
        );
    }

    private java.time.LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
