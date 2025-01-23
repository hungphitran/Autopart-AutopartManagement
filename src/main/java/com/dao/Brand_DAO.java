package com.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.connectDB.ConnectDB;
import com.entity.Brand;

public class Brand_DAO {
    private final ConnectDB db = ConnectDB.getInstance();
    private final Connection con;

    public Brand_DAO() {
        db.connect();
        this.con = ConnectDB.getConnection();
    }

    public ArrayList<Brand> getAll() {
        String query = "SELECT * FROM Brand WHERE Status = 'Active'";
        ArrayList<Brand> list = new ArrayList<>();

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String brandName = rs.getString("BrandName");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                list.add(new Brand(brandName, status, deletedAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Brand getByBrandName(String brandName) {
        String query = "SELECT * FROM Brand WHERE BrandName = ?";
        Brand brand = null;

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, brandName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("BrandName");
                String status = rs.getString("Status");
                LocalDateTime deletedAt = rs.getTimestamp("DeletedAt") != null ? rs.getTimestamp("DeletedAt").toLocalDateTime() : null;

                brand = new Brand(name, status, deletedAt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brand;
    }

    public boolean add(Brand brand) {
        boolean result = false;
        String query = "INSERT INTO Brand (BrandName, Status, DeletedAt) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, brand.getBrandName());
            stmt.setString(2, brand.getStatus());
            stmt.setTimestamp(3, brand.getDeletedAt() != null ? java.sql.Timestamp.valueOf(brand.getDeletedAt()) : null);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean update(Brand brand) {
        boolean result = false;
        String query = "UPDATE Brand SET Status = ?, DeletedAt = ? WHERE BrandName = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, brand.getStatus());
            stmt.setTimestamp(2, brand.getDeletedAt() != null ? java.sql.Timestamp.valueOf(brand.getDeletedAt()) : null);
            stmt.setString(3, brand.getBrandName());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean delete(String brandName) {
        boolean result = false;
        String query = "UPDATE Brand SET Status = 'Deleted', DeletedAt = GETDATE() WHERE BrandName = ?";

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, brandName);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean checkExistByBrandName(String brandName) {
        String query = "SELECT * FROM Brand WHERE BrandName = ?";
        boolean exists = false;

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, brandName);
            ResultSet rs = stmt.executeQuery();
            exists = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }
}
