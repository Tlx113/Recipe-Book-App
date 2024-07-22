/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package RecipeSystem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class RecipelistController implements Initializable {
    
    
  
    
    @FXML
    private TableColumn<Allrecipe, Double> allrecipe_SN;

    @FXML
    private TableColumn<myrecipes, String> allrecipe_cuisine;

    @FXML
    private AnchorPane allrecipe_page;

    @FXML
    private TableColumn<myrecipes, String> allrecipe_recipe;

    @FXML
    private TableView<myrecipes> Allrecipes_tbl;
    
    @FXML
    private Button close;

    @FXML
    private Button edit_btn;

    @FXML
    private Button home_btn;

    @FXML
    private Button minimize;

    @FXML
    private Button new_btn;

    @FXML
    private Button view_btn;

    @FXML
    private AnchorPane top_dashboard;
        
    private double x =0;
    private double y =0;

   private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private final AlertMessage alert = new AlertMessage();


    public ObservableList<myrecipes> dataList() { 

        ObservableList<myrecipes> myrecipes = FXCollections.observableArrayList();

        String sql ="SELECT r.RecipeID, r.RecipeName, r.CookingTime, c.Name AS Cuisine, m.MealName FROM cuisine c JOIN userrecipe r ON c.CuisineID = r.CuisineID JOIN mealtime m ON r.MealTimeID = m.MealTimeID ORDER BY RecipeID";

        connect = Database.connectDB();

       try {

            myrecipes aRecipe;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
             while (result.next()) {

                aRecipe = new myrecipes(result.getDouble("RecipeID"),
                        result.getString("RecipeName"),result.getString("Cuisine"));
                    

                myrecipes.add(aRecipe);
            }
            
            } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        try {
            if (result != null) {
                result.close();
            }
            if (prepare != null) {
                prepare.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        return myrecipes;
        }
    
private ObservableList<myrecipes> myrecipes;

public void showallRecipes() {

        myrecipes = dataList();

        allrecipe_SN.setCellValueFactory(new PropertyValueFactory<>("RecipeID"));
        allrecipe_recipe.setCellValueFactory(new PropertyValueFactory<>("RecipeName"));
        allrecipe_cuisine.setCellValueFactory(new PropertyValueFactory<>("Cuisine"));

    

        Allrecipes_tbl.setItems(myrecipes);
    
    }

 @FXML
    public void initialize() {
        allrecipe_SN.setCellValueFactory(new PropertyValueFactory<>("RecipeID"));
        allrecipe_recipe.setCellValueFactory(new PropertyValueFactory<>("RecipeName"));
        allrecipe_cuisine.setCellValueFactory(new PropertyValueFactory<>("Cuisine"));

        loadRecipes(null, null);
    }

    public void loadRecipes(String filterType, String filterCriterion) {
        ObservableList<myrecipes> recipes = FXCollections.observableArrayList();

        String sql = "SELECT r.RecipeID, r.RecipeName, r.CookingTime, c.Name AS Cuisine, m.MealName " +
                     "FROM recipe r " +
                     "JOIN cuisine c ON r.CuisineID = c.CuisineID " +
                     "JOIN mealtime m ON r.MealTimeID = m.MealTimeID";

        if (filterType != null && filterCriterion != null) {
            sql += " WHERE " + filterType + " = ?";
        }

        connect = Database.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            if (filterType != null && filterCriterion != null) {
                prepare.setString(1, filterCriterion);
            }

            result = prepare.executeQuery();

            while (result.next()) {
                myrecipes recipe = new myrecipes(
                        result.getDouble("RecipeID"),
                        result.getString("RecipeName"),
                        result.getString("Cuisine")
                );
                recipes.add(recipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (result != null) result.close();
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        Allrecipes_tbl.setItems(myrecipes);
    }

    @FXML
    public void minimize(){
        Stage stage = (Stage)minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @FXML
    public void exit(){
        System.exit(0);
    }
    
     @FXML
    public void home(ActionEvent event) {
        try {
            if (event.getSource() == home_btn) {
                
                Parent root = FXMLLoader.load(getClass().getResource("DashboardFX.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent e) -> {

                    stage.setX(e.getScreenX() - x);
                    stage.setY(e.getScreenY() - y);

                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

                home_btn.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void new_btn(ActionEvent event) {
        try {
            if (event.getSource() == new_btn) {
                
                Parent root = FXMLLoader.load(getClass().getResource("RecipeDetails.fxml"));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent e) -> {
                    x = e.getSceneX();
                    y = e.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent e) -> {

                    stage.setX(e.getScreenX() - x);
                    stage.setY(e.getScreenY() - y);

                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

                new_btn.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
private RecipeDetails fetchRecipeDetailsFromDB(Double recipeID) {
    String sql = "SELECT RecipeName, Description, CookingTime, CuisineID, MealTimeID FROM userrecipe WHERE RecipeID = ?";
        RecipeDetails recipeDetails = null;

        try (Connection connection = Database.connectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, recipeID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String recipeName = resultSet.getString("RecipeName");
                String description = resultSet.getString("Description");
                String cookingTime = resultSet.getString("CookingTime");
                int cuisineID = resultSet.getInt("CuisineID");
                int mealTimeID = resultSet.getInt("MealTimeID");

                recipeDetails = new RecipeDetails(recipeID, recipeName, description, cookingTime, cuisineID, mealTimeID);
            } else {
                System.out.println("Recipe with ID " + recipeID + " not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipeDetails;
    }

@FXML
public void viewRecipe(ActionEvent event) {
    myrecipes selectedRecipe = Allrecipes_tbl.getSelectionModel().getSelectedItem();
    if (selectedRecipe != null) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("viewdetails.fxml"));
            Parent root = loader.load();

            RecipeDetailsController controller = loader.getController();
            RecipeDetails recipeDetails = fetchRecipeDetailsFromDB(selectedRecipe.getRecipeID());
            controller.loadRecipeDetails(recipeDetails);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            view_btn.getScene().getWindow().hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } else {
        // Handle the case where no recipe is selected
         alert.successMessage("No recipe selected");
    }
}
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showallRecipes();
        initialize();
    }    
    
}
