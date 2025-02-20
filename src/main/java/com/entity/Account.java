package com.entity;

import java.time.LocalDateTime;
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

    // Default constructor
    public Account() {}

	public Account(String phone, String password, String token, String permission) {
		super();
		this.phone = phone;
		this.password = password;
		this.token = token;
		this.permission = permission;
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

	@Override
	public String toString() {
		return "Account [phone=" + phone + ", password=" + password + ", token=" + token + ", permission=" + permission
				+ "]";
	}

    
   }
