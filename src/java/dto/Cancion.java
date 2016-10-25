/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import ctrl.Helpers;

/**
 *
 * @author hmoraga
 */
public class Cancion {
    private Integer id;
    private String nombre;
    private String genero;
    private Integer duracion;
    private Integer idAlbum;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = Helpers.getHourFormatToSeconds(duracion);
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
        
    public Integer getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Integer idAlbum) {
        this.idAlbum = idAlbum;
    }    
}
