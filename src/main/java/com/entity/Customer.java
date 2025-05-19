package com.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Customer")
public class Customer 
{
    @Column(name = "cartId")
    private String cartId; 

    @Column(name = "fullName")
    private String fullName;

    @Id
    @Column(name = "email")
    private String email;
    
    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;
    
    @Column(name = "createdAt")
    private Timestamp createdAt;
    
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    
    
    

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}




	public Customer(String cartId, String fullName, String email, String phone, String address, String status,
			Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.cartId = cartId;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Customer(String cartId, String fullName, String email, String address, String status,
			Timestamp createdAt, Timestamp updatedAt) {
		super();
		this.cartId = cartId;
		this.fullName = fullName;
		this.email = email;
		this.address = address;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}




	public String getCartId() {
		return cartId;
	}




	public void setCartId(String cartId) {
		this.cartId = cartId;
	}




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




	public String getStatus() {
		return status;
	}




	public void setStatus(String status) {
		this.status = status;
	}




	public Timestamp getCreatedAt() {
		return createdAt;
	}




	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}




	public Timestamp getUpdatedAt() {
		return updatedAt;
	}




	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}




	@Override
	public String toString() {
		return "Customer [cartId=" + cartId + ", fullName=" + fullName + ", email=" + email + ", phone=" + phone
				+ ", address=" + address + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}



	
		
    
}
