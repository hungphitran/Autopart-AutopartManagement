package com.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import com.entity.ImportDetail;

public class ImportDetail_DAO {

    private final SessionFactory factory;

    public ImportDetail_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<ImportDetail> getAllByImportId(String importId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM ImportDetail id WHERE id.importId = :importId";
            Query query = session.createQuery(hql);
            query.setParameter("importId", importId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(ImportDetail importDetail) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(importDetail);
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

    public boolean update(ImportDetail importDetail) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(importDetail);
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

    public boolean checkExistById(String importId, String productId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT 1 FROM ImportDetail id WHERE id.importId = :importId AND id.productId = :productId";
            Query query = session.createQuery(hql);
            query.setParameter("importId", importId);
            query.setParameter("productId", productId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
//
//    public boolean delete(String importId) {
//        Session session = null;
//        Transaction transaction = null;
//        try {
//            session = factory.openSession();
//            transaction = session.beginTransaction();
//            String hql = "DELETE FROM ImportDetail id WHERE id.importId = :importId";
//            Query query = session.createQuery(hql);
//            query.setParameter("importId", importId);
//            int rowsAffected = query.executeUpdate();
//            transaction.commit();
//            return rowsAffected > 0;
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            e.printStackTrace();
//            return false;
//        } finally {
//            if (session != null) session.close();
//        }
//    }
}