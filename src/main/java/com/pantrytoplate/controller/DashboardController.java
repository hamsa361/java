package com.pantrytoplate.controller;

import com.pantrytoplate.service.MacroOptimizerService.IngredientItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
public class DashboardController {

    @GetMapping({"/", "/dashboard"})
    public String showDashboard(Model model) {
        model.addAttribute("pantryItems", getDefaultPantry());
        model.addAttribute("defaultCalories", 2000.0);
        model.addAttribute("defaultProtein", 140.0);
        return "dashboard";
    }

    public static List<IngredientItem> getDefaultPantry() {
        return Arrays.asList(
            new IngredientItem("Chicken", 165.0, 31.0, 0.0, 3.6, 500.0, LocalDate.now().plusDays(1)),
            new IngredientItem("Paneer", 265.0, 18.0, 1.2, 20.0, 400.0, LocalDate.now().plusDays(2)),
            new IngredientItem("Eggs", 155.0, 13.0, 1.1, 11.0, 350.0, LocalDate.now().plusDays(3)),
            new IngredientItem("Oats", 389.0, 16.9, 66.3, 6.9, 500.0, LocalDate.now().plusDays(10)),
            new IngredientItem("Rice", 130.0, 2.7, 28.0, 0.3, 600.0, LocalDate.now().plusDays(15)),
            new IngredientItem("Peanut Butter", 588.0, 25.0, 20.0, 50.0, 250.0, LocalDate.now().plusDays(20))
        );
    }
}