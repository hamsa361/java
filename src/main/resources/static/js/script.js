// INGREDIENT SEARCH

const searchInput = document.getElementById("ingredientSearch");

searchInput.addEventListener("input", function () {

    const searchValue = this.value.toLowerCase();

    document.querySelectorAll(".ingredient-column").forEach(card => {

        const name = card.querySelector(".ingredient-card")
                         .dataset.name.toLowerCase();

        card.style.display = name.includes(searchValue) ? "" : "none";

    });

});


// INGREDIENT FILTERS

const filterButtons = document.querySelectorAll(".filter-btn");

filterButtons.forEach(button => {

    button.addEventListener("click", function () {

        filterButtons.forEach(btn => btn.classList.remove("active"));

        this.classList.add("active");

        const category = this.dataset.filter;

        document.querySelectorAll(".ingredient-column").forEach(card => {

            const name = card.querySelector(".ingredient-card")
                             .dataset.name.toLowerCase();

            let show = true;

            if (category === "protein") {
                show = ["chicken", "paneer", "eggs"].includes(name);
            }

            if (category === "grains") {
                show = ["oats", "rice"].includes(name);
            }

            if (category === "other") {
                show = name === "peanut butter";
            }

            card.style.display = show ? "" : "none";

        });

    });

});


// QUANTITY SLIDERS

document.querySelectorAll(".quantity-slider").forEach(slider => {

    slider.addEventListener("input", function () {

        const value = this.value;

        const quantity = this.closest(".ingredient-details")
                              .querySelector(".quantity-value");

        quantity.textContent = value + " g";

    });

});


// MEAL STRATEGY

const mealOptions = document.querySelectorAll(".meal-option");

mealOptions.forEach(option => {

    option.addEventListener("click", function () {

        mealOptions.forEach(item => item.classList.remove("active"));

        this.classList.add("active");

    });

});


// FORM VALIDATION

document.getElementById("optimizerForm")
    .addEventListener("submit", function (event) {

        const calories = Number(document.getElementById("calories").value);

        const protein = Number(document.getElementById("protein").value);

        if (calories <= 0 || protein <= 0) {

            event.preventDefault();

            alert("Please enter valid calorie and protein targets.");

        }

    });


// DIETARY PREFERENCE CONFLICT WARNING

document.getElementById("optimizerForm")
    .addEventListener("submit", function (event) {

        const vegan = document.getElementById("vegan").checked;

        const vegetarian = document.getElementById("vegetarian").checked;

        if (vegan && vegetarian) {

            // Vegan is already a stricter vegetarian preference.
            // Both selections are allowed.

        }

    });