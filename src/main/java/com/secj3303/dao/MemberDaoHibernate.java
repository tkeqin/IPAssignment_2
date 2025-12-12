package com.secj3303.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secj3303.model.Member;

@Repository
public class MemberDaoHibernate implements MemberDao {

    @Autowired
    private SessionFactory sessionFactory;

    // Helper method to get the current session
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> findAll() {
        Query<Member> query = getCurrentSession().createQuery("from Member", Member.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Member findById(int id) {
        return getCurrentSession().get(Member.class, id);
    }

    /**
     * Inserts a new Person record and returns the generated ID.
     */
    @Override
    @Transactional
    public int insert(Member p) {
        // save() returns the Serializable ID of the persisted instance
        return (int) getCurrentSession().save(p);
    }

    /**
     * Updates an existing Person record.
     */
    @Override
    @Transactional
    public void update(Member p) {
        // merge() is generally safer than update() for detached entities
        getCurrentSession().merge(p); 
    }

    /**
     * Deletes a Person by ID. Returns 1 if deleted, 0 otherwise.
     */
    @Override
    @Transactional
    public int delete(int id) {
        Member memberToDelete = findById(id);
        if (memberToDelete != null) {
            getCurrentSession().delete(memberToDelete);
            return 1; // Indicate successful deletion
        }
        return 0; // Indicate no record was found/deleted
    }
    
    // --- Custom Methods (Kept from previous version, crucial for login) ---

    @Override
    @Transactional(readOnly = true)
    public Member findByUsername(String username) {
        String hql = "FROM Member m WHERE m.username = :uname";
        Query<Member> query = getCurrentSession().createQuery(hql, Member.class);
        query.setParameter("uname", username);
        
        List<Member> results = query.getResultList();
        
        return results.isEmpty() ? null : results.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> findAllMembers() {
        String hql = "FROM Member m WHERE m.role = 'member'";
        Query<Member> query = getCurrentSession().createQuery(hql, Member.class);
        return query.getResultList();
    }
}