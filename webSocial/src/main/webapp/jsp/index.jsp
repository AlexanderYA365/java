<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 15.01.2022
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<form action = "index.jsp">
    <label>username:
        <input type="text" name="username">
    </label>

    <label>password:
        <input type="password" name="password">
    </label>
    <div>
        <button onclick>go</button>
</div>
</form>
<div>
<button onclick="location.href='Account.jsp'">Registered new account</button>
</div>
</body>
</html>
