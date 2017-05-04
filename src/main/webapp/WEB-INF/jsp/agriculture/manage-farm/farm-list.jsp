<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script type="text/javascript">
    /**
     * @changed - Abhishek
     * @date - 03-12-2015
     */
    var country = '';
    <c:choose>
        <c:when test="${model.country ne null}">
            country = '${model.country}';
        </c:when>
        <c:otherwise>
            country = 'none';
        </c:otherwise>
    </c:choose>
    jQuery(document).ready(function () {
        jQuery('.bxslider2').bxSlider({
            nextSelector: '#slider-next',
            prevSelector: '#slider-prev',
            nextText: 'Onward →',
            prevText: '← Go back',
            infiniteLoop: false
        });
    });

</script>
<script>
    $(function () {
        var clickedOnId = false;
        /*$(document).click(function() {
         if (clickedOnId == false && $("#farm_option_div").hasClass("open")) {
         $("#farm_option_div").removeClass("open");
         }
         else {
         clickedOnId = false;
         }
         });*/
        /* $("#farm_option_div").unbind('click');
         $("#farm_option_div").click(function () {
         /!*$(this).addClass("open");
         clickedOnId = true;*!/
         if($("#farm_option_div").hasClass('open')){
         $("#farm_option_div").removeClass('open');
         } else {
         $("#farm_option_div").addClass('open');
         }
         });*/

        $('#menu-strategies, #menu-scenarios').hide();
        $('#menu-management').show();


    });
</script>
<script src="<c:url value="/js/agriculture/farm.js?v=0.1"/>" type="text/javascript"></script>

<div class="leftside">
    <%@ include file="common/menu.jsp" %>
    <div class="mainsection farm_section">
        <section>
            <div class="wrap clearfix">
                <div class="horizontal_slider">
                    <div class="btn-group farm_setting" id="farm_option_div">
                        <button aria-expanded="false" data-toggle="dropdown" class="farm_settings_btn dropdown-toggle"
                                type="button"><img src="<c:url value="/images/settings_icon.png"/>"> <span class="caret"></span></button>
                        <ul role="menu" class="dropdown-menu setting_menu">
                            <li><a onclick="deleteAllFarms()">Delete All</a></li>
                            <li><a onclick="deleteSelectedFarms()">Delete Selected Farm</a></li>
                            <li><a onclick="editSelectedFarm()">Edit Selected Farm Name</a></li>
                        </ul>
                    </div>
                    <ul class="bxslider2">
                        <c:set var="count" value="1" scope="page"></c:set>
                        <c:forEach var="farm" items="${model.farmList}">

                            <c:if test="${count % 3 eq '1'}">
                                <li>
                            </c:if>

                            <div class="col-lg-4 col-sm-3 col-md-3">
                                <div class="custom_checkbox">
                                    <input type="checkbox" id="farm-id-@-@-${farm.farmId}-@-@-${farm.farmName}"
                                           name="farms[]" value="${farm.farmId}" class="custom_input_box"/>
                                    <label for="farm-id-@-@-${farm.farmId}-@-@-${farm.farmName}">&nbsp;</label>
                                </div>
                                <div class="number_of_farm"><!-- <a><img src="images/farm1.jpg"></a> -->

                                    <!-- Modified by Harshit Gupta
                                    01-04-2014
                                    Start -->

                                        <%----%>
                                    <c:choose>
                                        <c:when test="${farm.saveFlag}">
                                            <div class="bx-caption">
                                                <a href="output-edit-farm-info.htm?farmId=${farm.farmId}"><img
                                                        src="<c:url value="/images/farm1.jpg"/>"><span>${farm.farmName}</span></a>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="bx-caption">
                                                <a href="output-edit-farm-info.htm?farmId=${farm.farmId}"><img
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

                            <c:set var="count" value="${count+1}"></c:set>
                        </c:forEach>
                    </ul>
                    <div class="outside"><p><span id="slider-prev"></span><span id="slider-next"></span></p>
                    </div>
                </div><!--end slider_wrapper-->
            </div>
            <div class="addnewfarm_section">
                <a id="popup" onclick="div_show1()" class="rolllink"><span data-title="Add New Farm">Add New Farm</span></a>
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
                                <%-- Add label as PPT 04322017, SN: G037--%>
                                <div class="input-group">
                                    <input type="text" class="form-control" id="farm-name" placeholder="Farm Name"/>
                                    <div class="input-group-addon">Farm</div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label>Physical Location</label>
                                <!-- Commented as per client requirement on 29-07-2015-->
                                <!-- <a id="physical_location" class="help_Infromation_PopUp"  href="#"><img src="images/i-icon.png"></a> -->
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
            <!-- Popup for update farm functionality By Harshit Gupta on 27-04-2015 -->
            <div id="updateFarmDiv">
                <div id="popupUpdateFarm">
                    <!-- Contact Us Form -->
                    <div class="popup_section">
                        <img id="close" src="<c:url value="/images/cross.png"/>" onclick="div_hideUpdateFarmDiv()">
                        <h2 class="popupheader">Update Farm</h2>
                        <input type="hidden" id="update-farm-id"/>
                        <div class="popupform">
                            <div class="form-group">
                                <label>Farm Name</label> <input type="text" class="form-control" id="update-farm-name"
                                                                placeholder="Farm Name"/>
                            </div>
                            <div class="form-group">
                                <label>Physical Location</label> <input type="text" class="form-control"
                                                                        id="update-physical-localtion"/>
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
                                <a onclick="updateSelectedFarm()" id="submit">Update farm</a>
                            </div>
                        </div>

                    </div>
                </div>
                <!-- Popup update farm div end -->

            </div>
            <!-- Popup Div Ends Here -->
        </section>


    </div>
</div>

<script type="text/javascript" src="<c:url value="/js/agriculture/farm/farm-list.js" />"></script>
<%@ include file="common/right_slider.jsp" %>