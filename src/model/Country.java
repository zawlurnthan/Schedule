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

/** Model class fo Country. */
public class Country {
    private int CountryID;
    private String Name;
    
    /** Constructor with three fields input.
     * @param CountryID
     * @param Name */
    public Country(int CountryID, String Name) {
        this.CountryID = CountryID;
        this.Name = Name;
    }

    /** Setter of Country ID.
     * @param CountryID */
    public void setCountryID(int CountryID) {
        this.CountryID = CountryID;
    }

    /** Setter of country name.
     * @param Name */
    public void setName(String Name) {
        this.Name = Name;
    }

    /** Getter of country ID.
     * @return country ID */
    public int getCountryID() {
        return CountryID;
    }
    
    /** Getter of country name.
     * @return name */
    public String getName() {
        return Name;
    }
    
    /** Return String of object. */
    @Override
    public String toString(){
        return(Integer.toString(CountryID) + " " + Name);
    }
}
