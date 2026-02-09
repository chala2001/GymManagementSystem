package com.example.GymManagementBackend.Entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    private Double height;
    private Double weight;

    @Column(name = "fat_percentage")
    private Double fatPercentage;

    private Double tdee;

    @Column(name = "calories_per_day")
    private Double caloriesPerDay;

    @Column(columnDefinition = "TEXT")
    private String gymRoutine;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(nullable = false)
    private String role = "USER";


    // Many users can have one trainer
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    // -------- Constructors --------

    public User() {
    }

    public User(String username, String password, String name, LocalDate dateOfBirth) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    // -------- Getters and Setters --------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getFatPercentage() {
        return fatPercentage;
    }

    public void setFatPercentage(Double fatPercentage) {
        this.fatPercentage = fatPercentage;
    }

    public Double getTdee() {
        return tdee;
    }

    public void setTdee(Double tdee) {
        this.tdee = tdee;
    }

    public Double getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(Double caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public String getGymRoutine() {
        return gymRoutine;
    }

    public void setGymRoutine(String gymRoutine) {
        this.gymRoutine = gymRoutine;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }
}