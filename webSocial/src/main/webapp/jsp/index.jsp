<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 15.01.2022
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Добро пожаловать</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<p align="center">
    Добро пожаловать
</p>
<div class="form">
       <form action="index.jsp" class = "modal">
           <fieldset>
               <legend>Введите данные:</legend>
            <c:if test="${errorLogin == 1}">
                <label class="error">
                    Ошибка при вводе логина или пароль
                </label>
            </c:if>
            <div class="field">
                <label>имя пользователя:</label><input type="text" name="username">
            </div>
            <div class="field">
                <label>пароль:</label> <input type="password" name="password">
            </div>
            <div class="field">
                <button onclick>войти</button>
            </div>
           </fieldset>
       </form>
    <button class="reg" onclick="location.href='RegistrationAccount.jsp'">Зарегистрировать новый аккаунт</button>
</div>

</body>
</html>


