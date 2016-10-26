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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;

/**
 *
 * @author hmoraga
 */
public class CancionDAOImpl implements CancionDAO
{

    private String INSERT = "INSERT INTO cancion(nombre,genero,duracion,idAlbum) VALUES(?,?,?,?)";
    private String FIND_SONGS_BY_ALBUM_ID = "SELECT * FROM cancion WHERE idAlbum=?";
    private String REMOVE_BY_SONG_ID = "DELETE FROM cancion WHERE id=?";
    private String UPDATE = "UPDATE cancion SET nombre=?,genero=?,duracion=? WHERE id=?";
    private String FIND_BY_ID = "SELECT * FROM cancion WHERE id =?";
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
    public List<Integer> addAll(List<Cancion> songs) throws SQLException
    {
        List<Integer> result = new ArrayList<Integer>();
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

                } catch (SQLException ex)
                {

                    Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println("Error on Cancion's DAO - Create: " + ex.getMessage());
                    result.add(-1);
                }
            } catch (SQLException ex)
            {
                Logger.getLogger(AlbumDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("Error on Cancion's DAO - Create: " + ex.getMessage());
                result.add(-1);
            }
        }
    }

    @Override
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
