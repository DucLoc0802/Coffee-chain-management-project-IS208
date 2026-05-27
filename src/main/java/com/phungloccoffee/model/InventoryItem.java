package com.phungloccoffee.model;

import java.math.BigDecimal;

public class InventoryItem {
    private int id;
    private String itemCode;
    private String itemName;
    private String unit;
    private BigDecimal quantityOnHand;
    private BigDecimal reorderLevel;

    public InventoryItem() {
    }

    public InventoryItem(int id, String itemCode, String itemName, String unit, BigDecimal quantityOnHand, BigDecimal reorderLevel) {
        this.id = id;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.unit = unit;
        this.quantityOnHand = quantityOnHand;
        this.reorderLevel = reorderLevel;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getItemCode() { return itemCode; }
    public void setItemCode(String itemCode) { this.itemCode = itemCode; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public BigDecimal getQuantityOnHand() { return quantityOnHand; }
    public void setQuantityOnHand(BigDecimal quantityOnHand) { this.quantityOnHand = quantityOnHand; }
    public BigDecimal getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(BigDecimal reorderLevel) { this.reorderLevel = reorderLevel; }

    @Override
    public String toString() { return itemName; }
}

