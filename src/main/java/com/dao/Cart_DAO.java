package com.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Brand;
import com.entity.Cart;
import com.entity.Product;

public class Cart_DAO {

    private final SessionFactory factory;

    public Cart_DAO(SessionFactory factory) {
        this.factory = factory;
    }


    @Transactional
    public Cart getById(String cartId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT c FROM Cart c LEFT JOIN FETCH c.products WHERE c.cartId = :cartId";
            Query query = session.createQuery(hql);
            query.setParameter("cartId", cartId);
            return (Cart) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Cart cart) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            // Save the cart
            session.save(cart);
            session.flush();

            
//            // For ProductsInCart, use SQL with createSQLQuery()
//            for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
//                String sql = "INSERT INTO ProductsInCart (cartId, productId, amount) VALUES (?, ?, ?)";
//                session.createSQLQuery(sql)
//                      .setParameter(0, cart.getCartId())
//                      .setParameter(1, entry.getKey().getProductId())
//                      .setParameter(2, entry.getValue())
//                      .executeUpdate();
//            }
//
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public boolean update(Cart cart) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(cart);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean delete(String cartId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE Cart c SET c.status = 'Deleted' WHERE c.cartId = :cartId";
            Query query = session.createQuery(hql);
            query.setParameter("cartId", cartId);
            int rowsAffected = query.executeUpdate();
            transaction.commit();
            return rowsAffected > 0;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public boolean checkExistById(String cartId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT 1 FROM Cart c WHERE c.cartId = :cartId AND status ='Active'";
            Query query = session.createQuery(hql);
            query.setParameter("cartId", cartId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    public String generateNextCartId() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT MAX(c.cartId) FROM Cart c WHERE c.cartId LIKE 'CART%'";
            Query query = session.createQuery(hql);
            String maxId = (String) query.uniqueResult();
            if (maxId == null) {
                return "CART001";
            }
            int currentNum = Integer.parseInt(maxId.substring(4));
            return String.format("CART%03d", currentNum + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "CART001";
        } finally {
            if (session != null) session.close();
        }
    }
    
}
