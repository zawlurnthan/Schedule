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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Contact;
import model.Customer;
import model.User;
import schedulingApp.Page;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */

/** Intend to control adding appointment views. */
public class AddAppointmentController implements Initializable {

    @FXML
    private TextField appointmentIDTf;
    @FXML
    private TextField titleTf;
    @FXML
    private TextField descriptionTf;
    @FXML
    private TextField locationTf;
    @FXML
    private ComboBox<Contact> contactCbb;
    @FXML
    private TextField typeTf;
    @FXML
    private DatePicker startDateTf;
    @FXML
    private ComboBox<String> startTimeCbb;
    @FXML
    private DatePicker endDateTf;    
    @FXML
    private ComboBox<String> endTimeCbb;    
    @FXML
    private ComboBox<Customer> customerIDCbb;
    @FXML
    private ComboBox<User> userIDCbb;
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
        startTimeCbb.setItems(geTimetList());
        endTimeCbb.setItems(geTimetList());
    }    

    /** 
     * Save all the input data coming through text-fields, combo-box and date-pickers.
     *  Whether check all fields are filled up, time overlapping and scheduling in past or not. 
     */
    @FXML
    private void onSaveHandle(ActionEvent event) throws IOException {
        try{
            String title = titleTf.getText();
            String description = descriptionTf.getText();
            String location = locationTf.getText();
            String type = typeTf.getText();
            int contactID = contactCbb.getValue().getId();
            
            // Creating Start Timestamp from input 
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("h:mm a");
            LocalDate sd = startDateTf.getValue();
            LocalTime st = LocalTime.parse(startTimeCbb.getValue(), dtf);
            LocalDateTime sldt = LocalDateTime.of(sd, st);
            Timestamp start = Timestamp.valueOf(sldt);           
            
            // Creating End Timestamp form input
            LocalDate ed = endDateTf.getValue();
            LocalTime et = LocalTime.parse(endTimeCbb.getValue(), dtf);
            LocalDateTime eldt = LocalDateTime.of(ed, et);
            Timestamp end = Timestamp.valueOf(eldt);                        
                        
            int custID = customerIDCbb.getValue().getCustomerID();
            int userID = userIDCbb.getValue().getId();     
            String user = UserDAO.user.getName();
            
            // Checking Input Errors
            boolean empty = checkEmptyFields();
            boolean notAvailable = checkWeekendAndPastDateTime(sd, ed, st, et);
            boolean overlap = AppointmentDAO.checkOverLapping(start, end);
            
            if(empty){
                warnningLabel.setText("* Must enter each field! *");                
            }else if(notAvailable){
                warnningLabel.setText("* Appointment isn't availale at \npast time or date and weekend! *");
            }else if(overlap){
                warnningLabel.setText("* Appointment is overlapping! *");
            }else{
                
                AppointmentDAO.insert(title, description, location, type, start, end, user, custID, userID, contactID);
                
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
    
    /** 
     * Create formatted specific time string with am, pm indicators and collect in ObersableList. 
     * @return ObservableList of time string.
     */
    public static ObservableList geTimetList(){
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("h:mm a");

        ZoneId nyZone = ZoneId.of("America/New_York");
        LocalDate nyDate = LocalDate.now();
        LocalTime nyTime = LocalTime.of(8, 0);
        LocalDateTime nydt = LocalDateTime.of(nyDate, nyTime);
        ZonedDateTime est = ZonedDateTime.of(nydt, nyZone);

        ZoneId lcZone = ZoneId.systemDefault();
        ZonedDateTime lzdt = est.withZoneSameInstant(lcZone);
        LocalTime localTime = lzdt.toLocalTime(); 
        
        LocalTime start = localTime;
        LocalTime end = localTime.plusHours(14);
        
        ObservableList<String> time = FXCollections.observableArrayList();
        while(start.isBefore(end)){
            time.add(start.format(tf));
                start = start.plusMinutes(30);              
        }        
        return time;
    }
    
    /** Check whether current schedule does not overlap with existing schedules and assure scheduling within in weekdays.
     * @param sd
     * @param ed
     * @param st
     * @param et
     * @return true if checks meet */
    public static boolean checkWeekendAndPastDateTime(LocalDate sd, LocalDate ed, LocalTime st, LocalTime et){
        boolean notAvailable = false;
        
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        int sDay = DayOfWeek.from(sd).getValue();
        int eDay = DayOfWeek.from(ed).getValue();
        
        if(sd.isBefore(today) || ed.isBefore(today) || 
                sDay == 6 || sDay == 7 || eDay == 6 || eDay == 7){
            notAvailable = true;
        }
        else if(sd.isEqual(today) || ed.isEqual(today)){
            if(st.isBefore(now) || et.isBefore(now))
                notAvailable = true;
        }           
        return notAvailable;
    }
    
    /** Check if any fields is missing.
     * @return true if even a field misses and invalid input */
    public boolean checkEmptyFields(){
        boolean empty = false;

        if (titleTf.getText().isEmpty()                
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
