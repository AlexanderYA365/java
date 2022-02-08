<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 30.01.2022
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Группы ${account.name}</title>
</head>
<body>
<div>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <c:forEach var="group" items="${groups}">
        <br>
        <c:out value="${group.groupName}"/>
    </c:forEach>
</div>
<br>
<button onclick="location.href='CreateGroup.jsp'">Создать группу</button>
<br>
<button onclick="location.href='findGroup.jsp'">поиск группы</button>
<br>
<button onclick="location.href='main.jsp'">На главную</button>
</body>
</html>
