package com.phungloccoffee.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PhieuNhapKho {
    private String phieuNhapId;
    private String khoId;
    private String nhaCungCapId;
    private String nhanVienId;
    private LocalDateTime ngayNhap;
    private BigDecimal tongSoLuong;
    private int soLuongMatHang;
    private String trangThai;
    private String ghiChu;

    public PhieuNhapKho() {
    }

    public PhieuNhapKho(String phieuNhapId, String khoId, String nhaCungCapId, String nhanVienId,
                        LocalDateTime ngayNhap, BigDecimal tongSoLuong, int soLuongMatHang, String trangThai, String ghiChu) {
        this.phieuNhapId = phieuNhapId;
        this.khoId = khoId;
        this.nhaCungCapId = nhaCungCapId;
        this.nhanVienId = nhanVienId;
        this.ngayNhap = ngayNhap;
        this.tongSoLuong = tongSoLuong;
        this.soLuongMatHang = soLuongMatHang;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
    }

    public String getPhieuNhapId() { return phieuNhapId; }
    public void setPhieuNhapId(String phieuNhapId) { this.phieuNhapId = phieuNhapId; }
    public String getKhoId() { return khoId; }
    public void setKhoId(String khoId) { this.khoId = khoId; }
    public String getNhaCungCapId() { return nhaCungCapId; }
    public void setNhaCungCapId(String nhaCungCapId) { this.nhaCungCapId = nhaCungCapId; }
    public String getNhanVienId() { return nhanVienId; }
    public void setNhanVienId(String nhanVienId) { this.nhanVienId = nhanVienId; }
    public LocalDateTime getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(LocalDateTime ngayNhap) { this.ngayNhap = ngayNhap; }
    public BigDecimal getTongSoLuong() { return tongSoLuong; }
    public void setTongSoLuong(BigDecimal tongSoLuong) { this.tongSoLuong = tongSoLuong; }
    public int getSoLuongMatHang() { return soLuongMatHang; }
    public void setSoLuongMatHang(int soLuongMatHang) { this.soLuongMatHang = soLuongMatHang; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    @Override public String toString() { return phieuNhapId; }
}
