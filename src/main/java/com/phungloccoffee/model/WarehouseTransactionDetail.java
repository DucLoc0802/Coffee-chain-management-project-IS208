package com.phungloccoffee.model;

import java.math.BigDecimal;

public class WarehouseTransactionDetail {
    private int id;
    private int transactionId;
    private int inventoryItemId;
    private BigDecimal quantity;
    private String note;

    public WarehouseTransactionDetail() {
    }

    public WarehouseTransactionDetail(int id, int transactionId, int inventoryItemId, BigDecimal quantity, String note) {
        this.id = id;
        this.transactionId = transactionId;
        this.inventoryItemId = inventoryItemId;
        this.quantity = quantity;
        this.note = note;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public int getInventoryItemId() { return inventoryItemId; }
    public void setInventoryItemId(int inventoryItemId) { this.inventoryItemId = inventoryItemId; }
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    @Override
    public String toString() { return inventoryItemId + " - " + quantity; }
}

