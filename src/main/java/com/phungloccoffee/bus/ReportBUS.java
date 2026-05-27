package com.phungloccoffee.bus;

import com.phungloccoffee.dao.ReportDAO;
import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.exception.PermissionException;
import com.phungloccoffee.exception.ValidationException;
import com.phungloccoffee.model.RevenueReport;

import java.time.LocalDate;
import java.util.List;

public class ReportBUS extends PermissionBUS {
    private final ReportDAO reportDAO = new ReportDAO();

    public List<RevenueReport> loadRevenue(LocalDate fromDate, LocalDate toDate) throws ValidationException, PermissionException, DatabaseException {
        requireRole("QUAN_LY_CHI_NHANH", "BAN_GIAM_DOC", "IT_ADMIN");
        if (fromDate == null || toDate == null || fromDate.isAfter(toDate)) {
            throw new ValidationException("Khoảng ngày báo cáo không hợp lệ.");
        }
        return reportDAO.findRevenue(fromDate, toDate);
    }
}
