var $fixedTables;

$(function () {
//	alert();
    $('body').on('focus', ".datepicker", function () {
        $(this).datepicker({
            clearBtn: true,
            autoclose: true,
        });
    });

    $(".strategy").change(function () {
        onStrategyChange();
    });
// $(".crops").change(function() {
// onCropSelectedOrRemoved($(this));
// });
// $(".resources").change(function() {
// onResourceSelectedOrRemoved($(this));
// });
    if ($("#Plan_by_Fields_table tbody tr").length < 1) {
        $("#Plan_by_Fields_table").hide();
    }
    if ($("#group_table_tbody tr").length == 0) {
        hideModifyColumnInCropLimitTable();
    }

    $(".multiselect", this.el).multiselect({
        includeSelectAllOption: true
    });

    hideLoadingImage();
    /**
     * @added - Abhishek
     * @date - 21-03-2016
     * @desc - according to slide#17 of 03142016
     */
    //$('#forward_sales_information_tbody').find('input[type="text"]').trigger('change');
    registerTemplates();

    buildBaselineStrategySeletion();

    var flag = localStorage.getItem('cropLimitFlag');
    if (typeof flag != 'undefined' && flag) {
        localStorage.removeItem('cropLimitFlag');
        showCropLimitsTab();
    }

    var resourceFlag = localStorage.getItem('resourcesFlag');
    if (typeof resourceFlag != 'undefined' && resourceFlag) {
        localStorage.removeItem('resourcesFlag');
        showResourcesTab();
    }

    var profitFlag = localStorage.getItem('profitCropFlag');
    if (typeof profitFlag != 'undefined' && profitFlag) {
        localStorage.removeItem('profitCropFlag');
        showCropsAndCropInformationTab();
        nextCropsAndCropsInformation();
    }

    var cropChoiceFlag = localStorage.getItem('cropChoicesFlag');
    if (typeof cropChoiceFlag != 'undefined' && cropChoiceFlag) {
        localStorage.removeItem('cropChoicesFlag');
        showCropsAndCropInformationTab();
    }

    var cropFieldChoiceFlag = localStorage.getItem('cropFieldChoicesFlag');
    if (typeof cropFieldChoiceFlag != 'undefined' && cropFieldChoiceFlag) {
        localStorage.removeItem('cropFieldChoicesFlag');
        showCropFieldChoicesTab();
        nextPlanByField();
    }

    var cropFieldFlag = localStorage.getItem('cropFieldFlag');
    if (typeof cropFieldFlag != 'undefined' && cropFieldFlag) {
        localStorage.removeItem('cropFieldFlag');
        showCropFieldChoicesTab();
    }


    $fixedTables = $('.tbl-fixd-hdr, .fld-chc-tbl-fixd-hdr,' +
        ' .resources-tbl-fixd-hdr, .crop-resources-tbl-fixd-hdr, .forward-sales-tbl-fixd-hdr');
    $fixedTables.bind('rowAddOrRemove', rowAddOrRemovehandler);
    $fixedTables.trigger('rowAddOrRemove');
    buildFixedTable();

    $('#crop_limits_table_tbody, #crop_contract_table_tbody, #group_table_tbody').find('tr').each(function () {
        if ($(this).find('.minCropAcreage').val() != '' && $(this).find('.minCropAcreage').val() != '0') {
            $(this).find('.minCropAcreage').trigger('change');
        } else if ($(this).find('.minCropAcreagePercentage').val() != '' && $(this).find('.minCropAcreagePercentage').val() != '0') {
            $(this).find('.minCropAcreagePercentage').trigger('change');
        }
        if ($(this).find('.maxCropAcreage').val() != '' && $(this).find('.maxCropAcreage').val() != '0') {
            $(this).find('.maxCropAcreage').trigger('change');
        } else if ($(this).find('.maxCropAcreagePercentage').val() != '' && $(this).find('.maxCropAcreagePercentage').val() != '0') {
            $(this).find('.maxCropAcreagePercentage').trigger('change');
        }
        // $(this).find('.minCropAcreagePercentage').trigger('change');
        // $(this).find('.maxCropAcreage').trigger('change');
        // $(this).find('.maxCropAcreagePercentage').trigger('change');
    });

    maxCropLimitPercentageValidate();

});
var currency = "$";
var uom = "acre";
var uomInCaps = "Acres";
var contractIdentifier = "Firm";
var totalAcresWhenSwitchingStrategyFieldsToAcres = 0;
var totalAcresWhenSwitchingStrategyAcresToFields = 0;
var flagSwitchedStrategyFieldsToAcres = false;
var flagSwitchedStrategyAcresToFields = false;

function maxCropLimitPercentageValidate() {
    $("input.popoverPercentage").change(function () {
        if ($.trim($(this).val()) == 0 && $(this).val() !== '') {
            $(this).parents().eq(1).find('a[id="popoverPercentageHelp"]').show();
        } else {
            $(this).parents().eq(1).find('a[id="popoverPercentageHelp"]').hide();
        }
    });
}

function rowAddOrRemovehandler() {
    var $tbody = $(this).find('tbody');
    var $thead = $(this).find('thead');
    if ($tbody.children().length > 6) {
        $thead.addClass('tbl-header-scrolll-width');
    } else {
        $thead.removeClass('tbl-header-scrolll-width');
    }
}

function registerTemplates() {
    $.template('additional-crop-income-tbody-tmpl', $('#additional-crop-income-tbody-tmpl'));
    $.template('optional-crop-info-table-tmpl', $('#optional-crop-info-table-tmpl'));
}

function buildFixedTable() {

    var td = $('.fld-chc-tbl-fixd-hdr thead > tr > td');
    var avgWidth = 100 / td.length;

    td.each(function () {
        $(this).css('width', avgWidth + '%');
    });

    $('.fld-chc-tbl-fixd-hdr tbody > tr > td').each(function () {
        $(this).css('width', avgWidth + '%');
    });

    var $target = $('#cropInformationDetailFirstTable');
    if ($target.find('tbody').children().length > 4) {
        $target.find('thead').addClass('overflow-table-fixed');
    } else {
        $target.find('thead').removeClass('overflow-table-fixed');
    }
    $fixedTables.trigger('rowAddOrRemove');

}

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
    // setOriginalImagesInAdv();
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
            // setAdvertisementImageForCropSelection();
            manageStep2 = true;
        } else if (globalStepImageNavigation == 3) {
            $('#image_bar').attr("src", "images/progress_bar/progress-bar3.png");
            $("#crop_information").addClass("visited active parent");
            $("#cropinfodetail").removeClass("hidden");
            // setAdvertisementImageForCropInfoDetails();
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
            // setAdvertisementImageForForwardSales();
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
            // setAdvertisementImageForCropSelection();
            manageStep2 = true;
        } else if (globalStepImageNavigation == 4) {
            $('#image_bar').attr("src", "images/progress_bar/progress-bar4.png");
            $("#crop_information").addClass("visited active parent");
            $("#cropinfodetail").removeClass("hidden");
            // setAdvertisementImageForCropInfoDetails();
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
            // setAdvertisementImageForForwardSales();
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
        if (oldStrategy == "fields" && strategy == "acres") {
            $("#acres_value").val($("#Plan_by_Fields_table tfoot tr:nth(0) td:nth(1)").text().trim());
            /**
             * @changed - Abhishek
             * @date - 05-12-2015
             */
            //$("#acres_value").attr("disabled", "disabled");

            $("#total_land_available").text($("#Plan_by_Fields_table tfoot tr:nth(0) td:nth(1)").text().trim());
            flagSwitchedStrategyFieldsToAcres = true;
            flagSwitchedStrategyAcresToFields = false;
            totalAcresWhenSwitchingStrategyFieldsToAcres = Number(removeAllCommas($("#Plan_by_Fields_table tfoot tr:nth(0) td:nth(1)").text().trim()));
        } else if (oldStrategy == "acres" && strategy == "fields") {
            flagSwitchedStrategyFieldsToAcres = false;
            flagSwitchedStrategyAcresToFields = true;
            $("#land_acres_planningbyfield").text("Land entered when planning by Acres : " + $("#acres_value").val() + " acres");
            $("#total_land_available").text($("#Plan_by_Fields_table tfoot tr:nth(0) td:nth(1)").text().trim());
            totalAcresWhenSwitchingStrategyAcresToFields = Number(removeAllCommas($("#acres_value").val()));
        }
        oldStrategy = strategy;
    }
    if (strategy == "fields") {
        $("#field-choice").removeClass("hidden");
        $("#show_hide_field_variance_button").show();
    } else if (strategy == "acres") {
        $("#field-choice").addClass("hidden");
        $("#show_hide_field_variance_button").hide();
    } else {
        $("#field-choice").removeClass("visited");
        $("#show_hide_field_variance_button").hide();
    }
}

function onCropSelectedOrRemoved(cropObject) {
    if ($(cropObject).prop("checked") == true) {
        addCropInAllTables($(cropObject).val());
    } else if ($(cropObject).prop("checked") == false) {
        /**
         * @changed - Abhishek
         * @date - 21-03-2016
         * @desc - according to slide#13 of 03142016
         */
        /*alertify.confirm('Are you sure you want to remove this crop with name "' + $(cropObject).val() + '" ?', function (e) {
            if (e) {*/
        removeCropFromAllTables($(cropObject).val());
        /*} else {
            $(cropObject).prop("checked", true);
        }
    });*/
    }

    buildFixedTable();
}

function onResourceSelectedOrRemoved(resourceObject) {
    if ($(resourceObject).prop("checked") == true) {
        addResourcesInAllTables(resourceObject);
    } else if ($(resourceObject).prop("checked") == false) {
        /* @author Jyoti    @date 02-01-2017  PPT NO : 12312106 Slide no : 3*/
        /*alertify.confirm('Are you sure you want to remove this resource with name "' + $(resourceObject).parent().parent().children("td:nth(1)").text().trim() + '" ?', function (e) {*/
        alertify.confirm('Would you like to de-activate "' + $(resourceObject).parent().parent().children("td:nth(1)").text().trim() + ' in the current analysis? ' + $(resourceObject).parent().parent().children("td:nth(1)").text().trim() + ' will not be deleted. It can be reactivated later.', function (e) {
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
    availableLand = Number(returnZeroIfBlank($("#acres_value").val()));
    if (availableLand <= 0) {
        customAlerts("Please enter land value for planting", type_error, time);
        addErrorClassOnObject($("#acres_value"));
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
    $("#cropInformationDetailFirstTable tbody tr").each(function () {
        validationFlag = true;

        if ($(this).children("td:nth(2)").find("input").val() == "") {
            customAlerts('Please enter the estimated yield for "' + $(this).children("td:nth(0)").text() + '"', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(2)").find("input"));
            validationFlag = false;
        } else if (Number($(this).children("td:nth(2)").find("input").val()) == "0.0") {
            customAlerts('Expected Yield for "' + $(this).children("td:nth(0)").text() + '" must be greater than zero', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(2)").find("input"));
            validationFlag = false;
        } else if (Number($(this).children("td:nth(3)").find("input").val()) != "" && Number($(this).children("td:nth(2)").find("input").val()) >= Number($(this).children("td:nth(3)").find("input").val())) {
            customAlerts('Maximum Yield value should be greater than Expected Value for "' + $(this).children("td:nth(0)").text() + '" crop', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(3)").find("input"));
            validationFlag = false;
        } else if (Number($(this).children("td:nth(4)").find("input").val()) != "" && Number($(this).children("td:nth(2)").find("input").val()) <= Number($(this).children("td:nth(4)").find("input").val())) {
            customAlerts('Minimum Yield value should be less than Expected Value for "' + $(this).children("td:nth(0)").text() + '" crop', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(4)").find("input"));
            validationFlag = false;
        } else if (Number($(this).children("td:nth(5)").find("input").val()) == "") {
            customAlerts('Please enter the estimated price for  "' + $(this).children("td:nth(0)").text() + '"', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(5)").find("input"));
            validationFlag = false;
        } else if (removeAllCommasAndDollar($(this).children("td:nth(5)").find("input").val()) == 0.00) {
            customAlerts('Expected price for "' + $(this).children("td:nth(0)").text() + '" must be greater than zero', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(5)").find("input"));
            validationFlag = false;
        } else if (Number($(this).children("td:nth(6)").find("input").val()) != "" && Number(removeAllCommasAndDollar($(this).children("td:nth(5)").find("input").val())) >= Number(removeAllCommasAndDollar($(this).children("td:nth(6)").find("input").val()))) {
            customAlerts('Maximum price value should be greater than Expected price value for "' + $(this).children("td:nth(0)").text() + '" crop', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(6)").find("input"));
            validationFlag = false;
        } else if (Number($(this).children("td:nth(7)").find("input").val()) != "" && Number(removeAllCommasAndDollar($(this).children("td:nth(7)").find("input").val())) >= Number(removeAllCommasAndDollar($(this).children("td:nth(5)").find("input").val()))) {
            customAlerts('Minimum price value should be less than Expected price value for "' + $(this).children("td:nth(0)").text() + '" crop', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(7)").find("input"));
            validationFlag = false;
        } else if (Number($(this).children("td:nth(8)").find("input").val()) == "") {
            customAlerts('Please enter the variable production cost for "' + $(this).children("td:nth(0)").text() + '"', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(8)").find("input"));
            validationFlag = false;
        }
        if (!validationFlag) {
            return false;
        }
    });
    validationFlagGlobal = true;
    $("#cropInformationDetailFirstTable tbody tr").each(function () {
        if ($(this).children("td:nth(8)").find("input").val() == "$0.00") {
            validationFlagGlobal = false;
        }
    });
    if ($("#cropInformationDetailFirstTable tbody tr").length > 0 && validationFlag) {
        return true;
    }
}

/*change br rohit*/
function validateFieldDifference() {
    var fieldDifferenceflag = true;
    var exp = $("#field_difference_exp").val();
    var min = $("#field_difference_min").val();
    var max = $("#field_difference_max").val();
    if ((exp != "" && min != "") || (exp != "" && max != "")) {
        if (Number(min) >= Number(exp)) {
            customAlerts('Minimum Yield value should be less than Expected Value', type_error, time);
            addErrorClassOnObject("#field_difference_min");
            // focusForValidation("field_difference_exp");
            fieldDifferenceflag = false;
            return false;
        } else if (Number(max) <= Number(exp)) {
            customAlerts('Maximum value should be greater than Expected Value', type_error, time);
            addErrorClassOnObject("#field_difference_max");
            // focusForValidation("field_difference_exp");
            fieldDifferenceflag = false;
            return false;
        }
    }
    return fieldDifferenceflag;
}

function confirmValidateCropsInformationDetails() {
    var validationFlag = true;
    var cropName = "";
    $("#cropInformationDetailFirstTable tbody tr").each(function () {
        if ($(this).children("td:nth(8)").find("input").val() == "$0.00") {
            cropName += $(this).children("td:nth(0)").text().trim() + ", ";
            validationFlag = false;
        }
    });
    if (validationFlag == false) {
        changeButtonLabelForAlertifyConfirm("Ok", "Go Back");
        alertify.confirm('The current value for the Variable Production Cost for growing "' + cropName.substring(0, cropName.length - 2) + '" is $0 per acre. If there are no variable costs for growing "' + cropName.substring(0, cropName.length - 2) + '" then press OK and continue. Otherwise go back and enter the Variable Production Costs for "' + cropName.substring(0, cropName.length - 2) + '"', function (e) {
            if (e) {
                callMethodForPageChangeAndProgressBarImage(4, 5);
            }
            resetButtonLabelForAlertifyConfirm();
        });
    }
}

function validatePlanByField() {
    if ($("#Plan_by_Fields_table tbody tr").length < 2) {
        customAlerts("When planning by Fields you must have at least two fields. Create an additional field or switch to planning by Acres", type_error, time);
        return false;
    } else {
        return true;
    }
}

function warningFallowPlanByField() {
    //alert("hello");
    var fallowFieldName = "";
    $("#Plan_by_Fields_table tbody tr").each(function () {
        if ($(this).children("td:nth(4)").find("input").prop("checked") == true) {
            fallowFieldName += $(this).children("td:nth(1)").text().trim() + ", ";
        }
    });
    if (fallowFieldName.length > 0) {
        customAlerts('You marked "' + fallowFieldName.substring(0, fallowFieldName.length - 2) + '" as Fallow so this field is not available for planting. If you would like to plant or consider planting "' + fallowFieldName.substring(0, fallowFieldName.length - 2) + '", uncheck the Fallow box', type_warning, time);
        return true;
    } else {
        return false;
    }
}

function validateCropFieldChoice() {
    if ($("#field_choice_crop_table tbody tr").length < 2) {
        return false;
    }
    var outerValidateCropFieldChoice = true;
    var fieldName = "";
    $("#field_choice_crop_table tbody tr").each(function () {
        var validateCropFieldChoice = false;
        var fallowFlag = false;
        fieldName = $(this).children("td:nth(0)").text();
        $(this).children("td").each(function () {
            if ($(this).find("input").prop("checked") == true) {
                validateCropFieldChoice = true;
            }
            if ($(this).find("input").prop("disabled") == true) {
                fallowFlag = true;
            }
        });
        if (validateCropFieldChoice == false && fallowFlag == false) {
            outerValidateCropFieldChoice = false;
            return outerValidateCropFieldChoice;
        }
    });
    if (outerValidateCropFieldChoice == false) {
        customAlerts('There are no crops selected for field "' + fieldName + '"?', type_error, time);
        return false;
    } else {
        return true;
    }
}

function warningCropFieldChoice() {
    var cropName = "";
    $("#field_choice_crop_table thead tr td:gt(0)").each(function () {
        var index = $(this).index();
        var flag = false;
        $("#field_choice_crop_table tbody tr").each(function () {
            if ($(this).children("td:nth(" + index + ")").find("input").prop("checked") == true) {
                flag = true;
                return false;
            }
        });
        if (flag == false) {
            cropName += $(this).text().trim() + ", ";
        }
    });
    if (cropName.length > 0) {
        cropName = cropName.substring(0, cropName.length - 2);
        customAlerts('You selected "' + cropName + '" as the crops you are considering growing, but you have not specified any fields where you are considering planting "' + cropName + '".<br/>Select at least one field where you are considering planting "' + cropName + '".<br/>If you do not select at least one field for planting "' + cropName + '" will not be considered for planting', type_warning, time);
        $("#alertify-logs").children().attr('style', 'text-align:justify !important');
        return true;
    } else {
        return false;
        // Fallow
    }
}

function validateResources() {
    var resourcesName = "";
    var resourcesFlag = true;
    var resourcesNameWithZeroValue = "";
    var resourcesFlagWithZeroValue = true;
    var totalCapitalAvailable = removeAllCommasAndDollar($.trim("" + $('#total_capital_available').val()));
    if (totalCapitalAvailable == "") {
        customAlerts("Please enter amount for Capital resource", type_error, time);
        addErrorClassOnObject("#total_capital_available");
        return false;
    } else if (totalCapitalAvailable == 0) {
        customAlerts("Please ensure that the amount entered are greater than zero for Capital resource", type_error, time);
        addErrorClassOnObject("#total_capital_available");
        return false;
    } else if (!validateNumberOnly(totalCapitalAvailable) || totalCapitalAvailable < 1) {
        customAlerts("Please ensure that the amount entered are greater than zero for Capital resource", "error", 0);
        focusForValidation("total_capital_available");
        return false;
    } else if (Number(totalCapitalAvailable) > 10000000) {
        customAlerts("The amount entered for Capital resource can not be more then $10,000,000.00", type_error, time);
        addErrorClassOnObject("#total_capital_available");
        return false;
    }
    $("#manage_resource tbody tr").each(function () {
        if ($(this).children("td:nth(1)").text().trim() != "Land" && $(this).children("td:nth(1)").text().trim() != "Capital") {
            if ($(this).children("td:nth(0)").find("input").prop("checked") == true && $(this).children("td:nth(3)").find("input").val() == "") {
                resourcesName += $(this).children("td:nth(1)").text().trim() + ", ";
                resourcesFlag = false;
                addErrorClassOnObject($(this).children("td:nth(3)").find("input"));
                return false;
            } else if ($(this).children("td:nth(0)").find("input").prop("checked") == true && $(this).children("td:nth(3)").find("input").val() == "0") {
                resourcesNameWithZeroValue += $(this).children("td:nth(1)").text().trim() + ", ";
                resourcesFlagWithZeroValue = false;
                addErrorClassOnObject($(this).children("td:nth(3)").find("input"));
                return false;
            }
        }
    });
    if (resourcesFlag == false) {
        customAlerts('Please enter amount for "' + resourcesName.substring(0, resourcesName.length - 2) + '" resources', type_error, time);
        return false;
    } else if (resourcesFlagWithZeroValue == false) {
        customAlerts('Resource amount can not be zero for "' + resourcesNameWithZeroValue.substring(0, resourcesNameWithZeroValue.length - 2) + '" resources', type_error, time);
        return false;
    } else {
        return true;
    }
}

function warningValidateResources() {
    var resourceName = "";
    $("#manage_resource tbody tr").each(function () {
        if ($(this).children("td:nth(1)").text().trim() != "Land" && $(this).children("td:nth(1)").text().trim() != "Capital") {
            if ($(this).children("td:nth(3)").find("input").val() != "" && $(this).children("td:nth(0)").find("input").prop("checked") == false) {
                resourceName += $(this).children("td:nth(1)").text().trim() + ", ";
            }
        }
    });
    /*if (resourceName.length > 0) {
        customAlerts('In Resource screen "' + resourceName.substring(0, resourceName.length - 2) + '" resource is not selected. If you want to use this resource, go back to Resource screen and select the checkbox', type_warning, time);
        return true;
    } else {*/
    return false;
    // }
}

function validateCropResourceUsage() {
    var indexNumber = 2;
    var outerValidation = true;
    $("#crop_resource_usage thead tr td:gt(2)").each(function () {
        var resourceName = $(this).children("span:nth(0)").text().trim();
        indexNumber++;
        var resourceUsed = 0;
        var resourceAmount = 0
        $("#crop_resource_usage tbody tr").each(function () {
            resourceUsed += Number(removeAllCommas($(this).children("td:nth(" + indexNumber + ")").find("input").val()));
        });
        var validation = true;
        $("#manage_resource tbody tr:gt(1)").each(function () {
            if ($(this).children("td:nth(1)").text().trim() == resourceName) {
                if (Number(removeAllCommas($(this).children("td:nth(3)").find("input").val())) < resourceUsed) {
                    resourceAmount = $(this).children("td:nth(3)").find("input").val();
                    addErrorClassOnObject($(this).children("td:nth(3)").find("input"));
                    validation = false;
                }
                return false;
            }
        });
        if (validation == false) {
            customAlerts('"' + resourceName + '" amount for all the crops cannot be greater than total "' + resourceName + '" amount "' + resourceAmount + '". If you would like to use this amount then, go back to the previous screen and increase the total "' + resourceName + '" amount', type_error, time);
            outerValidation = false;
            return outerValidation;
        }
    });
    return outerValidation;
}

function warningCropResourceUsage() {
    var resourceName = "";
    $("#crop_resource_usage thead tr td:gt(2)").each(function () {
        var index = $(this).index();
        var flag = false;
        $("#crop_resource_usage tbody tr").each(function () {
            if ($(this).children("td:nth(" + index + ")").find("input").val().trim() != "") {
                flag = true;
                return false;
            }
        });
        if (flag == false) {
            resourceName += $(this).children("span:nth(0)").text().trim() + ", ";
        }
    });
    if (resourceName.length > 0) {
        resourceName = resourceName.substring(0, resourceName.length - 2);
        /* @author Jyoti    @date 02-01-2017  PPT NO : 12312106 Slide no : 11*/
        alertify.confirm('You included "' + resourceName + '" as a resource for generating cropping strategies but none of the crops you are considering are currently using that resource.<br/><br/>Press OK to continue or Cancel to go back and enter the amount of the resource used by each crop.', function (e) {
            if (e) {
                callMethodForPageChangeAndProgressBarImage(8, 7);
            }
        });
    } else {
        callMethodForPageChangeAndProgressBarImage(8, 7);
    }
}

function validateForwardSales() {
    var totalForwardAcres = 0;
    var validationFlag = true;
    $("#forward_sales_information tbody tr").each(function () {
        if ($(this).children("td:nth(4)").find("input").val() != "") {
            totalForwardAcres += Number(removeAllCommas($(this).children("td:nth(4)").find("input").val()));
        }
        if (($(this).children("td:nth(5)").find("input").prop("checked") == true || $(this).children("td:nth(6)").find("input").prop("checked") == true) && ($(this).children("td:nth(2)").find("input").val().trim() == "" || $(this).children("td:nth(2)").find("input").val().trim() == "$0.00")) {
            customAlerts('Please enter a crop price to evaluate forward sales for "' + $(this).children("td:nth(0)").text().trim() + '"', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(2)").find("input"));
            validationFlag = false;
            return validationFlag;
        }
        else if (($(this).children("td:nth(5)").find("input").prop("checked") == true || $(this).children("td:nth(6)").find("input").prop("checked") == true) && ($(this).children("td:nth(2)").find("input").val().trim() == "" || $(this).children("td:nth(2)").find("input").val().trim() == "0")) {
            customAlerts('Please enter a crop price to evaluate forward sales for "' + $(this).children("td:nth(0)").text().trim() + '"', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(2)").find("input"));
            validationFlag = false;
            return validationFlag;
        }
        else if (($(this).children("td:nth(5)").find("input").prop("checked") == true || $(this).children("td:nth(6)").find("input").prop("checked") == true) && ($(this).children("td:nth(3)").find("input").val().trim() == "" || $(this).children("td:nth(4)").find("input").val().trim() == "" || $(this).children("td:nth(3)").find("input").val().trim() == "0" || $(this).children("td:nth(4)").find("input").val().trim() == "0")) {
            customAlerts('Please enter a crop quantity to evaluate forward sales for "' + $(this).children("td:nth(0)").text().trim() + '"', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(3)").find("input"));
            validationFlag = false;
            return validationFlag;
        }
        else if ($(this).children("td:nth(7)").find("input").val() != "" && Number(removeAllCommasWithPercent($(this).children("td:nth(7)").find("input").val())) > 100) {
            customAlerts('Upper Limit value can not be greater then 100% for "' + $(this).children("td:nth(0)").text().trim() + '" crop', type_error, time);
            addErrorClassOnObject($(this).children("td:nth(7)").find("input"));
            validationFlag = false;
            return validationFlag;
        }
    });
    if (validationFlag == true && totalForwardAcres > Number(removeAllCommas($("#total_land_available").text().trim()))) {
        customAlerts("Total acres in forward sales can not be greater then total available land", type_error, time);
        validationFlag = false;
    }
    return validationFlag;
}

function validateCropLimits() {
    var validationCropLimitFlag = true;
    var totalMinimumAcre = 0;
    var totalMinimumAcrePercentage = 0;
    var totalMaximumAcre = 0;
    var totalMaximumAcrePercentage = 0;
    var minimum_acres = 0;
    var minimum_acresPercentage = 0;
    var maximum_acres = 0;
    var maximum_acresPercentage = 0;
    var cropName = "";
    var totalLand = Number(removeAllCommas($("#total_land_available").text().trim()));

    $("#crop_limits_table_tbody tr").each(function () {
        cropName = $(this).children("td:nth(1)").text();
        minimum_acres = Number(removeAllCommas(returnZeroIfBlank($(this).children("td:nth(2)").find("input").val().trim())));
        minimum_acresPercentage = Number(removeAllCommas(returnZeroIfBlank($(this).children("td:nth(3)").find("input").val().trim())));
        maximum_acres = Number(removeAllCommas(returnZeroIfBlank($(this).children("td:nth(4)").find("input").val().trim())));
        maximum_acresPercentage = Number(removeAllCommas(returnZeroIfBlank($(this).children("td:nth(5)").find("input").val().trim())));

        if (maximum_acres > totalLand || maximum_acresPercentage > 100) {

            customAlerts('The total Maximum crop acreage limit for ' + cropName + ' cannot be greater than the Available land: ' + totalLand + ' acres', type_error, time);
            validationCropLimitFlag = false;
            return validationCropLimitFlag;
        }

        totalMinimumAcre += minimum_acres;
        totalMinimumAcrePercentage += minimum_acresPercentage;
        totalMaximumAcre += maximum_acres;
        totalMaximumAcrePercentage += maximum_acresPercentage;
        if (($(this).children("td:nth(4)").find("input").val().trim() != "" && $(this).children("td:nth(4)").find("input").val().trim() != "0")
            || ($(this).children("td:nth(5)").find("input").val().trim() != "" && $(this).children("td:nth(5)").find("input").val().trim() != "0")) {
            if (Number(removeAllCommas($(this).children("td:nth(4)").find("input").val())) < minimum_acres
                && Number(removeAllCommas($(this).children("td:nth(5)").find("input").val())) < minimum_acresPercentage) {
                /**
                 * @Chnaged - Abhishek
                 * @Date - 25-11-2015
                 * */
                customAlerts('Maximum Acreage Limit should be greater than Minimum Acreage Limit for "' + cropName + '"', type_error, time);
                addErrorClassOnObject($(this).children("td:nth(4)").find("input"));
                addErrorClassOnObject($(this).children("td:nth(5)").find("input"));
                validationCropLimitFlag = false;
                return validationCropLimitFlag;
            }
        }
//		$("#forward_sales_information tbody tr").each(function(){
//			if(cropName == $(this).children("td:nth(0)").text()){
//				if(minimum_acres < Number(removeAllCommas($(this).children("td:nth(4)").find("input").val()))){
//					 customAlerts('Minimum Acres amount should be greater than Acres entered in forward sales for crop "'+cropName+'"', type_error, time);
//					 addErrorClassOnObject($(this).children("td:nth(3)").find("input"));
//					 validationCropLimitFlag = false;
//					 return validationCropLimitFlag;
//				}
//			}
//		});
        if (validationCropLimitFlag == false) {
            return validationCropLimitFlag;
        }
    });
    if (validationCropLimitFlag == false) {
        return validationCropLimitFlag;
    }
    if (totalMinimumAcre > totalLand || totalMinimumAcrePercentage > 100) {
        // customAlerts('Total of the Minimum acres amount must be less than total available land "' + $("#total_land_available").text().trim() + '"', type_error, time);
        customAlerts('The total of all of Minimum crop acreage limits cannot be greater than available land: "' + totalLand + '". ' +
            'Reduce one or more Minimum crop acreage limits or increase Available land', type_error, time);
        validationCropLimitFlag = false;
        return validationCropLimitFlag;
    }
    /**
     * @changed : Jyoti Verma
     * @date : 21-02-2017
     * @type : {Reword according to slide : G024 of 04232017}
     */
        // if (totalMaximumAcre > totalLand || totalMaximumAcrePercentage >= 100) {
        //     // customAlerts('Total of the Maximum acres amount must not be more than total available land "' + $("#total_land_available").text().trim() + '"', type_error, time);
        //     customAlerts('The total of all Maximum crop acreage limits must not be more than the total available land: "' + totalLand + '". ' +
        //         'Reduce one or more Maximum crop acreage limits or increase Available land', type_error, time);
        //     validationCropLimitFlag = false;
        //     return validationCropLimitFlag;
        // }
    var groupMinAcres = 0;
    var groupMinAcresPercentage = 0;
    var groupMaximumAcres = 0;
    var groupMaximumAcresPercentage = 0;
    var groupName = "";
    $("#group_table_tbody tr").each(function () {
        groupName = $(this).children("td:nth(1)").text().trim();
        groupMinAcres = Number(removeAllCommas(returnZeroIfBlank($(this).children("td:nth(2)").find("input").val().trim())));
        groupMinAcresPercentage = Number(removeAllCommas(returnZeroIfBlank($(this).children("td:nth(3)").find("input").val().trim())));
        groupMaximumAcres = Number(removeAllCommas(returnZeroIfBlank($(this).children("td:nth(4)").find("input").val().trim())));
        groupMaximumAcresPercentage = Number(removeAllCommas(returnZeroIfBlank($(this).children("td:nth(5)").find("input").val().trim())));
        if ($(this).children("td:nth(4)").find("input").val() != "" || $(this).children("td:nth(5)").find("input").val() != "") {
            if ((groupMaximumAcres !== 0 && groupMaximumAcres < groupMinAcres) || (groupMaximumAcresPercentage !== 0 && groupMaximumAcresPercentage < groupMinAcresPercentage)) {
                /**
                 * @Chnaged - Abhishek
                 * @Date - 25-11-2015
                 * */
                customAlerts('Maximum Acreage Limit should be greater than Minimum Acreage Limit for "' + groupName + '"', type_error, time);
                addErrorClassOnObject($(this).children("td:nth(4)").find("input"));
                addErrorClassOnObject($(this).children("td:nth(5)").find("input"));
                validationCropLimitFlag = false;
                return validationCropLimitFlag;
            }
        }
        var completeGroupName = "";
        var totalMinimumForGroupByCrop = 0;
        var totalMinimumForGroupByCropPercentage = 0;
        var totalMaximumForGroupByCrop = 0;
        var totalMaximumForGroupByCropPercentage = 0;
        for (var i = 0; i < globalGroupArray[groupName].length; i++) {
            var cropNameOfGroup = globalGroupArray[groupName][i];
            completeGroupName += globalGroupArray[groupName][i] + ", ";
            $("#crop_limits_table_tbody tr").each(function () {
                if ($(this).children("td:nth(1)").text().trim() == cropNameOfGroup) {
                    totalMinimumForGroupByCrop += Number(removeAllCommas(returnZeroIfBlank($(this).children("td:nth(2)").find("input").val())));
                    totalMinimumForGroupByCropPercentage += Number(removeAllCommas(returnZeroIfBlank($(this).children("td:nth(3)").find("input").val())));
                    totalMaximumForGroupByCrop += Number(removeAllCommas(returnZeroIfBlank($(this).children("td:nth(4)").find("input").val())));
                    totalMaximumForGroupByCropPercentage += Number(removeAllCommas(returnZeroIfBlank($(this).children("td:nth(5)").find("input").val())));
                    return false;
                }
            });
        }
        completeGroupName = completeGroupName.substring(0, completeGroupName.length - 2);
        if ((groupMaximumAcres != 0 && groupMaximumAcres < totalMinimumForGroupByCrop)
            || (groupMaximumAcresPercentage != 0 && groupMaximumAcresPercentage < totalMinimumForGroupByCropPercentage)) {
            customAlerts('Maximum Acres amount for group "' + groupName + '" should not be less than total Acres entered for crops "' + completeGroupName + '"', type_error, time);
            validationCropLimitFlag = false;
            return validationCropLimitFlag;
        }
        if ((groupMinAcres < totalMinimumForGroupByCrop && groupMinAcres != 0)
            || (groupMinAcresPercentage != 0 && groupMinAcresPercentage < totalMinimumForGroupByCropPercentage)) {
            customAlerts('Minimum Acres amount for group "' + groupName + '" should not be less than total minimum acres entered for crops "' + completeGroupName + '"', type_error, time);
            validationCropLimitFlag = false;
            return validationCropLimitFlag;
        }
        if ((totalMaximumForGroupByCrop < groupMinAcres && totalMaximumForGroupByCrop != 0)
            || (totalMaximumForGroupByCropPercentage < groupMinAcresPercentage && totalMaximumForGroupByCropPercentage != 0)) {
            customAlerts('The minimum acreage for the Group "' + groupName + '" must be more than the total maximum acreage of each of the crops in the group: "' + completeGroupName
                + '".<br/>To fix this:<br/>Increase the minimum acreage for the Group or<br/>Decrease the maximum acreage for one or more crops in the Group', type_error, time);
            /*customAlerts('Minimum Acres amount for group "'+ groupName + '" should not be less than total maximum acres entered for crops "'+ completeGroupName + '"', type_error, time);*/
            validationCropLimitFlag = false;
            return validationCropLimitFlag;
        }
    });
    return validationCropLimitFlag;
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
    } else if (validationOutput == true && validationFlagGlobal == false) {
        confirmValidateCropsInformationDetails();
    }
}

var warningFallowPlanByFieldFlag = true;

function nextPlanByField() {
    if (validatePlanByField()) {
        if (warningFallowPlanByFieldFlag == true) {
            if (warningFallowPlanByField()) {
                warningFallowPlanByFieldFlag = false;
                return false;
            }
        }
        else {
            warningFallowPlanByFieldFlag = true;
        }
        callMethodForPageChangeAndProgressBarImage(5, 0);

    }
}

var warningForCropFieldChoice = true;

function nextCropFieldChoice() {
    if (validateCropFieldChoice()) {
        if (warningForCropFieldChoice == true) {
            if (warningCropFieldChoice()) {
                warningForCropFieldChoice = false;
            }
        }
        callMethodForPageChangeAndProgressBarImage(6, 0);
    }
}

var warningMessageForOneTimeOnly = true;

function nextResources() {
    if (validateResources()) {
        if (warningMessageForOneTimeOnly == true) {
            if (warningValidateResources()) {
                warningMessageForOneTimeOnly = false;
            }
        }
        callMethodForPageChangeAndProgressBarImage(7, 6);
    }
}

//var warningForCropResourceUsage = false;
function nextCropResourceUsage() {
    if (validateCropResourceUsage()) {
//		if(warningForCropResourceUsage == false){
        warningCropResourceUsage();
//			warningForCropResourceUsage = true;
//		}
//		callMethodForPageChangeAndProgressBarImage(8, 7);
    }
}

function nextForwardSales() {
    if (validateForwardSales()) {
        callMethodForPageChangeAndProgressBarImage(9, 8);
    }
}

function nextFieldDifference() {
    if (validateFieldDifference()) {
        setIconOnCropForFieldDifferenceOnCropResourceUsage();
        callMethodForPageChangeAndProgressBarImage(7, 6);
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
            $("#show_hide_field_variance_button").show();
        } else if (strategy == "acres") {
            $("input[name=plan_by_farm][value=acres]").prop("checked", true);
            $("input[name=plan_by_farm][value=acres]").attr("checked", "checked");
            $("#field-choice").addClass("hidden");
            $("#show_hide_field_variance_button").hide();
        }
        div_hide3();
        nextFarmInformation();
    } else {
        div_hide3();
        customAlerts("Please select a strategy first", type_error, time);
    }
}

var defaultUOMForCrop = "bushels";

function addCropInAllTables(cropName) {
    var flag = true;
    $("#cropInformationDetailFirstTable").find("tbody").find("tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == cropName) {
            flag = false;
        }
    });

    if (!flag) {
        return;
    }

    /**
     * @changed - Abhishek
     * @date - 14-01-2016
     * @desc - added onchange calculateProfitByCrop() for min and max yield and price
     */
    var rowHTMLForCropInformationDetails1 = '<tr class="tblbclgrnd text-center">' +
        '<td class="tblft1">' + cropName + '</td>' +
        '<td class="success uomtext"><select class="crop_selection_UOM" onchange="changeCropUOM(this)"> <option value="bushels">bushels</option><option value="crates">crates</option><option value="hundredweight">hundredweight</option><option value="kilograms">kilograms</option><option value="pounds">pounds</option><option value="tons">tons</option><option value="sacks">sacks</option><option value="bales">bales</option>	</select></td>' +
        '<td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithForOnePoint(this);changeExpectedYieldValue(this); calculateProfitByCrop(this)"></td>' +
        '<td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithForOnePoint(this);changeMaximumYieldValue(this); calculateProfitByCrop(this)"></td>' +
        '<td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithForOnePoint(this);changeMinimumYieldValue(this); calculateProfitByCrop(this)"></td>' +
        '<td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this);calculateProfitByCrop(this);addPopupNegativeValue(this)"></td>' +
        '<td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this); calculateProfitByCrop(this)"></td>' +
        '<td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this); calculateProfitByCrop(this)"></td>' +
        '<td class="success infotext"><input type="text" onkeypress="return isValidNumberValue(event)" placeholder="$0" onchange="addCommaSignWithDollar(this);variableProductionCostChange(this);calculateProfitByCrop(this)"> <br>	<span class="pull-right"><a onclick="showOptionalCropInformationDiv(\'' + cropName + '\')">Details</a></span></td>' +
        '<td class="success infotext" onmouseover="showPotentialProfitCriticalMessagePopup(this);" ><input type="text" disabled="disabled" placeholder="$0.00"><span class="pull-right" style="color: grey;font-size: small;"></span></td></tr>';
    /**
     * @changed - Abhishek
     * @date - 30-12-2015
     */
    /*var rowHTMLForCropInformationDetails2 = '<tr class="tblbclgrnd text-center"><td class="tblft1">' + cropName + '</td><td class="success"><input type="checkbox"></td><td class="success"><input type="checkbox"></td><td class="success"><input type="checkbox"></td></tr>';*/
    var rowHTMLForCropInformationDetails2 = '<tr class="tblbclgrnd text-center">' +
        '<td class="tblft1">' + cropName + '</td>' +
        '<td class="success"><input type="checkbox"></td>' +
        '<td class="success"><input type="checkbox"></td>' +
        '<td class="success hidden"><input type="checkbox"></td></tr>';
    var rowHTMLForCropInformationDetails3 = '<tr class="tblbclgrnd text-center">' +
        '<td class="tblft1">' + cropName + '</td>' +
        '<td class="success infotext"><input type="text" name="Crop" class="datepicker"></td>' +
        '<td class="success infotext"><input type="text" name="Crop" class="datepicker"></td>' +
        '<td class="success infotext"><input type="text" name="Crop" class="datepicker"></td>' +
        '<td class="success infotext"><input type="text" name="Crop"></td></tr>';

    $("#cropInformationDetailFirstTable").find("tbody").append(rowHTMLForCropInformationDetails1);
    $("#cropInformationDetailSecondTable").find("tbody").append(rowHTMLForCropInformationDetails2);
    $("#cropInformationDetailThirdTable").find("tbody").append(rowHTMLForCropInformationDetails3);

    /**
     * @Date - 10-09-2016
     * @desc - for adding dynamic row to Additional Crop Income information
     */
    $.tmpl('additional-crop-income-tbody-tmpl', {cropName: cropName}).appendTo('#crop-info-additional-income-tbody');

    var optionalCropInfoHeadsArr = [
        'Seed',
        'Fertilizer and soil amendments',
        'Herbicide',
        'Insecticide',
        'Fungicide',
        'Defoliant',
        'Other chemicals',
        'Crop insurance',
        'Crop consulting',
        'Other professional services',
        'Machinery operating costs',
        'Cost of operator and hired labor',
        'Custom hire and rental',
        'Irrigation',
        'Drying',
        'Storage',
        'Marketing',
        'Rent',
        'Operating interest (e.g. 6% x ½ year)',
        'Others'
    ];

    $.tmpl('optional-crop-info-table-tmpl', {
        cropName: cropName,
        optionalCropInfoHeadsArr: optionalCropInfoHeadsArr
    }).appendTo('#optional_crop_dynamic_div');


    // $("#optional_crop_dynamic_div").append(optionalCropInformationDetailHTML);
    $('div[name="optionalCropInformationDetail_' + cropName + '"]').hide();

    var rowHTMLForCropResourceUsage = '<tr class="tblgrn text-center"><td class="tblft1">' + cropName + '</td><td class="success infotext tittle-uppercase UOMCropResourceUsage">' + defaultUOMForCrop + '</td><td class="success infotext">$0.00 <img src="images/data.png"></td>';
    for (var i = 0, j = $("#crop_resource_usage thead tr td").length - 3; i < j; i++) {
        rowHTMLForCropResourceUsage += '<td class="success infotext"><input type="text" onchange="addCommaSignWithOutDollar(this);cropResourceUsageValueChange(this)" onkeypress="return isValidNumberValue(event)"></td>';
    }
    rowHTMLForCropResourceUsage += "</tr>";
    $("#crop_resource_usage tbody").append(rowHTMLForCropResourceUsage);
    /**
     * @changed - Abhishek
     * @date - 30-12-2015
     * @updated - 23-01-2016
     * @desc - changed according to slide# 1 of ppt 01042015
     */
//	var rowHTMLForCropForwardSales = '<tr class="tblbclgrnd text-center"><td class="tblft1">'+cropName+'</td><td class="success infotext tittle-uppercase">'+defaultUOMForCrop+'</td><td class="success croplimit"><input type="text"></td><td class="success croplimit"><input type="text"></td><td class="success croplimit"><input type="text"></td><td class="success croplimit"><input type="checkbox"></td><td class="success croplimit"><input type="checkbox"></td><td class="success croplimit"><input type="text"></td></tr>';
    var rowHTMLForCropForwardSales = '<tr class="tblbclgrnd text-center">' +
        '<td class="tblft1">' + cropName + '</td>' +
        '<td class="success infotext tittle-uppercase">' + defaultUOMForCrop + '</td>' +
        '<td class="success croplimit"><input type="text" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this);addForwardNegativePriceRedBox(this)"></td>' +
        '<td class="success croplimit"><input type="text" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithOutDollarDot(this);acerageCalForwardSale(this)"></td>' +
        '<td class="success croplimit"><input type="text" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithOutDollarDot(this);quantityCalForwardSale(this)"></td>' +
        '<td class="success croplimit"><input type="checkbox" onchange="proposedAndFirmSelection(this)"></td>' +
        '<td class="success croplimit"><input type="checkbox" onchange="proposedAndFirmSelection(this)"></td>' +
        '<td class="success croplimit hidden"><input type="text" onkeypress="return isValidNumberValueForWithOutDot(event)" onchange="addPercentSign(this)"></td></tr>';
    $("#forward_sales_information tbody").append(rowHTMLForCropForwardSales);

    var rowHTMLForCropLimit = "";
    if ($("#group_table_tbody tr").length == 0) {
        rowHTMLForCropLimit = '<tr class="tblbclgrnd text-center">' +
            '<td class="tblft1 hiddenTD"></td><td class="tblft1">' + cropName + '</td>' +
            '<td class="success croplimit"><input type="text" class="minCropAcreage" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithOutDollarDot(this); calculatePercentageOfMinAcreage(this);"></td>' +
            '<td class="success croplimit"><input type="text" class="minCropAcreagePercentage" onkeypress="return isValidNumberValue(event)" onchange="calculatePercentageOfMinAcreage(this);"></td>' +
            '<td class="success croplimit"><input type="text" class="maxCropAcreage" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithOutDollarDot(this); calculatePercentageOfMaxAcreage(this);"></td>' +
            '<td class="success croplimit"><input type="text" class="maxCropAcreagePercentage" onkeypress="return isValidNumberValue(event)" onchange="calculatePercentageOfMaxAcreage(this);"></td>' +
            '</tr>';
    } else {
        rowHTMLForCropLimit = '<tr class="tblbclgrnd text-center">' +
            '<td class="tblft1"></td>' +
            '<td class="tblft1">' + cropName + '</td>' +
            '<td class="success croplimit"><input type="text" class="minCropAcreage" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithOutDollarDot(this); calculatePercentageOfMinAcreage(this);"></td>' +
            '<td class="success croplimit"><input type="text" class="minCropAcreagePercentage" onkeypress="return isValidNumberValue(event)" onchange="calculatePercentageOfMinAcreage(this);"></td>' +
            '<td class="success croplimit"><input type="text" class="maxCropAcreage" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithOutDollarDot(this); calculatePercentageOfMaxAcreage(this);">' +
            '<a id="popoverPercentageHelp" class="help_Infromation_PopUp" href="javascript:;" style="display: none"><img src="/images/i-img.png"></a></td>' +
            '<td class="success croplimit"><input type="text" class="maxCropAcreagePercentage" onkeypress="return isValidNumberValue(event)" onchange="calculatePercentageOfMaxAcreage(this);">' +
            '<a id="popoverPercentageHelp" class="help_Infromation_PopUp" href="javascript:;" style="display: none"><img src="/images/i-img.png"></a></td>' +
            '</tr>';
    }
    $("#crop_limits_table_tbody").append(rowHTMLForCropLimit);
    maxCropLimitPercentageValidate();

    var optionRowHTMLForGroupCropSelection = '<option value="' + cropName + '">' + cropName + '</option>';
    $("#gropofcrop").append(optionRowHTMLForGroupCropSelection);
    deSelectAllCropsInGroupOptionAndRebuild();

    $("#Plan_by_Fields_table tbody tr").each(function () {
        $(this).children("td:nth(3)").find("select").append('<option value="' + cropName + '">' + cropName + '</option>');
    });
    $("#field_choice_crop_table thead tr").append('<td class="text-center add-fieldi">' + cropName + '</td>');
    $("#field_choice_crop_table tbody tr").each(function () {
        var fieldName = $(this).children("td:nth(0)").text().trim();
        var fallowFlag = false;
        $("#Plan_by_Fields_table tbody tr").each(function () {
            if ($(this).children("td:nth(1)").text().trim() == fieldName && $(this).children("td:nth(4)").find("input").prop("checked")) {
                fallowFlag = true;
                return false;
            }
        })
        if (fallowFlag == true) {
            $(this).append('<td class="success"><label class="input-label">' +
                '<input type="checkbox" disabled="" value="true" class="cropFieldChoiceCheckbox countChoiceCheckboxChenge" onchange="cropFieldChoiceCheckboxChenge(this)">' +
                /*cropName + */'</label></td>');
        } else {
            $(this).append('<td class="success"><label class="input-label">' +
                '<input type="checkbox" class="cropFieldChoiceCheckbox countChoiceCheckboxChenge" onchange="cropFieldChoiceCheckboxChenge(this)">' +
                /*cropName + */'</label></td>');
        }
    });
}

function removeCropFromAllTables(cropName) {
    $("#cropInformationDetailFirstTable tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == cropName) {
            $(this).remove();
            return false;
        }
    });
    $("#cropInformationDetailSecondTable tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == cropName) {
            $(this).remove();
            return false;
        }
    });
    $("#cropInformationDetailThirdTable tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == cropName) {
            $(this).remove();
            return false;
        }
    });
    $("#crop-info-additional-income-tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == cropName) {
            $(this).remove();
            return false;
        }
    });
    $("#crop_resource_usage tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == cropName) {
            $(this).remove();
            return false;
        }
    });
    $("#forward_sales_information tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == cropName) {
            $(this).remove();
            return false;
        }
    });
    $("#crop_limits_table_tbody tr").each(function () {
        if ($(this).children("td:nth(1)").text().trim() == cropName) {
            $(this).remove();
            return false;
        }
    });

    $('div[name="optionalCropInformationDetail_' + cropName + '"]').remove();

    $("#gropofcrop option").each(function () {
        if ($(this).val() == cropName) {
            $(this).remove();
            return false;
        }
    });

    $("#crop_contract_table_tbody tr").each(function () {
        if ($(this).children("td:nth(1)").text().trim() == cropName + " (" + contractIdentifier + ")") {
            $(this).remove();
            return false;
        }
    });

    deSelectAllCropsInGroupOptionAndRebuild();
    for (var key in globalGroupArray) {
        for (var i = 0; i < globalGroupArray[key].length; i++) {
            if (globalGroupArray[key][i] == cropName) {
                globalGroupArray[key].splice(i, 1);
                // delete globalGroupArray[key][i];
            }
        }
        if (globalGroupArray[key].length == 0) {
            delete globalGroupArray[key];
            deleteGroupByNameWhenCropIsremoved(key);
        }
    }

// if(strategy == "fields"){
    $("#Plan_by_Fields_table tbody tr").each(function () {
        $(this).children("td:nth(3)").find("select").find("option").each(function () {
            if ($(this).val().trim() == cropName) {
                $(this).remove();
                return false;
            }
        });
    });
    var index = 0;
    var tempIndex = 0;
    $("#field_choice_crop_table thead tr td").each(function () {
        if ($(this).text().trim() == cropName) {
            index = tempIndex;
            $(this).remove();
        }
        tempIndex++;
    });
    $("#field_choice_crop_table tbody tr").each(function () {
        $(this).children("td:nth(" + index + ")").remove();
    });
//	}
    if ($(".crops:checked").length == 0) {
        enableDisableLeftMenu(2);
    }
}

function deleteGroupByNameWhenCropIsremoved(groupName) {
    $("#group_table_tbody tr").each(function () {
        if ($(this).children("td:nth(1)").text().trim() == groupName) {
            $(this).remove();
        }
    });
    if ($("#group_table_tbody tr").length == 0) {
        hideModifyColumnInCropLimitTable();
    }
}

function addNewField() {
    var selectHTMLForOptions = '';
    $("input:checkbox[class=crops]:checked").each(function () {
        selectHTMLForOptions += '<option value="' + $(this).val() + '">' + $(this).val() + '</option>';
    });
    /*change by rohit*/
    var fieldName = $('#pop-up-field-name').val().trim();
    var fieldSize = $('#pop-up-field-size').val().trim();
    if (fieldName == "") {
        customAlerts("Please enter field name", type_error, time);
        addErrorClassOnObject('#pop-up-field-name');
        return false;
    }
    else if (fieldSize == "") {
        customAlerts("Please enter field size", type_error, time);
        addErrorClassOnObject('#pop-up-field-size');
        return false;
    }
    else if (!validateNumberOnly(fieldSize) || fieldSize < 1) {
        customAlerts("Please ensure that the value entered are greater than zero for Field size", type_error, time);
        addErrorClassOnObject('#pop-up-field-size');
        return false;
    }
    else {
        // alertify.confirm('Click OK to add a new field named "' + fieldName + '".', function (e) {
        //     if (e) {

        var validationFlag_Field = true;
        $("#Plan_by_Fields_table tbody tr").each(function () {
            if ($(this).children("td:nth(1)").text().trim() == fieldName) {
                customAlerts('"' + fieldName + '" field name is already exist', type_error, time);
                addErrorClassOnObject('#pop-up-field-name');
                validationFlag_Field = false;
                return false;
            }
        });
        if (validationFlag_Field) {
            var rowHTMLForPlanByField = '<tr class="success tblgrn text-center column-left"><td><input type="checkbox" class="fields"></td><td>' + $("#pop-up-field-name").val() + '</td><td>' + $("#pop-up-field-size").val() + '</td> <td><select onchange="lastCropSelected(this)"><option value="No Crop">No Crop</option>' + selectHTMLForOptions + '</select></td><td><input type="checkbox" value="true" onchange="fallowEnabledOrDisabled(this)"></td><td><input type="checkbox" value="true"></td><td><input type="checkbox" name="field-irrigate__1" value="true"></td></tr>';
            var totalLandByField = 0;
//	$("#Plan_by_Fields_table tbody").children("tr:nth("+($("#Plan_by_Fields_table tbody tr").length-1)+")").remove();
            $("#Plan_by_Fields_table tbody").append(rowHTMLForPlanByField);
            $("#Plan_by_Fields_table tbody tr").each(function () {
                totalLandByField += Number(removeAllCommas($(this).children("td:nth(2)").text()));
            });
//	var rowHtmlForLastRow = '<tr id="total-field-last-row" class="tblft text-center"><td class="tblft1">Total acres </td><td style="text-align: left" colspan="6" id="total-acres-value">'+totalLandByField+'</td></tr>';
//	$("#Plan_by_Fields_table tbody").append(rowHtmlForLastRow);

            var rowHTMLForCropFieldChoice = '<tr class="tblgrn text-center"><td class="tblft1">' + $("#pop-up-field-name").val() + '</td>';
            /*var count = 0;
            var cropsArr = [];
            $("#field_choice_crop_thead tr td").each(function(){
                if(count != 0){
                    cropsArr.push($(this).html());
                }
                count++;
            });

            count = 0;*/
            $("input:checkbox[class=crops]:checked").each(function () {
                rowHTMLForCropFieldChoice += '<td class="success"><label class="input-label">' +
                    '<input type="checkbox" class="cropFieldChoiceCheckbox countChoiceCheckboxChenge" onchange="cropFieldChoiceCheckboxChenge(this)">' +
                    /*cropsArr[count] + */'</label></td>';
                // count++;
            });
            rowHTMLForCropFieldChoice += "</tr>";
            $("#field_choice_crop_table tbody").append(rowHTMLForCropFieldChoice);
            $("#field_select_drop_down").append('<option value="' + $("#pop-up-field-name").val() + '">' + $("#pop-up-field-name").val() + '</option>');
            var alertMessage = "";
            if (totalLandByField > 10000) {
                alertMessage += "But the amount of land entered for \"" + fieldName + "\" field exceeds 10,000.00 acres. ";
            }
            /*if (alertMessage != "") {
                customAlerts('"' + fieldName + '" field added successfully. ' + alertMessage, type_warning, time);
            } else {
                customAlerts('"' + fieldName + '" field added successfully', type_success, time);
            }*/
            totalLandByField = getValueWithComma(totalLandByField);
            $("#total-acres-value").text(totalLandByField);
            $("#total_land_available").text(totalLandByField);
            $("#Plan_by_Fields_table").show();
        }
        // }
        $("#pop-up-field-name").val('');
        $("#pop-up-field-size").val('');

        buildFixedTable();
        $fixedTables.trigger('rowAddOrRemove');
        div_hide4();
        // });
    }
}

function fallowEnabledOrDisabled(obj) {
    var fieldName = $(obj).parent().parent().children("td:nth(1)").text().trim();
    if ($(obj).prop("checked") == true) {
        $("#field_choice_crop_table tbody tr").each(function () {
            if ($(this).children("td:nth(0)").text().trim() == fieldName) {
                $(this).find("input[class='cropFieldChoiceCheckbox']").prop("checked", false);
                $(this).find("input[class='cropFieldChoiceCheckbox']").prop("disabled", true);
                return false;
            }
        });
        if ($("#field_select_drop_down").val() == fieldName) {
            $("#field_select_drop_down").val("0");
            $("#field_select_drop_down").change();
        }
    } else if ($(obj).prop("checked") == false) {
        $("#field_choice_crop_table tbody tr").each(function () {
            if ($(this).children("td:nth(0)").text().trim() == fieldName) {
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
    $("#field_choice_crop_table thead tr td").each(function () {
        if ($(this).text().trim() == lastCropName) {
            return false;
        }
        indexNumber++;
    });
    $("#field_choice_crop_table tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == fieldName) {
            $(this).find("span").each(function () {
                $(this).parent().html($(this).html());
            });
            $(this).children("td:nth(" + indexNumber + ")").html("<span class='last_crop' title='This crop has been selected as last crop.'>" + $(this).children("td:nth(" + indexNumber + ")").html() + "</span>");
            return false;
        }
    });
}

var fieldNameForModify = "";

function modifyExistingField() {
    fieldNameForModify = "";
    if ($("#Plan_by_Fields_table tbody tr").length == 0) {
        customAlerts("You don't have any field to modify, Please add a field first to modify", type_error, time);
    } else if ($("#Plan_by_Fields_table tbody tr").length > 0 && $(".fields:checked").length == 1) {
        fieldNameForModify = $(".fields:checked").parent().parent().children("td:nth(1)").text().trim();
        $("#pop-up-field-name").val(fieldNameForModify);
        $("#pop-up-field-size").val($(".fields:checked").parent().parent().children("td:nth(2)").text().trim());
        div_show4();
    } else if ($("#Plan_by_Fields_table tbody tr").length > 0 && $(".fields:checked").length == 0) {
        customAlerts("Please select atleast one field to modify", type_error, time);
    } else if ($("#Plan_by_Fields_table tbody tr").length > 0 && $(".fields:checked").length > 1) {
        customAlerts("You can modify only one field at a time", type_error, time);
    }
}

function modifyField() {

    var fieldName = $('#pop-up-field-name').val().trim();
    var fieldSize = $('#pop-up-field-size').val().trim();
    if (fieldName == "") {
        customAlerts("Please enter field name", type_error, time);
        addErrorClassOnObject('#pop-up-field-name');
        return false;
    }
    else if (fieldSize == "") {
        customAlerts("Please enter field size", type_error, time);
        addErrorClassOnObject('#pop-up-field-size');
        return false;
    }
    else if (!validateNumberOnly(fieldSize) || fieldSize < 1) {
        customAlerts("Please ensure that the value entered are greater than zero for Field size", type_error, time);
        addErrorClassOnObject('#pop-up-field-size');
        return false;
    }
    else {
        alertify.confirm('Are you sure you want to update field named "' + fieldName + '"?', function (e) {
            if (e) {

                var validationFlag_Field = true;
                $("#Plan_by_Fields_table tbody tr").each(function () {
                    if ($(this).children("td:nth(1)").text().trim() == fieldName) {
                        if ($(this).children("td:nth(1)").text().trim() == fieldNameForModify) {
                            validationFlag_Field = true;
                            return false;
                        }
                        else {
                            customAlerts('"' + fieldName + '" field name is already exist', type_error, time);
                            addErrorClassOnObject('#pop-up-field-name');
                            validationFlag_Field = false;
                            return false;

                        }
                    }
                });
                if (validationFlag_Field) {
                    if ($(".fields:checked").parent().parent().children("td:nth(1)").text().trim() == fieldNameForModify) {
                        $(".fields:checked").parent().parent().children("td:nth(1)").text($("#pop-up-field-name").val());
                        $(".fields:checked").parent().parent().children("td:nth(2)").text($("#pop-up-field-size").val());

                        var totalLandByField = 0;
                        $("#Plan_by_Fields_table tbody tr").each(function () {
                            totalLandByField += Number(removeAllCommas($(this).children("td:nth(2)").text()));
                        });
                        var alertMessage = "";
                        if (totalLandByField > 10000) {
                            alertMessage += "But the amount of land entered for \"" + fieldName + "\" field exceeds 10,000.00 acres. ";
                        }
                        if (alertMessage != "") {
                            customAlerts('"' + fieldName + '" field updated successfully. '
                                + alertMessage, type_warning, time);
                        } else {
                            customAlerts('"' + fieldName + '" field updated successfully', type_success, time);
                        }
                        totalLandByField = getValueWithComma(totalLandByField);
                        $("#total-acres-value").text(totalLandByField);
                        $("#total_land_available").text(totalLandByField);
                        $("#field_choice_crop_table tbody tr").each(function () {
                            if ($(this).children("td:nth(0)").text().trim() == fieldNameForModify) {
                                $(this).children("td:nth(0)").text($("#pop-up-field-name").val());
                                return false;
                            }
                        });
                        $("#field_select_drop_down option").each(function () {
                            if ($(this).val() == fieldNameForModify) {
                                $(this).val($("#pop-up-field-name").val());
                                $(this).text($("#pop-up-field-name").val());
                                return false;
                            }
                        });
                    }
                }
            }
            div_hide4();
            $('#pop-up-field-name').val("");
            $('#pop-up-field-size').val("");
            $(".fields:checked").prop("checked", false);
        });
    }
}

function removeField() {

    if ($("#Plan_by_Fields_table tbody tr").length > 0 && $(".fields:checked").length == 0) {
        customAlerts("Please select atleast one field to remove", type_error, time);
    } else if ($("#Plan_by_Fields_table tbody tr").length == 0) {
        customAlerts("There are no fields, Please add fields first", type_error, time);
    } else if ($("#Plan_by_Fields_table tbody tr").length > 0 && $(".fields:checked").length > 0) {
        var fieldNameList = "";
        $("#Plan_by_Fields_table tbody tr").each(function () {
            if ($(this).children("td:nth(0)").find("input").prop("checked")) {
                fieldNameList += $(this).children("td:nth(1)").text().trim() + ", ";
            }
        });

        // alertify.confirm('Are you sure you want to remove this field "' + fieldNameList.substring(0, fieldNameList.length - 2) + '"?', function (e) {
        /*---------- Update according to PPT 02202017 -----------*/
        alertify.confirm('Are you sure you want to remove this field ?', function (e) {
            if (e) {
                var totalLandByField = 0;
                $("#Plan_by_Fields_table tbody tr").each(function () {
                    if ($(this).children("td:nth(0)").find("input").prop("checked")) {
                        var fieldName = $(this).children("td:nth(1)").text().trim();
                        $("#field_choice_crop_table tbody tr").each(function () {
                            if ($(this).children("td:nth(0)").text().trim() == fieldName) {
                                $(this).remove();
                            }
                        });
                        $(this).remove();
                    }
                });
                if ($("#Plan_by_Fields_table tbody tr").length < 1) {
                    $("#Plan_by_Fields_table").hide();
                    enableDisableLeftMenu(3);
                } else {
                    $("#Plan_by_Fields_table tbody tr").each(function () {
                        totalLandByField += Number(removeAllCommas($(this).children("td:nth(2)").text()));
                    });
                    totalLandByField = getValueWithComma(totalLandByField);
                    $("#total-acres-value").text(totalLandByField);
                    $("#total_land_available").text(totalLandByField);
                }
                $("#field_select_drop_down option").each(function () {
                    var fieldArr = fieldNameList.split(", ");
                    for (var key in fieldArr) {
                        var fieldName = fieldArr[key];
                        if (fieldName != "" && $(this).val() == fieldName) {
                            var flag = false;
                            if ($(this).is(':selected')) {
                                flag = true;
                            }
                            $(this).remove();
                            if (flag) {
                                $("#field_select_drop_down").val("0");
                                $("#field_select_drop_down").change();
                            }
                            return false;
                        }
                    }
                });

            } else {
                $("#Plan_by_Fields_table tbody tr").each(function () {
                    if ($(this).children("td:nth(0)").find("input").prop("checked")) {
                        $(this).children("td:nth(0)").find("input").prop("checked", false);
                    }
                });
            }
            $fixedTables.trigger('rowAddOrRemove');
        });


    }
}

function allCheckboxNone() {
    $(".cropFieldChoiceCheckbox").prop("checked", false);
}

function allCheckboxSelect() {
    $(".cropFieldChoiceCheckbox:not(:disabled)").prop("checked", true);

}

function addNewResource() {
    /*change by rohit*/
    var resourse_name = $("#resourse_name").val();
    if ($("#resourse_name").val() == "") {
        customAlerts("Please enter resource name", type_error, time);
        addErrorClassOnObject("#resourse_name");
        return false;
    }
    else if ($("#resourse_unit_name").val() == "") {
        // customAlerts("Please select unit of measure of particular resource", type_error, time);
        customAlerts("Please select the appropriate unit of measure for resource: " + resourse_name, type_error, time);
        addErrorClassOnObject("#resourse_unit_name");
        return false;
    }
    else {
        /**
         * @changed - Abhishek
         * @date - 30-12-2015
         */
        /*alertify.confirm('Are you sure you want to add a new resource named "' + resourse_name + '"?', function (e) {*/
        alertify.confirm('Add resource "' + resourse_name + '"?', function (e) {
            if (e) {

                validationResourseFlag = true;
                $("#manage_resource tbody tr").each(function () {
                    if ($(this).children("td:nth(1)").text().trim() == $("#resourse_name").val().trim()) {
                        customAlerts('"' + $("#resourse_name").val() + '" resource name is already exist', type_error, time);
                        addErrorClassOnObject('#resourse_name');
                        validationResourseFlag = false;
                        return false;
                    }
                });
                if (validationResourseFlag) {
                    var rowHTMLForAddResources = '<tr class="tblgrn text-center"><td class="tblft1"><input type="checkbox" name="resourceSelection" onchange="onResourceSelectedOrRemoved(this)"></td><td class="success croplimi">' + $("#resourse_name").val() + '</td><td class="success croplimit">' + $("#resourse_unit_name").val() + '</td><td class="success croplimit"><input type="text" onchange="addCommaSignWithOutDollarDot(this)" onkeypress="return isValidNumberValueForWithOutDot(event)"></td></tr>';
                    $("#manage_resource tbody").append(rowHTMLForAddResources);

                    customAlerts('"' + resourse_name + '"  resource added to the strategy', type_success, time);
                }
            }
            div_hide6();

        });
    }
}

function removeResource() {
    if ($("input[name='resourceSelection']").length == 0) {
        customAlerts("Land and Capital are required resources and cannot be removed", type_error, time);
    } else if ($("input[name='resourceSelection']").length > 0 && $("input[name='resourceSelection']:checked").length == 0) {
        customAlerts("Please select a resource to remove", type_error, time);
    } else if ($("input[name='resourceSelection']:checked").length > 0) {
        var ResourseNameList = "";
        $("#manage_resource tbody tr").each(function () {
            if ($(this).children("td:nth(0)").find("input").prop("disabled") == false && $(this).children("td:nth(0)").find("input").prop("checked")) {

                ResourseNameList += $(this).children("td:nth(1)").text().trim() + ", ";
            }
        });
        /*alertify.confirm('Are you sure you want to remove resource named "' + ResourseNameList.substring(0, ResourseNameList.length - 2) + '"?', function (e) {*/
        alertify.confirm('Remove resource "' + ResourseNameList.substring(0, ResourseNameList.length - 2) + '"?', function (e) {
            if (e) {
                $("#manage_resource tbody tr").each(function () {
                    if ($(this).children("td:nth(0)").find("input").prop("disabled") == false && $(this).children("td:nth(0)").find("input").prop("checked")) {
                        removeResourceFromAllTables($(this).children("td:nth(0)").find("input"));
                        $(this).remove();
                    }
                });
                customAlerts('"' + ResourseNameList.substring(0, ResourseNameList.length - 2) + '"' + " Resourse removed  Successfully", type_success, time);
            }

        });
    }

}

function addResourcesInAllTables(resourceObject) {
    var resourceName = $(resourceObject).parent().parent().children("td:nth(1)").text().trim();
    var resourceUOM = $(resourceObject).parent().parent().children("td:nth(2)").text().trim();
    var flag = true;
    $("#crop_resource_usage thead tr").each(function () {
        console.log($(this).children().eq(0).text());
        $(this).find('td').each(function () {
            if ($.trim($(this).find('span[class="tittle-uppercase"]').text()) == resourceName) {
                flag = false;
            }
        });

    });

    if (flag) {
        var theadHTML = '<td class="text-center"><span class="tittle-uppercase">' + resourceName + '</span><br><span class="resub">(' + resourceUOM + ')</span></td>';
        $("#crop_resource_usage thead tr").append(theadHTML);

        var tbodyTdHTML = '<td class="success infotext"><input type="text" onchange="addCommaSignWithOutDollar(this);cropResourceUsageValueChange(this)" onkeypress="return isValidNumberValue(event)"></td>';
        $("#crop_resource_usage tbody tr").each(function () {
            $(this).append(tbodyTdHTML);
        });
        var rowHTMLForFieldDifference = '<tr class="tblgrn text-center"><td class="tblft1 tittle-uppercase">' + resourceName + '</td><td class="success infotext"></td><td class="success infotext"><input type="text" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithOutDollar(this)"></td></tr>';
        $("#crop_resources_usages_difference_tbody").append(rowHTMLForFieldDifference);
    }


}

function removeResourceFromAllTables(resourceObject) {
    var resourceName = $(resourceObject).parent().parent().children("td:nth(1)").text().trim();
    var indexNumber = 0;
    $("#crop_resource_usage thead tr td").each(function () {
        if ($.trim($(this).children().eq(0).text()) == resourceName) {
            $(this).remove();
            return false;
        }
        indexNumber++;
    });
    $("#crop_resource_usage tbody tr").each(function () {
        $(this).children("td:nth(" + indexNumber + ")").remove();
    });
    $("#crop_resources_usages_difference_tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == resourceName) {
            $(this).remove();
        }
    });
}

function variableProductionCostChange(obj) {
    var cropName = $(obj).parent().siblings("td:nth(0)").text().trim();
    var variableProductionCost = $(obj).val();
    $("#crop_resource_usage tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == cropName) {
            $(this).children("td:nth(2)").html(returnZeroWithDollarIfBlank(variableProductionCost) + '<img src="images/data.png">');
            return false;
        }
    });
    if ($("#crop_select_drop_down").val() == $(obj).parent().siblings("td:nth(0)").text().trim()) {
        $("#resources_usages_production_resource_default").text(returnZeroWithDollarIfBlank(variableProductionCost));
    }
}

function addCommaSignForAcres(id) {
    if ($(id).val() == "") {

    } else {
        var value = $.trim("" + $(id).val().replace('$', '').replace(/,/g, ''));
        var valueWithPoint = Number(value).toFixed(2);
        if (valueWithPoint > 10000) {
            alertify.confirm("The amount of land entered exceeds 10,000 acres? " +
                "<br> Click OK to continue or Cancel to change the amount of Available Land", function (e) {
                if (e) {
                    nextPlanByAcres();
                }
            });
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

function addNewCrop() {
    var cropName = $("#crop_name").val().trim();
    if (cropName == "") {
        customAlerts("Please enter name of Crop", type_error, time);
        addErrorClassOnObject("#crop_name");
        return false;
    }
    if ($("input[class='crops'][value='" + cropName + "']").length > 0 || $("input[class='crops'][value='" + cropName.toUpperCase() + "']").length > 0 || $("input[class='crops'][value='" + cropName.toLowerCase() + "']").length > 0) {
        customAlerts('"' + cropName + '" crop name is already exist', type_error, time);
        addErrorClassOnObject("#crop_name");
        return false;
    }
    if ($("input[name='crop_type']:checked").length == 0) {
        customAlerts("Please select crop type", type_error, time);
        return false;
    }
    alertify.confirm('Are you sure you want to add a new crop named "' + cropName + '"?', function (e) {
        if (e) {
            if ($("input[name='crop_type']:checked").val() == "Field Crops") {
                var rowHTMLForCrop = '<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input type="checkbox" value="' + cropName + '" class="crops"  onchange="onCropSelectedOrRemoved(this)" name="field_crop[]"> &nbsp;&nbsp;<span>' + cropName + '</span></lable></li>';
                $("#crop_normal").append(rowHTMLForCrop);
            } else {
                var rowHTMLForCrop = '<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input type="checkbox" value="' + cropName + '" class="crops"  onchange="onCropSelectedOrRemoved(this)" name="vegitable_crop[]"> &nbsp;&nbsp;<span>' + cropName + '</span></lable></li>';
                $("#crop_vegitable").append(rowHTMLForCrop);
            }
            customAlerts('"' + cropName + '" crop is added successfully', type_success, time);
        }
        div_hide5();
        $('#crop_name').val("");
        $('input[name="crop_type"]').prop("checked", false);
    });
}

function removeCrops() {
    if ($(".crops:checked").length < 1) {
        customAlerts("No crop selected to remove, Please select atleast one Crop", type_error, time);
        return false;
    }
    var cropsName = "";
    $(".crops:checked").each(function () {
        cropsName += $(this).val() + ", ";
    });
    cropsName = cropsName.substring(0, cropsName.length - 2);
    alertify.confirm('Are you sure you want to remove "' + cropsName + '" crops?', function (e) {
        if (e) {
            $(".crops:checked").each(function () {
                removeCropFromAllTables($(this).val());
                $(this).parent().parent().remove();
            });
            customAlerts('"' + cropsName + '" Crops removed successfully', type_success, time);
        }
    });
    if ($(".crops:checked").length == 0) {
        enableDisableLeftMenu(2);
    }
}

/**
 * @changed - Abhishek
 * @date - 19-05-2016
 * @desc - for implementing select all functionality
 */
function selectAllContacts() {
    showLoadingImage();
    var checked = $(".crops:checked").length;
    var unChecked = $(".crops").length;


    if (checked == unChecked) {
        $('#select-unselect-img').attr('src', 'images/select_all.png');
        $(".crops").prop('checked', false);
    } else {
        $('#select-unselect-img').attr('src', 'images/unselect_all.png');
        $(".crops").prop('checked', true);
    }


    $(".crops").each(function () {
        $(this).trigger('change');
    });
    hideLoadingImage();

}

/**
 * @changed - Abhishek
 * @date - 19-05-2016
 * @desc - for implementing select all fub=nctionality
 */
function changeSelectAllImage() {
    $('#select-unselect-img').attr('src', 'images/select_all.png');
}

function changeCropUOM(obj) {
    var cropName = $(obj).parent().parent().children("td:nth(0)").text().trim();
    var cropUOM = $(obj).val();
    $("#crop_resource_usage tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == cropName) {
            $(this).children("td:nth(1)").text(cropUOM);
            return false;
        }
    });
    $("#forward_sales_information tbody tr").each(function () {
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
    var totalLand = Number(removeAllCommas($.trim($("#total_land_available").text())));
    $("#cropInformationDetailFirstTable tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == cropName) {
            cropAcrage = (cropQuantity / Number(removeAllCommas($(this).children("td:nth(2)").find("input").val())));
            return false;
        }
    });
    $(obj).parent().siblings("td:nth(3)").find("input").val(getValueWithComma(parseInt(cropAcrage)));
    if (cropAcrage != 0 && cropAcrage != "" && $(obj).parent().siblings("td:nth(5)").find("input").prop("checked") == false) {
        $(obj).parent().siblings("td:nth(4)").find("input").prop("checked", true);
    }
    $("#crop_contract_table_tbody tr").each(function () {
        if ($(this).children("td:nth(1)").text().trim() == cropName + " (" + contractIdentifier + ")") {
			$(this).children("td:nth(2)").find("input").val(getValueWithComma(parseInt(cropAcrage)));
            $(this).children("td:nth(3)").find("input").val(getValueWithComma(parseInt((cropAcrage * 100) / totalLand)));
            return false;
        }
    });
}

function quantityCalForwardSale(obj) {
    var cropName = $(obj).parent().siblings("td:nth(0)").text().trim();
    var cropAcrage = Number(removeAllCommas($(obj).val()));
    var totalLand = Number(removeAllCommas($.trim($("#total_land_available").text())));
    var cropQuantity = 0;
    $("#cropInformationDetailFirstTable tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == cropName) {
            cropQuantity = (cropAcrage * Number(removeAllCommas($(this).children("td:nth(2)").find("input").val())));
            return false;
        }
    });
    $(obj).parent().siblings("td:nth(3)").find("input").val(getValueWithComma(parseInt(cropQuantity)));
    if (cropQuantity != 0 && cropQuantity != "" && $(obj).parent().siblings("td:nth(5)").find("input").prop("checked") == false) {
        $(obj).parent().siblings("td:nth(4)").find("input").prop("checked", true);
    }
    $("#crop_contract_table_tbody tr").each(function () {
        if ($(this).children("td:nth(1)").text().trim() == cropName + " (" + contractIdentifier + ")") {
            $(this).children("td:nth(2)").find("input").val(getValueWithComma(parseInt(cropAcrage)));
            $(this).children("td:nth(3)").find("input").val(getValueWithComma(parseInt((cropAcrage * 100) / totalLand)));
            return false;
        }
    });
}

function proposedAndFirmSelection(obj) {
    $(obj).parent().siblings("td:nth(5)").find("input").prop("checked", false);
    var totalLand = Number(removeAllCommas($.trim($("#total_land_available").text())));

    if ($(obj).parent().parent().children("td:nth(6)").find("input").prop("checked")) {
        var cropName = $(obj).parent().parent().children("td:nth(0)").text().trim();
        var acreValue = $(obj).parent().parent().children("td:nth(4)").find("input").val();
        var rowHTMLForCropContractTable = "";
        /**
         * @changed - Abhishek
         * @date - 26-02-2016
         * @desc - switched min and max columns for crop limits section
         */
        if ($("#group_table_tbody tr").length == 0) {
            rowHTMLForCropContractTable = '<tr class="tblbclgrnd text-center">' +
                '<td class="tblft1 hiddenTD"></td>' +
                '<td class="tblft1">' + cropName + ' (' + contractIdentifier + ')</td>' +
                '<td class="success croplimit"><input type="text" value="' + acreValue + '" class="minCropAcreage" disabled="disabled"></td>' +
                '<td class="success croplimit"><input type="text" value="' + Math.ceil((acreValue / totalLand) * 100) + '" class="minCropAcreagePercentage" disabled="disabled"></td>' +
                '<td class="success croplimit">NA</td>' +
                '<td class="success croplimit">NA</td>' +
                '</tr>';
        } else {
            rowHTMLForCropContractTable = '<tr class="tblbclgrnd text-center">' +
                '<td class="tblft1"></td>' +
                '<td class="tblft1">' + cropName + ' (' + contractIdentifier + ')</td>' +
                '<td class="success croplimit"><input type="text" value="' + acreValue + '" class="minCropAcreage" disabled="disabled"></td>' +
                '<td class="success croplimit"><input type="text" value="' + Math.ceil((acreValue / totalLand) * 100) + '" class="minCropAcreage" disabled="disabled"></td>' +
                '<td class="success croplimit">NA</td>' +
                '<td class="success croplimit">NA</td>' +
                '</tr>';
        }
        $("#crop_contract_table_tbody").append(rowHTMLForCropContractTable);
    } else if ($(obj).parent().parent().children("td:nth(6)").find("input").prop("checked") == false) {
        var cropName = $(obj).parent().parent().children("td:nth(0)").text().trim();
        $("#crop_contract_table_tbody tr").each(function () {
            if ($(this).children().eq(1).text().trim() == cropName + " (" + contractIdentifier + ")") {
                $(this).remove();
                return false;
            }
        });
    }
}

function showOptionalCropInformationDiv(cropName) {
    $("#crop_name_dynamic").html(cropName);
    $("#add_production_cost_field").attr("onclick", "addProductionCostField('optionalCropInformationDetail_" + cropName + "')");
    $("#modify_production_cost_field").attr("onclick", "modifyProductionCostField('optionalCropInformationDetail_" + cropName + "')");
    $("#remove_production_cost_field").attr("onclick", "removeProductionCostField('optionalCropInformationDetail_" + cropName + "')");
    $("#optional_crop_dynamic_div > div").hide();
    $('div[name="optionalCropInformationDetail_' + cropName + '"]').show();
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

function addNewCostComponents(name) {
    if ($("#crop-optional-cost-components").val().trim() == "") {
        customAlerts("Please enter cost Component", type_error, time);
        addErrorClassOnObject("#crop-optional-cost-components");
        return false;
    } else {
        alertify.confirm('Are you sure you want to add a new component named "' + $("#crop-optional-cost-components").val().trim() + '"?', function (e) {
            if (e) {

                var validationFlagComponent = true;
                $('div[name="' + name + '"]').find("table tbody tr").each(function () {
                    if ($(this).children("td:nth(1)").text().trim() == $("#crop-optional-cost-components").val().trim()) {
                        customAlerts('"' + $("#crop-optional-cost-components").val().trim() + '" Component is already exist', type_error, time);
                        addErrorClassOnObject("#crop-optional-cost-components");
                        validationFlagComponent = false;
                        return false;
                    }

                });
                if (validationFlagComponent) {
                    var rowHTMLForCostComponentOdd = '<tr class="tblgrn"><td class="tblft1 text-center"><input type="checkbox"></td><td class="tblft1">' + $("#crop-optional-cost-components").val() + '</td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td><td class="success infotext"></td></tr>';
                    var rowHTMLForCostComponentEven = '<tr class="tblbclgrnd"><td class="tblft1 text-center"><input type="checkbox"></td><td class="tblft1">' + $("#crop-optional-cost-components").val() + '</td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td><td class="infotext"></td></tr>';
                    if ($('div[name="' + name + '"]').find("table tbody tr").length % 2 != 0) {
                        $('div[name="' + name + '"]').find("table tbody").append(rowHTMLForCostComponentEven);
                    } else {
                        $('div[name="' + name + '"]').find("table tbody").append(rowHTMLForCostComponentOdd);
                    }

                    customAlerts('"' + $("#crop-optional-cost-components").val().trim() + '" Cost component added successfully', type_success, time);
                }
            }
            div_hide10();
        });
    }
}

function removeProductionCostField(name) {
    if ($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]").length == 0) {
        customAlerts("There is no Component for remove, Please add a component first", type_error, time);
    } else if ($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").length == 0) {
        customAlerts("Please select the Component for remove", type_error, time);
    } else if ($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").length > 0) {
        var componentNameList = "";
        $('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").each(function () {
            componentNameList += $(this).parent().siblings("td:nth(0)").text().trim() + ", ";
            //$(this).parent().parent().remove();
        });
        alertify.confirm('Are you sure you want to remove component named "' + componentNameList.substring(0, componentNameList.length - 2) + '"?', function (e) {
            if (e) {
                $('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").each(function () {
                    $(this).parent().parent().remove();
                });
                calculateTotalForOptionalCropInformation($('div[name="' + name + '"]').find("table"));
                customAlerts('"' + componentNameList.substring(0, componentNameList.length - 2) + '"' + " Component removed  Successfully", type_success, time);
            }
        });
    }
}

var componentNameForModify = "";

function modifyProductionCostField(name) {
    componentNameForModify = "";
    if ($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]").length == 0) {
        customAlerts("There is no Component for modify, Please add a component first", type_error, time);
    } else if ($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").length == 0) {
        customAlerts("Please select the Component for modify", type_error, time);
    } else if ($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").length > 1) {
        customAlerts("You can modify only one Component at a time", type_error, time);
    } else if ($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").length == 1) {
        componentNameForModify = $('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").parent().siblings("td:nth(0)").text().trim();
        $("#crop-optional-cost-components").val($('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").parent().siblings("td:nth(0)").text().trim());
        $("#update-componemt-button").attr("onclick", "updateNewCostComponents('" + name + "')");
        $("#add-new-componemt-button").hide();
        $("#update-componemt-button").show();
        $("#add-cost-component-span-dynamic").html("Update Cost Component");
        div_show10();
    }
}

function updateNewCostComponents(name) {
    if ($("#crop-optional-cost-components").val().trim() == "") {
        customAlerts("Please enter cost Component", type_error, time);
        addErrorClassOnObject("#crop-optional-cost-components");
        return false;
    } else {
        alertify.confirm('Are you sure you want to update component named "' + $("#crop-optional-cost-components").val().trim() + '"?', function (e) {
            if (e) {

                var validationFlagComponent = true;
                $('div[name="' + name + '"]').find("table tbody tr").each(function () {
                    if ($(this).children("td:nth(1)").text().trim() == $("#crop-optional-cost-components").val().trim()) {
                        if (componentNameForModify == $("#crop-optional-cost-components").val().trim()) {
                            validationFlagComponent = true;
                            return false;
                        } else {
                            customAlerts('"' + $("#crop-optional-cost-components").val().trim() + '" Component is already exist', type_error, time);
                            addErrorClassOnObject("#crop-optional-cost-components");
                            validationFlagComponent = false;
                            return false;
                        }
                    }
                });
                if (validationFlagComponent) {
                    $('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").parent().siblings("td:nth(0)").text($("#crop-optional-cost-components").val());
                    customAlerts('"' + $("#crop-optional-cost-components").val().trim() + '" Cost component update successfully', type_success, time);
                }
            }
            div_hide10();
            $('div[name="' + name + '"]').find("table tbody").find("input[type=checkbox]:checked").prop("checked", false);
        });
    }
}

function getCalculateValue(obj) {
    var unitValue = 0;
    var dollarPerUnitValue = 0;
    if (Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(2)").find("input").val())) != "") {
        unitValue = Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(2)").find("input").val()));
    }
    if (Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(3)").find("input").val())) != "") {
        dollarPerUnitValue = Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(3)").find("input").val()));
    }
    var columnTotal = unitValue * dollarPerUnitValue;
    $(obj).parent().parent().children("td:nth(4)").text(addCommaSignWithDollarWithValue("" + columnTotal));
    calculateTotalForOptionalCropInformation($(obj).parent().parent().parent().parent());
}

function calculateTotalForOptionalCropInformation(tableObj) {
    var rowTotal = 0;
    $(tableObj).find("tbody > tr").each(function () {
        if ($(this).children("td:nth(4)").text().trim() != "") {
            rowTotal += Number(removeAllCommasAndDollar($(this).children("td:nth(4)").text().trim()));
        }
    });
    showMessageOnConsole(rowTotal);
    $(tableObj).find("tfoot").children("tr:nth(1)").children("td:nth(3)").text(addCommaSignWithDollarWithValue("" + rowTotal));
}

function calculateTotalForOptionalCropInformationForAllTables() {
    $("#optional_crop_dynamic_div > div table").each(function () {
        var total = 0;
        $(this).children("tbody").find("tr").each(function () {
            if ($(this).children("td:nth(4)").text().trim() != "") {
                total += Number(removeAllCommasAndDollar($(this).children("td:nth(4)").text().trim()));
            }
        });
        $(this).children("tfoot").find("tr:nth(1)").children("td:nth(3)").text(addCommaSignWithDollarWithValue(total));
    });
}

function setTotalOfOptionalCropInformationToVariableProductionCost() {
    var cropName = $("#crop_name_dynamic").html().trim();
    var variableValue = $('div[name="optionalCropInformationDetail_' + cropName + '"]').find("tfoot").children("tr:nth(1)").children("td:nth(3)").text().trim();
    var variableProductionCost = "";
    $(".show_hide_class").addClass("hidden");
    $("#cropinfodetail").removeClass("hidden");
    $("#optional_crop_dynamic_div > div").hide();
    var obj = null;
    $("#cropInformationDetailFirstTable tbody tr").each(function () {
        if ($(this).children("td:nth(0)").text().trim() == cropName) {
            variableProductionCost = $(this).children("td:nth(8)").find("input").val().trim();
            obj = $(this).children("td:nth(8)").find("input");
            return false;
        }
    });
    if (variableProductionCost == "") {
        variableProductionCost = "$0.00";
    }
    alertify.confirm('Do you want to change the variable production cost for "' + cropName + '" from "' + variableProductionCost + '" to "' + variableValue + '"?', function (e) {
        if (e) {
            $(obj).val(variableValue);
            $(obj).change();
        }
    });
}

function addNewGroup() {
    var groupName = $("#group_name").val().trim();
    if (groupName == "") {
        customAlerts("Please enter group name", type_error, time);
        addErrorClassOnObject("#group_name");
        return false;
    } else if ($("#gropofcrop option:checked").length == 0) {
        customAlerts("Please select atleast one crop for group", type_error, time);
        return false;
    } else {
        /**
         * @changed - abhishek @date - 27-11-2015
         */
        alertify.confirm('Add crop group :"' + groupName + '"?', function (e) {
            if (e) {


                var exist = false;
                $("#group_table_tbody tr").each(function () {
                    if ($(this).find("td:nth(1)").text().trim() == groupName) {
                        exist = true;
                        return false;
                    }
                });
                if (exist == true) {
                    customAlerts('"' + groupName + '" group name is already exist', type_error, time);
                    addErrorClassOnObject("#group_name");
                } else {
                    var groupCropArray = [];
                    $("#gropofcrop option:checked").each(function () {
                        groupCropArray.push($(this).val());
                    });
                    globalGroupArray[groupName] = groupCropArray;
                    var rowHTMLForGroup = '<tr class="tblbclgrnd text-center">' +
                        '<td class="tblft1"><input type="checkbox" name="groupNameSelection[]"></td>' +
                        '<td class="tblft1">' + groupName + '</td>' +
                        '<td class="success croplimit"><input type="text" class="minCropAcreage" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithOutDollarDot(this); calculatePercentageOfMinAcreage(this);"></td>' +
                        '<td class="success croplimit"><input type="text" class="minCropAcreagePercentage" onkeypress="return isValidNumberValue(event)" onchange="calculatePercentageOfMinAcreage(this);"></td>' +
                        '<td class="success croplimit"><input type="text" class="maxCropAcreage" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithOutDollarDot(this); calculatePercentageOfMaxAcreage(this);"></td>' +
                        '<td class="success croplimit"><input type="text" class="maxCropAcreagePercentage" onkeypress="return isValidNumberValue(event)" onchange="calculatePercentageOfMaxAcreage(this);"></td>' +
                        '</tr>';
                    showModifyColumnInCropLimitTable();
                    $("#group_table_tbody").append(rowHTMLForGroup);

                    customAlerts('"' + groupName + '" crop group added', type_success, time);
                }
            }
            div_hide11();
            $("#group_name").val('');
        });
    }
}

var modifyGroupName = "";

function getGroupForModify() {
    if ($("input[name='groupNameSelection[]']").length == 0) {
        /**
         * @Changed : Abhishek
         * @Date : 25-11-2015
         * */
//		customAlerts("There is no group for modify, Please first add a group", type_error, time);
        customAlerts("There are no crop groups to modify", type_error, time);
    } else if ($("input[name='groupNameSelection[]']:checked").length == 0) {
        /**
         * @Changed : Abhishek
         * @Date : 25-11-2015
         * */
//		customAlerts("Please select a group for modify", type_error, time);
        customAlerts("Select a group to modify", type_error, time);
    } else if ($("input[name='groupNameSelection[]']:checked").length > 1) {
        customAlerts("You can modify only one group at a time", type_error, time);
    } else {
        deSelectAllCropsInGroupOptionAndRebuild();
        modifyGroupName = $("input[name='groupNameSelection[]']:checked").parent().siblings("td:nth(0)").text().trim();
        $("#group_name").val(modifyGroupName);
        for (var i = 0; i < globalGroupArray[modifyGroupName].length; i++) {
            $("#gropofcrop option").each(function () {
                if ($(this).val() == globalGroupArray[modifyGroupName][i]) {
                    $(this).prop("selected", "selected");
                    return false;
                }
            });
        }
        $("#gropofcrop").multiselect('rebuild');
        div_show12();
    }
}

function removeGroup() {
    if ($("input[name='groupNameSelection[]']").length == 0) {
        /**
         * @Changed : Abhishek
         * @Date : 25-11-2015
         * */
//		customAlerts("There are no groups defined", type_error, time);
        customAlerts("There are no crop groups to remove", type_error, time);
    } else if ($("input[name='groupNameSelection[]']:checked").length == 0) {
        /**
         * @Changed : Abhishek
         * @Date : 25-11-2015
         * */
//		customAlerts("Please select a group for remove", type_error, time);
        customAlerts("Select a group to remove", type_error, time);
    } else if ($("input[name='groupNameSelection[]']:checked").length > 0) {
        var GroupNameList = "";
        $("input[name='groupNameSelection[]']:checked").each(function () {
            GroupNameList += $(this).parent().siblings("td:nth(0)").text().trim() + ", ";
            //delete globalGroupArray[$("input[name='groupNameSelection[]']:checked").parent().siblings("td:nth(0)").text().trim()];
            //$("input[name='groupNameSelection[]']:checked").parent().parent().remove();
        });
        /**
         * @Changed : Abhishek
         * @Date : 27-11-2015
         * */
//		alertify.confirm('Are you sure you want to remove group named "' + GroupNameList.substring(0, GroupNameList.length-2) + '"?', function(e) {
        alertify.confirm('Remove the crop group : "' + GroupNameList.substring(0, GroupNameList.length - 2) + '"?', function (e) {
            if (e) {
                $("input[name='groupNameSelection[]']:checked").each(function () {
                    delete globalGroupArray[$("input[name='groupNameSelection[]']:checked").parent().siblings("td:nth(0)").text().trim()];
                    $("input[name='groupNameSelection[]']:checked").parent().parent().remove();
                    if ($("#group_table_tbody tr").length == 0) {
                        hideModifyColumnInCropLimitTable();
                    }
                });
                /**
                 * @Changed : Abhishek
                 * @Date : 25-11-2015
                 * */
//		customAlerts('"'+GroupNameList.substring(0, GroupNameList.length-2)+'"'+" Group removed  Successfully", type_success, time);
                customAlerts('Crop group "' + GroupNameList.substring(0, GroupNameList.length - 2) + '"' + " removed", type_success, time);
            }
        });
    }
}

function deSelectAllCropsInGroupOptionAndRebuild() {
    $("#gropofcrop option").each(function () {
        $(this).prop("selected", "");
    });
    $("#gropofcrop").multiselect('rebuild');
}

function modifyGroup() {
    var groupName = $("#group_name").val().trim();
    if (groupName == "") {
        customAlerts("Group name can not be blank", type_error, time);
        addErrorClassOnObject("#group_name");
        return false;
    } else if ($("#gropofcrop option:checked").length == 0) {
        customAlerts("Please select atleast one crop for group", type_error, time);
        return false;
    } else {
        alertify.confirm('Are you sure you want to update group named "' + groupName + '"?', function (e) {
            if (e) {
                var exist = false;
                $("#group_table_tbody tr").each(function () {
                    if ($(this).find("td:nth(1)").text().trim() == groupName && modifyGroupName != groupName) {
                        exist = true;
                        return false;
                    }
                });
                if (exist == true) {
                    customAlerts('Group with name "' + groupName + '" already exist', type_error, time);
                    addErrorClassOnObject("#group_name");
                } else {

                    delete globalGroupArray[modifyGroupName];
                    var groupCropArray = [];
                    $("#gropofcrop option:checked").each(function () {
                        groupCropArray.push($(this).val());
                    });
                    globalGroupArray[groupName] = groupCropArray;
                    $("#group_table_tbody tr").each(function () {
                        if ($(this).find("td:nth(1)").text().trim() == modifyGroupName) {
                            $(this).find("td:nth(1)").text(groupName);
                            return false;
                        }
                    });

                    customAlerts('Group with name "' + groupName + '" updated successfully', type_success, time);
                }
            }
            $("#group_table_tbody").find("input[type=checkbox]:checked").prop("checked", false);
            div_hide11();
        });
    }
}

function changeExpectedYieldValue(obj) {
    if ($("#crop_select_drop_down").val() == $(obj).parent().siblings("td:nth(0)").text().trim()) {
        $("#crop_Yield_Difference_Expected").text($(obj).val());
    }
}

function changeMaximumYieldValue(obj) {
    if ($("#crop_select_drop_down").val() == $(obj).parent().siblings("td:nth(0)").text().trim()) {
        $("#crop_Yield_Difference_Maximum").text($(obj).val());
    }
}

function changeMinimumYieldValue(obj) {
    if ($("#crop_select_drop_down").val() == $(obj).parent().siblings("td:nth(0)").text().trim()) {
        $("#crop_Yield_Difference_Minimum").text($(obj).val());
    }
}

function showFieldVariencePage() {
    $(".show_hide_class").addClass("hidden");
    $("#field_varience").removeClass("hidden");
}

function cropFieldChoiceCheckboxChenge(obj) {
    var count=0;
    $(".countChoiceCheckboxChenge").each(function() {
        if($(this).is(':checked')){
            count++;
        }
    });


    if(count > 10)
    {
        customAlerts("Crops/fields selection is limited to 10",'error',0);

        $(".countChoiceCheckboxChenge").each(function() {
            $(this).prop("checked", false);
        })

    }

}

function cropResourceUsageValueChange(obj) {
    var indexNumber = $(obj).parent().prevAll().length;
    if ($("#crop_select_drop_down").val() == $(obj).parent().siblings("td:nth(0)").text().trim()) {
        $("#crop_resources_usages_difference_tbody tr").each(function () {
            if ($(this).children("td:nth(0)").text().trim() == $("#crop_resource_usage thead tr td:nth(" + indexNumber + ") span:nth(0)").text().trim()) {
                $(this).children("td:nth(1)").text($(obj).val());
                return false;
            }
        });
    }
}

function fieldSelectFieldVarience(obj) {
    if ($(obj).val() == "0") {

    } else {
        $("#Plan_by_Fields_table tbody tr").each(function () {
            if ($(this).children("td:nth(1)").text().trim() == $(obj).val()) {
                if ($(this).children("td:nth(4)").find("input").prop("checked")) {
                    customAlerts("Selected field is declared as Fallow so don't have any crops", type_error, time);
                }
            }
        });
        $("#crop_select_drop_down option").each(function () {
            if ($(this).val() != "0") {
                $(this).remove();
            }
        });
        $("#field_choice_crop_table tbody tr").each(function () {
            if ($(this).children("td:nth(0)").text().trim() == $(obj).val()) {
                var index = 1;
                $(this).children("td:gt(0)").each(function () {
                    if ($(this).find("input").is(":checked")) {
                        $("#crop_select_drop_down").append('<option value="' + $("#field_choice_crop_table thead tr").children("td:nth(" + index + ")").text().trim() + '">' + $("#field_choice_crop_table thead tr").children("td:nth(" + index + ")").text().trim() + '</option>');
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

function getFieldYieldDiffence(obj) {
    if ($(obj).val() == "0") {
        $("#crop_Yield_Difference_Expected").text("");
        $("#crop_Yield_Difference_Maximum").text("");
        $("#crop_Yield_Difference_Minimum").text("");
        $("#resources_usages_production_resource_default").text("");
        $("#field_difference_exp").val("");
        $("#field_difference_min").val("");
        $("#field_difference_max").val("");
        $("#resources_usages_production_cost_resource_override").val("");
        $("#crop_resources_usages_difference_tbody tr").each(function () {
            $(this).children("td:nth(1)").text("");
            $(this).children("td:nth(2)").find("input").val("");
        });
    } else {
        $("#crop_resources_usages_difference_tbody tr").each(function () {
            $(this).children("td:nth(2)").find("input").val("");
        });
        $("#cropInformationDetailFirstTable tbody tr").each(function () {
            if ($(this).children("td:nth(0)").text().trim() == $(obj).val()) {
                $("#crop_Yield_Difference_Expected").text($(this).children("td:nth(2)").find("input").val().trim());
                $("#crop_Yield_Difference_Maximum").text($(this).children("td:nth(3)").find("input").val().trim());
                $("#crop_Yield_Difference_Minimum").text($(this).children("td:nth(4)").find("input").val().trim());
                $("#resources_usages_production_resource_default").text($(this).children("td:nth(8)").find("input").val().trim());
                return false;
            }
        });
        $("#crop_resource_usage tbody tr").each(function () {
            if ($(this).children("td:nth(0)").text().trim() == $(obj).val()) {
                var indexNumber = 3;
                $(this).children("td:gt(2)").each(function () {
                    var value = $(this).find("input").val();
                    $("#crop_resources_usages_difference_tbody tr").each(function () {
                        if ($(this).children("td:nth(0)").text().trim() == $("#crop_resource_usage thead tr td:nth(" + indexNumber + ") span:nth(0)").text().trim()) {
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
    // setIconOnCropForFieldDifferenceOnCropResourceUsage($(obj));

}

function setIconOnCropForFieldDifferenceOnCropResourceUsage() {
    var $currentObj = $('#crop_select_drop_down');
    if ($currentObj == 0) {
        return false;
    }

    $('#crop_resource_usage tbody tr').each(function () {
        if ($(this).children("td:nth(0)").text().trim() == $currentObj.val()) {

            var flag = false;
            $('#yield-difference-tbody').find('input').each(function () {
                if ($.trim($(this).val()) != '' && removeAllCommasAndDollar($.trim($(this).val())) != '0') {
                    flag = true;
                }
            });

            $('#crop_resources_usages_difference_tbody').find('input').each(function () {
                if ($.trim($(this).val()) != '' && removeAllCommasAndDollar($.trim($(this).val())) != '0') {
                    flag = true;
                }
            });

            if (/*!$(this).children("td:nth(0)").hasClass("crop_field_diff") && */flag) {
                $(this).children("td:nth(0)").removeClass("crop_field_diff").addClass("crop_field_diff");
                /**
                 * @changed - Abhishek
                 * @Date - 02-12-2015
                 */
                $(this).children("td:nth(0)").attr("title", "This crop has differences in yield and/or resource usage for field " + $("#field_select_drop_down").val() + ".");
                //$(this).children("td:nth(0)").attr("title", "This crop have field difference with field "+$("#field_select_drop_down").val()+".");
            } else {
                $(this).children("td:nth(0)").removeClass("crop_field_diff");
                $(this).children("td:nth(0)").removeAttr("title");
            }
        }
    });
}

/*function processForIconInFieldDifference(cropName){
 removeIconFromCropForFieldDifferenceOnCropResourceUsage();
 if(checkIfFieldDifferenceIsEmptyOrNot() == false){
 setIconOnCropForFieldDifferenceOnCropResourceUsage(cropName);
 }
 }

 function checkIfFieldDifferenceIsEmptyOrNot() {
 var flag = true;
 if ($("#field_difference_exp").val().trim() != "") {
 flag = false;
 } else if ($("#field_difference_min").val().trim() != "") {
 flag = false;
 } else if ($("#field_difference_max").val().trim() != "") {
 flag = false;
 } else if ($("#crop_resources_usages_difference_tbody").parent().find("thead").find("input").val().trim() != "") {
 flag = false;
 } else {
 $("#crop_resources_usages_difference_tbody tr").each(function() {
 if ($(this).find("td:nth(2)").find("input").val().trim() != "") {
 flag = false;
 return false;
 }
 });
 }
 return flag;
 }


 function setIconOnCropForFieldDifferenceOnCropResourceUsage(cropName){
 $('#crop_resource_usage tbody tr').each(function(){
 if($(this).children("td:nth(0)").text().trim() == cropName){
 $(this).children("td:nth(0)").addClass("crop_field_diff");
 $(this).children("td:nth(0)").attr("title", "This crop have field difference with field "+$("#field_select_drop_down").val()+".");
 return false;
 }
 });
 }
 function removeIconFromCropForFieldDifferenceOnCropResourceUsage(){
 $('#crop_resource_usage tbody tr').each(function(){
 $(this).children("td:nth(0)").removeClass("crop_field_diff");
 $(this).children("td:nth(0)").removeAttr("title");
 });
 }*/

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
    }
    else if (!validateCropLimits()) {
        return false;
    } else {
        return true;
    }
}

function saveAllFarmInformation() {

    if (!checkAllValidation()) {
        return false;
    } else {
        var irrigate_val = "no";
        var evaluate_forward_sales_val = false;
        var evaluate_crop_insurance_val = false;
        var evaluate_storage_sales_val = false;
        var crop_str = [];

        var crop_information_detail = [];
        var option_crop_info_array = [];
        var optional_planting_date_array = [];
        var cal_var_cost_crops = [];
        var manage_resource_tbody_array = [];
        var crop_resource_usage_tbody_array = [];
        var crop_limits_table_tbody_array = [];
        var forward_sales_information_tbody_array = [];
        var plan_by_field_tbody_array = [];
        var field_choice_crop_tbody_row_array = [];
        var crop_resources_usages_difference_tbody_array = [];
        var crop_group_array = [];
        var field_difference_str = "";
        if ($("input[name='irrigate']:checked").length > 0) {
            irrigate_val = $.trim("" + $('input[name=irrigate]:checked').val());
        }
        if ($("input[name='evaluate_forward_sales']:checked").length > 0) {
            evaluate_forward_sales_val = $.trim("" + $('input[name=evaluate_forward_sales]:checked').val());
        }
        if ($("input[name='evaluate_storage_sales']:checked").length > 0) {
            evaluate_storage_sales_val = $.trim("" + $('input[name=evaluate_storage_sales]:checked').val());
        }
        if ($("input[name='evaluate_crop_insurance']:checked").length > 0) {
            evaluate_crop_insurance_val = $.trim("" + $('input[name=evaluate_crop_insurance]:checked').val());

        }
        $("input[name='vegitable_crop[]']").each(function () {
            crop_str.push($.trim("" + $(this).val() + "#-#-#" + $(this).prop("checked") + "#-#-#vegitable"));
            // showMessageOnConsole($.trim("" + $(this).val() + "#-#-#" + $(this).prop("checked") + "#-#-#vegitable"));
        });
        $("input[name='field_crop[]']").each(function () {
            crop_str.push($.trim("" + $(this).val() + "#-#-#" + $(this).prop("checked") + "#-#-#field"));
            // showMessageOnConsole($.trim("" + $(this).val() + "#-#-#" + $(this).prop("checked") + "#-#-#field"));
        });
        $("#cropInformationDetailFirstTable tbody tr").each(function () {
            var cropName = $(this).children("td:nth(0)").text().trim();
            crop_information_detail.push($(this).children("td:nth(0)").text().trim() + "#-#-#"
                + $(this).children("td:nth(1)").find("select").val().trim() + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(2)").find("input").val().trim())) + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(4)").find("input").val().trim())) + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(3)").find("input").val().trim())) + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(5)").find("input").val().trim())) + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(7)").find("input").val().trim())) + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(6)").find("input").val().trim())) + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(8)").find("input").val().trim())));
            /*showMessageOnConsole($(this).children("td:nth(0)").text().trim() + "#-#-#" + $(this).children("td:nth(1)").find("select").val().trim() + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(2)").find("input").val().trim())) + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(4)").find("input").val().trim())) + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(3)").find("input").val().trim())) + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(5)").find("input").val().trim())) + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(7)").find("input").val().trim())) + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(6)").find("input").val().trim())) + "#-#-#"
                + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(8)").find("input").val().trim())));*/
            $('div[name="optionalCropInformationDetail_' + cropName + '"] > table tbody tr').each(function () {
                cal_var_cost_crops.push(cropName + "#-#-#" + $(this).children("td:nth(1)").text().trim() + "#-#-#"
                    + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(2)").find("input").val().trim())) + "#-#-#"
                    + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(3)").find("input").val().trim())) + "#-#-#"
                    + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(4)").text().trim())));
                /*showMessageOnConsole(cropName + "#-#-#" + $(this).children("td:nth(1)").text().trim() + "#-#-#"
                    + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(2)").find("input").val().trim())) + "#-#-#"
                    + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(3)").find("input").val().trim())) + "#-#-#"
                    + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(4)").text().trim())));*/
            });
        });
        $("#cropInformationDetailSecondTable tbody tr").each(function () {
            option_crop_info_array.push($(this).children("td:nth(0)").text().trim() + "#-#-#"
                + $(this).children("td:nth(1)").find("input").prop("checked") + "#-#-#"
                + $(this).children("td:nth(2)").find("input").prop("checked") + "#-#-#"
                + $(this).children("td:nth(3)").find("input").prop("checked"));
            /*showMessageOnConsole($(this).children("td:nth(0)").text().trim() + "#-#-#"
                + $(this).children("td:nth(1)").find("input").prop("checked") + "#-#-#"
                + $(this).children("td:nth(2)").find("input").prop("checked") + "#-#-#"
                + $(this).children("td:nth(3)").find("input").prop("checked"));*/
        });
        $("#cropInformationDetailThirdTable tbody tr").each(function () {
            var str = $(this).children("td:nth(0)").text().trim() + "#-#-#";
            if ($(this).children("td:nth(1)").find("input").val() == "") {
                str += "00/00/0000#-#-#";
            } else {
                str += $(this).children("td:nth(1)").find("input").val() + "#-#-#";
            }
            if ($(this).children("td:nth(2)").find("input").val() == "") {
                str += "00/00/0000#-#-#";
            } else {
                str += $(this).children("td:nth(2)").find("input").val() + "#-#-#";
            }
            if ($(this).children("td:nth(3)").find("input").val() == "") {
                str += "00/00/0000#-#-#";
            } else {
                str += $(this).children("td:nth(3)").find("input").val() + "#-#-#";
            }
            if ($(this).children("td:nth(4)").find("input").val() == "") {
                str += "00";
            } else {
                str += $(this).children("td:nth(4)").find("input").val();
            }
            optional_planting_date_array.push(str);
            // showMessageOnConsole(str);
        });

        /**
         *  @added - abhishek
         *  @date - 12-09-2016
         *  @desc - for additional crop income
         */
        var additionalCropCostObj = {};
        $("#crop-info-additional-income-tbody tr").each(function () {
            additionalCropCostObj[$.trim($(this).find('.cropNameSpecific').html())] = {
                governmentPayments: returnZeroIfBlank(removeAllCommasAndDollar($(this).find('.governmentPaymentsSpecific').val())),
                coProducts: returnZeroIfBlank(removeAllCommasAndDollar($(this).find('.coProductsSpecific').val())),
                additionalVariableCost: returnZeroIfBlank(removeAllCommasAndDollar($(this).find('.additionalVariableCostSpecific').val())),
                additionalIncome: returnZeroIfBlank(removeAllCommasAndDollar($(this).find('.additionalIncomeSpecific').val())),
            }
        });

        manage_resource_tbody_array.push("Land" + "#-#-#" + "Acres" + "#-#-#" + returnZeroIfBlank(removeAllCommas($("#total_land_available").text().trim())) + "#-#-#active");
        // showMessageOnConsole("Land" + "#-#-#" + "Acres" + "#-#-#" + returnZeroIfBlank(removeAllCommas($("#total_land_available").text().trim())));
        manage_resource_tbody_array.push("Capital" + "#-#-#" + "$/acre" + "#-#-#" + returnZeroIfBlank(removeAllCommasAndDollar($("#total_capital_available").val().trim())) + "#-#-#active");
        // showMessageOnConsole("Capital" + "#-#-#" + "$/acre" + "#-#-#" + returnZeroIfBlank(removeAllCommasAndDollar($("#total_capital_available").val().trim())));
        $("#manage_resource tbody tr:gt(1)").each(function () {
            var resourceAmt = $(this).children("td:nth(3)").find("input").val();

            if ($(this).children("td:nth(0)").find("input").prop("checked") == true
                && (resourceAmt != '' && removeAllCommasAndDollar(resourceAmt) != '0')) {
                manage_resource_tbody_array.push($(this).children("td:nth(1)").text().trim() + "#-#-#"
                    + $(this).children("td:nth(2)").text().trim() + "#-#-#"
                    + returnZeroIfBlank(removeAllCommas($(this).children("td:nth(3)").find("input").val().trim())) + "#-#-#active");
                /*showMessageOnConsole($(this).children("td:nth(1)").text().trim() + "#-#-#" + $(this).children("td:nth(2)").text().trim() + "#-#-#"
                 + returnZeroIfBlank(removeAllCommas($(this).children("td:nth(3)").find("input").val().trim())));*/
            } else if (resourceAmt != '' && removeAllCommasAndDollar(resourceAmt) != '0') {
                manage_resource_tbody_array.push($(this).children("td:nth(1)").text().trim() + "#-#-#"
                    + $(this).children("td:nth(2)").text().trim() + "#-#-#"
                    + returnZeroIfBlank(removeAllCommas($(this).children("td:nth(3)").find("input").val().trim())) + "#-#-#inactive");
            }
        });
        $("#crop_resource_usage tbody tr").each(function () {
            var cropName = $(this).find("td:nth(0)").text().trim();
            var indexNumber = 3;
            $(this).find("td:gt(2)").each(function () {

                crop_resource_usage_tbody_array.push(cropName + "#-#-#0#-#-#" + $("#crop_resource_usage thead tr td:nth(" + indexNumber + ") span:nth(0)").text().trim() + "#-#-#"
                    + returnZeroWithTwoDecimalIfBlank(removeAllCommas($(this).find("input").val().trim())) + "#-#-#");
                /*showMessageOnConsole(cropName + "#-#-#0#-#-#" + $("#crop_resource_usage thead tr td:nth(" + indexNumber + ") span:nth(0)").text().trim() + "#-#-#"
                    + returnZeroWithTwoDecimalIfBlank(removeAllCommas($(this).find("input").val().trim())) + "#-#-#");*/
                indexNumber++;
            });
        });
        $("#crop_limits_table_tbody tr").each(function () {
            var str = $(this).children("td:nth(1)").text().trim() + "#-#-#";
            //  min 1
            if ($(this).children("td:nth(2)").find("input").val() == "") {
                str += "0#-#-#";
            } else {
                str += removeAllCommas($(this).children("td:nth(2)").find("input").val()) + "#-#-#";
            }
            //  min percentage 2
            if ($(this).children("td:nth(3)").find("input").val() == "") {
                str += "0#-#-#";
            } else {
                str += removeAllCommas($(this).children("td:nth(3)").find("input").val()) + "#-#-#";
            }
            //  max 3
            if ($(this).children("td:nth(4)").find("input").val() == "") {
                str += "0#-#-#";
            } else {
                str += removeAllCommas($(this).children("td:nth(4)").find("input").val()) + "#-#-#";
            }
            //  max percentage 4
            if ($(this).children("td:nth(5)").find("input").val() == "") {
                str += "0#-#-#";
            } else {
                str += removeAllCommas($(this).children("td:nth(5)").find("input").val()) + "#-#-#";
            }
            crop_limits_table_tbody_array.push(str);
            // showMessageOnConsole(str);
        });
        /**
         * @Altered - Abhishek
         * @Date - 07-12-2015
         */
        var total_land = returnZeroIfBlank(removeAllCommas($("#total_land_available").text().trim()));

        $("#forward_sales_information tbody tr").each(function () {
            var str = $(this).children("td:nth(0)").text().trim() + "#-#-#";

            // Price
            if ($(this).children("td:nth(2)").find("input").val() == "") {
                str += "0#-#-#";
            } else {
                str += removeAllCommasAndDollar($(this).children("td:nth(2)").find("input").val()) + "#-#-#";
            }

            //	Quantity
            if ($(this).children("td:nth(3)").find("input").val() == "") {
                str += "0#-#-#";
            } else {
                str += removeAllCommas($(this).children("td:nth(3)").find("input").val()) + "#-#-#";
            }

            //	Firm Check (Contract)
            if ($(this).children("td:nth(6)").find("input").prop("checked") == false) {
                str += "false#-#-#";
            } else {
                str += "true#-#-#";
            }

            //	Acres
            if ($(this).children("td:nth(4)").find("input").val() == "") {
                str += "0#-#-#";
            } else {
                str += removeAllCommas($(this).children("td:nth(4)").find("input").val()) + "#-#-#";
            }

            //  Upper Limit
            if ($(this).children("td:nth(7)").find("input").val() == "") {
                str += "0#-#-#0#-#-#";
            } else {
                str += removeAllCommasWithPercent($(this).children("td:nth(7)").find("input").val()) + "#-#-#0#-#-#";
            }

            //  Proposed
            if ($(this).children("td:nth(5)").find("input").prop("checked") == false) {
                str += "false";
            } else {
                str += "true";
            }

            str += "#-#-#" + (removeAllCommas($(this).children("td:nth(4)").find("input").val()) / total_land) * 100

            forward_sales_information_tbody_array.push(str);
            // showMessageOnConsole(str);
        });
        $("#Plan_by_Fields_table tbody tr").each(function () {
            var str = $(this).children("td:nth(1)").text().trim() + "#-#-#"
                + returnZeroIfBlank(removeAllCommas($(this).children("td:nth(2)").text().trim())) + "#-#-#"
                + $(this).children("td:nth(3)").find("select").val().trim() + "#-#-#";
            if ($(this).children("td:nth(4)").find("input").prop("checked") == false) {
                str += "false#-#-#";
            } else {
                str += "true#-#-#";
            }
            if ($(this).children("td:nth(5)").find("input").prop("checked") == false) {
                str += "false#-#-#";
            } else {
                str += "true#-#-#";
            }
            if ($(this).children("td:nth(6)").find("input").prop("checked") == false) {
                str += "false";
            } else {
                str += "true";
            }
            plan_by_field_tbody_array.push(str);
            // showMessageOnConsole(str);
        });
        $("#field_choice_crop_table tbody tr").each(function () {
            var fieldName = $(this).children("td:nth(0)").text().trim();
            var indexNumber = 1;
            $(this).children("td:gt(0)").each(function () {
                var flag = "false";
                if ($(this).find("input").prop("checked") == true) {
                    flag = "true";
                }
                field_choice_crop_tbody_row_array.push(fieldName + "#-#-#" + $("#field_choice_crop_table thead tr td:nth(" + indexNumber + ")").text().trim() + "#-#-#" + flag + "#-#-#");
                // showMessageOnConsole(fieldName + "#-#-#" + $("#field_choice_crop_table thead tr td:nth(" + indexNumber + ")").text().trim() + "#-#-#" + flag + "#-#-#");
                indexNumber++;
            });
        });
        $("#crop_resources_usages_difference_tbody tr").each(function () {
            crop_resources_usages_difference_tbody_array.push($(this).children("td:nth(0)").text().trim() + "#-#-#" + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(2)").find("input").val().trim())));
            // showMessageOnConsole($(this).children("td:nth(0)").text().trim() + "#-#-#" + returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(2)").find("input").val().trim())));
        });
        if ($("#field_select_drop_down").val() != 0 && $("#crop_select_drop_down").val() != 0) {
            field_difference_str = $("#field_select_drop_down").val() + "#-#-#" + $("#crop_select_drop_down").val() + "#-#-#";
            if ($("#field_difference_exp").val() != "") {
                field_difference_str += removeAllCommasAndDollar($("#field_difference_exp").val()) + "#-#-#";
            } else {
                field_difference_str += "0#-#-#";
            }
            if ($("#field_difference_min").val() != "") {
                field_difference_str += removeAllCommasAndDollar($("#field_difference_min").val()) + "#-#-#";
            } else {
                field_difference_str += "0#-#-#";
            }
            if ($("#field_difference_max").val() != "") {
                field_difference_str += removeAllCommasAndDollar($("#field_difference_max").val()) + "#-#-#";
            } else {
                field_difference_str += "0#-#-#";
            }
            if ($("#resources_usages_production_cost_resource_override").val() != "") {
                field_difference_str += removeAllCommasAndDollar($("#resources_usages_production_cost_resource_override").val());
            } else {
                field_difference_str += "0";
            }
        }
        // showMessageOnConsole(field_difference_str);
        $("#group_table_tbody tr").each(function () {
            crop_groupName = $(this).children("td:nth(1)").text().trim();
            var maximum = returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(2)").find("input").val().trim()));
            var maximumPercentage = returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(3)").find("input").val().trim()));
            var minimum = returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(4)").find("input").val().trim()));
            var minimumPercentage = returnZeroIfBlank(removeAllCommasAndDollar($(this).children("td:nth(5)").find("input").val().trim()));
            var cropName = "";
            var cropNumber = 0;
            for (var i = 0; i < globalGroupArray[crop_groupName].length; i++) {
                if (globalGroupArray[crop_groupName][i] == undefined) {
                    continue;
                }
                cropName += globalGroupArray[crop_groupName][i];
                if (i < globalGroupArray[crop_groupName].length - 1) {
                    cropName += "#-#-#";
                }
                cropNumber++;
            }
            /**
             * @changed - Abhishek
             * @date - 02-12-2015
             */
            //crop_group_array.push(crop_groupName + "#-#-#" + maximum + "#-#-#" + minimum + "#-#-#" + cropNumber + "#-#-#" + cropName);
            crop_group_array.push(crop_groupName + "#-#-#" + maximum + "#-#-#" + maximumPercentage + "#-#-#" + minimum + "#-#-#" + minimumPercentage + "#-#-#" + cropNumber + "#-#-#" + cropName);
            // showMessageOnConsole(crop_groupName + "#-#-#" + maximum + "#-#-#" + minimum + "#-#-#" + cropNumber + "#-#-#" + cropName);
        });
        /**
         * @added - abhishek
         * @date - 18-05-2016
         * @desc - flag for maintaining monty carlo analysis
         */
        var montyCarloStatus = $('input[name="montyCarloSwitch"]').prop('checked');

        var baselineVal = $('input[name="baselineOrStrategyRadio"]:checked').val();
        var strategyName = "";
        if (baselineVal == "new" && $.trim($('#new-strategy-name').val()).length == 0) {
            customAlerts('Please enter new strategy name', type_error, time);
            addErrorClassOnObject($('#new-strategy-name'));
            return;
        } else if (baselineVal == "new" && $.trim($('#new-strategy-name').val()).length > 0) {
            strategyName = $.trim($('#new-strategy-name').val());
        }

        var ajaxRequest = $.ajax({
            url: 'agriculture/farmController/saveFarmInformation',
            type: 'POST',
            /*async : false,*/
            beforeSend: showLoadingImageForStrategy(),
            data: ({
                farmId: farmId,
                irrigate_val: irrigate_val,
                evaluate_forward_sales_val: evaluate_forward_sales_val,
                evaluate_crop_insurance_val: evaluate_crop_insurance_val,
                evaluate_storage_sales_val: evaluate_storage_sales_val,
                strategy: strategy,
                total_land: total_land,
                crop_str: crop_str,
                crop_information_detail: crop_information_detail,
                cal_var_cost_crops: cal_var_cost_crops,
                manage_resource_tbody_array: manage_resource_tbody_array,
                option_crop_info_array: option_crop_info_array,
                optional_planting_date_array: optional_planting_date_array,
                crop_resource_usage_tbody_array: crop_resource_usage_tbody_array,
                crop_limits_table_tbody_array: crop_limits_table_tbody_array,
                forward_sales_information_tbody_array: forward_sales_information_tbody_array,
                plan_by_field_tbody_array: plan_by_field_tbody_array,
                field_choice_crop_tbody_row_array: field_choice_crop_tbody_row_array,
                crop_resources_usages_difference_tbody_array: crop_resources_usages_difference_tbody_array,
                field_difference_str: field_difference_str,
                crop_group_array: crop_group_array,
                montyCarloStatus: montyCarloStatus,
                additionalCropCostObj: JSON.stringify(additionalCropCostObj),
                strategyName: strategyName,
                baselineVal: baselineVal
            }),
            success: function (response) {
                var status = response.status;
                if (status == 'success') {
                    customAlerts('Generating the most profitable strategy for "' + farmName + '"<br> and preparing output... Please be patient', type_success, time);

                    showLoadingImage();
                    var delay = 1000; //Your delay in milliseconds by rohit 14-04-15
//		            setTimeout(function(){ window.location = "output-edit-farm-info.htm?farmId="+farmId; }, delay);
                    setTimeout(function () {
                        window.location = "output-farm-info.htm?farmId=" + farmId;
                    }, delay);
                    //window.location = "farm.htm";
                } else if (status == 'Already exists') {
                    customAlerts('Farm with name "' + farmName + '" already exists, Please enter other farm name', type_error, time);
                }
            },
            error: function (XMLHttpRequest, status, message) {
                if (status != 'abort') {
                    customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, type_error, time);
                }
            }

        }).done(function () {
            hideLoadingImageForStrategy();
        });
        addStopButtonOnLoadingImageToStopAjax(ajaxRequest);
    }
}

function lastCropForCropFieldChoice() {
    $("#Plan_by_Fields_table tbody tr").each(function () {
        if ($(this).children("td:nth(3)").find("select").val() != "No Crop") {
            var fieldName = $(this).find('td:nth(1)').text().trim();
            var lastCrop = $(this).children("td:nth(3)").find("select").val();
            var index = 0;
            $("#field_choice_crop_table thead tr td").each(function () {
                if (lastCrop == $(this).text().trim()) {
                    return false;
                }
                index++;
            });
            $("#field_choice_crop_table tbody tr").each(function () {
                if ($(this).find('td:nth(0)').text().trim() == fieldName) {
                    $(this).find('td:nth(' + index + ')').html("<span class='last_crop' title='This crop has been selected as last crop.'>"
                        + $(this).find('td:nth(' + index + ')').html() + "</span>");
                    return false;
                }
            });
        }
    });
}

function changeButtonLabelForAlertifyConfirm(labelForOk, labelForCancel) {
    alertify.set({
        labels: {
            ok: labelForOk,
            cancel: labelForCancel,
        }
    });
}

function resetButtonLabelForAlertifyConfirm() {
    alertify.set({
        labels: {
            ok: "Ok",
            cancel: "Cancel",
        }
    });
}

function hideModifyColumnInCropLimitTable() {
    if ($("#crop_limits_table thead tr td:nth(0)").hasClass("hiddenTD") == false) {
        $("#crop_limits_table thead tr td:nth(0)").addClass("hiddenTD");
        $("#crop_limits_table_tbody tr").each(function () {
            $(this).children("td:nth(0)").addClass("hiddenTD");
        });
        $("#crop_contract_table_tbody tr").each(function () {
            $(this).children("td:nth(0)").addClass("hiddenTD");
        });
    }
}

function showModifyColumnInCropLimitTable() {
    if ($("#crop_limits_table thead tr td:nth(0)").hasClass("hiddenTD") == true) {
        $("#crop_limits_table thead tr td:nth(0)").removeClass("hiddenTD");
        $("#crop_limits_table_tbody tr").each(function () {
            $(this).children("td:nth(0)").removeClass("hiddenTD");
        });
        $("#crop_contract_table_tbody tr").each(function () {
            $(this).children("td:nth(0)").removeClass("hiddenTD");
        });
    }
}

function onChangeOfAcresWhenPlanningByAcre(obj) {
    if (flagSwitchedStrategyFieldsToAcres && totalAcresWhenSwitchingStrategyFieldsToAcres != 0
        && Number(removeAllCommas($(obj).val())) != totalAcresWhenSwitchingStrategyFieldsToAcres) {
        changeButtonLabelForAlertifyConfirm("Yes", "No");
        alertify.confirm('You previously entered information on your fields including acreage.<br/>Your total acreage was computed to be '
            + getValueWithComma(totalAcresWhenSwitchingStrategyFieldsToAcres) + '.'
            + '<br/>Do you want to change this value?<br/>If no, press No.'
            + '<br/>If yes, press Yes and enter the Available land for planting.'
            + '<br/>Keep in mind that this is now a different amount than the total for the fields you previously entered.'
            + '<br/>If you go back to planning by Fields it is recommended that you change one or more of your field sizes so '
            + 'that the Total acres computed for your fields is equal to the Available land for planting.',
            function (e) {
                if (!e) {
                    $(obj).val(totalAcresWhenSwitchingStrategyFieldsToAcres);
                    addCommaSignForAcres(obj);
                }
                resetButtonLabelForAlertifyConfirm();
            });
        // customAlerts("This acreage is the total acres for the fields that you
        // previously defined.\nTo keep the total acreage shown here in sync
        // with the total acres previously entered for your fields, you must
        // change the acreage of one or more of your the fields.", type_warning,
        // time);
    }
}

function messageWhenStrategyChangedFromFieldToAcerage() {
    $("#contentBox_FieldToAcres").html("");
    if (flagSwitchedStrategyFieldsToAcres) {
        $("#contentBox_FieldToAcres").html("Available land was computed from the field information entered.<br/>If you want to add or subtract acreage go to the <span class=\"field_info_div\">Field Information page</span>. Then continue planning, i.e. change crops, crop limits, contracts, etc.");
    }
}

function changeDisplayPropertyOfTBodyInCropLimitTable() {
    if ($("#crop_contract_table_tbody tr").length == 0) {
        $("#crop_contract_table_tbody").css("display", "none");
    } else {
        $("#crop_contract_table_tbody").css("display", "block");
    }
    if ($("#group_table_tbody tr").length == 0) {
        $("#group_table_tbody").css("display", "none");
    } else {
        $("#group_table_tbody").css("display", "block");
    }
}

function addStopButtonOnLoadingImageToStopAjax(ajaxRequest) {
    var delay = 30000;
    setTimeout(function () {
        ajaxRequestToStop = ajaxRequest;
        var html = '<button onclick="stopTheStrategy()">Stop the strategy building process...</button>';
        $("#stopAjaxRequestDiv").html(html);
    }, delay);
}

var ajaxRequestToStop = null;

function stopTheStrategy() {
    changeButtonLabelForAlertifyConfirm('Ok', 'Continue');
    alertify.confirm('Stop the Strategy building process?', function (e) {
        if (e) {
            ajaxRequestToStop.abort();
            hideLoadingImageForStrategy();
            closeStrategyOrBaselinePopup();
            $("#stopAjaxRequestDiv").html('');
        }
    });
    changeButtonLabelForAlertifyConfirm('Ok', 'Cancel')
}

/*function calculateProfitByCrop(obj) {

    var potentialProfit = (Number(removeAllCommas($(obj).parent().parent().children("td:nth(2)").find("input").val()))
        * Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(5)").find("input").val())))
        - Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(8)").find("input").val()));

    /!**
     * @Auther Raja
     * @since 14-Dec, 2015
     * @summary Change current logic to : if Estimated Income is negative then show negative amount with red color and don't include this to crop calculation
     *!/

    if (isNaN(potentialProfit) || potentialProfit < 0) {
        //$(obj).parent().parent().children("td:nth(9)").find("input").val('$0.00');
        /!**
         * @changed - Abhishek
         * @date - 29-12-2015
         *!/
        /!*$(obj).parent().parent().children("td:nth(9)").find("input").val('-$' + Math.abs(potentialProfit)).addClass("errorClassWithRedColor");*!/
        $(obj).parent().parent().children("td:nth(9)").find("input").val('-$' + Math.round(Math.abs(potentialProfit) * 100) / 100).addClass("errorClassWithRedColor");
    } else {
        /!**
         * @changed - Abhishek
         * @date - 29-12-2015
         *!/
        /!*$(obj).parent().parent().children("td:nth(9)").find("input").val('$' + potentialProfit).removeClass("errorClassWithRedColor");*!/
        $(obj).parent().parent().children("td:nth(9)").find("input").val('$' + Math.round(potentialProfit * 100) / 100).removeClass("errorClassWithRedColor");
    }
}*/

/**
 * @added - Abhishek
 * @date - 14-01-2016
 * @desc - For calculating profit for each crop by using Monty Carlo Simulation
 */
function calculateProfitByCrop(obj) {
    var montyCarloStatus = $('input[name="montyCarloSwitch"]').prop('checked');
    // console.log(montyCarloStatus);
    var profitForCrop = 0;
    if (montyCarloStatus) {
        var minYield = Number(removeAllCommas($(obj).parent().parent().children("td:nth(4)").find("input").val()));
        var maxYield = Number(removeAllCommas($(obj).parent().parent().children("td:nth(3)").find("input").val()));

        var minPrice = Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(7)").find("input").val()));
        var maxPrice = Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(6)").find("input").val()));
        var varCostProduction = Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(8)").find("input").val()));


        if ((minYield != 0 && maxYield != 0) && (minPrice != 0 && maxPrice != 0)) {
            for (var i = 0; i < 10000; i++) {
                profitForCrop += getRandomNumber(minYield, maxYield) * getRandomNumber(minPrice, maxPrice) - varCostProduction;
            }

            profitForCrop = Number(profitForCrop / 10000);
        } else {
            profitForCrop = (Number(removeAllCommas($(obj).parent().parent().children("td:nth(2)").find("input").val()))
                * Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(5)").find("input").val())))
                - Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(8)").find("input").val()));
        }
    }
    /**
     * @added - Abhishek
     * @date - 23-01-2016
     * @desc - if user entered only expected price and yields for the crop then calculation of profit for crop
     */
    else {
        profitForCrop = (Number(removeAllCommas($(obj).parent().parent().children("td:nth(2)").find("input").val()))
            * Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(5)").find("input").val())))
            - Number(removeAllCommasAndDollar($(obj).parent().parent().children("td:nth(8)").find("input").val()));
    }

    $('#crop-info-additional-income-tbody').find('tr').each(function () {
        if ($.trim($(obj).parent().parent().children("td:nth(0)").html()) == $.trim($(this).find('.cropNameSpecific').html())) {
            var additionalIncome = Number(removeAllCommasAndDollar($(this).find('.additionalIncomeSpecific').val()));
            if (!isNaN(profitForCrop) && (typeof additionalIncome != 'undefined' && !isNaN(additionalIncome))) {
                $(obj).parent().parent()
                    .children("td:nth(9)").find('span')
                    .html(addCommaSignWithDollarWithValue(profitForCrop) + " + " + addCommaSignWithDollarWithValue(additionalIncome) + "**");
                profitForCrop += additionalIncome;

            }
        }
    });


    if (isNaN(profitForCrop) || profitForCrop < 0) {
        profitForCrop = Math.round(Math.abs(profitForCrop) * 100) / 100;
        $(obj).parent().parent().children("td:nth(9)").find("input").val('-$' + profitForCrop).addClass("errorClassWithRedColor");
    } else {
        profitForCrop = Math.round(profitForCrop * 100) / 100;
        $(obj).parent().parent().children("td:nth(9)").find("input").val('$' + profitForCrop).removeClass("errorClassWithRedColor");
    }


}

/**
 * @added - Abhishek
 * @date - 14-01-2016
 * @desc -for getting random number for monty carlo simulation
 */
function getRandomNumber(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

/**
 *  @added - abhishek
 *  @date - 18-05-2016
 *  @desc - for handling monty carlo analysis toggle
 */
function enableDisableMontyCarloAnalysis() {

    $('#cropInformationDetailFirstTable > tbody').find('tr').each(function () {
        calculateProfitByCrop($(this).children("td:nth(2)").find("input"));
    });

    $('#crop-info-additional-income-tbody').find('tr').each(function () {
        calculateAdditionalCropProfit($(this).find(".governmentPaymentsSpecific"), false);
    });

}

function calculateAdditionalCropProfit(currentObj, flag) {
    var $this = $(currentObj).parents().eq(1);

    var govtPayments = Number(removeAllCommasAndDollar($this.find(".governmentPaymentsSpecific").val()));
    var coProductsSpecific = Number(removeAllCommasAndDollar($this.find(".coProductsSpecific").val()));
    var additionalVariableCostSpecific = Number(removeAllCommasAndDollar($this.find(".additionalVariableCostSpecific").val()));

    var additionalIncome;
    if ((!isNaN(govtPayments) /*&& govtPayments != 0*/) &&
        (!isNaN(coProductsSpecific) /*&& coProductsSpecific != 0*/) &&
        (!isNaN(additionalVariableCostSpecific) /*&& additionalVariableCostSpecific != 0*/)) {
        additionalIncome = (govtPayments + coProductsSpecific) - additionalVariableCostSpecific;
        if (!isNaN(additionalIncome)) {
            additionalIncome = addCommaSignWithDollarWithValue(additionalIncome);
            additionalIncome = typeof additionalIncome == 'undefined' ? '$0.00' : additionalIncome;
            $this.find('.additionalIncomeSpecific').val(additionalIncome);

            $('#cropInformationDetailFirstTable > tbody').find('tr').each(function () {
                if ($.trim($(this).children("td:nth(0)").html()) == $.trim($this.find('.cropNameSpecific').html())) {
                    var $input = $(this).children("td:nth(9)").find("input");
                    if (flag) {
                        calculateProfitByCrop($(this).children("td:nth(2)").find("input"));
                    }
                }
            });

        } else {
            $this.find('.additionalIncomeSpecific').val('$0.00');
        }
    } else {
        $this.find('.additionalIncomeSpecific').val('$0.00');
    }

}

function buildBaselineStrategySeletion() {
    $('input[name="baselineOrStrategyRadio"]').change(function () {
        var $this = $(this);

        if ($this.prop('checked') == true && $this.val() == 'new') {
            $('.strategy-name-div').show();
        } else if ($this.prop('checked') == true && $this.val() == 'baseline') {
            $('.strategy-name-div').hide();
        } else {
            $('.strategy-name-div').hide();
        }

    })
}

function openStrategyOrBaselinePopup() {
    if (!checkAllValidation()) {
        return false;
    } else {
        $('#save-strategy-popoup').show();
    }
}

function closeStrategyOrBaselinePopup() {
    $('#save-strategy-popoup').hide();
}
function calculatePercentageOfMinAcreage(obj) {
    var currentTr = $(obj).closest('tr');
    var totalLand = Number(removeAllCommas($.trim($("#total_land_available").text())));
    var cropname = currentTr.find('#crop_limits_table_crop_name__1').val();
    var minAcreage = Number(removeAllCommas(currentTr.find('.minCropAcreage').val()));
    var minAcragePer = Number(removeAllCommas(currentTr.find('.minCropAcreagePercentage').val()));
        if (minAcreage <= totalLand) {
            var minAcreagePer = currentTr.find('.minCropAcreagePercentage').val();
            if ($(obj).hasClass('minCropAcreage') && minAcreage && (minAcreage != 0 || minAcreage != '')) {
                var per = Math.ceil((minAcreage / totalLand) * 100);
                currentTr.find('.minCropAcreagePercentage').val(isNaN(per) ? '' : per);
            }
            if ($(obj).hasClass('minCropAcreagePercentage') && minAcreagePer && (minAcreagePer != 0 || minAcreagePer != '')) {
                var val = Math.ceil((totalLand * minAcreagePer) / 100);
                currentTr.find('.minCropAcreage').val(isNaN(val) ? '' : val);}
        } else {
        customAlerts("The total Minimum acreage crop can not be grater than Available Land " + totalLand , 'error', 0);
        currentTr.find('.minCropAcreage').val('');
        currentTr.find('.minCropAcreagePercentage').val('');

        }
}
function calculatePercentageOfMinAcreagePercent(obj) {
    var currentTr = $(obj).closest('tr');
    var totalLand = Number(removeAllCommas($.trim($("#total_land_available").text())));
    var cropname = currentTr.find('#crop_limits_table_crop_name__1').val();
    var minAcreage = Number(removeAllCommas(currentTr.find('.minCropAcreage').val()));
    var minAcragePer = Number(removeAllCommas(currentTr.find('.minCropAcreagePercentage').val()));
    if (minAcragePer<= 100) {
       var minAcreagePer = currentTr.find('.minCropAcreagePercentage').val();
        if ($(obj).hasClass('minCropAcreage') && minAcreage && (minAcreage != 0 || minAcreage != '')) {
            var per = Math.ceil((minAcreage / totalLand) * 100);
            currentTr.find('.minCropAcreagePercentage').val(isNaN(per) ? '' : per);
        }
        if ($(obj).hasClass('minCropAcreagePercentage') && minAcreagePer && (minAcreagePer != 0 || minAcreagePer != '')) {
            var val = Math.ceil((totalLand * minAcreagePer) / 100);
            currentTr.find('.minCropAcreage').val(isNaN(val) ? '' : val);}
    } else {
        customAlerts("The total Minimum acreage percent crop can not be grater than 100 ", 'error', 0);
        currentTr.find('.minCropAcreage').val('');
        currentTr.find('.minCropAcreagePercentage').val('');

    }
}
function calculatePercentageOfMaxAcreage(obj) {
    var currentTr = $(obj).closest('tr');
    var totalLand = Number(removeAllCommas($.trim($("#total_land_available").text())));
    var cropname = currentTr.find('#crop_limits_table_crop_name__1').val();
    var maxAcreage = Number(removeAllCommas(currentTr.find('.maxCropAcreage').val()));
    var maxAcragePer = Number(removeAllCommas(currentTr.find('.maxCropAcreagePercentage').val()));
            if (maxAcreage <= totalLand ) {
            var maxAcreagePer = currentTr.find('.maxCropAcreagePercentage').val();
            if ($(obj).hasClass('maxCropAcreage') && maxAcreage && (maxAcreage != 0 || maxAcreage != '')) {
                var per = Math.ceil((maxAcreage / totalLand) * 100);
                currentTr.find('.maxCropAcreagePercentage').val(isNaN(per) ? '' : per);
            }
            if ($(obj).hasClass('maxCropAcreagePercentage') && maxAcreagePer && (maxAcreagePer != 0 || maxAcreagePer != '')) {
                var val = Math.ceil((totalLand * maxAcreagePer) / 100);
                currentTr.find('.maxCropAcreage').val(isNaN(val) ? '' : val);}}
     else {
        customAlerts("The total Maximum acreage crop limit can not be grater than Available Land " + totalLand +
            " <br>  The Maximum Crop Acreage Limit will be set to the amount  of Available Land " + totalLand, 'error', 0);
        currentTr.find('.maxCropAcreage').val('');
        currentTr.find('.maxCropAcreagePercentage').val('');
            }
}
function calculatePercentageOfMaxAcreagePercentage(obj) {
    var currentTr = $(obj).closest('tr');
    var totalLand = Number(removeAllCommas($.trim($("#total_land_available").text())));
    var cropname = currentTr.find('#crop_limits_table_crop_name__1').val();
    var maxAcreage = Number(removeAllCommas(currentTr.find('.maxCropAcreage').val()));
    var maxAcragePer = Number(removeAllCommas(currentTr.find('.maxCropAcreagePercentage').val()));
    if ( maxAcragePer >=0 && maxAcragePer<= 100  ) {
        var maxAcreagePer = currentTr.find('.maxCropAcreagePercentage').val();
        if ($(obj).hasClass('maxCropAcreage') && maxAcreage && (maxAcreage != 0 || maxAcreage != '')) {
            var per = Math.ceil((maxAcreage / totalLand) * 100);
            currentTr.find('.maxCropAcreagePercentage').val(isNaN(per) ? '' : per);
        }
        if ($(obj).hasClass('maxCropAcreagePercentage') && maxAcreagePer && (maxAcreagePer != 0 || maxAcreagePer != '')) {
            var val = Math.ceil((totalLand * maxAcreagePer) / 100);
            currentTr.find('.maxCropAcreage').val(isNaN(val) ? '' : val);}
    } else {
        customAlerts("The total Maximum acreage percent crop limit must be 1 to 100% ", 'error', 0);
        currentTr.find('.maxCropAcreage').val(isNaN(totalLand)?'':totalLand);
        currentTr.find('.maxCropAcreagePercentage').val('100');
    }
}

function addPopupNegativeValue(id) {
    // var idVal = removeAllCommasAndDollar($(id).val());
    // var colNo = idVal.split('__');
    // var cropCol = "forward_sales_information_tbody_row_crop_name__" + colNo[1];
    // console.log('#' + cropCol);
    // var val = $('#' + cropCol).text();
    var val = $.trim(removeAllCommasAndDollar($(id).val()));
    if (val <= 0) {
        $(id).css("border", "1px solid red");
        customAlerts('Expected price cannot be 0 or less than 0', 'error', 0);
    } else {
        $(id).css("border", "1px solid #b7b7b7");
    }
}

function addForwardNegativePriceRedBox(obj) {

    var forwardSalesPrice = removeAllCommasAndDollar($.trim($(obj).val()));

    if(forwardSalesPrice != "") {
        $("#cropInformationDetailFirstTable tbody").find('tr').each(function () {
            var cropname = $(obj).parent().parent().children("td:nth(0)").html();
            var acr=$(obj).parent().parent().children("td:nth(4)").find("input").val();
            if ($(this).children("td:nth(0)").html() == $(obj).parent().parent().children("td:nth(0)").html()) {
                var expectedYield = Number(removeAllCommasAndDollar($(this).children("td:nth(2)").find("input").val()));
                var variableProductionCost = Number(removeAllCommasAndDollar($(this).children("td:nth(8)").find("input").val()));
                var EstIncome=Number(removeAllCommasAndDollar($(this).children("td:nth(9)").find("input").val()));
                var per = Math.ceil((expectedYield * forwardSalesPrice) - variableProductionCost);
                if (per < 0) {
                    addErrorClassOnObject(obj);
                    $("#acr").html(acr);
                    $("#cropName").html(cropname);
                    $("#amount").html(per);
                    $("#cropName2").html(cropname);
                    $('#negative-message-pop-up').show();
                } else {
                    removeErrorClassFormObjects(obj);
                }
            }

        });
    }
}
