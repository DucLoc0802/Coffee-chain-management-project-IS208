package com.phungloccoffee.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HaoHutNguyenLieu {
    private String haoHutId;
    private String sanPhamId;
    private BigDecimal soLuong;
    private String lyDo;
    private LocalDateTime createdAt;

    public HaoHutNguyenLieu() {
    }

    public HaoHutNguyenLieu(String haoHutId, String sanPhamId, BigDecimal soLuong, String lyDo, LocalDateTime createdAt) {
        this.haoHutId = haoHutId;
        this.sanPhamId = sanPhamId;
        this.soLuong = soLuong;
        this.lyDo = lyDo;
        this.createdAt = createdAt;
    }

    public String getHaoHutId() { return haoHutId; }
    public void setHaoHutId(String haoHutId) { this.haoHutId = haoHutId; }
    public String getSanPhamId() { return sanPhamId; }
    public void setSanPhamId(String sanPhamId) { this.sanPhamId = sanPhamId; }
    public BigDecimal getSoLuong() { return soLuong; }
    public void setSoLuong(BigDecimal soLuong) { this.soLuong = soLuong; }
    public String getLyDo() { return lyDo; }
    public void setLyDo(String lyDo) { this.lyDo = lyDo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    @Override public String toString() { return haoHutId; }
}
