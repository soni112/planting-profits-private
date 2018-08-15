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

<script>
    <c:set value="${model.scenarioName}" var="scenarioName"/>
</script>

<script>
    $(document).ready(function () {
        $('[data-toggle="popover"]').tooltip();
    });
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
                                    <a href="#compareStrategies" class="open" delta="${farmId}"><div class="scnr_detail">Compare Strategies</div></a>
                                </li>
                                <li>
                                    <a href="#manageStrategies" delta="${farmId}"><div class="scnr_detail">Manage Strategies</div></a>
                                </li>
                                <li>
                                    <a href="#printStrategies" delta="${farmId}"><div class="scnr_detail">Print Strategies</div></a>
                                </li>
                            </ul>
                        </div>
                        <!-----start tab rightside description-------------------------------------->
                        <div id="compareStrategies" class="strategy_block right-strategy-details" style="display: block">
                            <div class="update_values result_str">
                                <a href="<c:url value="output-farm-info.htm?farmId=${farmId}"/>" class="alertify-button alertify-button-ok pull-right">Back to Baseline</a>
                                <div id="tableSectionForStrategy" class="text-center">
                                    <%--<button class="alertify-button alertify-button-ok pull-right"
                                            onclick="buildGaugeMeterComponent();toggleGaugeSection(); return false;">
                                        <i class="fa fa-bar-chart-o"></i> Dashboard
                                    </button>--%>
                                        <div class="pull-right cursor-pointer">
                                            <img src='<c:url value="/images/dashboard.png" />' onclick="buildGaugeMeterComponent();toggleGaugeSection(); return false;" style="margin-right: 15px;" />
                                        </div>
                                    <div class="pull-right cursor-pointer">
                                        <img src='<c:url value="/images/graph_tab.png" />' onclick="toggleGraphSection(); return false;" style="margin-right: 15px;" />
                                    </div>
                                    <div class="pull-right cursor-pointer">
                                    <img src='<c:url value="/images/showtext.png"/>' onclick="toggleTableSection(); return false;" style="margin-right: 15px;"/>
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
                                                        <div class="pull-left">
                                                            <h4>N/A = not included in strategy</h4>
                                                        </div>

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
                                    <%--<button class="alertify-button alertify-button-ok pull-right"
                                            onclick="buildGaugeMeterComponent();toggleGaugeSection(); return false;">
                                        <i class="fa fa-bar-chart-o"></i> Dashboard
                                    </button>--%>
                                        <div class="pull-right cursor-pointer">
                                            <img src='<c:url value="/images/dashboard.png" />' onclick="buildGaugeMeterComponent();toggleGaugeSection(); return false;" style="margin-right: 15px;" />
                                        </div>
                                    <div class="pull-right cursor-pointer">
                                        <img src='<c:url value="/images/graph_tab.png" />' onclick="toggleGraphSection(); return false;" style="margin-right: 15px;"/>
                                    </div>
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
                                <div id="gaugeSectionForStrategy" style="display: none">
                                    <%--<button class="alertify-button alertify-button-ok pull-right"
                                            onclick="buildGaugeMeterComponent();toggleGaugeSection(); return false;">
                                        <i class="fa fa-bar-chart-o"></i> Dashboard
                                    </button>--%>
                                        <div class="pull-right cursor-pointer">
                                            <img src='<c:url value="/images/dashboard.png" />' onclick="buildGaugeMeterComponent();toggleGaugeSection(); return false;" style="margin-right: 15px;" />
                                        </div>
                                    <div class="pull-right cursor-pointer">
                                        <img src='<c:url value="/images/graph_tab.png" />' onclick="toggleGraphSection(); return false;" style="margin-right: 15px;"/>
                                    </div>
                                    <div class="pull-right cursor-pointer">
                                        <img src='<c:url value="/images/showtext.png"/>' onclick="toggleTableSection(); return false;" style="margin-right: 15px;"/>
                                    </div>
                                    <%--<button class="alertify-button alertify-button-ok pull-right"
                                            onclick="buildGaugeMeterComponent(); return false;"
                                            style="margin-right: 15px;">Graph</button>--%>
                                    <div class="ques">
                                        <div class="table-responsive">
                                        <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr output_table text-center" width="100%">
                                        <thead>
                                        <tr class="tblhd add-fieldi">
                                            <td rowspan="2"> Strategy</td>
                                            <td rowspan="2">Estimated Income</td>
                                            <td colspan="4">Risk Profile</td>
                                            <td colspan="2">Asset Utilization</td>
                                        </tr>
                                        <tr class="tblhd add-fieldi">
                                            <td>Diversification
                                                <span><a id="diversification" class="help_Infromation_PopUp" href="javascript:;">
                                                    <img src="<c:url value="/images/i-icon.png"/>"></a>
                                                </span>
                                            </td>
                                            <td>Forward Sales
                                                <span><a id="forwardSales" class="help_Infromation_PopUp" href="javascript:;">
                                                    <img src="<c:url value="/images/i-icon.png"/>"></a>
                                                </span>
                                            </td>
                                            <td>Crop Insurance</td>
                                            <td>
                                                <a href="#applyScenarioToCurrent" onclick="openScenarioPopup(); return false" class="remove-text" data-toggle="popover" data-container="body" data-trigger="hover"
                                                   data-placement="top" title="Sceanrio Name"> Scenario Analysis</a>
                                            </td>
                                            <td>Return on Working Capital
                                                <span><a id="returnOnWorkingCapital" class="help_Infromation_PopUp" href="javascript:;">
                                                    <img src="<c:url value="/images/i-icon.png"/>"></a>
                                                </span>
                                            </td>
                                            <td>Acreage Under Conservation Practices
                                                <span><a id="acreageUnderConservationPractice" class="help_Infromation_PopUp" href="javascript:;">
                                                    <img src="<c:url value="/images/i-icon.png"/>"></a>
                                                </span>
                                            </td>
                                        </tr>


                                        </thead>
                                        <tbody id="enhancedProfitOutpout">
                                        <tr>
                                            <td class="success" colspan="8">No strategies created</td>
                                        </tr>
                                      <%--  <c:set var = "i" value = "${1}"/>
                                        <c:forEach var="dataForGenuerChart" items="${model.dataForGenuerChart}">
                                        <tr>
                                            <td class="success">
                                            <h4 style="color : #337ab7; cursor : pointer" onclick="openStrategyDetailsPopup('{{= strategy.id}}'); return false;">${dataForGenuerChart.strategyName}<h4>

                                            &lt;%&ndash;<small class="est-income-total">${dataForGenuerChart.strategyName}</small>&ndash;%&gt;
                                            </td>
                                            <td class="success">

                                            <div class="est-income-graph">
                                                    <span class="est-income-category">${i}</span>
                                                        <small class="est-income-total">$ ${dataForGenuerChart.EstimateIncome}</small>

                                                </div>
                                            </td>
                                            <td colspan="4" class="success">
                                                <div align="right" id="cropGaugeMeter${i}" value="${dataForGenuerChart.EstIncomeInOneCrop}" class="progress-graphs gauge_meter pull-left"></div>
                                                <div align="center" id="marketGaugeMeter${i}" value="${dataForGenuerChart.EstIncomeInForwardSale}" class="progress-graphs gauge_meter pull-left">
                                                </div>
                                                &lt;%&ndash;<div align="left" id="productionGaugeMeter${dataForGenuerChart.EstIncomeInForwardSale}" value="${dataForGenuerChart.EstIncomeInForwardSale}" class="progress-graphs gauge_meter pull-left"></div>&ndash;%&gt;
                                                <div align="left" id="productionGaugeMeter" >
                                                <small class=" gauge_meter pull-left">Coming-Soon</small>
                                                </div>

                                                <div class="gauge_meter">
                                                    <div class="secnario-analysis">
                                                        <span class="est-income-category">2</span>
                                                        <small class="est-income-total">$ ${total}</small>
                                                    </div>
                                                </div>
                                            </td>
                                            </td>
                                            <td colspan="2" class="success">
                                                <div style="width: 50%;float: left">
                                                    <div class="est-income-graph">
                                                        <span class="est-income-category">${i}</span>
                                                        <small class="est-income-total">$ ${dataForGenuerChart.ReturnWorkingCapital}</small>
                                                    </div>
                                                </div>
                                                <div style="width: 50%;float: left">
                                                    <c:if test="${dataForGenuerChart.AverageInConservationCrop>=50.0}">
                                                        <div class="secnario-analysis">
                                                            <i class="icon-thumbs-up fa fa-thumbs-up" aria-hidden="true"></i>
                                                            <small class="est-income-total">$ ${dataForGenuerChart.AverageInConservationCrop}</small>

                                                        </div>
                                                    </c:if>
                                                    <c:if test="${dataForGenuerChart.AverageInConservationCrop<50.0 && dataForGenuerChart.AverageInConservationCrop>25.0}">
                                                        <div class="secnario-analysis">
                                                            <i class="icon-thumbs-up fa fa-thumbs-down" aria-hidden="true"></i>
                                                            <small class="est-income-total">$ ${dataForGenuerChart.AverageInConservationCrop}</small>

                                                        </div>
                                                    </c:if>
                                                    <c:if test="${dataForGenuerChart.AverageInConservationCrop<25.0}">
                                                        <div class="secnario-analysis-red">
                                                            <i class="icon-thumbs-up fa fa-thumbs-down" aria-hidden="true"></i>
                                                            <small class="est-income-total">$ ${dataForGenuerChart.AverageInConservationCrop}</small>

                                                        </div>
                                                    </c:if>

                                                </div>

                                            </td>
                                        </tr>
                                            <c:set var = "i" value = "${i+1}"/>
                                        </c:forEach>--%>

                                        </tbody>
                                    </table>
                                    </div>
                                    </div>
                                </div>

                                <div id="scenario-popup" class="pop-up" style="display: none;">
                                    <div class="pop-up-body">
                                        <div class="popup_section">

                                            <div class="potencial_profit_popup">
                                                <div class="panel panel-yellow">
                                                    <div class="panel-heading text-center">
                                                        <label style="cursor: pointer;">Select Scenario Analysis</label>
                                                    </div>
                                                    <div class="panel-body" style="display: block">

                                                        <div id="strategyForScenarioDiv">
                                                            <c:forEach var="scenario" items="${model.savedScenarioData}">
                                                                <div class="form-group form-group1">
                                                                    <label>
                                                                        <input type="radio" data-scenarioName="${scenario.scenarioName}" name="scenarioCheckbox" value="${scenario.scenarioId}">
                                                                        &nbsp; &nbsp;${scenario.scenarioName}
                                                                    </label>
                                                                </div>
                                                            </c:forEach>
                                                        </div>

                                                        <div class="clearfix"></div>
                                                        <button class="alertify-button alertify-button-ok pull-right"
                                                                onclick="getScenarioOutputDetails('scenarioCheckbox'); return false;">Ok
                                                        </button>
                                                        <button class="alertify-button alertify-button-cancel pull-right"
                                                                onclick="closeScenarioPopup(true); return false;">Cancel
                                                        </button>
                                                    </div>


                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>



                        <div id="manageStrategies" class="strategy_block right-strategy-details" style="display: none">
                            <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none overflow-x">
                                <a href="<c:url value="output-farm-info.htm?farmId=${farmId}"/>"
                                   class="alertify-button alertify-button-ok pull-right"
                                    style="margin-bottom: 4px;">Back to Baseline</a>
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
                                <a href="<c:url value="output-farm-info.htm?farmId=${farmId}"/>"
                                   class="alertify-button alertify-button-ok pull-right"
                                   style="margin-bottom: 4px;">Back to Baseline</a>
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
                                <p id="show_msg" class="static_result_shown pull-left" style="font-size: 16px">
                                    Select up to three Strategies to include in the report.
                                </p>
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

                        <button class="alertify-button alertify-button-ok pull-right" onclick="showPopupForScenario(); closeStrategySelectionPopup(); return false;">Generate Report</button>
                        <button class="alertify-button alertify-button-cancel pull-right" onclick="closeStrategySelectionPopup(); return false;">Back</button>

                    </div>

                </div>

            </div>
        </div>
    </div>
</div>

<div id="generate-scenario-popup" class="pop-up" style="display: none;">
    <div class="pop-up-body">
        <div class="popup_section">

            <div class="potencial_profit_popup">
                <div class="panel panel-yellow">
                    <div class="panel-heading text-center">
                        <label style="cursor: pointer;">Select a Scenario to include in the report.</label>
                        <a id="featuredScenario" class="help_Infromation_PopUp" href="#">
                            <img src="<c:url value="/images/i-icon.png"/>">
                        </a>
                    </div>
                    <div class="panel-body" style="display: block">

                        <div id="generateReportForScenarioDiv">
                            <c:forEach var="scenario" items="${model.savedScenarioData}">
                                <div class="form-group form-group1">
                                    <label>
                                        <input type="radio" name="scenarioCheck" value="${scenario.scenarioId}">
                                        &nbsp; &nbsp;${scenario.scenarioName}
                                    </label>
                                </div>
                            </c:forEach>
                        </div>

                        <div class="clearfix"></div>
                        <button class="alertify-button alertify-button-ok pull-right"
                                onclick="generateReportForScenario();hidePopupForScenario(); return false;">Ok
                        </button>
                        <button class="alertify-button alertify-button-cancel pull-right"
                                onclick="hidePopupForScenario(); openStrategySelectionPopup(); return false;">Cancel
                        </button>
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
                            <a href="#tab-variance-information">Vary Price / Yield</a>
                            <img id="strategy-variance-information-info" class="help_Infromation_PopUp" src="<c:url value="/images/i-icon.png"/>">
                        </li>
                        <li>
                            <a href="#tab-variance-range">Price / Yield Range</a>
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
    <script type="text/x-jQuery-tmpl" id="enhancedOutputTemplate">
    {{each(key, strategy) gaugeGraphData}}
        <tr>
            <td class="success"><h4 style="color : #337ab7; cursor : pointer" onclick="openStrategyDetailsPopup('{{= strategy.id}}'); return false;">{{= strategy.strategyName}}<h4></td>
            <td class="success">
             <div class="est-income-graph">
                 <span class="est-income-category">{{= strategy.countEstimateIncome}}</span>
                <small class="est-income-total">{{= addCommaSignWithDollarForTextWithOutId(strategy.EstimateIncome)}}</small>
              </div></td>
            <td class="success"><div align="center" id="cropGaugeMeter{{= strategy.id}}" value="{{= strategy.EstIncomeInOneCrop}}" class="progress-graphs gauge_meter "></div>
            <small>{{= strategy.EstIncomeInOneCrop}}</small> </td>
            <td class="success"><div align="center"  id="marketGaugeMeter{{= strategy.id}}" value="{{= strategy.EstIncomeInForwardSale}}" class="progress-graphs gauge_meter "></div>
           <small> {{= strategy.EstIncomeInForwardSale}}</small></td>
            <td class="success">Coming Soon</td>
            <td class="success">
              <div class="gauge_meter">
              <div class="secnario-analysis">
                <span class="est-income-category">{{= key+1}}</span>
                    <small class="est-income-total">{{= strategy.scenarioAnalysis}}</small>
                  </div>
            </div>
            </td>
            <td class="success">
            <div class="est-income-graph">
                 <span class="est-income-category">{{= strategy.countReturnWorking}}</span>
                   <%--<small class="est-income-total">{{= addCommaSignWithDollarForTextWithOutId(strategy.ReturnWorkingCapital)}}</small>--%>
                   <small class="est-income-total">{{= strategy.ReturnWorkingCapital}}</small>
                </div>
            </div></td>
            <td class="success">
           {{if strategy.AverageInConservationCrop >= 50.0  }}
          <div class="gauge_meter">
           <div class="secnario-analysis">
                 <span class="est-income-category">{{= strategy.countConservation}}</span>

            <%--<div class="est-income-graph">--%>
                 <%--<i class="icon-thumbs-up fa fa-thumbs-up" aria-hidden="true"></i>--%>
                <%--<small class="est-income-total">{{= addCommaSignWithDollarForTextWithOutId(strategy.AverageInConservationCrop)}}</small>--%>
                <small class="est-income-total">{{= strategy.AverageInConservationCrop}}</small>
                <small class="est-income-total">{{= strategy.AverageInConversion}}</small>
                </div>
                </div>
           {{/if}}
           {{if strategy.AverageInConservationCrop > 25.0 && strategy.AverageInConservationCrop < 50.0 }}
            <%--<div class="secnario-analysis">--%>
                 <%--<i class="icon-thumbs-up fa fa-thumbs-up" aria-hidden="true"></i>--%>
                  <div class="gauge_meter">
           <div class="secnario-analysis">
                 <span class="est-income-category">{{= strategy.countConservation}}</span>
                 <%--<small class="est-income-total">{{= addCommaSignWithDollarForTextWithOutId(strategy.AverageInConservationCrop)}}</small>--%>
                <%--<small class="est-income-total">{{= addCommaSignWithDollarForTextWithOutId(strategy.AverageInConversion)}}</small>--%>
                <small class="est-income-total">{{= strategy.AverageInConservationCrop}}</small>
                <small class="est-income-total">{{= strategy.AverageInConversion}}</small>
                </div>
            </div>
           {{/if}}
           {{if strategy.AverageInConservationCrop < 25.0 }}
            <div class="gauge_meter">
           <div class="secnario-analysis">
                 <span class="est-income-category">{{= strategy.countConservation}}</span>
         <%--  <div class="secnario-analysis-red">
                 <i class="icon-thumbs-up fa fa-thumbs-down" aria-hidden="true"></i>--%>
                <%--<small class="est-income-total">{{= addCommaSignWithDollarForTextWithOutId(strategy.AverageInConservationCrop)}}</small>--%>
                <small class="est-income-total">{{= strategy.AverageInConservationCrop}}</small>
                <small class="est-income-total">{{= strategy.AverageInConversion}}</small>
                </div>
           {{/if}}</td>
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
    <td>Est. Income</td>
    <td>Total Acreage</td>
    {{each(key, header) headerDetails }}
        <td>{{html header}}</td>
	{{/each}}


</script>

<script type="text/x-jquery-tmpl" id="resourceTableHeaderTemplate">


    <td>Strategy</td>
    <td>Est. Income</td>
    <td>Total Acreage</td>
    {{each(key, header) headerDetails }}
    {{if header == "Acreage Assigned" || header == "Return on Working Capital"}}
        <td>{{html header}}</td>
        {{else}}
        <td>{{html header}} Used</td>
     {{/if}}

	{{/each}}




</script>