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
import com.entity.Discount;
import com.entity.Product;

public class Discount_DAO {

    private final SessionFactory factory;

    public Discount_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Discount> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Discount d WHERE d.status IN ('Active', 'Inactive')";
            Query query = session.createQuery(hql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    @Transactional
    public Discount getById(String discountId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Discount d WHERE d.discountId = :discountId";
            Query query = session.createQuery(hql);
            query.setParameter("discountId", discountId);
            return (Discount) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    
    @Transactional
    public List<Discount> getByCustomer(String customerEmail) {
        Session session = null;
        try {
            session = factory.openSession();
            String sql = "SELECT d.* FROM [dbo].[Discount] d " +
                    "LEFT JOIN [dbo].[UsedDiscount] ud " +
                    "ON d.discountId = ud.discountId AND ud.email = :email " +
                    "WHERE ud.discountId IS NULL " +
                    "AND d.status = 'Active' " +
                    "AND d.deleted = 0"+
                    "AND d.usageLimit > 0";
        Query query = session.createSQLQuery(sql).addEntity(Discount.class);
        query.setParameter("email", customerEmail);
        List<Discount> result = query.list();
        return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    
    
    @Transactional
    public boolean discountUsed(String customerEmail, String discountId) {
        Session session = null;
        try {
            session = factory.openSession();
            String sql = "INSERT INTO [dbo].[UsedDiscount] ( discountId, email) " +
                    "VALUES ( :discountId, :email)";
        Query query = session.createSQLQuery(sql);
        query.setParameter("discountId", discountId);
        query.setParameter("email", customerEmail);
        int rowsAffected = query.executeUpdate();
        return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }
    
    @Transactional
    public boolean deleteDiscountUsed(String discountId, String email) {
        Session session = null;
        try {
            session = factory.openSession();
            String sql = "DELETE FROM [dbo].[UsedDiscount] " +
                        "WHERE discountId = :discountId AND email = :email";
            Query query = session.createSQLQuery(sql);
            query.setParameter("discountId", discountId);
            query.setParameter("email", email);
            int rowsAffected = query.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    
    @Transactional
    public boolean updateDiscountUsed(String oldDiscountId, String email, String newDiscountId) {
        Session session = null;
        try {
            session = factory.openSession();
            String sql = "UPDATE [dbo].[UsedDiscount] SET discountId = :newDiscountId " +
                        "WHERE discountId = :discountId AND email = :email";
            Query query = session.createSQLQuery(sql);
            query.setParameter("newDiscountId", newDiscountId);
            query.setParameter("discountId", oldDiscountId);
            query.setParameter("email", email);
            int rowsAffected = query.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    
    public boolean changeStatus(String discountId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            
            String getStatusHql = "SELECT d.status FROM Discount d WHERE d.discountId = :discountId";
            Query statusQuery = session.createQuery(getStatusHql);
            statusQuery.setParameter("discountId", discountId);
            String currentStatus = (String) statusQuery.uniqueResult();
            
            String newStatus = "Active".equals(currentStatus) ? "Inactive" : "Active";
            
            String updateHql = "UPDATE Discount d SET d.status = :newStatus WHERE d.discountId = :discountId";
            Query updateQuery = session.createQuery(updateHql);
            updateQuery.setParameter("newStatus", newStatus);
            updateQuery.setParameter("discountId", discountId);
            
            int rowsAffected = updateQuery.executeUpdate();
            transaction.commit();
            return rowsAffected > 0;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Discount discount) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            // Save the cart
            session.save(discount);

            
//            // For ProductsInCart, use SQL with createSQLQuery()
//            for (Map.Entry<Product, Integer> entry : cart.getProducts().entrySet()) {
//                String sql = "INSERT INTO ProductsInCart (cartId, productId, amount) VALUES (?, ?, ?)";
//                session.createSQLQuery(sql)
//                      .setParameter(0, cart.getCartId())
//                      .setParameter(1, entry.getKey().getProductId())
//                      .setParameter(2, entry.getValue())
//                      .executeUpdate();
//            }
//
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public boolean update(Discount discount) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(discount);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean delete(String discountId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            String hql = "UPDATE Discount d SET d.status = 'Deleted' WHERE d.discountId = :discountId";
            Query query = session.createQuery(hql);
            query.setParameter("discountId", discountId);
            int rowsAffected = query.executeUpdate();
            transaction.commit();
            return rowsAffected > 0;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public boolean checkExistById(String discountId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT 1 FROM Discount d WHERE d.discountId = :discountId AND status ='Active'";
            Query query = session.createQuery(hql);
            query.setParameter("discountId", discountId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    public String generateNextDiscountId() {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT MAX(d.discountId) FROM Discount d WHERE d.discountId LIKE 'DIS%'";
            Query query = session.createQuery(hql);
            String maxId = (String) query.uniqueResult();
            if (maxId == null) {
                return "DIST001";
            }
            int currentNum = Integer.parseInt(maxId.substring(4));
            return String.format("DIS%03d", currentNum + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "DIS001";
        } finally {
            if (session != null) session.close();
        }
    }
    
}

