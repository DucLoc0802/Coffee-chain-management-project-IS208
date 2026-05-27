package com.phungloccoffee.gui.model;

import java.math.BigDecimal;
import java.util.Set;

public class ProductOption {
    private static final Set<String> DRINK_CATEGORIES = Set.of("Cà phê", "Trà sữa", "Trà");

    private final String productId;
    private final String productName;
    private final String category;
    private final BigDecimal basePrice;
    private final ProductStatus status;

    public ProductOption(String productId, String productName, String category, BigDecimal basePrice, ProductStatus status) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.basePrice = basePrice;
        this.status = status;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public boolean isDrink() {
        return DRINK_CATEGORIES.contains(category);
    }

    public boolean isToppingCategory() {
        return "Topping".equals(category);
    }

    public enum ProductStatus {
        AVAILABLE("Còn phục vụ"),
        OUT_OF_STOCK("Hết nguyên liệu"),
        PAUSED("Tạm ngưng");

        private final String label;

        ProductStatus(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }

        public boolean isAvailable() {
            return this == AVAILABLE;
        }
    }
}
