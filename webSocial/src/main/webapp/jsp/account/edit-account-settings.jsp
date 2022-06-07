<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.dao.User: Александр
  Date: 30.01.2022
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Изменение данных пользователя</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">

</head>
<body>
<%@ include file="../navbar.jsp" %>
<main role="main">
    <form method="POST" class="modal" onsubmit="return ValidPhone();">
        <br>Имя: <input name="name" value="${account.name}"/>
        <br>Фамилия: <input name="surname" value="${account.surname}"/>
        <br>Отчество: <input name="lastName" value="${account.lastName}"/>
        <br>Дата рождения: <input name="date" type="date" value="${account.date}"/>
        <br>icq: <input name="icq" value="${account.icq}"/>
        <br>Домашний адресс: <input name="addressHome" value="${account.addressHome}"/>
        <br>Рабочий адресс: <input name="addressJob" value="${account.addressJob}"/>
        <br>email: <input name="email" value="${account.email}"/>
        <br>Обо мне: <input name="aboutMe" value="${account.aboutMe}"/>
        <c:if test="${account.role == 0}">
            <br>роль <select id="typeRole" name="typeRole">
            <option value="USER" selected>Пользователь</option>
        </select>
        </c:if>

        <c:if test="${account.role == 1}">
            <br>роль <select id="typeRole" name="typeRole">
            <option value="USER">Пользователь</option>
            <option value="ADMIN" selected>Администратор</option>
        </select>
        </c:if>
        <c:forEach var="phone" items="${account.phones}">
            <c:if test="${phone.phoneType == 0}">
                <div id='phoneDiv'>
                    <input type="hidden" name="phoneId" value="${phone.id}"/>
                    <br><input class="phone" name="phone" value="${phone.phoneNumber}"/>
                    <select name="typePhone">
                        <option value="HOME" selected>Домашний</option>
                        <option value="WORK">Рабочий</option>
                        <option value="ADDITIONAL">Личный</option>
                    </select>
                    <button type='button' name="button" class='buttonDelete'>
                        удалить телефон
                    </button>
                </div>
            </c:if>
            <c:if test="${phone.phoneType == 1}">
                <div id='phoneDiv'>
                    <input type="hidden" name="phoneId" value="${phone.id}"/>
                    <br><input class="phone" name="phone" value="${phone.phoneNumber}"/>
                    <select name="typePhone">
                        <option value="HOME">Домашний</option>
                        <option value="WORK" selected>Рабочий</option>
                        <option value="ADDITIONAL">Личный</option>
                    </select>
                    <button type='button' name="button" class='buttonDelete'>
                        удалить телефон
                    </button>
                </div>
            </c:if>
            <c:if test="${phone.phoneType == 2}">
                <div id='phoneDiv'>
                    <input type="hidden" name="phoneId" value="${phone.id}"/>
                    <br><input class="phone" name="phone" value="${phone.phoneNumber}"/>
                    <select name="typePhone">
                        <option value="HOME">Домашний</option>
                        <option value="WORK">Рабочий</option>
                        <option value="ADDITIONAL" selected>Личный</option>
                    </select>
                    <button type='button' name="button" class='buttonDelete'>
                        удалить телефон
                    </button>
                </div>
            </c:if>
        </c:forEach>
        <br>
        <button type='button' name="button" id='button' class='button'
                onclick="createBewPhone(); addMask();">добавить телефон
        </button>
        <br><input type="submit" class='save' value="сохранить"/>
    </form>

</main>

<script src="${pageContext.request.contextPath}/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.maskedinput.js"></script>
<script src="${pageContext.request.contextPath}/js/edit-account-settings.js"></script>

</body>
</html>