<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 16.01.2022
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Ваша страница</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main">
    <div>
        <table>
            <tr><th>Имя пользователя</th><th>дата сообщения</th><th>сообщение</th><th>ответить</th></tr>
                <c:forEach var="massage" items="${massages}">
                    <tr><td><c:out value="${massage.usernameSender}"/></td>
                        <td><c:out value="${massage.publicationDate}"/></td>
                        <td><c:out value="${massage.massage}"/></td>
                        <td><input type="submit" name="button1" value="ответить"/></td></tr>
                </c:forEach>
        </table>
    </div>
</main>
</body>
</html>
