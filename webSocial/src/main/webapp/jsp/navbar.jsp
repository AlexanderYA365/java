<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.dao.User: Александр
  Date: 15.03.2022
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/css/navbar.css" rel="stylesheet">
</head>
<body>
<ul class="nav">
    <li><a href="${pageContext.request.contextPath}/account-friends">Мои друзья</a></li>
    <li><a href="${pageContext.request.contextPath}/account-group">Мои группы</a></li>
    <li><a href="${pageContext.request.contextPath}/main">Моя страница</a></li>
    <li><a href="${pageContext.request.contextPath}/account-message">Мои сообщения</a></li>
    <li><a href="${pageContext.request.contextPath}/my-account">Редактирование</a></li>
    <li><a href="${pageContext.request.contextPath}/account-logout">Выйти</a></li>
</ul>
</body>
</html>
