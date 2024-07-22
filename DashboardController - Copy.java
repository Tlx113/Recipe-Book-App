/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RecipeSystem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author USER
 */
public class DashboardController implements Initializable{
    
        @FXML
    private Button breakfast_btn;

    @FXML
    private Circle circle;

    @FXML
    private AnchorPane cuisines_form;

    @FXML
    private AnchorPane dashboard_pane;

    @FXML
    private Button desserts_btn;

    @FXML
    private Button dinner_btn;

    @FXML
    private Button drinks_btn;

    @FXML
    private Button edit_btn;

    @FXML
    private Button extras_btn;

    @FXML
    private AnchorPane favorites_form;

    @FXML
    private AnchorPane left_dashboard;

    @FXML
    private Button leftcuisines_btn;

    @FXML
    private Button allrecipes_btn;

    @FXML
    private Button leftmealtime_btn;

    @FXML
    private Button leftmyrecipes_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button lunch_btn;

    @FXML
    private Button maindishes_btn;

    @FXML
    private AnchorPane mealtime_form;

    @FXML
    private AnchorPane myrecipe_form;

    @FXML
    private Button new_btn;

    @FXML
    private Button search_btn;

    @FXML
    private Button sides_btn;
    
    @FXML
    private Button close;
    
    @FXML
    private Button ok_btn;

    @FXML
    private Button minimize;

    @FXML
    private Button starters_btn;

    @FXML
    private AnchorPane top_dashboard;

    @FXML
    private Label username_lbl;
    
    @FXML
    private TableColumn<myrecipes, Double> myrecipe_SN;

    @FXML
    private TableColumn<myrecipes ,String> myrecipe_cuisine;

     @FXML
    private Button favorites_btn;
     
     @FXML
     private Button view_btn;

    @FXML
    private TableColumn<myrecipes, String> myrecipe_recipe;

    @FXML
    private TableView<myrecipes> myrecipes_table;
   
   @FXML
    private Button delete_btn;

    @FXML
    private Button home_btn;
    
     @FXML
    private TableColumn<favrecipe, Double> favrecipe_SN;

    @FXML
    private TableColumn<favrecipe, String> favrecipe_cuisine;
    
    
    @FXML
    private TableColumn<favrecipe, String> favrecipe_recipe;

    @FXML
    private TableView<favrecipe> favrecipes_tbl;

    private final AlertMessage alert = new AlertMessage();

    private double x =0;
    private double y =0;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private RecipelistController recipeListController;
    
    public void switchForm(ActionEvent event) {

        if (event.getSource() == leftcuisines_btn) {
        
        favorites_form.setVisible(false);
        cuisines_form.setVisible(true);
        mealtime_form.setVisible(false);
        myrecipe_form.setVisible(false);
        
     }else if(event.getSource() == leftmealtime_btn) {
        favorites_form.setVisible(false);
        cuisines_form.setVisible(false);
        mealtime_form.setVisible(true);
        myrecipe_form.setVisible(false);
        
     }else if(event.getSource() == leftmyrecipes_btn) {
        favorites_form.setVisible(false);
        cuisines_form.setVisible(false);
        mealtime_form.setVisible(false);
        myrecipe_form.setVisible(true);
     }else if(event.getSource() == favorites_btn) {
        favorites_form.setVisible(true);
        cuisines_form.setVisible(false);
        mealtime_form.setVisible(false);
        myrecipe_form.setVisible(false);
     }
     }  
     
   public void usernameLabel() {
        username_lbl.setText(getData.Username);
    }
     
   public ObservableList<myrecipes> mydataList() { 

        ObservableList<myrecipes> myrecipes = FXCollections.observableArrayList();

        String sql = "SELECT u.RecipeID, u.RecipeName, c.Name AS Cuisine FROM cuisine c INNER JOIN userrecipe  u ON c.cuisineID=u.cuisineID";

        connect = Database.connectDB();

        try {

            myrecipes aRecipe;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
             while (result.next()) {

                aRecipe = new myrecipes(result.getDouble("RecipeID"),
                        result.getString("RecipeName"), result.getString("Cuisine"));
                    
                
            
                myrecipes.add(aRecipe);

            }
            
             } catch (SQLException e) {
            e.printStackTrace();
        }
        return myrecipes;
        }
    
private ObservableList<myrecipes> myrecipes;

public void showMyRecipes() {

        myrecipes = mydataList();

        myrecipe_SN.setCellValueFactory(new PropertyValueFactory<>("RecipeID"));
        myrecipe_recipe.setCellValueFactory(new PropertyValueFactory<>("RecipeName"));
        myrecipe_cuisine.setCellValueFactory(new PropertyValueFactory<>("Cuisine"));
    

        myrecipes_table.setItems(myrecipes);

    }
    
public ObservableList<favrecipe> favdataList() { 

        ObservableList<favrecipe> favrecipe = FXCollections.observableArrayList();
        String sql =  "SELECT u.RecipeID, u.RecipeName, c.Name AS Cuisine FROM cuisine c INNER JOIN userrecipe  u ON c.cuisineID=u.cuisineID";

        connect = Database.connectDB();

        try {

            favrecipe aRecipe;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
             while (result.next()) {

                aRecipe = new favrecipe(result.getDouble("RecipeID"),
                        result.getString("RecipeName"),result.getString("cuisine"));
                    

                favrecipe.add(aRecipe);

            }
            
             } catch (SQLException e) {
            e.printStackTrace();
        }
        return favrecipe;
        }
    
private ObservableList<favrecipe> favrecipe;

public void showfavRecipes() {

        favrecipe = favdataList();

        favrecipe_SN.setCellValueFactory(new PropertyValueFactory<>("RecipeID"));
        favrecipe_recipe.setCellValueFactory(new PropertyValueFactory<>("RecipeName"));
        favrecipe_cuisine.setCellValueFactory(new PropertyValueFactory<>("Cuisine"));

    

        favrecipes_tbl.setItems(favrecipe);
    
    }

public void openrecipelist(ActionEvent event) {
        try {
            if (event.getSource() == starters_btn) {
                // TO SWAP FROM DASHBOARD TO RECIPELIST FORM
                Parent root = FXMLLoader.load(getClass().getResource("Recipelist.fxml"));

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

                starters_btn.getScene().getWindow().hide();

            }else if (event.getSource() == sides_btn) {
                // TO SWAP FROM DASHBOARD TO RECIPELIST FORM
                Parent root = FXMLLoader.load(getClass().getResource("Recipelist.fxml"));

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

                sides_btn.getScene().getWindow().hide();

            }else if (event.getSource() == maindishes_btn) {
                // TO SWAP FROM DASHBOARD TO RECIPELIST FORM
                Parent root = FXMLLoader.load(getClass().getResource("Recipelist.fxml"));

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

                maindishes_btn.getScene().getWindow().hide();

            }else if (event.getSource() == lunch_btn) {
                // TO SWAP FROM DASHBOARD TO RECIPELIST FORM
                Parent root = FXMLLoader.load(getClass().getResource("Recipelist.fxml"));

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

                lunch_btn.getScene().getWindow().hide();

            }else if (event.getSource() == extras_btn) {
                // TO SWAP FROM DASHBOARD TO RECIPELIST FORM
                Parent root = FXMLLoader.load(getClass().getResource("Recipelist.fxml"));

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

                extras_btn.getScene().getWindow().hide();

            }else if (event.getSource() == drinks_btn) {
                // TO SWAP FROM DASHBOARD TO RECIPELIST FORM
                Parent root = FXMLLoader.load(getClass().getResource("Recipelist.fxml"));

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

                drinks_btn.getScene().getWindow().hide();

            }else if (event.getSource() == dinner_btn) {
                // TO SWAP FROM DASHBOARD TO RECIPELIST FORM
                Parent root = FXMLLoader.load(getClass().getResource("Recipelist.fxml"));

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

                dinner_btn.getScene().getWindow().hide();

            }else if (event.getSource() == desserts_btn) {
                // TO SWAP FROM DASHBOARD TO RECIPELIST FORM
                Parent root = FXMLLoader.load(getClass().getResource("Recipelist.fxml"));

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

                desserts_btn.getScene().getWindow().hide();

            }else if (event.getSource() == breakfast_btn) {
                // TO SWAP FROM DASHBOARD TO RECIPELIST FORM
                Parent root = FXMLLoader.load(getClass().getResource("Recipelist.fxml"));

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

                breakfast_btn.getScene().getWindow().hide();

            }else if (event.getSource() == allrecipes_btn) {
                // TO SWAP FROM DASHBOARD TO RECIPELIST FORM
                Parent root = FXMLLoader.load(getClass().getResource("Recipelist.fxml"));

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

                allrecipes_btn.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
@FXML
private void onEditButtonClicked(ActionEvent event) {
        myrecipes selectedRecipe = myrecipes_table.getSelectionModel().getSelectedItem();
        if (selectedRecipe != null) {
            openRecipeDetailsForm(selectedRecipe);
        } else {
            // Handle no selection
            alert.successMessage("No recipe selected!");
        }
    }

    private void openRecipeDetailsForm(myrecipes selectedRecipe) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeDetails.fxml"));
            Parent root = loader.load();

            RecipeDetailsController controller = loader.getController();
            RecipeDetails recipeDetails = fetchRecipeDetailsFromDB(selectedRecipe.getRecipeID());
            controller.loadRecipeDetails(recipeDetails);

            Stage stage = new Stage();
            stage.setTitle("Edit Recipe");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
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
        
    public void deleteRecipe(Double recipeID) {
    String deleteSQL = "DELETE FROM userrecipe WHERE RecipeID = ?";
    
    try (Connection connect = Database.connectDB();
         PreparedStatement deleteStmt = connect.prepareStatement(deleteSQL)) {
        
        deleteStmt.setDouble(1, recipeID);
        int rowsAffected = deleteStmt.executeUpdate();
        
        if (rowsAffected > 0) {
            // Recipe deleted successfully
            alert.successMessage("Recipe deleted successfully!");
            // Optionally show a success message or update UI
        } else {
            // No recipe found with the given ID
             alert.successMessage("No recipe found with RecipeID: " + recipeID);
            // Optionally show an error message or update UI
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception appropriately (e.g., show error message)
    }
}

public void handleDeleteButton(ActionEvent event) {
     // Get the selected recipe from the TableView
        myrecipes selectedRecipe = myrecipes_table.getSelectionModel().getSelectedItem();

        if (selectedRecipe != null) {
            // Extract the recipe ID from the selected recipe
            Double recipeIDToDelete = selectedRecipe.getRecipeID();

            // Call the deleteRecipe method with the actual recipe ID
            deleteRecipe(recipeIDToDelete);
        } else {
            // If no recipe is selected, show a message or handle appropriately
            alert.successMessage("No recipe selected. Please select a recipe to delete.");
        }
         myrecipes_table.refresh();
    }

public void setRecipeListController(RecipelistController controller) {
        this.recipeListController = controller;
    }

    @FXML
    private void filterStarter() {
        if (recipeListController != null) {
            recipeListController.loadRecipes("MealName", "Starter");
            
        }
    }

    @FXML
    private void filterMainCourse() {
        if (recipeListController != null) {
            recipeListController.loadRecipes("MealName", "Main Course");
        }
    }

    @FXML
    private void filterDessert() {
        if (recipeListController != null) {
            recipeListController.loadRecipes("MealName", "Dessert");
        }
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
    public void logout(ActionEvent event) {
        try {
            if (event.getSource() == logout_btn) {
                // TO SWAP FROM DASHBOARD TO LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("form1.fxml"));

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

                logout_btn.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                home_btn.getScene().getWindow().hide();;
                

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
public void viewRecipe(ActionEvent event) {
    myrecipes selectedRecipe = myrecipes_table.getSelectionModel().getSelectedItem();
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
    public void initialize(URL location, ResourceBundle resources) {
            showMyRecipes();
            showfavRecipes();
            usernameLabel();
    }   
    
}
