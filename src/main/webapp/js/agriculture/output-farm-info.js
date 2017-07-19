var resourceNameForPopUp="";
var unitForCropResourse="";

$(function(){
	if($("#sa_multiple_crop_contract_tbody").find("tr").length == 0){
		$("#sa_multiple_crop_contract_tbody").css("display", "none");
	}
	if($("#sa_multiple_crop_proposed_tbody").find("tr").length == 0){
		$("#sa_multiple_crop_proposed_tbody").css("display", "none");
	}
	if($("#sa_multiple_groups_tbody").find("tr").length == 0){
		$("#sa_multiple_groups_tbody").css("display", "none");
	}
	applyTabingOnSidemenu();


	/**
	 * @added - Abhishek
	 * @date - 08-02-2016
	 * @desc - changed according to slide#1 of 02062016
	 */
	// formatAcreageProfit();
	/**
	 * @changed - Abhishek
	 * @date - 11-12-2015
	 * @param potentialProfit
	 */
	calculateProfitForOneAndTwoCrops();

	addActiveClass($("#menu-output"));
	if (strategy == "Field") {
		customAlerts("The strategy building process for Plan by field is not available, Please try again later or check our Strategy building process for Plan by Acre", "warning", 0);
		return false;
	}
	if (checkStrategyForFarm == false) {
		customAlerts("Planting Profits could not generate a strategy that meets all of your objectives and constraints. " +
			"<br> You will have to make adjustments to resources, crop limits, crop/field choices, or other parameters.", "error", 0);
		return false;
	}

	applyGraphTableToggleAndSAToggle();
	registerTemplates();
	$('[data-toggle="popover"]').popover();
});

function registerTemplates(){
	$.template("tableBodyCropTemplate", $('#tableBodyCropTemplate'));
	$.template("tableHeaderCropTemplate", $("#tableHeaderCropTemplate"));
	$.template("multipleCropTbodyTemplate", $("#multipleCropTbodyTemplate"));
	$.template("resourceCropTheadTemplate", $("#resourceCropTheadTemplate"));
	$.template("sensitivity-output-tmpl", $("#sensitivity-output-tmpl"));
}

function applyDetailsThroughTemplate(templateId, datalist, targetId){
	//if(datalist.length > 0){
		$(targetId).html("");
		$(templateId).tmpl(datalist).appendTo(targetId);
	//}
}

function applyGraphTableToggleAndSAToggle(){
	$("#cropAcerageGraphShow").click(function() {
		$("#cropAcerageText").hide();
		$("#cropAcerageGraph").show();
	});
	$("#cropAcerageTextShow").click(function() {
		$("#cropAcerageText").show();
		$("#cropAcerageGraph").hide();
	});
	$("#resourceUseGraphShow").click(function() {
		$("#resourceUseText").hide();
		$("#resourceUseGraph").show();
	});
	$("#resourceUseTextShow").click(function() {
		$("#resourceUseText").show();
		$("#resourceUseGraph").hide();
	});
	$("#createScenarioGraphShow").click(function() {
		$("#createScenarioText").hide();
		$("#createScenarioGraph").show();
	});
	$("#createScenarioShow").click(function() {
		$("#createScenarioText").show();
		$("#createScenarioGraph").hide();
	});
	$('.panel-heading span.clickable').on("click", function (e) {
		if ($(this).hasClass('panel-collapsed')) {
			// expand the panel
			$(this).parents('.panel').find('.panel-body').slideDown();
			$(this).removeClass('panel-collapsed');
			$(this).find('i').removeClass('fa fa-chevron-down').addClass('fa fa-chevron-up');
		}
		else {
			// collapse the panel
			$(this).parents('.panel').find('.panel-body').slideUp();
			$(this).addClass('panel-collapsed');
			$(this).find('i').removeClass('fa fa-chevron-up').addClass('fa fa-chevron-down');
		}
	});
}

/**
 * @added - Abhishek
 * @date - 08-02-2016
 * @desc - changed according to slide#1 of 02062016
 */
function formatAcreageProfit(){
	$('#cropAcreageAndProfit > tbody > tr').each(function(){
		var acreage = $(this).children("td:nth(1)").text().trim();
		var profit = $(this).children("td:nth(2)").text().trim();

		if(acreage != 'N/A'){
			var acreageNum = acreage.substring(0, acreage.indexOf('(') - 1);
			var acreagePercentage = acreage.substring(acreage.indexOf('(') + 1, acreage.length - 1);
			$(this).children("td:nth(1)").html(acreageNum + '<br>' + acreagePercentage);
		}
		if(profit != 'N/A'){
			var profitNum = profit.substring(0, profit.indexOf('(') - 1);
			var profitPercentage = profit.substring(profit.indexOf('(') + 1, profit.length - 1);
			$(this).children("td:nth(2)").html(profitNum + '<br>' + profitPercentage);
		}


	});
}

/**
 * @added - Abhishek
 * @date - 11-12-2015
 * @param potentialProfit
 */
function calculateProfitForOneAndTwoCrops() {

	/**
	 * @changed - abhishek
	 * @date - 29-02-2016
	 * @desc - according to slide#11 of 02272016
	 */
	var cropProfitArray = [];

	$('#cropAcreageAndProfit').find('tr').each(function(){
		var currentCropProfit = $(this).children("td:nth(2)").html();
		currentCropProfit = currentCropProfit.split("<br>")[1];
		if(currentCropProfit !== undefined && currentCropProfit != "Profit" && currentCropProfit != "N/A") {
			currentCropProfit = currentCropProfit.replace(/\%/g, "");

			cropProfitArray.push(parseInt(currentCropProfit));

		}
	});

	var firstCropProfit = 0;
	var secondCropProfit = 0;

	for (var i = 0; i < cropProfitArray.length; i++) {
		if (cropProfitArray[i] > firstCropProfit) {
			secondCropProfit = firstCropProfit;
			firstCropProfit = cropProfitArray[i];
		} else if (cropProfitArray[i] > secondCropProfit)
			secondCropProfit = cropProfitArray[i];
	}

	$('#singleCropProfit').prepend(firstCropProfit + "%");
	$('#twoCropProfit').prepend((firstCropProfit + secondCropProfit) + "%");

}

function buildObjForTmplIfStrategyField(object){

	if (object.Strategy == 'Field') {
		for (var i = 0; i < object.Field_Crop_Info.length; i++) {
			var fieldInfo = object.Field_Crop_Info[i]["Field_Info"];
			object.Field_Crop_Info[i]['fieldName'] = fieldInfo.substring(0, fieldInfo.indexOf('(') - 1);
			object.Field_Crop_Info[i]['fieldSize'] = fieldInfo.substring(fieldInfo.indexOf('(') + 1, fieldInfo.length - 1);
		}
	}

	return object;
}

function alterHTMLOfTableAndShowPopupTable(result){
	var resourse_Crop_Value=null;
	if(result.cropValue==undefined){
		resourse_Crop_Value=result.resourceValue;
			/**
			 * @changed - Abhishek
			 * @date - 15-12-2015
			 * @updated - 19-12-2015
			 */
			$("#field_crop_button").html("<div class=\"yellobtn save_senario\"><a onclick=\"getStrategyForSinghalResourcesForCreateNewScenario("+resourse_Crop_Value+")\">Save</a></div>");
		//$("#field_crop_button").html("<div class=\"yellobtn save_senario\"><a onclick=\"getStrategyForSinghalResourcesForCreateNewScenario()\">Update</a></div>");
	} else {

		resourse_Crop_Value=result.cropValue;
			/**
			 * @changed - Abhishek
			 * @date - 15-12-2015
			 * @updated - 19-12-2015
			 */
			$("#field_crop_button").html("<div class=\"yellobtn save_senario\"><a onclick=\"getStrategyForSingleCropsForCreateNewScenario("+resourse_Crop_Value+")\">Save</a></div>");
		//$("#field_crop_button").html("<div class=\"yellobtn save_senario\"><a onclick=\"getStrategyForSingleCropsForCreateNewScenario()\">Update</a></div>");
	}

	var potential_pro = addCommaSignWithDollarForTextWithOutId(result.Potential_Profit);
	/**
	 * @changed - Abhishek
	 * @date - 15-12-2015
	 */

	if($('#Resource-Use').css('display') == 'block'){
		/**
		 * @changed - Abhishek
		 * @date - 05-02-2016
		 * @desc - according to slide#9 of 02052016
		 * @updated - 25-02-2016
		 * @desc - according to slide#2 of 02212016
		 */
		/*$("#sensetiveAnalysisCropAndResourcePotentialProfitSpan").html("Estimated Income is "+potential_pro+" with <span id=\"resourse_Value_Result\">"+resourse_Crop_Value+"</span> "+unitForCropResourse+" of "+resourceNameForPopUp+".");*/
		$("#sensetiveAnalysisCropAndResourcePotentialProfitSpan").html("Estimated Income : " + potential_pro+"<div class='hidden'> with <span id=\"resourse_Value_Result\">"+resourse_Crop_Value+"</span> "+unitForCropResourse+" of "+resourceNameForPopUp+".</div>");
	} else if($('#Crop-Limits').css('display') == 'block'){
		/**
		 * @changed - Abhishek
		 * @date - 05-02-2016
		 * @desc - according to slide#9 of 02052016
		 */
		/*$("#sensetiveAnalysisCropAndResourcePotentialProfitSpan").html("Estimated Income is " + potential_pro + " with " + $('#max_min_selector').val().toString().toLowerCase() + " of <span id=\"resourse_Value_Result\"> " + resourse_Crop_Value + "</span> of " + resourceNameForPopUp + " is required.");*/
		$("#sensetiveAnalysisCropAndResourcePotentialProfitSpan").html("Estimated Income :" + potential_pro + "<div class='hidden'> with " + $('#max_min_selector').val().toString().toLowerCase() + " of <span id=\"resourse_Value_Result\"> " + resourse_Crop_Value + "</span> of " + resourceNameForPopUp + " is required.</div>");
	}



	var theadObj = {};
	theadObj['Strategy'] = result.Strategy;
	var table = $("#sensetiveAnalysisCropAndResourceTable");
	applyDetailsThroughTemplate('#tableHeaderCropTemplate', theadObj, table.find("thead"));
	applyDetailsThroughTemplate('#tableBodyCropTemplate', buildObjForTmplIfStrategyField(result), table.find("tbody"));

	showSensetiveAnalysisCropAndResourcePopup();
}

function alterHTMLOfTableAndShowPopupTableForMultipalCropResourse(result){

	var potential_pro = result.Potential_Profit;
	/**
	 * @chanegd - Abhishek
	 * @date - 25-02-2016
	 * @desc - according to slide#2 of 02212016
	 */
	/*$("#sensetiveAnalysisCropAndResourcePotentialProfitSpan").html("Estimated Income is "+potential_pro+".");*/
	$("#sensetiveAnalysisCropAndResourcePotentialProfitSpan").html("Estimated Income : "+potential_pro);


	var theadObj = {};
	theadObj['Strategy'] = result.Strategy;
	var table = $("#sensetiveAnalysisCropAndResourceTable");
	applyDetailsThroughTemplate('#tableHeaderCropTemplate', theadObj, table.find("thead"));
	applyDetailsThroughTemplate('#tableBodyCropTemplate', buildObjForTmplIfStrategyField(result), table.find("tbody"));


	/**
	 * @changed - Abhishek
	 * @date - 08-12-2015
	 */
	updateCurrentPotentialProfitAndCalculateDifference(potential_pro);

	/**
	 * @changed - Abhishek
	 * @date - 10-12-2015
	 */
	//showSensetiveAnalysisCropAndResourcePopup();
}

function returnZeroIfBlank(value) {
	if (value == "") {
		return "0";
	} else {
		return value;
	}
}

function getStrategyForMultipleResources(){
	if(validateResourceTableForSA() == false){
		return false;
	}
	var resourceArray = [];
	$("#sa_multiple_resource_table").find("tbody tr").each(function(){
		var resourceName = $(this).children("td:nth(0)").text().trim();
		var resourceValue = $(this).children("td:nth(1)").text().trim();
		var resourceOverridedValue = $(this).children("td:nth(2)").find("input").val().trim();
		if(resourceValue != resourceOverridedValue){
			resourceArray.push(resourceName+"#-#-#"+removeAllCommas(resourceOverridedValue));
		}
	});
	if(resourceArray.length == 0){
        /**
		 * @changed - Jyoti
		 * @date - 30-01-2017
         */
		customAlerts("No resource has been changed so a new strategy cannot be generated", type_error, time);
		return false;
	}
	$.ajax({
		url : 'agriculture/SensetivityAnalysisController/getSAForStrategyByMultipleResource',
		type : 'POST',
		beforeSend: function(){
            $('#loading-strategy-content').html('Changing resources and generating a new strategy...');
			showLoadingImageForStrategy()
		},
		data : {
            farmInfoId : farmInfoId,
			resourceArray : resourceArray,
		},
		success : function(response) {
			var status = response.status;
			var result = response.result;
			if (status == 'success') {
				// result = JSON.parse(result);
				if(result.Potential_Profit == "$0"){
					customAlerts('Cannot generate a strategy with the amount of resources provided. Increase critical resource(s) to generate a feasible strategy', type_error, time);
					return false;
				}
				//alterHTMLOfTableAndShowPopupTable(result);

				/**
				 * @changed - Abhishek
				 * @date - 10-12-2015
				 */
				$('#multipleResourceViewStrategy').removeClass("hidden");

				$("#field_crop_button").html("<div class='yellobtn save_senario'><a onclick=\"getStrategyForMultipleResourcesForCreateNewScenario();hideSensetiveAnalysisCropAndResourcePopup();\">Save</a></div>");
				alterHTMLOfTableAndShowPopupTableForMultipalCropResourse(result);
			} else if (status == 'failed') {
				customAlerts('Some problem occured, Please try again later', type_error, time);
			}else{
				location.reload();
			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
		}

	}).done(function(){
		hideLoadingImageForStrategy();
		$('#loading-strategy-content').html('Generating strategy…')
	});
}

function validateResourceTableForSA(){
	var flag = true;
	$("#sa_multiple_resource_table tbody tr").each(function(){
		var resourceOverridedValue = $(this).children("td:nth(2)").find("input").val().trim();
		if(resourceOverridedValue == ""){
			customAlerts("Please enter new amount for "+$(this).children("td:nth(0)").text().trim()+" resources", type_error, time);
			flag = false;
			return flag;
		}
		else if(resourceOverridedValue == "0"){
			customAlerts("New resource amount can not be zero for "+$(this).children("td:nth(0)").text().trim()+" resources", type_error, time);
			flag = false;
			return flag;
		}
	});
	return flag;
}

function getStrategyForMultipleCrops(){
	if(validateCropsTableForSA() == false){
		return false;
	}
	var cropsArray = [], limitFlag = false, totalMinimumAcre = 0, totalMaximumAcre = 0;
	$("#sa_multiple_crops_tbody tr").each(function(){
		var cropName = $(this).children("td:nth(0)").text().trim();
		var maxValue = $.trim($(this).children("td:nth(2)").text());
		var maxOverridedValue = $.trim($(this).children("td:nth(4)").find("input").val());
        if((maxValue != '' || maxValue != '-') && maxOverridedValue != "") {
            maxOverridedValue = parseFloat(removeAllCommas(maxOverridedValue));
            maxValue = parseFloat(removeAllCommas(maxValue));
            if (maxOverridedValue > maxLand) {
                customAlerts("Maximum acreage limit of " + cropName + " cannot be more than total acreage i.e. " + maxLand + " acres", type_error, time);
                focusForValidationForObject($(this).children("td:nth(4)").find("input"));
                limitFlag = true;
                return false;
            } else if (maxOverridedValue < 0) {
                customAlerts("Maximum acreage limit of " + cropName + " cannot be negative", type_error, time);
                focusForValidationForObject($(this).children("td:nth(4)").find("input"));
                limitFlag = true;
                return false;
            } else if (maxValue != maxOverridedValue) {
                cropsArray.push(cropName + "#-#-#max#-#-#" + maxOverridedValue);
            }
            totalMaximumAcre += maxOverridedValue;
        }
        var minValue = $.trim($(this).children("td:nth(1)").text());
        var minOverridedValue = $.trim($(this).children("td:nth(3)").find("input").val());
        if((minValue != '' || minValue != '-') && minOverridedValue != "") {

            minOverridedValue = parseFloat(removeAllCommas(minOverridedValue));
            minValue = parseFloat(removeAllCommas(minValue));
            if (minOverridedValue > maxLand) {
                customAlerts("Minimum acreage limit of " + cropName + " cannot be more than total acreage i.e. " + maxLand + " acres", type_error, time);
                focusForValidationForObject($(this).children("td:nth(3)").find("input"));
                limitFlag = true;
                return false;
            } else if (minOverridedValue < 0) {
                customAlerts("Minimum acreage limit of " + cropName + " cannot be negative", type_error, time);
                focusForValidationForObject($(this).children("td:nth(3)").find("input"));
                limitFlag = true;
                return false;
            } else if (minValue != minOverridedValue && maxOverridedValue < maxLand) {
                cropsArray.push(cropName + "#-#-#min#-#-#" + minOverridedValue);
            }
            totalMinimumAcre += minOverridedValue;
        }
    });
	var cropContractArray = [];
	$("#sa_multiple_crop_contract_tbody tr").each(function(){
		var cropName = $(this).children("td:nth(0)").text().trim().split(" (Contract)")[0];
		var minValue = $.trim($(this).children("td:nth(1)").text());
		var minOverridedValue = $.trim($(this).children("td:nth(3)").find("input").val());
        if((minValue == '' || minValue == '-') && minOverridedValue == "") {

            minOverridedValue = minOverridedValue != "" ? parseFloat(removeAllCommas(minOverridedValue)) : 0.0;
            minValue = minValue != "" ? parseFloat(removeAllCommas(minValue)) : 0.0;

            if (minOverridedValue > maxLand) {
                customAlerts("Minimum acreage limit of " + cropName + " cannot be more than total acreage i.e. " + maxLand + " acres", type_error, time);
                focusForValidationForObject($(this).children("td:nth(3)").find("input"));
                limitFlag = true;
                return false;
            } else if (minOverridedValue < 0) {
                customAlerts("Minimum acreage limit of " + cropName + " cannot be negative", type_error, time);
                focusForValidationForObject($(this).children("td:nth(3)").find("input"));
                limitFlag = true;
                return false;
            } else if (minValue != minOverridedValue) {
                cropContractArray.push(cropName + "#-#-#min#-#-#" + minOverridedValue);
            }
            totalMinimumAcre += minOverridedValue;
        }
    });
	var cropProposedArray = [];
	$("#sa_multiple_crop_proposed_tbody tr").each(function(){
		var cropName = $(this).children("td:nth(0)").text().trim().split(" (Proposed)")[0];
		var minValue = $.trim($(this).children("td:nth(1)").text());
		var minOverridedValue = $.trim($(this).children("td:nth(3)").find("input").val());
        if((minValue == '' || minValue == '-') && minOverridedValue == "") {

            minOverridedValue = minOverridedValue != "" ? parseFloat(removeAllCommas(minOverridedValue)) : 0.0;
            minValue = minValue != "" ? parseFloat(removeAllCommas(minValue)) : 0.0;

            if (minOverridedValue > maxLand) {
                customAlerts("Minimum acreage limit of " + cropName + " cannot be more than total acreage i.e. " + maxLand + " acres", type_error, time);
                focusForValidationForObject($(this).children("td:nth(3)").find("input"));
                limitFlag = true;
                return false;
            } else if (minOverridedValue < 0) {
                customAlerts("Minimum acreage limit of " + cropName + " cannot be negative", type_error, time);
                focusForValidationForObject($(this).children("td:nth(3)").find("input"));
                limitFlag = true;
                return false;
            } else if (minValue != minOverridedValue) {
                cropProposedArray.push(cropName + "#-#-#min#-#-#" + minOverridedValue);
            }
            totalMinimumAcre += minOverridedValue;
        }
    });
	var cropsGroupArray = [];
	$("#sa_multiple_groups_tbody tr").each(function(){
		var groupName = $(this).children("td:nth(0)").text().trim();
		var maxValue = $.trim($(this).children("td:nth(2)").text());
		var maxOverridedValue = $.trim($(this).children("td:nth(4)").find("input").val());
        if((maxValue == '' || maxValue == '-') && maxOverridedValue == "") {

            maxOverridedValue = maxOverridedValue != "" ? parseFloat(removeAllCommas(maxOverridedValue)) : 0.0;
            maxValue = maxValue != "" ? parseFloat(removeAllCommas(maxValue)) : 0.0;


            if (maxOverridedValue > maxLand) {
                customAlerts("Maximum acreage limit of " + groupName + " cannot be more than total acreage i.e. " + maxLand + " acres", type_error, time);
                focusForValidationForObject($(this).children("td:nth(4)").find("input"));
                limitFlag = true;
                return false;
            } else if (maxOverridedValue < 0) {
                customAlerts("Maximum acreage limit of " + groupName + " cannot be negative", type_error, time);
                focusForValidationForObject($(this).children("td:nth(4)").find("input"));
                limitFlag = true;
                return false;
            } else if (maxValue != maxOverridedValue) {
                cropsGroupArray.push(groupName + "#-#-#max#-#-#" + maxOverridedValue);
            }
            totalMaximumAcre += maxOverridedValue;
        }

        var minValue = $.trim($(this).children("td:nth(1)").text());
        var minOverridedValue = $.trim($(this).children("td:nth(3)").find("input").val());
        if((minValue == '' || minValue == '-') && minOverridedValue == "") {

            minOverridedValue = minOverridedValue != "" ? parseFloat(removeAllCommas(minOverridedValue)) : 0.0;
            minValue = minValue != "" ? parseFloat(removeAllCommas(minValue)) : 0.0;
            if (minOverridedValue > maxLand) {
                customAlerts("Minimum acreage limit of " + groupName + " cannot be more than total acreage i.e. " + maxLand + " acres", type_error, time);
                focusForValidationForObject($(this).children("td:nth(3)").find("input"));
                limitFlag = true;
                return false;
            } else if (minOverridedValue < 0) {
                customAlerts("Minimum acreage limit of " + groupName + " cannot be negative", type_error, time);
                focusForValidationForObject($(this).children("td:nth(3)").find("input"));
                limitFlag = true;
                return false;
            } else if (minValue != minOverridedValue) {
                cropsGroupArray.push(groupName + "#-#-#min#-#-#" + minOverridedValue);
            }
            totalMinimumAcre += minOverridedValue;
        }
    });
    if(limitFlag){
        return false;
    } else if (totalMinimumAcre >= maxLand) {
        customAlerts('Total of the Minimum acres amount must be less than total available land "' + maxLand + '"', type_error, time);
        return false;
    } else if (totalMaximumAcre > maxLand) {
        customAlerts('Total of the Maximum acres amount must not be more than total available land "' + maxLand + '"', type_error, time);
        return false;
    } else if(cropsArray.length == 0 && cropsGroupArray.length == 0 && cropContractArray.length == 0 && cropProposedArray.length == 0){
		customAlerts("No crop limit has been changed so a new strategy cannot be generated", type_error, time);
		return false;
	}

	$.ajax({
		url : 'agriculture/SensetivityAnalysisController/getSAForStrategyByMultipleCrops',
		type : 'POST',
		beforeSend: function(){
			$('#loading-strategy-content').html('Preparing to Save strategy...');
			showLoadingImageForStrategy()
		},
		data : {
            farmInfoId : farmInfoId,
			cropsArray : cropsArray,
			cropContractArray : cropContractArray,
			cropProposedArray : cropProposedArray,
			cropsGroupArray : cropsGroupArray,
		},
		success : function(response) {
			var status = response.status;
			var result = response.result;
			if (status == 'success') {
				// result = JSON.parse(result);
				/**
				 * @changed - Abhishek
				 * @date - 15-12-2015
				 */
				$("#field_crop_button").html("<div class='yellobtn save_senario'><a onclick=\"getStrategyForMultipleCropsForCreateNewScenario();hideSensetiveAnalysisCropAndResourcePopup();\">Save</a></div>");
				//$("#field_crop_button").html("<div class='yellobtn save_senario'><a onclick=\"getStrategyForMultipleCropsForCreateNewScenario();hideSensetiveAnalysisCropAndResourcePopup();\">Update</a></div>");
				alterHTMLOfTableAndShowPopupTableForMultipalCropResourse(result);

				/**
				 * @changed -  Abhishek
				 * @date - 10-12-2015
				 */
				$('#viewStrategyMultipleCrops').removeClass('hidden');

			} else if (status == 'failed') {
				customAlerts('Some problem occured, Please try again later', type_error, time);
			} else{
				location.reload();
			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
		}

	}).done(function(){
		hideLoadingImageForStrategy();
		$('#loading-strategy-content').html('Generating strategy…')
	});
}

function getStrategyForMultipleCropsForCreateNewScenario(){
	if(validateCropsTableForSA() == false){
		return false;
	}
	var detailsForMultipleCrops = {};
	var cropsArray = [], cropArrayForTemplate = [];
	$("#sa_multiple_crops_tbody tr").each(function(){
		var cropName = $(this).children("td:nth(0)").text().trim();
		var maxValue = $(this).children("td:nth(2)").text().trim();
		var maxOverridedValue = $(this).children("td:nth(4)").find("input").val().trim();
		var minValue = $(this).children("td:nth(1)").text().trim();
		var minOverridedValue = $(this).children("td:nth(3)").find("input").val().trim();

		var cropData = {};
		cropData['cropName'] = cropName;
		cropData['minimum'] = minOverridedValue;
		cropData['maximum'] = maxOverridedValue;
		cropArrayForTemplate.push(cropData);

		if(replaceHifun(maxValue) != maxOverridedValue && maxOverridedValue != ""){
			cropsArray.push(cropName+"#-#-#max#-#-#"+removeAllCommas(maxOverridedValue));
		}
		if(replaceHifun(minValue) != minOverridedValue && minOverridedValue != ""){
			cropsArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(minOverridedValue));
		}
	});
	var cropContractArray = new Array();
	$("#sa_multiple_crop_contract_tbody tr").each(function(){
		var cropName = $(this).children("td:nth(0)").text().trim().split(" (Contract)")[0];
		var minValue = $(this).children("td:nth(1)").text().trim();
		var minOverridedValue = $(this).children("td:nth(3)").find("input").val().trim();

		var cropData = {};
		cropData['cropName'] = cropName;
		cropData['minimum'] = minOverridedValue;
		cropData['maximum'] = "-";
		cropArrayForTemplate.push(cropData);

		if(replaceHifun(minValue) != minOverridedValue && minOverridedValue != ""){
			cropContractArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(minOverridedValue));
		}
	});
	var cropProposedArray = new Array();
	$("#sa_multiple_crop_proposed_tbody tr").each(function(){
		var cropName = $(this).children("td:nth(0)").text().trim().split(" (Proposed)")[0];
		var minValue = $(this).children("td:nth(1)").text().trim();
		var minOverridedValue = $(this).children("td:nth(3)").find("input").val().trim();

		var cropData = {};
		cropData['cropName'] = cropName;
		cropData['minimum'] = minOverridedValue;
		cropData['maximum'] = "-";
		cropArrayForTemplate.push(cropData);

		if(replaceHifun(minValue) != minOverridedValue && minOverridedValue != ""){
			cropProposedArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(minOverridedValue));
		}
	});
	var cropsGroupArray = new Array();
	$("#sa_multiple_groups_tbody tr").each(function(){
		var groupName = $(this).children("td:nth(0)").text().trim();
		var maxValue = $(this).children("td:nth(2)").text().trim();
		var maxOverridedValue = $(this).children("td:nth(4)").find("input").val().trim();
		var minValue = $(this).children("td:nth(1)").text().trim();
		var minOverridedValue = $(this).children("td:nth(3)").find("input").val().trim();

		var cropData = {};
		cropData['cropName'] = groupName;
		cropData['mingetStrategyForMultipleResourcesForCreateNewScenarioimum'] = minOverridedValue;
		cropData['maximum'] = maxOverridedValue;
		cropArrayForTemplate.push(cropData);

		if(replaceHifun(maxValue) != maxOverridedValue && maxOverridedValue != ""){
			cropsGroupArray.push(groupName+"#-#-#max#-#-#"+removeAllCommas(maxOverridedValue));
		}
		if(replaceHifun(minValue) != minOverridedValue && minOverridedValue != ""){
			cropsGroupArray.push(groupName+"#-#-#min#-#-#"+removeAllCommas(minOverridedValue));
		}
	});

	detailsForMultipleCrops['cropDetails'] = cropArrayForTemplate;
	detailsForMultipleCrops['source'] = "crop";

	if(cropsArray.length == 0 && cropsGroupArray.length == 0 && cropContractArray.length == 0 && cropProposedArray.length == 0){
		customAlerts("No changes have been made to crop limit values so a new strategy was not generated", type_error, time);
		return false;
	}
	/**
	 * @changed - Abhishek
	 * @date - 05-02-2016
	 * @desc - according to slide#9 of 01282016
	 */
	/*$("#SaveStrategyForMultipleResources_id").html("<a onclick=\"SaveStrategyForMultipleCrops('multipal_Crops')\">Save this strategy</a>");*/
	$("#SaveStrategyForMultipleResources_id").html("<a onclick=\"SaveStrategyForMultipleCrops('multipal_Crops')\">Save</a>");
	$.ajax({
		url : 'agriculture/SensetivityAnalysisController/getSAForStrategyByMultipleCrops',
		type : 'POST',
		beforeSend: function(){
			$('#loading-strategy-content').html('Preparing to Save strategy...');
			showLoadingImageForStrategy()
		},
		data : {
            farmInfoId : farmInfoId,
			cropsArray : cropsArray,
			cropContractArray : cropContractArray,
			cropProposedArray : cropProposedArray,
			cropsGroupArray : cropsGroupArray,
		},
		success : function(response) {
			var status = response.status;
			var result = response.result;
			if (status == 'success') {
				// result = JSON.parse(result);
				if(result.Potential_Profit == "$0"){
					customAlerts('Sorry we are not able to prepare a strategy with these values, Please try changing some values to make a profitable strategy', type_error, time);
					return false;
				}
				$("#potential_profit_id").html("<span>"+result.Potential_Profit+"</span>");
				/**
				 * @changed - Abhishek
				 * @date - 05-02-2016
				 * @desc - according to slide#9 of 01282016
				 * @updated - 05-02-2016
				 * @desc - according to slide#4 of 02212016
				 */
				/*$("#scenario_heading").html("Updated values in crops for this scenario");*/
				$("#scenario_heading").html("Updated crop limit values for this strategy");
				$("#addcroppopup").html("<label>Add New Strategy Name</label><input type='text' id='pop-up-field-name-crop' />");

				var theadObj = {};
				theadObj['source'] = 'crop';
				var table = $("#resourceTableForCreateScenario");
				applyDetailsThroughTemplate('#resourceCropTheadTemplate', theadObj, table.find("thead"));
				applyDetailsThroughTemplate('#multipleCropTbodyTemplate', detailsForMultipleCrops, table.find("tbody"));

				theadObj = {};
				theadObj['Strategy'] = result.Strategy;
				table = $("#strategyDetailsTable");
				applyDetailsThroughTemplate('#tableHeaderCropTemplate', theadObj, table.find("thead"));
				applyDetailsThroughTemplate('#tableBodyCropTemplate', buildObjForTmplIfStrategyField(result), table.find("tbody"));


				showCreateNewScenario();
				if(result.Strategy == 'Field'){
					$("#createScenarioGraphShowByFieldLi").css("display", "");
					$("#createScenarioGraphShowByFieldLi").addClass("selected");
					$("#createScenarioGraphShowByFieldDiv").css("display", "");
					$("#createScenarioGraphShowByCropsLi").removeClass("selected");
					$("#createScenarioGraphShowByCropsDiv").css("display", "none");
					// changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByFieldGraph", result.JSONArrayObjectForGraphByField, "Field_Info");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByFieldGraph", result.JSONArrayObjectForGraphByField, "Crop_Name", "Land");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByCropsGraph", result.JSONArrayObjectForGraphByField, "Crop_Name", "Profit");
					applyDetailsThroughTemplate('#sensitivity-output-tmpl', result.outputDetails, $('#senstivity-output-table').find('tbody'));
				}else if(result.Strategy == 'Acerage'){
					// $("#createScenarioGraphShowByFieldLi").css("display", "");
					// $("#createScenarioGraphShowByFieldDiv").css("display", "none");
					// $("#createScenarioGraphShowByCropsLi").addClass("selected");
					// $("#createScenarioGraphShowByCropsDiv").css("display", "");
					$("#createScenarioGraphShowByFieldLi").css("display", "");
					$("#createScenarioGraphShowByFieldLi").addClass("selected");
					$("#createScenarioGraphShowByFieldDiv").css("display", "");
					$("#createScenarioGraphShowByCropsLi").removeClass("selected");
					$("#createScenarioGraphShowByCropsDiv").css("display", "none");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByFieldGraph", result.JSONArrayObjectForGraphByCrop, "Crop_Name", "Land");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByCropsGraph", result.JSONArrayObjectForGraphByCrop, "Crop_Name", "Profit");
					applyDetailsThroughTemplate('#sensitivity-output-tmpl', result.outputDetails, $('#senstivity-output-table').find('tbody'));
				}

			} else if (status == 'failed') {
				customAlerts('Some problem occured, Please try again later', type_error, time);
			} else{
				location.reload();
			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
		}
		
	}).done(function(){
		hideLoadingImageForStrategy();
		$('#loading-strategy-content').html('Generating strategy…')
	});
}

function getStrategyForSingleCropsForCreateNewScenario(updatedValue){
	/*if(validateCropsTableForSA() == false){
		return false;
	}*/

	var cropName = $("#forCastGraphCropList").val().trim().split("#-#-#")[1];
	var selectionType = $("#forCastGraphCropList").val().trim().split("#-#-#")[0];
	var rangeType = $("#max_min_selector").val();
	var differenceValue = removeAllCommas($("#forCastGraphValueForSingleCrop").val());
	var objectForCropDetails = {}, crop = {};
	var cropsArray = [], cropContractArray = [], cropProposedArray = [], cropsGroupArray = [], cropDetails = [];
	var rowHTML = "";
	if(selectionType=="Crop") {
		
		crop['cropName'] = cropName;		
		if(rangeType=="Maximum"){
			/**
			 * @changed - Abhishek
			 * @date - 19-12-2015
			 */
			/*cropsArray.push(cropName+"#-#-#max#-#-#"+removeAllCommas(differenceValue));
			rowHTML += "<tr class='tblgrn'><td class='success'>"+cropName+"</td> <td class='success'>"+differenceValue +"</td><td class='success'>-</td> </tr>";*/
			cropsArray.push(cropName+"#-#-#max#-#-#"+updatedValue);			
			crop['minimum'] = "-";
			crop['maximum'] = updatedValue;
			rowHTML += "<tr class='tblgrn'><td class='success'>"+cropName+"</td> <td class='success'>"+updatedValue +"</td><td class='success'>-</td> </tr>";
		}else{
			/**
			 * @changed - Abhishek
			 * @date - 19-12-2015
			 */
			/*cropsArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(differenceValue));
			rowHTML += "<tr class='tblgrn'><td class='success'>"+cropName+"</td> <td class='success'>-</td><td class='success'>"+differenceValue+"</td> </tr>";*/			
			crop['minimum'] = updatedValue;
			crop['maximum'] = "-";
			cropsArray.push(cropName+"#-#-#min#-#-#"+updatedValue);
			rowHTML += "<tr class='tblgrn'><td class='success'>"+cropName+"</td> <td class='success'>-</td><td class='success'>"+updatedValue+"</td> </tr>";
		}
		cropDetails.push(crop);
	}
	if(selectionType=="Proposed"){
		/**
		 * @changed - Abhishek
		 * @date - 19-12-2015
		 */
		/*rowHTML += "<tr class='tblgrn'><td class='success'>"+cropName+"(Proposed)</td> <td class='success'>NA</td><td class='success'>"+differenceValue+"</td> </tr>";
		cropProposedArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(differenceValue));*/
		
		crop['cropName'] = cropName + "(Proposed)";
		crop['minimum'] = updatedValue;
		crop['maximum'] = "N/A";
		cropDetails.push(crop);
		rowHTML += "<tr class='tblgrn'><td class='success'>"+cropName+"(Proposed)</td> <td class='success'>NA</td><td class='success'>"+updatedValue+"</td> </tr>";
		cropProposedArray.push(cropName+"#-#-#min#-#-#"+updatedValue);
	}
	if(selectionType=="Contract"){
		/**
		 * @changed - Abhishek
		 * @date - 19-12-2015
		 */
		/*rowHTML += "<tr class='tblgrn'><td class='success'>"+cropName+"(Contract)</td> <td class='success'>NA</td><td class='success'>"+differenceValue+"</td> </tr>";
		cropContractArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(differenceValue));*/
		crop['cropName'] = cropName + "(Contract)";
		crop['minimum'] = updatedValue;
		crop['maximum'] = "N/A";
		cropDetails.push(crop);
		rowHTML += "<tr class='tblgrn'><td class='success'>"+cropName+"(Contract)</td> <td class='success'>NA</td><td class='success'>"+updatedValue+"</td> </tr>";
		cropContractArray.push(cropName+"#-#-#min#-#-#"+updatedValue);
	}
	if(selectionType=="Group"){
		crop['cropName'] = cropName;
		if(rangeType=="Maximum"){
			/**
			 * @changed - Abhishek
			 * @date - 19-12-2015
			 */
			/*cropsGroupArray.push(cropName+"#-#-#max#-#-#"+removeAllCommas(differenceValue));
			rowHTML += "<tr class='tblgrn'><td class='success'>"+cropName+"</td> <td class='success'>"+differenceValue +"</td><td class='success'>-</td> </tr>";*/
			crop['minimum'] = "-";
			crop['maximum'] = updatedValue;
			cropsGroupArray.push(cropName+"#-#-#max#-#-#"+updatedValue);
			rowHTML += "<tr class='tblgrn'><td class='success'>"+cropName+"</td> <td class='success'>"+updatedValue +"</td><td class='success'>-</td> </tr>";
		}else{
			/**
			 * @changed - Abhishek
			 * @date - 19-12-2015
			 */
			/*cropsGroupArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(differenceValue));
			rowHTML += "<tr class='tblgrn'><td class='success'>"+cropName+"</td> <td class='success'>-</td><td class='success'>"+differenceValue+"</td> </tr>";*/
			crop['minimum'] = updatedValue;
			crop['maximum'] = "-";
			cropsGroupArray.push(cropName+"#-#-#min#-#-#"+updatedValue);
			rowHTML += "<tr class='tblgrn'><td class='success'>"+cropName+"</td> <td class='success'>-</td><td class='success'>"+updatedValue+"</td> </tr>";
		}
		cropDetails.push(crop);
	}
	
	objectForCropDetails['cropDetails'] = cropDetails;
	objectForCropDetails['source'] = "crop";
	/**
	 * @changed - Abhishek
	 * @date - 05-02-2016
	 * @desc - according to slide#9 of 01282016
	 */
	/*$("#SaveStrategyForMultipleResources_id").html("<a onclick=\"SaveStrategyForMultipleCrops('single_Crops')\">Save this strategy</a>");*/
	$("#SaveStrategyForMultipleResources_id").html("<a onclick=\"SaveStrategyForMultipleCrops('single_Crops')\">Save</a>");
	$.ajax({
		url : 'agriculture/SensetivityAnalysisController/getSAForStrategyByMultipleCrops',
		type : 'POST',
		beforeSend: function(){
			$('#loading-strategy-content').html('Preparing to Save strategy...')
			showLoadingImageForStrategy()
		},
		data : {
			farmInfoId : farmInfoId,
			cropsArray : cropsArray,
			cropContractArray : cropContractArray,
			cropProposedArray : cropProposedArray,
			cropsGroupArray : cropsGroupArray,
		},
		success : function(response) {
			var status = response.status;
			var result = response.result;
			if (status == 'success') {
				// result = JSON.parse(result);
				if(result.Potential_Profit == "$0"){
					customAlerts('Sorry we are not able to prepare a strategy with these values, Please try changing some values to make a profitable strategy', type_error, time);
					return false;
				}
				$("#potential_profit_id").html("<span>"+result.Potential_Profit+"</span>");
				/**
				 * @changed - Abhishek
				 * @date - 05-02-2016
				 * @desc - according to slide#9 of 01282016
				 * @updated - 05-02-2016
				 * @desc - according to slide#4 of 02212016
				 */
				/*$("#scenario_heading").html("Updated values in crops for this scenario");*/
				$("#scenario_heading").html("Updated crop limit values for this strategy");

				$("#addcroppopup").html("<label>Enter new strategy name</label><input type='text' id='pop-up-field-name-crop' />");

				var theadObj = {};
				theadObj['source'] = 'crop';
				var table = $("#resourceTableForCreateScenario");
				applyDetailsThroughTemplate('#resourceCropTheadTemplate', theadObj, table.find("thead"));
				applyDetailsThroughTemplate('#multipleCropTbodyTemplate', objectForCropDetails, table.find("tbody"));

				theadObj = {};
				theadObj['Strategy'] = result.Strategy;
				table = $("#strategyDetailsTable");
				applyDetailsThroughTemplate('#tableHeaderCropTemplate', theadObj, table.find("thead"));
				applyDetailsThroughTemplate('#tableBodyCropTemplate', buildObjForTmplIfStrategyField(result), table.find("tbody"));
				

				showCreateNewScenario();
				if(result.Strategy == 'Field'){
					$("#createScenarioGraphShowByFieldLi").show();
					$("#createScenarioGraphShowByFieldLi").addClass("selected");
					$("#createScenarioGraphShowByFieldDiv").show();
					$("#createScenarioGraphShowByCropsLi").removeClass("selected");
					$("#createScenarioGraphShowByCropsDiv").hide();
					// changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByFieldGraph", result.JSONArrayObjectForGraphByField, "Field_Info");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByFieldGraph", result.JSONArrayObjectForGraphByField, "Crop_Name", "Land");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByCropsGraph", result.JSONArrayObjectForGraphByField, "Crop_Name", "Profit");
					applyDetailsThroughTemplate('#sensitivity-output-tmpl', result.outputDetails, $('#senstivity-output-table').find('tbody'));
				}else if(result.Strategy == 'Acerage'){
					/*$("#createScenarioGraphShowByFieldLi").css("display", "none");
					$("#createScenarioGraphShowByFieldDiv").css("display", "");
					$("#createScenarioGraphShowByCropsLi").addClass("selected");
					$("#createScenarioGraphShowByCropsDiv").css("display", "");*/
					$("#createScenarioGraphShowByFieldLi").show();
					$("#createScenarioGraphShowByFieldLi").addClass("selected");
					$("#createScenarioGraphShowByFieldDiv").show();
					$("#createScenarioGraphShowByCropsLi").removeClass("selected");
					$("#createScenarioGraphShowByCropsDiv").hide();
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByFieldGraph", result.JSONArrayObjectForGraphByCrop, "Crop_Name", "Land");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByCropsGraph", result.JSONArrayObjectForGraphByCrop, "Crop_Name", "Profit");
					applyDetailsThroughTemplate('#sensitivity-output-tmpl', result.outputDetails, $('#senstivity-output-table').find('tbody'));
				}

			} else if (status == 'failed') {
				customAlerts('Some problem occured, Please try again later', type_error, time);
			} else{
				location.reload();
			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
		}
		
	}).done(function(){
		hideLoadingImageForStrategy();
		$('#loading-strategy-content').html('Generating strategy…')
	});
	hideSensetiveAnalysisCropAndResourcePopup();  //hide popup crop | Acrreage by rohit
}

function validateCropsTableForSA(){
	var flag = true;

	var cropsTr = $("#sa_multiple_crops_tbody tr");
	var cropGroupTr = $("#sa_multiple_groups_tbody tr");

	if(cropsTr.length == 0 && cropGroupTr.length == 0){
		customAlerts("No Crop Limits Specified", type_error, time);
		flag = false;
		return flag;
	}


	cropsTr.each(function(){
		var cropMaxOverridedValue = $(this).children("td:nth(4)").find("input").val().trim();
		var cropMinOverridedValue = $(this).children("td:nth(3)").find("input").val().trim();
		if(cropMaxOverridedValue != ""
			&& cropMaxOverridedValue != "0"
			&& Number(returnZeroIfBlank(cropMinOverridedValue)) >= Number(cropMaxOverridedValue)){
			customAlerts("Minimum crop limit must be less then maximum crop limit for "+$(this).children("td:nth(0)").text().trim()+" crop", type_error, time);
			flag = false;
			return flag;
		}
	});
	cropGroupTr.each(function(){
		var cropMaxOverridedValue = $(this).children("td:nth(4)").find("input").val().trim();
		var cropMinOverridedValue = $(this).children("td:nth(3)").find("input").val().trim();
		if(cropMaxOverridedValue != ""
			&& cropMaxOverridedValue != "0"
			&& Number(returnZeroIfBlank(cropMinOverridedValue)) >= Number(cropMaxOverridedValue)){
			customAlerts("Minimum crop limit must be less then maximum crop limit for "+$(this).children("td:nth(0)").text().trim()+" group", type_error, time);
			flag = false;
			return flag;
		}
	});
	return flag;
}

function replaceHifun(value) {
	return value.replace(/\-/g, "");
}

function forCastGraphForSingleResource() {
	resourceNameForPopUp="";
	unitForCropResourse="";
	var resourceName = $("#forCastGraphResourceList").val();
	var currentPotentialProfit = Number(removeAllCommasAndDollar($(".baseline_potential_profit").text().trim()));
	resourceNameForPopUp = $("#forCastGraphResourceList").val();
	var differenceValue = removeAllCommas($("#forCastGraphValueForSingleResource").val());
	if(differenceValue == ""){
		customAlerts("Value for "+resourceName+" can not be blank", type_error, time);
		return false;
	}
	/**
	 * @changed - Abhishek
	 * @Date - 02-12-2015
	 */
	/*else if(differenceValue == "0"){
		customAlerts("Value for "+resourceName+" can not be zero", type_error, time);
		return false;
	}*/
	$.ajax({
		url : 'agriculture/SensetivityAnalysisController/getSAForCastGraphForSingleResource',
		type : 'POST',
		beforeSend: function(){
            // $('#loading-strategy-content').html('Preparing to Save strategy...')
            //Update according to PPT 05232017, Slide no : G055
			if(resourceName == 'Capital'){
                $('#loading-strategy-content').html('Generating multiple strategies for Working Capital...');
			} else {
                $('#loading-strategy-content').html('Generating multiple strategies for ' + resourceName + "...");
			}

			showLoadingImageForStrategy();
		},
		data : {
			farmInfoId : farmInfoId,
			resourceName : resourceName,
			differenceValue : differenceValue,
			currentPotentialProfit : currentPotentialProfit
		},
		success : function(response) {
			var status = response.status;
			var result = response.result;
			if (status == 'success') {
				// result = JSON.parse(result);
				//alert("hello"+result.resourceUnit);
				changeValuesOfForCastSingleResourcechartdiv(resourceName, result);
				unitForCropResourse=result.resourceUnit;
			} else if (status == 'failed') {
				customAlerts('Some problem occured, Please try again later', type_error, time);
			} else{
				location.reload();
			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
		}

	}).done(function(){
		hideLoadingImageForStrategy();
		$('#loading-strategy-content').html('Generating strategy…')
	});
}

function forCastGraphForSingleCropLimit() {
	resourceNameForPopUp="";
	unitForCropResourse="";
	var cropName = $("#forCastGraphCropList").val().trim().split("#-#-#")[1];
	var selectionType = $("#forCastGraphCropList").val().trim().split("#-#-#")[0];
	var currentPotentialProfit = Number(removeAllCommasAndDollar($(".baseline_potential_profit").text().trim()));
	var rangeType = $("#max_min_selector").val();
	var differenceValue = removeAllCommas($("#forCastGraphValueForSingleCrop").val());
	 resourceNameForPopUp=$("#forCastGraphCropList").val().trim().split("#-#-#")[1];

	if(typeof cropName == 'undefined'){
		customAlerts("Please select crop before proceeding further", type_error, time);
		return false;
	} else if(cropName == ""){
		customAlerts("No Crop has been specified with Crop Limit", type_error, time);
		return false;
	} else if(differenceValue == ""){
		customAlerts("Value for "+cropName+" can not be blank", type_error, time);
		focusForValidation('forCastGraphValueForSingleCrop');
		return false;
	}
	/**
	 * @changed - Abhishek
	 * @Date - 02-12-2015
	 */
	/*else if(differenceValue == "0"){
		customAlerts("Value for "+cropName+" can not be zero", type_error, time);
		return false;
	}*/
	$.ajax({
		url : 'agriculture/SensetivityAnalysisController/SAForCastGraphForSingleCrop',
		type : 'POST',
		beforeSend: function(){
			//Update according to PPT 05232017, Slide no : G055
			// $('#loading-strategy-content').html('Preparing to Save strategy...')
			$('#loading-strategy-content').html('Generating multiple strategies for ' + cropName + "...");
			showLoadingImageForStrategy()
		},
		data : {
			farmInfoId : farmInfoId,
			cropName : cropName,
			selectionType : selectionType,
			rangeType : rangeType,
			differenceValue : differenceValue,
			currentPotentialProfit : currentPotentialProfit
		},
		success : function(response) {
			var status = response.status;
			var result = response.result;
			if (status == 'success') {
				// result = JSON.parse(result);
				//alert("hello3"+result.resourceUnit);
				changeValuesOfForCastSingleCropchartdiv(cropName, result);
				unitForCropResourse="acres";
			} else if (status == 'failed') {
				customAlerts('Some problem occured, Please try again later', type_error, time);
			} else{
				location.reload();
			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
		}

	}).done(function(){
		hideLoadingImageForStrategy();
		$('#loading-strategy-content').html('Generating strategy…')
	});
}

function getStrategyForMultipleResourcesForCreateNewScenario(){
	if(validateResourceTableForSA() == false){
		return false;
	}
	var objetForResourceDetails = {};
	var resourceArray = [], resourceDetails = [];
	$("#sa_multiple_resource_table tbody tr").each(function(){
		var resourceName = $(this).children("td:nth(0)").text().trim();
		var resourceValue = $(this).children("td:nth(1)").text().trim();
		var resourceOverridedValue = $(this).children("td:nth(2)").find("input").val().trim();

		var resource = {};
		resource['resourceName'] = resourceName;
		resource['resourceValue'] = resourceOverridedValue;
		resourceDetails.push(resource);

		if(resourceValue != resourceOverridedValue){
			resourceArray.push(resourceName+"#-#-#"+removeAllCommas(resourceOverridedValue));
		}
	});

	objetForResourceDetails["resourceDetails"] = resourceDetails;
	objetForResourceDetails["source"] = 'resource';

	if(resourceArray.length == 0){
		customAlerts("You have not changed any resource value so new strategy can not be prepared", type_error, time);
		return false;
	}
	/**
	 * @changed - Abhishek
	 * @date - 05-02-2016
	 * @desc - according to slide#9 of 01282016
	 */
	/*$("#SaveStrategyForMultipleResources_id").html("<a onclick=\"SaveStrategyForMultipleResources('multipal_Resourse')\">Save this strategy</a>");*/
	$("#SaveStrategyForMultipleResources_id").html("<a onclick=\"SaveStrategyForMultipleResources('multipal_Resourse')\">Save</a>");
	$.ajax({
		url : 'agriculture/SensetivityAnalysisController/getSAForStrategyByMultipleResource',
		type : 'POST',
		beforeSend: function(){
			$('#loading-strategy-content').html('Preparing to Save strategy...')
			showLoadingImageForStrategy()
		},
		data : {
			farmInfoId : farmInfoId,
			resourceArray : resourceArray,
		},
		success : function(response) {
			var status = response.status;
			var result = response.result;
			if (status == 'success') {
				// result = JSON.parse(result);
				if(result.Potential_Profit == "$0"){
					customAlerts('Sorry we are not able to prepare a strategy with these values, Please try changing some values to make a profitable strategy', type_error, time);
					return false;
				}
				$("#potential_profit_id").html("<span>"+result.Potential_Profit+"</span>");
				/**
				 * @changed - Abhishek
				 * @date - 05-02-2016
				 * @desc - according to slide#9 of 01282016
				 * @updated - 25-02-2016
				 * @desc - changed according to slide#5 of 02212016
				 */
				/*$("#scenario_heading").html("Updated values in resources for this scenario");*/
				$("#scenario_heading").html("Updated resource values for this strategy");
				$("#addcroppopup").html("<label>Add New Strategy Name</label><input type='text' id='pop-up-field-name-resourse' />");


				var theadObj = {};
				theadObj['source'] = 'resource';
				var table = $("#resourceTableForCreateScenario");
				applyDetailsThroughTemplate('#resourceCropTheadTemplate', theadObj, table.find("thead"));
				applyDetailsThroughTemplate('#multipleCropTbodyTemplate', objetForResourceDetails, table.find("tbody"));

				theadObj = {};
				theadObj['Strategy'] = result.Strategy;
				table = $("#strategyDetailsTable");
				applyDetailsThroughTemplate('#tableHeaderCropTemplate', theadObj, table.find("thead"));
				applyDetailsThroughTemplate('#tableBodyCropTemplate', buildObjForTmplIfStrategyField(result), table.find("tbody"));

				showCreateNewScenario();
				if(result.Strategy == 'Field'){
					$("#createScenarioGraphShowByFieldLi").css("display", "");
					$("#createScenarioGraphShowByFieldLi").addClass("selected");
					$("#createScenarioGraphShowByFieldDiv").css("display", "");
					$("#createScenarioGraphShowByCropsLi").removeClass("selected");
					$("#createScenarioGraphShowByCropsDiv").css("display", "none");
					// changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByFieldGraph", result.JSONArrayObjectForGraphByField, "Field_Info");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByFieldGraph", result.JSONArrayObjectForGraphByField, "Crop_Name", "Land");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByCropsGraph", result.JSONArrayObjectForGraphByField, "Crop_Name", "Profit");
					applyDetailsThroughTemplate('#sensitivity-output-tmpl', result.outputDetails, $('#senstivity-output-table').find('tbody'));
				}else if(result.Strategy == 'Acerage'){
					/*$("#createScenarioGraphShowByFieldLi").css("display", "none");
					$("#createScenarioGraphShowByFieldDiv").css("display", "");
					$("#createScenarioGraphShowByCropsLi").addClass("selected");
					$("#createScenarioGraphShowByCropsDiv").css("display", "");*/
					$("#createScenarioGraphShowByFieldLi").css("display", "");
					$("#createScenarioGraphShowByFieldLi").addClass("selected");
					$("#createScenarioGraphShowByFieldDiv").css("display", "");
					$("#createScenarioGraphShowByCropsLi").removeClass("selected");
					$("#createScenarioGraphShowByCropsDiv").css("display", "none");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByFieldGraph", result.JSONArrayObjectForGraphByCrop, "Crop_Name", "Land");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByCropsGraph", result.JSONArrayObjectForGraphByCrop, "Crop_Name", "Profit");
					applyDetailsThroughTemplate('#sensitivity-output-tmpl', result.outputDetails, $('#senstivity-output-table').find('tbody'));
				}
			} else if (status == 'failed') {
				customAlerts('Some problem occured, Please try again later', type_error, time);
			} else {
				location.reload();
			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
		}
	}).done(function(){
		hideLoadingImageForStrategy();
		$('#loading-strategy-content').html('Generating strategy...')
	});
}

function getStrategyForSinghalResourcesForCreateNewScenario(){
	/*if(validateResourceTableForSA() == false){
	 return false;
	 }*/

	var objetForResourceDetails = {};
	var resourceArray = [], resourceDetails = [];

	var resourceName = $("#forCastGraphResourceList").val().trim();
	var resourceOverridedValue = $("#resourse_Value_Result").text().trim();

	var resource = {};
	resource['resourceName'] = resourceName;
	resource['resourceValue'] = resourceOverridedValue;
	resourceDetails.push(resource);

	resourceArray.push(resourceName+"#-#-#"+removeAllCommas(resourceOverridedValue));

	objetForResourceDetails["resourceDetails"] = resourceDetails;
	objetForResourceDetails["source"] = 'resource';

	if(resourceArray.length == 0){
		customAlerts("You have not changed any resource value so new strategy can not be prepared", type_error, time);
		return false;
	}
	/**
	 * @changed - Abhishek
	 * @date - 05-02-2016
	 * @desc - according to slide#9 of 01282016
	 */
	/*$("#SaveStrategyForMultipleResources_id").html("<a onclick=\"SaveStrategyForMultipleResources('singal_Resourse')\">Save this strategy</a>");*/
	$("#SaveStrategyForMultipleResources_id").html("<a onclick=\"SaveStrategyForMultipleResources('singal_Resourse')\">Save</a>");
	$.ajax({
		url : 'agriculture/SensetivityAnalysisController/getSAForStrategyByMultipleResource',
		type : 'POST',
		beforeSend: function(){
			$('#loading-strategy-content').html('Preparing to Save strategy...')
			showLoadingImageForStrategy()
		},
		data : {
			farmInfoId : farmInfoId,
			resourceArray : resourceArray,
		},
		success : function(response) {
			var status = response.status;
			var result = response.result;
			if (status == 'success') {
				// result = JSON.parse(result);
				if(result.Potential_Profit == "$0"){
					customAlerts('Sorry we are not able to prepare a strategy with these values, Please try changing some values to make a profitable strategy', type_error, time);
					return false;
				}
				$("#potential_profit_id").html("<span>"+result.Potential_Profit+"</span>");
				/**
				 * @changed - Abhishek
				 * @date - 05-02-2016
				 * @desc - according to slide#1 of 02062016
				 * @updated - 25-02-2016
				 * @desc - changed according to slide#5 of 02212016
				 */
				/*$("#scenario_heading").html("Updated values in resources for this scenario");*/
				$("#scenario_heading").html("Updated resource values for this strategy");

				$("#addcroppopup").html("<label>Add New Strategy Name</label><input type='text' id='pop-up-field-name-resourse' />");

				var theadObj = {};
				theadObj['source'] = 'resource';
				var table = $("#resourceTableForCreateScenario");
				applyDetailsThroughTemplate('#resourceCropTheadTemplate', theadObj, table.find("thead"));
				applyDetailsThroughTemplate('#multipleCropTbodyTemplate', objetForResourceDetails, table.find("tbody"));

				theadObj = {};
				theadObj['Strategy'] = result.Strategy;
				table = $("#strategyDetailsTable");
				applyDetailsThroughTemplate('#tableHeaderCropTemplate', theadObj, table.find("thead"));
				applyDetailsThroughTemplate('#tableBodyCropTemplate', buildObjForTmplIfStrategyField(result), table.find("tbody"));


				showCreateNewScenario();
				if(result.Strategy == 'Field'){
					$("#createScenarioGraphShowByFieldLi").css("display", "");
					$("#createScenarioGraphShowByFieldLi").addClass("selected");
					$("#createScenarioGraphShowByFieldDiv").css("display", "");
					$("#createScenarioGraphShowByCropsLi").removeClass("selected");
					$("#createScenarioGraphShowByCropsDiv").css("display", "none");
					// changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByFieldGraph", result.JSONArrayObjectForGraphByField, "Field_Info");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByFieldGraph", result.JSONArrayObjectForGraphByField, "Crop_Name", "Land");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByCropsGraph", result.JSONArrayObjectForGraphByField, "Crop_Name", "Profit");
					applyDetailsThroughTemplate('#sensitivity-output-tmpl', result.outputDetails, $('#senstivity-output-table').find('tbody'));
				}else if(result.Strategy == 'Acerage'){
					$("#createScenarioGraphShowByFieldLi").css("display", "");
					$("#createScenarioGraphShowByFieldLi").addClass("selected");
					$("#createScenarioGraphShowByFieldDiv").css("display", "");
					$("#createScenarioGraphShowByCropsLi").removeClass("selected");
					$("#createScenarioGraphShowByCropsDiv").css("display", "none");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByFieldGraph", result.JSONArrayObjectForGraphByCrop, "Crop_Name", "Land");
					changeValuesOfForCastSingleCropLimitchartdiv("createScenarioGraphShowByCropsGraph", result.JSONArrayObjectForGraphByCrop, "Crop_Name", "Profit");
					applyDetailsThroughTemplate('#sensitivity-output-tmpl', result.outputDetails, $('#senstivity-output-table').find('tbody'));
				}
			} else if (status == 'failed') {
				customAlerts('Some problem occured, Please try again later', type_error, time);
			} else {
				location.reload();
			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
		},
	}).done(function(){
		hideLoadingImageForStrategy();
		$('#loading-strategy-content').html('Generating strategy...')
	});
	hideSensetiveAnalysisCropAndResourcePopup();  //hide popup crop | Acrreage by rohit
}

function switchForContractOrProposed(){
	var selectionType = $("#forCastGraphCropList").val().trim().split("#-#-#")[0];
	if(selectionType == "Contract" || selectionType == "Proposed"){
		// $("#max_min_selector").val("Minimum");
		$("#max_min_selector").html("<option value='Minimum'>Minimum</option>");
		$("#max_min_selector").attr("disabled", "disabled");
	} else {
		$("#max_min_selector").removeAttr("disabled");
	}
}

function getTotalAcreaege(){
	var totalAcreage = 0;
	if(strategy == "PLAN_BY_FIELDS"){
		$('#strategyDetailsTable').find('.fieldAcreageSpecific').each(function(){
			if ($(this).find('.fieldCropInfoSpecific').html() != "Not Planted") {
				totalAcreage += parseInt(removeAllCommasAndDollar($(this).find('.fieldSizeSpecific').html()));
			}
		});


	} else if(strategy == "PLAN_BY_ACRES"){
		$('#strategyDetailsTable').find('.acreageLandUsedSpecific').each(function(){
			totalAcreage += parseInt(removeAllCommasAndDollar($(this).html()));
		});
	}

	return totalAcreage;
}

function SaveStrategyForMultipleResources(resourse_Type){
	var resourceArray = [];
	//var potentialProfit = removeAllCommasAndDollar($("#potential_profit_id").text());
	var strategyName = $("#pop-up-field-name-resourse").val().trim();
	if (strategyName == ""){
		customAlerts("Please enter a name for strategy", "error", 0); return;
	}
	if(resourse_Type=="multipal_Resourse"){
		$("#sa_multiple_resource_table tbody tr").each(function(){
			var resourceName = $(this).children("td:nth(0)").text().trim();
			var resourceValue = $(this).children("td:nth(1)").text().trim();
			var resourceOverridedValue = $(this).children("td:nth(2)").find("input").val().trim();
			if(resourceValue != resourceOverridedValue){
				resourceArray.push(resourceName+"#-#-#"+removeAllCommas(resourceOverridedValue));
			}
		});
	} else {
		$("#resourceTableForCreateScenario tbody tr").each(function(){
			var resourceName = $(this).children("td:nth(0)").text().trim();
			var resourceOverridedValue = $(this).children("td:nth(1)").text().trim();
			resourceArray.push(resourceName+"#-#-#"+removeAllCommas(resourceOverridedValue));

		});
	}
	$.ajax({
		url : 'agriculture/SensetivityAnalysisController/SaveStrategyForMultipleResources',
		type : 'POST',
        data : {
            farmId : farmId,
            farmInfoId : farmInfoId,
            resourceArray : resourceArray,
            strategyName : strategyName
        },
        beforeSend: function(){
			$('#loading-strategy-content').html('Preparing to Save strategy...');
			showLoadingImageForStrategy()
		},
		success : function(response) {
			var status = response.status;
			if (status == 'success') {
				customAlerts('"'+strategyName+'" strategy has been successfully saved', type_success, time);
				updateCurrentPotentialProfitAndCalculateDifference($("#potential_profit_id").text());
				hideCreateNewScenario();

			}
			else if (status == 'Already exists') {
				customAlerts('Strategy with name "'+strategyName+'" already exists, Please enter other strategy name', type_error, time);

			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
		}
	}).done(function(){
		hideLoadingImageForStrategy();
		$('#loading-strategy-content').html('Generating strategy...')
	});
}

function SaveStrategyForMultipleCrops(resourse_Type){
	var strategyName = $("#pop-up-field-name-crop").val().trim();
	//var potentialProfit = removeAllCommasAndDollar($("#potential_profit_id").text());
	var cropsArray = [], cropContractArray = [], cropProposedArray = [], cropsGroupArray = [];
	if (strategyName == ""){
		customAlerts("Please enter a name for strategy", "error", 0); return;
	}
	if(resourse_Type=="multipal_Crops"){
		//alert("hello");
		$("#sa_multiple_crops_tbody tr").each(function(){
			var cropName = $(this).children("td:nth(0)").text().trim();
			var maxValue = $(this).children("td:nth(2)").text().trim();
			var maxOverridedValue = $(this).children("td:nth(4)").find("input").val().trim();
			var minValue = $(this).children("td:nth(1)").text().trim();
			var minOverridedValue = $(this).children("td:nth(3)").find("input").val().trim();
			if(replaceHifun(maxValue) != maxOverridedValue && maxOverridedValue != ""){
				cropsArray.push(cropName+"#-#-#max#-#-#"+removeAllCommas(maxOverridedValue));
			}
			if(replaceHifun(minValue) != minOverridedValue && minOverridedValue != ""){
				cropsArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(minOverridedValue));
			}
		});
		$("#sa_multiple_crop_contract_tbody tr").each(function(){
			var cropName = $(this).children("td:nth(0)").text().trim().split(" (Contract)")[0];
			var minValue = $(this).children("td:nth(1)").text().trim();
			var minOverridedValue = $(this).children("td:nth(3)").find("input").val().trim();
			if(replaceHifun(minValue) != minOverridedValue && minOverridedValue != ""){
				cropContractArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(minOverridedValue));
			}
		});
		$("#sa_multiple_crop_proposed_tbody tr").each(function(){
			var cropName = $(this).children("td:nth(0)").text().trim().split(" (Proposed)")[0];
			var minValue = $(this).children("td:nth(1)").text().trim();
			var minOverridedValue = $(this).children("td:nth(2)").find("input").val().trim();
			if(replaceHifun(minValue) != minOverridedValue && minOverridedValue != ""){
				cropProposedArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(minOverridedValue));
			}
		});
		$("#sa_multiple_groups_tbody tr").each(function(){
			var groupName = $(this).children("td:nth(0)").text().trim();
			var maxValue = $(this).children("td:nth(2)").text().trim();
			var maxOverridedValue = $(this).children("td:nth(4)").find("input").val().trim();
			var minValue = $(this).children("td:nth(1)").text().trim();
			var minOverridedValue = $(this).children("td:nth(3)").find("input").val().trim();
			if(replaceHifun(maxValue) != maxOverridedValue && maxOverridedValue != ""){
				cropsGroupArray.push(groupName+"#-#-#max#-#-#"+removeAllCommas(maxOverridedValue));
			}
			if(replaceHifun(minValue) != minOverridedValue && minOverridedValue != ""){
				cropsGroupArray.push(groupName+"#-#-#min#-#-#"+removeAllCommas(minOverridedValue));
			}
		});
		/*if(cropsArray.length == 0 && cropsGroupArray.length == 0 && cropContractArray.length == 0 && cropProposedArray.length == 0){
			customAlerts("No changes have been made to crop limit values so a new strategy was not generated", type_error, time);
			return false;
		}*/
	} else if(resourse_Type=="single_Crops") {
		var cropName = $("#forCastGraphCropList").val().trim().split("#-#-#")[1];
		var selectionType = $("#forCastGraphCropList").val().trim().split("#-#-#")[0];
		var rangeType = $("#max_min_selector").val();
		var differenceValue = removeAllCommas($("#forCastGraphValueForSingleCrop").val());

		if(selectionType=="Crop") {
			if(rangeType=="Maximum")
				cropsArray.push(cropName+"#-#-#max#-#-#"+removeAllCommas(differenceValue));
			else
				cropsArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(differenceValue));
		}
		if(selectionType=="Proposed") {
			cropProposedArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(differenceValue));
		}
		if(selectionType=="Contract"){
			cropContractArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(differenceValue));
		}
		if(selectionType=="Group"){
			if(rangeType=="Maximum")
				cropsGroupArray.push(cropName+"#-#-#max#-#-#"+removeAllCommas(differenceValue));
			else
				cropsGroupArray.push(cropName+"#-#-#min#-#-#"+removeAllCommas(differenceValue));
		}
	}

	$.ajax({
		url : 'agriculture/SensetivityAnalysisController/SaveStrategyForMultipleCrop',
		type : 'POST',
		beforeSend: function(){
			$('#loading-strategy-content').html('Preparing to Save strategy...')
			showLoadingImageForStrategy()
		},
		data : {
            farmInfoId : farmInfoId,
			farmId : farmId,
			cropsArray : cropsArray,
			cropContractArray : cropContractArray,
			cropProposedArray : cropProposedArray,
			cropsGroupArray : cropsGroupArray,
			strategyName : strategyName
		},
		success : function(response) {
			var status = response.status;
			if (status == 'success') {
				customAlerts('"'+strategyName+'" strategy has been successfully saved', type_success, time);
				updateCurrentPotentialProfitAndCalculateDifference($("#potential_profit_id").text());
				hideCreateNewScenario();
				
			} else if (status == 'Already exists') {
				customAlerts('Strategy with name "'+strategyName+'" already exists, Please enter other strategy name', type_error, time);
				//hideCreateNewScenario();
			} else {
				customAlerts('Something happened while processing your request', type_error, time);
			}
		},
		error : function(XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
		}
	}).done(function(){
		hideLoadingImageForStrategy();
		$('#loading-strategy-content').html('Generating strategy')
	});
}

function updateCurrentPotentialProfitAndCalculateDifference(updatedPotentialProfit) {
	$(".new_potential_profit").text(updatedPotentialProfit);
	var currentPotentialProfit = Number(removeAllCommasAndDollar(updatedPotentialProfit));
	var potentialProfit = Number(removeAllCommasAndDollar($(".baseline_potential_profit").text()));
	var difference = currentPotentialProfit - potentialProfit;
	if(difference < 0){
		$(".difference_bet_potential_profit").css("color", "red");
		$(".difference_bet_potential_profit").text("-"+addCommaSignWithDollarForTextWithOutId(Math.abs(difference)));
	}else{
		$(".difference_bet_potential_profit").css("color", "");
		$(".difference_bet_potential_profit").text(addCommaSignWithDollarForTextWithOutId(difference));
	}
}

function checkMinMaxForCrop(currentRef){

	if ($(currentRef).val() != '') {
		var selectionDetails = $(currentRef).val().split('#-#-#');
		var cropId, groupId;
		if (selectionDetails[0] == 'Crop') {
			cropId = selectionDetails[2];
		} else if (selectionDetails[0] == 'Group') {
			groupId = selectionDetails[2];
		} else if(selectionDetails[0] == 'Contract' || selectionDetails[0] == 'Proposed'){
			return;
		}

		var html = "";
		$.ajax({
			url: 'ajaxRequest/checkMinMaxForCrop',
			type: 'post',
			data: {
				farmId: farmId,
				cropId: cropId,
				groupId: groupId
			},
			beforeSend: showLoadingImage(),
			success: function (response) {
				var status = response.status;
				var result = response.result;
				if(status == 'success'){

					for(var i = 0; i< result.length; i++){
						html += "<option value='"+result[i]+"'>"+result[i]+"</option>";
					}
					$('#max_min_selector').html(html);

				}

			},
			error: function (a, b, c) {

			},
			complete: function () {
				hideLoadingImage();
			}
		})
	} else {
		$("#max_min_selector").html("<option value=''>Select crop first</option>");
	}
}