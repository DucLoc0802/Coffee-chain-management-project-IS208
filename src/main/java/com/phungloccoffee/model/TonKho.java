package com.phungloccoffee.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TonKho {
    private String khoId;
    private String sanPhamId;
    private BigDecimal soLuongTon;
    private BigDecimal mucTonToiThieu;
    private LocalDateTime lastUpdated;

    public TonKho() {
    }

    public TonKho(String khoId, String sanPhamId, BigDecimal soLuongTon, BigDecimal mucTonToiThieu, LocalDateTime lastUpdated) {
        this.khoId = khoId;
        this.sanPhamId = sanPhamId;
        this.soLuongTon = soLuongTon;
        this.mucTonToiThieu = mucTonToiThieu;
        this.lastUpdated = lastUpdated;
    }

    public String getKhoId() { return khoId; }
    public void setKhoId(String khoId) { this.khoId = khoId; }
    public String getSanPhamId() { return sanPhamId; }
    public void setSanPhamId(String sanPhamId) { this.sanPhamId = sanPhamId; }
    public BigDecimal getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(BigDecimal soLuongTon) { this.soLuongTon = soLuongTon; }
    public BigDecimal getMucTonToiThieu() { return mucTonToiThieu; }
    public void setMucTonToiThieu(BigDecimal mucTonToiThieu) { this.mucTonToiThieu = mucTonToiThieu; }
    public LocalDateTime getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) { this.lastUpdated = lastUpdated; }
    @Override public String toString() { return khoId + " - " + sanPhamId; }
}
