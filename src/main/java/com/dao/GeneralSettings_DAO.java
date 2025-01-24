package com.dao;

import java.sql.*;
import java.util.ArrayList;
import com.entity.GeneralSettings;

public class GeneralSettings_DAO {
    private final Connection connection;

    // Constructor to initialize the connection
    public GeneralSettings_DAO() throws ClassNotFoundException, SQLException {
    	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    	connection = DriverManager.getConnection("jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=ProductDB;encrypt=true;trustServerCertificate=true;sslProtocol=TLSv1.2;", "sa", "10802");
    }

    // Retrieve all general settings
    public ArrayList<GeneralSettings> getAll() {
        String query = "SELECT * FROM GeneralSettings";
        ArrayList<GeneralSettings> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String websiteName = rs.getString("WebsiteName");
                String logo = rs.getString("Logo");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                String copyright = rs.getString("Copyright");

                GeneralSettings settings = new GeneralSettings(websiteName, logo, phone, email, address, copyright);
                list.add(settings);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Retrieve general settings by website name
    public GeneralSettings getByWebsiteName(String websiteName) {
        String query = "SELECT * FROM GeneralSettings WHERE WebsiteName = ?";
        GeneralSettings settings = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, websiteName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String logo = rs.getString("Logo");
                String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String address = rs.getString("Address");
                String copyright = rs.getString("Copyright");

                settings = new GeneralSettings(websiteName, logo, phone, email, address, copyright);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return settings;
    }

    // Add new general settings
    public boolean add(GeneralSettings settings) {
        boolean result = false;
        String query = "INSERT INTO GeneralSettings (WebsiteName, Logo, Phone, Email, Address, Copyright) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, settings.getWebsiteName());
            stmt.setString(2, settings.getLogo());
            stmt.setString(3, settings.getPhone());
            stmt.setString(4, settings.getEmail());
            stmt.setString(5, settings.getAddress());
            stmt.setString(6, settings.getCopyright());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Update existing general settings
    public boolean update(GeneralSettings settings) {
        boolean result = false;
        String query = "UPDATE GeneralSettings SET Logo = ?, Phone = ?, Email = ?, Address = ?, Copyright = ? WHERE WebsiteName = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, settings.getLogo());
            stmt.setString(2, settings.getPhone());
            stmt.setString(3, settings.getEmail());
            stmt.setString(4, settings.getAddress());
            stmt.setString(5, settings.getCopyright());
            stmt.setString(6, settings.getWebsiteName());

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Delete general settings by website name
    public boolean delete(String websiteName) {
        boolean result = false;
        String query = "DELETE FROM GeneralSettings WHERE WebsiteName = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, websiteName);

            result = stmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Check if a general settings entry exists by website name
    public boolean checkExistByWebsiteName(String websiteName) {
        String query = "SELECT * FROM GeneralSettings WHERE WebsiteName = ?";
        boolean result = false;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, websiteName);
            ResultSet rs = stmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
