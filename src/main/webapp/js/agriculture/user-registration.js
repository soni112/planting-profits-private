$(document).ready(function() {
	$("#address").geocomplete({});

	// 10-03-2015 change start
	$("#user-password").keyup(function(event) {
		if (event.keyCode == 13) {
			submitLoginForm();
		}
	});
	// 10-03-2015 change End


	// physicalAddressSameForMailing();
});

function registerUser() {
	
	var isFormValid = validRegistrationForm();
	if (!isFormValid) {
		return false;
	}
	var accountType = $.trim("" + $('#user-account-type').val());
	var firstName = $.trim("" + $('#first-name').val());
	var lastName = $.trim("" + $('#last-name').val());
	var contact = $.trim("" + $('#contact-no').val());
	var email = $.trim("" + $('#email-id').val());
	var mailing_Address_Line1 = $.trim("" + $('#mailing-address-line1').val());
	var mailing_Address_Line2 = $.trim("" + $('#mailing-address-line2').val());
	var mailing_Address_City = $.trim("" + $('#mailing-address-city').val());
	var mailing_Address_State = $.trim("" + $('#mailing-address-state').val());
	var mailing_Address_Country = $.trim("" + $('#mailing-address-country').val());
	var mailing_Zip = $.trim("" + $('#mailing-zip').val());
	
	var physical_Address_Line1 = $.trim("" + $('#physical-address-line1').val());
	var physical_Address_Line2 = $.trim("" + $('#physical-address-line2').val());
	var physical_Address_City = $.trim("" + $('#physical-address-city').val());
	var physical_Address_State = $.trim("" + $('#physical-address-state').val());
	var physical_Zip = $.trim("" + $('#physical-zip').val());
	var physical_Address_Country = $.trim("" + $('#physical-address-country').val());


	$.ajax({
		url : 'agriculture/accountController/registerUser',
		type : 'POST',
		/*async : false,*/
		beforeSend: showLoadingImage(),
		data : ({
			accountType : accountType,
			firstName : firstName,
			lastName : lastName,
			contact : contact,
			email : email,
			mailing_Address_Line1 : mailing_Address_Line1,
			mailing_Address_Line2 : mailing_Address_Line2,
			mailing_Address_City : mailing_Address_City,
			mailing_Address_State : mailing_Address_State,
			mailing_Address_Country : mailing_Address_Country,
			mailing_Zip : mailing_Zip,
			physical_Address_Line1 : physical_Address_Line1,
			physical_Address_Line2 : physical_Address_Line2,
			physical_Address_City : physical_Address_City,
			physical_Address_State : physical_Address_State,
			physical_Address_Country : physical_Address_Country,
			physical_Zip : physical_Zip
		}),
		success : function(response) {
			var status = response.status;
			if (status == 'success') {
				//customAlerts("Your account has been created and verification mail send to your email\nPlease verify your account by your email", "success", 0);
				customAlerts("Your account has been created.  Account information has been sent to your email address", "success", 0);
				div_hide2();
			} else if (status == 'Already exists') {
				customAlerts("Email id is already registered, Choose another one!", "error", 0);
			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":"
					+ message, "error", 0);
		}

	}).done(function(){
		hideLoadingImage();
	});

}

function validRegistrationForm() {
	var isFormValidated = true;
	var accountType = $.trim("" + $('#user-account-type').val());
	var userName = $.trim("" + $('#first-name').val());
	var lastName = $.trim("" + $('#last-name').val());
	var contact = $.trim("" + $('#contact-no').val());
	var email = $.trim("" + $('#email-id').val());

	/**
	 * @changed - Abhishek
	 * @date - 08-12-2015
	 */
	var country = $.trim("" + $('#physical-address-country').val());
	
	if (accountType == "--Select Account Type--") {
		if (isFormValidated) {
			customAlerts("Please Select account type", "error", 0);	
			isFormValidated = false;
		}
		focusForValidation("user-account-type");
	}
	if (userName == "") {
		if (isFormValidated) {
			customAlerts("Please enter your first name", "error", 0);	
			isFormValidated = false;
		}
		focusForValidation("first-name");		
	}
	if (contact == "") {
		if (isFormValidated) {
			customAlerts("Please enter your contact number", "error", 0);	
			isFormValidated = false;
		}
		focusForValidation("contact-no");		
	}
	if ((""+contact).length > 13) {
		if (isFormValidated) {
			customAlerts("Please enter valid contact number", "error", 0);	
			isFormValidated = false;
		}
		focusForValidation("contact-no");		
	}
	if (email == "") {
		if (isFormValidated) {
			customAlerts("Please enter your email address", "error", 0);	
			isFormValidated = false;
		}
		focusForValidation("email-id");		
	}
	if (lastName == "") {
		if (isFormValidated) {
			customAlerts("Please enter your last name", "error", 0);
			isFormValidated = false;
		}
		focusForValidation("last-name");
	}
	var emailValidated = validateEmailAddress(email);
	if (!emailValidated) {
		if (isFormValidated) {
			customAlerts("Please enter valid email", "error", 0);	
			focusForValidation("email-id");
			isFormValidated = false;
		}
	}

	/**
	 * @changed - Abhishek
	 * @date - 08-12-2015
	 */
	if (country == "") {
		if (isFormValidated) {
			customAlerts("Please select physical address country", "error", 0);
			focusForValidation('physical-address-country');
			isFormValidated = false;
		}
	}

	var state = $('input[name="license-agreement-checkbox"]').prop('checked');

	if (!state) {
		if (isFormValidated) {
			customAlerts("Please accept the License Agreement", "error", 0);
			/*$('input[name="license-agreement-checkbox"]').css("border", "1px solid red");
			$('input[name="license-agreement-checkbox"]').click(function() {
				$(this).css("border", "1px solid #cccccc");
			});*/
			isFormValidated = false;
		}
	}
	return isFormValidated;
}

function submitLoginForm() {
	
	var emailId = $.trim("" + $('#user-email-id').val());
	var password = $.trim("" + $('#user-password').val());
	if (emailId == "") {
		customAlerts("Please enter your email", "error", 0);
		focusForValidation("user-email-id");
		return false;
	}
	if (password == "") {
		customAlerts("Please enter your password", "error", 0);	
		focusForValidation("user-password");
		return false;
	}
	$("#user-login-form").submit();
}

function recoverPassword() {
	
	var email=$.trim("" + $('#user-email-id-password-recovery').val());
	if (email == "") {
		customAlerts("Please enter your email", "error", 0);
		focusForValidation("user-email-id-password-recovery");
		return false;
	}
	if (!validateEmailAddress(email)) {
		customAlerts("Please enter valid email", "error", 0);
		focusForValidation("user-email-id-password-recovery");
		return false;
	}
	 $.ajax({
			url : 'agriculture/accountRecoveryController/sendRequestForAccountRecovery',
			type : 'POST',
			dataType: 'json',
			beforeSend: showLoadingImage(),
			data : ({
				email : email
			 	}),
			   success : function(data) {
			 if(data.status=="invalid user")
				{
				 customAlerts("Email does not exists", "error", 0);	
				}
				else if(data.status=="success")
				{
					customAlerts("Account recovery mail is send to your email", "success", 0);	
				    $('#user-email-id-password-recovery').val('');
				    div_hide7();
				 	}
			},
			error : function() {
				customAlerts("Some thing went wrong", "error", 0);
			}

		}).done(function(){
			hideLoadingImage();
		});
}

function updateUserPassword(){
	
var userEncodedEmailId = $.trim($('#userEncodeduserEmailAddress').val());
var password = $('#userPassword').val();
var confirmPassword=$("#userConfirmPassword").val();
if(password==""){
	customAlerts("Please enter password", "error", 0);
	focusForValidation("userPassword");	
	return false; 
}
if(confirmPassword==""){
	customAlerts("Please enter confirm password", "error", 0);
	focusForValidation("userConfirmPassword");
	return false; 
}
if(password!=confirmPassword){
	customAlerts("New Password and Confirm Password must be match", "error", 0);	
	focusForValidation("userPassword");
	focusForValidation("userConfirmPassword");
	return false; 
}
	$.ajax({
		url : 'agriculture/accountRecoveryController/changeUserPassword',
		type : 'POST',
		/*async : false,*/
		beforeSend: showLoadingImage(),
		data : ({
			email : userEncodedEmailId,
			password : password

		}),
		success : function(response) {
			var status = response.status;
			if (status == 'success') {
				customAlerts("Password successfully changed", "success", 0);
				div_hide8();
			} else if (status == 'failed') {
				customAlerts("Some thing went wrong", "error", 0);
			}
		},
		error : function(XMLHttpRequest,status, message) {
			customAlerts("Error"+ XMLHttpRequest+ ":"+ status+ ":"+ message, "error", 0);
		}
	}).done(function(){
		hideLoadingImage();
	});
}

function focusForValidation(id){
	$("#"+id).css("border", "1px solid red");
	$("#"+id).click(function() {
		$(this).css("border", "1px solid #cccccc");
	});
}
/*end*/

