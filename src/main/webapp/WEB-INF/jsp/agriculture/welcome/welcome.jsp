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
        <div class="leftside welcome-bg">
            <div class="col-lg-10 col-lg-offset-1 col-md-10 col-md-offset-1 col-sm-12 text-white welcomeUser">
                <h1 style="font-size:66px ">WELCOME TO PLANTING PROFITS!</h1><br/>
                <h2>Planting Profits<sup>&reg;</sup> is free for growers.</h2>
                <p class="weight-600">We offer several ways to help you quickly benefit from Planting Profits<sup>&reg;</sup>.</p>
                <ul class="list-unstyled">
                    <li><span class="check-icon"><i class="fa fa-check text-white"></i></span> Free training videos.</li>
                    <li><span class="check-icon"><i class="fa fa-check text-white"></i></span> Free webinars that teach the basics and advanced topics.</li>
                    <li><span class="check-icon"><i class="fa fa-check text-white"></i></span> Free customer support for quick calls. Fee-based packages for customers with greater support
                        needs.
                    </li>
                    <li><span class="check-icon"><i class="fa fa-check text-white"></i></span> On-line production planning service where we work with you to develop and analyze alternative
                        crop-acreage strategies.  </li>
                    <li><span class="check-icon"><i class="fa fa-check text-white"></i></span>Then compare strategies so you can select the one that is best for you.
                    </li>
                </ul>
                <br/>
                <h1>If you have any questions don&apos;t hesitate to contact us. We&apos;ll get
                    back to you as soon as possible.</h1>
                <br/>
                <br/>
                <h3 class="text-white">
                    <em> - Gary Schneider &dash; Founder and President Planting Profits<sup>&reg;</sup> </em>
                </h3>
                <div class="col-lg-12 col-md-12 col-sm-12 text-center">
                    <a class="alertify-button alertify-button-ok remove-text-deco btn-enter" href="<c:url value="/farm.htm"/>" >Enter</a>
                </div>
            </div><!-- /.col-12 -->
        </div>

        <%@ include file="../manage-farm/common/right_slider.jsp" %>
    </div>
</div>
