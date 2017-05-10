<!-- start -->
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<%--<link href="css/bootstrap-multiselect.css" rel="stylesheet" type="text/css"/>--%>
<%--    @added - Abhishek   @date - 19-04-2016    @desc - for adding panel for graphs and displaying    --%>
<link rel="stylesheet" href="<c:url value="/css/sb-admin-2.css"/>" type="text/css" media="all">
<script>
    <c:set value="${model.farm.farmId}" var="farmId"/>
    var currentFarmId = '${farmId}';
    var contextPath = '<c:out value="${pageContext.servletContext.contextPath}" />';
</script>

<div class="container-fluid">
    <div class="row">
        <div class="leftside">
            <%@ include file="../manage-farm/common/menu.jsp" %>
            <div class="right_farm_form_filled">
                <div class="edit_output_details_link">
                    <a class="alertify-button alertify-button-ok remove-text-deco" href="view-farm-info.htm?farmId=${farmId}">Change Farm Information</a>
                </div>
                <div class="output_base">
                    <h3><span id="headerText">${model.farm.farmName}</span></h3>

                    <div class="base_white strategy_section">
                        <div class="left-farmList">
                            <ul id="sidemenu" class="total_output width-full">
                                <li class="active">
                                    <a href="#compareStrategies"
                                       class="open"
                                       delta="${farmId}">
                                        <div class="scnr_detail">Compare Strategies</div>
                                    </a>
                                </li>
                                <li>
                                    <a href="#manageStrategies"
                                       delta="${farmId}">
                                        <div class="scnr_detail">Manage Strategies</div>
                                    </a>
                                </li>
                                <li>
                                    <a href="#printStrategies"
                                       delta="${farmId}">
                                        <div class="scnr_detail">Print Strategies</div>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <!-----start tab rightside description-------------------------------------->
                        <div id="compareStrategies" class="strategy_block right-strategy-details" style="display: block">
                            <div class="update_values result_str">

                                <div id="tableSectionForStrategy" class="text-center">
                                    <div class="pull-right cursor-pointer">
                                        <img src='<c:url value="/images/graph_tab.png" />' onclick="toggleGraphSection(); return false;" />
                                    </div>
                                    <div class="clearfix"></div>

                                    <div class="addcrop">
                                        <ul class="tabs-menu list-inline">
                                            <li class="current"><a href="#tabCropComparison">Crop Acreage</a></li>
                                            <li><a href="#tabStrategyResourceComparison">Resource Use</a></li>
                                            <li><a href="#tabRiskComparison">Risk Profile</a></li>
                                            <li><a href="#tabConservationComparison">Conservation</a></li>
                                        </ul>
                                        <div class="tab">

                                            <div id="tabCropComparison" class="tab-content" style="display: block">
                                                <div class="ques">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none overflow-x overflow-y-fixed-height">
                                                        <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr output_table text-center">
                                                            <thead>
                                                                <tr class="tblhd add-fieldi" id="cropComparisonThead">
                                                                    <td>Strategy</td>
                                                                    <td>Estimated Income</td>
                                                                    <td>Acreage</td>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="cropOutputDetailsTbody">
                                                                <tr>
                                                                    <td class="success" colspan="5">No strategies created</td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>

                                            <div id="tabStrategyResourceComparison" class="tab-content" style="display: none">
                                                <div class="ques">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none overflow-x overflow-y-fixed-height">
                                                        <table cellspacing="0" class="table table-striped tbl-bordr  tblbrdr output_table text-center">
                                                            <thead>
                                                                <tr class="tblhd add-fieldi" id="resourceComparisonThead">
                                                                    <td>Base-Strategy<br>for Report</td>
                                                                    <td>Strategy</td>
                                                                    <td>Estimated Income</td>
                                                                    <td>Acreage</td>
                                                                </tr>
                                                            </thead>
                                                            <tbody id="resourceOutputDetailsTbody">
                                                                <tr>
                                                                    <td class="success" colspan="5">No strategies created</td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>

                                            <div id="tabRiskComparison" class="tab-content" style="display: none">
                                                <div class="ques">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none overflow-x overflow-y-fixed-height">
                                                        <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr output_table text-center">
                                                            <thead>
                                                            <tr class="tblhd add-fieldi" id="highRiskComparisonThead">
                                                                <td>Base-Strategy<br>for Report</td>
                                                                <td>Strategy</td>
                                                                <td>Estimated Income</td>
                                                                <td>Acreage</td>
                                                            </tr>
                                                            </thead>
                                                            <tbody id="highRiskDetailsTbody">
                                                                <tr>
                                                                    <td class="success" colspan="5">No risk profile crops</td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>

                                            <div id="tabConservationComparison" class="tab-content" style="display: none">
                                                <div class="ques">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none overflow-x overflow-y-fixed-height">
                                                        <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr output_table text-center">
                                                            <thead>
                                                            <tr class="tblhd add-fieldi" id="conservationComparisonThead">
                                                                <td>Base-Strategy<br>for Report</td>
                                                                <td>Strategy</td>
                                                                <td>Estimated Income</td>
                                                                <td>Acreage</td>
                                                            </tr>
                                                            </thead>
                                                            <tbody id="conservationDetailsTbody">
                                                            <tr>
                                                                <td class="success" colspan="5">No crops under conservation practice</td>
                                                            </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>

                                </div>

                                <div id="graphSectionForStrategy" style="display: none">

                                    <div class="pull-right cursor-pointer">
                                        <img src='<c:url value="/images/showtext.png"/>' onclick="toggleTableSection(); return false;" style="margin-right: 15px;"/>
                                    </div>
                                    <div class="clearfix"></div>

                                    <div class="addcrop">
                                        <ul class="tabs-menu list-inline">
                                            <li class="current">
                                                <a href="#tabStrategyComparison">Comparison </a>
                                                <img id="strategy-comparison-info" class="help_Infromation_PopUp" src="<c:url value="/images/i-icon.png"/>">
                                            </li>
                                            <li>
                                                <a href="#tabVarianceComparison">Variance </a>
                                                <img id="strategy-variance-info" class="help_Infromation_PopUp" src="<c:url value="/images/i-icon.png"/>">
                                            </li>
                                        </ul>
                                        <div class="tab">

                                            <div id="tabStrategyComparison" class="tab-content" style="display: block">
                                                <div class="ques">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none overflow-x">
                                                        <div class="form-group form-group1">
                                                            <label>X-Axis</label>
                                                            <select class="form-control" id="xAxisValue">
                                                                <option value="" selected>X-Axis Value</option>
                                                                <c:forEach var="comparisonType" items="${model.strategyComparisonType}">
                                                                    <c:if test="${comparisonType.bit ne 3}">
                                                                        <option value="${comparisonType.bit}">${comparisonType.comparisonStr}</option>
                                                                    </c:if>
                                                                </c:forEach>

                                                                <%--<option value="0">Estimated Income</option>
                                                                <option value="1">Land Used</option>
                                                                <option value="2">Capital Used</option>
                                                                &lt;%&ndash;<option value="3">Crop Acreage Per Crop</option>&ndash;%&gt;
                                                                <option value="4">% Potenial Profit from Single Most Profitable Crop</option>
                                                                <option value="5">% Potenial Profit from Two Most Profitable Crops</option>
                                                                <option value="6">% Potenial Profit Forward Sold</option>
                                                                <option value="7">% Potenial Profit in High Risk Crops</option>
                                                                <option value="8">% Acreage in High Risk Crops</option>
                                                                <option value="9">% Potenial Profit in Conservation Crops</option>
                                                                <option value="10">% Acreage in Conservation Crops</option>
                                                                <option value="11">Estimated Income Given Min Prices and Yields</option>--%>

                                                                <c:forEach var="cropDetails" items="${model.cropDetailsForSelection}">
                                                                    <option value="${cropDetails.key}">${cropDetails.value}</option>
                                                                </c:forEach>

                                                            </select>
                                                        </div>

                                                        <div class="form-group form-group1">
                                                            <label>Y-Axis</label>
                                                            <select class="form-control" id="yAxisValue">
                                                                <option value="" selected>Y-Axis Value</option>
                                                                <c:forEach var="comparisonType" items="${model.strategyComparisonType}">
                                                                    <c:if test="${comparisonType.bit ne 3}">
                                                                        <option value="${comparisonType.bit}">${comparisonType.comparisonStr}</option>
                                                                    </c:if>
                                                                </c:forEach>
                                                                <%--<option value="0">Estimated Income</option>
                                                                <option value="1">Land Used</option>
                                                                <option value="2">Capital Used</option>
                                                                &lt;%&ndash;<option value="3">Crop Acreage Per Crop</option>&ndash;%&gt;
                                                                <option value="4">% Potenial Profit from Single Most Profitable Crop</option>
                                                                <option value="5">% Potenial Profit from Two Most Profitable Crops</option>
                                                                <option value="6">% Potenial Profit Forward Sold</option>
                                                                <option value="7">% Potenial Profit in High Risk Crops</option>
                                                                <option value="8">% Acreage in High Risk Crops</option>
                                                                <option value="9">% Potenial Profit in Conservation Crops</option>
                                                                <option value="10">% Acreage in Conservation Crops</option>
                                                                <option value="11">Estimated Income Given Min Prices and Yields</option>--%>

                                                                <c:forEach var="cropDetails" items="${model.cropDetailsForSelection}">
                                                                    <option value="${cropDetails.key}">${cropDetails.value}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>

                                                        <div class="clearfix"></div>

                                                        <button class="alertify-button alertify-button-ok pull-right"
                                                                onclick="getAndApplyComparisonData(); return false;"
                                                                style="margin-right: 15px;">Graph</button>

                                                        <div class="clearfix"></div>

                                                        <div id="multipleStrategyAnalysisChartDiv" class="chart-base"></div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div id="tabVarianceComparison" class="tab-content" style="display: none">
                                                <button class="btn btn-default pull-right"
                                                        onclick="openVarianceGraphSetupPopup(); return false;">
                                                    <i class="fa fa-bar-chart-o"></i> Graph Setup
                                                </button>
                                                <div id="varianceGraphChartDiv" class="chart-base"></div>
                                            </div>


                                        </div>
                                    </div>



                                </div>

                            </div>
                        </div>

                        <div id="manageStrategies" class="strategy_block right-strategy-details" style="display: none">

                            <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none overflow-x">
                                <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr output_table text-center">
                                    <thead>
                                    <tr class="tblhd add-fieldi">
                                        <td>Select Strategy</td>
                                        <td>Strategy</td>
                                        <td>Estimated Income</td>
                                        <td>Acreage</td>
                                    </tr>
                                    </thead>
                                    <tbody id="deleteStrategiesTbody">
                                        <tr>
                                            <td class="success" colspan="5">No strategies created</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <button class="alertify-button alertify-button-ok pull-right" onclick="deleteStrategy(); return false;">Delete</button>
                            </div>

                        </div>

                        <div id="printStrategies" class="strategy_block right-strategy-details" style="display: none">
                            <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none overflow-x">
                                <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr output_table text-center">
                                    <thead>
                                        <tr class="tblhd add-fieldi">
                                            <td>Select Strategy</td>
                                            <td>Strategy</td>
                                            <td>Estimated Income</td>
                                            <td>Acreage</td>
                                        </tr>
                                    </thead>
                                    <tbody id="printStrategiesTbody">
                                        <tr>
                                            <td class="success" colspan="5">No strategies created</td>
                                        </tr>
                                    </tbody>
                                </table>
                                <button class="alertify-button alertify-button-ok pull-right"
                                        onclick="openStrategySelectionPopup(); return false;">Print</button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../manage-farm/common/right_slider.jsp" %>
    </div>
</div>

<div style="display: none;" id="generatePdf-popup" class="pop-up">
    <div class="pop-up-body">
        <!-- Planning Form -->
        <div class="popup_section">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="panel panel-yellow">
                    <div class="panel-heading text-center">
                        <label>Select the featured strategy for the report.</label>
                        <a id="featuredStrategy" class="help_Infromation_PopUp" href="#">
                            <img src="<c:url value="/images/i-icon.png"/>">
                        </a>
                    </div>

                    <div class="panel-body" style="display: block">
                        <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none medium-height-overflow medium-height-overRide"
                                id="strategySelectionDiv">
                            <div class="form-group form-group1">
                                <label>No custom strategies are present for the farm.</label>
                            </div>
                        </div>

                        <button class="alertify-button alertify-button-ok pull-right" onclick="generateReport(); return false;">Generate Report</button>
                        <button class="alertify-button alertify-button-cancel pull-right" onclick="closeStrategySelectionPopup(); return false;">Cancel</button>

                    </div>

                </div>

            </div>
        </div>
    </div>
</div>

<div style="display: none;" id="strategy-selection-popup" class="pop-up">
    <div class="pop-up-body">
        <!-- Planning Form -->
        <div class="popup_section">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="panel panel-yellow">
                    <div class="panel-heading text-center">
                        <label>Select at least one strategy to view/compare.</label>
                    </div>

                    <div class="panel-body" style="display: block">
                        <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none medium-height-overflow medium-height-overRide"
                             id="strategiesListDiv">
                            <c:forEach var="farmCustomStrategyView" items="${model.farmCustomStrategyList}">
                                <div class="form-group form-group1">
                                    <label>
                                        <input type="checkbox" name="strategyComparisonCheckbox" value="${farmCustomStrategyView.id}">
                                        ${farmCustomStrategyView.strategyName}
                                    </label>
                                </div>
                            </c:forEach>
                        </div>

                        <button class="alertify-button alertify-button-ok pull-right" onclick="getStrategyForFarm(currentFarmId); return false;">Ok</button>
                        <button class="alertify-button alertify-button-cancel pull-right" onclick="closeStrategyComparisonSelectionPopup(); return false;">Cancel</button>

                    </div>

                </div>

            </div>
        </div>
    </div>
</div>

<div style="display: none;" id="view-strategyDetails-popup" class="pop-up">
    <div class="pop-up-body">
        <!-- Planning Form -->
        <div class="popup_section">
            <img onclick="closeStrategyDetailsPopup()" src="<c:url value="/images/cross.png"/> " class="img-close">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="panel panel-yellow">
                    <div class="panel-heading text-center">
                        <label style="cursor: pointer; text-transform: capitalize;"><h4>Strategy Details</h4></label>
                    </div>

                    <div class="panel-body" style="display: block">
                        <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none medium-height-overflow medium-height-overRide">

                            <table cellspacing="0" class="table table-striped tbl-bordr  tblbrdr output_table text-center"
                                    id="strategyDetailsTable">
                                <thead></thead>
                                <tbody></tbody>
                            </table>

                        </div>

                    </div>

                </div>

            </div>
        </div>
    </div>
</div>

<div style="display: none;" id="variance-graphSetup-popup" class="pop-up">
    <div class="pop-up-body">
        <!-- Planning Form -->
        <div class="popup_section">
            <div class="popupform messagepopup potencial_profit_popup">

                <div class="addcrop">
                    <ul class="tabs-menu list-inline">
                        <li class="current">
                            <a href="#tab-variance-information">Information</a>
                            <img id="strategy-variance-information-info" class="help_Infromation_PopUp" src="<c:url value="/images/i-icon.png"/>">
                        </li>
                        <li>
                            <a href="#tab-variance-range">Range</a>
                            <img id="strategy-variance-range-info" class="help_Infromation_PopUp" src="<c:url value="/images/i-icon.png"/>">
                        </li>
                    </ul>
                    <div class="tab">

                        <div id="tab-variance-information" class="tab-content" style="display: block">
                            <div class="ques">
                                <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none overflow-x">
                                    <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr output_table text-center">
                                        <thead>
                                            <tr class="tblhd add-fieldi">
                                                <td>Crop</td>
                                                <td>Price</td>
                                                <td>Yield</td>
                                                <td class="hidden">Production Cost</td>
                                            </tr>
                                        </thead>
                                        <tbody >
                                            <c:forEach var="cropTypeView" items="${model.cropTypeViewList}">
                                                <c:if test="${cropTypeView.selected}">
                                                    <tr>
                                                        <td class="success">
                                                            ${cropTypeView.cropName}
                                                        </td>
                                                        <td class="success">
                                                            <input type="checkbox" name="cropPrice" value="${cropTypeView.id}" checked/>
                                                        </td>
                                                        <td class="success">
                                                            <input type="checkbox" name="cropYield" value="${cropTypeView.id}" checked/>
                                                        </td>
                                                        <td class="success hidden">
                                                            <input type="checkbox" name="productionCost" value="${cropTypeView.id}" checked/>
                                                        </td>
                                                    </tr>
                                                </c:if>
                                            </c:forEach>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>

                        <div id="tab-variance-range" class="tab-content" style="display: none">
                            <div class="ques">
                                <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none overflow-x">
                                    <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr output_table text-center">
                                        <thead>
                                            <tr class="tblhd add-fieldi">
                                                <td>Crop</td>
                                                <td>Min Price</td>
                                                <td>Est Price</td>
                                                <td>Max Price</td>
                                                <td>Min Yield</td>
                                                <td>Est Yield</td>
                                                <td>Max Yield</td>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="cropTypeView" items="${model.cropTypeViewList}">
                                                <c:if test="${cropTypeView.selected}">
                                                    <tr class="cropVarianceSpecific">
                                                        <td class="success">${cropTypeView.cropName}<input type="hidden" class="cropIdSpecific" value="${cropTypeView.id}"/></td>
                                                        <td class="success">
                                                            ${cropTypeView.intMinCropPriceString}
                                                            <input type="hidden" class="cropMinPriceSpecific" value="${cropTypeView.intMinCropPriceString}" disabled/>
                                                        </td>

                                                        <td class="success">
                                                            ${cropTypeView.intExpCropPriceString}
                                                            <input type="hidden" class="cropMinPriceSpecific" value="${cropTypeView.intExpCropPriceString}" disabled/>
                                                        </td>

                                                        <td class="success">
                                                            ${cropTypeView.intMaxCropPriceString}
                                                            <input type="hidden" class="cropMaxPriceSpecific" value="${cropTypeView.intMaxCropPriceString}" disabled/>
                                                        </td>
                                                        <td class="success">
                                                            ${cropTypeView.intMinCropYield}
                                                            <input type="hidden" class="cropMinYieldSpecific" value="${cropTypeView.intMinCropYield}" disabled/>
                                                        </td>

                                                        <td class="success">
                                                            ${cropTypeView.intExpCropYield}
                                                            <input type="hidden" class="cropMinYieldSpecific" value="${cropTypeView.intExpCropYield}" disabled/>
                                                        </td>

                                                        <td class="success">
                                                            ${cropTypeView.intMaxCropYield}
                                                            <input type="hidden" class="cropMaxYieldSpecific" value="${cropTypeView.intMaxCropYield}" disabled/>
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
                <div class="clearfix"></div>
                <div style="margin-top: 1%">

                    <button class="alertify-button alertify-button-ok pull-right"
                            onclick="getVarianceGraphDetails(); return false;" >Ok</button>

                    <button class="alertify-button alertify-button-cancel pull-right"
                            onclick="closeVarianceGraphSetupPopup(); return false;">Cancel</button>

                </div>


            </div>
        </div>
    </div>
</div>

<%--<script src="js/plugins/jquery.tmpl.min.js" type="text/javascript"></script>--%>
<script src="<c:url value="/js/agriculture/farm-info-slider.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/agriculture/strategy/view-farm-strategy.js?v=0.1"/>" type="text/javascript"></script>
<script src="<c:url value="/js/plugins/jQuery.filedownload.js"/>" type="text/javascript"></script>
<script type="text/x-jQuery-tmpl" id="strategyOutputDetailsTbodyTemplate">
    {{each(key, strategy) strategyDetails}}
        <tr>
            {{if typeof reportFlag != 'undefined' && reportFlag == true}}
                <td class="success">
                    {{if strategy.strategyName == "Baseline Strategy" && (typeof baselineflag != 'undefined' && baselineflag == true)}}
                        <input type="checkbox" name="reportStrategyCheckbox" value="{{= strategy.id}}, {{= strategy.farmID}}" disabled>
                        {{else}}
                            <input type="checkbox" name="reportStrategyCheckbox" value="{{= strategy.id}}, {{= strategy.farmID}}">
                    {{/if}}
                </td>
            {{/if}}
            <td class="success"><h4 style="color : #337ab7; cursor : pointer" onclick="openStrategyDetailsPopup('{{= strategy.id}}'); return false;">{{= strategy.strategyName}}<h4></td>
            <td class="success"><h4>{{= addCommaSignWithDollarForTextWithOutId(strategy.potentialProfit)}}</h4></td>
            <td class="success"><h4>{{= addCommaSignForTextWithOutId(strategy.totalAcreage)}}</h4></td>
            {{each(key1, data) outputDetails}}
                {{if strategy.id == data.strategyId }}
                    {{each(key2, output) data.details}}
                        <td class="success"><h4>{{= output.amount}}</h4></td>
                    {{/each}}
                {{/if}}
            {{/each}}
        </tr>
    {{/each}}

</script>

<script type="text/x-jQuery-tmpl" id="strategyCheckboxTemplate">

    <div class="form-group form-group1">
        <label>
            <input type="radio" name="reportStrategyCheckbox" value="{{= id}}, {{= farmID}}" strategy-name="{{= strategyName}}">
            {{= strategyName}}
        </label>
    </div>
</script>

<script type="text/x-jquery-tmpl" id="strategyOutputTheadTemplate">
    {{if strategy == "PLAN_BY_ACRES"}}
        <tr class="tblhd add-fieldi">
			<td class="tblbrdr add-fieldi">Crop</td>
			<td class="add-fieldi">Acreage</td>
		</tr>
        {{else strategy == "PLAN_BY_FIELDS"}}
            <tr class="tblhd add-fieldi">
				<td class="tblbrdr add-fieldi">Field</td>
				<td class="add-fieldi">Acreage</td>
				<td class="add-fieldi">Crop</td>
			</tr>
    {{/if}}
</script>

<script type="text/x-jquery-tmpl" id="strategyOutputTbodyTemplate">

    {{if strategy == "PLAN_BY_ACRES" }}
		{{each(key, details) outputDetails }}
			<tr class="tblgrn">
				<td class="success">{{= details.cropName}}</td>
				<td class="success">{{= details.acreage}}</td>
			</tr>
		{{/each}}
		{{else strategy == "PLAN_BY_FIELDS" }}
			{{each(key, details) outputDetails }}
				<tr class="tblgrn fieldAcreageSpecific">
					<td class="success">{{= details.field}}</td>
					<td class="success">{{= details.size}}</td>
					<td class="success">{{= details.crop}}</td>
				</tr>
			{{/each}}
	{{/if}}
</script>

<script type="text/x-jquery-tmpl" id="strategyTableHeaderTemplate">


    <td>Strategy</td>
    <td>Estimated Income</td>
    <td>Total Acreage</td>
    {{each(key, header) headerDetails }}
        <td>{{html header}}</td>
	{{/each}}


</script>