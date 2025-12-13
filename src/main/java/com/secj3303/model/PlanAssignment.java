package com.secj3303.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "PLAN_ASSIGNMENT")
public class PlanAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private int assignmentId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Person member;

    @ManyToOne
    @JoinColumn(name = "plan_id", nullable = false)
    private FitnessPlan fitnessPlan;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Person trainer;

    @Column(name = "assigned_date")
    private LocalDate assignedDate;

    @Column(name = "status")
    private String status; // Active, Completed, Dropped

    // Getters and Setters
    public int getAssignmentId() { return assignmentId; }
    public void setAssignmentId(int assignmentId) { this.assignmentId = assignmentId; }

    public Person getMember() { return member; }
    public void setMember(Person member) { this.member = member; }

    public FitnessPlan getFitnessPlan() { return fitnessPlan; }
    public void setFitnessPlan(FitnessPlan fitnessPlan) { this.fitnessPlan = fitnessPlan; }

    public Person getTrainer() { return trainer; }
    public void setTrainer(Person trainer) { this.trainer = trainer; }

    public LocalDate getAssignedDate() { return assignedDate; }
    public void setAssignedDate(LocalDate assignedDate) { this.assignedDate = assignedDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}