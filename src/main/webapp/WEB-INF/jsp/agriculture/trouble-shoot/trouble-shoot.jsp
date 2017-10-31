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
                        <h1 class="text-left troubleshoot-heading">Trouble Shooting Tips</h1>

                        <br>
                        <c:choose>
                            <c:when test="${farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                                <h2><b>All available acreage not planted - Planning by Fields</b></h2>
                                <h2>
                                    <p>
                                        Why did Planting Profits not plant all of my available acreage? - Planning by Fields.
                                        &nbsp;<a data-toggle="collapse" href="#moreUnusedAcreageContentField" onclick="changeMoreLess(this); return false;">More</a>
                                    </p>
                                </h2>
                                <ul class="planning">
                                    <div id="moreUnusedAcreageContentField" class="collapse">
                                        Planting Profits focuses on maximizing profit, not maximizing the amount of acres planted.
                                        Although most of the time these objectives are the same, this is not always the case.
                                        Several factors may cause misalignment between maximizing profit and maximizing acres farmed.
                                        In these cases, Planting Profits will not plant all of the available land since doing so would either reduce estimated income or create a strategy that does not enforce all
                                            of the constraints in the farm model.
                                        Model parameters can easily be changed to bring any unplanted land into production. Of course changes must be realistic.
                                        <%--Remember, when a strategy does not use all available acres Planting Profits is saying that there is either a real conflict between profitability--%>
                                            <%--and planting all available land, or there is a constraint, i.e. a management decision, that needs to be changed in order to bring the remaining acreage into production.--%>
                                        When planning by fields, there are four common causes for a strategy that has unplanted acreage.
                                    </div>
                                </ul>
                                <p>Click one or more of the following parameters to fix the problem.</p>
                                <ul class="planning">
                                    <li>
                                        <a data-toggle="collapse" href="#unusedResourcesField"><b>Available resources</b></a>
                                        <div id="unusedResourcesField" class="collapse">
                                            There may not be enough of a resource to plant all of the acreage.  Often this is due to not providing enough working capital.  However, limitations of labor, equipment, water or some other constraint may prevent all land from being planted given the amount of the resource made available.
                                            <ul class="planning">
                                                <li>1) Are any resources all used up or almost used up? Check resources  <a href="javascript:;" onclick="navigateToResources();return false;">Check resources</a>.</li>
                                                <li>2) Increase the amount of the critical resource and try again to generate a strategy.</li>
                                            </ul>
                                            <%--<a href="javascript:;" onclick="navigateToOutputResources();return false;">Check resource availabilities.</a><br/>--%>
                                            Repeat these steps until the additional acreage is planted. After generating a strategy you can change critical resources and/or minimum crop acreage limits to examine the inter-play between resources and acreage planted.<br/>
                                            If increasing resources does not cause all acreage planted then try one of the other Troubleshooting paths.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#unusedCropsField"><b>Profitability of crops</b></a>
                                        <div id="unusedCropsField" class="collapse">
                                            Only profitable crops will selected for planting unless forced to do so with minimum crop acreage constraints since planting land to unprofitable crops decreases estimated income.<br/>
                                            Are all crops profitable?<br/>
                                            Check profitability of crops and note any unprofitable crops<br/><a href="javascript:;" onclick="navigateToProfitableCrops();return false;">Check crop details.</a><br/>
                                            Does one or more fields have only unprofitable crops that can be planted in it? <a href="javascript:;" onclick="navigateToCropFieldChoices();return false;">Check crop choices</a>
                                            &nbsp;and there are no minimum crop acreage limits forcing unprofitable crops to be planted? <a href="javascript:;" onclick="navigateToCropLimits();return false;">Check crop limits</a><br/>
                                            Make changes to model parameters that represent management decisions.<br/>
                                            Re-analyze the farm model. Additional acreage should be brought into production. If not check one of the other common causes.<br/>
                                        </div>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#unusedFieldsFallowField"><b>Fields designated fallow</b></a>
                                        <div id="unusedFieldsFallowField" class="collapse">
                                            If one or more fields are marked <span style="color: red">fallow</span>, these fields are not part of the available acreage and will not be planted. However, they will show up in the on-screen output and on the report as <span style="color: red">fallow</span>.<br/>
                                            Are any fields designated as fallow?
                                            <a href="javascript:;" onclick="navigateToCropFieldInformation();return false;">Check field information</a><br/>
                                            To bring any fallow fields into production, uncheck the box.Then go to the Crop/Field Choices page and indicate which crops you would like to consider planting in that field. <br/>
                                            Re-analyze the farm model. Additional acreage should be brought into production. If not check one of the other Troubleshooting paths.<br/>
                                        </div>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#unusedMaxAcreageLimitsField"><b>Maximum acreage limits</b></a>
                                        <div id="unusedMaxAcreageLimitsField" class="collapse">
                                            Maximum crop acreage limits limit the acreage that can be planted to one or more crops or groups of crops.  If there are maximum crop acreage limits are, in total, less than available land, when these limits are reached there will be land that is not assigned a crop. <br/>
                                            Are there maximum crop acreage limits? Is the total of all of the maximum crop acreage limits less than the available acreage? <a href="javascript:;" onclick="navigateToCropLimits();return false;">Check crop limits.</a><br/>
                                            If yes, this is likely causing some of the land not to be planted.<br/>
                                            Increase the maximum crop acreage limits on one or more crops.<br/>
                                            Note:  There must be enough resources to support the additional acreage. Also, there must be enough acreage across the fields where you can plant the crop(s) for which you plan to increase the maximum acreage limit. Otherwise you may bump up against one of your rotation constraints. <a href="javascript:;" onclick="navigateToCropFieldChoices();return false;">Check crop/field choices</a><br/>
                                            Re-analyze the farm model.<br/>
                                            If this was the issue additional acreage should be brought into production.<br/>
                                            In combination with the above situation for reaching maximum crop acreage limits, if you have minimum crop acreage limits (for unprofitable crops) that have been reached this can cause some land to go unplanted. <a href="javascript:;" onclick="navigateToCropLimits();return false;">Check crop limits.</a><br/>
                                            Increasing the these minimum crop acreage limits will likely cause your remaining land to be planted to the unprofitable crops. Of course, this will decrease the strategy’s estimated income.<br/>
                                            Note:  There must be enough resources to support the additional acreage. Also, there must be enough acreage across the fields where you can plant the crop(s) for which you plan to increase the minimum acreage limit. Otherwise you may bump up against one of your rotation constraints. <a href="javascript:;" onclick="navigateToCropFieldChoices();return false;">Check crop/field choices</a><br/>
                                            Re-analyze the farm model.<br/>
                                            If this was the issue additional acreage should be brought into production.<br/>
                                            If changing crop acreage limits for profitable and unprofitable crops does not cause all acreage planted then try one of the other Troubleshooting paths.

                                        </div>
                                    </li>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <h2><b>All available acreage not planted - Planning by Acreas</b></h2>

                                <h2>
                                    <p>
                                        Why did Planting Profits not plant all of my available acreage? - Planning by Acreage.
                                        &nbsp;<a data-toggle="collapse" href="#moreUnusedAcreageContent" onclick="changeMoreLess(this); return false;">More</a>
                                    </p>
                                </h2>
                                <ul class="planning">
                                    <div id="moreUnusedAcreageContent" class="collapse">
                                        Planting Profits focuses on maximizing profit, not maximizing the amount of acres planted.
                                        Although most of the time these objectives are the same, this is not always the case.
                                        Several factors may cause misalignment between maximizing profit and maximizing acres farmed.
                                        In these cases, Planting Profits will not plant all of the available land since doing so would either reduce estimated income
                                        or create a strategy that does not enforce all of the constraints entered in the farm model.
                                        Model parameters can easily be changed to bring any unplanted land into production. Of course changes must be realistic.
                                        <%--Remember, when a strategy does not use all available acres Planting Profits is saying that there is either a real conflict--%>
                                        <%--between profitability and planting all available land, or there is a constraint, i.e. a management decision, that needs to be changed in order to bring the remaining acreage into production.--%>
                                        When planning by acres, there are three common causes for a strategy that has unplanted acreage.
                                    </div>
                                </ul>
                                <p>Click one or more of the following parameters to fix the problem.</p>
                                <ul class="planning">
                                    <li>
                                        <a data-toggle="collapse" href="#unusedResourcesAcre"><b>Available resources</b></a>
                                        <div id="unusedResourcesAcre" class="collapse">
                                            There may not be enough of a resource to plant all of the acreage.  Often this is due to not providing enough working capital.  However, limitations of labor, equipment, water or some other constraint may prevent all land from being planted given the amount of the resource made available.<br/>
                                            <ul class="planning">
                                                <li>1) Are any resources all used up or almost used up? Check resources </li>
                                                <li>2) Increase the amount of the critical resource and try again to generate a strategy.</li>
                                            </ul>
                                            Repeat these steps until the additional acreage is planted. After generating a strategy you can change critical resources and/or minimum crop acreage limits to examine the inter-play between resources and acreage planted.<br/>
                                            <%--<a href="javascript:;" onclick="navigateToOutputResources();return false;">Check resource availabilities.</a><br/>--%>
                                            If increasing resources does not cause all acreage planted then try one of the other Troubleshooting paths.

                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#maxAcreageLimitAcre"><b>Maximum acreage limits</b></a>
                                        <div id="maxAcreageLimitAcre" class="collapse">
                                            Maximum crop acreage limits limit the acreage that can be planted to one or more crops or groups of crops.  If there are maximum crop acreage limits are, in total, less than available land, when these limits are reached there will be land that is not assigned a crop. <br/>
                                            Are there maximum crop acreage limits? Is the total of all of the maximum crop acreage limits less than the available acreage? <a href="javascript:;" onclick="navigateToCropLimits();return false;">Check crop limits.</a><br/>
                                            If yes, this is likely causing some of the land not to be planted.<br/>
                                            Increase the maximum crop acreage limits on one or more crops.<br/>
                                            Note:  There must be enough resources to support the additional acreage. Also, there must be enough acreage across the fields where you can plant the crop(s) for which you plan to increase the maximum acreage limit. Otherwise you may bump up against one of your rotation constraints. <a href="javascript:;" onclick="navigateToCropFieldChoices();return false;">Check crop/field choices</a><br/>
                                            Re-analyze the farm model.<br/>
                                            If this was the issue additional acreage should be brought into production.<br/>
                                            In combination with the above situation for reaching maximum crop acreage limits, if you have minimum crop acreage limits (for unprofitable crops) that have been reached this can cause some land to go unplanted. <a href="javascript:;" onclick="navigateToCropLimits();return false;">Check crop limits.</a><br/>
                                            Increasing the these minimum crop acreage limits will likely cause your remaining land to be planted to the unprofitable crops. Of course, this will decrease the strategy’s estimated income.<br/>
                                            Note:  There must be enough resources to support the additional acreage. Also, there must be enough acreage across the fields where you can plant the crop(s) for which you plan to increase the minimum acreage limit. Otherwise you may bump up against one of your rotation constraints. <a href="javascript:;" onclick="navigateToCropFieldChoices();return false;">Check crop/field choices</a><br/>
                                            Re-analyze the farm model.<br/>
                                            If this was the issue additional acreage should be brought into production.<br/>
                                            If changing crop acreage limits for profitable and unprofitable crops does not cause all acreage planted then try one of the other Troubleshooting paths.

                                        </div>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#unusedCropsAcre"><b>Profitability of crops</b></a>
                                        <div id="unusedCropsAcre" class="collapse">
                                            Profitability of crops combined with minimum and maximum crop acreage limits can result in some land not being planted.<br/>
                                            Unprofitable crops will only be included in a strategy by using minimum crop acreage limits. Period.  <br/>                                           
                                            If there are maximum crop acreage limits on profitable crops these are less than available land, when these limits are reached there will be land that is not assigned a crop.  If only unprofitable crops are left (after the maximum limits are reached on the profitable crops) and there is no minimum crop acreage limit then the land will not be assigned to a crop.<br/>
                                            Check the profitability of your crops and note any unprofitable crops. <a href="javascript:;" onclick="navigateToProfitableCrops();return false;">Check crop information</a><br/>
                                            Are there maximum crop acreage limits? Is the total of all of the maximum crop acreage limits on the profitable crops less than the available acreage?<a href="javascript:;" onclick="navigateToCropLimits();return false;">Check crop limits.</a><br/>
                                            If yes, this is likely causing some of the land not to be planted.<br/>
                                            Increase the maximum crop acreage limits on one or more crops.<br/>
                                            Note:  There must be enough resources to support the additional acreage. <br/>
                                            <%--Check profitability of crops and note any unprofitable crops<br/><a href="javascript:;" onclick="navigateToCropLimits();return false;">Check crop limits.</a><br/>--%>
                                            <%--Are there maximum crop acreage limits on the profitable crops that, in total, are less than available acreage and if there are unprofitable crops?<br/><a href="javascript:;" onclick="navigateToCropLimits();return false;">Check crop limits.</a><br/>--%>
                                            Re-analyze the farm model. <br/>
                                            If this was the issue additional acreage should be brought into production. <br/>
                                            In addition to or in combination with the above situation, if you have minimum crop acreage limits (for unprofitable crops) that have been reached this can cause some land to go unplanted. <a href="javascript:;" onclick="navigateToCropLimits();return false;">Check crop limits.</a><br/>
                                            Increasing the these minimum crop acreage limits will likely cause your remaining land to be planted to the unprofitable crops. Of course, this will decrease the strategy’s estimated income.<br/>
                                            Note:  There must be enough resources to support the additional acreage.<br/>
                                            Re-analyze the farm model.<br/>
                                            If this was the issue additional acreage should be brought into production.<br/>
                                            If changing crop limits for profitable and unprofitable crops does not cause all acreage planted then try one of the other Troubleshooting paths.
                                        </div>
                                    </li>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <h1 class="text-left troubleshoot-heading">Trouble Shooting Tips <a id="back-to-sensitivity-btn" class="pull-right" onclick="navigateToCropLimits(); return false;" href="javascript:;" style="display: block; font-size: 30%">Back to Planting Profits</a>
                        </h1>
                        <br>
                        <c:choose>
                            <c:when test="${farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                                <h2><b>Strategy not generated - Planning by Fields</b></h2>
                                <p>Please check the following and make adjustments to resources, crops, crop limits or crop/field choices.</p>
                                <br/>
                                <p>Click one or more of the following parameters to diagnose the problem.</p>
                                <ul class="planning">
                                    <li>
                                        <a data-toggle="collapse" href="#resources"><b>Available Resources</b></a>
                                        <div id="resources" class="collapse">
                                            There may not be enough of a resource to meet the minimum crop acreage limits.  <br/>
                                            <%--<a href="javascript:;" onclick="navigateToResources();return false;">Check resource availabilities</a>.<br/>--%>
                                            Sometimes resources are limitations on income and sometimes they’re not. In some cases resources easily be changed such as the amount of working capital available.  Some resources such as on-farm storage can’t be easily changed.<br/>
                                            If you have minimum crop acreage limits try one or  both of the following:
                                            <ul class="planning">
                                                <li>1) Increase the amount of any resource (except land) needed for the crops that have minimum crop acreage limits.  <a href="javascript:;" onclick="navigateToResources();return false;">Check resources</a>.</li>
                                                <li>2) Decrease one or more minimum crop acreage limits. <a href="javascript:;" onclick="navigateToCropLimits();return false;">Check crop acreage limits</a></li>
                                                <li>3) Try again to generate a strategy.</li>
                                            </ul>
                                            After generating a strategy you can change critical resources and/or minimum crop acreage limits to examine the inter-play between resources and minimum crop acreage limits.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#profitabilityOfCrops"><b>Profitability of crops</b></a>
                                        <div id="profitabilityOfCrops" class="collapse">
                                            Planting Profits will not allocate land to an unprofitable crop unless you use a minimum crop acreage limit.<br/>
                                            So, if all of your crops are unprofitable and you do use minimum crop acreage limits, Planting Profits will not generate a strategy.<br/>
                                            <a href="javascript:;" onclick="navigateToProfitableCrops();return false;">Check profitability of crops</a>
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#cropChoices"><b>Crop/field choices</b></a>
                                        <div id="cropChoices" class="collapse">

                                            Minimum crop acreage limits, combined with limitations on which crops can be grown in which fields may make some combinations of crops/fields/acreage, that you specified, infeasible.
                                            If there are one or more minimum crop acreage limits, there must be enough acreage across the fields in which the crop can be grown to meet the minimum crop acreage limit. This applies to each crop that has a minimum acreage limit. So multiple crops with minimum crop acreage limits may be competing for the same field. Often, due to the large number of possible combinations of crops/fields and minimum acreage limits this can be difficult to unravel.
                                            <%--<a href="javascript:;" onclick="navigateToCropFieldChoices();return false;">Check crop/field choices</a><br/>--%>
                                            For crops with minimum acreage limits, either:
                                            <ul class="planning">
                                                <li>1) Decrease the minimum crop acreage limit(s) that you suspect are causing the conflict. <a href="javascript:;" onclick="navigateToCropFieldChoices();return false;">Check crop acreage limit</a></li>
                                                <li>2) Increase the number of fields where the crop(s) with the minimum crop acreage limit(s) can be grown. <a href="javascript:;" onclick="navigateToCropFieldChoices();return false;">Check crop/field choices</a></li>
                                                <li>3) use a combination of 1 and 2.</li>
                                            </ul>
                                        </div>
                                        <br/>
                                    </li>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <h2>Strategy not generated - Planning by Acres</h2>
                                <p>Please check the following and make adjustments to resources, crops, crop limits or crop/field choices.</p>
                                <br/>
                                <p>Click one or more of the following parameters to diagnose the problem.</p>
                                <ul class="planning">
                                    <li>
                                        <a data-toggle="collapse" href="#resourcesAcres"><b>Available Resources</b></a>
                                        <div id="resourcesAcres" class="collapse">
                                            There may not be enough of a resource to meet the minimum crop acreage limits.   <br/>
                                            <%--<a href="javascript:;" onclick="navigateToResources();return false;">Check resource availabilities</a>.<br/>--%>
                                            Sometimes resources are limitations on income and sometimes they’re not. In some cases resources can be increased such as the amount of working capital available.  Some resources such as on-farm storage cannot be easily changed.<br/>
                                            If you have minimum crop acreage limits try one or  both of the following:
                                            <ul class="planning">
                                                <li>1) Increase the amount of any resource (except land) needed for the crops that have minimum crop acreage limits. <a href="javascript:;" onclick="navigateToResources();return false;">Check resources</a></li>
                                                <li>2) Decrease one or more minimum crop acreage limits.<a href="javascript:;" onclick="navigateToCropLimits();return false;">Check crop acreage limits</a></li>
                                                <li>3) Try again to generate a strategy.</li>
                                            </ul>
                                            After generating a strategy you can change critical resources and/or minimum crop acreage limits to examine the inter-play between resources and minimum crop acreage limits.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#profitabilityOfCropsAcres"><b>Profitability of crops</b></a>
                                        <div id="profitabilityOfCropsAcres" class="collapse">
                                            Planting Profits will not allocate land to an unprofitable crop unless you use a minimum crop acreage limit.<br/>
                                            So, if all of your crops are unprofitable and you do use minimum crop acreage limits, Planting Profits will not generate a strategy.<br/>
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
    $(function(){
        var sensitivityFlag = localStorage.getItem('sensitivityFlag');
        if(sensitivityFlag){
            $('#back-to-sensitivity-btn').show();
            localStorage.removeItem('sensitivityFlag');
        }
    });

    function navigateToResources() {
        localStorage.setItem('resourcesFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }

    function navigateToOutputResources() {
        localStorage.setItem('resourcesFlag', true);
        window.open('<c:url value="/output-farm-info.htm?farmId="/>${farmId}');
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

    function navigateToSensitivityAnalysis(){
        localStorage.setItem('sensitivityFlag', true);
        window.location = '<c:url value="/output-farm-info.htm?farmId="/>${farmId}';
    }

    function navigateToCropLimits() {
        localStorage.setItem('cropLimitFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }

    function navigateToCropFieldInformation() {
        localStorage.setItem('cropFieldFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }

    function changeMoreLess(obj) {
        if($(obj).text() == 'More'){
            $(obj).text('Less');
        } else {
            $(obj).text('More');
        }
    }
</script>