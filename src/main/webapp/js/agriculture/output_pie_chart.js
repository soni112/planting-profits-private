$(document).ready(function() {

	if (typeof jsonObj != 'undefined') {
		/**
		 * @changed - Abhishek
		 * @date - 08-01-2016
		 * @update - 19-01-2016
		 * @desc - changed function structure to dynamic application of chart by id supplied as parameters
		 */
		prepareCropAcreageChart(jsonObj,"chartdivByAcre");

		/**
		 * @changed - Abhishek
		 * @date - 08-01-2016
		 * @update - 19-01-2016
		 * @desc - changed function structure to dynamic application of chart by id supplied as parameters
		 */
		prepareCropAcreageChartByProfit(jsonObj, "chartdivByProfit");

		/**
		 * @changed - Abhishek
		 * @date - 08-01-2016
		 * @update - 19-01-2016
		 * @desc - changed function structure to dynamic application of chart by id supplied as parameters
		 */
		prepareResourceUsedUnsedChart(jsonObj, "chartContainer");

		changeValuesOfForCastSingleCropLimitchartdiv();
	}
});


function changeValuesOfForCastSingleResourcechartdiv(resourceName, jsonObject) {
	var jsonArray = jsonObject.Resource_Array;
	var resourceUnit = jsonObject.resourceUnit;
	$("#forCastSingleResourcechartdiv").css("display", "block");
	var chart = AmCharts.makeChart("forCastSingleResourcechartdiv", {
		"type": "serial",
		"categoryField": "resourceValue",
		"startDuration": 0,
		"categoryAxis": { "gridPosition": "start" },
		"trendLines": [],
		"numberFormatter" : {precision:-1, decimalSeparator:'.', thousandsSeparator:','},
		"graphs": [ {
			"balloonText": "[[bubbleMessage]]",
			"bullet": "round",
			"bulletSize" : "16",
			"title": "Amount of " + resourceName + " resource available (" + resourceUnit + ")",
			"valueField": "Potential_Profit",
			"bubbleMessage": "bubbleMessage" } ],
		"guides": [],
		"valueAxes": [ {"stackType": "regular", "title": "Estimated Income" } ],
		"allLabels": [],
		"balloon": {},
		"legend": { "useGraphSettings": true },
		/*"titles": [ {"size": 15, "text": "Amount of "+resourceName+ " resource available." } ],*/
		"dataProvider": jsonArray
	});
	//chart.addListener("rendered", removeWatermark);

	chart.addListener("clickGraphItem", handleClickForForCastSingleResource);

}

function changeValuesOfForCastSingleCropchartdiv(cropName, jsonObject) {
	var jsonArray = jsonObject.Resource_Array;
	$("#forCastSingleCropLimitchartdiv").css("display", "block");

	/**
	 * @change - Abhishek
	 * @date - 15-12-2015
	 */
	var legendTitle = $('#max_min_selector').val() + " acreage for " + cropName;

	var chart = AmCharts.makeChart("forCastSingleCropLimitchartdiv", {
		"type": "serial",
		"categoryField": "cropValue",
		"startDuration": 0,
		"categoryAxis": { "gridPosition": "start" },
		"trendLines": [],
		"numberFormatter" : {precision:-1, decimalSeparator:'.', thousandsSeparator:','},
		"graphs": [ {
			"balloonText": "[[bubbleMessage]]",
			"bullet": "round",
			"bulletSize" : "16",
			/*"title": "Amount of "+cropName+ " crop limit.",*/
			"title": legendTitle,
			"valueField": "Potential_Profit",
			"bubbleMessage": "bubbleMessage" } ],
		"guides": [],
		"valueAxes": [ {"stackType": "regular", "title": "Estimated Income" } ],
		"allLabels": [],
		"balloon": {},
		"legend": { "useGraphSettings": true },
		/*"titles": [ {"size": 15, "text": "Amount of "+resourceName+ " resource available." } ],*/
		"dataProvider": jsonArray
	});
	chart.addListener("clickGraphItem", handleClickForForCastSingleResource);

	chart.addListener("drawn", removeWatermark);

}

function handleClickForForCastSingleResource(event) {
	alterHTMLOfTableAndShowPopupTable(event.chart.dataProvider[event.index]);
}

function changeValuesOfForCastSingleCropLimitchartdiv(divID, jsonArray, dynamicTitleField, dynamicValueField) {
	var label_text = "[[title]]: [[value]]";
	if(dynamicValueField == "Profit"){
		label_text = "[[title]]: $[[value]]";
	}

	var sensitiveAnalysisCreateScenarioGraph = AmCharts.makeChart(divID, {
		"type" : "pie",
		"theme" : "light",
		"dataProvider" : jsonArray,
		"valueField" : dynamicValueField,
		"titleField" : dynamicTitleField,
		"outlineAlpha" : 0.4,
		"depth3D" : 15,
		// "balloonText" : "[[title]]<br><span style='font-size:14px'><b>[[value]] Acre</b> ([[percents]]%)</span>",
		"balloonText" : "[[title]]<br><span style='font-size:14px'><b>[[percents]]%</b></span>",
		"labelText": label_text,
		"angle" : 30,
		"export" : { "enabled" : true, }
	});

	jQuery('.chart-input').off().on('input change', function() {
		var property = jQuery(this).data('property');
		var target = sensitiveAnalysisCreateScenarioGraph;
		var value = Number(this.value);
		sensitiveAnalysisCreateScenarioGraph.startDuration = 0;
		if (property == 'innerRadius') {
			value += "%";
		}
		target[property] = value;
		sensitiveAnalysisCreateScenarioGraph.validateNow();
	});

	sensitiveAnalysisCreateScenarioGraph.addListener("drawn", removeWatermark);

}

/**
 * @added - Abhishek
 * @date - 08-01-2016
 * @desc - created so that charts can be created by outside of this javascript
 * @update - 19-01-2016
 * @desc - changed function structure to dynamic application of chart by id supplied as parameters
 */
function prepareCropAcreageChart(jsonObj, chartdivByAcre){
	var chartByAcre = AmCharts.makeChart(chartdivByAcre, {
		"type" : "pie",
		"theme" : "light",
		"startDuration": 0,
		"dataProvider" : jsonObj.jsonArrayForAcre,
		"valueField" : "Acre",
		"titleField" : "Crop",
		"percentFormatter":{precision:0, decimalSeparator:'.', thousandsSeparator:','},
		"outlineAlpha" : 0.4,
		"depth3D" : 15,
		// "balloonText" : "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>",
		"balloonText" : "[[title]]<br><span style='font-size:14px'><b>[[percents]]%</b></span>",
		"labelText": "[[title]]: [[value]]",
		"angle" : 30,
		"export" : { "enabled" : true, }
	});

	jQuery('.chart-input').off().on('input change', function() {
		var property = jQuery(this).data('property');
		var target = chartByAcre;
		var value = Number(this.value);
		chartByAcre.startDuration = 0;
		if (property == 'innerRadius') {
			value += "%";
		}
		target[property] = value;
		chartByAcre.validateNow();
	});

	chartByAcre.addListener("drawn", removeWatermark);

}

/**
 * @added - Abhishek
 * @date - 08-01-2016
 * @desc - created so that charts can be created by outside of this javascript
 * @update - 19-01-2016
 * @desc - changed function structure to dynamic application of chart by id supplied as parameters
 */
function prepareCropAcreageChartByProfit(jsonObj, chartdivByProfit){

	var chartByProfit = AmCharts.makeChart(chartdivByProfit, {
		"type" : "pie",
		"theme" : "light",
		"startDuration": 0,
		"dataProvider" : jsonObj.jsonArrayForProfit,
		"valueField" : "Profit",
		"titleField" : "Crop",
		"outlineAlpha" : 0.4,
		"percentFormatter":{precision:1, decimalSeparator:'.', thousandsSeparator:','},
		"depth3D" : 15,
		// "balloonText" : "[[title]]<br><span style='font-size:14px'><b>$[[value]]</b> ([[percents]]%)</span>",
		"balloonText" : "[[title]]<br><span style='font-size:14px'><b>[[percents]]%</b></span>",
		"labelText": "[[title]]: $[[value]]",
		"angle" : 30,
		"export" : { "enabled" : true, }
	});


	jQuery('.chart-input').off().on('input change', function() {
		var property = jQuery(this).data('property');
		var target = chartByProfit;
		var value = Number(this.value);
		chartByProfit.startDuration = 0;
		if (property == 'innerRadius') {
			value += "%";
		}
		target[property] = value;
		chartByProfit.validateNow();
	});

	chartByProfit.addListener("drawn", removeWatermark);
}

/**
 * @added - Abhishek
 * @date - 08-01-2016
 * @desc - created so that charts can be created by outside of this javascript
 * @update - 19-01-2016
 * @desc - changed function structure to dynamic application of chart by id supplied as parameters
 */
function prepareResourceUsedUnsedChart(jsonObj, chartContainer){
	var chart = new CanvasJS.Chart(chartContainer, {
		title : {
			text : "Resources",
			fontSize : 18
		},
		axisY : { title : "Used/Unused in %" },
		animationEnabled : false,
		toolTip :{
			shared : true,
			// content : "{label} {name}: {y} - <strong>#percent%</strong>"
			content : function (item){
                console.log(item);
                var x = item.entries[0];
				var y = item.entries[1];

				var total = x['dataPoint']['y'] + y['dataPoint']['y'];

                var xPercentage = parseInt((x['dataPoint']['y'] * 100) / total);
				var yPercentage = parseInt((y['dataPoint']['y'] * 100) / total);

				var xVal = (x['dataPoint']['label'] === 'Working Capital' ? '$' : '') + addCommaSignForTextWithOutId(x['dataPoint']['y'].toString());
				var yVal = (y['dataPoint']['label'] === 'Working Capital' ? '$' : '') + addCommaSignForTextWithOutId(y['dataPoint']['y'].toString());

				var xData = x['dataPoint']['label'] + ' ' + x['dataSeries']['name'] + ' : ' + xVal + ' - <strong>' + xPercentage + '%</strong>';
				var yData = y['dataPoint']['label'] + ' ' + y['dataSeries']['name'] + ' : ' + yVal + ' - <strong>' + yPercentage + '%</strong>';

				return xData + '<br/>' + yData;
			}
		},
		data : [
			{
				type : "stackedBar100",
				showInLegend : true,
				name : "Used",
                percentFormatString: "#0",
				dataPoints : jsonObj.cropResourceUsedForBarGraph
			},
			{
				type : "stackedBar100",
				showInLegend : true,
				name : "Unused",
                percentFormatString: "#0",
				dataPoints : jsonObj.cropResourceUnusedForBarGraph
			}
		],
		legend:{
			cursor:"pointer", itemclick:function(e) {
				if(typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible){
					e.dataSeries.visible = false;
				} else {
					e.dataSeries.visible = true;
				}
				chart.render();
			}
		}
	});
	chart.render();
    // chart.balloonFunction = function(item, graph) {
    //     var result = graph.balloonText;
    //     for (var key in item.dataContext) {
    //         if (item.dataContext.hasOwnProperty(key) && !isNaN(item.dataContext[key])) {
    //             var formatted = AmCharts.formatNumber(item.dataContext[key], {
    //                 precision: chart.precision,
    //                 decimalSeparator: chart.decimalSeparator,
    //                 thousandsSeparator: chart.thousandsSeparator
    //             }, 2);
    //             result = result.replace("[[" + key + "]]", formatted);
    //         }
    //     }
    //     return result;
    // };
}

function removeWatermark(){
	$('.amcharts-chart-div').find("a[href='http://www.amcharts.com/javascript-charts/']").remove();
}