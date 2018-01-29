<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Sushil
  Date: 22-Aug-17
  Time: 2:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="<c:url value="/css/payment-style.css?v=0.1"/>"/>

<section class="contibution">
    <article class="">
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-sm-12 text-center margin-top-2">
                    <h1 class="text-white uppercase">Learning Center</h1>
                        <ul class="list-unstyled text-white text-left" style="font-size: 20px;margin-left: 40%">
                            <li>
                                <i class="fa fa-check text-white"></i>
                                <a href="<c:url value="/training.htm"/>" class="remove-text-deco" target="_blank">Training</a>
                            </li>
                            <li>
                                <i class="fa fa-check text-white"></i>
                                <a href="<c:url value="/documents/Planting%20Profits%20Data%20Collection%20Worksheet.zip"/>" class="remove-text-deco" target="_blank">Farm Data Worksheets</a></li>
                            <li>
                                <i class="fa fa-check text-white"></i>
                                <a href="javascript:;" onclick="$('#view-planning-process-popup').show();"
                                   class="remove-text-deco">Production Planing Process</a>
                            </li>
                        </ul>
                </div><!-- /.col-12 -->
            </div><!-- /.row -->
        </div><!-- /.container -->
    </article>
</section>
<section class="navbar-fixed-bottom contribution-bottom-bg">
    <article class="contribution-select-donate-bg">
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-sm-12 text-center margin-top-1">
                    <%--<h3 style="color: #fff"><em>Space for future messages / ads / etc.</em></h3>--%>
                    <button class="btn-contribute" onclick="window.location = '<c:url value="/farm.htm"/>'">Back to Planting Profits</button>

                </div><!-- /.col-12 -->

            </div><!-- /.row -->
        </div><!-- /.container -->
    </article>
    </di><!--- col-lg-12 ---->
</section>


<script>
    $(function(){
        $('body').addClass('learning-bg')
    })
</script>


