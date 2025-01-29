package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import com.entity.BlogGroup;

public class BlogGroup_DAO {

    private final SessionFactory factory;

    public BlogGroup_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<BlogGroup> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM BlogGroup bg WHERE bg.status = 'Active'";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (session != null) session.close();
        }
    }

    public BlogGroup getByGroupName(String groupName) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM BlogGroup bg WHERE bg.groupName = :groupName";
            Query query = session.createQuery(hql);
            query.setParameter("groupName", groupName);
            return (BlogGroup) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(BlogGroup blogGroup) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(blogGroup);
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

    public boolean update(BlogGroup blogGroup) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(blogGroup);
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
            String hql = "UPDATE BlogGroup bg SET bg.status = 'Deleted', bg.deletedAt = current_timestamp() WHERE bg.groupName = :groupName";
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
            String hql = "FROM BlogGroup bg WHERE bg.groupName = :groupName";
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
