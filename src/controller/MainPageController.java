/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import schedulingApp.Page;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */

/** Control main page view of the application. */
public class MainPageController implements Initializable { 
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    /** Handle on action of choosing to Customer management department. */
    @FXML
    private void onCustomerHandle(ActionEvent event) throws IOException {
        
        String address = "/view/Customer.fxml";           
        new Page().setPage(event, address); 
    }
    
    /** Handle on action of choosing to Appointment management department. */
    @FXML
    private void onAppointmentHandle(ActionEvent event) throws IOException {
        
        String address = "/view/Appointment.fxml";          
        new Page().setPage(event, address); 
    }    
    
    /** Handle on action of choosing to group of reports. */
    @FXML
    private void onReportHandle(ActionEvent event) throws IOException {
        
        String address = "/view/Report.fxml";
        new Page().setPage(event, address); 
    }
}
