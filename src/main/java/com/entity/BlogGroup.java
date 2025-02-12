package com.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "BlogGroup")
public class BlogGroup {

    @Id
    @Column(name = "groupName", nullable = false, length = 50)
    private String groupName; // Primary key: Group name

    @Column(name = "parentGroup")
    private String parentGroup; // Foreign key: Parent group name (self-referencing)

    @Column(name = "status", length = 50, nullable = false)
    private String status = "Active"; // Status of the group (default: 'Active')

    @Column(name = "createdBy")
    private String createdBy; // Foreign key: Creator's email (from Employees table)

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt; // Timestamp for when the group was deleted

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
                ", parentGroup=" + parentGroup +
                ", status='" + status + '\'' +
                ", createdBy=" + createdBy  +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
