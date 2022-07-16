<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.dao.User: Александр
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
    <form action="account-write-message" class="modal">
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
                        <td><c:out value="${uniqueMessage.usernameSender}"/></td>
                        <td><c:out value="${uniqueMessage.publicationDate}"/></td>
                        <td><c:out value="${uniqueMessage.message}"/></td>
                        <td>
                            <button type="submit" name="selectUser"
                                    value=${uniqueMessage.senderId}>ответить
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
