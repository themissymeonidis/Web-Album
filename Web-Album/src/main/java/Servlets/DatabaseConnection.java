/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author muvox
 */
public class DatabaseConnection {
    protected static Connection initializeDatabase()
        throws SQLException, ClassNotFoundException
    {
        // Initialize all the information regarding
        // Database Connection
        String dbDriver = "com.mysql.cj.jdbc.Driver";
        String dbURL = "jdbc:mysql:// localhost:3306/users?serverTimezone=UTC";
        String dbUsername = "root";
        String dbPassword = "##Themis1##";
  
        Class.forName(dbDriver);
        Connection con = DriverManager.getConnection(dbURL,
                                                     dbUsername, 
                                                     dbPassword);
        return con;
    }
    
}
