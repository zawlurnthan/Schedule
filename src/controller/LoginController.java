/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DataAccess.AppointmentDAO;
import DataAccess.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import schedulingApp.Page;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */

/** Control login view. */
public class LoginController implements Initializable {   
    
    @FXML
    private Label userNameLb;   
    @FXML
    private Label passwordLb;
    @FXML
    private TextField userNameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Label errorMessageLb;
    @FXML
    private Label userLocationLb;
    @FXML
    private Button logInBtn;

    String errorMessage;        

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        rb = ResourceBundle.getBundle("languages/Login", Locale.getDefault());
        userNameLb.setText(rb.getString("userName"));
        passwordLb.setText(rb.getString("password"));
        logInBtn.setText(rb.getString("logIn"));
        errorMessage = rb.getString("errorMessage");
        userLocationLb.setText(rb.getString("userLocation") + 
                ZoneId.of(TimeZone.getDefault().getID()) + ".");        
    }    
   
    /** Handle on action of login process and load alert whether upcoming 
     * appointment within 15 minutes and main page if input criteria are correct. */
    @FXML
    private void onLogInHandle(ActionEvent event) throws IOException {

        String userName = userNameTxt.getText();
        String password = passwordTxt.getText();        
        boolean exit = UserDAO.checkUserLogin(userName, password);
          
        if(exit){            
            String add = "/view/MainPage.fxml";          
            new Page().setPage(event, add);                
            AppointmentDAO.checkUpcomingAppointment();
        }
        else{
            errorMessageLb.setText(errorMessage);
        } 
    }
}
