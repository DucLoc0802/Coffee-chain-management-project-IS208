package com.phungloccoffee.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtils {
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private DateTimeUtils() {
    }

    public static String formatDateTime(LocalDateTime value) {
        return value == null ? "" : value.format(DISPLAY_FORMAT);
    }
}

