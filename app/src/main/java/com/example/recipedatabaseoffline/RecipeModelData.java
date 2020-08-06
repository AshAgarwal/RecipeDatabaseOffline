package com.example.recipedatabaseoffline;

public class RecipeModelData {
    private String cuisineType;
    private String foodName;
    private String foodDescription;
    private String prepTime;
    private String cookTime;
    private String totalTime;
    private String recipeYield;
    private String mealType;
    private String foodCategory;

    public RecipeModelData() {}

    public RecipeModelData(String cuisineType, String foodName, String foodDescription, String prepTime, String cookTime, String totalTime, String recipeYield, String mealType) {
        this.cuisineType = cuisineType;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.totalTime = totalTime;
        this.recipeYield = recipeYield;
        this.mealType = mealType;
    }

    public String getFoodCategory() {
        return foodCategory;
    }

    public void setFoodCategory(String foodCategory) {
        this.foodCategory = foodCategory;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getRecipeYield() {
        return recipeYield;
    }

    public void setRecipeYield(String recipeYield) {
        this.recipeYield = recipeYield;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}
