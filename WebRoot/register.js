function regitser() {
	var userType = $('#userType').val();
	var userName = $('#username').val();
	var userPassword = $('#userpassword').val();
	$.ajax({
		data : {
			userType : userType,
			userName : userName,
			userPassword : userPassword
		},
		dataType : "json",
		error : function(data) {
			alert("Welcome  " + userName);
		},
		success : function(data) {
			alert("Welcome  " + userName);
		},
		type : "post",
		url : "regitser"
	});

}
