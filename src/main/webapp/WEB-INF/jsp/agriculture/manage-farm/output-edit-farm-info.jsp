<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="leftside">
    <%@ include file="common/menu.jsp" %>
    <script type="text/javascript">
        $(function () {
            prepareBasePopUp();

            /*$('#menu-management').show();*/
        });

        function showPopUpForSelectingStrategyies() {

            var data = [];
            var html = "<ul style='padding-left: 15px;'>";
            <c:forEach items="${model.strategies}" var="strategyData">
                data['${strategyData.getId()}'] = '${strategyData.getStrategyName()}';
            </c:forEach>


            for (var key in data) {
                html += "<li style='width: 50%; float: left; list-style-type: none; '>" +
                        "<label class='generatePDFList'><input type='checkbox' name='strategyIdCheckbox' value=" + key + "><span> " + data[key] + "</span></label></li>"
            }

            html + "</ul>";


            $("#content_box_help").html("");
            $("#content_box_help").html(html);

            if ($("#content_box_help").find("input[name='strategyIdCheckbox']").length == 0) {
                $("#content_box_help").html("No custom strategies are present for the farm.");
            }

            $("#content_box_help").find("input[name='strategyIdCheckbox']").each(function () {
                $(this).change(function () {
                    if ($("#content_box_help").find("input[name='strategyIdCheckbox']:checked").length > 3) {
                        customAlerts('Select up to three strategies for the report', "error", 0);
                        $("input[name='strategyIdCheckbox']").prop("checked", false);
                    }
                });
            });

            $('#info').show();

        }

        function prepareBasePopUp() {
            $('#content_box_help').addClass('generatePDFHeader');
            /**
             * @changed - Abhishek
             * @date - 03-02-2016
             * @desc - changed according to Slide#7 of 01282016
             */
            /*$('#content_box_help').parent().prepend('<h4>Select strategies for which you want to generate report :</h4>');*/
            $('#content_box_help').parent().prepend('<h4>Select which strategies to include in the report : </h4>');

            $('#content_box_help').parent().append('<div id="HideDivOFStrategy" class="yellobtn submit okbutton" style="margin-right: 6px;"><a>Cancel</a></div>');

            $('#HideDivOFStrategy a').click(function () {
                $('#info').hide();
            });

            $("#HideDivOFStrategy").siblings().children("a").attr("onclick", "generateReport(); return false;");
            $("#HideDivOFStrategy").prev().removeClass("margin-top-2");
        }

        function generateReport() {
            var strategyID = new Array();

            var strategies_checkboxes = $("#content_box_help").find("input[name='strategyIdCheckbox']");


            if (strategies_checkboxes.length > 0) {
                $(strategies_checkboxes).each(function () {
                    if ($(this).prop("checked") == true) {
                        strategyID.push($(this).val());
                    }
                });
                var contextPath = '<c:out value="${pageContext.servletContext.contextPath}" />';
                if(strategyID.length != 0){
                    window.location.href = contextPath + '/Generate-Report.htm?farmId=' + ${model.farm.farmId} + '&strategyID=' + strategyID.toString()+'&scenarioId=';
                    $('info').hide();
                }


            } else {
                customAlerts("Select maximum of three strategies for generating report", "error", 0);
            }


        }
    </script>

    <%--<script type="text/javascript">
        /**
        *@chaneged - abhishek
        *@date - 03-12-2015
        */
        $(function(){
            $('.op3').click(function(){

                var html = "<div class='popupdata'><span class='help-block'>Which type of strategy you want to view ?</span>" +
                        " <input type='button' id='baselineStrategy' value='View Output' class='btn btn-default redirectlinks'>" +
                        " <input type='button' id='customStrategy' value='Strategies' class='btn btn-default redirectlinks'>" +
                        "<input type='button' id='cancel-popup' value='Cancel' class='btn btn-default redirectlinks'></div>";

                $('#info').find('#content_box_help').html(html);

                $('#customStrategy').click(function(){
                    window.location = '<c:out value="${pageContext.servletContext.contextPath}" />/view-farm-strategy.htm';
                });

                $('#baselineStrategy').click(function(){
                    window.location = '<c:out value="${pageContext.servletContext.contextPath}" />/output-farm-info.htm?farmId=${model.farmId}';
                });

                $('#info').show();

                $('#cancel-popup').click(function(){
                    $('#info').hide();
                });

                $('#info').find('.clearfix').hide();

            });
        });
    </script>--%>


    <%--<div class="mainsection farm_section">
        <section>
            <div class="wrap clearfix">
                <div class="edit_farm_bg">
                    <div class="col-sm-4 col-md-4">
                        <a href="view-farm-info.htm?farmId=${model.farm.farmId}">
                            <div class="op1">
                                <!--    @changed - Abhishek     @date - 31-12-2015      @updated - 25-01-2016       @desc - applied the text as image and applied hidden class -->
                                &lt;%&ndash;<span class="edit_farm" style="font-size: 26px">Enter or Change Farm Information</span>&ndash;%&gt;
                                <span class="edit_farm hidden" style="font-size: 20px;left: 13px;">Enter or Change Farm Information</span>
                            </div>
                        </a>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <!--    @changed - Abhishek     @date - 25-01-2016       @desc - applied function for generating report -->
                        &lt;%&ndash;<div class="op2">&ndash;%&gt;
                            <div class="op2" onclick="showPopUpForSelectingStrategyies(); return false;" style="cursor: pointer;">
                            <span class="contrary" title="${model.farm.farmName}" style="font-size: 35px">${model.farm.farmName}</span>
                            <!-- <span class="contrary">Contrary to popular belief, Lorem
                                Ipsum is not simply random text. It has roots in a piece of
                                classical Latin literature from 45 BC, making it over 2000 years
                                old. Lorem Ipsum which looks reasonable. The generated Lorem
                                Ipsum is therefore always free from repetition</span> -->
                            <!-- <span class="contrary_contain">You have the option to
                                either Edit the details of Farm or see the Output of Farm. Click
                                on any of these to move ahead.</span> -->
                            &lt;%&ndash;<span class="contrary_contain" style="font-size: 26px; top: 21%; cursor: pointer;" onclick="showPopUpForSelectingStrategyies(); return false;">View Farm Information</span>&ndash;%&gt;
                                <!--    @changed - Abhishek     @date - 31-12-2015      @updated - 25-01-2016       @desc - applied the text as image and applied hidden class -->
                            &lt;%&ndash;<span class="contrary_contain" style="font-size: 26px; top: 21%; cursor: pointer;" onclick="showPopUpForSelectingStrategyies(); return false;">View Farm Information</span>&ndash;%&gt;
                            <span class="contrary_contain hidden" style="font-size: 26px; top: 21%; cursor: pointer;">View Farm Information</span>
                            <!-- <span class="contrary_contain">View Farm Information and it would provide a snapshot of the farm information, this would be very similar to the farm information on the Output report</span> -->
                        </div>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <a href="output-farm-info.htm?farmId=${model.farm.farmId}">
                            <div class="op3">
                                <!--    @changed - Abhishek     @date - 31-12-2015  @updated - 25-01-2016       @desc - applied the text as image and applied hidden class -->
                                &lt;%&ndash;<span class="show_output" style="font-size: 26px">View Output and Analyze the Farming Operation</span>&ndash;%&gt;
                                &lt;%&ndash;<span class="show_output hidden" style="font-size: 20px;">View Output and Analyze the Farming Operation</span>&ndash;%&gt;
                                <span class="show_output hidden" style="font-size: 20px;">View Baseline Strategy</span>
                            </div>
                        </a>
                    </div>
                </div>
                <!--end slider_wrapper-->
            </div>
        </section>
    </div>--%>

    <!---------------new container start here------------------->
    <div class="mainsection farm_section">
        <section>
            <div class="wrap clearfix">
                <div class="edit_farm_bg">
                    <div class="col-sm-4 col-md-4">
                        <a href="view-farm-info.htm?farmId=${model.farm.farmId}">
                            <div class="op1">
                                <!--    @changed - Abhishek     @date - 31-12-2015      @updated - 25-01-2016       @desc - applied the text as image and applied hidden class -->
                                <%--<span class="edit_farm" style="font-size: 26px">Enter or Change Farm Information</span>--%>
                                <span class="edit_farm hidden" style="font-size: 20px;left: 13px;">Enter or Change Farm Information</span>
                            </div>
                        </a>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <!--    @changed - Abhishek     @date - 25-01-2016       @desc - applied function for generating report -->
                        <%--<div class="op2">--%>
                            <div class="op2" onclick="showPopUpForSelectingStrategyies(); return false;" style="cursor: pointer;">
                            <span class="contrary" title="${model.farm.farmName}" style="font-size: 35px">${model.farm.farmName}</span>
                            <!-- <span class="contrary">Contrary to popular belief, Lorem
                                Ipsum is not simply random text. It has roots in a piece of
                                classical Latin literature from 45 BC, making it over 2000 years
                                old. Lorem Ipsum which looks reasonable. The generated Lorem
                                Ipsum is therefore always free from repetition</span> -->
                            <!-- <span class="contrary_contain">You have the option to
                                either Edit the details of Farm or see the Output of Farm. Click
                                on any of these to move ahead.</span> -->
                            <%--<span class="contrary_contain" style="font-size: 26px; top: 21%; cursor: pointer;" onclick="showPopUpForSelectingStrategyies(); return false;">View Farm Information</span>--%>
                                <!--    @changed - Abhishek     @date - 31-12-2015      @updated - 25-01-2016       @desc - applied the text as image and applied hidden class -->
                            <%--<span class="contrary_contain" style="font-size: 26px; top: 21%; cursor: pointer;" onclick="showPopUpForSelectingStrategyies(); return false;">View Farm Information</span>--%>
                            <span class="contrary_contain hidden" style="font-size: 26px; top: 21%; cursor: pointer;">View Farm Information</span>
                            <!-- <span class="contrary_contain">View Farm Information and it would provide a snapshot of the farm information, this would be very similar to the farm information on the Output report</span> -->
                        </div>
                    </div>
                    <div class="col-sm-4 col-md-4">
                        <a href="output-farm-info.htm?farmId=${model.farm.farmId}">
                            <div class="op3">
                                <!--    @changed - Abhishek     @date - 31-12-2015  @updated - 25-01-2016       @desc - applied the text as image and applied hidden class -->
                                <%--<span class="show_output" style="font-size: 26px">View Output and Analyze the Farming Operation</span>--%>
                                <%--<span class="show_output hidden" style="font-size: 20px;">View Output and Analyze the Farming Operation</span>--%>
                                <span class="show_output hidden" style="font-size: 20px;">View Baseline Strategy</span>
                            </div>
                        </a>
                    </div>
                </div>
                <!--end slider_wrapper-->
            </div>
        </section>
    </div>

    <!---------------new container end here--------------------->

</div>


<%@ include file="common/right_slider.jsp" %>