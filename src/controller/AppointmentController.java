
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DataAccess.AppointmentDAO;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import schedulingApp.Page;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */

/** Control main appointment list views. */
public class AppointmentController implements Initializable {

    @FXML
    private ToggleGroup selectGroup;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, Integer> apIDCol;
    @FXML
    private TableColumn<Appointment, String> titleCol;
    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, String> contactCol;
    @FXML
    private TableColumn<Appointment, String> typeCol;
    @FXML
    private TableColumn<Appointment, String> startCol;
    @FXML
    private TableColumn<Appointment, String> endCol;
    @FXML
    private TableColumn<Appointment, String> customerCol;
    
    @FXML
    private DatePicker datePicker;
    @FXML
    private RadioButton weekRBtn;
    @FXML
    private RadioButton monthRBtn;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        appointmentTableView.setItems(AppointmentDAO.getAllAppointment());
        
        apIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
    }    
    
    /** Handle on action of adding appointment. */
    @FXML
    private void onAddHandle(ActionEvent event) throws IOException {
        
        String address = "/view/AddAppointment.fxml";
        new Page().setPage(event, address); 
    }
    
    /** Handle on action of modifying appointment. 
     * Pre-populate selected data on all fields of modify appointment view. */
    @FXML
    private void onModifyHandle(ActionEvent event) throws IOException{

        try{            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
            loader.load();
            
            ModifyAppointmentController mac = loader.getController();
            mac.getSelectedAppointment(appointmentTableView.getSelectionModel().getSelectedItem());
    
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show(); 
        }
        catch(NullPointerException e){
            e.getMessage();
        }        
    }
    
    /** Delete selected item from table-view and refresh page after delete. */
    @FXML
    private void onDeleteHandle(ActionEvent event) throws IOException {

        Appointment a = appointmentTableView.getSelectionModel().getSelectedItem();           
                
        try{ 
            int id = a.getAppointmentID();
            String type = a.getType();
            AppointmentDAO.delete(id, type);  
        
            String address = "/view/Appointment.fxml";
            new Page().setPage(event, address); 
            
        }catch(NullPointerException e){
            e.getMessage();
        }        
    }
    
    /** Back to Home or Main Page. */
    @FXML
    private void onHomeHandle(ActionEvent event) throws IOException {
        
        String address = "/view/MainPage.fxml";
        new Page().setPage(event, address); 
    }   
    
    /** By selecting week radio-button, show all appointments available within selected week. */
    @FXML
    private void onWeekSelectHandle(ActionEvent event) {
        try{
            LocalDate date = datePicker.getValue();
            int week = date.get(WeekFields.of(Locale.US).weekOfYear());
            appointmentTableView.setItems(AppointmentDAO.getAllAppointmentByWeek(week)); 
        }catch(NullPointerException e){
            System.out.println("Pick a desire week first from the date-picker!");
        }        
    }
    
    /** By selecting month radio-button, show all available appointments within selected month. +*/
    @FXML
    private void onMonthSelectHandle(ActionEvent event) {
        try{
            int m = datePicker.getValue().getMonthValue();
            appointmentTableView.setItems(AppointmentDAO.getAllAppointmentByMonth(m)); 
        }catch(NullPointerException e){
            System.out.println("Pick a month first from the date-picker!");
        }       
    }
    
    /** get required specific week or month to be able to show appointments on table-views. */
    @FXML
    private void onDatePickerHandle(ActionEvent event) {
        if(weekRBtn.isSelected()){
            LocalDate date = datePicker.getValue();
            int week = date.get(WeekFields.of(Locale.US).weekOfYear());
            appointmentTableView.setItems(AppointmentDAO.getAllAppointmentByWeek(week)); 
        }
        else if(monthRBtn.isSelected()){
            int m = datePicker.getValue().getMonthValue();
            appointmentTableView.setItems(AppointmentDAO.getAllAppointmentByMonth(m)); 
        }        
    } 
}
