package com.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "ProductGroup") // Ánh xạ với bảng ProductGroup trong database
public class ProductGroup {

    @Id
    @Column(name = "groupName", length = 50) // Khóa chính: groupName
    private String groupName;

    @Column(name = "parentGroup") // Khóa ngoại: parentGroup
    private String parentGroup;

    @Column(name = "status", length = 50, columnDefinition = "NVARCHAR(50) DEFAULT 'Active'") // Trạng thái mặc định
    private String status;


    @Column(name = "deletedAt") // Thời điểm bị xóa
    private LocalDateTime deletedAt;

    // Default constructor
    public ProductGroup() {}

    // Parameterized constructor
    public ProductGroup(String groupName, String parentGroup, String status, LocalDateTime deletedAt) {
        this.groupName = groupName;
        this.parentGroup = parentGroup;
        this.status = status;
        this.deletedAt = deletedAt;
    }

    // Getters and Setters
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(String parentGroup) {
        this.parentGroup = parentGroup;
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
        return "ProductGroup{" +
                "groupName='" + groupName + '\'' +
                ", parentGroup=" + parentGroup  +
                ", status='" + status + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
