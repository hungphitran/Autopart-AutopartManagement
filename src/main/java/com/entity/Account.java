package com.entity;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "Account")
public class Account {
	@Transient // Not stored in the database, just for convenience
    private String roleName;
	
    @Id
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;

    @Column(name = "token")
    private String token;

    @Column(name ="permission")
    private String permission;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission", insertable = false, updatable = false, referencedColumnName = "roleGroupId")
    private RoleGroup roleGroup;
    
    @Column(name="status")
    private String status;
    
    @Column(name = "createdAt")
    private Timestamp createdAt;
    
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    
    @Column(name="deleted")
    private boolean deleted;

    // Default constructor
    public Account() {}

	public Account(String email, String password, String token, String permission, String status, Timestamp createdAt,
			Timestamp updatedAt, boolean deleted) {
		super();
		this.email = email;
		this.password = password;
		this.token = token;
		this.permission = permission;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleted = deleted;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public RoleGroup getRoleGroup() {
		return roleGroup;
	}

	public void setRoleGroup(RoleGroup roleGroup) {
		this.roleGroup = roleGroup;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Account [email=" + email + ", password=" + password + ", token=" + token + ", permission=" + permission
				+ ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deleted="
				+ deleted + "]";
	}

	
    
   }
