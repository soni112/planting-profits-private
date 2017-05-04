// this method called to check all screen for validation when saved farm
function callCssForShowDiv(value) {
	$(".show_hide_class").addClass("hidden");
	$("#" + value).removeClass("hidden");
}
function validationCallForLeftSlide() {
	var validationFlag = true;
	var flagforFieldAcres = $.trim(""
			+ $('input[name=plan_by_farm]:checked').val());

	if (flagforFieldAcres == "acres") {
		if (!validationPlanByAcres()) {
			// alert("validationPlanByAcres");
			callCssForShowDiv("planbyacres");
			validationFlag = false;
		}

		else if (!cropAndCropInformation()) {
			// alert("cropAndCropInformation");
			callCssForShowDiv("crop_cropinfo");
			validationFlag = false;

		} else if (!cropInfirmationDetail()) {
			// alert("cropInfirmationDetail");
			callCssForShowDiv("cropinfodetail");
			validationFlag = false;

		} else if (!validationresourse()) {
			// alert("validationresourse");
			callCssForShowDiv("resources_info");
			validationFlag = false;

		} else if (!validationcropResourseUsages()) {
			// alert("validationresourse");
			callCssForShowDiv("resources_usage");
			validationFlag = false;

		} else if (!validationCropForwardSales()) {
			// alert("validationresourse");
			callCssForShowDiv("forward_sales_div");
			validationFlag = false;

		}
	}
	if (flagforFieldAcres == "fields") {
		if (!cropAndCropInformation()) {
			// alert("cropAndCropInformation");
			callCssForShowDiv("crop_cropinfo");
			validationFlag = false;

		} else if (!cropInfirmationDetail()) {
			// alert("cropInfirmationDetail");
			callCssForShowDiv("cropinfodetail");
			validationFlag = false;

		} else if (!validationplanByFields()) {
			// alert("validationplanByFields");
			callCssForShowDiv("plan_by_fields");
			validationFlag = false;

		} else if (!validationcropFieldChoice()) {
			// alert("validationcropFieldChoice");
			callCssForShowDiv("crop_field_choice");
			validationFlag = false;

		} else if (!validationresourse()) {
			// alert("validationresourse");
			callCssForShowDiv("resources_info");
			validationFlag = false;

		} else if (!validationcropResourseUsages()) {
			// alert("validationresourse");
			callCssForShowDiv("resources_usage");
			validationFlag = false;

		} else if (!validationCropForwardSales()) {
			// alert("validationresourse");
			callCssForShowDiv("forward_sales_div");
			validationFlag = false;

		}
	}

	return validationFlag;
}
function validationfarmInformation() {
	var farmInformationFlag = true;
	if ($("input[name='plan_by_farm']:checked").length > 0) {

		farmInformationFlag = true;
		// return true;
	} else {
		customAlerts("Please select your strategy", "error", 0);
		farmInformationFlag = false;
		// return false;

	}
	return farmInformationFlag;
}
function validationPlanByAcres() {
	var planByAcresFlag = true;
	if (!createProcessForValidateAcresValue()) {
		planByAcresFlag = false;
	}
	return planByAcresFlag;
}
function cropAndCropInformation() {
	var cropAndCropInformationFlag = true;
	if (!createProcessForShowCropInfoDetails()) {
		cropAndCropInformationFlag = false;
	}
	return cropAndCropInformationFlag;
}
function cropInfirmationDetail() {
	var cropInfirmationDetailFlag = true;
	if (!createProcessForShowFieldCropPage()) {
		cropInfirmationDetailFlag = false;
	}
	return cropInfirmationDetailFlag;
}

function validationresourse() {
	var resourseFlag = true;
	if (!createProcessForShowResourcesUsagePage()) {
		resourseFlag = false;
	}
	return resourseFlag;
}
function validationcropResourseUsages() {

	var cropResourseUsagesFlag = true;
	if (!createProcessForShowForwardPlan()) {
		cropResourseUsagesFlag = false;
	}
	return cropResourseUsagesFlag;
}
function fowaredSale() {
	var fowaredSale = true;
	if (!createProcessForShowCropLimitPage()) {
		fowaredSale = false;
	}
	return fowaredSale;
}
function validationplanByFields() {

	var planByFieldsFlag = true;

	if (!createProcessForShowCropAndCropInfoPage()) {
		planByFieldsFlag = false;
	}

	return planByFieldsFlag;

}
function validationcropFieldChoice() {
	var cropFieldChoiceFlag = true;
	if (!createProcessForShowResourcesPage()) {
		cropFieldChoiceFlag = false;
	}
	return cropFieldChoiceFlag;
}
function validationCropForwardSales() {
	var cropForwardSalesFlag = true;
	if (!createProcessForShowCropLimitPage()) {
		cropForwardSalesFlag = false;
	}
	return cropForwardSalesFlag;
}
/* change by rohit on 02-06-15 */
function ProposedFirmCheckbox(obj, num) {
	var rowid = (obj.id).replace(/[A-Za-z_$-]/g, "");

	// var
	// proposed=$("#forward_sales_information_tbody_row_proposed__"+rowid).val();

	if (num == 1) {
		$("#forward_sales_information_tbody_row_firm__" + rowid).prop(
				"checked", false);
		// $("#forward_sales_information_tbody_row_proposed__"+rowid).prop("checked",false);
	}
	if (num == 2) {
		// $("#forward_sales_information_tbody_row_firm__"+rowid).prop("checked",false);
		$("#forward_sales_information_tbody_row_proposed__" + rowid).prop(
				"checked", false);
	}

}