package com.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.entity.ProductGroup;

public class ProductGroup_DAO {
    private final Connection connection;

    // Constructor to initialize the connection
    public ProductGroup_DAO() throws ClassNotFoundException, SQLException {
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    	connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=ProductDB;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2;", "sa", "10802");
    }

    public ArrayList<ProductGroup> getAll() {
        String query = "SELECT * FROM ProductGroup WHERE Status = 'Active'";
        ArrayList<ProductGroup> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String groupName = rs.getString("GroupName");
                String parentGroup = rs.getString("ParentGroup");
                String status = rs.getString("Status");
                String createdBy = rs.getString("CreatedBy");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                ProductGroup temp = new ProductGroup(groupName, parentGroup, status, createdBy, deletedAt);
                list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ProductGroup getByGroupName(String groupName) {
        String query = "SELECT * FROM ProductGroup WHERE GroupName = ?";
        ProductGroup temp = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, groupName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("GroupName");
                String parentGroup = rs.getString("ParentGroup");
                String status = rs.getString("Status");
                String createdBy = rs.getString("CreatedBy");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                temp = new ProductGroup(name, parentGroup, status, createdBy, deletedAt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public boolean add(ProductGroup productGroup) {
        boolean result = false;
        String query = "INSERT INTO ProductGroup (GroupName, ParentGroup, Status, CreatedBy, DeletedAt) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, productGroup.getGroupName());
            stmt.setString(2, productGroup.getParentGroup());
            stmt.setString(3, productGroup.getStatus());
            stmt.setString(4, productGroup.getCreatedBy());
            stmt.setTimestamp(5, productGroup.getDeletedAt() != null ? Timestamp.valueOf(productGroup.getDeletedAt()) : null);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean update(ProductGroup productGroup) {
        boolean result = false;
        String query = "UPDATE ProductGroup SET ParentGroup = ?, Status = ?, CreatedBy = ?, DeletedAt = ? WHERE GroupName = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, productGroup.getParentGroup());
            stmt.setString(2, productGroup.getStatus());
            stmt.setString(3, productGroup.getCreatedBy());
            stmt.setTimestamp(4, productGroup.getDeletedAt() != null ? Timestamp.valueOf(productGroup.getDeletedAt()) : null);
            stmt.setString(5, productGroup.getGroupName());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean delete(String groupName) {
        boolean result = false;
        String query = "UPDATE ProductGroup SET Status = 'Deleted', DeletedAt = GETDATE() WHERE GroupName = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, groupName);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean checkExistByGroupName(String groupName) {
        String query = "SELECT 1 FROM ProductGroup WHERE GroupName = ?";
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
