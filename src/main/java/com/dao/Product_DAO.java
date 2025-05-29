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
            String hql = "FROM Product p WHERE p.status IN ('Active', 'Inactive')";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public List<Product> getTopByStock(int limit) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Product p WHERE p.status = 'Active' ORDER BY p.stock DESC";
            Query query = session.createQuery(hql);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) session.close();
        }
    }

 // Lấy sản phẩm được đặt hàng nhiều nhất
    public List<Product> getTopProductsByOrders(int limit) {
        Session session = null;
        try {
            session = factory.openSession();
            String sql = "SELECT p.* " +
                         "FROM Product p " +
                         "WHERE p.status = 'Active' AND p.deleted = 0 " +
                         "ORDER BY (SELECT SUM(od.amount) FROM OrderDetail od WHERE od.productId = p.productId) DESC";
            Query query = session.createSQLQuery(sql).addEntity(Product.class);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) session.close();
        }
    }
    
    public boolean changeStatus(String productId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            
            // Lấy sản phẩm hiện tại
            String getStatusHql = "SELECT p.status FROM Product p WHERE p.productId = :productId";
            Query statusQuery = session.createQuery(getStatusHql);
            statusQuery.setParameter("productId", productId);
            String currentStatus = (String) statusQuery.uniqueResult();
            
            // Xác định trạng thái mới
            String newStatus = "Active".equals(currentStatus) ? "Inactive" : "Active";
            
            // Cập nhật trạng thái
            String updateHql = "UPDATE Product p SET p.status = :newStatus WHERE p.productId = :productId";
            Query updateQuery = session.createQuery(updateHql);
            updateQuery.setParameter("newStatus", newStatus);
            updateQuery.setParameter("productId", productId);
            
            int rowsAffected = updateQuery.executeUpdate();
            transaction.commit();
            return rowsAffected > 0;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw e;
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
            throw e;
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
            throw e;
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
            throw e;
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
            String hql = "UPDATE Product p SET p.status = 'Deleted', p.deletedAt = GETDATE() WHERE p.productId = :productId";
            Query query = session.createQuery(hql);
            query.setParameter("productId", productId);
            int rowsAffected = query.executeUpdate();
            transaction.commit();
            return rowsAffected > 0;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw e;
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
