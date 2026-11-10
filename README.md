# Hema: (data layer)
Project Title: Database and Domain Models Data Layer

Overview
This module implements the data layer for Team Member 1, focusing on structuring how data is stored, modeled, and retrieved within the application.

Core Java Domain Classes
The Ingredient class represents pantry items, tracking macro profiles including calories, protein, carbs, and fats per 100g, along with an expiry date and a built-in expiry priority scoring method that gives higher urgency to items expiring sooner.
The MacroTarget class stores nutritional goals such as target calories, protein, carbs, and fats.
The RecipeResult class stores generated recipe details and their corresponding aggregated nutritional totals.

Spring Data JPA Repositories
The repository interfaces IngredientRepository, MacroTargetRepository, and RecipeResultRepository handle database CRUD operations seamlessly using Spring Data JPA.

Database Prepopulation
The application automatically seeds the database on startup with a default pantry catalog containing Eggs, Paneer, Chicken, Oats, Rice, and Peanut Butter complete with standard macronutrient profiles.

Database Configuration
An embedded H2 database is configured via application properties with an enabled H2 console for easy data inspection, along with automatic schema generation.


# Rishita: (algorithm-service-layer)
Project Title: Macro Optimization & Expiry Prioritization Engine

Overview
This module implements the core business logic engine for Team Member 2, focusing on optimizing ingredient selection, hitting macro targets, and prioritizing expiring food items.

Core Optimization Logic
The MacroOptimizerService processes pantry ingredients to build balanced meal portions based on daily calorie and protein goals.
Uses a greedy optimization strategy to calculate protein density per calorie while strictly respecting maximum available pantry portion sizes.

Expiry Heuristic Engine
Includes dynamic expiry scoring that assigns high urgency multiplier scores to ingredients expiring soonest (10x priority for items expiring within 1 day, 5x for within 3 days).
Ensures pantry ingredients nearing expiration are prioritized first to reduce household food waste.

Data Structures & Results
Internal data structures handle seamless calculation processing between pantry inputs and final recommendations.
Returns an OptimizationResult object containing recommended ingredient portion weights (in grams), total achieved calories, total achieved protein, and target completion status.


# Lucky: Controllers, Services & PDF Export (Backend & Utilities)

## Overview
This module connects the web interface form inputs to the backend meal optimization algorithm, handles user session states, generates downloadable PDF recipe cards using Apache PDFBox, and verifies correctness through unit testing.

## Core Components
* **`DashboardController`**: Renders the main dashboard (`/`, `/dashboard`) and initializes default pantry ingredient items (Chicken, Paneer, Eggs, Oats, Rice, and Peanut Butter).
* **`OptimizerController`**: Handles form submissions (`/optimize`), passes calorie and protein targets into `MacroOptimizerService`, manages optimization results, and exposes the PDF download endpoint (`/download-recipe-card`).
* **`PdfExportService`**: Integrates **Apache PDFBox** to format and generate a clean, structured PDF report containing target vs. achieved macronutrient summaries and prioritized ingredient portions.
* **`MacroOptimizerServiceTest`**: A JUnit test suite verifying that the greedy optimization algorithm strictly respects daily calorie caps while hitting target protein thresholds.
