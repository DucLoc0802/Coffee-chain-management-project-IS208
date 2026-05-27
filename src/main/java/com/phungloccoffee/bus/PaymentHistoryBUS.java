package com.phungloccoffee.bus;

import com.phungloccoffee.dao.PaymentDAO;
import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.exception.PermissionException;
import com.phungloccoffee.model.PaymentHistory;

import java.util.List;

public class PaymentHistoryBUS extends PermissionBUS {
    private final PaymentDAO paymentDAO = new PaymentDAO();

    public List<PaymentHistory> loadHistory() throws DatabaseException, PermissionException {
        requireRole("THU_NGAN", "QUAN_LY_CHI_NHANH", "BAN_GIAM_DOC", "IT_ADMIN");
        return paymentDAO.findHistory();
    }
}
