package com.phungloccoffee.dao;

import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.model.RevenueReport;
import com.phungloccoffee.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {
    public List<RevenueReport> findRevenue(LocalDate fromDate, LocalDate toDate) throws DatabaseException {
        String sql = """
                SELECT TRUNC(created_at) AS report_date,
                       chi_nhanh_id AS branch_name,
                       COUNT(*) AS order_count,
                       SUM(tong_tien) AS revenue,
                       SUM(giam_gia) AS discount_amount,
                       SUM(tong_tien) AS net_revenue
                FROM don_hang
                WHERE TRUNC(created_at) BETWEEN ? AND ?
                  AND trang_thai = 'DA_HOAN_THANH'
                GROUP BY TRUNC(created_at), chi_nhanh_id
                ORDER BY report_date DESC
                """;
        List<RevenueReport> reports = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fromDate));
            stmt.setDate(2, Date.valueOf(toDate));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reports.add(new RevenueReport(rs.getDate("report_date").toLocalDate(), rs.getString("branch_name"), rs.getInt("order_count"), rs.getBigDecimal("revenue"), rs.getBigDecimal("discount_amount"), rs.getBigDecimal("net_revenue")));
                }
            }
            return reports;
        } catch (SQLException e) {
            throw new DatabaseException("Không thể tải báo cáo doanh thu.", e);
        }
    }
}
