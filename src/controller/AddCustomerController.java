/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DataAccess.CountryDAO;
import DataAccess.CustomerDAO;
import DataAccess.DivisionDAO;
import DataAccess.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Country;
import model.Division;
import schedulingApp.Page;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */

/** Control class for adding customer view. */
public class AddCustomerController implements Initializable {
    
    @FXML
    private TextField addNameTf;
    @FXML
    private TextField addAddressTf;
    @FXML
    private TextField addZidCodeTf;
    @FXML
    private TextField addPhoneTf;
    
    @FXML
    private ComboBox<Division> addDivisionCbb;
    @FXML
    private ComboBox<Country> addCountryCbb;
    @FXML
    private Label warnningLabel;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        addCountryCbb.setItems(CountryDAO.getAllCountry());
    }    

    /** Save all the input data coming through text-fields and combo-boxes. */
    @FXML
    private void onSaveHandle(ActionEvent event) throws IOException {        
        try{
            String name = addNameTf.getText();
            String address = addAddressTf.getText();
            String zid = addZidCodeTf.getText();
            String phone = addPhoneTf.getText();
            int divisionID = addDivisionCbb.getValue().getDivisionID();
            String user = UserDAO.user.getName();

            CustomerDAO.insert(name, address, zid, phone, user, divisionID);

            String add = "/view/Customer.fxml";
            new Page().setPage(event, add); 
        }
        catch(NullPointerException e){
            e.getMessage();
            warnningLabel.setText("* Must enter each field! *");
        }          
    }
    
    /** Back to Main page. */
    @FXML
    private void onBackHandle(ActionEvent event) throws IOException {
        
        String address = "/view/Customer.fxml";
        new Page().setPage(event, address); 
    }
    
    /** Handle in action of country combo-box functioning limit division combo-box list by choosing country first. */
    @FXML
    private void onCountryCbbHandle(ActionEvent event) {
        int id = addCountryCbb.getValue().getCountryID();
        addDivisionCbb.setItems(DivisionDAO.getSelectedDivision(id));
    }    
}
