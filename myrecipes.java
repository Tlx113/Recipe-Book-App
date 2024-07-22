/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RecipeSystem;

import javafx.collections.ObservableList;

/**
 *
 * @author USER
 */
public class myrecipes{
    
    private Double RecipeID;
    private String RecipeName;
    private String Cuisine;
    
    public myrecipes(Double RecipeID, String RecipeName,  String Cuisine){
        this.RecipeID= RecipeID;
        this.RecipeName= RecipeName;  
        this.Cuisine= Cuisine;
        
        
    }
    
     public Double getRecipeID(){
        return RecipeID;
    }
    public String getRecipeName(){
        return RecipeName;
    }
   
    public String getCuisine(){
        return Cuisine;
    }
    
}
