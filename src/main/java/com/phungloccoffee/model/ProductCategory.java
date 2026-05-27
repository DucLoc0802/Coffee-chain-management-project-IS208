package com.phungloccoffee.model;

import java.time.LocalDateTime;

public class ProductCategory {
    private String danhMucId;
    private String tenDanhMuc;
    private String moTa;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductCategory() {
    }

    public ProductCategory(String danhMucId, String tenDanhMuc, String moTa, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.danhMucId = danhMucId;
        this.tenDanhMuc = tenDanhMuc;
        this.moTa = moTa;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getDanhMucId() { return danhMucId; }
    public void setDanhMucId(String danhMucId) { this.danhMucId = danhMucId; }
    public String getTenDanhMuc() { return tenDanhMuc; }
    public void setTenDanhMuc(String tenDanhMuc) { this.tenDanhMuc = tenDanhMuc; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getName() { return tenDanhMuc; }
    public String getDescription() { return moTa; }

    @Override
    public String toString() {
        return tenDanhMuc;
    }
}
