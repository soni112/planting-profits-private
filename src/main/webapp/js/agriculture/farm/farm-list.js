/**
 * Created by abhishek on 2/9/16.
 */

function deleteSelectedFarms() {
    var totalFarms = $("input[name='farms[]']").length;
    if (totalFarms == 0) {
        customAlerts("You don't have any farm to delete", "warning", 0);
        return false;
    }
    totalFarms = $("input[name='farms[]']:checked").length;
    var farmNames = "";
    var confirmMessage = "";
    var successMessage = "";
    var farmIdsArray = new Array();
    if (totalFarms == 0) {
        customAlerts("Please select at least one farm to delete", "error", 0);
        return false;
    }
    $("input[name='farms[]']:checked").each(function() {
        farmIdsArray.push($(this).val());
        farmNames += $(this).attr('id').split("-@-@-")[2] + ", ";
    });
    if (farmNames.split(",").length <= 2) {
        farmNames = farmNames.substring(0, farmNames.length - 2);
        confirmMessage = 'Are you sure you want to delete "' + farmNames
            + '" farm?';
        successMessage = '"' + farmNames
            + '" farm has been deleted successfully';
    } else if (farmNames.split(",").length > 2) {
        farmNames = farmNames.substring(0, farmNames.length - 2);
        confirmMessage = 'Are you sure you want to delete these "' + farmNames + '" farms?';
        successMessage = '"' + farmNames + '" farms have been deleted successfully';
    }
    alertify.confirm(confirmMessage, function(e) {
        if (e) {
            $.ajax({
                url : 'agriculture/farmController/deleteSelectedFarms',
                type : 'POST',
                /* async : false, */
                beforeSend : showLoadingImage(),
                data : ({
                    farmIdsArray : farmIdsArray,
                }),
                success : function(response) {
                    var status = response.status;
                    if (status == "success") {
                        customAlerts(successMessage, "success", 0);
                    } else {
                        customAlerts("Some problem occured in deleting farms", "error", 0);
                    }
                    var delay = 1000;
                    setTimeout(function() {
                        window.location = "farm.htm";
                    }, delay);
                },
                error : function(XMLHttpRequest, status, message) {
                    customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, "error", 0);
                }
            }).done(function() {
                hideLoadingImage();
            });
        }
    });
}

function deleteAllFarms() {
    var totalFarms = $("input[name='farms[]']").length;
    if (totalFarms == 0) {
        customAlerts("You don't have any farm to delete", "warning", 0);
        return false;
    }
    alertify.confirm("Are you sure you want to delete all the farms?", function(e) {
        if (e) {
            $.ajax({
                url : 'agriculture/farmController/deleteAllFarms',
                type : 'POST',
                /* async : false, */
                beforeSend : showLoadingImage(),
                success : function(response) {
                    var status = response.status;
                    if (status == "success") {
                        customAlerts(
                            "All farms have been deleted successfully", "success", 0);
                    } else {
                        customAlerts(
                            "Some problem occured in deleting farms", "error", 0);
                    }
                    var delay = 1000;
                    setTimeout(function() { window.location = "farm.htm"; }, delay);
                },
                error : function(XMLHttpRequest, status, message) {
                    customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, "error", 0);
                }
            }).done(function() {
                hideLoadingImage();
            });
        }
    });
}
var selectedFarmIDForUpdate = 0;
var selectedFarmNameForUpdate = "";

function editSelectedFarm() {
    var totalFarms = $("input[name='farms[]']").length;
    if (totalFarms == 0) {
        customAlerts("You don't have any farm to edit", "error", 0);
        return false;
    }
    var totalSelectedFarms = $("input[name='farms[]']:checked").length;
    if (totalSelectedFarms == 0) {
        customAlerts("Please first select a farm for edit", "error", 0);
        return false;
    } else if (totalSelectedFarms > 1) {
        customAlerts("You can edit only one farm at a time", "error", 0);
        return false;
    }
    var farmName = $("input[name='farms[]']:checked").attr('id').split("-@-@-")[2];
    var farmId = $("input[name='farms[]']:checked").attr('value');
    alertify.confirm("Are you sure you want to edit \"" + farmName + "\" farm?", function(e) {
        if (e) {
            $.ajax({
                url : 'agriculture/farmController/getFarmDetails',
                type : 'POST',
                beforeSend : showLoadingImage(),
                data : {
                    farmId : farmId,
                },
                /* async : false, */
                success : function(response) {
                    var status = response.status;
                    var result = response.result;
                    if (status == "success") {
                        selectedFarmIDForUpdate = result.id;
                        selectedFarmNameForUpdate = result.name;
                        $("#update-farm-id").val(result.id);
                        $("#update-farm-name").val(result.name);
                        $("#update-physical-localtion").val(result.physical_Location);
                        $("#update-physical-localtion").attr('readonly', true);
                        div_showUpdateFarmDiv();
                    } else {
                        customAlerts("Some problem occured in farm edit process", "error", 0);
                    }
                },
                error : function(XMLHttpRequest, status, message) {
                    customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, "error", 0);
                }
            }).done(function() {
                hideLoadingImage();
            });
        }
    });
}

function updateSelectedFarm() {
    var farmId = $("#update-farm-id").val();
    var farmName = $("#update-farm-name").val();
    if (selectedFarmIDForUpdate == farmId) {

        if (farmName == "") {
            customAlerts("Farm name can not be blank", "error", 0);
            return false;
        } else if (selectedFarmNameForUpdate.trim() == farmName.trim()) {
            customAlerts("Old farm name and new farm name can not be same", "error", 0);
            return false;
        } else {
            alertify.confirm("Are you sure you want to update farm \"" + selectedFarmNameForUpdate + "\" name to \"" + farmName + "\" name?", function(e) {
                if (e) {
                    $.ajax({
                        url : 'agriculture/farmController/updateFarmDetails',
                        type : 'POST',
                        beforeSend : showLoadingImage(),
                        data : {
                            farmId : farmId,
                            farmName : farmName,
                        },
                        /* async : false, */
                        success : function(response) {
                            var status = response.status;
                            if (status == "success") {
                                div_hideUpdateFarmDiv();
                                customAlerts("Farm Name has been updated from \"" + selectedFarmNameForUpdate + "\" to \"" + farmName + "\" successfully", "success", 0);
                            } else {
                                div_hideUpdateFarmDiv();
                                customAlerts("Some problem occured in farm edit process, Please try again later", "error", 0);
                            }
                            var delay = 1000;
                            setTimeout(function() { window.location = "farm.htm"; }, delay);
                        },
                        error : function(XMLHttpRequest, status, message) {
                            div_hideUpdateFarmDiv();
                            customAlerts("Error" + XMLHttpRequest + ":" + status + ":" + message, "error", 0);
                        }
                    }).done(function() {
                        hideLoadingImage();
                    });
                }
            });
        }
    }
}

$(document).ready(function() {
    /**
     * @changed - Abhishek
     * @date - 03-12-2015
     */
    if(country != 'none'){

        $("#physical-localtion").geocomplete({
            country: country,
        });
    } else {
        $("#physical-localtion").geocomplete({
        });
    }

    // $("#physical-localtion").keyup(function(event) {
    //     if (event.keyCode == 13) {
    //         createFarm();
    //     }
    // });

    $("#physical-localtion").on('blur', function (event) {
        if($(this).val()!=""){
            document.getElementById('physical-localtion').readOnly = true;
        }
        else {
            document.getElementById('physical-localtion').readOnly = false;
        }

    })

});
function enableLocation() {
    document.getElementById('physical-localtion').readOnly = false;


}
function validCreatedFarm() {
    var isFarmValidated = true;
    var farmName = $.trim("" + $('#farm-name').val());
    var physicalLocation = $.trim("" + $('#physical-localtion').val());

    /*
     * Code commented as per clients requirement By Harshit Gupta 01-04-2015
     * Start
     */
    /*
     * var fixedCost = $.trim("" + $('#fixed_cost').val()); var livingExpenses =
     * $.trim("" + $('#living_expenses').val()); var additionalProfit =
     * $.trim("" + $('#additional_profit').val());
     */
    var fixedCost = "0";
    var livingExpenses = "0";
    var additionalProfit = "0";

    /* END */

    if (farmName == "") {
        if (isFarmValidated) {
            customAlerts("Please enter your farm name", "error", 0);
            addErrorClassOnObject('#farm-name');
            isFarmValidated = false;
        }
    }


    if (physicalLocation == "") {
        if (isFarmValidated) {
            customAlerts("Please enter Physical location ", "error", 0);
            addErrorClassOnObject('#physical-localtion');
            isFarmValidated = false;
        }
    }


    if (fixedCost == "") {
        if (isFarmValidated) {
            customAlerts("Please enter fixed cost", "error", 0);
            isFarmValidated = false;
        }
    }
    if (!validateNumberOnly(fixedCost)) {
        if (isFarmValidated) {
            customAlerts("Please enter valid fixed cost", "error", 0);
            isFarmValidated = false;
        }
    }
    if (livingExpenses == "") {
        if (isFarmValidated) {
            customAlerts("Please enter living expenses", "error", 0);
            isFarmValidated = false;
        }
    }
    if (!validateNumberOnly(livingExpenses)) {
        if (isFarmValidated) {
            customAlerts("Please enter valid living expenses", "error", 0);
            isFarmValidated = false;
        }
    }
    if (additionalProfit == "") {
        if (isFarmValidated) {
            customAlerts("Please enter additional profit", "error", 0);
            isFarmValidated = false;
        }
    }
    if (!validateNumberOnly(additionalProfit)) {
        if (isFarmValidated) {
            customAlerts("Please enter valid additional profit", "error", 0);
            isFarmValidated = false;
        }
    }
    return isFarmValidated;
}
function createFarm() {

    var isFarmValid = validCreatedFarm();
    if (!isFarmValid) {
        return false;
    }
    var farmName = $.trim("" + $('#farm-name').val());
    var physicalLocation = $.trim("" + $('#physical-localtion').val());
    /*
     * Code commented as per clients requirement By Harshit Gupta 01-04-2015
     * Start
     */
    /*
     * var fixedCost = $.trim("" + $('#fixed_cost').val()); var livingExpenses =
     * $.trim("" + $('#living_expenses').val()); var additionalProfit =
     * $.trim("" + $('#additional_profit').val()); var profitGoal = $.trim("" +
     * $('#profit_goal').val());
     */

    // var fixedCost = 0;
    // var livingExpenses = 0;
    // var additionalProfit = 0;
    // var profitGoal = 0;
    $.ajax({
        url : 'agriculture/farmController/createFarm',
        type : 'POST',
        /* async : false, */
        beforeSend : showLoadingImage(),
        data : ({
            farmName : farmName,
            physicalLocation : physicalLocation,
            // fixedCost : fixedCost,
            // livingExpenses : livingExpenses,
            // additionalProfit : additionalProfit,
            // profitGoal : profitGoal
        }),
        success : function(response) {
            var status = response.status;
            if (status == 'success') {
                customAlerts('"' + farmName + '" Farm name created successfully', "success", 0);
                var farmId = response.result;
                // alert(farmId);
                window.location = "farm-info.htm?farmId="+ farmId;
            } else if (status == 'Already exists') {
                customAlerts('"' + farmName + '" Farm name is already exists,Please enter other farm name', "error", 0);
            } else if (status == 'invalid user') {
                customAlerts("Please login first", "error", 0);
                window.location = "home.htm";
            }
        },
        error : function(XMLHttpRequest, status, message) {
            customAlerts("Error" + XMLHttpRequest + ":"
                + status + ":" + message, "error", 0);
        }

    }).done(function() {
        hideLoadingImage();
    });

}