<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 29.01.2022
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>All Accounts</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <%--    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet">--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/datatables.min.css"/>
</head>
<body>
<%@ include file="navbar.jsp" %>
<br>
<br>
<br>
<br>
<br>
<%--<table id="admin" class="table table-sm">--%>
<%--    <tr>--%>
<%--        <th>№</th>--%>
<%--        <th>Имя</th>--%>
<%--        <th>Фамилия</th>--%>
<%--        <th>Отчество</th>--%>
<%--        <th>ICQ</th>--%>
<%--        <th>аватар</th>--%>
<%--        <th>Домашний адресс</th>--%>
<%--        <th>Рабочий адресс</th>--%>
<%--        <th>почта</th>--%>
<%--        <th>Имя пользователя</th>--%>
<%--        <th>Пароль</th>--%>
<%--        <th>Роль</th>--%>
<%--    </tr>--%>
<%--    &lt;%&ndash;    <c:forEach var="account" items="${accounts}">&ndash;%&gt;--%>
<%--    &lt;%&ndash;        <tr>&ndash;%&gt;--%>
<%--    &lt;%&ndash;            <td><c:out value="${account.id}"/></td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;            <td><c:out value="${account.name}"/></td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;            <td><c:out value="${account.surname}"/></td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;            <td><c:out value="${account.lastName}"/></td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;            <td><c:out value="${account.icq}"/></td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;            <td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;                <img src="data:image/jpg;base64, ${encodedPhoto}"&ndash;%&gt;--%>
<%--    &lt;%&ndash;                     alt="Responsive image" style="width:50px;height:60px;"&ndash;%&gt;--%>
<%--    &lt;%&ndash;                     onerror="this.src='resources/img/noPhotoAvailable.jpg'" class="img-fluid"&ndash;%&gt;--%>
<%--    &lt;%&ndash;                     alt="Responsive image">&ndash;%&gt;--%>
<%--    &lt;%&ndash;            </td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;                &lt;%&ndash;            <td><c:out value="${account.photoFileName}"/></td>&ndash;%&gt;&ndash;%&gt;--%>
<%--    &lt;%&ndash;            <td><c:out value="${account.addressHome}"/></td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;            <td><c:out value="${account.addressJob}"/></td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;            <td><c:out value="${account.email}"/></td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;            <td><c:out value="${account.username}"/></td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;            <td><c:out value="${account.password}"/></td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;            <td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;                <c:if test="${account.role == 0}">&ndash;%&gt;--%>
<%--    &lt;%&ndash;                    <select id="typeRole" name="typeRole">&ndash;%&gt;--%>
<%--    &lt;%&ndash;                        <option value="USER" selected>Пользователь</option>&ndash;%&gt;--%>
<%--    &lt;%&ndash;                    </select>&ndash;%&gt;--%>
<%--    &lt;%&ndash;                </c:if>&ndash;%&gt;--%>
<%--    &lt;%&ndash;                <c:if test="${account.role == 1}">&ndash;%&gt;--%>
<%--    &lt;%&ndash;                    <select id="typeRole" name="typeRole">&ndash;%&gt;--%>
<%--    &lt;%&ndash;                        <option value="USER">Пользователь</option>&ndash;%&gt;--%>
<%--    &lt;%&ndash;                        <option value="ADMIN" selected>Администратор</option>&ndash;%&gt;--%>
<%--    &lt;%&ndash;                    </select>&ndash;%&gt;--%>
<%--    &lt;%&ndash;                </c:if>&ndash;%&gt;--%>
<%--    &lt;%&ndash;            </td>&ndash;%&gt;--%>
<%--    &lt;%&ndash;        </tr>&ndash;%&gt;--%>
<%--    &lt;%&ndash;    </c:forEach>&ndash;%&gt;--%>
<%--</table>--%>
<table border="0" margin="0" padding="0" width="100%"
       class="dataTables_wrapper" id="studentListTable">
    <thead>
    <tr>
        <th>Id</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Gender</th>
        <th>Address</th>
        <th>Grade</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${account.id}</td>
        <td>${account.id}</td>
        <td>${account.id}</td>
        <td>${account.id}</td>
        <td>${account.id}</td>
        <td>${account.id}</td>
    </tr>
    </tbody>
</table>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datatables.min.js"></script>
<script>
    $(document).ready(function () {
        $('#admin').DataTable({
            "bJQueryUI": true,
            "sPaginationType": "full_numbers",
            "iDisplayLength": 10,
            "bProcessing": true,
            "bServerSide": true,
            "sAjaxSource": "/getAccounts",
            "aoColumns": [{"bSearchable": false, "bVisible": false, "asSorting": ["asc"]},
                {"sWidth": "20%", "bSortable": true},
                {"sWidth": "20%", "bSortable": true},
                {"sWidth": "10%", "bSortable": true},
                {"sWidth": "20%", "bSortable": true},
                {"sWidth": "20%", "bSortable": true}
            ]
        });
    })

</script>

</body>
</html>
