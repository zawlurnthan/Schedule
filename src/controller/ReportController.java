/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DataAccess.AppointmentDAO;
import DataAccess.ContactDAO;
import DataAccess.CustomerDAO;
import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;
import model.Customer;
import schedulingApp.Page;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */

/** Control report page view. */
public class ReportController implements Initializable {

    @FXML
    private ComboBox<Customer> customerCbb;
    @FXML
    private ComboBox<String> typeCbb;
    @FXML
    private ComboBox<Month> monthCbb;
    @FXML
    private ComboBox<Contact> contactCbb;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, String> description;
    @FXML
    private TableColumn<Appointment, String> startCol;
    @FXML
    private TableColumn<Appointment, String> endCol;
    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;
    @FXML
    private TableView<Appointment> reportTableView;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        customerCbb.setItems(CustomerDAO.getAllCustomer());
        typeCbb.setItems(AppointmentDAO.getAllType());
        monthCbb.setItems(AppointmentDAO.getAllMonth());
        contactCbb.setItems(ContactDAO.getAllContact());

        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }    
    
    /** Selecting a customer form combo-box list. */
    @FXML
    private void onCustomerCbbHandle(ActionEvent event) {   
        int id = customerCbb.getValue().getCustomerID();
        reportTableView.setItems(AppointmentDAO.getAllAppointmentbyCustomerID(id));
    }
    
        
    /** Selecting a contact form combo-box list. */
    @FXML
    private void OnContactCbbHandle(ActionEvent event) {      
        int id = contactCbb.getValue().getId();
        reportTableView.setItems(AppointmentDAO.getAllAppointmentbyContactID(id));
    }  
    
    /** Selecting a type form combo-box list. */
    @FXML
    private void onTypeCbbHandle(ActionEvent event) {   
        try{
           String type = typeCbb.getSelectionModel().getSelectedItem();
            int m = monthCbb.getValue().getValue();
            reportTableView.setItems(AppointmentDAO.getAllAppointmentbyTypeAndMonth(type, m));  
        }catch(NullPointerException e){
            e.getMessage();
        }          
    }
    
    /** Selecting a month form combo-box list. */
    @FXML
    private void onMonthCbbHandle(ActionEvent event) {
        try{            
            String type = typeCbb.getSelectionModel().getSelectedItem();
            int m = monthCbb.getValue().getValue();
            reportTableView.setItems(AppointmentDAO.getAllAppointmentbyTypeAndMonth(type, m));   
        }catch(NullPointerException e){
            e.getMessage();
        }        
    }
  
    
    /** Back to main page or home. */
    @FXML
    private void onHomeHandle(ActionEvent event) throws IOException {
        
        String address = "/view/MainPage.fxml";
        new Page().setPage(event, address); 
    }
}
