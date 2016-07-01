$(document).ready(function() {
		$.ajax({
			data : {},
			dataType : "json",
			error : function(data) {

			},
			success : function(data) {
				$("#user_name").text(data.userName);
			},
			type : "post",
			url : "GetSessionServlet"
		});
	});



