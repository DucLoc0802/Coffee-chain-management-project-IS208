package com.phungloccoffee.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PhieuXuatKho {
    private String phieuXuatId;
    private String khoId;
    private String nhanVienId;
    private LocalDateTime ngayXuat;
    private String lyDoXuat;
    private int soLuongMatHang;
    private BigDecimal tongTien;
    private String trangThai;
    private String ghiChu;

    public PhieuXuatKho() {
    }

    public PhieuXuatKho(String phieuXuatId, String khoId, String nhanVienId, LocalDateTime ngayXuat,
                        String lyDoXuat, int soLuongMatHang, BigDecimal tongTien, String trangThai, String ghiChu) {
        this.phieuXuatId = phieuXuatId;
        this.khoId = khoId;
        this.nhanVienId = nhanVienId;
        this.ngayXuat = ngayXuat;
        this.lyDoXuat = lyDoXuat;
        this.soLuongMatHang = soLuongMatHang;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public String getPhieuXuatId() { return phieuXuatId; }
    public void setPhieuXuatId(String phieuXuatId) { this.phieuXuatId = phieuXuatId; }
    public String getKhoId() { return khoId; }
    public void setKhoId(String khoId) { this.khoId = khoId; }
    public String getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(String nhanVienId) { this.nhanVienId = nhanVienId; }
    public LocalDateTime getNgayXuat() { return ngayXuat; }
    public void setNgayXuat(LocalDateTime ngayXuat) { this.ngayXuat = ngayXuat; }
    public String getLyDoXuat() { return lyDoXuat; }
    public void setLyDoXuat(String lyDoXuat) { this.lyDoXuat = lyDoXuat; }
    public int getSoLuongMatHang() { return soLuongMatHang; }
    public void setSoLuongMatHang(int soLuongMatHang) { this.soLuongMatHang = soLuongMatHang; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    @Override public String toString() { return phieuXuatId; }
}
