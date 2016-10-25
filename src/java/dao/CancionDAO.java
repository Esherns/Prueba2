/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import dto.Cancion;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author hmoraga
 */
public interface CancionDAO {
    public int create(Cancion song)  throws SQLException;
    public List<Integer> addAll(List<Cancion> songs) throws SQLException;
    public List<Cancion> findByAlbumId(int albumId) throws SQLException;
    public List<String> getCabeceras() throws SQLException;
    public boolean removeBySongId(int albumId) throws SQLException;
    public boolean update(Cancion song) throws SQLException;
    public Cancion findById(int idCancion) throws SQLException;
}
