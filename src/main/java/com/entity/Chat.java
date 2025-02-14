package com.entity;

import javax.persistence.*;
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

    @Column(name = "userEmail", nullable = false, length = 255)
    private String userEmail; // User email

    @Column(name = "content", nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String content; // Message content

    @ElementCollection
    @CollectionTable(name = "ChatRoomImages", 
    joinColumns = @JoinColumn(name = "chatRoomId"))
    @MapKeyColumn(name = "imageId") 
    @Column(name = "images")
    private Map<String, String> images = new HashMap<>(); // List of image URLs

    @Column(name = "status", length = 50, nullable = false)
    private String status = "Active"; // Status of the chat (default: 'Active')

    @Column(name = "deletedAt")
    private LocalDateTime deletedAt; // Timestamp for when the chat was deleted

    // Default constructor
    public Chat() {}

    // Parameterized constructor
    public Chat(String userEmail, String chatRoomId, String content, 
                String status, LocalDateTime deletedAt) {
        this.userEmail = userEmail;
        this.chatRoomId = chatRoomId;
        this.content = content;
        this.status = status;
        this.deletedAt = deletedAt;
    }

    // Getters and Setters
    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getImages() {
        return images;
    }

    public void setImages(Map<String, String> images) {
        this.images = images;
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
        return "Chat{" +
                "userEmail='" + userEmail + '\'' +
                ", chatRoomId='" + chatRoomId + '\'' +
                ", content='" + content + '\'' +
                ", images=" + images +
                ", status='" + status + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
