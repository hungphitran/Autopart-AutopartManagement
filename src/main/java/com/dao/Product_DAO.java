package com.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;
import com.entity.Product;

public class Product_DAO {

    private final SessionFactory factory;

    public Product_DAO(SessionFactory factory) {
        this.factory = factory;
    }

	public List<Product> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Product p WHERE p.status = 'Active'";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) session.close();
        }
    }

    public Product getById(String productId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Product p WHERE p.productId = :productId";
            Query query = session.createQuery(hql);
            query.setParameter("productId", productId);
            return (Product) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Product product) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(product);
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

    public boolean update(Product product) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(product);
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

    public boolean delete(String productId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE Product p SET p.status = 'Deleted', p.deletedAt = current_timestamp() WHERE p.productId = :productId";
            Query query = session.createQuery(hql);
            query.setParameter("productId", productId);
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

    public boolean checkExistById(String productId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT 1 FROM Product p WHERE p.productId = :productId";
            Query query = session.createQuery(hql);
            query.setParameter("productId", productId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    public String generateNextProductId() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT MAX(p.productId) FROM Product p WHERE p.productId LIKE 'PROD%'";
            Query query = session.createQuery(hql);
            String maxId = (String) query.uniqueResult();
            if (maxId == null) {
                return "PROD001";
            }
            int currentNum = Integer.parseInt(maxId.substring(4));
            return String.format("PROD%03d", currentNum + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "PROD001";
        } finally {
            if (session != null) session.close();
        }
    }
}
