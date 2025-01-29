package com.entity;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "Cart")
public class Cart {

    @Id
    @Column(name = "cartId", nullable = false, length = 50)
    private String cartId; // ID của giỏ hàng

    @ElementCollection
    @CollectionTable(
        name = "ProductsInCart", 
        joinColumns = @JoinColumn(name = "cartId", referencedColumnName = "cartId")
    )
    @MapKeyColumn(name = "productId")
    @Column(name = "amount", nullable = false)
    private Map<String, Integer> products = new HashMap<>(); // Map của ProductId và số lượng

    @Column(name = "status", nullable = false, length = 50)
    private String status = "Active"; // Trạng thái của giỏ hàng (mặc định: 'Active')

    // Default constructor
    public Cart() {}

    // Parameterized constructor
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

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Add a product to the cart
    public void addProduct(String productId, int amount) {
        this.products.put(productId, this.products.getOrDefault(productId, 0) + amount);
    }

    // Remove a product from the cart
    public void removeProduct(String productId) {
        this.products.remove(productId);
    }

    // Update the amount of a product in the cart
    public void updateProductAmount(String productId, int amount) {
        if (this.products.containsKey(productId)) {
            this.products.put(productId, amount);
        } else {
            throw new IllegalArgumentException("Product not found in cart: " + productId);
        }
    }

    // Clear the cart
    public void clearCart() {
        this.products.clear();
    }

    // Override toString for easy debugging
    @Override
    public String toString() {
        return "Cart{" +
                "cartId='" + cartId + '\'' +
                ", products=" + products +
                ", status='" + status + '\'' +
                '}';
    }
}
