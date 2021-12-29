/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import utils.DBConnection;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
/**
 *
 * @author Zaw L Than
 */

/** Database Accessing Class for Division Model. */
public class DivisionDAO {    
    
    /** This method return ObservaleList of all divisions form database.
     * @return ObservableList */
    public static ObservableList getAllDivision(){
        
        ObservableList<Division> states = FXCollections.observableArrayList();   
        String sql = "SELECT Division_ID, Division, COUNTRY_ID FROM first_level_divisions;";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        
            while(rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID");

                Division d = new Division(divisionID, name, countryID);
                states.add(d);
            }   
        } catch (SQLException e) {e.getMessage();}           
        return states;
    }    
    
    /** Return ObservableList of selected division with id input.
     * @param id
     * @return ObservableList */
    public static ObservableList getSelectedDivision(int id){
        
        ObservableList<Division> states = FXCollections.observableArrayList(); 
        String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE COUNTRY_ID = ?;";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
        
            while(rs.next()){
                int divisionID = rs.getInt("Division_ID");
                String name = rs.getString("Division");

                Division d = new Division(divisionID, name, id);
                states.add(d);
            }   
        } catch (SQLException e) {e.getMessage();}           
        return states;
    }   
    
    /** Return selected division by id input.
     * @param id
     * @return Division */
    public static Division getDivision(int id){
        
        Division d = null;
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?;";
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String name = rs.getString("Division");
                int countryID = rs.getInt("COUNTRY_ID"); 
                
                d = new Division(id, name, countryID);
            }
        }catch(SQLException ex){ex.getMessage();}
        return d;
    }
}

