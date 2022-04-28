/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package guiatm;

import java.sql.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author USER
 */
public class Accounts extends FXMLDocumentController{
    
    @FXML
    private Label Balance;

    @FXML
    private TextField amountSend;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField depositAmount;

    @FXML
    private Label displayName;

    @FXML
    private Label displayNumber;

    @FXML
    private TextField otherAccountNo;

    @FXML
    private TextField withdrawAmount;
    
    public void AccountDetails(){
        
        String getAccNo = accNo.getText();
        int accountnumber = 0;
        String userFname = "";
        String userLname = "";
        double userBalance = 0.0;
        
        try{
            String names = "Tonny";
            String psswrd = "tonnymakondesa6@gmail.com";
            String dbms = "jdbc:mysql://localhost:3307/atm";
            
            con = DriverManager.getConnection(dbms, names, psswrd);
            
            statement = con.createStatement();
            String sql = "SELECT * FROM user WHERE AccountNo=" + getAccNo;
            result = statement.executeQuery(sql);
            
            while(result.next()){
                accountnumber = result.getInt("AccountNo");
                userFname = result.getString("FirstName");
                userLname = result.getString("LastName");
                userBalance = result.getDouble("Balance");
            }
            
            String accountnumber1 = String.valueOf(accountnumber);
            String balance = String.valueOf(userBalance);
            
            displayName.setText(userFname + " " + userLname);
            displayNumber.setText(accountnumber1);
            Balance.setText(balance);
            
        }catch(Exception e){}
    }

    @FXML
    void depositButton(ActionEvent event) {

    }

    @FXML
    void sendMoney(ActionEvent event) {
        
        String deposit1 = amountSend.getText();
        double intDeposit = Double.parseDouble(deposit1);
        String otherAcc = otherAccountNo.getText();
        int otherAcc1 = Integer.parseInt(otherAcc);
        String nameF = "";
        String nameL = "";
        
        try{
            String names = "Tonny";
            String psswrd = "tonnymakondesa6@gmail.com";
            String dbms = "jdbc:mysql://localhost:3307/atm";
            
            con = DriverManager.getConnection(dbms, names, psswrd);
            
            String query = "UPDATE user SET Balance = ? + Balance WHERE AccountNo=?";
            pst = con.prepareStatement(query);
            pst.setDouble(1, intDeposit);
            pst.setInt(2, otherAcc1);
            int status = pst.executeUpdate();
            
            statement = con.createStatement();
            String sql = "SELECT * FROM user WHERE AccountNo=" + otherAcc;
            
            result = statement.executeQuery(sql);
            while(result.next()){
               nameF = result.getString("FirstName");
               nameL = result.getString("LastName");
            }
            
            if(status == 1){
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Money sent to " + nameF + " " + nameL);
                alert.show();
            }else{
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Sorry account specified does not exist");
                alert.show();
            }
            
            con.close();
            
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to update balance, Please contact admin for help");
            alert.setContentText(e.toString());
            alert.show();
        }
    }

    @FXML
    void signOut(ActionEvent event) {

    }

    @FXML
    void withdrawButton(ActionEvent event) {

    }


}
