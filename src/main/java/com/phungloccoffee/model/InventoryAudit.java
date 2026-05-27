package com.phungloccoffee.model;

import java.time.LocalDateTime;

public class InventoryAudit {
    private int id;
    private String auditCode;
    private String branchName;
    private int auditedBy;
    private LocalDateTime auditedAt;
    private String status;

    public InventoryAudit() {
    }

    public InventoryAudit(int id, String auditCode, String branchName, int auditedBy, LocalDateTime auditedAt, String status) {
        this.id = id;
        this.auditCode = auditCode;
        this.branchName = branchName;
        this.auditedBy = auditedBy;
        this.auditedAt = auditedAt;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAuditCode() { return auditCode; }
    public void setAuditCode(String auditCode) { this.auditCode = auditCode; }
    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
    public int getAuditedBy() { return auditedBy; }
    public void setAuditedBy(int auditedBy) { this.auditedBy = auditedBy; }
    public LocalDateTime getAuditedAt() { return auditedAt; }
    public void setAuditedAt(LocalDateTime auditedAt) { this.auditedAt = auditedAt; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() { return auditCode; }
}

