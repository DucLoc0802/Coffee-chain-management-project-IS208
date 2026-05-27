package com.phungloccoffee.service.auth;

public class SmsService {
    public void sendPasswordResetOtp(String phoneNumber, String otpCode) {
        String message = buildOtpMessage(otpCode);

        System.out.println("==================================================");
        System.out.println("[SMS DEMO - PHUNG LOC COFFEE]");
        System.out.println("Send to: " + phoneNumber);
        System.out.println("OTP: " + otpCode);
        System.out.println("Message: " + message);
        System.out.println("==================================================");
    }

    private String buildOtpMessage(String otpCode) {
        return "Ma OTP khoi phuc mat khau Phung Loc Coffee cua ban la: "
                + otpCode
                + ". Ma co hieu luc trong 5 phut. Khong chia se ma nay.";
    }
}
