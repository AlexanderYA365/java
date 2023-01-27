<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 30.01.2022
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Создать группу</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<main role="main">
    <form:form action="create-group" class="modal" modelAttribute="group" method="post">
        <br>Введите желаемое имя группы<input name="groupName"/>
        <br>Выберите аватар для загрузки: <input type="file" name="logo"><br/>
        <br>Введите описание группы<textarea name="info"> </textarea>
        <br>
        <button type="submit">отправить</button>
    </form:form>
</main>
</body>
</html>
