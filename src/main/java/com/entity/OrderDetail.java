//package com.entity;
//
//import java.math.BigDecimal;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//
//@Entity
//@Table(name = "OrderDetail")
//public class OrderDetail 
//{
//	@Id
//    @Column(name = "orderId")
//    private String orderId;
//
//    @Id
//    @Column(name = "productId")
//    private String productId;
//
//    @Column(name = "productName")
//    private String productName;
//
//    @Column(name = "amount")
//    private int amount;
//
//    @Column(name = "unitPrice")
//    private BigDecimal unitPrice;
//
//	public OrderDetail() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//
//	public OrderDetail(String orderId, String productId, String productName, int amount, BigDecimal unitPrice) {
//		super();
//		this.orderId = orderId;
//		this.productId = productId;
//		this.productName = productName;
//		this.amount = amount;
//		this.unitPrice = unitPrice;
//	}
//
//	public String getOrderId() {
//		return orderId;
//	}
//
//	public void setOrderId(String orderId) {
//		this.orderId = orderId;
//	}
//
//	public String getProductId() {
//		return productId;
//	}
//
//	public void setProductId(String productId) {
//		this.productId = productId;
//	}
//
//	public String getProductName() {
//		return productName;
//	}
//
//	public void setProductName(String productName) {
//		this.productName = productName;
//	}
//
//	public int getAmount() {
//		return amount;
//	}
//
//	public void setAmount(int amount) {
//		this.amount = amount;
//	}
//
//	public BigDecimal getUnitPrice() {
//		return unitPrice;
//	}
//
//	public void setUnitPrice(BigDecimal unitPrice) {
//		this.unitPrice = unitPrice;
//	}
//
//	@Override
//	public String toString() {
//		return "OrderDetail [orderId=" + orderId + ", productId=" + productId + ", productName=" + productName
//				+ ", amount=" + amount + ", unitPrice=" + unitPrice + "]";
//	}
//
//    
//}
