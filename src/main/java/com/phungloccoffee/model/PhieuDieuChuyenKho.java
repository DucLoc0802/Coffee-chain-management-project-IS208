package com.phungloccoffee.model;

import java.time.LocalDateTime;

public class PhieuDieuChuyenKho {
    private String phieuDieuChuyenId;
    private String khoNguonId;
    private String khoDichId;
    private String nhanVienId;
    private LocalDateTime ngayDieuChuyen;
    private String trangThai;

    public PhieuDieuChuyenKho() {
    }

    public PhieuDieuChuyenKho(String phieuDieuChuyenId, String khoNguonId, String khoDichId, String nhanVienId, LocalDateTime ngayDieuChuyen, String trangThai) {
        this.phieuDieuChuyenId = phieuDieuChuyenId;
        this.khoNguonId = khoNguonId;
        this.khoDichId = khoDichId;
        this.nhanVienId = nhanVienId;
        this.ngayDieuChuyen = ngayDieuChuyen;
        this.trangThai = trangThai;
    }

    public String getPhieuDieuChuyenId() { return phieuDieuChuyenId; }
    public void setPhieuDieuChuyenId(String phieuDieuChuyenId) { this.phieuDieuChuyenId = phieuDieuChuyenId; }
    public String getKhoNguonId() { return khoNguonId; }
    public void setKhoNguonId(String khoNguonId) { this.khoNguonId = khoNguonId; }
    public String getKhoDichId() { return khoDichId; }
    public void setKhoDichId(String khoDichId) { this.khoDichId = khoDichId; }
    public String getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(String nhanVienId) { this.nhanVienId = nhanVienId; }
    public LocalDateTime getNgayDieuChuyen() { return ngayDieuChuyen; }
    public void setNgayDieuChuyen(LocalDateTime ngayDieuChuyen) { this.ngayDieuChuyen = ngayDieuChuyen; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    @Override public String toString() { return phieuDieuChuyenId; }
}
