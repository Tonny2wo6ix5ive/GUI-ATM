/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiatm;

import java.sql.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author USER
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private BorderPane mainPane;
    
    @FXML
    private Pane childPane;
    
    @FXML
    private Label label;
    
    @FXML
    private TextField accNo;

    @FXML
    private PasswordField cPassword;

    @FXML
    private TextField fName;

    @FXML
    private TextField lName;

    @FXML
    private TextField newAccNo;

    @FXML
    private PasswordField password;
    
    PreparedStatement pst;
    Connection con;
    Statement statement;
    
    public void connection(){
        try{
            String name = "Tonny";
            String psswrd = "tonnymakondesa6@gmail.com";
            String dbms = "jdbc:mysql://localhost:3307/atm";
        
            con = DriverManager.getConnection(dbms, name, psswrd);
            statement = con.createStatement();
            
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Fail to connect to DataBase");
            alert.setContentText(e.toString());
            alert.show();
        }
    }
    

    @FXML
    void signInBTN(ActionEvent event) {
        connection();
        String accountN = accNo.getText();
        String psswrd = password.getText();
        
        try{
            String getAccountNo = "SELECT * FROM user WHERE AccountNo ==" + accountN.toString();
            String getPassword = "SELECT * FROM user WHERE Passwrd ==" + psswrd.toString();
            ResultSet result1 = statement.executeQuery(getAccountNo);
            ResultSet result2 = statement.executeQuery(psswrd);
            
            if(result1.getString("AccountNo") == accountN && result2.getString("Passwrd") == psswrd){
                Logic logic = new Logic();
                Pane childPane = logic.getPage("Account");
                mainPane.setCenter(childPane);
                mainPane.getChildren().removeAll(childPane);
                mainPane.getChildren().addAll(childPane);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Fail to signIn");
                alert.setContentText("Account number or Password may be wrong");
                alert.show();
            }
            
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Fail to signIn");
            alert.setContentText(e.toString());
            alert.show();
        }
    }

    @FXML
    void signUpBTN(ActionEvent event) {
        connection();
        
        String newAccount = newAccNo.getText();
        String fNam = fName.getText();
        String lNam = lName.getText();
        String newPasswrd = cPassword.getText();
        try{
            pst = con.prepareStatement("INSERT INTO user(AccountNo ,FirstName, LastName, Passwrd) VALUES(?,?,?,?)");
            pst.setString(1, newAccount);
            pst.setString(2, fNam);
            pst.setString(3, lNam);
            pst.setString(4, newPasswrd);
            int status = pst.executeUpdate();
            
            if(status == 1){
                Logic logic = new Logic();
                Pane childPane = logic.getPage("Account");
                mainPane.setCenter(childPane);
                mainPane.getChildren().removeAll(childPane);
                mainPane.getChildren().addAll(childPane);
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Fail to signUp");
            alert.setContentText(e.toString());
            alert.show();
        }
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connection();
    }    
    
}
