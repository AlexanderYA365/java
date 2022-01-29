<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 16.01.2022
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Social network</title>
</head>
<body>
<div>
    <br>friends
    <br>groups
    <br>my account
    <br>message
    <div>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <c:forEach var="massage" items="${massages}">
    <br>
        <c:out value="${massage.massage}" />
    </c:forEach>
    </div>
</div>
</body>
</html>
