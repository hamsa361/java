package com.pantrytoplate.controller;

import com.pantrytoplate.service.MacroOptimizerService;
import com.pantrytoplate.service.MacroOptimizerService.IngredientItem;
import com.pantrytoplate.service.MacroOptimizerService.MacroTargetGoal;
import com.pantrytoplate.service.MacroOptimizerService.OptimizationResult;
import com.pantrytoplate.service.PdfExportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
public class OptimizerController {

    private final MacroOptimizerService optimizerService = new MacroOptimizerService();
    private final PdfExportService pdfExportService = new PdfExportService();
    private OptimizationResult lastOptimizationResult;
    private MacroTargetGoal lastTargetGoal;

    @PostMapping("/optimize")
    public String handleFormSubmission(
            @RequestParam("targetCalories") double targetCalories,
            @RequestParam("targetProtein") double targetProtein,
            Model model) {

        List<IngredientItem> pantry = DashboardController.getDefaultPantry();
        MacroTargetGoal targetGoal = new MacroTargetGoal(targetCalories, targetProtein);

        OptimizationResult result = optimizerService.optimizeMeal(pantry, targetGoal);
        this.lastOptimizationResult = result;
        this.lastTargetGoal = targetGoal;

        model.addAttribute("result", result);
        model.addAttribute("targetGoal", targetGoal);
        model.addAttribute("recommendedPortions", result.recommendedPortions);
        model.addAttribute("totalCalories", result.totalCaloriesAchieved);
        model.addAttribute("totalProtein", result.totalProteinAchieved);
        model.addAttribute("isTargetMet", result.isTargetMet);

        return "dashboard";
    }

    @GetMapping("/download-recipe-card")
    public ResponseEntity<byte[]> downloadRecipeCard(
            @RequestParam(value = "targetCalories", defaultValue = "2000") double targetCalories,
            @RequestParam(value = "targetProtein", defaultValue = "140") double targetProtein) throws IOException {

        if (this.lastOptimizationResult == null) {
            this.lastTargetGoal = new MacroTargetGoal(targetCalories, targetProtein);
            this.lastOptimizationResult = optimizerService.optimizeMeal(
                    DashboardController.getDefaultPantry(), this.lastTargetGoal);
        }

        byte[] pdfBytes = pdfExportService.generateRecipeCardPdf(this.lastOptimizationResult, this.lastTargetGoal);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=optimized-recipe-card.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}