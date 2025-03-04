package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
    private java.sql.Date orderDate;

    @Column(name ="confirmedBy")
    private String confirmedBy;
    
    @Column(name = "status")
    private String status;

    @Column(name = "deletedAt")
    private Timestamp  deletedAt;
    
    @OneToMany(mappedBy = "orderId", fetch = FetchType.EAGER)
    private List<OrderDetail> orderDetails; // Thêm danh sách chi tiết

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(String orderId, String discountId, String userPhone, String shipAddress, BigDecimal totalCost,
			Date orderDate, String confirmedBy, String status, Timestamp deletedAt) {
		super();
		this.orderId = orderId;
		this.discountId = discountId;
		this.userPhone = userPhone;
		this.shipAddress = shipAddress;
		this.totalCost = totalCost;
		this.orderDate = orderDate;
		this.confirmedBy = confirmedBy;
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

	public java.sql.Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(java.sql.Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getConfirmedBy() {
		return confirmedBy;
	}

	public void setConfirmedBy(String confirmedBy) {
		this.confirmedBy = confirmedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}
	
	public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", discountId=" + discountId + ", userPhone=" + userPhone
				+ ", shipAddress=" + shipAddress + ", totalCost=" + totalCost + ", orderDate=" + orderDate
				+ ", confirmedBy=" + confirmedBy + ", status=" + status + ", deletedAt=" + deletedAt + "]";
	}

	    
    
    
}