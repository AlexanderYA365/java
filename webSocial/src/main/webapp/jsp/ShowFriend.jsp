<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
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
    <div class="main">
        <br/>
        Имя пользователя ${friendAccount.name}
        <br/>
        Фамилия пользователя ${friendAccount.surname}
        <br>
        Отчетство пользователя ${friendAccount.lastName}
        <br>
        Дата регистрации пользователя ${friendAccount.date}
        <br>
        ISQ пользователя ${friendAccount.icq}
        <br>
        домашний адрес ${friendAccount.addressHome}
        <br>
        рабочий адрес ${friendAccount.addressJob}
        <br>
        почта ${friendAccount.email}
        <br>
        о пользователе ${friendAccount.aboutMe}
    </div>
    <br>

    <c:if test="${friendFlag == 1}">
    Вы можете подать заявку в друзья
    <!------аватарка, информация пользователя, подать заявку-->
    </c:if>
    <c:if test="${friendFlag == 2}">
    Вам отказано в просмотре страницы, пользователь отклонил Вашу заявку
    <!------аватарка, информация пользователя, повторно подать заявку-->
    </c:if>
    <c:if test="${friendFlag == 0}">
    Вы можете просматривать страницу, оставлять сообщения на стене
    <!------страница видна пользователю-->
    <br>
    <div>
        <table>
            <tr>
                <th>Имя пользователя</th>
                <th>дата сообщения</th>
                <th>сообщение</th>
            </tr>
            <c:forEach var="message" items="${messages}">
                <tr>
                    <td><c:out value="${message.usernameSender}"/></td>
                    <td><c:out value="${message.publicationDate}"/></td>
                    <td><c:out value="${message.message}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>

    </c:if>
    <br>
</body>
</html>
