/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dto.Album;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author hmoraga
 */
public interface AlbumDAO {
    public int create(Album album) throws SQLException, IllegalArgumentException;
    public Album findByName(String albumName) throws SQLException;
    public List<Album> findByArtist(String artist) throws SQLException;
    public int findId(String albumName, String artist) throws SQLException;
    public int deleteAlbumByName(String albumName, String artist) throws SQLException;
    public List<String> getCabeceras() throws SQLException; // me retorna las cabeceras de la consulta
    public boolean updateInfoAlbum(String nombreAlbum, String artista, int idAlbum) throws SQLException;
    public Album findById(int idAlbum) throws SQLException;
}
