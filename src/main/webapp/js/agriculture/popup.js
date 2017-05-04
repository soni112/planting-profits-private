//Function To Display Popup
function div_show() {

	document.getElementById('abc').style.display = "block";
}

// Function to Hide Popup
function div_hide() {

	document.getElementById('abc').style.display = "none";
}

// Function To Display Popup
function div_show1() {
	document.getElementById('abc').style.display = "block";
}

// Function to Hide Popup
function div_hide1() {
	removeErrorClassFormObjects("#farm-name");
	removeErrorClassFormObjects("#physical-localtion");
	document.getElementById('abc').style.display = "none";
}

// Function To Display Popup
function div_show2() {

	document.getElementById('register').style.display = "block";
}

// Function to Hide Popup
function div_hide2() {
	document.getElementById('register').style.display = "none";
}

// Function To Display Popup
function div_show3() {

	document.getElementById('select-strategies-pop-up').style.display = "block";
}

// Function to Hide Popup
function div_hide3() {

	document.getElementById('select-strategies-pop-up').style.display = "none";
}

// Function To Display Popup
function div_show4() {
	$("#add-field-span-dynamic").html("Update Field");
	document.getElementById('modify_field_button').style.display = "block";
	document.getElementById('add_field_button').style.display = "none";
	document.getElementById('add-new-crop-pop-up').style.display = "block";
}

function div_show4ForAddField() {
	$("#add-field-span-dynamic").html("Add Field");
	document.getElementById('modify_field_button').style.display = "none";
	document.getElementById('add_field_button').style.display = "block";
	document.getElementById('add-new-crop-pop-up').style.display = "block";
}

// Function to Hide Popup
function div_hide4() {
	removeErrorClassFormObjects("#pop-up-field-name");
	removeErrorClassFormObjects("#pop-up-field-size");
	document.getElementById('add-new-crop-pop-up').style.display = "none";
}

// Function To Display Popup
function div_show5() {
	$("input[name='crop_type']").attr('checked', false);
	$("#crop_name").val('');
	document.getElementById('add-new-crop-and-crop-info').style.display = "block";
}

// Function to Hide Popup
function div_hide5() {
	removeErrorClassFormObjects("#crop_name");
	document.getElementById('add-new-crop-and-crop-info').style.display = "none";
}

// Function To Display Popup
function div_show6() {
	$("#resourse_name").val('');
	$("#resourse_unit_name").val('');

	document.getElementById('add-newresource-popup').style.display = "block";
}

// Function to Hide Popup
function div_hide6() {

	$('#resourse_name').val("");
	$('#resourse_unit_name').val("");
	removeErrorClassFormObjects("#resourse_name");
	removeErrorClassFormObjects("#resourse_unit_name");
	document.getElementById('add-newresource-popup').style.display = "none";
}

function div_show7() {

	document.getElementById('forgetPassword').style.display = "block";
}

function div_hide7() {
	document.getElementById('forgetPassword').style.display = "none";
}

function div_show8() {

	document.getElementById('AccountRecoveryPopUp').style.display = "block";
}

function div_hide8() {
	document.getElementById('AccountRecoveryPopUp').style.display = "none";
}

function div_show9() {

	$("#user_current_password").val("");
	$("#user_new_password").val("");
	$("#user_confirm_password").val("");
	document.getElementById('ChangePasswordPopUp').style.display = "block";
}

function div_hide9() {
	document.getElementById('ChangePasswordPopUp').style.display = "none";
}

function div_show10() {
	document.getElementById('add-newCropProductionCostsDetails-popup').style.display = "block";
}

function div_hide10() {

	document.getElementById('add-newCropProductionCostsDetails-popup').style.display = "none";
}

// Function To Display Popup
function div_show11() {
	$("#group_name").val("");

	document.getElementById('add_group').style.display = "block";
	document.getElementById('add_group_button').style.display = "block";
	document.getElementById('modify_group').style.display = "none";
	document.getElementById('modify_group_button').style.display = "none";
	document.getElementById('add-new-group-and-crop').style.display = "block";
}

// Function to Hide Popup
function div_hide11() {
	removeErrorClassFormObjects("#group_name");
	document.getElementById('add-new-group-and-crop').style.display = "none";
}

function div_show12() {
	document.getElementById('add_group').style.display = "none";
	document.getElementById('add_group_button').style.display = "none";
	document.getElementById('modify_group').style.display = "block";
	document.getElementById('modify_group_button').style.display = "block";
	document.getElementById('add-new-group-and-crop').style.display = "block";
}

// Function to Hide Popup
function div_hide12() {

	document.getElementById('add-new-group-and-crop').style.display = "none";
}

// add method by rohit 6-4-15
function div_hidePlanByField() {
	if ($("#plan-by-field-tbody > tr").length <= 1) {
		$("#Plan_by_Fields_thead").hide();
		$("#plan-by-field-tbody").hide();

		// alert("hello");
	} else {
		$("#Plan_by_Fields_thead").show();
		$("#plan-by-field-tbody").show();
	}
}

// Function To Display Popup
function div_showUpdateFarmDiv() {

	document.getElementById('updateFarmDiv').style.display = "block";
}

// Function to Hide Popup
function div_hideUpdateFarmDiv() {

	document.getElementById('updateFarmDiv').style.display = "none";
}

// Function To Display Popup
function div_show13() {
	document.getElementById('info').style.display = "block";
}

// Function to Hide Popup
function div_hide13() {
	document.getElementById('info').style.display = "none";
}

/* Added by Harshit Gupta for Loading image */
function showLoadingImage() {
	// $('#loadingImage').css("display", "block");
	$('#loadingImage').show()
}

function hideLoadingImage() {
	// $('#loadingImage').css("display", "none");
	$('#loadingImage').hide();
}

function showLoadingImageForStrategy() {
	$('#loadingImageForStrategy').css("display", "block");
}

function hideLoadingImageForStrategy() {
	$('#loadingImageForStrategy').css("display", "none");
}

/**
 * @added - Abhishek
 * @changed - 23-01-2016
 * @desc - function for getting comma applied value
 */
function addCommaSignWithOutDollarDotForDirectValue(value) {
	if (value == "") {

	} else {
		//var value = $.trim("" + $(value).replace(/,/g, ''));
		var valueWithPoint = Number(value).toFixed(2);
		var valueWithComma;
		if (valueWithPoint % 1 != 0) {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
		} else {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
		}
		return valueWithComma;
	}
}

function showCriticalMessagePopup(resourceName, profitBy1Dollar, lossByOneDollar, upResourceLimit, downResourceLimit, unit, primalValue) {
	$("#resourceNameInc").html(resourceName);
	$("#resourceNameDec").html(resourceName);
	$("#profitBy1Dollar").html(profitBy1Dollar);
	$("#lossBy1Dollar").html(lossByOneDollar);
	/**
	 * @changed - Abhishek
	 * @date - 23-01-2016
	 * @desc - changed according to slide# 18 of 01042016
	 */
	/*$("#upResourceLimit").html(upResourceLimit);*/
	$("#upResourceLimit").html(addCommaSignWithOutDollarDotForDirectValue(primalValue) + " + " + upResourceLimit);
	$("#downResourceLimit").html(downResourceLimit);
	$("#unitCriticalMessage").html(unit);
	document.getElementById('critical-message-pop-up').style.display = "block";
}

/**
 * @added - Abhishek
 * @changed - 04-01-2016
 * @desc - function for guidelines for crops and cropGroups
 */
function showCropOrGroupCriticalMessage(cropOrGroupName, minimumDualValue, maximumDualvalue, maximumPrimalValue, minimumPrimalValue){
	$('.cropOrGroupName').html(cropOrGroupName);

	$('#maximumDualvalue').html(maximumDualvalue);
	$('#maximumPrimalValue').html(maximumPrimalValue);

	$('#minimumDualvalue').html(minimumDualValue);
	$('#minimumPrimalValue').html(minimumPrimalValue);

	$('#criticalMessageForCropOrGroupPopUp').show();
}


function hideCriticalMessagePopup() {
	document.getElementById('critical-message-pop-up').style.display = "none";
}

/**
 * @author Raja
 * @since 14 Dec, 2015
 * @summary Show error message when Estimated Income is negative
 */

function showPotentialProfitCriticalMessagePopup(obj) {

    var cropName = $(obj).parent().find("td:eq(0)").text();
    var potentialProfit = $(obj).find("input").val();

    if(potentialProfit.indexOf("-") != -1){
        $(".cropNameForPopup").html(cropName);
        $("#potentialProfitForPopup").html(potentialProfit);
        document.getElementById('critical-message-pop-up').style.display = "block";
    }
}

function hidePotentialProfitCriticalMessagePopup() {
    $(".cropNameForPopup").html("");
    $("#potentialProfitForPopup").html("");
	document.getElementById('critical-message-pop-up').style.display = "none";
}

function showSensetiveAnalysisCropAndResourcePopup() {
	document.getElementById('sensetiveAnalysisCropAndResourcePopup').style.display = "block";
}

function hideSensetiveAnalysisCropAndResourcePopup() {
	document.getElementById('sensetiveAnalysisCropAndResourcePopup').style.display = "none";

	/**
	 * @changed - Abhishek
	 * @date - 15-12-2015
	 */
	//$("#sensetiveAnalysisCropAndResourceTable thead").html('');
	//$("#sensetiveAnalysisCropAndResourceTable tbody").html('');
}


function showCreateNewScenario() {
	document.getElementById('createNewScenario').style.display = "block";
}
	
//Function to Hide Popup
function hideCreateNewScenario(){
	document.getElementById('createNewScenario').style.display = "none";
}