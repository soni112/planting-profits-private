var farmname="";

$(function() {
	
	addActiveClass($("#menu-strategies"));
	applyTabingOnSidemenu();
	registerTemplates();

	// showLoadingImageForStrategy();
	
	openStrategyComparisonSelectionPopup();
	// onclick="openStrategySelectionPopup(); return false;"
    resetStrategyComparisonGraph();
});

/*	***********************		Common/Builder Specific	***********************		*/

function registerTemplates(){
	$.template("strategyOutputDetailsTbodyTemplate", $("#strategyOutputDetailsTbodyTemplate"));
	$.template("strategyCheckboxTemplate", $("#strategyCheckboxTemplate"));
	$.template("strategyOutputTheadTemplate", $("#strategyOutputTheadTemplate"));
	$.template("strategyOutputTbodyTemplate", $("#strategyOutputTbodyTemplate"));
	$.template("strategyTableHeaderTemplate", $("#strategyTableHeaderTemplate"));
}

function toggleGraphSection(){
	$("#tableSectionForStrategy").hide();
	$("#graphSectionForStrategy").show();
	// $("#headerText").html("Graphical Comparison Of Strategies")
}

function resetStrategyComparisonGraph(){
    $('#xAxisValue').val('2');
    $('#yAxisValue').val('0');
}

function toggleTableSection(){
	$("#tableSectionForStrategy").show();
	$("#graphSectionForStrategy").hide();
	// $("#headerText").html("Strategy Comparison")
}

function prepareStrategyAnalysisGraph(object){
	/*var chart = AmCharts.makeChart( "multipleStrategyAnalysisChartDiv", {
		"type": "xy",
		"theme": "light",
		"balloon":{
			"fixedPosition":true,
		},
		"dataProvider": object.graphDataJsonObject,
		"valueAxes": [
			{
				"position": "bottom",
				"axisAlpha": 0
			},{
				"minMaxMultiplier": 1.2,
				"axisAlpha": 0,
				"position": "left"
			}
		],
		"startDuration": 0,
		"graphs": object.graphJsonObject,
		"marginLeft": 46,
		"marginBottom": 35,
		"legend":[{
			"enabled": true,
		} ]

	} );

	chart.addListener("rendered", function(){
		$('.amcharts-chart-div').find("a[href='http://www.amcharts.com/javascript-charts/']").hide();
	});*/

	var chart = AmCharts.makeChart( "multipleStrategyAnalysisChartDiv", {
		"type": "xy",
		"startDuration": 0.5,
		"graphs": object.graphJsonObject,
		"valueAxes": [
			{
				"axisAlpha": 0
			},
			{
				"position": "bottom",
				"axisAlpha": 0
			}
		],
		"dataProvider": object.graphDataJsonObject,
		"legend": {
			"enabled": true
		}
	});


}

function prepareVarianceGraph(object){
	AmCharts.makeChart("varianceGraphChartDiv",	{
		"type": "serial",
		"categoryField": "strategyName",
		"columnSpacing3D": 1,
		"columnWidth": 0.4,
		"autoMarginOffset": 40,
		"marginRight": 60,
		"marginTop": 60,
		"theme": "light",
		"categoryAxis": {
			"title": "Strategies"
		},
		"trendLines": [],
		"graphs": [{
			"balloonText": "Average Expected Income:<br><b>[[open]]</b>    <br>Minimum Expected Income:<br><b>[[low]]</b>   <br>Maximum Expected Income:<br><b>[[high]]</b>",

			"fillAlphas": 1,
			"fillColors": "#7f8da9",
			"gapPeriod": 2,
			"id": "g1",
			"lineColor": "#7f8da9",
			"lineThickness": 3,

			"negativeFillAlphas": 0,
			"negativeFillColors": "#db4c3c",
			"negativeLineAlpha": 0.11,
			"negativeLineColor": "#db4c3c",

			"title": "",
			"topRadius": 0,
			"type": "candlestick",
			"valueField": "Average Profit",

			"closeField": "Average Profit",
			"highField": "Maximum Profit",
			"lowField": "Minimum Profit",
			"openField": "Average Profit"
		}],
		"valueAxes": [{
			"title": "Estimated Income"
		}],
		"dataProvider": object.varianceGraphData,
		/*"dataProvider": [
			{
				"strategyName": "Baseline Strategy",
				"Average Profit": "136.65",
				"Minimum Profit": "134.15",
				"Maximum Profit": "136.96"
			},
			{
				"strategyName": "Strategy 1",
				"Average Profit": "135.26",
				"Minimum Profit": "131.50",
				"Maximum Profit": "135.95"
			},
			{
				"strategyName": "Strategy 2",
				"Average Profit": "132.90",
				"Minimum Profit": "128.30",
				"Maximum Profit": "135.27"
			},
			{
				"strategyName": "Strategy 3",
				"Average Profit": "134.94",
				"Minimum Profit": "132.63",
				"Maximum Profit": "137.24"
			}
		]*/
	});
}

function applyHtmlThroughTemplate(templateId, dataList, targetId){

	if ($(dataList).length > 0) {

		$(targetId).html("");
		$(templateId).tmpl(dataList).appendTo(targetId);
	}
}

function applyCheckBoxValidation(){
	$("#printStrategies").find("input[name='reportStrategyCheckbox']").each(function () {
		$(this).change(function () {
			if ($("#printStrategies").find("input[name='reportStrategyCheckbox']:checked").length > 3) {
				customAlerts('Select up to three strategies for the report', "error", 0);
				/**
				 * @changed - Abhishek
				 * @date - 09-01-2016
				 * @desc - not disabling the base selected strategy
				 */
				$("input[name='reportStrategyCheckbox']").each(function(){
					if(!$(this).attr("base")){
						$(this).prop("checked", false);
					}
				});
			}
		});
	});
}

function openStrategySelectionPopup(){

	var selectedStrategy = $('#printStrategies').find('input[name="reportStrategyCheckbox"]:checked');

	if(selectedStrategy.length == 0){
		customAlerts("Please select strategy for report generation.", "error", 0);
		return;
	}
	var strategyArray = [];
	$(selectedStrategy).each(function(){

		strategyArray.push($.trim($(this).val().split(',')[0]));

	});

	applyHtmlThroughTemplate("#strategyCheckboxTemplate", getStrategyComparisonDetails(strategyArray), "#strategySelectionDiv")

	var target = $('#strategySelectionDiv').find('input[name="reportStrategyCheckbox"]');
	target.each(function () {

		if($(this).attr('strategy-name').trim() == "Baseline Strategy"){
			$(this).prop("checked", true);
		} else {
			$(this).prop("checked", false);
		}

	});


	$('#generatePdf-popup').show();

}

function closeStrategySelectionPopup(){
	$('#generatePdf-popup').hide();
}

function openStrategyDetailsPopup(strategyId){

	var theadObj = {};
	var tbodyObj = _strategyOutput.strategyOutput;
	for(var key in tbodyObj){

		if(tbodyObj[key].farmCustomStrategyId == strategyId){
			tbodyObj = tbodyObj[key];
            theadObj['strategy'] = tbodyObj['strategy']
			break;
		}
	}
	var table = $('#strategyDetailsTable');
	applyHtmlThroughTemplate('#strategyOutputTheadTemplate', theadObj, table.find("thead"));
	applyHtmlThroughTemplate('#strategyOutputTbodyTemplate', tbodyObj, table.find("tbody"));


	$('#view-strategyDetails-popup').show();
}

function closeStrategyDetailsPopup(strategyId){
	$('#view-strategyDetails-popup').hide();
}

function openStrategyComparisonSelectionPopup(){
	$('#strategy-selection-popup').show();
}

function closeStrategyComparisonSelectionPopup(){
	$('#strategy-selection-popup').hide();
	showLoadingImage();
	window.location.href = "output-edit-farm-info.htm?farmId=" + currentFarmId;
}

function openVarianceGraphSetupPopup(){
	$('#variance-graphSetup-popup').show();
}

function closeVarianceGraphSetupPopup(){
	$('#variance-graphSetup-popup').hide();
}


function applyValidation(object) {
	$(object).css("border", "1px solid red");
	$(object).click(function() {
		$(this).css("border", "1px solid #cccccc");
	});
}

/*	***********************		Strategy Specific	***********************		*/

function processStrategyComparison(){
	
	var strategyArray = [];
	var checkedStrategyCheckBox = $('input[name="strategyComparisonCheckbox"]:checked');

	// if(checkedStrategyCheckBox.length == 0){
	// 	customAlerts("Select a strategy for comparison", "error", 0);
	// 	return;
	// }

	checkedStrategyCheckBox.each(function(){
		strategyArray.push($(this).val());
	});

	localStorage.removeItem("strategyArrayForComparison");
	localStorage.setItem("strategyArrayForComparison", strategyArray);
	
	var comparisonDetails = _strategyOutput;
	comparisonDetails['strategyDetails'] = getStrategyComparisonDetails(strategyArray);


	applyHeaders(comparisonDetails);
	applyComparisonDetails(comparisonDetails);

	// closeStrategyComparisonSelectionPopup();
	$('#strategy-selection-popup').hide();
}

function getStrategyComparisonDetails(strategyArray){
	var array = [];

	$(_strategyOutput['strategyDetails']).each(function(key, strategy){

		$(strategyArray).each(function(index, value){
			if(strategy.id == value){
				array.push(strategy);
			}
		});

	});

	return array;
}


function getStrategyForFarm(farmId){
	var strategyArray = [];
	var checkedStrategyCheckBox = $('input[name="strategyComparisonCheckbox"]:checked');

	if(checkedStrategyCheckBox.length == 0){
		customAlerts("Select a strategy for comparison", "error", 0);
		return;
	}

	checkedStrategyCheckBox.each(function(){
		strategyArray.push($(this).val());
	});


	if (typeof farmId != 'undefined') {
		$.ajax({
			url: 'ajaxRequest/getStrategyForFarm',
			type: 'POST',
			beforeSend: showLoadingImageForStrategy(),
			data: {
				farmId: farmId,
				strategyIdArray: strategyArray,
			},
			success: function (response) {
				var status = response.status;
				var result = response.result;
				if (status == 'success') {

					_strategyOutput = result;

					
					// applyHtmlThroughTemplate("#strategyCheckboxTemplate", result.strategyDetails, "#strategySelectionDiv");

					//	applying strategy details on deletion section
					var manageStrategiesDetails = {};
					manageStrategiesDetails['strategyDetails'] = result.strategyDetails;
					manageStrategiesDetails["reportFlag"] = true;
					manageStrategiesDetails["baselineflag"] = true;
					applyHtmlThroughTemplate("#strategyOutputDetailsTbodyTemplate", manageStrategiesDetails, "#deleteStrategiesTbody");

					//	applying strategy details on print section
					manageStrategiesDetails["baselineflag"] = false;
					applyHtmlThroughTemplate("#strategyOutputDetailsTbodyTemplate", manageStrategiesDetails, "#printStrategiesTbody");

					applyCheckBoxValidation();

					/*var data = {};
					data['graphDataJsonObject'] = [];
					data['graphJsonObject'] = [];
					prepareStrategyAnalysisGraph(data);*/
                    getAndApplyComparisonData();

					
					var varianceGraphData = {};
					varianceGraphData["varianceGraphData"] = result.jsonArrayForVarianceGraphData;
					prepareVarianceGraph(varianceGraphData);


					toggleTableSection();

					processStrategyComparison();


				} else if (status == 'Not exists') {
					customAlerts('No strategies prepared for strategy analysis', type_error, time);
				} else {
					location.reload();
				}
			},
			error: function (XMLHttpRequest, status, message) {
				customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
			}
		}).complete(function () {
			hideLoadingImageForStrategy();
		});
	}
}

function applyHeaders(outputDetails){

	var header = {};
//	crop comparison header
	header["headerDetails"] = outputDetails.jsonArrayForCropHeader;
	applyHtmlThroughTemplate("#strategyTableHeaderTemplate", header, "#cropComparisonThead");

//	resource comparison header
	header["headerDetails"] = outputDetails.jsonArrayForResourceHeader;
	applyHtmlThroughTemplate("#strategyTableHeaderTemplate", header, "#resourceComparisonThead");

//	conservation comparison header
	header["headerDetails"] = outputDetails.jsonArrayForConservationCropHeader;
	applyHtmlThroughTemplate("#strategyTableHeaderTemplate", header, "#conservationComparisonThead");

//	highRisk comparison header
	header["headerDetails"] = outputDetails.jsonArrayForHighRiskCropHeader;
	applyHtmlThroughTemplate("#strategyTableHeaderTemplate", header, "#highRiskComparisonThead");
}

function applyComparisonDetails(outputDetails){
//	crop output details
    var comparisonDetails = {};
    comparisonDetails["strategyDetails"] = outputDetails.strategyDetails;
	comparisonDetails["reportFlag"] = false;
	comparisonDetails["outputDetails"] = prepareCropComparisonDetails(outputDetails);
	applyHtmlThroughTemplate("#strategyOutputDetailsTbodyTemplate", comparisonDetails, "#cropOutputDetailsTbody");

//	resource output details
    var comparisonDetails1 = {};
    comparisonDetails1["strategyDetails"] = outputDetails.strategyDetails;
    comparisonDetails1["reportFlag"] = false;
	comparisonDetails1["outputDetails"] = outputDetails.jsonArrayForResource;
	applyHtmlThroughTemplate("#strategyOutputDetailsTbodyTemplate", comparisonDetails1, "#resourceOutputDetailsTbody");

//	conservation output details
    if(outputDetails.jsonArrayForConservationCrop[0].details.length != 0){
        var comparisonDetails2 = {};
        comparisonDetails2["strategyDetails"] = outputDetails.strategyDetails;
        comparisonDetails2["reportFlag"] = false;
		comparisonDetails2["outputDetails"] = outputDetails.jsonArrayForConservationCrop;
		applyHtmlThroughTemplate("#strategyOutputDetailsTbodyTemplate", comparisonDetails2, "#conservationDetailsTbody");
	}

//	highRisk output details
	if(outputDetails.jsonArrayForHighRiskCrop[0].details.length != 0){
        var comparisonDetails3 = {};
        comparisonDetails3["strategyDetails"] = outputDetails.strategyDetails;
        comparisonDetails3["reportFlag"] = false;
		comparisonDetails3["outputDetails"] = outputDetails.jsonArrayForHighRiskCrop;
		applyHtmlThroughTemplate("#strategyOutputDetailsTbodyTemplate", comparisonDetails3, "#highRiskDetailsTbody");
	}
}

function prepareCropComparisonDetails(outputDetails){
	var headerArr = outputDetails.jsonArrayForCropHeader;
	var strategyDetailsArr = outputDetails.jsonArrayForCrop;

	var holder = [];
	for(var key in strategyDetailsArr){
		var strategyDetails = strategyDetailsArr[key];
		var cropDetailsArr = [];
        for (var crop in headerArr){
        	for(var index in strategyDetails["details"]){
            	var cropDetails = strategyDetails["details"][index];
                if(cropDetails["name"] === headerArr[crop]){
                    cropDetailsArr.push(cropDetails);
                    break;
                }
            }
        }
        var data = {};
		data["strategyName"] = strategyDetails["strategyName"];
		data["strategyId"] = strategyDetails["strategyId"];
		data["details"] = cropDetailsArr;
		holder.push(data);
	}
	return holder;
}

function deleteStrategy(){

	var strategyArray = [];
	var target = $('#manageStrategies').find('input[name="reportStrategyCheckbox"]:checked');
	if (target.length == 0){
		customAlerts("Please select the strategies to delete", type_error, 0);
		return;
	}

	target.each(function(){
		strategyArray.push($.trim($(this).val().split(',')[0]));
	});

	alertify.confirm('Are you sure you want to delete strategy(s) ?', function(e) {
		if (e) {
			$.ajax({

				url : 'ajaxRequest/deleteStrategy',
				type : 'POST',
				data : {
					farmId : currentFarmId,
					strategyIdArray : strategyArray,

				},
				success : function(response) {
					var status = response.status;
					if (status == "success") {
						customAlerts("Strategy successfully deleted", type_success, 0);
					} else {
						customAlerts("Some problem occurred while deleting strategy(s)", type_error, 0);
					}
					var delay = 1000;
					setTimeout(function() {
						window.location.reload();
					}, delay);
				},
				error : function(XMLHttpRequest, status, message){
					customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
				}
			});
		}
	});

}

/**
 * @author abhishek
 * @date 30-11-2015
 */
function generateReport(){
	var strategyIdArray = [];
	var checkboxesSelectedForReport = $("#strategySelectionDiv").find("input[name='reportStrategyCheckbox']");
	var farmId = "";

	if(checkboxesSelectedForReport.length > 0){
		var baseStrategy;
		$(checkboxesSelectedForReport).each(function(){
			if($(this).prop("checked") == true){
				baseStrategy = $.trim($(this).val().split(',')[0]);
				/*var value = $(this).val().split(",");
				farmId = $.trim(value[1]);

				if ($(this).prop('base')) {

				} else {
					strategyIdArray.push($.trim(value[0]));
				}*/

			} else{
				strategyIdArray.push($(this).val().split(',')[0]);
			}
		});
		strategyIdArray.push(baseStrategy);

		var link = contextPath + '/Generate-Report.htm?farmId=' + currentFarmId + '&strategyID='+ strategyIdArray.toString();
		// console.log(link);
        // $.fileDownload(link);

		// var a = $('<a></a>').attr('href', link).attr('download', true).attr('target', '_blank');
        // $(a).trigger('click');

		window.location = contextPath + '/Generate-Report.htm?farmId=' + currentFarmId + '&strategyID='+ strategyIdArray.toString();

	} else {
		customAlerts("Select maximum of three strategies for generating report", "error", 0);
	}

}

function getAndApplyComparisonData(){

	var farmID = $('#sidemenu').find('.open').attr('delta');
	var xAxisValue = $('#xAxisValue').val();
	var yAxisValue = $('#yAxisValue').val();

	if(xAxisValue == ""){
		customAlerts("Please select a value for X Axis", "error", 0);
		applyValidation('#xAxisValue');
		return;
	} else if(yAxisValue == ""){
		customAlerts("Please select a value for Y Axis", "error", 0);
		applyValidation('#yAxisValue');
		return;
	} else if(xAxisValue == yAxisValue){
		applyValidation('#xAxisValue, #yAxisValue');
		customAlerts("X Axis and Y Axis cannot be same", "error", 0);
		return;
	}

	var strategyArray = localStorage.getItem("strategyArrayForComparison");

	$.ajax({
		url: 'ajaxRequest/getStrategyComparisonChartData',
		type: 'POST',
		beforeSend: showLoadingImageForStrategy(),
		data: {
			xAxisValue : xAxisValue,
			yAxisValue : yAxisValue,
			farmId : farmID,
			strategyArray: strategyArray
		},
		success: function (response) {
			var status = response.status;
			var result = response.result;
			if (status == 'success') {


				prepareStrategyAnalysisGraph(result);

			}
		},
		error: function (XMLHttpRequest, status, message) {
			customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
		}
	}).complete(function () {
		hideLoadingImageForStrategy();
	});
}

function getVarianceGraphDetails(){

	var strategyIdArray = [];
	var checkedStrategyCheckBox = $('input[name="strategyComparisonCheckbox"]:checked');

	checkedStrategyCheckBox.each(function(){
		strategyIdArray.push($(this).val());
	});

	var cropPriceSelectionArray = [];
	var cropYieldSelectionArray = [];
	var cropProductionCostSelectionArray = [];

	$('input[name="cropPrice"]:checked').each(function(){
		cropPriceSelectionArray.push($(this).val());
	});

	$('input[name="cropYield"]:checked').each(function(){
		cropYieldSelectionArray.push($(this).val());
	});

	$('input[name="productionCost"]:checked').each(function(){
		cropProductionCostSelectionArray.push($(this).val());
	});

	var rangeValues = {};

	$('.cropVarianceSpecific').each(function(){
		var data = {};
		data['minPrice'] = $(this).find('.cropMinPriceSpecific').val();
		data['maxPrice'] = $(this).find('.cropMaxPriceSpecific').val();
		data['minYield'] = $(this).find('.cropMinYieldSpecific').val();
		data['maxYield'] = $(this).find('.cropMaxYieldSpecific').val();


		rangeValues[$(this).find('.cropIdSpecific').val()] = data;
	});

	rangeValues = JSON.stringify(rangeValues);


	$.ajax({
		url: 'ajaxRequest/getVarianceGraphDetails',
		type: 'post',
		data: {
			farmId: currentFarmId,
			strategyIdArray: strategyIdArray,
			cropPriceSelection: cropPriceSelectionArray,
			cropYieldSelection: cropYieldSelectionArray,
			cropProductionCostSelection: cropProductionCostSelectionArray,
			rangeValues: rangeValues
		},
		beforeSend: showLoadingImageForStrategy(),
		success: function(response){
			var status = response.status;
			var result = response.result;
			if (status == 'success') {
				var varianceGraphData = {};
				varianceGraphData["varianceGraphData"] = result;
				prepareVarianceGraph(varianceGraphData);
			}
		},
		error: function(a, b, c){

		},
		complete: function(){
			hideLoadingImageForStrategy();
		}
	});


	closeVarianceGraphSetupPopup();
}