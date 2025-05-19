package com.entity;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Product")  // Đánh dấu lớp này là thực thể JPA, ánh xạ với bảng "Product"
public class Product implements Comparable<Product>{

    @Id
    @Column(name = "productId")  // Khóa chính
    private String productId; // Primary key: Product ID

    @Column(name = "productName", nullable = false)  // Ánh xạ với cột "productName" trong bảng
    private String productName; // Product name

    @Column(name = "productGroupId")
    private String productGroupId; // Foreign key: Group name (references ProductGroup)

    @Column(name = "brandId")
    private String brandId; // Foreign key: Brand name (references Brand)

    @Column(name = "salePrice")  // Ánh xạ với cột "salePrice" trong bảng
    private double salePrice; // Sale price

    @Column(name = "costPrice")  // Ánh xạ với cột "costPrice" trong bảng
    private double costPrice; // Cost price

    @Column(name = "stock")  // Ánh xạ với cột "stock" trong bảng
    private int stock; // Stock quantity

    @Column(name = "unit")  // Ánh xạ với cột "unit" trong bảng
    private String unit; // Unit of measurement

    @Column(name = "imageUrls")  // Lưu trữ danh sách URL hình ảnh
    private String imageUrls; // List of image URLs

    @Column(name = "weight")  // Ánh xạ với cột "weight" trong bảng
    private double weight; // Weight

    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'Active'")  // Trạng thái mặc định 'Active'
    private String status; // Status (default: 'Active')

    @Column(name = "deletedAt")  // Ánh xạ với cột "deletedAt" trong bảng
    private Timestamp  deletedAt; // Deletion timestamp
    
    @Column(name = "createdAt")
    private Timestamp createdAt;
    
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    
    @Column(name="deleted")
    private boolean deleted;

    @Column(name = "description")  // Ánh xạ với cột "description" trong bảng
    private String description; // Description

    // Default constructor
    public Product() {}

	public Product(String productId, String productName, String productGroupId, String brandId, double salePrice,
			double costPrice, int stock, String unit, String imageUrls, double weight, String status,
			Timestamp deletedAt, Timestamp createdAt, Timestamp updatedAt, boolean deleted, String description) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productGroupId = productGroupId;
		this.brandId = brandId;
		this.salePrice = salePrice;
		this.costPrice = costPrice;
		this.stock = stock;
		this.unit = unit;
		this.imageUrls = imageUrls;
		this.weight = weight;
		this.status = status;
		this.deletedAt = deletedAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleted = deleted;
		this.description = description;
	}

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

	public String getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
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

	public String getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(String imageUrls) {
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

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", productGroupId=" + productGroupId
				+ ", brandId=" + brandId + ", salePrice=" + salePrice + ", costPrice=" + costPrice + ", stock=" + stock
				+ ", unit=" + unit + ", imageUrls=" + imageUrls + ", weight=" + weight + ", status=" + status
				+ ", deletedAt=" + deletedAt + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deleted="
				+ deleted + ", description=" + description + "]";
	}
    @Override
    public int compareTo(Product o) {
        return this.productId.compareTo(o.productId); // Hoặc sắp xếp theo productName, salePrice...
    }

  
}
