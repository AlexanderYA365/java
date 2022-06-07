<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.dao.User: Александр
  Date: 16.01.2022
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Ваша страница</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="navbar.jsp" %>
<br>
<br>
<br>
<main role="main" class="modal">
    <form method="post" class="modal">
        Сообщение
        <textarea name="NewWallMessage" cols="40" rows="3"></textarea>
        <input type="submit" value="отправить"/>
    </form>

    <div>
        <form method="post" class="modal">
            <table>
                <tr>
                    <th>Имя пользователя</th>
                    <th>дата сообщения</th>
                    <th>сообщение</th>
                    <th>ответить</th>
                    <th>удалить</th>
                </tr>
                <c:forEach var="message" items="${messages}">
                    <tr>
                        <td><c:out value="${message.usernameSender}"/></td>
                        <td><c:out value="${message.publicationDate}"/></td>
                        <td><c:out value="${message.message}"/></td>
                        <td><button type="submit" name="replyAccount" value="${message.id}"/>ответить</td>
                        <td><button type="submit" name="deleteText" value="${message.id}"/>удалить</td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </div>
</main>
</body>
</html>
