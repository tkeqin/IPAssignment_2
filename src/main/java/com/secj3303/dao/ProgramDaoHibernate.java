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

    /**
     * Open a new Hibernate session (manual session management).
     */
    private Session openSession() {
        return sessionFactory.openSession(); // use openSession() for pure Hibernate
    }

    @Override
    public List<Program> findAll() {
        Session session = openSession();
        Transaction tx = session.beginTransaction();
        List<Program> programs;

        try {
            programs = session.createQuery("from Program", Program.class).list();
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        return programs;
    }

    @Override
    public Program findById(Integer id) {
        Session session = openSession();
        Transaction tx = session.beginTransaction();
        Program program;

        try {
            program = session.get(Program.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        return program;
    }

    @Override
    public void save(Program program) {
        Session session = openSession();
        Transaction tx = session.beginTransaction();

        try {
            session.saveOrUpdate(program);
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

        try {
            Program program = session.get(Program.class, id);
            if (program != null) {
                session.delete(program);
                tx.commit();
                return 1; // deletion successful
            } else {
                tx.rollback();
                return 0; // no record found
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
