package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.connectDB.ConnectDB;
import com.entity.Permission;

public class Permission_DAO {
    private final ConnectDB db = ConnectDB.getInstance();
    private final Connection con;

    // Constructor to initialize the connection
    public Permission_DAO() {
        db.connect();
        this.con = ConnectDB.getConnection();
    }

    public ArrayList<Permission> getAll() {
        String query = "SELECT * FROM Permissions WHERE Status = 'Active'";
        ArrayList<Permission> list = new ArrayList<>();

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String permissionId = rs.getString("PermissionId");
                String permissionName = rs.getString("PermissionName");
                String description = rs.getString("Description");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                Permission temp = new Permission(permissionId, permissionName, description, status, deletedAt);
                list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Permission getById(String permissionId) {
        String query = "SELECT * FROM Permissions WHERE PermissionID = ?";
        Permission temp = null;

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, permissionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	 String id = rs.getString("PermissionId");
                 String permissionName = rs.getString("PermissionName");
                 String description = rs.getString("Description");
                 String status = rs.getString("Status");
                 LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                temp = new Permission(id, permissionName, description, status, deletedAt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public boolean add(Permission permission) {
        boolean result = false;
        String query = "INSERT INTO Permissions (PermissionID, PermissionName, Description, Status, DeletedAt) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, permission.getPermissionId());
            stmt.setString(2, permission.getPermissionName());
            stmt.setString(3, permission.getDescription());
            stmt.setString(4, permission.getStatus());
            stmt.setTimestamp(5, permission.getDeletedAt() != null ? java.sql.Timestamp.valueOf(permission.getDeletedAt()) : null);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean update(Permission permission) {
        boolean result = false;
        String query = "UPDATE Permissions SET PermissionName = ?, Description = ?, Status = ?, DeletedAt = ? WHERE PermissionID = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, permission.getPermissionName());
            stmt.setString(2, permission.getDescription());
            stmt.setString(3, permission.getStatus());
            stmt.setTimestamp(4, permission.getDeletedAt() != null ? java.sql.Timestamp.valueOf(permission.getDeletedAt()) : null);
            stmt.setString(5, permission.getPermissionId());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean delete(String permissionId) {
        boolean result = false;
        String query = "UPDATE Permissions SET Status = 'Deleted', DeletedAt = GETDATE() WHERE PermissionID = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, permissionId);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean checkExistById(String permissionId) {
        String query = "SELECT * FROM Permissions WHERE PermissionID = ?";
        boolean result = false;

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, permissionId);
            ResultSet rs = stmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String generateNextPermissionId() {
        String query = "SELECT MAX(PermissionID) FROM Permissions WHERE PermissionID LIKE 'PER%'";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId == null) {
                    return "PER001";
                }

                if (maxId.length() >= 3) {
                    try {
                        int currentNum = Integer.parseInt(maxId.substring(3).trim());
                        return String.format("PER%03d", currentNum + 1);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return "PER001";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "PER000";
    }
}
