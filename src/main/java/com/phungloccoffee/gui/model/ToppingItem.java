package com.phungloccoffee.gui.model;

import java.math.BigDecimal;

public class ToppingItem {
    private String toppingId;
    private String toppingName;
    private BigDecimal price;

    public ToppingItem() {
    }

    public ToppingItem(String toppingId, String toppingName, BigDecimal price) {
        this.toppingId = toppingId;
        this.toppingName = toppingName;
        this.price = price;
    }

    public String getToppingId() {
        return toppingId;
    }

    public void setToppingId(String toppingId) {
        this.toppingId = toppingId;
    }

    public String getToppingName() {
        return toppingName;
    }

    public void setToppingName(String toppingName) {
        this.toppingName = toppingName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
