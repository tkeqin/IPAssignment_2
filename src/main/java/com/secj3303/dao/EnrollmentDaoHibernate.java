package com.secj3303.dao;

import com.secj3303.model.Enrollment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Transactional
@Repository
public class EnrollmentDaoHibernate implements EnrollmentDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Enrollment enrollment) {
        getCurrentSession().save(enrollment);
    }

    @Override
    public void update(Enrollment enrollment) {
        getCurrentSession().update(enrollment);
    }

    @Override
    public void delete(Enrollment enrollment) {
        getCurrentSession().delete(enrollment);
    }

    @Override
    public Enrollment findById(int id) {
        return getCurrentSession().get(Enrollment.class, id);
    }

    @Override
    public List<Enrollment> findAll() {
        return getCurrentSession()
                .createQuery("FROM Enrollment", Enrollment.class)
                .list();
    }

    @Override
    public Enrollment findByMemberAndProgram(int memberId, int programId) {
        return getCurrentSession()
                .createQuery(
                    "FROM Enrollment e " +
                    "WHERE e.member.id = :memberId " +
                    "AND e.program.id = :programId",
                    Enrollment.class
                )
                .setParameter("memberId", memberId)
                .setParameter("programId", programId)
                .uniqueResult();
    }

    @Override
    public List<Enrollment> findByMemberId(int memberId) {
        return getCurrentSession()
                .createQuery(
                    "FROM Enrollment e WHERE e.member.id = :memberId",
                    Enrollment.class
                )
                .setParameter("memberId", memberId)
                .list();
    }
}
