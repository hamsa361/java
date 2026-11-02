package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    // Macro profiles per 100g
    private double calories;
    private double protein;
    private double carbs;
    private double fats;

    private LocalDate expiryDate;

    public Ingredient() {}

    public Ingredient(String name, double calories, double protein, double carbs, double fats, LocalDate expiryDate) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.expiryDate = expiryDate;
    }

    /**
     * Calculates an expiry priority score. 
     * Items expiring sooner get a higher score to prioritize usage in recipes.
     */
    public long getExpiryPriorityScore() {
        if (expiryDate == null) {
            return Long.MAX_VALUE; // Lowest priority if no expiry date
        }
        long daysUntilExpiry = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
        // Closer to expiry or already expired yields higher urgency score
        return Math.max(0, 100 - daysUntilExpiry);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }

    public double getCarbs() { return carbs; }
    public void setCarbs(double carbs) { this.carbs = carbs; }

    public double getFats() { return fats; }
    public void setFats(double fats) { this.fats = fats; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }
}
