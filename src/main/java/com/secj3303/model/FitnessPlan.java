package com.secj3303.model;

import javax.persistence.*;

@Entity
@Table(name = "FITNESS_PLAN")
public class FitnessPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private int planId;

    @Column(name = "program_id")
    private int programId;

    @Column(name = "plan_name", nullable = false)
    private String planName; // e.g., "Weight Loss - Beginner"

    @Column(name = "description", length = 1000)
    private String description; // Detailed workout routine

    @Column(name = "difficulty_level")
    private String difficultyLevel; // Beginner, Intermediate, Advanced

    @Column(name = "duration_weeks")
    private int durationWeeks;

    // Constructors
    public FitnessPlan() {}

    public FitnessPlan(String planName, String description, int program_id, String difficultyLevel, int durationWeeks) {
        this.planName = planName;
        this.description = description;
        this.programId = program_id;
        this.difficultyLevel = difficultyLevel;
        this.durationWeeks = durationWeeks;
    }

    // Getters and Setters
    public int getPlanId() { return planId; }
    public void setPlanId(int planId) { this.planId = planId; }

    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }

    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public int getDurationWeeks() { return durationWeeks; }
    public void setDurationWeeks(int durationWeeks) { this.durationWeeks = durationWeeks; }
}