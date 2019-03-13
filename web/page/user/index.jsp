<%-- 
    Document   : index
    Created on : Mar 13, 2019, 2:56:23 PM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <H1>Добро пожаловать в нашу библиотеку</H1>
        ${info}<br>
        <a href="showLogin">Войти</a><br>
        <a href="logout">Выйти</a><br>
        <a href="showRegistration">Зарегистрироваться</a><br>
        <a href="showListBooks">Список книг</a><br>
    </body>
</html>
