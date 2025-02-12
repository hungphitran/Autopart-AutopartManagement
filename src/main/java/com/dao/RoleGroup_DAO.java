package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import com.entity.RoleGroup;

public class RoleGroup_DAO {

    private final SessionFactory factory;

    public RoleGroup_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<RoleGroup> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM RoleGroup rg WHERE rg.status = 'Active'";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) session.close();
        }
    }

    public RoleGroup getById(String roleGroupId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM RoleGroup rg WHERE rg.roleGroupId = :roleGroupId";
            Query query = session.createQuery(hql);
            query.setParameter("roleGroupId", roleGroupId);
            return (RoleGroup) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(RoleGroup roleGroup) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(roleGroup);
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

    public boolean update(RoleGroup roleGroup) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(roleGroup);
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

    public boolean delete(String roleGroupId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE RoleGroup rg SET rg.status = 'Deleted', rg.deletedAt = current_timestamp() WHERE rg.roleGroupId = :roleGroupId";
            Query query = session.createQuery(hql);
            query.setParameter("roleGroupId", roleGroupId);
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

    public boolean checkExistById(String roleGroupId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT 1 FROM RoleGroup rg WHERE rg.roleGroupId = :roleGroupId";
            Query query = session.createQuery(hql);
            query.setParameter("roleGroupId", roleGroupId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    public String generateNextRoleGroupId() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT MAX(rg.roleGroupId) FROM RoleGroup rg WHERE rg.roleGroupId LIKE 'RG%'";
            Query query = session.createQuery(hql);
            String maxId = (String) query.uniqueResult();
            if (maxId == null) {
                return "RG001";
            }
            int currentNum = Integer.parseInt(maxId.substring(2));
            return String.format("RG%03d", currentNum + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "RG001";
        } finally {
            if (session != null) session.close();
        }
    }
}
