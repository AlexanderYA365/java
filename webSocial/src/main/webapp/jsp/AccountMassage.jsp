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
<form action="AccountWriteMassage.jsp" class = "modal">
    <table>
        <tr><th>Имя пользователя</th><th>дата сообщения</th><th>сообщение</th><th>ответить</th></tr>
        <c:forEach var="massage" items="${massageList}">
            <tr><td><c:out value="${massage.usernameSender}"/></td>
                <td><c:out value="${massage.publicationDate}"/></td>
                <td><c:out value="${massage.massage}"/></td>
                <td><button type="submit" name="selectUser"
                            value=${massage.idSender}>ответить</button></td></tr>
        </c:forEach>
    </table>
</form>
<button onclick="location.href='main.jsp'">На главную</button>

</body>
</html>
