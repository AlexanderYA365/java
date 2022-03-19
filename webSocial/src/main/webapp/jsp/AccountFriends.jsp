<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 31.01.2022
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Друзья аккаунта ${account.name}</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main">
    <br>друзья аккаунта ${account.name}
    <div>
        <table>
            <tr><th>Имя пользователя</th><th>Имя друга</th><th>написать сообщение</th></tr>
            <c:forEach var="friend" items="${friends}">
                <tr><td><c:out value="${friend.username}"/></td>
                    <td><c:out value="${friend.name}"/></td>
                    <td><input type="submit" name="button1" value="написать"/></td></tr>
            </c:forEach>
        </table>
    </div>
    <button onclick="location.href='AddFriendAccount.jsp'">добавить друга</button>
</main>
</body>
</html>
