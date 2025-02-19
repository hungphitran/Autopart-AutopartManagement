package com.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Blog")
public class Blog {

    @Id
    @Column(name = "blogId", nullable = false )
    private String blogId; // Primary key: Blog ID
 
    @Column(name = "blogGroupId")
    private String blogGroupId; // Foreign key: Group name (references BlogGroup)

    @Column(name = "title", nullable = false )
    private String title; // Title of the blog

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description; // Description of the blog

    @Column(name = "status", nullable = false)
    private String status = "Active"; // Status of the blog (default: 'Active')

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt; // Timestamp for when the blog was deleted

    // Default constructor
    public Blog() {}

    // Parameterized constructor
    public Blog(String blogId, String blogGroupId, String title, 
                String description, String status, LocalDateTime deletedAt) {
        this.blogId = blogId;
        this.blogGroupId = blogGroupId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.deletedAt = deletedAt;
    }

    // Getters and Setters
    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getBlogGroupId() {
        return blogGroupId;
    }

    public void setBlogGroupId(String blogGroupId) {
        this.blogGroupId = blogGroupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "Blog{" +
                "blogId='" + blogId + '\'' +
                ", blogGroupId=" + blogGroupId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
