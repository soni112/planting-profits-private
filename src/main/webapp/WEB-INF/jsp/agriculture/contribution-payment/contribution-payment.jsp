<%--
  Created by IntelliJ IDEA.
  User: abhishek
  Date: 9/9/16
  Time: 2:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<link rel="stylesheet" href="<c:url value="/css/payment-style.css"/>"/>
<section>
    <div class="container">
        <div class="row" id="donation-amount-div">
            <div class="jumbotron payment-jumbo col-lg-12 col-md-12 text-center">
               <div class="img-corner">
                    <img src="<c:url value="/images/contribution/contribution1.jpg"/> " style="width: 100%;">
                    <div class="clearfix"></div>
                    <br>
                </div>
                <div class="margin-top-2">
                    <div class="col-lg-2 col-md-2 col-sm-2 padding-0"></div>
                    <div class="col-lg-8 col-md-8 col-sm-8">
                        <h2 class="no-margin-top" style="font-size: 40px; line-height: 1.3em;">
                            Planting Profits<sup style="top: -10px;">&reg;</sup> is free for growers.
                            <br>Contributions from our users enable us to maintain and improve Planting Profits<sup style="top: -10px;">&reg;</sup>.
                            <br>We gladly accept contributions…</h2>
                        <div class="clearfix"></div>

                        <%--<h4 class="text-brown">Would you like to make a contribution?</h4>--%>

                        <div class="margin-top-2 m-b-1" style="font-size: 18px">
                            <a class="alertify-button alertify-button-ok text-center remove-text-deco font-weight-600"
                               role="button" data-toggle="collapse" href="#whishdonateamount"
                               aria-expanded="false">Make a contribution
                            </a>
                            <%-- Remove No thanks button according to PPT 04322017, SN: G036--%>
                            <%--<button class="alertify-button alertify-button-ok text-cenetr font-weight-600"
                                    id="logout-btn"
                                    onclick="processLogout('<c:url value="/j_spring_security_logout"/>'); return false;">
                                No Thanks
                            </button>--%>
                            <button class="alertify-button alertify-button-ok text-cenetr font-weight-600"
                                    onclick="navigateBackToApplication(); return false;">
                                Back to application
                            </button>
                        </div>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-2"></div>
                    <br>
                    <hr/>
                    <div class="col-lg-2 col-md-2 col-sm-2"></div>
                    <div class="col-lg-8 col-md-8 col-sm-8">
                        <%--<h3><span class="cal-amt">Calculated Amount: <span id="amt-to-donate">${solverCount}</span></span></h3>--%>
                        <%--<div class="clearfix"></div>--%>
                        <%--<h4 class="chang-amount">--%>
                        <%--As your wish amount you want to Donate &nbsp;--%>
                        <%--<a role="button" data-toggle="collapse" href="#whishdonateamount"--%>
                        <%--aria-expanded="false">Calculated amount...</a>--%>
                        <%--</h4>--%>

                        <div class="row">
                            <%-- <div class="col-lg-3 col-md-3 col-sm-3 padding-0"></div>--%>
                            <div class="donate_money" id="amount-to-be-donated"><%--col-lg-6 col-md-6 col-sm-6 padding-0--%>
                                <div class="form-group form-group1">
                                    <button class="alertify-button alertify-button-ok text-center pull-right donation-amt-btn"
                                            title="select amount and click Make Contibution"
                                            value="$10">$10</button>
                                </div>
                                <div class="form-group form-group1">
                                    <button class="alertify-button alertify-button-ok text-center pull-left donation-amt-btn"
                                            title="select amount and click Make Contibution"
                                            value="$25">$25</button>
                                </div>
                                <div class="form-group form-group1">
                                    <button class="alertify-button alertify-button-ok text-center pull-right donation-amt-btn"
                                            title="select amount and click Make Contibution"
                                            value="$50">$50</button>
                                </div>
                                <div class="form-group form-group1">
                                    <button class="alertify-button alertify-button-ok text-center pull-left donation-amt-btn"
                                            title="select amount and click Make Contibution"
                                            value="$100">$100</button>
                                </div>
                                <a role="button" data-toggle="collapse" href="#whishdonateamount"
                                   aria-expanded="false">Other amount...</a>


                                <div class="chang-amount collapse" id="whishdonateamount">
                                    <div class="cal-amt-input">
                                        <input type="hidden" value="" id="payment-amount">
                                        <%--<label class="lbl-inln-input">contribution: </label>--%>
                                        <input class="form-control payment-whish-input text-left first-name"
                                               placeholder="$0.00"
                                               onchange="addCommaSignWithDollar(this); return false;"
                                               id="modified-amt"
                                               type="text"/>
                                        <button class="alertify-button alertify-button-ok"
                                                type="button" onclick="changeValue(this); showPaymentOptions(); return false;">Contribute</button>
                                    </div>
                                </div>
                            </div>
                            <%--<div class="col-lg-3 col-md-3 col-sm-3 padding-0"></div>--%>

                        </div>

                    </div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2">
                </div>
                <div class="img-dwn-corner">
                    <img src="<c:url value="/images/contribution/contribution2.jpg"/> " style="width: 100%;">
                </div>
            </div>
        </div><!--- col-lg-12 ---->

    </div>
        <div class="row" id="payment-options-div" style="display: none">
        <div class="jumbotron payment-jumbo margin-top-2 col-lg-12 col-md-12">
            <div class="content">

                <div class="sap_tabs">
                    <div id="horizontalTab" style="display: block; width: 100%; margin: 0px;">
                        <div class="pay-tabs">
                            <h2>Select Payment Method</h2>
                            <ul class="resp-tabs-list">
                                <li class="resp-tab-item" aria-controls="tab_item-0" role="tab"><span>
                                    <label class="pic2">
                                     <%--<img src="<c:url value="/images/pic2.png"/>"/>--%>
                                        <i class="fa fa-credit-card fa-2x"></i>
                                    </label>
                                    Credit Card</span>
                                </li>
                                <%--<li class="resp-tab-item" aria-controls="tab_item-1" role="tab"><span>
                                    <label class="pic2">
                                     <img src="<c:url value="/images/pic1.png"/>"/>
                                    </label>
                                    Net Banking</span>
                                </li>--%>
                                <%--<li class="resp-tab-item" aria-controls="tab_item-2" role="tab"><span>
                                    <label class="pic2">
                                    <img src="<c:url value="/images/pic4.png"/>"/>
                                    </label>
                                    PayPal</span>
                                </li>--%>
                                <li class="resp-tab-item" aria-controls="tab_item-3" role="tab"><span>
                                    <label class="pic2">
                                    <%--<img src="<c:url value="/images/pic3.png"/>"/>--%>
                                        <i class="fa fa-credit-card fa-2x"></i>
                                    </label>
                                    Debit Card</span>
                                </li>
                                <div class="clear"></div>
                            </ul>
                        </div>
                        <div class="resp-tabs-container">
                            <div class="tab-1 resp-tab-content" aria-labelledby="tab_item-0">
                                <div class="payment-info">
                                    <form class="credit-card-payment-form">
                                        <div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-2 col-sm-6">

                                            <h3 class="pay-title">Credit Card Info</h3>
                                            <div class="tab-for">
                                                <h5>NAME ON CARD</h5>
                                                <input type="text" value=""
                                                       name="nameOnCard">
                                                <h5>CARD NUMBER</h5>
                                                <input class="pay-logo" type="text"
                                                       name="cardNumber"
                                                       placeholder="0000-0000-0000-0000"
                                                       size="20" data-stripe="number"
                                                       required="">
                                            </div>
                                            <div class="transaction">
                                                <div class="tab-form-left user-form">
                                                    <h5>EXPIRATION</h5>
                                                    <ul>
                                                        <li>
                                                            <input type="text" class="text_box"
                                                                   name="expMonth"
                                                                   placeholder="MM"
                                                                   size="2" data-stripe="exp_month"
                                                                   min="1" max="12"/>
                                                        </li>
                                                        <li>
                                                            <input type="text" class="text_box"
                                                                   name="expYear"
                                                                   size="2" data-stripe="exp_year"
                                                                   placeholder="YY"
                                                                   min="16"/>
                                                        </li>

                                                    </ul>
                                                </div>
                                                <div class="tab-form-right user-form-rt">
                                                    <h5>CVV NUMBER</h5>
                                                    <input type="text" placeholder="XXX"
                                                           name="cvv"
                                                           data-stripe="cvc"
                                                           required="">
                                                </div>
                                                <div class="clear"></div>
                                            </div>

                                        </div>
                                        <div class="col-lg-12 col-sm-12 col-md-12 text-center">
                                            <button class="margin-top-2 padding-bottom-2 alertify-button alertify-button-ok jumbotron submit"
                                                    onclick="submitPaymentForm('credit-card-payment-form'); return false;"
                                                    type="submit">Submit
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="tab-1 resp-tab-content" aria-labelledby="tab_item-3">
                                <div class="payment-info">
                                    <form class="debit-card-payment-form">
                                        <div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-2 col-sm-6">
                                            <h3 class="pay-title">Dedit Card Info</h3>
                                            <div class="tab-for">
                                                <h5>NAME ON CARD</h5>
                                                <input type="text" value=""
                                                       name="nameOnCard">
                                                <h5>CARD NUMBER</h5>
                                                <input class="pay-logo" type="text"
                                                       name="cardNumber"
                                                       placeholder="0000-0000-0000-0000"
                                                       size="20" data-stripe="number"
                                                       required="">
                                            </div>
                                            <div class="transaction">
                                                <div class="tab-form-left user-form">
                                                    <h5>EXPIRATION</h5>
                                                    <ul>
                                                        <li>
                                                            <input type="text" class="text_box"
                                                                   name="expMonth"
                                                                   placeholder="MM"
                                                                   size="2" data-stripe="exp_month"
                                                                   min="1"/>
                                                        </li>
                                                        <li>
                                                            <input type="text" class="text_box"
                                                                   name="expYear"
                                                                   size="2" data-stripe="exp_year"
                                                                   placeholder="YY"
                                                                   min="16"/>
                                                        </li>

                                                    </ul>
                                                </div>
                                                <div class="tab-form-right user-form-rt">
                                                    <h5>CVV NUMBER</h5>
                                                    <input type="text"
                                                           placeholder="XXX"
                                                           name="cvv"
                                                           data-stripe="cvc"
                                                           required="">
                                                </div>
                                                <div class="clear"></div>
                                            </div>
                                            <div class="col-lg-12 col-md-12 text-center">
                                                <button class="margin-top-2 padding-bottom-2 alertify-button alertify-button-ok jumbotron"
                                                        onclick="submitPaymentForm('debit-card-payment-form'); return false;"
                                                        type="button">Submit
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>

    </div>
    </div>
    <!--- container --->
</section>


<script>
    var appContext = '<c:url value="/"/>';
    var stripePublishKey = '<c:out value="${stripePublishKey}"/>';
</script>
<script type="text/javascript" src="https://js.stripe.com/v2/"></script>
<script type="text/javascript" src="<c:url value="/js/plugins/input-mask/jquery.inputmask.bundle.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/plugins/pay-easyResponsiveTabs.js"/>"></script>
<script type="text/javascript"
        src="<c:url value="/js/agriculture/contribution-payment/contribution-payment.js?v=0.1"/>"></script>