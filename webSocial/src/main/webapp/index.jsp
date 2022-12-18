<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 15.01.2022
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Добро пожаловать</title>
    <style>
        <%@include file='resources/css/style.css' %>
    </style>
</head>
<body>
<p align="center">
    Добро пожаловать
</p>

<div class="form">
    <form class="modal" modelAttribute="account" action="${pageContext.request.contextPath}/index.jsp" method="POST">
        <fieldset>
            <legend>Введите данные:</legend>
            <c:if test="${param.error == true}">
                <label class="error">
                    Ошибка при вводе логина или пароль
                </label>
            </c:if>
            <div class="field">
                <label>имя пользователя:</label><input id="username" type="text" name="username">
            </div>
            <div class="field">
                <label>пароль:</label> <input id="password" type="password" name="password">
            </div>
            <div class="checkbox">
                <label>
                    <input type="checkbox" name="remember-me"> Запомнить меня
                </label>
            </div>
            <div class="field">
                <button onclick="href='index.jsp'">войти</button>
            </div>
        </fieldset>
    </form>
    <button class="reg" onclick="location.href='registration-account'">Зарегистрировать новый аккаунт</button>
</div>

</body>
</html>