<%-- 
    Document   : login
    Created on : 15-10-2016, 1:39:14
    Author     : hmoraga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/estilos.css">
        <title>Login Page</title>
    </head>
    <body>
        <div class="loginFrame">
        <form action="Login.do" method="POST">
            <table class="loginTable">
                <col><col>
                <tr>
                    <td>Username:</td>
                    <td class="loginTable"><input type="text" name="user" value="" size="15" required></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td class="loginTable"><input type="password" name="pass" value="" size="15" required></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" name="Ingresar"></td>
                </tr>
                <c:if test="${errorMsg!=null}">
                    <tr>
                        <td colspan="2" class="errorMsg"><c:out value="${errorMsg}" /></td>
                    </tr>
                </c:if>
            </table>
        </form>   
        </div>
    </body>
</html>
