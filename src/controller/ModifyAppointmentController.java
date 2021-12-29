/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DataAccess.AppointmentDAO;
import DataAccess.ContactDAO;
import DataAccess.CustomerDAO;
import DataAccess.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import schedulingApp.Page;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */

/** Intend to control modify appointment views. */
public class ModifyAppointmentController implements Initializable {

    @FXML
    private TextField appointmentIDTf;
    @FXML
    private TextField titleTf;
    @FXML
    private TextField descriptionTf;
    @FXML
    private TextField locationTf;
    @FXML
    private TextField typeTf;
    @FXML
    private DatePicker startDateTf;
    @FXML
    private DatePicker endDateTf;
    @FXML
    private ComboBox<Contact> contactCbb;
    @FXML
    private ComboBox<Customer> customerIDCbb;
    @FXML
    private ComboBox<User> userIDCbb;
    @FXML
    private ComboBox<String> startTimeCbb;
    @FXML
    private ComboBox<String> endTimeCbb;
    @FXML
    private Label warnningLabel;    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        contactCbb.setItems(ContactDAO.getAllContact());
        customerIDCbb.setItems(CustomerDAO.getAllCustomer());
        userIDCbb.setItems(UserDAO.getAllUser());
        startTimeCbb.setItems(AddAppointmentController.geTimetList());
        endTimeCbb.setItems(AddAppointmentController.geTimetList());

    }    
    
    /** 
     * Save all the input data coming through text-fields, combo-box and date-pickers.
     *  Whether check all fields are filled up, time overlapping and scheduling in past or not. 
     */
    @FXML
    private void onSaveHandle(ActionEvent event) throws IOException {
        try{
            int id = Integer.parseInt(appointmentIDTf.getText());
            String title = titleTf.getText();
            String description = descriptionTf.getText();
            String location = locationTf.getText();
            String type = typeTf.getText();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a");
            LocalDate sd = startDateTf.getValue();
            LocalTime st = LocalTime.parse(startTimeCbb.getValue(), dtf);
            LocalDateTime sldt = LocalDateTime.of(sd, st);
            Timestamp start = Timestamp.valueOf(sldt);

            LocalDate ed = endDateTf.getValue();
            LocalTime et = LocalTime.parse(endTimeCbb.getValue(), dtf);
            LocalDateTime eldt = LocalDateTime.of(ed, et);
            Timestamp end = Timestamp.valueOf(eldt);

            int contactID = contactCbb.getValue().getId();
            int customerID = customerIDCbb.getValue().getCustomerID();
            int userID = userIDCbb.getValue().getId();
            String user = UserDAO.user.getName();
            
            boolean empty = checkEmptyForModify();
            boolean notAvailable = AddAppointmentController.checkWeekendAndPastDateTime(sd, ed, st, et);
            boolean overlap = AppointmentDAO.checkOverLappingForModiy(start, end, id);
            
            if(empty){
                warnningLabel.setText("* Must enter each field! *");                
            }else if(notAvailable){
                warnningLabel.setText("* Appointment isn't availale at \npast time or date and weekend! *");
            }else if(overlap){
                warnningLabel.setText("* Appointment is overlapping! *");
            }else{
            
                AppointmentDAO.update(id, title, description, location, type, start, end, user, customerID, userID, contactID);    

                String address = "/view/Appointment.fxml";
                new Page().setPage(event, address); 
            }
        }
        catch(NullPointerException e){
            warnningLabel.setText("* Must enter each field \nwith proper type value! *"); 
        }        
    }
    
    /** Back to Main page. */
    @FXML
    private void onBackHandle(ActionEvent event) throws IOException {
        
        String address = "/view/Appointment.fxml";
        new Page().setPage(event, address); 
    }
    
    /** Receive transfer object which was selected on table-view to populate on the form of modify appointment.
     * @param ap */
    public void getSelectedAppointment(Appointment ap){
        LocalDateTime sldt = ap.getStart().toLocalDateTime();
        LocalDate startDate = sldt.toLocalDate();
        LocalTime startTime = sldt.toLocalTime();
        
        LocalDateTime eldt = ap.getEnd().toLocalDateTime();
        LocalDate endDate = eldt.toLocalDate();
        LocalTime endTime = eldt.toLocalTime();
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("h:mm a");
        
        appointmentIDTf.setText(String.valueOf(ap.getAppointmentID()));
        titleTf.setText(ap.getTitle());
        descriptionTf.setText(ap.getDescription());
        locationTf.setText(ap.getLocation());
        typeTf.setText(ap.getType());
        
        startDateTf.setValue(startDate);
        startTimeCbb.getSelectionModel().select(startTime.format(tf));
        
        endDateTf.setValue(endDate);                
        endTimeCbb.getSelectionModel().select(endTime.format(tf));

        Contact c = ContactDAO.getContact(ap.getContactName());
        contactCbb.getSelectionModel().select(c);

        Customer cust = CustomerDAO.getACustomer(ap.getCustomerName());
        customerIDCbb.getSelectionModel().select(cust);  

        User u = UserDAO.getUser(ap.getUserID());
        userIDCbb.getSelectionModel().select(u);
    }  
    
    /** Check if any fields is missing.
     * @return true if even a field misses and invalid input */
    public boolean checkEmptyForModify(){
        boolean empty = false;

        if(titleTf.getText().isEmpty()
                || descriptionTf.getText().isEmpty()
                || locationTf.getText().isEmpty()
                || contactCbb.getValue() == null 
                || typeTf.getText().isEmpty()
                || startDateTf.getValue() == null 
                || startTimeCbb.getValue() == null
                || endDateTf.getValue() == null
                || endTimeCbb.getValue() == null
                || customerIDCbb.getValue() == null
                || userIDCbb.getValue() == null){
            empty = true;
        }
        return empty;
    }
}

