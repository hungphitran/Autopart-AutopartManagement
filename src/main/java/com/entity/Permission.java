package com.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "Permission") // Tên bảng trong database
public class Permission {
    @Id
    @Column(name = "permissionId", length = 50) // Cột permissionId
    private String permissionId;

    @Column(name = "permissionName", nullable = false, length = 255) // Cột permissionName
    private String permissionName;

    @Column(name = "description", length = 255) // Cột description
    private String description;

    @Column(name = "status", length = 50, columnDefinition = "NVARCHAR(50) DEFAULT 'Active'") // Cột status với giá trị mặc định
    private String status;

    @Column(name = "deletedAt") // Cột deletedAt
    private LocalDateTime deletedAt;

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
