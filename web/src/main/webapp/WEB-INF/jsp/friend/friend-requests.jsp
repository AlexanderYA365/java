<%--
  Created by IntelliJ IDEA.
  User: Александр
  Date: 03.03.2023
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Заявки в друзья</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/datatables.min.css"/>
</head>
<body>
<%@ include file="../navbar.jsp" %>
<br>
<br>
<table border="0" margin="0" padding="0" width="100%"
       class="dataTables_wrapper" id="find-account">
    <thead>
    <tr>
        <th>Id</th>
        <th>Имя</th>
        <th>Отчество</th>
        <th>Фамилия</th>
        <th>Добавить</th>
        <th>Отказать</th>
    </tr>
    </thead>
</table>

<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js.map"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datatables.min.js"></script>
<script>
    $(document).ready(function () {
        var url = window.location;
        var string = url.toString();
        let newUrl = string.substring(0, string.lastIndexOf("/"));
        const table = $('#find-account').DataTable({
            processing: true,
            serverSide: true,
            ajax: {
                url: newUrl + '/get-friend-requests',
                dataSrc: 'data'
            },
            columns: [
                {data: 'id'},
                {data: 'name'},
                {data: 'surname'},
                {data: 'lastName'},
                {defaultContent: '<button id="add-btn">Добавить</button>'},
                {defaultContent: '<button id="del-btn">Отказать</button>'},
            ]
        });

        $('#find-account tbody').on('click', '#add-btn', function () {
            const data = table.row($(this).parents('tr')).data();
            var id = data.id
            console.log(id)
            console.log(newUrl)
            $.ajax({
                type: "GET",
                url: newUrl + '/accept-application-friend/' + id
            });
        });

        $('#find-account tbody').on('click', '#del-btn', function () {
            const data = table.row($(this).parents('tr')).data();
            var id = data.id
            console.log(id)
            $.ajax({
                type: "GET",
                url: newUrl + '/delete-account/' + id
            });
        });
    });
</script>
</body>
</html>
