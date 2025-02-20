package com.entity;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "BlogGroup")
public class BlogGroup {

    @Id
    @Column(name ="blogGroupId")
    private String blogGroupId;
    
    @Column(name = "groupName", nullable = false, length = 50)
    private String groupName; // Primary key: Group name

    @Column(name = "status", length = 50, nullable = false)
    private String status = "Active"; // Status of the group (default: 'Active')

    @Column(name = "deletedAt")
    private Timestamp  deletedAt; // Timestamp for when the group was deleted

    // Default constructor
    public BlogGroup() {}

	public BlogGroup(String blogGroupId, String groupName, String status,
			Timestamp  deletedAt) {
		super();
		this.blogGroupId = blogGroupId;
		this.groupName = groupName;
		this.status = status;
		this.deletedAt = deletedAt;
	}

	public String getBlogGroupId() {
		return blogGroupId;
	}

	public void setBlogGroupId(String blogGroupId) {
		this.blogGroupId = blogGroupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public Timestamp  getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp  deletedAt) {
		this.deletedAt = deletedAt;
	}

	@Override
	public String toString() {
		return "BlogGroup [blogGroupId=" + blogGroupId + ", groupName=" + groupName 
				+ ", status=" + status +  ", deletedAt=" + deletedAt + "]";
	}

   
}
