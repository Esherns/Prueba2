/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import ctrl.DBAccess;
import dto.Cancion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author diegoMarquez_silvioBelledone
 */
public class CancionDAOImpl implements CancionDAO
{

    private static final String INSERT = "INSERT INTO cancion(nombre,genero,duracion,idAlbum) VALUES(?,?,?,?)";
    private static final String FIND_SONGS_BY_ALBUM_ID = "SELECT * FROM cancion WHERE idAlbum=?";
    private static final String REMOVE_BY_SONG_ID = "DELETE FROM cancion WHERE id=?";
    private static final String UPDATE = "UPDATE cancion SET nombre=?,genero=?,duracion=? WHERE id=?";
    private static final String FIND_BY_ID = "SELECT * FROM cancion WHERE id =?";
    private ResultSetMetaData rsmd;

    /**
     * Map the current row of the given ResultSet to a Cancion.
     *
     * @param resultSet The ResultSet of which the current row is to be mapped
     * to an User.
     * @return The mapped User from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static Cancion map(ResultSet resultSet) throws SQLException
    {
        Cancion song = new Cancion();

        song.setId(resultSet.getInt("id"));
        song.setNombre(resultSet.getString("nombre"));
        song.setGenero(resultSet.getString("genero"));
        song.setDuracion(resultSet.getInt("duracion"));
        song.setIdAlbum(resultSet.getInt("idAlbum"));

        return song;
    }

    @Override
    /**
     * Inserts a Cancion into the database
     *
     * @param song The song that is to be inserted
     * @return an int that represents the insert result (e.g. -1 if error)
     * @throws SQLException If something fails at database level.
     */
    public int create(Cancion song) throws SQLException
    {
        try
        {
            DBAccess acceso = DBAccess.getInstance();

            PreparedStatement op = acceso.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            try
            {

                op.setString(1, song.getNombre());
                op.setString(2, song.getGenero());
                op.setString(3, String.valueOf(song.getDuracion()));
                op.setString(4, String.valueOf(song.getIdAlbum()));

                return op.executeUpdate();

            } catch (SQLException ex)
            {

                Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error on Cancion's DAO - Create: " + ex.getMessage());
                return -1;
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Error on Cancion's DAO - Create: " + ex.getMessage());
            return -1;
        }
    }

    @Override
    /**
     * Inserts a list of Cancion into the database
     *
     * @param songs The group of songs that will be inserted
     * @return A dynamic array of Integers. Each one represents the insert status of its
     * corresponding Cancion
     * @throws SQLException If something fails at database level.
     */
    public List<Integer> addAll(List<Cancion> songs) throws SQLException
    {
        List<Integer> result = new ArrayList<>();
        for (Cancion song : songs)
        {
            try
            {
                DBAccess acceso = DBAccess.getInstance();

                PreparedStatement op = acceso.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

                try
                {

                    op.setString(1, song.getNombre());
                    op.setString(2, song.getGenero());
                    op.setString(3, String.valueOf(song.getDuracion()));
                    op.setString(4, String.valueOf(song.getIdAlbum()));

                    result.add(op.executeUpdate());
                    return result;

                } catch (SQLException ex)
                {

                    Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println("Error on Cancion's DAO - Create: " + ex.getMessage());
                    result.add(-1);
                    return result;
                }
            } catch (SQLException ex)
            {
                Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error on Cancion's DAO - Create: " + ex.getMessage());
                result.add(-1);
                return result;
            }
        }
        return result;
    }

    @Override
    /**
     * Retrieves all the Cancions that pertain to a specified Album
     *
     * @param albumId The album from  which the songs will be returned  
     * @return A dynamic array of Cancions. Every Cancion belong to that album
     * @throws SQLException If something fails at database level.
     */
    public List<Cancion> findByAlbumId(int albumId) throws SQLException
    {
        try
        {
            DBAccess acceso = DBAccess.getInstance();

            PreparedStatement op = acceso.getConnection().prepareStatement(FIND_SONGS_BY_ALBUM_ID);
            List<dto.Cancion> result = new ArrayList<>();

            try
            {
                op.setString(1, String.valueOf(albumId));

                ResultSet rs = op.executeQuery();
                while (rs.next())
                {
                    dto.Cancion s = new dto.Cancion();
                    s.setNombre(rs.getString("nombre"));
                    s.setGenero(rs.getString("genero"));
                    s.setDuracion(rs.getString("duracion"));
                    s.setIdAlbum(Integer.parseInt(rs.getString("idAlbum")));
                    s.setId(Integer.parseInt(rs.getString("id")));
                    result.add(s);
                }

                return result;

            } catch (SQLException ex)
            {

                Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                return result;

            }
        } catch (SQLException ex)
        {
            List<dto.Cancion> result = new ArrayList<>();
            Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return result;

        }
    }

    @Override
    /**
     * Retrieves all the queries used in this DAO
     *
     * @return A list of the queries, in string format, that will be used
     * @throws SQLException If something fails at database level.
     */
    public List<String> getCabeceras() throws SQLException
    {
        List<String> lista = new ArrayList<>();
        lista.add(INSERT);
        lista.add(FIND_SONGS_BY_ALBUM_ID);
        lista.add(REMOVE_BY_SONG_ID);
        lista.add(UPDATE);
        lista.add(FIND_BY_ID);
        return lista;
    }

    @Override
    /**
     * Removes a song from the database
     *
     * @param songId the song to be removed
     * @return true or false, depending on the deletion result
     * @throws SQLException If something fails at database level.
     */
    public boolean removeBySongId(int songId) throws SQLException
    {
        try
        {
            DBAccess acceso = DBAccess.getInstance();

            PreparedStatement op = acceso.getConnection().prepareStatement(REMOVE_BY_SONG_ID);

            try
            {
                op.setString(1, String.valueOf(songId));

                return op.executeUpdate() != 0;

            } catch (SQLException ex)
            {

                Logger.getLogger(CancionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                return false;

            }
        } catch (SQLException ex)
        {

            Logger.getLogger(CancionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
    }

    @Override
    /**
     * Updates a song's data on the database
     *
     * @param song the song to be updated. Its fields will be the new ones
     * @return true or false, depending on the update result
     * @throws SQLException If something fails at database level.
     */
    public boolean update(Cancion song) throws SQLException
    {
        try
        {
            DBAccess acceso = DBAccess.getInstance();

            PreparedStatement op = acceso.getConnection().prepareStatement(UPDATE);

            try
            {
                //"UPDATE cancion SET nombre=?,genero=?,duracion=? WHERE id=?";
                op.setString(1, song.getNombre());
                op.setString(2, song.getGenero());
                op.setInt(3, song.getDuracion());
                op.setInt(4, song.getId());

                return (op.executeUpdate() == 1);

            } catch (SQLException ex)
            {

                Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                return false;

            }
        } catch (SQLException ex)
        {

            Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
    }

    @Override
    /**
     * Looks for a song with a specific Id and then returns it
     *
     * @param song the song to be searched.  
     * @return The song, only if was found. Otherwise, a null value will be returned
     * @throws SQLException If something fails at database level.
     */
    public Cancion findById(int idCancion) throws SQLException
    {
        try {
                DBAccess acceso = DBAccess.getInstance();

                PreparedStatement op = acceso.getConnection().prepareStatement(FIND_BY_ID);
                try {
                    //"SELECT * FROM cancion WHERE id =?"
                    op.setInt(1, idCancion);

                    ResultSet rs = op.executeQuery();
                    if (rs.next()) 
                    {
                       Cancion a = new Cancion();
                       a.setNombre(rs.getString("nombre"));
                       a.setGenero(rs.getString("genero"));
                       a.setDuracion(rs.getString("duracion"));
                       a.setIdAlbum(rs.getInt("idAlbum"));
                       return a;
                    }
                    else
                    {
                        return null;
                    }
                    
                } catch (SQLException ex) {
                    
                    Logger.getLogger(CancionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(CancionDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                return null;
                
            }
    }

}
