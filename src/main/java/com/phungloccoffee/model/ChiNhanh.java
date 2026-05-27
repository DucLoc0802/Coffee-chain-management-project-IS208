package com.phungloccoffee.model;

import java.time.LocalDateTime;

public class ChiNhanh {
    private String chiNhanhId;
    private String tenChiNhanh;
    private String diaChi;
    private String phone;
    private int trangThai;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ChiNhanh() {
    }

    public ChiNhanh(String chiNhanhId, String tenChiNhanh, String diaChi, String phone, int trangThai) {
        this(chiNhanhId, tenChiNhanh, diaChi, phone, trangThai, null, null);
    }

    public ChiNhanh(String chiNhanhId, String tenChiNhanh, String diaChi, String phone, int trangThai,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.chiNhanhId = chiNhanhId;
        this.tenChiNhanh = tenChiNhanh;
        this.diaChi = diaChi;
        this.phone = phone;
        this.trangThai = trangThai;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getChiNhanhId() { return chiNhanhId; }
    public void setChiNhanhId(String chiNhanhId) { this.chiNhanhId = chiNhanhId; }
    public String getTenChiNhanh() { return tenChiNhanh; }
    public void setTenChiNhanh(String tenChiNhanh) { this.tenChiNhanh = tenChiNhanh; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    @Override public String toString() { return tenChiNhanh; }
}
