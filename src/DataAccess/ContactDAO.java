/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import utils.DBConnection;
import java.sql.*;

/**
 *
 * @author Zaw L Than
 */

/** Database Accessing Class for Contact Model. */
public class ContactDAO {
    
    /** Return ObservableList of all Contacts form table of database.
     * @return ObservableList */
    public static ObservableList<Contact> getAllContact(){
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts;";
        
        try (Connection conn = DBConnection.startConnection()){            
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact c = new Contact(id, name, email);
                contacts.add(c);
            }
        }catch(SQLException e){
            e.getMessage();
        }
        return contacts;        
    }
    
    /** Return a contact searching by name.
     * @param name
     * @return contact */
    public static Contact getContact(String name){
        Contact c = null;
        String sql = "SELECT * FROM contacts WHERE Contact_Name = ?;";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("Contact_ID");
                String email = rs.getString("Email");
                c = new Contact(id, name, email);
            }
        }catch(SQLException e){e.getMessage();}
    return c;    
    }
}
