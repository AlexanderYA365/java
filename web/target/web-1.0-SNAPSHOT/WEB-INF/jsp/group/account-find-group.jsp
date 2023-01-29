<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 09.02.2022
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Поиск групп</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<main role="main">
    <form method="post">
        <br>GroupName: <input name="GroupName"/>
        <br><input type="submit" value="Submit"/>
    </form>
    <form method="post">
        <div>
            <table>
                <tr>
                    <th>Имя группы</th>
                    <th>добавить</th>
                </tr>
                <c:forEach var="group" items="${findGroups}">
                    <input type="hidden" name="groupId"
                           value="${group.groupId}"/>
                    <tr>
                        <td><c:out value="${group.groupName}"/></td>
                        <td><input type="submit" name="button1" value="добавить"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </form>
</main>
</body>
</html>
