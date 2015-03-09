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
    searchTrainAlert.hide();

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