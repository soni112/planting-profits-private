<%--
  Created by IntelliJ IDEA.
  User: decipher03
  Date: 6/10/17
  Time: 12:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-lg-12 col-md-12">
            <div class="jumbotron margin-top-2">

                <h1 class="text-center">Troubleshoot Baseline Strategy</h1>
                <br>
                <h2>IF PLANNING BY FIELDS:</h2>
                <p>
                    Please check the following and make adjustments to
                    resources, crop limits or crop/field choices
                </p>
                <br />
                <p>
                    Click one or more of the following parameters to
                    diagnose the problem
                </p>
                <ul class="planning">
                    <li>
                        <a data-toggle="collapse" href="#resources">Resources</a>
                        <div id="resources" class="collapse">
                            Are
                            there sufficient resources to meet minimum crop acreage limits?<br />
                            <a href="">Check resource availabilities</a>. <%--<link to resources page>--%><br />
                            Are any resources critical, i.e. all used up or almost all used up?<br />
                            If yes, increase the amount of critical resource(s) or decrease the minimum crop acreage limits (assuming this is an option).
                        </div>
                    </li>
                    <li>
                        <a data-toggle="collapse" href="#profitabilityOfCrops">Profitability of crops</a>
                        <div id="profitabilityOfCrops" class="collapse">
                            Are all crops profitable?<br />
                            In order to maximize estimated income,<br />
                            Planting Profits will not allocate land or other resources to any unprofitable
                            crops unless minimum crop acreage limits are specified.<br />
                            <a href="">Check profitability of crops</a> <%--<link to crop details page>--%>
                        </div>
                    </li>
                    <li>
                        <a data-toggle="collapse" href="#cropChoices">Crop/field choices</a>
                        <div id="cropChoices" class="collapse">
                            Minimum crop acreage limits, combined with limitations on which crops can be grown in which fields may make some combinations of
                            crops/fields infeasible.<br />
                            If there are one or more minimum crop acreage limits specified, is there enough
                            acreage across the fields where the crop can be grown to meet the minimum?<br />
                            This applies to each crop that has a minimum acreage limit.  Some of which may be competing for the same field
                            <%--<link to crop/field choices page>--%><br />
                            Often, due to the large number of possible combinations this can be difficult to unravel.<br />
                            For crops with minimum acreage limits, either: 1) decrease the acreage limit, 2) increase the number of fields where the crop
                            (s)<br />
                            with the crop acreage limit(s) can be grown, or 3) a combination of 1 and 2.
                        </div>
                    </li>
                </ul>

                <hr />

                <h2>IF PLANNING BY ACRES:</h2>
                <p>
                    Please check the following and make adjustments to
                    resources, crop limits or crop/field choices.
                </p>
                <br />
                <p>
                    Click one or more of the following parameters to
                    diagnose the problem
                </p>
                <ul class="planning">
                    <li>
                        <a data-toggle="collapse" href="#resourcesAcres">Resources</a>
                        <div id="resourcesAcres" class="collapse">
                            Are there sufficient resources to meet minimum crop acreage limits? <br />
                            <a href="">Check resource availabilities</a>. <%--<link to resources page>--%><br />
                            Are any resources critical, i.e. all used up or almost all used up?<br />
                            If yes, increase the amount of critical resource(s) or decrease the minimum crop acreage limits (assuming this is an option).
                        </div>
                    </li>
                    <li>
                        <a data-toggle="collapse" href="#profitabilityOfCropsAcres">Profitability of crops</a>
                        <div id="profitabilityOfCropsAcres" class="collapse">
                            Are all crops profitable?<br />
                            In order to maximize estimated income,<br />
                            Planting Profits will not allocate land or other resources to any unprofitable
                            crops unless minimum crop acreage limits are specified.<br />
                            <a href="">Check profitability of crops</a> <%--<link to crop details page>--%>
                        </div>
                    </li>
                </ul>
            </div><!-- jumbotron -->
        </div><!-- /.col-12 -->
    </div><!-- /.row -->
</div><!-- /.container -->