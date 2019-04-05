<%-- 
    Document   : index
    Created on : Jan 14, 2019, 9:28:52 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Библиотека</title>
    </head>
    <body>
        <H1>Добро пожаловать в нашу библиотеку</H1>
        <p id="info">${info}</p>
        <a id="showLogin" href="showLogin">Войти</a><br>
        <a id="logout" href="logout">Выйти</a><br>
        <a id="showRegistration" href="showRegistration">Зарегистрироваться</a><br>
    </body>        
</html>
