<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 31.01.2022
  Time: 20:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Друзья аккаунта ${account.name}</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>
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
                        <td><a href='<c:url value = "/show-friend?id=${friend.id}" />'> ${friend.username}</a></td>
                        <td><c:out value="${friend.name}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </form>
    <button onclick="location.href='add-friend-account'">добавить друга</button>
</main>
</body>
</html>
