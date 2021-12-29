
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utils.DBConnection;
import java.sql.*;

/**
 *
 * @author Zaw L Than
 */

/** Database Accessing Class for Country Model . */
public class CountryDAO {
    
    /** Return ObservableList of all Country form table of database.
     * @return ObservableList */
    public static ObservableList getAllCountry(){
        
        ObservableList<Country> countries = FXCollections.observableArrayList();
        String sql = "SELECT Country_ID, Country FROM countries;";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int countryID = rs.getInt("Country_ID");
                String name = rs.getString("Country");

                Country c = new Country(countryID, name);
                countries.add(c);
            } 
        }catch(SQLException ex){ex.getMessage();}
        
        return countries;
    }
    
    /** Return a country searching by id input.
     * @param id
     * @return country */
    public static Country getCountry(int id){
        
        Country c = null;
        String sql = "SELECT * FROM countries WHERE Country_ID = ?;";
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String name = rs.getString("Country");
                
                c = new Country(id, name);
            }  
        }catch(SQLException ex){ex.getMessage();}
        return c;
    }
}
