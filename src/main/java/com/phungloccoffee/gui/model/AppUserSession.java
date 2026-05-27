package com.phungloccoffee.gui.model;

public class AppUserSession {
    private String userId;
    private String username;
    private String employeeId;
    private String fullName;
    private AppRole role;
    private String roleName;
    private String branchId;
    private String branchName;
    private String email;
    private String phone;

    public AppUserSession(String userId, String username, String employeeId, String fullName,
                          AppRole role, String roleName, String branchId, String branchName,
                          String email, String phone) {
        this.userId = userId;
        this.username = username;
        this.employeeId = employeeId;
        this.fullName = fullName;
        this.role = role;
        this.roleName = roleName;
        this.branchId = branchId;
        this.branchName = branchName;
        this.email = email;
        this.phone = phone;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getFullName() { return fullName == null || fullName.isBlank() ? username : fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public AppRole getRole() { return role; }
    public void setRole(AppRole role) { this.role = role; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public String getBranchId() { return branchId; }
    public void setBranchId(String branchId) { this.branchId = branchId; }
    public String getBranchName() { return branchName == null || branchName.isBlank() ? "Toàn hệ thống" : branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAvatarText() {
        String name = getFullName();
        if (name == null || name.isBlank()) {
            return "?";
        }
        return name.trim().substring(0, 1).toUpperCase();
    }
}
