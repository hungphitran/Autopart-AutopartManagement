package com.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;
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
            String hql = (status == "History") 
            		? "FROM Order o WHERE o.status IN ('Cancelled', 'Completed')" 
            		: "FROM Order o WHERE o.status = :status";
            Query query = session.createQuery(hql);
            if (!status.equals("History")) {
                query.setParameter("status", status);
            }
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public List<Order> getOrderByEmail(String email){
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Order o WHERE o.userEmail = :email and deleted = false";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    @Transactional
    public boolean add(Order order) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            return true;
        } 
            catch (Exception e) {
            if (transaction != null) transaction.rollback();
            
            throw e;
        } 
            finally {
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
        } 
        catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw e;
        } 
        finally {
            if (session != null) session.close();
        }
    }

    public boolean delete(String orderId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE Order o SET o.status = 'Deleted', o.deletedAt = GETDATE(), o.deleted =TRUE WHERE o.orderId = :orderId";
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
            LocalDate currentDate = LocalDate.now(); 
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            String dateStr = currentDate.format(formatter); 

            String prefix = "ORD" + dateStr;

            String hql = "SELECT MAX(o.orderId) FROM Order o WHERE o.orderId LIKE :prefix";
            Query query = session.createQuery(hql);
            query.setParameter("prefix", prefix + "%");
            String maxId = (String) query.uniqueResult();

            if (maxId == null) {
                return prefix + "001"; 
            }

            int currentNum = Integer.parseInt(maxId.substring(prefix.length()));
            return String.format("%s%03d", prefix, currentNum + 1); 
        } catch (Exception e) {
            e.printStackTrace();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            String dateStr = currentDate.format(formatter);
            return "ORD" + dateStr + "001";
        } finally {
            if (session != null) session.close();
        }
    }
    
    public List<Order> getOrdersByDateRangeAndStatus(java.sql.Date startDate, java.sql.Date endDate, String status) {
        Session session = factory.openSession();
        String hql = "FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate AND o.status = :status";
        Query query = session.createQuery(hql);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("status", status);
        
        return query.list();
    }
}