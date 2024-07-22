/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RecipeSystem;

/**
 *
 * @author USER
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeDetailsDAO {
    
 public RecipeDetails fetchRecipeDetailsFromDB(Double recipeID) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        RecipeDetails recipeDetails = null;

        try {
            conn = Database.connectDB();

            String sql = "SELECT RecipeID, RecipeName, Description, CookingTime, CuisineID, MealTimeID " +
                         "FROM recipe " +
                         "WHERE RecipeID = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setDouble(1, recipeID);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Double recipeId = rs.getDouble("RecipeID");
                String recipeName = rs.getString("RecipeName");
                String description = rs.getString("Description");
                String cookingTime = rs.getString("CookingTime");
                int cuisineId = rs.getInt("CuisineID");
                int mealTimeId = rs.getInt("MealTimeID");

                recipeDetails = new RecipeDetails(recipeId, recipeName, description, cookingTime, cuisineId, mealTimeId);
            } else {
                System.out.println("No recipe found with RecipeID: " + recipeID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return recipeDetails;
    }
 
}



