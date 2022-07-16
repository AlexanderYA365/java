<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.dao.User: Александр
  Date: 13.01.2022
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Создание аккаунта</title>
    <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>
<body>
<p align="center">
    Регистрация нового пользователя
</p>
<form method="post" class="modal">
    <br>Имя пользователя: <input name="username"/>
    <br>Пароль: <input id="password" name="password" type="password" pattern="^\S{6,}$"
                       onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Must have at least 6 characters' : ''); if(this.checkValidity()) form.password_two.pattern = this.value;"
                       placeholder="Password" required>
    <br>Повтор пароля: <input id="password_two" name="password_two" type="password" pattern="^\S{6,}$"
                              onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Please enter the same Password as above' : '');"
                              placeholder="Verify Password" required>
    <br>Имя: <input name="name"/>
    <br>Фамилия: <input name="surname"/>
    <br>Отчество: <input name="lastName"/>
    <br>Дата рождения: <input name="date" type="date"/>
    <div id='phoneDiv'>
        <input type="hidden" name="phoneId" value="${phone.id}"/>
        <br><input class="phone" name="phone" value="${phone.phoneNumber}"/>
        <select name="typePhone">
            <option value="HOME" selected>Домашний</option>
            <option value="WORK">Рабочий</option>
            <option value="ADDITIONAL">Личный</option>
        </select>
        <button type='button' name="button" class='buttonDelete'>
            удалить телефон
        </button>
    </div>
    <br>
    <button type='button' name="button" id='button' class='button'
            onclick="createNewPhone(); addMask();">добавить телефон
    </button>
    <br>icq: <input name="icq"/>
    <br>Домашний адрес: <input name="addressHome"/>
    <br>Рабочий адрес: <input name="addressJob"/>
    <br>email: <input name="email"/>
    <br>Обо мне: <input name="aboutMe"/>
    <br>
    роль <select id="typeRole" name="typeRole">
    <option value="USER" selected>Пользователь</option>
</select>
    <br><input type="submit" value="Зарегестрировать"/>
</form>

<script src="${pageContext.request.contextPath}/resources/js/edit-account-settings.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.maskedinput.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>

</body>
</html>
