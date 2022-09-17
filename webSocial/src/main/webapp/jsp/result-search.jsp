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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/datatables.min.css"/>
</head>
<body>
<%@ include file="navbar.jsp" %>
<br>
<br>
<br>
<br>
<br>
<table border="0" margin="0" padding="0" width="100%"
       class="dataTables_wrapper" id="search-result">
    <thead>
    <tr>
        <th>id</th>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Отчество</th>
        <th>Дата рождения</th>
        <th>isGroup</th>
    </tr>
    </thead>
</table>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datatables.min.js"></script>
<script>
    $(document).ready(function () {
        $('#search-result').dataTable({
            processing: true,
            serverSide: true,
            ajax: {
                url: '/socnet/getSearch',
            },
            columns: [
                {data: 'id'},
                {data: 'name'},
                {data: 'surname'},
                {data: 'lastName'},
                {data: 'groupName'},
                {data: 'isGroup'},
            ],
        });
    });
</script>

</body>
</html>
