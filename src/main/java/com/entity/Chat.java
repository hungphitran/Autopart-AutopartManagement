package com.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Chat {
    private String userEmail;    // User email
    private String chatRoomId;   // Primary key: Chat room ID
    private String content;      // Message content
    private ArrayList<String> images; // List of image URLs
    private String status;       // Status of the chat (default: 'Active')
    private LocalDateTime deletedAt;  // Timestamp for when the chat was deleted

    // Default constructor
    public Chat() {
        this.images = new ArrayList<>(); // Initialize images as an empty list
    }

    // Parameterized constructor
    public Chat(String userEmail, String chatRoomId, String content, ArrayList<String> images, 
                String status, LocalDateTime deletedAt) {
        this.userEmail = userEmail;
        this.chatRoomId = chatRoomId;
        this.content = content;
        this.images = images;
        this.status = status;
        this.deletedAt = deletedAt;
    }

    // Getters and Setters
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
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
