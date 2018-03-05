<!-- start -->
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<link href="<c:url value="/css/bootstrap-multiselect.css"/>" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="<c:url value="/css/sb-admin-2.css"/>" type="text/css" media="all">
<!-- @changed - Jyoti @date - 30-01-2017 -->
<c:set var="farmId" value="${model.farm.farmId}"/>
<!-- end -->
<div class="container-fluid">
    <div class="row">
        <div class="leftside">
            <%@ include file="common/menu.jsp" %>
            <div class="right_farm_form_filled">
                <div class="edit_output_details_link">
                    <!-- @changed - Jyoti    @date - 30-01-2017 -->
                    <a class="alertify-button alertify-button-ok remove-text-deco" href="view-farm-info.htm?farmId=${farmId}">Change Farm Information</a>
                </div>
                <div class="output_base">
                    <h3>${model.farmInfoView.farmName} (Baseline Strategy)</h3>
                    <div class="base_white">
                        <ul class="total_output" id="sidemenu">
                            <c:choose>
                                <c:when test="${model.farmInfoView.strategy eq 'PLAN_BY_ACRES'}">
                                    <li class="active"><a href="#Crop-Acreage" class="open">
                                        <div class="lf_pointer_images"><img src="<c:url value="/images/pointer-image2.jpg"/>"></div>
                                        <div class="right_detail"> Crop Acreage
                                            <!-- <span>You can see the acreage of each crop planted.</span> --></div>
                                    </a></li>
                                </c:when>
                                <c:otherwise>
                                    <li class="active"><a href="#Crop-Acreage" class="open">
                                        <div class="lf_pointer_images"><img src="<c:url value="/images/pointer-image2.jpg"/>"></div>
                                        <div class="right_detail"> Crop Acreage
                                            <!-- <span>You can see the acreage of each crop planted.</span> --></div>
                                    </a></li>
                                </c:otherwise>
                            </c:choose>
                            <c:if test="${model.farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                                <li><a href="#Crop&ndash;Field-Assignments">
                                    <div class="lf_pointer_images"><img src="<c:url value="/images/pointer-image1.jpg"/>"></div>
                                    <div class="right_detail"> Crop &ndash; Field Assignments
                                        <!-- <span>You can see crop to field allocation.</span> --></div>
                                </a></li>
                            </c:if>
                            <li><a href="#Resource-Use">
                                <div class="lf_pointer_images"><img src="<c:url value="/images/pointer-image3.jpg"/>">
                                </div>
                                <div class="right_detail"> Resource Use
                                    <!-- <span>You can see which resources you use.</span> --></div>
                                <c:if test="${model.resourceJsonObject.resourceFlags['Land']}">
                                    <div id="resource-highlight-icon" title="All available acreage not planted"><i class="fa fa-circle" style="color: red; float: right;"></i></div>
                                </c:if>
                            </a></li>
                            <li><a href="#Crop-Limits">
                                <div class="lf_pointer_images"><img src="<c:url value="/images/pointer-image4.jpg"/>">
                                </div>
                                <div class="right_detail"> Crop Acreage Limits
                                    <!-- <span>You can see your min. &amp; max. crop limits.</span> --></div>
                            </a></li>
                            <li><a href="#Forward-Sales">
                                <div class="lf_pointer_images"><img src="<c:url value="/images/pointer-image5.jpg"/>">
                                </div>
                                <div class="right_detail"> Forward Sales
                                    <!-- <span>You can see your contract crop.</span> --></div>
                            </a></li>
                        </ul>
                        <ul class="potential_criteria">
                            <li>Estimated Income:
                                <span class="baseline_potential_profit"
                                      id="potentialProfit">$${model.potentialProfit}</span>
                            </li>
                            <!-- <li>Profit Goal: <span>$--,--</span></li> -->
                        </ul>
                        <!-----start tab rightside description-------------------------------------->

                        <!--	@changed - Abhishek 	@Date - 27-11-2015		@desc - according to slide#1 of 01-28-2016	-->
                        <%--<div class="contentblock ${model.farmInfoView.strategy eq 'PLAN_BY_FIELDS'?'hidden':''}"id="Crop-Acreage">--%>
                        <div class="contentblock" id="Crop-Acreage">
                            <div id="cropAcerageText" class="text_field" style="display: none">
                                <div class="addremove-field padding-left-none pull-right">
                                    <a id="cropAcerageGraphShow" class="show_graph"><img
                                            src="<c:url value="/images/graph_tab.png"/>"></a>
                                </div>
                                <div class="clearfix"></div>
                                <div class="table-responsive Crop-Acreage-tabel" style="max-height: 298px;">
                                    <table width="100%" cellspacing="0" id="cropAcreageAndProfit"
                                           class="table table-striped tbl-bordr  tblbrdr output_table">
                                        <thead>
                                        <tr class="tblhd add-fieldi">
                                            <td class="tblbrdr add-fieldi">Crop</td>
                                            <td class="add-fieldi">Acreage</td>
                                            <td>Estimated Income</td>
                                            <td>Estimated Income per Acre ($/acre)</td>
                                            <td style="text-align: center;">
                                                Land Profitability Index
                                                <a class="help_Infromation_PopUp" id="landProfitablityIndex">
                                                    <img src="<c:url value="/images/i-icon.png"/>">
                                                </a>
                                            </td>
                                            <td>
                                                Rating
                                                <a class="help_Infromation_PopUp" id="landProfitablityIndexRating">
                                                    <img src="<c:url value="/images/i-icon.png"/>">
                                                </a>
                                            </td>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="acrageDetails" items="${model.cropAcreageJsonArray}">
                                            <tr class="tblgrn">
                                                <td class="success">${acrageDetails.cropName}</td>
                                                <td class="success">${acrageDetails.acreage}</td>
                                                <td class="success">${acrageDetails.profit}</td>
                                                <td class="success">${acrageDetails.ratio}</td>
                                                <td class="success">${acrageDetails.index}</td>
                                                <td class="success rating${acrageDetails.rating}"></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                        <%--<c:if test="${model.farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                                            <tbody>
                                            <c:set var="rowCountForCropAcreage" value="1" />

                                                <c:forEach var="sortedTreeMapForAcreage" items="${model.hashMapForAcre}">
                                                    <c:forEach var="cropTypeView" items="${model.cropTypeView}">
                                                        <c:if test="${cropTypeView.cropName eq sortedTreeMapForAcreage.key}" >
                                                            <tr class="tblgrn">
                                                                <td class="success">
                                                                    ${cropTypeView.cropName}
                                                                    <c:set value="true" var="loopFlag"/>
                                                                    <c:forEach var="farmOutputDetails" items="${model.farmOutputDetails}">
                                                                        <c:if test="${farmOutputDetails.cropTypeView.id eq cropTypeView.id and loopFlag}">
                                                                            <c:if test="${farmOutputDetails.forFirm}"> (Firm) <c:set value="false" var="loopFlag"/> </c:if>
                                                                            <c:if test="${farmOutputDetails.forProposed}"> (Proposed) <c:set value="false" var="loopFlag"/></c:if>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </td>
                                                                <td class="success">${model.hashMapForAcre[cropTypeView.cropName]}</td>
                                                                <td class="success">${model.hashMapForProfit[cropTypeView.cropName] eq "0 (0.0%)" or model.hashMapForProfit[cropTypeView.cropName] eq "0 (-0.0%)" ? "N/A" : model.hashMapForProfit[cropTypeView.cropName]}</td>
                                                                <td class="success">${model.hashMapForRatio[cropTypeView.cropName] eq 0 ? "N/A" : model.hashMapForRatio[cropTypeView.cropName]}</td>
                                                                <td class="success">${model.hashMapForProfitIndex[cropTypeView.cropName] eq "0.0" ? "N/A" : model.hashMapForProfitIndex[cropTypeView.cropName].toString().replaceAll("\\%","")}</td>
                                                                <c:choose>
                                                                    <c:when test='${model.hashMapForProfit[cropTypeView.cropName] eq "0 (0.0%)" or model.hashMapForProfit[cropTypeView.cropName] eq "0 (-0.0%)" }'>
                                                                        <td class="success ratingGrey"></td>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <td class="success rating${model.hashMapForRating[cropTypeView.cropName]}"></td>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                </c:forEach>
                                            </tbody>
                                        </c:if>


                                        <c:if test="${model.farmInfoView.strategy eq 'PLAN_BY_ACRES'}">
                                            <tbody>
                                            <c:set var="rowCountForCropAcreage" value="1" />
                                            <c:forEach var="farmOutputDetails" items="${model.farmOutputDetails}">
                                                <c:if test="${farmOutputDetails.cropTypeView.selected}">
                                                    &lt;%&ndash;<c:choose>&ndash;%&gt;
                                                        &lt;%&ndash;<c:when test="${rowCountForCropAcreage % 2 eq '1'}">&ndash;%&gt;
                                                            <tr class="tblgrn">
                                                                <td class="success">${farmOutputDetails.cropTypeView.cropName}
                                                                    <c:if test="${farmOutputDetails.forFirm}"> (Firm)</c:if>
                                                                    <c:if test="${farmOutputDetails.forProposed}"> (Proposed)</c:if>
                                                                </td>
                                                                <td class="success">${farmOutputDetails.usedAcres} (${farmOutputDetails.usedAcresPercentage}%)</td>
                                                                <c:choose>
                                                                    <c:when test="${farmOutputDetails.profit ne 0}">
                                                                        <!--	@changed - Abhishek 	@Date - 23-01-2016		@desc - changed according to slide# 19 of 01042015	-->
                                                                        &lt;%&ndash;<td class="success">$${farmOutputDetails.profit} (${farmOutputDetails.usedCapitalPercentage})</td>&ndash;%&gt;
                                                                        <td class="success">$${farmOutputDetails.profit} (${farmOutputDetails.usedCapitalPercentage}%)</td>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <td class="success">N/A</td>
                                                                    </c:otherwise>
                                                                </c:choose>

                                                                <td class="success">${farmOutputDetails.ratio eq 0.0 ? "N/A" : farmOutputDetails.ratio}</td>
                                                                <td class="success">${farmOutputDetails.profitIndex eq 0.0 ? "N/A" : farmOutputDetails.profitIndex}</td>
                                                                <c:choose>
                                                                    <c:when test="${farmOutputDetails.profit ne 0}">
                                                                        <td class="success rating${farmOutputDetails.rating}"></td>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <td class="success ratingGrey"></td>
                                                                    </c:otherwise>
                                                                </c:choose>

                                                            </tr>
                                                        &lt;%&ndash;</c:when>
                                                        <c:otherwise>
                                                            <tr class="tblbclgrnd">
                                                                <td class="success">${farmOutputDetails.cropTypeView.cropName}
                                                                    <c:if test="${farmOutputDetails.forFirm}"> (Firm)</c:if>
                                                                    <c:if test="${farmOutputDetails.forProposed}"> (Proposed)</c:if>
                                                                </td>
                                                                <td class="success">${farmOutputDetails.usedAcres} (${farmOutputDetails.usedAcresPercentage}%)</td>
                                                                    &lt;%&ndash;                                                                    <td class="success">$${farmOutputDetails.profit} (${farmOutputDetails.usedCapitalPercentage}%)</td> &ndash;%&gt;
                                                                <c:choose>
                                                                    <c:when test="${farmOutputDetails.profit ne 0}">
                                                                        <td class="success">$${farmOutputDetails.profit} (${farmOutputDetails.usedCapitalPercentage}%)</td>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <td class="success">N/A</td>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                                <td class="success">${farmOutputDetails.ratio eq 0.0 ? "N/A" : farmOutputDetails.ratio}</td>
                                                                <td class="success">${farmOutputDetails.profitIndex eq 0.0 ? "N/A" : farmOutputDetails.profitIndex}</td>
                                                                <c:choose>
                                                                    <c:when test="${farmOutputDetails.profit ne 0}">
                                                                        <td class="success rating${farmOutputDetails.rating}"></td>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <td class="success ratingGrey"></td>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </tr>
                                                        </c:otherwise>&ndash;%&gt;
                                                    &lt;%&ndash;</c:choose>&ndash;%&gt;
                                                    <c:set var="rowCountForCropAcreage" value="1" />
                                                </c:if>
                                            </c:forEach>
                                            </tbody>
                                        </c:if>--%>
                                    </table>
                                </div>
                                <div style="margin-left: 20px;">
                                    <!--    @changed - Abhishek     @date - 11-12-2015  -->
                                    <span id="singleCropProfit"> of Estimated Income from single crop</span><br/>
                                    <span id="twoCropProfit"> of Estimated Income from top two crops</span>
                                </div>
                            </div>
                            <div id="cropAcerageGraph" class="graph_field" style="display: block">
                                <div class="addremove-field padding-left-none pull-right">
                                    <a id="cropAcerageTextShow" class="show_text"><img
                                            src="<c:url value="/images/showtext.png"/>"></a>
                                </div>
                                <div class="clr"></div>
                                <div class="graph_area">
                                    <!--Horizontal tabs start-->
                                    <ul data-persist="true" class="tabs">
                                        <li class="selected">
                                            <a href="#by_acres"><span class="icon"><img
                                                    src="<c:url value="/images/acres_icon.png"/>"></span> &nbsp;By Acres</a>
                                        </li>
                                        <li class="">
                                            <a href="#by_profit"><span class="icon"><img
                                                    src="<c:url value="/images/profit.png"/>"></span> &nbsp;By
                                                Profit</a>
                                        </li>
                                    </ul>
                                    <div class="home-search-tab-container">
                                        <div id="by_acres" style="display: block;">
                                            <div id="chartdivByAcre" class="chartdiv"></div>


                                        </div>
                                        <div id="by_profit" style="display: none;">
                                            <div id="chartdivByProfit" class="chartdiv"></div>

                                        </div>
                                    </div>
                                    <!--Horizontal tabs end-->
                                </div>
                            </div>
                        </div>
                        <!-- @end #Crop-Acreage -->

                        <c:if test="${model.farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                            <div class="contentblock hidden" id="Crop&ndash;Field-Assignments">
                                <div class="table-responsive" style="max-height: 298px;">
                                    <table width="100%" cellspacing="0"
                                           class="table table-striped tbl-bordr tblbrdr output_table">
                                        <thead>
                                        <tr class="tblhd add-fieldi">
                                            <td class="tblbrdr add-fieldi">Field</td>
                                            <td class="add-fieldi">Acreage</td>
                                            <td>Crop</td>
                                        </tr>
                                        </thead>
                                        <tbody id="crop_Field_Assignment">
                                        <c:set var="cropFieldRowCount" value="1"/>
                                        <c:forEach var="cropFieldsList" items="${model.fieldInfoList}">
                                            <c:set var="planted" value="false"/>
                                            <c:choose>
                                                <c:when test="${cropFieldsList.fallow eq 'true'}">
                                                    <tr class="tblgrn">
                                                        <td class="success">${cropFieldsList.fieldName}</td>
                                                        <td class="success">${cropFieldsList.fieldSizeStr}</td>
                                                        <td class="success">Fallow</td>
                                                    </tr>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:forEach var="farmOutputDetails"
                                                               items="${model.farmOutputDetails}">
                                                        <c:if test="${farmOutputDetails.fieldInfoView.fieldName eq cropFieldsList.fieldName and farmOutputDetails.usedAcres ne '0'}">
                                                            <c:set var="planted" value="true"/>
                                                            <tr class="tblgrn">
                                                                <td class="success">${cropFieldsList.fieldName}</td>
                                                                <td class="success">${farmOutputDetails.usedAcres}</td>
                                                                <td class="success">${farmOutputDetails.cropTypeView.cropName}
                                                                    <c:if test="${farmOutputDetails.forFirm}"> (Firm)</c:if>
                                                                    <c:if test="${farmOutputDetails.forProposed}"> (Proposed)</c:if>
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                    <c:if test="${planted eq 'false'}">
                                                        <tr class="tblgrn">
                                                            <td class="success">${cropFieldsList.fieldName}</td>
                                                            <td class="success">${cropFieldsList.fieldSize}</td>
                                                            <td class="success">Not Planted</td>
                                                        </tr>
                                                    </c:if>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <!-- @end #Crop–Field-Assignments -->
                        </c:if>

                        <div class="contentblock hidden" id="Resource-Use">
                            <div id="resourceUseText" class="text_field" style="display: none">
                                <div class="addremove-field padding-left-none pull-right">
                                    <a id="resourceUseGraphShow" class="show_graph"><img
                                            src="<c:url value="/images/resource_graph_btn.png"/>"></a>
                                </div>
                                <div class="clearfix"></div>
                                <div class="table-responsive Crop-Acreage-tabel" style="max-height: 298px;">
                                    <table id="resource-table" width="100%" cellspacing="0" class="table table-striped tbl-bordr  tblbrdr">
                                        <thead>
                                        <tr class="tblhd add-fieldi">
                                            <td class="tblbrdr add-fieldi">Resource</td>
                                            <td class="tblbrdr add-fieldi">Total Available</td>
                                            <td class="add-fieldi">Used</td>
                                            <td>Unused</td>
                                            <td>Impacting Profit</td>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:set var="resourceFlag" value="false" />
                                        <c:forEach var="resourceList" items="${model.resourceJsonObject.resourceDetails}">
                                            <%--<c:set var="key" value="${resourceList.cropResourceUse}" />
                                            <tr class="tblgrn">
                                                <c:choose>
                                                    <c:when test="${resourceList.cropResourceUse eq 'Capital'}">
                                                        <td class="success">Working Capital</td>
                                                    </c:when>
                                                    <c:when test="${resourceList.cropResourceUse eq 'Land'}">
                                                        <td class="success">${resourceList.cropResourceUse}</td>
                                                        <c:set value="${resourceList.cropResourceUseAmount}" var="maxLand"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="success">${resourceList.cropResourceUse}</td>
                                                    </c:otherwise>
                                                </c:choose>
                                                <td class="success">${resourceList.cropResourceUseAmount}</td>
                                                <td class="success">${model.cropResourceUsed[key]}</td>
                                                <td class="success">${model.cropResourceUnused[key]}</td>
                                                <c:choose>
                                                    <c:when test="${model.cropResourceUnused[key] eq '0'}">
                                                        &lt;%&ndash;<td class="success">
                                                            <a onmouseover="showCriticalMessagePopup('${resourceList.cropResourceUse}',
                                                                ${model.mapResourceDualValueView[key] ne null ? model.mapResourceDualValueView[key].profitPerUnit : '0'},
                                                                0,
                                                                ${model.mapResourceDualValueView[key] ne null ? model.mapResourceDualValueView[key].dualValue : '0'},
                                                                0,
                                                                '${model.mapResourceDualValueView[key].resourceUnitName}',
                                                                ${model.mapResourceDualValueView[key] ne null ? model.mapResourceDualValueView[key].primalValue : '0'})">Yes</a>
                                                        </td>&ndash;%&gt;
                                                        <td class="success">Yes</td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="success">No</td>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tr>--%>
                                            <tr class="tblgrn">
                                                <td class="success">
                                                    ${resourceList.resourceName}
                                                </td>
                                                <td class="success">${resourceList.totalAvailable}</td>
                                                <td class="success">${resourceList.used}</td>
                                                <td class="success">${resourceList.unused}</td>
                                                <c:choose>
                                                    <c:when test="${model.resourceJsonObject.resourceFlags[resourceList.resourceName]}">
                                                        <td class="success" title="Resource limits or crop acreage limits preventing all land from being planted">
                                                            <a class="remove-text-deco" style="color: red" target="_blank">${resourceList.impactingProfit}<sup>*</sup></a>
                                                        </td>
                                                        <c:set var="resourceFlag" value="true" />

                                                    </c:when>
                                                    <c:otherwise>
                                                        <td class="success">${resourceList.impactingProfit}</td>
                                                    </c:otherwise>
                                                </c:choose>

                                            </tr>
                                            <c:if test="${resourceList.resourceName eq 'Land'}">
                                                <c:set value="${resourceList.totalAvailable}" var="maxLand"/>
                                            </c:if>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                    <div class="clearfix"></div>
                                    <c:if test="${resourceFlag}">
                                    <p class="pull-left">
                                        * One or more factors preventing all available land from being planted.
                                        <c:url value="/troubleshoot.htm" var="troubleshooturl">
                                            <c:param name="farmId" value="${farmId}"/>
                                            <c:param name="key" value="unused"/>
                                        </c:url>
                                        <a class="remove-text-deco" style="color: red" href="<c:out value="${troubleshooturl}"/>" target="_blank">${resourceList.impactingProfit}Troubleshooting </a>
                                    </p>
                                    </c:if>
                                </div>


                            </div>
                            <div id="resourceUseGraph" class="graph_field" style="display: block">
                                <div class="addremove-field padding-left-none pull-right">
                                    <a id="resourceUseTextShow" class="show_text">
                                        <c:if test="${model.resourceJsonObject.resourceFlags['Land']}">
                                            <div id="resource-table-highlight-icon" style="position: relative; top: 10px; right: -1px;" title="All available acreage not planted">
                                                <i class="fa fa-circle" style="color: red; float: right;"></i>
                                            </div>
                                        </c:if>
                                        <img src="<c:url value="/images/showtext.png"/>">
                                    </a>
                                </div>
                                <div class="clr"></div>
                                <div class="graph_area">
                                    <div class="resource_graph">
                                        <div id="chartContainer" style="height: 268px; width: 98%;"></div>

                                    </div>

                                </div>
                                <c:if test="${resourceFlag}">
                                <p class="pull-left">
                                    * One or more factors preventing all available land from being planted.
                                    <c:url value="/troubleshoot.htm" var="troubleshooturl">
                                        <c:param name="farmId" value="${farmId}"/>
                                        <c:param name="key" value="unused"/>
                                    </c:url>
                                    <a class="remove-text-deco" style="color: red" href="<c:out value="${troubleshooturl}"/>" target="_blank">${resourceList.impactingProfit}Troubleshooting </a>
                                </p>
                                </c:if>
                            </div>
                            <div class="clearfix">
                            </div>
                            <br>
                            <div id="resource-senstivity-block" class="panel panel-default">
                                <div class="panel-heading">
                                    <%--	@changed - Abhishek 	@date - 25-02-2016		@desc - According to slide#8 of 02212016	--%>
                                    <%--<h3 class="panel-title inc_dec_title">Change Estimated Income by increasing or decreasing a resource :</h3>--%>
                                    <h3 class="panel-title inc_dec_title">
                                        <%--Analyze Estimated Income by increasing or decreasing a resource.--%>
                                        Increase or decrease resources to analyze impact on Estimated Income
                                        <a class="help_Infromation_PopUp" id="resourceSestivityDiv"
                                           style="padding-left: 10px">
                                            <img src="<c:url value="/images/i-icon.png"/>">
                                        </a>
                                    </h3>
                                    <span class="pull-right clickable panel-collapsed"><i
                                            class="fa fa-chevron-down"></i></span>
                                </div>
                                <div id="resource-senstivity-single-multiple" class="panel-body" style="display: none;">
                                    <ul class="tabs" data-persist="true">
                                        <li class="selected"><a href="#single_resource">Single Resource</a></li>
                                        <li class="">
                                            <a href="#multiple_resources">Multiple Resources</a>
                                            <img class="help_Infromation_PopUp" id="resourceSestivityMultipleDiv"
                                                 src="<c:url value="/images/i-icon.png"/>">
                                        </li>
                                    </ul>
                                    <div class="sensitive_analysis_container">
                                        <div id="single_resource" style="display: block;">
                                            <%--	@added - Abhishek 	@date - 25-02-2016		@desc - According to slide#8 of 02212016	--%>
                                            <%--<label class="resource_label">Select Resource to vary and the Amount of each</label>
                                                <a class="help_Infromation_PopUp" id="SAChangeInPotentialProfit">
                                                    <img src="<c:url value="/images/i-icon.png"/>">
                                                </a>--%>
                                            <div class="resource_value">
                                                <div class="value_input">
                                                    <%--	@added - Abhishek 	@date - 25-02-2016		@desc - According to slide#8 of 02212016	--%>
                                                    <label>Resource to analyze</label>
                                                    <select id="forCastGraphResourceList">
                                                        <c:forEach var="resource" items="${model.resourceList}">
                                                            <c:if test="${resource.isActive()}">
                                                                <c:choose>
                                                                    <c:when test="${model.farmInfoView.strategy eq 'PLAN_BY_ACRES' and resource.cropResourceUse eq 'Land'}">
                                                                        <option value="${resource.cropResourceUse}">${resource.cropResourceUse}</option>
                                                                    </c:when>
                                                                    <c:when test="${resource.cropResourceUse eq 'Capital'}">
                                                                        <option value="${resource.cropResourceUse}">
                                                                            Working Capital
                                                                        </option>
                                                                    </c:when>
                                                                    <c:when test="${model.farmInfoView.strategy ne 'PLAN_BY_FIELDS' and resource.cropResourceUse ne 'Land'}">
                                                                        <option value="${resource.cropResourceUse}">${resource.cropResourceUse}</option>
                                                                    </c:when>
                                                                </c:choose>

                                                            </c:if>

                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="value_input">
                                                    <%--	@added - Abhishek 	@date - 25-02-2016		@desc - According to slide#8 of 02212016	--%>
                                                    <label>Analysis Increment</label>
                                                    <input id="forCastGraphValueForSingleResource"
                                                           placeholder="0" type="text"
                                                           onchange="addCommaSignWithOutDollarDot(this);"
                                                           onkeypress="return isValidNumberValueForForCastSA(event)"/>
                                                </div>
                                                <!--
                                                    @Changed - Abhishek
                                                    @date - 25-11-2015
                                                -->
                                                <div class="yellobtn sensitive_updatebtn">
                                                    <a id="forCastGraphSubmitButtonForSingleResource"
                                                       onclick="forCastGraphForSingleResource()">Analyze</a>
                                                </div>
                                                <%--	@added - Abhishek 	@date - 25-02-2016		@desc - According to slide#12 of 02212016	--%>
                                                <div class="sensitive_updatebtn">
                                                    <a class="help_Infromation_PopUp"
                                                       id="SAChangeInPotentialProfitResource"
                                                       style="padding-left: 10px">
                                                        <img src="<c:url value="/images/i-icon.png"/>">
                                                    </a>
                                                </div>
                                                <div class="pull-right" style="width: 30%;">
                                                    <c:choose>
                                                        <%--<c:when test="${model.farmInfoView.strategy eq 'PLAN_BY_ACRES'}">--%>
                                                            <%--<div style="margin-top: 8%; padding: 1% 3%;">--%>
                                                                <%--To increase the amount of Land go to<br>--%>
                                                                <%--<a href="javascript:;"--%>
                                                                   <%--onclick="navigateToResources(); return false;"--%>
                                                                   <%--style="text-decoration: underline;">Resources</a>--%>
                                                            <%--</div>--%>
                                                        <%--</c:when>--%>
                                                        <c:when test="${model.farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                                                            <div style="margin-top: 8%; padding: 1% 3%;">
                                                                To increase the amount of Land go to<br>
                                                                <a href="javascript:;"
                                                                   onclick="navigateToFieldInformation(); return false;"
                                                                   style="text-decoration: underline;">Field Information </a>
                                                            </div>
                                                        </c:when>
                                                    </c:choose>
                                                </div>
                                            </div>
                                            <div class="box box-info">
                                                <div class="box-body chart-responsive">
                                                    <div id="forCastSingleResourcechartdiv"
                                                         style="width: 100%; height: 400px; background-color: #FFFFFF; margin-top: 12px; float: left; display: none;"></div>
                                                    <p class="pull-left" style="display: none" id="SingleResource_Message">
                                                        * One or more factors preventing all available land from being planted.
                                                        <c:url value="/troubleshoot.htm" var="troubleshooturl">
                                                            <c:param name="farmId" value="${farmId}"/>
                                                            <c:param name="key" value="unused"/>
                                                        </c:url>
                                                        <a class="remove-text-deco" style="color: red" href="<c:out value="${troubleshooturl}"/>" target="_blank">${resourceList.impactingProfit}Troubleshooting </a>
                                                    </p>
                                                </div>
                                                <!-- /.box-body -->
                                            </div>
                                        </div>
                                        <div id="multiple_resources" style="display: none;">
                                            <label class="resource_label">Change the amounts of one or more
                                                resources</label>
                                            <div class="table-responsive Crop-Acreage-tabel">
                                                <table id="sa_multiple_resource_table" width="100%" cellspacing="0"
                                                       class="table table-striped tbl-bordr tblbrdr text-center">
                                                    <thead>
                                                    <tr class="tblhd add-fieldi">
                                                        <td class="tblbrdr add-fieldi">Resource</td>
                                                        <td class="add-fieldi">Original</td>
                                                        <td>New</td>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach var="resourceList" items="${model.resourceList}">
                                                        <c:if test="${resourceList.isActive()}">
                                                            <c:choose>
                                                                <%--<c:when test="${resourceList.cropResourceUse eq 'Land'}">
                                                                    <tr class="tblgrn line_no_break">
                                                                        <td class="success">${resourceList.cropResourceUse}</td>
                                                                        <td class="success">${resourceList.cropResourceUseAmount}</td>
                                                                        <td class="success croplimit"><input type="text"
                                                                                                             value="${resourceList.cropResourceUseAmount}"
                                                                                                             onchange="addCommaSignWithOutDollarDot(this);"
                                                                                                             onkeypress="return isValidNumberValueForWithOutDot(event)" />
                                                                        </td>
                                                                    </tr>
                                                                </c:when>--%>
                                                                <c:when test="${model.farmInfoView.strategy eq 'PLAN_BY_ACRES' and resourceList.cropResourceUse eq 'Land'}">
                                                                    <tr class="tblgrn line_no_break">
                                                                        <td class="success">Land</td>
                                                                        <td class="success">${resourceList.cropResourceUseAmount}</td>
                                                                        <td class="success croplimit"><input type="text"
                                                                                                             value="${resourceList.cropResourceUseAmount}"
                                                                                                             onchange="addCommaSignWithOutDollarDot(this);"
                                                                                                             onkeypress="return isValidNumberValueForWithOutDot(event)"/>
                                                                        </td>
                                                                    </tr>
                                                                </c:when>
                                                                <c:when test="${model.farmInfoView.strategy eq 'PLAN_BY_FIELDS' and resourceList.cropResourceUse eq 'Land'}">
                                                                    <tr class="tblgrn line_no_break">
                                                                        <td class="success">${resourceList.cropResourceUse}</td>
                                                                        <td class="success">${resourceList.cropResourceUseAmount}</td>
                                                                        <td class="success croplimit"><input type="text"
                                                                                                             disabled
                                                                                                             value="${resourceList.cropResourceUseAmount}"
                                                                                                             onchange="addCommaSignWithOutDollarDot(this);"
                                                                                                             onkeypress="return isValidNumberValueForWithOutDot(event)"/>
                                                                        </td>
                                                                    </tr>
                                                                </c:when>
                                                                <c:when test="${resourceList.cropResourceUse eq 'Capital'}">
                                                                    <tr class="tblgrn line_no_break">
                                                                        <td class="success">Working Capital</td>
                                                                        <td class="success">${resourceList.cropResourceUseAmount}</td>
                                                                        <td class="success croplimit"><input type="text"
                                                                                                             value="${resourceList.cropResourceUseAmount}"
                                                                                                             onchange="addCommaSignWithOutDollarDot(this);"
                                                                                                             onkeypress="return isValidNumberValueForWithOutDot(event)"/>
                                                                        </td>
                                                                    </tr>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <tr class="tblgrn line_no_break">
                                                                        <td class="success">${resourceList.cropResourceUse}</td>
                                                                        <td class="success">${resourceList.cropResourceUseAmount}</td>
                                                                        <td class="success croplimit"><input type="text"
                                                                                                             value="${resourceList.cropResourceUseAmount}"
                                                                                                             onchange="addCommaSignWithOutDollarDot(this);"
                                                                                                             onkeypress="return isValidNumberValueForWithOutDot(event)"/>
                                                                        </td>
                                                                    </tr>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:if>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <%--@changed - Abhishek     @date - 08-12-2015--%>
                                            <div class="baseline">
                                                <p class="static_result_shown">
                                                    <span class="leftspan">Baseline Estimated Income:</span> <span class="rightspan">$${model.potentialProfit}</span>
                                                </p>
                                                <p class="static_result_shown">
                                                    <span class="leftspan">Estimated Income from change to Baseline:</span> <span
                                                        class="new_potential_profit rightspan">$${model.potentialProfit}</span>
                                                </p>
                                                <p class="static_result_shown">
                                                    <span class="leftspan">Difference:</span> <span class="difference_bet_potential_profit rightspan">$0</span>
                                                </p>
                                            </div>
                                            <div class="pull-left" style="width: 49%;">
                                                <c:choose>
                                                    <c:when test="${model.farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                                                <div style="margin-top: 8%; padding: 1% 3%;">
                                                   To increase the amount of Land go to<br>
                                                    <%--<a href="javascript:;"--%>
                                                       <%--onclick="navigateToResources(); return false;"--%>
                                                       <%--style="text-decoration: underline;">Resources</a>--%>
                                                    <a href="javascript:;"
                                                       onclick="navigateToFieldInformation(); return false;"
                                                       style="text-decoration: underline;">Field Information</a>
                                                </div>
                                                    </c:when>
                                                   <%-- <c:otherwise>
                                                        <div style="margin-top: 8%; padding: 1% 3%;">
                                                            To increase the amount of Land go to<br>
                                                            <a href="javascript:;"
                                                               onclick="navigateToFieldInformation(); return false;"
                                                               style="text-decoration: underline;">Field Information</a>
                                                        </div>
                                                    </c:otherwise>--%>

                                                </c:choose>
                                            </div>

                                            <div style="width: 50%; float: right;">
                                                <!-- <div class="yellobtn pre_next">
                                                    <a onclick="getStrategyForMultipleResourcesForCreateNewScenario()">Update</a>
                                                </div> -->

                                                <!--
                                                    @Changed - Abhishek
                                                    @Date - 25-11-2015
                                                -->
                                                <div id="available-acreage-not-planted-msg" style="display: none;margin-left: -10px;">
                                                    All available acreage not planted.
                                                    <c:url value="/troubleshoot.htm" var="troubleshooturl">
                                                        <c:param name="farmId" value="${farmId}"/>
                                                        <c:param name="key" value="unused"/>
                                                    </c:url>
                                                    <a class="remove-text-deco" style="color: red" href="<c:out value="${troubleshooturl}"/>" target="_blank">${resourceList.impactingProfit}Troubleshooting </a>
                                                </div>
                                                <div class="yellobtn pre_next"
                                                     onclick="getStrategyForMultipleResources()">
                                                    <a>Analyze</a>
                                                </div>
                                                <!--
                                                    @Changed - Abhishek
                                                    @Date - 10-12-2015
                                                -->
                                                <div id="multipleResourceViewStrategy" class="yellobtn pre_next hidden"
                                                     onclick="showSensetiveAnalysisCropAndResourcePopup(); return false;">
                                                    <a>View Strategy</a>
                                                </div>
                                            </div>

                                        </div>
                                        <!-- /.box-body -->
                                    </div>
                                </div>

                            </div>

                        </div>
                        <!-- @end #Resource-Use -->

                        <div class="contentblock hidden" id="Crop-Limits">
                            <div class="table-responsive" style="max-height: 330px;">
                                <table width="100%" cellspacing="0"
                                       class="table table-striped tbl-bordr  tblbrdr output_table">
                                    <thead>
                                    <tr class="tblhd add-fieldi">
                                        <td class="tblbrdr add-fieldi">Crop</td>
                                        <td>Minimum Limit</td>
                                        <td>Maximum Limit</td>
                                        <c:if test="${model.farmInfoView.strategy ne 'PLAN_BY_FIELDS'}">
                                            <td>Impacting Income</td>
                                            <td>To Increase Estimated Income</td>
                                        </c:if>
                                        <td>Acreage Assigned</td>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach var="cropLimit" items="${model.cropLimitsJsonArray}">
                                        <tr class="tblgrn">
                                            <td class="success">${cropLimit.cropName}</td>
                                            <td class="success">${cropLimit.minLimit}</td>
                                            <td class="success">${cropLimit.maxLimit}</td>
                                            <c:if test="${model.farmInfoView.strategy ne 'PLAN_BY_FIELDS'}">
                                                <td class="success">
                                                    <c:choose>
                                                        <c:when test="${cropLimit.impactingIncome ne '--'}">
                                                            <a href="javascript:void(0)"
                                                               class="remove-text-deco"
                                                               data-toggle="popover"
                                                               data-trigger="hover"
                                                               data-placement="top"
                                                               style="color:#337ab7"
                                                               data-content="${cropLimit.message}">${cropLimit.impactingIncome}</a>
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${cropLimit.impactingIncome}
                                                        </c:otherwise>
                                                    </c:choose>

                                                </td>
                                                <td class="success">${cropLimit.incDecIncome}</td>
                                            </c:if>
                                            <td class="success">${cropLimit.acreagePlanted}</td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <%--	@changed - Abhishek 	@date - 25-02-2016		@desc - According to slide#8 of 02212016	--%>
                                    <%--<h3 class="panel-title inc_dec_title">Change in Estimated Income by Increasing or Decreasing Crop Limit :</h3>--%>
                                    <h3 class="panel-title inc_dec_title">
                                        <%--Analyze Estimated Income by increasing or decreasing crop limit.--%>
                                        Increase or decrease Crop Acreage Limits to analyze the impact on Estimated
                                        Income
                                        <a class="help_Infromation_PopUp" id="cropLimitSestivityDiv"
                                           style="padding-left: 10px">
                                            <img src="<c:url value="/images/i-icon.png"/>">
                                        </a>
                                    </h3>
                                    <span class="pull-right clickable panel-collapsed"><i
                                            class="fa fa-chevron-down"></i></span>
                                </div>
                                <div class="panel-body" style="display: none;">
                                    <ul class="tabs" data-persist="true">
                                        <li class="selected"><a href="#single_crop_limit">Single Crop Limit</a></li>
                                        <li class="">
                                            <a href="#multiple_crop_limit">Multiple Crop Limits</a>
                                            <img class="help_Infromation_PopUp" id="cropLimitSestivityMultipleDiv"
                                                 src="<c:url value="/images/i-icon.png"/>">
                                        </li>
                                    </ul>
                                    <div class="sensitive_analysis_container">
                                        <div id="single_crop_limit" style="display: block;">
                                            <%--	@changed - Abhishek 	@date - 25-02-2016		@desc - According to slide#8 of 02212016	--%>
                                            <%--<label class="resource_label">Select which Crop Limit to vary and the amount of each</label>--%>
                                            <div class="resource_value">
                                                <div class="pull-right">
                                                    <div style="margin-bottom: 3%;">
                                                        To create new Crop Acreage Limits go to<br>
                                                        <a href="javascript:;"
                                                           onclick="navigateToFieldInformationOnsamepage(); return false;"
                                                           style="text-decoration: underline;">Field Information
                                                        </a>
                                                    </div>
                                                </div>
                                                <div class="clearfix"></div>

                                                <div class="value_input width-26">
                                                    <%--	@added - Abhishek 	@date - 25-02-2016		@desc - According to slide#8 of 02212016	--%>
                                                    <label>Crop acreage limit to analyze</label>
                                                    <select id="forCastGraphCropList"
                                                            onchange="switchForContractOrProposed(); checkMinMaxForCrop(this); return false;">
                                                        <option value="">Select crop</option>
                                                        <c:set value="0" var="cropLimitFlag"/>
                                                        <c:forEach var="cropTypeForCropLimit"
                                                                   items="${model.cropTypeView}">
                                                            <c:if test="${cropTypeForCropLimit.selected}">
                                                                <c:if test="${(cropTypeForCropLimit.minimumAcres ne '' and cropTypeForCropLimit.minimumAcres ne '0')
																		or (cropTypeForCropLimit.maximumAcres ne '' and cropTypeForCropLimit.maximumAcres ne '0')}">
                                                                    <c:set value="1" var="cropLimitFlag"/>
                                                                    <option value="Crop#-#-#${cropTypeForCropLimit.cropName}#-#-#${cropTypeForCropLimit.id}">${cropTypeForCropLimit.cropName}</option>
                                                                    <c:choose>
                                                                        <c:when test="${cropTypeForCropLimit.selected and cropTypeForCropLimit.firmchecked eq 'true'}">
                                                                            <option value="Contract#-#-#${cropTypeForCropLimit.cropName}#-#-#${cropTypeForCropLimit.id}">${cropTypeForCropLimit.cropName}
                                                                                (Firm)
                                                                            </option>
                                                                        </c:when>
                                                                        <c:when test="${cropTypeForCropLimit.selected and cropTypeForCropLimit.proposedchecked eq 'true'}">
                                                                            <option value="Proposed#-#-#${cropTypeForCropLimit.cropName}#-#-#${cropTypeForCropLimit.id}">${cropTypeForCropLimit.cropName}
                                                                                (Proposed)
                                                                            </option>
                                                                        </c:when>
                                                                    </c:choose>
                                                                </c:if>
                                                            </c:if>

                                                        </c:forEach>
                                                        <c:forEach var="cropsGroupView"
                                                                   items="${model.cropsGroupViews}">
                                                            <c:if test="${(cropsGroupView.minimumAcres ne '' and cropsGroupView.minimumAcres ne '0')
																		or (cropsGroupView.maximumAcres ne '' and cropsGroupView.maximumAcres ne '0')}">
                                                                <c:set value="1" var="cropLimitFlag"/>
                                                                <option value="Group#-#-#${cropsGroupView.cropsGroupName}#-#-#${cropsGroupView.id}">${cropsGroupView.cropsGroupName}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                        <c:if test="${cropLimitFlag == 0}">
                                                            <option value="Crop#-#-#">No Crop Limit specifed</option>
                                                        </c:if>

                                                    </select>
                                                </div>
                                                <div class="value_input width-26">
                                                    <%--	@added - Abhishek 	@date - 25-02-2016		@desc - According to slide#8 of 02212016	--%>
                                                    <label>Min or Max</label>
                                                    <select id="max_min_selector">
                                                        <option value="">Select crop first</option>
                                                        <%--<option value="Minimum">Minimum</option>--%>
                                                        <%--<option value="Maximum">Maximum</option>--%>
                                                    </select>
                                                </div>
                                                <div class="value_input width-26">
                                                    <%--	@added - Abhishek 	@date - 25-02-2016		@desc - According to slide#8 of 02212016	--%>
                                                    <label>Analysis Increment</label>
                                                    <input id="forCastGraphValueForSingleCrop" placeholder="0"
                                                           type="text" onchange="addCommaSignWithOutDollarDot(this);"
                                                           onkeypress="return isValidNumberValueForForCastSA(event)"/>
                                                </div>
                                                <div class="value_input width-16">
                                                    <div class="yellobtn sensitive_updatebtn">
                                                        <a id="forCastGraphSubmitButtonForSingleCrop"
                                                           onclick="forCastGraphForSingleCropLimit()">Analyze</a>
                                                        <!--  onclick="forCastGraphForSingleCropLimit()" -->
                                                    </div>
                                                    <%--	@added - Abhishek 	@date - 25-02-2016		@desc - According to slide#13 of 02212016	--%>
                                                    <div class="sensitive_updatebtn">
                                                        <a class="help_Infromation_PopUp"
                                                           id="SAChangeInPotentialProfitCropLimits"
                                                           style="padding-left: 5px">
                                                            <img src="<c:url value="/images/i-icon.png"/> ">
                                                        </a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="box box-info">
                                                <div class="box-body chart-responsive">
                                                    <div id="forCastSingleCropLimitchartdiv"
                                                         style="width: 100%; height: 400px; background-color: #FFFFFF; margin-top: 12px; float: left; display: none;"></div>
                                                </div>
                                                <p class="pull-left" id="SingleCrop_Message" style="display: none">
                                                    * One or more factors preventing all available land from being planted.
                                                    <c:url value="/troubleshoot.htm" var="troubleshooturl">
                                                        <c:param name="farmId" value="${farmId}"/>
                                                        <c:param name="key" value="unused"/>
                                                    </c:url>
                                                    <a class="remove-text-deco" style="color: red" href="<c:out value="${troubleshooturl}"/>" target="_blank">${resourceList.impactingProfit}Troubleshooting </a>
                                                </p>
                                                <!-- /.box-body -->
                                            </div>

                                        </div>
                                        <div id="multiple_crop_limit" style="display: none;">
                                            <label class="resource_label">Change one or more crop acreage limits</label>
                                            <div class="table-responsive Crop-Acreage-tabel">
                                                <table cellspacing="0"
                                                       class="table table-striped tbl-bordr tblbrdr text-center multiple_resource_table">
                                                    <thead>
                                                    <tr class="tblhd add-fieldi">
                                                        <td class="tblbrdr add-fieldi">Crop</td>
                                                        <%--<td class="add-fieldi">Maximum</td>
                                                        <td class="add-fieldi">Minimum</td>
                                                        <td class="add-fieldi">New Maximum</td>
                                                        <td class="add-fieldi">New Minimum</td>--%>
                                                        <td class="add-fieldi">Minimum</td>
                                                        <td class="add-fieldi">Maximum</td>
                                                        <td class="add-fieldi">New Minimum</td>
                                                        <td class="add-fieldi">New Maximum</td>
                                                    </tr>
                                                    </thead>
                                                    <tbody id="sa_multiple_crops_tbody">
                                                    <c:set var="multipleCropsLimitsFlag" value="0"/>
                                                    <c:forEach var="cropListForCropLimit" items="${model.cropTypeView}">
                                                        <c:if test="${cropListForCropLimit.selected
																		and ( (cropListForCropLimit.minimumAcres ne '' and cropListForCropLimit.minimumAcres ne '0')
																		or (cropListForCropLimit.maximumAcres ne '' and cropListForCropLimit.maximumAcres ne '0') )}">
                                                            <c:set var="multipleCropsLimitsFlag" value="1"/>
                                                            <tr class="tblgrn">
                                                                <td class="success">${cropListForCropLimit.cropName}</td>
                                                                <td class="success">${cropListForCropLimit.minimumAcres eq '' ? '-' : cropListForCropLimit.minimumAcres}</td>
                                                                <td class="success">${cropListForCropLimit.maximumAcres eq '' ? '-' : cropListForCropLimit.maximumAcres}</td>
                                                                <td class="success">
                                                                    <c:choose>
                                                                        <c:when test="${cropListForCropLimit.minimumAcres ne ''}">
                                                                            <input type="text"
                                                                                   onchange="addCommaSignWithOutDollarDot(this);"
                                                                                   onkeypress="return isValidNumberValueForWithOutDot(event)"
                                                                                   value="${cropListForCropLimit.minimumAcres}">
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            -
                                                                            <input type="hidden"
                                                                                   value="${cropListForCropLimit.minimumAcres}">
                                                                        </c:otherwise>
                                                                    </c:choose>

                                                                </td>
                                                                <td class="success">
                                                                    <c:choose>
                                                                        <c:when test="${cropListForCropLimit.maximumAcres ne ''}">
                                                                            <input type="text"
                                                                                   onchange="addCommaSignWithOutDollarDot(this);"
                                                                                   onkeypress="return isValidNumberValueForWithOutDot(event)"
                                                                                   value="${cropListForCropLimit.maximumAcres}">
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            -
                                                                            <input type="hidden"
                                                                                   value="${cropListForCropLimit.maximumAcres}">
                                                                        </c:otherwise>
                                                                    </c:choose>

                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                    </tbody>
                                                    <tbody id="sa_multiple_crop_contract_tbody">
                                                    <c:forEach var="cropListForCropLimit" items="${model.cropTypeView}">
                                                        <c:if test="${cropListForCropLimit.selected and cropListForCropLimit.firmchecked eq 'true'
																		and ( (cropListForCropLimit.minimumAcres ne '' and cropListForCropLimit.minimumAcres ne '0')
																		or (cropListForCropLimit.maximumAcres ne '' and cropListForCropLimit.maximumAcres ne '0') )}">
                                                            <c:set var="multipleCropsLimitsFlag" value="1"/>
                                                            <tr class="tblgrn">
                                                                <td class="success">${cropListForCropLimit.cropName}
                                                                    (Firm)
                                                                </td>
                                                                <td class="success">${cropListForCropLimit.forwardAcres}</td>
                                                                <td class="success">NA</td>
                                                                <td class="success">
                                                                    <c:choose>
                                                                        <c:when test="${cropListForCropLimit.forwardAcres ne '' and cropListForCropLimit.forwardAcres ne '0'}">
                                                                            <input type="text"
                                                                                   onchange="addCommaSignWithOutDollarDot(this);"
                                                                                   onkeypress="return isValidNumberValueForWithOutDot(event)"
                                                                                   value="${cropListForCropLimit.forwardAcres}">
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            -
                                                                            <input type="hidden"
                                                                                   value="${cropListForCropLimit.forwardAcres}">
                                                                        </c:otherwise>
                                                                    </c:choose>

                                                                </td>
                                                                <td class="success">NA</td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                    </tbody>
                                                    <tbody id="sa_multiple_crop_proposed_tbody">
                                                    <c:forEach var="cropListForCropLimit" items="${model.cropTypeView}">
                                                        <c:if test="${cropListForCropLimit.selected and cropListForCropLimit.proposedchecked eq 'true'
																		and ( (cropListForCropLimit.minimumAcres ne '' and cropListForCropLimit.minimumAcres ne '0')
																		or (cropListForCropLimit.maximumAcres ne '' and cropListForCropLimit.maximumAcres ne '0') )}">
                                                            <c:set var="multipleCropsLimitsFlag" value="1"/>
                                                            <tr class="tblgrn">
                                                                <td class="success">${cropListForCropLimit.cropName}(Proposed)</td>
                                                                <td class="success">${cropListForCropLimit.forwardAcres}</td>
                                                                <td class="success">NA</td>
                                                                <td class="success">
                                                                    <c:choose>
                                                                    <c:when test="${cropListForCropLimit.forwardAcres ne '' and cropListForCropLimit.forwardAcres ne '0'}">
                                                                    <input type="text"
                                                                           onchange="addCommaSignWithOutDollarDot(this);"
                                                                           onkeypress="return isValidNumberValueForWithOutDot(event)"
                                                                           value="${cropListForCropLimit.forwardAcres}">
                                                                </td>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    -
                                                                    <input type="hidden"
                                                                           value="${cropListForCropLimit.forwardAcres}">
                                                                </c:otherwise>
                                                                </c:choose>

                                                                <td class="success">NA</td>
                                                            </tr>
                                                        </c:if>
                                                    </c:forEach>
                                                    </tbody>
                                                    <tbody id="sa_multiple_groups_tbody">
                                                    <c:forEach var="cropsGroupViewsList"
                                                               items="${model.cropsGroupViews}">
                                                        <c:if test="${(cropsGroupViewsList.minimumAcres ne '' and cropsGroupViewsList.minimumAcres ne '0')
																		or (cropsGroupViewsList.maximumAcres ne '' and cropsGroupViewsList.maximumAcres ne '0')}">
                                                            <c:set var="multipleCropsLimitsFlag" value="1"/>
                                                            <tr class="tblgrn">
                                                                <td class="success">${cropsGroupViewsList.cropsGroupName}</td>
                                                                <td class="success">${cropsGroupViewsList.minimumAcres eq '0' ? '-' : cropsGroupViewsList.minimumAcres}</td>
                                                                <td class="success">${cropsGroupViewsList.maximumAcres eq '0' ? '-' : cropsGroupViewsList.maximumAcres}</td>
                                                                <td class="success">
                                                                    <c:choose>
                                                                        <c:when test="${cropsGroupViewsList.minimumAcres ne '' and cropsGroupViewsList.minimumAcres ne '0'}">
                                                                            <input type="text"
                                                                                   onchange="addCommaSignWithOutDollarDot(this);"
                                                                                   onkeypress="return isValidNumberValueForWithOutDot(event)"
                                                                                   value="${cropsGroupViewsList.minimumAcres eq '0' ? '' : cropsGroupViewsList.minimumAcres}">
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            -
                                                                            <input type="hidden"
                                                                                   value="${cropsGroupViewsList.minimumAcres}">
                                                                        </c:otherwise>
                                                                    </c:choose>

                                                                </td>
                                                                <td class="success">
                                                                    <c:choose>
                                                                        <c:when test="${cropsGroupViewsList.maximumAcres ne '' and cropsGroupViewsList.maximumAcres ne '0'}">
                                                                            <input type="text"
                                                                                   onchange="addCommaSignWithOutDollarDot(this);"
                                                                                   onkeypress="return isValidNumberValueForWithOutDot(event)"
                                                                                   value="${cropsGroupViewsList.maximumAcres eq '0' ? '' : cropsGroupViewsList.maximumAcres}">
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            -
                                                                            <input type="hidden"
                                                                                   value="${cropsGroupViewsList.maximumAcres}">
                                                                        </c:otherwise>
                                                                    </c:choose>

                                                                </td>

                                                            </tr>
                                                        </c:if>

                                                    </c:forEach>
                                                    </tbody>
                                                    <tbody>
                                                    <c:if test="${multipleCropsLimitsFlag eq 0}">
                                                        <tr class="tblgrn">
                                                            <td class="success" colspan="5">No Crop Limits specifed.</td>
                                                        </tr>
                                                    </c:if>
                                                    </tbody>
                                                </table>
                                            </div>
                                            <%-- @changed - Abhishek        @date - 08-12-2015 --%>
                                            <div class="baseline">
                                                <p class="static_result_shown">
                                                    <span class="leftspan">Baseline Estimated Income:</span> <span class="rightspan">$${model.potentialProfit}</span>
                                                </p>
                                                <p class="static_result_shown">
                                                    <span class="leftspan">Estimated Income from change to Baseline:</span> <span
                                                        class="new_potential_profit rightspan">$${model.potentialProfit}</span>
                                                </p>
                                                <p class="static_result_shown">
                                                    <span class="leftspan">Difference:</span> <span class="difference_bet_potential_profit rightspan">$0</span>
                                                </p>
                                                <div class="yellobtn pre_next" onclick="getStrategyForMultipleCrops()">
                                                    <a>Analyze</a>
                                                </div>

                                                <!-- @changed - Abhishek    @date - 10-12-2015 -->
                                                <div id="viewStrategyMultipleCrops" class="yellobtn pre_next hidden"
                                                     onclick="showSensetiveAnalysisCropAndResourcePopup(); return;">
                                                    <a>View Strategy</a>
                                                </div>
                                            </div>
                                            <div class="pull-left" style="width: 49%;">
                                                <div style="margin-top: 8%; padding: 1% 3%;">
                                                    To create new Crop Acreage Limits go to<br>
                                                    <a href="javascript:;"
                                                       onclick="navigateToCropLimits(); return false;"
                                                       style="text-decoration: underline;">Crop Acreage Limits</a>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- /.box-body -->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- @end #Crop-Limits -->

                        <div class="contentblock hidden" id="Forward-Sales">
                            <div class="table-responsive" style="max-height: 329px;">
                                <table width="100%" cellspacing="0" class="table table-striped tbl-bordr  tblbrdr">
                                    <thead>
                                    <tr class="tblhd add-fieldi">
                                        <td class="tblbrdr add-fieldi">Crop</td>
                                        <td class="add-fieldi">Contract Amount and Price</td>
                                        <td class="add-fieldi">Firm/Proposed</td>
                                        <td>Filled</td>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="jsonObj" items="${model.forwardSalesJsonArray}">
                                        <tr class="tblgrn">
                                            <td class="success">${jsonObj.cropName}</td>
                                            <td class="success">${jsonObj.forwardSalesAmount}</td>
                                            <td class="success">${jsonObj.firmProposedCheck}</td>
                                            <td class="success">${jsonObj.status}</td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>
                            </div>
                            <span>${model.mapDifferentValues["usedForwardAcresP"]}% of Estimated Income forward sold</span>
                        </div>
                        <!-- @end #Forward-Sales -->
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="common/right_slider.jsp" %>
    </div>
</div>

<div id="critical-message-pop-up" style="display: none;">
    <div id="popupContact">
        <!-- Planning Form -->
        <div class="popup_section">
            <img id="close" onclick="hideCriticalMessagePopup()" src="<c:url value="/images/cross.png"/>">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="increase_profit">
                    <p>
                        Increasing <span id="resourceNameInc"></span>
                        will increase Estimated Income by $<span id="profitBy1Dollar"></span>
                        for each <span id="unitCriticalMessage"></span>
                        added up to <span id="upResourceLimit"></span>
                    </p>
                </div>
                <div class="decrease_profit">
                    <p>
                        Decreasing <span id="resourceNameDec"></span>
                        will decrease Estimated Income by $<span id="lossBy1Dollar"></span>
                        for eachdollar removed down to <span id="downResourceLimit"></span>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Abhishek - 04-01-2016 - Add popup on mouseover for guidelines -->
<div id="criticalMessageForCropOrGroupPopUp" style="display: none;">
    <div id="popupContact">
        <!-- Planning Form -->
        <div class="popup_section">
            <img class="closePopup" onclick="$('#criticalMessageForCropOrGroupPopUp').hide()"
                 src="<c:url value="/images/cross.png"/>">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="increase_profit">
                    <p>
                        Increasing acres of <span class="cropOrGroupName"></span>
                        will decrease Estimated Income by $<span id="maximumDualvalue"></span>
                        for each acre added up to <span id="maximumPrimalValue"></span> acres.
                    </p>
                </div>
                <div class="decrease_profit">
                    <p>
                        Decreasing acres of <span class="cropOrGroupName"></span>
                        will increase Estimated Income by approximately <span id="minimumDualvalue"></span>
                        for each acre removed down to <span id="minimumPrimalValue"></span> acres.
                    </p>
                </div>
                <!-- <div class="decrease_profit">
                    <p>Decreasing <span id="resourceNameDec"></span> will decreaseEstimated Income by <span id="lossBy1Dollar"></span> for eachdollar removed down to <span id="downResourceLimit"></span></p>
                </div> -->
            </div>
        </div>
    </div>
</div>


<div id="sensetiveAnalysisCropAndResourcePopup" style="display: none;">
    <div id="popupContact" style="left: 33%; width: 35%;">
        <!-- Planning Form -->
        <div class="popup_section">
            <img class="closePopup" onclick="hideSensetiveAnalysisCropAndResourcePopup()"
                 src="<c:url value="/images/cross.png"/>">
            <div class="popupform messagepopup">
                <ul class="potential_criteria">
                    <li><span id="sensetiveAnalysisCropAndResourcePotentialProfitSpan"></span></li>
                </ul>
                <div class="clr"></div>
                <div class="table-responsive Crop-Acreage-tabel">
                    <table id="sensetiveAnalysisCropAndResourceTable" width="100%"
                           cellspacing="0"
                           class="table table-striped tbl-bordr  tblbrdr output_table">
                        <thead>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div id="acreage-not-planted-msg" style="display: none">All available acreage not planted.</div>
                <div id="field_crop_button"></div>
            </div>
        </div>
    </div>
</div>

<!-- Edited By :- sawai singh start -->
<div id="createNewScenario" style="display: none;">
    <div id="popupContact">
        <!-- Planning Form -->
        <div class="popup_section">
            <img class="closePopup" onclick="hideCreateNewScenario()" src="<c:url value="/images/cross.png"/>"/>
            <h3 class="farm_name">${model.farmInfoView.farmName}</h3>
            <div class="popupform messagepopup create_scenario_section">
                <p class="potential_profit">
                    Estimated Income: <span id="potential_profit_id">$000,000</span>
                </p>
                <div id="addcroppopup" class="addcroppup">
                    <!-- <label>Add New Strategy Name</label>
                     <input type="text" id="pop-up-field-name" /> -->
                </div>

                <!-- Updated values in resources for this scenario-->
                <div id="createScenarioGraph" class="graph_field">
                    <div class="addremove-field padding-left-none pull-right">
                        <a id="createScenarioShow" class="show_text"> <img src="<c:url value="/images/showtext.png"/>"></a>
                    </div>
                    <div class="clr"></div>
                    <!-- <div class="sensitive_analysis_container">
                        <div id="sensitiveAnalysisCreateScenarioGraph" class="chartdiv" style="width: 100%; height: 400px; background-color: #FFFFFF;"></div>
                    </div> -->
                    <div class="graph_area">
                        <!--Horizontal tabs start-->
                        <ul class="tabs" data-persist="true">
                            <li class="selected" id="createScenarioGraphShowByFieldLi">
                                <%--<a href="#createScenarioGraphShowByFieldDiv"> <span class="icon"><img src="images/fields_icon.png"></span>&nbsp;By Fields</a>--%>
                                <a href="#createScenarioGraphShowByFieldDiv"> <span class="icon"><img
                                        src="<c:url value="/images/acres_icon.png"/>"></span>&nbsp;By Acres</a>
                            </li>
                            <li class="" id="createScenarioGraphShowByCropsLi">
                                <%--<a href="#createScenarioGraphShowByCropsDiv"><span class="icon"> <img src="images/crops_icon.png"></span>&nbsp;By Crops</a>--%>
                                <a href="#createScenarioGraphShowByCropsDiv"><span class="icon"> <img
                                        src="<c:url value="/images/profit.png"/>"></span>&nbsp;By Profit</a>
                            </li>
                        </ul>
                        <div class="home-search-tab-container">
                            <div id="createScenarioGraphShowByFieldDiv" style="display: block;">
                                <div id="createScenarioGraphShowByFieldGraph" class="chartdiv"
                                     style="width: 100%; height: 300px;"></div>
                            </div>
                            <div id="createScenarioGraphShowByCropsDiv" style="display: none;">
                                <div id="createScenarioGraphShowByCropsGraph" class="chartdiv"
                                     style="width: 100%; height: 300px;"></div>
                            </div>
                        </div>
                        <!--Horizontal tabs end-->
                    </div>
                </div>
                <div id="createScenarioText" class="text_field">
                    <div class="addremove-field padding-left-none pull-right">
                        <a class="show_graph" id="createScenarioGraphShow"> <img
                                src="<c:url value="/images/graph_tab.png"/>"></a>
                    </div>
                    <div class="clr"></div>
                    <ul class="tabs" data-persist="true">
                        <li class="selected">
                            <a href="#senstivity-output-tab"> <span class="icon"><i
                                    class="fa fa-bar-chart-o"></i></span>&nbsp;Strategy Output</a>
                        </li>
                        <li>
                            <a href="#senstivity-output-crop-field-tab"><span class="icon"> <i
                                    class="fa fa-paw"></i></span>&nbsp;Crop/Field</a>
                        </li>
                    </ul>
                    <div class="home-search-tab-container">
                        <div id="senstivity-output-tab" style="display: block;">
                            <div class="table-responsive Crop-Acreage-tabel" id="senstivity-output-table">
                                <table width='100%' cellspacing='0'
                                       class='table table-striped tbl-bordr  tblbrdr output_table text-center'>
                                    <thead>
                                    <tr class="tblhd add-fieldi">
                                        <td>Crop</td>
                                        <td>Acreage</td>
                                        <td>Estimated Income</td>
                                        <td>Estimated Income per Acre ($/acre)</td>
                                        <td>Land Profitability Index</td>
                                        <td>Rating</td>
                                    </tr>
                                    </thead>
                                    <tbody id="">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div id="senstivity-output-crop-field-tab" style="display: none;">
                            <div class="table-responsive Crop-Acreage-tabel" id="show_strategy_for_scenario">
                                <table width='100%' cellspacing='0'
                                       class='table table-striped tbl-bordr  tblbrdr output_table'
                                       id="strategyDetailsTable">
                                    <thead></thead>
                                    <tbody></tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="panel panel-default strategy_for_scenario">
                    <div class="panel-heading">
                        <h3 class="panel-title inc_dec_title"><p class="scenario_heading" id="scenario_heading"></p>
                        </h3>
                        <span class="pull-right clickable"><i class="fa fa-chevron-down"></i></span>
                    </div>
                    <div class="panel-body">
                        <div class="table-responsive Crop-Acreage-tabel">
                            <table id="resourceTableForCreateScenario" width='100%'
                                   cellspacing='0'
                                   class='table table-striped tbl-bordr  tblbrdr output_table'>
                                <thead>

                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="yellobtn pre_next save_senario">
                    <div id="SaveStrategyForMultipleResources_id"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="checkStrategy-pop-up" style="display: none;" class="pop-up">
    <div class="pop-up-body">
        <!-- Planning Form -->
        <div class="popup_section">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="panel panel-yellow">
                    <div class="panel-heading text-center">
                        <label style="cursor: pointer; text-transform: capitalize;"><h4>Troubleshoot Strategy</h4>
                        </label>
                    </div>

                    <div class="panel-body text-center" style="display: block">
                        <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none medium-height-overflow medium-height-overRide">
                            <p>Planting Profits could not generate a strategy that meets<br>all of your objectives and constraints.</p>
                            <c:url value="/troubleshoot.htm" var="myURL">
                                <c:param name="farmId" value="${farmId}"/>
                                <c:param name="key" value="baseline"/>
                            </c:url>
                        </div>
                        <div class="clearfix"></div>
                        <div>
                            <a class="alertify-button alertify-button-ok remove-text-deco"
                               href="${myURL}"
                                style="color:#0f0f0f">Troubleshoot</a>
                            <a class="alertify-button alertify-button-ok remove-text-deco"
                                    id="checkStrategy-pop-up-close-btn" href="javascript:;"
                                onclick="$('#checkStrategy-pop-up').hide();"
                                style="color:#0f0f0f; display: none;">Back</a>
                        </div>
                    </div>

                </div>

            </div>
        </div>
    </div>
</div>

<!-- end -->
<script>
    var farmId = '<c:out value="${model.farm.farmId}" />';
    var farmInfoId = '<c:out value="${model.farmInfoView.id}" />';
    var strategy = "${model.farmInfoView.strategy}";
    var jsonObjectForGraphs = '<c:out value="${model.jsonObjectForGraphs}" escapeXml="false"/>';
    var jsonObj = JSON.parse(jsonObjectForGraphs);
    <!--    @changed - Abhishek     @date - 11-12-2015  	@updated - 08-01-2016-->
    var checkStrategyForFarm = <c:out value="${model.checkStrategyForFarm}"/>;

    var maxLand = parseInt(removeAllCommas('${maxLand}'));

    function navigateToCropFieldChoices() {
        localStorage.setItem('cropFieldChoicesFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }
    function navigateToCropLimits() {
        localStorage.setItem('cropLimitFlag', true);
        window.location = '<c:url value="/view-farm-info.htm?farmId="/>${farmId}';
    }

    function navigateToFieldInformationOnsamepage() {
        localStorage.setItem('cropFieldFlag', true);
        window.location='<c:url value="/view-farm-info.htm?farmId="/>${farmId}';
    }
    function navigateToResources() {
        localStorage.setItem('resourcesFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }

    function navigateToFieldInformation() {
        localStorage.setItem('cropFieldFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }


</script>

<%--<script type="text/javascript" src="js/plugins/jquery.tmpl.min.js"></script>--%>
<%--<script src="<c:url value="/js/agriculture/output-farm.js"/>" type="text/javascript"></script>--%>
<script type="text/javascript" src="<c:url value="/js/agriculture/farm-info-slider.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/agriculture/output_pie_chart.js?v=1.0"/>"></script>
<script type="text/javascript" src="<c:url value="/js/agriculture/output-farm-info.js?v=1.0"/>"></script>

<script type="text/x-jQuery-tmpl" id="tableHeaderCropTemplate">
	{{if Strategy == "Acerage" }}
		<tr class="tblhd add-fieldi">
			<td class="tblbrdr add-fieldi">Crop</td>
			<td class="add-fieldi">Acreage</td>
		</tr>
		{{else Strategy == "Field" }}
			<tr class="tblhd add-fieldi">
				<td class="tblbrdr add-fieldi">Field</td>
				<td class="add-fieldi">Acreage</td>
				<td class="add-fieldi">Crop</td>
			</tr>
	{{/if}}



</script>

<script type="text/x-jQuery-tmpl" id="tableBodyCropTemplate">
	{{if Strategy == "Acerage" }}
		{{each(key, cropDetails) Crop_Details }}
			<tr class="tblgrn">
				<td class="success">{{= cropDetails.Crop_Name}}</td>
				<td class="success acreageLandUsedSpecific">{{= cropDetails.land}}</td>
			</tr>
		{{/each}}
		{{else Strategy == "Field" }}
			{{each(key, fieldDetails) Field_Crop_Info }}
				<tr class="tblgrn fieldAcreageSpecific">
					<td class="success">{{= fieldDetails.fieldName}}</td>
					<td class="success fieldSizeSpecific">{{= fieldDetails.fieldSize}}</td>
					<td class="success fieldCropInfoSpecific">{{= fieldDetails.Crop_Info}}</td>
				</tr>
			{{/each}}
	{{/if}}



</script>

<script type="text/x-jQuery-tmpl" id="resourceCropTheadTemplate">
	{{if source == "crop"}}
		<tr class='tblhd add-fieldi'>
			<td class='tblbrdr add-fieldi'>Crops</td>
			<td class='add-fieldi'>Baseline Minimum</td>
			<td class='add-fieldi'>Baseline Maximum</td>
			<td class='add-fieldi'>New Minimum</td>
			<td class='add-fieldi'>New Maximum</td>
		</tr>
		{{else source == "resource"}}
			<tr class='tblhd add-fieldi'>
				<td class='tblbrdr add-fieldi'>Resource</td>
				<td class='tblbrdr add-fieldi'>Baseline</td>
				<td class='add-fieldi'>New</td>
			</tr>
	{{/if}}



</script>

<script type="text/x-jQuery-tmpl" id="multipleCropTbodyTemplate">
	{{if source == "crop"}}
		{{each(key, crop) cropDetails}}
			<tr class="tblgrn">
				<td class="success">{{= crop.cropName}}</td>
				<td class="success">{{= crop.old_minimum}}</td>
				<td class="success">{{= crop.old_maximum}}</td>
				<td class="success">{{= crop.new_minimum}}</td>
				<td class="success">{{= crop.new_maximum}}</td>
			</tr>
		{{/each}}
		{{else source == "resource"}}
			{{each(key, resource) resourceDetails}}
			<tr class="tblgrn">
				<td class="success">{{= resource.resourceName}}</td>
				<td class="success">{{= resource.resourceValueOld}}</td>
				<td class="success">{{= resource.resourceValueNew}}</td>
			</tr>
		{{/each}}
	{{/if}}




</script>

<script type="text/x-jQuery-tmpl" id="sensitivity-output-tmpl">


	<tr class="tblgrn">
		<td class="success">{{html cropName}}</td>
		<td class="success">{{html acreage}}</td>
		<td class="success">{{html profit}}</td>
		<td class="success">{{html ratio}}</td>
		<td class="success">{{html index}}</td>
		<td class="success rating{{html rating}}"></td>
		<%--<td class="success">{{= cropTypeView.cropName}}
			{{if forFirm}}
				(Firm)
			{{else forProposed}}
				(Proposed)
			{{/if}}
		</td>
		<td class="success">&dollar;{{= profit}}<br>{{= usedCapitalPercentage}}%</td>
		<td class="success">{{= usedAcres}}<br>{{= usedAcresPercentage}}%</td>
		<td class="success">
			{{if ratio == 0.0}}
				N/A
			{{else}}
				{{= ratio}}
			{{/if}}
		</td>
		<td class="success">
			{{if profitIndex == 0.0}}
				N/A
			{{else}}
				{{= profitIndex}}
			{{/if}}

		</td>
		{{if profit != 0}}">
			<td class="success rating{{= rating}}"></td>
		{{else}}
			<td class="success ratingGrey"></td>
		{{/if}}--%>
	</tr>



</script>