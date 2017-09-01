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
                <div class="col-md-12 col-sm-12 text-center">
                    <h3 class="text-white uppercase">Consultant's Corner</h3>
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
<section class="navbar-fixed-bottom contribution-bottom-bg" id="donation-amount-div">
    <article class="margin-top-1">
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-sm-12 text-center">
                    <h3 class="text-white">Planting Profits<sup>&reg;</sup> is free for growers.</h3>
                    <p class="text-white">Your contribution will help us maintain and improve Planting Profits<sup>&reg;</sup><br>
                        for the benefit of all growers.</p>

                </div><!-- /.col-12 -->
            </div><!-- /.row -->
        </div>
    </article>
    <article class="contribution-select-donate-bg">
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-sm-12 text-center">
                    <h3 class="text-white uppercase">Contribute</h3>
                    <button class="btn-contribute" onclick="showPaymentOptions(this); return false;" data-value="10">$10</button>
                    <button class="btn-contribute" onclick="showPaymentOptions(this); return false;" data-value="25">$25</button>
                    <button class="btn-contribute" onclick="showPaymentOptions(this); return false;" data-value="50">$50</button>
                    <button class="btn-contribute" onclick="showPaymentOptions(this); return false;" data-value="100">$100</button>
                    <button class="btn-contribute" onclick="showPaymentOptions(this); return false;" data-value="other">Other</button>
                    <br><div class="clearfix"></div>
                    <div id="manual-amt-div" class="col-md-3 col-md-offset-5 col-sm-6 col-sm-offset-3" style="display: none;padding: 1%; left: 20px;">
                        <input type="text" class="form-control" id="payment-amount" placeholder="Enter amount to contribute" style=" width: 50%;">
                    </div>
                    <br><div class="clearfix"></div>
                    <button class="btn-contribute" id="logout-btn" onclick="processLogout('<c:url value="/j_spring_security_logout"/>'); return false;">No Thanks</button>
                    <button class="btn-contribute" onclick="navigateBackToApplication(); return false;">Back to application</button>


                </div><!-- /.col-12 -->

            </div><!-- /.row -->
        </div><!-- /.container -->
    </article>
    <article class="margin-1">
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-sm-12 text-center">
                    <p class="text-white">Thank you for your generosity.</p>
                </div><!-- /.col-12 -->
            </div><!-- /.row -->
        </div><!-- /.container -->
    </article>
    </di><!--- col-lg-12 ---->
</section>
<section class="contribution-bottom-bg" id="payment-options-div" style=" display: none; ">
    <!--- container --->
    <article class="margin-top-2 paymentMode" >
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-sm-12">
                    <h3 class="text-white m-b-2 text-center">Select Payment Method</h3>
                    <!-- Nav tabs -->
                    <ul class="nav nav-tabs text-center" role="tablist">
                        <li role="presentation" class="active">
                            <a href="#creditCard" aria-controls="Credit Card" role="tab" data-toggle="tab">
                                <i class="glyphicon glyphicon-credit-card"></i><br>
                                Credit Card
                            </a>
                        </li>
                        <li role="presentation">
                            <a href="#debitCard" aria-controls="Debit Card" role="tab" data-toggle="tab">
                                <i class="glyphicon glyphicon-credit-card"></i><br>
                                Debit Card
                            </a>
                        </li>
                    </ul>

                    <!-- Tab panes -->
                    <div class="tab-content" style="display: block">
                        <div role="tabpanel" class="tab-pane active" id="creditCard">
                            <div class="payment-info">
                                <form id="credit-card-payment-form">
                                    <div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-2 col-sm-6">

                                        <h3 class="pay-title text-white">Credit Card Info</h3>
                                        <div class="tab-for">
                                            <h5 class="text-white">NAME ON CARD</h5>
                                            <input type="text" value="" name="nameOnCard">
                                            <h5 class="text-white">CARD NUMBER</h5>
                                            <input class="pay-logo " type="text" name="cardNumber" placeholder="0000-0000-0000-0000" size="20" data-stripe="number" required="" autocomplete="false">
                                        </div>
                                        <div class="transaction">
                                            <div class="tab-form-left">
                                                <h5 class="text-white">EXPIRATION</h5>
                                                <ul>
                                                    <li>
                                                        <input type="text" class="text_box" name="expMonth" placeholder="MM" size="2" data-stripe="exp_month" min="1" max="12" autocomplete="false">
                                                    </li>
                                                    <li>
                                                        <input type="text" class="text_box" name="expYear" size="2" data-stripe="exp_year" placeholder="YY" min="16" autocomplete="false">
                                                    </li>
                                                </ul>
                                            </div>
                                            <div class="tab-form-right user-form-rt">
                                                <h5 class="text-white">CVV NUMBER</h5>
                                                <input type="password" class="cvvNumber" placeholder="XXX" name="cvv" data-stripe="cvc" required="" autocomplete="false">
                                            </div>
                                        </div>

                                    </div>
                                    <br><div class="clear"></div>
                                    <div class="nav nav-tabs col-lg-12 col-sm-12 col-md-12 text-center" style="padding: 1%; margin-top: 2%;">
                                        <button class="btn-contribute" onclick="submitPaymentForm('#credit-card-payment-form'); return false;">Submit</button>
                                        <button class="btn-contribute" onclick="showContributionScreen(); return false;">Cancel</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div role="tabpanel" class="tab-pane" id="debitCard">
                            <div class="payment-info">
                                <form id="debit-card-payment-form">
                                    <div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-2 col-sm-6">
                                        <h3 class="pay-title text-white">Dedit Card Info</h3>
                                        <div class="tab-for">
                                            <h5 class="text-white">NAME ON CARD</h5>
                                            <input type="text" value="" name="nameOnCard">
                                            <h5 class="text-white">CARD NUMBER</h5>
                                            <input class="pay-logo" type="text" name="cardNumber" placeholder="0000-0000-0000-0000" size="20" data-stripe="number" required="" autocomplete="false">
                                        </div>
                                        <div class="transaction">
                                            <div class="tab-form-left">
                                                <h5 class="text-white">EXPIRATION</h5>
                                                <ul>
                                                    <li>
                                                        <input type="text" class="text_box" name="expMonth" placeholder="MM" size="2" data-stripe="exp_month" min="1" autocomplete="false">
                                                    </li>
                                                    <li>
                                                        <input type="text" class="text_box" name="expYear" size="2" data-stripe="exp_year" placeholder="YY" min="16" autocomplete="false">
                                                    </li>

                                                </ul>
                                            </div>
                                            <div class="tab-form-right user-form-rt">
                                                <h5 class="text-white">CVV NUMBER</h5>
                                                <input type="password" class="cvvNumber" placeholder="XXX" name="cvv" data-stripe="cvc" required="" autocomplete="false">
                                            </div>
                                        </div>
                                    </div>
                                    <br><div class="clearfix"></div>
                                    <div class="nav nav-tabs col-lg-12 col-sm-12 col-md-12 text-center" style="padding: 1%; margin-top: 2%;">
                                        <button class="btn-contribute" onclick="submitPaymentForm('#debit-card-payment-form'); return false;">Submit</button>
                                        <button class="btn-contribute" onclick="showContributionScreen(); return false;">Cancel</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>

                </div><!-- /.col-12 -->
            </div><!-- /.row -->
        </div><!-- /.container -->
    </article>
</section>


<script>
    var appContext = '<c:url value="/"/>';
    var stripePublishKey = '<c:out value="${stripePublishKey}"/>';
</script>
<script type="text/javascript" src="https://js.stripe.com/v2/"></script>
<script type="text/javascript" src="<c:url value="/js/plugins/input-mask/jquery.inputmask.bundle.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/plugins/pay-easyResponsiveTabs.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/agriculture/contribution-payment/contribution-payment.js?v=0.1"/>"></script>
