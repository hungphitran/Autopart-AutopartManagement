package com.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Define inheritance strategy
@Table(name = "Account")
public class Account {
    @Column(name = "fullName")
    private String fullName;

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "token")
    private String token;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "cartId")
    private String cartId;

    @Column(name = "status")
    private String status;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

    // Default constructor
    public Account() {}

    // Parameterized constructor
    public Account(String fullName, String email, String password, String token, String phone,
                   String address, String avatar, String cartId, String status, LocalDateTime deletedAt) {
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

    // Getters and setters
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
                ", deletedAt=" + deletedAt +
                '}';
    }
}
