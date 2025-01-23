package com.connectDB;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	public static Connection con = null;
	private static ConnectDB instance = new ConnectDB();
	
	public static ConnectDB getInstance() {
		return instance;
	}
	
	public void connect() {
        try {
        	String url = "jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=ProductDB;encrypt=true;trustServerCertificate=true";
            String user = "sa";
            String password = "10802";
            con = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Disconnected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
	
	public static Connection getConnection() {
		return con;
	}
}

