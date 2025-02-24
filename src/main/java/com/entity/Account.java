package com.entity;

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

    // Default constructor
    public Account() {}

	public Account(String phone, String password, String token, String permission, String status) {
		super();
		this.phone = phone;
		this.password = password;
		this.token = token;
		this.permission = permission;
		this.status = status;
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

	@Override
	public String toString() {
		return "Account [phone=" + phone + ", password=" + password + ", token=" + token + ", permission=" + permission
				+ ", status=" + status + "]";
	}

    
   }
