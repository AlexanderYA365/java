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
</head>
<body>
<br>друзья аккаунта ${account.name}
<div>
    <c:forEach var="friend" items="${friendsname}">
        <br>
        <c:out value="${friend}"/>
    </c:forEach>
</div>
<br>
<button onclick="location.href='main.jsp'">На главную</button>
<br>
<button onclick="location.href='AddFriendAccount.jsp'">добавить друга</button>
</body>
</html>
