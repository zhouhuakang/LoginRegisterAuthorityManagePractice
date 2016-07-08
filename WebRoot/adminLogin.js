function loginCheck() {

	var userType = $('#userType').val();

	if ($('#username').val() == "" || $('#password').val() == "") {
		alert("UserName or Password can not be null!");
	} else {

		$.ajax({
			data : { // The data sent to the database
				userType : userType,
				username : $("#username").val(),
				password : $("#password").val()
			},
			dataType : "json",
			success : function(data) {
				// if (data.ifSuccessful == "true") {
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
				// }
				// if (data.ifSuccessful == "false") {
				// alert("用户名或密码错误！");
				// }

			},
			type : "post",
			url : "login_validate",
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(XMLHttpRequest.readyState + XMLHttpRequest.status
						+ XMLHttpRequest.responseText);

			}
		});
	}

}