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
