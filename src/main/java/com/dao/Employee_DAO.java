package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import com.entity.Employee;

public class Employee_DAO {

    private final SessionFactory factory;

    public Employee_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Employee> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Employee e WHERE e.status = 'Active'";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (session != null) session.close();
        }
    }

    public Employee getByEmail(String email) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Employee e  WHERE e.email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            return (Employee) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Employee employee) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            
            session.save(employee);
            
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

    public boolean update(Employee employee) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            
            session.update(employee);
            
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

    public boolean delete(String email) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE Account a SET a.status = 'Deleted', a.deletedAt = current_timestamp() WHERE a.email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
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

    public boolean checkExistByEmail(String email) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Employee e WHERE e.email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
}