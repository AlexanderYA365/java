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
    <title>Account massage</title>
</head>
<body>
<table>
    <tr><th>Имя пользователя</th><th>дата сообщения</th><th>сообщение</th><th>ответить</th></tr>
    <c:forEach var="massage" items="${massageList}">
        <input type="hidden" name="accountId" value="${account.id}"/>
        <tr><td><c:out value="${massage.idSender}"/></td>
            <td><c:out value="${massage.publicationDate}"/></td>
            <td><c:out value="${massage.massage}"/></td>
            <td><input type="submit" name="button1" value="ответить"/></td></tr>
    </c:forEach>
</table>
<button onclick="location.href='main.jsp'">На главную</button>
</body>
</html>
