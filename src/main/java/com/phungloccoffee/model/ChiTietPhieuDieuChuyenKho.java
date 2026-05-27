package com.phungloccoffee.model;

import java.math.BigDecimal;

public class ChiTietPhieuDieuChuyenKho {
    private String chiTietDieuChuyenId;
    private String phieuDieuChuyenId;
    private String sanPhamId;
    private BigDecimal soLuong;

    public ChiTietPhieuDieuChuyenKho() {
    }

    public ChiTietPhieuDieuChuyenKho(String chiTietDieuChuyenId, String phieuDieuChuyenId, String sanPhamId, BigDecimal soLuong) {
        this.chiTietDieuChuyenId = chiTietDieuChuyenId;
        this.phieuDieuChuyenId = phieuDieuChuyenId;
        this.sanPhamId = sanPhamId;
        this.soLuong = soLuong;
    }

    public String getChiTietDieuChuyenId() { return chiTietDieuChuyenId; }
    public void setChiTietDieuChuyenId(String chiTietDieuChuyenId) { this.chiTietDieuChuyenId = chiTietDieuChuyenId; }
    public String getPhieuDieuChuyenId() { return phieuDieuChuyenId; }
    public void setPhieuDieuChuyenId(String phieuDieuChuyenId) { this.phieuDieuChuyenId = phieuDieuChuyenId; }
    public String getSanPhamId() { return sanPhamId; }
    public void setSanPhamId(String sanPhamId) { this.sanPhamId = sanPhamId; }
    public BigDecimal getSoLuong() { return soLuong; }
    public void setSoLuong(BigDecimal soLuong) { this.soLuong = soLuong; }
    @Override public String toString() { return chiTietDieuChuyenId; }
}
