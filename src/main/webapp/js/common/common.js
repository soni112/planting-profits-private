var errorClassName = "errorClass";
var type_success = "success";
var type_error = "error";
var type_warning = "warning";
var time = 0;

function checkExtension(fileName, expression) {
	fileName = fileName.toLowerCase();
	if (fileName) {
		if (expression.test(fileName)) {
			return true;
		} else {
			return false;
		}
	} else {
		return true;
	}
}

function validateEmailAddress(email) {
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return filter.test(email);
}

function validateNumericNegativeValue(value){
    var regex = /(^[\-]{1}[0-9\.]+)|(^[0-9\.]+)$/;
    return regex.test(value);
}

function validateNumericValue(value){
	var regex = /^[0-9]*$/;
	return regex.test(value);
}

function isValidNumberValue(evt) {
	var e = evt || window.event; // window.event is safer, thanks
	// @ThiefMaster
	var charCode = e.which || e.keyCode;

	if (charCode > 31 && (charCode < 47 || charCode > 57) && (charCode != 46))
		return false;
	if (e.shiftKey)
		return false;
	if (charCode == 47)
		return false;
	return true;
}

function isValidNumberValueForWithOutDot(evt) {
	var e = evt || window.event; // window.event is safer, thanks
	// @ThiefMaster
	var charCode = e.which || e.keyCode;

	if (charCode > 31 && (charCode < 47 || charCode > 57) && (charCode != 46))
		return false;
	if (e.shiftKey)
		return false;
	if (charCode == 47)
		return false;
	if (charCode == 46)
		return false;
	return true;
}

function isValidNumberValueForForCastSA(evt) {
	var e = evt || window.event; // window.event is safer, thanks
	// @ThiefMaster
	var charCode = e.which || e.keyCode;
	if (charCode > 31 && (charCode < 47 || charCode > 57) && (charCode != 46) && (charCode != 45))
		return false;
	if (e.shiftKey)
		return false;
	if (charCode == 47)
		return false;
	if (charCode == 46)
		return false;
	return true;
}

function addCommaSignWithForOnePoint(id) {
	if ($(id).val() == "") {

	} else {
		var value = $.trim("" + $(id).val().replace('$', '').replace(/,/g, ''));
		var valueWithPoint = Number(value).toFixed(1);
		var valueWithComma;
		if (valueWithPoint % 1 != 0) {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
		} else {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
			valueWithComma = valueWithComma + ".0";
		}
		return $(id).val(valueWithComma);
	}
}

function addCommaSignWithDollar(id) {
	if ($(id).val() == "") {
	} else {
		var value = $.trim("" + $(id).val().replace('$', '').replace(/,/g, ''));
		var valueWithPoint = Number(value).toFixed(3);
		var valueWithComma;
		if (valueWithPoint % 1 != 0) {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
			/**
			 * @chanegd - Abhishek
			 * @date - 30-12-2015
			 */
			// var regex = /(\.[0-9]{1,2})/g;
			// if ($(regex.exec(valueWithComma)).length == 2){
			// 	valueWithComma = valueWithComma + "0"
			// }
		} else {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
			valueWithComma = valueWithComma + ".00";
		}
		var finalValue = "$" + valueWithComma;
		return $(id).val(finalValue);
	}
}

function addCommaSignWithOutDollar(id) {
	if ($(id).val() == "") {

	} else {
		var value = $.trim("" + $(id).val().replace('$', '').replace(/,/g, ''));
		var valueWithPoint = Number(value).toFixed(2);
		var valueWithComma;
		if (valueWithPoint % 1 != 0) {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
		} else {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
			valueWithComma = valueWithComma + ".00";
		}
		return $(id).val(valueWithComma);
	}
}

function addCommaSignWithDollarWithValue(value) {
	if (value == "" || value == 0) {
		return "$0.00";
	} else {
		value = "" + value;
		value = $.trim("" + value.replace('$', '').replace(/,/g, ''));
		var valueWithPoint = Number(value).toFixed(2);
		var valueWithComma;
		if (valueWithPoint % 1 != 0) {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
		} else {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
			valueWithComma = valueWithComma + ".00";
		}
        return "$" + valueWithComma;
	}
}

function addCommaSignInCapital(id) {
	if ($(id).val() == "") {

	} else {
		var value = $.trim("" + $(id).val().replace('$', '').replace(/,/g, ''));
		var valueWithPoint = Number(value);
		var valueWithComma;
		if (valueWithPoint % 1 != 0) {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
		} else {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
		}
		var finalValue = "$" + valueWithComma;
		return $(id).val(finalValue);
	}
}

function getValueWithComma(value) {
    return Number(value).toLocaleString('en');
}

function addCommaSignWithOutDollarDot(id) {
	if ($(id).val() == "") {

	} else {
		var value = $.trim("" + $(id).val().replace(/,/g, ''));
		var valueWithPoint = Number(value).toFixed(2);
		var valueWithComma;
		if (valueWithPoint % 1 != 0) {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
		} else {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
		}
		return $(id).val(valueWithComma);
	}
}

function isValidStringValueOnly(evt) {
	var theEvent = evt || window.event;
	var key = theEvent.keyCode || theEvent.which;
	key = String.fromCharCode(key);
	var regex = /^[a-zA-Z0-9-\b\t ]*$/; // @Manoj
	if (!regex.test(key)) {
		theEvent.returnValue = false;
		if (theEvent.preventDefault)
			theEvent.preventDefault();
	}
}

function isValidAlphaNumericValueOnly(evt) {
	var theEvent = evt || window.event;
	var key = theEvent.keyCode || theEvent.which;
	key = String.fromCharCode(key);
	var regex = /^[a-zA-Z0-9\b\t]*$/; // @Manoj
	if (!regex.test(key)) {
		theEvent.returnValue = false;
		if (theEvent.preventDefault)
			theEvent.preventDefault();
	}
}

function validatePersoneName(name) {
	var filter = /^[a-zA-Z][a-zA-Z0-9-\b ]*$/; // @Manoj
	return filter.test(name);
}

function validateNumberOnly(mobile) {
	var filter = /^[0-9\.\,]*$/; // @Manoj
	return filter.test(mobile);
}

function validateMoney(mobile) {
	var filter = /^[0-9][\.]]*$/; // @Manoj
	return filter.test(mobile);
}

function returnZeroIfBlank(value) {
	if (value == "") {
		return "0";
	} else {
		return value;
	}
}

function returnZeroWithTwoDecimalIfBlank(value) {
	if (value == "") {
		return "0.00";
	} else {
		return value;
	}
}

function returnZeroWithDollarIfBlank(value) {
	if (value == "") {
		return "$0.00";
	} else {
		return value;
	}
}

function showMessageOnConsole(message) {
	console.log(message);
}


/* Created by Harshit Gupta to remove multiple occurences of Comma 06-04-2015 */

function removeAllCommas(str) {
	str = str.replace(/,/g, "");
	return str;
}

function removeAllCommasAndDollar(str) {
	if (str =="" || str =="undefined" || str ==undefined){
	}else{
        str = str.replace("$", "");
        str = str.replace(/,/g, "");
	}
	return str;
}

function removeAllCommasWithPercent(str) {
	str = str.replace("%", "");
	str = str.replace(/,/g, "");
	return str;
}

/*
 * update By Bhagvan Singh on 08-04-2015 for upperLimit validate start
 */
function percentageLimit() {
	var flag = "";
	for (var i = 1; i <= $("#forward_sales_information_tbody > tr").length; i++) {
		var str = $("#forward_sales_information_tbody_row_upperLimit__" + i).val().replace("%", "");
		// var regexPattern = /^((0|[1-9]\d?)(\.\d{1,2})?|100(\.00?)?)$/;
		if (Number(str) <= 100) {
			flag = true;
		} else {
			customAlerts("Upper Limit value can not be greater then 100%.", "error", 0);
			flag = false;
			return false;
		}
	}
	return flag;
}

function addPercentSign(id) {
	if ($(id).val() == "") {
	} else {
		var value = $.trim("" + $(id).val().replace("%", ""));
		var finalValue = value + "%";
		return $(id).val(finalValue);
	}
}
// end

function addActiveClass(obj) {
	$("#upperNavigationBar li").each(function() {
		$(this).removeClass("active");
	});
	if (obj != "") {
		$(obj).addClass("active");
	}
}

function customAlerts(message, type, time) {
	$("#alertify-logs").html("");
	alertify.log(message.replace(/"/g, '') + ".", type, time);
}

/*function callMethodForProgressBarImage(forField, forAcre) {
	if (strategy == "fields") {
		changeProgressBarNaviagationImage(forField);
	} else {
		changeProgressBarNaviagationImage(forAcre);
	}
}

function changeProgressBarNaviagationImage(globalStepImageNavigation) {
	setOriginalImagesInAdv();
	$(".show_hide_class").addClass("hidden");
	$(".active").removeClass("active parent");
	if (strategy == "fields") {
		if (globalStepImageNavigation == 1) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar1.png");
			$("#farm_information").addClass("visited active parent");
			$("#farm-info").removeClass("hidden");
		} else if (globalStepImageNavigation == 2) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar2.png");
			$("#crop_information").addClass("visited active parent");
			$("#crop_cropinfo").removeClass("hidden");
			setAdvertisementImageForCropSelection();
		} else if (globalStepImageNavigation == 3) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar3.png");
			$("#crop_information").addClass("visited active parent");
			$("#cropinfodetail").removeClass("hidden");
			setAdvertisementImageForCropInfoDetails();
		} else if (globalStepImageNavigation == 4) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar4.png");
			$("#crop-field").addClass("visited active parent");
			$("#plan_by_fields").removeClass("hidden");
		} else if (globalStepImageNavigation == 5) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar4-5.png");
			$("#crop-field").addClass("visited active parent");
			$("#crop_field_choice").removeClass("hidden");
		} else if (globalStepImageNavigation == 6) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar5.png");
			$("#resources").addClass("visited active parent");
			$("#resources_info").removeClass("hidden");
		} else if (globalStepImageNavigation == 7) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar6.png");
			$("#resources").addClass("visited active parent");
			$("#resources_usage").removeClass("hidden");
		} else if (globalStepImageNavigation == 8) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar7.png");
			$("#forward-sales").addClass("visited active parent");
			$("#forward_sales_div").removeClass("hidden");
			setAdvertisementImageForForwardSales();
		} else if (globalStepImageNavigation == 9) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar8.png");
			$("#crop-limits").addClass("visited active parent");
			$("#crop_limits_div").removeClass("hidden");
		}
	} else if (strategy == "acres") {
		if (globalStepImageNavigation == 1) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar1.png");
			$("#farm_information").addClass("visited active parent");
			$("#farm-info").removeClass("hidden");
		} else if (globalStepImageNavigation == 2) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar2.png");
			$("#farm_information").addClass("visited active parent");
			$("#planbyacres").removeClass("hidden");
		} else if (globalStepImageNavigation == 3) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar3.png");
			$("#crop_information").addClass("visited active parent");
			$("#crop_cropinfo").removeClass("hidden");
			setAdvertisementImageForCropSelection();
		} else if (globalStepImageNavigation == 4) {
			$('#cropinfodetail').attr("src", "images/progress_bar/progress-bar4.png");
			$("#crop_information").addClass("visited active parent");
			$("#cropinfodetail").removeClass("hidden");
			setAdvertisementImageForCropInfoDetails();
		} else if (globalStepImageNavigation == 5) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar5.png");
			$("#resources").addClass("visited active parent");
			$("#resources_info").removeClass("hidden");
		} else if (globalStepImageNavigation == 6) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar6.png");
			$("#resources").addClass("visited active parent");
			$("#resources_usage").removeClass("hidden");
		} else if (globalStepImageNavigation == 7) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar7.png");
			$("#forward-sales").addClass("visited active parent");
			$("#forward_sales_div").removeClass("hidden");
			setAdvertisementImageForForwardSales();
		} else if (globalStepImageNavigation == 8) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar8.png");
			$("#crop-limits").addClass("visited active parent");
			$("#crop_limits_div").removeClass("hidden");
		}
	}

}*/

/*
 * function showHelpPopUp(message) { $("#HelpPopUp").html(""); var content = "";
 * for (var i = 0; i < message.length; i++) { content += "<p class=\"helpPopUpContent\">" +
 * message[i] + "</p>"; } $("#HelpPopUp").html(content); }
 */
/*
 * created by Bhagvan Singh on 05-05-2015 For focus on validated text field
 * start
 */

function focusForValidation(id) {
	$("#" + id).css("border", "1px solid red");
	$("#" + id).click(function() {
		$(this).css("border", "1px solid #cccccc");
	});
}

function focusForValidationForObject(obj) {
    $(obj).css("border", "1px solid red");
    $(obj).click(function() {
        $(this).css("border", "1px solid #cccccc");
    });
}
/* end */
/*
 * created by Bhagvan Singh on 06-05-2015 For focus on validated text field
 * start
 */
function totalLandValidationForCropLimit() {
	var isCropLimitAcresValid = true;
	var totalLandWithComma = $("#total_land_available").html();
	var totalLand = totalLandWithComma.replace(/,/g, '');
	var minimum_acrs = 0;
	var maximum_acrs = 0;
	var min_group_sum = 0;
	var max_group_sum = 0;
	$("#crop_limits_table_tbody tr").each(
			function() {
				var max_value = Number($(this).children("td:nth(2)").find(
						"input").val().trim().replace(/,/g, ''));
				var min_value = Number($(this).children("td:nth(3)").find(
						"input").val().trim().replace(/,/g, ''));
				if (max_value != "") {
					maximum_acrs += max_value;
				}
				if (min_value != "") {
					minimum_acrs += min_value;
				}
			});
	if (totalLand <= minimum_acrs && minimum_acrs != 0) {
		customAlerts("Total of the Minimum acres amount should be less than "
				+ '"' + totalLandWithComma + '"', "error", 0);
		isCropLimitAcresValid = false;
	}
	if (totalLand < maximum_acrs && maximum_acrs != 0) {
		customAlerts(
				"Total of the Maximum acres amount should not be greater than "
						+ '"' + totalLandWithComma + '"', "error", 0);
		isCropLimitAcresValid = false;
	}
	$("#add_group_table_tbody tr").each(
			function() {
				var maxGroupValue = Number($(this).children("td:nth(2)").find(
						"input").val().trim().replace(/,/g, ''));
				var minGroupValue = Number($(this).children("td:nth(3)").find(
						"input").val().trim().replace(/,/g, ''));
				if (maxGroupValue != "") {
					max_group_sum += maxGroupValue;
				}
				if (minGroupValue != "") {
					min_group_sum += minGroupValue;
				}
			});
	if (totalLand <= min_group_sum && min_group_sum != 0) {
		customAlerts(
				"Total of the available group Minimum acres amount should be less than "
						+ '"' + totalLandWithComma + '"', "error", 0);
		isCropLimitAcresValid = false;
	}
	if (totalLand < max_group_sum && max_group_sum != 0) {
		customAlerts(
				"Total of the available group Maximum acres amount should not be greater than "
						+ '"' + totalLandWithComma + '"', "error", 0);
		isCropLimitAcresValid = false;
	}
	return isCropLimitAcresValid;
}
/* end */
/*
 * $(document).ready(function(){ alert("Hello of ready");
 * $("#cropinfodetail").on('classChange', function() { alert("Hello in on
 * classChenge"); if($(this).attr("class") == "show_hide_class"){ var html = '<ul><li><img
 * src="images/cropinfodetail1.jpg"></li>' +'<li><img
 * src="images/cropinfodetail2.png"></li>' +'<li><img
 * src="images/cropinfodetail3.gif"></li>' +'<li><img
 * src="images/cropinfodetail4.jpg"></li>' +'<li><img
 * src="images/cropinfodetail5.gif"></li>' +'<li><img
 * src="images/cropinfodetail6.jpg"></li>' +'</ul>';
 * $(".right_side_add").html(html); } }); });
 */
var originalright_side_addHtml = '<ul> <li><img src="images/img3.jpg"></li><li><img src="images/img2.jpg"> </li></ul>';
function setOriginalImagesInAdv() {
	$(".right_side_add").html(originalright_side_addHtml);
}

function setAdvertisementImageForCropInfoDetails() {
	if ($("#cropinfodetail").attr("class") == "show_hide_class") {
		var html = '<ul><li><img src="images/advertised_image/cropinfodetail1.png"></li>'
				+ '<li><img src="images/advertised_image/cropinfodetail2.png"></li>'
				+ '<li><img src="images/advertised_image/cropinfodetail3.png"></li>'
				+ '<li><img src="images/advertised_image/cropinfodetail4.png"></li>'
				+ '<li><img src="images/advertised_image/cropinfodetail5.png"></li>'
				+ '<li><img src="images/advertised_image/cropinfodetail6.png"></li>'
				+ '</ul>';
		$(".right_side_add").html(html);
	}
}

function setAdvertisementImageForForwardSales() {
	if ($("#forward_sales_div").attr("class") == "show_hide_class") {
		var html = '<ul><li><img src="images/advertised_image/forwardSales1.jpg"></li>'
				+ '<li><img src="images/advertised_image/forwardSales2.jpg"></li>'
				+ '<li><img src="images/advertised_image/forwardSales3.jpg"></li>'
				+ '<li><img src="images/advertised_image/forwardSales4.jpg"></li>'
				+ '</ul>';
		$(".right_side_add").html(html);
	}
}

function setAdvertisementImageForCropSelection() {
	if ($("#crop_cropinfo").attr("class") == "show_hide_class") {
		var html = '<ul><li><img src="images/advertised_image/cropSelection1.png"></li>'
				+ '<li><img src="images/advertised_image/cropSelection2.png"></li>'
				+ '<li><img src="images/advertised_image/cropSelection3.png"></li>'
				+ '<li><img src="images/advertised_image/cropSelection4.png"></li>'
				+ '</ul>';
		$(".right_side_add").html(html);
	}
}

function changeStrategyAndFrom() {
	manageStep1 = false;
	manageStep2 = false;
	manageStep3 = false;
	manageStep4 = false;
	manageStep5 = false;
	manageStep6 = false;
	$(".visited").removeClass("visited");
	$("#farm_information").addClass("visited");
}

function enableDisableMenu() {
	if (strategy == "fields") {
		$("#crop-field").css("display", "");
	} else {
		$("#crop-field").css("display", "none");
	}
	$(".visited").removeClass("visited");
	$("#farm_information").addClass("visited");
	if (manageStep1) {
		$("#crop_information").addClass("visited");
	} else {
		return false;
	}
	if (strategy == "fields" && manageStep2) {
		$("#crop-field").addClass("visited");
	}
	if (manageStep3) {
		$("#resources").addClass("visited");
	} else {
		return false;
	}
	if (manageStep4) {
		$("#forward-sales").addClass("visited");
	} else {
		return false;
	}
	if (manageStep5) {
		$("#crop-limits").addClass("visited");
	} else {
		return false;
	}

}

function addErrorClassOnObject(object) {
	$("." + errorClassName).each(function() {
		$(this).removeClass(errorClassName);
	});
	$(object).addClass(errorClassName);
	$(object).bind("click", function() {
		removeErrorClassFormObjects(object);
	});
}

function removeErrorClassFormObjects(object) {
	if ($(object).hasClass(errorClassName)) {
		$(object).removeClass(errorClassName);
	}
}

function addCommaSignWithDollarForText(id) {
	if ($(id).text() == "") {
	} else {
		var value = $.trim("" + $(id).text().replace('$', '').replace(/,/g, ''));
		var valueWithComma;
		valueWithComma = Number(value).toLocaleString('en');
		var finalValue = "$" + valueWithComma;
		return $(id).text(finalValue);
	}
}

function addCommaSignWithDollarForTextWithOutId(obj) {
	//var value = id.replace('$', '').replace(/,/g, '');
		var valueWithComma;
		valueWithComma = Number(obj).toLocaleString('en');
		//alert(valueWithComma);
		var finalValue = "$" + valueWithComma;
		return finalValue;
}

function addCommaSignForTextWithOutId(id) {
	//var value = id.replace('$', '').replace(/,/g, '');
	var valueWithComma;
	if(id == "-"){
		valueWithComma = "-";
	} else {
		valueWithComma = Number(id).toLocaleString('en');
		//alert(valueWithComma);
		//var finalValue = valueWithComma;
	}
	return valueWithComma;
	
}

function applyTabingOnSidemenu(){
	$('#sidemenu a').on('click', function(e) {
		e.preventDefault();

		if ($(this).hasClass('open')) {
			// do nothing because the link is already open
		} else {
			var oldcontent = $('#sidemenu a.open').attr('href');
			var newcontent = $(this).attr('href');

			$(oldcontent).fadeOut(200, function() {
				/**
				 * @changed- Abhishek
				 * @date - 23-01-2016
				 * @desc - changed according to slide# 12 of 01042015
				 */
				/*$(newcontent).fadeIn().removeClass('hidden');*/
				$(newcontent).fadeIn(100).removeClass('hidden');
				$(oldcontent).addClass('hidden');
			});

			$('#sidemenu a').removeClass('open');
			$(this).addClass('open');
		}
	});
}

/*	*************************************	Country and State functionality   **************************************/

function physicalAddressSameForMailing(target){

	if ($(target).find('input[name="physicalSameCheckBox"]').prop('checked')) {
		$(target).find('.mailing-address-state').html($(target).find('.physical-address-state').html());

		$(target).find('.mailing-address-line1').val($(target).find('.physical-address-line1').val());
		$(target).find('.mailing-address-line2').val($(target).find('.physical-address-line2').val());
		$(target).find('.mailing-address-city').val($(target).find('.physical-address-city').val());
		$(target).find('.mailing-address-state').val($(target).find('.physical-address-state').val());
		$(target).find('.mailing-address-country').val($(target).find('.physical-address-country').val());
		$(target).find('.mailing-zip').val($(target).find('.physical-zip').val());
	} else {
		$(target).find('.mailing-address-state').html("<option value='' selected>Select Country First</option>");

		$(target).find('.mailing-address-line1').val('');
		$(target).find('.mailing-address-line2').val('');

		$(target).find('.mailing-address-city').val('');

		$(target).find('.mailing-address-state').val('');
		$(target).find('.mailing-address-country').val('');
		$(target).find('.mailing-zip').val('');
	}


}

function getStatesForCountry(container, target){

	var countryId = 0;
	if(typeof target != "undefined" && target == "physical"){
		countryId = $(container).find('.physical-address-country').val();
	} else if(typeof target != "undefined" && target == "mailing"){
		countryId = $(container).find('.mailing-address-country').val();
	} else if(typeof target != "undefined" && target == "issue"){
        countryId = $(container).find('select[name="country"]').val();
    } else {
		return;
	}

	if (countryId != "") {
		$.ajax({
			url: "agriculture/accountController/getStatesForCountry",
			type: "post",
			beforeSend: showLoadingImage(),
			data: {
				countryId: countryId
			},
			success: function (response) {
				var status = response.status;
				var result = response.result;
				if (status == "success") {

					var html = "<option value='' selected>Select State</option>";
					$(result).each(function (key, value) {
						html += "<option value='" + value.id + "'>" + value.stateName + "</option>";
					});

					if (typeof target != "undefined" && target == "physical") {
						$(container).find('.physical-address-state').html(html);
					} else if (typeof target != "undefined" && target == "mailing") {
						$(container).find('.mailing-address-state').html(html);
					} else if(typeof target != "undefined" && target == "issue"){
                        $(container).find('select[name="state"]').html(html);
                    }

				}
			},
			error: function () {

			},
			complete: function () {
				hideLoadingImage();
			}
		});
	}

}

function navigateToContributionPage(url){

	localStorage.setItem('navigateFlag', "true");
	window.location = url;
}