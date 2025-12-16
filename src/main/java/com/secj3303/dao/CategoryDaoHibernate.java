package com.secj3303.dao;

import com.secj3303.model.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@Repository
public class CategoryDaoHibernate implements CategoryDao {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Open a new Hibernate session (manual session management).
     */
    private Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    public List<Category> findAll() {
        Session session = openSession();
        Transaction tx = session.beginTransaction();
        List<Category> categories;

        try {
            categories = session.createQuery("from Category", Category.class).list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        return categories;
    }

    @Override
    public Category findById(Integer id) {
        Session session = openSession();
        Transaction tx = session.beginTransaction();
        Category category;

        try {
            category = session.get(Category.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        return category;
    }

    @Override
    public void save(Category category) {
        Session session = openSession();
        Transaction tx = session.beginTransaction();

        try {
            session.saveOrUpdate(category);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public int delete(Integer id) {
        Session session = openSession();
        Transaction tx = session.beginTransaction();
        int result = 0;

        try {
            Category category = session.get(Category.class, id);
            if (category != null) {
                session.delete(category);
                result = 1;
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        return result;
    }
}
