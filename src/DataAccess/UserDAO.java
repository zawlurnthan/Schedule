/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utils.DBConnection;
import java.sql.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Zaw L Than
 */

/** Database Accessing Class for User Model. */
public class UserDAO {
    public static User user;
    
    /** Return ObservableList of all users.
     * @return ObservableList */
    public static ObservableList getAllUser(){
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users;";
        
        try (Connection conn = DBConnection.startConnection()){
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            
            while(rs.next()){
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String password = rs.getString("Password");
                
                User u = new User(id, name, password);
                users.add(u);
            }
        }catch(SQLException e){e.getMessage();}
        return users;
    }
    
    /** Return a user by id input.
     * @param id
     * @return user */
    public static User getUser(int id){
        User u = null;
        String sql = "SELECT * FROM users WHERE User_ID = ?;";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int uId = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String password = rs.getString("Password");
                
                u = new User(uId, name, password);
            }
        }catch(SQLException e){e.getMessage();}    
        return u;
    }
    
    /** Checks user name and password are correct and return Boolean.
     * @param userName
     * @param password
     * @return true if input username and password are correct. */
    
    public static boolean checkUserLogin(String userName, String password){
        boolean exit = false;
        String sql = "SELECT * FROM users WHERE User_Name = ? AND Password = ?;";
        
        try (Connection conn = DBConnection.startConnection()){                    
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, password); 
            ResultSet rs = ps.executeQuery();

            if(rs.next()){   
                int id = rs.getInt("User_ID");
                user = new User();
                user.setId(id);
                user.setName(userName);
                user.setPassword(password);
                
                trackingLoginActivity(userName, true);  
                exit = true;
                
            }else{
                trackingLoginActivity(userName, false);
                exit = false;
            }
        }catch(SQLException e){
            e.getMessage();
        }
    return exit;
}
    /** Track user login activity when and where it takes place.
     * @param username
     * @param success */
    public static void trackingLoginActivity(String username, boolean success){  
        
        String filename = "src/files/login_activity.txt";
        ZonedDateTime zdt = ZonedDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM/dd/yyyy  h:mm a z");
        String stamp = dtf.format(zdt);
        
        try{
            FileWriter fw = new FileWriter(filename, true);
            try (PrintWriter pw = new PrintWriter(fw)) {
                pw.println("User " + username + (success ? " Successfully logged in at " : " gave invalid login at ") + stamp);
            }
        }
        catch(IOException e){
            e.getMessage();
            System.out.println("Something went wrong!");
        }        
    }
}
