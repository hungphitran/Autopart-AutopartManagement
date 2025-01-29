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
            String hql = "FROM ProductGroup pg WHERE pg.status = 'Active'";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (session != null) session.close();
        }
    }

    public ProductGroup getByGroupName(String groupName) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM ProductGroup pg WHERE pg.groupName = :groupName";
            Query query = session.createQuery(hql);
            query.setParameter("groupName", groupName);
            return (ProductGroup) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
            return false;
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

    public boolean delete(String groupName) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE ProductGroup pg SET pg.status = 'Deleted', pg.deletedAt = current_timestamp() WHERE pg.groupName = :groupName";
            Query query = session.createQuery(hql);
            query.setParameter("groupName", groupName);
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

    public boolean checkExistByGroupName(String groupName) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM ProductGroup pg WHERE pg.groupName = :groupName";
            Query query = session.createQuery(hql);
            query.setParameter("groupName", groupName);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
}
