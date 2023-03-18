<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 31.01.2022
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Поиск друга</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/datatables.min.css"/>
</head>
<body>
<%@ include file="../navbar.jsp" %>
<main role="main">
    <div class="modal">
        <br>Имя: <input id="name-account" name="name"/>
        <button onclick="buttonPressed()">Поиск</button>
    </div>
    <br>
</main>
<div id="container" hidden="hidden">
    <table border="0" margin="0" padding="0" width="100%"
           class="dataTables_wrapper" id="find-account">
        <thead>
        <tr>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Фамилия</th>
            <th>Добавить</th>
        </tr>
        </thead>
    </table>
</div>

<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js.map"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/datatables.min.js"></script>
<script>
    const buttonPressed = () => {
        var div = document.getElementById('container');
        div.removeAttribute("hidden");
        var url = window.location;
        var string = url.toString();
        var newUrl = string.substring(0, string.lastIndexOf("/"));
        $.ajax({
            url: newUrl + '/result-search?search=' + document.getElementById('name-account').value,
            method: 'get',
        });
        load();
    }

    function load() {
        var url = window.location;
        var string = url.toString();
        var newUrl = string.substring(0, string.lastIndexOf("/"));
        const table = $('#find-account').DataTable({
            processing: true,
            serverSide: true,
            ajax: {
                url: newUrl + '/get-accounts',
                dataSrc: 'searchResults'
            },
            columns: [
                {data: 'name'},
                {data: 'surname'},
                {data: 'lastName'},
                {defaultContent: '<button>Добавить</button>'},
            ]
        });

        $('#find-account tbody').on('click', 'button', function () {
            const data = table.row($(this).parents('tr')).data();
            let selectMember = data.id
            let role = $(this).find(":selected").val();
            $.ajax({
                type: "GET",
                url: newUrl + '/add-account/' + selectMember + '/' + role
            });
        });
    };

</script>

</body>
</html>
