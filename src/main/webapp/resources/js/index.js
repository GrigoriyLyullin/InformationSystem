function checkSignInForm() {

    var loginAlert = $('#loginAlert');
    var loginError = $('#loginError');
    var loginMessage = $('#loginMessage');
    loginAlert.hide();

    var loginStr = $('#username').val();
    var passwordStr = $('#password').val();

    if (loginStr === "" || passwordStr === "") {
        console.log("Username or password is not valid");
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

function initStationNameListener() {
    $(document).ready(function ($) {
        // workaround for bug in mouse item selection
        $.fn.typeahead.Constructor.prototype.blur = function () {
            var that = this;
            setTimeout(function () {
                that.hide()
            }, 250);
        };
        // load all station names
        $.get('stations', function (data) {
            stationNames = JSON.parse(data);
        });
        $('#Station-Name').typeahead({
            source: function () {
                $.get('stations?startWith=' + $('#Station-Name').val().slice(0, -1), function (data) {
                    stationNames = JSON.parse(data);
                });
                return stationNames;
            }
        });
        $('#Station-From-Name').typeahead({
            source: function () {
                $.get('stations?startWith=' + $('#Station-From-Name').val().slice(0, -1), function (data) {
                    stationNames = JSON.parse(data);
                });
                return stationNames;
            }
        });
        $('#Station-To-Name').typeahead({
            source: function () {
                $.get('stations?startWith=' + $('#Station-To-Name').val().slice(0, -1), function (data) {
                    stationNames = JSON.parse(data);
                });
                return stationNames;
            }
        });
    });
}
