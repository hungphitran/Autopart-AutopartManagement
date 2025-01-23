package com.entity;

import java.util.ArrayList;

public class Product {
    private String productId;         // Primary key: Product ID
    private String productName;       // Product name
    private String groupName;         // Foreign key: Group name (references ProductGroup)
    private String brandName;         // Foreign key: Brand name (references Brand)
    private double salePrice;         // Sale price
    private double costPrice;         // Cost price
    private int stock;                // Stock quantity
    private String unit;              // Unit of measurement
    private ArrayList<String> imageUrls; // List of image URLs
    private double weight;            // Weight
    private String status;            // Status (default: 'Active')
    private String deletedAt;         // Deletion timestamp
    private String description;       // Description

    // Default constructor
    public Product() {
        this.imageUrls = new ArrayList<>(); // Initialize the ArrayList
    }

    // Parameterized constructor
    public Product(String productId, String productName, String groupName, String brandName,
                   double salePrice, double costPrice, int stock, String unit,
                   ArrayList<String> imageUrls, double weight, String status,
                   String deletedAt, String description) {
        this.productId = productId;
        this.productName = productName;
        this.groupName = groupName;
        this.brandName = brandName;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.stock = stock;
        this.unit = unit;
        this.imageUrls = imageUrls != null ? imageUrls : new ArrayList<>();
        this.weight = weight;
        this.status = status;
        this.deletedAt = deletedAt;
        this.description = description;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Override toString for better representation
    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", salePrice=" + salePrice +
                ", costPrice=" + costPrice +
                ", stock=" + stock +
                ", unit='" + unit + '\'' +
                ", imageUrls=" + imageUrls +
                ", weight=" + weight +
                ", status='" + status + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
