package com.entity;

import java.time.LocalDateTime;

public class RoleGroup {
    private String roleGroupId;        // ID nhóm quyền
    private String roleGroupName;      // Tên nhóm quyền
    private String status;             // Trạng thái (mặc định: 'Active')
    private LocalDateTime deletedAt;   // Thời điểm bị xóa

    // Default constructor
    public RoleGroup() {}

    // Parameterized constructor
    public RoleGroup(String roleGroupId, String roleGroupName, String status, LocalDateTime deletedAt) {
        this.roleGroupId = roleGroupId;
        this.roleGroupName = roleGroupName;
        this.status = status;
        this.deletedAt = deletedAt;
    }

    // Getters and setters
    public String getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(String roleGroupId) {
        this.roleGroupId = roleGroupId;
    }

    public String getRoleGroupName() {
        return roleGroupName;
    }

    public void setRoleGroupName(String roleGroupName) {
        this.roleGroupName = roleGroupName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    // Override toString for easy debugging
    @Override
    public String toString() {
        return "RoleGroup{" +
                "roleGroupId='" + roleGroupId + '\'' +
                ", roleGroupName='" + roleGroupName + '\'' +
                ", status='" + status + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
