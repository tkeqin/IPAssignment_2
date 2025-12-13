package com.secj3303.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BMI_RECORD")
public class BmiRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bmi_id")
    private int bmiId;

    // ManyToOne relationship with the Person table (the Member)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Person member; // Hibernate requires a Person object here

    @Column(name = "height_cm")
    private double heightCm;

    @Column(name = "weight_kg")
    private double weightKg;

    @Column(name = "bmi_value")
    private double bmiValue;

    @Column(name = "record_date")
    private LocalDateTime recordDate;

    // Constructors (omitted for brevity)
    // Getters and Setters (essential)
    // ...
    // Example Getters/Setters:
    public int getBmiId() { return bmiId; }
    public void setBmiId(int bmiId) { this.bmiId = bmiId; }
    
    public Person getMember() { return member; }
    public void setMember(Person member) { this.member = member; }
    
    public double getHeightCm() { return heightCm; }
    public void setHeightCm(double heightCm) { this.heightCm = heightCm; }
    
    public double getWeightKg() { return weightKg; }
    public void setWeightKg(double weightKg) { this.weightKg = weightKg; }
    
    public double getBmiValue() { return bmiValue; }
    public void setBmiValue(double bmiValue) { this.bmiValue = bmiValue; }
    
    public LocalDateTime getRecordDate() { return recordDate; }
    public void setRecordDate(LocalDateTime recordDate) { this.recordDate = recordDate; }
}
