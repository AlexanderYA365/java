<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 26.03.2023
  Time: 19:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Отказ в заявках</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<%@ include file="../navbar.jsp" %>
<br>
${deniedFriends}
<br>
<br>
<table id="table-denied">
    <tr>
        <th>id</th>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Отчество</th>
        <th>Действие</th>
    </tr>
</table>
<button id="previous">предыдущие</button>
<button id="next">следующие</button>
<body>
<script>
    $('#table-denied').append($('<tr>')
        .append($('<td>').append("text1"))
        .append($('<td>').append("text2"))
        .append($('<td>').append("text3"))
        .append($('<td>').append("text4"))
        .append($('<td>').append("text5"))
    )
</script>
</body>
</html>
