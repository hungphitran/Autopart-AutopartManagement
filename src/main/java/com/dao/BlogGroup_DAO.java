package com.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.entity.BlogGroup;

public class BlogGroup_DAO {
    private final Connection connection;

    // Constructor to initialize the connection
    public BlogGroup_DAO() throws ClassNotFoundException, SQLException {
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    	connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=ProductDB;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2;", "sa", "10802");
    }

    public ArrayList<BlogGroup> getAll() {
        String query = "SELECT * FROM BlogGroup WHERE Status = 'Active'";
        ArrayList<BlogGroup> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String groupName = rs.getString("GroupName");
                String parentGroup = rs.getString("ParentGroup");
                String status = rs.getString("Status");
                String createdBy = rs.getString("CreatedBy");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                BlogGroup temp = new BlogGroup(groupName, parentGroup, status, createdBy, deletedAt);
                list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public BlogGroup getByGroupName(String groupName) {
        String query = "SELECT * FROM BlogGroup WHERE GroupName = ?";
        BlogGroup temp = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, groupName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("GroupName");
                String parentGroup = rs.getString("ParentGroup");
                String status = rs.getString("Status");
                String createdBy = rs.getString("CreatedBy");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                temp = new BlogGroup(name, parentGroup, status, createdBy, deletedAt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public boolean add(BlogGroup blogGroup) {
        boolean result = false;
        String query = "INSERT INTO BlogGroup (GroupName, ParentGroup, Status, CreatedBy, DeletedAt) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, blogGroup.getGroupName());
            stmt.setString(2, blogGroup.getParentGroup());
            stmt.setString(3, blogGroup.getStatus());
            stmt.setString(4, blogGroup.getCreatedBy());
            stmt.setTimestamp(5, blogGroup.getDeletedAt() != null ? Timestamp.valueOf(blogGroup.getDeletedAt()) : null);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean update(BlogGroup blogGroup) {
        boolean result = false;
        String query = "UPDATE BlogGroup SET ParentGroup = ?, Status = ?, CreatedBy = ?, DeletedAt = ? WHERE GroupName = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, blogGroup.getParentGroup());
            stmt.setString(2, blogGroup.getStatus());
            stmt.setString(3, blogGroup.getCreatedBy());
            stmt.setTimestamp(4, blogGroup.getDeletedAt() != null ? Timestamp.valueOf(blogGroup.getDeletedAt()) : null);
            stmt.setString(5, blogGroup.getGroupName());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean delete(String groupName) {
        boolean result = false;
        String query = "UPDATE BlogGroup SET Status = 'Deleted', deletedAt = GETDATE() WHERE GroupName = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, groupName);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean checkExistByGroupName(String groupName) {
        String query = "SELECT * FROM BlogGroup WHERE GroupName = ?";
        boolean result = false;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, groupName);
            ResultSet rs = stmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}

