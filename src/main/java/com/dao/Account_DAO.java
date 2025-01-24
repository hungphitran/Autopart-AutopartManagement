package com.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import com.entity.Account;

public class Account_DAO {
    private final Connection connection;

    // Constructor to initialize the connection
    public Account_DAO() throws SQLException, ClassNotFoundException {
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    	connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=ProductDB;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2;", "sa", "10802");
    }

    // Retrieve all active accounts
    public ArrayList<Account> getAll() {
        String query = "SELECT * FROM Account WHERE Status = 'Active'";
        ArrayList<Account> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                String token = rs.getString("Token");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address"); // Retrieve address
                String avatar = rs.getString("Avatar");
                String cartId = rs.getString("CartId");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                Account temp = new Account(fullName, email, password, token, phone, address, avatar, cartId, status, deletedAt);
                list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Retrieve an account by email
    public Account getByEmail(String email) {
        String query = "SELECT * FROM Account WHERE Email = ?";
        Account temp = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String fullName = rs.getString("FullName");
                String password = rs.getString("Password");
                String token = rs.getString("Token");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address"); // Retrieve address
                String avatar = rs.getString("Avatar");
                String cartId = rs.getString("CartId");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                temp = new Account(fullName, email, password, token, phone, address, avatar, cartId, status, deletedAt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temp;
    }

    // Add a new account
    public boolean add(Account account) {
        boolean result = false;
        String query = "INSERT INTO Account (FullName, Email, Password, Token, Phone, Address, Avatar, CartId, Status, DeletedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, account.getFullName());
            stmt.setString(2, account.getEmail());
            stmt.setString(3, account.getPassword());
            stmt.setString(4, account.getToken());
            stmt.setString(5, account.getPhone());
            stmt.setString(6, account.getAddress()); // Set address
            stmt.setString(7, account.getAvatar());
            stmt.setString(8, account.getCartId());
            stmt.setString(9, account.getStatus());
            stmt.setTimestamp(10, account.getDeletedAt() != null ? Timestamp.valueOf(account.getDeletedAt()) : null);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Update an existing account
    public boolean update(Account account) {
        boolean result = false;
        String query = "UPDATE Account SET FullName = ?, Password = ?, Token = ?, Phone = ?, Address = ?, Avatar = ?, CartId = ?, Status = ?, DeletedAt = ? WHERE Email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, account.getFullName());
            stmt.setString(2, account.getPassword());
            stmt.setString(3, account.getToken());
            stmt.setString(4, account.getPhone());
            stmt.setString(5, account.getAddress()); // Update address
            stmt.setString(6, account.getAvatar());
            stmt.setString(7, account.getCartId());
            stmt.setString(8, account.getStatus());
            stmt.setTimestamp(9, account.getDeletedAt() != null ? Timestamp.valueOf(account.getDeletedAt()) : null);
            stmt.setString(10, account.getEmail());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Soft delete an account
    public boolean delete(String email) {
        boolean result = false;
        String query = "UPDATE Account SET Status = 'Deleted', DeletedAt = GETDATE() WHERE Email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Check if an account exists by email
    public boolean checkExistByEmail(String email) {
        String query = "SELECT * FROM Account WHERE Email = ?";
        boolean result = false;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
