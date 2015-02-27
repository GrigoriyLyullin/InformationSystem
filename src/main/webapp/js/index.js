function checkForm() {
	
	$('#loginAlert').hide();
			
	var loginStr = $('login').value;
	var passwordStr = $('password').value;
	
    if (loginStr == "" || passwordStr == "") {
		console.log("Username or password is not valid");
		 $('#loginAlert').show();
	} else {
		return true;
	}
	return false;
}