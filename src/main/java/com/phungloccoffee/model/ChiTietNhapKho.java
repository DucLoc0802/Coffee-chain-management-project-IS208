package com.phungloccoffee.model;

import java.math.BigDecimal;

public class ChiTietNhapKho {
    private String chiTietNhapId;
    private String phieuNhapId;
    private String sanPhamId;
    private BigDecimal soLuong;
    private BigDecimal donGia;

    public ChiTietNhapKho() {
    }

    public ChiTietNhapKho(String chiTietNhapId, String phieuNhapId, String sanPhamId, BigDecimal soLuong, BigDecimal donGia) {
        this.chiTietNhapId = chiTietNhapId;
        this.phieuNhapId = phieuNhapId;
        this.sanPhamId = sanPhamId;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getChiTietNhapId() { return chiTietNhapId; }
    public void setChiTietNhapId(String chiTietNhapId) { this.chiTietNhapId = chiTietNhapId; }
    public String getPhieuNhapId() { return phieuNhapId; }
    public void setPhieuNhapId(String phieuNhapId) { this.phieuNhapId = phieuNhapId; }
    public String getSanPhamId() { return sanPhamId; }
    public void setSanPhamId(String sanPhamId) { this.sanPhamId = sanPhamId; }
    public BigDecimal getSoLuong() { return soLuong; }
    public void setSoLuong(BigDecimal soLuong) { this.soLuong = soLuong; }
    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }
    @Override public String toString() { return chiTietNhapId; }
}
