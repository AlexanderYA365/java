<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 30.01.2022
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создать группу</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main">
    <form method="post" class = "modal">
        <br>name: <input name="name"/>
        <br>logo: <input name="logo"/>
        <br><input type="submit" value="Submit"/>
    </form>
</main>>
</body>
</html>
