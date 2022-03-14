<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 13.02.2022
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Пишем сообщение пользователю: ${massage.usernameSender}</title>
    <style>
        <%@include file='css/style.css' %>
    </style>
</head>
<body>


<form method="post" class = "modal">
    <div>
        <table>
            <tr><th>Имя пользователя</th><th>дата</th><th>сообщение</th></tr>
            <c:forEach var="massage" items="${massageList}">
                <tr><td><c:out value="${massage.usernameSender}"/></td>
                    <td><c:out value="${massage.publicationDate}"/></td>
                    <td><c:out value="${massage.massage}"/></td></tr>
            </c:forEach>
        </table>
    </div>
</form>

<form method="post" class = "modal">
    Сообщение
    <div>
        <textarea name="NewMessage" cols="40" rows="3"></textarea>
        <input type="submit"  value="отправить"/>
    </div>
</form>

</body>
</html>
