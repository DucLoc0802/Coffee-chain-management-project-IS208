package com.phungloccoffee.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public final class CurrencyFormatter {
    private static final NumberFormat FORMATTER = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    private CurrencyFormatter() {
    }

    public static String format(BigDecimal amount) {
        return FORMATTER.format(amount == null ? BigDecimal.ZERO : amount);
    }
}
