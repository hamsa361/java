package com.pantrytoplate.service;

import com.pantrytoplate.service.MacroOptimizerService.IngredientItem;
import com.pantrytoplate.service.MacroOptimizerService.MacroTargetGoal;
import com.pantrytoplate.service.MacroOptimizerService.OptimizationResult;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MacroOptimizerServiceTest {

    @Test
    public void testAlgorithmHitsTargetMacrosWithoutExceedingCalorieLimit() {
        MacroOptimizerService service = new MacroOptimizerService();

        List<IngredientItem> samplePantry = Arrays.asList(
            new IngredientItem("Chicken", 165.0, 31.0, 0.0, 3.6, 500.0, LocalDate.now().plusDays(1)),
            new IngredientItem("Paneer", 265.0, 18.0, 1.2, 20.0, 400.0, LocalDate.now().plusDays(2)),
            new IngredientItem("Eggs", 155.0, 13.0, 1.1, 11.0, 350.0, LocalDate.now().plusDays(3)),
            new IngredientItem("Oats", 389.0, 16.9, 66.3, 6.9, 500.0, LocalDate.now().plusDays(10))
        );

        double maxCalorieLimit = 1800.0;
        double targetProtein = 140.0;
        MacroTargetGoal goal = new MacroTargetGoal(maxCalorieLimit, targetProtein);

        OptimizationResult result = service.optimizeMeal(samplePantry, goal);

        // 1. Verify total calories strictly do not exceed the calorie limit
        assertTrue(result.totalCaloriesAchieved <= maxCalorieLimit + 0.01,
                "Total calories (" + result.totalCaloriesAchieved + ") exceeded limit (" + maxCalorieLimit + ")");

        // 2. Verify target protein macro threshold is met
        assertTrue(result.isTargetMet,
                "Target protein was not met. Achieved: " + result.totalProteinAchieved + "g");

        // 3. Verify expiring items were selected into recommended portions
        assertFalse(result.recommendedPortions.isEmpty(), "Recommended portions should not be empty");
        assertTrue(result.recommendedPortions.containsKey("Chicken"),
                "Highest expiry priority item (Chicken) should be included");
    }
}