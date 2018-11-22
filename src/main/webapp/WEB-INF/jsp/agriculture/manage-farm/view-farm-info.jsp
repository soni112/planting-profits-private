<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="cfun" uri="/WEB-INF/tld/functions.tld" %>
<link rel="stylesheet" href="<c:url value="/css/bootstrap-multiselect.css"/>" type="text/css"/>
<link rel="stylesheet" href="<c:url value="/css/sb-admin-2.css"/>" type="text/css" media="all">
<script src="<c:url value="/js/plugins/jquery.slimscroll.js"/>"></script>
<script>
    showLoadingImage();
    var farmId = '<c:out value="${model.farm.farmId}" />';
    var farmName = '<c:out value="${model.farm.farmName}" />';
    var strategyJSTL = '<c:out value="${model.farmInfoView.strategy}" />';
    var oldStrategy = "";
    var strategy = "";
    var globalGroupArray = new Array();
    $(document).ready(function () {

        $('#header-farm-name').html(farmName);

        addActiveClass($("#menu-farm"));

        if (strategyJSTL == "PLAN_BY_FIELDS") {
            oldStrategy = "fields";
            strategy = "fields";
            $("#field-choice").removeClass("hidden");
            $("#show_hide_field_variance_button").show();
        } else {
            oldStrategy = "acres";
            strategy = "acres";
            $("#field-choice").addClass("hidden");
            $("#show_hide_field_variance_button").hide();
        }
        var manageStep1 = true;
        var manageStep2 = true;
        var manageStep3 = true;
        var manageStep4 = true;
        var manageStep5 = true;
        var manageStep6 = true;
        var availableLand = 0;
        enableDisableLeftMenu(6);
        lastCropForCropFieldChoice();
        calculateTotalForOptionalCropInformationForAllTables();
    });

</script>
<script src="<c:url value="/js/agriculture/farm/manage_farm.js?v=0.1" />" type="text/javascript"></script>
<script src="<c:url value="/js/agriculture/farm-info-slider.js" />" type="text/javascript"></script>
<script src="<c:url value="/js/plugins/bootstrap-multiselect.js" />" type="text/javascript"></script>

<script type="text/javascript">
    <c:forEach var="groupList" items="${model.cropsGroupList}">
    var cropArray = new Array();
    <c:forEach var="crops" items="${groupList.cropSet}">
    cropArray.push('${crops.cropName}');
    </c:forEach>
    globalGroupArray['${groupList.cropsGroupName}'] = cropArray;
    </c:forEach>
</script>
<!-- end -->

<div class="leftside">
    <%@ include file="common/menu.jsp" %>
    <div class="mainsection farm_section farm_innerpages">
        <section>
            <div class="wrap clearfix">
                <%@ include file="common/left_slider.jsp" %>
                <div
                        class="col-lg-9 col-sm-9 col-md-9 padding-right-none padding-left-none">
                    <div class="right_farm_form_filled">
                        <!-- @changed - Abhishek @date - 01-12-2015 -->
                        <%--<div class="progress_bar">--%>
                        <div class="progress_bar hidden">
                            <img id="image_bar" src="<c:url value="/images/progress_bar/progress-bar1.png"/>">
                        </div>
                        <div id="farm-info" class="show_hide_class visited">
                            <div class="form_area">
                                <!-- @changed - Abhishek @date - 30-12-2015 -->
                                <%--<h2 class="form-heading">Farm Information</h2>--%>
                                <h2 class="form-heading">Planning Mode</h2>
                                <div class="ques">
                                    <div class="col-lg-4 col-md-12 col-sm-12 padding-left-none">
                                        <span class="filled_question">Plan By:</span>
                                    </div>
                                    <c:choose>
                                        <c:when test="${model.farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                                            <div class="col-lg-2 col-md-3 col-sm-3 padding-left-none option_selection">
                                                <label>
                                                    <input type="radio" class="strategy" name="plan_by_farm"
                                                           value="fields" checked="checked"><span>Fields</span>
                                                </label>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-3 option_selection">
                                                <label>
                                                    <input type="radio" class="strategy" name="plan_by_farm"
                                                           value="acres"><span>Acres</span>
                                                </label>
                                            </div>
                                            <div class="col-lg-3 col-md-4 col-sm-4 option_selection">
                                                <!-- <label> <input type="radio" class="strategy" name="plan_by_farm" value="both" ><span>Both</span></label> -->
                                            </div>
                                        </c:when>

                                        <c:when test="${model.farmInfoView.strategy eq 'PLAN_BY_ACRES'}">
                                            <div class="col-lg-2 col-md-3 col-sm-3 padding-left-none option_selection">
                                                <label>
                                                    <input type="radio" class="strategy" name="plan_by_farm"
                                                           value="fields"><span>Fields</span>
                                                </label>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-3 option_selection">
                                                <label>
                                                    <input type="radio" class="strategy" name="plan_by_farm"
                                                           value="acres" checked="checked"><span>Acres</span>
                                                </label>
                                            </div>
                                            <div class="col-lg-3 col-md-4 col-sm-4 option_selection">
                                                <!-- <label> <input type="radio" class="strategy" name="plan_by_farm" value="both" ><span>Both</span></label> -->
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-lg-2 col-md-3 col-sm-3 padding-left-none option_selection">
                                                <label>
                                                    <input type="radio" class="strategy" name="plan_by_farm"
                                                           value="fields"><span>Fields</span>
                                                </label>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-3 option_selection">
                                                <label>
                                                    <input type="radio" class="strategy" name="plan_by_farm"
                                                           value="acres"><span>Acres</span>
                                                </label>
                                            </div>
                                            <div class="col-lg-3 col-md-4 col-sm-4 option_selection">
                                                <!-- <label> <input type="radio" class="strategy" name="plan_by_farm" value="both" checked="checked"><span>Both</span></label> -->
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="col-lg-1 col-md-2 col-sm-2 option_selection">
                                        <a id="farm_Information_planBy" class="help_Infromation_PopUp"
                                           href="javascript:;">
                                            <img src="<c:url value="/images/i-icon.png"/> ">
                                        </a>
                                    </div>
                                </div>
                                <div class="clearfix"></div>

                                <!--
                                    @changed -abhishek @date - 27-11-2015
                                 -->

                                <%--<div class="ques">
                                    <div class="col-lg-4 col-md-12 col-sm-12 padding-left-none">
                                        <span class="filled_question">Do you irrigate any of your land? </span>
                                    </div>
                                    <c:choose>
                                        <c:when test="${model.farmInfoView.irrigate eq 'yes'}">
                                            <div class="col-lg-2 col-md-3 col-sm-2 padding-left-none option_selection">
                                                <label> <input type="radio" name="irrigate" value="no"><span>No</span></label>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-3 option_selection">
                                                <label> <input type="radio" name="irrigate" value="yes" checked="checked"><span>Yes</span></label>
                                            </div>
                                            <div class="col-lg-3 col-md-4 col-sm-5 option_selection">
                                                <!-- <label> <input type="radio" name="irrigate" value="sometimes"><span>Sometimes</span></label> -->
                                            </div>
                                        </c:when>
                                        <c:when test="${model.farmInfoView.irrigate eq 'sometimes'}">
                                            <div class="col-lg-2 col-md-3 col-sm-2 padding-left-none option_selection">
                                                <label> <input type="radio" name="irrigate" value="no"><span>No</span></label>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-3 option_selection">
                                                <label> <input type="radio" name="irrigate" value="yes"><span>Yes</span></label>
                                            </div>
                                            <div class="col-lg-3 col-md-4 col-sm-5 option_selection">
                                                <!-- <label> <input type="radio" name="irrigate" value="sometimes" checked="checked"><span>Sometimes</span></label> -->
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="col-lg-2 col-md-3 col-sm-2 padding-left-none option_selection">
                                                <label> <input type="radio" name="irrigate" value="no" checked="checked"><span>No</span></label>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-3 option_selection">
                                                <label> <input type="radio" name="irrigate" value="yes"><span>Yes</span></label>
                                            </div>
                                            <div class="col-lg-3 col-md-4 col-sm-5 option_selection">
                                                <!-- <label> <input type="radio" name="irrigate" value="sometimes"><span>Sometimes</span></label> -->
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="col-lg-1 col-md-2 col-sm-2 option_selection">
                                        <a id="farm_Information_irrigate" class="help_Infromation_PopUp" href="javascript:;"><img src="<c:url value="/images/i-icon.png"/>"></a>
                                    </div>
                                </div>
                                <!-- start by rohit on 29-04-15 -->
                                <div class="clearfix"></div>
                                <div class="ques">
                                    <div class="col-lg-4 col-md-12 col-sm-12 padding-left-none">
                                        <span class="filled_question">Do you have on-farm storage? </span>
                                    </div>
                                    <c:choose>
                                        <c:when test="${model.farmInfoView.evaluate_storage}">
                                            <div class="col-lg-2 col-md-3 col-sm-3 padding-left-none option_selection">
                                                <label> <input type="radio" name="evaluate_storage_sales" value="false"><span>No</span></label>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-3 option_selection">
                                                <label> <input type="radio" name="evaluate_storage_sales" checked="checked" value="true"><span>Yes</span></label>
                                            </div>
                                            <div class="col-lg-3 col-md-4 col-sm-4 option_selection">
                                                <label></label>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div
                                                class="col-lg-2 col-md-3 col-sm-3 padding-left-none option_selection">
                                                <label> <input type="radio"
                                                    name="evaluate_storage_sales" checked="checked"
                                                    value="false"><span>No</span></label>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-3 option_selection">
                                                <label> <input type="radio"
                                                    name="evaluate_storage_sales" value="true"><span>Yes</span></label>
                                            </div>
                                            <div class="col-lg-3 col-md-4 col-sm-4 option_selection">
                                                <label></label>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="col-lg-1 col-md-2 col-sm-2 option_selection">
                                        <a id="farm_Information_storage"
                                            class="help_Infromation_PopUp" href="javascript:;"><img
                                            src="<c:url value="/images/i-icon.png"/>"></a>
                                    </div>
                                </div>
                                <!-- end by rohit on 29-04-15 -->

                                <div class="clearfix"></div>
                                <div class="ques">
                                    <div class="col-lg-4 col-md-12 col-sm-12 padding-left-none">
                                        <span class="filled_question">Would you like to
                                            evaluate forward sales?</span>
                                    </div>

                                    <c:choose>
                                        <c:when
                                            test="${model.farmInfoView.evaluate_forward_sales eq 'true'}">
                                            <div
                                                class="col-lg-2 col-md-3 col-sm-3 padding-left-none option_selection">
                                                <label> <input type="radio"
                                                    name="evaluate_forward_sales" value="false"><span>No</span></label>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-3 option_selection">
                                                <label> <input type="radio"
                                                    name="evaluate_forward_sales" value="true"
                                                    ${model.farmInfoView.evaluate_forward_sales eq 'true'?'checked':''}><span>Yes</span></label>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div
                                                class="col-lg-2 col-md-3 col-sm-3 padding-left-none option_selection">
                                                <label> <input type="radio"
                                                    name="evaluate_forward_sales" value="false"
                                                    checked="checked"><span>No</span></label>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-3 option_selection">
                                                <label> <input type="radio"
                                                    name="evaluate_forward_sales" value="true"><span>Yes</span></label>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="col-lg-3 col-md-4 col-sm-4 option_selection">
                                        <label></label>
                                    </div>
                                    <div class="col-lg-1 col-md-2 col-sm-2 option_selection">
                                        <a id="farm_Information_forward"
                                            class="help_Infromation_PopUp" href="javascript:;"><img
                                            src="<c:url value="/images/i-icon.png"/>"></a>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <div class="ques">
                                    <div class="col-lg-4 col-md-12 col-sm-12 padding-left-none">
                                        <span class="filled_question">Would you like to
                                            evaluate crop insurance?</span>
                                    </div>
                                    <c:choose>
                                        <c:when
                                            test="${model.farmInfoView.evaluate_crop_insurance eq 'true'}">
                                            <div
                                                class="col-lg-2 col-md-3 col-sm-3  padding-left-none option_selection">
                                                <label> <input type="radio"
                                                    name="evaluate_crop_insurance" value="false"><span>No</span></label>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-3 option_selection">
                                                <label> <input type="radio"
                                                    name="evaluate_crop_insurance" value="true"
                                                    checked="checked"><span>Yes</span></label>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div
                                                class="col-lg-2 col-md-3 col-sm-3  padding-left-none option_selection">
                                                <label> <input type="radio"
                                                    name="evaluate_crop_insurance" value="false"
                                                    checked="checked"><span>No</span></label>
                                            </div>
                                            <div class="col-lg-2 col-md-3 col-sm-3 option_selection">
                                                <label> <input type="radio"
                                                    name="evaluate_crop_insurance" value="true"><span>Yes</span></label>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="col-lg-3 col-md-4 col-sm-4 option_selection">
                                        <label></label>
                                    </div>
                                    <div class="col-lg-1 col-md-2 col-sm-2 option_selection">
                                        <a id="farm_Information_insurance"
                                            class="help_Infromation_PopUp" href="javascript:;"><img
                                            src="<c:url value="/images/i-icon.png"/>"></a>
                                    </div>
                                </div>
                                <div class="clearfix"></div>--%>


                                <div class="yellobtn pre_next">
                                    <a id="submit popup" onclick="nextFarmInformation()"> <!-- onclick="div_show3()" -->
                                        Next
                                    </a>
                                </div>
                                <!-- <div class="yellobtn pre_next">
<a id="submit" href="javascript:;">Previous</a>
</div> -->
                            </div>
                            <!-- Popup Div Starts Here -->
                            <div id="select-strategies-pop-up"
                                 class="common-pop-class-for-all">
                                <div id="popupContact">
                                    <!-- Planning Form -->
                                    <div class="popup_section">
                                        <div class="popupform messagepopup">
                                            <p class="planningtext">You indicated that you would like
                                                to plan by both fields and total acres (i.e. without regard
                                                to fields). To plan by fields you will need to enter
                                                information on your farm's fields.</p>
                                            <p>Would you like to begin planning by fields or acres?</p>
                                            <label> <input type="radio" name="plan_by_farm_name"
                                                           value="fields"><span
                                                    class="planninghead">Fields</span></label>
                                            <label> <input type="radio" name="plan_by_farm_name"
                                                           value="acres"><span class="planninghead">Acres</span></label>
                                            <div class="clearfix">
                                                <div class="yellobtn submit okbutton">
                                                    <a id="submit" onclick="selectStrategyInCaseOfBoth()">
                                                        <!-- onclick="showMyNextPage()" -->Ok
                                                    </a>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>


                            </div>
                            <!-- Popup Div Ends Here -->
                        </div>
                        <!-- @end #farm-info -->
                        <div id="plan_by_fields" class="show_hide_class hidden">
                            <div class="form_area">
                                <h2 class="field-heading">
                                    <%--Plan by Fields --%>
                                    Field Information
                                    <a id="Plan_by_Fields" class="help_Infromation_PopUp" href="javascript:;">
                                        <img src="<c:url value="/images/i-icon.png"/>">
                                    </a>
                                    <span id="land_acres_planningbyfield"></span>
                                </h2>
                                <div class="clearfix"></div>
                                <div style="margin-bottom: 8px;" class="all_buttons">
                                    <div class="top_buttons addremove-field">
                                        <a onclick="div_show4ForAddField()"><img
                                                src="<c:url value="/images/add_field.png"/>"></a>
                                    </div>
                                    <div class="top_buttons addremove-field">
                                        <a onclick="modifyExistingField()"><img
                                                src="<c:url value="/images/modify_field.png"/>"></a>
                                    </div>
                                    <div class="top_buttons addremove-field padding-left-none">
                                        <a onclick="removeField()"><img src="<c:url value="/images/remove.png"/>"></a>
                                    </div>
                                </div>
                                <!-- <div class="col-lg-5 col-md-2 col-sm-2 text-left"></div>
<div class="col-lg-2 col-md-3 col-sm-3  addremove-field"><a onclick="addNewFieldValidation();div_show4()"><img src="images/add_field.png"></a></div>
<div class="col-lg-2 col-md-3 col-sm-3  addremove-field"><a onclick="modifyExistingField()"><img src="images/modify.png"></a></div>
<div class="col-lg-3 col-md-4 col-sm-4  add-field padding-right-none"><a onclick="removeField()"><img src="images/remove.png"></a></div> -->
                                <div class="clearfix"></div>


                                <div class="ques">
                                    <div class="table-responsive">
                                        <table id="Plan_by_Fields_table" class="table table-striped tbl-bordr tbl-fixd-hdr tbl-fixd-hdr-manag tblbrdr scroll" cellspacing="0" width="100%">
                                            <thead id="Plan_by_Fields_thead" style="display: table-header-group;">
                                                <tr class="tblheader text-center add-fieldi">
                                                    <td>Modify</td>
                                                    <td class="tblbrdr text-center add-fieldi">Name</td>
                                                    <td class="text-center add-fieldi">Size (In Acres) </td>
                                                    <td class="text-center">Last Crop
                                                        <a id="Plan_by_Fields_Last_Crop" class="help_Infromation_PopUp" href="javascript:;"><span class="add-fieldi"><img src="<c:url value="/images/i-img.png"/>"></span></a>
                                                    </td>
                                                    <td class="text-center">Fallow
                                                        <a id="Plan_by_Fields_Fallow" class="help_Infromation_PopUp" href="javascript:;"><span class="add-fieldi"><img src="<c:url value="/images/i-img.png"/>"></span></a>
                                                    </td>
                                                    <td class="text-center">Divide
                                                        <a id="Plan_by_Fields_Divide" class="help_Infromation_PopUp" href="javascript:;"><span class="add-fieldi"><img src="<c:url value="/images/i-img.png"/>"></span></a>
                                                    </td>
                                                    <td class="text-center">Irrigate
                                                        <a id="Plan_by_Fields_Irrigate" class="help_Infromation_PopUp" href="javascript:;"><span class="add-fieldi"><img src="<c:url value="/images/i-img.png"/>"></span></a>
                                                    </td>
                                                </tr>
                                            </thead>
                                            <tbody id="plan-by-field-tbody" class="scrollbar-dynamic scrollDiv" style="display: table-row-group;">

                                            <!--          create field dynamically get field information from FarmInfoView list -->

                                            <c:set var="rowCount" value="1"/>
                                            <c:set var="totalSize" value="0"/>
                                            <c:forEach var="fieldList" items="${model.fieldInfoList}">
                                                <!--get crop list from fieldInfoList View object -->
                                                <tr class="success tblgrn text-center column-left">
                                                    <td class="pull-left"><input id="row-field-manage_checkbox__${rowCount}"
                                                               type="checkbox" class="fields"></td>
                                                    <td id="row-field-name__${rowCount}" class="pull-left">${fieldList.fieldName}</td>
                                                    <td id="row-field-size__${rowCount}" class="pull-left">${fieldList.fieldSizeStr}</td>
                                                    <td class="pull-left"><select onchange="lastCropSelected(this)"
                                                                id="selected_last_crop____${rowCount}">
                                                        <option value="No Crop">No Crop</option>
                                                        <c:forEach var="cropList" items="${model.cropTypeView}">
                                                            <c:if test="${cropList.selected}">
                                                                <option value="${cropList.cropName}"
                                                                    ${cropList.cropName eq fieldList.lastCrop ? 'selected' : ''}>${cropList.cropName}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select></td>
                                                    <td class="pull-left"><input name="field-follow__${rowCount}"
                                                               id="field-follow__${rowCount}" type="checkbox"
                                                               onchange="fallowEnabledOrDisabled(this)" value="true"
                                                        ${fieldList.fallow ?'checked':''}></td>
                                                    <td class="pull-left"><input name="field-divide__${rowCount}"
                                                               id="field-divide__${rowCount}" type="checkbox"
                                                               value="true" ${fieldList.divide ?'checked':''}></td>
                                                    <td class="pull-left"><input name="field-irrigate__${rowCount}"
                                                               id="field-irrigate__${rowCount}" type="checkbox"
                                                               value="true" ${fieldList.irrigate ?'checked':''}></td>
                                                    <c:set var="totalSize"
                                                           value="${totalSize+fieldList.fieldSize}"/>
                                                </tr>
                                                <c:set var="rowCount" value="${rowCount+1}"/>
                                            </c:forEach>

                                            </tbody>
                                            <tfoot>
                                            <tr id="total-field-last-row" class="tblft text-center">
                                                <td class="tblft1">Total acres</td>
                                                <td style="text-align: left" colspan="6"
                                                    id="total-acres-value"><fmt:formatNumber
                                                        type="number" maxFractionDigits="2" value="${totalSize}"/>

                                                </td>
                                            </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <div class="yellobtn pre_next">
                                    <a id="submit popup" onclick="nextPlanByField()">
                                        <!-- onclick="showMyNextPage1()" -->Next
                                    </a>
                                </div>
                                <div class="yellobtn pre_next">
                                    <a id="submit"
                                       onclick="callMethodForPageChangeAndProgressBarImage(3,4)">Previous</a>
                                </div>
                                <div id="add-new-crop-pop-up" class="common-pop-class-for-all">
                                    <div id="popupContact">
                                        <!-- Planning Form -->
                                        <div class="popup_section">
                                            <img onclick="div_hide4()" src="<c:url value="/images/cross.png"/>"
                                                 id="close">
                                            <h2
                                                    style="padding: 0 0 0 22px; color: #BE922F; font-weight: bold;"
                                                    class="popupheadercrop">
                                                <span id="add-field-span-dynamic">Add Field</span>
                                            </h2>
                                            <div style="padding: 5px 20px;"
                                                 class="popupform messagepopup">
                                                <div class="clearfix"></div>
                                                <div class="addcroppup">
                                                    <label>Field Name</label> <input type="text"
                                                                                     id="pop-up-field-name"/>
                                                </div>
                                                <div class="addcroppup">
                                                    <label>Field Size (In Acres)</label> <input type="text"
                                                                                                id="pop-up-field-size"
                                                                                                onchange="addCommaSignWithOutDollarDot(this)"
                                                                                                onkeypress="return isValidNumberValueForWithOutDot(event)"/>
                                                </div>
                                                <div class="clearfix"></div>
                                                <div style="margin: 10px 0px;" class="yellobtn submit"
                                                     id="add_field_button">
                                                    <a id="submit" onclick="addNewField()">Save</a>
                                                </div>
                                                <div style="margin: 10px 0px;" class="yellobtn submit"
                                                     id="add_field_button_new">
                                                    <a id="submit" onclick="addMultiNewField()">Save & New</a>
                                                </div>
                                                <div style="margin: 10px 0px;" class="yellobtn submit"
                                                     id="modify_field_button">
                                                    <a id="submit" onclick="modifyField()">Update Field</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- @end #plan_by_fields -->
                        <div id="planbyacres" class="show_hide_class hidden">

                            <div class="form_area">
                                <h2 class="field-heading">
                                    Plan by Acres <span><a id="plan_by_acres"
                                                           class="help_Infromation_PopUp" href="javascript:;"><img
                                        src="<c:url value="/images/i-icon.png"/>"></a></span>
                                </h2>
                                <div class="clearfix"></div>
                                <div class="ques">
									<span class="plant">Available land for planting:
                                        <!-- update By Bhagvan Singh on 11-04-2015 start -->
										<input type="text" id="acres_value"
                                               maxlength="8"
                                               onkeypress="return isValidNumberValueForWithOutDot(event)"
                                               onchange="addCommaSignForAcres(this);onChangeOfAcresWhenPlanningByAcre(this)"
                                               value="${model.farmInfoView.landStr}"/>
											&nbsp;&nbsp;<span> Acres</span>
                                        <!--                     end -->
                                        <!-- <select reqired>
                      <option>Acres</option>
                    </select> --></span>
                                </div>
                                <div class="ques_FieldToAcres" id="contentBox_FieldToAcres"></div>
                                <div class="clearfix"></div>
                                <div class="yellobtn pre_next">
                                    <a id="submit" onclick="nextPlanByAcres()">Next</a>
                                </div>
                                <div class="yellobtn pre_next">
                                    <a id="submit"
                                       onclick="callMethodForPageChangeAndProgressBarImage(1,1)">Previous</a>
                                </div>
                            </div>
                        </div>
                        <!-- @end #planbyacres -->
                        <div id="crop_cropinfo" class="show_hide_class hidden">
                            <div class="form_area">
                                <h2 class="field-heading"> Crops and Crop Information
                                    <a id="Crops_and_Crop_Information" class="help_Infromation_PopUp"
                                       href="javascript:;"><img src="<c:url value="/images/i-icon.png"/>"></a>
                                </h2>
                                <div class="clearfix"></div>

                                <div class="all_buttons" style="margin-bottom: 8px;">
                                    <div class="top_buttons addremove-field">
                                        <a onclick="div_show5()"><img src="<c:url value="/images/add-crop.png"/>"></a>
                                    </div>
                                    <div class="top_buttons addremove-field padding-left-none">
                                        <a onclick="removeCrops()"><img src="<c:url value="/images/remove-crops.png"/>"></a>
                                    </div>
                                    <div class="top_buttons addremove-field padding-left-none">
                                        <a onclick="selectAllContacts()"><img id="select-unselect-img"
                                                                              src="<c:url value="/images/select_all.png"/>"></a>
                                    </div>
                                    <a id="Crops_and_Crop_Information_add" class="help_Infromation_PopUp"
                                       href="javascript:;"><img src="<c:url value="/images/i-icon.png"/>"></a>
                                </div>

                                <!---------------------start tab------------------------------->
                                <div class="addcrop">
                                    <p class="addcroptext">Select the crops you are considering planting.</p>
                                    <div class="clearfix"></div>
                                    <div id="tabs-container">
                                        <ul class="tabs-menu list-inline">
                                            <li class="current"><a href="#tab-1">Field Crops</a></li>
                                            <li><a href="#tab-2">Vegetable/Fruit Crops</a></li>
                                        </ul>
                                        <div class="tab">

                                            <!--         change start -->

                                            <div id="tab-1" class="tab-content">
                                                <div class="ques">
                                                    <div
                                                            class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">
                                                        <ul class="list-unstyled text-left" id="crop_normal">
                                                            <c:set var="isFountFieldCrop" value="0"/>
                                                            <c:forEach var="farm" items="${model.cropTypeView}">
                                                                <!--get crop list from CropView object -->
                                                                <c:if test="${farm.cropTypeName eq 'Field_Crop'}">
                                                                    <li class="col-lg-4 col-md-4 col-sm-6 padding-left-none">
                                                                        <img src="<c:url value="/images/cropimg.png"/>">
                                                                        &nbsp;&nbsp;
                                                                        <label class="labelForCrops">
                                                                            <input type="checkbox" name="field_crop[]"
                                                                                   onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();"
                                                                                   class="crops"
                                                                                   value="${farm.cropName}"
                                                                                ${farm.selected eq 'true'?'checked':''}>&nbsp;&nbsp;
                                                                            <span>${farm.cropName}</span>
                                                                        </label>
                                                                    </li>
                                                                </c:if>
                                                            </c:forEach>
                                                        </ul>
                                                    </div>
                                                </div>

                                            </div>

                                            <!--         change end -->
                                            <div id="tab-2" class="tab-content">
                                                <div class="ques">
                                                    <div
                                                            class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">
                                                        <ul class="list-unstyled text-left" id="crop_vegitable">
                                                            <c:set var="isFountVegitableCrop" value="0"/>
                                                            <c:forEach var="farm" items="${model.cropTypeView}">
                                                                <!--get crop list from CropView object -->
                                                                <c:if test="${farm.cropTypeName eq 'Vegitable_Crop'}">
                                                                    <li class="col-lg-4 col-md-4 col-sm-6 padding-left-none">
                                                                        <img src="<c:url value="/images/cropimg.png"/>">
                                                                        &nbsp;&nbsp;
                                                                        <label class="labelForCrops">
                                                                            <input type="checkbox"
                                                                                   name="vegitable_crop[]"
                                                                                   onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();"
                                                                                   class="crops"
                                                                                   value="${farm.cropName}"
                                                                                ${farm.selected eq 'true'?'checked':''}>&nbsp;&nbsp;
                                                                            <span>${farm.cropName}</span>
                                                                        </label>
                                                                    </li>
                                                                </c:if>
                                                            </c:forEach>
                                                        </ul>
                                                    </div>

                                                </div>

                                            </div>


                                        </div>
                                    </div>
                                </div>
                                <!--------------end tab------------------------->


                                <div class="yellobtn pre_next">
                                    <a id="submit popup" onclick="nextCropsAndCropsInformation()">Next</a>
                                </div>
                                <div class="yellobtn pre_next">
                                    <a id="submit"
                                       onclick="callMethodForPageChangeAndProgressBarImage(1,2)">Previous</a>
                                </div>
                                <!-- Popup Div Starts Here -->
                                <div id="add-new-crop-and-crop-info"
                                     class="common-pop-class-for-all">
                                    <div id="popupContact">
                                        <!-- Planning Form -->
                                        <div class="popup_section">
                                            <img id="close" src="<c:url value="/images/cross.png"/>"
                                                 onclick="div_hide5()">
                                            <h2 class="popupheadercrop"
                                                style="padding: 0 0 0 22px; color: #BE922F; font-weight: bold;">Add
                                                Crop</h2>
                                            <div class="popupform messagepopup"
                                                 style="padding: 5px 20px;">
                                                <%--<p class="addcroppopuptext">Choose area where you want to add Crop :</p>--%>
                                                <p class="addcroppopuptext">Choose the type of crop to add:</p>
                                                <div class="clearfix"></div>
                                                <label class="labelForCrops"><input type="radio"
                                                                                    name="crop_type"
                                                                                    value="Field Crops"><span
                                                        class="planninghead">Field Crop</span></label> <label
                                                    class="labelForCrops"><input type="radio"
                                                                                 name="crop_type"
                                                                                 value="Vegetable or Fruit Crops"
                                                                                 class="planningradio"><span
                                                    class="planninghead">Vegetable/Fruit
														Crop</span></label>
                                                <div class="clearfix"></div>
                                                <div class="addcroppup">
                                                    <label>Crop Name</label> <input type="text" id="crop_name"
                                                                                    name="Crop Name">
                                                </div>
                                                <div class="clearfix"></div>
                                                <div class="yellobtn submit" style="margin: 10px 0px;">
                                                    <a onclick="addNewCrop()" id="submit">Add Crop</a>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                                <!-- Popup Div Ends Here -->


                            </div>
                        </div>
                        <!-- @end #crop_cropinfo -->
                        <!-- @end #add_crop -->
                        <div id="cropinfodetail" class="show_hide_class hidden">
                            <div class="form_area">
                                <h2 class="info-heading">Crop Information Details</h2>

                                <div class="clearfix"></div>
                                <div class="info">Enter the information for each crop.</div>
                                <!--	@changed - Abhishek		@date - 25-11-2015 	 -->
                                <div class="clearfix"></div>
                                <div>* Required</div>
                                <div class="clearfix"></div>
                                <div class="pull-right">
                                    <%--<label>Vary price and yield between Min and Max values to compute expected profit per acre--%>
                                    <label>Check the box to vary price and yield to compute Estimated Income
                                        <input type="checkBox" name="montyCarloSwitch"
                                        ${model.farmInfoView.montyCarloStatus == true ? 'checked' : ''}
                                               onchange="enableDisableMontyCarloAnalysis(); return false;">
                                    </label>
                                    <a id="montyCarloSwitch" class="help_Infromation_PopUp" href="javascript:;"><img
                                            src="<c:url value="/images/i-icon.png"/>"></a>
                                </div>
                                <div class="clearfix"></div>

                                <div class="ques">
                                    <div class="table-responsive" style="max-height: 463px;"> <%----%>
                                        <table class="table table-striped tbl-bordr overflow-x tblbrdr table-fixed"
                                               cellspacing="0" width="100%" id="cropInformationDetailFirstTable">
                                            <thead>
                                            <tr class="tblhd text-center add-fieldi">
                                                <td class="tblbrdr text-center add-fieldi">Crop</td>
                                                <td class="text-center add-fieldi uom_width">UoM</td>
                                                <td class="text-center" colspan="3">
                                                    <a id="Crop_Information_Details_yield"
                                                       class="help_Infromation_PopUp" href="javascript:;">
                                                        <span class="add-fieldi">Yields (UoM/acre) <img
                                                                src="<c:url value="/images/i-img.png"/>"></span>
                                                    </a><br>
                                                    <span class="infosubhead expected_range">Expected*</span>
                                                    <span class="infosubhead max_range">Max</span>
                                                    <span class="infosubhead min_range">Min</span></td>
                                                <td class="text-center" colspan="3"><a
                                                        id="Crop_Information_Details_price"
                                                        class="help_Infromation_PopUp" href="javascript:;"><span
                                                        class="add-fieldi">Prices ($/UoM) <img
                                                        src="<c:url value="/images/i-img.png"/>"></span></a><br>
                                                    <span class="infosubhead expected_range">Expected*</span> <span
                                                            class="infosubhead max_range">Max</span> <span
                                                            class="infosubhead min_range">Min</span></td>
                                                <td class="text-center">Variable Production Costs <span
                                                        class="infosubhead">($/acre)*</span><span><a
                                                        id="variable_production_cost"
                                                        class="help_Infromation_PopUp" href="javascript:;"><img
                                                        src="<c:url value="/images/i-icon.png"/>"></a></span>
                                                </td>
                                                <td class="text-center">Est. Income <span
                                                        class="infosubhead">($/acre)</span><span><a
                                                        id="profit_per_acre" class="help_Infromation_PopUp"
                                                        href="javascript:;"><img
                                                        src="<c:url value="/images/i-icon.png"/>"></a></span>
                                                </td>
                                            </tr>

                                            </thead>
                                            <tbody id="crop_information_tbody" class="scrollDiv">

                                            <!--         start update by Bhagvan Singh on 06-04-2015-->
                                            <c:set var="rowCount" value="1"/>
                                            <c:forEach var="farm" items="${model.cropTypeView}">
                                                <!--get crop list from CropView object -->
                                                <c:if test="${farm.selected}">
                                                    <tr class="tblbclgrnd text-center"
                                                        id="crop_info_details__${rowCount}">
                                                        <td class="tblft1"
                                                            id="crop_info_details_field_crop_name__${rowCount}">${farm.cropName}</td>
                                                        <td class="success uomtext">
                                                            <select onchange="changeCropUOM(this)"
                                                                    class="crop_selection_UOM">
                                                                <option value="bushels" ${farm.cropUOM eq 'bushels'?'selected':''}>
                                                                    bushels
                                                                </option>
                                                                <option value="crates" ${farm.cropUOM eq 'crates'?'selected':''}>
                                                                    crates
                                                                </option>
                                                                <option value="hundredweight" ${farm.cropUOM eq 'hundredweight'?'selected':''}>
                                                                    hundredweight
                                                                </option>
                                                                <option value="kilograms" ${farm.cropUOM eq 'kilograms'?'selected':''}>
                                                                    kilograms
                                                                </option>
                                                                <option value="pounds" ${farm.cropUOM eq 'pounds'?'selected':''}>
                                                                    pounds
                                                                </option>
                                                                <option value="tons" ${farm.cropUOM eq 'tons'?'selected':''}>
                                                                    tons
                                                                </option>
                                                                <option value="sacks" ${farm.cropUOM eq 'sacks'?'selected':''}>
                                                                    sacks
                                                                </option>
                                                                <option value="bales" ${farm.cropUOM eq 'bales'?'selected':''}>
                                                                    bales
                                                                </option>
                                                            </select>
                                                        </td>
                                                        <td class="success infotext">
                                                            <input type="text"
                                                                   onchange="addCommaSignWithForOnePoint(this);changeExpectedYieldValue(this);calculateProfitByCrop(this);"
                                                                   onkeypress="return isValidNumberValue(event)"
                                                                   id="crop_info_yields_expected__${rowCount}"
                                                                   name="Crop"
                                                                   value="${farm.intExpCropYieldStr}">
                                                        </td>
                                                        <td class="success infotext"><input
                                                                onchange="addCommaSignWithForOnePoint(this);changeMaximumYieldValue(this);calculateProfitByCrop(this)"
                                                                onkeypress="return isValidNumberValue(event)"
                                                                id="crop_info_yields_max__${rowCount}" type="text"
                                                                name="Crop"
                                                                value="${farm.intMaxCropYieldStr}"></td>
                                                        <td class="success infotext"><input type="text"
                                                                                            onchange="addCommaSignWithForOnePoint(this);changeMinimumYieldValue(this);calculateProfitByCrop(this)"
                                                                                            onkeypress="return isValidNumberValue(event)"
                                                                                            id="crop_info_yields_min__${rowCount}"
                                                                                            name="Crop"
                                                                                            value="${farm.intMinCropYieldStr}">
                                                        </td>
                                                        <td class="success infotext"><input
                                                                onchange="addCommaSignWithDollar(this);calculateProfitByCrop(this)"
                                                                onkeypress="return isValidNumberValue(event)"
                                                                id="crop_info_price_expected__${rowCount}" type="text"
                                                                name="Crop"
                                                                value="${farm.intExpCropPriceString}"></td>
                                                        <td class="success infotext"><input type="text"
                                                                                            onchange="addCommaSignWithDollar(this);calculateProfitByCrop(this)"
                                                                                            onkeypress="return isValidNumberValue(event)"
                                                                                            id="crop_info_price_max__${rowCount}"
                                                                                            name="Crop"
                                                                                            value="${farm.intMaxCropPriceString}">
                                                        </td>
                                                        <td class="success infotext"><input type="text"
                                                                                            onchange="addCommaSignWithDollar(this);calculateProfitByCrop(this)"
                                                                                            onkeypress="return isValidNumberValue(event)"
                                                                                            id="crop_info_price_min__${rowCount}"
                                                                                            name="Crop"
                                                                                            value="${farm.intMinCropPriceString}">
                                                        </td>
                                                        <td class="success infotext">
                                                            <input type="text"
                                                                   onchange="addCommaSignWithDollar(this);variableProductionCostChange(this);calculateProfitByCrop(this)"
                                                                   placeholder="$0"
                                                                   onkeypress="return isValidNumberValue(event)"
                                                                   id="calulated_cost_of_production__${farm.cropNameForId}"
                                                                   value="${farm.calculatedVariableProductionCostFloat ne '' ? farm.calculatedVariableProductionCostFloat : '$0.00'}">
                                                            <br> <span class="pull-right"><a
                                                                onclick="showOptionalCropInformationDiv('${farm.cropName}')">Details</a></span>
                                                        </td>
                                                        <td class="success infotext"
                                                            onmouseover="showPotentialProfitCriticalMessagePopup(this);">
                                                            <c:set scope="page" value="" var="p_profit"/>
                                                            <c:choose>
                                                                <c:when test="${farm.calculatedProfitFloatPrimitiveType le 0.0}">
                                                                    <c:set scope="page" var="p_profit"
                                                                           value="-$${cfun:absFloat(farm.calculatedProfitFloatPrimitiveType)}"/>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:set scope="page" var="p_profit"
                                                                           value="$${farm.calculatedProfitFloatPrimitiveType}"/>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <input type="text" disabled="disabled" placeholder="$0"
                                                                   value="${farm.calculatedProfitFloat ne '' ? p_profit : '$0.00'}"
                                                                   class="<c:if test="${farm.calculatedProfitFloat ne '' and farm.calculatedProfitFloatPrimitiveType le 0.0}">errorClassWithRedColor</c:if>"/>
                                                            <span class="text-center"
                                                                  style="color: grey;font-size: small;">
																	<c:if test="${farm.additionalIncome ne 0.0}">
                                                                        ${farm.calculatedProfitString ne '' ? p_profit : '$0.00'} + ${farm.additionalIncomeString}
                                                                    </c:if>
																</span>
                                                        </td>

                                                    </tr>

                                                    <c:set var="rowCount" value="${rowCount+1}"/>
                                                </c:if>
                                            </c:forEach>

                                            <!--         start end -->

                                            </tbody>
                                        </table>
                                        <span class="pull-right">** Additional net income</span>
                                    </div>
                                </div>
                                <div class="clearfix"></div>

                                <div class="panel-group" id="accordion">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <a class="remove-text-deco" data-toggle="collapse" data-parent="#accordion"
                                               href="#collapseOptionalCropInfo">
                                                <h4 class="panel-title">
                                                    Optional crop information <i
                                                        class="fa fa-chevron-down pull-right"></i>
                                                </h4>
                                            </a>
                                        </div>
                                        <div id="collapseOptionalCropInfo" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <div class="table-responsive" style="max-height: 225px;">
                                                    <table id="cropInformationDetailSecondTable"
                                                           class="table table-striped tbl-bordr  tblbrdr"
                                                           cellspacing="0" width="100%">
                                                        <thead>
                                                        <tr class="tblhd text-center add-fieldi">
                                                            <td class="tblbrdr text-center add-fieldi">Crop</td>
                                                            <!--	@changed - Abhishek		@date - 30-12-2015 -->
                                                            <td class="text-center add-fieldi hidden">Irrigated</td>
                                                            <td class="text-center">Conservation Practice <a
                                                                    id="Conservation_Practice"
                                                                    class="help_Infromation_PopUp"
                                                                    href="javascript:;"><img
                                                                    src="<c:url value="/images/i-icon.png"/>"></a></td>
                                                            <td class="text-center">Hi-Risk Crop <a id="hiRisk_crop"
                                                                                                    class="help_Infromation_PopUp"
                                                                                                    href="javascript:;"><img
                                                                    src="<c:url value="/images/i-icon.png"/>"></a></td>
                                                        </tr>
                                                        </thead>
                                                        <tbody id="optional_crop_info_tbody">

                                                        <!--         start update by Bhagvan Singh-->
                                                        <c:set var="rowCount" value="1"/>
                                                        <c:forEach var="farm" items="${model.cropTypeView}">
                                                            <!--get crop list from CropView object -->
                                                            <c:if test="${farm.selected}">
                                                                <tr class="tblgrn text-center"
                                                                    id="optional_crop_info__${rowCount}">
                                                                    <td class="tblft1"
                                                                        id="optional_crop_info_field_crop_name__${rowCount}">${farm.cropName}</td>
                                                                    <!--	@changed - Abhishek		@date - 30-12-2015 -->
                                                                        <%--<td class="success"><input type="checkbox" id="optional_crop_info_irrigated__${rowCount}" value="true" ${listValue.irrigated eq 'true'?'checked':''} /></td>--%>
                                                                    <td class="success hidden"><input type="checkbox"
                                                                                                      id="optional_crop_info_irrigated__${rowCount}"
                                                                                                      value="true" ${farm.irrigated eq 'true'?'checked':''} />
                                                                    </td>
                                                                    <td class="success"><input type="checkbox"
                                                                                               id="optional_crop_info_conservation_practice__${rowCount}"
                                                                                               value="true" ${farm.conservation_Crop eq 'true'?'checked':''} />
                                                                    </td>
                                                                    <td class="success"><input type="checkbox"
                                                                                               id="optional_crop_info_hi_risk_crop__${rowCount}"
                                                                                               value="true" ${farm.hiRiskCrop eq 'true'?'checked':''} />
                                                                    </td>
                                                                </tr>
                                                                <c:set var="rowCount" value="${rowCount+1}"/>
                                                            </c:if>
                                                        </c:forEach>

                                                        <!--         start end -->

                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <a class="remove-text-deco" data-toggle="collapse" data-parent="#accordion"
                                               href="#collapseOptionalPlanningDate">
                                                <h4 class="panel-title">
                                                    Optional planting dates (for cashflow analysis) <i
                                                        class="fa fa-chevron-down pull-right"></i>
                                                </h4>
                                            </a>
                                        </div>
                                        <div id="collapseOptionalPlanningDate" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <div class="table-responsive" style="max-height: 225px;">
                                                    <table id="cropInformationDetailThirdTable"
                                                           class="table table-striped tbl-bordr  tblbrdr hidden"
                                                           cellspacing="0" width="100%">
                                                        <thead>
                                                        <tr class="tblhd text-center add-fieldi">
                                                            <td class="tblbrdr text-center add-fieldi">Crop</td>
                                                            <td class="text-center add-fieldi">Preferred Planting Date
                                                            </td>
                                                            <td class="text-center">Early Planting Date</td>
                                                            <td class="text-center">Late Planting Date</td>
                                                            <td class="text-center">Length of Season(Days)</td>
                                                        </tr>
                                                        </thead>
                                                        <tbody id="optional_planting_date">
                                                        <c:set var="rowCount" value="1"/>
                                                        <c:forEach var="farm" items="${model.cropTypeView}">
                                                            <c:if test="${farm.selected}">
                                                                <tr class="tblgrn text-center"
                                                                    id="optional_planting_date_row__${rowCount}">
                                                                    <td class="tblft1"
                                                                        id="optional_planting_date_field_crop_name__${rowCount}">${farm.cropName}</td>
                                                                    <td class="success infotext"><input
                                                                            class="datepicker" type="text"
                                                                            id="optional_planting_date_preferred_planting_date__${rowCount}"
                                                                            value="${farm.preferredPlantingDateStr}"
                                                                            name="Crop"></td>
                                                                    <td class="success infotext"><input
                                                                            class="datepicker" type="text"
                                                                            value="${farm.earlyPlantingDateStr}"
                                                                            id="optional_planting_date_early_planting_date__${rowCount}"
                                                                            name="Crop"></td>
                                                                    <td class="success infotext"><input
                                                                            class="datepicker"
                                                                            value="${farm.latePlantingDateStr}"
                                                                            type="text"
                                                                            id="optional_planting_date_late_planting_date__${rowCount}"
                                                                            name="Crop"></td>
                                                                    <td class="success infotext"><input
                                                                            value="${farm.lengthofSeasonStr}"
                                                                            type="text"
                                                                            id="optional_planting_date_length_of_session_days__${rowCount}"
                                                                            name="Crop"
                                                                            onkeypress="return isValidNumberValue(event)">
                                                                    </td>
                                                                </tr>
                                                                <c:set var="rowCount" value="${rowCount+1}"/>
                                                            </c:if>
                                                        </c:forEach>

                                                        </tbody>
                                                    </table>
                                                    <table width="100%" cellspacing="0"
                                                           class="table table-striped tbl-bordr  tblbrdr">
                                                        <tbody>
                                                        <tr class="tblgrn text-center">
                                                            <%--<td class="success infotext">Under construction.  Planting dates not needed at this time.</td>--%>
                                                            <td class="success infotext">Cashflow Analysis Under
                                                                Construction
                                                            </td>
                                                        </tr>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <a class="remove-text-deco" data-toggle="collapse" data-parent="#accordion"
                                               href="#collapseAdditionlIncome">
                                                <h4 class="panel-title">
                                                    Additional Crop Income <i class="fa fa-chevron-down pull-right"></i>
                                                </h4>
                                            </a>
                                        </div>
                                        <div id="collapseAdditionlIncome" class="panel-collapse collapse">
                                            <div class="panel-body">
                                                <div class="table-responsive" style="max-height: 225px;">
                                                    <table class="table table-striped tbl-bordr  tblbrdr"
                                                           cellspacing="0" width="100%" id="">
                                                        <thead>
                                                        <tr class="tblhd text-center add-fieldi">
                                                            <td class="text-center add-fieldi">Crop Name</td>
                                                            <td class="text-center">Government Payments ($/acre)</td>
                                                            <td class="text-center">Co-products ($/acre)</td>
                                                            <td class="text-center">Add’l Variable Production Cost
                                                                ($/acre)
                                                            </td>
                                                            <td class="text-center">Add’l Income ($/acre)</td>
                                                        </tr>
                                                        </thead>
                                                        <tbody id="crop-info-additional-income-tbody">
                                                        <c:forEach var="cropTypeView" items="${model.cropTypeView}">
                                                            <c:if test="${cropTypeView.selected}">
                                                                <tr class="tblbclgrnd text-center">
                                                                    <td class="tblft1 cropNameSpecific">${cropTypeView.cropName}</td>
                                                                    <td class="success infotext">
                                                                        <input type="text"
                                                                               class="governmentPaymentsSpecific"
                                                                               onkeypress="return isValidNumberValue(event)"
                                                                               onchange="addCommaSignWithDollar(this); calculateAdditionalCropProfit(this, true); return false;"
                                                                               value="${cropTypeView.governmentPaymentsString}">
                                                                    </td>
                                                                    <td class="success infotext">
                                                                        <input type="text" class="coProductsSpecific"
                                                                               onkeypress="return isValidNumberValue(event)"
                                                                               onchange="addCommaSignWithDollar(this); calculateAdditionalCropProfit(this, true); return false;"
                                                                               value="${cropTypeView.coProductsPriceString}">
                                                                    </td>
                                                                    <td class="success infotext">
                                                                        <input type="text"
                                                                               class="additionalVariableCostSpecific"
                                                                               onkeypress="return isValidNumberValue(event)"
                                                                               onchange="addCommaSignWithDollar(this); calculateAdditionalCropProfit(this, true); return false;"
                                                                               value="${cropTypeView.additionalVariableProductionCostString}">
                                                                    </td>
                                                                    <td class="success infotext">
                                                                        <input type="text"
                                                                               class="additionalIncomeSpecific"
                                                                               onkeypress="return isValidNumberValue(event)"
                                                                               onchange="addCommaSignWithDollar(this); return false;"
                                                                               value="${cropTypeView.additionalIncomeString}"
                                                                               disabled>
                                                                    </td>
                                                                </tr>
                                                            </c:if>
                                                        </c:forEach>
                                                        </tbody>
                                                    </table>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <form onsubmit="return false" id="formBlank">
                                <div class="clearfix"></div>
                                <div class="yellobtn pre_next">
                                    <a id="submit popup" onclick="nextCropsInformationDetails()">Next</a>
                                </div>
                                <div class="yellobtn pre_next">
                                    <a id="submit"
                                       onclick="callMethodForPageChangeAndProgressBarImage(2,3)">Previous</a>
                                </div>

                            </div>
                        </div>
                        <!-- @end #cropinfodetail -->
                        <div id="optional_crop" class="show_hide_class hidden">
                            <div class="form_area">
                                <h2 class="field-heading">
                                    Optional Crop Production Costs Details
                                    <%--@changed - Abhishek 	@date - 29-12-2015		@desc - Added help information functionality--%>
                                    <%--<span><img src="<c:url value="/images/i-icon.png"/>"></span>--%>
                                    <a id="Optional_Crop_Production_Costs_Details" class="help_Infromation_PopUp"
                                       href="javascript:;"><img src="<c:url value="/images/i-icon.png"/>"></a>
                                </h2>
                                <div class="all_buttons">
                                    <div class="top_buttons addremove-field">
                                        <a id="add_production_cost_field"
                                           onclick="addProductionCostField(1)"><img
                                                src="<c:url value="/images/add-componentbutton.png"/>"></a>
                                    </div>
                                    <div class="top_buttons addremove-field">
                                        <a id="modify_production_cost_field"
                                           onclick="modifyProductionCostField(2)"><img
                                                src="<c:url value="/images/modify.png"/>"></a>
                                    </div>
                                    <div class="top_buttons add-field padding-right-none">
                                        <a id="remove_production_cost_field"
                                           onclick="removeProductionCostField(3)"><img
                                                src="<c:url value="/images/remove-component.png"/>"></a>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <div class="info">
                                    To calculate the Variable Production cost of <span id="crop_name_dynamic"></span>,
                                </div>
                                <div class="clearfix"></div>
                                <%-- @author Jyoti    @date 02-01-2017  PPT NO : 12312106 Slide no : 16--%>
                                <%--<div class="optionsub pull-left">Enter the quantity and price or
                                    cost for each cost component used to raise the crop.</div>--%>
                                <div class="optionsub pull-left">enter the quantity and cost for each input used to
                                    raise the crop.
                                </div>

                                <div class="ques" id="optional_crop_dynamic_div">

                                    <!-- Modified by Harshit Gupta 10-04-2015
Start
 -->
                                    <c:forEach var="cropList" items="${model.cropTypeView}" varStatus="loop">
                                        <!--get crop list from CropView object -->
                                        <c:if test="${cropList.selected}">
                                            <%-- <c:out value="${cropList.cropName}"></c:out> --%>
                                            <!-- forEach loop for header & footer creation crop wise -->
                                            <div class='table-responsive'
                                                 name="optionalCropInformationDetail_${cropList.cropName}"
                                                 style="display: none">
                                                <table id='newhtml'
                                                       class='table table-striped tbl-bordr  tblbrdr'
                                                       cellspacing='0' width='100%'>
                                                    <thead>
                                                    <tr class='tblhd add-fieldi'>
                                                        <td class='text-center'>Modify</td>
                                                        <td class='tblbrdr add-fieldi'>Component</td>
                                                        <td class='text-center add-fieldi'>Units per acre</td>
                                                        <td class='text-center'>$ per Unit</td>
                                                        <td class='text-center'>$ per Acre</td>
                                                    </tr>
                                                    </thead>
                                                    <tbody
                                                            id="production_cost_table_tbody__${cropList.cropNameForId}">
                                                    <!-- get components list from OptionalCropProductionCostsDetailsView object  -->
                                                    <c:set var="rowCount" value="1"/>
                                                    <c:set var="total" value=""/>
                                                    <c:set var="cropNameId" value=""/>
                                                    <c:forEach var="farm" items="${model.CropCostsDetailsList}">
                                                        <c:set var="cropNameId" value="${farm.cropName}"/>
                                                        <%-- <c:out value="${listValue.cropName}" /> --%>
                                                        <!-- forEach loop for row creation crop cost_component_row wise -->
                                                        <c:set var="total" value="${farm.calculateTotalStr}"/>
                                                        <c:if test="${cropList.id eq farm.cropId}">
                                                            <c:choose>
                                                                <c:when test="${rowCount % 2 eq '0'}">
                                                                    <!-- c:when for css alernate create -->
                                                                    <tr class="tblbclgrnd"
                                                                        id="production_cost_component_row__${rowCount}_${cropNameId}"
                                                                        class="tblbclgrnd">
                                                                        <td class="tblft1 text-center"><input
                                                                                type="checkbox"
                                                                                id="production_cost_component_manage_checkbox__${rowCount}_${cropNameId}">
                                                                        </td>
                                                                        <td
                                                                                id="production_cost_component_name__${rowCount}_${cropNameId}"
                                                                                class="tblft1 text-left">${farm.costComponentsName}</td>
                                                                        <td class="infotext"><input type="text"
                                                                                                    id="production_cost_value_1_row__${rowCount}_${cropNameId}"
                                                                                                    onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"
                                                                                                    onkeypress="return isValidNumberValue(event)"
                                                                                                    name="Crop"
                                                                                                    value="${farm.unitsPerAcreStr}"/>
                                                                        </td>
                                                                        <td class="infotext"><input type="text"
                                                                                                    id="production_cost_value_2_row__${rowCount}_${cropNameId}"
                                                                                                    onchange="getCalculateValue(this);addCommaSignWithDollar(this)"
                                                                                                    onkeypress="return isValidNumberValue(event)"
                                                                                                    name="Crop"
                                                                                                    value="${farm.unitPricesStr}"/>
                                                                        </td>
                                                                        <td
                                                                                id="production_cost_calculate_value__${rowCount}_${cropNameId}"
                                                                                class="infotext">${farm.unitTotalStr}</td>
                                                                    </tr>

                                                                </c:when>
                                                                <c:otherwise>
                                                                    <tr class="tblgrn"
                                                                        id="production_cost_component_row__${rowCount}_${cropNameId}"
                                                                        class="tblgrn">
                                                                        <td class="tblft1 text-center"><input
                                                                                type="checkbox"
                                                                                id="production_cost_component_manage_checkbox__${rowCount}_${cropNameId}">
                                                                        </td>
                                                                        <td
                                                                                id="production_cost_component_name__${rowCount}_${cropNameId}"
                                                                                class="tblft1 text-left">${farm.costComponentsName}</td>
                                                                        <td class="success infotext"><input type="text"
                                                                                                            id="production_cost_value_1_row__${rowCount}_${cropNameId}"
                                                                                                            onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"
                                                                                                            onkeypress="return isValidNumberValue(event)"
                                                                                                            name="Crop"
                                                                                                            value="${farm.unitsPerAcreStr}"/>
                                                                        </td>
                                                                        <td class="success infotext"><input type="text"
                                                                                                            id="production_cost_value_2_row__${rowCount}_${cropNameId}"
                                                                                                            onchange="getCalculateValue(this);addCommaSignWithDollar(this)"
                                                                                                            onkeypress="return isValidNumberValue(event)"
                                                                                                            name="Crop"
                                                                                                            value="${farm.unitPricesStr}"/>
                                                                        </td>
                                                                        <td
                                                                                id="production_cost_calculate_value__${rowCount}_${cropNameId}"
                                                                                class="success infotext">${farm.unitTotalStr}</td>
                                                                    </tr>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <c:set var="rowCount" value="${rowCount+1}"/>
                                                        </c:if>
                                                    </c:forEach>
                                                    </tbody>
                                                    <tfoot>
                                                    <tr class="tblgrn">
                                                        <td class="tblft1 optncal" colspan="2">Calculate</td>
                                                        <td class="success"></td>
                                                        <td class="success"></td>
                                                        <td class="success"></td>
                                                    </tr>
                                                    <tr class="tblft text-center">
                                                        <td class="tblft1" colspan="2">Total Variable Cost per Acre:
                                                        </td>
                                                        <td><input type="hidden"
                                                                   id="hidden_id_for_production_cost_row_${loop.index}"
                                                                   value="${cropList.cropNameForId}" class="tblgrn">
                                                        </td>
                                                        <td></td>
                                                        <td class="tblgrn"
                                                            id="production_cost_calculate_total_${cropList.cropNameForId}">${cropList.calculatedVariableProductionCostFloat}</td>
                                                    </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                    <!-- details end-->
                                    <!-- End 10-04-2015 by Harshit Gupta -->

                                </div>
                                <div class="clearfix"></div>
                                <div class="yellobtn pre_next">
                                    <a id="submit popup"
                                       onclick="setTotalOfOptionalCropInformationToVariableProductionCost()">OK</a>
                                </div>
                                <!-- <div class="yellobtn pre_next">
<a id="submit" href="javascript:;">Previous</a>
</div> -->
                            </div>
                        </div>
                        <!-- @end #optional_crop -->
                        <div id="crop_field_choice" class="show_hide_class hidden">
                            <div class="form_area">
                                <h2 class="field-heading">Crop/Field Choices</h2>
                                <!-- update By Bhagvan Singh on 07-04-2015 button select all & undo
start -->
                                <div style="margin-bottom: 8px;" class="all_buttons">
                                    <div class="top_buttons addremove-field">
                                        <a onclick="allCheckboxNone()"><img
                                                src="<c:url value="/images/select-none.png"/>"></a>
                                    </div>
                                    <div class="top_buttons addremove-field padding-left-none">
                                        <a onclick="allCheckboxSelect()"><img
                                                src="<c:url value="/images/select-all.png"/>"></a>
                                    </div>
                                </div>
                                <!-- <div>
 <div style="float: right; width: 19%;" class="col-lg-3 col-md-5 col-sm-5  addremove-field padding-left-none"><a onclick="allCheckboxNone()"><img src="images/select-none.png"></a></div>
<div style="float: right; width: 19%;" class="col-lg-3 col-md-5 col-sm-5  addremove-field"><a onclick="allCheckboxSelect()"><img src="images/select-all.png"></a></div></div> -->
                                <!-- end -->
                                <div class="clearfix"></div>
                                <div class="info">
                                    Select which crops you are considering planting in each field.
                                    <span><a id="crop_field_choices"
                                             class="help_Infromation_PopUp" href="javascript:;"><img
                                            src="<c:url value="/images/i-icon.png"/>"></a></span>
                                </div>
                                <div class="ques">
                                    <div class="table-responsive" <%--style="overflow: auto; max-height: 265px;"--%>>
                                        <table class="table table-striped tbl-bordr tblbrdr fld-chc-tbl-fixd-hdr"
                                               cellspacing="0" width="100%" id="field_choice_crop_table">
                                            <thead id="field_choice_crop_thead">

                                            <!--       Crop/Field Choices dynamic table genarate details get by Crop/Field Choices View list -->

                                            <tr class="tblheader text-center add-fieldi"
                                                id="field_choice_crop_thead_row_first">
                                                <td class="tblbrdr text-center add-fieldi"
                                                    id="field_choice_crop_thead_row_column__1">
                                                    Field/Crop
                                                </td>
                                                <c:set var="headCount" value="2"/>
                                                <c:forEach var="cropForHead" items="${model.cropTypeView}">
                                                    <c:if test="${cropForHead.selected}">
                                                        <td class="text-center add-fieldi"
                                                            id="field_choice_crop_thead_row_column__${headCount}">${cropForHead.cropName}</td>
                                                        <c:set var="headCount" value="${headCount+1}"/>
                                                    </c:if>
                                                </c:forEach>
                                            </tr>
                                            </thead>
                                            <tbody id="field_choice_crop_tbody" class="scrollDiv">
                                            <!--         start row cropFieldsDetails -->
                                            <c:set var="rowFieldCount" value="1"/>
                                            <c:forEach var="cropFieldsList" items="${model.fieldInfoList}">

                                                <tr id="field_choice_crop_tbody_row__${rowFieldCount}"
                                                    class="tblgrn text-center">
                                                    <td class="tblft1"
                                                        id="field_choice_crop_tbody_row_first_column__${rowFieldCount}">${cropFieldsList.fieldName}</td>

                                                    <c:set var="falllow" value="false"/>
                                                        <%-- <c:set var="lastCropName" value="" />  --%>
                                                    <c:forEach var="fieldList" items="${model.fieldInfoList}">
                                                        <c:if test="${fieldList.fieldName eq cropFieldsList.fieldName && fieldList.fallow}">
                                                            <c:set var="falllow" value="true"/>
                                                        </c:if>
                                                        <%-- <c:if test="${fieldList.fieldName eq cropFieldsList.fieldName && fieldList.lastCrop ne 'No Crop'}">
<c:set var="lastCropName" value="fieldList.lastCrop" />
</c:if> --%>
                                                    </c:forEach>

                                                    <c:set var="columnCropCount" value="1"/>
                                                    <c:forEach var="cropFieldsChoice"
                                                               items="${model.cropFieldsDetails}">


                                                        <c:if test="${cropFieldsChoice.fieldName.fieldName eq cropFieldsList.fieldName}">
                                                            <td id="field_choice_crop_tbody_row_others_column__${rowFieldCount}__column__${columnCropCount}"
                                                                class="success">
                                                                <label>
                                                                    <input type="checkbox"
                                                                           data-row="${rowFieldCount}"
                                                                           data-col="${columnCropCount}"
                                                                           onchange="cropFieldChoiceCheckboxChenge(this)"
                                                                           class="cropFieldChoiceCheckbox countChoiceCheckboxChenge"
                                                                           id="field_choice_crop_selected_row__${rowFieldCount}__column__${columnCropCount}"
                                                                           value="true"
                                                                        ${cropFieldsChoice.cropFieldChoice eq 'true'?'checked':''}
                                                                        ${falllow eq 'true'?'disabled':''}>
                                                                        <%--<span class="input-label">${cropFieldsChoice.cropType.cropName}</span>--%>
                                                                </label>
                                                            </td>
                                                            <c:set var="columnCropCount" value="${columnCropCount+1}"/>
                                                        </c:if>
                                                    </c:forEach>
                                                    <c:set var="rowFieldCount" value="${rowFieldCount+1}"/>
                                                </tr>
                                            </c:forEach>


                                            <!--         end row cropFieldsDetails -->

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="clearfix"></div>

                                <div class="yellobtn pre_next">
                                    <a onclick="nextCropFieldChoice()">Next</a>
                                </div>
                                <div class="yellobtn pre_next">
                                    <a id="submit"
                                       onclick="callMethodForPageChangeAndProgressBarImage(4,0)">Previous</a>
                                </div>


                            </div>
                        </div>
                        <!-- @end #crop_field_choice -->
                        <div id="resources_info" class="show_hide_class hidden">
                            <div class="form_area">
                                <h2 class="field-heading">Resources</h2>
                                <div class="clearfix"></div>
                                <div style="margin-bottom: 8px;" class="all_buttons">
                                    <div class="top_buttons addremove-field">
                                        <a onclick="div_show6()"><img
                                                src="<c:url value="/images/add_resource.png"/>"></a>
                                    </div>
                                    <div class="top_buttons addremove-field padding-left-none">
                                        <a onclick="removeResource()"><img
                                                src="<c:url value="/images/remove_resources.png"/>"></a>
                                    </div>
                                </div>
                                <!-- <div class="col-lg-6 col-md-2 col-sm-2 text-left"></div>
<div class="col-lg-3 col-md-5 col-sm-5  addremove-field"><a onclick="div_show6()"><img src="images/add_resource.png"></a></div>
<div class="col-lg-3 col-md-5 col-sm-5  addremove-field padding-left-none"><a onclick="removeResourse()"><img src="images/remove_resources.png"></a></div> -->
                                <div class="clearfix"></div>
                                <!--
                                    @Changed - Abhishek
                                    @Date - 25-11-2015
                                 -->
                                <div class="info" style="margin-top: 5px;">
                                    Select which resources you want to include in the analysis.<span><a
                                        id="resourse_manage" class="help_Infromation_PopUp" href="javascript:;"><img
                                        src="<c:url value="/images/i-icon.png"/>"></a></span>
                                </div>


                                <div class="ques">
                                    <div class="maintbl">  <%--style="max-height: 214px;"--%>
                                        <table id="manage_resource"
                                               class="table tbl table-striped tbl-bordr  tblbrdr resources-tbl-fixd-hdr"
                                               cellspacing="0" width="100%">
                                            <thead>
                                            <tr class="tblheader text-center add-fieldi">
                                                <td class="tblbrdr text-center add-fieldi">Manage</td>
                                                <td class="text-center add-fieldi">Resource Name</td>
                                                <!--	@changed - Abhishek		@date - 23-01-2016 		@desc - Added help information functionality-->
                                                <%--<td class="text-center add-fieldi">Unit of Measure</td>--%>
                                                <td class="text-center add-fieldi">Unit of Measure<a
                                                        id="resourceUnitOfmeasure" class="help_Infromation_PopUp"
                                                        href="javascript:;"><img
                                                        src="<c:url value="/images/i-icon.png"/>"></a></td>
                                                <!--	@changed - Abhishek		@date - 29-12-2015 		@desc - Added help information functionality-->
                                                <%--<td class="text-center">Amount Available <img src="<c:url value="/images/i-img.png"/>"></td>--%>
                                                <td class="text-center">Amount Available <a id="amount_Available"
                                                                                            class="help_Infromation_PopUp"
                                                                                            href="javascript:;"><img
                                                        src="<c:url value="/images/i-icon.png"/>"></a></td>
                                            </tr>
                                            </thead>
                                            <tbody id="manage_resource_tbody">
                                            <c:set var="rowCount" value="1"/>
                                            <c:set var="resourceNo" value="1"/>
                                            <c:forEach var="resourceListForManageResource"
                                                       items="${model.resourceList}">
                                                <!-- details start -->
                                                <c:choose>
                                                    <c:when test="${resourceListForManageResource.cropResourceUse eq 'Land'}">
                                                        <tr class="tblgrn text-center"
                                                            id="manage_resource_row_resource__1">
                                                            <td class="tblft1"><input type="checkbox"
                                                                                      checked="checked"
                                                                                      disabled="disabled"></td>
                                                            <td class="success croplimi">Land</td>
                                                            <td class="success croplimit">Acres</td>
                                                            <td id="total_land_available"
                                                                class="success croplimit">${resourceListForManageResource.cropResourceUseAmount}</td>
                                                        </tr>
                                                    </c:when>
                                                    <c:when
                                                            test="${resourceListForManageResource.cropResourceUse eq 'Capital'}">
                                                        <tr class="tblbclgrnd text-center"
                                                            id="manage_resource_row_resource__2">
                                                            <td class="tblft1"><input type="checkbox"
                                                                                      checked="checked"
                                                                                      disabled="disabled"></td>
                                                            <td class="croplimi">Working Capital</td>
                                                            <td class=" croplimit">$</td>
                                                            <!--  modified by Bhagvan Singh auto comma added
            start -->
                                                            <!-- Modified by Harshit Gupta on 20-04-2015 -->
                                                            <td class="croplimit"><input type="text"
                                                                                         id="total_capital_available"
                                                                                         onkeypress="return isValidNumberValueForWithOutDot(event)"
                                                                                         onchange="addCommaSignInCapital(this)"
                                                                                         value="$${resourceListForManageResource.cropResourceUseAmount}">
                                                            </td>
                                                            <!--                  end -->
                                                        </tr>
                                                    </c:when>
                                                </c:choose>
                                                <c:choose>

                                                    <c:when test="${rowCount % 2 eq '0'}">
                                                        <c:if
                                                                test="${resourceListForManageResource.cropResourceUse ne 'Capital' and resourceListForManageResource.cropResourceUse ne 'Land'}">
                                                            <tr class="tblbclgrnd text-center"
                                                                id="manage_resource_row_resource__${rowCount}">
                                                                <td class="tblft1">
                                                                    <input type="checkbox"
                                                                           onchange="onResourceSelectedOrRemoved(this)"
                                                                           name="resourceSelection"
                                                                           id="manage_resource_row_checkbox__${rowCount}"
                                                                           <c:if test="${resourceListForManageResource.isActive()}">checked="checked"</c:if>>
                                                                </td>
                                                                <td class="croplimi"
                                                                    id="resource_manage_name__${resourceNo}">${resourceListForManageResource.cropResourceUse}</td>
                                                                <td class="croplimit"
                                                                    id="resource_manage_uom__${resourceNo}">${resourceListForManageResource.uoMResource}</td>
                                                                <td class="croplimit"><input type="text"
                                                                                             id="resource_manage__${resourceNo}"
                                                                                             onkeypress="return isValidNumberValueForWithOutDot(event)"
                                                                                             onchange="addCommaSignWithOutDollarDot(this)"
                                                                                             value="${resourceListForManageResource.cropResourceUseAmount}">
                                                                </td>
                                                            </tr>
                                                            <c:set var="resourceNo" value="${resourceNo+1}"/>
                                                        </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:if
                                                                test="${resourceListForManageResource.cropResourceUse ne 'Capital' and resourceListForManageResource.cropResourceUse ne 'Land'}">
                                                            <tr class="tblgrn text-center"
                                                                id="manage_resource_row_resource__${rowCount}">
                                                                <td class="tblft1"><input type="checkbox"
                                                                                          onchange="onResourceSelectedOrRemoved(this)"
                                                                                          name="resourceSelection"
                                                                                          id="manage_resource_row_checkbox__${rowCount}"
                                                                                          <c:if test="${resourceListForManageResource.isActive()}">checked="checked"</c:if>>
                                                                </td>
                                                                <td class="success croplimi"
                                                                    id="resource_manage_name__${resourceNo}">${resourceListForManageResource.cropResourceUse}</td>
                                                                <td class="success croplimit"
                                                                    id="resource_manage_uom__${resourceNo}">${resourceListForManageResource.uoMResource}</td>
                                                                <td class="success croplimit"><input type="text"
                                                                                                     id="resource_manage__${resourceNo}"
                                                                                                     onchange="addCommaSignWithOutDollarDot(this)"
                                                                                                     onkeypress="return isValidNumberValueForWithOutDot(event)"
                                                                                                     value="${resourceListForManageResource.cropResourceUseAmount}">
                                                                </td>
                                                            </tr>
                                                            <c:set var="resourceNo" value="${resourceNo+1}"/>
                                                        </c:if>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:set var="rowCount" value="${rowCount+1}"/>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="clearfix"></div>


                                <div class="yellobtn pre_next">
                                    <a onclick="nextResources()">Next</a>
                                </div>
                                <div class="yellobtn pre_next">
                                    <a id="submit"
                                       onclick="callMethodForPageChangeAndProgressBarImage(5, 4)">Previous</a>
                                </div>

                            </div>
                        </div>
                        <!-- @end #resources -->
                        <div id="resources_usage" class="show_hide_class hidden">
                            <div class="form_area">
                                <h2 class="field-heading">Crop Resources Usage</h2>
                                <div class="clearfix"></div>
                                <div class="info">
                                    Enter the amount of each resource used per acre.
                                    <span><a id="resourse_usage" class="help_Infromation_PopUp" href="javascript:;"><img
                                            src="<c:url value="/images/i-icon.png"/>"></a></span>
                                </div>
                                <div class="ques">
                                    <div class="maintbl" style="max-height: 250px;">
                                        <table id="crop_resource_usage"
                                               class="table tbl table-striped tbl-bordr  tblbrdr Crop_Resources_Usage_table"
                                               cellspacing="0" width="100%">
                                            <thead id="crop_resource_usage_thead">
                                            <tr id="crop_resource_usage_thead_first_row"
                                                class="tblhd text-center add-fieldi">
                                                <td class="tblbrdr text-center tittle-uppercase"
                                                    id="crop_resource_usage_thead_first_row_column__1">Crop
                                                </td>
                                                <td class="text-center tittle-uppercase UOMCropResourceUsage"
                                                    id="crop_resource_usage_thead_first_row_column__2">Yields<br>
                                                    <span class="resub">(UoM acre)</span>
                                                </td>
                                                <td class="text-center tittle-uppercase"
                                                    id="crop_resource_usage_thead_first_row_column__3">Variable
                                                    Production Cost<br>
                                                    <span class="resub">($/acre*)</span>${resourceHead.cropResourceUse}
                                                    <%--	@added - Abhishek		@date - 23-01-2016		@desc - added according to silde# 5 of 01042015		--%>
                                                    <span><a class="help_Infromation_PopUp"
                                                             id="variable_production_cost_resourse"><img
                                                            src="<c:url value="/images/i-icon.png"/>"></a></span>
                                                </td>
                                                <c:set var="headCount" value="4"/>
                                                <c:forEach var="resourceHead" items="${model.resourceList}">
                                                    <!-- modify Bhagvan Singh on 06-04-2015 for remove start -->
                                                    <c:if test="${resourceHead.isActive() and resourceHead.cropResourceUse ne 'Land' and resourceHead.cropResourceUse ne 'Capital' and resourceHead.cropResourceUseAmount ne '' and resourceHead.cropResourceUseAmount ne '0'}">
                                                        <td class="text-center">
                                                            <span id="crop_resource_usage_thead_first_row_column__${headCount}" class="tittle-uppercase">${resourceHead.cropResourceUse}</span><br>
                                                            <span class="resub">(${resourceHead.uoMResource}) per acre</span>
                                                        </td>
                                                        <c:set var="headCount" value="${headCount+1}"/>
                                                    </c:if>
                                                </c:forEach>
                                            </tr>
                                            </thead>
                                            <tbody id="crop_resource_usage_tbody">

                                            <%--         start row resource --%>
                                            <c:set var="rowCount" value="1"/>
                                            <c:forEach var="cropList" items="${model.cropTypeView}">
                                                <c:if test="${cropList.selected}">
                                                    <tr id="crop_resource_usage_row__${rowCount}"
                                                        class="tblgrn text-center">
                                                        <c:choose>
                                                            <c:when test="${model.farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                                                                <c:choose>
                                                                    <c:when test="${cropList.cropYieldFieldId ne null and cropList.cropYieldFieldId ne '0'}">
                                                                        <td id="crop_resource_usage_crop__${rowCount}"
                                                                            class="tblft1 crop_field_diff"
                                                                            title="This crop has differences in yield and/or resource usage for field ${cropList.fieldNameForVariances}.">${cropList.cropName}</td>
                                                                        <%--<td id="crop_resource_usage_crop__${rowCount}"
                                                                            class="tblft1 crop_field_diff"
                                                                            title="This crop have field difference with field ${cropList.fieldNameForVariances}.">${cropList.cropName}</td>--%>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <td id="crop_resource_usage_crop__${rowCount}"
                                                                            class="tblft1">${cropList.cropName}</td>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td id="crop_resource_usage_crop__${rowCount}"
                                                                    class="tblft1">${cropList.cropName}</td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <td class="success infotext tittle-uppercase UOMCropResourceUsage"
                                                            id="crop_resource_usage_row_uomValue__${rowCount}">${cropList.cropUOM}</td>
                                                        <td class="success infotext"
                                                            id="crop_resource_usage_row_productionCost__${rowCount}">${cropList.calculatedVariableProductionCostFloat ne '' ? cropList.calculatedVariableProductionCostFloat : '$0.00'}
                                                            <img src="<c:url value="/images/data.png"/>"/></td>
                                                        <c:set var="columnCount" value="1"/>
                                                        <c:forEach var="resourceList" items="${model.resourceList}">
                                                            <c:if test="${resourceList.isActive()}">
                                                                <c:forEach var="resourcesVariancesList"
                                                                           items="${model.resourceVariancesList}">
                                                                    <%-- resourcesVariancesList:${resourcesVariancesList.id} --%>
                                                                    <%-- <c:out value="${listValue.cropName}" /> --%>
                                                                    <%--   	forEach loop for row creation crop cost_component_row wise --%>

                                                                    <c:if test="${cropList.id eq resourcesVariancesList.cropId and resourcesVariancesList.cropFieldResourceUse ne 'Land' and resourcesVariancesList.cropFieldResourceUse ne 'Capital'}">
                                                                        <c:if test="${resourceList.cropResourceUse eq resourcesVariancesList.cropFieldResourceUse}">
                                                                            <%--   		cropResourceAmount : ${resourcesVariancesList.cropResourceAmount} --%>
                                                                            <c:if test="${resourcesVariancesList.cropResourceAmount ne 'undefined'}">
                                                                            <td class="success infotext">
                                                                                <input type="text"
                                                                                       data-resName = "${resourcesVariancesList.cropName}"
                                                                                       onchange="addCommaSignWithOutDollar(this);cropResourceUsageValue(this);cropResourceUsageValueChange(this);"
                                                                                       onkeypress="return isValidNumberValue(event)"
                                                                                       id="crop_resource_usage__${rowCount}__resource__${columnCount}"
                                                                                       value="${resourcesVariancesList.cropResourceAmount eq '0.00' or resourcesVariancesList.cropResourceAmount eq '.00' ?'':(resourcesVariancesList.cropResourceAmount eq 'undefined' ? '':resourcesVariancesList.cropResourceAmount)}"/>
                                                                            </td>
                                                                            </c:if>
                                                                            <c:set var="columnCount"
                                                                                   value="${columnCount+1}"/>
                                                                        </c:if>
                                                                    </c:if>
                                                                </c:forEach>
                                                            </c:if>
                                                        </c:forEach>
                                                    </tr>
                                                    <c:set var="rowCount" value="${rowCount+1}"/>
                                                </c:if>
                                            </c:forEach>
                                            <%--         end row resource --%>

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="clearfix"></div>
                                <div class="ques" id="show_hide_field_variance_button">
                                    <%--	@changed - Abhishek 	@date - 23-01-2016		@desc - chanegd according to slide# 5 of 01042015	--%>
                                    <%--<span class="cropbtn pre_pos"><a onclick="showFieldVariencePage()">Field Differences<img src="<c:url value="/images/i-icon.png"/>"></a></span>--%>
                                    <span class="cropbtn pre_pos"><a
                                            onclick="showFieldVariencePage()">Field Differences</a></span>
                                    <span><a class="help_Infromation_PopUp" id="fieldDifference"><img
                                            src="<c:url value="/images/i-icon.png"/>"
                                            style="margin-top: 7px;"></a></span>
                                </div>

                                <div class="yellobtn pre_next">
                                    <!-- <a onclick="showFieldVariencePage()">Next</a> -->
                                    <!-- change 09-03-2015 -->
                                    <!-- <a onclick="showForwardSalesPage()">Next</a> -->
                                    <a onclick="nextCropResourceUsage()">Next</a>
                                    <!-- change 09-03-2015 -->
                                </div>
                                <div class="yellobtn pre_next">
                                    <a id="submit"
                                       onclick="callMethodForPageChangeAndProgressBarImage(6, 5)">Previous</a>
                                </div>

                            </div>
                        </div>
                        <!-- @end #resources_usage -->
                        <div id="field_varience" class="show_hide_class hidden">
                            <div class="form_area">
                                <h2 class="field-heading">Field Differences</h2>
                                <div class="clearfix"></div>
                                <div class="info">
                                    Account for variability of yields and resource use among fields.
                                    <%--@changed - Abhishek 	@date - 29-12-2015 		@desc - Added help information functionality--%>
                                    <%--<img src="<c:url value="/images/i-icon.png"/>">--%>
                                    <a id="amount_variablity_yield" class="help_Infromation_PopUp"
                                       href="javascript:;"><img src="<c:url value="/images/i-icon.png"/>"></a>
                                </div>
                                <div class="clearfix"></div>
                                <div class="ques">
                                    <div class="col-lg-6 col-md-6 col-sm-6 pull-left text-right">
                                        <select class="variance" onchange="fieldSelectFieldVarience(this)"
                                                id="field_select_drop_down">
                                            <option value="0">Select Field</option>
                                            <!-- 28-03-2015 start Field Name List from View start -->
                                            <c:forEach var="fieldListForVarience" items="${model.fieldInfoList}">
                                                <option value="${fieldListForVarience.fieldName}" ${fieldListForVarience.id eq fieldListForVarience.fieldIdForVariances ? 'selected' : ''}>${fieldListForVarience.fieldName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <c:set var="selected_field_difference_crop" value=""/>
                                    <div class="col-lg-6 col-md-6 col-sm-6 pull-right text-left">
                                        <select onchange="getFieldYieldDiffence(this)" class="variance"
                                                id="crop_select_drop_down">
                                            <option value="0">Select Crop</option>
                                            <c:forEach var="cropListForVariences" items="${model.cropTypeView}">
                                                <c:if test="${cropListForVariences.selected}">
                                                    <option value="${cropListForVariences.cropName}" ${cropListForVariences.id eq cropListForVariences.cropIdForVariences ? 'selected' : ''}>${cropListForVariences.cropName}</option>
                                                    <c:if test="${cropListForVariences.id eq cropListForVariences.cropIdForVariences}">
                                                        <c:set var="selected_field_difference_crop"
                                                               value="${cropListForVariences.cropName}"/>
                                                    </c:if>
                                                    <c:if test="${cropListForVariences.id eq cropListForVariences.cropIdForVariences}"></c:if>
                                                </c:if>
                                            </c:forEach>
                                            <!-- <option>Select Crop1</option>
<option>Select Crop2</option>
<option>Select Crop3</option> -->
                                        </select>
                                    </div>
                                    <div class="clearfix"></div>
                                    <div class="ques">
                                        <div class="col-lg-6 col-md-6 col-sm-6">
                                            <p class="text-left variancesub"> Yield Difference
                                                <%--@changed - Abhishek 	@date - 29-12-2015 	@desc - Added help information functionality--%>
                                                <%--<img src="<c:url value="/images/i-icon.png"/>">--%>
                                                <a id="yield_Difference" class="help_Infromation_PopUp"
                                                   href="javascript:;"><img
                                                        src="<c:url value="/images/i-icon.png"/>"></a>
                                            </p>
                                            <div class="table-responsive">
                                                <table class="table table-striped tbl-bordr  tblbrdr" cellspacing="0"
                                                       width="100%">
                                                    <thead>
                                                    <tr class="tblhd text-center add-fieldi">
                                                        <td class="tblbrdr text-center"></td>
                                                        <%--@changed - Abhishek 	@date - 30-12-2015	--%>
                                                        <%--<td class="text-center">Previously Entered</td>--%>
                                                        <td class="text-center">Default</td>
                                                        <%--@changed - Abhishek 	@date - 30-12-2015	--%>
                                                        <%--<td class="text-center">Override</td>--%>
                                                        <td class="text-center">Field-Specific</td>
                                                    </tr>
                                                    </thead>
                                                    <tbody id="yield-difference-tbody">
                                                    <!-- create By Bhagvan Singh on 13-04-2015 for unselected condition create Yield Difference
    start -->
                                                    <c:set var="yieldDifferenceStatus" value="1"/>

                                                    <c:forEach var="cropList" items="${model.cropTypeView}" begin="1" end="3">
                                                        <c:if test="${cropList.selected}">
                                                            <c:if test="${cropList.cropIdForVariences eq cropList.id}">
                                                                <c:set var="yieldDifferenceStatus"
                                                                       value="${yieldDifferenceStatus+1}"/>
                                                                <!-- end -->
                                                                <tr class="tblgrn text-center">
                                                                    <td class="tblft1">Expected</td>
                                                                    <td class="success infotext"
                                                                        id="crop_Yield_Difference_Expected">${cropList.intExpCropYield}</td>
                                                                    <td class="success infotext">
                                                                        <input type="text" id="field_difference_exp"
                                                                               onchange="addCommaSignWithForOnePoint(this)"
                                                                               onkeypress="return isValidNumberValue(event)"
                                                                               name="${cropList.cropName}"
                                                                               value="${cropList.expCropYieldFieldStr}"/>
                                                                    </td>
                                                                </tr>
                                                                <tr class="tblbclgrnd text-center">
                                                                    <td class="tblft1">Minimum</td>
                                                                    <td class="infotext"
                                                                        id="crop_Yield_Difference_Minimum">${cropList.intMinCropYield eq '.0' ? '' : cropList.intMinCropYield}</td>
                                                                    <td class="infotext">
                                                                        <input type="text" id="field_difference_min"
                                                                               onchange="addCommaSignWithForOnePoint(this)"
                                                                               onkeypress="return isValidNumberValue(event)"
                                                                               name="${cropList.cropName}"
                                                                               value="${cropList.minCropYieldFieldStr}"/>
                                                                    </td>
                                                                </tr>
                                                                <tr class="tblgrn text-center">
                                                                    <td class="tblft1">Maximum</td>
                                                                    <td class="success infotext"
                                                                        id="crop_Yield_Difference_Maximum">${cropList.intMaxCropYield eq '.0' ? '' : cropList.intMaxCropYield}</td>
                                                                    <td class="success infotext">
                                                                        <input type="text" id="field_difference_max"
                                                                               onchange="addCommaSignWithForOnePoint(this)"
                                                                               onkeypress="return isValidNumberValue(event)"
                                                                               name="${cropList.cropName}"
                                                                               value="${cropList.maxCropYieldFieldStr}"/>
                                                                    </td>
                                                                </tr>
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                    <c:forEach var="exe_min_max" items="${model.cropTypeView}">
                                                        <c:if test="${exe_min_max.selected}">
                                                        <c:if test="${exe_min_max.cropIdForVariences eq exe_min_max.id}">
                                                        <input type="hidden" id="expCropYieldField" value="${exe_min_max.expCropYieldField}"/>
                                                        <input type="hidden" id="minCropYieldField" value="${exe_min_max.minCropYieldField}"/>
                                                        <input type="hidden" id="maxCropYieldField" value="${exe_min_max.maxCropYieldField}"/>
                                                        <input type="hidden" id="fieldNameForVariances" value="${exe_min_max.fieldNameForVariances}"/>
                                                        <input type="hidden" id="fieldIdForVariances" value="${exe_min_max.fieldIdForVariances}"/>
                                                        <input type="hidden" id="cropName" value="${exe_min_max.cropName}"/>
                                                        </c:if>
                                                        </c:if>
                                                    </c:forEach>
                                                    <!-- create By Bhagvan Singh on 13-04-2015 for unselected condition create Yield Difference
    start -->
                                                    <c:if test="${yieldDifferenceStatus eq 1}">
                                                        <tr class="tblgrn text-center">
                                                            <td class="tblft1">Expected</td>
                                                            <td class="success infotext"
                                                                id="crop_Yield_Difference_Expected"></td>
                                                            <td class="success infotext">
                                                                <input type="text" id="field_difference_exp"
                                                                       onchange="addCommaSignWithForOnePoint(this)"
                                                                       onkeypress="return isValidNumberValue(event)"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="tblbclgrnd text-center">
                                                            <td class="tblft1">Minimum</td>
                                                            <td class="infotext"
                                                                id="crop_Yield_Difference_Minimum"></td>
                                                            <td class="infotext">
                                                                <input type="text" id="field_difference_min"
                                                                       onchange="addCommaSignWithForOnePoint(this)"
                                                                       onkeypress="return isValidNumberValue(event)"/>
                                                            </td>
                                                        </tr>
                                                        <tr class="tblgrn text-center">
                                                            <td class="tblft1">Maximum</td>
                                                            <td class="success infotext"
                                                                id="crop_Yield_Difference_Maximum"></td>
                                                            <td class="success infotext">
                                                                <input type="text" id="field_difference_max"
                                                                       onchange="addCommaSignWithForOnePoint(this)"
                                                                       onkeypress="return isValidNumberValue(event)"/>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                    <!-- end -->
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>

                                        <div class="col-lg-6 col-md-6 col-sm-6">
                                            <p class="text-left variancesub">
                                                Resource Usage Difference
                                                <%--@changed - Abhishek 	@date - 29-12-2015 	@desc - Added help information functionality --%>
                                                <%--<img src="<c:url value="/images/i-icon.png"/>">--%>
                                                <a id="resource_usage_Difference" class="help_Infromation_PopUp"
                                                   href="javascript:;"><img
                                                        src="<c:url value="/images/i-icon.png"/>"></a>
                                            </p>
                                            <div class="table-responsive">
                                                <table class="table table-striped tbl-bordr  tblbrdr"
                                                       cellspacing="0" width="100%">
                                                    <thead>
                                                    <tr class="tblhd text-center add-fieldi">
                                                        <%--@changed - Abhishek 	@date - 30-12-2015	--%>
                                                        <%--<td class="tblbrdr text-center">Crop</td>--%>
                                                        <td class="tblbrdr text-center">Resource</td>
                                                        <%--@changed - Abhishek 	@date - 30-12-2015	--%>
                                                        <%--<td class="text-center">Previously Entered</td>--%>
                                                        <td class="text-center">Default</td>
                                                        <%--@changed - Abhishek 	@date - 30-12-2015	--%>
                                                        <%--<td class="text-center">Override</td>--%>
                                                        <td class="text-center">Field-Specific</td>
                                                    </tr>

                                                    </thead>

                                                    <tbody id="crop_resources_usages_difference_tbody">
                                                    <!-- update By Bhagvan Singh On 09-04-2015 for Variable Production Cost in Resource Usage Difference screen
        start -->
                                                    <c:set var="resourceUsageDifferenceStatus" value="1"/>
                                                    <c:set var="cropIdForResourceUsageDifference" value=""/>
                                                    <c:forEach var="resourceListForCropResourcesUsages"
                                                               items="${model.cropTypeView}" end="1">
                                                        <c:if
                                                                test="${resourceListForCropResourcesUsages.selected and resourceListForCropResourcesUsages.id eq resourceListForCropResourcesUsages.cropIdForVariences}">

                                                            <tr class="tblgrn text-center"
                                                                id="resources_usages_production_row">
                                                                <td class="tblft1 tittle-uppercase"
                                                                    id="resources_usages_difference_row_resource_name">
                                                                    Variable Production Cost
                                                                </td>
                                                                <td class="success infotext"
                                                                    id="resources_usages_production_resource_default">${resourceListForCropResourcesUsages.calculatedVariableProductionCostFloat}</td>
                                                                <td class="success infotext">
                                                                    <input type="text"
                                                                           id="resources_usages_production_cost_resource_override"
                                                                           onchange="addCommaSignWithDollar(this)"
                                                                           onkeypress="return isValidNumberValue(event)"
                                                                           value="${resourceListForCropResourcesUsages.varProductionCostStr}">
                                                                </td>
                                                            </tr>
                                                            <c:set var="cropIdForResourceUsageDifference"
                                                                   value="${resourceListForCropResourcesUsages.cropIdForVariences}"/>
                                                            <c:set var="resourceUsageDifferenceStatus"
                                                                   value="${resourceUsageDifferenceStatus+1}"/>
                                                        </c:if>
                                                    </c:forEach>

                                                    <c:forEach var="resourceListForCropResourcesUsages" items="${model.cropTypeView}">
                                                    <c:if test="${resourceListForCropResourcesUsages.selected and resourceListForCropResourcesUsages.id eq resourceListForCropResourcesUsages.cropIdForVariences}">
                                                    <input type="hidden" id="productionCost" value="${resourceListForCropResourcesUsages.varProductionCostStr}"/>
                                                    </c:if>
                                                    </c:forEach>

                                                    <!-- created By Bhagvan Singh for variable prodution cost default column on 13-042015
    start -->
                                                    <c:if test="${resourceUsageDifferenceStatus eq 1}">
                                                        <tr class="tblgrn text-center"
                                                            id="resources_usages_production_row">
                                                            <td class="tblft1 tittle-uppercase"
                                                                id="resources_usages_difference_row_resource_name">
                                                                Variable Production Cost
                                                            </td>
                                                            <td class="success infotext"
                                                                id="resources_usages_production_resource_default"></td>
                                                            <td class="success infotext">
                                                                <input type="text"
                                                                       id="resources_usages_production_cost_resource_override"
                                                                       onchange="addCommaSignWithDollar(this)"
                                                                       onkeypress="return isValidNumberValue(event)">
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                    <!--             end -->
                                                    <c:set var="resourceNo" value="1"/>
                                                    <c:forEach var="resourceListForCropResourcesUsages"
                                                               items="${model.resourceList}">
                                                        <!-- details start -->
                                                        <!-- Resources usages difference start dynamicaly -->
                                                        <c:if
                                                                test="${resourceListForCropResourcesUsages.cropResourceUse ne 'Land' and resourceListForCropResourcesUsages.cropResourceUse ne 'Capital'}">
                                                            <tr id="resources_usages_difference_row__${resourceNo}"
                                                                class="tblgrn text-center">
                                                                <td id="resources_usages_difference_row__${resourceNo}_resource_name"
                                                                    class="tblft1 tittle-uppercase">${resourceListForCropResourcesUsages.cropResourceUse}</td>
                                                                <c:set var="flagForfieldDifference" value="0"/>
                                                                <c:forEach var="resourceVariances"
                                                                           items="${model.resourceVariancesList}">
                                                                    <c:if test="${resourceVariances.cropName eq selected_field_difference_crop and resourceVariances.cropFieldResourceUse eq resourceListForCropResourcesUsages.cropResourceUse}">
                                                                        <td id="resources_usages_difference_row__${resourceNo}_resource_default"
                                                                            class="success infotext">
                                                                                ${resourceVariances.cropResourceAmount}
                                                                        </td>
                                                                        <c:set var="flagForfieldDifference" value="1"/>
                                                                    </c:if>
                                                                </c:forEach>
                                                                <c:if test="${flagForfieldDifference eq '0'}">
                                                                    <td id="resources_usages_difference_row__${resourceNo}_resource_default"
                                                                        class="success infotext"></td>
                                                                </c:if>
                                                                    <%-- 			<c:if test="${resourceListForCropResourcesUsages.id eq resourceListForCropResourcesUsages.resourseUsageId}"> --%>
                                                                <td class="success infotext">
                                                                    <input type="text"
                                                                           id="resources_usages_difference_row__${resourceNo}_resource_override"
                                                                           onkeypress="return isValidNumberValue(event)"
                                                                           onchange="addCommaSignWithOutDollar(this)"
                                                                           value="${resourceListForCropResourcesUsages.resourseOverrideAmount}">
                                                                </td>
                                                                    <%-- 			</c:if> --%>
                                                            </tr>
                                                            <c:set var="resourceNo" value="${resourceNo+1}"/>
                                                        </c:if>
                                                    </c:forEach>
                                                    </tbody>

                                                    <!-- end -->
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="clearfix"></div>

                                    <div class="yellobtn pre_next"
                                         id="dynamic_button_for_yeild_differnce">
                                        <a onclick="nextFieldDifference(); saveFieldDifference()">Ok</a>
                                    </div>
                                    <div class="yellobtn pre_next"
                                         id="dynamic_button_for_yeild_differnce">
                                        <a onclick="getValueForFieldDiffrence(); saveDatabaseValuesToLocalStorage(); nextFieldDifference2();">Reset All to Original Values</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- @end #field_varience -->
                        <div id="forward_sales_div" class="show_hide_class hidden">
                            <div class="form_area">
                                <h2 class="field-heading">
                                    Forward Sales <span><a id="forward_sale"
                                                           class="help_Infromation_PopUp" href="javascript:;"><img
                                        src="<c:url value="/images/i-icon.png"/>"></a></span>
                                </h2>
                                <div class="clearfix"></div>
                                <div class="info">
                                    Enter information on firm or proposed forward sales.
                                    <%--@changed - Abhishek 	@date - 29-12-2015	--%>
                                    <%--<a id="forward_sale_proposed" class="help_Infromation_PopUp" href="javascript:;"><img src="<c:url value="/images/i-icon.png"/>" class="add-fieldi"></a>--%>
                                </div>
                                <div class="ques">
                                    <div class="table-responsive">  <%--style="max-height: 300px;"--%>
                                        <table id="forward_sales_information"
                                               class="table table-striped tbl-bordr  tblbrdr forward-sales-tbl-fixd-hdr"
                                               cellspacing="0" width="100%">
                                            <thead>
                                            <!--	@changed - Abhishek		@date - 29-12-2015 		@desc - Added help information functionality   -->
                                            <tr class="tblheader text-center add-fieldi">
                                                <td class="tblbrdr text-center add-fieldi">Crop</td>
                                                <td class="text-center add-fieldi">Yield Units</td>
                                                <td class="text-center add-fieldi">Price <a id="forward_sale_price"
                                                                                            class="help_Infromation_PopUp"
                                                                                            href="javascript:;"><img
                                                        src="<c:url value="/images/i-icon.png"/>"></a></td>
                                                <td class="text-center add-fieldi">Amount <a id="forward_sale_amount"
                                                                                             class="help_Infromation_PopUp"
                                                                                             href="javascript:;"><img
                                                        src="<c:url value="/images/i-icon.png"/>"></a></td>
                                                <td class="text-center add-fieldi">Acres <a id="forward_sale_acres"
                                                                                            class="help_Infromation_PopUp"
                                                                                            href="javascript:;"><img
                                                        src="<c:url value="/images/i-icon.png"/>"></a></td>
                                                <td class="text-center">Proposed <a id="forward_sale_proposed"
                                                                                    class="help_Infromation_PopUp"
                                                                                    href="javascript:;"><img
                                                        src="<c:url value="/images/i-icon.png"/>"></a></td>
                                                <td class="text-center">Firm <a id="forward_sale_firm_td"
                                                                                class="help_Infromation_PopUp"
                                                                                href="javascript:;"><img
                                                        src="<c:url value="/images/i-icon.png"/>"></a></td>
                                                <td class="text-center hidden">Upper Limit (%) <a
                                                        id="forward_sale_upper_limit" class="help_Infromation_PopUp"
                                                        href="javascript:;"><img
                                                        src="<c:url value="/images/i-icon.png"/>"></a></td>
                                            </tr>
                                            </thead>
                                            <!--         Modify by Bhagvan Singh 06-04-2015 start -->
                                            <tbody id="forward_sales_information_tbody" class="scrollDiv">
                                            <c:set var="forwardSalesRowCount" value="1"/>
                                            <c:set var="ContactIdentifierValue" value=""/>
                                            <c:forEach var="cropListForforwardSale" items="${model.cropTypeView}">
                                                <!--get crop list from CropView object -->
                                                <c:if test="${cropListForforwardSale.selected}">
                                                    <c:set var="ContactIdentifierValue"
                                                           value="${cropListForforwardSale.contactIdentifier}"/>
                                                    <tr id="forward_sales_information_tbody_row__${forwardSalesRowCount}"
                                                        class="tblbclgrnd text-center">
                                                        <td id="forward_sales_information_tbody_row_crop_name__${forwardSalesRowCount}"
                                                            class="tblft1">${cropListForforwardSale.cropName}</td>
                                                        <td class="success infotext tittle-uppercase"
                                                            id="forward_sales_information_tbody_row_uomValue__${forwardSalesRowCount}">${cropListForforwardSale.cropUOM}</td>
                                                        <!--	@changed - Abhishek		@date - 31-12-2015 -->
                                                        <td class="success croplimit">
                                                            <input type="text"
                                                                   id="forward_sales_information_tbody_row_crop_price__${forwardSalesRowCount}"
                                                                   value="${cropListForforwardSale.priceStr}"
                                                                   onkeypress="return isValidNumberValue(event)"
                                                                   onchange="addCommaSignWithDollar(this);addForwardNegativePriceRedBox(this); "></td>
                                                        <!--	@changed - Abhishek		@date - 31-12-2015 -->
                                                        <td class="success croplimit">
                                                            <input type="text"
                                                                   id="forward_sales_information_tbody_row_quantity__${forwardSalesRowCount}"
                                                                   value="${cropListForforwardSale.quantityStr}"
                                                                   onkeypress="return isValidNumberValue(event)"
                                                                   onchange="addCommaSignWithOutDollarDot(this);acerageCalForwardSale(this)">
                                                        </td>
                                                        <td class="success croplimit">
                                                            <input type="text"
                                                                   id="forward_sales_information_tbody_row_acres__${forwardSalesRowCount}"
                                                                   value="${cropListForforwardSale.acresStr}"
                                                                   onkeypress="return isValidNumberValue(event)"
                                                                   onchange="addCommaSignWithOutDollarDot(this);quantityCalForwardSale(this)">
                                                        </td>
                                                        <td class="success croplimit">
                                                            <input type="checkbox"
                                                                   onchange="proposedAndFirmSelection(this)"
                                                                   id="forward_sales_information_tbody_row_proposed__${forwardSalesRowCount}"
                                                                   value="true"
                                                                ${cropListForforwardSale.proposedchecked eq true ? 'checked':''}>
                                                        </td>
                                                        <td class="success croplimit">
                                                            <input type="checkbox"
                                                                   onchange="proposedAndFirmSelection(this)"
                                                                   id="forward_sales_information_tbody_row_firm__${forwardSalesRowCount}"
                                                                   value="true"
                                                                ${cropListForforwardSale.firmchecked eq 'true' ? 'checked':''}>
                                                        </td>
                                                        <td id="forward_sales_information_tbody_row_upper_limit__${forwardSalesRowCount}"
                                                            class="success croplimit hidden">
                                                            <input type="text"
                                                                   id="forward_sales_information_tbody_row_upperLimit__${forwardSalesRowCount}"
                                                                   onchange="addPercentSign(this)"
                                                                   value="${cropListForforwardSale.upperLimitStr}"
                                                                   onkeypress="return isValidNumberValueForWithOutDot(event)">
                                                        </td>
                                                    </tr>
                                                    <c:set var="forwardSalesRowCount"
                                                           value="${forwardSalesRowCount+1}"/>
                                                </c:if>
                                            </c:forEach>
                                            </tbody>
                                            <!-- 		modify end -->
                                        </table>
                                    </div>
                                </div>
                                </form>
                                <div class="clearfix"></div>
                                <div class="ques">
                                    <%--
<span class="pull-left salestext">Contract Identifier</span> <span class="sale">
Commented as per client requirement
<input type="text" id="contact_identifier" name="" value="${ContactIdentifierValue}"></span>  <img src="<c:url value="/images/i-icon.png"/>" class="salesimg"> --%>
                                </div>

                                <div class="yellobtn pre_next">
                                    <a onclick="nextForwardSales()">Next</a>
                                </div>
                                <div class="yellobtn pre_next">
                                    <!-- change 09-03-2015 -->
                                    <!-- <a id="submit" onclick="showFieldVariencePage()">Previous</a> -->
                                    <!-- change 09-03-2015 -->
                                    <!-- <a id="submit" onclick="showResourcesUsagePage()">Previous</a> -->
                                    <a id="submit"
                                       onclick="callMethodForPageChangeAndProgressBarImage(7, 6)">Previous</a>
                                </div>

                            </div>
                        </div>
                        <!-- @end #forward_sales -->
                        <div id="crop_limits_div" class="show_hide_class hidden">
                            <div class="form_area">
                                <h2 class="field-heading">Crop Limits</h2>
                                <div class="clearfix"></div>
                                <div class="ques">
                                    <!-- @changed - Abhishek 	@date - 05-12-2015 		@updated - 30-12-2015-->
                                    <p class="croptext pull-right" style=" margin-bottom: 10px; ">
                                        Create a group of crops and enter minimum <br>or maximum acreage limits for the
                                        group
                                        <a id="crop_group" class="help_Infromation_PopUp" href="javascript:;"><img
                                                src="<c:url value="/images/i-icon.png"/>"></a>
                                    </p>
                                    <div class="clearfix"></div>

                                    <div class="pull-right">
										<span>
											<!-- @changed - Abhishek 	@date - 30-12-2015-->
											<%--<a id="crop_group" class="help_Infromation_PopUp" href="javascript:;"><img src="<c:url value="/images/i-icon.png"/>"></a>--%>
											<a onclick="deSelectAllCropsInGroupOptionAndRebuild(); div_show11()"><img
                                                    src="<c:url value="/images/add-group.png"/>"></a>
										</span>
                                        <span><a onclick="getGroupForModify()"><img
                                                src="<c:url value="/images/modify_group.png"/>"></a></span>
                                        <span><a onclick="removeGroup()"><img
                                                src="<c:url value="/images/remove_group.png"/>"></a></span>
                                    </div>

                                    <div class="clearfix"></div>
                                    <%--	@changed - Abhishek 	@date - 26-01-2016		@desc - changed according to silde# 6 of 01042016	--%>
                                    <%--<div class="info pull-left" style="margin-top: 1.5px"> Enter minimum and maximum acreage limit for each crop--%>
                                    <div class="info pull-left" style="margin-top: 1.5px"> Enter minimum and/or maximum
                                        acreage limits for any crop.
                                        <a id="crop_limit" class="help_Infromation_PopUp" href="javascript:;"><img
                                                src="<c:url value="/images/i-icon.png"/>"></a>
                                    </div>

                                </div>

                                <div class="ques">
                                    <div class="table-responsive">  <%--crop-limits-tbl-fixd-hdr--%>
                                        <table id="crop_limits_table" class="table table-striped crop-limits-tbl tbl-bordr tbl-fixd-hdr tblbrdr output_table scroll"
                                               cellspacing="0" width="100%">
                                            <thead>
                                            <tr class="tblheader text-center add-fieldi">
                                                <td class="tblbrdr text-center add-fieldi">Modify</td>
                                                <td class="tblbrdr text-center add-fieldi">Crop</td>
                                                <!-- @changed - Abhishek 	@date - 23-01-2016		@desc - according to silde# 130of 01042015-->
                                                <%--<td class="text-center add-fieldi">Minimum Acres <img src="<c:url value="/images/i-img.png"/>"></td>
                                                <td class="text-center">Maximum Acres <img src="<c:url value="/images/i-img.png"/>"></td>--%>
                                                <td class="text-center add-fieldi">Minimum Acres
                                                    <a id="crop_limit_min" class="help_Infromation_PopUp"
                                                       href="javascript:;"><img
                                                            src="<c:url value="/images/i-img.png"/>"></a>
                                                </td>
                                                <td class="text-center add-fieldi">Minimum Acres %
                                                    <%--<a id="crop_limit_min_percentage" class="help_Infromation_PopUp" href="javascript:;"><img src="<c:url value="/images/i-img.png"/>"></a>--%>
                                                </td>
                                                <td class="text-center">Maximum Acres
                                                    <a id="crop_limit_max" class="help_Infromation_PopUp"
                                                       href="javascript:;"><img
                                                            src="<c:url value="/images/i-img.png"/>"></a>
                                                </td>
                                                <td class="text-center">Maximum Acres %
                                                    <%--<a id="crop_limit_max_percentage" class="help_Infromation_PopUp" href="javascript:;"><img src="<c:url value="/images/i-img.png"/>"></a>--%>
                                                </td>
                                            </tr>
                                            </thead>
                                            <tbody class="scrollbar-dynamic scrollDiv" id="crop_limits_table_tbody">

                                            <c:set var="rowCountForCropLimit" value="1"/>
                                            <c:forEach var="cropListForCropLimit" items="${model.cropTypeView}">
                                                <!--get crop list from CropView object -->
                                                <c:if test="${cropListForCropLimit.selected}">
                                                    <tr id="crop_limits_table_tbody_row__${rowCountForCropLimit}"
                                                        class="tblbclgrnd text-center">
                                                        <td class="tblft1"></td>
                                                        <td id="crop_limits_table_crop_name__${rowCountForCropLimit}"
                                                            class="tblft1">${cropListForCropLimit.cropName}</td>
                                                        <td class="success croplimit">
                                                            <input type="text"
                                                                   id="crop_limits_crop_minimum_acres__${rowCountForCropLimit}"
                                                                   class="minCropAcreage"
                                                                   onkeypress="return isValidNumberValue(event)"
                                                                   onchange="addCommaSignWithOutDollarDot(this); calculatePercentageOfMinAcreage(this);"
                                                                   value="${cropListForCropLimit.minimumAcres}">

                                                        </td>
                                                        <td class="success croplimit">
                                                            <input type="text"
                                                                   onkeypress="return isValidNumberValue(event)"
                                                                   class="minCropAcreagePercentage"
                                                                   onchange="calculatePercentageOfMinAcreagePercent(this); return false;"
                                                                   value="${cropListForCropLimit.minimumAcresPercentage}">
                                                        </td>
                                                        <td class="success croplimit">
                                                            <input type="text"
                                                                   id="crop_limits_crop_maximum_acres__${rowCountForCropLimit}"
                                                                   class="maxCropAcreage maxCropAcrageCount"
                                                                   onkeypress="return isValidNumberValue(event)"
                                                                   onchange="addCommaSignWithOutDollarDot(this); calculatePercentageOfMaxAcreage(this); return false;"
                                                                   value="${cropListForCropLimit.maximumAcres}">
                                                            <a id="popoverPercentageHelp" class="help_Infromation_PopUp"
                                                               href="javascript:;" style="display: none"><img src="<c:url value="/images/i-img.png"/>"></a>
                                                        </td>
                                                        <td class="success croplimit">
                                                            <input type="text"
                                                                   <%--id="group_crop_maximum_acreage_percentage"--%>
                                                                   onkeypress="return isValidNumberValue(event)"
                                                                   class="maxCropAcreagePercentage"
                                                                   onchange="calculatePercentageOfMaxAcreagePercentage  (this); return false;"
                                                                   value="${cropListForCropLimit.maximumAcresPercentage}">
                                                            <a id="popoverPercentageHelp" class="help_Infromation_PopUp"
                                                               href="javascript:;" style="display: none"><img src="<c:url value="/images/i-img.png"/>"></a>
                                                        </td>

                                                    </tr>
                                                    <c:set var="rowCountForCropLimit"
                                                           value="${rowCountForCropLimit+1}"/>
                                                </c:if>
                                            </c:forEach>


                                            </tbody>
                                            <tbody id="crop_contract_table_tbody" style="border-top: 0px">
                                            <c:forEach var="cropListForforwardSale" items="${model.cropTypeView}">
                                                <!--get crop list from CropView object -->
                                                <c:if test="${cropListForforwardSale.selected && cropListForforwardSale.firmchecked eq 'true'}">
                                                    <tr class="tblbclgrnd text-center">
                                                        <td class="tblft1"></td>
                                                        <td class="tblft1">${cropListForforwardSale.cropName} (Firm)
                                                        </td>
                                                        <!--	@changed - Abhishek		@date - 30-12-2015		@desc - switched below two TD	-->
                                                        <td class="success croplimit"><input type="text"
                                                                                             value="${cropListForforwardSale.acresStr}"
                                                                                             disabled="disabled"
                                                                                             class="minCropAcreage">
                                                        </td>
                                                        <td class="success croplimit">
                                                            <input type="text"
                                                                   onkeypress="return isValidNumberValue(event)"
                                                                   class="minCropAcreagePercentage"
                                                                   disabled="disabled"
                                                                   onchange="calculatePercentageOfMinAcreage(this); return false;"
                                                                   value="${cropListForforwardSale.forwardAcresStr}">
                                                        </td>
                                                        <td class="success croplimit">NA</td>
                                                        <td class="success croplimit">NA</td>
                                                    </tr>
                                                </c:if>
                                            </c:forEach>
                                            </tbody>
                                            <tbody id="group_table_tbody" style="border-top: 0px">
                                            <c:set var="groupCount" value="1"/>
                                            <c:forEach var="groupList" items="${model.cropsGroupList}">
                                                <tr id="group_table_tbody_row_${groupCount}"
                                                    class="tblbclgrnd text-center">
                                                    <td class="tblft1">
                                                        <input type="checkbox"
                                                               id="group_crop_check_acres__${groupCount}"
                                                               name="groupNameSelection[]">
                                                    </td>
                                                    <td id="group_table_group_name_${groupCount}"
                                                        class="tblft1">${groupList.cropsGroupName}</td>
                                                    <td class="success croplimit">
                                                        <input type="text" id="group_crop_minimum_acres__${groupCount}"
                                                               onkeypress="return isValidNumberValue(event)"
                                                               class="minCropAcreage"
                                                               onchange="calculatePercentageOfMinAcreage(this); return false;"
                                                               value="${groupList.minimumAcres eq 0 ? '' : groupList.minimumAcres}">
                                                    </td>
                                                    <td class="success croplimit">
                                                        <input type="text" onkeypress="return isValidNumberValue(event)"
                                                               class="minCropAcreagePercentage"
                                                               onchange="calculatePercentageOfMinAcreage(this); return false;"
                                                               value="${groupList.minimumAcresPercentage}">
                                                    </td>
                                                    <td class="success croplimit">
                                                        <input type="text" id="group_crop_maximum_acres__${groupCount}"
                                                               onkeypress="return isValidNumberValue(event)"
                                                               class="maxCropAcreage"
                                                               onchange="calculatePercentageOfMaxAcreage(this); return false;"
                                                               value="${groupList.maximumAcres eq 0 ? '' : groupList.maximumAcres}">
                                                    </td>
                                                    <td class="success croplimit">
                                                        <input type="text" onkeypress="return isValidNumberValue(event)"
                                                               class="maxCropAcreagePercentage"
                                                               onchange="calculatePercentageOfMaxAcreage(this); return false;"
                                                               value="${groupList.maximumAcresPercentage}">
                                                    </td>
                                                </tr>
                                                <c:set var="groupCount" value="${groupCount+1}"/>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="clearfix"></div>

                                <div class="yellobtn pre_next">
                                    <!-- <a onclick="showCropInsurancePage()">Next</a> -->
                                    <!-- @Changed - Abhishek @Date - 25-11-2015 -->
                                    <%--<a onclick="saveAllFarmInformation()">Analyze</a>--%>
                                    <a onclick="openStrategyOrBaselinePopup(this); return false;">Analyze</a>
                                </div>
                                <div class="yellobtn pre_next">
                                    <a id="submit"
                                       onclick="callMethodForPageChangeAndProgressBarImage(8, 7); return false;">Previous</a>
                                </div>
                                <!-- <div class="yellobtn pre_next"><a id="submit" >Add Additional Group</a></div> -->
                            </div>
                        </div>
                        <!-- @end #forward_sales -->
                        <div id="crop_insurance_div" class="show_hide_class hidden">
                            <div class="form_area">
                                <h2 class="field-heading">Crop Insurance</h2>
                                <div class="clearfix"></div>
                                <div class="info">
                                    Enter information for each of these crops. <img
                                        src="<c:url value="/images/i-icon.png"/>">
                                </div>
                                <div class="ques">
                                    <div class="table-responsive">
                                        <table class="table table-striped tbl-bordr  tblbrdr"
                                               cellspacing="0" width="100%">
                                            <thead>
                                            <tr class="tblhd text-center add-fieldi">
                                                <td class="tblbrdr text-center add-fieldi">Crop</td>
                                                <td class="text-center add-fieldi">Crop Insurance</td>
                                                <td class="text-center">Type 1</td>
                                                <td class="text-center">Type 2</td>
                                                <td class="text-center">Type 3</td>
                                                <td class="text-center">Acres</td>
                                                <td class="text-center">Premium</td>
                                            </tr>
                                            </thead>
                                            <tbody id="crop_insurance_table_tbody">
                                            <!-- <tr class="tblgrn text-center" id="crop_insurance_table_tbody_row__1">
            <td class="tblft1" id="crop_insurance_table_tbody_row_crop_name__1">Crop1</td>
             <td class="success infotext"><input type="checkbox" id="crop_insurance_table_crop_insurance__1" /></td>
             <td class="success infotext"><input type="text" id="crop_insurance_table_crop_type_1__1" /></td>
              <td class="success infotext"><input type="text" id="crop_insurance_table_crop_type_2__1" /></td>
              <td class="success infotext"><input type="text" id="crop_insurance_table_crop_type_3__1" /></td>
               <td class="success cinfotext" id="crop_insurance_table_crop_arces__1" ></td>
               <td class="success infotext" id="crop_insurance_table_crop_premium__1" ></td>
        </tr>
        <tr class="tblbclgrnd text-center">
            <td class="tblft1">Crop2</td>
            <td class="infotext"><input type="checkbox" name=""></td>
             <td class="infotext"><input type="text"></td>
              <td class="infotext"><input type="text"></td>
              <td class="infotext"><input type="text"></td>
              <td class="infotext"></td>
              <td class="infotext"></td>
        </tr>
        <tr class="tblgrn text-cenaddPopupNegativeValueter">
            <td class="tblft1">Crop3</td>
             <td class="success infotext"><input type="checkbox"></td>
             <td class="success infotext"><input type="text"></td>
              <td class="success infotext"><input type="text"></td>
              <td class="success infotext"><input type="text"></td>
              <td class="success infotext"></td>
              <td class="success infotext"></td>
        </tr>
        <tr class="tblbclgrnd text-center">
            <td class="tblft1">Crop4</td>
             <td class="infotext"><input type="checkbox"></td>
             <td class="infotext"><input type="text"></td>
              <td class="infotext"><input type="text"></td>
              <td class="infotext"><input type="text"></td>
              <td class="infotext"></td>
              <td class="infotext"></td>
        </tr>
        <tr class="tblgrn text-center">
            <td class="tblft1">Cropn</td>
             <td class="success infotext"><input type="checkbox"></td>
             <td class="success infotext"><input type="text"></td>
              <td class="success infotext"><input type="text"></td>
              <td class="success infotext"><input type="text"></td>
              <td class="success infotext"></td>
              <td class="success infotext"></td>
        </tr> -->

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="clearfix"></div>

                                <div class="yellobtn pre_next">
                                    <a onclick="saveAllFarmInformation()">Analyze</a>
                                </div>
                                <div class="yellobtn pre_next">
                                    <a id="submit" onclick="showCropLimitPage()">Previous</a>
                                </div>

                            </div>
                        </div>
                        <!-- @end #crop_insurance -->
                    </div>
                </div>
        </section>


    </div>
</div>
<div id="add-newresource-popup" class="common-pop-class-for-all">
    <div id="popupContact">
        <!-- Planning Form -->
        <div class="popup_section">
            <img onclick="div_hide6()" src="<c:url value="/images/cross.png"/>" id="close">
            <h2 style="padding: 0 0 0 22px; color: #BE922F; font-weight: bold;"
                class="popupheadercrop">Add Resource</h2>
            <div style="padding: 5px 20px;" class="popupform messagepopup">
                <div class="clearfix"></div>
                <div class="addcroppup">
                    <label>Resource Name</label> <input type="text" id="resourse_name"/>
                </div>
                <div class="addcroppup">
                    <label>Unit of measure </label>
                    <!-- <input type="text" id="resourse_unit_name" /> -->
                    <div class="select_uom">
                        <select id="resourse_unit_name">
                            <option value="">--Select UOM--</option>
                            <option value="acre-ft">acre-ft</option>
                            <option value="acres/hr">acres/hr</option>
                            <option value="acre-in">acre-in</option>
                            <option value="bales">bales</option>
                            <option value="bu">bu</option>
                            <option value="cwt">cwt</option>
                            <option value="gal/hr">gal/hr</option>
                            <option value="gal/acre">gal/acre</option>
                            <option value="hrs/mo">hrs/mo</option>
                            <option value="hrs/wk">hrs/wk</option>
                            <option value="lbs/acre">lbs/acre</option>
                            <option value="lit/ha">lit/ha</option>
                        </select>
                    </div>
                </div>
                <div class="clearfix"></div>
                <div style="margin: 10px 0px;" class="yellobtn submit">
                    <a id="submit" onclick="addNewResource()">Add resource</a>
                </div>
            </div>

        </div>
    </div>
</div>

<div id="add-newCropProductionCostsDetails-popup"
     class="common-pop-class-for-all">
    <div id="popupContact">
        <!-- Planning Form -->
        <div class="popup_section">
            <img onclick="div_hide10()" src="<c:url value="/images/cross.png"/>" id="close">
            <h2 style="padding: 0 0 0 22px; color: #BE922F; font-weight: bold;" class="popupheadercrop">
                <span id="add-cost-component-span-dynamic">Add Cost Component</span>
            </h2>
            <div style="padding: 5px 20px;" class="popupform messagepopup">
                <div class="clearfix"></div>
                <div class="addcroppup">
                    <label>Cost Component</label> <input type="text" id="crop-optional-cost-components"/>
                </div>
                <div class="clearfix"></div>
                <div style="margin: 10px 0px;" class="yellobtn submit">
                    <a id="add-new-componemt-button" onclick="addNewCostComponents(1)">Add</a>
                </div>
                <div style="margin: 10px 0px;" class="yellobtn submit">
                    <a id="update-componemt-button" onclick="updateCostComponents(2)">Update</a>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- add group popup -->
<div class="common-pop-class-for-all" id="add-new-group-and-crop">
    <div id="popupContact">
        <!-- Planning Form -->
        <div class="popup_section">
            <img onclick="div_hide11()" src="<c:url value="/images/cross.png"/>" id="close">

            <!-- @Abhishek 25-11-2015 changed from Crop Group to Add Crop Group -->
            <h2 style="padding: 0 0 0 22px; color: #BE922F; font-weight: bold;" class="popupheadercrop" id="add_group">
                Add Crop Group
                <span><a id="add_group" class="help_Infromation_PopUp" href="javascript:;"><img
                        src="<c:url value="/images/i-icon.png"/>"></a></span>
            </h2>
            <h2 style="padding: 0 0 0 22px; color: #BE922F; font-weight: bold;" class="popupheadercrop"
                id="modify_group">Modify Group</h2>
            <div style="padding: 5px 20px;" class="popupform messagepopup">
                <div class="addcroppup">

                    <!-- @New - Abhishek 25-11-2015 -->
                    <style type="text/css">
                        .yellobtn a {
                            padding: 5px 15px;
                        }
                    </style>

                    <label>Crop Group Name</label> <input type="text" name="Grop Name" id="group_name">
                    <div class="clearfix"></div>
                    <label style="margin-top: 5px;">Select crops for this group</label>
                    <div class="group-button-select-fix">
                        <div class="multiselect_crop" style="float: left;width: 70%;">
                            <select class="multiselect" id="gropofcrop" multiple="multiple">
                                <c:forEach var="listValue" items="${model.cropTypeView}">
                                    <!--get crop list from CropView object -->
                                    <c:if test="${listValue.selected}">
                                        <option value="${listValue.cropName}">${listValue.cropName}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                            <%--<script type="text/javascript">
                                $(document).ready(function() {
                                    $(".multiselect", this.el).multiselect({
                                        includeSelectAllOption : true
                                    });
                                });
                            </script>
                            <script type="text/javascript">
                                $(document).ready(function() {
                                    $(".btn-group").click(function() {
                                        $(this).toggleClass("open");
                                    });
                                });
                            </script>--%>
                        </div>

                        <div class="yellobtn submit">
                            <a id="add_group_button" onclick="addNewGroup()">Add Group</a>
                            <a id="modify_group_button" onclick="modifyGroup()">Update Group</a>
                        </div>

                    </div>

                </div>

            </div>

        </div>
    </div>
</div>
<!-- Raja - 14 Dec, 2015 - Add popup on mouseover if Estimated Income is negative -->
<div style="display: none;" id="critical-message-pop-up">
    <div id="popupContact">
        <!-- Planning Form -->
        <div class="popup_section">
            <img src="<c:url value="/images/cross.png"/>" onclick="hidePotentialProfitCriticalMessagePopup()"
                 id="close">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="increase_profit">
                    <p>
                        <span class="cropNameForPopup"></span> profit per acre (<span
                            id="potentialProfitForPopup"></span>) is less than zero.<br>
                        <span class="cropNameForPopup"></span> will not be included in the strategy since it has a
                        negative profit per acre unless you enter a minimum acreage limit for <span
                            class="cropNameForPopup"></span>.
                    </p>
                </div>
                <!-- <div class="decrease_profit">
                    <p>Decreasing <span id="resourceNameDec"></span> will decreaseEstimated Income by <span id="lossBy1Dollar"></span> for eachdollar removed down to <span id="downResourceLimit"></span></p>
                </div> -->
            </div>
        </div>
    </div>
</div>

<div style="display: none;" id="save-strategy-popoup" class="pop-up">
    <div class="pop-up-body">
        <!-- Planning Form -->
        <div class="popup_section">
            <img onclick="closeStrategyOrBaselinePopup(); return false;" src="<c:url value="/images/cross.png"/>"
                 class="img-close">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="panel panel-yellow">
                    <div class="panel-heading text-center">
                        <%--<label style="cursor: pointer"><h4>How do you want to save this</h4></label>--%>
                        <label>
                            <h4>Save as the Baseline strategy<br>or as a new strategy?</h4>
                        </label>
                        <a id="new_strategy" class="help_Infromation_PopUp" href="javascript:;">
                            <img src="<c:url value="/images/i-icon.png"/>">
                        </a>
                    </div>

                    <div class="panel-body" style="display: block">
                        <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none medium-height-overflow medium-height-overRide">
                            <div class="form-group form-group1">
                                <label>
                                    <input type="radio" name="baselineOrStrategyRadio" value="baseline" checked>
                                    Baseline Strategy
                                </label>
                            </div>
                            <div class="form-group form-group1">
                                <label>
                                    <input type="radio" name="baselineOrStrategyRadio" value="new">
                                    New Strategy
                                </label>
                            </div>
                            <div class="clearfix"></div>
                            <div class="text-center strategy-name-div" style="display: none">
                                <label>Strategy Name</label>
                                <input type="text" id="new-strategy-name" style="width: 50%"/>
                            </div>
                            <div class="clearfix"></div>
                            <div style="margin-top: 1%">
                                <button class="alertify-button alertify-button-ok pull-right"
                                        onclick="saveAllFarmInformation(); return false;">Ok
                                </button>
                                <button class="alertify-button alertify-button-cancel pull-right"
                                        onclick="closeStrategyOrBaselinePopup(); return false;">Cancel
                                </button>
                            </div>
                        </div>

                    </div>

                </div>

            </div>
        </div>
    </div>
</div>
<div style="display: none;" id="negative-message-pop-up">
    <div id="popupContact">
        <!-- Planning Form -->
        <div class="popup_section">
            <img src="<c:url value="/images/cross.png"/>" onclick="$('#negative-message-pop-up').hide(); return false;" id="close">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="increase_profit">
                    <p>
                        Warning: Estimated income for forward sales of
                        <span id="cropName"></span>  is   <span style="color: red" id="profitperacre"></span> per acre due to the combination of contract price, crop yield
                        and production costs .<br>
                        Since this is a Firm contract, acreage will be assigned to  <span id="cropName2"></span> even though the per acre income is <span style="color: red" id="profitperacre2"></span> .<br>
                        If you do not want any acres assigned to forward sales of <span id="cropName3"></span>, uncheck the “Firm” box .
                    <p/>

                </div>
                <!-- <div class="decrease_profit">
                    <p>Decreasing <span id="resourceNameDec"></span> will decreaseEstimated Income by <span id="lossBy1Dollar"></span> for eachdollar removed down to <span id="downResourceLimit"></span></p>
                </div> -->
            </div>
        </div>
    </div>
</div>
<script type="x-jQuery-tmpl" id="additional-crop-income-tbody-tmpl">
    <tr class="tblbclgrnd text-center">
        <td class="tblft1 cropNameSpecific">{{= cropName}}</td>
        <td class="success infotext"><input type="text" class="governmentPaymentsSpecific" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this); calculateAdditionalCropProfit(this, true); return false;"></td>
        <td class="success infotext"><input type="text" class="coProductsSpecific" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this); calculateAdditionalCropProfit(this, true); return false;"></td>
        <td class="success infotext"><input type="text" class="additionalVariableCostSpecific" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this); calculateAdditionalCropProfit(this, true); return false;"></td>
        <td class="success infotext"><input type="text" class="additionalIncomeSpecific" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this); return false;" disabled></td>
    </tr>


</script>

<script type="x-jQuery-tmpl" id="optional-crop-info-table-tmpl">
	<div class="table-responsive" name="optionalCropInformationDetail_{{= cropName}}">
		<table class="table table-striped tbl-bordr  tblbrdr" cellspacing="0" width="100%">
			<thead>
			   <tr class="tblhd add-fieldi">
				   <td class="text-center">Modify</td>
				   <td class="tblbrdr add-fieldi">Component</td>
				   <td class="text-center add-fieldi">Units</td>
				   <td class="text-center">$ per Unit</td>
				   <td class="text-center">$ per Acre</td>
			   </tr>
			</thead>
			<tbody>
				{{each(key, optionalCropInfoHeads) optionalCropInfoHeadsArr}}
				   <tr class="tblgrn">
					   <td class="tblft1 text-center"><input type="checkbox"></td>
					   <td class="tblft1 text-left">{{= optionalCropInfoHeads}}</td>
					   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td>
					   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td>
					   <td class="success infotext"></td>
				   </tr>
				{{/each}}
			</tbody>
			<tfoot>
				<tr class="tblgrn">
				   <td class="tblft1 optncal" colspan="2">Calculate</td>
				   <td class="success"></td>
				   <td class="success"></td>
				   <td class="success"></td>
				</tr><tr class="tblft text-center">
				   <td class="tblft1" colspan="2">Total Variable Cost per Acre: </td>
				   <td><input type="hidden"> </td>
				   <td></td>
				   <td class="optndoller">$0.00</td>
				</tr>
			</tfoot>
        </table>
    </div>

</script>

<script type="text/javascript">
    $(function(){
        $('.scrollDiv').slimScroll({
//            height: 'auto',
        });
    });
</script>



<%@ include file="common/right_slider.jsp" %>