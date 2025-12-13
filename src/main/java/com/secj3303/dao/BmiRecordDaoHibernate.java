package com.secj3303.dao;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.secj3303.model.BmiRecord;

@Repository
@Transactional
public class BmiRecordDaoHibernate implements BmiRecordDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(BmiRecord bmiRecord) {
        getCurrentSession().save(bmiRecord);
    }

    @Override
    public List<BmiRecord> findByMemberId(int memberId) {
        // HQL query to find all BMI records for a specific member, ordered by date
        return getCurrentSession().createQuery(
            "from BmiRecord br where br.member.personId = :memberId order by br.recordDate desc", BmiRecord.class)
            .setParameter("memberId", memberId)
            .list();
    }

    @Override
    public BmiRecord findLatestByMemberId(int memberId) {
        // HQL query to get the most recent record
        return getCurrentSession().createQuery(
            "from BmiRecord br where br.member.personId = :memberId order by br.recordDate desc", BmiRecord.class)
            .setParameter("memberId", memberId)
            .setMaxResults(1)
            .uniqueResult();
    }
}
