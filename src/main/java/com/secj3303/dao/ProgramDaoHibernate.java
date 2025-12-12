package com.secj3303.dao;

import com.secj3303.model.Program;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@Repository
public class ProgramDaoHibernate implements ProgramDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.getCurrentSession();
    }

    
    @Override
    public List<Program> findAll() {
        // Implementation using Hibernate to fetch all Program records
        Session session = openSession();
        List<Program> programs = session
            .createQuery("from Program", Program.class)
            .list();

        session.close();
        return programs;
    }

    @Override
    public Program findById(Integer id) {
        // Implementation using Hibernate to fetch a Program by its ID
        Session session = openSession();
        Program program = session.get(Program.class, id);
        session.close();
        return program;
    }

    @Override
    public void save(Program program) {
        // Implementation using Hibernate to save a Person record
        Session session = openSession();
        Transaction tx = session.beginTransaction();

        try {
            session.saveOrUpdate(program);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public int delete(Integer id) {
        // Implementation using Hibernate to delete a Program by its ID
        Session session = openSession();
        Transaction tx = session.beginTransaction();
        try {
            Program program = session.get(Program.class, id);
            if (program != null) {
                session.delete(program);
                tx.commit();
                return 1; // Indicate successful deletion
            } else {
                tx.rollback();
                return 0; // Indicate no record found to delete
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        } finally {
            session.close();
        }
 
    }
    
}