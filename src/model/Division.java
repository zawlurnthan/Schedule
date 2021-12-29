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

/** Model class of Division. */
public class Division {
    private int divisionID;
    private String name;
    private int countryID;
    
    /** Constructor of Division class with all required fields input.
     * @param divisionID
     * @param Name
     * @param countryID */
    public Division(int divisionID, String Name, int countryID) {
        this.divisionID = divisionID;
        this.name = Name;
        this.countryID = countryID;
    }

    /** Setter of Division ID.
     * @param divisionID */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /** Setter of Division Name.
     * @param name */
    public void setName(String name) {
        this.name = name;
    }

    /** Setter of Country ID of Division.
     * @param countryID */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /** Getter of Division ID.
     * @return division ID */
    public int getDivisionID() {
        return divisionID;
    }
    
    /** Getter of Division Name.
     * @return name */
    public String getName() {
        return name;
    }

    /** Getter of Country ID of Division.
     * @return country ID */
    public int getCountryID() {
        return countryID;
    }      
    
    /** Return String of object. */
    @Override
    public String toString(){
        return(Integer.toString(divisionID) + " " + name);
    }
}
