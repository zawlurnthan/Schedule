/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;

/**
 *
 * @author Zaw L Than
 */

/** Model class of Appointment. */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp start;
    private Timestamp end;
    private int customerID;
    private String customerName;
    private int userID;
    private int contactID;  
    private String contactName;
    
    /** Constructor without arguments. */
    public Appointment() {
    }
    
    /** Constructor with all fields members including customer name and contact name.
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerName
     * @param userID
     * @param contactName */
    public Appointment(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end, String customerName, int userID, String contactName) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerName = customerName;
        this.userID = userID;
        this.contactName = contactName;
    }

    /** Constructor with all fields members without customer name and contact name
     * @param appointmentID.
     * @param title
     * @param description
     * @param location
     * @param type
     * @param start
     * @param end
     * @param customerID
     * @param userID
     * @param contactID*/
    public Appointment(int appointmentID, String title, String description, String location, String type, Timestamp start, Timestamp end, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }    
    
    /** Setter of appointment ID.
     * @param appointmentID */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }
    
    /** Setter of appointment title.
     * @param title */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /** Setter of appointment description.
     * @param description */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /** Setter of appointment location.
     * @param location */
    public void setLocation(String location) {
        this.location = location;
    }
    
    /** Setter of appointment type.
     * @param type */
    public void setType(String type) {
        this.type = type;
    }
    
    /** Setter of appointment start time and date.
     * @param start */
    public void setStart(Timestamp start) {
        this.start = start;
    }
    
    /** Setter of appointment start time and date.
     * @param end */
    public void setEnd(Timestamp end) {
        this.end = end;
    }
    
    /** Setter of customer ID in appointment.
     * @param customerID */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** Setter of user ID in appointment.
     * @param userID */
    public void setUserID(int userID) {
        this.userID = userID;
    }
    
    /** Setter of contact ID in appointment.
     * @param contactID */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
    
    /** Setter of customer name in appointment.
     * @param customerName */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    /** Setter of contact name in appointment.
     * @param contactName */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    /** Getter of appointment ID.
     * @return appointment ID */
    public int getAppointmentID() {
        return appointmentID;
    }
    
    /** Getter of appointment title.
     * @return title */
    public String getTitle() {
        return title;
    }
    
    /** Getter of appointment description.
     * @return description */
    public String getDescription() {
        return description;
    }
    
    /** Getter of appointment location.
     * @return location */
    public String getLocation() {
        return location;
    }
    
    /** Getter of appointment type.
     * @return  */
    public String getType() {
        return type;
    }
    
    /** Getter of appointment start timestamp.
     * @return start timestamp */
    public Timestamp getStart() {
        return start;
    }
    
    /** Getter of appointment end timestamp.
     * @return end timestamp */
    public Timestamp getEnd() {
        return end;
    }
    
    /** Getter of customer ID in appointment.
     * @return customer ID */
    public int getCustomerID() {
        return customerID;
    }
    
    /** Getter of user ID in appointment.
     * @return user ID */
    public int getUserID() {
        return userID;
    }
    /** Getter of contact ID in appointment.
     * @return contact ID */
    public int getContactID() {
        return contactID;
    }  
    
    /** Getter of customer name in appointment.
     * @return customer name */
    public String getCustomerName() {
        return customerName;
    }
    
    /** Getter of contact name of appointment.
     * @return contact name */
    public String getContactName() {
        return contactName;
    }
}
