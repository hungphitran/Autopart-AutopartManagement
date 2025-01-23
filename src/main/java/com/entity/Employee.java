package com.entity;

import java.time.LocalDateTime;

public class Employee extends Account {
    private String roleGroupId; // Role group ID (foreign key)

    // Default constructor
    public Employee() {
        super();
    }

    // Parameterized constructor
    public Employee(String fullName, String email, String password, String token, String phone,
                    String avatar, String cartId, String status, LocalDateTime deletedAt, String roleGroupId) {
        // Initialize fields from the Account class
        super(fullName, email, password, token, phone, avatar, cartId, status, deletedAt);
        this.roleGroupId = roleGroupId;
    }

    // Getter and Setter for roleGroupId
    public String getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(String roleGroupId) {
        this.roleGroupId = roleGroupId;
    }

    // Override toString for debugging
    @Override
    public String toString() {
        return "Employee{" +
                "roleGroupId='" + roleGroupId + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", token='" + getToken() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", avatar='" + getAvatar() + '\'' +
                ", cartId='" + getCartId() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", deletedAt='" + getDeletedAt() + '\'' +
                '}';
    }
}
