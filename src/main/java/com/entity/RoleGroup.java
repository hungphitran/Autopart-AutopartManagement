package com.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "RoleGroup") // Tên bảng trong database
public class RoleGroup {
    @Id
    @Column(name = "roleGroupId", length = 50) // Cột roleGroupId
    private String roleGroupId;

    @Column(name = "roleGroupName") // Cột roleGroupName
    private String roleGroupName;

    @Column(name = "status", length = 50, columnDefinition = "NVARCHAR(50) DEFAULT 'Active'") // Cột status với giá trị mặc định
    private String status;

    @Column(name = "deletedAt") // Cột deletedAt
    private LocalDateTime deletedAt;

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
