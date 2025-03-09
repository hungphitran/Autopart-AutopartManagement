package com.entity;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "Cart")
public class Cart {

    @Id
    @Column(name = "cartId")
    private String cartId;

    @Column(name = "createDate")
    private Timestamp  createDate;
    
    

    @ElementCollection
    @CollectionTable(name = "ProductsInCart", 
                     joinColumns = @JoinColumn(name = "cartId"))
    @MapKeyColumn(name = "productId") 
    @Column(name = "amount")
    private Map<String, Integer> products = new HashMap<>();

    // Constructors
    public Cart() {}

    public Cart(String cartId, Timestamp createDate) {
        this.cartId = cartId;
        this.createDate = createDate;
    }

    // Getters and Setters
    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    // Override toString for better debugging
    @Override
    public String toString() {
        return "Cart{" +
                "cartId='" + cartId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", products=" + products +
                '}';
    }
}

