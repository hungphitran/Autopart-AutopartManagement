package com.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

import com.entity.Account;

public class Account_DAO {

    private final SessionFactory factory;

    public Account_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Account> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            // Sử dụng LEFT JOIN FETCH để lấy thông tin roleGroup cùng với Account
            String hql = "FROM Account a LEFT JOIN FETCH a.roleGroup WHERE a.status IN ('Active', 'Inactive')";
            Query query = session.createQuery(hql);
            List<Account> accounts = (List<Account>) query.list();

            // Gán roleName từ roleGroup nếu tồn tại
            for (Account account : accounts) {
                if (account.getRoleGroup() != null) {
                    account.setRoleName(account.getRoleGroup().getRoleGroupName());
                }
            }

            return accounts;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        } finally {
            if (session != null) session.close();
        }
    }

    public Account getByEmail(String email) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Account a LEFT JOIN FETCH a.roleGroup WHERE a.email = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", email);
            Account account = (Account) query.uniqueResult();

            // Gán roleName nếu roleGroup tồn tại
            if (account != null && account.getRoleGroup() != null) {
                account.setRoleName(account.getRoleGroup().getRoleGroupName()); 
            }

            return account;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Account account) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(account);
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

    public boolean update(Account account) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(account);
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
            String hql = "UPDATE Account a SET a.status = 'Deleted', a.deletedAt = GETDATE() WHERE a.email = :email";
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
            String hql = "FROM Account a WHERE a.email = :email";
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
    
    public boolean changeStatus(String email) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            
            String getStatusHql = "SELECT a.status FROM Account a WHERE a.email= :email";
            Query statusQuery = session.createQuery(getStatusHql);
            statusQuery.setParameter("email", email);
            String currentStatus = (String) statusQuery.uniqueResult();
            
            String newStatus = "Active".equals(currentStatus) ? "Inactive" : "Active";
            
            String updateHql = "UPDATE Account a SET a.status = :newStatus WHERE a.email = :email";
            Query updateQuery = session.createQuery(updateHql);
            updateQuery.setParameter("newStatus", newStatus);
            updateQuery.setParameter("email", email);
            
            int rowsAffected = updateQuery.executeUpdate();
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