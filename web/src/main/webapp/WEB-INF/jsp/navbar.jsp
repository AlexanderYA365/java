<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 15.03.2022
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/css/navbar.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/jquery-ui.css" rel="stylesheet">
</head>
<body>
<ul class="nav">
    <li><a href="${pageContext.request.contextPath}/account-friends">Мои друзья</a></li>
    <li><a href="${pageContext.request.contextPath}/account-group">Мои группы</a></li>
    <li><a href="${pageContext.request.contextPath}/main">Моя страница</a></li>
    <li><a href="${pageContext.request.contextPath}/account-message">Мои сообщения</a></li>
    <li><a href="${pageContext.request.contextPath}/my-account">Редактирование</a></li>
    <li><a href="${pageContext.request.contextPath}/account-logout">Выйти</a></li>
</ul>

<form class="ui-widget" action="${pageContext.request.contextPath}/result-search" method="GET">
    <label for="search">Поиск: </label>
    <input id="search" name="search"/>
    <button type="submit" onclick="location.href='${pageContext.request.contextPath}/result-search'">поиск</button>
</form>


<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>

    $(function () {
        $("#search").autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: '<c:url value = "/search" />',
                    data: {
                        filter: request.term
                    },
                    success: function (data) {
                        response($.map(data, function (searchResult, i) {
                            return {
                                value: searchResult,
                                label: searchResult.isGroup ? "Группа - " + searchResult.name :
                                    "Пользователь - " + searchResult.name + " " + searchResult.surname
                            }
                        }));
                    },
                });
            },
            minLength: 3,
        });
    });

    $("#search").autocomplete({
        select: function (event, ui) {
        }
    });

    $("#search").on("autocompleteselect", function (event, ui) {
        console.log(ui.item.value.id.toString())
        if (ui.item.value.isGroup) {
            console.log(ui.item.value.isGroup.toString())
            document.location.href = "/socnet/show-group?id=" + ui.item.value.id;
        } else {
            document.location.href = "/socnet/show-friend?id=" + ui.item.value.id;
        }
    });

</script>

</body>
</html>
