<%--
  Created by IntelliJ IDEA.
  User: abhishek
  Date: 3/2/18
  Time: 12:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<link rel="stylesheet" href="<c:url value="/css/sb-admin-2.css"/>" type="text/css" media="all">

<div class="container-fluid">
    <div class="row">
        <div class="leftside common-bg main-body">
                <section class="body-content-middile margin-top-3 welcomeBackUser">
                    <div class="m-b-2">
                        <h1 class="weight-700 text-center text-white">
                            Welcome Back!<br/>
                        <div class="col-lg-12 col-md-12 col-sm-12 text-center">
                            <a class="alertify-button alertify-button-ok remove-text-deco btn-enter" href="<c:url value="/farm.htm"/>" >Enter</a>
                        </div>
                    </div><!-- /.col-12 -->

                    <div class="sponsers-block">
                        <aside class="left-nav-sponsers p-r-0">
                        <div class="left-nav-info" style="overflow-y: auto">
                            <h2 class="weight-600 text-center">Grower</h2>
                            <ul class="col-lg-12 col-offset-1 col-md-12 col-offset-1 list-unstyled">
                                <c:forEach var="farmdetails" items="${model.allFarmsForUser}">
                                    <li>
                                        <a href="<c:url value="/output-edit-farm-info.htm?farmId=${farmdetails.farmId}"/>">
                                            <span class="farm-info-name">Farm :</span> <span class="farm-info">${farmdetails.farmName} / ${farmdetails.physicalLocation}</span>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>

                        </div><!-- /.Grower -->
                    </aside>

                        <aside class="left-nav-sponsers p-r-0">
                        <div class="left-nav-info">
                            <h2 class="weight-600 text-center">Latest News</h2>
                        </div><!-- /.Latest News -->
                    </aside>
                    </div>

                </section>

        </div>


        <%--<%@ include file="../manage-farm/common/right_slider.jsp" %>--%>
        <aside>
            <!-- <div class="advertisement">
        <h2>Advertisement Here</h2>
        </div> -->
            <div class="right_side_add right-body">
                <%--<ul>--%>
                <!-- <li><img style="width:250px;height: 230px;"  src="images/advertised_image/image1sliderhome.jpg"> </li>
<li><img style="width:250px;height: 230px;"  src="images/advertised_image/image2sliderhome.jpg"> </li>
-->
                <!--	@changed - Abhishek		@date - 12-02-2016		@desc - according to slide#1 of 02112016 -->
                <%--<li><img src="images/advertised_image/image1.jpg"></li>--%>
                <%--<li><h3>Sponsored By</h3></li>
                <li><h1>Your Logo</h1></li>
                <li><h1>Your Logo</h1></li>
                <li><h3>Please thank our Sponsors</h3></li>--%>
                <div class="adver-margin-top"><h3>Today&#39;s Sponsor</h3></div>
                <div class="text-center" style="margin-top: 45%;"><h4>Reserve this area for Future <br> Revenue producing content</h4></div>

                <%--</ul>--%>
            </div>

        </aside>
    </div>
</div>

<%--
<script type="text/javascript">
    $(function(){
        $('body').addClass('common-bg');
    })
</script>--%>
