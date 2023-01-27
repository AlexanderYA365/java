<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 08.02.2022
  Time: 20:57
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Сообщения пользователя</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<main role="main">
    <form action="account-message" class="modal" method="POST">
        <c:if test="${haveMessage == 0}">
            <table>
                <tr>
                    <th>Имя пользователя</th>
                    <th>дата сообщения</th>
                    <th>сообщение</th>
                    <th>ответить</th>
                </tr>
                <c:forEach var="uniqueMessage" items="${uniqueMessages}">
                    <tr>
                        <td><c:out value="${uniqueMessage.usernameReceiving}"/></td>
                        <td><c:out value="${uniqueMessage.publicationDate}"/></td>
                        <td><c:out value="${uniqueMessage.message}"/></td>
                        <td>
                            <button type="submit" name="selectUser"
                                    value=${uniqueMessage.receiverId}>ответить
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </form>
</main>
</body>
</html>
