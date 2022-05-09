<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.dao.User: Александр
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
<%@ include file="navbar.jsp" %>
<br>
<br>
<br>
<main role="main">
    <form class="modal">
        друзья аккаунта ${account.name}
        <div>
            <table>
                <tr>
                    <th>Имя пользователя</th>
                    <th>Имя друга</th>
                </tr>
                <c:forEach var="friend" items="${friends}">
                    <tr>
                        <td><a href='<c:url value = "/ShowFriend?id=${friend.id}" />'> ${friend.username}</a></td>
                        <td><c:out value="${friend.name}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </form>
    <button onclick="location.href='AddFriendAccount'">добавить друга</button>
</main>
</body>
</html>
