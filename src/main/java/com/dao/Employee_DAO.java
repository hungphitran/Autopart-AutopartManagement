package com.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.entity.Employee;

public class Employee_DAO {
    private final Connection connection;

    // Constructor to initialize the connection
    public Employee_DAO() throws ClassNotFoundException, SQLException {
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    	connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=ProductDB;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2;", "sa", "10802");
    }

    // Get all active employees
    public ArrayList<Employee> getAll() {
        String query = "SELECT * FROM Employee e INNER JOIN Accounts a ON e.Email = a.Email WHERE a.Status = 'Active'";
        ArrayList<Employee> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                // Get Account fields
                String fullName = rs.getString("FullName");
                String email = rs.getString("Email");
                String password = rs.getString("Password");
                String token = rs.getString("Token");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                String avatar = rs.getString("Avatar");
                String cartId = rs.getString("CartId");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                // Get Employee-specific field
                String roleGroupId = rs.getString("RoleGroupId");

                Employee emp = new Employee(fullName, email, password, token, phone, address, avatar, cartId, status, deletedAt, roleGroupId);
                list.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get an employee by email
    public Employee getByEmail(String email) {
        String query = "SELECT * FROM Employee e INNER JOIN Accounts a ON e.Email = a.Email WHERE a.Email = ?";
        Employee emp = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Get Account fields
                String fullName = rs.getString("FullName");
                String password = rs.getString("Password");
                String token = rs.getString("Token");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                String avatar = rs.getString("Avatar");
                String cartId = rs.getString("CartId");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                // Get Employee-specific field
                String roleGroupId = rs.getString("RoleGroupId");

                emp = new Employee(fullName, email, password, token, phone, address, avatar, cartId, status, deletedAt, roleGroupId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emp;
    }

    // Add a new employee
    public boolean add(Employee employee) {
        boolean result = false;
        String accountQuery = "INSERT INTO Account (FullName, Email, Password, Token, Phone, Address, Avatar, CartId, Status, DeletedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String employeeQuery = "INSERT INTO Employee (Email, RoleGroupId) VALUES (?, ?)";

        try (PreparedStatement accountStmt = connection.prepareStatement(accountQuery);
             PreparedStatement employeeStmt = connection.prepareStatement(employeeQuery)) {

            // Insert into Accounts table
            accountStmt.setString(1, employee.getFullName());
            accountStmt.setString(2, employee.getEmail());
            accountStmt.setString(3, employee.getPassword());
            accountStmt.setString(4, employee.getToken());
            accountStmt.setString(5, employee.getPhone());
            accountStmt.setString(6, employee.getAddress());
            accountStmt.setString(7, employee.getAvatar());
            accountStmt.setString(8, employee.getCartId());
            accountStmt.setString(9, employee.getStatus());
            accountStmt.setTimestamp(10, employee.getDeletedAt() != null ? Timestamp.valueOf(employee.getDeletedAt()) : null);
            accountStmt.executeUpdate();

            // Insert into Employees table
            employeeStmt.setString(1, employee.getEmail());
            employeeStmt.setString(2, employee.getRoleGroupId());
            result = employeeStmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Update an employee
    public boolean update(Employee employee) {
        boolean result = false;
        String accountQuery = "UPDATE Account SET FullName = ?, Password = ?, Token = ?, Phone = ?, Address = ?, Avatar = ?, CartId = ?, Status = ?, DeletedAt = ? WHERE Email = ?";
        String employeeQuery = "UPDATE Employee SET RoleGroupId = ? WHERE Email = ?";

        try (PreparedStatement accountStmt = connection.prepareStatement(accountQuery);
             PreparedStatement employeeStmt = connection.prepareStatement(employeeQuery)) {

            // Update Accounts table
            accountStmt.setString(1, employee.getFullName());
            accountStmt.setString(2, employee.getPassword());
            accountStmt.setString(3, employee.getToken());
            accountStmt.setString(4, employee.getPhone());
            accountStmt.setString(5, employee.getAddress());
            accountStmt.setString(6, employee.getAvatar());
            accountStmt.setString(7, employee.getCartId());
            accountStmt.setString(8, employee.getStatus());
            accountStmt.setTimestamp(9, employee.getDeletedAt() != null ? Timestamp.valueOf(employee.getDeletedAt()) : null);
            accountStmt.setString(10, employee.getEmail());
            accountStmt.executeUpdate();

            // Update Employees table
            employeeStmt.setString(1, employee.getRoleGroupId());
            employeeStmt.setString(2, employee.getEmail());
            result = employeeStmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Delete an employee
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

    // Check if an employee exists by email
    public boolean checkExistByEmail(String email) {
        String query = "SELECT * FROM Employee WHERE Email = ?";
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
