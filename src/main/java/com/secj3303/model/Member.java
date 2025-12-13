/**package com.secj3303.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// Note: In a real-world scenario, you might use a DTO for form input 
// and map it to the Entity to keep validation separate from the Entity class.

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Integer memberId; // Changed to Integer to allow for null checks (new entities)

    @Column(name = "username", unique = true, nullable = false)
    @NotEmpty(message = "Username is required.") // Adding validation for registration
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "Password is required.") // Adding validation for registration
    private String password;

    // Critical for the manual session-based role checking
    @Column(name = "role", nullable = false)
    private String role; // Stores "member", "trainer", or "admin"

    @Column(name = "name")
    @NotEmpty(message = "Name is required.")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    // --- BMI and Profile Fields integrated from the first Person class ---

    @Column(name = "year_of_birth")
    @NotNull(message = "Year of Birth is required.")
    @Min(value = 1900, message = "Please enter a valid Year of Birth.")
    @Max(value = 2024, message = "Please enter a valid Year of Birth.")
    private Integer yearOfBirth;

    @Column(name = "weight_kg")
    @NotNull(message = "Weight is required.")
    @DecimalMin(value = "0.1", inclusive = false, message = "Weight must be positive.")
    private Double weight; // in kg

    @Column(name = "height_m")
    @NotNull(message = "Height is required.")
    @DecimalMin(value = "0.1", inclusive = false, message = "Height must be positive.")
    private Double height; // in m

    @Column(name = "gender")
    @NotEmpty(message = "Gender is required.")
    private String gender;
    
    // Interests field is excluded as it wasn't in the DB schema and complicates the simple model.
    // If needed, it should be mapped to a separate table or stored as a String/JSON.

    // --- Constructors ---
    public Member() {
    }

    // Constructor for registration
    public Member(String username, String password, String role, String name, String email, String phoneNumber, 
                  Integer yearOfBirth, Double weight, Double height, String gender) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.yearOfBirth = yearOfBirth;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
    }

    // --- Business Logic Methods (BMI and Age) ---

    // Calculate Age
    @Transient // Tell Hibernate NOT to map this method result to a database column
    public int getAge() {
        if (this.yearOfBirth == null) return 0;
        return LocalDate.now().getYear() - this.yearOfBirth;
    }

    // Calculate BMI (Body Mass Index)
    @Transient // Tell Hibernate NOT to map this method result to a database column
    public double getBmi() {
        if (this.weight == null || this.height == null || this.height <= 0) {
            return 0.0;
        }
        // BMI Formula: weight (kg) / height (m)^2
        double bmi = this.weight / (this.height * this.height);
        return Math.round(bmi * 10.0) / 10.0; // Round to 1 decimal place
    }

    // Determine BMI Category
    @Transient // Tell Hibernate NOT to map this method result to a database column
    public String getCategory() {
        double bmi = getBmi();
        if (bmi == 0.0) {
            return "N/A";
        } else if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi < 25) {
            return "Normal weight";
        } else if (bmi < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    // --- Getters and Setters (REQUIRED by Hibernate and Thymeleaf) ---

    public Integer getMemberId() { return memberId; }
    public void setMemberId(Integer memberId) { this.memberId = memberId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    // BMI/Profile Getters and Setters
    public Integer getYearOfBirth() { return yearOfBirth; }
    public void setYearOfBirth(Integer yearOfBirth) { this.yearOfBirth = yearOfBirth; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
} **/