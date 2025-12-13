package com.secj3303.dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.secj3303.model.*;

@Repository
@Transactional
public class TrainerDaoHibernate implements TrainerDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void savePlan(FitnessPlan plan) {
        getCurrentSession().saveOrUpdate(plan);
    }

    @Override
    public List<FitnessPlan> findAllPlans() {
        return getCurrentSession().createQuery("from FitnessPlan", FitnessPlan.class).list();
    }

    @Override
    public FitnessPlan findPlanById(int id) {
        return getCurrentSession().get(FitnessPlan.class, id);
    }

    @Override
    public void saveAssignment(PlanAssignment assignment) {
        getCurrentSession().saveOrUpdate(assignment);
    }

    @Override
    public PlanAssignment findAssignmentById(int id) {
        return getCurrentSession().get(PlanAssignment.class, id);
    }

    // --- NEW: Secure lookup for Member ---
    @Override
    public PlanAssignment findAssignmentByMemberAndId(int memberId, int assignmentId) {
        return getCurrentSession().createQuery(
            "from PlanAssignment pa where pa.assignmentId = :aid and pa.member.personId = :mid", PlanAssignment.class)
            .setParameter("aid", assignmentId)
            .setParameter("mid", memberId)
            .uniqueResult();
    }

    @Override
    public List<PlanAssignment> findAssignmentsByTrainer(int trainerId) {
        return getCurrentSession().createQuery(
            "from PlanAssignment pa where pa.trainer.personId = :tid order by pa.assignedDate desc", PlanAssignment.class)
            .setParameter("tid", trainerId)
            .list();
    }

    @Override
    public List<Person> findDistinctMembersWithAssignments(int trainerId) {
        return getCurrentSession().createQuery(
            "select distinct pa.member from PlanAssignment pa where pa.trainer.personId = :tid", Person.class)
            .setParameter("tid", trainerId)
            .list();
    }

    @Override
    public List<PlanAssignment> findAllAssignmentsByMemberId(int memberId) {
        return getCurrentSession().createQuery(
            "from PlanAssignment pa where pa.member.personId = :mid order by pa.assignedDate desc", PlanAssignment.class)
            .setParameter("mid", memberId)
            .list();
    }

    @Override
    public void saveSession(TrainingSession session) {
        getCurrentSession().saveOrUpdate(session);
    }

     @Override
    public List<TrainingSession> findSessionsByTrainer(int trainerId) {
        // UPDATED QUERY: Sort by sessionDate then startTime
        return getCurrentSession().createQuery(
            "from TrainingSession ts where ts.trainer.personId = :tid order by ts.sessionDate asc, ts.startTime asc", TrainingSession.class)
            .setParameter("tid", trainerId)
            .list();
    }

    @Override
    public TrainingSession findSessionById(int sessionId) {
        return getCurrentSession().get(TrainingSession.class, sessionId);
    }

    @Override
    public List<Person> findAllMembers() {
        return getCurrentSession().createQuery(
            "from Person p where p.role = 'member'", Person.class).list();
    }
}