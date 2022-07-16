$(".buttonDelete").click(function () {
    $(this).parent().remove();
});

function createNewPhone() {
    var mySelect = document.createElement('select');
    mySelect.add(new Option('домашний', 'HOME'));
    mySelect.add(new Option('Рабочий', 'WORK'));
    mySelect.add(new Option('Личный', 'ADDITIONAL'));
    mySelect.name = 'typeNewPhone';
    mySelect.id = 'typeNewPhone';
    var myInput = document.createElement('input');
    myInput.type = 'text';
    myInput.id = 'newPhone';
    myInput.name = 'newPhone';
    myInput.class = 'newPhone';
    $(".button").before(myInput, mySelect);
}

$(document).ready(function () {
    $body = $('body');
    $body.find('.phone').each(function () {
        $(this).mask("+7 (999) 99-99-999", {autoclear: false});
    });
});

function addMask() {
    $('#newPhone').mask("+7 (999) 99-99-999", {autoclear: false});
}

function sendPost() {
    clearError();
    return checkedField();
}

function checkedField() {
    var fields = document.querySelectorAll('.field')
    let valid = true;
    for (var i = 0; i < fields.length; i++) {
        if (!fields[i].value) {
            console.log('поле не заполнено', fields[i])
            var error = generateError('заполните поле')
            $(fields[i]).after(error)
            valid = false;
        }
    }
    return valid;
}

var generateError = function (text) {
    var error = document.createElement('div')
    error.className = 'error'
    error.style.color = 'red'
    error.innerHTML = text
    return error
}

function clearError() {
    var form = document.querySelector('.modal')
    var errors = form.querySelectorAll('.error')
    for (var i = 0; i < errors.length; i++) {
        errors[i].remove()
    }
}