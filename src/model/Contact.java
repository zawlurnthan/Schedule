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

/** Model class of Contact. */
public class Contact {
    private int id;
    private String name;
    private String email;
    
    /** Constructor with all fields input.
     * @param id
     * @param name
     * @param email */
    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    
    /** Setter of Contact ID.
     * @param id */
    public void setId(int id) {
        this.id = id;
    }
    
    /** Setter of Contact name.
     * @param name */
    public void setName(String name) {
        this.name = name;
    }
    
    /** Setter of Contact email.
     * @param email */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /** Getter of Contact ID.
     * @return Contact ID */
    public int getId() {
        return id;
    }
    
    /** Getter of Contact ID.
     * @return Contact name */
    public String getName() {
        return name;
    }
    
    /** Getter of Contact ID.
     * @return email */
    public String getEmail() {
        return email;
    }
    
    /** Return string of object. */
    @Override
    public String toString(){
        return (Integer.toString(id) + " " + name);
    }
}
