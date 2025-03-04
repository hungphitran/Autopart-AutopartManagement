package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import com.entity.Order;

public class Order_DAO {

    private final SessionFactory factory;

    public Order_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Order> getOrderByStatus(String status) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Order o WHERE o.status = :status";
            Query query = session.createQuery(hql);
            query.setParameter("status", status);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) session.close();
        }
    }

    public Order getById(String orderId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Order o WHERE o.orderId = :orderId";
            Query query = session.createQuery(hql);
            query.setParameter("orderId", orderId);
            return (Order) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Order order) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(order);
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

    public boolean update(Order order) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(order);
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

    public boolean delete(String orderId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE Order o SET o.status = 'Deleted', o.deletedAt = current_timestamp() WHERE o.orderId = :orderId";
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

    public boolean checkExistById(String orderId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT 1 FROM Order o WHERE o.orderId = :orderId";
            Query query = session.createQuery(hql);
            query.setParameter("orderId", orderId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    public String generateNextOrderId() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT MAX(o.orderId) FROM Order o WHERE o.orderId LIKE 'ORDER%'";
            Query query = session.createQuery(hql);
            String maxId = (String) query.uniqueResult();
            if (maxId == null) {
                return "ORDER001";
            }
            int currentNum = Integer.parseInt(maxId.substring(5));
            return String.format("ORDER%03d", currentNum + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "ORDER001";
        } finally {
            if (session != null) session.close();
        }
    }
}