package com.dao;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.entity.Brand;
import com.entity.Cart;
import com.entity.Product;

public class Cart_DAO {

    private final SessionFactory factory;

    public Cart_DAO(SessionFactory factory) {
        this.factory = factory;
    }


    @Transactional
    public Cart getById(String cartId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT c FROM Cart c LEFT JOIN FETCH c.products WHERE c.cartId = :cartId";
            Query query = session.createQuery(hql);
            query.setParameter("cartId", cartId);
            return (Cart) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Cart cart) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();

            // Save the cart
            session.save(cart);
            session.flush();

            
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
    
    public boolean update(Cart cart) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.update(cart);
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

    
    
    public boolean checkExistById(String cartId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "SELECT 1 FROM Cart c WHERE c.cartId = :cartId";
            Query query = session.createQuery(hql);
            query.setParameter("cartId", cartId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    public String generateNextCartId() {
        try {
            // Lấy timestamp hiện tại
            long timestamp = System.currentTimeMillis();
            
            // Tạo chuỗi ngẫu nhiên sử dụng SecureRandom
            SecureRandom random = new SecureRandom();
            byte[] randomBytes = new byte[8]; // 8 bytes cho chuỗi ngẫu nhiên
            random.nextBytes(randomBytes);
            
            // Mã hóa chuỗi ngẫu nhiên thành Base64 và loại bỏ ký tự không mong muốn
            String randomString = Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(randomBytes)
                    .replaceAll("[^a-zA-Z0-9]", "")
                    .substring(0, 8); // Lấy 8 ký tự đầu
            
            // Tạo cartId với định dạng CART + timestamp + chuỗi ngẫu nhiên
            return "CART" + timestamp + randomString;
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về giá trị mặc định nếu có lỗi
            return "CART" + System.currentTimeMillis() + "ERROR";
        }
    }

    
}
