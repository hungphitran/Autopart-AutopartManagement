package com.entity;

import java.util.HashMap;

public class Cart {
    private String cartId;                 // ID of the cart
    private HashMap<String, Integer> products; // HashMap of ProductId and Amount
    private String status;                // Status of the cart (e.g., 'Active', 'Inactive')

    // Default constructor
    public Cart() {
        this.products = new HashMap<>();
        this.status = "Active"; // Default status
    }

    // Parameterized constructor
    public Cart(String cartId) {
        this.cartId = cartId;
        this.products = new HashMap<>();
        this.status = "Active"; // Default status
    }

    // Parameterized constructor with status
    public Cart(String cartId, String status) {
        this.cartId = cartId;
        this.products = new HashMap<>();
        this.status = status;
    }

    // Getters and Setters
    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Integer> products) {
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
