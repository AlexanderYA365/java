<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 08.02.2022
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Сообщения пользователя</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main">
    <form action="AccountWriteMessage.jsp" class="modal">
        <c:if test="${haveMessage == 0}">
            <table>
                <tr>
                    <th>Имя пользователя</th>
                    <th>дата сообщения</th>
                    <th>сообщение</th>
                    <th>ответить</th>
                </tr>
                <c:forEach var="message" items="${messageList}">
                    <tr>
                        <td><c:out value="${message.usernameSender}"/></td>
                        <td><c:out value="${message.publicationDate}"/></td>
                        <td><c:out value="${message.message}"/></td>
                        <td>
                            <button type="submit" name="selectUser"
                                    value=${message.idSender}>ответить
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </form>
</main>
</body>
</html>
