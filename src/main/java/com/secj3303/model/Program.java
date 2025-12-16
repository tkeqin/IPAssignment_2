package com.secj3303.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="program")
public class Program {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "program_id") //changed the attribute name from 'id' to 'program_id' to avoid confusion
    private Integer programId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name="duration_weeks")
    private int durationWeeks;

    @Column(name="monthly_fee")
    private Double monthlyFee;

    public Program(){}

    // Getters and Setters
    public Integer getId() {
        return programId;
    }

    public void setId(Integer programId) {
        this.programId = programId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationWeeks() {
        return durationWeeks;
    }

    public void setDurationWeeks(int durationWeeks) {
        this.durationWeeks = durationWeeks;
    }

    public Double getMonthlyFee() {
        return monthlyFee;
    }   

    public void setMonthlyFee(Double monthlyFee) {
        this.monthlyFee = monthlyFee;
    }
}