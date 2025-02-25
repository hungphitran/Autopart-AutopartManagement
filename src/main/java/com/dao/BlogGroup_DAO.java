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

    public BlogGroup getById(String blogGroupId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM BlogGroup bg WHERE bg.blogGroupId = :blogGroupId";
            Query query = session.createQuery(hql);
            query.setParameter("blogGroupId", blogGroupId);
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

    public boolean delete(String blogGroupId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE BlogGroup bg SET bg.status = 'Deleted', bg.deletedAt = GETDATE() WHERE bg.blogGroupId = :blogGroupId";
            Query query = session.createQuery(hql);
            query.setParameter("blogGroupId", blogGroupId);
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

    public boolean checkExistById(String blogGroupId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM BlogGroup bg WHERE bg.blogGroupId = :blogGroupId";
            Query query = session.createQuery(hql);
            query.setParameter("blogGroupId", blogGroupId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public String generateNextBlogtGroupId() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT MAX(bg.blogGroupId) FROM BlogGroup bg WHERE bg.blogGroupId LIKE 'BG%'";
            Query query = session.createQuery(hql);
            String maxId = (String) query.uniqueResult();
            if (maxId == null) {
                return "BG001";
            }
            int currentNum = Integer.parseInt(maxId.substring(2));
            return String.format("BG%03d", currentNum + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "BG001";
        } finally {
            if (session != null) session.close();
        }
    }
}
