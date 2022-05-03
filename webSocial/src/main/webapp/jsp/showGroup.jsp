<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.dao.User: Александр
  Date: 03.03.2022
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Группа ${group.groupName}</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<%@ include file="navbar.jsp" %>
<br>
<br>
<br>
<main role="main">
    <c:if test="${newUserGroup == 1}">
        Ваша заявка на рассмотрении
        <!------видно только членов группы если новенький? или только добавляться в группу может?-->
    </c:if>
    <c:if test="${newUserGroup == 2}">
        Вам отказано во вступлении в группу
        <!------видно только членов группы если новенький? или только добавляться в группу может?-->
    </c:if>
    <c:if test="${newUserGroup == 0}">
        Ваша заявка принята
        <!------видно только членов группы если новенький? или только добавляться в группу может?-->
    </c:if>
    <br>name - ${group}
</main>
</body>
</html>
