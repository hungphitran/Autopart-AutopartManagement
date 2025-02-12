package com.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;
import com.entity.Blog;

public class Blog_DAO {

    private final SessionFactory factory;

    public Blog_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Blog> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Blog b WHERE b.status = 'Active'";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) session.close();
        }
    }

    public Blog getById(String blogId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Blog b WHERE b.blogId = :blogId";
            Query query = session.createQuery(hql);
            query.setParameter("blogId", blogId);
            return (Blog) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Blog blog) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(blog);
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

    public boolean update(Blog blog) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(blog);
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

    public boolean delete(String blogId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE Blog b SET b.status = 'Deleted', b.deletedAt = current_timestamp() WHERE b.blogId = :blogId";
            Query query = session.createQuery(hql);
            query.setParameter("blogId", blogId);
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

    public boolean checkExistById(String blogId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT 1 FROM Blog b WHERE b.blogId = :blogId";
            Query query = session.createQuery(hql);
            query.setParameter("blogId", blogId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    public String generateNextBlogId() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT MAX(b.blogId) FROM Blog b WHERE b.blogId LIKE 'BLOG%'";
            Query query = session.createQuery(hql);
            String maxId = (String) query.uniqueResult();
            if (maxId == null) {
                return "BLOG001";
            }
            int currentNum = Integer.parseInt(maxId.substring(4));
            return String.format("BLOG%03d", currentNum + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "BLOG001";
        } finally {
            if (session != null) session.close();
        }
    }
}
