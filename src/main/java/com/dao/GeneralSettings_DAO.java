package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import com.entity.GeneralSettings;

public class GeneralSettings_DAO {

    private final SessionFactory factory;

    // Constructor to initialize the SessionFactory
    public GeneralSettings_DAO(SessionFactory factory) {
        this.factory = factory;
    }

//    public List<GeneralSettings> getAll() {
//        Session session = null;
//        try {
//            session = factory.openSession();
//            String hql = "FROM GeneralSettings";
//            Query query = session.createQuery(hql);
//            return query.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ArrayList<>(); // Return an empty list in case of error
//        } finally {
//            if (session != null) session.close();
//        }
//    }

    public GeneralSettings getByWebsiteName(String websiteName) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM GeneralSettings gs WHERE gs.websiteName = :websiteName";
            Query query = session.createQuery(hql);
            query.setParameter("websiteName", websiteName);
            return (GeneralSettings) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(GeneralSettings generalSettings) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(generalSettings);
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

    public boolean update(GeneralSettings generalSettings) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(generalSettings);
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

    public boolean delete(String websiteName) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "DELETE FROM GeneralSettings gs WHERE gs.websiteName = :websiteName";
            Query query = session.createQuery(hql);
            query.setParameter("websiteName", websiteName);
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

    public boolean checkExistByWebsiteName(String websiteName) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM GeneralSettings gs WHERE gs.websiteName = :websiteName";
            Query query = session.createQuery(hql);
            query.setParameter("websiteName", websiteName);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
}
