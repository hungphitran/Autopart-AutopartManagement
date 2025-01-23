package com.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private String orderId;          // Primary key: Order ID
    private String cartId;           // Foreign key: Cart ID
    private String userEmail;        // Foreign key: User email
    private BigDecimal totalCost;    // Total cost of the order
    private String status;           // Status of the order (default: 'Active')
    private LocalDateTime deletedAt; // Timestamp for when the order was deleted

    // Default constructor
    public Order() {}

    // Parameterized constructor
    public Order(String orderId, String cartId, String userEmail, 
                 BigDecimal totalCost, String status, LocalDateTime deletedAt) {
        this.orderId = orderId;
        this.cartId = cartId;
        this.userEmail = userEmail;
        this.totalCost = totalCost;
        this.status = status;
        this.deletedAt = deletedAt;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
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

    // Override toString for better representation
    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", cartId='" + cartId + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", totalCost=" + totalCost +
                ", status='" + status + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
