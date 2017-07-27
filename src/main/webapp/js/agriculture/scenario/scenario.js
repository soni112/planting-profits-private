/**
 * @author Abhishek
 * Date 24-11-2015
 */
$(function () {
    applyTabingOnSidemenu();

    addActiveClass($("#menu-scenarios"));

    //  for handling of tabing on page
    $(".tabs-menu a").click(function (event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
		$(".tab-content").not(tab).removeClass("hidden");
        $(tab).fadeIn();
        $(tab).siblings().hide();

    });

    $("#cropAcerageGraphShow").click(function() {
        $("#tabViewCropAcreageText").hide();
        $("#tabViewCropAcreageGraph").show();
    });
    $("#cropAcerageTextShow").click(function() {
        $("#tabViewCropAcreageText").show();
        $("#tabViewCropAcreageGraph").hide();
    });
    $("#resourceUseGraphShow").click(function() {
        $("#tabViewResourceUsageText").hide();
        $("#tabViewResourceUsageGraph").show();
    });
    $("#resourceUseTextShow").click(function() {
        $("#tabViewResourceUsageText").show();
        $("#tabViewResourceUsageGraph").hide();
    });

    /**
     * @added - Abhishek
     * @date - 19-01-2016
     * @desc - added toggle functionality for graphs and text data for 'Apply to all' section
     */
    $("#strategyCropAcerageGraphShow").click(function() {
        $("#tabViewStrategyCropAcreageText").hide();
        $("#tabViewStrategyCropAcreageGraph").show();
    });
    $("#strategyCropAcerageTextShow").click(function() {
        $("#tabViewStrategyCropAcreageText").show();
        $("#tabViewStrategyCropAcreageGraph").hide();
    });
    $("#strategyResourceUseGraphShow").click(function() {
        $("#tabViewStrategyResourceUsageText").hide();
        $("#tabViewStrategyResourceUsageGraph").show();
    });
    $("#strategyRsourceUseTextShow").click(function() {
        $("#tabViewStrategyResourceUsageText").show();
        $("#tabViewStrategyResourceUsageGraph").hide();
    });

    /**
     * @added - Abhishek
     * @date - 25-01-2016
     * @desc - for applying data on crop specific if entered in global
     */
    applyCropSpecificAndGlobalValidation();

    /**
     * @added - Abhishek
     * @date - 01-02-2016
     * @desc - Applied validation for farm selection and strategy selection
     */
    applyFarmAndStrategySelctionValidation();
    var lastSavedScenario = sessionStorage.getItem("lastSavedScenario");

    if(lastSavedScenario != null){
        $('#sidemenu').find("a[href='#viewEditScenario']").trigger("click");
        $('#saved_scenario').val(lastSavedScenario);
        $('#saved_scenario').trigger('change');
        sessionStorage.removeItem("lastSavedScenario");
    }

    registerTemplates();
});

function registerTemplates(){
    $.template("scenarioAnalysisOutputTmpl", $("#scenarioAnalysisOutputTmpl"));
}

function toggleGraphSection(){
    $('#scenarioOutputGraphSection').show();
    $('#scenarioOutputTableSection').hide();
}

function toggleTableSection(){
    $('#scenarioOutputTableSection').show();
    $('#scenarioOutputGraphSection').hide();
}

function openStrategySelectPopup(){
    var strategyRadio = $('input[name="strategyRadio"]');
    if(strategyRadio.length == 1){
        saveScenarioData('createScenario');
    } else {
        $('#strategy-select-popup').show();
    }

}

function closeStrategySelectPopup(){
    $('#strategy-select-popup').hide();
}

function openScenarioAnalysisPopup(){
    $('#scenarioAnalysisOutputTbody').html('<tr class="tblgrn"><td colspan="5" class="success">Select and apply scenario to view details</td></tr>');
    $('#savedScenarioForCurrentStrategy').val('0, 0');
    $('#scenario-analysis-popup').show();
}

function closeScenarioAnalysisPopup(flag){
    if(flag) {
        $('#scenarioAnalysisOutputTbody').html('<tr class="tblgrn"><td colspan="5" class="success">No Strategies selected<br><a href="javascript;" onclick="openScenarioAnalysisPopup(); return false;">Click here</a></td></tr>');
    }
    $('#scenario-analysis-popup').hide();
}

function applyHtmlThroughTemplate(templateId, dataList, targetId){

    if ($(dataList).length > 0) {

        $(targetId).html("");
        $(templateId).tmpl(dataList).appendTo(targetId);
    }
}

function prepareScenarioComparisonChart(object) {
    var chart = AmCharts.makeChart( "scenarioAnalysisChartDiv", {
        "type": "serial",
        "theme": "light",
        "dataProvider": object,
        "valueAxes": [ {
            "gridColor": "#FFFFFF",
            "gridAlpha": 0.2,
            "dashLength": 0
        } ],
        "gridAboveGraphs": true,
        "startDuration": 0,
        "graphs": [ {
            "balloonText": "[[category]]: <b>$[[value]]</b>",
            "fillAlphas": 0.8,
            "lineAlpha": 0.2,
            "type": "column",
            "valueField": "potentialProfit"
        } ],
        "chartCursor": {
            "categoryBalloonEnabled": false,
            "cursorAlpha": 0,
            "zoomable": false
        },
        "categoryField": "strategyName",
        "categoryAxis": {
            "gridPosition": "start",
            "gridAlpha": 0,
            "tickPosition": "start",
            "tickLength": 20
        }

    } );

    chart.addListener("rendered", function () {
        $('.amcharts-chart-div').find("a[href='http://www.amcharts.com/javascript-charts/']").hide();
    });

}


function saveScenarioData(containerId) {
    var container = $("#" + containerId);
    if (!validateScenario(container)) {
        return false;
    }

    var strategyId;
    if (containerId == "createScenario") {
        var strategyRadio = $('input[name="strategyRadio"]:checked');
        if($('input[name="strategyRadio"]').length == 1){
            strategyId = $('input[name="strategyRadio"]').val();
        } else if (strategyRadio.length == 0) {
            customAlerts("Please select the strategy for this scenario", "error", 0);
            return false;
        } else {
            strategyId = $(strategyRadio).val();
        }

    } else{
        strategyId = $.trim($("#saved_scenario").find(":selected").val().split(',')[1]);
    }


    var scenarioDataCropSpecific = [];

    // Get Scenario Name
    var scenarioName = $.trim(container.find("input[type=text][name=scenario_name]").val());

    // Get Global Data
    var scenario_global_crop_price = $.trim(container.find("input[type=text][name=scenario_global_crop_price]").val());
    var scenario_global_crop_yields = $.trim(container.find("input[type=text][name=scenario_global_crop_yields]").val());
    var scenario_global_crop_prod_cost = $.trim(container.find("input[type=text][name=scenario_global_crop_prod_cost]").val());
    var scenario_Comment = $.trim(container.find("textarea[name=scenarioGlobalComment]").val());
    var crop_specific_comment = $.trim(container.find("textarea[name=scenarioCropSpecificComment]").val());

    // Get Crop Specific Data
    $("#"+containerId).find("table tbody tr").each(function () {
        var crop_data = {};
        crop_data['crop_id'] = $(this).attr("data-id");
        crop_data['crop_name'] = $(this).find("td:eq(0)").text();
        crop_data['crop_price'] = $(this).find("td:eq(1) input").val();
        crop_data['crop_yield'] = $(this).find("td:eq(2) input").val();
        crop_data['crop_prod_cost'] = $(this).find("td:eq(3) input").val();
        scenarioDataCropSpecific.push(crop_data);
    });

    var scenarioId = null;

    if (typeof window.scenarioId != 'undefined') {
        scenarioId = $.trim($("#saved_scenario").find(":selected").val().split(',')[0]);
    }
    showLoadingImage();
    $.ajax({
        url: 'ajaxRequest/scenario/saveScenario',
        type: 'POST',
        data: {
            scenarioName: scenarioName,
            scenario_global_crop_price: scenario_global_crop_price,
            scenario_global_crop_yields: scenario_global_crop_yields,
            scenario_global_crop_prod_cost: scenario_global_crop_prod_cost,
            scenario_Comment : scenario_Comment,
            crop_specific_comment : crop_specific_comment,
            cropSpecific: JSON.stringify(scenarioDataCropSpecific),
            farmId: farmId,
            strategyId: strategyId,
            scenarioId: scenarioId
        },
        success: function (response) {
            if (response.status.indexOf("success") != -1) {

                customAlerts("Scenario has been saved", "success", 0);
                // showLoadingImage();
                setTimeout(function () {
                    sessionStorage.setItem("lastSavedScenario", response.result);
                    window.location.reload();
                }, 2000);
            } else {
                customAlerts(response.result, "error", 0);
                hideLoadingImage();
            }

        },
        error: function (a, b, c) {
            // console.log(a);
            // console.log(b);
            // console.log(c);
        },
        complete: function (response) {
            delete window.scenarioId;
            // hideLoadingImage();
        }
    });

}

function loadSelectedScenarioData(obj) {

    // Disable data inputs for scenario
    enableOrDisableScenarioInput($('#viewEditScenario'), "disable");

    // Get selected Scenario
    //var scenarioId = $("#saved_scenario").find(":selected").val();
    var tempData = $("#saved_scenario").find(":selected").val();
    if(tempData === undefined){
        return;
    } else {
        tempData = tempData.split(',');
    }

    var scenarioId = $.trim(tempData[0]);
    var strategyId = $.trim(tempData[1]);
    // Check if scenario is selected or not
    if (scenarioId == 0) {
        resetScenarioInputs($('#viewEditScenario'));
        return;
    }

    var message = "";
    var isValidated = false;
    // Check if everything is fine
    if (!validateNumericValue(farmId)) {
        isValidated = false;
        message = "Farm ID is invalid";
    } else if (!validateNumericValue(strategyId)) {
        isValidated = false;
        message = "Strategy ID is invalid";
    } else if (!validateNumericValue(scenarioId)) {
        isValidated = false;
        message = "Scenario ID is invalid";
    } else {
        isValidated = true;
    }

    if (isValidated) {
        // Show Loading
        showLoadingImage();

        // Call Ajax to load Scenario
        $.ajax({
            url: 'ajaxRequest/scenario/getScenario/' + farmId + '/' + strategyId + '/' + scenarioId,
            type: 'GET',
            success: function (response) {

                // Check if valid data
                if (response.status.indexOf("success") != -1) {
                    // Set Data to HTML & Enable remaining sections
                    changeFlagForCreate = [];
                    changeFlagForEdit = [];
                    setScenarioDataToHTML(response.result, $('#viewEditScenario'));
                } else {
                    customAlerts(response.result, "error", 0);
                }
            },
            error: function (a, b, c) {

            },
            complete: function (response) {
                hideLoadingImage();
            }
        });
    }
    else {
        customAlerts(message, "error", 0);
        return false;
    }
}

function modifyScenarioHandler() {
    enableOrDisableScenarioInput($('#viewEditScenario'), "enable");
}

function setScenarioDataToHTML(data, container) {
    $(container).find("input[name=scenario_name]").val(data.scenarioName);
    /**
     * @changed - Abhishek
     * @date - 27-01-2016
     * @desc - applied trigger for add validation for change in global to crop specific
     */
    /*$(container).find("input[name=scenario_global_crop_price]").val(data.scenarioGlobalCropPrice).trigger('change');
    $(container).find("input[name=scenario_global_crop_yields]").val(data.scenarioGlobalCropYields).trigger('change');
    $(container).find("input[name=scenario_global_crop_prod_cost]").val(data.scenarioGlobalCropProdCost).trigger('change');*/

    if(data.scenarioGlobalCropPrice != 0 ){
        $(container).find("input[name=scenario_global_crop_price]").val(data.scenarioGlobalCropPrice).trigger('change');
    } else {
        $(container).find("input[name=scenario_global_crop_price]").val('');
    }
    if(data.scenarioGlobalCropYields != 0){
        $(container).find("input[name=scenario_global_crop_yields]").val(data.scenarioGlobalCropYields).trigger('change');
    } else {
        $(container).find("input[name=scenario_global_crop_yields]").val('');
    }
    if(data.scenarioGlobalCropProdCost != 0){
        $(container).find("input[name=scenario_global_crop_prod_cost]").val(data.scenarioGlobalCropProdCost).trigger('change');
    } else {
        $(container).find("input[name=scenario_global_crop_prod_cost]").val('');
    }

    $(container).find("textarea[name=scenarioGlobalComment]").val(data.globalComment);
    $(container).find("textarea[name=scenarioCropSpecificComment]").val(data.cropSpecificComment);

    var tableObj = $(container).find("table");
    var cropSpecificFlag = false;
    tableObj.find("tbody tr").each(function () {
        if ((typeof data.cropSpecific[$(this).attr("data-id")]) != 'undefined') {
            cropSpecificFlag = true;
            var cropData = data.cropSpecific[$(this).attr("data-id")];
            $(this).find("td:eq(1) input").val(cropData.price != null ? cropData.price : "");
            $(this).find("td:eq(2) input").val(cropData.yields != null ? cropData.yields : "");
            $(this).find("td:eq(3) input").val(cropData.prodCost != null ? cropData.prodCost : "");
        } else {
            // console.log(data.cropSpecific[$(this).attr("data-id")]);
        }
    });

    if(cropSpecificFlag && (data.scenarioGlobalCropPrice == 0 && data.scenarioGlobalCropYields == 0 && data.scenarioGlobalCropProdCost == 0)){
        $('#cropSpecifcIndicator').show();
    } else {
        $('#cropSpecifcIndicator').hide();
    }

}

function resetScenarioInputs(container) {
    $(container).find("input").val("");
}

function enableOrDisableScenarioInput(container, type) {
    if ((typeof type != 'string') || (type.indexOf("disable") == -1 && type.indexOf("enable") == -1)) {
        customAlerts("Invalid type specified for enable/disable", "error", 0);
        return;
    }
    if (type.indexOf("enable") != -1) {
        $(container).find("input[name=scenario_name]").removeAttr("disabled");
        $(container).find("input[name=scenario_global_crop_price]").removeAttr("disabled");
        $(container).find("input[name=scenario_global_crop_yields]").removeAttr("disabled");
        $(container).find("input[name=scenario_global_crop_prod_cost]").removeAttr("disabled");

        var tableObj = $(container).find("table");
        tableObj.find("tbody tr").each(function () {
            $(this).find("input").removeAttr("disabled");
        });
    } else if (type.indexOf("disable") != -1) {
        $(container).find("input[name=scenario_name]").attr("disabled", true);
        $(container).find("input[name=scenario_global_crop_price]").attr("disabled", true);
        $(container).find("input[name=scenario_global_crop_yields]").attr("disabled", true);
        $(container).find("input[name=scenario_global_crop_prod_cost]").attr("disabled", true);

        var tableObj = $(container).find("table");
        tableObj.find("tbody tr").each(function () {
            $(this).find("input").attr("disabled", true);
        });
    }
}

function validateScenario(container) {
    var isValidated = true;
    var scenarioNameObj = $(container).find("input[name=scenario_name]");
    var scenario_global_crop_price = $(container).find("input[name=scenario_global_crop_price]");
    var scenario_global_crop_yields = $(container).find("input[name=scenario_global_crop_yields]");
    var scenario_global_crop_prod_cost = $(container).find("input[name=scenario_global_crop_prod_cost]");

    if ($.trim($(scenarioNameObj).val()).length <= 0) {
        customAlerts("Please enter scenario name", "error", 0);
        addErrorClassOnObject(scenarioNameObj);
        isValidated = false;
    }
    else if ($.trim($(scenario_global_crop_price).val()).length > 0 && !validateNumericNegativeValue($.trim($(scenario_global_crop_price).val()))) {
        customAlerts("Please correct global crop price", "error", 0);
        addErrorClassOnObject(scenario_global_crop_price);
        isValidated = false;
    } else if ($.trim($(scenario_global_crop_yields).val()).length > 0 && !validateNumericNegativeValue($.trim($(scenario_global_crop_yields).val()))) {
        customAlerts("Please correct global crop yields", "error", 0);
        addErrorClassOnObject(scenario_global_crop_yields);
        isValidated = false;
    } else if ($.trim($(scenario_global_crop_prod_cost).val()).length > 0 && !validateNumericNegativeValue($.trim($(scenario_global_crop_prod_cost).val()))) {
        customAlerts("Please correct global crop production cost", "error", 0);
        addErrorClassOnObject(scenario_global_crop_prod_cost);
        isValidated = false;
    } else if (isValidated) {
        // Do Crop Specific Validation
        var tableObj = $(container).find("table");
        $(tableObj).find("tbody tr").each(function () {
            if ($.trim($(this).find("td:eq(1) input").val()).length > 0 && !validateNumericNegativeValue($.trim($(this).find("td:eq(1) input").val()))) {
                customAlerts("Please correct price for " + $(this).find("td:eq(0)").text(), "error", 0);
                addErrorClassOnObject($(this).find("td:eq(1) input"));
                isValidated = false;
                return false;
            }
            else if ($.trim($(this).find("td:eq(2) input").val()).length > 0 && !validateNumericNegativeValue($.trim($(this).find("td:eq(2) input").val()))) {
                customAlerts("Please correct price for " + $(this).find("td:eq(0)").text(), "error", 0);
                addErrorClassOnObject($(this).find("td:eq(2) input"));
                isValidated = false;
                return false;
            }
            else if ($.trim($(this).find("td:eq(2) input").val()).length > 0 && !validateNumericNegativeValue($.trim($(this).find("td:eq(2) input").val()))) {
                customAlerts("Please correct price for " + $(this).find("td:eq(0)").text(), "error", 0);
                addErrorClassOnObject($(this).find("td:eq(2) input"));
                isValidated = false;
                return false;
            }
        });
    }

    return isValidated;
}

/**
 * @added - Abhishek
 * @date - 08-01-2016
 * @desc - getting output details for selected scenario and strategy
 */
function getScenarioOutputDetailsForStrategy(){

    var tempData = $("#savedScenarioForCurrentStrategy").val().split(',');
    var scenarioID = $.trim(tempData[0]);

    var strategyIdArray = [];

    var checkedStrategyCheckBox = $('#strategyForScenarioDiv').find('input[name="strategyScenarioCheckbox"]:checked');

    if(checkedStrategyCheckBox.length == 0){
        customAlerts("Select a strategy for scenario analysis", "error", 0);
        openScenarioAnalysisPopup();
        return;
    }

    checkedStrategyCheckBox.each(function(){
        strategyIdArray.push($(this).val());
    });

    
    //if(scenarioID != 0){

        showLoadingImage();

        $.ajax({
            url: 'ajaxRequest/getApplyToAllScenariosOutput',
            type: 'post',
            data: {
                farmId: farmId,
                scenarioId: scenarioID,
                strategyIdArray: strategyIdArray
            },
            success: function (response) {

                var status = response.status;
                var result = response.result;
                if (status == 'success') {

                    closeScenarioAnalysisPopup();

                    applyHtmlThroughTemplate('#scenarioAnalysisOutputTmpl', result.outputDetails, '#scenarioAnalysisOutputTbody');

                    if(scenarioID == "0"){
                        $('#scenarioAnalysisChartDiv').html('');
                        toggleTableSection();
                    } else {
                        prepareScenarioComparisonChart(result.graphDetails);
                    }


                } else {
                    customAlerts(response.result, "error", 0);
                }

            },
            error: function (jqXHR, textStatus, errorThrow) {
                // console.log("jqXHR : " + jqXHR);
                // console.log("textStatus : " + textStatus);
                // console.log("errorThrow : " + errorThrow);
            },
            complete: function (response) {
                hideLoadingImage();
            }
        });


    //} else {
    //    customAlerts("Select a Scenario.", "error", 0);
    //}


}

/**
 * @added - Abhishek
 * @date - 25-01-2016
 * @desc - for applying data on crop specific if entered in global
 */
var changeFlagForCreate = [], changeFlagForEdit = [];
function applyCropSpecificAndGlobalValidation(){
    $('#globalCropPriceCreate, #globalYieldCreate, #globalVarCostCreate').change(function(){
        var currentGroupID = $(this).attr('id');
        if(currentGroupID == 'globalCropPriceCreate'){

            var globalCropPriceCreate = $(this).val();
            $('.globalCropPriceCreate').val(globalCropPriceCreate);
            changeFlagForCreate.unshift('globalCropPriceCreate');

        } else if(currentGroupID == 'globalYieldCreate'){

            var globalYieldCreate = $(this).val();
            $('.globalYieldCreate').val(globalYieldCreate);
            changeFlagForCreate.unshift('globalYieldCreate');

        } else if(currentGroupID == 'globalVarCostCreate'){

            var globalVarCostCreate = $(this).val();
            $('.globalVarCostCreate').val(globalVarCostCreate);
            changeFlagForCreate.unshift('globalVarCostCreate');

        }
    });

    $('#globalCropPriceEdit, #globalYieldEdit, #globalVarCostEdit').change(function(){
        var currentGroupID = $(this).attr('id');
        if(currentGroupID == 'globalCropPriceEdit'){

            var globalCropPriceEdit = $(this).val();
            $('.globalCropPriceEdit').val(globalCropPriceEdit);
            changeFlagForEdit.unshift('globalCropPriceEdit');

        } else if(currentGroupID == 'globalYieldEdit'){

            var globalYieldEdit = $(this).val();
            $('.globalYieldEdit').val(globalYieldEdit);
            changeFlagForEdit.unshift('globalYieldEdit');

        } else if(currentGroupID == 'globalVarCostEdit'){

            var globalVarCostEdit = $(this).val();
            $('.globalVarCostEdit').val(globalVarCostEdit);
            changeFlagForEdit.unshift('globalVarCostEdit');

        }
    });

    $('.globalCropPriceCreate, .globalYieldCreate, .globalVarCostCreate').change(function(){

       var currentGroupClass = $(this).attr('class');
       if(currentGroupClass == 'globalCropPriceCreate'){

           if (changeFlagForCreate.indexOf('globalCropPriceCreate') != -1) {
               $('#critical-message-pop-up').show();
               $('#globalCropPriceCreate').val('');
               changeFlagForCreate.splice(changeFlagForCreate.indexOf('globalCropPriceCreate'), 1);
           }

       } else if(currentGroupClass == 'globalYieldCreate'){

           if (changeFlagForCreate.indexOf('globalYieldCreate') != -1) {
               $('#critical-message-pop-up').show();
               $('#globalYieldCreate').val('');
               changeFlagForCreate.splice(changeFlagForCreate.indexOf('globalYieldCreate'), 1);
           }

       } else if(currentGroupClass == 'globalVarCostCreate'){

           if (changeFlagForCreate.indexOf('globalVarCostCreate') != -1) {
               $('#critical-message-pop-up').show();
               $('#globalVarCostCreate').val('');
               changeFlagForCreate.splice(changeFlagForCreate.indexOf('globalVarCostCreate'), 1);
           }

       }
       changeFlagForCreate = false;

    });

    $('.globalCropPriceEdit, .globalYieldEdit, .globalVarCostEdit').change(function(){

        var currentGroupClass = $(this).attr('class');
        if(currentGroupClass == 'globalCropPriceEdit'){

            if (changeFlagForEdit.indexOf('globalCropPriceEdit') != -1) {
                $('#critical-message-pop-up').show();
                $('#globalCropPriceEdit').val('');
                changeFlagForEdit.splice(changeFlagForEdit.indexOf('globalCropPriceEdit'), 1);
            }

        } else if(currentGroupClass == 'globalYieldEdit'){

            if (changeFlagForEdit.indexOf('globalYieldEdit') != -1) {
                $('#critical-message-pop-up').show();
                $('#globalYieldEdit').val('');
                changeFlagForEdit.splice(changeFlagForEdit.indexOf('globalYieldEdit'), 1);
            }

        } else if(currentGroupClass == 'globalVarCostEdit'){

            if (changeFlagForEdit.indexOf('globalVarCostEdit') != -1) {
                $('#critical-message-pop-up').show();
                $('#globalVarCostEdit').val('');
                changeFlagForEdit.splice(changeFlagForEdit.indexOf('globalVarCostEdit'), 1);
            }

        }
        changeFlagForEdit = false;

    });

}

/**
 * @added - Abhishek
 * @date - 01-02-2016
 * @desc - Applied validation for farm selection and strategy selection
 */
function applyFarmAndStrategySelctionValidation(){
    $("input[name='radioFarm']").change(function(){

        if ($(this).prop("checked")) {
            $(this).parents().eq(2).next().find("input[name='strategyIdCheckbox']").prop('disabled', false);
        }

        $(this).parents().eq(3).siblings().find("input[name='strategyIdCheckbox']").prop('disabled', true);

    });
}

function loadScenarioPage() {

    if($('#accordion').find("input[name='radioFarm']:checked").length == 0){
        customAlerts("Select farm to continue or Cancel", "error", 0);
        return false;
    }

    if($("input[name='radioFarm']:checked").parents().eq(2).next().find("input[name='strategyIdCheckbox']:checked").length == 0){
        customAlerts("Select strategy to continue", "error", 0);
        return false;
    }

    var selectedFarmId = $("input[name='radioFarm']:checked").val();

    var checkBoxList = $("input[name='radioFarm']:checked").parents().eq(2).next().find("input[name='strategyIdCheckbox']:checked");

    //var selectedStrategyId = $("input[name='radioFarm']:checked").parents().eq(2).next().find("input[name='strategyIdCheckbox']:checked").val();

    var selectedStrategyIdArray = [];
    $(checkBoxList).each(function(){
        selectedStrategyIdArray.push($(this).val());
    });


    if (typeof selectedFarmId == 'undefined' && selectedStrategyId == 'undefined') {
        showLoadingImage();
        setTimeout(function(){
            window.location.href = 'farm.htm';
        }, 1000);
    } else{
        showLoadingImage();
        //window.location.href = 'view-farm-scenarios.htm?farmId=' + selectedFarmId + '&strategyId=' + selectedStrategyId;
        window.location.href = 'view-farm-scenarios.htm?farmId=' + selectedFarmId + '&strategyId[]=' + selectedStrategyIdArray;
    }

}

function toggleCollapseable(currentRefenrence){
    if ($(currentRefenrence).find('i').hasClass('fa-angle-down')) {
        $(currentRefenrence).find('i').removeClass('fa-angle-down');
        $(currentRefenrence).find('i').addClass('fa-angle-right');
        $(currentRefenrence).removeClass('collapsedSpecific');
        $(currentRefenrence).find("input[name='radioFarm']").prop('checked', false);
        $(currentRefenrence).find("input[name='radioFarm']").prop('checked', false).trigger('change');

    } else {

        $(currentRefenrence).find('i').addClass('fa-angle-down');
        $(currentRefenrence).find('i').removeClass('fa-angle-right');
        $(currentRefenrence).find("input[name='radioFarm']").prop('checked', true);
        $(currentRefenrence).find("input[name='radioFarm']").prop('checked', true).trigger('change');

        $('.collapsedSpecific').each(function(){
            $(this).find('i').addClass('fa-angle-right');
            $(this).find('i').removeClass('fa-angle-down');
            $(this).removeClass('collapsedSpecific');
        });

        $(currentRefenrence).addClass('collapsedSpecific')
    }



}

function addCommaForValue(value){
    var valueWithPoint = Number(value).toFixed(2);
    var valueWithComma;
    if (valueWithPoint % 1 != 0) {
        valueWithComma = Number(valueWithPoint).toLocaleString('en');
    } else {
        valueWithComma = Number(valueWithPoint).toLocaleString('en');
    }
    return valueWithComma;
}