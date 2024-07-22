/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RecipeSystem;



public class favrecipe {
     
    private Double RecipeID;
    private String RecipeName;
    private String Cuisine;
    
    
    public favrecipe(Double RecipeID, String RecipeName,  String Cuisine){
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

    

