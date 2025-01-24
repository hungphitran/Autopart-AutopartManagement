package com.dao;

import java.sql.*;
import java.util.ArrayList;
import com.entity.Product;
import java.util.List;


public class Product_DAO {
    private final Connection connection;

    // Constructor to initialize the connection
    public Product_DAO() throws ClassNotFoundException, SQLException {
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    	connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=ProductDB;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2;", "sa", "10802");
    }

    public ArrayList<Product> getAll() {
        String query = "SELECT * FROM Product WHERE Status = 'Active'";
        ArrayList<Product> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String productId = rs.getString("ProductId");
                String productName = rs.getString("ProductName");
                String groupName = rs.getString("GroupName");
                String brandName = rs.getString("BrandName");
                double salePrice = rs.getDouble("SalePrice");
                double costPrice = rs.getDouble("CostPrice");
                int stock = rs.getInt("Stock");
                String unit = rs.getString("Unit");
                String imageUrlsStr = rs.getString("ImageUrls");
                ArrayList<String> imageUrls = new ArrayList<>(List.of(imageUrlsStr.split(",")));
                double weight = rs.getDouble("Weight");
                String status = rs.getString("Status");
                String deletedAt = rs.getString("DeletedAt");
                String description = rs.getString("Description");

                Product temp = new Product(productId, productName, groupName, brandName, salePrice, costPrice, stock,
                        unit, imageUrls, weight, status, deletedAt, description);
                list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Product getById(String productId) {
        String query = "SELECT * FROM Product WHERE ProductId = ?";
        Product temp = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String id = rs.getString("ProductId");
                String productName = rs.getString("ProductName");
                String groupName = rs.getString("GroupName");
                String brandName = rs.getString("BrandName");
                double salePrice = rs.getDouble("SalePrice");
                double costPrice = rs.getDouble("CostPrice");
                int stock = rs.getInt("Stock");
                String unit = rs.getString("Unit");
                String imageUrlsStr = rs.getString("ImageUrls");
                ArrayList<String> imageUrls = new ArrayList<>(List.of(imageUrlsStr.split(",")));
                double weight = rs.getDouble("Weight");
                String status = rs.getString("Status");
                String deletedAt = rs.getString("DeletedAt");
                String description = rs.getString("Description");

                temp = new Product(id, productName, groupName, brandName, salePrice, costPrice, stock, unit, imageUrls,
                        weight, status, deletedAt, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public boolean add(Product product) {
        boolean result = false;
        String query = "INSERT INTO Product (ProductId, ProductName, GroupName, BrandName, SalePrice, CostPrice, " +
                "Stock, Unit, ImageUrls, Weight, Status, DeletedAt, Description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getProductId());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getGroupName());
            stmt.setString(4, product.getBrandName());
            stmt.setDouble(5, product.getSalePrice());
            stmt.setDouble(6, product.getCostPrice());
            stmt.setInt(7, product.getStock());
            stmt.setString(8, product.getUnit());
            stmt.setString(9, String.join(",", product.getImageUrls()));
            stmt.setDouble(10, product.getWeight());
            stmt.setString(11, product.getStatus());
            stmt.setString(12, product.getDeletedAt());
            stmt.setString(13, product.getDescription());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean update(Product product) {
        boolean result = false;
        String query = "UPDATE Product SET ProductName = ?, GroupName = ?, BrandName = ?, SalePrice = ?, CostPrice = ?, " +
                "Stock = ?, Unit = ?, ImageUrls = ?, Weight = ?, Status = ?, DeletedAt = ?, Description = ? WHERE ProductId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getGroupName());
            stmt.setString(3, product.getBrandName());
            stmt.setDouble(4, product.getSalePrice());
            stmt.setDouble(5, product.getCostPrice());
            stmt.setInt(6, product.getStock());
            stmt.setString(7, product.getUnit());
            stmt.setString(8, String.join(",", product.getImageUrls()));
            stmt.setDouble(9, product.getWeight());
            stmt.setString(10, product.getStatus());
            stmt.setString(11, product.getDeletedAt());
            stmt.setString(12, product.getDescription());
            stmt.setString(13, product.getProductId());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean delete(String productId) {
        boolean result = false;
        String query = "UPDATE Product SET Status = 'Deleted', DeletedAt = GETDATE() WHERE ProductId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, productId);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean checkExistById(String productId) {
        String query = "SELECT * FROM Product WHERE ProductId = ?";
        boolean result = false;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public String generateNextProductId() {
        String query = "SELECT MAX(ProductId) FROM Product WHERE ProductId LIKE 'PROD%'";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId == null) {
                    return "PROD001";
                }

                if (maxId.length() >= 4) {
                    try {
                        int currentNum = Integer.parseInt(maxId.substring(4).trim());
                        return String.format("PROD%03d", currentNum + 1);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return "PROD001";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "PROD000";
    }

}
