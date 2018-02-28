$(document).ready(function() {
	$('body').on('focus', ".datepicker", function() {
		$(this).datepicker({
			clearBtn : true,
			autoclose : true,
		});
	});

	$(".strategy").change(function() {
		onStrategyChange();
	});
// $(".crops").change(function() {
// onCropSelectedOrRemoved($(this));
// });
// $(".resources").change(function() {
// onResourceSelectedOrRemoved($(this));
// });
	if($("#Plan_by_Fields_table tbody tr").length<1){
		$("#Plan_by_Fields_table").hide();
	}
	hideLoadingImage();
});
var type_success = "success";
var type_error = "error";
var type_warning = "warning";
var time = 0;

var manageStep1 = false;
var manageStep2 = false;
var manageStep3 = false;
var manageStep4 = false;
var manageStep5 = false;
var manageStep6 = false;
var oldStrategy = "";
var strategy = "";
var availableLand = 0;
var currency = "$";
var uom = "Acre";
var uomCapitals = "Acres";

function disableLeftMenu(stepNumber) {
	if (stepNumber < 2) {
		manageStep2 = false;
		$("#crop_information").removeClass("visited");
	}
	if (strategy == "fields" && stepNumber < 3) {
		manageStep3 = false;
		$("#field-choice").removeClass("visited");
	}
	if (stepNumber < 4) {
		manageStep4 = false;
		$("#resources").removeClass("visited");
	}
	if (stepNumber < 5) {
		manageStep5 = false;
		$("#forward-sales").removeClass("visited");
	}
	if (stepNumber < 6) {
		manageStep6 = false;
		$("#crop-limits").removeClass("visited");
	}
}
function enableLeftMenu(stepNumber) {
	if (stepNumber >= 2) {
		manageStep2 = true;
		$("#crop_information").addClass("visited");
	}
	if (strategy == "fields" && stepNumber >= 3) {
		manageStep3 = true;
		$("#field-choice").addClass("visited");
	}
	if (stepNumber >= 4) {
		manageStep4 = true;
		$("#resources").addClass("visited");
	}
	if (stepNumber >= 5) {
		manageStep5 = true;
		$("#forward-sales").addClass("visited");
	}
	if (stepNumber >= 6) {
		manageStep6 = true;
		$("#crop-limits").addClass("visited");
	}
}
function enableDisableLeftMenu(stepNumber) {
	disableLeftMenu(stepNumber);
	enableLeftMenu(stepNumber);
}

function changePageAndProgressBarImage(globalStepImageNavigation) {
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
			manageStep2 = true;
		} else if (globalStepImageNavigation == 3) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar3.png");
			$("#crop_information").addClass("visited active parent");
			$("#cropinfodetail").removeClass("hidden");
			setAdvertisementImageForCropInfoDetails();
			manageStep2 = true;
		} else if (globalStepImageNavigation == 4) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar4.png");
			$("#field-choice").addClass("visited active parent");
			$("#plan_by_fields").removeClass("hidden");
			manageStep3 = true;
		} else if (globalStepImageNavigation == 5) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar4-5.png");
			$("#field-choice").addClass("visited active parent");
			$("#crop_field_choice").removeClass("hidden");
			manageStep3 = true;
		} else if (globalStepImageNavigation == 6) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar5.png");
			$("#resources").addClass("visited active parent");
			$("#resources_info").removeClass("hidden");
			manageStep4 = true;
		} else if (globalStepImageNavigation == 7) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar6.png");
			$("#resources").addClass("visited active parent");
			$("#resources_usage").removeClass("hidden");
			manageStep4 = true;
		} else if (globalStepImageNavigation == 8) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar7.png");
			$("#forward-sales").addClass("visited active parent");
			$("#forward_sales_div").removeClass("hidden");
			setAdvertisementImageForForwardSales();
			manageStep5 = true;
		} else if (globalStepImageNavigation == 9) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar8.png");
			$("#crop-limits").addClass("visited active parent");
			$("#crop_limits_div").removeClass("hidden");
			manageStep6 = true;
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
			manageStep2 = true;
		} else if (globalStepImageNavigation == 4) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar4.png");
			$("#crop_information").addClass("visited active parent");
			$("#cropinfodetail").removeClass("hidden");
			setAdvertisementImageForCropInfoDetails();
			manageStep2 = true;
		} else if (globalStepImageNavigation == 5) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar5.png");
			$("#resources").addClass("visited active parent");
			$("#resources_info").removeClass("hidden");
			manageStep4 = true;
		} else if (globalStepImageNavigation == 6) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar6.png");
			$("#resources").addClass("visited active parent");
			$("#resources_usage").removeClass("hidden");
			manageStep4 = true;
		} else if (globalStepImageNavigation == 7) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar7.png");
			$("#forward-sales").addClass("visited active parent");
			$("#forward_sales_div").removeClass("hidden");
			setAdvertisementImageForForwardSales();
			manageStep5 = true;
		} else if (globalStepImageNavigation == 8) {
			$('#image_bar').attr("src", "images/progress_bar/progress-bar8.png");
			$("#crop-limits").addClass("visited active parent");
			$("#crop_limits_div").removeClass("hidden");
			manageStep6 = true;
		}
	}
}

function callMethodForPageChangeAndProgressBarImage(forField, forAcre) {
	if (strategy == "fields") {
		changePageAndProgressBarImage(forField);
	} else {
		changePageAndProgressBarImage(forAcre);
	}
}

/*All Onchange Functions Start*/
function onStrategyChange() {
	strategy = $("input[name=plan_by_farm]:checked").val().trim();
	if (strategy != oldStrategy) {
		manageStep1 = false;
		manageStep2 = false;
		manageStep3 = false;
		manageStep4 = false;
		manageStep5 = false;
		manageStep6 = false;
		enableDisableLeftMenu(1);
		oldStrategy = strategy;
	}
	if (strategy == "fields") {
		$("#field-choice").removeClass("hidden");
		$("#show_hide_field_variance_button").show();
	} else if (strategy == "acres") {
		$("#field-choice").addClass("hidden");
		$("#show_hide_field_variance_button").hide();
	}
}

function onCropSelectedOrRemoved(cropObject) {
	if ($(cropObject).prop("checked") == true) {
		addCropInAllTables($(cropObject).val());
	} else if ($(cropObject).prop("checked") == false) {
		alertify.confirm('Are you sure you want to remove this crop with name "'+$(cropObject).val()+'" ?', function(e) {
			if (e) {
				removeCropFromAllTables($(cropObject).val());
			} else {
				$(cropObject).prop("checked", true);
			}
		});
	}
}

function onResourceSelectedOrRemoved(resourceObject) {
	if ($(resourceObject).prop("checked") == true) {
		addResourcesInAllTables(resourceObject);
	} else if ($(resourceObject).prop("checked") == false) {
		alertify.confirm('Are you sure you want to remove this resource with name "'+$(resourceObject).parent().parent().children("td:nth(1)").text().trim()+'" ?', function(e) {
			if (e) {
				removeResourceFromAllTables(resourceObject);
			} else {
				$(resourceObject).prop("checked", true);
			}
		});
	}
}


/* All OnChange function End */

/*All Validation functions Start*/

function validateFarmInformation() {
	if ($("input[name=plan_by_farm]:checked").length == 0) {
		/*change by rohit Strategy to strategy*/
		customAlerts("Please select your strategy", type_error, time);
		return false;
	} else {
		return true;
	}
}

function validatePlanByAcres() {
	availableLand = Number($("#acres_value").val());
	if (availableLand <= 0) {
		customAlerts("Please enter land value for planting", type_error, time);
		return false;
	} else {
		return true;
	}
}

function validateCropsAndCropsInformation() {
	if ($("input:checkbox[class=crops]:checked").length <= 0) {
		customAlerts("Select the crops you are considering planting", type_error, time);
		return false;
	} else {
		return true;
	}
}

function validateCropsInformationDetails() {
	var validationFlag = true;
	$("#cropInformationDetailFirstTable tbody tr").each(function(){
		validationFlag = true;
		if(Number($(this).children("td:nth(2)").find("input").val()) == ""){
			customAlerts('Please fill Expected Yield value for "'+$(this).children("td:nth(0)").text()+'" crop', type_error, time);
			validationFlag = false;
		}else if(Number($(this).children("td:nth(3)").find("input").val()) != "" && Number($(this).children("td:nth(2)").find("input").val()) >= Number($(this).children("td:nth(3)").find("input").val())){
			customAlerts('Maximum Yield value should be greater than Expected Value for "'+$(this).children("td:nth(0)").text()+'" crop', type_error, time);
			validationFlag = false;
		}else if(Number($(this).children("td:nth(4)").find("input").val()) != "" && Number($(this).children("td:nth(2)").find("input").val()) <= Number($(this).children("td:nth(4)").find("input").val())){
			customAlerts('Minimum Yield value should be less than Expected Value for "'+$(this).children("td:nth(0)").text()+'" crop', type_error, time);
			validationFlag = false;
		}else if(Number($(this).children("td:nth(5)").find("input").val()) == ""){
			customAlerts('Please fill Expected Price value for "'+$(this).children("td:nth(0)").text()+'" crop', type_error, time);
			validationFlag = false;
		}else if(Number($(this).children("td:nth(6)").find("input").val()) != "" && Number($(this).children("td:nth(5)").find("input").val()) >= Number($(this).children("td:nth(6)").find("input").val())){
			customAlerts('Maximum price value should be greater than Expected price value for "'+$(this).children("td:nth(0)").text()+'" crop', type_error, time);
			validationFlag = false;
		}else if(Number($(this).children("td:nth(7)").find("input").val()) != "" && Number($(this).children("td:nth(5)").find("input").val()) >= Number($(this).children("td:nth(7)").find("input").val())){
			customAlerts('Minimum price value should be less than Expected price value for "'+$(this).children("td:nth(0)").text()+'" crop', type_error, time);
			validationFlag = false;
		}else if(Number($(this).children("td:nth(8)").find("input").val()) == ""){
			customAlerts('Please enter production cost for "'+$(this).children("td:nth(0)").text()+'" crop name', type_error, time);
			validationFlag = false;
		}
		if(!validationFlag){
			return false;
		}
	});
	validationFlagGlobal = true;
	$("#cropInformationDetailFirstTable tbody tr").each(function(){
		if($(this).children("td:nth(8)").find("input").val() == "$0.00"){
			validationFlagGlobal = false;
		}
	});
	if($("#cropInformationDetailFirstTable tbody tr").length > 0 && validationFlag){
		return true;	
	}
}

function confirmValidateCropsInformationDetails() {
	var validationFlag = true;
	var cropName = "";
	$("#cropInformationDetailFirstTable tbody tr").each(function(){
		if($(this).children("td:nth(8)").find("input").val() == "$0.00"){
			cropName += $(this).children("td:nth(0)").text().trim()+", ";
			validationFlag = false;
		}
	});
	if(validationFlag == false){
	alertify.confirm('$0.00 is not a valid variable production cost value for "'+cropName.substring(0, cropName.length-2)+'" crop but if you want to continue then click ok otherwise cancel?', function(e) {
		if (e) {
			callMethodForPageChangeAndProgressBarImage(4, 5);
		}
	});
	}
}

function validatePlanByField() {
	if ($("#Plan_by_Fields_table tbody tr").length < 2) {
		customAlerts("Please add at least two fields", type_error, time);
		return false;
	} else {
		return true;
	}
}
function warningFallowPlanByField() {
	var fallowFieldName = "";
	$("#Plan_by_Fields_table tbody tr").each(function(){
		if($(this).children("td:nth(4)").find("input").prop("checked") == true){
			fallowFieldName += $(this).children("td:nth(1)").text().trim()+", ";
		}
	});
	if(fallowFieldName.length > 0){
		customAlerts('You marked "'+fallowFieldName.substring(0, fallowFieldName.length-2)+'" as Fallow so this field is not available for planting. If you would like to plant or consider planting "'+fallowFieldName.substring(0, fallowFieldName.length-2)+'", go back to the previous screen and uncheck the Fallow box', type_error, time);
	}
}

function validateCropFieldChoice() {
	if ($("#field_choice_crop_table tbody tr").length < 2) {
		return false;
	}
	var outerValidateCropFieldChoice = true;
	var fieldName = "";
	$("#field_choice_crop_table tbody tr").each(function() {
		var validateCropFieldChoice = false;
		var fallowFlag = false;
		fieldName = $(this).children("td:nth(0)").text();
		$(this).children("td").each(function() {
			if ($(this).find("input").prop("checked") == true) {
				validateCropFieldChoice = true;
			}if ($(this).find("input").prop("disabled") == true) {
				fallowFlag = true;
			}
		});
		if (validateCropFieldChoice == false && fallowFlag == false) {
			outerValidateCropFieldChoice = false;
			return outerValidateCropFieldChoice;
		}
	});
	if (outerValidateCropFieldChoice == false) {
		customAlerts('There are no crops selected for field "' + fieldName + '"', type_error, time);
		return false;
	} else {
		return true;
	}
}

function validateResources() {
	var resourcesName = "";
	var resourcesFlag = true;
	if ($("#total_capital_available").val() == "") {
		customAlerts("Please enter amount for Capital resource", type_error, time);
		return false;
	}
	if (Number(removeAllCommasAndDollar($("#total_capital_available").val())) > 10000000) {
		customAlerts("Value for Capital can not be more then $10,000,000.00", type_error, time);
		return false;
	}
	$("#manage_resource tbody tr").each(function(){
		if($(this).children("td:nth(1)").text().trim() != "Land" && $(this).children("td:nth(1)").text().trim() != "Capital"){
			if($(this).children("td:nth(0)").find("input").prop("checked") == true && $(this).children("td:nth(3)").find("input").val() == ""){
				resourcesName += $(this).children("td:nth(1)").text().trim()+", ";
				resourcesFlag = false;
			}
		}
	});
	if(resourcesFlag == false){
		customAlerts('Please enter amount for "'+resourcesName.substring(0, resourcesName.length-2)+'" resources', type_error, time);
		return false;
	} else {
		return true;
	}
}

function warningValidateResources() {
	var resourceName = "";
	$("#manage_resource tbody tr").each(function(){
		if($(this).children("td:nth(1)").text().trim() != "Land" && $(this).children("td:nth(1)").text().trim() != "Capital"){
			if($(this).children("td:nth(3)").find("input").val() != "" && $(this).children("td:nth(0)").find("input").prop("checked") == false){
				resourceName += $(this).children("td:nth(1)").text().trim()+", ";
			}
		}
	});
	if(resourceName.length > 0){
	customAlerts('In Resource screen "'+resourceName.substring(0, resourceName.length-2)+'" resource is not selected. If you want to use this resource, go back to Resource screen and select the checkbox', type_warning, time);
	}
}

function validateCropResourceUsage() {
	var indexNumber = 2;
	var outerValidation = true;
	$("#crop_resource_usage thead tr td:gt(2)").each(function(){
		var resourceName = $(this).children("span:nth(0)").text().trim();
		indexNumber++;
		var resourceUsed = 0;
		var resourceAmount = 0
		$("#crop_resource_usage tbody tr").each(function(){
			resourceUsed += Number(removeAllCommas($(this).children("td:nth("+indexNumber+")").find("input").val()));
		});	
		var validation = true;
	$("#manage_resource tbody tr:gt(1)").each(function(){
		if($(this).children("td:nth(1)").text().trim() == resourceName ){
			if(Number(removeAllCommas($(this).children("td:nth(3)").find("input").val())) < resourceUsed){
				resourceAmount= $(this).children("td:nth(3)").find("input").val();
				validation = false;
			}
			return false;
		}
	});
	if(validation == false){
		customAlerts('"'+resourceName+'" amount for all the crops cannot be greater than total "'+resourceName+'" amount "'+resourceAmount+'". If you would like to use this amount then, go back to the previous screen and increase the total "'+resourceName+'" amount', type_error, time);
		outerValidation = false;
		return outerValidation;
	}
	});
	return outerValidation;
}

function validateForwardSales() {
	var totalForwardAcres = 0;
	var validationFlag = true;
	$("#forward_sales_information tbody tr").each(function(){
		if($(this).children("td:nth(4)").find("input").val() != ""){
		totalForwardAcres += Number(removeAllCommas($(this).children("td:nth(4)").find("input").val()));
		}
		if($(this).children("td:nth(7)").find("input").val() != "" && Number(removeAllCommasWithPercent($(this).children("td:nth(7)").find("input").val())) > 100){
			customAlerts("Upper Limit value can not be greater then 100%", type_error, time);
			validationFlag = false;
			return validationFlag;
		}
	});
	if(validationFlag == true && totalForwardAcres > Number(removeAllCommas($("#total_land_available").text().trim()))){
		customAlerts("Total acres in forward sales can not be greater then total available land", type_error, time);
		validationFlag = false;
	}
	return validationFlag;
}
/*All Validations Ends*/


/*All Next function Start*/
function nextFarmInformation() {
	if (validateFarmInformation()) {
		if (strategy == "both") {
			div_show3();
			return false;
		} else {
			callMethodForPageChangeAndProgressBarImage(2, 2);
		}
	}
}

function nextPlanByAcres() {
	if (validatePlanByAcres()) {
		callMethodForPageChangeAndProgressBarImage(2, 3);
	}
}

function nextCropsAndCropsInformation() {
	if (validateCropsAndCropsInformation()) {
		callMethodForPageChangeAndProgressBarImage(3, 4);
	}
}
var validationFlagGlobal = true;
function nextCropsInformationDetails() {
	var validationOutput = validateCropsInformationDetails();
	if (validationOutput && validationFlagGlobal) {
		callMethodForPageChangeAndProgressBarImage(4, 5);
	}else if(validationOutput == true && validationFlagGlobal == false){
		confirmValidateCropsInformationDetails();
	}
}

var warningFallowPlanByFieldFlag = true;
function nextPlanByField() {
	if (validatePlanByField()) {
		callMethodForPageChangeAndProgressBarImage(5, 0);
		if (warningFallowPlanByFieldFlag == true) {
			warningFallowPlanByField();
			warningFallowPlanByFieldFlag = false;
		}
	}
}

function nextCropFieldChoice(){
	if (validateCropFieldChoice()) {
		callMethodForPageChangeAndProgressBarImage(6, 0);
	}
}
var warningMessageForOneTimeOnly = true;
function nextResources(){
	if (validateResources()) {
		if(warningMessageForOneTimeOnly){
			warningValidateResources();
			warningMessageForOneTimeOnly = false;
		}
		callMethodForPageChangeAndProgressBarImage(7, 6);
	}
}

function nextCropResourceUsage() {
	if (validateCropResourceUsage()) {
		callMethodForPageChangeAndProgressBarImage(8, 7);
	}
}

function nextForwardSales() {
	if (validateForwardSales()) {
		callMethodForPageChangeAndProgressBarImage(9, 8);
	}
}
/*All Next function End*/

function selectStrategyInCaseOfBoth() {
	if ($("input[name=plan_by_farm_name]:checked").length > 0) {
		strategy = $("input[name=plan_by_farm_name]:checked").val();
		$("input[name=plan_by_farm][value=both]").removeAttr("checked");
		$("input[name=plan_by_farm][value=both]").prop("checked", false);
		if (strategy == "fields") {
			$("input[name=plan_by_farm][value=fields]").attr("checked", "checked");
			$("input[name=plan_by_farm][value=fields]").prop("checked", true);
			$("#field-choice").removeClass("hidden");
		} else if (strategy == "acres") {
			$("input[name=plan_by_farm][value=acres]").prop("checked", true);
			$("input[name=plan_by_farm][value=acres]").attr("checked", "checked");
			$("#field-choice").addClass("hidden");
		}
		div_hide3();
		nextFarmInformation();
	} else {
		div_hide3();
		customAlerts(select_a_strategy_first, select_a_strategy_first_type, select_a_strategy_first_time);
	}
}

var defaultUOMForCrop = "bushels";
function addCropInAllTables(cropName) {
	var rowHTMLForCropInformationDetails1 = '<tr class="tblbclgrnd text-center"><td class="tblft1">'+cropName+'</td><td class="success uomtext"><select class="crop_selection_UOM" onchange="changeCropUOM(this)"> <option value="bushels">bushels</option><option value="crates">crates</option><option value="hundredweight">hundredweight</option><option value="kilograms">kilograms</option><option value="pounds">pounds</option><option value="tons">tons</option>	</select></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithForOnePoint(this);changeExpectedYieldValue(this)"></td>	<td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithForOnePoint(this);changeMaximumYieldValue(this)"></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithForOnePoint(this);changeMinimumYieldValue(this)"></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this)"></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this)"></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this)"></td>	<td class="success infotext"><br><input type="text" onkeypress="return isValidNumberValue(event)" placeholder="$0" onchange="addCommaSignWithDollar(this);variableProductionCostChange(this)"> <br>	<span class="pull-right"><a onclick="showOptionalCropInformationDiv(\''+cropName+'\')">Details</a></span></td></tr>';
	var rowHTMLForCropInformationDetails2 = '<tr class="tblbclgrnd text-center"><td class="tblft1">'+cropName+'</td><td class="success"><input type="checkbox"></td><td class="success"><input type="checkbox"></td><td class="success"><input type="checkbox"></td></tr>';
	var rowHTMLForCropInformationDetails3 = '<tr class="tblbclgrnd text-center"><td class="tblft1">'+cropName+'</td><td class="success infotext"><input type="text" name="Crop" class="datepicker"></td><td class="success infotext"><input type="text" name="Crop" class="datepicker"></td><td class="success infotext"><input type="text" name="Crop" class="datepicker"></td><td class="success infotext"><input type="text" name="Crop"></td></tr>';
	$("#cropInformationDetailFirstTable tbody").append(rowHTMLForCropInformationDetails1);
	$("#cropInformationDetailSecondTable tbody").append(rowHTMLForCropInformationDetails2);
	$("#cropInformationDetailThirdTable tbody").append(rowHTMLForCropInformationDetails3);
	
	var optionalCropInformationDetailHTML = '<div class="table-responsive" name="optionalCropInformationDetail_'+cropName+'"><table class="table table-striped tbl-bordr  tblbrdr" cellspacing="0" width="100%"><thead><tr class="tblhd add-fieldi"><td class="text-center">Modify</td><td class="tblbrdr add-fieldi">Component</td><td class="text-center add-fieldi">Units</td><td class="text-center">$ per Unit</td><td class="text-center">$ per Acre</td></tr></thead><tbody><tr class="tblgrn"><td class="tblft1 text-center"><input type="checkbox"></td><td class="tblft1">Crop Insurance</td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td><td class="success infotext"></td></tr><tr class="tblbclgrnd"><td class="tblft1 text-center"><input type="checkbox"></td><td class="tblft1">Equipment</td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td><td class="infotext"></td></tr><tr class="tblgrn"><td class="tblft1 text-center"><input type="checkbox"></td><td class="tblft1">Fertilizer</td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td><td class="success infotext"></td></tr><tr class="tblbclgrnd"><td class="tblft1 text-center"><input type="checkbox"></td><td class="tblft1">Financing</td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td><td class="infotext" ></td></tr><tr  class="tblgrn"><td class="tblft1 text-center"><input type="checkbox" ></td><td class="tblft1" >Herbicide</td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)" ></td><td class="success infotext"></td></tr><tr class="tblbclgrnd"><td class="tblft1 text-center"><input type="checkbox" ></td><td class="tblft1" >Insecticide</td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)" ></td><td class="infotext"></td></tr><tr class="tblgrn"><td class="tblft1 text-center"><input type="checkbox" ></td><td class="tblft1">Irrigation</td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)" ></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td><td class="success infotext" ></td></tr><tr class="tblbclgrnd"><td class="tblft1 text-center"><input type="checkbox" ></td><td class="tblft1" >Labor</td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)" ></td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)" ></td><td class="infotext" ></td></tr><tr class="tblgrn"><td class="tblft1 text-center"><input type="checkbox" ></td><td class="tblft1">Micro Nutrients</td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)" ></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)" ></td><td class="success infotext" ></td></tr><tr class="tblbclgrnd"><td class="tblft1 text-center"><input type="checkbox" ></td><td class="tblft1" >Professional Services</td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)" ></td><td class="infotext"></td></tr><tr class="tblgrn"><td class="tblft1 text-center"><input type="checkbox"></td><td class="tblft1">Rent</td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td><td class="success infotext"></td></tr><tr class="tblbclgrnd"><td class="tblft1 text-center"><input type="checkbox"></td><td class="tblft1">Seed</td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td><td class="infotext"></td></tr></tbody><tfoot><tr class="tblgrn"><td class="tblft1 optncal" colspan="2">Calculate</td><td class="success"></td><td class="success"></td><td class="success"></td></tr><tr class="tblft text-center"><td class="tblft1" colspan="2">Variable Cost per Acre: </td><td><input type="hidden"> </td><td></td><td class="optndoller">$0.00</td></tr></tfoot></table></div>'
	$("#optional_crop_dynamic_div").append(optionalCropInformationDetailHTML);
	$('div[name="optionalCropInformationDetail_'+cropName+'"]').hide();
	
	var rowHTMLForCropResourceUsage = '<tr class="tblgrn text-center"><td class="tblft1">'+cropName+'</td><td class="success infotext tittle-uppercase">'+defaultUOMForCrop+'</td><td class="success infotext">$ 0.00 <img class="orange_data_help" src="images/data.png"></td>';
	for(var i=0; i < $("#crop_resource_usage thead tr td").length-3; i++){
		rowHTMLForCropResourceUsage += '<td class="success infotext"><input type="text" onchange="cropResourceUsageValueChange(this)"></td>';
	}
	rowHTMLForCropResourceUsage += "</tr>";
	$("#crop_resource_usage tbody").append(rowHTMLForCropResourceUsage);
//	var rowHTMLForCropForwardSales = '<tr class="tblbclgrnd text-center"><td class="tblft1">'+cropName+'</td><td class="success infotext tittle-uppercase">'+defaultUOMForCrop+'</td><td class="success croplimit"><input type="text"></td><td class="success croplimit"><input type="text"></td><td class="success croplimit"><input type="text"></td><td class="success croplimit"><input type="checkbox"></td><td class="success croplimit"><input type="checkbox"></td><td class="success croplimit"><input type="text"></td></tr>';
	var rowHTMLForCropForwardSales = '<tr class="tblbclgrnd text-center"><td class="tblft1">'+cropName+'</td><td class="success infotext tittle-uppercase">'+defaultUOMForCrop+'</td><td class="success croplimit"><input type="text" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this)"></td><td class="success croplimit"><input type="text" onkeypress="return isValidNumberValue(event)" onchange="acerageCalForwardSale(this)"></td><td class="success croplimit"><input type="text" onkeypress="return isValidNumberValue(event)" onchange="quantityCalForwardSale(this)"></td><td class="success croplimit"><input type="checkbox" onchange="proposedAndFirmSelection(this)"></td><td class="success croplimit"><input type="checkbox" onchange="proposedAndFirmSelection(this)"></td><td class="success croplimit"><input type="text" onkeypress="return isValidNumberValueForWithOutDot(event)" onchange="addPercentSign(this)"></td></tr>';
	$("#forward_sales_information tbody").append(rowHTMLForCropForwardSales);
	
	var rowHTMLForCropLimit = '<tr class="tblbclgrnd text-center"><td class="tblft1"></td><td class="tblft1">'+cropName+'</td><td class="success croplimit"><input type="text"></td><td class="success croplimit"><input type="text"></td></tr>';
	$("#crop_limits_table_tbody").append(rowHTMLForCropLimit);
	
	var optionRowHTMLForGroupCropSelection = '<option value="'+cropName+'">'+cropName+'</option>';
	$("#gropofcrop").append(optionRowHTMLForGroupCropSelection);
	deSelectAllCropsInGroupOptionAndRebuild();
	
	$("#Plan_by_Fields_table tbody tr").each(function(){
		$(this).children("td:nth(3) select").append('<option value="'+cropName+'">'+cropName+'</option>');
	});
	$("#field_choice_crop_table thead tr").append('<td class="text-center add-fieldi">'+cropName+'</td>');
	$("#field_choice_crop_table tbody tr").each(function(){
		$(this).append('<td class="success"><input type="checkbox" class="cropFieldChoiceCheckbox" onchange="cropFieldChoiceCheckboxChenge(this)"></td>');
	});
}
function removeCropFromAllTables(cropName){
	$("#cropInformationDetailFirstTable tbody tr").each(function(){
		if($(this).children("td:nth(0)").text().trim() == cropName){
			$(this).remove();
			return false;
		}
	});
	$("#cropInformationDetailSecondTable tbody tr").each(function(){
		if($(this).children("td:nth(0)").text().trim() == cropName){
			$(this).remove();
			return false;
		}
	});
	$("#cropInformationDetailThirdTable tbody tr").each(function(){
		if($(this).children("td:nth(0)").text().trim() == cropName){
			$(this).remove();
			return false;
		}
	});
	$("#crop_resource_usage tbody tr").each(function(){
		if($(this).children("td:nth(0)").text().trim() == cropName){
			$(this).remove();
			return false;
		}
	});
	$("#forward_sales_information tbody tr").each(function(){
		if($(this).children("td:nth(0)").text().trim() == cropName){
			$(this).remove();
			return false;
		}
	});
	$("#crop_limits_table_tbody tr").each(function(){
		if($(this).children("td:nth(1)").text().trim() == cropName){
			$(this).remove();
			return false;
		}
	});
	
	$('div[name="optionalCropInformationDetail_'+cropName+'"]').remove();
	
	$("#gropofcrop option").each(function(){
		if($(this).val() == cropName){
			$(this).remove();
			return false;
		}
	});
	deSelectAllCropsInGroupOptionAndRebuild();
	for ( var key in globalGroupArray) {
		for (var i = 0; i < globalGroupArray[key].length; i++) {
			if (globalGroupArray[key][i] == cropName) {
				delete globalGroupArray[key][i];
			}
		}
	}
// if(strategy == "fields"){
		$("#Plan_by_Fields_table tbody tr").each(function(){
			$(this).children("td:nth(3) select option").each(function(){
				if($(this).val().trim() == cropName){
					$(this).remove();
					return false;
				}
			});
		});
		var index = 0;
		var tempIndex = 0;
		$("#field_choice_crop_table thead tr td").each(function(){
			if($(this).text().trim() == cropName){
				index = tempIndex;
				$(this).remove();
			}
			tempIndex++;
		});
		$("#field_choice_crop_table tbody tr").each(function(){
			$(this).children("td:nth("+index+")").remove();
		});
//	}
}

function addNewField(){
	var selectHTMLForOptions = '';
	$("input:checkbox[class=crops]:checked").each(function(){
		selectHTMLForOptions += '<option value="'+$(this).val()+'">'+$(this).val()+'</option>';
	});
	var rowHTMLForPlanByField = '<tr class="success tblgrn text-center"><td><input type="checkbox" class="fields"></td><td>'+$("#pop-up-field-name").val()+'</td><td>'+$("#pop-up-field-size").val()+'</td> <td><select onchange="lastCropSelected(this)"><option value="No Crop">No Crop</option>'+selectHTMLForOptions+'</select></td><td><input type="checkbox" value="true" onchange="fallowEnabledOrDisabled(this)"></td><td><input type="checkbox" value="true"></td><td><input type="checkbox" name="field-irrigate__1" value="true"></td></tr>';
	var totalLandByField = 0;
//	$("#Plan_by_Fields_table tbody").children("tr:nth("+($("#Plan_by_Fields_table tbody tr").length-1)+")").remove();
	$("#Plan_by_Fields_table tbody").append(rowHTMLForPlanByField);
	$("#Plan_by_Fields_table tbody tr").each(function(){
		totalLandByField += Number(removeAllCommas($(this).children("td:nth(2)").text()));
	});
//	var rowHtmlForLastRow = '<tr id="total-field-last-row" class="tblft text-center"><td class="tblft1">Total acres </td><td style="text-align: left" colspan="6" id="total-acres-value">'+totalLandByField+'</td></tr>';
//	$("#Plan_by_Fields_table tbody").append(rowHtmlForLastRow);
	
	var rowHTMLForCropFieldChoice = '<tr class="tblgrn text-center"><td class="tblft1">'+$("#pop-up-field-name").val()+'</td>';
	$("input:checkbox[class=crops]:checked").each(function(){
		rowHTMLForCropFieldChoice += '<td class="success"><input type="checkbox" class="cropFieldChoiceCheckbox" onchange="cropFieldChoiceCheckboxChenge(this)"></td>';
	});
	rowHTMLForCropFieldChoice += "</tr>";
	$("#field_choice_crop_table tbody").append(rowHTMLForCropFieldChoice);
	$("#field_select_drop_down").append('<option value="'+$("#pop-up-field-name").val()+'">'+$("#pop-up-field-name").val()+'</option>');
	$("#pop-up-field-name").val('');
	$("#pop-up-field-size").val('');
	totalLandByField = getValueWithComma(totalLandByField);
	$("#total-acres-value").text(totalLandByField);
	$("#total_land_available").text(totalLandByField);
	$("#Plan_by_Fields_table").show();
	div_hide4();
}

function fallowEnabledOrDisabled(obj) {
	var fieldName = $(obj).parent().parent().children("td:nth(1)").text().trim();
	if($(obj).prop("checked") == true){
		$("#field_choice_crop_table tbody tr").each(function(){
			if($(this).children("td:nth(0)").text().trim() == fieldName){
				$(this).find("input[class='cropFieldChoiceCheckbox']").prop("checked", false);
				$(this).find("input[class='cropFieldChoiceCheckbox']").prop("disabled", true);
				return false;
			}
		});
	}else if($(obj).prop("checked") == false){
		$("#field_choice_crop_table tbody tr").each(function(){
			if($(this).children("td:nth(0)").text().trim() == fieldName){
				$(this).find("input[class='cropFieldChoiceCheckbox']").prop("disabled", false);
				return false;
			}
		});
	}
}

function lastCropSelected(obj) {
	var fieldName = $(obj).parent().parent().children("td:nth(1)").text().trim();
	var lastCropName = $(obj).val();
	var indexNumber = 0;
	$("#field_choice_crop_table thead tr td").each(function() {
		if($(this).text().trim() == lastCropName){
			return false; 
		}
		indexNumber++;
	});
	$("#field_choice_crop_table tbody tr").each(function() {
				if ($(this).children("td:nth(0)").text().trim() == fieldName) {
					$(this).find("span").each(function(){
						$(this).parent().html($(this).html());
					});
					$(this).children("td:nth("+indexNumber+")").html("<span class='last_crop'>"+$(this).children("td:nth("+indexNumber+")").html()+"</span>");
					return false;
				}
	});
}

var fieldNameForModify = "";
function modifyExistingField() {
	fieldNameForModify = "";
	if($("#Plan_by_Fields_table tbody tr").length == 0){
		customAlerts("You don't have any field to modify, Please add a field first to modify", type_error, time);
	}else if($("#Plan_by_Fields_table tbody tr").length > 0 && $(".fields:checked").length == 1){
		fieldNameForModify = $(".fields:checked").parent().parent().children("td:nth(1)").text().trim();
		$("#pop-up-field-name").val(fieldNameForModify);
		$("#pop-up-field-size").val($(".fields:checked").parent().parent().children("td:nth(2)").text().trim());
		div_show4();
	}else if($("#Plan_by_Fields_table tbody tr").length > 0 && $(".fields:checked").length == 0){
		customAlerts("Please select atleast one field to modify", type_error, time);
	}else if($("#Plan_by_Fields_table tbody tr").length > 0 && $(".fields:checked").length > 1){
		customAlerts("You can modify only one field at a time", type_error, time);
	}
}

function modifyField() {
	if($(".fields:checked").parent().parent().children("td:nth(1)").text().trim() == fieldNameForModify){
		$(".fields:checked").parent().parent().children("td:nth(1)").text($("#pop-up-field-name").val());
		$(".fields:checked").parent().parent().children("td:nth(2)").text($("#pop-up-field-size").val());
	}
	var totalLandByField = 0;
	$("#Plan_by_Fields_table tbody tr").each(function(){
		totalLandByField += Number(removeAllCommas($(this).children("td:nth(2)").text()));
	});
	totalLandByField = getValueWithComma(totalLandByField);
	$("#total-acres-value").text(totalLandByField);
	$("#total_land_available").text(totalLandByField);
	$("#field_choice_crop_table tbody tr").each(function(){
		if($(this).children("td:nth(0)").text().trim() == fieldNameForModify){
			$(this).children("td:nth(0)").text($("#pop-up-field-name").val());
			return false;
		}
	});
	$("#field_select_drop_down option").each(function() {
		if ($(this).val() == fieldNameForModify) {
			$(this).val($("#pop-up-field-name").val());
			$(this).text($("#pop-up-field-name").val());
			return false;
		}
	});
	div_hide4();
	$('#pop-up-field-name').val("");
	$('#pop-up-field-size').val("");
	$(".fields:checked").prop("checked", false);
}

function removeField(){
		
	if($("#Plan_by_Fields_table tbody tr").length > 0 && $(".fields:checked").length == 0){
		customAlerts("Please select atleast one field to remove", type_error, time);
	}else if($("#Plan_by_Fields_table tbody tr").length == 0){
		customAlerts("There are no fields, Please add fields first", type_error, time);
	}else if ($("#Plan_by_Fields_table tbody tr").length > 0 && $(".fields:checked").length > 0) {
		var fieldNameList = "";
		$("#Plan_by_Fields_table tbody tr").each(function() {
			if ($(this).children("td:nth(0)").find("input").prop("checked")) {
				fieldNameList += $(this).children("td:nth(1)").text().trim()+", ";
			}
		});
		alertify.confirm('Are you sure you want to remove this field "' + fieldNameList.substring(0, fieldNameList.length-2) + '"?', function(e) {
			if (e) {
				var totalLandByField = 0;
				$("#Plan_by_Fields_table tbody tr").each(function() {
					if ($(this).children("td:nth(0)").find("input").prop("checked")) {
						var fieldName = $(this).children("td:nth(1)").text().trim();
						$("#field_choice_crop_table tbody tr").each(function() {
						if ($(this).children("td:nth(0)").text().trim() == fieldName) {
							$(this).remove();
							}});
						$(this).remove();
						}});
					if ($("#Plan_by_Fields_table tbody tr").length < 1) {
						$("#Plan_by_Fields_table").hide();
					} else {
						$("#Plan_by_Fields_table tbody tr").each(function() {
						totalLandByField += Number(removeAllCommas($(this).children("td:nth(2)").text()));
						});
						totalLandByField = getValueWithComma(totalLandByField);
						$("#total-acres-value").text(totalLandByField);
						$("#total_land_available").text(totalLandByField);
					}
					$("#field_select_drop_down option").each(function() {
						if ($(this).val() == fieldName) {
							var flag = false;
							if($(this).is(':selected')){
								flag = true;
							}
							$(this).remove();
							if(flag){
								$("#field_select_drop_down").val("0");
								$("#field_select_drop_down").change();
							}
							return false;
						}
					});
			}else{
				$("#Plan_by_Fields_table tbody tr").each(function() {
					if ($(this).children("td:nth(0)").find("input").prop("checked")) {
						$(this).children("td:nth(0)").find("input").prop("checked", false);
					}
				});
			}
		});
	}
}

function allCheckboxNone(){
	$(".cropFieldChoiceCheckbox").prop("checked", false);
}

function allCheckboxSelect(){
	$("td > .cropFieldChoiceCheckbox:not(:disabled)").prop("checked", true);
}

function addNewResource(){
	/*change by rohit*/
	var resourse_name=$("#resourse_name").val();
	if ($("#resourse_name").val() == "") {
		customAlerts("Please enter resourse name", type_error, time);
		return false;
	}
	else if ($("#resourse_unit_name").val() == "") {
		customAlerts("Please enter unit of measure of particular resource", type_error, time);
		return false;
	}
	else
		{
		alertify.confirm('Are you sure you want to add a new resourse named "' + resourse_name + '"?', function(e) {
			if (e) {
			
		validationResourseFlag=true;
	$("#manage_resource tbody tr").each(function(){
		if($(this).children("td:nth(1)").text().trim()==$("#resourse_name").val().trim())
			{
			customAlerts('"'+$("#resourse_name").val()+'" resource name is already exist', type_error,time);
			validationResourseFlag=false;
			return false;
			}
		});
	if(validationResourseFlag)
		{
	var rowHTMLForAddResources = '<tr class="tblgrn text-center"><td class="tblft1"><input type="checkbox" name="resourceSelection[]" onchange="onResourceSelectedOrRemoved(this)"></td><td class="success croplimi">'+$("#resourse_name").val()+'</td><td class="success croplimit">'+$("#resourse_unit_name").val()+'</td><td class="success croplimit"><input type="text"></td></tr>';
	$("#manage_resource tbody").append(rowHTMLForAddResources);
	
	customAlerts('"'+resourse_name+'"  resource added to the strategy', type_success,time);
}
			}
			div_hide6();
			
		});
			}
}

function removeResource(){
	if ($("input[name='resourceSelection']").length == 0) {
		customAlerts("Land and Capital are required resources and cannot be removed", type_error, time);
	} else if ($("input[name='resourceSelection']").length > 0 && $("input[name='resourceSelection']:checked").length == 0) {
		customAlerts("Please select a resource to remove", type_error, time);
	} else if($("input[name='resourceSelection']:checked").length > 0){
		$("#manage_resource tbody tr").each(function(){
			if($(this).children("td:nth(0)").find("input").prop("disabled") == false &&  $(this).children("td:nth(0)").find("input").prop("checked")){
				removeResourceFromAllTables($(this).children("td:nth(0)").find("input"));
				$(this).remove();
			}
		});
		customAlerts("Resource removed successfully", type_error, time);
	} 
}

function addResourcesInAllTables(resourceObject){
	var resourceName = $(resourceObject).parent().parent().children("td:nth(1)").text().trim();
	var resourceUOM = $(resourceObject).parent().parent().children("td:nth(2)").text().trim();
	var theadHTML = '<td class="text-center"><span class="tittle-uppercase">'+resourceName+'</span><br><span class="resub">('+resourceUOM+')</span></td>';
	$("#crop_resource_usage thead tr").append(theadHTML);
	var tbodyTdHTML = '<td class="success infotext"><input type="text" onchange="cropResourceUsageValueChange(this)"></td>';
	$("#crop_resource_usage tbody tr").each(function(){
		$(this).append(tbodyTdHTML);
	});
	var rowHTMLForFieldDifference = '<tr class="tblgrn text-center"><td class="tblft1 tittle-uppercase">'+resourceName+'</td><td onkeypress="return isValidNumberValueForWithOutDot(event)" class="success infotext"></td><td class="success infotext"><input type="text" onkeypress="return isValidNumberValueForWithOutDot(event)" onchange="addCommaSignWithOutDollarDot(this)"></td></tr>';
	$("#crop_resources_usages_difference_tbody").append(rowHTMLForFieldDifference);
	
}

function removeResourceFromAllTables(resourceObject){
	var resourceName = $(resourceObject).parent().parent().children("td:nth(1)").text().trim();
	var indexNumber = 0;
	$("#crop_resource_usage thead tr td").each(function(){
		if($(this).children("span:nth(0)").text().trim() == resourceName){
			$(this).remove();
			return false;
		}
		indexNumber++;
	});
	$("#crop_resource_usage tbody tr").each(function(){
		$(this).children("td:nth("+indexNumber+")").remove();
	});
	$("#crop_resources_usages_difference_tbody tr").each(function(){
		if($(this).children("td:nth(0)").text().trim() == resourceName){
		$(this).remove();
		}
	});
}

function variableProductionCostChange(obj) {
	var cropName = $(obj).parent().siblings("td:nth(0)").text().trim();
	var variableProductionCost = $(obj).val();
	$("#crop_resource_usage tbody tr").each(function() {
		if ($(this).children("td:nth(0)").text().trim() == cropName) {
			$(this).children("td:nth(2)").text(variableProductionCost);
			return false;
		}
	});
	if($("#crop_select_drop_down").val() == $(obj).parent().siblings("td:nth(0)").text().trim()){
		$("#resources_usages_production_resource_default").text($(obj).val());
	}
}

function addCommaSignForAcres(id) {
	if ($(id).val() == "") {
	} else {
		var value = $.trim("" + $(id).val().replace('$', '').replace(/,/g, ''));
		var valueWithPoint = Number(value).toFixed(2);
		if (valueWithPoint > 10000) {
			customAlerts("The amount of land entered exceeds 10,000 acres",
					"warning", 0);
		}
		var valueWithComma;
		if (valueWithPoint % 1 != 0) {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
		} else {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
		}
		$("#total_land_available").text(valueWithComma);
		return $(id).val(valueWithComma);
	}
}

function isValidNumberValueForWithOutDot(evt) {
	var e = evt || window.event;
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
function isValidNumberValue(evt) {
	var e = evt || window.event;
	var charCode = e.which || e.keyCode;
	if (charCode > 31 && (charCode < 47 || charCode > 57) && (charCode != 46))
		return false;
	if (e.shiftKey)
		return false;
	if (charCode == 47)
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
		var valueWithPoint = Number(value).toFixed(2);
		var valueWithComma;
		if (valueWithPoint % 1 != 0) {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
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
	if (value == "") {
	} else {
		var value = $.trim("" + value.replace('$', '').replace(/,/g, ''));
		var valueWithPoint = Number(value).toFixed(2);
		var valueWithComma;
		if (valueWithPoint % 1 != 0) {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
		} else {
			valueWithComma = Number(valueWithPoint).toLocaleString('en');
			valueWithComma = valueWithComma + ".00";
		}
		var finalValue = "$" + valueWithComma;
		return finalValue;
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
			valueWithComma = valueWithComma;
		}
		var finalValue = "$" + valueWithComma;
		return $(id).val(finalValue);
	}
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
function getValueWithComma(value) {
		var valueWithComma = Number(value).toLocaleString('en');
		return valueWithComma;
}
function addNewCrop() {
	var cropName = $("#crop_name").val().trim();
	if (cropName == "") {
		customAlerts("Please enter name of Crop", "error", 0);
		return false;
	}
	if ($("input[class='crops'][value='"+cropName+"']").length > 0 || $("input[class='crops'][value='"+cropName.toUpperCase()+"']").length > 0 || $("input[class='crops'][value='"+cropName.toLowerCase()+"']").length > 0) {
		customAlerts('"' + cropName + '" crop name is already exist', "error", 0);
			return false;
		}
	if ($("input[name='crop_type']:checked").length == 0) {
		customAlerts("Please select crop type", "error", 0);
			return false;
		}
	alertify.confirm('Are you sure you want to add a new crop named "' + cropName + '"?', function(e) {
		if (e) {
			if ($("input[name='crop_type']:checked").val() == "Field Crops") {
				var rowHTMLForCrop = '<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" value="'+cropName+'" class="crops"  onchange="onCropSelectedOrRemoved(this)" name="field_crop[]"> &nbsp;&nbsp;<span>'+cropName+'</span></li>';
				$("#crop_normal").append(rowHTMLForCrop);
			} else {
				var rowHTMLForCrop = '<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" value="'+cropName+'" class="crops"  onchange="onCropSelectedOrRemoved(this)" name="vegitable_crop[]"> &nbsp;&nbsp;<span>'+cropName+'</span></li>';
				$("#crop_vegitable").append(rowHTMLForCrop);
			}
			customAlerts('"' + cropName + '" crop is added successfully', "success", 0);
		}
		div_hide5();
		$('#crop_name').val("");
		$('input[name="crop_type"]').prop("checked", false);
	});
}
function removeCrops(){
	if($(".crops:checked").length < 1){
		customAlerts("No crop selected to remove, Please select atleast one Crop", type_error, time);
		return false;
	}
	var cropsName = "";
	$(".crops:checked").each(function() {
		cropsName += $(this).val() + ", ";
	});
	cropsName = cropsName.substring(0, cropsName.length-2);
	alertify.confirm('Are you sure you want to remove "'+cropsName+'" crops?', function(e) {
		if (e) {
			$(".crops:checked").each(function(){
				removeCropFromAllTables($(this).val());
				$(this).parent().remove();
			});
			customAlerts('"' + cropsName + '" Crops removed successfully', "success", 0);
		}
	});
}

function changeCropUOM(obj) {
	var cropName = $(obj).parent().parent().children("td:nth(0)").text().trim();
	var cropUOM = $(obj).val();
	$("#crop_resource_usage tbody tr").each(function() {
		if ($(this).children("td:nth(0)").text().trim() == cropName) {
			$(this).children("td:nth(1)").text(cropUOM);
			return false;
		}
	});
	$("#forward_sales_information tbody tr").each(function() {
		if ($(this).children("td:nth(0)").text().trim() == cropName) {
			$(this).children("td:nth(1)").text(cropUOM);
			return false;
		}
	});
}
function acerageCalForwardSale(obj) {
	var cropName = $(obj).parent().siblings("td:nth(0)").text().trim();
	var cropQuantity = Number(removeAllCommas($(obj).val()));
	var cropAcrage = 0;
	$("#cropInformationDetailFirstTable tbody tr").each(function() {
		if ($(this).children("td:nth(0)").text().trim() == cropName) {
			cropAcrage = (cropQuantity / Number(removeAllCommas($(this).children("td:nth(2)").find("input").val())));
			return false;
		}
	});
	$(obj).parent().siblings("td:nth(3)").find("input").val(parseInt(cropAcrage));
	if(cropAcrage != 0 && cropAcrage != "" && $(obj).parent().siblings("td:nth(5)").find("input").prop("checked") == false){
		$(obj).parent().siblings("td:nth(4)").find("input").prop("checked", true);
	}
}

function quantityCalForwardSale(obj) {
	var cropName = $(obj).parent().siblings("td:nth(0)").text().trim();
	var cropAcrage = Number(removeAllCommas($(obj).val()));
	var cropQuantity = 0;
	$("#cropInformationDetailFirstTable tbody tr").each(function() {
		if ($(this).children("td:nth(0)").text().trim() == cropName) {
			cropQuantity = (cropAcrage * Number(removeAllCommas($(this).children("td:nth(2)").find("input").val())));
			return false;
		}
	});
	$(obj).parent().siblings("td:nth(3)").find("input").val(parseInt(cropQuantity));
	if(cropQuantity != 0 && cropQuantity != "" && $(obj).parent().siblings("td:nth(5)").find("input").prop("checked") == false){
		$(obj).parent().siblings("td:nth(4)").find("input").prop("checked", true);
	}
}

function proposedAndFirmSelection(obj) {
	$(obj).parent().siblings("td:nth(5)").find("input").prop("checked", false);
}

function showOptionalCropInformationDiv(cropName) {
	$("#crop_name_dynamic").html(cropName);
	$("#add_production_cost_field").attr("onclick", "addProductionCostField('optionalCropInformationDetail_" + cropName + "')");
	$("#modify_production_cost_field").attr("onclick", "modifyProductionCostField('optionalCropInformationDetail_" + cropName + "')");
	$("#remove_production_cost_field").attr("onclick", "removeProductionCostField('optionalCropInformationDetail_" + cropName + "')");
	$("#optional_crop_dynamic_div > div").hide();
	$('div[name="optionalCropInformationDetail_'+cropName+'"]').show();
	$(".show_hide_class").addClass("hidden");
	$("#optional_crop").removeClass("hidden");
}

function addProductionCostField(name) {
	$("#crop-optional-cost-components").val("");
	$("#add-new-componemt-button").attr("onclick", "addNewCostComponents('" + name + "')");
	$("#add-new-componemt-button").show();
	$("#update-componemt-button").hide();
	$("#add-cost-component-span-dynamic").html("Add Cost Component");
	div_show10();
}

function addNewCostComponents(name){
	if($("#crop-optional-cost-components").val() == ""){
		customAlerts("Please enter cost Component", type_error, time);
		return false;
	}else{
	var rowHTMLForCostComponentOdd = '<tr class="tblgrn"><td class="tblft1 text-center"><input type="checkbox"></td><td class="tblft1">'+$("#crop-optional-cost-components").val()+'</td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td><td class="success infotext"></td></tr>'; 
	var rowHTMLForCostComponentEven = '<tr class="tblbclgrnd"><td class="tblft1 text-center"><input type="checkbox"></td><td class="tblft1">'+$("#crop-optional-cost-components").val()+'</td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td><td class="infotext"></td></tr>';
	if($('div[name="'+name+'"]').find("table tbody tr").length % 2 != 0){
		$('div[name="'+name+'"]').find("table tbody").append(rowHTMLForCostComponentEven);
	}else{
		$('div[name="'+name+'"]').find("table tbody").append(rowHTMLForCostComponentOdd);
	}
	div_hide10();
	}
}

function removeProductionCostField(name) {
	if ($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]").length == 0) {
		customAlerts("There is no Component for remove, Please add a component first", type_error, time);
	}else if ($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").length == 0) {
		customAlerts("Please select the Component for remove", type_error, time);
	}else if($('div[name="'+name+'"]').find("table tbody").find("input[type=checkbox]:checked").length > 0){
		$('div[name="'+name+'"]').find("table tbody").find("input[type=checkbox]:checked").each(function(){
			$(this).parent().parent().remove();
		});
		calculateTotalForOptionalCropInformation($('div[name="'+name+'"]').find("table"));
		customAlerts("Components removed successfully", type_success, time);
	}
}

function modifyProductionCostField(name) {
	if ($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]").length == 0) {
		customAlerts("There is no Component for modify, Please add a component first", type_error, time);
	}else if ($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").length == 0) {
		customAlerts("Please select the Component for modify", type_error, time);
	}else if ($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").length > 1) {
		customAlerts("You can modify only one Component at a time", type_error, time);
	}else if($('div[name="'+name+'"]').find("table tbody").find("input[type=checkbox]:checked").length == 1){
		$("#crop-optional-cost-components").val($('div[name="'+name+'"]').find("table tbody").find("input[type=checkbox]:checked").parent().siblings("td:nth(0)").text().trim());
		$("#update-componemt-button").attr("onclick", "updateNewCostComponents('" + name + "')");
		$("#add-new-componemt-button").hide();
		$("#update-componemt-button").show();
		$("#add-cost-component-span-dynamic").html("Update Cost Component");
		div_show10();
	}
}

function updateNewCostComponents(name){
	if($("#crop-optional-cost-components").val() == ""){
		customAlerts("Please enter cost Component", type_error, time);
		return false;
	}else{
	$('div[name="'+name+'"]').find("table tbody").find("input[type=checkbox]:checked").parent().siblings("td:nth(0)").text($("#crop-optional-cost-components").val());
	div_hide10();
	}
}
function getCalculateValue(obj){
	var unitValue = 0;
	var dollarPerUnitValue = 0;
	if(Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(2)").find("input").val())) != ""){
	unitValue = Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(2)").find("input").val()));
	}if(Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(3)").find("input").val())) != ""){
		dollarPerUnitValue = Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(3)").find("input").val()));
	}
	var columnTotal = unitValue * dollarPerUnitValue;
	$(obj).parent().parent().children("td:nth(4)").text(addCommaSignWithDollarWithValue(""+columnTotal));
	calculateTotalForOptionalCropInformation($(obj).parent().parent().parent().parent());
}

function calculateTotalForOptionalCropInformation(tableObj) {
	var rowTotal = 0;
	$(tableObj).find("tbody > tr").each(function(){
		if($(this).children("td:nth(4)").text().trim() != ""){
		rowTotal += Number(removeAllCommasAndDollar($(this).children("td:nth(4)").text().trim()));
		}
	});
	// console.log(rowTotal);
	$(tableObj).find("tfoot").children("tr:nth(1)").children("td:nth(3)").text(addCommaSignWithDollarWithValue(""+rowTotal));
}

function setTotalOfOptionalCropInformationToVariableProductionCost() {
	var cropName = $("#crop_name_dynamic").html().trim();
	var variableValue = $('div[name="optionalCropInformationDetail_'+cropName+'"]').find("tfoot").children("tr:nth(1)").children("td:nth(3)").text().trim();
	var variableProductionCost = "";
	$(".show_hide_class").addClass("hidden");
	$("#cropinfodetail").removeClass("hidden");
	$("#optional_crop_dynamic_div > div").hide();
	var obj = null;
	$("#cropInformationDetailFirstTable tbody tr").each(function(){
		if($(this).children("td:nth(0)").text().trim() == cropName){
			variableProductionCost = $(this).children("td:nth(8)").find("input").val().trim();
			obj = $(this).children("td:nth(8)").find("input");
			return false;
		}
	});
	if(variableProductionCost == ""){
		variableProductionCost = "$0.00";
	}
	alertify.confirm('Do you want to change the variable production cost for "'+cropName+'" from "'+variableProductionCost+'" to "'+variableValue+'"?', function(e) {
		if (e) {
			$(obj).val(variableValue);
			$(obj).change();
		}
	});
}

var globalGroupArray = new Array();

function addNewGroup() {
	var groupName = $("#group_name").val();
	if (groupName == "") {
		customAlerts("Group name can not be blank", type_error, time);
		return false;
	}else if ($("#gropofcrop option:checked").length == 0) {
		customAlerts("Please select atleast one crop for group", type_error, time);
		return false;
	} else {
		var exist = false;
		$("#group_table_tbody tr").each(function() {
			if ($(this).find("td:nth(1)").text().trim() == groupName) {
				exist = true;
				return false;
			}
		});
		if (exist == true) {
			customAlerts('Group with name "' + groupName + '" already exist', type_error, time);
		} else {
			var groupCropArray = new Array();
			$("#gropofcrop option:checked").each(function(){
				groupCropArray.push($(this).val());
			});
			globalGroupArray[groupName] = groupCropArray;
			var rowHTMLForGroup = '<tr class="tblbclgrnd text-center"><td class="tblft1"><input type="checkbox" name="groupNameSelection[]"></td><td class="tblft1">' + groupName + '</td><td class="success croplimit"><input type="text" onkeypress="return isValidNumberValue(event)"></td><td class="success croplimit"><input type="text" onkeypress="return isValidNumberValue(event)"></td></tr>';
			$("#group_table_tbody").append(rowHTMLForGroup);
			div_hide11();
		}
	}
}
var modifyGroupName = "";
function getGroupForModify(){
	if($("input[name='groupNameSelection[]']").length == 0){
		customAlerts("There is no group for modify, Please first add a group", type_error, time);
	}else if($("input[name='groupNameSelection[]']:checked").length == 0){
		customAlerts("Please select a group for modify", type_error, time);
	}else if($("input[name='groupNameSelection[]']:checked").length > 1){
		customAlerts("You can modify only one group at a time", type_error, time);
	}else{
		deSelectAllCropsInGroupOptionAndRebuild();
		modifyGroupName = $("input[name='groupNameSelection[]']:checked").parent().siblings("td:nth(0)").text().trim();
		$("#group_name").val(modifyGroupName);
		for(var i=0;i < globalGroupArray[modifyGroupName].length;i++){
			$("#gropofcrop option").each(function(){
				if($(this).val() == globalGroupArray[modifyGroupName][i]){
					$(this).prop("selected", "selected");
					return false;
				}
			});
		}
		$("#gropofcrop").multiselect('rebuild');
		div_show12();
	}
}

function removeGroup(){
	if($("input[name='groupNameSelection[]']").length == 0){
		customAlerts("There is no group for remove, Please first add a group", type_error, time);
	}else if($("input[name='groupNameSelection[]']:checked").length == 0){
		customAlerts("Please select a group for remove", type_error, time);
	}else if($("input[name='groupNameSelection[]']:checked").length > 0){
		$("input[name='groupNameSelection[]']:checked").each(function(){
			delete globalGroupArray[$("input[name='groupNameSelection[]']:checked").parent().siblings("td:nth(0)").text().trim()];
			$("input[name='groupNameSelection[]']:checked").parent().parent().remove();
		});
		customAlerts("Selected groups have been removed successfully", type_success, time);
	}
}

function deSelectAllCropsInGroupOptionAndRebuild() {
	$("#gropofcrop option").each(function() {
		$(this).prop("selected", "");
	});
	$("#gropofcrop").multiselect('rebuild');
}

function modifyGroup() {
	var groupName = $("#group_name").val();
	if (groupName == "") {
		customAlerts("Group name can not be blank", type_error, time);
		return false;
	}else if ($("#gropofcrop option:checked").length == 0) {
		customAlerts("Please select atleast one crop for group", type_error, time);
		return false;
	} else {
		var exist = false;
		$("#group_table_tbody tr").each(function() {
			if ($(this).find("td:nth(1)").text().trim() == groupName && modifyGroupName != groupName) {
				exist = true;
				return false;
			}
		});
		if (exist == true) {
			customAlerts('Group with name "' + groupName + '" already exist', type_error, time);
		} else {
			
			delete globalGroupArray[modifyGroupName];
			var groupCropArray = new Array();
			$("#gropofcrop option:checked").each(function(){
				groupCropArray.push($(this).val());
			});
			globalGroupArray[groupName] = groupCropArray;
			$("#group_table_tbody tr").each(function() {
				if ($(this).find("td:nth(1)").text().trim() == modifyGroupName) {
					$(this).find("td:nth(1)").text(groupName);
					return false;
				}
			});
			div_hide11();
			customAlerts('Group with name "'+groupName+'" updated successfully', type_success, time);
		}
	}
}

function changeExpectedYieldValue(obj) {
	if($("#crop_select_drop_down").val() == $(obj).parent().siblings("td:nth(0)").text().trim()){
		$("#crop_Yield_Difference_Expected").text($(obj).val());
	}
}

function changeMaximumYieldValue(obj) {
	if($("#crop_select_drop_down").val() == $(obj).parent().siblings("td:nth(0)").text().trim()){
		$("#crop_Yield_Difference_Maximum").text($(obj).val());
	}
}

function changeMinimumYieldValue(obj) {
	if($("#crop_select_drop_down").val() == $(obj).parent().siblings("td:nth(0)").text().trim()){
		$("#crop_Yield_Difference_Minimum").text($(obj).val());
	}
}

function showFieldVariencePage(){
	$(".show_hide_class").addClass("hidden");
	$("#field_varience").removeClass("hidden");
}

function cropFieldChoiceCheckboxChenge(obj){
	
}

function cropResourceUsageValueChange(obj) {
//	alert($(obj).parent().parent().getIndex($(obj).parent()));
}

function fieldSelectFieldVarience(obj){
	if($(obj).val() == "0"){
		
	}else{
		$("#crop_select_drop_down option").each(function(){
			if($(this).val() != "0"){
				$(this).remove();
			}
		});
		$("#field_choice_crop_table tbody tr").each(function(){
		if($(this).children("td:nth(0)").text().trim() == $(obj).val()){
		var index = 1;
			$(this).children("td:gt(0)").each(function(){
				if($(this).find("input").is(":checked")){
					$("#crop_select_drop_down").append('<option value="'+$("#field_choice_crop_table thead tr").children("td:nth("+index+")").text().trim()+'">'+$("#field_choice_crop_table thead tr").children("td:nth("+index+")").text().trim()+'</option>');
				}
				index++;
			});
			return false;
		}
	});
	}
	$("#crop_select_drop_down").val("0");
	$("#crop_select_drop_down").change();
}

function getFieldYieldDiffence(obj){
	if ($(obj).val() == "0") {
		$("#crop_Yield_Difference_Expected").text("");
		$("#crop_Yield_Difference_Maximum").text("");
		$("#crop_Yield_Difference_Minimum").text("");
		$("#resources_usages_production_resource_default").text("");
		$("#field_difference_exp").text("");
		$("#field_difference_min").text("");
		$("#field_difference_max").text("");
		$("#resources_usages_production_cost_resource_override").text("");
		$("#crop_resources_usages_difference_tbody tr").each(function(){
			$(this).children("td:nth(1)").text("");
			$(this).children("td:nth(2)").find("input").val("");
		});
	}else{
		$("#cropInformationDetailFirstTable tbody tr").each(function(){
		if($(this).children("td:nth(0)").text().trim() == $(obj).val()){
			$("#crop_Yield_Difference_Expected").text($(this).children("td:nth(2)").find("input").val().trim());
			$("#crop_Yield_Difference_Maximum").text($(this).children("td:nth(3)").find("input").val().trim());
			$("#crop_Yield_Difference_Minimum").text($(this).children("td:nth(4)").find("input").val().trim());
			$("#resources_usages_production_resource_default").text($(this).children("td:nth(8)").find("input").val().trim());
			return false;
		}
		});
		$("#crop_resource_usage tbody tr").each(function(){
			if($(this).children("td:nth(0)").text().trim() == $(obj).val()){
				var indexNumber = 3;
				$(this).children("td:gt(2)").each(function(){
					var value= $(this).find("input").val();
					$("#crop_resources_usages_difference_tbody tr").each(function(){
						if($(this).children("td:nth(0)").text().trim() == $("#crop_resource_usage thead tr td:nth("+indexNumber+") span:nth(0)").text().trim()){
							$(this).children("td:nth(1)").text(value);
							return false;
						}
					});
					indexNumber++;
				});
				return false;
			}
		});
	}
}

function checkAllValidation() {
	if (!validateFarmInformation()) {
		callMethodForPageChangeAndProgressBarImage(1, 1);
		return false;
	} else if (strategy == "acres" && !validatePlanByAcres()) {
		callMethodForPageChangeAndProgressBarImage(0, 2);
		return false;
	} else if (!validateCropsAndCropsInformation()) {
		callMethodForPageChangeAndProgressBarImage(2, 3);
		return false;
	} else if (!validateCropsInformationDetails()) {
		callMethodForPageChangeAndProgressBarImage(3, 4);
		return false;
	} else if (strategy == "fields" && !validatePlanByField()) {
		callMethodForPageChangeAndProgressBarImage(4, 0);
		return false;
	} else if (strategy == "fields" && !validateCropFieldChoice()) {
		callMethodForPageChangeAndProgressBarImage(5, 0);
		return false;
	} else if (!validateResources()) {
		callMethodForPageChangeAndProgressBarImage(6, 5);
		return false;
	} else if (!validateCropResourceUsage()) {
		callMethodForPageChangeAndProgressBarImage(7, 6);
		return false;
	} else if (!validateForwardSales()) {
		callMethodForPageChangeAndProgressBarImage(8, 7);
		return false;
	} else {
		return true;
	}
}

function saveAllFarmInformation() {
	if (!checkAllValidation()) {
		return false;
	}
}























