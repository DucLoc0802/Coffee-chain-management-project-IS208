package com.phungloccoffee.gui.model;

public enum AppRole {
    THU_NGAN,
    QUAN_LY_CHI_NHANH,
    NHAN_VIEN_KHO,
    IT_ADMIN,
    BAN_GIAM_DOC;

    public String getDisplayName() {
        return switch (this) {
            case THU_NGAN -> "Thu ngân";
            case QUAN_LY_CHI_NHANH -> "Quản lý chi nhánh";
            case NHAN_VIEN_KHO -> "Nhân viên kho";
            case IT_ADMIN -> "IT Admin";
            case BAN_GIAM_DOC -> "Ban giám đốc";
        };
    }
}
