/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guiatm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 *
 * @author USER
 */
public class Logic {
    @FXML
    private Pane childPane;
    
    public Pane getPage(String fileName) throws IOException{
        try{
            URL fileURL = GUIATM.class.getResource("/guiatm/" + fileName + ".fxml");
            if(fileURL==null){
                throw new java.io.FileNotFoundException("File not found");
            }
            
            childPane = new FXMLLoader().load(fileURL);
            
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        return childPane;
    }
}
