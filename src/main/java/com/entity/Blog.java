package com.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "Blog")
public class Blog {
    @Transient // Not stored in the database, just for convenience
    private String blogGroupName;
    
    @Id
    @Column(name = "blogId", nullable = false)
    private String blogId; // Primary key: Blog ID

    // Store the foreign key directly as a column
    @Column(name = "blogGroupId", nullable = true) 
    private String blogGroupId;

    // Maintain the relationship with BlogGroup for object navigation
    @ManyToOne(fetch = FetchType.EAGER) // EAGER fetch for immediate loading, or LAZY if you prefer
    @JoinColumn(name = "blogGroupId", insertable = false, updatable = false, referencedColumnName = "blogGroupId")
    private BlogGroup blogGroup; // Relationship with BlogGroup

    @Column(name = "title", nullable = false)
    private String title; // Title of the blog

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String description; // Description of the blog

    @Column(name = "status", nullable = false)
    private String status; 
    
    @Column(name="content")
    private String content;
    
    @Column(name="createdBy")
    private String createdBy;
 
    @Column(name = "createdAt")
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());
    
    @Column(name = "updatedAt")
    private Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());
    
    @Column(name="deleted", columnDefinition = "BIT DEFAULT 'FALSE'")
    private boolean deleted;

    @Column(name = "deletedAt")
    private Timestamp deletedAt; // Timestamp for when the blog was deleted
    

    // Default constructor
    public Blog() {}


	public Blog(String blogGroupName, String blogId, String blogGroupId, BlogGroup blogGroup, String title,
			String description, String status, String content, String createdBy, Timestamp createdAt,
			Timestamp updatedAt, boolean deleted, Timestamp deletedAt) {
		super();
		this.blogGroupName = blogGroupName;
		this.blogId = blogId;
		this.blogGroupId = blogGroupId;
		this.title = title;
		this.description = description;
		this.status = status;
		this.content = content;
		this.createdBy = createdBy;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleted = deleted;
		this.deletedAt = deletedAt;
	}


	public String getBlogGroupName() {
		return blogGroupName;
	}


	public void setBlogGroupName(String blogGroupName) {
		this.blogGroupName = blogGroupName;
	}


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


	public BlogGroup getBlogGroup() {
		return blogGroup;
	}


	public void setBlogGroup(BlogGroup blogGroup) {
		this.blogGroup = blogGroup;
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


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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


	public Timestamp getDeletedAt() {
		return deletedAt;
	}


	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}


	@Override
	public String toString() {
		return "Blog [blogGroupName=" + blogGroupName + ", blogId=" + blogId + ", blogGroupId=" + blogGroupId
				+ ", blogGroup=" + blogGroup + ", title=" + title + ", description=" + description + ", status="
				+ status + ", content=" + content + ", createdBy=" + createdBy + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", deleted=" + deleted + ", deletedAt=" + deletedAt + "]";
	}

	

	
	

    
}