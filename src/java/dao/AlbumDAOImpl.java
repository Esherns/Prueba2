/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import ctrl.DBAccess;
import dto.Album;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hmoraga
 */
public class AlbumDAOImpl implements AlbumDAO{
    private static String INSERT_ALBUM = "INSERT INTO album(nombre,artista) VALUES (?,?)";
    private static String FIND_ALBUM_BY_ID = "SELECT * FROM album WHERE id=?";
    private static String FIND_ALBUM_BY_NAME = "SELECT * FROM album WHERE nombre=?";
    private static String FIND_ALBUMS_BY_ARTIST = "SELECT * FROM album WHERE artista=?";
    private static String FIND_ID_BY_ALBUM_NAME_ARTIST_NAME = "SELECT id FROM album WHERE nombre=? AND artista=?";
    private static String DELETE_ALBUM_BY_NAME_AND_ARTIST = "DELETE * FROM album WHERE nombre=? AND artista=?";
    private static String UPDATE_ALBUM_BY_ID = "UPDATE album SET nombre=?, artista=? WHERE id=?";
    private ResultSetMetaData rsmd;
            
    /**
     * Map the current row of the given ResultSet to an Album.
     * @param resultSet The ResultSet of which the current row is to be mapped to an Album.
     * @return The mapped Album from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Album map(ResultSet resultSet) throws SQLException {
        Album album = new Album();
        
        album.setId(resultSet.getInt("id"));
        album.setNombre(resultSet.getString("nombre"));
        album.setArtista(resultSet.getString("artista"));
        
        return album;
    }
}