function checkForm() {

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