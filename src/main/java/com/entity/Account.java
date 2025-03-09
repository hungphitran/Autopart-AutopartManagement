package com.entity;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "Account")
public class Account {

    @Id
    @Column(name = "phone")
    private String phone;
    
    @Column(name = "password")
    private String password;

    @Column(name = "token")
    private String token;

    @Column(name ="permission")
    private String permission;
    
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

	public Account(String phone, String password, String token, String permission, String status, Timestamp createdAt,
			Timestamp updatedAt, boolean deleted) {
		super();
		this.phone = phone;
		this.password = password;
		this.token = token;
		this.permission = permission;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleted = deleted;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
		return "Account [phone=" + phone + ", password=" + password + ", token=" + token + ", permission=" + permission
				+ ", status=" + status + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deleted="
				+ deleted + "]";
	}

	
    
   }
