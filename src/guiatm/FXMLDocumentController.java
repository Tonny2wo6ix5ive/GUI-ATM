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
     BorderPane mainPane;
    
    @FXML
    private Pane childPane;
    
    @FXML
    private Label label;
    
    @FXML
     TextField accNo;

    @FXML
    private PasswordField cPassword;

    @FXML
     TextField fName;

    @FXML
    private TextField lName;

    @FXML
     TextField newAccNo;

    @FXML
     PasswordField password;
    
    PreparedStatement pst;
    Connection con;
    Statement statement;
    ResultSet result;
    
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
    void signInBTN(ActionEvent event) throws SQLException {
        connection();
        
        String accountN = accNo.getText();
        int accountN1 = Integer.parseInt(accountN);
        String psswrd = password.getText();
        
        int databaseUser = 1;
        String databasePassword = "";
        
        try{
            String name = "Tonny";
            String passwrd = "tonnymakondesa6@gmail.com";
            String dbms = "jdbc:mysql://localhost:3307/atm";
            
            con = DriverManager.getConnection(dbms, name, passwrd);
            
            statement = con.createStatement();
            String sql = "SELECT * FROM user WHERE AccountNo=" + accountN1;
            
            result = statement.executeQuery(sql);
            
            while(result.next()){
                databaseUser = result.getInt("AccountNo");
                databasePassword = result.getString("Passwrd");
            }
            
            if(accountN1 == databaseUser && psswrd.equalsIgnoreCase(databasePassword)){
                Logic logic = new Logic();
                Pane childPane = logic.getPage("Account");
                mainPane.setCenter(childPane);
                mainPane.getChildren().removeAll(childPane);
                mainPane.getChildren().addAll(childPane);
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setHeaderText("Fail to signIn");
                alert.setContentText("Account Number or Password may be incorrect");
                alert.show();
            }
            
            con.close();
            
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
        double balance = 0.0;
        try{
            pst = con.prepareStatement("INSERT INTO user(AccountNo ,FirstName, LastName, Passwrd, Balance) VALUES(?,?,?,?,?)");
            pst.setString(1, newAccount);
            pst.setString(2, fNam);
            pst.setString(3, lNam);
            pst.setString(4, newPasswrd);
            pst.setDouble(5, balance);
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
