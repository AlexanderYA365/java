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
    <div>
        <button onclick="location.href='AccountFriends.jsp'">Account Friends</button>
    </div>
    <div>
        <button onclick="location.href='AccountGroup.jsp'">Account Groups</button>
    </div>
    <div>
        <button onclick="location.href='myAccount.jsp'">My Account</button>
    </div>
    <div>
        <button onclick="location.href='AccountMassage.jsp'">My message</button>
    </div>
    <br>message
    <div>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <c:forEach var="massage" items="${massages}">
            <br>
            <c:out value="${massage.massage}"/>
        </c:forEach>
    </div>
</div>
</body>
</html>
