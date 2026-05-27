package com.phungloccoffee.util;

public final class SessionManager {
    private static String userId;
    private static String tenDangNhap;
    private static String vaiTro;
    private static String nhanVienId;
    private static String hoTen;
    private static String chiNhanhId;

    private SessionManager() {
    }

    public static void login(String userId, String tenDangNhap, String vaiTro,
                             String nhanVienId, String hoTen, String chiNhanhId) {
        SessionManager.userId = userId;
        SessionManager.tenDangNhap = tenDangNhap;
        SessionManager.vaiTro = vaiTro;
        SessionManager.nhanVienId = nhanVienId;
        SessionManager.hoTen = hoTen;
        SessionManager.chiNhanhId = chiNhanhId;
    }

    public static void logout() {
        userId = null;
        tenDangNhap = null;
        vaiTro = null;
        nhanVienId = null;
        hoTen = null;
        chiNhanhId = null;
    }

    public static boolean isLoggedIn() {
        return userId != null;
    }

    public static String getCurrentUserId() {
        return userId;
    }

    public static String getCurrentUsername() {
        return tenDangNhap == null ? "Chưa đăng nhập" : tenDangNhap;
    }

    public static String getCurrentRole() {
        return vaiTro == null ? "Chưa xác định" : vaiTro;
    }

    public static String getCurrentEmployeeId() {
        return nhanVienId;
    }

    public static String getCurrentEmployeeName() {
        if (hoTen != null && !hoTen.isBlank()) {
            return hoTen;
        }
        return getCurrentUsername();
    }

    public static String getCurrentBranchId() {
        return chiNhanhId;
    }

    public static boolean hasRole(String role) {
        return vaiTro != null && vaiTro.equalsIgnoreCase(role);
    }

    public static boolean hasAnyRole(String... roles) {
        if (vaiTro == null) {
            return false;
        }
        for (String role : roles) {
            if (vaiTro.equalsIgnoreCase(role)) {
                return true;
            }
        }
        return false;
    }

    public static String toVietnameseRole(String role) {
        if (role == null) {
            return "Chưa xác định";
        }
        return switch (role) {
            case "THU_NGAN" -> "Thu ngân";
            case "QUAN_LY_CHI_NHANH" -> "Quản lý chi nhánh";
            case "NHAN_VIEN_KHO" -> "Nhân viên kho";
            case "IT_ADMIN" -> "IT Admin";
            case "BAN_GIAM_DOC" -> "Ban giám đốc";
            case "NHAN_VIEN_PHA_CHE" -> "Nhân viên pha chế";
            case "NHAN_VIEN_PHUC_VU" -> "Nhân viên phục vụ";
            default -> role;
        };
    }
}
