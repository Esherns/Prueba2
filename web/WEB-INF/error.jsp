<%-- 
    Document   : error
    Created on : 22-10-2016, 20:34:59
    Author     : hmoraga
--%>

<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isErrorPage="true" import="java.sql.*" contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagina De Errores Varios</title>
    </head>
    <body>
Message:
<%=exception.getMessage()%>

StackTrace:
<%
	StringWriter stringWriter = new StringWriter();
	PrintWriter printWriter = new PrintWriter(stringWriter);
	exception.printStackTrace(printWriter);
	out.println(stringWriter);
	printWriter.close();
	stringWriter.close();
%>
    </body>
</html>
