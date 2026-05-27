package com.phungloccoffee.bus;

import com.phungloccoffee.dao.OrderDAO;
import com.phungloccoffee.dao.ProductDAO;
import com.phungloccoffee.exception.DatabaseException;
import com.phungloccoffee.exception.PermissionException;
import com.phungloccoffee.exception.ValidationException;
import com.phungloccoffee.model.Order;
import com.phungloccoffee.model.OrderDetail;
import com.phungloccoffee.model.Product;
import com.phungloccoffee.util.SessionManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class POSBUS extends PermissionBUS {
    private final ProductDAO productDAO = new ProductDAO();
    private final OrderDAO orderDAO = new OrderDAO();

    public List<Product> loadMenuProducts() throws DatabaseException, PermissionException {
        requireRole("THU_NGAN", "QUAN_LY_CHI_NHANH", "IT_ADMIN");
        return productDAO.findActiveProducts();
    }

    public void createOrder(List<OrderDetail> details)
            throws ValidationException, PermissionException, DatabaseException {
        requireRole("THU_NGAN", "QUAN_LY_CHI_NHANH");
        if (details == null || details.isEmpty()) {
            throw new ValidationException("Giỏ hàng chưa có sản phẩm.");
        }

        BigDecimal total = details.stream()
                .map(OrderDetail::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order(
                "DH" + System.currentTimeMillis(),
                null,
                SessionManager.getCurrentBranchId(),
                SessionManager.getCurrentEmployeeId(),
                "DANG_TAO",
                total,
                BigDecimal.ZERO,
                total,
                "CHUA_THANH_TOAN",
                LocalDateTime.now(),
                LocalDateTime.now());
        orderDAO.createOrder(order, details);
    }
}
