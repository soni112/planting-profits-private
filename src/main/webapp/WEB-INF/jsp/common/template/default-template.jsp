<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<!doctype html>
<html lang="en">
<head>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel='shortcut icon' type='image/x-icon' href='<c:url value="/images/favicon.png"/>'/>
    <meta name="viewport" contant="width=evice-width, initial-scale=1">
    <!--------Bootstrap Stylesheet--------------->
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.css"/> " type="text/css" media="all">
    <link rel="stylesheet" href="<c:url value="/css/font-awesome.css"/> " type="text/css" media="all">
    <link rel="stylesheet" href="<c:url value="/css/jquery.bxslider.css"/>"  type="text/css" />
    <!--------end Bootstrap Stylesheet--------------->
    <link rel="stylesheet" href="<c:url value="/css/style.css?v=1.0"/>" type="text/css" media="all">
    <link rel="stylesheet" href="<c:url value="/css/responsive.css?v=1.0"/>" type="text/css" media="all">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-datetimepicker.min.css"/>" type="text/css" media="all">
    <link rel="stylesheet" href="<c:url value="/css/datepicker.css"/>" type="text/css" media="all">
    <!-- css for alert -->
    <link rel="stylesheet" href="<c:url value="/css/alertify.core.css"/>" type="text/css" media="all">
    <link rel="stylesheet" href="<c:url value="/css/alertify.default.css"/>" type="text/css" media="all">

    <!-- css by developer -->
    <link rel="stylesheet" href="<c:url value="/css/dev-design.css?v=1.0"/>" type="text/css" media="all">


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- comment for output screen
    start -->
    <script src="<c:url value="/js/plugins/jquery.js"/>"  type="text/javascript"></script>
    <%--<script src="<c:url value="/js/jquery-1.10.2.min.js"/>" type="text/javascript" ></script>--%>
    <script src="<c:url value="/js/plugins/tabcontent.js"/>" type="text/javascript"></script>
    <!-- end -->
    <%--<script src="<c:url value="/js/jquery.min.js"/>"  type="text/javascript"></script>--%>
    <script src="<c:url value="/js/plugins/bootstrap.js"/>"  type="text/javascript"></script>
    <script src="<c:url value="/js/plugins/jquery.bxslider.js"/>"  type="text/javascript"></script>
    <script src="<c:url value="/js/plugins/jquery.bxslider.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/plugins/bootstrap-datepicker.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/plugins/bootstrap-datetimepicker.min.js"/>" type="text/javascript"></script>
    <!-- jQuery for alert -->
    <script src="<c:url value="/js/plugins/alertify.js"/>" type="text/javascript"></script>
    <!-- common js start -->
    <script src="<c:url value="/js/common/common.js"/>" type="text/javascript"></script>
    <script src="http://maps.googleapis.com/maps/api/js?libraries=places&key=AIzaSyAvKD-Xvr6y41ycFCPqMiDe7rFaK4x_RCs"></script>
    <script src="<c:url value="/js/plugins/jquery.geocomplete.js"/>" type="text/javascript"></script>
    <!-- add by rohit on 29-04-15 -->
    <script src="<c:url value="/js/agriculture/validation_farm.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/agriculture/helpInformation.js?v=1.0"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/pie_chart_graph/jquery.canvasjs.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/pie_chart_graph/amcharts.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/pie_chart_graph/serial.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/pie_chart_graph/pie.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/pie_chart_graph/xy.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/pie_chart_graph/light.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/plugins/jquery.tmpl.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/plugins/jquery.slimscroll.js"/>" type="text/javascript"></script>


    <script type="text/javascript">
        jQuery(document).ready(function(){
            jQuery('.bxslider').bxSlider({
                mode: 'fade',
                captions: true,
                auto: true
            });
        });
    </script>
    <script src="<c:url value="/js/agriculture/popup.js"/>" type="text/javascript"></script>
    <title>Planting Profits</title>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <tiles:insertAttribute name="header" />
        <tiles:insertAttribute name="body-content" />
        <tiles:insertAttribute name="footer" />
    </div>
</div>
<!-- Popup Div Starts Here -->
<div id="info">
    <div id="popupContact">
        <!-- Planning Form -->
        <div class="popup_section">
            <!-- <img id="close" src="images/cross.png" onclick ="div_hide13()"> -->
            <h2 class="popupheader">Information</h2>
            <div class="">
                <div class="popupform messagepopup">
                    <div id="content_box_help" class="popup_section_inner">

                    </div>
                    <!-- <p>You indicated that you would like to plan by both fields and total acres (i.e. without regard to fields).  To plan by fields you will need to enter information on your farm's fields.
                    </p>
                    <p>Would you like to begin planning by fields or acres?</p>-->

                    <div class="clearfix"></div>
                    <div class="yellobtn submit okbutton margin-top-2">
                        <a onclick ="div_hide13()">Ok</a>
                        <!--id="submit" onClick="showMyNextPage()"-->
                    </div>

                </div>
            </div>
        </div>

    </div>
</div>

<%--   @added - Abhishek   @date - 18-12-2015  --%>
<div id="updatedResourcePopop" class="updatedResourcePopop">
    <div class="popupContact">
        <div class="popup_section">
            <h2 class="popupheader">Information</h2>
            <div class="">
                <div class="popupform messagepopup">
                    <div id="updatedResourcePopopContent" class="popup_section_inner">
                        <div class="table-responsive strategy_table">
                            <table id="strategy_div_content" width="100%" cellspacing="0" class="table table-striped tbl-bordr  tblbrdr output_table">
                                <thead></thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                    <div class="yellobtn submit okbutton margin-top-2">
                        <a onclick ="$('#updatedResourcePopop').hide()">Cancel</a>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<!-- Popup Div Ends Here -->

<div style="display: none; z-index: 999;" id="view-planning-process-popup" class="pop-up">
    <div class="pop-up-body" style="width: 70%;left: 15%; top: 12%;">
        <!-- Planning Form -->
        <div class="popup_section">
            <img onclick="$('#view-planning-process-popup').hide();" src="<c:url value="/images/cross.png"/> " class="img-close">
            <div class="popupform" style="padding: 0 0;">
                <div class="panel">
                    <div class="panel-heading text-center" style="padding: 0 0;">
                        <h2 class="popupheader">Production Planning Process</h2>
                    </div>

                    <div class="panel-body" style="display: block">
                        <img src="<c:url value="/images/production_planning/Planting%20Profits%20Planning%20Process.jpg"/>"
                        style="width: 100%;"/>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>


<!-- Google Analytics code for local website -->
<%--<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-74862522-2', 'auto');
    ga('send', 'pageview');

</script>--%>

<!-- Google Analytics code for live website -->
<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-74862522-1', 'auto');
    ga('send', 'pageview');

</script>
</body>
</html>
