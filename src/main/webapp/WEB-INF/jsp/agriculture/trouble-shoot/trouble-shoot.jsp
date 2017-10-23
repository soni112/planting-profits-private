<%--
  Created by IntelliJ IDEA.
  User: decipher03
  Date: 6/10/17
  Time: 12:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<c:set var="farmId" value="${farm.farmId}"/>

<div class="container">
    <div class="row">
        <div class="col-lg-12 col-md-12">
            <div class="jumbotron margin-top-2">

                <c:choose>
                    <c:when test="${key eq 'unused'}">
                        <h1 class="text-center">Troubleshoot Resources</h1>
                        <br>
                        <c:choose>
                            <c:when test="${farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                                <h2>
                                    <b>IF PLANNING BY FIELDS:</b><br/>
                                    <p>Why was all of my available acreage not planted?</p>
                                </h2>
                                <p>Click one or more of the following parameters to fix the problem.</p>
                                <ul class="planning">
                                    <li>
                                        <a data-toggle="collapse" href="#unusedAcreageField"><b>Why did Planting Profits not plant all of my available acreage?</b></a>
                                        <div id="unusedAcreageField" class="collapse">
                                            Planting Profits focuses on maximizing profit, not maximizing the amount of acres planted.
                                            Although most of the time these objectives are the same, this  is not always the case.
                                            Several factors may cause misalignment between maximizing profit and maximizing acres farmed.<br/>
                                            In these cases, Planting Profits to not plant all of the available land since doing so would either reduce estimated income or create a strategy that does not
                                            enforce all of the constraints entered in the farm model.
                                            Model parameters can easily be changed to bring any unplanted land into production.
                                            Of course changes must be realistic. Remember, when a strategy does not use all available acres Planting Profits is saying that there is either a real conflict
                                            between profitability and planting all available land, or there is a constraint, i.e. a management decision, that needs to be changed in order to bring the remaining acreage into production.
                                            When planning by fields, there are four common causes for a strategy that has unplanted acreage.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#unusedResourcesField"><b>Available resources</b></a>
                                        <div id="unusedResourcesField" class="collapse">
                                            Resources are allocated to the most profitable crop(s) and to crops with minimum crop acreage limits in combinations that maximize profit.
                                            Minimum crop acreage limits may consume resources needed to plant all land.<br/>
                                            Are there sufficient resources to plant all of the land? <br/>
                                            <a href="javascript:;" onclick="navigateToResources();return false;">Check resource availabilities.</a><br/>
                                            Are any resources critical, i.e. all used up or almost all used up?<br/>
                                            If yes, increase the amount of critical resource(s) (assuming this is an option).<br/>
                                            Re-analyze the farm model. Additional acreage should be brought into production. If not check one of the other common causes.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#unusedCropsField"><b>Profitability of crops</b></a>
                                        <div id="unusedCropsField" class="collapse">
                                            Only profitable crops will selected for planting unless forced to do so with minimum crop acreage constraints since planting land to unprofitable crops decreases estimated income.<br/>
                                            Are all crops profitable?<br/>
                                            Check profitability of crops and note any unprofitable crops<br/>
                                            <a href="javascript:;" onclick="navigateToCropFieldChoices();return false;">Check crop details.</a><br/>
                                        </div>
                                    </li>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <h2>
                                    <b>IF PLANNING BY ACRES:</b><br/>
                                    <p>Why was all of my available acreage not planted?</p>
                                </h2>
                                <p>Click one or more of the following parameters to fix the problem.</p>
                                <ul class="planning">
                                    <li>
                                        <a data-toggle="collapse" href="#unusedAcreageAcre"><b>Why did Planting Profits not plant all of my available acreage?</b></a>
                                        <div id="unusedAcreageAcre" class="collapse">
                                            Planting Profits focuses on maximizing profit, not maximizing the amount of acres planted.
                                            Although most of the time these objectives are the same, this is not  always the case.
                                            Several factors may cause misalignment between maximizing profit and maximizing acres farmed.<br/>
                                            In these cases, Planting Profits to not plant all of the available land since doing so would either reduce estimated income
                                            or create a strategy that does not  enforce all of the constraints entered in the farm model.
                                            Model parameters can easily be changed to bring any unplanted land into production.
                                            Of course  changes must be realistic. Remember, when a strategy does not use all available acres Planting Profits
                                            is saying that there is either a real conflict between  profitability and planting all available land, or there is a constraint,
                                            i.e. a management decision, that needs to be changed in order to bring the remaining acreage into production. When planning by acres, there are three common causes for a strategy that has unplanted acreage.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#unusedResourcesAcre"><b>Available resources</b></a>
                                        <div id="unusedResourcesAcre" class="collapse">
                                            Resources are allocated to the most profitable crop(s) and to crops with minimum crop acreage limits in combinations that maximize profit.  Minimum crop acreage limits may consume resources needed to plant all land.<br/>
                                            Are there sufficient resources to plant all of the land?<br/>
                                            <a href="javascript:;" onclick="navigateToResources();return false;">Check resource availabilities.</a><br/>
                                            Are any resources critical, i.e. all used up or almost all used up?<br/>
                                            If yes, increase the amount of critical resource(s) (assuming this is an option).<br/>
                                            Re-analyze the farm model. Additional acreage should be brought into production. If not check one of the other common causes.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#unusedCropsAcre"><b>Profitability of crops</b></a>
                                        <div id="unusedCropsAcre" class="collapse">
                                            Profitability of crops combined with minimum and maximum crop acreage limits can result in unplanted land.
                                            First off, unprofitable crops will only be included in  a strategy by using minimum crop acreage limits.
                                            Period. Now if there are maximum crop acreage limits on all profitable crops that are less than the available
                                            land and these limits are reached then there will be land that has not been assigned a crop.
                                            Now, because the maximum crop acreage limits have been reached, only unprofitable crops can be planted to the remaining acreage,
                                            but unprofitable crops will only be included in a strategy using minimum crop acreage limits.<br/>
                                            The result can be land left unplanted.
                                        </div>
                                    </li>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <h1 class="text-center">Trouble Shooting Tips Strategy not Generated.</h1>
                        <br>
                        <c:choose>
                            <c:when test="${farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                                <h2><b>IF PLANNING BY FIELDS:</b></h2>
                                <p>Please check the following and make adjustments to resources, crop limits or crop/field choices.</p>
                                <br/>
                                <p>Click one or more of the following parameters to diagnose the problem.</p>
                                <ul class="planning">
                                    <li>
                                        <a data-toggle="collapse" href="#resources"><b>Resources</b></a>
                                        <div id="resources" class="collapse">
                                            Are there sufficient resources to meet minimum crop acreage limits?<br/>
                                            <a href="javascript:;" onclick="navigateToResources();return false;">Check resource availabilities</a>.<br/>
                                            Are any resources critical, i.e. all used up or almost all used up?<br/>
                                            If yes, increase the amount of critical resource(s) or decrease the minimum crop acreage limits (assuming this is an option).
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#profitabilityOfCrops"><b>Profitability of crops</b></a>
                                        <div id="profitabilityOfCrops" class="collapse">
                                            Are all crops profitable?<br/>
                                            In order to maximize estimated income, Planting Profits will not allocate land or other resources to unprofitable crops unless minimum crop acreage limits are specified.<br/>
                                            <a href="javascript:;" onclick="navigateToProfitableCrops();return false;">Check profitability of crops</a>
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#cropChoices"><b>Crop/field choices</b></a>
                                        <div id="cropChoices" class="collapse">
                                            Minimum crop acreage limits, combined with limitations on which crops can be grown in which fields may make some combinations of crops/fields infeasible.
                                            If there are one or more minimum crop acreage limits specified, is there enough acreage across the fields where the crop can be grown to meet the minimum crop acreage limits?<br/>
                                            This applies to each crop that has a minimum acreage limit. Some of which may be competing for the same field.<br/>

                                            <a href="javascript:;" onclick="navigateToCropFieldChoices();return false;">Check crop/field choices</a><br/>

                                            Often, due to the large number of possible combinations this can be difficult to unravel.<br/>
                                            For crops with minimum acreage limits, either:
                                            <ul class="planning">
                                                <li>1) decrease the crop acreage limit</li>
                                                <li>2) increase the number of fields where the crop (s) with the crop acreage limit(s) can be grown</li>
                                                <li>3) use a combination of 1 and 2.</li>
                                            </ul>
                                        </div>
                                        <br/>
                                    </li>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <h2>IF PLANNING BY ACRES:</h2>
                                <p>Please check the following and make adjustments to resources and/or crop acreage limits.</p>
                                <br/>
                                <p>Click one or more of the following parameters to diagnose the problem.</p>
                                <ul class="planning">
                                    <li>
                                        <a data-toggle="collapse" href="#resourcesAcres"><b>Resources</b></a>
                                        <div id="resourcesAcres" class="collapse">
                                            Are there sufficient resources to meet minimum crop acreage limits? <br/>
                                            <a href="javascript:;" onclick="navigateToResources();return false;">Check resource availabilities</a>.<br/>
                                            Are any resources critical, i.e. all used up or almost all used up?<br/>
                                            If yes, increase the amount of critical resource(s) or decrease the minimum crop acreage limits (assuming this is an option).
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#profitabilityOfCropsAcres"><b>Profitability of crops</b></a>
                                        <div id="profitabilityOfCropsAcres" class="collapse">
                                            Are all crops profitable?<br/>
                                            In order to maximize estimated income, Planting Profits will not allocate land or other resources to unprofitable crops unless minimum crop acreage limits are specified.<br/>
                                            <a href="javascript:;" onclick="navigateToProfitableCrops();return false;">Check profitability of crops</a>
                                        </div>
                                    </li>
                                </ul>
                            </c:otherwise>

                        </c:choose>
                    </c:otherwise>
                </c:choose>

            </div><!-- jumbotron -->
        </div><!-- /.col-12 -->
    </div><!-- /.row -->
</div>
<!-- /.container -->

<script>
    function navigateToResources() {
        localStorage.setItem('resourcesFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }
    function navigateToProfitableCrops() {
        localStorage.setItem('profitCropFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }
    function navigateToCropChoices() {
        localStorage.setItem('cropChoicesFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }
    function navigateToCropFieldChoices() {
        localStorage.setItem('cropFieldChoicesFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }
</script>