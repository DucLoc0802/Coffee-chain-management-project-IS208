package com.phungloccoffee.model;

public class Employee {
    private int id;
    private String employeeCode;
    private String fullName;
    private String phone;
    private String branchName;
    private String position;
    private String status;

    public Employee() {
    }

    public Employee(int id, String employeeCode, String fullName, String phone, String branchName, String position, String status) {
        this.id = id;
        this.employeeCode = employeeCode;
        this.fullName = fullName;
        this.phone = phone;
        this.branchName = branchName;
        this.position = position;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() { return fullName; }
}

