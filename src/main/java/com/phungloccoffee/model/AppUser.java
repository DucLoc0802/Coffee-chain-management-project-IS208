package com.phungloccoffee.model;

import java.time.LocalDateTime;

public class AppUser {
    private String userId;
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;
    private int trangThai;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String hoTen;
    private String nhanVienId;
    private String chiNhanhId;

    public AppUser() {
    }

    public AppUser(String userId, String tenDangNhap, String matKhau, String vaiTro, int trangThai,
                   LocalDateTime lastLogin, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
        this.lastLogin = lastLogin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(String nhanVienId) { this.nhanVienId = nhanVienId; }
    public String getChiNhanhId() { return chiNhanhId; }
    public void setChiNhanhId(String chiNhanhId) { this.chiNhanhId = chiNhanhId; }

    public String getUsername() { return tenDangNhap; }
    public void setUsername(String username) { this.tenDangNhap = username; }
    public String getPasswordHash() { return matKhau; }
    public void setPasswordHash(String passwordHash) { this.matKhau = passwordHash; }
    public String getFullName() { return hoTen == null ? tenDangNhap : hoTen; }
    public void setFullName(String fullName) { this.hoTen = fullName; }
    public String getRole() { return vaiTro; }
    public void setRole(String role) { this.vaiTro = role; }
    public String getStatus() { return String.valueOf(trangThai); }
    public void setStatus(String status) { this.trangThai = "ACTIVE".equalsIgnoreCase(status) ? 1 : Integer.parseInt(status); }
    public Integer getBranchId() { return null; }

    @Override
    public String toString() {
        return getFullName() + " (" + vaiTro + ")";
    }
}
