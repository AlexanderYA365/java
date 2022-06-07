
$(".buttonDelete").click(function () {
    $(this).parent().remove();
});

function createBewPhone() {
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
