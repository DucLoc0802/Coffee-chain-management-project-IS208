package com.phungloccoffee.bus;

import com.phungloccoffee.dao.PaymentDAO;
import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.exception.PermissionException;
import com.phungloccoffee.exception.ValidationException;
import com.phungloccoffee.model.Payment;
import com.phungloccoffee.util.ValidationUtils;

import java.math.BigDecimal;

public class PaymentBUS extends PermissionBUS {
    private final PaymentDAO paymentDAO = new PaymentDAO();

    public void pay(int orderId, String method, BigDecimal amount) throws ValidationException, PermissionException, DatabaseException {
        requireRole("THU_NGAN", "QUAN_LY_CHI_NHANH");
        if (orderId <= 0) {
            throw new ValidationException("Mã hóa đơn không hợp lệ.");
        }
        ValidationUtils.requireText(method, "Phương thức thanh toán");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Số tiền thanh toán phải lớn hơn 0.");
        }
        paymentDAO.save(new Payment(0, orderId, method, amount, null, "DA_THANH_TOAN"));
    }
}
