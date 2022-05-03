<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.dao.User: Александр
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
<%@ include file="navbar.jsp" %>
<main role="main">
    <form class="modal">
        <br>name: ${account.name}
        <br>surname: ${account.surname}
        <br>lastName: ${account.lastName}
        <br>Date: ${account.date}
        <br>icq: ${account.icq}

        <c:forEach var="phone" items="${account.phones}">
            <c:if test="${phone.phoneType == 0}">
                <br>домашний телефон -
            </c:if>
            <c:if test="${phone.phoneType == 1}">
                <br>рабочий телефон -
            </c:if>
            <c:if test="${phone.phoneType == 2}">
                <br>личный телефон -
            </c:if>
            ${phone.phoneNumber}
        </c:forEach>

        <br>addressHome: ${account.addressHome}
        <br>addressJob: ${account.addressJob}
        <br>email: ${account.email}
        <br>aboutMe: ${account.aboutMe}
    </form>
    <button onclick="location.href='AccountEditSettings'">Изменить данные пользователя</button>
</main>
</body>
</html>
