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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;

/**
 *
 * @author hmoraga
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

    public List<Cancion> getAll() throws Exception
    {
        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try
        {

            return (List<Cancion>) session.createCriteria(Cancion.class).list();

        } catch (Exception e)
        {

            session.getTransaction().rollback();
            session.close();
            System.err.println(e.getMessage());
            throw e;
        } finally
        {
            session.close();

        }
    }

    @Override
    public int create(Cancion c) throws SQLException
    {

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try
        {

            session.save(c);
            session.getTransaction().commit();
            session.close();
            return 0;
        } catch (Exception e)
        {

            session.getTransaction().rollback();
            session.close();
            System.err.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Integer> addAll(List<Cancion> songs) throws SQLException
    {
        List<Integer> result = null;

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        for (Cancion song : songs)
        {
            String nombre = song.getNombre();
            String genero = song.getGenero();
            int duracion = song.getDuracion();
            int idAlbum = song.getIdAlbum();

            Query query = session.createSQLQuery(INSERT);
            query.setString(1, nombre);
            query.setString(2, genero);
            query.setString(3, String.valueOf(duracion));
            query.setString(4, String.valueOf(idAlbum));

            if (result != null)
            {
                result.addAll(query.list());
            } else
            {
                result = query.list();
            }
        }

        return result;
    }

    @Override
    public List<Cancion> findByAlbumId(int albumId) throws SQLException
    {
        List<Cancion> result = null;

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        Query query = session.createSQLQuery(FIND_SONGS_BY_ALBUM_ID);
        query.setString(1, String.valueOf(albumId));

        result = (List<Cancion>) query.list();

        return result;
    }

    @Override
    public List<String> getCabeceras() throws SQLException
    {
        List<String> result = null;

        Session session = NewHibernateUtil.getSessionFactory().openSession();
        Query query = session.createSQLQuery("SELECT * FROM CANCION");

        ClassMetadata classMetadata = NewHibernateUtil.getSessionFactory().getClassMetadata(Album.class);
        List<String> propertyNames = Arrays.asList(classMetadata.getPropertyNames());

        return result;
    }

    @Override
    public boolean removeBySongId(int songId) throws SQLException
    {
        List<Cancion> result = null;

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        Query query = session.createSQLQuery(REMOVE_BY_SONG_ID);
        query.setString(1, String.valueOf(songId));

        result = (List<Cancion>) query.list();

        return result != null;
    }

    @Override
    public boolean update(Cancion song) throws SQLException
    {
        List<Cancion> result = null;

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        Query query = session.createSQLQuery(UPDATE);
        query.setString(1, song.getNombre());
        query.setString(2, song.getGenero());
        query.setString(3, String.valueOf(song.getDuracion()));

        result = (List<Cancion>) query.list();

        return result != null;
    }

    @Override
    public Cancion findById(int idCancion) throws SQLException
    {
        Cancion result = null;

        Session session = NewHibernateUtil.getSessionFactory().openSession();

        Query query = session.createSQLQuery(UPDATE);
        query.setString(1,String.valueOf(idCancion));

        result = (Cancion) query.list().get(0);

        return result;
    }

}
