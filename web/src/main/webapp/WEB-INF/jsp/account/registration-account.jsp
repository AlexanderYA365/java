<%--
  Created by IntelliJ IDEA.
  com.getjavajob.training.yakovleva.common.User: Александр
  Date: 13.01.2022
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание аккаунта</title>
    <link href="${pageContext.request.contextPath}/resources/css/registration-account.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.css" rel="stylesheet"/>
</head>
<body class="p-3 mb-2 bg-primary text-dark">
<section class="vh-100">
    <div class="container py-5 h-100">
        <div class="row justify-content-center align-items-center h-100">
            <div class="col-8 col-lg-6 col-xl-6">
                <div class="card shadow-2-strong card-registration" style="border-radius: 15px;">
                    <div class="card-body p-4 p-md-5">
                        <h3 class="mb-4 pb-2 pb-md-0 mb-md-5">Регистрация нового пользователя</h3>
                        <form enctype="multipart/form-data" modelAttribute="account"
                              action="${pageContext.request.contextPath}/registration-account" method="POST">
                            <div class="row">
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="username">Имя пользователя</label>
                                        <input type="text" id="username" name="username"
                                               class="form-control form-control-lg"/>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4">
                                </div>
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="password">Пароль</label>
                                        <input type="password" name="password" id="password"
                                               class="form-control form-control-lg"
                                               pattern="^\S{6,}$"
                                               onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Пароль должен состоять из 6 символов' : ''); if(this.checkValidity()) form.password_two.pattern = this.value;"
                                               placeholder="Пароль" required/>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="password-confirm">Подтверждение пароль</label>
                                        <input type="password" id="password-confirm"
                                               class="form-control form-control-lg"
                                               pattern="^\S{6,}$"
                                               onchange="this.setCustomValidity(this.validity.patternMismatch ? 'Пожалуйста повторите пароль' : '');"
                                               placeholder="Подтверждение пароля" required/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="name">Имя</label>
                                        <input type="text" name="name" id="name" class="form-control form-control-lg"/>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="lastName">Отчество</label>
                                        <input type="text" name="lastName" id="lastName"
                                               class="form-control form-control-lg"/>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4">
                                    <div class="form-outline">
                                        <label class="form-label" for="surname">Фамилия</label>
                                        <input type="text" name="surname" id="surname"
                                               class="form-control form-control-lg"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 mb-4 d-flex align-items-center">
                                    <div class="form-outline datepicker w-100">
                                        <label for="birthdayDate" class="form-label">Дата рождения</label>
                                        <input type="date" name="date" class="form-control form-control-lg"
                                               id="birthdayDate"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 mb-4 pb-2">
                                    <div class="form-outline">
                                        <label class="form-label" for="emailAddress">Email</label>
                                        <input type="email" name="email" id="emailAddress"
                                               placeholder="Введите Ваш email"
                                               class="form-control form-control-lg"/>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4 pb-2">
                                    <div class="form-outline">
                                        <label class="form-label" for="icq">icq</label>
                                        <input type="text" name="icq" id="icq" placeholder="Введите Ваш icq"
                                               class="form-control form-control-lg"/>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4 pb-2">
                                    <div class="form-outline">
                                        <label class="form-label" for="addressHome">Введите Ваш домашний адрес</label>
                                        <input type="text" name="addressHome" id="addressHome"
                                               placeholder="Введите Ваш домашний адрес"
                                               class="form-control form-control-lg"/>
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4 pb-2">
                                    <div class="form-outline">
                                        <label class="form-label" for="addressJob">Введите Ваш рабочий адрес</label>
                                        <input type="text" name="addressJob" id="addressJob"
                                               placeholder="Введите Ваш рабочий адрес"
                                               class="form-control form-control-lg"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6 mb-4 pb-2">
                                    <label for="typePhone" class="form-label">Тип телефона</label>
                                    <select name="typePhone" class="form-select" id="typePhone" required="">
                                        <option value="">Выберите...</option>
                                        <option value="HOME">Домашний</option>
                                        <option value="WORK">Рабочий</option>
                                        <option value="ADDITIONAL">Личный</option>
                                    </select>
                                    <div class="invalid-feedback">
                                        Пожалуйста, выберите тип телефоне
                                    </div>
                                </div>
                                <div class="col-md-6 mb-4 pb-2">
                                    <div class="form-outline">
                                        <label class="form-label" for="phone">Номер телефона</label>
                                        <input type="text" name="phone" id="phone"
                                               class="form-control form-control-lg"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-12 mb-4 pb-2">
                                <div class="form-file">
                                    <input type="file" name="file" class="form-file-input" id="customFile">
                                    <label class="form-file-label" for="customFile">
                                        <span class="form-file-text">Выберите аватар...</span>
                                        <span class="form-file-button">Выбрать</span>
                                    </label>
                                    </input>
                                </div>
                            </div>
                            <div class="col-md-12">
                                <div class="form-outline mb-4">
                                    <label class="form-label" for="aboutMe">Обо мне</label>
                                    <textarea class="form-control" name="aboutMe" id="aboutMe" rows="4"></textarea>
                                </div>
                            </div>
                            <div class="mt-4 pt-2">
                                <input class="btn btn-primary btn-lg" type="submit" value="Зарегистрироваться"/>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<script src="${pageContext.request.contextPath}/resources/js/edit-account-settings.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.6.0.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery.maskedinput.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>

</body>
</html>
