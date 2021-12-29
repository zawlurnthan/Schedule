/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Zaw L Than
 */

/** Model Class of Customer. */
public class Customer {
    private int customerID;
    private String name;
    private String address;
    private String zipCode;
    private String phone;
    private int divisionID;

    /** Constructor of Customer with all required fields input.
     * @param customerID
     * @param name
     * @param address
     * @param zipCode
     * @param phone
     * @param divisionID */
    public Customer(int customerID, String name, String address, String zipCode, String phone, int divisionID) {
        this.customerID = customerID;
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }    

    /** Constructor of Customer with all required fields input without id input.
     * @param name
     * @param address
     * @param zipCode
     * @param phone
     * @param divisionID */
    public Customer(String name, String address, String zipCode, String phone, int divisionID) {
        this.name = name;
        this.address = address;
        this.zipCode = zipCode;
        this.phone = phone;
        this.divisionID = divisionID;
    }
    
    /** Setter of customer ID.
     * @param customerID */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** Setter of customer name.
     * @param name */
    public void setName(String name) {
        this.name = name;
    }
    
    /** Setter of customer address.
     * @param address */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /** Setter of customer postal code.
     * @param zipCode */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    /** Setter of customer phone.
     * @param phone */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /** Setter of customer division ID.
     * @param divisionID */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }
        
    /** Getter of customer ID.
     * @return customer ID */
    public int getCustomerID() {
        return customerID;
    }
    
    /** Getter of customer name.
     * @return name */
    public String getName() {
        return name;
    }
    
    /** Getter of customer address.
     * @return address */
    public String getAddress() {
        return address;
    }

    /** Getter of customer postal code.
     * @return postal code */
    public String getZipCode() {
        return zipCode;
    }
    
    /** Getter of customer phone.
     * @return phone */
    public String getPhone() {
        return phone;
    }

    /** Getter of customer division ID.
     * @return division ID */    
    public int getDivisionID() {
        return divisionID;
    }        

    /** Return String of object. */    
    @Override
    public String toString(){
        return (Integer.toString(customerID) + " " + name);
    }
}
