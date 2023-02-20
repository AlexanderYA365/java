<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 16.01.2022
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Странница ${account.name}</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<main role="main">
    <form class="modal">
        <br>Имя: ${account.name}
        <br>Фамилия: ${account.surname}
        <br>Отчество: ${account.lastName}
        <br>Дата: ${account.date}
        <br>icq: ${account.icq}
        <c:forEach var="phone" items="${account.phones}">
            <c:if test="${phone.phoneType == 0}">
                <br>домашний телефон -
            </c:if>
            <c:if test="${phone.phoneType == 1}">
                <br>рабочий телефон -
            </c:if>
            <c:if test="${phone.phoneType == 2}">
                <br>личный телефон -
            </c:if>
            ${phone.phoneNumber}
        </c:forEach>
        <br>Домашний адрес: ${account.addressHome}
        <br>Рабочий адрес: ${account.addressJob}
        <br>email: ${account.email}
        <br>Обо мне: ${account.aboutMe}
        <br/>
        <form method="GET" action="edit-account-settings">
            <button>Изменить данные пользователя</button>
        </form>
        <br/>
        <form method="GET" action="save-account-settings">
            <button>Сохранить профиль</button>
        </form>
        <br/>
        <form method="POST" enctype="multipart/form-data" action="update-account-settings">
            Выберите файл для загрузки: <input type="file" name="uploadXml">
            <br><input type="submit" value="сохранить"/>
        </form>
    </form>
</main>
</body>
</html>
