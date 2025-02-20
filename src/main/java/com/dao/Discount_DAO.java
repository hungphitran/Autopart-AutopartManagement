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
import com.entity.Discount;
import com.entity.Product;

public class Discount_DAO {

    private final SessionFactory factory;

    public Discount_DAO(SessionFactory factory) {
        this.factory = factory;
    }


    @Transactional
    public Discount getById(String discountId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Discount d WHERE d.status = 'Active' and d.discountId = :discountId";
            Query query = session.createQuery(hql);
            query.setParameter("discountId", discountId);
            return (Discount) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Discount discount) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            // Save the cart
            session.save(discount);

            
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
    
    public boolean update(Discount discount) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(discount);
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

    public boolean delete(String discountId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE Discount d SET d.status = 'Deleted' WHERE d.discountId = :discountId";
            Query query = session.createQuery(hql);
            query.setParameter("discountId", discountId);
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
    
    public boolean checkExistById(String discountId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT 1 FROM Discount d WHERE d.discountId = :discountId AND status ='Active'";
            Query query = session.createQuery(hql);
            query.setParameter("discountId", discountId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    public String generateNextDiscountId() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT MAX(d.discountId) FROM Discount d WHERE d.discountId LIKE 'DIS%'";
            Query query = session.createQuery(hql);
            String maxId = (String) query.uniqueResult();
            if (maxId == null) {
                return "DIST001";
            }
            int currentNum = Integer.parseInt(maxId.substring(4));
            return String.format("DIS%03d", currentNum + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "DIS001";
        } finally {
            if (session != null) session.close();
        }
    }
    
}

