package com.entity;

public class Blog {
    private String blogId;        // Primary key: Blog ID
    private String groupName;     // Foreign key: Group name (references BlogGroup)
    private String title;         // Title of the blog
    private String description;   // Description of the blog
    private String status;        // Status of the blog (default: 'Active')
    private String deletedAt;     // Timestamp for when the blog was deleted

    // Default constructor
    public Blog() {}

    // Parameterized constructor
    public Blog(String blogId, String groupName, String title, 
                String description, String status, String deletedAt) {
        this.blogId = blogId;
        this.groupName = groupName;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    // Override toString for better representation
    @Override
    public String toString() {
        return "Blog{" +
                "blogId='" + blogId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", deletedAt='" + deletedAt + '\'' +
                '}';
    }
}

