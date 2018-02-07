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
            <div class="col-lg-12 col-md-12 col-sm-12 text-white welcomeUser">
                <h1 style="font-size:68px "><em>Welcome to Planting Profits!</em></h1><br/>
                <h2>Planting Profits<sup>&reg;</sup> is free for growers.</h2><br/>
                <p class="weight-600">We offer several ways to help you quickly benefit from Planting Profits.</p>
                <br/>
                <ul>
                    <li style="font-size: 22px; padding: 0 0;">Free training videos.</li>
                    <li style="font-size: 22px; padding: 0 0;">Free webinars that teach the basics and advanced topics.</li>
                    <li style="font-size: 22px; padding: 0 0;">Free customer support for quick calls. Fee-based packages for customers with greater support
                        needs.
                    </li>
                    <li style="font-size: 22px; padding: 0 0;">On-line production planning service where we work with you to develop and analyze alternative
                        crop-acreage strategies. Then compare strategies so you can select the one that is best for you.
                    </li>
                </ul>
                <br/>
                <h1><em>If you have any questions don&apos;t hesitate to contact us. We&apos;ll get
                    back to you as soon as possible.</em></h1>
                <br/>
                <br/>
                <h3 class="text-white">
                    <em> - Gary Schneider &dash; Founder and President Planting Profits</em>
                    <a class="alertify-button alertify-button-ok remove-text-deco"
                       href="<c:url value="/farm.htm"/>" style="padding: 0px 15px; color: #000">Enter</a>
                </h3>
            </div><!-- /.col-12 -->
        </div>

        <%@ include file="../manage-farm/common/right_slider.jsp" %>
    </div>
</div>
