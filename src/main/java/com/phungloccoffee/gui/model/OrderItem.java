package com.phungloccoffee.gui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderItem {
    private String productId;
    private String productName;
    private String category;
    private BigDecimal basePrice = BigDecimal.ZERO;
    private int quantity = 1;
    private String sugarLevel;
    private String iceLevel;
    private List<ToppingItem> toppings = new ArrayList<>();
    private String note;
    private BigDecimal lineTotal = BigDecimal.ZERO;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice == null ? BigDecimal.ZERO : basePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = Math.max(1, quantity);
    }

    public String getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(String sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    public String getIceLevel() {
        return iceLevel;
    }

    public void setIceLevel(String iceLevel) {
        this.iceLevel = iceLevel;
    }

    public List<ToppingItem> getToppings() {
        return toppings;
    }

    public void setToppings(List<ToppingItem> toppings) {
        this.toppings = toppings == null ? new ArrayList<>() : new ArrayList<>(toppings);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal == null ? BigDecimal.ZERO : lineTotal;
    }
}
