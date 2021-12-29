/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingApp;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author Zaw L Than
 */

/** Class implement interface to use lambda in creating page. */
public class Page {

    /** Interface in order to use lambda. */
    interface createPage{
        void setPage(ActionEvent e, String a);
    }
    
    /** Create page using lambda. */
    createPage p = (event, address) -> {
            try{
                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource(address));
                stage.setScene(new Scene(scene));
                stage.setTitle("Scheduling Management Application");
                stage.show();
             }catch(IOException e){
                 System.out.println("Problem: " + e.getMessage());
             }};    
    
    /** Set page using lambda.
     * @param event
     * @param address */
    public void setPage(ActionEvent event, String address){
        p.setPage(event, address);
    }
}


