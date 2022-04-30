<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 13.01.2022
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание аккаунта</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<form method="post" class="modal">
    <br>Username: <input name="username"/>
    <br>password: <input id="password" name="password" type="password" pattern="^\S{6,}$"
                         onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Must have at least 6 characters' : ''); if(this.checkValidity()) form.password_two.pattern = this.value;"
                         placeholder="Password" required>
    <br>Verify Password: <input id="password_two" name="password_two" type="password" pattern="^\S{6,}$"
                                onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above' : '');"
                                placeholder="Verify Password" required>
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
</body>
</html>
