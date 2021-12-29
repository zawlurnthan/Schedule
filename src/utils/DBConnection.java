/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.*;

/**
 *
 * @author Zaw L Than
 */

/** Create Connection object to database. */
public class DBConnection {
    private static final String db = "jdbc:mysql://localhost:3306/schedule";                 //"jdbc:mysql://wgudb.ucertify.com/WJ07qQX";
    private static final String user = "root";                                       //"U07qQX";
    private static final String password = "P@55w0rd";                                   //"53689105080";
    public static Connection conn = null;
    
    /** Setter of connection to database.
     * @return  */
    public static Connection startConnection(){
        try{
            conn = DriverManager.getConnection(db, user, password);            
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
    /** Stop the connection to database.
     * @throws java.sql.SQLException */
    public static void stopConnection() throws SQLException{
        conn.close();
    }
}
