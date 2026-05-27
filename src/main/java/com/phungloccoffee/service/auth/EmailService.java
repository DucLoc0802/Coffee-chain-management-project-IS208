package com.phungloccoffee.service.auth;

public class EmailService {
    public void sendPasswordResetOtp(String toEmail, String otpCode) {
        System.out.println("==================================================");
        System.out.println("[EMAIL DEMO - PHUNG LOC COFFEE]");
        System.out.println("Send to: " + toEmail);
        System.out.println("OTP: " + otpCode);
        System.out.println("Subject: Ma xac nhan khoi phuc mat khau - Phung Loc Coffee");
        System.out.println("==================================================");
    }
}
