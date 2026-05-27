package com.phungloccoffee.util;

import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class PasswordUtil {
    private static final String OTP_SHA256_PREFIX = "OTP_SHA256$";

    private PasswordUtil() {
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    public static boolean verifyPassword(String plainPassword, String passwordHash) {
        if (plainPassword == null || passwordHash == null) {
            return false;
        }

        if (isBcryptHash(passwordHash)) {
            try {
                return BCrypt.checkpw(plainPassword, passwordHash);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        // Temporary compatibility for old demo data that still stores plain-text passwords.
        return plainPassword.equals(passwordHash);
    }

    public static String hashOtp(String otpCode) {
        return OTP_SHA256_PREFIX + sha256(otpCode);
    }

    public static boolean verifyOtp(String otpCode, String otpHash) {
        if (otpCode == null || otpHash == null || !otpHash.startsWith(OTP_SHA256_PREFIX)) {
            return false;
        }
        String expected = hashOtp(otpCode);
        return MessageDigest.isEqual(
                expected.getBytes(StandardCharsets.UTF_8),
                otpHash.getBytes(StandardCharsets.UTF_8)
        );
    }

    private static boolean isBcryptHash(String value) {
        return value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$");
    }

    private static String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available.", e);
        }
    }
}
