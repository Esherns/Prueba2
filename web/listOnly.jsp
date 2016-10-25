<%-- 
    Document   : listOnly
    Created on : 23-10-2016, 2:50:46
    Author     : hmoraga
--%>

<%@page import="dao.CancionDAOImpl"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css">
        <title>Listar Canciones</title>
    </head>
    <body>
        <div class="username"><p>${username}</p></div>
        <form action="CargarCanciones.do" method="GET">
            <table>
                <tr>
                    <td>
                        <label for="searchBy">Listar por:</label>
                        <select name="searchBy" id="searchBy">
                            <c:if test="${roles==null}">
                                <option value="Album">Album</option>
                                <option value="Artista">Artista</option>
                            </c:if>
                            <c:if test="${roles!=null}">
                                <option value="${selected}" selected>${selected}</option>
                                <c:forEach items="${roles}" var="rol">
                                    <c:if test="${rol!=selected}">
                                        <option value="${rol}">${rol}</option>
                                    </c:if>    
                                </c:forEach>            
                            </c:if>
                        </select>
                    </td>
                    <td>
                        <input type="text" name="data" size="40" maxlength="60" value="${data}">
                    </td>
                    <td id="left">
                        <input type="submit" value="submit" name="Consultar">
                    </td>
                </tr>
            </table>
        </form>
        <hr>
            <table id="resultados">
            <c:if test="${listaCanciones!=null}">
                <thead>
                    <tr>
                        <th>Nombre</th>
                        <th>G&eacute;nero</th>
                        <th>Duraci&oacute;n</th>
                        <th>Album</th>
                        <th>Artista</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listaCanciones}" var="cancionExtendida">
                    <tr>
                        <!-- aqui mostrar los campos indicados en las cabeceras -->
                    </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="4"></td>
                        <td><a href="Salir.do">Salir</a></td>
                    </tr>
                </tbody>
            </c:if>        
            <c:if test="${listaCanciones==null}">
                <tr><td class="errorMsg">${errorMsg}</td></tr>
                <tr><td><a href="Salir.do">Salir</a></td></tr>
            </c:if>
            </table> 
    </body>
</html>
