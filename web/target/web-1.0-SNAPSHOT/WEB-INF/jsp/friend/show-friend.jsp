<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<br>
<br>
<br>
<div align="center">
    <img src="data:image/jpg;base64, ${encodedPhoto}"
         alt="Responsive image" style="width:120px;height:140px;"
         onerror="this.src='resources/img/noPhotoAvailable.jpg'" class="img-fluid"
         alt="Responsive image">
</div>
<main role="main">
    <div class="main">
        <br/>
        Имя пользователя ${friendAccount.name}
        <br/>
        Фамилия пользователя ${friendAccount.surname}
        <br>
        Отчетство пользователя ${friendAccount.lastName}
        <br>
        Дата регистрации пользователя ${friendAccount.date}
        <br>
        ISQ пользователя ${friendAccount.icq}
        <br>
        домашний адрес ${friendAccount.addressHome}
        <br>
        рабочий адрес ${friendAccount.addressJob}
        <br>
        почта ${friendAccount.email}
        <br>
        о пользователе ${friendAccount.aboutMe}
    </div>
    <br>
    ${friendAccount.id}
    <form action="account-message" method="POST">
        <button type="submit" name="selectUser" value=${friendAccount.id}>Написать сообщение!!!
        </button>
    </form>



    <c:if test="${friendFlag == 1}">
    <!------аватарка, информация пользователя, подать заявку-->
    <form method="post">
        <button type="submit" name="add"> Подать заявку на добавление в друзья</button>
    </form>
    </c:if>
    <c:if test="${friendFlag == 2}">
    Вам отказано в просмотре страницы, пользователь отклонил Вашу заявку
    <!------аватарка, информация пользователя, повторно подать заявку-->
    <br>
    <form modelAttribute="friend" method="post">
        <button type="submit" name="add-account"> Подать заявку на добавление в друзья</button>
    </form>
    </c:if>
    <c:if test="${friendFlag == 0}">
    <br>
    </c:if>
    <br>
    <div class="modal">
        <table>
            <tr>
                <th>Имя пользователя</th>
                <th>дата сообщения</th>
                <th>сообщение</th>
            </tr>
            <c:forEach var="message" items="${messages}">
                <tr>
                    <td><c:out value="${message.usernameSender}"/></td>
                    <td><c:out value="${message.publicationDate}"/></td>
                    <td><c:out value="${message.message}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>
    <form:form action="friend-add-wall-message" class="modal" method="post">
    <textarea name="NewWallMessage" cols="40" rows="3"></textarea>
    <button type="submit">отправить</button>
    </form:form>
</body>
</html>
