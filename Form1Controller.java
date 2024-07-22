/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package RecipeSystem;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
//import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * FXML Controller class
 *
 * @author USER
 */
public class Form1Controller implements Initializable {

    
    @FXML
    private CheckBox LoginCheck; 
    
    @FXML
    private TextField Username;

    @FXML
    private PasswordField password;
    
      @FXML
    private TextField login_showPassword;

    @FXML
    private Button login_Btn;

    @FXML
    private Button close;

    @FXML
    private Button minimize;
    
      @FXML
    private AnchorPane LoginPage;

    @FXML
    private Hyperlink Register_loginlink;
    
    
    @FXML
    private CheckBox RegisterCheck;

    @FXML
    private TextField RegisterEmail;

    @FXML
    private TextField RegisterName;

    @FXML
    private AnchorPane RegisterPage;

    @FXML
    private PasswordField RegisterPassword;
    
      @FXML
    private TextField register_showPassword;

    @FXML
    private Button Register_Btn1;

    @FXML
    private Hyperlink Login_registerlink;

    

     public void switchForm(ActionEvent event) {

        if (event.getSource() == Register_loginlink) {
        
        LoginPage.setVisible(true);
        RegisterPage.setVisible(false);
        
        }else if (event.getSource() == Login_registerlink) {
        
        LoginPage.setVisible(false);
        RegisterPage.setVisible(true);}
     }
   
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    private AlertMessage alert = new AlertMessage();
    
    private double x = 0;
    private double y = 0;
    
    
    public void login(){
        
        String sql = "SELECT * FROM user WHERE Username = ? and password = ?";
        
        connect = Database.connectDB();
        
        try{
            
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, Username.getText());
            prepare.setString(2, password.getText());
            result = prepare.executeQuery();
            
             try {

                if (!login_showPassword.isVisible()) {
                    if (!login_showPassword.getText().equals(password.getText())) {
                        login_showPassword.setText(password.getText());
                    }
                } else {
                    if (!login_showPassword.getText().equals(password.getText())) {
                        password.setText(login_showPassword.getText());
                    }
                }
             }catch(Exception e){e.printStackTrace();}
             
             
            Alert alert;
            
            if(Username.getText().isEmpty() || password.getText().isEmpty()){
                
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Admin Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields.");
                alert.showAndWait();
            }else{
                if(result.next()){
                    
                   getData.Username = Username.getText();
                    
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Admin Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    
//                    TO HIDE THE LOGIN FORM
                    login_Btn.getScene().getWindow().hide();
                    
//                    FOR DASHBOARD
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
                    
                }else{
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Admin Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username or Password.");
                    alert.showAndWait();
                }
            }
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void loginShowPassword() {

        if (LoginCheck.isSelected()) {
            login_showPassword.setText(password.getText());
            login_showPassword.setVisible(true);
            password.setVisible(false);
        } else {
            password.setText(login_showPassword.getText());
            login_showPassword.setVisible(false);
            password.setVisible(true);
        }

    }
     
    public void registerAccount() {

        
        
        if (RegisterEmail.getText().isEmpty()
                || RegisterName.getText().isEmpty()
                || RegisterPassword.getText().isEmpty()) {
            // LETS CREATE OUR ALERT MESSAGE
          
            alert.errorMessage("Please fill all blank fields");
        } else {

            // WE WILL CHECK IF THE USERNAME THAT USER ENTERED IS ALREADY EXIST TO OUR DATABASE 
            String checkUsername = "SELECT * FROM user WHERE username = '"
                    + RegisterName.getText() + "'";

            connect = Database.connectDB();

            try {

                if (!register_showPassword.isVisible()) {
                    if (!register_showPassword.getText().equals(RegisterPassword.getText())) {
                        register_showPassword.setText(RegisterPassword.getText());
                    }
                } else {
                    if (!register_showPassword.getText().equals(RegisterPassword.getText())) {
                        RegisterPassword.setText(register_showPassword.getText());
                    }
                }

                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert.errorMessage(RegisterName.getText() + " is already exist!");
                } else if (RegisterPassword.getText().length() < 8) { // CHECK IF THE CHARACTERS OF THE PASSWORD IS LESS THAN TO 8
                    alert.errorMessage("Invalid Password, at least 8 characters needed");
                } else {
                    // TO INSERT THE DATA TO OUR DATABASE
                    String insertData = "INSERT INTO user (email, username, password) VALUES(?,?,?)";

                  

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, RegisterEmail.getText());
                    prepare.setString(2, RegisterName.getText());
                    prepare.setString(3, RegisterPassword.getText());
                   

                    prepare.executeUpdate();

                    alert.successMessage("Registered Successfully!");
                    registerClear();

                    // TO SWITCH THE FORM INTO LOGIN FORM
                    LoginPage.setVisible(true);
                    RegisterPage.setVisible(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
     
      public void registerClear() {
        RegisterEmail.clear();
        RegisterName.clear();
        RegisterPassword.clear();
        register_showPassword.clear();
    }
    
    public void registerShowPassword() {

        if (RegisterCheck.isSelected()) {
            register_showPassword.setText(RegisterPassword.getText());
            register_showPassword.setVisible(true);
            RegisterPassword.setVisible(false);
        } else {
            RegisterPassword.setText(register_showPassword.getText());
            register_showPassword.setVisible(false);
            RegisterPassword.setVisible(true);
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
    
    /*
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    
}
