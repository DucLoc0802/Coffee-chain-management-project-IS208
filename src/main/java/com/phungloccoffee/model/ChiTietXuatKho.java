package com.phungloccoffee.model;

import java.math.BigDecimal;

public class ChiTietXuatKho {
    private String chiTietXuatId;
    private String phieuXuatId;
    private String sanPhamId;
    private BigDecimal soLuong;
    private BigDecimal donGia;

    public ChiTietXuatKho() {
    }

    public ChiTietXuatKho(String chiTietXuatId, String phieuXuatId, String sanPhamId, BigDecimal soLuong, BigDecimal donGia) {
        this.chiTietXuatId = chiTietXuatId;
        this.phieuXuatId = phieuXuatId;
        this.sanPhamId = sanPhamId;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getChiTietXuatId() { return chiTietXuatId; }
    public void setChiTietXuatId(String chiTietXuatId) { this.chiTietXuatId = chiTietXuatId; }
    public String getPhieuXuatId() { return phieuXuatId; }
    public void setPhieuXuatId(String phieuXuatId) { this.phieuXuatId = phieuXuatId; }
    public String getSanPhamId() { return sanPhamId; }
    public void setSanPhamId(String sanPhamId) { this.sanPhamId = sanPhamId; }
    public BigDecimal getSoLuong() { return soLuong; }
    public void setSoLuong(BigDecimal soLuong) { this.soLuong = soLuong; }
    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }
    @Override public String toString() { return chiTietXuatId; }
}
