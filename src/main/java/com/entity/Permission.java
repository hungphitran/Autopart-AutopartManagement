package com.entity;

import java.time.LocalDateTime;

public class Permission {
    private String permissionId;      // ID quyền
    private String permissionName;    // Tên quyền
    private String description;       // Mô tả quyền
    private String status;            // Trạng thái (Active, Inactive, etc.)
    private LocalDateTime deletedAt;  // Thời điểm bị xóa

    // Default constructor
    public Permission() {}

    // Parameterized constructor
    public Permission(String permissionId, String permissionName, String description, String status, LocalDateTime deletedAt) {
        this.permissionId = permissionId;
        this.permissionName = permissionName;
        this.description = description;
        this.status = status;
        this.deletedAt = deletedAt;
    }

    // Getters and setters
    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "Permission{" +
                "permissionId='" + permissionId + '\'' +
                ", permissionName='" + permissionName + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
