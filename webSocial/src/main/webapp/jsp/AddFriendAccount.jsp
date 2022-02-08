<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 31.01.2022
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post">
    <br>name: <input name="name"/>
    <br><input type="submit" value="Submit"/>
</form>
<form method="post">
<div>

    <c:forEach var="account" items="${accounts}">
        <input type="hidden" name="accountId" value="${account.id}"/>
        ${room.id}
        <br> <c:out value="${account.name}"/>
        <input type="submit" name="button1" value="add"/>
    </c:forEach>
</div>
</form>
<br>
<button onclick="location.href='main.jsp'">На главную</button>

</body>
</html>
