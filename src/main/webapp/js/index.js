function checkSignInForm() {

    var loginAlert = $('#loginAlert');
    loginAlert.hide();

    var loginStr = $('#username').val();
    var passwordStr = $('#password').val();

    if (loginStr === "" || passwordStr === "") {
        console.log("Username or password is not valid");
        loginAlert.show();
        return false;
    } else {
        return true;
    }
}

function checkScheduleByStationForm() {

    var scheduleAlert = $('#scheduleAlert');
    var stationNotFoundAlert = $('#stationNotFoundAlert');
    scheduleAlert.hide();
    stationNotFoundAlert.hide();

    var stationName = $('#Station-Name').val();
    //var date = $('#date').val();
    //var time = $('#time').val();

    if (stationName === "") { // || date === "" || time === "") {
        scheduleAlert.show();

        return false;
    }
    return true;
}