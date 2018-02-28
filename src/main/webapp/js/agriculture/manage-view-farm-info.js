var strategy = "";
//var field_crop = new Array();
//var field_name = new Array();
//var crop_array=new Array();
//var resources = {};
//var crop_uom = {};
//var crop_total_production_cost = {};
//var total_land = "";
var flagChange = "";
var manageStep1 = true;
var manageStep2 = true;
var manageStep3 = true;
var manageStep4 = true;
var manageStep5 = true;
var manageStep6 = true;
//var groupArray = new Array();
$(document).ready(function() {
	if(strategyJSTL=="PLAN_BY_FIELDS")
		{
		strategy="fields";
		/*Modified By Harshit Gupta on 09-04-2015*/
		flagChange="fields";
		$("#crop-field").css("display","");
		}
	else
		{
		strategy="acres";
		/*Modified By Harshit Gupta on 09-04-2015*/
		flagChange="acres";
		$("#crop-field").css("display","none");
		}
	/*27-03-2015
	By Harshit Gupta
	Start*/

	enableDisableMenu();
//	$("#crop-insurance").addClass("visited");
	
	/*if (crop_insurance_boolean == "true") {
		$("#crop-insurance").css("display", "");
	} else{
		$("#crop-insurance").css("display", "none");
	}
	if (forward_sales_boolean == "true") {
		$("#forward-sales").css("display", "");
	} else{
		$("#forward-sales").css("display", "none");
	}*/
	//end
	
	$("#physical-localtion").geocomplete({});
	// 10-03-2015 change start
	$("#physical-localtion").keyup(function(event) {
		if (event.keyCode == 13) {
			createFarm();
		}
	});
	$("#acres_value").keyup(function(event) {
		if (event.keyCode == 13) {
			validateAcresValue();
		}
	});
	// 10-03-2015 change End	
	$('body').on('focus', ".datepicker",function() {
		$(this).datepicker({
			clearBtn :true, autoclose : true,
		});
	});
	/*This method is defined in document.ready to complete the last crop functionality in crop field choice screen*/
	lastCropForCropFieldChoice();
	//customAlerts("Under Development", "error", 0);
	hideLoadingImage();
});
function createFarm() {
	
	var isFarmValid = validCreatedFarm();
	if (!isFarmValid) {
		return false;
	}
	var farmName = $.trim("" + $('#farm-name').val());
	var physicalLocation = $.trim("" + $('#physical-localtion').val());

	$.ajax({
		url : 'agriculture/farmController/createFarm',
		type : 'POST',
		/*async : false,*/
		beforeSend: showLoadingImage(),
		data : ({
			farmName : farmName,
			physicalLocation : physicalLocation
		}),
		success : function(response) {
			var status = response.status;
			if (status == 'success') {
				customAlerts('"'+farmName +'" Farm name created successfully', "success", 0);	
				var farmId = response.result;
				// alert(farmId);
				window.location = "farm-info.htm?farmId=" + farmId + "";
			} else if (status == 'Already exists') {
				customAlerts('"'+farmName+'" Farm name is already exists,Please enter other farm name', "error", 0);	
			}else if (status == 'invalid user') {
				customAlerts("Please login first", "error", 0);
				window.location="home.htm";
			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, "error", 0);	
		}

	}).done(function(){
		hideLoadingImage();
	});

}
function validCreatedFarm() {
	var isFarmValidated = true;
	var farmName = $.trim("" + $('#farm-name').val());
	var physicalLocation = $.trim("" + $('#physical-localtion').val());
	if (farmName == "") {
		if (isFarmValidated) {
			customAlerts("Please enter your farm name", "error", 0);
			isFarmValidated = false;
		}
	}
	if (physicalLocation == "") {
		if (isFarmValidated) {
			customAlerts("Please enter farm physical-location", "error", 0);
			isFarmValidated = false;
		}
	}

	return isFarmValidated;
}
function showPlanByPage() {
	if (createProcessForShowPlanByPage()) {
		callMethodForPageChangeAndProgressBarImage(2, 2);
	}
}
function showMyPlan() {
	
	if ($("input[name='plan_by_farm_name']:checked").length > 0) {
		div_hide3();
		
		var flag = $
				.trim("" + $('input[name=plan_by_farm_name]:checked').val());
		strategy = flag;
		if (flag == "fields") {
			/* window.location = "plan_by_field.htm?farmId="+farmId+""; */
			// 26-02-15 start
			if (flagChange != "fields") {
				changeStrategyAndFrom();
				// $('input[name="field_crop[]"]').prop("checked", false);
				// $('input[name="vegitable_crop[]"]').prop("checked", false);
			}
			flagChange = "fields";
			manageStep1 = true;
		} else if (flag == "acres") {
			/* window.location = "plan_by_acres.htm?farmId="+farmId+""; */
			// 26-02-15 start
			if (flagChange != "acres") {
				changeStrategyAndFrom();
				// $('input[name="field_crop[]"]').prop("checked", false);
				// $('input[name="vegitable_crop[]"]').prop("checked", false);
			}
			flagChange = "acres";
			manageStep1 = true;
		}
		enableDisableMenu();
	} else {
		customAlerts("Please select your strategy", "error", 0);
		return false;
	}
}
function validateAcresValue() {
	if(createProcessForValidateAcresValue()){
		showCroAndCropInfoPage();
		callMethodForPageChangeAndProgressBarImage(0, 3);
		}
}
function addNewField() {
	
	var fieldName = $.trim("" + $('#pop-up-field-name').val());
	var fieldSize = $.trim("" + $('#pop-up-field-size').val());
	//var fieldLastCrop = $.trim("" + $('#pop-up-field-last-crop').val());
	//var fieldFollow = false;
	//var fieldDivide = false;
	//var fieldIrrigate = false;
	//fieldFollow = $('input[name="pop-up-field-follow"]:checked').val();
//	fieldDivide = $('input[name="pop-up-field-divide"]:checked').val();
	//fieldIrrigate = $('input[name="pop-up-field-irrigate"]:checked').val();
	if (fieldName == "") {
		customAlerts("Please enter field name", "error", 0);
		return false;
	}
	if (fieldSize == "") {
		customAlerts("Please enter field size", "error", 0);
		return false;
	}
	if (!validateNumberOnly(fieldSize) || fieldSize < 1) {
		customAlerts("Please ensure that the value entered are greater than zero for Field size", "error", 0);
		return false;
	}
	var total = 0;
	var rowCount = $('#plan-by-field-tbody >tr').length;
	if (rowCount != 0) {
		rowCount = rowCount - 1;
	}
	for (var i = 1; i <= rowCount; i++) {
		var rowFieldSize = $.trim("" + $('#row-field-size__' + i).html().replace(/,/g, ''));
		total += Number(rowFieldSize);
		// alert($('#row-field-size__' + i).html());
		var rowFieldName = $.trim("" + $('#row-field-name__' + i).html());
		// alert(rowFieldName);
		if (fieldName.toLowerCase() === rowFieldName.toLowerCase()) {
			customAlerts('"'+fieldName+'" field name is already exist', "error", 0);
			return false;
		}
	}
	field_name.push(fieldName);
	total += Number(fieldSize.replace(/,/g, ''));
	/*if (fieldLastCrop == "") {
		fieldLastCrop = "No Crop";
	}*/
	var rowCount = $('#plan-by-field-tbody >tr').length;
	if (rowCount != 0) {
		rowCount = rowCount - 1;
	}

	$('#total-field-last-row').remove();

	var newHtml = "";

	if (rowCount % 2 == 0) {

		newHtml += "<tr class=\"success tblgrn text-center\">";
	} else {

		newHtml += "<tr class=\"tblbclgrnd text-center\">";
	}
	var optionValue="";
	optionValue+="<option value=\"No Crop\">No Crop</option>";
	for (var l = 0; l < field_crop.length; l++) {
		optionValue+="<option value="+field_crop[l]+">"+field_crop[l]+"</option>";	
		}
	rowCount++;
	var totalWithComma=addCommaSignOnValue(total);
	newHtml += "<td><input id=\"row-field-manage_checkbox__"+ rowCount + "\" type=\"checkbox\"></td><td id=\"row-field-name__" + rowCount + "\">" + fieldName+ "</td>" + "<td id=\"row-field-size__" + rowCount + "\">"	+ fieldSize + "</td> " + "<td><select id=\"selected_last_crop____"+rowCount+"\">"+optionValue+"</select></td>"
	 + "<td><input value=\"true\" name=\"field-follow__"+rowCount+"\" id=\"field-follow__"+rowCount+"\" type=\"checkbox\" ></td>"+ "<td><input value=\"true\" name=\"field-divide__"+rowCount+"\" id=\"field-divide__"+rowCount+"\" type=\"checkbox\"></td>" + "<td><input value=\"true\" name=\"field-irrigate__"+rowCount+"\" id=\"field-irrigate__"+rowCount+"\" type=\"checkbox\"></td>"
	 + "</tr> <tr class=\"tblft text-center\" id=\"total-field-last-row\">"	+ "<td class=\"tblft1\">Total acres </td><td id=\"total-acres-value\" colspan=\"6\" style=\"text-align: left\">"	+ totalWithComma	+ "</td></tr>";
	total_land = total;
	$('#plan-by-field-tbody').append(newHtml);
	div_hidePlanByField();
	div_hide4();
	var alertMessage = "";
	if(total > 10000)
    {
    	alertMessage += "But the amount of land entered for \""+fieldName+"\" field exceeds 10,000.00 acres. ";
    }
	if (alertMessage != "") {
		customAlerts('"' + fieldName + '" field added successfully. '
				+ alertMessage, "warning", 0);
	} else {
		customAlerts('"' + fieldName + '" field added successfully', "success",
				0);
	}
	$('#pop-up-field-name').val("");
	$('#pop-up-field-size').val("");
	//$('#pop-up-field-last-crop').val("");
	//$('input[name="pop-up-field-follow"]').prop("checked", false);
	//$('input[name="pop-up-field-divide"]').prop("checked", false);
	//$('input[name="pop-up-field-irrigate"]').prop("checked", false);
}
function showFarmInfoPage() {
	callMethodForPageChangeAndProgressBarImage(2, 3);
}
function showFarmInfoPageForAcres() {
	callMethodForPageChangeAndProgressBarImage(1, 1);
}
function addNewCrop() {
	var cropAdd=false;
	
	var cropName = $.trim("" + $('#crop_name').val());
	var cropType = "";
	if ($("input[name='crop_type']:checked").length > 0) {
		cropType = $.trim("" + $('input[name=crop_type]:checked').val());
		if (cropName == "") {
			customAlerts("Please enter name of Crop", "error", 0);
			return false;
		}
		var isValid = true;
		$('input[type=checkbox]').each(function() {
			var cropListName = $.trim("" + $(this).val());
			if (cropListName.toLowerCase() === cropName.toLowerCase()) {

				isValid = false;
			}
		});
		if (!isValid) {
			customAlerts('"'+cropName+'" crop name is already exist', "error", 0);
			return false;
		}		
		if (confirm('Are you sure you want to add a new crop named "'
				+ cropName + '"?')) {			
			if (cropType == "Field Crops") {
			var newHtml = '<li class=\"col-lg-4 col-md-4 col-sm-6 padding-left-none\"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" value="'
					+ cropName
					+ '"></input>&nbsp;&nbsp;<span>'
					+ cropName
					+ '</span></li>';
			$("#crop_normal").append(newHtml);

		} else {
			var newHtml = '<li class=\"col-lg-4 col-md-4 col-sm-6 padding-left-none\"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" value="'
					+ cropName
					+ '"></input>&nbsp;&nbsp;<span>'
					+ cropName
					+ '</span></li>';
			$("#crop_vegitable").append(newHtml);
		}	
			cropAdd=true;
			
		}
		$('#crop_name').val("");
		$('input[name="crop_type"]').prop("checked", false);
		/*updated By Bhagvan Singh on 16-04-2015
		start*/
		if(cropAdd)
		{		
		div_hide5();
		customAlerts('"'+cropName+'" crop is added successfully', "success", 0);
		}
		else
			{
			div_hide5();
			}
		/*end*/
	
	} else {
		customAlerts("Please select crop type", "error", 0);
		return false;
	}
  }
function showCroAndCropInfoPage() {
	if (createProcessForShowCropAndCropInfoPage()) {
		callMethodForPageChangeAndProgressBarImage(5, 0);
	}
}
function decidePageUsingStrategy() {
	
	callMethodForPageChangeAndProgressBarImage(1, 2);
}
function showCropInfoDetails() {
	if (createProcessForShowCropInfoDetails()) {
		callMethodForPageChangeAndProgressBarImage(3, 4);
	}
}

var crop_detail_id = "";
var crop_detail_array = new Array();
function getProductionCostDetails(crop_id, crop_name) {
	$("#crop_name_dynamic").html(crop_name);
	crop_name = crop_name.replace(/\s+/g, '');
	crop_detail_id = crop_name;
	var id = crop_name;
	if($("#optional_crop_dynamic_div_innerdiv_append"+crop_detail_id+" table tbody tr").length>2){
		crop_detail_array.push(id);
	}
	$("#add_production_cost_field").attr("onclick","addProductionCostField('"+crop_detail_id+"')");
	$("#modify_production_cost_field").attr("onclick","modifyProductionCostField('"+crop_detail_id+"')");
	$("#remove_production_cost_field").attr("onclick","removeProductionCostField('"+crop_detail_id+"')");
	
	flagid = false;
	for (var i = 0; i < crop_detail_array.length; i++) {
		if (id == crop_detail_array[i]) {

			flagid = true;
		}

	}
	if (!flagid) {
		crop_detail_array.push(id);
		var a = "<table id='newhtml' class='table table-striped tbl-bordr  tblbrdr' cellspacing='0' width='100%'>"
				+ "<thead><tr class='tblhd add-fieldi'><td class='text-center'>Modify</td><td class='tblbrdr add-fieldi'>Component</td><td class='text-center add-fieldi'># of Units per Acre</td><td class='text-center'>$ per Unit</td><td class='text-center'>$ per Acre</td></tr></thead>"
				+ "<tbody id='production_cost_table_tbody__"+id+"'>"
				+ "<tr id='production_cost_component_row__1_"+ id+ "' class='tblgrn'><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__1_"+ id+ "\"></td><td class='tblft1' id='production_cost_component_name__1_"+ id+ "'>Crop Insurance</td><td class='success infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(1);addCommaSignWithOutDollar(this)' id='production_cost_value_1_row__1_"
				+ id
				+ "' /></td><td class='success infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(1);addCommaSignWithDollar(this)' id='production_cost_value_2_row__1_"
				+ id
				+ "' /></td><td class='success infotext' id='production_cost_calculate_value__1_"
				+ id
				+ "'></td></tr>"
				+ "<tr id='production_cost_component_row__2_"+ id+ "' class='tblbclgrnd'><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__2_"+ id+ "\"></td><td class='tblft1' id='production_cost_component_name__2_"+ id+ "'>Equipment</td><td class='infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(2);addCommaSignWithOutDollar(this)' id='production_cost_value_1_row__2_"
				+ id
				+ "' /></td><td class='infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(2);addCommaSignWithDollar(this)' id='production_cost_value_2_row__2_"
				+ id
				+ "' /></td><td class='infotext' id='production_cost_calculate_value__2_"
				+ id
				+ "'></td></tr>"
				+ "<tr id='production_cost_component_row__3_"+ id+ "' class='tblgrn'><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__3_"+ id+ "\"></td><td class='tblft1' id='production_cost_component_name__3_"+ id+ "'>Fertilizer</td><td class='success infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(3);addCommaSignWithOutDollar(this)' id='production_cost_value_1_row__3_"
				+ id
				+ "' /></td><td class='success infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(3);addCommaSignWithDollar(this)' id='production_cost_value_2_row__3_"
				+ id
				+ "' /></td><td class='success infotext' id='production_cost_calculate_value__3_"
				+ id
				+ "'></td></tr>"
				+ "<tr id='production_cost_component_row__4_"+ id+ "' class='tblbclgrnd'><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__4_"+ id+ "\"></td><td class='tblft1' id='production_cost_component_name__4_"+ id+ "'>Financing</td><td class='infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(4);addCommaSignWithOutDollar(this)' id='production_cost_value_1_row__4_"
				+ id
				+ "' /></td><td class='infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(4);addCommaSignWithDollar(this)' id='production_cost_value_2_row__4_"
				+ id
				+ "' /></td><td class='infotext' id='production_cost_calculate_value__4_"
				+ id
				+ "'></td></tr>"
				+ "<tr id='production_cost_component_row__5_"+ id+ "' class='tblgrn'><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__5_"+ id+ "\"></td><td class='tblft1' id='production_cost_component_name__5_"+ id+ "'>Herbicide</td><td class='success infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(5);addCommaSignWithOutDollar(this)' id='production_cost_value_1_row__5_"
				+ id
				+ "' /></td><td class='success infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(5);addCommaSignWithDollar(this)' id='production_cost_value_2_row__5_"
				+ id
				+ "' /></td><td class='success infotext' id='production_cost_calculate_value__5_"
				+ id
				+ "'></td></tr>"
				+ "<tr id='production_cost_component_row__6_"+ id+ "' class='tblbclgrnd'><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__6_"+ id+ "\"></td><td class='tblft1' id='production_cost_component_name__6_"+ id+ "'>Insecticide</td><td class='infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(6);addCommaSignWithOutDollar(this)' id='production_cost_value_1_row__6_"
				+ id
				+ "' /></td><td class='infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(6);addCommaSignWithDollar(this)' id='production_cost_value_2_row__6_"
				+ id
				+ "' /></td><td class='infotext' id='production_cost_calculate_value__6_"
				+ id
				+ "'></td></tr>"
				+ "<tr id='production_cost_component_row__7_"+ id+ "' class='tblgrn'><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__7_"+ id+ "\"></td><td class='tblft1' id='production_cost_component_name__7_"+ id+ "'>Irrigation</td><td class='success infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(7);addCommaSignWithOutDollar(this)' id='production_cost_value_1_row__7_"
				+ id
				+ "' /></td><td class='success infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(7);addCommaSignWithDollar(this)' id='production_cost_value_2_row__7_"
				+ id
				+ "' /></td><td class='success infotext' id='production_cost_calculate_value__7_"
				+ id
				+ "'></td></tr>"
				+ "<tr id='production_cost_component_row__8_"+ id+ "' class='tblbclgrnd'><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__8_"+ id+ "\"></td><td class='tblft1' id='production_cost_component_name__8_"+ id+ "'>Labor</td><td class='infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(8);addCommaSignWithOutDollar(this)' id='production_cost_value_1_row__8_"
				+ id
				+ "' /></td><td class='infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(8);addCommaSignWithDollar(this)' id='production_cost_value_2_row__8_"
				+ id
				+ "' /></td><td class='infotext' id='production_cost_calculate_value__8_"
				+ id
				+ "'></td></tr>"
				+ "<tr id='production_cost_component_row__9_"+ id+ "' class='tblgrn'><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__9_"+ id+ "\"></td><td class='tblft1' id='production_cost_component_name__9_"+ id+ "'>Micro Nutrients</td><td class='success infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(9);addCommaSignWithOutDollar(this)' id='production_cost_value_1_row__9_"
				+ id
				+ "' /></td><td class='success infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(9);addCommaSignWithDollar(this)' id='production_cost_value_2_row__9_"
				+ id
				+ "' /></td><td class='success infotext' id='production_cost_calculate_value__9_"
				+ id
				+ "'></td></tr>"
				+ "<tr id='production_cost_component_row__10_"+ id+ "' class='tblbclgrnd'><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__10_"+ id+ "\"></td><td class='tblft1' id='production_cost_component_name__10_"+ id+ "'>Professional Services</td><td class='infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(10);addCommaSignWithOutDollar(this)' id='production_cost_value_1_row__10_"
				+ id
				+ "' /></td><td class='infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(10);addCommaSignWithDollar(this)' id='production_cost_value_2_row__10_"
				+ id
				+ "' /></td><td class='infotext' id='production_cost_calculate_value__10_"
				+ id
				+ "'></td></tr>"
				+ "<tr id='production_cost_component_row__11_"+ id+ "' class='tblgrn'><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__11_"+ id+ "\"></td><td class='tblft1' id='production_cost_component_name__11_"+ id+ "'>Rent</td><td class='success infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(11);addCommaSignWithOutDollar(this)' id='production_cost_value_1_row__11_"
				+ id
				+ "' /></td><td class='success infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(11);addCommaSignWithDollar(this)' id='production_cost_value_2_row__11_"
				+ id
				+ "' /></td><td class='success infotext' id='production_cost_calculate_value__11_"
				+ id
				+ "'></td></tr>"
				+ "<tr id='production_cost_component_row__12_"+ id+ "' class='tblbclgrnd'><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__12_"+ id+ "\"></td><td class='tblft1' id='production_cost_component_name__12_"+ id+ "'>Seed</td><td class='infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(12);addCommaSignWithOutDollar(this)' id='production_cost_value_1_row__12_"
				+ id
				+ "' /></td><td class='infotext'><input type='text' name='Crop' onkeypress='return isValidNumberValue(event)' onchange='getCalculateValue(12);addCommaSignWithDollar(this)' id='production_cost_value_2_row__12_"
				+ id
				+ "' /></td><td class='infotext' id='production_cost_calculate_value__12_"
				+ id
				+ "'></td></tr>"
				+ "<tr class='tblgrn'><td class='tblft1 optncal' colspan=\"2\">Calculate</td><td class='success'></td><td class='success'></td><td class='success'></td></tr>"
				+ "<tr class='tblft text-center'><td class='tblft1' colspan=\"2\">Variable Cost per Acre: </td><td><input type='hidden' id='hidden_id_for_production_cost_row' value='0' /> </td><td></td><td class='optndoller' id='production_cost_calculate_total_"
				+ id + "'>$0</td></tr>" + "</tbody></table>";
//  bhagvan singh work after component table created 11-04-15
//		$("#optional_crop_dynamic_div").append(a);
		if($("#optional_crop_dynamic_div_innerdiv_append"+crop_detail_id+" table tbody tr").length == 0){
			$("#optional_crop_dynamic_div").append("<div class='table-responsive' id=\"optional_crop_dynamic_div_innerdiv_append"+ id+ "\"></div>");
		}
		$("#optional_crop_dynamic_div_innerdiv_append"+crop_detail_id).html(a);
		$("#optional_crop_dynamic_div_innerdiv_append"+crop_detail_id).show();
		
		$(".show_hide_class").addClass("hidden");
		$("#optional_crop").removeClass("hidden");
		$("#hidden_id_for_production_cost_row").val(id);
		$("#production_cost_calculate_total").html("$xxx.xx");
	} else if (flagid == true) {

		$("#optional_crop_dynamic_div_innerdiv_append" + id).show();
		$(".show_hide_class").addClass("hidden");
		$("#optional_crop").removeClass("hidden");
		$("#hidden_id_for_production_cost_row").val(id);

	}

}
function getCalculateValue(id) {
	
	/*update by Bhagvan Singh on 13-04-2015
	start*/
	


	var productionCostValue1 = $.trim(""
			+ $('#production_cost_value_1_row__' + id + "_" + crop_detail_id)
					.val().replace('$', '').replace(/,/g, ''));
	var productionCostValue2 = $.trim(""
			+ $('#production_cost_value_2_row__' + id + "_" + crop_detail_id)
					.val().replace('$', '').replace(/,/g, ''));
	
	// name"+$('#production_cost_value_2_row__'+id+"_"+crop_detail_id));
	// alert("id
	// value"+$('#production_cost_value_2_row__'+id+"_"+crop_detail_id).val());

	// alert(productionCostValue1+"2 is :"+productionCostValue2);
	if (productionCostValue1 == "") {

		$("#production_cost_calculate_value__" + id + "_" + crop_detail_id)
				.html("");
		getTotalCost(crop_detail_id);
		return false;
	}
	if (productionCostValue2 == "") {

		$("#production_cost_calculate_value__" + id + "_" + crop_detail_id)
				.html("");
		getTotalCost(crop_detail_id);
		return false;
	}
	if (!validateNumberOnly(productionCostValue1)) {
		$("#production_cost_value_1_row__" + id).css("border", "1px solid red");
		$("#production_cost_value_1_row__" + id).click(function() {
			$(this).css("border", "1px solid #cccccc");
		});
		$("#production_cost_calculate_value__" + id + "_" + crop_detail_id)
				.html("");
		getTotalCost(crop_detail_id);
		return false;
	}
	if (!validateNumberOnly(productionCostValue2)) {
		$("#production_cost_value_2_row__" + id).css("border", "1px solid red");
		$("#production_cost_value_2_row__" + id).click(function() {
			$(this).css("border", "1px solid #cccccc");
		});
		$("#production_cost_calculate_value__" + id + "_" + crop_detail_id)
				.html("");
		getTotalCost(crop_detail_id);
		return false;
	}
	productionCostValue1 = Number(productionCostValue1);
	productionCostValue2 = Number(productionCostValue2);
	var total_cost=(productionCostValue1 * productionCostValue2);
	
	var a=addCommaSignWithDollarWithValue(""+total_cost);
	$("#production_cost_calculate_value__" + id + "_" + crop_detail_id).html(a);
	getTotalCost(crop_detail_id);


	/*end*/
	
	/*

	var productionCostValue1 = $.trim(""
			+ $('#production_cost_value_1_row__' + id + "_" + crop_detail_id)
					.val().replace('$', '').replace(/,/g, ''));
	var productionCostValue2 = $.trim(""
			+ $('#production_cost_value_2_row__' + id + "_" + crop_detail_id)
					.val().replace('$', '').replace(/,/g, ''));
	
	// name"+$('#production_cost_value_2_row__'+id+"_"+crop_detail_id));
	// alert("id
	// value"+$('#production_cost_value_2_row__'+id+"_"+crop_detail_id).val());

	// alert(productionCostValue1+"2 is :"+productionCostValue2);
	if (productionCostValue1 == "") {

		$("#production_cost_calculate_value__" + id + "_" + crop_detail_id)
				.html("");
		getTotalCost();
		return false;
	}
	if (productionCostValue2 == "") {

		$("#production_cost_calculate_value__" + id + "_" + crop_detail_id)
				.html("");
		getTotalCost();
		return false;
	}
	if (!validateNumberOnly(productionCostValue1)) {
		$("#production_cost_value_1_row__" + id).css("border", "1px solid red");
		$("#production_cost_value_1_row__" + id).click(function() {
			$(this).css("border", "1px solid #cccccc");
		});
		$("#production_cost_calculate_value__" + id + "_" + crop_detail_id)
				.html("");
		getTotalCost();
		return false;
	}
	if (!validateNumberOnly(productionCostValue2)) {
		$("#production_cost_value_2_row__" + id).css("border", "1px solid red");
		$("#production_cost_value_2_row__" + id).click(function() {
			$(this).css("border", "1px solid #cccccc");
		});
		$("#production_cost_calculate_value__" + id + "_" + crop_detail_id)
				.html("");
		getTotalCost();
		return false;
	}
	productionCostValue1 = Number(productionCostValue1);
	productionCostValue2 = Number(productionCostValue2);
	var total_cost=(productionCostValue1 * productionCostValue2);
	
	var a=addCommaSignWithDollarWithValue(""+total_cost);
	$("#production_cost_calculate_value__" + id + "_" + crop_detail_id).html(a);
	getTotalCost();
//	getTotalCost(crop_detail_id);

*/}
function showTotalProductionCost() {
	$("#optional_crop_dynamic_div_innerdiv_append" + crop_detail_id).hide();
	var rowId = $.trim("" + $('#hidden_id_for_production_cost_row').val());
	// alert("row id is"+rowId);
	var total = $.trim(""
			+ $('#production_cost_calculate_total_' + crop_detail_id).html());
	// rowId=parseInt(rowId);
	// alert("total is :"+total);
	// alert("row id2 is"+rowId);
	// var newHtml=""+total+"<br /><span class=\"pull-right\"><a
	// onclick=\"getProductionCostDetails("+rowId+")\">Detail</a></span></td>";
	var previous_total=$('#calulated_cost_of_production__' + rowId).val();



	/*Modified by Harshit Gupta
	31-03-2015
	For Undefined problem
	*/
	
	if(parseInt(total)>0)
	$('#calulated_cost_of_production__' + rowId).val(total);
	
	/*MOdified by Harshit Gupta on 20-04-2015*/
	if (previous_total == "") {
		previous_total = "$0";
	}
	 var r = confirm('Do you want to change the variable production cost for "'+rowId+' " from " '+ previous_total+'" to "'+total+'"?');
	    if (r == true) {
	    	$('#calulated_cost_of_production__' + rowId).val(total);
	    } else {
	    	$('#calulated_cost_of_production__' + rowId).val(previous_total);
	    }
	
}
function getTotalCost(id) {
	
	/*updated by Bhagvan Singh on 13-04-2015
	start*/

//	alert("ID : "+id);
	var rowLength = $('#production_cost_table_tbody__'+id+' >tr').length;
	rowLength = parseInt(rowLength);
	rowLength = rowLength - 2;
	var total = 0;
	for (var i = 1; i <= rowLength; i++) {
		var rowTotal = $.trim(""
				+ $('#production_cost_calculate_value__' + i + "_"+ crop_detail_id).html().replace('$', '').replace(/,/g, ''));
		if (rowTotal != "" && validateNumberOnly(rowTotal)) {
			rowTotal = Number(rowTotal);
			total += rowTotal;
		}
	}
	$("#production_cost_calculate_total_" + crop_detail_id).html(addCommaSignWithDollarWithValue(""+total));

	
	/*end*/
	
	
	/*
	var rowLength = $('#production_cost_table_tbody >tr').length;
	rowLength = parseInt(rowLength);
	rowLength = rowLength - 2;
	var total = 0;
	for (var i = 1; i <= rowLength; i++) {
		var rowTotal = $.trim(""
				+ $('#production_cost_calculate_value__' + i + "_"
								+ crop_detail_id).html().replace('$', '').replace(/,/g, ''));
		if (rowTotal != "" && validateNumberOnly(rowTotal)) {
			rowTotal = Number(rowTotal);
			total += rowTotal;
		}
	}
	$("#production_cost_calculate_total_" + crop_detail_id).html(addCommaSignWithDollarWithValue(""+total));
*/}
function showFieldCropPage() {
	if (createProcessForShowFieldCropPage()) {
		if(strategy == "acres"){
			showResourcesPage();
		}
		callMethodForPageChangeAndProgressBarImage(4, 5);
	}
}
function showResourcesPage() {
	
	if(createProcessForShowResourcesPage()){
	callMethodForPageChangeAndProgressBarImage(6, 5);
	}
}
function resourcesPreviousPage() {
	callMethodForPageChangeAndProgressBarImage(5, 4);
}
function removeCropFieldChoiceHead() {
	var theadRowLength = $("#field_choice_crop_thead > tr").length;
	var theadColumnLength = 1;

	if (theadRowLength != "0") {
		theadColumnLength = $("#field_choice_crop_thead_row_first > td").length;
	}
	theadColumnLength = parseInt(theadColumnLength);
	var myTestArray = new Array();
	for (var i = 2; i <= theadColumnLength; i++) {
		var columnName = $.trim(""
				+ $("#field_choice_crop_thead_row_column__" + i).html());
		var isFind = false;
		// alert("columnName : "+columnName);
		for (var j = 0; j < field_crop.length; j++) {
			if (columnName.toLowerCase() === field_crop[j].toLowerCase()) {
				isFind = true;
				// alert("crop found : "+columnName);
			}

		}
		if (!isFind) {
			// alert("crop not found : "+columnName);
			myTestArray.push(i);
		}
	}

	var textNowFlag = 0;
	for (var i = 0; i < myTestArray.length; i++) {
		myTestArray[i] = parseInt(myTestArray[i]);
		myTestArray[i] = myTestArray[i] - textNowFlag;

		var deletedRowCount = $('#field_choice_crop_thead_row_first > td').length;
		var deleteVericalRowCount = $('#field_choice_crop_tbody > tr').length;
		if (deletedRowCount > 0) {
			var newHtml = "<tr id=\"field_choice_crop_thead_row_first\" class=\"tblhd text-center add-fieldi\"><td id=\"field_choice_crop_thead_row_column__1\" class=\"tblbrdr text-center add-fieldi\">Field/Crop</td>";
			var rowCount = 2;
			for (var k = 2; k <= deletedRowCount; k++) {
				if (k != (myTestArray[i])) {
					newHtml += "<td id=\"field_choice_crop_thead_row_column__"
							+ rowCount
							+ "\" class=\"text-center add-fieldi\">"
							+ $("#field_choice_crop_thead_row_column__" + k)
									.html() + "</td>";
					rowCount++;
				}
			}
			newHtml += "</tr>";
			$("#field_choice_crop_thead_row_column__" + (myTestArray[i]))
					.remove();
			$("#field_choice_crop_thead").html(newHtml);
		}
		if (deleteVericalRowCount > 0) {
			var rowCount = 1;
			for (var k = 1; k <= deleteVericalRowCount; k++) {
				var columnCount = 1;
				var deleteCericalColumnCount = $('#field_choice_crop_tbody_row__'
						+ k + ' > td').length;
				// alert("deleteCericalColumnCount "+ deleteCericalColumnCount);
				// alert(" myTestArray[i] "+myTestArray[i]);
				var newHtml = "<td class=\"tblft1\" id=\"field_choice_crop_tbody_row_first_column__"
						+ k
						+ "\">"
						+ $("#field_choice_crop_tbody_row_first_column__" + k)
								.html() + "</td>";

				for (var j = 1; j < deleteCericalColumnCount; j++) {
					myTestArray[i] = parseInt(myTestArray[i]);
					if (j != (myTestArray[i] - 1)) {
						// alert("row no : "+k+" column no : "+j+" not match :
						// "+(myTestArray[i]-1));
						var check = "";
						if ($(
								'#field_choice_crop_selected_row__' + k
										+ '__column__' + j).is(':checked')) {
							check = "checked";
						}
						newHtml += "<td id=\"field_choice_crop_tbody_row_others_column__"
								+ k
								+ "__column__"
								+ columnCount
								+ "\" class=\"success\"><input type=\"checkbox\" id=\"field_choice_crop_selected_row__"
								+ k
								+ "__column__"
								+ columnCount
								+ "\"  "
								+ check + " /></td>";
						columnCount++;
					} else {
						// alert("row no : "+k+" column no : "+j+" match :
						// "+(myTestArray[i]-1));
					}
				}
				$(
						"#field_choice_crop_tbody_row_others_column__" + k
								+ "__column__" + (myTestArray[i] - 1)).remove();
				// alert("Remove id is
				// field_choice_crop_tbody_row_others_column__"+k+"__column__" +
				// (myTestArray[i]-1));
				$("#field_choice_crop_tbody_row__" + k).html(newHtml);
				rowCount++;
			}
		}
		//field_crop.splice(myTestArray[i], 1);
		textNowFlag++;
	}
}
function checkCropTableBalance() {
	var headColumnLength = $("#field_choice_crop_thead_row_first > td").length;
	var bodyRowLength = $("#field_choice_crop_tbody > tr").length;
	// alert("headColumnLength : "+headColumnLength);
	// alert("bodyRowLength : "+bodyRowLength);
	headColumnLength = parseInt(headColumnLength);
	bodyRowLength = parseInt(bodyRowLength);
	for (var i = 1; i <= bodyRowLength; i++) {
		var lengthOfBodyRow = $("#field_choice_crop_tbody_row__" + i + " > td").length;
		lengthOfBodyRow = parseInt(lengthOfBodyRow);
		if (lengthOfBodyRow != headColumnLength) {
			// alert("lengthOfBodyRow : "+lengthOfBodyRow);
			// alert("headColumnLength : "+headColumnLength);
			var count = lengthOfBodyRow;
			var newHtml = "";
			for (var j = lengthOfBodyRow; j < headColumnLength; j++) {
				newHtml += "<td class=\"success\" id=\"field_choice_crop_tbody_row_others_column__"
						+ i
						+ "__column__"
						+ j
						+ "\"><input type=\"checkbox\" id=\"field_choice_crop_selected_row__"
						+ i + "__column__" + j + "\"></td>";
				count++;
			}
			$("#field_choice_crop_tbody_row__" + i).append(newHtml);
		}

	}

}
var resourceValidationForCheckboxGlobal="";
function showResourcesUsagePage() {

	if (createProcessForShowResourcesUsagePage()) {
		callMethodForPageChangeAndProgressBarImage(7, 6);
	}
}
function showFieldVariencePage() {
	crop_array=[];
	var length_showresource=$("#field_choice_crop_tbody >tr").length;
	var crop_field_flag=false;
		for(var i=1;i<=length_showresource;i++)
			{
		 crop_field_flag=false;
			var length_showresource_td=$("#field_choice_crop_tbody_row__"+i+" >td").length;
			for(var j=1;j<=length_showresource_td;j++)
				{
				if($("#field_choice_crop_selected_row__"+i+"__column__"+j+"").prop('disabled'))
				{ 
				crop_field_flag=true;
				}else if($("#field_choice_crop_selected_row__"+i+"__column__"+j+"").prop('checked'))
					{ 
					crop_field_flag=true;
					var a=$("#field_choice_crop_tbody_row_first_column__"+i).html();
				            
					var b=$("#field_choice_crop_thead_row_column__"+(j+1)).html();
					crop_array.push(a+"#-#-#"+b);
					}
				}
			}
		if(!crop_field_flag )
			{
			customAlerts("Please select at least one crop for each field", "error", 0);
			return false;
			
			}
	addFieldVarianceResourceValue();
	$(".show_hide_class").addClass("hidden");
	$("#field_varience").removeClass("hidden");
	$('#field_select_drop_down option').each(
			function(idx, val) {
				var isRemove = true;
				for (var i = 0; i < field_name.length; i++) {
					if ($(this).val().toLowerCase() === field_name[i]
							.toLowerCase()) {
						isRemove = false;
					}
				}
				if ($(this).val() == "0") {
					isRemove = false;
				}
				if (isRemove) {
					$(
							"#field_select_drop_down option[value="
									+ $(this).val() + "]").remove();
				}
			});
	
	
	$('#crop_select_drop_down option').each(
			function(idx, val) {
				var isRemove = true;
				for (var i = 0; i < field_crop.length; i++) {
					if ($(this).val().toLowerCase() === field_crop[i]
							.toLowerCase()) {
						isRemove = false;
					}
				}
				if ($(this).val() == "0") {
					isRemove = false;
				}
				if (isRemove) {
					$(
							"#crop_select_drop_down option[value="
									+ $(this).val() + "]").remove();
				}
			});

	var newFieldDropDown = "";
	var newCropDropDown = "";
	for (var i = 0; i < field_name.length; i++) {
		var isAdd = true;
		$('#field_select_drop_down option').each(function(idx, val) {
			if ($(this).val().toLowerCase() === field_name[i].toLowerCase()) {
				isAdd = false;
			}
		});
		if (isAdd) {
			newFieldDropDown += "<option value='" + field_name[i] + "'>"
					+ field_name[i] + "</option>";
		}
	}
	/*uncomment By Bhagvan Singh on 10-04-2015 for Dynamic Field add
	start*/
	$("#field_select_drop_down").append(newFieldDropDown);
//	end
	for (var i = 0; i < field_crop.length; i++) {

		var isAdd = true;
		$('#crop_select_drop_down option').each(function(idx, val) {
			if ($(this).val().toLowerCase() === field_crop[i].toLowerCase()) {
				isAdd = false;
			}
		});
		if (isAdd) {
			newCropDropDown += "<option value='" + field_crop[i] + "'>"
					+ field_crop[i] + "</option>";
		}

	}
	/*uncomment By Bhagvan Singh on 10-04-2105 for Dynamic crop add
	start*/
	$("#crop_select_drop_down").append(newCropDropDown);
//	end
//	$('#image_bar').attr("src", "images/progress-bar-11.png");
	var cropName=$("#crop_select_drop_down").val();
	if(cropName!="0")
      {
		for(var k=1;k<=$("#crop_resources_usages_difference_tbody > tr").length;k++)
		{
	for(var i=1;i<=$("#crop_resource_usage_tbody >tr").length;i++)
	{
	for(var j=4;j<=$("#crop_resource_usage_row__"+i+" >td").length;j++)
		{
		if(($("#resources_usages_difference_row__"+k+"_resource_name").html()==$("#crop_resource_usage_thead_first_row_column__"+j).html()) && cropName==$("#crop_resource_usage_crop__"+i).html())
			{
			$("#resources_usages_difference_row__"+k+"_resource_default").html($.trim("" + $("#crop_resource_usage__"+i+"__resource__"+(j-3)).val()));
			}
		
		}
		
	
    }
	
		}
		
      }
	/*Added by Harshit Gupta for reloading field difference value on 20-04-2015*/
//	var selectedCrop = $("#crop_select_drop_down").val();
//	$("#field_select_drop_down").change();
////	$("#crop_select_drop_down").val(selectedCrop);
	$("#crop_select_drop_down").change();
}
function fieldselectcrop(fieldvalue)
{
	$("#crop_Yield_Difference_Expected").html("");
	$("#crop_Yield_Difference_Minimum").html("");
	$("#crop_Yield_Difference_Maximum").html("");
	$("#resources_usages_production_resource_default").html("");
	$("#resources_usages_production_cost_resource_override").val("");
	$("#field_difference_exp").val("");
	$("#field_difference_min").val("");
	$("#field_difference_max").val("");
	for(var k=1;k<=$("#crop_resources_usages_difference_tbody > tr").length;k++)
	{
		
		$("#resources_usages_difference_row__"+k+"_resource_default").html("");
		$("#resources_usages_difference_row__"+k+"_resource_override").val("");
	}
//	alert("value is"+fieldvalue);
	$("#crop_select_drop_down").empty();
	var fallowFieldCheck = 0;
	var newCropDropDown = "<option value=0>Select Crop </option>";
	for(var i=0;i<crop_array.length;i++)
		{
		var fieldcrop=crop_array[i].split("#-#-#");
		// console.log(crop_array[i]+"---------------"+fieldvalue);
		if(fieldcrop[0]==fieldvalue)
			{
			fallowFieldCheck += 1;
//			alert("true"+fallowFieldCheck);
			/*if (fieldvalue.trim() != "0" && fallowFieldCheck == 0){
			alert("fallow : "+fieldvalue.trim());
			}*/
			newCropDropDown += "<option value='" + fieldcrop[1] + "'>"
			+ fieldcrop[1] + "</option>";
			}
		
		}
	/*Modified by Harshit Gupta on 13-04-2015
	 * Start
	 * */
	if (fieldvalue.trim() == "0"){
		$("#crop_Yield_Difference_Expected").html("");
		$("#crop_Yield_Difference_Minimum").html("");
		$("#crop_Yield_Difference_Maximum").html("");
		$("#resources_usages_production_resource_default").html("");
		$("#resources_usages_production_cost_resource_override").val("");
		/*Added by Harshit Gupta on 09-04-2015
		 * Start*/
		$("#field_difference_exp").val("");
		$("#field_difference_min").val("");
		$("#field_difference_max").val("");
//		$("#resources_usages_difference_row1__0_resource_override").val("");
		/*End*/
		for(var k=1;k<=$("#crop_resources_usages_difference_tbody > tr").length;k++)
		{
			
			$("#resources_usages_difference_row__"+k+"_resource_default").html("");
			$("#resources_usages_difference_row__"+k+"_resource_override").val("");
		}
	}if (fieldvalue.trim() != "0" && fallowFieldCheck == 0){
		customAlerts("Selected field is declared as Fallow so don't have any crops", "error", 0);
		$("#crop_Yield_Difference_Expected").html("");
		$("#crop_Yield_Difference_Minimum").html("");
		$("#crop_Yield_Difference_Maximum").html("");
		$("#resources_usages_production_resource_default").html("");
		$("#resources_usages_production_cost_resource_override").val("");
		/*Added by Harshit Gupta on 09-04-2015
		 * Start*/
		$("#field_difference_exp").val("");
		$("#field_difference_min").val("");
		$("#field_difference_max").val("");
//		$("#resources_usages_difference_row1__0_resource_override").val("");
		/*End*/
		
	}
	$("#crop_select_drop_down").append(newCropDropDown);
}
		function showForwardSalesPage() {

			removeForwardSalesTableValue();
			var rowLength = $("#forward_sales_information_tbody > tr ").length;
			var newHtml = "";
			var rowCount = parseInt(rowLength);
			var nowRowCount=0;
			for (var i = 0; i < field_crop.length; i++) {
				var isAdd = true;
				for (var j = 1; j <= rowLength; j++) {
					var columnName = $.trim(""
							+ $('#forward_sales_information_tbody_row_crop_name__' + j)
									.html());
					if (columnName.toLowerCase() === field_crop[i].toLowerCase()) {
						isAdd = false;
						nowRowCount=j;
					}
				}
				var uomValue = "";
				$.each(crop_uom, function(key, value) {
					if (field_crop[i].toLowerCase() == key.toLowerCase()) {
						uomValue = crop_uom[key];
//						 alert("uomValue : "+uomValue);
					}
				});
				if (isAdd) {
					
					rowCount++;
					if (rowCount % 2 == 0) {
						newHtml += "<tr class=\"tblgrn text-center\" id=\"forward_sales_information_tbody_row__"
								+ rowCount
								+ "\"><td class=\"tblft1\" id=\"forward_sales_information_tbody_row_crop_name__"
								+ rowCount
								+ "\">"
								+ field_crop[i]
								+ "</td><td id=\"forward_sales_information_tbody_row_uomValue__"	+ rowCount	+ "\" class=\"success infotext tittle-uppercase\">"
								+ uomValue
								+ "</td>"
								+ "<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"priceCalForwardSale(this);addCommaSignWithDollar(this)\" id=\"forward_sales_information_tbody_row_crop_price__"
								+ rowCount
								+ "\" /></td><td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"quantityCalForwardSale(this)\" id=\"forward_sales_information_tbody_row_quantity__"
								+ rowCount
								+ "\" /></td><td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"acreCalForwardSale(this)\" id=\"forward_sales_information_tbody_row_acres__"
								+ rowCount
								+ "\" /></td>"
								+ "<td class=\"success croplimit\"><input type=\"checkbox\" onclick=\"ProposedFirmCheckbox(this,1)\" id=\"forward_sales_information_tbody_row_proposed__"
								+ rowCount
								+ "\" /></td><td class=\"success croplimit\"><input type=\"checkbox\" onclick=\"ProposedFirmCheckbox(this,2)\" id=\"forward_sales_information_tbody_row_firm__"
								+ rowCount
								+ "\" /></td><td class=\"success croplimit\" id=\"forward_sales_information_tbody_row_upper_limit__"
								+ rowCount + "\"><input type=\"text\" onkeypress=\"return isValidNumberValueForWithOutDot(event)\" onchange=\"addPercentSign(this)\" id=\"forward_sales_information_tbody_row_upperLimit__"+ rowCount+ "\" /></td>" +
										"</tr>";
					} else {
						newHtml += "<tr class=\"tblbclgrnd text-center\" id=\"forward_sales_information_tbody_row__"
								+ rowCount
								+ "\"><td class=\"tblft1\" id=\"forward_sales_information_tbody_row_crop_name__"
								+ rowCount
								+ "\">"
								+ field_crop[i]
								+ "</td><td id=\"forward_sales_information_tbody_row_uomValue__"	+ rowCount	+ "\" class=\"success infotext tittle-uppercase\">"
								+ uomValue
								+ "</td>"
								+ "<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"priceCalForwardSale(this);addCommaSignWithDollar(this)\" id=\"forward_sales_information_tbody_row_crop_price__"
								+ rowCount
								+ "\" /></td><td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"quantityCalForwardSale(this)\" id=\"forward_sales_information_tbody_row_quantity__"
								+ rowCount
								+ "\" /></td><td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"acreCalForwardSale(this)\" id=\"forward_sales_information_tbody_row_acres__"
								+ rowCount
								+ "\" /></td>"
								+ "<td class=\"success croplimit\"><input type=\"checkbox\" onclick=\"ProposedFirmCheckbox(this,1)\" id=\"forward_sales_information_tbody_row_proposed__"
								+ rowCount
								+ "\" /></td><td class=\"success croplimit\"><input type=\"checkbox\" onclick=\"ProposedFirmCheckbox(this,2)\" id=\"forward_sales_information_tbody_row_firm__"
								+ rowCount
								+ "\" /></td><td class=\"success croplimit\" id=\"forward_sales_information_tbody_row_upper_limit__"
								+ rowCount + "\"><input type=\"text\" onkeypress=\"return isValidNumberValueForWithOutDot(event)\" onchange=\"addPercentSign(this)\" id=\"forward_sales_information_tbody_row_upperLimit__"+ rowCount+ "\" /></td>" +
								"</tr>";
					}

				}
				else
					{
					$("#forward_sales_information_tbody_row_uomValue__"+nowRowCount).html("");
					$("#forward_sales_information_tbody_row_uomValue__"+nowRowCount).html(uomValue);
					}
			}
			$("#forward_sales_information_tbody").append(newHtml);
			manageStep4 = true;
			enableDisableMenu();
			callMethodForPageChangeAndProgressBarImage(8, 7);
}
function showCropLimitPage() {

	if (createProcessForShowCropLimitPage()) {
		callMethodForPageChangeAndProgressBarImage(9, 8);
	}
}
function showCropInsurancePage() {
	addRemoveCropInsuranceTable();
	$(".show_hide_class").addClass("hidden");
	$("#crop_insurance_div").removeClass("hidden");
	$("#crop-insurance").addClass("visited");
	manageStep6 = true;
}

/*update by Bhagvan Singh on 07-04-2015
start*/
function showFieldCropChoicePreviousPage() {
	callMethodForPageChangeAndProgressBarImage(4, 0);
}
//end
function addFieldVarianceResourceValue() {
	var rowLength = $("#crop_resources_usages_difference_tbody > tr ").length;
	var myTestArray = new Array();
	for (var i = 1; i <= rowLength; i++) {
		var isFind = false;
		var columnName = $.trim(""+ $('#resources_usages_difference_row__' + i+ '_resource_name').html());
		$.each(resources, function(key, v) {

			if (columnName.toLowerCase() === key.toLowerCase()) {
				isFind = true;
			}
		});
		if (!isFind) {
			myTestArray.push(i);
		}
	}
	var flag = 0;
	for (var i = 0; i < myTestArray.length; i++) {
		myTestArray[i] = parseInt(myTestArray[i]);
		myTestArray[i] = myTestArray[i] - parseInt(flag);
		var newHtml = "";
		var rowCount = $("#crop_resources_usages_difference_tbody > tr ").length;
		var count = 1;
		for (var k = 1; k <= rowCount; k++) {
			if (k != myTestArray[i]) {
				newHtml += "<tr class=\"tblgrn text-center\" id=\"resources_usages_difference_row__"+ count	+ "\"><td class=\"tblft1 tittle-uppercase\" id=\"resources_usages_difference_row__"	+ count	+ "_resource_name\" >"	+ $("#resources_usages_difference_row__" + k+ "_resource_name").html()	+ "</td>"+ "<td class=\"success infotext\"  id=\"resources_usages_difference_row__"+ count+ "_resource_default\">"+ $("#resources_usages_difference_row__" + k+ "_resource_default").html()+ "</td><td class=\"success infotext\"><input type=\"text\" id=\"resources_usages_difference_row__"+ count	+ "_resource_override\" onchange=\"addCommaSignWithOutDollarDot(this)\" onkeypress=\"return isValidNumberValueForWithOutDot(event)\" value='"+ $("#resources_usages_difference_row__" + k+ "_resource_override").val()+ "' ></td></tr>";
				count++;
			}
		}
		flag++;
		$("#crop_insurance_table_tbody_row__" + myTestArray[i]).remove;
		$("#crop_resources_usages_difference_tbody").html("");
		$("#crop_resources_usages_difference_tbody").html(newHtml);
	}

	rowLength = $("#crop_resources_usages_difference_tbody > tr").length;
	// alert("rowLength : "+rowLength);
	var rowCount = parseInt(rowLength);
	var newHtml = "";
	// alert("rowCount : "+rowCount);
	var defalutResourceName="land";
	$.each(resources,function(k, v) {
						var isAdd = true;
						var nowColumnCount=0;
						var resourceValue="";
						
						//alert("k.toLowerCase() : "+k.toLowerCase());
						for (var i = 1; i <= rowLength; i++) {
							var columnName = $.trim(""+ $('#resources_usages_difference_row__'+ i + '_resource_name').html());
							if (columnName.toLowerCase() === k.toLowerCase()) {
								isAdd = false;
								nowColumnCount=i;
								// change by rohit to show default value by 16-04-2015;
								//resourceValue=resources[k];
								resourceValue= $.trim(""+ $('#resources_usages_difference_row__'+i+'_resource_default').html());
							}
							
						}
						if (isAdd) {
							if(k.toLowerCase()!=defalutResourceName.toLowerCase())
								{
								
								
								if(k.toLowerCase()!="Capital".toLowerCase())
									{
								newHtml += "<tr class=\"tblgrn text-center\" id=\"resources_usages_difference_row__"+ (rowCount-1)+ "\"><td class=\"tblft1 tittle-uppercase\" id=\"resources_usages_difference_row__"+ (rowCount-1)+ "_resource_name\" >"+ k	+ "</td>"+ "<td class=\"success infotext\" id=\"resources_usages_difference_row__"+ (rowCount-1)+ "_resource_default\"></td><td class=\"success infotext\"><input type=\"text\" onkeypress=\"return isValidNumberValueForWithOutDot(event)\" onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"resources_usages_difference_row__"	+ (rowCount-1)+ "_resource_override\" ></td></tr>";
								}
								rowCount++;
								}
							}
						else
							{
							if(k.toLowerCase()!=defalutResourceName.toLowerCase())
								{
								$("#resources_usages_difference_row__"+ nowColumnCount+ "_resource_default").html("");
								$("#resources_usages_difference_row__"+ nowColumnCount+ "_resource_default").html(resourceValue);
								}
							}

					});
	//alert("Second");
	$("#crop_resources_usages_difference_tbody").append(newHtml);
}
function removeForwardSalesTableValue() {
	var rowLength = $("#forward_sales_information_tbody > tr ").length;

	var myTestArray = new Array();
	for (var i = 1; i <= rowLength; i++) {
		var isFind = false;
		for (var j = 0; j < field_crop.length; j++) {
			var columnName = $.trim(""
					+ $('#forward_sales_information_tbody_row_crop_name__' + i)
							.html());
			if (columnName.toLowerCase() === field_crop[j].toLowerCase()) {
				isFind = true;
			}
		}
		if (!isFind) {
			myTestArray.push(i);
		}
	}
	var flag = 0;
	for (var i = 0; i < myTestArray.length; i++) {
		myTestArray[i] = parseInt(myTestArray[i]);
		myTestArray[i] = myTestArray[i] - parseInt(flag);
		var newHtml = "";
		var rowCount = $("#forward_sales_information_tbody > tr ").length;
		var count = 1;
		for (var k = 1; k <= rowCount; k++) {
			if (k != myTestArray[i]) {
				var uomValue = "";
				$.each(crop_uom, function(key, value) {
//					if ($("#forward_sales_information_tbody_row_crop_name__"	+ k).html().toLowerCase() == key.toLowerCase()) {
						uomValue = crop_uom[key];
//						 alert("uomValue : "+uomValue);
//					}
				});
				var checked = "";
				if ($("#forward_sales_information_tbody_row_firm__" + k).is(
						':checked')) {
					checked = "checked";
				}
				var proposedchecked = "";
				if ($("#forward_sales_information_tbody_row_proposed__" + k).is(
				':checked')) {
					proposedchecked = "checked";
				}
				if (count % 2 == 0) {
					newHtml += "<tr class=\"tblgrn text-center\" id=\"forward_sales_information_tbody_row__" + count
							+ "\"><td class=\"tblft1\" id=\"forward_sales_information_tbody_row_crop_name__"
							+ count
							+ "\">"
							+ $("#forward_sales_information_tbody_row_crop_name__"	+ k).html()
							+ "</td><td id=\"forward_sales_information_tbody_row_uomValue__"+ count	+ "\" class=\"success infotext tittle-uppercase\">"+uomValue+"</td>"
							+ "<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"priceCalForwardSale(this);addCommaSignWithDollar(this)\" id=\"forward_sales_information_tbody_row_crop_price__"
							+ count
							+ "\" value='"
							+ $("#forward_sales_information_tbody_row_crop_price__"	+ k).val()
							+ "' /></td><td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"quantityCalForwardSale(this)\" id=\"forward_sales_information_tbody_row_quantity__"
							+ count
							+ "\" value='"	+ $("#forward_sales_information_tbody_row_quantity__"+ k).val()	+ "' /></td><td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"acreCalForwardSale(this)\" id=\"forward_sales_information_tbody_row_acres__"
							+ count
							+ "\" value='"	+ $("#forward_sales_information_tbody_row_acres__"+ k).val()	+ "' /></td>"
							+ "<td class=\"success croplimit\"><input type=\"checkbox\" onclick=\"ProposedFirmCheckbox(this,1)\"  id=\"forward_sales_information_tbody_row_proposed__"
							+ count
							+ "\" "
							+ proposedchecked
							+ " /><td class=\"success croplimit\"><input type=\"checkbox\" onclick=\"ProposedFirmCheckbox(this,2)\"  id=\"forward_sales_information_tbody_row_firm__"
							+ count
							+ "\" "
							+ checked
							+ " /></td><td class=\"success croplimit\" id=\"forward_sales_information_tbody_row_upper_limit__"
							+ count + "\"><input type=\"text\" onkeypress=\"return isValidNumberValueForWithOutDot(event)\" onchange=\"addPercentSign(this)\" id=\"forward_sales_information_tbody_row_upperLimit__"
							+ count
							+ "\" value='"	+ $("#forward_sales_information_tbody_row_upperLimit__"+ k).val()	+ "' /></td></tr>";
				} else {
					newHtml += "<tr class=\"tblbclgrnd text-center\" id=\"forward_sales_information_tbody_row__" + count + "\">" +
									"<td class=\"tblft1\" id=\"forward_sales_information_tbody_row_crop_name__" + count + "\">" + $("#forward_sales_information_tbody_row_crop_name__"	+ k).html() + "</td>" +
									"<td id=\"forward_sales_information_tbody_row_uomValue__"	+ count	+ "\" class=\"success infotext tittle-uppercase\">"+uomValue+"</td>" + "<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"priceCalForwardSale(this);addCommaSignWithDollar(this)\" id=\"forward_sales_information_tbody_row_crop_price__" + count + "\" value='"	+ $("#forward_sales_information_tbody_row_crop_price__"	+ k).val()	+ "' /></td>" +
									"<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"quantityCalForwardSale(this)\" id=\"forward_sales_information_tbody_row_quantity__" + count + "\" value='"	+ $("#forward_sales_information_tbody_row_quantity__"+ k).val()	+ "' /></td>" +
									"<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"acreCalForwardSale(this)\" id=\"forward_sales_information_tbody_row_acres__" + count + "\" value='"	+ $("#forward_sales_information_tbody_row_acres__"+ k).val()	+ "' /></td>" +
									"<td class=\"success croplimit\"><input type=\"checkbox\" onclick=\"ProposedFirmCheckbox(this,1)\" id=\"forward_sales_information_tbody_row_proposed__" + count + "\" " + proposedchecked + " />" +
									"<td class=\"success croplimit\"><input type=\"checkbox\" onclick=\"ProposedFirmCheckbox(this,2)\" id=\"forward_sales_information_tbody_row_firm__" + count + "\" " + checked + " /></td>" +
									"<td class=\"success croplimit\" id=\"forward_sales_information_tbody_row_upper_limit__" + count + "\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onkeypress=\"return isValidNumberValueForWithOutDot(event)\" onchange=\"addPercentSign(this)\" id=\"forward_sales_information_tbody_row_upperLimit__" + count + "\" value='"	+ $("#forward_sales_information_tbody_row_upperLimit__"+ k).val()	+ "' /></td>" +
								"</tr>";
				}
				count++;
			}
			/*
			 * else { alert("find id k is : "+k); alert("find id array is :
			 * "+myTestArray[i]);
			 * 
			 * //myTestArray.splice(myTestArray[i], 1); }
			 */
		}
		flag++;
		$("#forward_sales_information_tbody_row__" + myTestArray[i]).remove;
		$("#forward_sales_information_tbody").html("");
		$("#forward_sales_information_tbody").html(newHtml);
	}
}
function addRemoveCropLimitTableData() {
	var rowLength = $("#crop_limits_table_tbody > tr ").length;

	var myTestArray = new Array();
	for (var i = 1; i <= rowLength; i++) {
		var isFind = false;
		for (var j = 0; j < field_crop.length; j++) {
			var columnName = $.trim(""
					+ $('#crop_limits_table_crop_name__' + i).html());
			if (columnName.toLowerCase() === field_crop[j].toLowerCase()) {
				isFind = true;
			}
		}
		if (!isFind) {
			myTestArray.push(i);
		}
	}
	var flag = 0;
	for (var i = 0; i < myTestArray.length; i++) {
		myTestArray[i] = parseInt(myTestArray[i]);
		myTestArray[i] = myTestArray[i] - parseInt(flag);
		var newHtml = "";
		var rowCount = $("#crop_limits_table_tbody > tr ").length;
		var count = 1;
		for (var k = 1; k <= rowCount; k++) {
			if (k != myTestArray[i]) {
				if (count % 2 == 0) {
					newHtml += "<tr class=\"tblgrn text-center\" id=\"crop_limits_table_tbody_row__"
							+ count
							+ "\"><td class=\"tblft1\"></td>" +
								"<td class=\"tblft1\" id=\"crop_limits_table_crop_name__"
							+ count
							+ "\">"
							+ $("#crop_limits_table_crop_name__" + k).html()
							+ "</td>"
							+ "<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"crop_limits_crop_maximum_acres__"
							+ count
							+ "\" value='"
							+ $("#crop_limits_crop_maximum_acres__" + k).val()
							+ "'/></td><td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"crop_limits_crop_minimum_acres__"
							+ count
							+ "\" value='"
							+ $("#crop_limits_crop_minimum_acres__" + k).val()
							+ "'/></td></tr>";
				} else {
					newHtml += "<tr class=\"tblbclgrnd text-center\" id=\"crop_limits_table_tbody_row__"
							+ count
							+ "\"><td class=\"tblft1\"></td>" +
							"<td class=\"tblft1\" id=\"crop_limits_table_crop_name__"
							+ count
							+ "\">"
							+ $("#crop_limits_table_crop_name__" + k).html()
							+ "</td>"
							+ "<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"crop_limits_crop_maximum_acres__"
							+ count
							+ "\" value='"
							+ $("#crop_limits_crop_maximum_acres__" + k).val()
							+ "'/></td><td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"crop_limits_crop_minimum_acres__"
							+ count
							+ "\" value='"
							+ $("#crop_limits_crop_minimum_acres__" + k).val()
							+ "'/></td></tr>";
				}
				count++;
			}
		}
		flag++;
		$("#forward_sales_information_tbody_row__" + myTestArray[i]).remove;
		$("#crop_limits_table_tbody").html("");
		$("#crop_limits_table_tbody").html(newHtml);
	}

	rowLength = $("#crop_limits_table_tbody > tr ").length;
	var newDivHtml = "";
	var rowCount = parseInt(rowLength);
	for (var i = 0; i < field_crop.length; i++) {
		var isAdd = true;
		for (var j = 1; j <= rowLength; j++) {
			var columnName = $.trim(""
					+ $('#crop_limits_table_crop_name__' + j).html());
			if (columnName.toLowerCase() === field_crop[i].toLowerCase()) {
				isAdd = false;
			}
		}
		if (isAdd) {
			rowCount++;
			if (rowCount % 2 == 0) {
				newDivHtml += "<tr class=\"tblgrn text-center\" id=\"crop_limits_table_tbody_row__"
						+ rowCount
						+ "\"><td class=\"tblft1\"></td>" +
								"<td class=\"tblft1\" id=\"crop_limits_table_crop_name__"
						+ rowCount
						+ "\">"
						+ field_crop[i]
						+ "</td>"
						+ "<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"crop_limits_crop_maximum_acres__"
						+ rowCount + "\" /></td><td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"crop_limits_crop_minimum_acres__"
						+ rowCount
						+ "\" /></td></tr>";
			} else {
				newDivHtml += "<tr class=\"tblbclgrnd text-center\" id=\"crop_limits_table_tbody_row__"
						+ rowCount
						+ "\"><td class=\"tblft1\"></td>" +
								"<td class=\"tblft1\" id=\"crop_limits_table_crop_name__"
						+ rowCount
						+ "\">"
						+ field_crop[i]
						+ "</td>"
						+ "<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"crop_limits_crop_maximum_acres__"
						+ rowCount + "\" /></td><td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"crop_limits_crop_minimum_acres__"
						+ rowCount
						+ "\" /></td></tr>";
			}

		}
	}
	$("#crop_limits_table_tbody").append(newDivHtml);

}

function addRemoveCropInsuranceTable() {
	var rowLength = $("#crop_insurance_table_tbody > tr ").length;

	var myTestArray = new Array();
	for (var i = 1; i <= rowLength; i++) {
		var isFind = false;
		for (var j = 0; j < field_crop.length; j++) {
			var columnName = $.trim(""
					+ $('#crop_insurance_table_tbody_row_crop_name__' + i)
							.html());
			if (columnName.toLowerCase() === field_crop[j].toLowerCase()) {
				isFind = true;
			}
		}
		if (!isFind) {
			myTestArray.push(i);
		}
	}
	var flag = 0;
	for (var i = 0; i < myTestArray.length; i++) {
		myTestArray[i] = parseInt(myTestArray[i]);
		myTestArray[i] = myTestArray[i] - parseInt(flag);
		var newHtml = "";
		var rowCount = $("#crop_insurance_table_tbody > tr ").length;
		var count = 1;
		for (var k = 1; k <= rowCount; k++) {
			if (k != myTestArray[i]) {
				var checked = "";
				if ($("#crop_insurance_table_crop_insurance__" + k).is(
						':checked')) {
					checked = "checked";
				}

				if (count % 2 == 0) {
					newHtml += "<tr class=\"tblgrn text-center\" id=\"crop_insurance_table_tbody_row__"
							+ count
							+ "\"> <td class=\"tblft1\" id=\"crop_insurance_table_tbody_row_crop_name__"
							+ count
							+ "\">"
							+ $(
									"#crop_insurance_table_tbody_row_crop_name__"
											+ k).html()
							+ "</td><td class=\"success infotext\"><input type=\"checkbox\" id=\"crop_insurance_table_crop_insurance__"
							+ count
							+ "\" "
							+ checked
							+ " /></td>"
							+ "<td class=\"success infotext\"><input type=\"text\" id=\"crop_insurance_table_crop_type_1__"
							+ count
							+ "\" value='"
							+ $("#crop_insurance_table_crop_type_1__" + k)
									.val()
							+ "' /></td><td class=\"success infotext\"><input type=\"text\" id=\"crop_insurance_table_crop_type_2__"
							+ count
							+ "\" value='"
							+ $("#crop_insurance_table_crop_type_2__" + k)
									.val()
							+ "' /></td><td class=\"success infotext\"><input type=\"text\" id=\"crop_insurance_table_crop_type_3__"
							+ count
							+ "\" value='"
							+ $("#crop_insurance_table_crop_type_3__" + k)
									.val()
							+ "' /></td>"
							+ "<td class=\"success cinfotext\" id=\"crop_insurance_table_crop_arces__"
							+ count
							+ "\" ></td> <td class=\"success infotext\" id=\"crop_insurance_table_crop_premium__"
							+ count + "\" ></td> </tr>";
				} else {
					newHtml += "<tr class=\"tblbclgrnd text-center\" id=\"crop_insurance_table_tbody_row__"
							+ count
							+ "\"> <td class=\"tblft1\" id=\"crop_insurance_table_tbody_row_crop_name__"
							+ count
							+ "\">"
							+ $(
									"#crop_insurance_table_tbody_row_crop_name__"
											+ k).html()
							+ "</td><td class=\"success infotext\"><input type=\"checkbox\" id=\"crop_insurance_table_crop_insurance__"
							+ count
							+ "\" "
							+ checked
							+ " /></td>"
							+ "<td class=\"success infotext\"><input type=\"text\" id=\"crop_insurance_table_crop_type_1__"
							+ count
							+ "\" value='"
							+ $("#crop_insurance_table_crop_type_1__" + k)
									.val()
							+ "' /></td><td class=\"success infotext\"><input type=\"text\" id=\"crop_insurance_table_crop_type_2__"
							+ count
							+ "\" value='"
							+ $("#crop_insurance_table_crop_type_2__" + k)
									.val()
							+ "' /></td><td class=\"success infotext\"><input type=\"text\" id=\"crop_insurance_table_crop_type_3__"
							+ count
							+ "\" value='"
							+ $("#crop_insurance_table_crop_type_3__" + k)
									.val()
							+ "' /></td>"
							+ "<td class=\"success cinfotext\" id=\"crop_insurance_table_crop_arces__"
							+ count
							+ "\" ></td> <td class=\"success infotext\" id=\"crop_insurance_table_crop_premium__"
							+ count + "\" ></td> </tr>";
				}
				count++;
			}
		}
		flag++;
		$("#crop_insurance_table_tbody_row__" + myTestArray[i]).remove;
		$("#crop_insurance_table_tbody").html("");
		$("#crop_insurance_table_tbody").html(newHtml);
	}

	rowLength = $("#crop_insurance_table_tbody > tr ").length;
	var newDivHtml = "";
	var rowCount = parseInt(rowLength);
	for (var i = 0; i < field_crop.length; i++) {
		var isAdd = true;
		for (var j = 1; j <= rowLength; j++) {
			var columnName = $.trim(""
					+ $('#crop_insurance_table_tbody_row_crop_name__' + j)
							.html());
			if (columnName.toLowerCase() === field_crop[i].toLowerCase()) {
				isAdd = false;
			}
		}
		if (isAdd) {
			rowCount++;
			if (rowCount % 2 == 0) {
				newDivHtml += "<tr class=\"tblgrn text-center\" id=\"crop_insurance_table_tbody_row__"
						+ rowCount
						+ "\"> <td class=\"tblft1\" id=\"crop_insurance_table_tbody_row_crop_name__"
						+ rowCount
						+ "\">"
						+ field_crop[i]
						+ "</td><td class=\"success infotext\"><input type=\"checkbox\" id=\"crop_insurance_table_crop_insurance__"
						+ rowCount
						+ "\" /></td>"
						+ "<td class=\"success infotext\"><input type=\"text\" id=\"crop_insurance_table_crop_type_1__"
						+ rowCount
						+ "\" /></td><td class=\"success infotext\"><input type=\"text\" id=\"crop_insurance_table_crop_type_2__"
						+ rowCount
						+ "\" /></td><td class=\"success infotext\"><input type=\"text\" id=\"crop_insurance_table_crop_type_3__"
						+ rowCount
						+ "\" /></td>"
						+ "<td class=\"success cinfotext\" id=\"crop_insurance_table_crop_arces__"
						+ rowCount
						+ "\" ></td> <td class=\"success infotext\" id=\"crop_insurance_table_crop_premium__"
						+ rowCount + "\" ></td> </tr>";
			} else {
				newDivHtml += "<tr class=\"tblbclgrnd text-center\" id=\"crop_insurance_table_tbody_row__"
						+ rowCount
						+ "\"> <td class=\"tblft1\" id=\"crop_insurance_table_tbody_row_crop_name__"
						+ rowCount
						+ "\">"
						+ field_crop[i]
						+ "</td><td class=\"success infotext\"><input type=\"checkbox\" id=\"crop_insurance_table_crop_insurance__"
						+ rowCount
						+ "\" /></td>"
						+ "<td class=\"success infotext\"><input type=\"text\" id=\"crop_insurance_table_crop_type_1__"
						+ rowCount
						+ "\" /></td><td class=\"success infotext\"><input type=\"text\" id=\"crop_insurance_table_crop_type_2__"
						+ rowCount
						+ "\" /></td><td class=\"success infotext\"><input type=\"text\" id=\"crop_insurance_table_crop_type_3__"
						+ rowCount
						+ "\" /></td>"
						+ "<td class=\"success cinfotext\" id=\"crop_insurance_table_crop_arces__"
						+ rowCount
						+ "\" ></td> <td class=\"success infotext\" id=\"crop_insurance_table_crop_premium__"
						+ rowCount + "\" ></td> </tr>";
			}

		}
	}
	$("#crop_insurance_table_tbody").append(newDivHtml);
}
function removeCropResourcesUsageTableValue() {
	var theadRowLength = $("#crop_resource_usage_thead > tr").length;
	var theadColumnLength = 1;

	if (theadRowLength != "0") {
		theadColumnLength = $("#crop_resource_usage_thead_first_row > td").length;		
	}
	theadColumnLength = parseInt(theadColumnLength);
	var myTestArray = new Array();
	for (var i = 4; i <= theadColumnLength; i++) {
		var columnName = $.trim(""+ $("#crop_resource_usage_thead_first_row_column__" + i).html());
		var isFind = false;
		// alert("columnName : "+columnName);
		$.each(resources, function(key, value) {

			if (columnName.toLowerCase() == key.toLowerCase()) {
				isFind = true;
				// alert("crop found : "+columnName);
			}

		});
		if (!isFind) {
			// alert("crop not found : "+columnName);
			myTestArray.push(i);
		}
	}

	var textNowFlag = 0;
	for (var i = 0; i < myTestArray.length; i++) {
		myTestArray[i] = parseInt(myTestArray[i]);
		myTestArray[i] = myTestArray[i] - textNowFlag;

		var deletedRowCount = $('#crop_resource_usage_thead_first_row > td').length;
		var deleteVericalRowCount = $('#crop_resource_usage_tbody > tr').length;
		if (deletedRowCount > 0) {
			var newHtml = "<tr class=\"tblhd text-center add-fieldi\" id=\"crop_resource_usage_thead_first_row\"><td id=\"crop_resource_usage_thead_first_row_column__1\" class=\"tblbrdr text-center tittle-uppercase\">Crop</td><td id=\"crop_resource_usage_thead_first_row_column__2\" class=\"text-center tittle-uppercase\">Yields<br/><span class=\"resub\">(UoM acre)</span></td><td  id=\"crop_resource_usage_thead_first_row_column__3\" class=\"text-center tittle-uppercase\">Variable production cost<br/><span class=\"resub\">($/acre*)</span><span><a id='variable_production_cost_resourse' class='help_Infromation_PopUp' onclick=\"crop_resourse_usages_click('variable_production_cost_resourse')\"><img  src='images/i-icon.png'></a></span></td>";
			var rowCount = 4;
			for (var k = 4; k <= deletedRowCount; k++) {
				if (k != (myTestArray[i])) {
					newHtml += "<td class=\"text-center\"><span class=\"tittle-uppercase\" id=\"crop_resource_usage_thead_first_row_column__"+ rowCount+ "\">"	+ $("#crop_resource_usage_thead_first_row_column__"	+ k).html()	+ "</span><br/><span class=\"resub\">(UoM acre)</span></td>";
					rowCount++;
				}
			}
			newHtml += "</tr>";
			// $("#crop_resource_usage_thead_first_row_column__" +
			// (myTestArray[i])).remove();
			$("#crop_resource_usage_thead").html("");
			$("#crop_resource_usage_thead").html(newHtml);
		}
		if (deleteVericalRowCount > 0) {
			var rowCount = 1;
			for (var k = 1; k <= deleteVericalRowCount; k++) {
				var columnCount = 1;
				var deleteCericalColumnCount = $('#crop_resource_usage_row__'+ k + ' > td').length;
				var uomValue = "";
				$.each(crop_uom, function(key, value) {
					if ($.trim(""+ $('#crop_resource_usage_crop__'+ k).html()).toLowerCase() == key.toLowerCase()) {
						uomValue = crop_uom[key];
					}
				});
				var productionCost = "";
				$.each(crop_total_production_cost, function(key, value) {
					if ($.trim(""+ $('#crop_resource_usage_crop__'+ k).html()).toLowerCase() == key.toLowerCase()) {
						productionCost = crop_total_production_cost[key];
					}
				});
				var newHtml = "<td class=\"tblft1\" id=\"crop_resource_usage_crop__"+ rowCount+ "\">"+ $("#crop_resource_usage_crop__" + k).html()+ "</td><td id=\"crop_resource_usage_row_uomValue__"	+ rowCount	+ "\"  class=\"success infotext tittle-uppercase\">"	+ uomValue+ "</td>"	+ "<td id=\"crop_resource_usage_row_productionCost__"	+ rowCount	+ "\" class=\"success infotext\">$ "+ productionCost+ " <img src=\"images/data.png\" class=\"orange_data_help\"></td>";
				deleteCericalColumnCount = deleteCericalColumnCount - 3;
				for (var j = 1; j <= deleteCericalColumnCount; j++) {
					if (j != (myTestArray[i] - 3)) {
						newHtml += "<td class=\"success infotext\"><input onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"crop_resource_usage__"+ k+ "__resource__"+ columnCount+ "\" type=\"text\" value='"+ $("#crop_resource_usage__" + k+ "__resource__" + j).val()+ "' onkeypress=\"return isValidNumberValueForWithOutDot(event)\" /></td>";
						columnCount++;
					}

				}
				// $("#crop_resource_usage__"+k+"__resource__" +
				// myTestArray[i]).remove();
				// alert("Remove id is
				// field_choice_crop_tbody_row_others_column__"+k+"__column__" +
				// (myTestArray[i]-1));
				$("#crop_resource_usage_row__" + k).html("");
				$("#crop_resource_usage_row__" + k).html(newHtml);
				rowCount++;
			}
		}
		// field_crop.splice(myTestArray[i], 1);
		textNowFlag++;
	}
	removeCropResourcesUsageTableBodyValue();
}
function removeCropResourcesUsageTableBodyValue() {
	var rowLength = $("#crop_resource_usage_tbody > tr ").length;
	var myTestArray = new Array();
	for (var i = 1; i <= rowLength; i++) {
		var isFind = false;
		for (var j = 0; j < field_crop.length; j++) {
			var columnName = $.trim(""
					+ $('#crop_resource_usage_crop__' + i).html());
			if (columnName.toLowerCase() === field_crop[j].toLowerCase()) {
				isFind = true;
			}
		}
		if (!isFind) {
			myTestArray.push(i);
		}
	}
	var flag = 0;
	var columnCount = $("#crop_resource_usage_thead_first_row > td ").length;
	columnCount = columnCount - 3;
	for (var i = 0; i < myTestArray.length; i++) {
		myTestArray[i] = parseInt(myTestArray[i]);
		myTestArray[i] = myTestArray[i] - parseInt(flag);
		var newHtml = "";
		var rowCount = $("#crop_resource_usage_tbody > tr ").length;
		var count = 1;
		for (var k = 1; k <= rowCount; k++) {
			if (k != myTestArray[i]) {
				var uomValue = "";
				$.each(crop_uom, function(key, value) {
					if ($.trim(""+ $('#crop_resource_usage_crop__'+ k).html()).toLowerCase() == key.toLowerCase()) {
						uomValue = crop_uom[key];
					}
				});
				var productionCost = "";
				$.each(crop_total_production_cost, function(key, value) {
					if ($.trim(	""	+ $('#crop_resource_usage_crop__'+ k).html()).toLowerCase() == key.toLowerCase()) {
						productionCost = crop_total_production_cost[key];
					}
				});
				newHtml += "<tr class=\"tblgrn text-center\" id=\"crop_resource_usage_row__"+ count	+ "\"><td class=\"tblft1\" id=\"crop_resource_usage_crop__"	+ count	+ "\">"
						+ $("#crop_resource_usage_crop__" + k).html()+ "</td><td id=\"crop_resource_usage_row_uomValue__"	+ count	+ "\" class=\"success infotext tittle-uppercase\">"	+ uomValue+ "</td>"
						+ "<td id=\"crop_resource_usage_row_productionCost__"	+ count	+ "\" class=\"success infotext\">$ "+ productionCost+ " <img src=\"images/data.png\" class=\"orange_data_help\"></td>";

				for (var j = 1; j <= columnCount; j++) {
					newHtml += "<td class=\"success infotext\"><input onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"crop_resource_usage__"+ count+ "__resource__"+ j	+ "\" type=\"text\" value='"+ $("#crop_resource_usage__" + k+ "__resource__" + j).val()	+ "' onkeypress=\"return isValidNumberValueForWithOutDot(event)\" /></td>";
				}
				newHtml += "</tr>";
				count++;
			}
		}
		flag++;
		$("#crop_resource_usage_row__" + myTestArray[i]).remove;
		$("#crop_resource_usage_tbody").html("");
		$("#crop_resource_usage_tbody").html(newHtml);
	}
}
function addCropResourcesUsageTableValue() {
	var newTheadHtml = "";
	var newTbodyHtml = "";
	var theadRowLength = $("#crop_resource_usage_thead > tr").length;
	var theadColumnLength = 1;

	if (theadRowLength != "0") {
		theadColumnLength = $("#crop_resource_usage_thead_first_row > td").length;
	}
	theadColumnLength = parseInt(theadColumnLength);
	var columnCount = theadColumnLength;
	if (theadRowLength == "0") {
		newTheadHtml += "<tr class=\"tblhd text-center add-fieldi\" id=\"crop_resource_usage_thead_first_row\"><td id=\"crop_resource_usage_thead_first_row_column__1\" class=\"tblbrdr text-center tittle-uppercase\">Crop</td><td id=\"crop_resource_usage_thead_first_row_column__2\" class=\"text-center tittle-uppercase\">Yields<br/><span class=\"resub\">(UoM acre)</span></td><td  id=\"crop_resource_usage_thead_first_row_column__3\" class=\"text-center tittle-uppercase\">Variable production cost<br/><span class=\"resub\">($/acre*)</span><span><a id='variable_production_cost_resourse' class='help_Infromation_PopUp' onclick=\"crop_resourse_usages_click('variable_production_cost_resourse')\"><img  src='images/i-icon.png'></a></span></td>";
		columnCount = 3;
	}
	$.each(resources,function(key, value) {
						var flag = true;
						// 09-03-2015 change
						var resourceName = "land";
						/*Modified by Bhagvan Singh to hide capital in Crop resource usage on 06-04-2015
						Start*/
						var resourceNameCap = "capital";
						/* && key.toLowerCase() != resourceNameCap
						 * End*/
						// 09-03-2015 change
						for (var k = 1; k <= theadColumnLength; k++) {
							var columnName = $.trim(""+ $('#crop_resource_usage_thead_first_row_column__'+ k).html());
							if (columnName.toLowerCase() == key.toLowerCase()) {
								flag = false;
							}
						}
						if (flag) {
							// 09-03-2015 change
							if (key.toLowerCase() != resourceName && key.toLowerCase() != resourceNameCap) {
								columnCount++;
								newTheadHtml += "<td class=\"text-center\"><span class=\"tittle-uppercase\" id=\"crop_resource_usage_thead_first_row_column__"	+ columnCount+ "\">"+ key+ "</span><br/><span class=\"resub\">("+ resourcesUOM[key]+ ")</span></td>";
							}
						}
						// 09-03-2015 change

					});

	if (theadRowLength == "0") {
		newTheadHtml += "</tr>";
		$("#crop_resource_usage_thead").append(newTheadHtml);
	} else {
		$("#crop_resource_usage_thead_first_row").append(newTheadHtml);
	}

	columnCount = columnCount - 2;

	var tbodyRowLength = $("#crop_resource_usage_tbody > tr").length;
	var rowCount = parseInt(tbodyRowLength);
	var nowRowCount=0;
	for (var i = 0; i < field_crop.length; i++) {
		var flag = true;
		for (var k = 1; k <= tbodyRowLength; k++) {
			var rowName = $.trim(""	+ $('#crop_resource_usage_crop__' + k).html());
			if (rowName.toLowerCase() === field_crop[i].toLowerCase()) {
				flag = false;
				nowRowCount=k;
			}
		}
		var uomValue = "";
		$.each(crop_uom, function(key, value) {
			if (field_crop[i].toLowerCase() == key.toLowerCase()) {
				uomValue = crop_uom[key];
//				 alert("uomValue : "+uomValue);
			}
		});
	
		var productionCost = "";
		$.each(crop_total_production_cost, function(key, value) {
			if (field_crop[i].toLowerCase() == key.toLowerCase()) {
				productionCost = crop_total_production_cost[key];
			}
		});
		if (flag) 
		{
			rowCount++;
			newTbodyHtml += "<tr class=\"tblgrn text-center\" id=\"crop_resource_usage_row__"+ rowCount	+ "\"><td class=\"tblft1\" id=\"crop_resource_usage_crop__"	+ rowCount	+ "\">"	+ field_crop[i]	+ "</td><td id=\"crop_resource_usage_row_uomValue__"	+ rowCount	+ "\" class=\"success infotext tittle-uppercase\">"	+ uomValue+ "</td>"+ "<td id=\"crop_resource_usage_row_productionCost__"	+ rowCount	+ "\" class=\"success infotext\">$ "+ productionCost + " <img src=\"images/data.png\" class=\"orange_data_help\"></td>";
			for (var j = 1; j < columnCount; j++) {
				newTbodyHtml += "<td class=\"success infotext\"><input onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"crop_resource_usage__"+ rowCount+ "__resource__"+ j	+ "\" type=\"text\" onkeypress=\"return isValidNumberValueForWithOutDot(event)\" /></td>";
			}
			newTbodyHtml += "</tr>";

		}
		else
			{
			var nowNewHtml="$ "+ productionCost + " <img src=\"images/data.png\" class=\"orange_data_help\">";
			$("#crop_resource_usage_row_uomValue__"+nowRowCount).html("");
			$("#crop_resource_usage_row_uomValue__"+nowRowCount).html(uomValue);
			$("#crop_resource_usage_row_productionCost__"+nowRowCount).html("");
			$("#crop_resource_usage_row_productionCost__"+nowRowCount).html(nowNewHtml);
			}
	}
	$("#crop_resource_usage_tbody").append(newTbodyHtml);
}
function balanceCropResourcesUsageTableValue() {
	var headColumnLength = $("#crop_resource_usage_thead_first_row > td").length;
	var bodyRowLength = $("#crop_resource_usage_tbody > tr").length;
	// alert("headColumnLength : "+headColumnLength);
	// alert("bodyRowLength : "+bodyRowLength);
	headColumnLength = parseInt(headColumnLength);
	bodyRowLength = parseInt(bodyRowLength);
	for (var i = 1; i <= bodyRowLength; i++) {
		var lengthOfBodyRow = $("#crop_resource_usage_row__" + i + " > td").length;
		lengthOfBodyRow = parseInt(lengthOfBodyRow);
		if (lengthOfBodyRow != headColumnLength) {
			// alert("lengthOfBodyRow : "+lengthOfBodyRow);
			// alert("headColumnLength : "+headColumnLength);
			var count = lengthOfBodyRow-2;
			var newHtml = "";
			for (var j = lengthOfBodyRow; j < headColumnLength; j++) {
				newHtml += "<td class=\"success infotext\"><input onchange=\"addCommaSignWithOutDollarDot(this)\" id=\"crop_resource_usage__"+ i + "__resource__" + count + "\" type=\"text\" onkeypress=\"return isValidNumberValueForWithOutDot(event)\" /></td>";
				count++;
			}
			$("#crop_resource_usage_row__" + i).append(newHtml);
		}

	}
}
function saveAllFarmInformation() {
	
	// this method called to check all screen for validation when saved farm
	if(!validationCallForLeftSlide())
		{
		return false;
		}
	
	
	/*update by Bhagvan Singh on 14-04-2015 for minimum is not mendatary
	start*/
	for(var i=1;i<=$("#crop_limits_table_tbody >tr").length;i++)
	{
		if($("#crop_limits_crop_minimum_acres__"+i).val().replace(/,/g, '')!="" && $("#crop_limits_crop_maximum_acres__"+i).val().replace(/,/g, '')!="")
	     {
			if(Number($("#crop_limits_crop_minimum_acres__"+i).val().replace(/,/g, '')) >= Number($("#crop_limits_crop_maximum_acres__"+i).val().replace(/,/g, '')))
			{
				customAlerts('Maximum Acres amount should be greater than Minimum Acres amount for "'+$("#crop_limits_table_crop_name__"+i).html()+'" crop name'+" crop", "error", 0);
				focusForValidation("crop_limits_crop_maximum_acres__"+i);
				focusForValidation("crop_limits_crop_minimum_acres__"+i);
				return false;
			}
			
			
	     }
		var minimum_acres=Number($("#crop_limits_crop_minimum_acres__"+i).val().replace(/,/g, ''));
		var acres=Number($("#forward_sales_information_tbody_row_acres__"+i).val().replace(/,/g, ''));
		if(minimum_acres <acres)
			{
			 customAlerts('Minimum Acres amount should be greater than Acres entered in forward sales for crop "'+$("#crop_limits_table_crop_name__"+i).html()+'"', "error", 0);
			 focusForValidation("crop_limits_crop_minimum_acres__"+i);
			 return false;
			}
		/*end*/
		/*if($("#crop_limits_crop_maximum_acres__"+i).val()=="")
	   {
			customAlerts("Please enter maximum acres", "error", 0);
			return false;
	   }
		else
			{
		  if(Number($("#crop_limits_crop_minimum_acres__"+i).val()) >= Number($("#crop_limits_crop_maximum_acres__"+i).val()))
			{
			customAlerts("Minimum Acres amount should be greater than Maximum Acres for :"+$("#crop_limits_table_crop_name__"+i).html()+"", "error", 0);
			return false;
			}
		}}*/
	   
	}
	
	for (var i = 1; i <= $("#add_group_table_tbody tr").length; i++) {
		var min_group = 0;
		var max_group = 0;
		var min_group_sum = 0;
		var max_group_sum = 0;
		if ($("#group_crop_minimum_acres__" + i).val() != "" && $("#group_crop_maximum_acres__" + i).val() != "") {
			min_group = Number(removeAllCommas($("#group_crop_minimum_acres__" + i).val()));
			max_group = Number(removeAllCommas($("#group_crop_maximum_acres__" + i).val()));
			min_group_sum += min_group;
			max_group_sum += min_group;
			if (max_group < min_group) {
				customAlerts('Maximum Acres amount should be greater than Minimum Acres for "'
								+ $("#group_table_group_name_" + i).html().trim() + '" group', "error", 0);
				focusForValidation("group_crop_maximum_acres__"+i);
				focusForValidation("group_crop_minimum_acres__"+i);
				return false;
			}
		}
	}

	for (var i = 1; i <= $("#add_group_table_tbody tr").length; i++) {
		var group_name_for_validation = $("#group_table_group_name_" + i).html().trim();
		var min_crop = 0;
		var min_group = 0;
		var max_group = 0;
		var max_crop = 0;
		var crop_name = "";
		if ($("#group_crop_minimum_acres__" + i).val() != "") {
			min_group = Number(removeAllCommas($("#group_crop_minimum_acres__" + i).val()));
		}
		if ($("#group_crop_maximum_acres__" + i).val() != "") {
			max_group = Number(removeAllCommas($("#group_crop_maximum_acres__" + i).val()));
		}
		for (var j = 0; j < groupArray[group_name_for_validation].length; j++) {
			crop_name += groupArray[group_name_for_validation][j] + ", ";
			for (var k = 1; k <= $("#crop_limits_table_tbody tr").length; k++) {
				if ($("#crop_limits_table_crop_name__" + k).html().trim() == groupArray[group_name_for_validation][j]) {
					max_crop += Number(removeAllCommas($("#crop_limits_crop_maximum_acres__" + k).val()));
					min_crop += Number(removeAllCommas($("#crop_limits_crop_minimum_acres__" + k).val()));
				}
			}
		}
		crop_name = crop_name.substring(0, crop_name.length - 2);
		if (max_group < min_crop && max_group!=0) {
			customAlerts('Maximum Acres amount for group "' + group_name_for_validation + '" should not be less than total Acres entered for crop "' + crop_name + '"', "error", 0);
			focusForValidation("group_crop_maximum_acres__"+i);
			return false;
		}
		if (min_group < min_crop && min_group!=0) {
			customAlerts('Minimum Acres amount for group "' + group_name_for_validation + '" should not be less than total Acres entered for crop "'+ crop_name + '"', "error", 0);
			focusForValidation("group_crop_minimum_acres__"+i);
			return false;
		}
		
	}
		/*created by Bhagvan Singh on 06-05-2015 For focus on validated text field
		start*/		
		if (!totalLandValidationForCropLimit()) {
			return false;
		}
		/*end*/
//	end
	var irrigate_val = "no";
	var evaluate_forward_sales_val = false;
	var evaluate_crop_insurance_val = false;
	var evaluate_storage_sales_val = false;
	
	/*08-03-2015
	Modified by Harshit Gupta
	Start
	*/
	var crop_str=new Array();
	// end
	
	var crop_information_detail=new Array();	
	var option_crop_info_array=new Array();
	var optional_planting_date_array=new Array();
	var cal_var_cost_crops=new Array();
	var manage_resource_tbody_array=new Array();
	var crop_resource_usage_tbody_array=new Array();
	var crop_limits_table_tbody_array=new Array();
	var forward_sales_information_tbody_array=new Array();
	var plan_by_field_tbody_array=new Array();
	var field_choice_crop_tbody_row_array=new Array();
	var crop_resources_usages_difference_tbody_array=new Array();
	var crop_group_array=new Array();
	
	
	if ($("input[name='irrigate']:checked").length > 0) {
		irrigate_val = $.trim("" + $('input[name=irrigate]:checked').val());
	}
	if ($("input[name='evaluate_forward_sales']:checked").length > 0) {
		evaluate_forward_sales_val = $.trim(""
				+ $('input[name=evaluate_forward_sales]:checked').val());
		
	}if ($("input[name='evaluate_storage_sales']:checked").length > 0) {
		evaluate_storage_sales_val = $.trim(""
				+ $('input[name=evaluate_storage_sales]:checked').val());
		
	}
	if ($("input[name='evaluate_crop_insurance']:checked").length > 0) {
		evaluate_crop_insurance_val = $.trim(""
				+ $('input[name=evaluate_crop_insurance]:checked').val());
		
	}
	
	
	
	/*08-03-2015
	Modified by Harshit Gupta
	Start
	*/
	$("input[name='vegitable_crop[]']").each(function() {
		crop_str.push($.trim("" + $(this).val()+"#-#-#"+$(this).prop("checked")+"#-#-#vegitable"));
		});
	$("input[name='field_crop[]']").each(function() {
		crop_str.push($.trim("" + $(this).val()+"#-#-#"+$(this).prop("checked")+"#-#-#field"));
	});
	// end
	
	var rowLength=$("#crop_information_tbody > tr").length;
	for(var i=1;i<=rowLength;i++)
		{
		/*var crop_info_yields_expected=$("#crop_info_yields_expected__" + i).val().trim();
		var crop_info_yields_min=$("#crop_info_yields_min__" + i).val().trim();
		var crop_info_yields_max=$("#crop_info_yields_max__" + i).val().trim();
		var crop_info_price_expected=$("#crop_info_price_expected__" + i).val().trim();
		var crop_info_price_min=$("#crop_info_price_min__" + i).val().trim();
		var crop_info_price_max=$("#crop_info_price_max__" + i).val().trim();
		var calulated_cost_of_production=$("#calulated_cost_of_production__"+ $("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).val().replace('$', '').trim();
		
		*/
		crop_information_detail.push($.trim("" + $('#crop_info_details_field_crop_name__'+i).html())+"#-#-#"+
		$("#crop_selection_UOM__" + i + " option:selected").val()+"#-#-#"+
		$("#crop_info_yields_expected__" + i).val().replace(/,/g, '')+"#-#-#"+
		$("#crop_info_yields_min__" + i).val().replace(/,/g, '')+"#-#-#"+
		$("#crop_info_yields_max__" + i).val().replace(/,/g, '')+"#-#-#"+
		$("#crop_info_price_expected__" + i).val().replace('$', '').replace(/,/g, '')+"#-#-#"+
		$("#crop_info_price_min__" + i).val().replace('$', '').replace(/,/g, '')+"#-#-#"+
		$("#crop_info_price_max__" + i).val().replace('$', '').replace(/,/g, '')+"#-#-#"+
		$("#calulated_cost_of_production__"+ $("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).val().replace('$', '').replace(/,/g, ''));
		var production_cost_table_tbody_length=$("#production_cost_table_tbody__"+$("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')+"> tr").length;
		//alert("length :"+production_cost_table_tbody_length);
		//alert("go"+production_cost_table_tbody_length);
		if(production_cost_table_tbody_length > 0)
		{
/*Modified by Bhagvan Singh on 11-04-2015
Start*/
			for(var j=1;j<=production_cost_table_tbody_length-2;j++)
				{

		if($("#production_cost_calculate_value__"+j+"_"+$("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).text()!="")
			{
			//alert("hello2"+$("#crop_info_details_field_crop_name__" + i).html()+$("#production_cost_calculate_value__"+j+"_"+$("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).html());
			cal_var_cost_crops.push($("#crop_info_details_field_crop_name__" + i).html()+"#-#-#"+$("#production_cost_component_name__"+j+"_"+$("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).html()+"#-#-#"+
					$("#production_cost_value_1_row__"+j+"_"+$("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).val().replace("$","").replace(/,/g, '')+"#-#-#"+
					$("#production_cost_value_2_row__"+j+"_"+$("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).val().replace("$","").replace(/,/g, '')+"#-#-#"+
					$("#production_cost_calculate_value__"+j+"_"+$("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).html().replace("$","").replace(/,/g, ''));
			}
		else if($("#production_cost_calculate_value__"+j+"_"+$("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).text() == "")
			{
				//alert("hello2"+$("#crop_info_details_field_crop_name__" + i).html()+$("#production_cost_calculate_value__"+j+"_"+$("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).html());
				cal_var_cost_crops.push($("#crop_info_details_field_crop_name__" + i).html()
										+"#-#-#"+$("#production_cost_component_name__"+j+"_"+$("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).html()
										+"#-#-#"+0+"#-#-#"+0+"#-#-#"+0);
				}
				
	/*End*/			
				}
		
		}
		
		}
		var option_crop_info_length=$("#optional_crop_info_tbody >tr").length;
//		alert("option_crop_info_length : "+option_crop_info_length);
		for(var i=1;i<=option_crop_info_length;i++)
			{
			var option_crop_name=$("#optional_crop_info_field_crop_name__"+i).html();
			var option_crop_irregation=false;
			var option_crop_conservation_practice=false;
			var option_crop_hi_risk_crop=false;
			if(document.getElementById("optional_crop_info_irrigated__"+i).checked)
				{
				 option_crop_irregation=true;
//				 alert("option_crop_irregation : "+option_crop_irregation);
				}
				if (document.getElementById("optional_crop_info_conservation_practice__"+i).checked) 
				{
					
					option_crop_conservation_practice=true;
//					alert("option_crop_conservation_practice : "+option_crop_conservation_practice);
				}
				if (document.getElementById("optional_crop_info_hi_risk_crop__"+i).checked) 
				{
					option_crop_hi_risk_crop=true;
//					alert("option_crop_hi_risk_crop : "+option_crop_hi_risk_crop);
				}
				option_crop_info_array.push($.trim("" +option_crop_name+"#-#-#"+option_crop_irregation+"#-#-#"+option_crop_conservation_practice+"#-#-#"+option_crop_hi_risk_crop));
			}
//		alert("option_crop_info_array : "+option_crop_info_array);
		var optional_planting_date_length=$("#optional_planting_date >tr").length;
		//alert("length is"+optional_planting_date_length);
		for (var i=1;i<=optional_planting_date_length;i++) 
		{
			var optional_planting_date_preferred_planting_date="";
			var optional_planting_date_early_planting_date="00/00/0000 ";
			var optional_planting_date_late_planting_date="";
			var optional_planting_date_length_of_session_days="";
			var optional_planting_date_field_crop_name=$("#optional_planting_date_field_crop_name__"+i).html();
			if($("#optional_planting_date_preferred_planting_date__"+i).val()=="")
		    optional_planting_date_preferred_planting_date="00/00/0000";	
		   else
	    	optional_planting_date_preferred_planting_date=$("#optional_planting_date_preferred_planting_date__"+i).val();
			
			if($("#optional_planting_date_early_planting_date__"+i).val()=="")
				optional_planting_date_early_planting_date="00/00/0000";	
			   else
				   optional_planting_date_early_planting_date=$("#optional_planting_date_early_planting_date__"+i).val();
				
			if($("#optional_planting_date_late_planting_date__"+i).val()=="")
				optional_planting_date_late_planting_date="00/00/0000";	
			   else
				   optional_planting_date_late_planting_date=$("#optional_planting_date_late_planting_date__"+i).val();
				
			if($("#optional_planting_date_length_of_session_days__"+i).val()=="")
				optional_planting_date_length_of_session_days="00";	
			   else
				optional_planting_date_length_of_session_days=$("#optional_planting_date_length_of_session_days__"+i).val();
				
			optional_planting_date_array.push($.trim("" +optional_planting_date_field_crop_name+"#-#-#"+optional_planting_date_preferred_planting_date+"#-#-#"+optional_planting_date_early_planting_date+"#-#-#"+optional_planting_date_late_planting_date+"#-#-#"+optional_planting_date_length_of_session_days));
		}
			
	var manage_resource_tbody_length=$("#manage_resource_tbody >tr").length;
	manage_resource_tbody_array.push("Land"+"#-#-#"+"Acres"+"#-#-#"+$("#total_land_available").html().replace(/,/g, ''));
	manage_resource_tbody_array.push("Capital"+"#-#-#"+"$/acre"+"#-#-#"+$("#total_capital_available").val().replace('$', '').replace(/,/g, ''));
	for(var i=3;i<=manage_resource_tbody_length;i++)
		{
		
		  if($("#manage_resource_row_checkbox__"+i).prop('checked'))
			  {
			  
			  manage_resource_tbody_array.push($("#resource_manage_name__"+(i-2)).html()+"#-#-#"+$("#resource_manage_uom__"+(i-2)).html()+"#-#-#"+$("#resource_manage__"+(i-2)).val().replace(/,/g, ''));
			  }
		  }
	
	for(var i=1;i<=$("#crop_resource_usage_tbody >tr").length;i++)
	{
	
	for(var j=4;j<=$("#crop_resource_usage_row__"+i+" >td").length;j++)
		{
		if($("#crop_resource_usage__"+i+"__resource__"+(j-3)).val().replace(/,/g, '')!="")
			{
		var a=$("#crop_resource_usage_crop__"+i).html()+"#-#-#"+$("#crop_resource_usage_thead_first_row >td").length+"#-#-#";
		a+=$("#crop_resource_usage_thead_first_row_column__"+j).html()+"#-#-#"+$("#crop_resource_usage__"+i+"__resource__"+(j-3)).val().replace(/,/g, '')+"#-#-#";
		crop_resource_usage_tbody_array.push(a);
		}
		}
	
    }
	
	for(var i=1;i<=$("#crop_limits_table_tbody >tr").length;i++)
	{
	   var minimum_acrs="0";
	   var maximum_acrs="0";
		if($("#crop_limits_crop_minimum_acres__"+i).val().trim().replace(/,/g, '')!="")
	     {
			minimum_acrs=$("#crop_limits_crop_minimum_acres__"+i).val().replace(/,/g, '');
	     }
	   if($("#crop_limits_crop_maximum_acres__"+i).val().trim().replace(/,/g, '')!="")
	   {
		   maximum_acrs=$("#crop_limits_crop_maximum_acres__"+i).val().replace(/,/g, '');
	   }
		crop_limits_table_tbody_array.push($("#crop_limits_table_crop_name__"+i).html()+"#-#-#"+minimum_acrs+"#-#-#"+maximum_acrs);
		}
	
	
	for(var i=1;i<=$("#forward_sales_information_tbody >tr").length;i++)
	{
	    var firm_checkbox="false";
        /*if($("#forward_sales_information_tbody_row_firm__"+i).prop('checked'))
		{ */ 
        	if ($("#forward_sales_information_tbody_row_firm__"+i).is(':checked')) 
        	{
        	firm_checkbox="true";
        	
			}
        	var proposed_checkbox="false";
            	if ($("#forward_sales_information_tbody_row_proposed__"+i).is(':checked')) 
            	{
            		proposed_checkbox="true";
            	
    			}
            	
        	/*update By Bhagvan Singh on 10-04-2015
        	start*/
        	var price="0";
        	var quantity="0";
        	var acres="0";
        	var upperLimit="0";
        	var contect_identifire="0";
        	if($("#forward_sales_information_tbody_row_crop_price__"+i).val().replace('$', '').replace(/,/g, '')!="")
        		{
               price=$("#forward_sales_information_tbody_row_crop_price__"+i).val().replace('$', '').replace(/,/g, '');
        		}
        	if($("#forward_sales_information_tbody_row_quantity__"+i).val().replace(/,/g, '')!="")
        		{
               quantity=$("#forward_sales_information_tbody_row_quantity__"+i).val().replace(/,/g, '');
        		}
        	if($("#forward_sales_information_tbody_row_acres__"+i).val().replace(/,/g, '')!="")
        		{
        acres=  $("#forward_sales_information_tbody_row_acres__"+i).val().replace(/,/g, '');
        		}
        	if($("#forward_sales_information_tbody_row_upperLimit__"+i).val().replace("%","")!="")
        		{
        upperLimit=$("#forward_sales_information_tbody_row_upperLimit__"+i).val().replace("%","");
        		}
        	/*
        	 * Commented as per client requirement
        	 * if($("#contact_identifier").val()!="")
        		{
        contect_identifire=$("#contact_identifier").val();
        		}*/
//        	end
		forward_sales_information_tbody_array.push($("#forward_sales_information_tbody_row_crop_name__"+i).html()+"#-#-#"+price+"#-#-#"+quantity+"#-#-#"+firm_checkbox+"#-#-#"+acres+"#-#-#"+upperLimit+"#-#-#"+contect_identifire+"#-#-#"+proposed_checkbox);
		}
	
	
	for(var i=1;i<=$("#plan-by-field-tbody >tr").length-1;i++)
	{
	    var follow="false";
	    var divide="false";
	    var irrigate="false";
	   //alert("go"+"");
	                
	  // alert("go11"+$("input[name='field-follow__"+i+"']:checked").val());
	   //alert("go21"+$("#field-follow__"+i).html());
	  // alert("go31"+$("#field-follow__"+i).is(':checked'));
	    if ($("input[name='field-follow__"+i+"']:checked").val()) 
    	{
    		follow="true";
    	
		}
    	if ($("input[name='field-divide__"+i+"']:checked").val()) 
    	{
    		divide="true";
    	
		}
    	if ($("input[name='field-irrigate__"+i+"']:checked").val()) 
    	{
    		irrigate="true";
    	
		}
        	plan_by_field_tbody_array.push($("#row-field-name__"+i).html()+"#-#-#"+$("#row-field-size__"+i).html().replace(/,/g, '')+"#-#-#"+$("#selected_last_crop____"+i).val()+"#-#-#"+follow+"#-#-#"+divide+"#-#-#"+irrigate);
		}
	
	
	for(var i=1;i<=$("#field_choice_crop_tbody >tr").length;i++)
	{
	
	for(var j=1;j<=$("#field_choice_crop_tbody_row__"+i+" >td").length-1;j++)
		{
		var a=$("#field_choice_crop_tbody_row_first_column__"+i).html()+"#-#-#"+$("#field_choice_crop_thead_row_column__"+(j+1)).html()+"#-#-#";
		var check_status="false";
		if($("#field_choice_crop_selected_row__"+i+"__column__"+j).is(':checked'))
			{
			check_status="true";
			}
		a+=check_status+"#-#-#";
		field_choice_crop_tbody_row_array.push(a);
		}
	}
	
	for(var i=1;i<=$("#crop_resources_usages_difference_tbody >tr").length;i++)
	{
	if($("#resources_usages_difference_row__"+i+"_resource_override").val()!="")
			{
		crop_resources_usages_difference_tbody_array.push($("#resources_usages_difference_row__"+i+"_resource_name").html()+"#-#-#"+$("#resources_usages_difference_row__"+i+"_resource_override").val());
			}
		}
	
	
	var field_select_drop_down=$("#field_select_drop_down").val();
	var crop_select_drop_down=$("#crop_select_drop_down").val();
	var field_difference_str="";
	if(field_select_drop_down!=0 && crop_select_drop_down!=0)
		{
		var field_difference_exp=0;
		var field_difference_min=0;
		var field_difference_max=0;
		var field_variable_production_cost=0;
		if($("#field_difference_exp").val()!="")
	     {
		field_difference_exp=$("#field_difference_exp").val().replace('$', '').replace(/,/g, '');
		 }
		if($("#field_difference_min").val()!="")
		{
			field_difference_min=$("#field_difference_min").val().replace('$', '').replace(/,/g, '');
		}
		if($("#field_difference_max").val()!="")
		{
			field_difference_max=$("#field_difference_max").val().replace('$', '').replace(/,/g, '');
		}
		if($("#resources_usages_production_cost_resource_override").val()!="")
		{
			field_variable_production_cost=$("#resources_usages_production_cost_resource_override").val().replace('$', '').replace(/,/g, '');
		}
		field_difference_str=field_select_drop_down+"#-#-#"+crop_select_drop_down+"#-#-#"+field_difference_exp+"#-#-#"+field_difference_min+"#-#-#"+field_difference_max+"#-#-#"+field_variable_production_cost;
			
		}
	
	
	//alert("field_difference_array"+field_difference_str);
	
	for(var j=1;j<=$("#add_group_table_tbody tr").length;j++)
	{
	
         crop_groupName=$("#group_table_group_name_"+j).html();
         var maximum=$("#group_crop_maximum_acres__"+j).val();
         var minimum=$("#group_crop_minimum_acres__"+j).val();
         var cropName="";
         var cropNumber=0;
         for(var i=0;i<groupArray[crop_groupName].length;i++){
	     // alert(groupArray[crop_groupName][i]);
        	 cropName += groupArray[crop_groupName][i];
 			if (i < groupArray[crop_groupName].length - 1) {
 				cropName += "#-#-#";
 			}
        	cropNumber++;
        }
		/**
		 * @changed - abhishek
		 * @date - 02-12-2015
		 */
		//crop_group_array.push(crop_groupName+"#-#-#"+maximum+"#-#-#"+minimum+"#-#-#"+cropNumber+"#-#-#"+cropName);
        crop_group_array.push(crop_groupName+"#-#-#"+minimum+"#-#-#"+maximum+"#-#-#"+cropNumber+"#-#-#"+cropName);
	}
	
	
	/* var crop_groupName="";
	for(var j=1;j<=$("#add_group_table_tbody tr").length;j++)
	{
	
         crop_groupName=$("#group_table_group_name_"+j).html();
         var tempName = crop_groupName;
         var maximum=$("#group_crop_maximum_acres__"+j).val();
         var minimum=$("#group_crop_minimum_acres__"+j).val();
        
//         alert("maximum : "+maximum+"minimum : "+minimum);
         var cropName="";
         for(var i=0;i < groupArray[crop_groupName].length;i++){
        	 crop_group_array+=crop_group_array.push(groupArray[crop_groupName][i]);
        	 cropName+=groupArray[crop_groupName][i]+"#-#-#";
        }
         crop_group_array.push(crop_groupName+"#-#-#"+maximum+"#-#-#"+minimum+"#-#-#"+cropName);
	}*/
	for(var i=0;i<crop_group_array.length;i++)
	{
//	alert("hello :"+crop_group_array[i]);
	}
	/*alert("crop_information_detail"+crop_information_detail);
	alert("option_crop_info_array"+option_crop_info_array);
	alert("optional_planting_date_array"+optional_planting_date_array);
	alert("cal_var_cost_crops"+cal_var_cost_crops);
	alert("manage_resource_tbody_array"+manage_resource_tbody_array);
	alert("crop_resource_usage_tbody_array"+crop_resource_usage_tbody_array);
	alert("crop_limits_table_tbody_array"+crop_limits_table_tbody_array);
	alert("forward_sales_information_tbody_array"+forward_sales_information_tbody_array);
	alert("plan_by_field_tbody_array"+plan_by_field_tbody_array);
	alert("field_choice_crop_tbody_row_array"+field_choice_crop_tbody_row_array);
	alert("crop_resources_usages_difference_tbody_array"+crop_resources_usages_difference_tbody_array);*/
	
	// chnage by rohit on 28-04-15
	var flagforFieldAcres = $.trim("" + $('input[name=plan_by_farm]:checked').val());
	if(flagforFieldAcres == "acres")
		{
		var acresValue = $.trim("" + $('#acres_value').val());
		acresValue = removeAllCommas(acresValue);
		total_land="";
		total_land=acresValue;
		}
	else if (flagforFieldAcres == "fields")
		{
		total_land=total_land;
		}
//	customAlerts("Please wait your strategy is being prepared", "success", 0);
	$.ajax({
		url : 'agriculture/farmController/saveFarmInformation',
		type : 'POST',
		/*async : false,*/
		beforeSend: showLoadingImage(),
		data : ({
			farmId : farmId,
			irrigate_val : irrigate_val,
			evaluate_forward_sales_val : evaluate_forward_sales_val,
			evaluate_storage_sales_val : evaluate_storage_sales_val,
			evaluate_crop_insurance_val : evaluate_crop_insurance_val,
			strategy : strategy,
			total_land : total_land,
			crop_str : crop_str,
			crop_information_detail : crop_information_detail,
			cal_var_cost_crops : cal_var_cost_crops,
			manage_resource_tbody_array : manage_resource_tbody_array,
			option_crop_info_array : option_crop_info_array,
			optional_planting_date_array : optional_planting_date_array,
			crop_resource_usage_tbody_array : crop_resource_usage_tbody_array,
			crop_limits_table_tbody_array : crop_limits_table_tbody_array,
			forward_sales_information_tbody_array : forward_sales_information_tbody_array,
			plan_by_field_tbody_array : plan_by_field_tbody_array,
			field_choice_crop_tbody_row_array : field_choice_crop_tbody_row_array,
			crop_resources_usages_difference_tbody_array : crop_resources_usages_difference_tbody_array,
			field_difference_str : field_difference_str,
			crop_group_array : crop_group_array			
			
		}),
		success : function(response) {
			var status = response.status;
			if (status == 'success') {
				customAlerts('"'+farmName+'" farm has been successfully updated', "success", 0);
				var delay = 1000; //Your delay in milliseconds by rohit 14-04-15
	            setTimeout(function(){ window.location = "output-edit-farm-info.htm?farmId="+farmId; }, delay);
				//window.location = "farm.htm";
			} else if (status == 'Already exists') {
				customAlerts('"'+farmName+'" farm is already exists, Please enter other farm name', "error", 0);
			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, "error", 0);
		},
	}).done(function(){
		hideLoadingImage();
	});
	}

//09-03-2015 change start
function showForwardPlan() {
	if (createProcessForShowForwardPlan()) {
		showForwardSalesPage();
		callMethodForPageChangeAndProgressBarImage(8, 7);
	}
}
function showBackwardPlan() {
//	alert("showBackwardPlan");
	/*var flagForward = $.trim("" + $('input[name=plan_by_farm]:checked').val());
	if (flagForward == "fields") {
//		alert("flagForward : " + flagForward);
		showFieldVariencePage();
	} else {
//		alert("flagForward else : " + flagForward);
		showResourcesUsagePage();
	}*/
	showResourcesUsagePage();
	callMethodForPageChangeAndProgressBarImage(7, 6);
}

//09-03-2015 change End
function removeField()
{
	var fieldNameForRemove="";
	
	var rowLength=$("#plan-by-field-tbody > tr").length;
	//alert("hello"+rowLength);
	if(rowLength=="0" || rowLength=="1")
		{
		/*Messege modified by Harshit Gupta on 09-04-2015
		Start*/
		customAlerts("There are no fields, Please add fields first", "error", 0);
		/*end*/
		return false;
		}
	var falg=true;
	for(var i=1;i<rowLength;i++)
		{
		if($("#row-field-manage_checkbox__"+i).is(':checked'))
			{
			falg=false;
			fieldNameForRemove+=$('#row-field-name__'+i).html()+",";
			}
		}
	if(falg)
		{
		customAlerts("No field selected, Please select atleast one Field", "error", 0);
		return false;
		}
	var fieldsSelect=fieldNameForRemove.substring(0, fieldNameForRemove.length-1);
	if(!confirm('Are you sure you want to remove "'+fieldsSelect+'" fields?')){
		for(var i=1;i <= $("#plan-by-field-tbody tr").length;i++){
			if($("#row-field-manage_checkbox__"+i).is(":checked"))				
			$("#row-field-manage_checkbox__"+i).removeAttr("checked");
		}
		return false;
	}
	var myTestArray = new Array();
	for(var i=1;i<rowLength;i++)
	{
		
	if($("#row-field-manage_checkbox__"+i).is(':checked'))
		{
		myTestArray.push(i);
		/*Modified by Harshit Gupta on 13-04-2015
		 * Start*/
		$("#field_choice_crop_tbody tr").each(function(){
			if($(this).children('td:first').html().trim() == $('#row-field-name__'+i).html().trim()){
				$(this).remove();
			}
		});
		/*End*/
		var index = field_name.indexOf($.trim("" + $('#row-field-name__'+i).html()));
		if (index > -1) {
			field_name.splice(index, 1);
		}
		}
	}
	var flag = 0;
	for (var i = 0; i < myTestArray.length; i++) {
		myTestArray[i] = parseInt(myTestArray[i]);
		myTestArray[i] = myTestArray[i] - parseInt(flag);
		var newHtml = "";
		var total = 0;
		var insideRowCount = $("#plan-by-field-tbody > tr ").length;
		var count = 1;
		//alert("insideRowCount : "+insideRowCount);
		for (var k = 1; k < insideRowCount; k++) {
			if (k != myTestArray[i]) {
				var rowFieldSize = $.trim("" + $('#row-field-size__' + k).html());
				var rowFieldName = $.trim("" + $('#row-field-name__' + k).html());
				var optionSelectedValue=$.trim("" + $('#selected_last_crop____' + k).val());
				total += Number(rowFieldSize.replace(/,/g, ''));
				var fieldFollowChecked="";
				var fieldDivideChecked="";
				var fieldIrrigateChecked="";
				if($('#field-follow__'+k).is(':checked'))
				{
					fieldFollowChecked="checked";
				}
			    if($('#field-divide__'+k).is(':checked'))
			    	{
			    	fieldDivideChecked="checked";
			    	}
			    if($('#field-irrigate__'+k).is(':checked'))
			    	{
			    	fieldIrrigateChecked="checked";
			    	}
				if (count % 2 == 0) {

					newHtml += "<tr class=\"success tblgrn text-center\">";
				} else {

					newHtml += "<tr class=\"tblbclgrnd text-center\">";
				}
				var optionValue="";
				var selectedValue="";
				if(optionSelectedValue=="No Crop")
					{
					selectedValue="selected";
					}
				optionValue+="<option value=\"No Crop\" "+selectedValue+">No Crop</option>";
				for (var l = 0; l < field_crop.length; l++) {
					selectedValue="";
					if(optionSelectedValue==field_crop[l])
						{
					    	selectedValue="selected";
						}
					optionValue+="<option value="+field_crop[l]+" "+selectedValue+">"+field_crop[l]+"</option>";	
					}
				newHtml += "<td><input id=\"row-field-manage_checkbox__"+ count + "\" type=\"checkbox\"></td><td id=\"row-field-name__" + count + "\">" + rowFieldName+ "</td>" + "<td id=\"row-field-size__" + count + "\">"	+ rowFieldSize + "</td> " + "<td><select id=\"selected_last_crop____"+count+"\">"+optionValue+"</select></td>"
				 + "<td><input value=\"true\" id=\"field-follow__"+count+"\" name=\"field-follow__"+count+"\" type=\"checkbox\" "+fieldFollowChecked+" ></td>"+ "<td><input value=\"true\" id=\"field-divide__"+count+"\" name=\"field-divide__"+count+"\" type=\"checkbox\" "+fieldDivideChecked+"></td>" + "<td><input value=\"true\" id=\"field-irrigate__"+count+"\" name=\"field-irrigate__"+count+"\" type=\"checkbox\" "+fieldIrrigateChecked+"></td>"
				 + "</tr> ";
				
				count++;
			}
		}
		flag++;
		total_land = total;
		var totalWithComma=addCommaSignOnValue(total);
		newHtml+="<tr class=\"tblft text-center\" id=\"total-field-last-row\">"	+ "<td class=\"tblft1\">Total acres </td><td id=\"total-acres-value\" colspan=\"6\" style=\"text-align: left\">"+totalWithComma+ "</td></tr>";
		$("#plan-by-field-tbody").html("");
		$("#plan-by-field-tbody").html(newHtml);
		div_hidePlanByField();
		
		/*Modified by Harshit Gupta on 13-04-2015
		 * Start*/
		var removedFieldName = fieldNameForRemove.substring(0, fieldNameForRemove.length-1);
		customAlerts('"'+removedFieldName+'" Field removed successfully ', "success", 0);
		balanceDataAndIDsForTable();
		/*End*/
	}
}
function modifyExistingField()
{

	$("#pop-up-field-name").val('');
	$("#pop-up-field-size").val('');
	var rowLength=$("#plan-by-field-tbody > tr").length;
	if(rowLength=="0")
		{
		customAlerts("Please select atleast one field for modify", "error", 0);
		return false;
		}
	var falg=0;
	for(var i=1;i<rowLength;i++)
		{
		if($("#row-field-manage_checkbox__"+i).is(':checked'))
			{
			//$("#row-field-manage_checkbox__"+i).attr('checked',false);
			falg++;
			}
		}
	if(falg==0)
		{
		customAlerts("Please select atleast one field for modify", "error", 0);
		return false;
		}
	if(falg>1)
	{
		customAlerts("You can modify only one field at a time", "error", 0);

	return false;
	}
	$("#add_field_button").hide();
	$("#modify_field_button").show();
	$("#add-field-span-dynamic").html("Update Field");
	for(var i=1;i<rowLength;i++)
	{
	if($("#row-field-manage_checkbox__"+i).is(':checked'))
		{
		$('#pop-up-field-name').val($.trim("" + $('#row-field-name__'+i).html()));
		$('#pop-up-field-size').val($.trim("" + $('#row-field-size__'+i).html()));
		
		}
	}
	
	div_show4();
}
function addNewFieldValidation()
{
	$("#modify_field_button").hide();
	$("#add_field_button").show();
	$('#pop-up-field-name').val("");
	$('#pop-up-field-size').val("");
}
function modifyField()
{
	
	var rowLength=$("#plan-by-field-tbody > tr").length;
	var rowCount=0;
	for(var i=1;i<rowLength;i++)
	{
	if($("#row-field-manage_checkbox__"+i).is(':checked'))
		{
		rowCount=i;
		$("#row-field-manage_checkbox__"+i).attr('checked',false);
		}
	}
	var fieldName = $.trim("" + $('#pop-up-field-name').val());
	var fieldSize = $.trim("" + $('#pop-up-field-size').val());
	
	if (fieldName == "") {
		customAlerts("Please enter name of field", "error", 0);
	
		return false;
	}
	if (fieldSize == "") {
		customAlerts("Please enter size of field", "error", 0);
	
		return false;
	}
	if (!validateNumberOnly(fieldSize) || fieldSize < 1) {
		customAlerts("Please enter valid size for field", "error", 0);
		
		return false;
	}
	var total = 0;
	for (var j = 1; j < rowLength; j++) {
		if(j!=rowCount)
			{
			var rowFieldSize = $.trim("" + $('#row-field-size__' +j).html().replace(/,/g, ''));
			total = total+parseInt(rowFieldSize);
			var rowFieldName = $.trim("" + $('#row-field-name__' + j).html());
			if (fieldName.toLowerCase() === rowFieldName.toLowerCase()) {
				customAlerts('"'+fieldName+ '"Field name is already exist', "error", 0);
				return false;
			}
		}
		else
			{
			var index = field_name.indexOf($.trim("" + $('#row-field-name__'+j).html()));
			//alert("index : "+index);
			if (index > -1) {
				field_name.splice(index, 1);
			}
			}
	}
	field_name.push(fieldName);
	total =total+ parseInt(fieldSize.replace(/,/g, ''));
	total_land = total;
	$('#total-acres-value').html(total.toLocaleString('en'));
	$('#row-field-size__' +rowCount).html(fieldSize);
	$('#row-field-name__' +rowCount).html(fieldName);
	div_hide4(); 
	div_hidePlanByField();
	var alertMessage = "";
	if(total > 10000)
    {
    	alertMessage += "But the amount of land entered for \""+fieldName+"\" field exceeds 10,000.00 acres. ";
    }
	if (alertMessage != "") {
		customAlerts('"' + fieldName + '" Field name is updated successfully. '
				+ alertMessage, "warning", 0);
	} else {
		customAlerts('"'+fieldName+'" Field name is updated successfully', "success", 0);
	}
//	customAlerts('"'+fieldName+'" Field name is updated successfully', "success", 0);
	$('#pop-up-field-name').val("");
	$('#pop-up-field-size').val("");
}
function getFieldYieldDiffence(sel)
{
	var cropName=sel.value;
	//alert(cropName);
	if(cropName=="0")
		{
		$("#crop_Yield_Difference_Expected").html("");
		$("#crop_Yield_Difference_Minimum").html("");
		$("#crop_Yield_Difference_Maximum").html("");
		$("#resources_usages_production_resource_default").html("");
		return false;
		}
	var rowLength=$("#crop_information_tbody > tr").length;
		for(var i=1;i<=rowLength;i++)
			{
			var rowCropName= $.trim("" + $('#crop_info_details_field_crop_name__'+i).html());
			if (rowCropName.toLowerCase() === cropName.toLowerCase()) {
				$("#crop_Yield_Difference_Expected").html($.trim("" + $('#crop_info_yields_expected__'+i).val()));
				$("#crop_Yield_Difference_Minimum").html($.trim("" + $('#crop_info_yields_min__'+i).val()));
				$("#crop_Yield_Difference_Maximum").html($.trim("" + $('#crop_info_yields_max__'+i).val()));
				$("#resources_usages_production_resource_default").html($.trim("" + $('#calulated_cost_of_production__'+cropName.replace(/\s+/g, '')).val()));
			//	return false;
			}
			}
	      for(var k=1;k<=$("#crop_resources_usages_difference_tbody > tr").length;k++)
			{
		for(var i=1;i<=$("#crop_resource_usage_tbody >tr").length;i++)
		{
		for(var j=4;j<=$("#crop_resource_usage_row__"+i+" >td").length;j++)
			{
			if(($("#resources_usages_difference_row__"+k+"_resource_name").html()==$("#crop_resource_usage_thead_first_row_column__"+j).html()) && cropName==$("#crop_resource_usage_crop__"+i).html())
				{
				$("#resources_usages_difference_row__"+k+"_resource_default").html($.trim("" + $("#crop_resource_usage__"+i+"__resource__"+(j-3)).val()));
				}
			
			}
			
		
	    }
		
			}
}

function addNewResource()
{	
	
	var resourseName = $.trim("" + $('#resourse_name').val());
	var unitName = $.trim("" + $('#resourse_unit_name').val());
	if (resourseName == "") {
		customAlerts("Please enter resourse name", "error", 0);
		return false;
	}
	if (unitName == "") {
		customAlerts("Please enter unit of measure of particular resource", "error", 0);
		return false;
	}
	/*if (!validateNumberOnly(unitName)) {
		alert("Please valid unit ");
		return false;
	}*/
	/*Modified by Harshit Gupta on 08-04-2015
	 * Start*/
	if (resourseName.toLowerCase() === "Land".toLowerCase() || resourseName.toLowerCase() === "Capital".toLowerCase()) {
		customAlerts('"'+resourseName+'" resource name is already exist', "error", 0);
		return false;
	}
	/*End*/
	var rowCount = $('#manage_resource_tbody >tr').length;
	//alert("length :"+rowCount);
	for (var i = 3; i <= rowCount; i++) {
		var rowFieldName = $.trim("" + $('#resource_manage_name__' + (i-2)).html());
		//total += parseInt(rowFieldSize);
	//	alert($('#resource_manage_name__' + (i-2)).html());
		//var rowFieldName = $.trim("" + $('#row-field-name__' + i).html());
		// alert(rowFieldName);
		if (resourseName.toLowerCase() === rowFieldName.toLowerCase()) {
			customAlerts('"'+resourseName+'" resource name is already exist', "error", 0);
			return false;
		}
	}

	var newHtml = "";
	rowCount++;
	if (rowCount % 2 == 0) {

		newHtml += "<tr class=\"tblbclgrnd text-center\" id=\"manage_resource_row_resource__"+rowCount+"\">";
	} else {

		newHtml += "<tr class=\"tblgrn text-center\" id=\"manage_resource_row_resource__"+rowCount+"\">";
	}
	
	newHtml += "<td class='tblft1'><input type='checkbox' name=\"resourceSelection[]\" id=\"manage_resource_row_checkbox__"+rowCount+"\"></td>"+
	"<td class='success croplimi' id=\"resource_manage_name__"+(rowCount-2)+"\">"+resourseName+"</td>"+
	"<td class='success croplimit' id=\"resource_manage_uom__"+(rowCount-2)+"\">"+unitName+"</td>"+
	"<td class='success croplimit'><input type='text' onchange='addCommaSignWithOutDollarDot(this)' id='resource_manage__"+(rowCount-2)+"' onkeypress='return isValidNumberValueForWithOutDot(event)'></td>";

	$('#manage_resource_tbody').append(newHtml);
	div_hide6();
	customAlerts('"'+resourseName+'"  resource added to the strategy', "success", 0);
	$('#resource_manage__'+(rowCount-2)).focus();
}
function removeResourse()
{
	var resourceNameForRemove="";
	var resourceNameForRemoveSelect="";
	
	var myTestArray=new Array();
	//alert("hello");
	var rowLength=$("#manage_resource_tbody > tr").length;
	//alert("length before:"+rowLength);
	
	if (rowLength > 2) {
		for (var i = 3; i <= rowLength; i++) {
			
			if ($("#manage_resource_row_checkbox__" + i).is(':checked')) {
				resourceNameForRemoveSelect += $("#resource_manage_name__" + (i-2)).html()+",";
				}
		}
		}
	
	var resourseSelect=resourceNameForRemoveSelect.substring(0, resourceNameForRemoveSelect.length-1);
	
	
	
	if(rowLength=="0")
		{
		customAlerts("There are no fields to remove, Please create a field first", "error", 0);
		return false;
		}
	if(rowLength == 2)
	{
	customAlerts("Land and Capital are required resources and cannot be removed", "error", 0);
	return false;
	}
	if($('input[name="resourceSelection[]"]:checked').length == 0)
	{
		customAlerts("No resource selected, Please first select which resource you would like to remove", "error", 0);
		return false;
	}
	var falg=true;
	var confirmFlag = false;
	if(confirm('Are you sure you want to remove "'+resourseSelect+'" Resources?')){
		confirmFlag = true;
	}else{
		return false;
	}
	if (rowLength > 2) {
		for (var i = 3; i <= rowLength; i++) {
			myTestArray.push($("#resource_manage_name__" + (i - 2)).html());
			// alert($("#resource_manage_name__"+(i-2)).html());

			if (confirmFlag && $("#manage_resource_row_checkbox__" + i).is(':checked')) {
				/*Code added by Harshit Gupta to resolve resource not remove error on 09-06-2015
				 * Start*/
				/*updated By Bhagvan Singh for remove Resources name in alertify on 15-04-2015
				start*/
				resourceNameForRemove += $("#resource_manage_name__" + (i-2)).html()+",";
				/*end*/
				delete resources[$("#resource_manage_name__"+ (i - 2)).html().trim()];
				/*End*/
//				alert("resourceName : "+resourceNameForRemove);
				$("#manage_resource_row_resource__" + i).remove();
				falg = false;
			}
		}
		}
		/*Modified by Harshit Gupta 07-04-2015*/
	if(falg && rowLength > 2)
	{
	customAlerts("Select the resource you would like to remove", "error", 0);
	return false;
	}

	if(!falg)
		{
		/*updated By Bhagvan Singh for remove Resources name in alertify on 15-04-2015
		start*/
		var removedResources=resourceNameForRemove.substring(0, resourceNameForRemove.length-1);
		customAlerts('"'+removedResources+'" Resource removed successfully', "success", 0);
		/*end*/
		var rowLength1=$("#manage_resource_tbody > tr").length;
		//alert("length after:"+rowLength1);
		counter=3;
		for(var j=3;j<=rowLength;j++)
		{
			//alert("111"+myTestArray[j-3]);
			//alert("222"+$("#resource_manage_name__"+(j-2)).html());
		if($("#resource_manage_name__"+(j-2)).html()==myTestArray[j-3])
			{
//			alert($("#resource_manage_name__"+(j-2)).html());
			 $("#manage_resource_row_resource__"+(j)).attr('id',"manage_resource_row_resource__"+counter);
			 $("#manage_resource_row_checkbox__"+(j)).attr('id',"manage_resource_row_checkbox__"+counter);
			 $("#manage_resource_row_checkbox__"+(j)).attr('id',"manage_resource_row_checkbox__"+counter);
			 $("#resource_manage_name__"+(j-2)).attr('id',"resource_manage_name__"+((counter)-2));
			 $("#resource_manage_uom__"+(j-2)).attr('id',"resource_manage_uom__"+((counter)-2));
			 $("#resource_manage__"+(j-2)).attr('id',"resource_manage__"+((counter)-2));
			 counter++;
			}
		
		
		}}
	
	//alert("array is"+myTestArray);
}




/*
created By Bhagvan Singh on 06-04-2015
start*/
function getTotalProfitGoal()
{
	var fixedCost = $.trim("" + $('#fixed_cost').val());
	var livingExpenses = $.trim("" + $('#living_expenses').val());
	var additionalProfit = $.trim("" + $('#additional_profit').val());
	var total=0;
	if(fixedCost !="" && validateNumberOnly(fixedCost))
		{
		total=parseInt(total)+parseInt(fixedCost);
		}
	if(livingExpenses !="" && validateNumberOnly(livingExpenses))
	{
		total=parseInt(total)+parseInt(livingExpenses);
	}
	if(additionalProfit !="" && validateNumberOnly(additionalProfit))
	{
		total=parseInt(total)+parseInt(additionalProfit);
	}
	
	$("#profit_goal").val(total);
}
function addProductionCostField(id)
{
	
	$("#crop-optional-cost-components").val("");
	$("#add-new-componemt-button").show();
	$("#update-componemt-button").hide();
	$("#add-new-componemt-button").attr("onclick","addNewCostComponents('"+id+"')");
	div_show10();
	$("#add-cost-component-span-dynamic").html("Add Cost Component");
}
function modifyProductionCostField(id)
{
	
	var rowCount=$("#production_cost_table_tbody__"+id+" > tr").length;
	
	var falg=0;
	for(var i=1;i<=(rowCount-2);i++)
		{
		if($("#production_cost_component_manage_checkbox__"+i+"_"+id).is(':checked'))
			{
			falg++;
			}
		}
	if(falg==0)
		{
		customAlerts("Please Select the Component for modify", "error", 0);
		return false;
		}
	if(falg>1)
	{
		customAlerts("Please select only one Component for modify", "error", 0);
	return false;
	}
	for(var i=1;i<=(rowCount-2);i++)
	{
	if($("#production_cost_component_manage_checkbox__"+i+"_"+id).is(':checked'))
		{
		$('#crop-optional-cost-components').val($.trim("" + $("#production_cost_component_name__"+i+"_"+id).html()));
//		$("#production_cost_component_manage_checkbox__"+i+"_"+id).removeAttr('checked');
		}
	}
	$("#add-new-componemt-button").hide();
	$("#update-componemt-button").show();
	$("#add-cost-component-span-dynamic").html("Update Cost Component");
	div_show10();
	$("#update-componemt-button").attr("onclick","updateCostComponents('"+id+"')");
	
}
function removeProductionCostField(id)
{
	var componentFieldNameForRemove="";
	
	var rowLength=$("#production_cost_table_tbody__"+id+" > tr").length;
	var myTestArray = new Array();
	for(var i=1;i<=(rowLength-2);i++)
		{
		if($("#production_cost_component_manage_checkbox__"+i+"_"+id).is(':checked'))
			{
			componentFieldNameForRemove+=$("#production_cost_component_name__"+i+"_"+id).html()+",";
			myTestArray.push(i);
			}
		}
	var componentFieldName=componentFieldNameForRemove.substring(0, componentFieldNameForRemove.length-1);
	if(myTestArray.length<1)
		{
		customAlerts("No component selected to remove, Please select at least one Component", "error", 0);
		return false; 
		}
	if(!confirm('Are you sure you want to remove "'+componentFieldName+'" component?')){
		for(var i=1;i<=(rowLength-2);i++)
		{
			if($("#production_cost_component_manage_checkbox__"+i+"_"+id).is(':checked'))			
			$("#production_cost_component_manage_checkbox__"+i+"_"+id).removeAttr("checked");
		}
		return false;
	}
	
	var flag=0;
	for(var i=0;i<myTestArray.length;i++)
		{
		var rowCount=1;
		myTestArray[i]=parseInt(myTestArray[i])-parseInt(flag);
		var nowLength=$("#production_cost_table_tbody__"+id+" > tr").length;
		var newHtml="";
		for(var j=1;j<=(nowLength-2);j++)
		{
			if(j!=myTestArray[i])
				{
				if(rowCount % 2 == 0)
				{
				newHtml+="<tr class=\"tblgrn\" id=\"production_cost_component_row__"+rowCount+"_"+id+"\"><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__"+rowCount+"_"+id+"\"></td><td id=\"production_cost_component_name__"+rowCount+"_"+id+"\" class=\"tblft1\">"+$("#production_cost_component_name__"+j+"_"+id).html()+"</td><td class=\"success infotext\"><input type=\"text\" id=\"production_cost_value_1_row__"+rowCount+"_"+id+"\" onchange=\"getCalculateValue("+rowCount+");addCommaSignWithOutDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" name=\"Crop\" value='"+ $("#production_cost_value_1_row__"+j+"_"+id).val()+ "' /></td><td class=\"success infotext\"><input type=\"text\" id=\"production_cost_value_2_row__"+rowCount+"_"+id+"\" onchange=\"getCalculateValue("+rowCount+");addCommaSignWithDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" name=\"Crop\" value='"+ $("#production_cost_value_2_row__"+j+"_"+id).val()+ "' /></td><td id=\"production_cost_calculate_value__"+rowCount+"_"+id+"\" class=\"success infotext\">"+$("#production_cost_calculate_value__"+j+"_"+id).html()+"</td></tr>";
				}
			else
				{
				newHtml+="<tr class=\"tblbclgrnd\" id=\"production_cost_component_row__"+rowCount+"_"+id+"\"><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__"+rowCount+"_"+id+"\"></td><td id=\"production_cost_component_name__"+rowCount+"_"+id+"\" class=\"tblft1\">"+$("#production_cost_component_name__"+j+"_"+id).html()+"</td><td class=\"success infotext\"><input type=\"text\" id=\"production_cost_value_1_row__"+rowCount+"_"+id+"\" onchange=\"getCalculateValue("+rowCount+");addCommaSignWithOutDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" name=\"Crop\" value='"+ $("#production_cost_value_1_row__"+j+"_"+id).val()+ "' /></td><td class=\"success infotext\"><input type=\"text\" id=\"production_cost_value_2_row__"+rowCount+"_"+id+"\" onchange=\"getCalculateValue("+rowCount+");addCommaSignWithDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" name=\"Crop\" value='"+ $("#production_cost_value_2_row__"+j+"_"+id).val()+ "' /></td><td id=\"production_cost_calculate_value__"+rowCount+"_"+id+"\" class=\"success infotext\">"+$("#production_cost_calculate_value__"+j+"_"+id).html()+"</td></tr>";
				}
			rowCount++;
				}
		}
		newHtml+="<tr class=\"tblgrn\"><td colspan=\"2\" class=\"tblft1 optncal\">Calculate</td><td class=\"success\"></td><td class=\"success\"></td><td class=\"success\"></td></tr>" +
		"<tr class=\"tblft text-center\"><td colspan=\"2\" class=\"tblft1\">Variable Cost per Acre: </td><td><input type=\"hidden\" value="+id+" id=\"hidden_id_for_production_cost_row\"> </td><td></td><td id=\"production_cost_calculate_total_"+id+"\" class=\"optndoller\">"+$("#production_cost_calculate_total_"+id).html()+"</td></tr>";
        $("#production_cost_table_tbody__"+id).html("");
        $("#production_cost_table_tbody__"+id).html(newHtml);
		flag++;
		}
	getProdutionCostUpdate(id);
	
	customAlerts('"'+componentFieldName+'"'+" Component removed  Successfully", "success", 0);
}
function addNewCostComponents(id)
{
	
	var costComponentName=$.trim("" + $('#crop-optional-cost-components').val());
	if(costComponentName=="")
		{
		customAlerts("Please enter Cost component", "error", 0);
		return false;
		}
	var rowLength=$("#production_cost_table_tbody__"+id+" > tr").length;
	for(var i=1;i<=(rowLength-2);i++)
		{
		var rowCostComponentName=$.trim("" + $('#production_cost_component_name__'+i+'_'+id).html());
		//alert("rowCostComponentName :"+rowCostComponentName);
		if (costComponentName.toLowerCase() === rowCostComponentName.toLowerCase()) {
			customAlerts('"'+costComponentName+'" Component is already exist', "error", 0);
			return false;
		}
		}
	var rowCount=1;
	var newHtml="";
	var confirmFlag = confirm("Are you sure you want to add a new Component named \""+costComponentName+"\"?"); 
	if(confirmFlag){
	for(var i=1;i<=(rowLength-2);i++)
	{
		if(rowCount % 2 == 0)
			{
			newHtml+="<tr class=\"tblgrn\" id=\"production_cost_component_row__"+rowCount+"_"+id+"\"><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__"+rowCount+"_"+id+"\"></td><td id=\"production_cost_component_name__"+rowCount+"_"+id+"\" class=\"tblft1\">"+$("#production_cost_component_name__"+i+"_"+id).html()+"</td><td class=\"success infotext\"><input type=\"text\" id=\"production_cost_value_1_row__"+rowCount+"_"+id+"\" onchange=\"getCalculateValue("+rowCount+");addCommaSignWithOutDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" name=\"Crop\" value='"+ $("#production_cost_value_1_row__"+i+"_"+id).val()+ "' /></td><td class=\"success infotext\"><input type=\"text\" id=\"production_cost_value_2_row__"+rowCount+"_"+id+"\" onchange=\"getCalculateValue("+rowCount+");addCommaSignWithDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" name=\"Crop\" value='"+ $("#production_cost_value_2_row__"+i+"_"+id).val()+ "' /></td><td id=\"production_cost_calculate_value__"+rowCount+"_"+id+"\" class=\"success infotext\">"+$("#production_cost_calculate_value__"+i+"_"+id).html()+"</td></tr>";
			}
		else
			{
			newHtml+="<tr class=\"tblbclgrnd\" id=\"production_cost_component_row__"+rowCount+"_"+id+"\"><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__"+rowCount+"_"+id+"\"></td><td id=\"production_cost_component_name__"+rowCount+"_"+id+"\" class=\"tblft1\">"+$("#production_cost_component_name__"+i+"_"+id).html()+"</td><td class=\"success infotext\"><input type=\"text\" id=\"production_cost_value_1_row__"+rowCount+"_"+id+"\" onchange=\"getCalculateValue("+rowCount+");addCommaSignWithOutDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" name=\"Crop\" value='"+ $("#production_cost_value_1_row__"+i+"_"+id).val()+ "' /></td><td class=\"success infotext\"><input type=\"text\" id=\"production_cost_value_2_row__"+rowCount+"_"+id+"\" onchange=\"getCalculateValue("+rowCount+");addCommaSignWithDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" name=\"Crop\" value='"+ $("#production_cost_value_2_row__"+i+"_"+id).val()+ "' /></td><td id=\"production_cost_calculate_value__"+rowCount+"_"+id+"\" class=\"success infotext\">"+$("#production_cost_calculate_value__"+i+"_"+id).html()+"</td></tr>";
			}
		rowCount++;
	}
	newHtml+="<tr class=\"tblbclgrnd\" id=\"production_cost_component_row__"+rowCount+"_"+id+"\"><td class=\"tblft1 text-center\"><input type=\"checkbox\" id=\"production_cost_component_manage_checkbox__"+rowCount+"_"+id+"\"></td><td id=\"production_cost_component_name__"+rowCount+"_"+id+"\" class=\"tblft1\">"+costComponentName+"</td><td class=\"success infotext\"><input type=\"text\" id=\"production_cost_value_1_row__"+rowCount+"_"+id+"\" onchange=\"getCalculateValue("+rowCount+");addCommaSignWithOutDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" name=\"Crop\"  /></td><td class=\"success infotext\"><input type=\"text\" id=\"production_cost_value_2_row__"+rowCount+"_"+id+"\" onchange=\"getCalculateValue("+rowCount+");addCommaSignWithDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" name=\"Crop\"  /></td><td id=\"production_cost_calculate_value__"+rowCount+"_"+id+"\" class=\"success infotext\"></td></tr>";
	newHtml+="<tr class=\"tblgrn\"><td colspan=\"2\" class=\"tblft1 optncal\">Calculate</td><td class=\"success\"></td><td class=\"success\"></td><td class=\"success\"></td></tr>" +
			"<tr class=\"tblft text-center\"><td colspan=\"2\" class=\"tblft1\">Variable Cost per Acre: </td><td><input type=\"hidden\" value="+id+" id=\"hidden_id_for_production_cost_row\"> </td><td></td><td id=\"production_cost_calculate_total_"+id+"\" class=\"optndoller\">"+$("#production_cost_calculate_total_"+id).html()+"</td></tr>";
	$("#production_cost_table_tbody__"+id).html("");
	$("#production_cost_table_tbody__"+id).html(newHtml);
	}
	div_hide10();
	if(confirmFlag)
	{customAlerts('"'+costComponentName+'"'+" Cost component added successfully", "success", 0);}
}
function updateCostComponents(id)
{
	
	var costComponentName=$.trim("" + $('#crop-optional-cost-components').val());
	var selectedRow=0;
	if(costComponentName=="")
		{
		customAlerts("Please enter cost Component", "success", 0);
		return false;
		}
	var rowLength=$("#production_cost_table_tbody__"+id+" > tr").length;
	for(var i=1;i<=(rowLength-2);i++)
	{
	if($("#production_cost_component_manage_checkbox__"+i+"_"+id).is(':checked'))
		{
		selectedRow=i;
		}
	}
	for(var i=1;i<=(rowLength-2);i++)
		{
		if(i!=selectedRow)
			{
			var rowCostComponentName=$.trim("" + $('#production_cost_component_name__'+i+'_'+id).html());
			if (costComponentName.toLowerCase() === rowCostComponentName.toLowerCase()) {
				customAlerts('"'+costComponentName+'"'+" Component is already exist", "error", 0);
				return false;
			}
			}
		}
	$("#production_cost_component_name__"+selectedRow+"_"+id).html($.trim("" + $('#crop-optional-cost-components').val()));
	$("#production_cost_component_manage_checkbox__"+selectedRow+"_"+id).removeAttr('checked');
	div_hide10();
	customAlerts('"'+costComponentName+'"'+" Component Updated Successfully", "success", 0);
}
function getProdutionCostUpdate(id)
{
	/*update by Bhagvan Singh on 16-04-2015 for component total 
	start*/
	var rowLength=$("#production_cost_table_tbody__"+id+" > tr").length;
	var total=0;
	var rowTotal=0;
	for(var i=1;i<=(rowLength-2);i++)
	{
		rowTotal=$.trim("" + $("#production_cost_calculate_value__"+i+"_"+id).html().replace('$', '').replace(/,/g, ''));
		if (rowTotal!="") {
			total+=parseFloat(rowTotal);
		}
	}
	$("#production_cost_calculate_total_"+id).html("");
	$("#production_cost_calculate_total_"+id).html(addCommaSignWithDollarWithValue(""+total));
	/*end*/
}
function addDollerSign(id)
{
	var temp=$.trim("" + $(id).val());
	$(id).val("$"+temp.replace('$', ''));
}
function addCommaSign(id)
{
	
	if($(id).val()=="" )
	{
	
	}
   else
	{
	   var temp=$.trim("" + $(id).val().replace(/,/g, ''));
		
		$(id).val(Number(temp).toLocaleString('en'));
	}
   
}
function addOneDigitPoint(id)
{
	
	var temp=$.trim("" + $(id).val());
	if(temp!="")
		{
	$(id).val(Number(temp).toFixed(1));
}}
function addTwoDigitPoint(id)
{
	var temp=$.trim("" + $(id).val());
	if(temp!="")
		{
	$(id).val(Number(temp).toFixed(2));
}
}


function cropInformationDetailsMaxMinCheck()
{
	
	var isExpValid=true;
	/*updated by Bhagvan Singh for validation on 16-04-2015
	start*/
   for(var i=1;i<=$("#crop_information_tbody > tr").length;i++)
		{
		if ($.trim("" + $('#crop_info_yields_expected__'+i).val())=="")
		{
			
			customAlerts('Please fill Expected Yield value for "'+$("#crop_info_details_field_crop_name__"+i).html()+'" crop', "error", 0);
			focusForValidation("crop_info_yields_expected__"+i);
			isExpValid=false;
			return isExpValid;
		}
		else
		{
			if ($.trim("" + $('#crop_info_yields_max__'+i).val())!="")
			{
				if(Number($.trim("" + $('#crop_info_yields_max__'+i).val().replace(/,/g, ''))) <= Number($.trim("" + $('#crop_info_yields_expected__'+i).val().replace(/,/g, ''))))
					{
					
				customAlerts('Maximum Yield value should be greater than Expected Value for "'+$("#crop_info_details_field_crop_name__"+i).html()+'" crop', "error", 0);
				focusForValidation("crop_info_yields_expected__"+i);
				focusForValidation("crop_info_yields_max__"+i);
				isExpValid=false;
				return isExpValid;
			        }      
			}
			if ($('#crop_info_yields_min__'+i).val()!="")
			{
				if(Number($('#crop_info_yields_min__'+i).val().replace(/,/g, '')) >= Number($('#crop_info_yields_expected__'+i).val().replace(/,/g, '')))
					{
					
				customAlerts('Minimum Yield value should be less than Expected Value for "'+$("#crop_info_details_field_crop_name__"+i).html()+'" crop', "error", 0);
				focusForValidation("crop_info_yields_expected__"+i);
				focusForValidation("crop_info_yields_min__"+i);
				isExpValid=false;
				return isExpValid;
			        }      
			}
			
		}
		
		if ($.trim("" + $('#crop_info_price_expected__'+i).val())=="")
		{
			
			customAlerts('Please fill Expected Price value for "'+$("#crop_info_details_field_crop_name__"+i).html()+'" crop', "error", 0);
			focusForValidation("crop_info_price_expected__"+i);
			isExpValid=false;
			return isExpValid;
		}
		else
		{
			if ($.trim("" + $('#crop_info_price_max__'+i).val())!="")
			{
				if(Number($.trim("" + $('#crop_info_price_max__'+i).val().replace('$', '').replace(/,/g, ''))) <= Number($.trim("" + $('#crop_info_price_expected__'+i).val().replace('$', '').replace(/,/g, ''))))
					{
					
				customAlerts('Maximum price value should be greater than Expected price value for "'+$("#crop_info_details_field_crop_name__"+i).html()+'" crop', "error", 0);
				focusForValidation("crop_info_price_expected__"+i);
				focusForValidation("crop_info_price_max__"+i);
				isExpValid=false;
				return isExpValid;
			        }      
			}
			if ($('#crop_info_price_min__'+i).val()!="")
			{
				if(Number($('#crop_info_price_min__'+i).val().replace('$', '').replace(/,/g, '')) >= Number($('#crop_info_price_expected__'+i).val().replace('$', '').replace(/,/g, '')))
					{
					
				customAlerts('Minimum price value should be less than Expected price value for "'+$("#crop_info_details_field_crop_name__"+i).html()+'" crop', "error", 0);
				focusForValidation("crop_info_price_min__"+i);
				focusForValidation("crop_info_price_expected__"+i);
				isExpValid=false;
				return isExpValid;
			        }      
			}
			
			
		}
		/*end*/
			/*($.trim("" + $('#crop_info_yields_expected__'+i).val()));
			($.trim("" + $('#crop_info_yields_min__'+i).val()));
			($.trim("" + $('#crop_info_yields_max__'+i).val()));
			return true;*/
		// add by rohit to check production cost by 17-04-15 
		var cropName = $.trim(""+ $('#crop_info_details_field_crop_name__' + i).html());
		var newCropName = cropName.replace(/\s+/g, '');
		var varName = $.trim(""+ $('#calulated_cost_of_production__' + newCropName).val());
		varName = varName.replace('$', '');
		if (varName.length == "0" || !validateNumberOnly(varName)) {
			customAlerts('Please enter production cost for "' + cropName+'" crop name', "error", 0);
			focusForValidation("calulated_cost_of_production__"+newCropName);
			isExpValid=false;
			return isExpValid;
		}
		
		}
   return isExpValid;
}


/*Created by Harshit Gupta for Table id Balancing and removing non needed rows
02-04-2015
Start*/

function balanceDataAndIDsForTable() {
	var newHtml_for_field_choice_crop_tbody = "";
	var rowCount = 0;
	$("#field_choice_crop_tbody tr")
			.each(
					function() {
//						console.log($(this).html());
//						console.log($(this).find('td:eq(1)').html().trim());
//						console.log($(this).children('td').length);
						newHtml_for_field_choice_crop_tbody += "<tr id=\"field_choice_crop_tbody_row__"
								+ (++rowCount)
								+ "\" class=\"tblgrn text-center\"><td class=\"tblft1\" id=\"field_choice_crop_tbody_row_first_column__"
								+ (rowCount)
								+ "\">"
								+ $(this).find('td:nth-child(1)').html().trim()
								+ "</td>";
						for (var j = 1; j < $(this).children('td').length; j++) {
//							console.log($(this).find('td:eq(' + (j + 1) + ')'));
//							console.log($(this).find('td:eq(' + (j + 1) + ')').html());
//							console.log("Hello----------"+$(this).find('td:nth-child(' + (j + 1) + ') input')
//									.prop("checked"));
//							console.log("How are you?");
							newHtml_for_field_choice_crop_tbody += "<td id=\"field_choice_crop_tbody_row_others_column__"
									+ rowCount
									+ "__column__"
									+ j
									+ "\" class=\"success\"><input type=\"checkbox\" id=\"field_choice_crop_selected_row__"
									+ rowCount
									+ "__column__"
									+ j
									+ "\"";
							if ($(this).find('td:nth-child(' + (j + 1) + ') input')
									.prop("checked")) {
								newHtml_for_field_choice_crop_tbody += "checked=\"checked\"";
							}
							newHtml_for_field_choice_crop_tbody += "/></td>";
						}
						newHtml_for_field_choice_crop_tbody += "</tr>";
//						console.log(newHtml_for_field_choice_crop_tbody);
						
					});
	$("#field_choice_crop_tbody").html('');
	$("#field_choice_crop_tbody").html(newHtml_for_field_choice_crop_tbody);
	

	/*
	 * for(var i=1;i<=rowLength;i++){ newHtml_for_field_choice_crop_tbody += '<tr class="tblgrn text-center" id="field_choice_crop_tbody_row__1">';
	 * for(var j=2;j<=columnLength;j++){ newHtml_for_field_choice_crop_tbody += '<td id="field_choice_crop_tbody_row_first_column__1" class="tblft1">sdfgs</td><td class="success" id="field_choice_crop_tbody_row_others_column__1__column__1"><input
	 * type="checkbox" id="field_choice_crop_selected_row__1__column__1"></td></tr>'; } }
	 */

}

function removeDeletedFieldsFromTable() {
	var table = $("#field_choice_crop_table");
	for(var i=1;i<=$(table).find("tbody tr").length;i++){
	}
}
 /*update by Bhagvan Singh on 13-04-2015
 start*/
function allCheckboxSelect()
{
	for(var i=1;i<=$("#field_choice_crop_tbody >tr").length;i++)
	{
	
	for(var j=1;j<=$("#field_choice_crop_tbody_row__"+i+" >td").length-1;j++)
 		{
			var last_crop_class=$("#field_choice_crop_selected_row__" + i + "__column__" + j).parent().attr('class');
			if(last_crop_class=='last_crop' || $("#field_choice_crop_selected_row__" + i + "__column__" + j).prop('disabled'))
				{
				
				}
			else
				{
			$("#field_choice_crop_selected_row__" + i + "__column__" + j)
						.prop('checked', true);
			}
	}
	}
}
function allCheckboxNone()
{
	for(var i=1;i<=$("#field_choice_crop_tbody >tr").length;i++)
	{
	
	for(var j=1;j<=$("#field_choice_crop_tbody_row__"+i+" >td").length-1;j++)
		{
		$("#field_choice_crop_selected_row__"+i+"__column__"+j).removeAttr('checked');
			
	}
	}
}

/*create calculate for price for forwaresale by rohit singhal 6-4-15*/
/*start*/
function priceCalForwardSale(obj)
{
	/*commented by Bhagvan Singh on 15-04-2015 for calculate acres & quantity according to expected yield
	start*/
	/*
	
	var rowid=(obj.id).replace(/[A-Za-z_$-]/g, "");	
	
	if($("#forward_sales_information_tbody_row_crop_price__"+rowid).val()=="")
        		{
		//customAlerts("Please fill price", "success", 0);	
		return  false;
        		}
	else
		{
		if($("#forward_sales_information_tbody_row_quantity__"+rowid).val()!="")
		{
         var a=parseFloat($("#forward_sales_information_tbody_row_quantity__"+rowid).val())*parseFloat($("#forward_sales_information_tbody_row_crop_price__"+rowid).val().replace("$","").replace(/,/g, ''));
         $("#forward_sales_information_tbody_row_acres__"+rowid).val(a);
		}
		else if($("#forward_sales_information_tbody_row_acres__"+rowid).val()!="")
			{
			var a=parseFloat($("#forward_sales_information_tbody_row_acres__"+rowid).val())/parseFloat($("#forward_sales_information_tbody_row_crop_price__"+rowid).val().replace("$","").replace(/,/g, ''));
	         $("#forward_sales_information_tbody_row_quantity__"+rowid).val(a);
			
			}
		
        }*/
	/*end*/
	
}
function quantityCalForwardSale(obj)
{
	/*update By Bhagvan Singh for Plan_By_Field on 15-04-2015
	start*/
//	alert("quantityCalForwardSale");
	
	var rowid=(obj.id).replace(/[A-Za-z_$-]/g, "");	
	var forwardSaleCropName="";
	var	quantityValueForSelectedCrop=$("#forward_sales_information_tbody_row_quantity__"+rowid).val().replace(/,/g, '');
	if($("#forward_sales_information_tbody_row_quantity__"+rowid).val().replace(/,/g, '')!="")
	{
		$("#forward_sales_information_tbody_row_proposed__"+rowid).prop("checked",true);
		$("#forward_sales_information_tbody_row_firm__"+rowid).prop("checked",false);
		forwardSaleCropName=$("#forward_sales_information_tbody_row_crop_name__"+rowid).html();
		
		quantityValueForSelectedCrop=Number(quantityValueForSelectedCrop).toFixed(2);
		var rowlength=$("#crop_information_tbody > tr").length;
		 for(var i=1;i <= rowlength;i++)
		  {
			 cropName=$("#crop_info_details_field_crop_name__"+i).html();
//			 alert("cropName : "+cropName);
			 if(cropName.toLocaleLowerCase()==forwardSaleCropName.toLocaleLowerCase())
			 {
				 var expectedValueForSelectedCrop=$("#crop_info_yields_expected__"+i).val().replace(/,/g, '');
				 // expectedValueForSelectedCrop*acresValueForSelectedCrop;
				  var result=Number(quantityValueForSelectedCrop)/Number(expectedValueForSelectedCrop);
//				  alert("result : "+result);
				  
				  if(Math.round(result)<1)
					  {
			     $("#forward_sales_information_tbody_row_acres__"+rowid).val(0);
					  }
				  else
					  {
					  $("#forward_sales_information_tbody_row_acres__"+rowid).val(addCommaSignOnValue(Math.round(result)));
					  }
			 }
		}
	}
	
	 return $(obj).val(addCommaSignOnValue(quantityValueForSelectedCrop));
}
function acreCalForwardSale(obj)
{
	/*update By Bhagvan Singh for Plan_By_Field on 15-04-2015
	start*/
//	alert("acreCalForwardSale");
	
	var rowid=(obj.id).replace(/[A-Za-z_$-]/g, "");	
	var forwardSaleCropName="";
	var	acresValueForSelectedCrop="";	
	forwardSaleCropName=$("#forward_sales_information_tbody_row_crop_name__"+rowid).html();
	acresValueForSelectedCrop=$("#forward_sales_information_tbody_row_acres__"+rowid).val().replace(/,/g, '');
	acresValueForSelectedCrop=Number(acresValueForSelectedCrop).toFixed(2);
//	alert("forwardSaleCropName : "+forwardSaleCropName);
//var	acresValueForSelectedCrop=$("#forward_sales_information_tbody_row_acres__"+rowid).val().replace(/,/g, '');
	//alert("acresValueForSelectedCrop : "+acresValueForSelectedCrop);
  if($("#forward_sales_information_tbody_row_acres__"+rowid).val()!="")
  {
	var rowlength=$("#crop_information_tbody > tr").length;
	 for(var i=1;i <= rowlength;i++)
	  {
		 cropName=$("#crop_info_details_field_crop_name__"+i).html();
//		 alert("cropName : "+cropName);
		 if(cropName.toLocaleLowerCase()==forwardSaleCropName.toLocaleLowerCase())
		 {
			 var expectedValueForSelectedCrop=$("#crop_info_yields_expected__"+i).val().replace(/,/g, '');
			 // expectedValueForSelectedCrop*acresValueForSelectedCrop;
			 var result=Number(acresValueForSelectedCrop)*Number(expectedValueForSelectedCrop);
//			  alert("result : "+addCommaSignOnValueresult(result));
		     $("#forward_sales_information_tbody_row_quantity__"+rowid).val(addCommaSignOnValue(result.toFixed(2)));
		 }
	  }
  }
  return $(obj).val(addCommaSignOnValue(Math.round(acresValueForSelectedCrop)));
}

/*end
*/
/*add By Bhagvan Singh for Plan_By_Field on 07-04-2015
start*/
function showFarmInfoPagePrevious() {
	callMethodForPageChangeAndProgressBarImage(3, 0);
}

//end

/*update By Bhagvan Singh on 07-04-2015 for addCommaSignOnValue
start*/
function addCommaSignOnValue(id)
{

	if(id=="" )
	{
	
	}
   else
	{
    var value=id;
    var valueWithPoint=Number(value).toFixed(2);
  var valueWithComma;
  if(valueWithPoint % 1 != 0)
   {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');			
   }
  else
  {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');
	/*valueWithComma=valueWithComma+".00";			*/
  }
  // var finalValue=valueWithComma;
    return valueWithComma;
	}

	/*updated by Bhagvan Singh on 16-04-2015 for two floating point
	start*/
	//return Number(value).toFixed(2).toLocaleString('en');
	/*end*/
}
//end
/*update By Bhagvan Singh on 11-04-2015 for varible production cost
start*/
function addCommaSignWithDollar(id)
{
	if($(id).val()=="" )
	{
	
	}
   else
	{
    var value=$.trim("" + $(id).val().replace("$",""));
    var valueWithPoint=Number(value).toFixed(2);
  var valueWithComma;
  if(valueWithPoint % 1 != 0)
   {
	valueWithComma=Number(valueWithPoint).toLocaleString('en-IN');			
   }
  else
  {
	valueWithComma=Number(valueWithPoint).toLocaleString('en-IN');
	valueWithComma=valueWithComma+".00";			
  }
   var finalValue="$"+valueWithComma;
    return $(id).val(finalValue);
	}
}
//end

/*created By Bhagvan Singh on 13-04-2015
start*/
function fieldVarianceMaxMinCheck(crop_select_drop_down)
{
	
	var flag=true;
	var exp=$("#field_difference_exp").val();
	var min=$("#field_difference_min").val();
	var max=$("#field_difference_max").val();
	
	if ((exp!="" && min!="") || (exp!="" && max!=""))
		{
		
		if(Number(min) >= Number(exp))
		{
			
			customAlerts('Minimum Yield value should be less than Expected Value for "'+crop_select_drop_down+'" Crop', "error", 0);
			focusForValidation("field_difference_min");
			focusForValidation("field_difference_exp");
			flag=false;
		}
		else if(Number(max) <= Number(exp))
		{
			customAlerts('Maximum  value should be greater than Expected Value for "'+crop_select_drop_down+'" Crop', "error", 0);
			focusForValidation("field_difference_max");
			focusForValidation("field_difference_exp");
			flag=false;
        }
		
		
		}
return flag;
		
		}


function removeCrops()
{
	/*Updated by Bhagvan Singh on 15-04-2015
	Start*/
	var cropNameForRemoveSelect="";
	$('input[name="field_crop[]"]').each(function() {
		if($(this).is(":checked")){
			cropNameForRemoveSelect+=$(this).val()+",";
			
		}
	});
		
		$('input[name="vegitable_crop[]"]').each(function() {
			if($(this).is(":checked")){
				cropNameForRemoveSelect+=$(this).val()+",";
			}
		});
		var cropsSelect=cropNameForRemoveSelect.substring(0, cropNameForRemoveSelect.length-1);
	var cropNameForRemove="";
	
	if($('input[name="field_crop[]"]:checked').length > 0 || $('input[name="vegitable_crop[]"]:checked').length > 0)
	{
	if(confirm('Are you sure you want to remove "'+cropsSelect+'" crops?'))
	{
		$('input[name="field_crop[]"]').each(function() {
		if($(this).is(":checked")){
			cropNameForRemove+=$(this).val()+",";
			$(this).parent().remove();
			/*
			customAlerts("Crops removed successfully", "success", 0);*/
		}
		
	});
		$('input[name="vegitable_crop[]"]').each(function() {
			if($(this).is(":checked")){
				cropNameForRemove+=$(this).val()+",";
				$(this).parent().remove();
				/*
				customAlerts("Crops removed successfully", "success", 0);*/
			}
			
		});
		var crops=cropNameForRemove.substring(0, cropNameForRemove.length-1);
		customAlerts('"'+crops+'" Crops removed successfully', "success", 0);
	}/*else{
			$('input[name="field_crop[]"]').each(function() {
			if($(this).is(":checked")){
				$(this).removeAttr("checked");
				
			}
		});
			
			$('input[name="vegitable_crop[]"]').each(function() {
				if($(this).is(":checked")){
					$(this).removeAttr("checked");
				}
			});
			
	}*/}else{
		customAlerts("No crop selected to remove, Please  select atleast one Crop", "error", 0);
	}
	/*End*/
	
}

/*update By Bhagvan Singh on 11-04-2015 for varible production cost
start*/
function addCommaSignWithDollar(id)
{
	if($(id).val()=="" )
	{
	
	}
   else
	{
    var value=$.trim("" + $(id).val().replace('$', '').replace(/,/g, ''));
    var valueWithPoint=Number(value).toFixed(2);
  var valueWithComma;
  if(valueWithPoint % 1 != 0)
   {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');			
   }
  else
  {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');
	valueWithComma=valueWithComma+".00";			
  }
   var finalValue="$"+valueWithComma;
    return $(id).val(finalValue);
	}
}
function addCommaSignWithOutDollar(id)
{
	if($(id).val()=="" )
	{
	
	}
   else
	{
    var value=$.trim("" + $(id).val().replace('$', '').replace(/,/g, ''));
    var valueWithPoint=Number(value).toFixed(2);
  var valueWithComma;
  if(valueWithPoint % 1 != 0)
   {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');			
   }
  else
  {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');
	valueWithComma=valueWithComma+".00";			
  }
  // var finalValue=valueWithComma;
    return $(id).val(valueWithComma);
	}
}

/*created by Bhagvan Singh on 15-04-2015 for singal floating point
start*/
function addCommaSignWithForOnePoint(id)
{
	if($(id).val()=="" )
	{
	
	}
   else
	{
    var value=$.trim("" + $(id).val().replace('$', '').replace(/,/g, ''));
    var valueWithPoint=Number(value).toFixed(1);
  var valueWithComma;
  if(valueWithPoint % 1 != 0)
   {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');			
   }
  else
  {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');
	valueWithComma=valueWithComma+".0";			
  }
  // var finalValue=valueWithComma;
    return $(id).val(valueWithComma);
	}
}
/*end*/
function addCommaSignWithDollarWithValue(value)
{
	

	if(value=="" )
	{
	
	}
   else
	{
    var value=$.trim("" + value.replace('$', '').replace(/,/g, ''));
    var valueWithPoint=Number(value).toFixed(2);
  var valueWithComma;
  if(valueWithPoint % 1 != 0)
   {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');			
   }
  else
  {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');
	valueWithComma=valueWithComma+".00";			
  }
   var finalValue="$"+valueWithComma;
    return finalValue;
	}

}
//end


/*created By Bhagvan Singh on 17-04-2015 for total acres
start*/
function addCommaSignForAcres(id)
{
	if($(id).val()=="" )
	{
	
	}
   else
	{
    var value=$.trim("" + $(id).val().replace('$', '').replace(/,/g, ''));
    var valueWithPoint=Number(value).toFixed(2);
    if(valueWithPoint > 10000)
    {
    	customAlerts("The amount of land entered exceeds 10,000 acres", "warning", 0);
    }
  var valueWithComma;
  if(valueWithPoint % 1 != 0)
   {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');			
   }
  else
  {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');
	/*valueWithComma=valueWithComma+".00";*/			
  }
  // var finalValue=valueWithComma;
    return $(id).val(valueWithComma);
	}
}

/*update By Bhagvan Singh on 17-04-2015 for varible production cost
start*/
function addCommaSignInCapital(id)
{
	if($(id).val()=="" )
	{
	
	}
   else
	{
    var value=$.trim("" + $(id).val().replace('$', '').replace(/,/g, ''));
    var valueWithPoint=Number(value);
    var valueWithComma;
  if(valueWithPoint % 1 != 0)
   {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');			
   }
  else
  {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');
	valueWithComma=valueWithComma;			
  }
   var finalValue="$"+valueWithComma;
    return $(id).val(finalValue);
	}
}

//added by rohit on21-04-15
function addCommaSignWithOutDollarDot(id)
{
	if($(id).val()=="" )
	{
	
	}
   else
	{
    var value=$.trim("" + $(id).val().replace('$', '').replace(/,/g, ''));
    var valueWithPoint=Number(value).toFixed(2);
  var valueWithComma;
  if(valueWithPoint % 1 != 0)
   {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');			
   }
  else
  {
	valueWithComma=Number(valueWithPoint).toLocaleString('en');
	/*valueWithComma=valueWithComma+".00";		*/	
  }
  // var finalValue=valueWithComma;
    return $(id).val(valueWithComma);
	}
}

/*var availableCropsForGroups = new Array();
function insertCropsInAvailableCropArray() {
	for (var i = 1; i <= $("#crop_limits_table_tbody >tr").length; i++) {
		availableCropsForGroups.push($("#crop_limits_table_crop_name__" + i)
				.html().trim());
	}
	$.each(groupArray, function(key, value) {
		var crop_group_array = groupArray[key];
		for (var j = 0; j < crop_group_array.length; j++) {
			for (var k = 0; k < availableCropsForGroups.length; k++) {
				if (crop_group_array[j].toLowerCase == availableCropsForGroups[k].toLowerCase) {
//					console.log("Before "+availableCropsForGroups[k]);
					availableCropsForGroups.splice(k, 1);
//					console.log("After "+availableCropsForGroups[k]);
					break;
				}
			}
		}
	});
}


// created by rohit on 23-04-15
function addCropDiv()
{
	var cropName="";
	$("#group_name").val("");
	for(var i=0;i<availableCropsForGroups.length;i++)
	{
	   cropName+="<option value=\""+availableCropsForGroups[i]+"\">"+availableCropsForGroups[i]+"</option>";
	}
	$("#gropofcrop").html(cropName);
	$("#gropofcrop").multiselect('rebuild');
	div_show11();
}


function addNewGroup()
{
	var crop_groupName=$("#group_name").val().trim();
	var crop_selected_Length = $("#gropofcrop :selected").length;
	if (crop_groupName == "") {
		customAlerts("Please enter group name", "error", 0);
		return false;
	}
	if (crop_selected_Length == 0) {
		customAlerts("Please select atleast one crop", "error", 0);
		return false;
	}
	$("#add_group_table_tbody tr").each(function(){
		if ($(this).children("td:nth(1)").html().trim().toLowerCase() === crop_groupName.toLowerCase()) {
			customAlerts('"'+crop_groupName+'" group name is already exist', "error", 0);
			return false;
		}
	});
	if (!confirm("Are you sure you want to add a group name \""+crop_groupName+"\"?")) {
		div_hide11();
		return false;
	}
	var crop_groupArray = new Array();
	$("#gropofcrop :selected").each(function(){
		crop_groupArray.push($(this).val());
		for (var k = 0; k < availableCropsForGroups.length; k++) {
			if ($(this).val().toLowerCase == availableCropsForGroups[k].toLowerCase) {
//				console.log("before "+availableCropsForGroups[k]);
				availableCropsForGroups.splice(k, 1);
//				console.log("After "+availableCropsForGroups[k]);
				break;
			}
		}
	});
	groupArray[crop_groupName] = crop_groupArray;
	console.log("Group length in jsp "+groupArray.length);
	var rowCount = $("#add_group_table_tbody tr").length+1;
	var tbodyofgroup="";
	
	tbodyofgroup+="<tr class=\"tblbclgrnd text-center\" id=\"group_table_tbody_row_"+rowCount+"\">" +
	        "<td class=\"tblft1\"><input type=\"checkbox\" name=\"groupNameSelection[]\" id=\"group_crop_check_acres__"+rowCount+"\"></td>"+
			"<td class=\"tblft1\" id=\"group_table_group_name_"+rowCount+"\">"+crop_groupName+"</td>" +
			"<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" id=\"group_crop_maximum_acres__"+rowCount+"\"></td>" +
			"<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" id=\"group_crop_minimum_acres__"+rowCount+"\"></td>" +
			"</tr>";
	comment by Bhagvan Singh For get data from database
	start
	$('#add_group_table_tbody').append(tbodyofgroup);
	end
	div_hide11();
	customAlerts('"'+crop_groupName+'" group name is added successfully', "success", 0);
}

Added by Harshit on 23-04-2015
var group_to_modify = "";
function modifyGroupDiv()
{
	var group_name = "";
	if($("input[name='groupNameSelection[]']:checked").length < 1){
		customAlerts("Please select atleast one group for modify", "error", 0);
		return false;
	}else if($("input[name='groupNameSelection[]']:checked").length > 1){
		customAlerts("Please select only one group for modify", "error", 0);
		return false;
	}else if($("input[name='groupNameSelection[]']:checked").length == 1){
		group_name = $("input[name='groupNameSelection[]']:checked").parent().parent().children("td:nth(1)").html().trim();
		group_to_modify = group_name;
		var cropName="";
		$("#group_name").val(group_name);
		
		for(var i=0;i<groupArray[group_name].length;i++)
		{
			cropName+="<option value=\""+groupArray[group_name][i]+"\" selected=\"selected\">"+groupArray[group_name][i]+"</option>";
		}
		for(var i=0;i<availableCropsForGroups.length;i++)
		{
		   cropName+="<option value=\""+availableCropsForGroups[i]+"\">"+availableCropsForGroups[i]+"</option>";
		}
		$("#gropofcrop").html(cropName);
		$("#gropofcrop").multiselect('rebuild');
		div_show12();
	}	
}

function modifyGroup()
{
	var crop_groupName=$("#group_name").val().trim();
	var crop_selected_Length = $("#gropofcrop :selected").length;
	if (crop_groupName == "") {
		customAlerts("Please enter group name", "error", 0);
		return false;
	}
	if (crop_selected_Length == 0) {
		customAlerts("Please select atleast one crop", "error", 0);
		return false;
	}
	if (!confirm("Are you sure you want to modify group name \""+crop_groupName+"\"?")) {
		div_hide12();
		return false;
	}
	var group_name_find_length = 0;
	$("#add_group_table_tbody tr").each(function(){
		if ($(this).children("td:nth(1)").html().trim().toLowerCase() === crop_groupName.toLowerCase()) {
			group_name_find_length++;
		}if (group_name_find_length > 1) {
			customAlerts('"' + groupName + '" group name already exists for another group',
					"error", 0);
			return false;
		}
		
	});
	var availableCropsForGroups = new Array();
	$("#gropofcrop :not(:selected)").each(function(){
		availableCropsForGroups.push($(this).val());
	});
	
	var crop_groupArray = new Array();
	$("#gropofcrop :selected").each(function(){
		crop_groupArray.push($(this).val());
	});
	groupArray[crop_groupName] = crop_groupArray;
	$("#add_group_table_tbody tr").each(function(){
		if ($(this).children("td:nth(1)").html().trim().toLowerCase() === group_to_modify.toLowerCase()) {
			$(this).children("td:nth(1)").html(crop_groupName);
		}
	});
	div_hide12();
	customAlerts('"'+crop_groupName+'" group name updated successfully', "success", 0);
}

function removeGroup(){
	var groupNameForRemove="";
	if($("input[name='groupNameSelection[]']:checked").length == 0){
		customAlerts("Please select at one group", "error", 0);
		return false;
	}
	$("#add_group_table_tbody tr").each(function(){
		if($(this).children("td:nth(0)").find("input").prop("checked")){
			groupNameForRemove += $(this).children("td:nth(1)").html().trim()+", ";
		}
	});
	groupNameForRemove = groupNameForRemove.substring(0, groupNameForRemove.length-2);
	if (!confirm("Are you sure you want to remove selected group name \""+groupNameForRemove+"\"?")) {
		return false;
	}
	else{
		$("#add_group_table_tbody tr").each(function(){
			if($(this).children("td:nth(0)").find("input").prop("checked")){
				for(var i=0;i<groupArray[$(this).children("td:nth(1)").html().trim()].length;i++)
				{
					availableCropsForGroups.push(groupArray[$(this).children("td:nth(1)").html().trim()][i]);
				}
				delete groupArray[$(this).children("td:nth(1)").html().trim()];
				$(this).remove();
			}
		});
		customAlerts("Group with name "+groupNameForRemove+" has been deleted successfully", "success", 0);
	}
	var i=1;
	$("#add_group_table_tbody tr").each(function(){
		$(this).attr("id", "group_table_tbody_row_"+i);
		$(this).children("td:nth(0)").find("input").attr("id", "group_crop_check_acres__"+i);
		$(this).children("td:nth(1)").attr("id", "group_table_group_name_"+i);
		$(this).children("td:nth(2)").find("input").attr("id", "group_crop_maximum_acres__"+i);
		$(this).children("td:nth(3)").find("input").attr("id", "group_crop_minimum_acres__"+i);
		i++;
	});
}
End*/


function addCropDiv()
{
	var cropName = "";
	$("#group_name").val("");
	for (var i = 1; i <= $("#crop_limits_table_tbody >tr").length; i++) {
		cropName += "<option value=\""
				+ $("#crop_limits_table_crop_name__" + i).html().trim() + "\">"
				+ $("#crop_limits_table_crop_name__" + i).html().trim()
				+ "</option>";
	}
	$("#gropofcrop").html(cropName);
	$("#gropofcrop").multiselect('rebuild');
	div_show11();
}


function addNewGroup()
{
	var crop_groupName=$("#group_name").val().trim();
	var crop_selected_Length = $("#gropofcrop :selected").length;
	if (crop_groupName == "") {
		customAlerts("Please enter group name", "error", 0);
		return false;
	}
	if (crop_selected_Length == 0) {
		customAlerts("Please select atleast one crop", "error", 0);
		return false;
	}
	var nameCheckFlag = false;
	$("#add_group_table_tbody tr").each(function(){
		if ($(this).children("td:nth(1)").html().trim().toLowerCase() == crop_groupName.toLowerCase()) {
			customAlerts('"'+crop_groupName+'" group name is already exist', "error", 0);
			nameCheckFlag = true;
			return false;
		}
	});
	if(nameCheckFlag){
		return false;
	}
	if (!confirm("Are you sure you want to add a group name \""+crop_groupName+"\"?")) {
		div_hide11();
		return false;
	}
	var crop_groupArray = new Array();
	$("#gropofcrop :selected").each(function(){
		crop_groupArray.push($(this).val());
	});
	groupArray[crop_groupName] = crop_groupArray;
	// console.log("Group length in jsp "+groupArray.length);
//	alert("groupArray[crop_groupName]"+groupArray[crop_groupName]+"   :   "+crop_groupArray);
//	alert("groupArray.length"+groupArray.length);
	var rowCount = $("#add_group_table_tbody tr").length+1;
	var tbodyofgroup="";
	
	tbodyofgroup+="<tr class=\"tblbclgrnd text-center\" id=\"group_table_tbody_row_"+rowCount+"\">" +
	        "<td class=\"tblft1\"><input type=\"checkbox\" name=\"groupNameSelection[]\" id=\"group_crop_check_acres__"+rowCount+"\"></td>"+
			"<td class=\"tblft1\" id=\"group_table_group_name_"+rowCount+"\">"+crop_groupName+"</td>" +
			"<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" id=\"group_crop_maximum_acres__"+rowCount+"\"></td>" +
			"<td class=\"success croplimit\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" id=\"group_crop_minimum_acres__"+rowCount+"\"></td>" +
			"</tr>";
	/*comment by Bhagvan Singh For get data from database
	start*/
	$('#add_group_table_tbody').append(tbodyofgroup);
	/*end*/
	div_hide11();
	customAlerts('"'+crop_groupName+'" group name is added successfully', "success", 0);
}

/*Added by Harshit on 23-04-2015*/
var group_to_modify = "";
function modifyGroupDiv()
{
	var group_name = "";
	if ($("input[name='groupNameSelection[]']:checked").length < 1) {
		customAlerts("Please select atleast one group for modify", "error", 0);
		return false;
	} else if ($("input[name='groupNameSelection[]']:checked").length > 1) {
		customAlerts("Please select only one group for modify", "error", 0);
		return false;
	} else if ($("input[name='groupNameSelection[]']:checked").length == 1) {
		group_name = $("input[name='groupNameSelection[]']:checked").parent()
				.parent().children("td:nth(1)").html().trim();
		group_to_modify = group_name;
		var cropName = "";
		$("#group_name").val(group_name);
		for (var i = 1; i <= $("#crop_limits_table_tbody >tr").length; i++) {
			cropName += "<option value=\""
					+ $("#crop_limits_table_crop_name__" + i).html().trim()
					+ "\"";
			for (var j = 0; j < groupArray[group_name].length; j++) {
				if (groupArray[group_name][j] == $(
						"#crop_limits_table_crop_name__" + i).html().trim())
					cropName += "selected=\"selected\"";
			}
			cropName += ">"
					+ $("#crop_limits_table_crop_name__" + i).html().trim()
					+ "</option>";
		}
		$("#gropofcrop").html(cropName);
		$("#gropofcrop").multiselect('rebuild');
		div_show12();
	}
}

function modifyGroup()
{
	var crop_groupName=$("#group_name").val().trim();
	var crop_selected_Length = $("#gropofcrop :selected").length;
	if (crop_groupName == "") {
		customAlerts("Please enter group name", "error", 0);
		return false;
	}
	if (crop_selected_Length == 0) {
		customAlerts("Please select atleast one crop", "error", 0);
		return false;
	}
	var nameCheckFlag = false;
	$("#add_group_table_tbody tr").each(function(){
		if ($(this).children("td:nth(1)").html().trim().toLowerCase() == crop_groupName.toLowerCase() && group_to_modify.trim().toLowerCase() != crop_groupName.trim().toLowerCase()) {
			customAlerts('"'+crop_groupName+'" group name is already exist', "error", 0);
			nameCheckFlag = true;
			return false;
		}
	});
	if(nameCheckFlag){
		return false;
	}
	if (!confirm("Are you sure you want to modify group name \""+crop_groupName+"\"?")) {
		div_hide12();
		return false;
	}
	var group_name_find_length = 0;
	$("#add_group_table_tbody tr").each(function(){
		$(this).find('input:checkbox').removeAttr('checked');
		if ($(this).children("td:nth(1)").html().trim().toLowerCase() === crop_groupName.toLowerCase()) {
			group_name_find_length++;
		}if (group_name_find_length > 1) {
			customAlerts('"' + groupName + '" group name already exists for another group',
					"error", 0);
			return false;
		}
		
	});
	var crop_groupArray = new Array();
	$("#gropofcrop :selected").each(function(){
		crop_groupArray.push($(this).val());
	});
	groupArray[crop_groupName] = crop_groupArray;
	$("#add_group_table_tbody tr").each(function(){
		if ($(this).children("td:nth(1)").html().trim().toLowerCase() === group_to_modify.toLowerCase()) {
			$(this).children("td:nth(1)").html(crop_groupName);
		}
	});
	div_hide12();
	customAlerts('"'+crop_groupName+'" group name updated successfully', "success", 0);
}

function removeGroup(){
	var groupNameForRemove="";
	if($("input[name='groupNameSelection[]']:checked").length == 0){
		customAlerts("Please select at one group", "error", 0);
		return false;
	}
	$("#add_group_table_tbody tr").each(function(){
		if($(this).children("td:nth(0)").find("input").prop("checked")){
			groupNameForRemove += $(this).children("td:nth(1)").html().trim()+", ";
		}
	});
	groupNameForRemove = groupNameForRemove.substring(0, groupNameForRemove.length-2);
	if (!confirm("Are you sure you want to remove selected group name \""+groupNameForRemove+"\"?")) {
		return false;
	}
	else{
		$("#add_group_table_tbody tr").each(function(){
			if($(this).children("td:nth(0)").find("input").prop("checked")){
				$(this).remove();
			}
		});
		customAlerts("Group with name "+groupNameForRemove+" has been deleted successfully", "success", 0);
	}
	var i=1;
	$("#add_group_table_tbody tr").each(function(){
		$(this).attr("id", "group_table_tbody_row_"+i);
		$(this).children("td:nth(0)").find("input").attr("id", "group_crop_check_acres__"+i);
		$(this).children("td:nth(1)").attr("id", "group_table_group_name_"+i);
		$(this).children("td:nth(2)").find("input").attr("id", "group_crop_maximum_acres__"+i);
		$(this).children("td:nth(3)").find("input").attr("id", "group_crop_minimum_acres__"+i);
		i++;
	});
}

function lastCropForCropFieldChoice(){
	for (var i = 1; i < $("#plan-by-field-tbody tr").length; i++) {
		var lastCrop = $("#selected_last_crop____"+i).val().trim();
		var index=0;
		if(lastCrop != "No Crop"){
			$("#field_choice_crop_thead_row_first td").each(function(){
				if(lastCrop == $(this).html().trim()){
					index = $(this).index()+1;
					return false;
				}
			});
			$("#field_choice_crop_tbody tr").each(function(){
				if($(this).find('td:nth-child(1)').html().trim() == $("#row-field-name__"+i).html().trim()){
									$(this).find('td:nth-child(' + index+ ')').html(
									"<span class='last_crop' title='This crop has been selected as last crop.'>"+ $(this).find('td:nth-child('+ index
									+ ')').html()+ "</span>");
									return false;
				}
			});
		}
	}
}

/*End*/