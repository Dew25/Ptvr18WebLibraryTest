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
        ${info}<br>
        
        
        <a href="showLogin">Войти</a><br>
        <a href="logout">Выйти</a><br>
        <a href="showRegistration">Зарегистрироваться</a><br>
       
        <c:choose>
            <c:when test="${userRole.name eq 'USER'}">
                <a href="showListBooks">Список книг</a><br>
                <a href="showChangePassword">Изменить пароль</a><br>
            </c:when>
            <c:when test="${userRole.name eq 'MANAGER'}">
                <a href="showListBooks">Список книг</a><br>
                <a href="showChangePassword">Изменить пароль</a><br>
                <a href="showListReaders">Список читателей</a><br>
                <a href="showPageForGiveBook">Выдать книгу</a><br>
                <a href="showPageForReturnBook">Вернуть книгу</a><br>
                <a href="showAddNewBook">Добавить книгу</a><br>
            </c:when>    
                 <c:when test="${userRole.name eq 'ADMINISTRATOR'}">
                <a href="showListBooks">Список книг</a><br>
                <a href="showChangePassword">Изменить пароль</a><br>
                <a href="showListReaders">Список читателей</a><br>
                <a href="showPageForGiveBook">Выдать книгу</a><br>
                <a href="showPageForReturnBook">Вернуть книгу</a><br>
                <a href="showAddNewBook">Добавить книгу</a><br>
                <a href="showChangeRole">Изменить роль</a>
            </c:when>   
        </c:choose>
</html>
