<%-- 
    Document   : showBook
    Created on : Mar 7, 2019, 10:14:49 AM
    Author     : Melnikov
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Выбранная для просмотра книга</title>
    </head>
    <body>
        <h1>Просмотр книги!</h1>
        Обложка: <br>
        <img src="insertFile/${cover.path}"  ><br>
        Заголовок: ${book.name}<br>
        Автор: ${book.author}<br>
        Доступно книг: ${book.count}
    </body>
</html>
