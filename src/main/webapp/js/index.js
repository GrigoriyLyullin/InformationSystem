function checkForm() {
	
	$('#loginAlert').hide();
			
	var usernameStr = document.getElementById('username').value;
	var passwordStr = document.getElementById('password').value;
	
    if (usernameStr == "" || passwordStr == "") {
		
		console.log("Username or password is not valid");
		 $('#loginAlert').show();
		
	} else {

		$('#form-signin-body').hide();
		$('#form-preloader').show();
	
		var xhr = new XMLHttpRequest();
		xhr.open("POST", "http://localhost:8080/FrontServlet");
		xhr.setRequestHeader('Content-Type', 'application/json');
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4) {
				var userdata = JSON.parse(xhr.responseText);
				console.log(userdata.name + " " + userdata.surname + " " + userdata.birthdate);
				
				document.getElementById('name').innerHTML = "Name: " + userdata.name;
				document.getElementById('surname').innerHTML = "Surname: " + userdata.surname;
				document.getElementById('birthdate').innerHTML = "Date of birth: " + userdata.birthdate; 
				
				$('#form-preloader').hide();
				$('#form-userdata').show();
			}
		};
		
		var value = JSON.stringify({
			username: usernameStr,
			password: passwordStr
		});
		
		xhr.send(value);
	}
	
	return false;
}K;