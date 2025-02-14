package com.entity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "Cart")
public class Cart {

    @Id
    @Column(name = "cartId", length = 50)
    private String cartId;

    @Column(name = "status", length = 50, columnDefinition = "NVARCHAR(50) DEFAULT 'Active'")
    private String status;

    @ElementCollection
    @CollectionTable(name = "ProductsInCart", 
                     joinColumns = @JoinColumn(name = "cartId"))
    @MapKeyColumn(name = "productId") 
    @Column(name = "amount")
    private Map<String, Integer> products = new HashMap<>();

    // Constructors
    public Cart() {}

    public Cart(String cartId, String status) {
        this.cartId = cartId;
        this.status = status;
    }

    // Getters and Setters
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
                ", status='" + status + '\'' +
                ", products=" + products +
                '}';
    }
}

