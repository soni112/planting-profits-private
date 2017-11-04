<!--
@author Abhishek
Date 24-11-2015
-->
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script>
    var farmId = '${model.farm.farmId}';
    <%--var strategyId = '${scenario.strategyId}';--%>
</script>
<style type="text/css">
    .hidden {
        display: none;
    }

    .open {
        display: block;
    }
</style>

<div class="leftside">
    <%@ include file="../manage-farm/common/menu.jsp" %>
    <div class="mainsection farm_section">

        <div class="right_farm_form_filled">
            <div class="edit_output_details_link">
                <a class="alertify-button alertify-button-ok remove-text-deco" href="view-farm-info.htm?farmId=${farmId}">Change Farm Information</a>
            </div>
            <div class="output_base" id="scenarion-container">
                <h3>${model.farm.farmName}</h3>

                <div class="base_white">
                    <ul id="sidemenu" class="total_output">
                        <li class="active">
                            <a href="#createScenario" class="open"><div class="scnr_detail">Create New Scenario</div></a>
                        </li>
                        <li>
                            <a href="#viewEditScenario"><div class="scnr_detail">View/Modify Existing Scenario</div></a>
                        </li>
                        <li>
                            <a href="#applyScenarioToCurrent" onclick="openScenarioAnalysisPopup(); return false;"><div class="scnr_detail">Scenario Analysis</div></a>
                        </li>
                        <li>
                            <a href="#insurance-evaluator-tab"><div class="scnr_detail">Evaluate Crop Insurance</div></a>
                        </li>
                    </ul>

                    <!-----start tab rightside description-------------------------------------->

                    <div id="createScenario" class="contentblock">
                        <a href="<c:url value="output-farm-info.htm?farmId=${farmId}"/>" class="alertify-button alertify-button-ok pull-right" style="margin-top: 4px;">Back to Baseline</a>
                        <ul class="potential_criteria">
                            <li>Name of Scenario:
                                <div class="scenario_global">
                                    <input type="text" name="scenario_name" value=""/>
                                    <div class="pull-right">
                                        <a class="help_Infromation_PopUp scenarioName"><img src="<c:url value="/images/i-icon.png" />"></a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                        <!---------------------start tab------------------------------->
                        <div class="addcrop">
                            <div class="clearfix"></div>
                            <div id="tabs-container">
                                <ul class="tabs-menu list-inline">
                                    <li class="current"><a href="#tabScenarioGlobalDetails">Global</a></li>
                                    <li><a href="#tabScenarioCropSpecificDetails">Crop-Specific</a></li>
                                    <li>
                                        <a href="#" class="help_Infromation_PopUp globalCropTabHelp"
                                           style="padding-left: 10px">
                                            <img src="<c:url value="/images/i-icon.png" />">
                                        </a>
                                    </li>
                                </ul>
                                <div class="tab">

                                    <!--         change start -->

                                    <div id="tabScenarioGlobalDetails" class="tab-content" style="display: block">
                                        <div class="ques">
                                            <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">
                                                <div class="sec_mrgn_bottom">
                                                    <label class="resource_label"> Increase/Decrease all crop prices by:
                                                        <div class="pull-right">
                                                            <div class="pull-right">
                                                                <div class="secnario_per">%</div>
                                                                <a class="help_Infromation_PopUp globalPrices"><img
                                                                        src="<c:url value="/images/i-icon.png" />"></a>
                                                            </div>
                                                            <div class="scenario_global">
                                                                <input type="text" id="globalCropPriceCreate"
                                                                       name="scenario_global_crop_price" maxlength="5"
                                                                       placeholder="0"
                                                                       onKeyPress="return isValidNumberValueForForCastSA(event);">
                                                            </div>
                                                        </div>
                                                    </label>

                                                </div>
                                                <div class="sec_mrgn_bottom">
                                                    <label class="resource_label"> Increase/Decrease all crop yields by:
                                                        <div class="pull-right">
                                                            <div class="pull-right">
                                                                <div class="secnario_per">%</div>
                                                                <a class="help_Infromation_PopUp globalYield"><img
                                                                        src="<c:url value="/images/i-icon.png" />"></a>
                                                            </div>
                                                            <div class="scenario_global">
                                                                <input type="text" id="globalYieldCreate"
                                                                       name="scenario_global_crop_yields" maxlength="5"
                                                                       placeholder="0"
                                                                       onKeyPress="return isValidNumberValueForForCastSA(event);">
                                                            </div>
                                                        </div>
                                                    </label>


                                                </div>
                                                <div class="sec_mrgn_bottom">
                                                    <label class="resource_label"> Increase/Decrease all crop variable
                                                        production costs by:
                                                        <div class="pull-right">
                                                            <div class="pull-right">
                                                                <div class="secnario_per">%</div>
                                                                <a class="help_Infromation_PopUp globalVarCost"><img
                                                                        src="<c:url value="/images/i-icon.png" />"></a>
                                                            </div>

                                                            <div class="scenario_global">
                                                                <input type="text" id="globalVarCostCreate"
                                                                       name="scenario_global_crop_prod_cost"
                                                                       maxlength="5" placeholder="0"
                                                                       onKeyPress="return isValidNumberValueForForCastSA(event);"/>
                                                            </div>
                                                        </div>
                                                    </label>

                                                </div>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">
                                                <label class="resource_label"> Comment:</label>
                                                <textarea name="scenarioGlobalComment"
                                                          class="scenarioTextarea"></textarea>
                                            </div>
                                        </div>
                                    </div>

                                    <!--         change end -->
                                    <div id="tabScenarioCropSpecificDetails" class="tab-content">
                                        <div class="ques">
                                            <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">
                                                <!---start table----->
                                                <div class="panel-body" style="display: block;">
                                                    <div class="table-responsive" style="max-height: 225px;">
                                                        <table width="100%" cellspacing="0"
                                                               id="createnewScenarionCropSpecificTable"
                                                               class="table table-striped tbl-bordr  tblbrdr">
                                                            <thead>
                                                            <tr class="tblhd text-center add-fieldi">
                                                                <td class="tblbrdr text-center add-fieldi">Crop</td>
                                                                <td class="text-center add-fieldi">% Price Change (+/-)
                                                                    <a href="#"
                                                                       class="help_Infromation_PopUp cropSpecificPrice"
                                                                       style="padding-left: 10px">
                                                                        <img src="<c:url value="/images/i-icon.png" />">
                                                                    </a>
                                                                </td>
                                                                <td class="text-center">% Yield Change (+/-)
                                                                    <a href="#"
                                                                       class="help_Infromation_PopUp cropSpecificCropYeild"
                                                                       style="padding-left: 10px">
                                                                        <img src="<c:url value="/images/i-icon.png" />">
                                                                    </a>
                                                                </td>
                                                                <td class="text-center">% Production Costs Change (+/-)
                                                                    <a href="#"
                                                                       class="help_Infromation_PopUp cropSpecificVarCost"
                                                                       style="padding-left: 10px">
                                                                        <img src="<c:url value="/images/i-icon.png" />">
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                            </thead>
                                                            <tbody>

                                                            <c:forEach var="cropType" items="${model.cropList}">
                                                                <c:if test="${cropType.selected}">
                                                                    <tr class="tblgrn text-center"
                                                                        data-id="${cropType.id}">
                                                                        <td class="tblft1">${cropType.cropName}</td>
                                                                        <td class="success infotext"><input type="text"
                                                                                                            value=""
                                                                                                            class="globalCropPriceCreate"
                                                                                                            maxlength="6"
                                                                                                            onkeypress="return isValidNumberValueForForCastSA(event)"
                                                                                                            placeholder="0 %"/>
                                                                        </td>
                                                                        <td class="success infotext"><input type="text"
                                                                                                            value=""
                                                                                                            class="globalYieldCreate"
                                                                                                            maxlength="6"
                                                                                                            onkeypress="return isValidNumberValueForForCastSA(event)"
                                                                                                            placeholder="0 %"/>
                                                                        </td>
                                                                        <td class="success infotext"><input type="text"
                                                                                                            value=""
                                                                                                            class="globalVarCostCreate"
                                                                                                            maxlength="6"
                                                                                                            onkeypress="return isValidNumberValueForForCastSA(event)"
                                                                                                            placeholder="0 %"/>
                                                                        </td>
                                                                    </tr>
                                                                </c:if>
                                                            </c:forEach>

                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                                <!----- end table ------>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">
                                                <label class="resource_label"> Comment:</label>
                                                <textarea name="scenarioCropSpecificComment"
                                                          class="scenarioTextarea"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--------------end tab------------------------->
                        <div class="clearfix"></div>
                        <div class="yellobtn pre_next">
                            <a href="#" onclick="openStrategySelectPopup();">Save</a>
                        </div>
                        <%--</div>--%>
                    </div>
                    <!-- @end #createNewScenario -->

                    <div id="viewEditScenario" class="contentblock hidden">
                        <a href="<c:url value="output-farm-info.htm?farmId=${farmId}"/>" class="alertify-button alertify-button-ok pull-right" style="margin-top: 4px;">Back to Baseline</a>
                        <%--<div class="text_field">--%>
                        <div class="clearfix"></div>
                        <ul class="potential_criteria">
                            <li>
                                <label class="resource_label"> Saved Scenarios:
                                    <div class="scenario_global">
                                        <select id="saved_scenario" onchange="loadSelectedScenarioData(this);">
                                            <option value="0, 0" selected>--Please Select--</option>
                                            <c:forEach var="scenarioView" items="${model.savedScenarioData}">
                                                <option value="${scenarioView.scenarioId}, ${scenarioView.farmCustomStrategy.id}">${scenarioView.scenarioName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </label>
                            </li>
                        </ul>
                        <!---------------------start tab------------------------------->
                        <div class="addcrop">
                            <div class="clearfix"></div>
                            <div id="tabs-container1">
                                <ul class="tabs-menu list-inline">
                                    <%--    @changed - Abhishek     @date - 27-01-2016      @desc - changed view    --%>
                                    <%--<li class="hidden"><a href="#tabViewEditSavedScenario">Saved Scenarios</a></li>--%>
                                    <li class="current"><a href="#tabViewEditGlobalDetails">Global</a></li>
                                    <li><a href="#tabViewEditCropSpecificDetails">Crop-Specific</a></li>
                                    <li>
                                        <a href="#" class="help_Infromation_PopUp globalCropTabHelp"
                                           style="padding-left: 10px">
                                            <img src="<c:url value="/images/i-icon.png" />">
                                        </a>
                                    </li>
                                </ul>
                                <div class="tab">

                                    <div class="tab-content" id="tabViewEditGlobalDetails" style="display: block">
                                        <div class="ques">
                                            <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">
                                                <div class="sec_mrgn_bottom pull-right" id="cropSpecifcIndicator"
                                                     style="display:none">
                                                    <label class="resource_label" style="color: darkred">Crop-specific values have been previously entered</label>
                                                </div>

                                                <div class="sec_mrgn_bottom">
                                                    <label class="resource_label"> Name of Scenario :
                                                        <div class="pull-right">
                                                            <div class="pull-right">
                                                                <a class="help_Infromation_PopUp scenarioName"><img
                                                                        src="<c:url value="/images/i-icon.png" />"></a>
                                                            </div>

                                                            <div class="scenario_global">
                                                                <input type="text" disabled name="scenario_name"
                                                                       placeholder=""/>
                                                            </div>
                                                        </div>
                                                    </label>

                                                </div>
                                                <div class="sec_mrgn_bottom">

                                                    <label class="resource_label"> Increase/Decrease all crop prices by:
                                                        <div class="pull-right">
                                                            <div class="pull-right">
                                                                <div class="secnario_per">%</div>
                                                                <a class="help_Infromation_PopUp globalPrices"><img
                                                                        src="<c:url value="/images/i-icon.png" />"></a>
                                                            </div>
                                                            <div class="scenario_global">
                                                                <input type="text" disabled
                                                                       name="scenario_global_crop_price"
                                                                       id="globalCropPriceEdit" placeholder="0"
                                                                       onKeyPress="return isValidNumberValueForForCastSA(event);"/>
                                                            </div>
                                                        </div>
                                                    </label>

                                                </div>
                                                <div class="sec_mrgn_bottom">
                                                    <label class="resource_label"> Increase/Decrease all crop yields by:
                                                        <div class="pull-right">
                                                            <div class="pull-right">
                                                                <div class="secnario_per">%</div>
                                                                <a class="help_Infromation_PopUp globalYield"><img
                                                                        src="<c:url value="/images/i-icon.png" />"></a>
                                                            </div>
                                                            <div class="scenario_global">
                                                                <input type="text" disabled
                                                                       name="scenario_global_crop_yields"
                                                                       id="globalYieldEdit" placeholder="0"
                                                                       onKeyPress="return isValidNumberValueForForCastSA(event);"/>
                                                            </div>
                                                        </div>
                                                    </label>

                                                </div>
                                                <div class="sec_mrgn_bottom">
                                                    <label class="resource_label"> Increase/Decrease all crop variable
                                                        production costs by:
                                                        <div class="pull-right">
                                                            <div class="pull-right">
                                                                <div class="secnario_per">%</div>
                                                                <a class="help_Infromation_PopUp globalVarCost"><img
                                                                        src="<c:url value="/images/i-icon.png" />"></a>
                                                            </div>
                                                            <div class="scenario_global">
                                                                <input type="text" disabled
                                                                       name="scenario_global_crop_prod_cost"
                                                                       id="globalVarCostEdit" placeholder="0"
                                                                       onKeyPress="return isValidNumberValueForForCastSA(event);"/>
                                                            </div>
                                                        </div>
                                                    </label>

                                                </div>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">
                                                <label class="resource_label"> Comment:</label>
                                                <textarea name="scenarioGlobalComment"
                                                          class="scenarioTextarea"></textarea>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="tab-content" id="tabViewEditCropSpecificDetails">
                                        <div class="ques">
                                            <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">

                                                <!---start table----->
                                                <div class="panel-body" style="display: block;">
                                                    <div class="table-responsive" style="max-height: 225px;">
                                                        <table width="100%" cellspacing="0"
                                                               id="viewModifyScenarioCropSpecific"
                                                               class="table table-striped tbl-bordr  tblbrdr">
                                                            <thead>
                                                            <tr class="tblhd text-center add-fieldi">
                                                                <td class="tblbrdr text-center add-fieldi">Crop</td>
                                                                <td class="text-center add-fieldi">% Price Change (+/-)
                                                                    <a href="#"
                                                                       class="help_Infromation_PopUp cropSpecificPrice"
                                                                       style="padding-left: 10px">
                                                                        <img src="<c:url value="/images/i-icon.png" />">
                                                                    </a>
                                                                </td>
                                                                <td class="text-center">% Yield Change (+/-)
                                                                    <a href="#"
                                                                       class="help_Infromation_PopUp cropSpecificCropYeild"
                                                                       style="padding-left: 10px">
                                                                        <img src="<c:url value="/images/i-icon.png" />">
                                                                    </a>
                                                                </td>
                                                                <td class="text-center">% Production Costs Change (+/-)
                                                                    <a href="#"
                                                                       class="help_Infromation_PopUp cropSpecificVarCost"
                                                                       style="padding-left: 10px">
                                                                        <img src="<c:url value="/images/i-icon.png" />">
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                            </thead>
                                                            <tbody id="optional_planting_date">
                                                            <c:forEach var="cropType" items="${model.cropList}">
                                                                <c:if test="${cropType.selected}">
                                                                    <tr class="tblgrn text-center"
                                                                        data-id="${cropType.id}">
                                                                        <td class="tblft1">${cropType.cropName}</td>
                                                                        <td class="success infotext"><input disabled
                                                                                                            type="text"
                                                                                                            value=""
                                                                                                            class="globalCropPriceEdit"
                                                                                                            onkeypress="return isValidNumberValueForForCastSA(event)"
                                                                                                            placeholder="0 %"/>
                                                                        </td>
                                                                        <td class="success infotext"><input disabled
                                                                                                            type="text"
                                                                                                            value=""
                                                                                                            class="globalYieldEdit"
                                                                                                            onkeypress="return isValidNumberValueForForCastSA(event)"
                                                                                                            placeholder="0 %"/>
                                                                        </td>
                                                                        <td class="success infotext"><input disabled
                                                                                                            type="text"
                                                                                                            value=""
                                                                                                            class="globalVarCostEdit"
                                                                                                            onkeypress="return isValidNumberValueForForCastSA(event)"
                                                                                                            placeholder="0 %"/>
                                                                        </td>
                                                                    </tr>
                                                                </c:if>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                                <!----- end table ------>
                                            </div>
                                            <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">
                                                <label class="resource_label"> Comment:</label>
                                                <textarea name="scenarioCropSpecificComment"
                                                          class="scenarioTextarea"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--------------end tab------------------------->
                        <%--</div>--%>
                        <div class="yellobtn pre_next">
                            <a href="javascript:;"
                               onclick="window.scenarioId = $('#saved_scenario').find(':selected').val();saveScenarioData('viewEditScenario');">Save</a>
                        </div>
                        <div class="yellobtn pre_next">
                            <a onclick="modifyScenarioHandler();" href="javascript:;">Modify</a>
                        </div>
                        <div class="yellobtn pre_next">
                            <a href="<c:url value="/output-edit-farm-info.htm?farmId="/>${model.farm.farmId}">Cancel</a>
                        </div>
                    </div>
                    <!-- @end #viewEditScenario -->

                    <div id="applyScenarioToCurrent" class="contentblock hidden">
                        <a href="<c:url value="output-farm-info.htm?farmId=${farmId}"/>" class="alertify-button alertify-button-ok pull-right" style="margin-top: 4px;">Back to Baseline</a>
                        <ul class="potential_criteria">
                            <li>
                                <label class="resource_label"> Select Scenario:
                                    <div class="scenario_global">
                                        <select id="savedScenarioForCurrentStrategy">
                                            <option value="0, 0">--Please Select--</option>
                                            <c:forEach var="scenarioView" items="${model.savedScenarioData}">
                                                <option value="${scenarioView.scenarioId}, ${scenarioView.farmCustomStrategy.id}">${scenarioView.scenarioName}</option>
                                            </c:forEach>
                                        </select>

                                    </div>
                                </label>
                                <div class="yellobtn">
                                    <a onclick="getScenarioOutputDetailsForStrategy(); return false;"
                                       href="javascript:;">Apply Scenario</a>
                                </div>
                            </li>
                        </ul>
                        <div class="table-responsive col-lg-12" id="applyToCurrentScenarioOutputDiv">

                            <div id="scenarioOutputTableSection">
                                <div class="pull-right cursor-pointer">
                                    <img src="<c:url value="/images/graph_tab.png"/>" onclick="toggleGraphSection(); return false;"/>
                                </div>

                                <table cellspacing="0" class="table table-striped tbl-bordr  tblbrdr output_table">
                                    <thead>
                                    <tr class="tblhd add-fieldi">
                                        <td class="tblbrdr add-fieldi">Strategy Name</td>
                                        <td class="add-fieldi">Original Est. Income</td>
                                        <td class="add-fieldi">Scenario Est. Income</td>
                                        <td class="add-fieldi">Difference</td>
                                        <td class="add-fieldi">Strategy Acres</td>
                                    </tr>
                                    </thead>
                                    <tbody id="scenarioAnalysisOutputTbody">
                                    <tr class="tblgrn">
                                        <td colspan="5" class="success">Select and apply scenario to view details</td>
                                    </tr>
                                    <%--<c:forEach var="famrOutputDetails" items="${model.outputDetails}">
                                        <tr class="tblgrn">
                                            <td class="success currentScenarioNameSpecific">${famrOutputDetails.strategyName}</td>
                                            <td class="success infotext currentScenarioOrignalSpecific">$${famrOutputDetails.potentialProfit}</td>
                                            <td class="success infotext currentScenarioEstimatedSpecific"></td>
                                            <td class="success infotext currentScenarioDifferenceSpecific"></td>
                                            <td class="success infotext currentScenarioAcreageSpecific">${famrOutputDetails.acreage}</td>
                                        </tr>
                                    </c:forEach>--%>

                                    </tbody>
                                </table>
                            </div>

                            <div id="scenarioOutputGraphSection" style="display: none">
                                <div class="pull-right cursor-pointer" id="tableButtonSection">
                                    <img src="<c:url value="/images/showtext.png"/>" onclick="toggleTableSection(); return false;"/>
                                </div>
                                <div class="clearfix"></div>

                                <div id="scenarioAnalysisChartDiv" class="chart-base" style="max-height: 500px;"></div>


                            </div>

                        </div>

                    </div>
                    <!-- @end #applyScenarioToCurrent -->

                    <div id="insurance-evaluator-tab" class="strategy_block right-strategy-details" style="display: none">
                        <a href="<c:url value="output-farm-info.htm?farmId=${farmId}"/>" class="alertify-button alertify-button-ok pull-right" style="margin-top: 4px;">Back to Baseline</a>
                        <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none overflow-x">
                            <table width="100%" cellspacing="0" class="table table-striped tbl-bordr  tblbrdr">
                                <tbody>
                                <tr class="tblgrn text-center">
                                    <td class="success infotext">Crop Insurance Evaluator under construction</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- @end #insurance-evaluator-tab -->

                </div>
            </div>
        </div>

    </div>


</div>
<%@ include file="../manage-farm/common/right_slider.jsp" %>

<%--    @added - Abhishek       @date - 25-01-2016      @desc - added popup for change in prices of global or crop specific     --%>
<div id="critical-message-pop-up" style="display: none;">
    <div id="popupContact">
        <div class="popup_section">
            <img src="<c:url value="/images/cross.png"/>" onclick="$('#critical-message-pop-up').hide(); return false;" id="close">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="increase_profit">
                    <%--<p> If changed values in crop specific then global prices will not be applied. </p>--%>
                    <p>Crop-specific values override Global values.</p>
                    <p>Crop-specific values will be used for Scenario analysis </p>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="strategy-select-popup" class="pop-up" style="display: none;">
    <div class="pop-up-body">
        <div class="popup_section">

            <div class="potencial_profit_popup">
                <div class="panel panel-yellow">
                    <div class="panel-heading text-center">
                        <label style="cursor: pointer;">Select strategy to associate with the scenario. </label>
                    </div>

                    <div class="panel-body" style="display: block">

                        <c:forEach var="strategy" items="${model.strategyList}">
                            <div class="form-group form-group1">
                                <label>
                                    <input type="radio" name="strategyRadio" value="${strategy.id}"> &nbsp;
                                    &nbsp;${strategy.strategyName}
                                </label>
                            </div>
                        </c:forEach>
                        <div class="clearfix"></div>
                        <button class="alertify-button alertify-button-ok pull-right"
                                id="saveScenariobtn" onclick="saveScenarioData('createScenario')">Save
                        </button>
                        <button class="alertify-button alertify-button-ok pull-right"
                                onclick="closeStrategySelectPopup(); return false;">Cancel
                        </button>

                    </div>

                </div>

            </div>
        </div>
    </div>
</div>

<div id="scenario-analysis-popup" class="pop-up" style="display: none;">
    <div class="pop-up-body">
        <div class="popup_section">

            <div class="potencial_profit_popup">
                <div class="panel panel-yellow">
                    <div class="panel-heading text-center">
                        <label style="cursor: pointer;">Select strategies for scenario analysis. </label>
                    </div>
                    <div class="panel-body" style="display: block">

                        <div id="strategyForScenarioDiv">
                            <c:forEach var="strategy" items="${model.strategyList}">
                                <div class="form-group form-group1">
                                    <label>
                                        <input type="checkbox" name="strategyScenarioCheckbox" value="${strategy.id}">
                                        &nbsp; &nbsp;${strategy.strategyName}
                                    </label>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="clearfix"></div>
                        <button class="alertify-button alertify-button-ok pull-right"
                                onclick="getScenarioOutputDetailsForStrategy(); return false;">Ok
                        </button>
                        <button class="alertify-button alertify-button-cancel pull-right"
                                onclick="closeScenarioAnalysisPopup(true); return false;">Cancel
                        </button>
                    </div>


                </div>

            </div>
        </div>
    </div>
</div>


<%--    @added - Abhishek   @date - 18-01-2016    @desc - for adding panel for graphs and displaying apply to all output    --%>
<link rel="stylesheet" href="<c:url value="/css/sb-admin-2.css"/>" type="text/css" media="all">

<%--<script type="text/javascript" src="js/plugins/jquery.tmpl.min.js"></script>--%>

<!-- @added - Abhishek @date - 08-01-2016 @desc - for creating charts for updated scenario-->
<script type="text/javascript" src="<c:url value="/js/agriculture/output_pie_chart.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/agriculture/scenario/scenario.js"/>"></script>
<script>
    $('#globalCropPriceCreate').keyup(function(){
        if ($(this).val() > 100){
            customAlerts("No numbers above 100");
            $(this).val('');
        }
    });

    $('#globalYieldCreate').keyup(function(){
        if ($(this).val() > 100){
            customAlerts("No numbers above 100");
            $(this).val('');
        }
    });

    $('#globalVarCostCreate').keyup(function(){
        if ($(this).val() > 100){
            customAlerts("No numbers above 100");
            $(this).val('');
        }
    });

</script>
<script type="text/x-jQuery-tmpl" id="scenarioAnalysisOutputTmpl">
    <tr class="tblgrn">
        <td class="success">{{= strategyName}}</td>
        <td class="success">{{= strategyOutput}}</td>
        <td class="success">{{= scenarioOutput}}</td>
        {{if typeof differenceColor != "undefined" && differenceColor == "red"}}
            <td class="success" style="color: {{= differenceColor}}">{{= difference}}</td>
        {{else}}
            <td class="success">{{= difference}}</td>
        {{/if}}
        <td class="success">{{= acreage}}</td>
    </tr>

</script>