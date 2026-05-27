package com.phungloccoffee.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private String donHangId;
    private String khachHangId;
    private String chiNhanhId;
    private String nhanVienId;
    private String trangThai;
    private BigDecimal tamTinh;
    private BigDecimal giamGia;
    private BigDecimal tongTien;
    private String trangThaiThanhToan;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Order() {
    }

    public Order(String donHangId, String khachHangId, String chiNhanhId, String nhanVienId, String trangThai,
                 BigDecimal tamTinh, BigDecimal giamGia, BigDecimal tongTien, String trangThaiThanhToan,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.donHangId = donHangId;
        this.khachHangId = khachHangId;
        this.chiNhanhId = chiNhanhId;
        this.nhanVienId = nhanVienId;
        this.trangThai = trangThai;
        this.tamTinh = tamTinh;
        this.giamGia = giamGia;
        this.tongTien = tongTien;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getDonHangId() { return donHangId; }
    public void setDonHangId(String donHangId) { this.donHangId = donHangId; }
    public String getKhachHangId() { return khachHangId; }
    public void setKhachHangId(String khachHangId) { this.khachHangId = khachHangId; }
    public String getChiNhanhId() { return chiNhanhId; }
    public void setChiNhanhId(String chiNhanhId) { this.chiNhanhId = chiNhanhId; }
    public String getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(String nhanVienId) { this.nhanVienId = nhanVienId; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public BigDecimal getTamTinh() { return tamTinh; }
    public void setTamTinh(BigDecimal tamTinh) { this.tamTinh = tamTinh; }
    public BigDecimal getGiamGia() { return giamGia; }
    public void setGiamGia(BigDecimal giamGia) { this.giamGia = giamGia; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public String getTrangThaiThanhToan() { return trangThaiThanhToan; }
    public void setTrangThaiThanhToan(String trangThaiThanhToan) { this.trangThaiThanhToan = trangThaiThanhToan; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getOrderCode() { return donHangId; }
    public BigDecimal getTotalAmount() { return tongTien; }
    public String getStatus() { return trangThai; }

    @Override
    public String toString() {
        return donHangId;
    }
}
