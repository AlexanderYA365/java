<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.dao.User: Александр
  Date: 30.01.2022
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменение данных пользователя</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<%@ include file="navbar.jsp" %>
<main role="main">
    <form method="post" class="modal">
        <br>name: <input name="name"/>
        <br>surname: <input name="surname"/>
        <br>lastName: <input name="lastName"/>
        <br>Date: <input name="Date"/>
        <br>phone: <input name="phone"/>
        <br>icq: <input name="icq"/>
        <br>addressHome: <input name="addressHome"/>
        <br>addressJob: <input name="addressJob"/>
        <br>email: <input name="email"/>
        <br>aboutMe: <input name="aboutMe"/>
        <br><input type="submit" value="Submit"/>
    </form>
</main>
</body>
</html>
