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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.SQLException;
/**
 * FXML Controller class
 *
 * @author USER
 */
public class RecipeDetailsController implements Initializable {

    static void showRecipeDetails(RecipeDetails recipeDetails) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @FXML
    private AnchorPane top_dashboard;
    @FXML
    private Button new_btn;
    @FXML
    private Button search_btn;
    @FXML
    private Button edit_btn;
    @FXML
    private Button close;
    @FXML
    private Button minimize;
    @FXML
    private Button home_btn;
    @FXML
    private TextField Ctime_txt;

    @FXML
    private TextField Rname_txt;

    @FXML
    private TextArea description_area;
    
    @FXML
    private AnchorPane ingredients_pane;
    
    @FXML
    private ComboBox<String> cuisineComboBox;
    
    @FXML
    private ComboBox<String> mealtimeComboBox;

    @FXML
    private Button save_btn;
    
    @FXML
    private Button ok_btn;
    
    @FXML
    private Button update_btn;
    
    private double x =0;
    private double y =0;
    
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
            if ((event.getSource() == home_btn)||(event.getSource() == ok_btn)) {
                
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
                ok_btn.getScene().getWindow().hide();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
     private final AlertMessage alert = new AlertMessage();

    public void loadCuisines() {
        String sql = "SELECT Name FROM cuisine";

        try (Connection connect = Database.connectDB();
             PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {

            ObservableList<String> cuisineList = FXCollections.observableArrayList();
            while (result.next()) {
                cuisineList.add(result.getString("Name"));
            }
            cuisineComboBox.setItems(cuisineList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadMealTimes() {
        String sql = "SELECT MealName FROM mealtime";

        try (Connection connect = Database.connectDB();
             PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {

            ObservableList<String> mealTimeList = FXCollections.observableArrayList();
            while (result.next()) {
                mealTimeList.add(result.getString("MealName"));
            }
            mealtimeComboBox.setItems(mealTimeList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addrecipe() throws IOException {
        if (description_area.getText().isEmpty() ||
            Rname_txt.getText().isEmpty() ||
            Ctime_txt.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
            return;
        }

        String checkRecipeName = "SELECT * FROM recipe WHERE RecipeName = ?";
        String recipeName = Rname_txt.getText();

        try (Connection connect = Database.connectDB();
             PreparedStatement checkStmt = connect.prepareStatement(checkRecipeName)) {

            checkStmt.setString(1, recipeName);
            try (ResultSet result = checkStmt.executeQuery()) {
                if (result.next()) {
                    alert.errorMessage(recipeName + " already exists!");
                    return;
                }
            }

            String selectedCuisine = cuisineComboBox.getSelectionModel().getSelectedItem();
            if (selectedCuisine == null) {
                alert.errorMessage("Please select a cuisine.");
                return;
            }
            int cuisineID = getCuisineID(selectedCuisine, connect);
            if (cuisineID == -1) {
                System.out.println("Cuisine not found.");
                return;
            }

            String selectedMealTime = mealtimeComboBox.getSelectionModel().getSelectedItem();
            if (selectedMealTime == null) {
                alert.errorMessage("Please select a meal time.");
                return;
            }
            int mealTimeID = getMealTimeID(selectedMealTime, connect);
            if (mealTimeID == -1) {
                System.out.println("Meal time not found.");
                return;
            }

            String insertData = "INSERT INTO userrecipe (RecipeName, Description, CookingTime, CuisineID, MealTimeID) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement insertStmt = connect.prepareStatement(insertData)) {
                insertStmt.setString(1, Rname_txt.getText());
                insertStmt.setString(2, description_area.getText());
                insertStmt.setString(3, Ctime_txt.getText());
                insertStmt.setInt(4, cuisineID);
                insertStmt.setInt(5, mealTimeID);

                insertStmt.executeUpdate();
                alert.successMessage("Registered Successfully!");

                // Hide current window and show the dashboard
                save_btn.getScene().getWindow().hide();
                showDashboard();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getCuisineID(String cuisineName, Connection connect) {
        String sql = "SELECT CuisineID FROM cuisine WHERE Name = ?";
        try (PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setString(1, cuisineName);
            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {
                    return result.getInt("CuisineID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int getMealTimeID(String mealTimeName, Connection connect) {
        String sql = "SELECT MealTimeID FROM mealtime WHERE MealName = ?";
        try (PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setString(1, mealTimeName);
            try (ResultSet result = prepare.executeQuery()) {
                if (result.next()) {
                    return result.getInt("MealTimeID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void showDashboard() throws IOException {
        // Implementation to load and show the DashboardFX.fxml
           Parent root = FXMLLoader.load(getClass().getResource("DashboardFX.fxml"));
                    
                    Stage stage = new Stage();
                    
                    Scene scene = new Scene(root);
                    
                    root.setOnMousePressed((MouseEvent event) ->{
                        
                        x = event.getSceneX();
                        y = event.getSceneY();
                        
                    });
                    
                    root.setOnMouseDragged((MouseEvent event) ->{
                       
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                        
                    });
                    
                    stage.initStyle(StageStyle.TRANSPARENT);
                    
                    stage.setTitle("Recipe Book");
                    stage.setScene(scene);
                    stage.show();
                     
    }
    
     private RecipeDetails currentRecipe;

    public void loadRecipeDetails(RecipeDetails recipeDetails) {
        this.currentRecipe = recipeDetails;
        if (recipeDetails != null) {
        Rname_txt.setText(recipeDetails.getRecipeName());
        description_area.setText(recipeDetails.getDescription());
        Ctime_txt.setText(recipeDetails.getCookingTime());
        cuisineComboBox.setValue(getCuisineNameByID(recipeDetails.getCuisineID()));
        mealtimeComboBox.setValue(getMealTimeNameByID(recipeDetails.getMealTimeID()));
    }else{
        clear();
        }
    }
    
    private void clear(){
    Rname_txt.setText("");
    description_area.setText("");
    description_area.setText("");
    Ctime_txt.setText("");
    cuisineComboBox.setValue("");
    mealtimeComboBox.setValue("");
    }

    @FXML
    private void onSaveButtonClicked() {
        // Update recipe details from form
        currentRecipe.setRecipeName(Rname_txt.getText());
        currentRecipe.setDescription(description_area.getText());
        currentRecipe.setCookingTime(Ctime_txt.getText());
        currentRecipe.setCuisineID(getCuisineIDByName(cuisineComboBox.getValue()));
        currentRecipe.setMealTimeID(getMealTimeIdByName(mealtimeComboBox.getValue()));

        updateRecipeInDB(currentRecipe);
    }

    private void updateRecipeInDB(RecipeDetails recipe) {
        String updateSQL = "UPDATE userrecipe SET RecipeName = ?, Description = ?, CookingTime = ?, CuisineID = ?, MealTimeID = ? WHERE RecipeID = ?";
        Connection connect = null;
        PreparedStatement prepare = null;

        try {
            connect = Database.connectDB();
            prepare = connect.prepareStatement(updateSQL);

            prepare.setString(1, recipe.getRecipeName());
            prepare.setString(2, recipe.getDescription());
            prepare.setString(3, recipe.getCookingTime());
            prepare.setInt(4, recipe.getCuisineID());
            prepare.setInt(5, recipe.getMealTimeID());
            prepare.setDouble(6, recipe.getRecipeID());

            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected > 0) {
                alert.successMessage("Recipe updated Successfully!");
            } else {
                System.out.println("No recipe found with the given ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (prepare != null) prepare.close();
                if (connect != null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCuisineNameByID(int cuisineID) {
String sql = "SELECT Name FROM cuisine WHERE CuisineID = ?";
        String cuisineName = null;

        try (Connection connection = Database.connectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cuisineID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cuisineName = resultSet.getString("Name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cuisineName;    }

    private String getMealTimeNameByID(int mealTimeID) {
      String sql = "SELECT MealName FROM mealtime WHERE MealTimeID = ?";
        String mealTimeName = null;

        try (Connection connection = Database.connectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, mealTimeID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                mealTimeName = resultSet.getString("MealName");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mealTimeName;
    }

    // Fetch meal time ID by name
    private int getMealTimeIdByName(String mealTimeName) {
        String sql = "SELECT MealTimeID FROM mealtime WHERE MealName = ?";
        int mealTimeId = -1;

        try (Connection connection = Database.connectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, mealTimeName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                mealTimeId = resultSet.getInt("MealTimeID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mealTimeId;
    }

    private int getCuisineIDByName(String cuisineName) {
         String sql = "SELECT CuisineID FROM cuisine WHERE Name = ?";
        int cuisineId = -1;

        try (Connection connection = Database.connectDB();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, cuisineName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cuisineId = resultSet.getInt("CuisineID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cuisineId;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCuisines();
        loadMealTimes();
    }    

   
    
}
