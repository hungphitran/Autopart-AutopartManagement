package com.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Brand;
import com.entity.Cart;
import com.entity.Chat;
import com.entity.Product;

public class Chat_DAO {

    private final SessionFactory factory;

    public Chat_DAO(SessionFactory factory) {
        this.factory = factory;
    }


    @Transactional
    public Chat getById(String chatRoomId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT c FROM Chat WHERE c.status ='Active'";
            Query query = session.createQuery(hql);
            query.setParameter("chatRoomId", chatRoomId);
            return (Chat) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Chat chat) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            // Save the cart
            session.save(chat);
            session.flush();
            
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
    
    public boolean update(Chat chat) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(chat);
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

    public boolean delete(String chatRoomId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE Cart c SET c.status = 'Deleted' WHERE c.chatRoomId = :chatRoomId";
            Query query = session.createQuery(hql);
            query.setParameter("chatRoomId", chatRoomId);
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
    
    public boolean checkExistById(String chatRoomId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT 1 FROM Chat c WHERE c.chatRoomId = :chatRoomId AND status ='Active'";
            Query query = session.createQuery(hql);
            query.setParameter("chatRoomId", chatRoomId);
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
            String hql = "SELECT MAX(c.chatRoomId) FROM Chat c WHERE c.chatRoomId LIKE 'CHAT%'";
            Query query = session.createQuery(hql);
            String maxId = (String) query.uniqueResult();
            if (maxId == null) {
                return "CHAT001";
            }
            int currentNum = Integer.parseInt(maxId.substring(4));
            return String.format("CHAT%03d", currentNum + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "CHAT001";
        } finally {
            if (session != null) session.close();
        }
    }
    
}
