package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "[Order]") // Nếu "Order" là từ khóa SQL, đặt trong []
public class Order {

    @Id
    @Column(name = "orderId")
    private String orderId;

    @Column(name = "discountId")
    private String discountId;

    @Column(name = "userPhone")
    private String userPhone;

    @Column(name = "shipAddress")
    private String shipAddress;

    @Column(name = "totalCost")
    private BigDecimal totalCost;

    @Column(name = "orderDate")
    private LocalDate orderDate;

    @Column(name = "status")
    private String status;

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt;

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(String orderId, String discountId, String userPhone, String shipAddress, BigDecimal totalCost,
			LocalDate orderDate, String status, LocalDateTime deletedAt) {
		super();
		this.orderId = orderId;
		this.discountId = discountId;
		this.userPhone = userPhone;
		this.shipAddress = shipAddress;
		this.totalCost = totalCost;
		this.orderDate = orderDate;
		this.status = status;
		this.deletedAt = deletedAt;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDiscountId() {
		return discountId;
	}

	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getShipAddress() {
		return shipAddress;
	}

	public void setShipAddress(String shipAddress) {
		this.shipAddress = shipAddress;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
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
		return "Order [orderId=" + orderId + ", discountId=" + discountId + ", userPhone=" + userPhone
				+ ", shipAddress=" + shipAddress + ", totalCost=" + totalCost + ", orderDate=" + orderDate + ", status="
				+ status + ", deletedAt=" + deletedAt + "]";
	}
    
    
    
}