package com.phungloccoffee.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentHistory {
    private String orderCode;
    private String cashierName;
    private String paymentMethod;
    private BigDecimal amount;
    private LocalDateTime paidAt;
    private String status;

    public PaymentHistory() {
    }

    public PaymentHistory(String orderCode, String cashierName, String paymentMethod, BigDecimal amount, LocalDateTime paidAt, String status) {
        this.orderCode = orderCode;
        this.cashierName = cashierName;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.paidAt = paidAt;
        this.status = status;
    }

    public String getOrderCode() { return orderCode; }
    public void setOrderCode(String orderCode) { this.orderCode = orderCode; }
    public String getCashierName() { return cashierName; }
    public void setCashierName(String cashierName) { this.cashierName = cashierName; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return orderCode;
    }
}

