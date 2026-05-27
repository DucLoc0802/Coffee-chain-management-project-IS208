package com.phungloccoffee.model;

public class KhachHang {
    private String khachHangId;
    private String hoTen;
    private String phone;
    private String email;
    private int trangThai;

    public KhachHang() {
    }

    public KhachHang(String khachHangId, String hoTen, String phone, String email, int trangThai) {
        this.khachHangId = khachHangId;
        this.hoTen = hoTen;
        this.phone = phone;
        this.email = email;
        this.trangThai = trangThai;
    }

    public String getKhachHangId() { return khachHangId; }
    public void setKhachHangId(String khachHangId) { this.khachHangId = khachHangId; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
    @Override public String toString() { return hoTen; }
}
