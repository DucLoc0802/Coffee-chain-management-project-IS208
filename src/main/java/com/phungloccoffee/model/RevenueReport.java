package com.phungloccoffee.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RevenueReport {
    private LocalDate reportDate;
    private String branchName;
    private int orderCount;
    private BigDecimal revenue;
    private BigDecimal discountAmount;
    private BigDecimal netRevenue;

    public RevenueReport() {
    }

    public RevenueReport(LocalDate reportDate, String branchName, int orderCount, BigDecimal revenue, BigDecimal discountAmount, BigDecimal netRevenue) {
        this.reportDate = reportDate;
        this.branchName = branchName;
        this.orderCount = orderCount;
        this.revenue = revenue;
        this.discountAmount = discountAmount;
        this.netRevenue = netRevenue;
    }

    public LocalDate getReportDate() { return reportDate; }
    public void setReportDate(LocalDate reportDate) { this.reportDate = reportDate; }
    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
    public int getOrderCount() { return orderCount; }
    public void setOrderCount(int orderCount) { this.orderCount = orderCount; }
    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
    public BigDecimal getNetRevenue() { return netRevenue; }
    public void setNetRevenue(BigDecimal netRevenue) { this.netRevenue = netRevenue; }

    @Override
    public String toString() { return branchName + " - " + reportDate; }
}

