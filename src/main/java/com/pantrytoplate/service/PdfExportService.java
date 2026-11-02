package com.pantrytoplate.service;

import com.pantrytoplate.service.MacroOptimizerService.MacroTargetGoal;
import com.pantrytoplate.service.MacroOptimizerService.OptimizationResult;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class PdfExportService {

    public byte[] generateRecipeCardPdf(OptimizationResult result, MacroTargetGoal target) throws IOException {
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                // Header
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 18);
                content.newLineAtOffset(50, 730);
                content.showText("PantryToPlate - Optimized Meal Recipe Card");
                content.endText();

                // Target vs Achieved Summary
                content.beginText();
                content.setFont(PDType1Font.HELVETICA, 12);
                content.newLineAtOffset(50, 695);
                content.showText(String.format("Target Goal: %.0f kcal | %.1f g Protein",
                        target.getTargetCalories(), target.getTargetProtein()));
                content.endText();

                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 12);
                content.newLineAtOffset(50, 675);
                content.showText(String.format("Achieved Macros: %.1f kcal | %.1f g Protein | Target Met: %s",
                        result.totalCaloriesAchieved,
                        result.totalProteinAchieved,
                        result.isTargetMet ? "YES" : "NO"));
                content.endText();

                // Recommended Portions List
                int yPosition = 635;
                content.beginText();
                content.setFont(PDType1Font.HELVETICA_BOLD, 14);
                content.newLineAtOffset(50, yPosition);
                content.showText("Recommended Ingredient Portions (Prioritized by Expiry):");
                content.endText();

                for (Map.Entry<String, Double> entry : result.recommendedPortions.entrySet()) {
                    yPosition -= 22;
                    content.beginText();
                    content.setFont(PDType1Font.HELVETICA, 12);
                    content.newLineAtOffset(65, yPosition);
                    content.showText(String.format("- %s: %.1f grams", entry.getKey(), entry.getValue()));
                    content.endText();
                }
            }

            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }
}