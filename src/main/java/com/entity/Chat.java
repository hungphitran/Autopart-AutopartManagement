package com.entity;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Chat")
public class Chat {

    @Id
    @Column(name = "chatRoomId", nullable = false, length = 50)
    private String chatRoomId; // Primary key: Chat room ID

    @Column(name = "userPhone", nullable = false, length = 255)
    private String userPhone; // User email

    @Column(name = "content", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String content; // Message content

    @Column(name = "status", length = 50, nullable = false)
    private String status = "Active"; // Status of the chat (default: 'Active')

    @Column(name = "deletedAt")
    private Timestamp  deletedAt; // Timestamp for when the chat was deleted

    @Column(name = "createdAt")
    private Timestamp createdAt;
    
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    
    @Column(name="deleted")
    private boolean deleted;
    
    // Default constructor
    public Chat() {}

	public Chat(String chatRoomId, String userPhone, String content, String status, Timestamp deletedAt,
			Timestamp createdAt, Timestamp updatedAt, boolean deleted) {
		super();
		this.chatRoomId = chatRoomId;
		this.userPhone = userPhone;
		this.content = content;
		this.status = status;
		this.deletedAt = deletedAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleted = deleted;
	}

	public String getChatRoomId() {
		return chatRoomId;
	}

	public void setChatRoomId(String chatRoomId) {
		this.chatRoomId = chatRoomId;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	@Override
	public String toString() {
		return "Chat [chatRoomId=" + chatRoomId + ", userPhone=" + userPhone + ", content=" + content + ", status="
				+ status + ", deletedAt=" + deletedAt + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ ", deleted=" + deleted + "]";
	}

	
   
}
