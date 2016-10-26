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
    private static final String INSERT_ALBUM = "INSERT INTO album(nombre,artista) VALUES (?,?)";
    private static final String FIND_ALBUM_BY_ID = "SELECT * FROM album WHERE id=?";
    private static final String FIND_ALBUM_BY_NAME = "SELECT * FROM album WHERE nombre=?";
    private static final String FIND_ALBUMS_BY_ARTIST = "SELECT * FROM album WHERE artista=?";
    private static final String FIND_ID_BY_ALBUM_NAME_ARTIST_NAME = "SELECT id FROM album WHERE nombre=? AND artista=?";
    private static final String DELETE_ALBUM_BY_NAME_AND_ARTIST = "DELETE * FROM album WHERE nombre=? AND artista=?";
    private static final String UPDATE_ALBUM_BY_ID = "UPDATE album SET nombre=?, artista=? WHERE id=?";
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
    @Override
    public int create(Album album)
    {
            try {
                DBAccess acceso = DBAccess.getInstance();

                PreparedStatement op = acceso.getConnection().prepareStatement(INSERT_ALBUM,Statement.RETURN_GENERATED_KEYS);
                
                try {
                    
                    op.setString(1, album.getNombre());
                    op.setString(2, album.getArtista());

                    return op.executeUpdate();
                } catch (SQLException ex) {
                    
                    Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return -1;
                }
            } catch (SQLException ex) {
                Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
    }
    
    @Override
    public Album findByName(String albumName)
    {
                try {
                DBAccess acceso = DBAccess.getInstance();

                PreparedStatement op = acceso.getConnection().prepareStatement(FIND_ALBUM_BY_NAME);
                try {
                    op.setString(1, albumName);

                    ResultSet rs = op.executeQuery();
                    if (rs.next()) 
                    {
                       Album a = new Album();
                       a.setArtista(rs.getString("artista"));
                       a.setNombre(rs.getString("nombre"));
                       a.setId(rs.getInt("id"));
                       return a;
                    }
                    else
                    {
                        return null;
                    }
                    
                } catch (SQLException ex) {
                    
                    Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
                
            }
    }
    
    @Override
    public List<Album> findByArtist(String artist)
    {
            try {
                DBAccess acceso = DBAccess.getInstance();

                PreparedStatement op = acceso.getConnection().prepareStatement(FIND_ALBUMS_BY_ARTIST);
                List<Album> lista = new ArrayList<>();
                
                try {
                    op.setString(1, artist);

                    ResultSet rs = op.executeQuery();
                    while (rs.next()) 
                    {
                       Album a = new Album();
                       a.setArtista(rs.getString("artista"));
                       a.setNombre(rs.getString("nombre"));
                       a.setId(rs.getInt("id"));
                       lista.add(a);
                    }

                    return lista;
                    
                    
                } catch (SQLException ex) {
                    
                    Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return lista;
                    
                }
            } catch (SQLException ex) {
                List<Album> lista = new ArrayList<>();
                Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                return lista;
                
            }
    }
    
    @Override
    public int findId(String albumName, String artist)
    {
            try {
                DBAccess acceso = DBAccess.getInstance();

                PreparedStatement op = acceso.getConnection().prepareStatement(FIND_ID_BY_ALBUM_NAME_ARTIST_NAME);
                
                try {
                    op.setString(1, albumName);
                    op.setString(2, artist);

                    ResultSet rs = op.executeQuery();
                    if (rs.next()) 
                    {    
                       return rs.getInt("id");
                    }

                    else
                    {
                        return -1;
                    }
                    
                    
                } catch (SQLException ex) {
                    
                    Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return -1;
                    
                }
            } catch (SQLException ex) {
                
                Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
                
            }
    }
    
    @Override
    public int deleteAlbumByName(String albumName, String artist)
    {
             try {
                DBAccess acceso = DBAccess.getInstance();

                PreparedStatement op = acceso.getConnection().prepareStatement(DELETE_ALBUM_BY_NAME_AND_ARTIST);
                
                try {
                    
                    op.setString(1, albumName);
                    op.setString(2, artist);
                    
                    op.executeUpdate();
                    return 1;
                    
                    
                } catch (SQLException ex) {
                    
                    Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return -1;
                    
                }
            } catch (SQLException ex) {
                
                Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
                
            } 
    }
    
    @Override
    public boolean updateInfoAlbum(String nombreAlbum, String artista, int idAlbum)
    {
            try {
                DBAccess acceso = DBAccess.getInstance();

                PreparedStatement op = acceso.getConnection().prepareStatement(UPDATE_ALBUM_BY_ID);
                
                try {
                    op.setString(1, nombreAlbum);
                    op.setString(2, artista);
                    op.setInt(3,idAlbum);

                    if (op.executeUpdate()==1) 
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                    
                    
                } catch (SQLException ex) {
                    
                    Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                    
                }
            } catch (SQLException ex) {
                
                Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                return false;
                
            } 
    }
    
    @Override
    public Album findById(int idAlbum)
    {
            try {
                DBAccess acceso = DBAccess.getInstance();

                PreparedStatement op = acceso.getConnection().prepareStatement(FIND_ALBUM_BY_ID);
                try {
                    op.setInt(1, idAlbum);

                    ResultSet rs = op.executeQuery();
                    if (rs.next()) 
                    {
                       Album a = new Album();
                       a.setArtista(rs.getString("artista"));
                       a.setNombre(rs.getString("nombre"));
                       a.setId(rs.getInt("id"));
                       return a;
                    }
                    else
                    {
                        return null;
                    }
                    
                } catch (SQLException ex) {
                    
                    Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
                
            }
    }
    
    @Override
    public List<String> getCabeceras()
    {
        List<String> lista = new ArrayList<>(); 
        lista.add(INSERT_ALBUM);
        lista.add(FIND_ALBUM_BY_ID);
        lista.add(FIND_ALBUM_BY_NAME);
        lista.add(FIND_ALBUMS_BY_ARTIST);
        lista.add(FIND_ID_BY_ALBUM_NAME_ARTIST_NAME);
        lista.add(DELETE_ALBUM_BY_NAME_AND_ARTIST);
        lista.add(UPDATE_ALBUM_BY_ID);
        return lista;
    }
}