function loginCheck() {

	var userType = $('#userType').val();

	if ($('#username').val() == "" || $('#password').val() == "") {
		alert("Can not null!!");
	} else {

		$.ajax({
			data : { // The data sent to the database
				userType : userType,
				username : $("#username").val(),
				password : $("#password").val()
			},
			dataType : "json",
			success : function(data) {
				switch (data.userType) {
				case 0:
					window.location.href = 'UserAdminIndex.html';
					break;
				case 1:
					window.location.href = 'UserEnterpriseIndex.html';
					break;
				case 2:
					window.location.href = 'UserPersonIndex.html';
					break;
				}

			},
			type : "post",
			url : "login_validate",
			error : function(data) {

			}
		});
	}

}