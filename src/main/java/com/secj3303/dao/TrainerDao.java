package com.secj3303.dao;

import java.util.List;
import com.secj3303.model.*;

public interface TrainerDao {
    // Fitness Plan Operations
    void savePlan(FitnessPlan plan);
    List<FitnessPlan> findAllPlans();
    FitnessPlan findPlanById(int id);

    // Plan Assignment Operations
    void saveAssignment(PlanAssignment assignment);
    PlanAssignment findAssignmentById(int id);
    
    // NEW: Secure lookup for Member updates (prevents updating others' plans)
    PlanAssignment findAssignmentByMemberAndId(int memberId, int assignmentId);

    List<PlanAssignment> findAssignmentsByTrainer(int trainerId);
    
    // Find unique members assigned to a trainer
    List<Person> findDistinctMembersWithAssignments(int trainerId);
    
    // Find ALL assignments for a member (History)
    List<PlanAssignment> findAllAssignmentsByMemberId(int memberId);
    
    // Session Operations
    void saveSession(TrainingSession session);
    List<TrainingSession> findSessionsByTrainer(int trainerId);
    TrainingSession findSessionById(int sessionId); 
    
    // Helper to get Members
    List<Person> findAllMembers();
}