/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servidorhilos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author manue
 */
public class Conection {
    
    Connection con;
    final String url="jdbc:mysql://localhost:3306/hilos";
    final String user="root";
    final String pass="";
    final String timezone="?useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    public  Connection Conectar() throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url+timezone, user, pass);
        }catch(ClassNotFoundException | SQLException ex){
            Logger.getLogger("DBcon").log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
    return con;
    }
}
