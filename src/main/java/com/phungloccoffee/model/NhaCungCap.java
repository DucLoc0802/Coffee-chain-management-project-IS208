package com.phungloccoffee.model;

public class NhaCungCap {
    private String nhaCungCapId;
    private String tenNhaCungCap;
    private String phone;
    private String email;
    private int trangThai;

    public NhaCungCap() {
    }

    public NhaCungCap(String nhaCungCapId, String tenNhaCungCap, String phone, String email, int trangThai) {
        this.nhaCungCapId = nhaCungCapId;
        this.tenNhaCungCap = tenNhaCungCap;
        this.phone = phone;
        this.email = email;
        this.trangThai = trangThai;
    }

    public String getNhaCungCapId() { return nhaCungCapId; }
    public void setNhaCungCapId(String nhaCungCapId) { this.nhaCungCapId = nhaCungCapId; }
    public String getTenNhaCungCap() { return tenNhaCungCap; }
    public void setTenNhaCungCap(String tenNhaCungCap) { this.tenNhaCungCap = tenNhaCungCap; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
    @Override public String toString() { return tenNhaCungCap; }
}
