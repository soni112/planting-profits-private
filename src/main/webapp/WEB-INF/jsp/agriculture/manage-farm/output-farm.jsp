<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript">
    jQuery(document).ready(function () {
        jQuery('.bxslider2').bxSlider({
            nextSelector: '#slider-next',
            prevSelector: '#slider-prev',
            nextText: 'Onward →',
            prevText: '← Go back'
        });
    });
    $(document).ready(function () {
        addActiveClass($("#menu-output"));
    });
</script>
<script src="<c:url value="/js/agriculture/farm/manage_farm.js"/>" type="text/javascript"></script>

<div class="leftside">
    <%@ include file="common/menu.jsp" %>
    <div class="mainsection farm_section">
        <section>
            <div class="wrap clearfix">
                <div class="horizontal_slider" style="height: 370px;">Q
                    <ul class="bxslider2">
                        <c:set var="count" value="1" scope="page" />
                        <c:forEach var="farm" items="${model.farmList}">

                            <c:if test="${count % 3 eq '1'}">
                                <li>
                            </c:if>

                            <div class="col-lg-4 col-sm-3 col-md-3">
                                <div class="number_of_farm"><!-- <a><img src="images/farm1.jpg"></a> -->

                                    <!-- Modified by Harshit Gupta
                                    01-04-2014
                                    Start -->

                                        <%----%>
                                    <c:choose>
                                        <c:when test="${farm.saveFlag}">
                                            <div class="bx-caption">
                                                <a href="output-farm-info.htm?farmId=${farm.id}"><img
                                                        src="<c:url value="/images/farm1.jpg"/>"><span>${farm.farmName}</span></a>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="bx-caption">
                                                <a href="farm-info.htm?farmId=${farm.id}"><img
                                                        src="<c:url value="/images/farm1.jpg"/>"><span>${farm.farmName}</span></a>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                        <%--   <c:choose>
                                          <c:when test="${listValue.saveFlag}">
                                          <div class="bx-caption"><a href="view-farm-info.htm?farmId=${listValue.id}">${listValue.farmName}</a></div></c:when>
                                          <c:otherwise>
                                          <div class="bx-caption"><a href="farm-info.htm?farmId=${listValue.id}">${listValue.farmName}</a></div></c:otherwise>
                                          </c:choose> --%>

                                    <!-- end -->

                                </div>
                            </div>
                            <c:if test="${count % 3 eq '0'}">
                                </li>
                            </c:if>


                            <%--  <div class="col-lg-4 col-sm-3 col-md-3">
                             <div class="number_of_farm"><a><img src="images/farm2.jpg"></a>
                             <div class="bx-caption"><a href="#">${listValue.farmName}</a></div>
                             </div>
                             </div>

                             <div class="col-lg-4 col-sm-3 col-md-3">
                             <div class="number_of_farm"><a href="#"><img src="images/farm3.jpg"></a>
                             <div class="bx-caption"><a href="#">${listValue.farmName}</a></div>
                             </div>
                             </div> --%>

                            <c:set var="count" value="${count+1}" />
                        </c:forEach>
                    </ul>
                    <div class="outside"><p><span id="slider-prev"></span><span id="slider-next"></span></p>
                    </div>
                </div><!--end slider_wrapper-->
            </div>
            <!-- Popup Div Starts Here -->
            <div id="abc">
                <div id="popupAddNewFarm">
                    <!-- Contact Us Form -->
                    <div class="popup_section">
                        <img id="close" src="<c:url value="/images/cross.png"/>" onclick="div_hide1()">
                        <h2 class="popupheader">Add New Farm</h2>
                        <div class="popupform">
                            <div class="form-group">
                                <label>Farm Name</label>
                                <input type="text" class="form-control" id="farm-name" placeholder="Farm Name"/>
                            </div>
                            <div class="form-group">
                                <label>Physical Location</label>
                                <input type="text" class="form-control" id="physical-localtion"/>
                            </div>
                            <!--
                            Code commented as per clients requirement
                            By Harshit Gupta
                            01-04-2015
                            Start
                             -->
                            <!-- <div class="form-group">
                            <label>Fixed Cost</label>
                            <input type="text" class="form-control" id="fixed_cost" placeholder="Fixed Cost" onkeypress="return isValidNumberValue(event)" onchange="getTotalProfitGoal()" />
                            </div>
                            <div class="form-group">
                            <label>Living Expenses</label>
                            <input type="text" class="form-control" id="living_expenses" placeholder="Living Expenses" onkeypress="return isValidNumberValue(event)" onchange="getTotalProfitGoal()"/>
                            </div>
                            <div class="form-group">
                            <label>Additional Profit</label>
                            <input type="text" class="form-control" id="additional_profit" placeholder="Living Expenses" onkeypress="return isValidNumberValue(event)" onchange="getTotalProfitGoal()"/>
                            </div>
                            <div class="form-group">
                            <label>Profit Goal</label>
                            <input type="text" class="form-control" id="profit_goal" disabled="disabled" placeholder="Profit Goal">
                            </div> -->

                            <!-- End -->

                            <div class="yellobtn submit">
                                <a onclick="createFarm()" id="submit">Get Started</a>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <!-- Popup Div Ends Here -->
        </section>


    </div>
</div>
<%@ include file="common/right_slider.jsp" %>