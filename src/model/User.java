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

/** Model class of User. */
public class User {
    private int id;
    private String name;
    private String password;

    /** Constructor of class without arguments. */
    public User() {
    }
    
    /** Constructor of class with all field members input.
     * @param id
     * @param name
     * @param password */
    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    /** Setter of User ID.
     * @param id */
    public void setId(int id) {
        this.id = id;
    }

    /** Setter of User Name.
     * @param name */
    public void setName(String name) {
        this.name = name;
    }

    /** Setter of User password.
     * @param password */
    public void setPassword(String password) {
        this.password = password;
    }

    /** Getter of User ID.
     * @return user ID */
    public int getId() {
        return id;
    }
    
    /** Setter of User name.
     * @return name */
    public String getName() {
        return name;
    }

    /** Setter of User password.
     * @return password */
    public String getPassword() {
        return password;
    } 

    /** Return String object. */
    @Override
    public String toString(){
        return (Integer.toString(id) + " " + name);
    }  
}
