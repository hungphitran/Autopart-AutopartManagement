package com.entity;

import java.time.LocalDateTime;

public class Brand {
    private String brandName;   // Primary key: Brand name
    private String status;      // Status of the brand (default: 'Active')
    private LocalDateTime deletedAt;   // Timestamp for when the brand was deleted

    // Default constructor
    public Brand() {}

    // Parameterized constructor
    public Brand(String brandName, String status, LocalDateTime deletedAt) {
        this.brandName = brandName;
        this.status = status;
        this.deletedAt = deletedAt;
    }

    // Getters and Setters
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    // Override toString for better representation
    @Override
    public String toString() {
        return "Brand{" +
                "brandName='" + brandName + '\'' +
                ", status='" + status + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}

