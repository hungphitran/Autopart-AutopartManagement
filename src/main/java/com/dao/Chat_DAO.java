package com.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.entity.Chat;

public class Chat_DAO {
    private final Connection connection;

    // Constructor to initialize the database connection
    public Chat_DAO() throws ClassNotFoundException, SQLException {
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    	connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=ProductDB;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2;", "sa", "10802");
    }

    // Retrieve all active chats
    public ArrayList<Chat> getAll() {
        String query = "SELECT * FROM Chat WHERE Status = 'Active'";
        ArrayList<Chat> chats = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String userEmail = rs.getString("UserEmail");
                String chatRoomId = rs.getString("ChatRoomId");
                String content = rs.getString("Content");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                ArrayList<String> images = getImagesByChatRoomId(chatRoomId);
                Chat chat = new Chat(userEmail, chatRoomId, content, images, status, deletedAt);
                chats.add(chat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chats;
    }

    // Retrieve chat by chatRoomId
    public Chat getByChatRoomId(String chatRoomId) {
        String query = "SELECT * FROM Chat WHERE ChatRoomId = ?";
        Chat chat = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, chatRoomId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String userEmail = rs.getString("UserEmail");
                String content = rs.getString("Content");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                ArrayList<String> images = getImagesByChatRoomId(chatRoomId);
                chat = new Chat(userEmail, chatRoomId, content, images, status, deletedAt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chat;
    }

    // Add a new chat
    public boolean add(Chat chat) {
        boolean result = false;
        String query = "INSERT INTO Chat (UserEmail, ChatRoomId, Content, Status, DeletedAt) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, chat.getUserEmail());
            stmt.setString(2, chat.getChatRoomId());
            stmt.setString(3, chat.getContent());
            stmt.setString(4, chat.getStatus());
            stmt.setTimestamp(5, chat.getDeletedAt() != null ? Timestamp.valueOf(chat.getDeletedAt()) : null);

            result = stmt.executeUpdate() >= 1;

            if (result) {
                saveImages(chat.getChatRoomId(), chat.getImages());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Update an existing chat
    public boolean update(Chat chat) {
        boolean result = false;
        String query = "UPDATE Chat SET UserEmail = ?, Content = ?, Status = ?, DeletedAt = ? WHERE ChatRoomId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, chat.getUserEmail());
            stmt.setString(2, chat.getContent());
            stmt.setString(3, chat.getStatus());
            stmt.setTimestamp(4, chat.getDeletedAt() != null ? Timestamp.valueOf(chat.getDeletedAt()) : null);
            stmt.setString(5, chat.getChatRoomId());

            result = stmt.executeUpdate() >= 1;

            if (result) {
                saveImages(chat.getChatRoomId(), chat.getImages());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Delete a chat (soft delete)
    public boolean delete(String chatRoomId) {
        boolean result = false;
        String query = "UPDATE Chat SET Status = 'Deleted', DeletedAt = GETDATE() WHERE ChatRoomId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, chatRoomId);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Retrieve images associated with a chat room
    private ArrayList<String> getImagesByChatRoomId(String chatRoomId) {
        String query = "SELECT Images FROM ChatRoomImages WHERE ChatRoomId = ?";
        ArrayList<String> images = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, chatRoomId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String[] imageArray = rs.getString("Images").split(";");
                for (String image : imageArray) {
                    images.add(image.trim());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return images;
    }

    // Save images associated with a chat room
    private void saveImages(String chatRoomId, ArrayList<String> images) {
        String query = "MERGE INTO ChatRoomImages AS target " +
                       "USING (VALUES (?, ?)) AS source (ChatRoomId, Images) " +
                       "ON target.ChatRoomId = source.ChatRoomId " +
                       "WHEN MATCHED THEN UPDATE SET Images = source.Images " +
                       "WHEN NOT MATCHED THEN INSERT (ChatRoomId, Images) VALUES (source.ChatRoomId, source.Images);";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            String imagesString = String.join(";", images);
            stmt.setString(1, chatRoomId);
            stmt.setString(2, imagesString);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String generateNextChatId() {
        String query = "SELECT MAX(ChatRoomId) FROM Chat WHERE ChatRoomId LIKE 'CHAT%'";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId == null) {
                    return "CHAT001";
                }

                if (maxId.length() >= 3) {
                    try {
                        int currentNum = Integer.parseInt(maxId.substring(3).trim());
                        return String.format("CHAT%03d", currentNum + 1);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return "CHAT001";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "CHAT000";
    }
}
