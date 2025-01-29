package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "[Order]")
public class Order {

    @Id
    @Column(name = "orderId", nullable = false, length = 50)
    private String orderId; // Primary key: Order ID

    @Column(name = "cartId", nullable = false)
    private String cart; // Foreign key: Cart ID (references Cart)

    @Column(name = "userEmail", nullable = false)
    private String user; // Foreign key: User email (references Accounts)

    @Column(name = "totalCost", precision = 18, scale = 2)
    private BigDecimal totalCost; // Total cost of the order

    @Column(name = "status", length = 50, nullable = false)
    private String status = "Active"; // Status of the order (default: 'Active')

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt; // Timestamp for when the order was deleted

    // Default constructor
    public Order() {}

    // Parameterized constructor
    public Order(String orderId, String cart, String user, 
                 BigDecimal totalCost, String status, LocalDateTime deletedAt) {
        this.orderId = orderId;
        this.cart = cart;
        this.user = user;
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

    public String getCart() {
        return cart;
    }

    public void setCart(String cart) {
        this.cart = cart;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
                ", cart=" + cart +
                ", user=" + user +
                ", totalCost=" + totalCost +
                ", status='" + status + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
