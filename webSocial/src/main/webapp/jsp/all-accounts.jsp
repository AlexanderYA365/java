<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.dao.User: Александр
  Date: 29.01.2022
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>All Accounts</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="navbar.jsp" %>
tttttttttyta
<%--<input type="radio" name="questions[${questions.key.id}]" value="${answers.id}" id="radio"/>--%>

<%--@elvariable id="friend" type="com.getjavajob.training.yakovleva.dao.Account"--%>
<label>${friends}</label>
<br>
<br>
<br>
<br>


<%--@elvariable id="Account" type="com.getjavajob.training.yakovleva.dao.Account"--%>
<form:form action="test5" method="post">
    <c:forEach items="${friends}" var="friend">
        <button type="submit" name="id" value="${friend.id}">
                ${friend.id}</button>
    </c:forEach>
</form:form>


${account.username}


<form:form action="all-accounts" modelAttribute="account" method="get">
    First Name : <form:input path="username"/>
    <br><br>
    id : <form:input path="id"/>
    <br><br>
    <input type="submit" value="submit"/>
</form:form>
<br>
<br>
<br>
photo
<br>
<img src="data:image/jpg;base64, ${encodedPhoto}"
     alt="Responsive image" style="width:120px;height:140px;">

<br>
<br>
<br>
<br>
<br>
<form:form action="test3" modelAttribute="account" method="post" enctype="multipart/form-data">
    <input type="file" id="uploadPhoto" name="uploadPhoto" class="form-control">
    <input type="submit" value="Upload">
</form:form>
<form method="POST" enctype="multipart/form-data" action="test3">
    File to upload: <input type="file" name="file"><br/>
    Name: <input type="text" name="name">
    <br/> <br/>
    <input type="submit" value="Upload"> Press here to upload the file!
</form>
<button onclick="location.href='all-accounts'">Изменить данные пользователя</button>
</body>
</html>
