package com.entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class Customer 
{
	@Id
    @Column(name = "cartId")
    private String cartId; 

    @Column(name = "fullName")
    private String fullName;

    @Id
    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;
    
    

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Customer(String cartId, String fullName, String phone, String address, String status) {
		super();
		this.cartId = cartId;
		this.fullName = fullName;
		this.phone = phone;
		this.address = address;
		this.status = status;
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

	@Override
	public String toString() {
		return "Customer [cartId=" + cartId + ", fullName=" + fullName + ", phone=" + phone + ", address=" + address
				+ ", status=" + status + "]";
	}
    
	
    
}
