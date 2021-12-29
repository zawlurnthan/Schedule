/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utils.DBConnection;
import java.sql.*;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Zaw L Than
 */

/** Database Accessing Class for Customer Model . */
public class CustomerDAO {
    
    /** Inserts all parameters to associated table in database. 
     * @param name
     * @param address
     * @param zid
     * @param phone
     * @param user
     * @param divisionID */
    public static void insert(String name, String address, String zid, String phone, String user, int divisionID){
        
        String sql = "INSERT INTO customers VALUES(NULL, ?, ?, ?, ?,NOW(), ?, NOW(), ?, ?);";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, zid);
            ps.setString(4, phone);
            ps.setString(5, user);
            ps.setString(6, user);
            ps.setInt(7, divisionID);
            
            ps.execute();
        }
        catch(SQLException ex){
            ex.getMessage();
        }
        
    }
    
    /** Update a record from table in database.
     * @param id    
     * @param name    
     * @param address    
     * @param zid    
     * @param phone    
     * @param user    
     * @param divisionID */    
    public static void update(int id, String name, String address, String zid, String phone, String user, int divisionID){
       String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, "
               + "Postal_Code = ?, Phone = ?, Last_Update = NOW(), Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?;";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, zid);
            ps.setString(4, phone);
            ps.setString(5, user);
            ps.setInt(6, divisionID);
            ps.setInt(7, id);
            
            ps.execute();
        }
        catch(SQLException ex){
            ex.getMessage();
        } 
    }
    
    /** Delete a record from a table in database searching by id and name input.
     * @param id
     * @param name */
    public static void delete(int id, String name){
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation of Deleting Record");
        alert.setHeaderText("Are you sure to delete selected customer?");
        alert.setContentText("Customer ID: " + id + "\nCustomer Name: " + name);
           
        Optional<ButtonType> result = alert.showAndWait();     
        if(result.get() == ButtonType.OK){
            String sql = "DELETE FROM customers WHERE Customer_ID = ?;";
        
            try (Connection conn = DBConnection.startConnection()){
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.execute();
            }
            catch(SQLException ex){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Not able to delete!");
                a.setContentText("To delete a customer \nfirst delete all associate appointments with selected customer.");
                a.showAndWait();
            }
        }           
    }

    /** Return ObservableList of all customers form database.
     * @return ObservableList */
    public static ObservableList getAllCustomer(){
        
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers;";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int customerID = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zipCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                
                Customer c = new Customer(customerID, name, address, zipCode, phone, divisionID);
                customers.add(c);    
            }     
        }catch(SQLException ex){
            ex.getMessage();
        }            
        return customers;
    }    
    
    /** Return a customer by searching name.
     * @param name
     * @return customer */
    public static Customer getACustomer(String name){
        
        Customer c = null;
        String sql = "SELECT * FROM customers WHERE Customer_Name = ?;";        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("Customer_ID");
                String address = rs.getString("Address");
                String zipCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionID = rs.getInt("Division_ID");
                c = new Customer(id, name, address, zipCode, phone, divisionID);
            }    
        }catch(SQLException ex){
            System.out.println("Issuse: " + ex.getMessage());
        }
        return c;
    }
}
