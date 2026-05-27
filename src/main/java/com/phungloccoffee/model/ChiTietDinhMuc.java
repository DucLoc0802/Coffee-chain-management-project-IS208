package com.phungloccoffee.model;

import java.math.BigDecimal;

public class ChiTietDinhMuc {
    private String chiTietDinhMucId;
    private String dinhMucId;
    private String nguyenLieuId;
    private BigDecimal soLuong;

    public ChiTietDinhMuc() {
    }

    public ChiTietDinhMuc(String chiTietDinhMucId, String dinhMucId, String nguyenLieuId, BigDecimal soLuong) {
        this.chiTietDinhMucId = chiTietDinhMucId;
        this.dinhMucId = dinhMucId;
        this.nguyenLieuId = nguyenLieuId;
        this.soLuong = soLuong;
    }

    public String getChiTietDinhMucId() { return chiTietDinhMucId; }
    public void setChiTietDinhMucId(String chiTietDinhMucId) { this.chiTietDinhMucId = chiTietDinhMucId; }
    public String getDinhMucId() { return dinhMucId; }
    public void setDinhMucId(String dinhMucId) { this.dinhMucId = dinhMucId; }
    public String getNguyenLieuId() { return nguyenLieuId; }
    public void setNguyenLieuId(String nguyenLieuId) { this.nguyenLieuId = nguyenLieuId; }
    public BigDecimal getSoLuong() { return soLuong; }
    public void setSoLuong(BigDecimal soLuong) { this.soLuong = soLuong; }
    @Override public String toString() { return chiTietDinhMucId; }
}
