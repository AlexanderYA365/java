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

<div class="ui-widget">
    <label for="search">Поиск: </label>
    <input id="search"/>
    <button href="${pageContext.request.contextPath}/result-search">поиск</button>
</div>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<%--<script src="${pageContext.request.contextPath}/resources/js/navbar.js"></script>--%>
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
                        response($.map(data, function (account, i) {
                            let id = account.id
                            return {
                                value: account.id,
                                label: account.name + " " + account.surname
                            }
                        }));
                    },
                });
            },
            minLength: 1,
        });
    });

    $("#search").autocomplete({
        select: function (event, ui) {
            console.log("tyt3")

        }
    });

    $("#search").on("autocompleteselect", function (event, ui) {
        document.location.href = "/socnet/show-friend?id=" + ui.item.value;
    });

</script>

</body>
</html>
