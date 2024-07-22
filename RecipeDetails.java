/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RecipeSystem;

/**
 *
 * @author USER
 */
public class RecipeDetails  {
    private Double RecipeID;
    private String RecipeName;
    private String Description;
    private String CookingTime;
    private int CuisineID;
    private int MealTimeID;

    public RecipeDetails(Double RecipeID, String RecipeName, String Description, String CookingTime, int CuisineID, int MealTimeID) {
        this.RecipeID = RecipeID;
        this.RecipeName = RecipeName;
        this.Description = Description;
        this.CookingTime = CookingTime;
        this.CuisineID = CuisineID;
        this.MealTimeID = MealTimeID;
    }

    public Double getRecipeID() {
        return RecipeID;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public String getDescription() {
        return Description;
    }

    public String getCookingTime() {
        return CookingTime;
    }

    public int getCuisineID() {
        return CuisineID;
    }

    public int getMealTimeID() {
        return MealTimeID;
    }

    // Setters for updating fields
    public void setRecipeName(String RecipeName) {
        this.RecipeName = RecipeName;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setCookingTime(String CookingTime) {
        this.CookingTime = CookingTime;
    }

    public void setCuisineID(int CuisineID) {
        this.CuisineID = CuisineID;
    }

    public void setMealTimeID(int MealTimeID) {
        this.MealTimeID = MealTimeID;
    }
}

