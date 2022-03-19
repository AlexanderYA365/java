<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 30.01.2022
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Группы ${account.name}</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<main role="main">
    <div>
        <table>
            <tr><th>Имя группы</th><th>картинка</th></tr>
        <c:forEach var="group" items="${groups}">
            <tr><td><a href='<c:url value = "/showGroup?id=${group.idGroup}" />'>${group.groupName}</a></td>
                <td><c:out value="${group.logo}"/></td></tr>
        </c:forEach>
        </table>
    </div>
    <br><button onclick="location.href='CreateGroup.jsp'">Создать группу</button>
    <br><button onclick="location.href='AccountFindGroup.jsp'">поиск группы</button>
</main>
</body>
</html>
