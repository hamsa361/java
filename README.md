#hema-(data layer)
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
