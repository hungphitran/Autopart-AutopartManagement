package com.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

@Entity
@Table(name = "RoleGroup") // Tên bảng trong database
public class RoleGroup {
    @Id
    @Column(name = "roleGroupId") // Cột roleGroupId
    private String roleGroupId;

    @Column(name = "roleGroupName") // Cột roleGroupName
    private String roleGroupName;

    @Column(name = "description")
    private String description;
    
    @ElementCollection
    @CollectionTable(name = "RoleGroupPermissions", 
                     joinColumns = @JoinColumn(name = "roleGroupId"))
    @MapKeyColumn(name = "permissionName") 
    private List<String> permissions = new ArrayList<>();
    
    @Column(name = "status", length = 50, columnDefinition = "NVARCHAR(50) DEFAULT 'Active'") // Cột status với giá trị mặc định
    private String status;

    @Column(name = "deletedAt") // Cột deletedAt
    private LocalDateTime deletedAt;

    // Default constructor
    public RoleGroup() {}

	public RoleGroup(String roleGroupId, String roleGroupName, String description, String status,
			LocalDateTime deletedAt) {
		super();
		this.roleGroupId = roleGroupId;
		this.roleGroupName = roleGroupName;
		this.description = description;
		this.status = status;
		this.deletedAt = deletedAt;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
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

	@Override
	public String toString() {
		return "RoleGroup [roleGroupId=" + roleGroupId + ", roleGroupName=" + roleGroupName + ", description="
				+ description + ", permissions=" + permissions + ", status=" + status + ", deletedAt=" + deletedAt
				+ "]";
	}

    
}
