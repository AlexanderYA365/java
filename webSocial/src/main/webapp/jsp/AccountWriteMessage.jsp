<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 13.02.2022
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Пишем сообщение пользователю: ${message.usernameSender}</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main">
    <div id="main" class="modal">
        <div class="article1">
            <table>
                <tr>
                    <th>Имя пользователя</th>
                    <th>дата</th>
                    <th>сообщение</th>
                </tr>
                <c:forEach var="message" items="${personalMail}">
                    <tr>
                        <td><c:out value="${message.usernameSender}"/></td>
                        <td><c:out value="${message.publicationDate}"/></td>
                        <td><c:out value="${message.message}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
        <form method="post">
            Сообщение
            <textarea name="NewMessage" cols="40" rows="3"></textarea>
            <input type="submit" value="отправить"/>
        </form>
    </div>
</main>
</body>
</html>
