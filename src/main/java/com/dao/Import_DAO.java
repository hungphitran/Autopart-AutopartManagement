package com.dao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import com.entity.Blog;
import com.entity.Employee;
import com.entity.Import;

import org.hibernate.Query;


public class Import_DAO {
	@Autowired
    private final SessionFactory factory;

    public Import_DAO(SessionFactory factory) {
        this.factory = factory;
    }

    public List<Import> getAll() {
        Session session = null;
        try {
            session = factory.openSession();

            String hql = "SELECT i FROM Import i LEFT JOIN FETCH i.employee ORDER BY i.importDate DESC";
            Query query = session.createQuery(hql);
            
            List<Import> imports = (List<Import>) query.list();
            
            for (Import imp : imports) {
                if (imp.getEmployee() != null) {
                    imp.setEmployeeName(imp.getEmployee().getFullName());
                }
            }
            
            return imports;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }

    public Import getById(String importId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Import i WHERE i.importId = :importId";
            Query query = session.createQuery(hql);
            query.setParameter("importId", importId);
            return (Import) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) session.close();
        }
    }
    
    public List<Import> getImportByEmail(String employeeEmail) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Import i WHERE i.employeeEmail = :email";
            Query query = session.createQuery(hql);
            query.setParameter("email", employeeEmail);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        } finally {
            if (session != null) session.close();
        }
    }

    public boolean add(Import importEntity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            
            session.save(importEntity);
            
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

    public boolean update(Import importEntity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            
            session.update(importEntity);
            
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

//    public boolean delete(String importId) {
//        Session session = null;
//        Transaction transaction = null;
//        try {
//            session = factory.openSession();
//            transaction = session.beginTransaction();
//            String hql = "UPDATE Import i SET i.deleted = TRUE, i.deletedAt = GETDATE() WHERE i.importId = :importId";
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

    public boolean checkExistById(String importId) {
        Session session = null;
        try {
            session = factory.openSession();
            String hql = "FROM Import i WHERE i.importId = :importId";
            Query query = session.createQuery(hql);
            query.setParameter("importId", importId);
            return query.uniqueResult() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }


    public String generateNextImportId() {
        Session session = null;
        try {
            session = factory.openSession();
            LocalDate currentDate = LocalDate.now(); 
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
            String dateStr = currentDate.format(formatter); 

            String prefix = "IMP" + dateStr;

            String hql = "SELECT MAX(i.importId) FROM Import i WHERE i.importId LIKE :prefix";
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
            return "IMP" + dateStr + "001";
        } finally {
            if (session != null) session.close();
        }
    }
}