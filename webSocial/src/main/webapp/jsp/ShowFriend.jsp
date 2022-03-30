<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 22.03.2022
  Time: 22:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main">
    <div class="main">
        Имя пользователя ${friendAccount.name}
        Фамилия пользователя ${friendAccount.surname}
        Отчетство пользователя ${friendAccount.lastName}
        Дата регистрации пользователя ${friendAccount.date}
        ISQ пользователя ${friendAccount.icq}
        домашний адрес ${friendAccount.addressHome}
        рабочий адрес ${friendAccount.addressJob}
        почта ${friendAccount.email}
        о пользователе ${friendAccount.aboutMe}
    </div>

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
