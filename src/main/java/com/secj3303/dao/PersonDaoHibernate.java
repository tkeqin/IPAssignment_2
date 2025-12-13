package com.secj3303.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional; // Import if using Spring's tx

import com.secj3303.model.Person;

@Repository // Marks this as a Spring component for persistence
@Transactional // Ensures methods run within a database transaction
public class PersonDaoHibernate implements PersonDao {

    @Autowired
    private SessionFactory sessionFactory; // Injected from dispatcher-servlet.xml

    // Helper method to get the current Hibernate Session
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Person findById(int id) {
        return getCurrentSession().get(Person.class, id);
    }

    @Override
    public List<Person> findAll() {
        return getCurrentSession().createQuery("from Person", Person.class).list();
    }

    @Override
    public void save(Person person) {
        getCurrentSession().saveOrUpdate(person);
    }

    @Override
    public void delete(Person person) {
        getCurrentSession().delete(person);
    }
    
    /**
     * Finds a Person by username and password for authentication.
     */
    @Override
    public Person findByUsernameAndPassword(String username, String password) {
        try {
            return getCurrentSession().createQuery(
                    "from Person p where p.username = :user AND p.password = :pass", Person.class)
                    .setParameter("user", username)
                    .setParameter("pass", password)
                    .uniqueResult();
        } catch (Exception e) {
            // Log the error
            return null;
        }
    }
}