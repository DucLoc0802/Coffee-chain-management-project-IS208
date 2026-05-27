package com.phungloccoffee.model;

import java.time.LocalDateTime;

public class WarehouseTransaction {
    private int id;
    private String transactionCode;
    private String transactionType;
    private String branchName;
    private String status;
    private int createdBy;
    private LocalDateTime createdAt;

    public WarehouseTransaction() {
    }

    public WarehouseTransaction(int id, String transactionCode, String transactionType, String branchName, String status, int createdBy, LocalDateTime createdAt) {
        this.id = id;
        this.transactionCode = transactionCode;
        this.transactionType = transactionType;
        this.branchName = branchName;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTransactionCode() { return transactionCode; }
    public void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }
    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() { return transactionCode; }
}

