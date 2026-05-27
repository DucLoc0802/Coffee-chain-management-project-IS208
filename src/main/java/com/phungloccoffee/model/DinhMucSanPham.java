package com.phungloccoffee.model;

public class DinhMucSanPham {
    private String dinhMucId;
    private String sanPhamId;
    private int trangThai;

    public DinhMucSanPham() {
    }

    public DinhMucSanPham(String dinhMucId, String sanPhamId, int trangThai) {
        this.dinhMucId = dinhMucId;
        this.sanPhamId = sanPhamId;
        this.trangThai = trangThai;
    }

    public String getDinhMucId() { return dinhMucId; }
    public void setDinhMucId(String dinhMucId) { this.dinhMucId = dinhMucId; }
    public String getSanPhamId() { return sanPhamId; }
    public void setSanPhamId(String sanPhamId) { this.sanPhamId = sanPhamId; }
    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }
    @Override public String toString() { return dinhMucId; }
}
