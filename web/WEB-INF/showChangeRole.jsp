<%-- 
    Document   : showChangeRole
    Created on : Feb 15, 2019, 1:49:31 PM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin panel</title>
    </head>
    <body>
        <h1>Страница администратора</h1>
        Список пользователей:
        <BR>
        <form action="changeRole" method="POST">
            <c:forEach var="role" items="${listRoles}">
                <p><input name="roleId" type="radio" value="${role.id}">${role.name}</p>
            </c:forEach>
            <select name="userId" >
                <c:forEach var="user" items="${listUsers}">
                    <option value="${user.id}">${user.reader.surnfve}</option>
                </c:forEach>
            </select>
            <p><input type="submit" value="Назначить"></p>
        </form>
    </body>
</html>
