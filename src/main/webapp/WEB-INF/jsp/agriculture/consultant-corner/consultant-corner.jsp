<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Sushil
  Date: 22-Aug-17
  Time: 2:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="<c:url value="/css/payment-style.css?v=0.1"/>"/>

<section class="contibution">
    <article class="">
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-sm-12 text-center margin-top-2">
                    <h1 class="text-white uppercase">Consultant's Corner</h1>
                    <ul class="list-unstyled text-white text-left" style="font-size: 20px;margin-left: 40%">
                        <li>
                            <i class="fa fa-check text-white"></i>
                            <a href="https://appointiv.herokuapp.com/appointivwebform/plantingprofits/booking"
                               class="remove-text-deco"
                               target="_blank">Schedule an Appointment</a>
                        </li>
                        <li><i class="fa fa-check text-white"></i> Find a Consultant</li>
                        <li><i class="fa fa-check text-white"></i> Request Support</li>
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
                    <h3 style="color: #fff"><em>Space for future messages / ads / etc.</em></h3>
                    <button class="btn-contribute" onclick="window.location = '<c:url value="/farm.htm"/>'">Back to Planting Profits</button>
                </div><!-- /.col-12 -->

            </div><!-- /.row -->
        </div><!-- /.container -->
    </article>
    </di><!--- col-lg-12 ---->
</section>

<script>
    $(function(){
        $('body').addClass('consultant-bg')
    })
</script>
<script type="text/javascript" src="<c:url value="/js/agriculture/contribution-payment/contribution-payment.js?v=0.1"/>"></script>
