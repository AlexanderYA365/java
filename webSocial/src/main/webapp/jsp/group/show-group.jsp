<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 03.03.2022
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Группа ${group.groupName}</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="../navbar.jsp" %>
<br>
<br>
<br>
<main role="main">
    <c:if test="${application.status == 1}">
        Ваша заявка на рассмотрении
        <!------видно только членов группы если новенький? или только добавляться в группу может?-->
    </c:if>
    <c:if test="${application.status == 2}">
        Вам отказано во вступлении в группу
        <!------видно только членов группы если новенький? или только добавляться в группу может?-->
    </c:if>
    <c:if test="${application.status == 0}">
        Ваша заявка принята
        <!------видно только членов группы если новенький? или только добавляться в группу может?-->
    </c:if>
    <br>Группа - ${members.get(0).group.groupName}
    <br>Описание группы - ${members.get(0).group.info}
    <br>
    <div class="modal">
        <br>Участники:
        <table>
            <tr>
                <th>Имя</th>
                <th>Фамилия</th>
                <th>Должность</th>
            </tr>
            <c:forEach var="member" items="${members}">
                <tr>
                    <td><c:out value="${member.member.name}"/></td>
                    <td><c:out value="${member.member.lastName}"/></td>
                    <c:if test="${member.groupRole == 'MEMBER'}">
                        <td>Участник</td>
                    </c:if>

                    <c:if test="${member.groupRole == 'ADMIN'}">
                        <td>Администратор</td>
                    </c:if>

                    <c:if test="${member.groupRole == 'MODER'}">
                        <td>Модератор</td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </div>

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
    <form:form action="group-add-message" class="modal" method="post">
        <textarea name="NewWallMessage" cols="40" rows="3"></textarea>
        <button type="submit">отправить</button>
    </form:form>

</main>
</body>
</html>
