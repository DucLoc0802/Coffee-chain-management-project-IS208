package com.phungloccoffee.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NhanVien {
    private String nhanVienId;
    private String userId;
    private String chiNhanhId;
    private String hoTen;
    private String cccd;
    private String email;
    private String phone;
    private String chucVu;
    private int trangThai;
    private LocalDate ngayVaoLam;
    private String ghiChu;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NhanVien() {
    }

    public NhanVien(String nhanVienId, String userId, String chiNhanhId, String hoTen, String cccd,
                    String email, String phone, String chucVu, int trangThai, LocalDate ngayVaoLam,
                    String ghiChu, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.nhanVienId = nhanVienId;
        this.userId = userId;
        this.chiNhanhId = chiNhanhId;
        this.hoTen = hoTen;
        this.cccd = cccd;
        this.email = email;
        this.phone = phone;
        this.chucVu = chucVu;
        this.trangThai = trangThai;
        this.ngayVaoLam = ngayVaoLam;
        this.ghiChu = ghiChu;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(String nhanVienId) { this.nhanVienId = nhanVienId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getChiNhanhId() { return chiNhanhId; }
    public void setChiNhanhId(String chiNhanhId) { this.chiNhanhId = chiNhanhId; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }
    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
    public LocalDate getNgayVaoLam() { return ngayVaoLam; }
    public void setNgayVaoLam(LocalDate ngayVaoLam) { this.ngayVaoLam = ngayVaoLam; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return hoTen;
    }
}
