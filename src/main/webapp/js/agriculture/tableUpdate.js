function createProcessForShowPlanByPage(){
	if ($("input[name='plan_by_farm']:checked").length > 0) {
		/*
		 * var irrigate_val=""; var evaluate_forward_sales_val=false; var
		 * evaluate_crop_insurance_val=false; if
		 * ($("input[name='irrigate']:checked").length > 0) {
		 * irrigate_val=$.trim("" + $('input[name=irrigate]:checked').val()); }
		 * if ($("input[name='evaluate_forward_sales']:checked").length > 0) {
		 * evaluate_forward_sales_val=$.trim("" +
		 * $('input[name=evaluate_forward_sales]:checked').val()); } if
		 * ($("input[name='evaluate_crop_insurance']:checked").length > 0) {
		 * evaluate_crop_insurance_val=$.trim("" +
		 * $('input[name=evaluate_crop_insurance]:checked').val()); } $.ajax({
		 * url : 'agriculture/farmController/updateFarmAtMiddle', type : 'POST',
		 * async : false, data : ({ farmId:farmId, irrigate_val : irrigate_val,
		 * evaluate_forward_sales_val : evaluate_forward_sales_val,
		 * evaluate_crop_insurance_val:evaluate_crop_insurance_val }), success :
		 * function(response) { var status = response.status; if (status ==
		 * 'success') { //alert("success"); } else if (status == 'Already
		 * exists') { alert("Farm Name is already registered, Choose another
		 * one!"); } }, error : function(XMLHttpRequest, status, message) {
		 * alert("Error" + XMLHttpRequest + ":" + status + ":" + message); }
		 * 
		 * });
		 */
		var flag = $.trim("" + $('input[name=plan_by_farm]:checked').val());
		crop_insurance_boolean = $.trim(""
				+ $('input[name=evaluate_crop_insurance]:checked').val());
		forward_sales_boolean = $.trim(""
				+ $('input[name=evaluate_forward_sales]:checked').val());
		evaluate_storage_sales_boolean = $.trim(""
				+ $('input[name=evaluate_storage_sales]:checked').val());
		if (flag == "fields") {
			strategy = flag;
			/* window.location = "plan_by_field.htm?farmId="+farmId+""; */
			// 26-02-15 start
			if (flagChange != "fields") {
				changeStrategyAndFrom();
				// $('input[name="field_crop[]"]').prop("checked", false);
				// $('input[name="vegitable_crop[]"]').prop("checked", false);
			}
			flagChange = "fields";
			manageStep1 = true;
			enableDisableMenu();
		} else if (flag == "acres") {
			strategy = flag;
			/* window.location = "plan_by_acres.htm?farmId="+farmId+""; */
			// 26-02-15 start
			if (flagChange != "acres") {
				changeStrategyAndFrom();
				// $('input[name="field_crop[]"]').prop("checked", false);
				// $('input[name="vegitable_crop[]"]').prop("checked", false);
			}
			flagChange = "acres";
			manageStep1 = true;
			enableDisableMenu();
		} else {
			div_show3();
		}
		/*
		 * 27-03-2015 By Harshit Gupta Start
		 */
		/*
		 * if (crop_insurance_boolean == "true") {
		 * $("#crop-insurance").css("display", ""); } else{
		 * $("#crop-insurance").css("display", "none"); } //
		 * alert(forward_sales_boolean); if (forward_sales_boolean == "true") {
		 * $("#previousOfMinMaxAcre").attr("onclick", "showForwardSalesPage()");
		 * $("#forward-sales").css("display", ""); } else{
		 * $("#previousOfMinMaxAcre").attr("onclick", "showBackwardPlan()");
		 * $("#forward-sales").css("display", "none"); }
		 */
	} else {
		customAlerts("Please select your strategy", "error", 0);

		return false;
	}
	return true;
}
function createProcessForShowCropInfoDetails(){
	var oldLengthOfFieldCrop = field_crop.length;
	var selectedCheckBoxLength = 0;
	// $("#crop_information_tbody").html("");
	// $("#optional_planting_date").html("");
	// $("#optional_crop_info_tbody").html("");
	$("input[name='vegitable_crop[]']:checked").each(function() {
		selectedCheckBoxLength++;
		var cropName = $(this).val();
		if (field_crop.length > 0) {
			var flag = true;
			for (var i = 0; i < field_crop.length; i++) {
				if (cropName.toLowerCase() === field_crop[i].toLowerCase()) {
					// alert("crop found");
					flag = false;
				}

			}
			if (flag) {
				field_crop.push(cropName);
			}
		} else {
			// alert("crop inserted at outside : "+cropName);
			field_crop.push(cropName);
		}
	});
	$("input[name='field_crop[]']:checked").each(function() {
		selectedCheckBoxLength++;
		var cropName = $(this).val();
		if (field_crop.length > 0) {
			var flag = true;
			for (var i = 0; i < field_crop.length; i++) {
				if (cropName.toLowerCase() === field_crop[i].toLowerCase()) {
					// alert("crop found");
					flag = false;
				}

			}
			if (flag) {
				field_crop.push(cropName);
			}
		} else {
			// alert("crop inserted at outside : "+cropName);
			field_crop.push(cropName);
		}

	});
	if (selectedCheckBoxLength == 0) {
		customAlerts("Select the crops you are considering planting ", "error", 0);
		/*comment by Bhagvan Singh
		start*/
		 /*You will not be able to continue to the analysis without selecting at least one crop. If you select only one crop, planting profit will only be able to recommend planting that crop in the analysis*/
		/*end*/
		return false;
	}
	var myTestArray = new Array();
	for (var i = 0; i < field_crop.length; i++) {
		var flag = false;
		$("input[name='field_crop[]']:checked").each(function() {
			var cropName = $(this).val();
			// alert("Field crop select crop : "+cropName);
			if (cropName.toLowerCase() === field_crop[i].toLowerCase()) {
				flag = true;
				// alert("crop match : "+cropName);
			}
		});
		$("input[name='vegitable_crop[]']:checked").each(function() {
			var cropName = $(this).val();
			// alert("vegitable crop select crop : "+cropName);
			if (cropName.toLowerCase() === field_crop[i].toLowerCase()) {
				flag = true;
				// alert("crop match : "+cropName);
			}
		});
		if (!flag) {
			myTestArray.push(i);
		}
		// alert(flag);
	}

	// alert("My Array Length : "+myTestArray.length);
	var textNowFlag = 0;
	// alert("textNowFlag : "+ textNowFlag);
	for (var i = 0; i < myTestArray.length; i++) {
		myTestArray[i] = parseInt(myTestArray[i]);
		myTestArray[i] = myTestArray[i] - textNowFlag;
		// oldLengthOfFieldCrop=oldLengthOfFieldCrop-1;
		// alert("item is : "+field_crop[myTestArray[i]]);
		// oldLengthOfFieldCrop=oldLengthOfFieldCrop-1;
		oldLengthOfFieldCrop = oldLengthOfFieldCrop - 1;
		field_crop.splice(myTestArray[i], 1);
		// alert("field_crop.length ="+field_crop.length);

		var deletedRowCount = $('#crop_information_tbody > tr').length;
		// alert("deletedRowCount : "+deletedRowCount);
		if (deletedRowCount > 0) {
			var newCropInfoTbody = "";
			var newOptionalPlantingDateTbody = "";
			var newOptionalCropInfoTbody = "";
			var rowCount = 1;
			for (var j = 1; j <= deletedRowCount; j++) {
				if (j != (myTestArray[i] + 1)) {
					if (rowCount % 2 == 0) {
						newCropInfoTbody += "<tr class=\"tblgrn text-center\" id=\"crop_info_details__"
								+ rowCount
								+ "\"><td class=\"tblft1\" id=\"crop_info_details_field_crop_name__"
								+ rowCount
								+ "\">"
								+ $("#crop_info_details_field_crop_name__" + j)
										.html()
								+ "</td><td class=\"success uomtext\"><select class=\"crop_selection_UOM\" id=\"crop_selection_UOM__"
								+ rowCount
								+ "\"> "
								+ "<option value=\"bushels\">bushels</option><option value=\"crates\">crates</option><option value=\"hundredweight\">hundredweight</option><option value=\"kilograms\">kilograms</option><option value=\"pounds\">pounds</option><option value=\"tons\">tons</option>	</select></td>"
								+ "<td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithForOnePoint(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_yields_expected__"
								+ rowCount
								+ "\" name=\"Crop\" value='"
								+ $("#crop_info_yields_expected__" + j).val()
								+ "' /></td><td class=\"success infotext\"><input onkeypress=\"return isValidNumberValue(event)\" onchange='addCommaSignWithForOnePoint(this)' id=\"crop_info_yields_max__"
								+ rowCount
								+ "\" type=\"text\" value='"
								+ $("#crop_info_yields_max__" + j).val()
								+ "'	name=\"Crop\"></td><td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithForOnePoint(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_yields_min__"
								+ rowCount
								+ "\" value='"
								+ $("#crop_info_yields_min__" + j).val()
								+ "'	name=\"Crop\"></td>	"
								+ "<td class=\"success infotext\"><input onkeypress=\"return isValidNumberValue(event)\" onchange='addCommaSignWithDollar(this)' id=\"crop_info_price_expected__"
								+ rowCount
								+ "\"  type=\"text\" value='"
								+ $("#crop_info_price_expected__" + j).val()
								+ "' name=\"Crop\"></td><td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithDollar(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_price_max__"
								+ rowCount
								+ "\" value='"
								+ $("#crop_info_price_max__" + j).val()
								+ "' name=\"Crop\"></td><td class=\"success infotext\"><input onkeypress=\"return isValidNumberValue(event)\" onchange='addCommaSignWithDollar(this)' type=\"text\" id=\"crop_info_price_min__"
								+ rowCount
								+ "\" value='"
								+ $("#crop_info_price_min__" + j).val()
								+ "' name=\"Crop\"></td>	"
								+ "<td class=\"success infotext\" ><br /><input type=\"text\" onchange=\"addCommaSignWithDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" placeholder=\"$0\" value='"+ $("#calulated_cost_of_production__"+ $("#crop_info_details_field_crop_name__" + j).html().replace(/\s+/g, '')).val()	+ "' id=\"calulated_cost_of_production__"+ $("#crop_info_details_field_crop_name__" + j).html().replace(/\s+/g, '')+ "\"></input><br />	<span class=\"pull-right\"><a onclick=\"getProductionCostDetails("
								+ rowCount
								+ ",'"
								+ $("#crop_info_details_field_crop_name__" + j)
										.html()
								+ "')\">Details</a></span></td></tr>";

						newOptionalPlantingDateTbody += "<tr class=\"tblgrn text-center\" id=\"optional_planting_date_row__"
								+ rowCount
								+ "\"><td class=\"tblft1\" id=\"optional_planting_date_field_crop_name__"
								+ rowCount
								+ "\">"
								+ $(
										"#optional_planting_date_field_crop_name__"
												+ j).html()
								+ "</td><td class=\"success infotext\"><input class=\"datepicker\" type=\"text\" id=\"optional_planting_date_preferred_planting_date__"
								+ rowCount
								+ "\" value='"
								+ $(
										"#optional_planting_date_preferred_planting_date__"
												+ j).val()
								+ "' name=\"Crop\"></td>"
								+ "<td class=\"success infotext\"><input class=\"datepicker\" type=\"text\" value='"
								+ $(
										"#optional_planting_date_early_planting_date__"
												+ j).val()
								+ "' id=\"optional_planting_date_early_planting_date__"
								+ rowCount
								+ "\" name=\"Crop\"></td><td class=\"success infotext\"><input class=\"datepicker\" value='"
								+ $(
										"#optional_planting_date_late_planting_date__"
												+ j).val()
								+ "' type=\"text\" id=\"optional_planting_date_late_planting_date__"
								+ rowCount
								+ "\" name=\"Crop\"></td>	<td class=\"success infotext\"><input value='"
								+ $(
										"#optional_planting_date_length_of_session_days__"
												+ j).val()
								+ "' type=\"text\" onkeypress=\"return isValidNumberValue(event)\" id=\"optional_planting_date_length_of_session_days__"
								+ rowCount + "\"	name=\"Crop\"></td>	</tr>";

						var optional_crop_info_irrigated = "";
						var optional_crop_info_conservation_practice = "";
						var optional_crop_info_hi_risk_crop = "";

						if ($('#optional_crop_info_irrigated__' + j).is(
								':checked')) {
							optional_crop_info_irrigated = "checked";
						}
						if ($('#optional_crop_info_conservation_practice__' + j)
								.is(':checked')) {
							optional_crop_info_conservation_practice = "checked";
						}
						if ($('#optional_crop_info_hi_risk_crop__' + j).is(
								':checked')) {
							optional_crop_info_hi_risk_crop = "checked";
						}
						newOptionalCropInfoTbody += "<tr class=\"tblgrn text-center\" id=\"optional_crop_info__"
								+ rowCount
								+ "\"><td class=\"tblft1\" id=\"optional_crop_info_field_crop_name__"
								+ rowCount
								+ "\">"
								+ $("#optional_crop_info_field_crop_name__" + j)
										.html()
								+ "</td><td class=\"success\"><input type=\"checkbox\"  "
								+ optional_crop_info_irrigated
								+ "   id=\"optional_crop_info_irrigated__"
								+ rowCount
								+ "\" /></td><td class=\"success\"><input type=\"checkbox\"  "
								+ optional_crop_info_conservation_practice
								+ "  id=\"optional_crop_info_conservation_practice__"
								+ rowCount
								+ "\" /></td>"
								+ "<td class=\"success\"><input type=\"checkbox\"  "
								+ optional_crop_info_hi_risk_crop
								+ "  id=\"optional_crop_info_hi_risk_crop__"
								+ rowCount + "\" /></td></tr>";

					}

					else {
						newCropInfoTbody += "<tr class=\"tblbclgrnd text-center\" id=\"crop_info_details__"
								+ rowCount
								+ "\"><td class=\"tblft1\" id=\"crop_info_details_field_crop_name__"
								+ rowCount
								+ "\">"
								+ $("#crop_info_details_field_crop_name__" + j)
										.html()
								+ "</td><td class=\"success uomtext\"><select class=\"crop_selection_UOM\" id=\"crop_selection_UOM__"
								+ rowCount
								+ "\"> "
								+ "<option value=\"bushels\">bushels</option><option value=\"crates\">crates</option><option value=\"hundredweight\">hundredweight</option><option value=\"kilograms\">kilograms</option><option value=\"pounds\">pounds</option><option value=\"tons\">tons</option></select></td>"
								+ "<td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithForOnePoint(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_yields_expected__"
								+ rowCount
								+ "\" name=\"Crop\" value='"
								+ $("#crop_info_yields_expected__" + j).val()
								+ "' /></td><td class=\"success infotext\"><input onchange='addCommaSignWithForOnePoint(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_yields_max__"
								+ rowCount
								+ "\" type=\"text\" value='"
								+ $("#crop_info_yields_max__" + j).val()
								+ "'	name=\"Crop\"></td><td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithForOnePoint(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_yields_min__"
								+ rowCount
								+ "\" value='"
								+ $("#crop_info_yields_min__" + j).val()
								+ "'	name=\"Crop\"></td>"
								+ "<td class=\"success infotext\"><input onchange='addCommaSignWithDollar(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_price_expected__"
								+ rowCount
								+ "\"  type=\"text\" value='"
								+ $("#crop_info_price_expected__" + j).val()
								+ "' name=\"Crop\"></td>	<td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithDollar(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_price_max__"
								+ rowCount
								+ "\" value='"
								+ $("#crop_info_price_max__" + j).val()
								+ "' name=\"Crop\"></td><td class=\"success infotext\"><input  onchange='addCommaSignWithDollar(this)' onkeypress=\"return isValidNumberValue(event)\" type=\"text\" id=\"crop_info_price_min__"
								+ rowCount
								+ "\" value='"
								+ $("#crop_info_price_min__" + j).val()
								+ "' name=\"Crop\"></td>"
								+ "<td class=\"success infotext\" ><br><input type=\"text\" onchange=\"addCommaSignWithDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" placeholder=\"$0\" value='"	+ $("#calulated_cost_of_production__"+ $("#crop_info_details_field_crop_name__" + j).html().replace(/\s+/g, '')).val()	+ "' id=\"calulated_cost_of_production__"
								+ $("#crop_info_details_field_crop_name__" + j)
										.html().replace(/\s+/g, '')
								+ "\"></input><br />	<span class=\"pull-right\"><a onclick=\"getProductionCostDetails("
								+ rowCount
								+ ",'"
								+ $("#crop_info_details_field_crop_name__" + j)
										.html()
								+ "')\">Details</a></span></td></tr>";

						newOptionalPlantingDateTbody += "<tr class=\"tblbclgrnd text-center\" id=\"optional_planting_date_row__"
								+ rowCount
								+ "\"><td class=\"tblft1\" id=\"optional_planting_date_field_crop_name__"
								+ rowCount
								+ "\">"
								+ $(
										"#optional_planting_date_field_crop_name__"
												+ j).html()
								+ "</td><td class=\"success infotext\"><input class=\"datepicker\" type=\"text\" id=\"optional_planting_date_preferred_planting_date__"
								+ rowCount
								+ "\" value='"
								+ $(
										"#optional_planting_date_preferred_planting_date__"
												+ j).val()
								+ "' name=\"Crop\"></td>"
								+ "<td class=\"success infotext\"><input class=\"datepicker\" type=\"text\" value='"
								+ $(
										"#optional_planting_date_early_planting_date__"
												+ j).val()
								+ "' id=\"optional_planting_date_early_planting_date__"
								+ rowCount
								+ "\" name=\"Crop\"></td><td class=\"success infotext\"><input class=\"datepicker\" value='"
								+ $(
										"#optional_planting_date_late_planting_date__"
												+ j).val()
								+ "' type=\"text\" id=\"optional_planting_date_late_planting_date__"
								+ rowCount
								+ "\" name=\"Crop\"></td>	<td class=\"success infotext\"><input value='"
								+ $(
										"#optional_planting_date_length_of_session_days__"
												+ j).val()
								+ "' type=\"text\" onkeypress=\"return isValidNumberValue(event)\" id=\"optional_planting_date_length_of_session_days__"
								+ rowCount + "\"	name=\"Crop\"></td>	</tr>";

						var optional_crop_info_irrigated = "";
						var optional_crop_info_conservation_practice = "";
						var optional_crop_info_hi_risk_crop = "";

						if ($('#optional_crop_info_irrigated__' + j).is(
								':checked')) {
							optional_crop_info_irrigated = "checked";
						}
						if ($('#optional_crop_info_conservation_practice__' + j)
								.is(':checked')) {
							optional_crop_info_conservation_practice = "checked";
						}
						if ($('#optional_crop_info_hi_risk_crop__' + j).is(
								':checked')) {
							optional_crop_info_hi_risk_crop = "checked";
						}
						newOptionalCropInfoTbody += "<tr class=\"tblbclgrnd text-center\" id=\"optional_crop_info__"
								+ rowCount
								+ "\"><td class=\"tblft1\" id=\"optional_crop_info_field_crop_name__"
								+ rowCount
								+ "\">"
								+ $("#optional_crop_info_field_crop_name__" + j)
										.html()
								+ "</td><td class=\"success\"><input type=\"checkbox\"  "
								+ optional_crop_info_irrigated
								+ "   id=\"optional_crop_info_irrigated__"
								+ rowCount
								+ "\" /></td><td class=\"success\"><input type=\"checkbox\"  "
								+ optional_crop_info_conservation_practice
								+ "  id=\"optional_crop_info_conservation_practice__"
								+ rowCount
								+ "\" /></td>"
								+ "<td class=\"success\"><input type=\"checkbox\"  "
								+ optional_crop_info_hi_risk_crop
								+ "  id=\"optional_crop_info_hi_risk_crop__"
								+ rowCount + "\" /></td></tr>";
					}
					rowCount++;
				}
			}
			$("#crop_info_details__" + (myTestArray[i] + 1)).remove();
			$("#optional_planting_date_row__" + (myTestArray[i] + 1)).remove();
			$("#optional_crop_info__" + (myTestArray[i] + 1)).remove();
			$("#crop_information_tbody").html(newCropInfoTbody);
			$("#optional_planting_date").html(newOptionalPlantingDateTbody);
			$("#optional_crop_info_tbody").html(newOptionalCropInfoTbody);

		}
		textNowFlag++;
	}
	var total = oldLengthOfFieldCrop;
	var cropInfoTbody = "";
	var optionalPlantingDateTbody = "";
	var optionalCropInfoTbody = "";
	// alert("oldLengthOfFieldCrop = "+oldLengthOfFieldCrop);
	// alert("field_crop = "+field_crop.length);
	for (var i = oldLengthOfFieldCrop; i < field_crop.length; i++) {
		total = total + 1;
		if (total % 2 == 0) {
			cropInfoTbody += "<tr class=\"tblgrn text-center\" id=\"crop_info_details__"
					+ total
					+ "\"><td class=\"tblft1\" id=\"crop_info_details_field_crop_name__"
					+ total
					+ "\">"
					+ field_crop[i]
					+ "</td><td class=\"success uomtext\"><select class=\"crop_selection_UOM\" id=\"crop_selection_UOM__"
					+ total
					+ "\"> "
					+ "<option value=\"bushels\">bushels</option><option value=\"crates\">crates</option><option value=\"hundredweight\">hundredweight</option><option value=\"kilograms\">kilograms</option><option value=\"pounds\">pounds</option><option value=\"tons\">tons</option>	</select></td>"
					+ "<td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithForOnePoint(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_yields_expected__"
					+ total
					+ "\" name=\"Crop\"  /></td>	<td class=\"success infotext\"><input onchange='addCommaSignWithForOnePoint(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_yields_max__"
					+ total
					+ "\" type=\"text\" 	name=\"Crop\"></td><td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithForOnePoint(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_yields_min__"
					+ total
					+ "\" 	name=\"Crop\"></td>"
					+ "<td class=\"success infotext\"><input onchange='addCommaSignWithDollar(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_price_expected__"
					+ total
					+ "\"  type=\"text\"  name=\"Crop\"></td><td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithDollar(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_price_max__"
					+ total
					+ "\"  name=\"Crop\"></td><td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithDollar(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_price_min__"
					+ total
					+ "\"  name=\"Crop\"></td>	"
					+ "<td class=\"success infotext\" ><br><input  placeholder=\"$0\" type=\"text\" onchange=\"addCommaSignWithDollar(this)\" onkeypress=\"return isValidNumberValue(event)\" id=\"calulated_cost_of_production__"
					+ field_crop[i].replace(/\s+/g, '')
					+ "\"></input><br />	<span class=\"pull-right\"><a onclick=\"getProductionCostDetails("
					+ total
					+ ",'"
					+ field_crop[i]
					+ "')\">Details</a></span></td></tr>";

			optionalPlantingDateTbody += "<tr class=\"tblgrn text-center\" id=\"optional_planting_date_row__"
					+ total
					+ "\"><td class=\"tblft1\" id=\"optional_planting_date_field_crop_name__"
					+ total
					+ "\">"
					+ field_crop[i]
					+ "</td><td class=\"success infotext\"><input type=\"text\" class=\"datepicker\" id=\"optional_planting_date_preferred_planting_date__"
					+ total
					+ "\" name=\"Crop\"></td>"
					+ "<td class=\"success infotext\"><input type=\"text\" class=\"datepicker\" class=\"datepicker\" id=\"optional_planting_date_early_planting_date__"
					+ total
					+ "\" name=\"Crop\"></td><td class=\"success infotext\"><input type=\"text\" class=\"datepicker\" id=\"optional_planting_date_late_planting_date__"
					+ total
					+ "\" name=\"Crop\"></td>	<td class=\"success infotext\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" id=\"optional_planting_date_length_of_session_days__"
					+ total + "\"	name=\"Crop\"></td>	</tr>";

			optionalCropInfoTbody += "<tr class=\"tblgrn text-center\" id=\"optional_crop_info__"
					+ total
					+ "\"><td class=\"tblft1\" id=\"optional_crop_info_field_crop_name__"
					+ total
					+ "\">"
					+ field_crop[i]
					+ "</td><td class=\"success\"><input type=\"checkbox\" id=\"optional_crop_info_irrigated__"
					+ total
					+ "\" /></td><td class=\"success\"><input type=\"checkbox\" id=\"optional_crop_info_conservation_practice__"
					+ total
					+ "\" /></td>"
					+ "<td class=\"success\"><input type=\"checkbox\" id=\"optional_crop_info_hi_risk_crop__"
					+ total + "\" /></td></tr>";
		}

		else {
			cropInfoTbody += "<tr class=\"tblbclgrnd text-center\" id=\"crop_info_details__"
					+ total
					+ "\"><td class=\"tblft1\" id=\"crop_info_details_field_crop_name__"
					+ total
					+ "\">"
					+ field_crop[i]
					+ "</td><td class=\"success uomtext\"><select class=\"crop_selection_UOM\" id=\"crop_selection_UOM__"
					+ total
					+ "\"> "
					+ "<option value=\"bushels\">bushels</option><option value=\"crates\">crates</option><option value=\"hundredweight\">hundredweight</option><option value=\"kilograms\">kilograms</option><option value=\"pounds\">pounds</option><option value=\"tons\">tons</option>	</select></td>"
					+ "<td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithForOnePoint(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_yields_expected__"
					+ total
					+ "\" name=\"Crop\"  /></td>	<td class=\"success infotext\"><input onchange='addCommaSignWithForOnePoint(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_yields_max__"
					+ total
					+ "\" type=\"text\"	name=\"Crop\"></td><td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithForOnePoint(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_yields_min__"
					+ total
					+ "\" 	name=\"Crop\"></td>"
					+ "<td class=\"success infotext\"><input onchange='addCommaSignWithDollar(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_price_expected__"
					+ total
					+ "\"  type=\"text\" name=\"Crop\"></td><td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithDollar(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_price_max__"
					+ total
					+ "\" name=\"Crop\"></td><td class=\"success infotext\"><input type=\"text\" onchange='addCommaSignWithDollar(this)' onkeypress=\"return isValidNumberValue(event)\" id=\"crop_info_price_min__"
					+ total
					+ "\"  name=\"Crop\"></td>	"
					+ "<td class=\"success infotext\"><br><input type=\"text\" onchange=\"addCommaSignWithDollar(this)\"  placeholder=\"$0\" onkeypress=\"return isValidNumberValue(event)\" id=\"calulated_cost_of_production__"
					+ field_crop[i].replace(/\s+/g, '')
					+ "\"></span> <br />	<span class=\"pull-right\"><a onclick=\"getProductionCostDetails("
					+ total
					+ ",'"
					+ field_crop[i]
					+ "')\">Details</a></span></td></tr>";

			optionalPlantingDateTbody += "<tr class=\"tblbclgrnd text-center\" id=\"optional_planting_date_row__"
					+ total
					+ "\"><td class=\"tblft1\" id=\"optional_planting_date_field_crop_name__"
					+ total
					+ "\">"
					+ field_crop[i]
					+ "</td><td class=\"success infotext\"><input type=\"text\" class=\"datepicker\" id=\"optional_planting_date_preferred_planting_date__"
					+ total
					+ "\" name=\"Crop\"></td>"
					+ "<td class=\"success infotext\"><input class=\"datepicker\" type=\"text\" id=\"optional_planting_date_early_planting_date__"
					+ total
					+ "\" name=\"Crop\"></td><td class=\"success infotext\"><input type=\"text\" class=\"datepicker\" id=\"optional_planting_date_late_planting_date__"
					+ total
					+ "\" name=\"Crop\"></td>	<td class=\"success infotext\"><input type=\"text\" onkeypress=\"return isValidNumberValue(event)\" id=\"optional_planting_date_length_of_session_days__"
					+ total + "\"	name=\"Crop\"></td>	</tr>";

			optionalCropInfoTbody += "<tr class=\"tblbclgrnd text-center\" id=\"optional_crop_info__"
					+ total
					+ "\"><td class=\"tblft1\" id=\"optional_crop_info_field_crop_name__"
					+ total
					+ "\">"
					+ field_crop[i]
					+ "</td><td class=\"success\"><input type=\"checkbox\" id=\"optional_crop_info_irrigated__"
					+ total
					+ "\" /></td><td class=\"success\"><input type=\"checkbox\" id=\"optional_crop_info_conservation_practice__"
					+ total
					+ "\" /></td>"
					+ "<td class=\"success\"><input type=\"checkbox\" id=\"optional_crop_info_hi_risk_crop__"
					+ total + "\" /></td></tr>";

		}

	}
	/*
	 * for(var i=oldLengthOfVegitableCrop;i<vegitable_crop.length;i++) {
	 * total=total+1; //alert(total); if(total % 2 == 0) { cropInfoTbody+="<tr class=\"tblgrn text-center\" id=\"crop_info_details__"+total+"\"><td class=\"tblft1\">"+vegitable_crop[i]+"</td><td class=\"success uomtext\">pre-pop
	 * based on crop type</td>" + "<td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td><td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td> <td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td>" + "<td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td><td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td> <td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td>" + "<td class=\"success infotext\" id=\"calulated_cost_of_production__"+total+"\">$xxxxxxxx<br />
	 * <span class=\"pull-right\"><a
	 * onclick=\"getProductionCostDetails("+total+")\">Detail</a></span></td></tr>";
	 * 
	 * optionalPlantingDateTbody+="<tr class=\"tblgrn text-center\" id=\"optional_planting_date_row__"+total+"\"><td class=\"tblft1\">"+vegitable_crop[i]+"</td><td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td>" + "<td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td><td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td> <td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td> </tr>";
	 * 
	 * optionalCropInfoTbody+="<tr class=\"tblgrn text-center\" id=\"optional_crop_info__"+total+"\"><td class=\"tblft1\">"+vegitable_crop[i]+"</td><td class=\"success\"><input
	 * type=\"checkbox\"></td><td class=\"success\"><input type=\"checkbox\"></td>" + "<td class=\"success\"><input
	 * type=\"checkbox\"></td></tr>"; }
	 * 
	 * else { cropInfoTbody+="<tr class=\"tblbclgrnd text-center\" id=\"crop_info_details__"+total+"\"><td class=\"tblft1\">"+vegitable_crop[i]+"</td><td class=\"success uomtext\">pre-pop
	 * based on crop type</td>" + "<td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td><td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td> <td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td>" + "<td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td><td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td> <td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td>" + "<td class=\"success infotext\" id=\"calulated_cost_of_production__"+total+"\">$xxxxxxxx<br />
	 * <span class=\"pull-right\"><a
	 * onclick=\"getProductionCostDetails("+total+")\">Detail</a></span></td></tr>";
	 * 
	 * optionalPlantingDateTbody+="<tr class=\"tblbclgrnd text-center\" id=\"optional_planting_date_row__"+total+"\"><td class=\"tblft1\">"+vegitable_crop[i]+"</td><td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td>" + "<td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td><td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td> <td class=\"success infotext\"><input
	 * type=\"text\" name=\"Crop\"></td> </tr>";
	 * 
	 * optionalCropInfoTbody+="<tr class=\"tblbclgrnd text-center\" id=\"optional_crop_info__"+total+"\"><td class=\"tblft1\">"+vegitable_crop[i]+"</td><td class=\"success\"><input
	 * type=\"checkbox\"></td><td class=\"success\"><input type=\"checkbox\"></td>" + "<td class=\"success\"><input
	 * type=\"checkbox\"></td></tr>"; } }
	 */
	$("#crop_information_tbody").append(cropInfoTbody);
	$("#optional_planting_date").append(optionalPlantingDateTbody);
	$("#optional_crop_info_tbody").append(optionalCropInfoTbody);

	

	var rowLengthForDrop=$("#plan-by-field-tbody > tr").length;
		 for(var i=1;i<=rowLengthForDrop;i++)
			 {
			  var optionSelectedValue="";
			  optionSelectedValue=$.trim("" + $('#selected_last_crop____' + i).val());
			  var selectedValue="";
				if(optionSelectedValue=="No Crop")
					{
					selectedValue="selected";
					}
				var newHtml="<option value=\"No Crop\" "+selectedValue+">No Crop</option>";
					
					
				  for (var j = 0; j < field_crop.length; j++)
					  {
					  selectedValue="";
					  if(optionSelectedValue==field_crop[j])
						{
						selectedValue="selected";
						}
					  newHtml+="<option value=\""+field_crop[j]+"\" "+selectedValue+">"+field_crop[j]+"</option>";
					  }
				  $("#selected_last_crop____"+i).html(newHtml); 
			 }
		 return true;
}

function createProcessForShowFieldCropPage() {
	var isExpValid = cropInformationDetailsMaxMinCheck();
	if (!isExpValid) {
		return false;
	}
	var rowCount = $("#crop_information_tbody > tr").length;
	for (var i = 1; i <= rowCount; i++) {
//		alert($("#calulated_cost_of_production__"+ $("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).val()+"-----------"+ $("#crop_info_details_field_crop_name__" + i).html());
		if(($("#calulated_cost_of_production__"+ $("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).val()=="$0") || ($("#calulated_cost_of_production__"+ $("#crop_info_details_field_crop_name__" + i).html().replace(/\s+/g, '')).val()=="$0.00"))
		{
			/*+$("#crop_info_details_field_crop_name__" + i).html()*/
		    var r = confirm("$0 is not a valid value but if you want to continue then click ok otherwise cancel");
		    if (r == true) {
		    	
		    } else {
		    	return false;
		    }
			//customAlerts("Please enter production cost for " + cropName, "error", 0);
		}
		crop_uom[$.trim(""+ $('#crop_info_details_field_crop_name__' + i).html())] = $("#crop_selection_UOM__" + i + " option:selected").val();
	}
	for (var j = 1; j <= rowCount; j++) {
		var cropName = $.trim(""
				+ $('#crop_info_details_field_crop_name__' + j).html());
		var newCropName = cropName.replace(/\s+/g, '');
		var varName = $.trim(""+ $('#calulated_cost_of_production__' + newCropName).val());
		varName = varName.replace('$', '');
		// alert("var Name"+varName);
		 //alert("var Name length "+varName.length);
		if (isExpValid) {
		if (varName.length == "0" || !validateNumberOnly(varName)) {
			customAlerts('Please enter production cost for "' + cropName+'" crop name', "error", 0);

			return false;
		} else {
			crop_total_production_cost[cropName] = varName;
		}
	}
	}
	/*
	 * $.each(crop_uom, function(k, v) { alert("Key = "+k); alert("Value =
	 * "+crop_uom[k]); });
	 */
	if (strategy == "fields") {
		
		
		div_hidePlanByField();
		
		var newTheadHtml = "";
		var theadRowLength = $("#field_choice_crop_thead > tr").length;
		var theadColumnLength = 1;
		if (theadRowLength == "0") {
			newTheadHtml += "<tr id=\"field_choice_crop_thead_row_first\" class=\"tblhd text-center add-fieldi\"><td id=\"field_choice_crop_thead_row_column__1\" class=\"tblbrdr text-center add-fieldi\">Field/Crop</td>";
			// theadColumnLength=$("#field_choice_crop_thead_row_first >
			// td").length;
		}
		if (theadRowLength != "0") {
			// newTheadHtml+="<tr id=\"field_choice_crop_thead_row_first\"
			// class=\"tblhd text-center add-fieldi\"><td
			// id=\"field_choice_crop_thead_row_column__1\" class=\"tblbrdr
			// text-center add-fieldi\">Field/Crop</td>";
			theadColumnLength = $("#field_choice_crop_thead_row_first > td").length;
		}
		theadColumnLength = parseInt(theadColumnLength);
		// theadColumnLength=theadColumnLength-1;

		// alert("theadColumnLength : "+theadColumnLength);

		var newTbodyHtml = "";
		var columnCount = parseInt(theadColumnLength);

		for (var i = 0; i < field_crop.length; i++) {
			var flag = true;
			for (var k = 1; k <= theadColumnLength; k++) {
				var columnName = $
						.trim(""
								+ $('#field_choice_crop_thead_row_column__' + k)
										.html());
				if (columnName.toLowerCase() === field_crop[i].toLowerCase()) {
					flag = false;
				}
			}
			if (flag) {
				columnCount++;
				newTheadHtml += "<td id=\"field_choice_crop_thead_row_column__"
						+ columnCount + "\" class=\"text-center add-fieldi\">"
						+ field_crop[i] + "</td>";
			}

		}

		if (theadRowLength == "0") {
			newTheadHtml += "</tr>";
			$("#field_choice_crop_thead").append(newTheadHtml);
		} else {
			$("#field_choice_crop_thead_row_first").append(newTheadHtml);
		}

		var tbodyRowLength = $("#field_choice_crop_tbody > tr").length;
		var rowCount = parseInt(tbodyRowLength);
		for (var i = 0; i < field_name.length; i++) {
			var flag = true;
			for (var k = 1; k <= tbodyRowLength; k++) {
				var rowName = $.trim(""
						+ $('#field_choice_crop_tbody_row_first_column__' + k)
								.html());
				if (rowName.toLowerCase() === field_name[i].toLowerCase()) {
					flag = false;
				}
			}
			if (flag) {
				rowCount++;
				newTbodyHtml += "<tr id=\"field_choice_crop_tbody_row__"
						+ rowCount
						+ "\" class=\"tblgrn text-center\"><td class=\"tblft1\" id=\"field_choice_crop_tbody_row_first_column__"
						+ rowCount + "\">" + field_name[i] + "</td>";
				for (var j = 1; j < columnCount; j++) {
					newTbodyHtml += "<td id=\"field_choice_crop_tbody_row_others_column__"
							+ rowCount
							+ "__column__"
							+ j
							+ "\" class=\"success\"><input type=\"checkbox\" id=\"field_choice_crop_selected_row__"
							+ rowCount + "__column__" + j + "\" /></td>";
				}
				newTbodyHtml += "</tr>";

			}

		}
		$("#field_choice_crop_tbody").append(newTbodyHtml);
		// 25-02-2015 start
		/*
		Commented by Harshit Gupta
		03-04-2015
		Start
		var removeFieldFlag = false;
		for (var k = 1; k <= tbodyRowLength; k++) {
			var rowName = $.trim(""
					+ $('#field_choice_crop_tbody_row_first_column__' + k)
							.html());
			var flag = true;
			for (var i = 0; i < field_name.length; i++) {
				if (rowName.toLowerCase() === field_name[i].toLowerCase()) {
					flag = false;
				}
			}
			if (flag) {
				removeFieldFlag = true;
				$('#field_choice_crop_tbody_row_first_column__' + k).parent()
						.remove();
			}
		}
		if (removeFieldFlag) {
			balanceDataAndIDsForTable();
		}
		End*/
		removeCropFieldChoiceHead();
		checkCropTableBalance();
		manageStep2 = true;
		enableDisableMenu();

		/** Modified by Harshit Gupta for Fallow 31-03-2015 Start*/
		/*Commented by Harshit Gupta on 08-04-2015*/
		
		/*var fallowFields = "";
		for (var i = 0; i < field_name.length; i++) {
			if ($("input[name=field-follow__" + (parseInt(i) + 1) + "]")
					.prop('checked')) {
				fallowFields += $("#row-field-name__" + (parseInt(i) + 1)).html().trim()+", ";
				$("#field_choice_crop_tbody_row__"+(parseInt(i) + 1)).find("input").each(function() {
			        $(this).removeAttr('checked');
			        $(this).attr('disabled','disabled');
			    });
			} else {
				$("#field_choice_crop_tbody_row__"+(parseInt(i) + 1)).find("input").each(function() {
			        $(this).removeAttr('disabled');
			    });
			}
			}
		if(fallowFields.length>0){
		fallowFields = fallowFields.substring(0, fallowFields.length-2);
		customAlerts(fallowFields.toUpperCase() +" is/are the fallow fields and you can not select them.", "error", 0);
		}*/
		
		/*end*/
		
	}return true;
}
function createProcessForShowCropAndCropInfoPage() {
	if (strategy == "fields") {
		var rowCount = $('#plan-by-field-tbody >tr').length;
		rowCount = rowCount - 1;
		if (rowCount < 2) {
			customAlerts("Please add at least two fields", "error", 0);
			return false;
		}
		var newTheadHtml = "";
		var theadRowLength = $("#field_choice_crop_thead > tr").length;
		var theadColumnLength = 1;
		if (theadRowLength == "0") {
			newTheadHtml += "<tr id=\"field_choice_crop_thead_row_first\" class=\"tblhd text-center add-fieldi\"><td id=\"field_choice_crop_thead_row_column__1\" class=\"tblbrdr text-center add-fieldi\">Field/Crop</td>";
			// theadColumnLength=$("#field_choice_crop_thead_row_first >
			// td").length;
		}
		if (theadRowLength != "0") {
			// newTheadHtml+="<tr id=\"field_choice_crop_thead_row_first\"
			// class=\"tblhd text-center add-fieldi\"><td
			// id=\"field_choice_crop_thead_row_column__1\" class=\"tblbrdr
			// text-center add-fieldi\">Field/Crop</td>";
			theadColumnLength = $("#field_choice_crop_thead_row_first > td").length;
		}
		theadColumnLength = parseInt(theadColumnLength);
		// theadColumnLength=theadColumnLength-1;

		// alert("theadColumnLength : "+theadColumnLength);

		var newTbodyHtml = "";
		var columnCount = parseInt(theadColumnLength);

		for (var i = 0; i < field_crop.length; i++) {
			var flag = true;
			for (var k = 1; k <= theadColumnLength; k++) {
				var columnName = $
						.trim(""
								+ $('#field_choice_crop_thead_row_column__' + k)
										.html());
				if (columnName.toLowerCase() === field_crop[i].toLowerCase()) {
					flag = false;
				}
			}
			if (flag) {
				columnCount++;
				newTheadHtml += "<td id=\"field_choice_crop_thead_row_column__"
						+ columnCount + "\" class=\"text-center add-fieldi\">"
						+ field_crop[i] + "</td>";
			}

		}

		if (theadRowLength == "0") {
			newTheadHtml += "</tr>";
			$("#field_choice_crop_thead").append(newTheadHtml);
		} else {
			$("#field_choice_crop_thead_row_first").append(newTheadHtml);
		}

		var tbodyRowLength = $("#field_choice_crop_tbody > tr").length;
		var rowCount = parseInt(tbodyRowLength);
		for (var i = 0; i < field_name.length; i++) {
			var flag = true;
			for (var k = 1; k <= tbodyRowLength; k++) {
				var rowName = $.trim(""
						+ $('#field_choice_crop_tbody_row_first_column__' + k)
								.html());
				if (rowName.toLowerCase() === field_name[i].toLowerCase()) {
					flag = false;
				}
			}
			if (flag) {
				rowCount++;
				newTbodyHtml += "<tr id=\"field_choice_crop_tbody_row__"
						+ rowCount
						+ "\" class=\"tblgrn text-center\"><td class=\"tblft1\" id=\"field_choice_crop_tbody_row_first_column__"
						+ rowCount + "\">" + field_name[i] + "</td>";
				for (var j = 1; j < columnCount; j++) {
					newTbodyHtml += "<td id=\"field_choice_crop_tbody_row_others_column__"
							+ rowCount
							+ "__column__"
							+ j
							+ "\" class=\"success\"><input type=\"checkbox\" id=\"field_choice_crop_selected_row__"
							+ rowCount + "__column__" + j + "\" /></td>";
				}
				newTbodyHtml += "</tr>";

			}

		}
		$("#field_choice_crop_tbody").append(newTbodyHtml);
		// 25-02-2015 start
		/*
		Created by Harshit Gupta
		08-04-2015
		Start
		*/
		var removeFieldFlag = false;
		for (var k = 1; k <= tbodyRowLength; k++) {
			var rowName = $.trim(""
					+ $('#field_choice_crop_tbody_row_first_column__' + k)
							.html());
			var flag = true;
			for (var i = 0; i < field_name.length; i++) {
				if (rowName.toLowerCase() === field_name[i].toLowerCase()) {
					flag = false;
				}
			}
			if (flag) {
				removeFieldFlag = true;
				$('#field_choice_crop_tbody_row_first_column__' + k).parent()
						.remove();
			}
		}
		if (removeFieldFlag) {
			balanceDataAndIDsForTable();
		}
		/*End*/
		removeCropFieldChoiceHead();
		checkCropTableBalance();

		manageStep2 = true;
		enableDisableMenu();
		/*
		 * Written by Harshit Gupta for Last Crop 16-04-2015 Start
		 */
		$("#field_choice_crop_tbody tr").each(function() {
			$(this).find('td').each(function() {
				var flag=false;
				if($(this).find("input").prop('checked')){
					flag = true;
				}
				$(this).html($(this).find("input").parent().html());
				if(flag){
					$(this).find("input").attr('checked',true);
				}
			});
		});
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
		/*
		 * Modified by Harshit Gupta for Fallow 31-03-2015 Start
		 */
		var fallowFields = "";
		for (var i = 0; i < field_name.length; i++) {
			if ($("input[name=field-follow__" + (parseInt(i) + 1) + "]")
					.prop('checked')) {
				fallowFields += $("#row-field-name__" + (parseInt(i) + 1)).html().trim()+", ";
				$("#field_choice_crop_tbody_row__"+(parseInt(i) + 1)).find("input").each(function() {
			        $(this).removeAttr('checked');
			        $(this).attr('disabled','disabled');
			    });
			} else {
				$("#field_choice_crop_tbody_row__"+(parseInt(i) + 1)).find("input").each(function() {
			        $(this).removeAttr('disabled');
			    });
			}
			}
		if(fallowFields.length>0){
			var alertMessage = "";
			if (fallowFields.split(",").length <= 2) {
				fallowFields = fallowFields.substring(0,
						fallowFields.length - 2);
				/*customAlerts(fallowFields+ " is a Fallow field so you can not select this field.","error", 0);*/
				alertMessage += "You marked \""+fallowFields+"\" as Fallow so this field is not available for planting.";
			} else if (fallowFields.split(",").length > 2) {
				fallowFields = fallowFields.substring(0,
						fallowFields.length - 2);
				/*customAlerts(fallowFields+ " are the Fallow field so you can not select these fields.","error", 0);*/
				alertMessage += "You marked \""+fallowFields+"\" as Fallow so these fields are not available for planting.";
				}
			customAlerts(alertMessage + " If you would like to plant or consider planting "+fallowFields+", go back to the previous screen and uncheck the Fallow box","warning", 0);
//		customAlerts("Select the crop you want to consider growing in each field.", "success", 0);
		}
		
		/*end*/
		
	
		
	}
	return true;
}

function createProcessForShowResourcesPage() {
	if(strategy == "fields")
	{
	crop_array=[];
	var length_showresource=$("#field_choice_crop_tbody >tr").length;
	//alert(length_showresource);
//if ($("input[name='plan_by_farm']:checked").length > 0)
	var crop_field_flag=false;
		for(var i=1;i<=length_showresource;i++)
			{
		 crop_field_flag=false;
			var length_showresource_td=$("#field_choice_crop_tbody_row__"+i+" >td").length;
		//	alert("td length :"+length_showresource_td);
			for(var j=1;j<length_showresource_td;j++)
				{
				
				/*
				 * Modified by Harshit Gupta 31-03-2015 for Fallow Start
				 */
				if($("#field_choice_crop_selected_row__"+i+"__column__"+j+"").prop('disabled')){
					crop_field_flag=true;
				} else if($("#field_choice_crop_selected_row__"+i+"__column__"+j+"").prop('checked'))
					{ 
					//alert("true"+"i :"+i+"j :"+j);
					crop_field_flag=true;
					var a=$("#field_choice_crop_tbody_row_first_column__"+i).html();
				            
					var b=$("#field_choice_crop_thead_row_column__"+(j+1)).html();
					//alert(a+b);
					crop_array.push(a+"#-#-#"+b);
					
					}
				
				}
			if(!crop_field_flag )
				{
				customAlerts('There are no crops selected for field "'+$("#field_choice_crop_tbody_row_first_column__"+i).html()+'"' , "error", 0);
				return false;
				
				}
			}
		}


// 25-02-2015 start
$("#total_land_available").html(addCommaSignOnValue(total_land));
resources['land'] = $("#total_land_available").html();
manageStep3 = true;
enableDisableMenu();
return true;
}
function createProcessForShowResourcesUsagePage() {
	if (strategy == "fields") {
		$("#show_hide_field_variance_button").show();
		var crop_select_drop_down=$("#crop_select_drop_down").val();
		if(crop_select_drop_down!="0")
			{
		var flag=fieldVarianceMaxMinCheck(crop_select_drop_down);
		if(!flag)
			{
			return false;
			}
	}
	}
	else
	{
		$("#show_hide_field_variance_button").hide();
	}
	var totalCapitalAvailable = removeAllCommasAndDollar($.trim("" + $('#total_capital_available').val()));
	if (totalCapitalAvailable == "") {
		customAlerts("Please enter amount for Capital resource", "error", 0);
		focusForValidation("total_capital_available");
		return false;
	}/*update by Bhagvan Singh on 15-04-2015 for capital value 0 start*/
    else if (totalCapitalAvailable == "0") {
        customAlerts("Please ensure that the values entered are greater than zero for Capital resource", "error", 0);
        focusForValidation("total_capital_available");
        return false;
    } else if(totalCapitalAvailable > 100000000) {
	    	customAlerts("Value for Capital can not be more then $10,000,000.00", "error", 0);
	    	focusForValidation("total_capital_available");
	    	return false;
	}
	/*end*/
	// change by rohit 16-04-15
	else if (!validateNumberOnly(totalCapitalAvailable) || totalCapitalAvailable < 1) {
		customAlerts("Please ensure that the values entered are greater than zero for Capital resource", "error", 0);
		focusForValidation("total_capital_available");
		return false;
	}

	resources['capital'] = totalCapitalAvailable;
	//alert("resources['capital'] : "+resources['capital']);
	var manageResourceRowLength = $("#manage_resource_tbody > tr").length;
	for(var l=3;l<=manageResourceRowLength;l++)
		{
		if($('#manage_resource_row_checkbox__'+l).is(':checked'))
			{
			//alert('manage_resource_row_checkbox__'+l+'is Checked');
			if ($.trim("" + $('#resource_manage__' + (l-2)).val()) == "") {
				customAlerts('Please enter amount for "'+$("#resource_manage_name__" + (l-2)).html()+'" resource', "error", 0);
				focusForValidation("resource_manage__" + (l-2));
				return false;
			}
			
			}
	}
	var resourceValidationForCheckbox = "";
	for(var l=3;l<=manageResourceRowLength;l++)
	{
		 if(!$('#manage_resource_row_checkbox__'+l).is(':checked') && !$("#resource_manage__"+(l-2)).val() == "")
			{
			 resourceValidationForCheckbox += $("#resource_manage_name__" + (l-2)).html().trim()+", ";
			}
	}
	if(resourceValidationForCheckbox != resourceValidationForCheckboxGlobal && resourceValidationForCheckbox != ""){
		resourceValidationForCheckboxGlobal = resourceValidationForCheckbox;
		if(resourceValidationForCheckbox.split(",").length==2){
			resourceValidationForCheckbox = resourceValidationForCheckbox.substring(0,resourceValidationForCheckbox.length-2);
			customAlerts('In Resource screen "'+resourceValidationForCheckbox+'" resource is not selected. If you want to use this resource, go back to Resource screen and select the checkbox', "warning", 0);
		}else if(resourceValidationForCheckbox.split(",").length>2){
			resourceValidationForCheckbox = resourceValidationForCheckbox.substring(0,resourceValidationForCheckbox.length-2);
			customAlerts('In Resource screen "'+resourceValidationForCheckbox+'" resources are not selected. If you want to use these resources, go back to Resource screen and select the checkboxes', "warning", 0);
		}
	}
	
	/*return false;*/
	var resourceNo = 1;
	for (var k = 3; k <= manageResourceRowLength; k++) {
		if ($('#manage_resource_row_checkbox__'+k).is(':checked')) {
			var resourceValue=$	.trim("" + $('#resource_manage__' + resourceNo).val());
			resourceValue = removeAllCommas(resourceValue);
			if(validateNumberOnly(resourceValue) && resourceValue > 0)
				{
				resources[$("#resource_manage_name__" + resourceNo).html()] =resourceValue;
				resourcesUOM[$("#resource_manage_name__" + resourceNo).html()] =$("#resource_manage_uom__" + resourceNo).html(); 
				}
			else
				{
				customAlerts('Please ensure that the value entered are greater than zero for "'+$('#resource_manage_name__' + resourceNo).html()+'" resource', "error", 0);
				focusForValidation("resource_manage__" + resourceNo);
				return false;
				}
			
		} else {
			delete resources[$("#resource_manage_name__" + resourceNo).html()];
			delete resourcesUOM[$("#resource_manage_name__" + resourceNo).html()];
		}
		resourceNo++;
	}
	/*
	 * $.each(resources, function(k, v) { alert("Key = "+k); alert("Value =
	 * "+resources[k]); });
	 */

	removeCropResourcesUsageTableValue();
	addCropResourcesUsageTableValue();
	balanceCropResourcesUsageTableValue();
	return true;
}
function createProcessForShowForwardPlan() {

	var headColumnLength=$("#crop_resource_usage_thead_first_row > td").length;
	var bodyRowLength=$("#crop_resource_usage_tbody > tr").length;
	var counter=1;
	for(var i=4;i<=headColumnLength;i++)
		{
		var flag=true;
		var resourceName=$.trim("" + $('#crop_resource_usage_thead_first_row_column__'+i).html());
		var cropName="";
		var total=0;	
		for(var j=1;j<=bodyRowLength;j++)
			{
			cropName=$.trim("" + $('#crop_resource_usage_crop__'+j).html());
			var resourceValue=$.trim("" + $("#crop_resource_usage__"+j+"__resource__"+counter).val().replace(/,/g, ''));
			if(validateNumberOnly(resourceValue))
				{
				if(resourceValue!="")
					{
					total=total+Number(resourceValue);
					}
				}
			else
				{
				customAlerts('please enter valid "'+resourceName+'" amount for '+cropName+' crop name', "error", 0);
				return false;
				}
			//alert($("#crop_resource_usage__"+j+"__resource__"+counter).val());
			}
		total=Number(total);
		
		  $.each(resources, function(key, value) {
			  if (resourceName.toLowerCase() == key.toLowerCase()) {
				  var totalResource=Number(removeAllCommas(""+resources[key]));
				  //alert("totalResource : "+totalResource);
				 // alert("total : "+total);
//				  alert(totalResource+" "+total);
				  if(totalResource<total)
					  {
					  customAlerts('"'+resourceName+'" amount for all the crops cannot be greater than total "'+resourceName+'" amount "'+totalResource+'". If you would like to use this amount then, go back to the previous screen and increase the total "'+resourceName+'" amount', "error", 0);
					  flag= false;
					  }
			  }
			  //alert("Key = "+key); 
			  //alert("Value ="+resources[key]);
		});
		 		if(!flag)
		 			{
		 			return false;
		 			}
		counter++; 
		}
	return true;
}
function createProcessForShowCropLimitPage() {
	var total_acres=0;
	for ( var i = 1; i <= $("#forward_sales_information_tbody >tr").length; i++) {
		if ($("#forward_sales_information_tbody_row_acres__" + i).val() != "") {
         total_acres+=Number($("#forward_sales_information_tbody_row_acres__" + i).val());
		}
	}
	
	if(Number(total_acres) >= Number($("#total_land_available").html()))
		{
		customAlerts("Total acres amount can not be greater then land amount", "error", 0);
		return false;
		}
	var flag=percentageLimit();
	if(!flag)
	{
		return false;
	}
//	end
	addRemoveCropLimitTableData();
	manageStep5 = true;
	enableDisableMenu();
	/*insertCropsInAvailableCropArray();*/
	//added by rohit on 20-04-15
	for(var i=1;i<=$("#crop_limits_table_tbody >tr").length;i++)
	{
		$("#crop_limits_crop_minimum_acres__"+i).val($("#forward_sales_information_tbody_row_acres__"+i).val());
		//$("#crop_limits_crop_minimum_acres__"+i).prop('readonly', true);
	}
	return true;
}

function createProcessForValidateAcresValue() {

	/*
	 * update by Bhagvan Singh on 07-04-2015 start
	 */
	var acresValue = $.trim("" + $('#acres_value').val());
	acresValue = removeAllCommas(acresValue);
	// End
	if (acresValue == "") {
		customAlerts("Please enter land value for planting", "error", 0);
		focusForValidation("acres_value");
		return false;
	}
	if (!validateNumberOnly(acresValue) || acresValue < 1) {
		customAlerts(
				"Please ensure that the value entered are greater than zero for Land",
				"error", 0);
		focusForValidation("acres_value");
		return false;
	}
	total_land = acresValue;
	
	/*
	 * else { $.ajax({ url : 'agriculture/farmController/createAcres', type :
	 * 'POST', async : false, data : ({ acresValue : acresValue, farmId : farmId
	 * }), success : function(response) { var status = response.status; if
	 * (status == 'success') { //alert("Your farm has been created"); var
	 * acresId=response.result; //alert(farmId); window.location =
	 * "crop-and-crop-info.htm?farmId="+farmId+"&acresId="+acresId+""; } else if
	 * (status == 'Already exists') { alert("Farm Name is already registered,
	 * Choose another one!"); } }, error : function(XMLHttpRequest, status,
	 * message) { alert("Error" + XMLHttpRequest + ":" + status + ":" +
	 * message); }
	 * 
	 * }); }
	 */
	return true;
}