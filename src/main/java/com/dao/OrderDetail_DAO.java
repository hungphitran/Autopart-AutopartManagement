package com.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import com.entity.OrderDetail;

public class OrderDetail_DAO {

    private final SessionFactory factory;

    public OrderDetail_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<OrderDetail> getAllByOrderId(String orderId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM OrderDetail od WHERE od.orderId = :orderId";
            Query query = session.createQuery(hql);
            query.setParameter("orderId", orderId);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(OrderDetail orderDetail) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(orderDetail);
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

    public boolean update(OrderDetail orderDetail) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(orderDetail);
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


    public boolean checkExistById(String orderId, String productId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT 1 FROM OrderDetail od WHERE od.orderId = :orderId AND od.productId = :productId";
            Query query = session.createQuery(hql);
            query.setParameter("orderId", orderId);
            query.setParameter("productId", productId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public boolean deleteByOrderId(String orderId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "DELETE FROM OrderDetail od WHERE od.orderId = :orderId";
            Query query = session.createQuery(hql);
            query.setParameter("orderId", orderId);
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
    
    public boolean delete(String orderId, String productId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "DELETE FROM OrderDetail od WHERE od.orderId = :orderId AND od.productId = :productId";
            Query query = session.createQuery(hql);
            query.setParameter("orderId", orderId);
            query.setParameter("productId", productId);
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
}
