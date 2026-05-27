package com.phungloccoffee.model;

public class Kho {
    private String khoId;
    private String chiNhanhId;
    private String tenKho;
    private int trangThai;

    public Kho() {
    }

    public Kho(String khoId, String chiNhanhId, String tenKho, int trangThai) {
        this.khoId = khoId;
        this.chiNhanhId = chiNhanhId;
        this.tenKho = tenKho;
        this.trangThai = trangThai;
    }

    public String getKhoId() { return khoId; }
    public void setKhoId(String khoId) { this.khoId = khoId; }
    public String getChiNhanhId() { return chiNhanhId; }
    public void setChiNhanhId(String chiNhanhId) { this.chiNhanhId = chiNhanhId; }
    public String getTenKho() { return tenKho; }
    public void setTenKho(String tenKho) { this.tenKho = tenKho; }
    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
    @Override public String toString() { return tenKho; }
}
