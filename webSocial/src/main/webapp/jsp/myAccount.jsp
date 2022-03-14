<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 16.01.2022
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Странница ${account.name}</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<form class = "modal">
<br>name: ${account.name}
<br>surname: ${account.surname}
<br>lastName: ${account.lastName}
<br>Date: ${account.date}
<br>icq: ${account.icq}
<br>addressHome: ${account.addressHome}
<br>addressJob: ${account.addressJob}
<br>email: ${account.email}
<br>aboutMe: ${account.aboutMe}
</form>
<button onclick="location.href='EditAccountSettings.jsp'">Изменить данные пользователя</button>
<button onclick="location.href='main.jsp'">На главную</button>
</body>
</html>
