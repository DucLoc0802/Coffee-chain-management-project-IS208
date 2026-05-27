package com.phungloccoffee.model;

public class PosDevice {
    private String posDeviceId;
    private String chiNhanhId;
    private String tenThietBi;
    private int trangThai;

    public PosDevice() {
    }

    public PosDevice(String posDeviceId, String chiNhanhId, String tenThietBi, int trangThai) {
        this.posDeviceId = posDeviceId;
        this.chiNhanhId = chiNhanhId;
        this.tenThietBi = tenThietBi;
        this.trangThai = trangThai;
    }

    public String getPosDeviceId() { return posDeviceId; }
    public void setPosDeviceId(String posDeviceId) { this.posDeviceId = posDeviceId; }
    public String getChiNhanhId() { return chiNhanhId; }
    public void setChiNhanhId(String chiNhanhId) { this.chiNhanhId = chiNhanhId; }
    public String getTenThietBi() { return tenThietBi; }
    public void setTenThietBi(String tenThietBi) { this.tenThietBi = tenThietBi; }
    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
    @Override public String toString() { return tenThietBi; }
}
