/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import ctrl.Helpers;
import java.io.Serializable;

/**
 *
 * @author hmoraga
 */
public class CancionExtendida implements Serializable{
    private Cancion cancion;
    private Album album;

    public CancionExtendida(Cancion cancion, Album album) {
        this.cancion = cancion;
        this.album = album;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Cancion getCancion() {
        return cancion;
    }

    public void setCancion(Cancion cancion) {
        this.cancion = cancion;
    }
    
    // Helper para entregar la duracion de la cancion en format Hora
    public String getDuracion(){
        return Helpers.getSecondsToHourFormat(cancion.getDuracion());
    }
}
