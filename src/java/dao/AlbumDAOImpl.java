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
    /**
     * Crea el album entregado en la base de datos.
     * *@param album es el album a crear dentro de la base de datos.
     * @return este retorna un int, si es 1 siginifica que se realizo con exito 
     * y -1 en caso contrario.
     */
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
    
    /**
     * Metodo para buscar un album por nombre.
     * *@param albumName representa el nombre del album a buscar.
     * @return retorna el album buscado, en caso de no econtrarlo lo retorna nulo 
     * 
     */
    
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
    
     /**
     * Metodo para buscar todos los albums relacionados a un artista
     * *@param artist representa el nombre del artista a buscar.
     * @return retorna una lista de  albums con el nombre del artista ingresado. 
     * 
     */
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
     /**
     * Metodo para buscar el id del album de acuerdo a un nombre y artista ingresado 
     * *@param albumName representa el nombre del album a buscar.
     * *@param artist representa de el artista.
     * @return retorna el id del album buscado, en caso de fallar retorna -1. 
     * 
     */
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
    
     /**
     * Metodo para eliminar el album de acuerdo al nombre y artista ingresado.
     * *@param albumName representa el nombre del album a eliminar.
     * *@param artist representa el nombre del artista relacionado al album.
     * @return retorna un int, si es 1 se realizo con exito, en caso contrario retorna -1. 
     * 
     */
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
    
     /**
     * Metodo para actualizar la informacion del album en la base de datos.
     * *@param nombreAlbum representa el nombre del album a actualizar.
     * *@param artista representa el nombre del artista relacionado al album.
     * *@param idAlbum representa el id del album a modificar.
     * @return retorna true si se realizo con exito y false en caso contrario. 
     * 
     */
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
     /**
     * Metodo para buscar un album de acuerdo a un id.
     * *@param idAlbum representa el id del album a buscar.
     * @return retorna un album en caso de encontrarlo, en caso contrario retorna nulo. 
     * 
     */
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
         /**
     * Metodo para obtener todas las consultas sql.
     * @return retorna una lista con todas las consusltas sql. 
     * 
     */
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