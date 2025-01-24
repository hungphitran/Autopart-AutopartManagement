package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.entity.Order;

public class Order_DAO {
    private final Connection connection;

    // Constructor to initialize the connection
    public Order_DAO() throws ClassNotFoundException, SQLException {
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    	connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=ProductDB;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2;", "sa", "10802");
    }

    // Retrieve all active orders
    public ArrayList<Order> getAll() {
        String query = "SELECT * FROM Order WHERE Status = 'Active'";
        ArrayList<Order> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String orderId = rs.getString("OrderId");
                String cartId = rs.getString("CartId");
                String userEmail = rs.getString("UserEmail");
                BigDecimal totalCost = rs.getBigDecimal("TotalCost");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null 
                    ? rs.getTimestamp("DeletedAt").toLocalDateTime() 
                    : null;

                Order temp = new Order(orderId, cartId, userEmail, totalCost, status, deletedAt);
                list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Retrieve an order by its ID
    public Order getById(String orderId) {
        String query = "SELECT * FROM Order WHERE OrderId = ?";
        Order temp = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String cartId = rs.getString("CartId");
                String userEmail = rs.getString("UserEmail");
                BigDecimal totalCost = rs.getBigDecimal("TotalCost");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null 
                    ? rs.getTimestamp("DeletedAt").toLocalDateTime() 
                    : null;

                temp = new Order(orderId, cartId, userEmail, totalCost, status, deletedAt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temp;
    }

    // Add a new order
    public boolean add(Order order) {
        boolean result = false;
        String query = "INSERT INTO Order (OrderId, CartId, UserEmail, TotalCost, Status, DeletedAt) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, order.getOrderId());
            stmt.setString(2, order.getCartId());
            stmt.setString(3, order.getUserEmail());
            stmt.setBigDecimal(4, order.getTotalCost());
            stmt.setString(5, order.getStatus());
            stmt.setTimestamp(6, order.getDeletedAt() != null 
                ? Timestamp.valueOf(order.getDeletedAt()) 
                : null);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Update an existing order
    public boolean update(Order order) {
        boolean result = false;
        String query = "UPDATE Order SET CartId = ?, UserEmail = ?, TotalCost = ?, Status = ?, DeletedAt = ? WHERE OrderId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, order.getCartId());
            stmt.setString(2, order.getUserEmail());
            stmt.setBigDecimal(3, order.getTotalCost());
            stmt.setString(4, order.getStatus());
            stmt.setTimestamp(5, order.getDeletedAt() != null 
                ? Timestamp.valueOf(order.getDeletedAt()) 
                : null);
            stmt.setString(6, order.getOrderId());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Soft delete an order
    public boolean delete(String orderId) {
        boolean result = false;
        String query = "UPDATE Order SET Status = 'Deleted', DeletedAt = GETDATE() WHERE OrderId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, orderId);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Check if an order exists by its ID
    public boolean checkExistById(String orderId) {
        String query = "SELECT * FROM Order WHERE OrderId = ?";
        boolean result = false;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Generate the next order ID
    public String generateNextOrderId() {
        String query = "SELECT MAX(OrderId) FROM Order WHERE OrderId LIKE 'ORDER%'";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId == null) {
                    return "ORDER001";
                }

                if (maxId.length() >= 5) {
                    try {
                        int currentNum = Integer.parseInt(maxId.substring(5).trim());
                        return String.format("ORDER%03d", currentNum + 1);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return "ORDER001";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "ORDER000";
    }
}