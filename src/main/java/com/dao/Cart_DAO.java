package com.dao;

import java.sql.*;
import java.util.HashMap;
import com.connectDB.ConnectDB;
import com.entity.Cart;

public class Cart_DAO {
    private final ConnectDB db = ConnectDB.getInstance();
    private final Connection con;

    // Constructor to initialize the connection
    public Cart_DAO() {
        db.connect(); // Initialize the database connection
        this.con = ConnectDB.getConnection(); // Get the connection object
    }

    // Retrieve a cart by its ID
    public Cart getById(String cartId) {
        String queryCart = "SELECT * FROM Cart WHERE CartId = ?";
        String queryProducts = "SELECT ProductId, Amount FROM ProductsInCart WHERE CartId = ?";
        Cart cart = null;

        try (PreparedStatement stmtCart = con.prepareStatement(queryCart);
             PreparedStatement stmtProducts = con.prepareStatement(queryProducts)) {
            stmtCart.setString(1, cartId);
            ResultSet rsCart = stmtCart.executeQuery();

            if (rsCart.next()) {
                String status = rsCart.getString("Status");
                cart = new Cart(cartId, status);

                stmtProducts.setString(1, cartId);
                ResultSet rsProducts = stmtProducts.executeQuery();

                while (rsProducts.next()) {
                    String productId = rsProducts.getString("ProductId");
                    int amount = rsProducts.getInt("Amount");
                    cart.getProducts().put(productId, amount);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cart;
    }

    // Add a new cart
    public boolean add(Cart cart) {
        boolean result = false;
        String queryCart = "INSERT INTO Cart (CartId, Status) VALUES (?, ?)";
        String queryProducts = "INSERT INTO ProductsInCart (CartId, ProductId, Amount) VALUES (?, ?, ?)";

        try (PreparedStatement stmtCart = con.prepareStatement(queryCart);
             PreparedStatement stmtProducts = con.prepareStatement(queryProducts)) {
            // Insert cart
            stmtCart.setString(1, cart.getCartId());
            stmtCart.setString(2, cart.getStatus());
            result = stmtCart.executeUpdate() >= 1;

            // Insert products
            for (String productId : cart.getProducts().keySet()) {
                stmtProducts.setString(1, cart.getCartId());
                stmtProducts.setString(2, productId);
                stmtProducts.setInt(3, cart.getProducts().get(productId));
                stmtProducts.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Update cart and products
    public boolean update(Cart cart) {
        boolean result = false;
        String updateCartQuery = "UPDATE Cart SET Status = ? WHERE CartId = ?";
        String deleteProductsQuery = "DELETE FROM ProductsInCart WHERE CartId = ?";
        String insertProductsQuery = "INSERT INTO ProductsInCart (CartId, ProductId, Amount) VALUES (?, ?, ?)";

        try (PreparedStatement updateCartStmt = con.prepareStatement(updateCartQuery);
             PreparedStatement deleteProductsStmt = con.prepareStatement(deleteProductsQuery);
             PreparedStatement insertProductsStmt = con.prepareStatement(insertProductsQuery)) {
            // Update cart status
            updateCartStmt.setString(1, cart.getStatus());
            updateCartStmt.setString(2, cart.getCartId());
            updateCartStmt.executeUpdate();

            // Remove existing products
            deleteProductsStmt.setString(1, cart.getCartId());
            deleteProductsStmt.executeUpdate();

            // Insert updated products
            for (String productId : cart.getProducts().keySet()) {
                insertProductsStmt.setString(1, cart.getCartId());
                insertProductsStmt.setString(2, productId);
                insertProductsStmt.setInt(3, cart.getProducts().get(productId));
                insertProductsStmt.executeUpdate();
            }
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Delete a cart by ID
    public boolean delete(String cartId) {
        boolean result = false;
        String updateStatusQuery = "UPDATE Cart SET Status = 'Deleted', DeletedAt = GETDATE() WHERE CartId = ?";

        try (PreparedStatement updateStatusStmt = con.prepareStatement(updateStatusQuery)) {
            // Update the status of the cart to 'Deleted'
            updateStatusStmt.setString(1, cartId);
            result = updateStatusStmt.executeUpdate() >= 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    // Check if a cart exists by its ID
    public boolean checkExistById(String cartId) {
        String query = "SELECT * FROM Cart WHERE CartId = ?";
        boolean exists = false;

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, cartId);
            ResultSet rs = stmt.executeQuery();
            exists = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }

    // Generate the next cart ID
    public String generateNextCartId() {
        String query = "SELECT MAX(CartId) FROM Cart WHERE CartId LIKE 'CART%'";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                String maxId = rs.getString(1);
                if (maxId == null) {
                    return "CART001";
                }

                if (maxId.length() >= 4) {
                    try {
                        int currentNum = Integer.parseInt(maxId.substring(4).trim());
                        return String.format("CART%03d", currentNum + 1);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return "CART001";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "CART000";
    }
}
