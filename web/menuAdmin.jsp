<%-- 
    Document   : menuAdmin
    Created on : 16-10-2016, 4:31:39
    Author     : hmoraga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css">
        <title>Men&uacute; Principal</title>
    </head>
    <body>
        <div class="username"><p>${username}</p></div>
        <nav>
        <ul>
            <li><a href="addSongs.jsp">Agregar canciones</a></li>
            <li><a href="listSongs.jsp">Listar canciones</a></li>
            <li><a href="Salir.do">Salir</a></li>
	</ul>
        </nav>
    </body>
</html>
