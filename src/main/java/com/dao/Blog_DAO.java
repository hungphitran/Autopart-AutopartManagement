package com.dao;

import java.sql.*;
import java.util.ArrayList;
import com.entity.Blog;

public class Blog_DAO {
    private final Connection connection;

    // Constructor to initialize the connection
    public Blog_DAO() throws ClassNotFoundException, SQLException {
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    	connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=ProductDB;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2;", "sa", "10802");
    }

    // Retrieve all active blogs
    public ArrayList<Blog> getAll() {
        String query = "SELECT * FROM Blog WHERE Status = 'Active'";
        ArrayList<Blog> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String blogId = rs.getString("BlogId");
                String groupName = rs.getString("GroupName");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String status = rs.getString("Status");
                String deletedAt = rs.getString("DeletedAt");

                Blog temp = new Blog(blogId, groupName, title, description, status, deletedAt);
                list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Retrieve a blog by its ID
    public Blog getById(String blogId) {
        String query = "SELECT * FROM Blog WHERE BlogId = ?";
        Blog temp = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, blogId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String groupName = rs.getString("GroupName");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String status = rs.getString("Status");
                String deletedAt = rs.getString("DeletedAt");

                temp = new Blog(blogId, groupName, title, description, status, deletedAt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temp;
    }

    // Add a new blog
    public boolean add(Blog blog) {
        boolean result = false;
        String query = "INSERT INTO Blog (BlogId, GroupName, Title, Description, Status, DeletedAt) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, blog.getBlogId());
            stmt.setString(2, blog.getGroupName());
            stmt.setString(3, blog.getTitle());
            stmt.setString(4, blog.getDescription());
            stmt.setString(5, blog.getStatus());
            stmt.setString(6, blog.getDeletedAt());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Update an existing blog
    public boolean update(Blog blog) {
        boolean result = false;
        String query = "UPDATE Blog SET GroupName = ?, Title = ?, Description = ?, Status = ?, DeletedAt = ? WHERE BlogId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, blog.getGroupName());
            stmt.setString(2, blog.getTitle());
            stmt.setString(3, blog.getDescription());
            stmt.setString(4, blog.getStatus());
            stmt.setString(5, blog.getDeletedAt());
            stmt.setString(6, blog.getBlogId());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Soft delete a blog
    public boolean delete(String blogId) {
        boolean result = false;
        String query = "UPDATE Blog SET Status = 'Deleted', DeletedAt = GETDATE() WHERE BlogId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, blogId);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Check if a blog exists by its ID
    public boolean checkExistById(String blogId) {
        String query = "SELECT * FROM Blog WHERE BlogId = ?";
        boolean result = false;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, blogId);
            ResultSet rs = stmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Generate the next blog ID
    public String generateNextBlogId() {
        String query = "SELECT MAX(BlogId) FROM Blog WHERE BlogId LIKE 'BLOG%'";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId == null) {
                    return "BLOG001";
                }

                if (maxId.length() >= 4) {
                    try {
                        int currentNum = Integer.parseInt(maxId.substring(4).trim());
                        return String.format("BLOG%03d", currentNum + 1);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return "BLOG001";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "BLOG000";
    }
}
