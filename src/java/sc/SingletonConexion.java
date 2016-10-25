/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Silvio
 */
public class SingletonConexion 
{
    public static Connection con; 
    private static SingletonConexion INSTANCE = null;
 

    private SingletonConexion() {
        abrirConexion();
    }
    


    private synchronized static void crearInstancia() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonConexion();
        }
    }

    public static SingletonConexion getInstancia() {
        if (INSTANCE == null) crearInstancia();
        return INSTANCE;
    }

    public static void eliminarInstancia() {
        INSTANCE = null;
        cerrarConexion();
    }    
    

    private boolean abrirConexion() {
        String user = "root";
        String clave = "root";
        String host = "localhost";
        String puerto = "3306";
        String bd = "test";
        String URL = "jdbc:mysql://"+host+":"+puerto+"/"+bd;
 
        try { 
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(URL, user, clave);
        } catch (SQLException e) {
            System.out.println("Error al abrir la conexión.");
            return false;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SingletonConexion.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }     
    

    private static boolean cerrarConexion() {
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión.");
            return false;
        }
        return true;
    }  
}
