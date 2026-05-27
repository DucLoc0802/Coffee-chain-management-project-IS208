package com.phungloccoffee.util;

import com.phungloccoffee.exception.ValidationException;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    public static void requireText(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " khong duoc de trong.");
        }
    }
}
