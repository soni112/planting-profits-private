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
<section class="contibution">
    <article class="">
        <div class="container">
            <div class="row">
                <div class="col-md-12 col-sm-12 text-right">
                    <h1 class="text-white uppercase">Sow the way...</h1>
                </div><!-- /.col-12 -->
            </div><!-- /.row -->
        </div><!-- /.container -->
    </article>
</section>
<section class="navbar-fixed-bottom contribution-bottom-bg">
   <%-- <div class="container">
        <div class="row" id="donation-amount-div">--%>
            <%--<div class="jumbotron payment-jumbo col-lg-12 col-md-12 text-center">
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

                        &lt;%&ndash;<h4 class="text-brown">Would you like to make a contribution?</h4>&ndash;%&gt;

                        <div class="margin-top-2 m-b-1" style="font-size: 18px">
                            <a class="alertify-button alertify-button-ok text-center remove-text-deco font-weight-600"
                               role="button" data-toggle="collapse" href="#whishdonateamount"
                               aria-expanded="false">Make a contribution
                            </a>
                            &lt;%&ndash; Remove No thanks button according to PPT 04322017, SN: G036&ndash;%&gt;
                            &lt;%&ndash;<button class="alertify-button alertify-button-ok text-cenetr font-weight-600"
                                    id="logout-btn"
                                    onclick="processLogout('<c:url value="/j_spring_security_logout"/>'); return false;">
                                No Thanks
                            </button>&ndash;%&gt;
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
                        &lt;%&ndash;<h3><span class="cal-amt">Calculated Amount: <span id="amt-to-donate">${solverCount}</span></span></h3>&ndash;%&gt;
                        &lt;%&ndash;<div class="clearfix"></div>&ndash;%&gt;
                        &lt;%&ndash;<h4 class="chang-amount">&ndash;%&gt;
                        &lt;%&ndash;As your wish amount you want to Donate &nbsp;&ndash;%&gt;
                        &lt;%&ndash;<a role="button" data-toggle="collapse" href="#whishdonateamount"&ndash;%&gt;
                        &lt;%&ndash;aria-expanded="false">Calculated amount...</a>&ndash;%&gt;
                        &lt;%&ndash;</h4>&ndash;%&gt;

                        <div class="row">
                            &lt;%&ndash; <div class="col-lg-3 col-md-3 col-sm-3 padding-0"></div>&ndash;%&gt;
                            <div class="donate_money" id="amount-to-be-donated">&lt;%&ndash;col-lg-6 col-md-6 col-sm-6 padding-0&ndash;%&gt;
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
                                        &lt;%&ndash;<label class="lbl-inln-input">contribution: </label>&ndash;%&gt;
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
                            &lt;%&ndash;<div class="col-lg-3 col-md-3 col-sm-3 padding-0"></div>&ndash;%&gt;

                        </div>

                    </div>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2">
                </div>
                <div class="img-dwn-corner">
                    <img src="<c:url value="/images/contribution/contribution2.jpg"/> " style="width: 100%;">
                </div>
            </div>--%>
                <article class="margin-top-1">
                    <div class="container">
                    <div class="row">
                        <div class="col-md-12 col-sm-12 text-center">
                            <h3 class="text-white">Planting Profits<sup>®</sup> is free for growers.</h3>
                            <p class="text-white">Your contribution will help us maintain and improve Planting Profits<sup>®</sup><br>
                                for the benefits for all growers.</p>

                        </div><!-- /.col-12 -->
                    </div><!-- /.row -->
                </article>
                <article class="contribution-select-donate-bg">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-12 col-sm-12 text-center">
                                <h3 class="text-white uppercase">Contribute</h3>
                                <button class="btn-contribute">$10</button>
                                <button class="btn-contribute">$25</button>
                                <button class="btn-contribute">$50</button>
                                <button class="btn-contribute">$100</button>
                                <button class="btn-contribute">Other</button>
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
        </div><!--- col-lg-12 ---->

    <%--</div>--%>
      <%-- <div class="container">
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
                                     &lt;%&ndash;<img src="<c:url value="/images/pic2.png"/>"/>&ndash;%&gt;
                                        <i class="fa fa-credit-card fa-2x"></i>
                                    </label>
                                    Credit Card</span>
                                </li>
                                &lt;%&ndash;<li class="resp-tab-item" aria-controls="tab_item-1" role="tab"><span>
                                    <label class="pic2">
                                     <img src="<c:url value="/images/pic1.png"/>"/>
                                    </label>
                                    Net Banking</span>
                                </li>&ndash;%&gt;
                                &lt;%&ndash;<li class="resp-tab-item" aria-controls="tab_item-2" role="tab"><span>
                                    <label class="pic2">
                                    <img src="<c:url value="/images/pic4.png"/>"/>
                                    </label>
                                    PayPal</span>
                                </li>&ndash;%&gt;
                                <li class="resp-tab-item" aria-controls="tab_item-3" role="tab"><span>
                                    <label class="pic2">
                                    &lt;%&ndash;<img src="<c:url value="/images/pic3.png"/>"/>&ndash;%&gt;
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
    </div>--%>
    <!--- container --->
       <article class="margin-top-2 paymentMode" style=" display: none; ">
           <div class="container">
               <div class="row">
                   <div class="col-md-12 col-sm-12">
                       <h3 class="text-white m-b-2 text-center">Select Payment Method</h3>
                       <!-- Nav tabs -->
                       <ul class="nav nav-tabs text-center" role="tablist">
                           <li role="presentation" class="active">
                               <a href="#creditCard" aria-controls="Credit Card" role="tab" data-toggle="tab">
                                   <i class="glyphicon glyphicon-credit-card"></i>
                                   <br>
                                   Credit Card
                               </a>

                           </li>
                           <li role="presentation">
                               <a href="#debitCard" aria-controls="Debit Card" role="tab" data-toggle="tab">
                                   <i class="glyphicon glyphicon-credit-card"></i>
                                   <br>
                                   Debit Card
                               </a>

                           </li>

                       </ul>

                       <!-- Tab panes -->
                       <div class="tab-content">
                           <div role="tabpanel" class="tab-pane active" id="creditCard">
                               <div class="payment-info">
                                   <form class="credit-card-payment-form">
                                       <div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-2 col-sm-6">

                                           <h3 class="pay-title">Credit Card Info</h3>
                                           <div class="tab-for">
                                               <h5>NAME ON CARD</h5>
                                               <input type="text" value="" name="nameOnCard">
                                               <h5>CARD NUMBER</h5>
                                               <input class="pay-logo " type="text" name="cardNumber" placeholder="0000-0000-0000-0000" size="20" data-stripe="number" required="">
                                           </div>
                                           <div class="transaction">
                                               <div class="tab-form-left">
                                                   <h5>EXPIRATION</h5>
                                                   <ul>
                                                       <li>
                                                           <input type="text" class="text_box" name="expMonth" placeholder="MM" size="2" data-stripe="exp_month" min="1" max="12">
                                                       </li>
                                                       <li>
                                                           <input type="text" class="text_box" name="expYear" size="2" data-stripe="exp_year" placeholder="YY" min="16">
                                                       </li>

                                                   </ul>
                                               </div>
                                               <div class="tab-form-right user-form-rt">
                                                   <h5>CVV NUMBER</h5>
                                                   <input type="text" class="cvvNumber" placeholder="XXX" name="cvv" data-stripe="cvc" required="">
                                               </div>
                                               <div class="clear"></div>
                                           </div>

                                       </div>
                                       <div class="col-lg-12 col-sm-12 col-md-12 text-center">
                                           <button class="margin-top-2 padding-bottom-2 alertify-button alertify-button-ok btn btn-warning submit" onclick="submitPaymentForm('credit-card-payment-form'); return false;" type="submit">Submit
                                           </button>
                                       </div>
                                   </form>
                               </div>
                           </div>
                           <div role="tabpanel" class="tab-pane" id="debitCard">
                               <div class="payment-info">
                                   <form class="debit-card-payment-form">
                                       <div class="col-lg-6 col-lg-offset-3 col-md-6 col-md-offset-2 col-sm-6">
                                           <h3 class="pay-title">Dedit Card Info</h3>
                                           <div class="tab-for">
                                               <h5>NAME ON CARD</h5>
                                               <input type="text" value="" name="nameOnCard">
                                               <h5>CARD NUMBER</h5>
                                               <input class="pay-logo" type="text" name="cardNumber" placeholder="0000-0000-0000-0000" size="20" data-stripe="number" required="">
                                           </div>
                                           <div class="transaction">
                                               <div class="tab-form-left">
                                                   <h5>EXPIRATION</h5>
                                                   <ul>
                                                       <li>
                                                           <input type="text" class="text_box" name="expMonth" placeholder="MM" size="2" data-stripe="exp_month" min="1">
                                                       </li>
                                                       <li>
                                                           <input type="text" class="text_box" name="expYear" size="2" data-stripe="exp_year" placeholder="YY" min="16">
                                                       </li>

                                                   </ul>
                                               </div>
                                               <div class="tab-form-right user-form-rt">
                                                   <h5 class="text-white">CVV NUMBER</h5>
                                                   <input type="text" class="cvvNumber" placeholder="XXX" name="cvv" data-stripe="cvc" required="">
                                               </div>
                                               <div class="clear"></div>
                                           </div>
                                           <div class="col-lg-12 col-md-12 text-center">
                                               <button class="margin-top-2 padding-bottom-2 alertify-button alertify-button-ok  btn btn-warning" onclick="submitPaymentForm('debit-card-payment-form'); return false;" type="button">Submit
                                               </button>
                                           </div>
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