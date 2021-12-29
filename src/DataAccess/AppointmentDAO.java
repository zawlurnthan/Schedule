/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utils.DBConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Zaw L Than
 */

/** Database Accessing Class for appointment model which has methods with lambda. */
public class AppointmentDAO {
    
    /** Inserts all data to an associate table of database.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param user
     * @param custID
     * @param userID
     * @param contactID */
    public static void insert(String title, String description, String location, 
            String type, Timestamp start, Timestamp end, String user, int custID, int userID, int contactID){
        
        String sql = "INSERT INTO appointments VALUES(NULL, ?, ?, ?, ?, ?, ?, NOW(), ?, NOW(), ?, ?, ?, ?);";
        
        try (Connection conn = DBConnection.startConnection()){               
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setString(7, user);
            ps.setString(8, user);
            ps.setInt(9, custID);
            ps.setInt(10, userID);
            ps.setInt(11, contactID);
                
            ps.execute();    
        }
        catch(SQLException e){
            e.getMessage();
        }
    }
    
    /** Update a record of table in database.
     * @param id
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param user
     * @param custID
     * @param userID
     * @param contactID */
    public static void update(int id, String title, String description, 
            String location, String type, Timestamp start, Timestamp end, 
            String user, int custID, int userID, int contactID){
       
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, "
               + "Type = ?, Start = ?, End = ?, Last_Update = NOW(), Last_Updated_By = ?, "
               + "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?;";
        
        try (Connection conn = DBConnection.startConnection()){                     
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setString(7, user);
            ps.setInt(8, custID);
            ps.setInt(9, userID);
            ps.setInt(10, contactID);
            ps.setInt(11, id);
            
            ps.execute();
        }
        catch(SQLException e){
            e.getMessage();
        } 
    }
    
    /** Delete a record from a table of database in which searching by Appointment id and type.
     * @param id
     * @param type */
    public static void delete(int id, String type){
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation of Deleting Record");
        alert.setHeaderText("Are you sure to delete selected appointment?");
        alert.setContentText("Appointment ID: " + id + 
                "\nAppointment type: " + type);
        
        Optional<ButtonType> result = alert.showAndWait();     
        if(result.get() == ButtonType.OK){
            String sql = "DELETE FROM appointments WHERE Appointment_ID = ?;";
        
            try (Connection conn = DBConnection.startConnection()){                
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.execute();
            }catch(SQLException e){
                e.getMessage();
            }
        }   
    }
    
    /** Return ObservableList of all records of appointments table from database.
     * @return ObservableList */
    public static ObservableList getAllAppointment(){
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        
        String sql = "SELECT Appointment_ID, Title, Description, Location, "
                + "Type, Start, End, User_ID, cus.Customer_Name, con.Contact_Name "
                + "FROM((appointments ap INNER JOIN customers cus ON "
                + "ap.Customer_ID = cus.Customer_ID) INNER JOIN contacts con "
                + "ON ap.Contact_ID = con.Contact_ID) ORDER BY Appointment_ID;";
        
        try (Connection conn = DBConnection.startConnection()){   
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title"); 
                String description = rs.getString("Description"); 
                String location = rs.getString("Location"); 
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                String customerName = rs.getString("Customer_Name");
                int userID = rs.getInt("User_ID");
                String contactName = rs.getString("Contact_Name");
                
                Appointment a = new Appointment(id, title, description, location, 
                        type, start, end, customerName, userID, contactName);
                appointments.add(a);                
            }  
        }catch(SQLException e){
            System.out.println("Problem: " + e.getMessage());
        }
        return appointments;
    }
    
    /** Checks whether selected date and time are already in record.
     * @param start
     * @param end
     * @return true if timestamps match.  */
    public static boolean checkOverLapping(Timestamp start, Timestamp end){
        
        boolean overlap = false;
        String sql = "SELECT * FROM appointments WHERE "
                + "(Start <= ? AND End >= ?) "
                + "OR (Start <= ? AND End >= ?) "
                + "OR Start = ? OR End = ? ;";
        
        try (Connection conn = DBConnection.startConnection()){            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, start);
            ps.setTimestamp(2, start);
            ps.setTimestamp(3, end);
            ps.setTimestamp(4, end);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                overlap = true;
            }
        }
        catch(SQLException e){
            e.getMessage();
        }
        return overlap;
    }
    
    /** Checks whether selected date and time are already in record as well only for Modify Appointment.
     * @param start
     * @param end
     * @param id
     * @return true if timestamps match. */
    public static boolean checkOverLappingForModiy(Timestamp start, Timestamp end, int id){
        boolean overlap = false;
        String sql = "SELECT * FROM appointments WHERE "
                + "(Start <= ? AND End >= ?) OR "
                + "(Start <= ? AND End >= ?) OR "
                + "Start = ? OR End = ? AND Appointment_ID != ?;";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, start);
            ps.setTimestamp(2, start);
            ps.setTimestamp(3, end);
            ps.setTimestamp(4, end);
            ps.setTimestamp(5, start);
            ps.setTimestamp(6, end);
            ps.setInt(7, id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                overlap = true;
            }
        }catch(SQLException e){
            e.getMessage();
        }
        return overlap;
    }
    
    /** Check if there is upcoming appointment within 15 minutes
     * @return true if there is an appointment. */
    public static boolean checkUpcomingAppointment(){
        
        ObservableList<Appointment> ap = getAllAppointment();
        LocalDateTime lcTime = LocalDateTime.now();        
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("MMM d, yyyy  h:mm a");          
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        for (Appointment a : ap){
            LocalDateTime sTime = a.getStart().toLocalDateTime();
            long interval = ChronoUnit.MINUTES.between(lcTime, sTime);
            
            if(interval > 0 && interval <= 15){
                int id = a.getAppointmentID();

                alert.setTitle("UpComing Appointment");
                alert.setHeaderText("You have an appointment in " 
                                + interval + " minutes.");
                alert.setContentText("Appointment ID:    " + id +
                                "\nDate and Time: " + sTime.format(tf));
                alert.showAndWait();    
                return true; // use this to stop loop 
            }
        }
        alert.setTitle("Information");
        alert.setHeaderText("No appointment yet.");
        alert.setContentText("There is no upcoming appointment within 15 minutes.");
        alert.showAndWait();       
        return false;
    }    
    
    
    /** Return ObservableList of all type of Appointments
     * and use lambda for moving appointments and collecting types.
     * @return ObservableList */
    public static ObservableList getAllType(){
        ObservableList<String> type = FXCollections.observableArrayList();
        ObservableList<Appointment> ap = getAllAppointment();
        
        /** Use lambda for each loop in adding type to new container. */
        ap.forEach(a -> {
            type.add(a.getType());
        });
        
        return type;   
    }
    
    /** Return ObservableList of all appointments searching by customer id input.
     * @param custID
     * @return ObservableList */
    public static ObservableList getAllAppointmentbyCustomerID(int custID){
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?;";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, custID);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title"); 
                String description = rs.getString("Description"); 
                String location = rs.getString("Location"); 
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                
                Appointment a = new Appointment(id, title, description, location, 
                        type, start, end, custID, userID, contactID);
                appointments.add(a);
            }
        }catch(SQLException e){
            e.getMessage();
        }
        return appointments;
    }
    
    
    /** Return ObservableList of all appointments searching by contact id input.
     * @param contactID
     * @return ObservableList */
    public static ObservableList getAllAppointmentbyContactID(int contactID){
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?;";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title"); 
                String description = rs.getString("Description"); 
                String location = rs.getString("Location"); 
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int custID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");                
                
                Appointment a = new Appointment(id, title, description, location, 
                        type, start, end, custID, userID, contactID);
                appointments.add(a);
            }
        }catch(SQLException e){
            e.getMessage();
        }
        return appointments;
    }
    
    
    
    /** Return ObservableList of all appointments searching by type and month input.
     * @param type
     * @param m
     * @return ObservableList */
    public static ObservableList getAllAppointmentbyTypeAndMonth(String type, int m){
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Type = ? AND MONTH(Start) = ?;";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, type);
            ps.setInt(2, m);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title"); 
                String description = rs.getString("Description"); 
                String location = rs.getString("Location"); 
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                int custID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");
                
                Appointment a = new Appointment(id, title, description, location, 
                        type, start, end, custID, userID, contactID);
                appointments.add(a);
            }
        }catch(SQLException e){
            e.getErrorCode();
        }
        return appointments;
    }
    
    /** Return all months in a years.
     * @return ObservableList */
    public static ObservableList getAllMonth(){
        Month[] m = Month.values();
        ObservableList<Month> months = FXCollections.observableArrayList();        
        
        months.addAll(Arrays.asList(m));
        return months;
    }
    
    /** Return ObservableList of all appointments searching by number value of week in a year as 1 to 53.
     * @param week
     * @return ObservableList */
    public static ObservableList getAllAppointmentByWeek(int week){
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
                String sql = "SELECT Appointment_ID, Title, Description, Location, "
                + "Type, Start, End, User_ID, cus.Customer_Name, con.Contact_Name "
                + "FROM((appointments ap INNER JOIN customers cus ON "
                + "ap.Customer_ID = cus.Customer_ID) INNER JOIN contacts con "
                + "ON ap.Contact_ID = con.Contact_ID) WHERE WEEKOFYEAR(Start) = ? "
                        + "ORDER BY Appointment_ID;";                              
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, week);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title"); 
                String description = rs.getString("Description"); 
                String location = rs.getString("Location"); 
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                String customerName = rs.getString("Customer_Name");
                int userID = rs.getInt("User_ID");
                String contactName = rs.getString("Contact_Name");
                
                Appointment a = new Appointment(id, title, description, location, 
                        type, start, end, customerName, userID, contactName);
                appointments.add(a);
            }
        }catch(SQLException e){
            System.out.println("Problem: " + e.getMessage());
        }
        return appointments;
    }
    
    /** Return ObservableList of appointments searching by number value of month as 1 to 12.
     * @param month
     * @return ObservableLis */
    public static ObservableList getAllAppointmentByMonth(int month){
        
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();        
        String sql = "SELECT Appointment_ID, Title, Description, Location, "
                + "Type, Start, End, User_ID, cus.Customer_Name, con.Contact_Name "
                + "FROM((appointments ap INNER JOIN customers cus ON "
                + "ap.Customer_ID = cus.Customer_ID) INNER JOIN contacts con "
                + "ON ap.Contact_ID = con.Contact_ID) WHERE MONTH(Start) = ? ORDER BY Appointment_ID;";
        
        try (Connection conn = DBConnection.startConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, month);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title"); 
                String description = rs.getString("Description"); 
                String location = rs.getString("Location"); 
                String type = rs.getString("Type");
                Timestamp start = rs.getTimestamp("Start");
                Timestamp end = rs.getTimestamp("End");
                String customerName = rs.getString("Customer_Name");
                int userID = rs.getInt("User_ID");
                String contactName = rs.getString("Contact_Name");
                
                Appointment a = new Appointment(id, title, description, location, 
                        type, start, end, customerName, userID, contactName);
                appointments.add(a);
            }
        }catch(SQLException e){
            System.out.println("Problem: " + e.getMessage());
        }
        return appointments;
    }
}
