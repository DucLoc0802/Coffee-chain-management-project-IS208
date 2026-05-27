package com.phungloccoffee.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {
    private int id;
    private int orderId;
    private String method;
    private BigDecimal amount;
    private LocalDateTime paidAt;
    private String status;

    public Payment() {
    }

    public Payment(int id, int orderId, String method, BigDecimal amount, LocalDateTime paidAt, String status) {
        this.id = id;
        this.orderId = orderId;
        this.method = method;
        this.amount = amount;
        this.paidAt = paidAt;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return method + " - " + amount;
    }
}

