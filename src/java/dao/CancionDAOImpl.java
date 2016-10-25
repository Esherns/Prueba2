/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import ctrl.DBAccess;
import dto.Cancion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hmoraga
 */
public class CancionDAOImpl implements CancionDAO{
    private String INSERT = "INSERT INTO cancion(nombre,genero,duracion,idAlbum) VALUES(?,?,?,?)";
    private String FIND_SONGS_BY_ALBUM_ID = "SELECT * FROM cancion WHERE idAlbum=?";
    private String REMOVE_BY_SONG_ID = "DELETE FROM cancion WHERE id=?";
    private String UPDATE = "UPDATE cancion SET nombre=?,genero=?,duracion=? WHERE id=?";
    private String FIND_BY_ID = "SELECT * FROM cancion WHERE id =?";
    private ResultSetMetaData rsmd;
        
    /**
     * Map the current row of the given ResultSet to a Cancion.
     * @param resultSet The ResultSet of which the current row is to be mapped to an User.
     * @return The mapped User from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Cancion map(ResultSet resultSet) throws SQLException {
        Cancion song = new Cancion();
        
        song.setId(resultSet.getInt("id"));
        song.setNombre(resultSet.getString("nombre"));
        song.setGenero(resultSet.getString("genero"));
        song.setDuracion(resultSet.getInt("duracion"));
        song.setIdAlbum(resultSet.getInt("idAlbum"));
        
        return song;
    }
}
