package com.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.connectDB.ConnectDB;
import com.entity.RoleGroup;

public class RoleGroup_DAO {
    private final ConnectDB db = ConnectDB.getInstance();
    private final Connection con;

    // Constructor to initialize the connection
    public RoleGroup_DAO() {
        db.connect();  // Initialize the database connection
        this.con = ConnectDB.getConnection();  // Get the connection object
    }

    public ArrayList<RoleGroup> getAll() {
        String query = "SELECT * FROM RoleGroups WHERE Status = 'Active'";
        ArrayList<RoleGroup> list = new ArrayList<>();

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String roleGroupId = rs.getString("RoleGroupId");
                String roleGroupName = rs.getString("RoleGroupName");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                RoleGroup temp = new RoleGroup(roleGroupId, roleGroupName, status, deletedAt);
                list.add(temp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public RoleGroup getById(String roleGroupId) {
        String query = "SELECT * FROM RoleGroups WHERE RoleGroupID = ?";
        RoleGroup temp = null;

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, roleGroupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	String id = rs.getString("RoleGroupId");
                String roleGroupName = rs.getString("RoleGroupName");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                temp = new RoleGroup(id, roleGroupName, status, deletedAt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public boolean add(RoleGroup roleGroup) {
        boolean result = false;
        String query = "INSERT INTO RoleGroups (RoleGroupID, RoleGroupName, Status, DeletedAt) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, roleGroup.getRoleGroupId());
            stmt.setString(2, roleGroup.getRoleGroupName());
            stmt.setString(3, roleGroup.getStatus());
            stmt.setTimestamp(4, roleGroup.getDeletedAt() != null ? Timestamp.valueOf(roleGroup.getDeletedAt()) : null);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean update(RoleGroup roleGroup) {
        boolean result = false;
        String query = "UPDATE RoleGroups SET RoleGroupName = ?, Status = ?, DeletedAt = ? WHERE RoleGroupID = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, roleGroup.getRoleGroupName());
            stmt.setString(2, roleGroup.getStatus());
            stmt.setTimestamp(3, roleGroup.getDeletedAt() != null ? Timestamp.valueOf(roleGroup.getDeletedAt()) : null);
            stmt.setString(4, roleGroup.getRoleGroupId());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean delete(String roleGroupId) {
        boolean result = false;
        String query = "UPDATE RoleGroups SET Status = 'Deleted', deletedAt = GETDATE() WHERE RoleGroupID = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, roleGroupId);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public boolean checkExistById(String roleGroupId) {
        String query = "SELECT * FROM RoleGroups WHERE RoleGroupID = ?";
        boolean result = false;

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, roleGroupId);
            ResultSet rs = stmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String generateNextRoleGroupId() {
        String query = "SELECT MAX(RoleGroupID) FROM RoleGroups WHERE RoleGroupID LIKE 'RG%'";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId == null) {
                    return "RG001";
                }

                if (maxId.length() >= 2) {
                    try {
                        int currentNum = Integer.parseInt(maxId.substring(2).trim());
                        return String.format("RG%03d", currentNum + 1);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return "RG001";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "RG000";
    }
}
