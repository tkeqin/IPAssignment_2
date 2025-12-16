package com.secj3303.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private int enrollment_id;

    // Member who enrolls //link to the table Person
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Person member;

    // Program enrolled //link to the table Program
    @ManyToOne
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(name = "enroll_date")
    private LocalDate enrollDate;

    @Column(name = "status")
    private String status; // ACTIVE, COMPLETED, CANCELLED

    // ===== Getters & Setters =====

    public int getId() {
        return enrollment_id;
    }

    public void setId(int id) {
        this.enrollment_id = id;
    }

    public Person getMember() {
        return member;
    }

    public void setMember(Person member) {
        this.member = member;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
