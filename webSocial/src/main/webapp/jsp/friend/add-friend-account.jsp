<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 31.01.2022
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Поиск друга</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<main role="main">
    <form method="post" class="modal">
        <br>name: <input name="name"/>
        <br><input type="submit" value="Submit"/>
    </form>
    <form method="post" class="modal">
        <div>
            <table>
                <tr>
                    <th>Имя пользователя</th>
                    <th>добавить</th>
                </tr>
                <c:forEach var="account" items="${accounts}">
                    <input type="hidden" name="accountId" value="${account.id}"/>
                    <tr>
                        <td><c:out value="${account.name}"/></td>
                        <td>
                            <button type="submit" name="friendId" value="${account.id}"/>
                            добавить
                        </td>
                            <%--                        <td><input type="submit" name="button1" value="да"/></td>--%>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </form>
</main>
</body>
</html>
