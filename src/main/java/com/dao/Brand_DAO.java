package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import com.entity.Brand;

public class Brand_DAO {

    private final SessionFactory factory;

    public Brand_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Brand> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Brand b WHERE b.status IN ('Active', 'Inactive')";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public boolean changeStatus(String brandId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            
            // Lấy sản phẩm hiện tại
            String getStatusHql = "SELECT b.status FROM Brand b WHERE b.brandId = :brandId";
            Query statusQuery = session.createQuery(getStatusHql);
            statusQuery.setParameter("brandId", brandId);
            String currentStatus = (String) statusQuery.uniqueResult();
            
            // Xác định trạng thái mới
            String newStatus = "Active".equals(currentStatus) ? "Inactive" : "Active";
            
            // Cập nhật trạng thái
            String updateHql = "UPDATE Brand b SET b.status = :newStatus WHERE b.brandId = :brandId";
            Query updateQuery = session.createQuery(updateHql);
            updateQuery.setParameter("newStatus", newStatus);
            updateQuery.setParameter("brandId", brandId);
            
            int rowsAffected = updateQuery.executeUpdate();
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

    public Brand getById(String brandId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Brand b WHERE b.brandId = :brandId";
            Query query = session.createQuery(hql);
            query.setParameter("brandId", brandId);
            return (Brand) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Brand brand) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(brand);
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

    public boolean update(Brand brand) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(brand);
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

    public boolean delete(String brandId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE Brand b SET b.status = 'Deleted', b.deletedAt = GETDATE() WHERE b.brandId = :brandId";
            Query query = session.createQuery(hql);
            query.setParameter("brandId", brandId);
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

    public boolean checkExistById(String brandId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Brand b WHERE b.brandId = :brandId";
            Query query = session.createQuery(hql);
            query.setParameter("brandId", brandId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public boolean checkExistByName(String brandName) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Brand b WHERE b.brandName = :brandName";
            Query query = session.createQuery(hql);
            query.setParameter("brandName", brandName);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public String generateNextBrandId() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT MAX(b.brandId) FROM Brand b WHERE b.brandId LIKE 'BRAND%'";
            Query query = session.createQuery(hql);
            String maxId = (String) query.uniqueResult();
            if (maxId == null) {
                return "BRAND001";
            }
            int currentNum = Integer.parseInt(maxId.substring(5));
            return String.format("BRAND%03d", currentNum + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "BRAND001";
        } finally {
            if (session != null) session.close();
        }
    }
}
