function changeUserPassword(){
	
	var  currentPassword=$.trim(""+$("#user_current_password").val());
	var  newPassword=$.trim(""+$("#user_new_password").val());
	var  confirmPassword=$.trim(""+$("#user_confirm_password").val());
	 if(currentPassword==""){
		customAlerts("Please enter your current password", "error", 0);
		focusForValidation("user_current_password");
		return false;

	 } 
	 if(newPassword==""){
		customAlerts("Please enter new password", "error", 0);	
		focusForValidation("user_new_password");
		return false; 
	 }
	 if(confirmPassword==""){
		customAlerts("Please enter Confirm password", "error", 0);	
		focusForValidation("user_confirm_password");
		return false; 
	 }
	 if(newPassword!=confirmPassword){
		customAlerts("New Password  and Confirm Password must be match", "error", 0);	
		focusForValidation("user_new_password");
		focusForValidation("user_confirm_password")
		return false; 
	 }
	 if(newPassword==currentPassword){
			customAlerts("New Password  and Current Password must not same", "error", 0);
			focusForValidation("user_new_password");
			focusForValidation("user_current_password");
			return false; 
		 }
	$.ajax({
		url : "agriculture/accountController/changeUserPassword",
		type : 'POST',
		beforeSend: showLoadingImage(),
		/*async : false,*/
		data : {
			currentPassword : currentPassword,
			newPassword : newPassword
			 
		},
		success : function(response) {
			 if(response.status=='success'){
				 customAlerts("Your password successfully changed", "success", 0);	
				 $("#user_current_password").val("");
				 $("#user_new_password").val("");
				 $("#user_confirm_password").val("");
				 div_hide9();
				 
			 }else if(response.status=='invalid user'){
				 customAlerts("Invalid user", "error", 0); 
				 $("#user_current_password").val("");
				 $("#user_new_password").val("");
				 $("#user_confirm_password").val("");
				 div_hide9();
				
			 }else if(response.status=='Not exists'){
				 customAlerts("Invalid Current Password", "error", 0); 
			 }
			 
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, "error", 0); 
		},
	}).done(function(){
		hideLoadingImage();
	});
}

/*created by Bhagvan Singh on 05-05-2015 For focus on validated text field
start*/
function focusForValidation(id){
	$("#"+id).css("border", "1px solid red");
	$("#"+id).click(function() {
		$(this).css("border", "1px solid #cccccc");
	});
}
/*end*/
