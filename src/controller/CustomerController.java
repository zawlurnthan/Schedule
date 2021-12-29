/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DataAccess.CustomerDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import schedulingApp.Page;

/**
 * FXML Controller class
 *
 * @author Zaw L Than
 */

/** Control Customer main page view. */
public class CustomerController implements Initializable {

    @FXML
    private TableView<Customer> custTableView;
    @FXML
    private TableColumn<Customer, Integer> customerIDCol;
    @FXML
    private TableColumn<Customer, String> nameCol;
    @FXML
    private TableColumn<Customer, String> addressCol;
    @FXML
    private TableColumn<Customer, String> postalCodeCol;
    @FXML
    private TableColumn<Customer, String> phoneCol;
    @FXML
    private TableColumn<Customer, Integer> divisionIDCol;
     
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Customer> temp = CustomerDAO.getAllCustomer();                
        custTableView.setItems(temp);
        
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        divisionIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }    
    
    /** Handle on action of adding customer. */
    @FXML
    private void onAddHandle(ActionEvent event) throws IOException {
        
        String address = "/view/AddCustomer.fxml";
        new Page().setPage(event, address); 
    }
    
    /** Handle on action of modifying customer record. 
     * Pre-populate selected data on all fields of modify customer view. */
    @FXML
    private void onModifyHandle(ActionEvent event) throws IOException {
        
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
            loader.load();

            ModifyCustomerController mpController = loader.getController();
            mpController.getSelectedCustomer(custTableView.getSelectionModel().getSelectedItem());

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
    private void onDeleteHandle(ActionEvent event) throws SQLException, IOException {        
        
        try{
            Customer cust = custTableView.getSelectionModel().getSelectedItem();
            int id = cust.getCustomerID();
            String name = cust.getName();
            CustomerDAO.delete(id, name);
        
            String address = "/view/Customer.fxml";
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
}
