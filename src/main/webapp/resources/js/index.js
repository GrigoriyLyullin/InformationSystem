function checkSignInForm() {

    var loginAlert = $('#login-alert');
    var loginAlertReserve = $('#login-alert-reserve');
    var loginError = $('#login-error');
    var loginErrorReserve = $('#login-error-reserve');
    var loginMessage = $('#login-message');

    loginAlert.hide();
    loginError.hide();
    loginMessage.hide();
    loginErrorReserve.hide();
    loginAlertReserve.hide();

    var loginStr = $('#username').val();
    var passwordStr = $('#password').val();

    if (loginStr === "" || passwordStr === "") {
        loginAlert.show();
        loginError.hide();
        loginMessage.hide();
        return false;
    } else {
        return true;
    }
}

function checkSearchTrainForm() {

    var searchTrainAlert = $('#searchTrainAlert');
    var trainSearchingError = $('#trainSearchingErrorAlert');
    var trainNotFoundError = $('#trainNotFoundAlert');
    searchTrainAlert.hide();
    trainSearchingError.hide();
    trainNotFoundError.hide();

    var stationFromName = $('#Station-From-Name').val();
    var stationToName = $('#Station-To-Name').val();
    var dateFrom = $('#dateFrom').val();
    var timeFrom = $('#timeFrom').val();
    var dateTo = $('#dateTo').val();
    var timeTo = $('#timeTo').val();

    if (stationFromName === "" || stationToName === "" || dateFrom === "" || dateTo === "") {
        searchTrainAlert.show();
        return false;
    }
    return true;
}

var stationNames = [];

function init() {
    $(document).ready(function ($) {
        // load all station names
        $.get('stations', function (data) {
            stationNames = JSON.parse(data);
        });
        $('#schedule-by-station-name').typeahead({
            source: function () {
                return stationNames;
            },
            items: 5,
            minLength: 0
        });
        $('#Station-From-Name').typeahead({
            source: function () {
                return stationNames;
            },
            items: 5,
            minLength: 0
        });
        $('#Station-To-Name').typeahead({
            source: function () {
                return stationNames;
            },
            items: 5,
            minLength: 0
        });
        $(function () {
            $('#dateFrom').datepicker({
                startDate: new Date(),
                todayHighlight: true,
                format: "yyyy-mm-dd"
            });
        });
        $('.btn-buy-disabled').click(function (e) {
            e.preventDefault();
            $('html, body').animate({
                scrollTop: $("#form-signin-body").offset().top - 100
            }, 1000);
            $('#login-message').show();
        });
    });
}

function init_buy_ticket_page() {
    $(document).ready(function ($) {
        $('#buy-ticket-date-from').datepicker({
            orientation: "bottom",
            startDate: new Date(),
            todayHighlight: true,
            format: "yyyy-mm-dd"
        });
        $('#buy-ticket-time-from').timepicker({
            showSeconds: false,
            showMeridian: false,
            minuteStep: 1
        });
        $('#buy-ticket-birthdate-from').datepicker({
            orientation: "bottom",
            endDate: new Date(),
            startView: "decade",
            todayHighlight: true,
            format: "yyyy-mm-dd"
        });
        $('#buy-ticket-expires-from').datepicker({
            orientation: "bottom",
            startDate: new Date(),
            startView: "months",
            minViewMode: "months",
            todayHighlight: true,
            format: "yyyy-mm"
        });
        var buyTicketFromError = $('.buy-ticket-form-error');
        buyTicketFromError.click(function () {
            $(this).removeClass("buy-ticket-form-error");
            $(this).find("p").remove();
        });
        buyTicketFromError.keypress(function () {
            $(this).removeClass("buy-ticket-form-error");
            $(this).find("p").remove();
        });
    });
}
