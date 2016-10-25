/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ctrl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author hm/jb
 */
public class DBAccess {
    private static Connection conn;
    private static DBAccess instance;
    
    private DBAccess(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //connect DB
            conn = DriverManager.getConnection("jdbc:mysql://localhost/dej4501","root","root");
        } catch(SQLException | ClassNotFoundException e)
        {
            System.err.println(e.getMessage());             
        }
    }
     
    public static DBAccess getInstance()
    {
            if (instance == null) {
            instance = new DBAccess();
        }
        return instance;
    }
    
    public Connection getConnection(){
        if (instance == null) {
            instance = new DBAccess();
        }
        
        return conn;
    }
    
    /**
    *
    *Método utilizado para realizar las instrucciones: INSERT, DELETE y UPDATE
    *@param sql Cadena que contiene la instrucción SQL a ejecutar
    *@return estado regresa el estado de la ejecución, true(éxito) o false(error)
    *
    */
    public boolean ejecutarComando(String sql)
    {
       try {
          Statement sentencia = conn.createStatement();
          sentencia.executeUpdate(sql);
       } catch (SQLException ex) {
          ex.printStackTrace();
          return false;
       }
       return true;
    } 

    public PreparedStatement ejecutarConParametros(String maqueta){
        PreparedStatement op;
        try {
            op = conn.prepareStatement(maqueta);
        } catch (SQLException ex) {
            System.out.println("Error al crear objeto PreparedStatement(2): " + ex);
            return null;
        }
        return op;
    }
    
    /**
    *
    *Método utilizado para realizar la instrucción SELECT
    *@param sql Cadena que contiene la instrucción SQL a ejecutar
    *@return resultado regresa los registros generados por la consulta
    *
    */
    public ResultSet ejecutarConsulta(String sql)
    {
       ResultSet resultado;
       try {
          Statement sentencia = conn.createStatement();
          resultado = sentencia.executeQuery(sql);
       } catch (SQLException ex) {
          ex.printStackTrace();
          return null;
       }

       return resultado;
    }   
    
    public void cerrarAcceso()
    {
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }    
}
