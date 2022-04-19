/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guiatm;

import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author USER
 */
public class Accounts extends FXMLDocumentController{
    
    @FXML
    private Label balance;

    @FXML
    private TextField confirmAcc;
    
    @FXML
    private TextField deposit;

    @FXML
    private Label name;

    @FXML
    void depobtn(ActionEvent event) {
        
        String deposit1 = deposit.getText();
        double intDeposit = Double.parseDouble(deposit1);
        String newAcc = confirmAcc.getText();
        int newAcc1 = Integer.parseInt(newAcc);
        
        try{
            String names = "Tonny";
            String psswrd = "tonnymakondesa6@gmail.com";
            String dbms = "jdbc:mysql://localhost:3307/atm";
            
            con = DriverManager.getConnection(dbms, names, psswrd);
            
            String query = "UPDATE user SET Balance = ? + Balance WHERE AccountNo=?";
            pst = con.prepareStatement(query);
            pst.setDouble(1, intDeposit);
            pst.setInt(2, newAcc1);
            pst.executeUpdate();
            
            con.close();
            
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to update balance");
            alert.setContentText(e.toString());
            alert.show();
        }
    }
    
    public void accountDetails(){
        connection();
        
        try{
            name.setText(fName.getText());
            
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to get user information");
            alert.setContentText(e.toString());
            alert.show();
        }
    }
}
