<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 16.01.2022
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Ваша страница</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="navbar.jsp" %>
<br>
<c:if test="${account.role == 1}">
    <br>
    <button onclick="location.href='${pageContext.request.contextPath}/admin/admin-panel'">панель администратра</button>
</c:if>

<div align="center">
    <img src="data:image/jpg;base64, ${encodedPhoto}"
         alt="Responsive image" style="width:120px;height:140px;"
         onerror="this.src='resources/img/noPhotoAvailable.jpg'" class="img-fluid"
         alt="Responsive image">
</div>
<main role="main">
    <form class="modal">
        <br>Имя: ${account.name}
        <br>Фамилия: ${account.surname}
        <br>Отчество: ${account.lastName}
        <br>Дата рождения: ${account.date}
        <br>icq: ${account.icq}
        <c:forEach var="phone" items="${account.phones}">
            <c:if test="${phone.phoneType == 0}">
                <br>домашний телефон:
            </c:if>
            <c:if test="${phone.phoneType == 1}">
                <br>рабочий телефон:
            </c:if>
            <c:if test="${phone.phoneType == 2}">
                <br>личный телефон:
            </c:if>
            ${phone.phoneNumber}
        </c:forEach>
        <br>Домашний адрес: ${account.addressHome}
        <br>Рабочий адрес: ${account.addressJob}
        <br>email: ${account.email}
        <br>Обо мне: ${account.aboutMe}
    </form>
</main>
<br>
<main role="main" class="modal">
    <form method="post" class="modal" action="${pageContext.request.contextPath}/sendWallMessage">
        Сообщение
        <textarea name="NewWallMessage" cols="40" rows="3"></textarea>
        <input type="submit" value="отправить"/>
    </form>

    <div>
        <form method="post" class="modal" action="${pageContext.request.contextPath}/sendWallMessage">
            <table>
                <tr>
                    <th>Имя пользователя</th>
                    <th>дата сообщения</th>
                    <th>сообщение</th>
                    <th>ответить</th>
                    <th>удалить</th>
                </tr>
                <c:forEach var="message" items="${messages}">
                    <tr>
                        <td><c:out value="${message.usernameSender}"/></td>
                        <td><c:out value="${message.publicationDate}"/></td>
                        <td><c:out value="${message.message}"/></td>
                        <td>
                            <button type="submit" name="replyAccount" value="${message.id}"/>
                            ответить
                        </td>
                        <td>
                            <button type="submit" name="deleteText" value="${message.id}"/>
                            удалить
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </form>
    </div>
</main>
</body>
</html>
