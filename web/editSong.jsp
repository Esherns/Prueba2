<%-- 
    Document   : editSong
    Created on : 22-10-2016, 16:19:14
    Author     : hmoraga
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css">
        <title>Editando cancion ${cancionExtendida.cancion.nombre}</title>
    </head>
    <body>
        <!-- completar todos los ??? -->
        <div class="username"><p>${username}</p></div>
        <form action="UpdateSong.do" method="POST">
            <input type="hidden" name="idAlbum" value="???">
            <input type="hidden" name="idCancion" value="???">
            <table>
                <tr>
                    <td>Nombre:</td>
                    <td><input type="text" name="nombre" value="???"></td>
                </tr>
                <tr>
                    <td>G&eacute;nero:</td>
                    <td><input type="text" name="genero" value="???"></td>
                </tr>
                <tr>
                    <td>Duracion:</td>
                    <td><input type="text" name="duracion" value="${cancionExtendida.duracion}"></td>
                </tr>
                <tr>
                    <td>Album:</td>
                    <td><input type="text" name="nombreAlbum" value="???" readonly></td>
                </tr>
                <tr>
                    <td>Artista:</td>
                    <td><input type="text" name="artista" value="???" readonly></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" name="submit" value="Enviar"></td>
                </tr>
            </table>
        </form>
    </body>
</html>
