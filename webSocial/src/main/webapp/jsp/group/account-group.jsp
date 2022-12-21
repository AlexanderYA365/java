<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 30.01.2022
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Группы ${account.name}</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<br>
<br>
<br>
<main role="main">
    <form class="modal">
        группы аккаунта ${account.name}
        <div>
            <table>
                <tr>
                    <th>Имя группы</th>
                    <th>картинка</th>
                </tr>
                <c:forEach var="groupMember" items="${groupMembers}">
                    <tr>
                        <td>
                            <a href='<c:url value = "show-group?id=${groupMember.group.groupId}" />'>${groupMember.group.groupName}</a>
                        </td>
                        <td><c:out value="${groupMember.group.logo}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </form>
    <br>
    <button onclick="location.href='create-group'">Создать группу</button>
    <br>
    <button onclick="location.href='account-find-group'">поиск группы</button>
</main>
</body>
</html>
