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
            String hql = "FROM Brand b WHERE b.status = 'Active'";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (session != null) session.close();
        }
    }

    public Brand getByBrandName(String brandName) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Brand b WHERE b.brandName = :brandName";
            Query query = session.createQuery(hql);
            query.setParameter("brandName", brandName);
            return (Brand) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

    public boolean delete(String brandName) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE Brand b SET b.status = 'Deleted', b.deletedAt = current_timestamp() WHERE b.brandName = :brandName";
            Query query = session.createQuery(hql);
            query.setParameter("brandName", brandName);
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

    public boolean checkExistByBrandName(String brandName) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Brand b WHERE b.brandName = :brandName";
            Query query = session.createQuery(hql);
            query.setParameter("brandName", brandName);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
}
