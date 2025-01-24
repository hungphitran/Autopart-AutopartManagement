package com.entity;

import java.time.LocalDateTime;

public class Account {
    private String fullName;     // Full name of the account holder
    private String email;        // Email (Primary Key)
    private String password;     // Password
    private String token;        // Token for authentication
    private String phone;        // Phone number
    private String address;
    private String avatar;       // Avatar URL
    private String cartId;       // Cart ID (foreign key)
    private String status;       // Status (default: 'Active')
    private LocalDateTime deletedAt;    // Deletion timestamp

    // Default constructor
    public Account() {}

    // Parameterized constructor
    public Account(String fullName, String email, String password, String token, String phone, String address,
			String avatar, String cartId, String status, LocalDateTime deletedAt) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.token = token;
		this.phone = phone;
		this.address = address;
		this.avatar = avatar;
		this.cartId = cartId;
		this.status = status;
		this.deletedAt = deletedAt;
	}

    // Getters and Setters
    public String getFullName() {
        return fullName;
    }

	public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
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

    // Override toString for debugging
    @Override
    public String toString() {
        return "Account{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                 ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", cartId='" + cartId + '\'' +
                ", status='" + status + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}

