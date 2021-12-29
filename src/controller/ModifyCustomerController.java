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
import model.Customer;
import model.Division;
import schedulingApp.Page;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */

/** Control class for modifying customer view. */
public class ModifyCustomerController implements Initializable {

    @FXML
    private TextField mdfUserIDTf;
    @FXML
    private TextField mdfNameTf;
    @FXML
    private TextField mdfAddressTf;
    @FXML
    private TextField mdfZidCodeTf;
    @FXML
    private TextField mdfPhoneTf;
    
    @FXML
    private ComboBox<Division> mdfDivisionCbb;
    @FXML
    private ComboBox<Country> mdfCountryCbb;
    @FXML
    private Label warnningLabel;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        mdfCountryCbb.setItems(CountryDAO.getAllCountry());
    }    

    /** Save all the input data coming through text-fields and combo-boxes. */
    @FXML
    private void onSaveHandle(ActionEvent event) throws IOException {
        
        try{
            int id = Integer.parseInt(mdfUserIDTf.getText());
            String name = mdfNameTf.getText();
            String address = mdfAddressTf.getText();
            String zid = mdfZidCodeTf.getText();
            String phone = mdfPhoneTf.getText();
            int divisionID = mdfDivisionCbb.getValue().getDivisionID();
            String user = UserDAO.user.getName();

            CustomerDAO.update(id, name, address, zid, phone, user, divisionID);
        
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
        int id = mdfCountryCbb.getValue().getCountryID();
        mdfDivisionCbb.setItems(DivisionDAO.getSelectedDivision(id));
    }
    
    /** receiving transferred object form main customer page.
     * @param p */
    public void getSelectedCustomer(Customer p){

        int dID = p.getDivisionID();
        Division d = DivisionDAO.getDivision(dID);
        int cID = d.getCountryID();
        Country c = CountryDAO.getCountry(cID);
        
        mdfUserIDTf.setText(String.valueOf(p.getCustomerID()));
        mdfNameTf.setText(p.getName());
        mdfAddressTf.setText(p.getAddress());
        mdfZidCodeTf.setText(p.getZipCode());
        mdfPhoneTf.setText(p.getPhone());  
        
        mdfDivisionCbb.getSelectionModel().select(d);
        mdfCountryCbb.getSelectionModel().select(c);        
    }    
}






