//package com.dao;
//
//import java.util.List;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.query.Query;
//import com.entity.Cart;
//
//public class Cart_DAO {
//
//    private final SessionFactory factory;
//
//    public Cart_DAO(SessionFactory factory) {
//        this.factory = factory;
//    }
//
//    // Retrieve a cart by its ID
//    public Cart getById(String cartId) {
//        try (Session session = factory.openSession()) {
//            String hqlCart = "FROM Cart c WHERE c.cartId = :cartId";
//            Query<Cart> queryCart = session.createQuery(hqlCart, Cart.class);
//            queryCart.setParameter("cartId", cartId);
//            Cart cart = queryCart.uniqueResult();
//
//            if (cart != null) {
//                String hqlProducts = "SELECT p.productId, p.amount FROM ProductsInCart p WHERE p.cartId = :cartId";
//                Query<Object[]> queryProducts = session.createQuery(hqlProducts, Object[].class);
//                queryProducts.setParameter("cartId", cartId);
//
//                for (Object[] row : queryProducts.getResultList()) {
//                    String productId = (String) row[0];
//                    int amount = (int) row[1];
//                    cart.getProducts().put(productId, amount);
//                }
//            }
//            return cart;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // Add a new cart
//    public boolean add(Cart cart) {
//        try (Session session = factory.openSession()) {
//            session.beginTransaction();
//
//            // Save cart
//            session.save(cart);
//
//            // Save products
//            cart.getProducts().forEach((productId, amount) -> {
//                String hql = "INSERT INTO ProductsInCart (cartId, productId, amount) VALUES (:cartId, :productId, :amount)";
//                Query<?> query = session.createNativeQuery(hql);
//                query.setParameter("cartId", cart.getCartId());
//                query.setParameter("productId", productId);
//                query.setParameter("amount", amount);
//                query.executeUpdate();
//            });
//
//            session.getTransaction().commit();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Update cart and products
//    public boolean update(Cart cart) {
//        try (Session session = factory.openSession()) {
//            session.beginTransaction();
//
//            // Update cart
//            session.update(cart);
//
//            // Remove old products
//            String hqlDelete = "DELETE FROM ProductsInCart p WHERE p.cartId = :cartId";
//            Query<?> deleteQuery = session.createQuery(hqlDelete);
//            deleteQuery.setParameter("cartId", cart.getCartId());
//            deleteQuery.executeUpdate();
//
//            // Insert new products
//            cart.getProducts().forEach((productId, amount) -> {
//                String hqlInsert = "INSERT INTO ProductsInCart (cartId, productId, amount) VALUES (:cartId, :productId, :amount)";
//                Query<?> insertQuery = session.createNativeQuery(hqlInsert);
//                insertQuery.setParameter("cartId", cart.getCartId());
//                insertQuery.setParameter("productId", productId);
//                insertQuery.setParameter("amount", amount);
//                insertQuery.executeUpdate();
//            });
//
//            session.getTransaction().commit();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Delete a cart by ID
//    public boolean delete(String cartId) {
//        try (Session session = factory.openSession()) {
//            session.beginTransaction();
//
//            String hql = "UPDATE Cart c SET c.status = 'Deleted', c.deletedAt = current_timestamp() WHERE c.cartId = :cartId";
//            Query<?> query = session.createQuery(hql);
//            query.setParameter("cartId", cartId);
//
//            int rowsAffected = query.executeUpdate();
//            session.getTransaction().commit();
//            return rowsAffected > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Check if a cart exists by its ID
//    public boolean checkExistById(String cartId) {
//        try (Session session = factory.openSession()) {
//            String hql = "SELECT 1 FROM Cart c WHERE c.cartId = :cartId";
//            Query<?> query = session.createQuery(hql);
//            query.setParameter("cartId", cartId);
//            return query.uniqueResult() != null;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // Generate the next cart ID
//    public String generateNextCartId() {
//        try (Session session = factory.openSession()) {
//            String hql = "SELECT MAX(c.cartId) FROM Cart c WHERE c.cartId LIKE 'CART%'";
//            Query<String> query = session.createQuery(hql, String.class);
//            String maxId = query.uniqueResult();
//
//            if (maxId == null) {
//                return "CART001";
//            }
//            int currentNum = Integer.parseInt(maxId.substring(4));
//            return String.format("CART%03d", currentNum + 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "CART001";
//        }
//    }
//}
