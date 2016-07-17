function register() {
	var userType = $('#userType').val();
	var userName = $('#username').val();
	var userPassword = $('#userpassword').val();
	if (userName == "" || userPassword == "") {
		$('#warn').toggle();
	} else {
		$.ajax({
			data : {
				userType : userType,
				userName : userName,
				userPassword : userPassword
			},
			dataType : "json",
			error : function(data) {
				alert("error " + userName);
			},
			success : function(data) {
				alert("Welcome  " + userName);
			},
			type : "post",
			url : "register"
		});
	}
}
