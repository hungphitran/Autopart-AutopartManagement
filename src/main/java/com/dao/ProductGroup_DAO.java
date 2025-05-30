package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import com.entity.ProductGroup;

public class ProductGroup_DAO {

    private final SessionFactory factory;

    public ProductGroup_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<ProductGroup> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
//            String hql = "FROM ProductGroup pg WHERE pg.status = 'Active'";
            String hql = "FROM ProductGroup";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    
    public List<ProductGroup> getTopByProductCount(int limit) {
        Session session = null;
        try {
            session = factory.openSession();
            String sql = "SELECT pg.* " +
                         "FROM ProductGroup pg " +
                         "WHERE pg.status = 'Active' AND pg.deleted = 0 " +
                         "ORDER BY (SELECT COUNT(*) FROM Product p WHERE p.productGroupId = pg.productGroupId AND p.status = 'Active' AND p.deleted = 0) DESC";
            Query query = session.createSQLQuery(sql).addEntity(ProductGroup.class);
            query.setMaxResults(limit);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
            } finally {
            if (session != null) session.close();
        }
    }
    

    public ProductGroup getById(String productGroupId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM ProductGroup pg WHERE pg.productGroupId = :productGroupId";
            Query query = session.createQuery(hql);
            query.setParameter("productGroupId", productGroupId);
            return (ProductGroup) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(ProductGroup productGroup) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(productGroup);
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

    public boolean update(ProductGroup productGroup) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(productGroup);
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

    public boolean delete(String productGroupId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE ProductGroup pg SET pg.status = 'Deleted', pg.deletedAt = GETDATE() WHERE pg.productGroupId = :productGroupId";
            Query query = session.createQuery(hql);
            query.setParameter("productGroupId", productGroupId);
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

    public boolean checkExistById(String productGroupId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM ProductGroup pg WHERE pg.productGroupId = :productGroupId";
            Query query = session.createQuery(hql);
            query.setParameter("productGroupId", productGroupId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
    
    
    public boolean checkExistByName(String productGroupName) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM ProductGroup pg WHERE pg.groupName = :groupName";
            Query query = session.createQuery(hql);
            query.setParameter("groupName", productGroupName);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public String generateNextProductGroupId() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT MAX(pg.productGroupId) FROM ProductGroup pg WHERE pg.productGroupId LIKE 'PG%'";
            Query query = session.createQuery(hql);
            String maxId = (String) query.uniqueResult();
            if (maxId == null) {
                return "PG001";
            }
            int currentNum = Integer.parseInt(maxId.substring(2));
            return String.format("PG%03d", currentNum + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "PG001";
        } finally {
            if (session != null) session.close();
        }
    }
    
    
    
    
    public boolean changeStatus(String groupId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            
            // Lấy sản phẩm hiện tại
            String getStatusHql = "SELECT pg.status FROM ProductGroup pg WHERE pg.productGroupId = :groupId";
            Query statusQuery = session.createQuery(getStatusHql);
            statusQuery.setParameter("groupId", groupId);
            String currentStatus = (String) statusQuery.uniqueResult();
            System.out.println("current status of id: "+groupId +" "+": "+ currentStatus);
            // Xác định trạng thái mới
            String newStatus = "Active".equals(currentStatus) ? "Inactive" : "Active";
            
            // Cập nhật trạng thái
            String updateHql = "UPDATE ProductGroup pg SET pg.status = :newStatus WHERE pg.productGroupId = :groupId";
            Query updateQuery = session.createQuery(updateHql);
            updateQuery.setParameter("newStatus", newStatus);
            updateQuery.setParameter("groupId", groupId);
            
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
}
