package com.phungloccoffee.gui.controller.pos;

import com.phungloccoffee.bus.PaymentBUS;
import com.phungloccoffee.util.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

public class PaymentController {
    @FXML private TextField orderIdField;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> methodComboBox;

    private final PaymentBUS paymentBUS = new PaymentBUS();

    @FXML
    private void initialize() {
        methodComboBox.getItems().setAll("Tiền mặt", "Chuyển khoản", "Ví điện tử", "Thẻ ngân hàng");
    }

    @FXML
    private void handlePayment() {
        try {
            paymentBUS.pay(Integer.parseInt(orderIdField.getText()), methodComboBox.getValue(), new BigDecimal(amountField.getText()));
            AlertUtils.showInfo("Thanh toán thành công.");
        } catch (Exception e) {
            AlertUtils.showError(e.getMessage());
        }
    }
}
