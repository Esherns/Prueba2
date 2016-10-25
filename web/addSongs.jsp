<%-- 
    Document   : addSongs
    Created on : 16-10-2016, 19:30:43
    Author     : hmoraga
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css">
        <title>Agregar canciones y &aacute;lbumes</title>
    </head>
    <body>
        <div class="username"><p>${username}</p></div>
        <form action="CreateAddForm.do" method="GET">
            <table>
                <thead>
                    <tr>
                        <th>&Aacute;lbum (required)</th>
                        <th>Artista (required)</th>
                        <th>Cantidad de canciones (required)</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input type="text" name="album" size="30" maxlength="50" placeholder="album title here" value="${album}" required></td>
                        <td><input type="text" name="artista" size="30" maxlength="50" placeholder="artist here" value="${artista}" required></td>
                        <td><input type="text" name="canciones" value="${canciones}" required></td>
                        <td><input type="submit" name="envio" value="agregar"></td>
                    </tr>
                <c:if test="${errorMsg!=null}">
                    <tr>
                        <td colspan="4" class="errorMsg"><c:out value="${errorMsg}" /></td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </form>
        <br />
    <c:if test="${canciones!=null}">
        <form action="AddSongs.do" method="POST">
            <table>
                <thead>
                    <tr>
                        <th>Canci&oacute;n</th>
                        <th>G&eacute;nero musical</th>
                        <th>Duraci&oacute;n</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach begin="1" end="${canciones}" step="1">
                    <tr>
                        <td><input type="text" name="cancion" placeholder="song name here" size="30" maxlength="50" required></td>
                        <td><input type="text" name="genero" placeholder="music style here" size="30" maxlength="50" required></td>
                        <td><input type="text" name="duracion" placeholder="hh:mm:ss" pattern="^(?:(?:([01]?\d|2[0-3]):)?([0-5]?\d):)?([0-5]?\d)$" size="8" required></td>
                    </tr>
                </c:forEach>
                    <tr>
                        <td colspan="2"></td>
                        <td><input type="submit" name="Crear" value="crear"></td>
                    </tr>
                </tbody>
            </table>
        </form>
    </c:if>
    </body>
</html>
