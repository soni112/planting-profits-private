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
                        <h1 class="text-left troubleshoot-heading">Troubleshooting Tips
                            <a class="pull-right"
                               onclick="navigateToOutputResources(); return false;"
                               href="javascript:;" style="font-size: 30%">Back to Planting Profits</a>
                        </h1>
                        <br>
                        <c:choose>
                            <c:when test="${farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                                <h2><b>All available acreage not assigned crops – Planning by Fields</b></h2>
                                <h2>
                                    <p>
                                        Why were all fields not assigned crops?
                                        &nbsp;<a data-toggle="collapse" href="#moreUnusedAcreageContentField"
                                                 onclick="changeMoreLess(this); return false;">More</a>
                                    </p>
                                </h2>
                                <ul class="planning">
                                    <div id="moreUnusedAcreageContentField" class="collapse">
                                        Planting Profits focuses on maximizing estimated income not maximizing acres
                                        planted. Although most of the time these objectives are the same, this is not
                                        always the
                                        case.
                                        Several factors may cause misalignment between maximizing estimated income and
                                        maximizing
                                        acres planted.
                                        In these cases, Planting Profits will not assign crops to all of the available
                                        land since
                                        doing so would either reduce estimated income or create a strategy that does not
                                        enforce all
                                        of your constraints.
                                            <%--Model parameters can easily be changed to bring any unplanted land into--%>
                                            <%--production. Of course changes must be realistic.--%>
                                            <%--Remember, when a strategy does not use all available acres Planting Profits is saying that there is either a real conflict between profitability--%>
                                            <%--and planting all available land, or there is a constraint, i.e. a management decision, that needs to be changed in order to bring the remaining acreage into production.--%>
                                        When planning by fields, there are four common causes for a strategy that has
                                        unassigned acreage.
                                    </div>
                                </ul>
                                <p>Review the Troubleshooting Tips below and make adjustments to resources, crop/field
                                    choices, crop acreage limits or crops so that all fields are assigned crops.</p>
                                <ul class="planning">
                                    <li>
                                        <a data-toggle="collapse" href="#unusedResourcesField"><b>Available
                                            resources</b></a>
                                        <div id="unusedResourcesField" class="collapse">
                                            There may not be enough of a particular resource to assign crops to all
                                            fields. Often this is due to insufficient Working Capital. However if you’re
                                            using optional resources,
                                            limitations on labor, equipment, water or some other constraint,
                                            may prevent crops from being assigned to all fields. If it looks like a
                                            resource
                                            issue, try the following:
                                            <ul class="planning">
                                                <p>1) Are any resources all used up or almost used up?
                                                    <a href="javascript:;"
                                                       onclick="navigateToResources();return false;">Check resources</a>.
                                                </p>
                                                <p>2) If yes, increase the amount of the critical resource. You can
                                                    change it by going to the <a href="javascript:;"
                                                                                 onclick="navigateToResources();return false;">Resources
                                                        page</a> or use the
                                                    <a href="javascript:;"
                                                       onclick="navigateToOutputResources();return false;"> resources
                                                        sensitivity analysis tool.</a>
                                                </p>
                                                <p>3) Re-analyze the farm model.</p>
                                            </ul>
                                                <%--<a href="javascript:;" onclick="navigateToOutputResources();return false;">Check resource availabilities.</a><br/>--%>
                                            If all fields are not assigned crops, check one of the other Troubleshooting
                                            paths.
                                                <%--Repeat these steps until the additional acreage is planted. After--%>
                                                <%--generating a strategy you can change critical resources and/or minimum crop--%>
                                                <%--acreage limits to examine the inter-play between resources, estimated income--%>
                                                <%--and acreage planted<br/>--%>
                                                <%--If all available acreage is not brought into production check one of the--%>
                                                <%--other Troubleshooting paths.<br/>--%>
                                        </div>
                                        <br/>
                                    </li>

                                    <li>
                                        <a data-toggle="collapse" href="#unusedFieldsFallowField"><b>Fields designated
                                            fallow</b></a>
                                        <div id="unusedFieldsFallowField" class="collapse">
                                            If one or more fields are marked Fallow, these fields are not part of the
                                            available acreage and will not be assigned crops. These fields will be
                                            assigned a
                                            crop called <span style="color: black">fallow</span>.<br/>
                                            <ul class="planning">
                                                <p>1) Are any fields designated as fallow?
                                                    <a href="javascript:;"
                                                       onclick="navigateToCropFieldInformation();return false;">Check
                                                        field information</a>
                                                </p>
                                                <p>2) To bring fields marked Fallow into production, uncheck the Fallow
                                                    box. Then go to the Crop/Field Choices page and indicate which crops
                                                    you would consider planting in that field.
                                                </p>
                                                <p>3) Repeat for all fields marked Fallow.</p>
                                                <p>4) Re-analyze the farm model.</p>
                                            </ul>
                                                <%--If all available acreage is not brought into production check one of the--%>
                                                <%--other Troubleshooting paths.--%>
                                            If all fields are not assigned crops check one of the other Troubleshooting
                                            paths.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#unusedMaxAcreageLimitsField"><b>Maximum acreage
                                            limits</b></a>
                                        <div id="unusedMaxAcreageLimitsField" class="collapse">
                                            Maximum crop acreage limits limit the acreage that can be planted to one or
                                            more crops or groups of crops. If there are maximum crop acreage limits that
                                            are, in total, less than available land, when these limits are reached
                                            there may be land that is not assigned a crop. If it looks like maximum
                                            crop acreage limits may be the issue, try the following:<br/>
                                            <ul class="planning">
                                                <p>1) Are there maximum crop acreage limits? If so, have most of the
                                                    maximum crop acreage limits been reached? <a
                                                            href="javascript:;"
                                                            onclick="navigateToCropLimits();return false;">Check
                                                        crop limits.</a></p>
                                                <p>2) If yes, this is likely causing some of the land not to be
                                                    planted.
                                                </p>
                                                <p>3) Increase the maximum crop acreage limits on one or more
                                                    crops.<br/>
                                                    There must be enough resources to support the additional acreage.
                                                    Also, there must be enough acreage among the fields where you can
                                                    plant the crop(s) for which you plan to increase the maximum acreage
                                                    limit. Otherwise you may bump up against one of your crop/field
                                                    rotation
                                                    constraints. <a
                                                            href="javascript:;"
                                                            onclick="navigateToCropFieldChoices();return false;">Check
                                                        crop/field
                                                        choices</a>
                                                </p>
                                                <p>4) Re-analyze the farm model.</p>
                                            </ul>
                                            In combination with the above situation for maximum crop acreage limits, if
                                            you have minimum crop acreage limits (for unprofitable crops) that have been
                                            reached this can cause some fields to not be assigned crops.
                                            <a href="javascript:;"
                                               onclick="navigateToCropLimits();return false;">Check
                                                crop limits.</a><br/>
                                            Increasing these minimum crop acreage limits will likely cause remaining
                                            land to be assigned unprofitable crops. This will
                                            decrease the strategy’s estimated income.<br/>
                                            There must be enough resources to support the additional acreage. Also,
                                            there must be enough acreage among the fields where you can plant the
                                            crop(s) for which you plan to increase the minimum acreage limit. Otherwise
                                            you may bump up against one of your crop/field rotation constraints. <a
                                                href="javascript:;"
                                                onclick="navigateToCropFieldChoices();return false;">Check
                                            crop/field
                                            choices</a><br/>
                                            If all available acreage is not brought into production check one of the
                                            other Troubleshooting paths.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#unusedCropsField"><b>Profitability of crops</b></a>
                                        <div id="unusedCropsField" class="collapse">
                                            Planting Profits will not allocate land to unprofitable crops unless you
                                            use minimum crop acreage limits. <br/>
                                            (Note: Unless you have unprofitable crops it is
                                            unlikely that the (un)profitability of crops is preventing Planting Profits
                                            from generating a strategy.) If any of your crops are unprofitable and
                                            you do not enter minimum crop acreage limits, Planting Profits will not
                                            assign any acreage to that crop. This, in combination with maximum crop
                                            acreage limits, resource limit will not assign any fields to that crop.
                                            Planting Profits from assigning crops to all of your fields since
                                            doing so will decrease estimated income. If it looks like (un)profitability
                                            of
                                            the crops may be the issue, try the following: <br/>
                                            <ul class="planning">
                                                <p>1) Are any crops unprofitable? <a href="javascript:;"
                                                                                     onclick="navigateToProfitableCrops();return false;">Check
                                                    profitability of crops</a> If not, then this is not the reason
                                                    Planting Profits did not assign crops to all acreage.</p>
                                                <p>2) If any crops are unprofitable, are there minimum crop acreage
                                                    limits on these crops? <a href="javascript:;"
                                                                              onclick="navigateToOutputCropAcreageLimits();return false;">Check
                                                        crop acreage limits</a></p>
                                                <p>3) If yes, enter or increase minimum crop acreage limits for the
                                                    unprofitable crop(s).(Warning: this will likely decrease estimated
                                                    income.)</p>
                                                <p>4) Go to the Crop/Field Choices page to make sure you have enough
                                                    acreage across your fields in which you can plant the unprofitable
                                                    crop(s).</p>
                                                <p>5) Re-analyze the farm model.</p>
                                            </ul>
                                            If all fields are not assigned crops check one of the other Troubleshooting
                                            paths.

                                        </div>
                                        <br/>
                                    </li>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <h2><b>All available acreage not planted - Planning by Acres</b></h2>

                                <h2>
                                    <p>
                                        Why was all available land not assigned crops?
                                        &nbsp;<a data-toggle="collapse" href="#moreUnusedAcreageContent"
                                                 onclick="changeMoreLess(this); return false;">More</a>
                                    </p>
                                </h2>
                                <ul class="planning">
                                    <div id="moreUnusedAcreageContent" class="collapse">
                                        Planting Profits focuses on maximizing estimated income, not maximizing acres
                                        planted.
                                        Although most of the time these objectives are the same, this is not always the
                                        case.
                                        Several factors may cause misalignment between maximizing estimated income and
                                        maximizing acres farmed.
                                        In these cases, Planting Profits will not assign crops to all of the available
                                        land since doing so would either reduce estimated income or create a strategy
                                        that does not enforce all of your constraints.
                                        When planning by acres, there are three common causes for a strategy that has
                                        acreage that is not assigned a crop.
                                    </div>
                                </ul>
                                <%--<p>Review the Troubleshooting paths below and make adjustments to resources, and crop acreage limits or crop so that all acreage is assigned a crop. </p>--%>
                                <%--<p> Please make adjustments to resources, crop/field choices, crop acreage limits or crops to generate a strategy. Click on the parameters below to find and fix the problem.</p>--%>
                                <p>
                                    Review the Troubleshooting tips below and make adjustments to resources, crop acreage limits or crops so that all acreage are assigned crops.
                                </p>

                                <ul class="planning">
                                    <li>
                                        <a data-toggle="collapse" href="#unusedResourcesAcre"><b>Available resources and minimum crop acreage limits</b></a>
                                        <div id="unusedResourcesAcre" class="collapse">
                                            There may not be enough of a particular resource to assign crops to all
                                            acreage.
                                            Often this is due to insufficient Working Capital.
                                            However if you’re using optional resources, limitations of labor, equipment,
                                            water or some other constraint, may prevent acreage from being assigned
                                            crops.
                                            If it looks like a resource issue, try the following: <br/>
                                            <ul class="planning">
                                                <p>1) Are any resources all used up or almost used up? <a
                                                        href="javascript:;"
                                                        onclick="navigateToOutputResources();return false;">Check
                                                    resources.</a>
                                                </p>
                                                <p>2) If yes, increase the amount of the critical resource. You can
                                                    change it by going to the Resources page or use the resources
                                                    sensitivity analysis tool.
                                                        <%--<a href="javascript:;"
                                                          onclick="navigateToResources();return false;">--%>
                                                        <%--<a href="javascript:;"
                                                        onclick="navigateToOutputResources();return false;">--%>
                                                </p>
                                                <p>3) Re-analyze the farm model.</p>
                                            </ul>
                                            After generating a strategy you can change critical resources and/or minimum
                                            crop acreage limits to examine the inter-play between resources, estimated
                                            income and acreage planted.<br/>
                                                <%--<a href="javascript:;" onclick="navigateToOutputResources();return false;">Check resource availabilities.</a><br/>--%>
                                            If all available acreage is not assigned crops, check one of the other
                                            Troubleshooting paths.

                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#maxAcreageLimitAcre"><b>Maximum acreage
                                            limits</b></a>
                                        <div id="maxAcreageLimitAcre" class="collapse">
                                            Maximum crop acreage limits limit the acreage that can be planted to one or
                                            more crops or groups of crops. If there are maximum crop acreage limits that
                                            are, in total, less than available land, when these limits are reached there
                                            may be land that is not assigned a crop. If it looks like maximum
                                            crop acreage limits may be the issue, try the following: <br/>
                                            <ul>
                                                <p>1) Are there maximum crop acreage limits? If so, have most of the
                                                    maximum crop acreage limits been reached? <a href="javascript:;"
                                                                                                 onclick="navigateToCropLimits();return false;">Check
                                                        crop limits.</a></p>
                                                <p>2) If yes, this is likely causing some of the land not to be
                                                    planted.
                                                </p>
                                                <p>3) Increase the maximum crop acreage limits on one or more
                                                    crops.</p>
                                                <p>4) There must be enough resources to support the additional acreage.
                                                    Otherwise you may bump up against one of your resource constraints.
                                                    <a href="javascript:;"
                                                       onclick="navigateToResources();return false;">Check
                                                        resources</a>
                                                </p>
                                                <p>5) Re-analyze the farm model.</p>
                                            </ul>
                                            In combination with the above situation for maximum crop acreage limits, if
                                            you have minimum crop acreage limits (for unprofitable crops) that have been
                                            reached, this can cause remaining land to be assigned unprofitable crops. <a
                                                href="javascript:;" onclick="navigateToCropLimits();return false;">Check
                                            crop limits.</a><br/>
                                            Increasing these minimum crop acreage limits will likely cause
                                            remaining land to be planted but to unprofitable crops. This will
                                            decrease the strategy’s estimated income.<br/>
                                            There must be enough resources to support the additional acreage. Otherwise
                                            you may bump up against one of your crop/field rotation constraints. <a
                                                href="javascript:;"
                                                onclick="navigateToCropFieldChoices();return false;">Check crop/field
                                        </a><br/>
                                            If all available acreage is not brought into production check one of the
                                            other Troubleshooting paths.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#unusedCropsAcre"><b>Profitability of crops</b></a>
                                        <div id="unusedCropsAcre" class="collapse">
                                            Planting Profits will not allocate land to unprofitable crops unless you
                                            use minimum crop acreage limits. <br/>
                                            (Note: Unless you have unprofitable crops it is
                                            unlikely that the (un)profitability of crops is preventing Planting Profits
                                            from generating a strategy.) If any of your crops are unprofitable and
                                            you do not enter minimum crop acreage limits, Planting Profits will not
                                            assign any acreage to that crop. This, in combination with maximum crop
                                            acreage limits or resource limitations, can prevent Planting Profits from
                                            assigning crops to all of your land since doing so will decrease estimated
                                            income. If it looks like (un)profitability of
                                            one or more crops may be the reason all acreage are not assigned crops, try
                                            the following: <br/>
                                            <ul class="planning">
                                                <p>1) Are any crops unprofitable? <a href="javascript:;"
                                                                                     onclick="navigateToProfitableCrops();return false;">Check
                                                    profitability
                                                    of crops</a> If not,
                                                    this is not the reason Planting Profits did not assign crops to all
                                                    acreage.
                                                </p>
                                                <p>2) If any crops are unprofitable, are there minimum crop acreage
                                                    limits on these crops? <a
                                                            href="javascript:;"
                                                            onclick="navigateToOutputCropAcreageLimits();return false;">Check
                                                        crop acreage limits</a> If yes, enter or increase minimum crop
                                                    acreage
                                                    limits
                                                    for the
                                                    unprofitable crop(s).(Warning: this will likely decrease estimated
                                                    income.)
                                                </p>
                                                <p>3) Re-analyze the farm model.</p>
                                            </ul>

                                            If all available acreage is not brought into production check one of the
                                            other Troubleshooting paths.
                                        </div>
                                        <br/>
                                    </li>
                                </ul>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:otherwise>
                        <h1 class="text-left troubleshoot-heading">Troubleshooting Tips
                            <a class="pull-right"
                               onclick="navigateToCropLimits(); return false;"
                               href="javascript:;" style="font-size: 30%">Back to Planting Profits</a>
                        </h1>
                        <br>
                        <c:choose>
                            <c:when test="${farmInfoView.strategy eq 'PLAN_BY_FIELDS'}">
                                <h2><b>Planning by Fields - Strategy not generated</b></h2>
                                <p>Review the Troubleshooting tips below and
                                    make adjustments to resources, crop acreage
                                    limits or crops so that all acres are assigned
                                    crops.</p>
                                <br/>
                                <%--<p>Click one or more of the following parameters to diagnose the problem.</p>--%>
                                <ul class="planning">
                                    <li>
                                        <a data-toggle="collapse" href="#resources"><b>Available resources and minimum crop acreage limits</b></a>
                                        <div id="resources" class="collapse">
                                            There may not be enough of a particular resource to meet the minimum crop
                                            acreage limits you entered. Remember that <b>firm</b> forward sales contracts
                                            create minimum acreage limits. These resources may be flexible, such as working
                                            capital, and can easily be changed. Some resources, such as on-farm storage,
                                            are not easily changed. If you have minimum crop acreage limits, try one or
                                            both of the following to generate a strategy: <br/>
                                                <%--<a href="javascript:;" onclick="navigateToResources();return false;">Check resource availabilities</a>.<br/>--%>
                                            <ul class="planning">
                                                <p>1) Increase the amount of any resource that is needed for crops
                                                    that have minimum crop acreage limits. Often there is not enough
                                                    working capital to support the minimum crop limits. Do you have
                                                    enough working capital? Are there other resources that may be
                                                    insufficient to support minimum crop acreage limits? Can any of
                                                    these suspects be increased? <a href="javascript:;"
                                                                                    onclick="navigateToResources();return false;">Check
                                                        resources</a>.
                                                </p>
                                                <p>2) If you cannot increase the limiting resource, decrease one or
                                                    more minimum crop acreage limits. You may also need to decrease
                                                    the amount of one or more crops that have a <b>firm</b> forward sales
                                                    contract.
                                                    <a href="javascript:;" onclick="navigateToCropLimits();return false;">Check
                                                        crop acreage limits</a> and/or
                                                    <a href="javascript:;" onclick="navigateToForwardSales();return false;">forward sales</a>.</p>
                                                <p>3) Use a combination of 1 and 2. After generating a strategy you
                                                    can back down the critical resource(s) that you increased in order
                                                    to generate your strategy. You can also change the minimum crop
                                                    acreage limits and explore the inter-play between resources, minimum
                                                    crop acreage limits and estimated income.
                                                </p>
                                                <p>4) Try again to generate a strategy.</p>
                                            </ul>
                                            If a strategy is not generated, check one of the other Troubleshooting
                                            paths.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#cropChoices"><b>Crop/field choices and crop acreage limits </b></a>
                                        <div id="cropChoices" class="collapse">

                                            Minimum crop acreage limits, combined with which crops can be grown in which
                                            fields, may make some combinations of crops/fields/acreage, that you
                                            specified, infeasible,i.e. not possible. That is, Planting Profits cannot generate a strategy
                                            because of conflicts in the model.<br/>
                                            If there are one or more minimum crop acreage limits, there must be enough
                                            acreage across the fields in which the crop can be grown to meet the minimum
                                            crop acreage limit. This applies to each crop that has a minimum acreage
                                            limit including any crops that have a firm forward sales contract.<br/>
                                                <%--<a href="javascript:;" onclick="navigateToCropFieldChoices();return false;">Check crop/field choices</a><br/>--%>
                                            Multiple crops with minimum crop acreage limits may be competing for the
                                            same field. Often, due to the large number of possible combinations of
                                            crops/fields and minimum acreage limits this can be difficult to unravel.
                                            For crops with minimum acreage limits, either:<br/>

                                            <ul class="planning">
                                                <p>1) Decrease the minimum crop acreage limit(s) that you suspect are
                                                    causing the conflict. <a href="javascript:;"
                                                                             onclick="navigateToCropLimits();return false;">Check
                                                        crop acreage limits</a></p>
                                                <p>2) Increase the number of fields where the crop(s) with the minimum
                                                    crop acreage limit(s) and <b>firm</b> forward sales contract can be
                                                    grown. <a href="javascript:;" onclick="navigateToCropFieldChoices();return false;">Check
                                                        crop/field choices</a></p>
                                                <p>3) Use a combination of 1 and 2. After generating a strategy you can
                                                    change the minimum crop acreage limits. You can go back to your
                                                    crop/field choices and if there is a crop assigned to a field that
                                                    you don’t like uncheck it so it cannot be considered for that field
                                                    if that is a preferred rotation. Explore the inter-play between
                                                    minimum crop acreage limits and crop/field choices and estimated
                                                    income.
                                                </p>
                                            </ul>
                                            If a strategy is not generated, check one of the other Troubleshooting
                                            paths.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#profitabilityOfCrops"><b>Profitability of
                                            crops</b></a>
                                        <div id="profitabilityOfCrops" class="collapse">
                                            Planting Profits will not allocate land to an unprofitable crop unless you
                                            use a minimum crop acreage limits.<br/>
                                            So, if all of your crops are unprofitable and you do use minimum crop
                                            acreage limits, Planting Profits will not generate a strategy.<br/>
                                            <a href="javascript:;" onclick="navigateToProfitableCrops();return false;">Check
                                                profitability of crops</a> then <a href="javascript:;"
                                                                                   onclick="navigateToCropLimits();return false;">Check
                                            crop acreage limits</a><br/>
                                            Re-analyze the farm model.<br/>
                                            If a strategy is not generated, check one of the other Troubleshooting
                                            paths.
                                        </div>
                                        <br/>
                                    </li>
                                </ul>
                            </c:when>
                            <c:otherwise>
                                <h2><b>Planning by Acres - Strategy not generated</b></h2>
                                <p>Please make adjustments to resources, crop/field choices, crop acreage limits or
                                    crops to generate a strategy. Remember that <b>firm</b> forward sales contracts
                                    create minimum crop acreage limits. Click on the parameters below to find and fix the
                                    problem.</p>
                                <br/>
                                <%--<p>Click one or more of the following troubleshooting areas to find and fix the--%>
                                <%--problem.</p>--%>
                                <ul class="planning">
                                    <li>
                                        <a data-toggle="collapse" href="#resourcesAcres"><b>Available resources and minimum crop acreage limits</b></a>
                                        <div id="resourcesAcres" class="collapse">
                                            There may not be enough of a particular resource to meet the minimum crop
                                            acreage limits you entered. These resources may be flexible, such as working
                                            capital, and can easily be changed. Some resources, such as on-farm storage,
                                            are not easily changed. If you have minimum crop acreage limits, try one or
                                            both of the following to generate a strategy: <br/>
                                                <%--<a href="javascript:;" onclick="navigateToResources();return false;">Check resource availabilities</a>.<br/>--%>
                                            <ul class="planning">
                                                <p>1) Increase the amount of any resource that is needed for crops that
                                                    have minimum crop acreage limits. You may also need to decrease the
                                                    amount of one or more crops that have a <b>firm</b> forward sales
                                                    contract. Often there is not enough working capital to support the
                                                    minimum crop limits. Do you have enough working capital? Are there
                                                    other resources that may be insufficient to support minimum crop
                                                    acreage limits? Can any of these suspects be increased?
                                                    <a href="javascript:;"
                                                                  onclick="navigateToResources();return false;">Check
                                                        resources</a></p>
                                                <p>2) If you cannot increase the limiting resource, decrease one or
                                                    more minimum crop acreage limits or the amount of acreage that has
                                                    <b>firm</b> forward sales contracts.
                                                    <a href="javascript:;" onclick="navigateToCropLimits();return false;">Check
                                                        crop acreage limits</a> and/or <a href="javascript:;" onclick="navigateToForwardSales();return false;">forward sales</a>.</p>
                                                <p>3) Use a combination of 1 and 2. After generating a strategy you can
                                                    back down the critical resource(s) that you increased in order to
                                                    generate your strategy. You can also change the minimum crop acreage
                                                    limits and explore the inter-play between resources, minimum crop.
                                                </p>
                                                <p>4) Re-analyze the farm model.</p>
                                            </ul>
                                            If a strategy is not generated, check one of the other Troubleshooting
                                            paths.
                                        </div>
                                        <br/>
                                    </li>
                                    <li>
                                        <a data-toggle="collapse" href="#profitabilityOfCropsAcres"><b>Profitability of
                                            crops</b></a>
                                        <div id="profitabilityOfCropsAcres" class="collapse">
                                            Planting Profits will not allocate land to unprofitable crop unless you
                                            use minimum crop acreage limits.<br/>
                                            (Note: Unless you have unprofitable crops as
                                            some of your crop choices it is unlikely that (un)profitability of crops is
                                            preventing Planting Profits from generating a strategy.) But if all of your
                                            crops are unprofitable and you do not have entered minimum crop acreage
                                            limits, Planting Profits will not generate a strategy. <br/>
                                            <ul class="planning">
                                                <p>1) Are all crops unprofitable? <a href="javascript:;"
                                                                                     onclick="navigateToProfitableCrops();return false;">Check
                                                    profitability
                                                    of crops</a> If not, this is not the reason Planting Profits cannot
                                                    generate a strategy.
                                                </p>
                                                <p>2) If all crops are unprofitable, are there minimum crop acreage
                                                    limits on one or more crops? <a href="javascript:;"
                                                                                    onclick="navigateToCropLimits();return false;">Check
                                                        crop acreage limits</a> If not, enter
                                                    minimum crop acreage limits for one or more crops.
                                                </p>
                                                <p>3) Re-analyze the farm model.</p>
                                            </ul>
                                            If a strategy is not generated, check one of the other Troubleshooting
                                            paths.
                                        </div>
                                        <br/>
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
    $(function () {
        var sensitivityFlag = localStorage.getItem('sensitivityFlag');
        if (sensitivityFlag) {
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

    function navigateToSensitivityAnalysis() {
        localStorage.setItem('sensitivityFlag', true);
        window.location = '<c:url value="/output-farm-info.htm?farmId="/>${farmId}';
    }

    function navigateToCropLimits() {
        localStorage.setItem('cropLimitFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }
    function navigateToForwardSales() {
        localStorage.setItem('forwardSalesFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }

    function navigateToCropFieldInformation() {
        localStorage.setItem('cropFieldFlag', true);
        window.open('<c:url value="/view-farm-info.htm?farmId="/>${farmId}');
    }

    function navigateToOutputCropAcreageLimits() {
        localStorage.setItem('cropLimitFlag', true);
        window.open('<c:url value="/output-farm-info.htm?farmId="/>${farmId}');
    }

    function changeMoreLess(obj) {
        if ($(obj).text() == 'More') {
            $(obj).text('Less');
        } else {
            $(obj).text('More');
        }
    }
</script>