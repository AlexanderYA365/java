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
<c:if test="${group.idGroupCreator == account.id}">
    <form:form action="group-admin-panel" class="modal-start" method="GET">
        <button type="submit">Панель управления</button>
    </form:form>
</c:if>

<main role="main">
    <c:if test="${groupFlag >= 2}">
        <form:form action="group-add-members" class="modal" method="post">
            <button type="submit"> Подать заявку на добавление в группу</button>
        </form:form>
        <c:if test="${groupFlag == 2}">
            <br>
            Вам отказано в просмотре страницы, пользователь отклонил Вашу заявку
        </c:if>
    </c:if>
    <br>

    <div class="container">
        <div class="row">
            <div class="col-md-8">
                Группа - ${members.get(0).group.groupName}
                Описание группы - ${members.get(0).group.info}
            </div>
            <div class="col-6 col-md-4">
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
                            <c:if test="${member.groupRole == 'SUBSCRIBER'}">
                                <td>Подписавщийся</td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>


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
                    <c:if test="${member.groupRole == 'SUBSCRIBER'}">
                        <td>Подписавщийся</td>
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
            <c:forEach var="message" items="${groupMessages}">
                <tr>
                    <td><c:out value="${message.usernameSender}"/></td>
                    <td><c:out value="${message.publicationDate}"/></td>
                    <td><c:out value="${message.message}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>
    <c:if test="${groupFlag == 0}">
        <form:form action="group-add-message" class="modal" method="post">
            <textarea name="NewWallMessage" cols="40" rows="3"></textarea>
            <button type="submit">отправить</button>
        </form:form>
        <form:form action="delete-members" class="modal" method="post">
            <button type="submit" value="${members.get(0).group.groupId}" name="groupId">Выйти из группы</button>
        </form:form>
    </c:if>
    <c:if test="${group.idGroupCreator == account.id}">
        <form:form action="delete-group" class="modal" method="post">
            <button type="submit" value="${members.get(0).group.groupId}" name="groupId">Удалить группу</button>
        </form:form>
    </c:if>
</main>
</body>
</html>