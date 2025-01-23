package com.entity;

import java.time.LocalDateTime;

public class BlogGroup {
    private String groupName;       // Primary key: Group name
    private String parentGroup;     // Foreign key: Parent group name
    private String status;          // Status of the group (default: 'Active')
    private String createdBy;       // Foreign key: Creator's email (from Employees table)
    private LocalDateTime deletedAt;       // Timestamp for when the group was deleted

    // Default constructor
    public BlogGroup() {}

    // Parameterized constructor
    public BlogGroup(String groupName, String parentGroup, 
                     String status, String createdBy, LocalDateTime deletedAt) {
        this.groupName = groupName;
        this.parentGroup = parentGroup;
        this.status = status;
        this.createdBy = createdBy;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
        return "BlogGroup{" +
                "groupName='" + groupName + '\'' +
                ", parentGroup='" + parentGroup + '\'' +
                ", status='" + status + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}

