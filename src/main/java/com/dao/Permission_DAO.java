package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import com.entity.Permission;

public class Permission_DAO {

    private final SessionFactory factory;

    public Permission_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Permission> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Permission p WHERE p.status = 'Active'";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) session.close();
        }
    }

    public Permission getById(String permissionId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Permission p WHERE p.permissionId = :permissionId";
            Query query = session.createQuery(hql);
            query.setParameter("permissionId", permissionId);
            return (Permission) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Permission permission) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(permission);
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

    public boolean update(Permission permission) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(permission);
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

    public boolean delete(String permissionId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE Permission p SET p.status = 'Deleted', p.deletedAt = current_timestamp() WHERE p.permissionId = :permissionId";
            Query query = session.createQuery(hql);
            query.setParameter("permissionId", permissionId);
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

    public boolean checkExistById(String permissionId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT 1 FROM Permission p WHERE p.permissionId = :permissionId";
            Query query = session.createQuery(hql);
            query.setParameter("permissionId", permissionId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    public String generateNextPermissionId() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT MAX(p.permissionId) FROM Permission p WHERE p.permissionId LIKE 'PER%'";
            Query query = session.createQuery(hql);
            String maxId = (String) query.uniqueResult();
            if (maxId == null) {
                return "PER001";
            }
            int currentNum = Integer.parseInt(maxId.substring(3));
            return String.format("PER%03d", currentNum + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "PER001";
        } finally {
            if (session != null) session.close();
        }
    }
}
