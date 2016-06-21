function loginCheck() {
	if ($('#username').val() == "" || $('#password').val() == "") {
		alert("Can not null");
	} else {
		$.ajax({
			data : { // The data sent to the database
				userType : $("#userType").val(),
				username : $("#username").val(),
				password : $("#password").val()
			},
			dataType : "json",
			success : function() {

			},
			type : "post",
			url : login_validate
		});
	}

}