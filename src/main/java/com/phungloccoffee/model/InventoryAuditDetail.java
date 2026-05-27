package com.phungloccoffee.model;

import java.math.BigDecimal;

public class InventoryAuditDetail {
    private int id;
    private int auditId;
    private int inventoryItemId;
    private BigDecimal systemQuantity;
    private BigDecimal actualQuantity;
    private String note;

    public InventoryAuditDetail() {
    }

    public InventoryAuditDetail(int id, int auditId, int inventoryItemId, BigDecimal systemQuantity, BigDecimal actualQuantity, String note) {
        this.id = id;
        this.auditId = auditId;
        this.inventoryItemId = inventoryItemId;
        this.systemQuantity = systemQuantity;
        this.actualQuantity = actualQuantity;
        this.note = note;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAuditId() { return auditId; }
    public void setAuditId(int auditId) { this.auditId = auditId; }
    public int getInventoryItemId() { return inventoryItemId; }
    public void setInventoryItemId(int inventoryItemId) { this.inventoryItemId = inventoryItemId; }
    public BigDecimal getSystemQuantity() { return systemQuantity; }
    public void setSystemQuantity(BigDecimal systemQuantity) { this.systemQuantity = systemQuantity; }
    public BigDecimal getActualQuantity() { return actualQuantity; }
    public void setActualQuantity(BigDecimal actualQuantity) { this.actualQuantity = actualQuantity; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    @Override
    public String toString() { return inventoryItemId + " audit"; }
}

