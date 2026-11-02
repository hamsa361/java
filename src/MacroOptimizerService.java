package com.pantrytoplate.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class MacroOptimizerService {

    public static class IngredientItem {
        private String name;
        private double caloriesPer100g;
        private double proteinPer100g;
        private double carbsPer100g;
        private double fatPer100g;
        private double availableGrams;
        private LocalDate expiryDate;

        public IngredientItem(String name, double cal, double pro, double carb, double fat, double grams, LocalDate expiry) {
            this.name = name;
            this.caloriesPer100g = cal;
            this.proteinPer100g = pro;
            this.carbsPer100g = carb;
            this.fatPer100g = fat;
            this.availableGrams = grams;
            this.expiryDate = expiry;
        }

        public int getExpiryPriorityScore() {
            if (expiryDate == null) return 0;
            long daysUntilExpiry = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
            if (daysUntilExpiry <= 1) return 10;
            if (daysUntilExpiry <= 3) return 5;
            return 1;
        }

        public String getName() { return name; }
        public double getCaloriesPer100g() { return caloriesPer100g; }
        public double getProteinPer100g() { return proteinPer100g; }
        public double getAvailableGrams() { return availableGrams; }
    }

    public static class MacroTargetGoal {
        private double targetCalories;
        private double targetProtein;

        public MacroTargetGoal(double cal, double protein) {
            this.targetCalories = cal;
            this.targetProtein = protein;
        }

        public double getTargetCalories() { return targetCalories; }
        public double getTargetProtein() { return targetProtein; }
    }

    public static class OptimizationResult {
        public Map<String, Double> recommendedPortions = new HashMap<>();
        public double totalCaloriesAchieved = 0;
        public double totalProteinAchieved = 0;
        public boolean isTargetMet = false;
    }

    public OptimizationResult optimizeMeal(List<IngredientItem> pantry, MacroTargetGoal target) {
        OptimizationResult result = new OptimizationResult();

        pantry.sort((a, b) -> Integer.compare(b.getExpiryPriorityScore(), a.getExpiryPriorityScore()));

        double remainingCalories = target.getTargetCalories();

        for (IngredientItem item : pantry) {
            if (remainingCalories <= 0) break;

            double macroDensity = item.getProteinPer100g() / Math.max(item.getCaloriesPer100g(), 1.0);

            if (macroDensity > 0) {
                double maxGramsByCalories = (remainingCalories / item.getCaloriesPer100g()) * 100.0;
                double portionToUse = Math.min(item.getAvailableGrams(), maxGramsByCalories);

                if (portionToUse > 10) {
                    result.recommendedPortions.put(item.getName(), portionToUse);

                    double calsUsed = (portionToUse / 100.0) * item.getCaloriesPer100g();
                    double proteinGained = (portionToUse / 100.0) * item.getProteinPer100g();

                    remainingCalories -= calsUsed;
                    result.totalCaloriesAchieved += calsUsed;
                    result.totalProteinAchieved += proteinGained;
                }
            }
        }

        result.isTargetMet = result.totalProteinAchieved >= (target.getTargetProtein() * 0.85);
        return result;
    }
}