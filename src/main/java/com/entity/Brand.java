package com.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "Brand") // Ánh xạ class với bảng Brand trong cơ sở dữ liệu
public class Brand {

    @Id
    @Column(name = "brandName", length = 255) // Khóa chính: Tên thương hiệu
    private String brandName;

    @Column(name = "status", length = 50, columnDefinition = "NVARCHAR(50) DEFAULT 'Active'") // Trạng thái mặc định
    private String status;

    @Column(name = "deletedAt") // Thời điểm bị xóa
    private LocalDateTime deletedAt;

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
                ", deletedAt=" + deletedAt +
                '}';
    }
}
