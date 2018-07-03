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
<link rel="stylesheet" href="<c:url value="/css/accordion.css?v=0.1"/>"/>
<script type="text/javascript" src="<c:url value="/js/accordion.js"/>"></script>

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
                                <a href="<c:url value="/documents/Planting Profits 5-Step Planning Process.pdf"/>"                                    class="remove-text-deco">Production Planing Process
                                </a>

                            <%--<a href="javascript:; " onclick="$('#view-planning-process-popup').show();"--%>
                            </li>
                        </ul>
                </div><!-- /.col-12 -->
            </div><!-- /.row -->
        </div><!-- /.container -->
    </article>
</section>
<section class="faq">
    <div class="container">
        <div class="row">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                               Application Support
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <h5><strong>Q - I only farm corn and soybeans how can the software help me?</strong></h5>
                            <p><b>Ans</b> -
                                Planting Profits provides the greatest benefit to growers who have several crop choices and some flexibility in their rotations.
                                The more crop/field choices the greater the benefit.  That being said a grower who has five fields and two choices for each field has a possible 32 crop field combinations.
                                Even if you only have half that many, it’s still a lot to analyze.  Planting Profits can help you find the most profitable combination in seconds after you enter some basic farm information.
                                If you don’t have many crop/acreage choices, Planting Profits can still help you decide how much to forward sell,
                                how much to pay for rental acreage and provide other useful information to support management decisions.
                            </p>
                            <hr />
                            <h5><strong>Q - I irrigate some of my crops and the other is dryland? How does this work?</strong></h5>
                            <p><b>Ans</b> -
                                Planting Profits treats irrigated and dryland crops as different crops.  For example, irrigated corn differs from dryland corn since income per acre is different due to differences in yield, yield variance, production costs and perhaps price.
                                Also, for irrigated crops a water constraint can be added to model irrigation water allotment or system capacity to understand if or how these factors limit production.
                            </p>
                            <hr />
                            <h5><strong>Q - How does the software address crop insurance?</strong></h5>
                            <P><b>Ans</b> -
                                Planting Profits does not currently analyze crop insurance. A crop insurance module is under development and should be available in Q1 2019.
                            </P>
                            <hr />
                            <h5><strong>Q - How does the software address forward sales?</strong></h5>
                            <p><b>Ans</b> -
                                Planting Profits has a screen where you can setup a forward sale.  You can indicate that the sale is firm, i.e. a commitment or you can let Planting Profits evaluate the contract based on alternative crop/acreage combinations.
                                The forward sale is analyzed and is factored in as part of overall operational profitability, risk and resource use.
                            </p>
                            <hr />
                            <h5><strong>Q - I already sold some of my crop, how does that factor in?</strong></h5>
                            <p><b>Ans</b> -
                                You can model the sale by using a Firm forward sale. This will assign the required acreage to the crop you already sold. The forward sale is factored in as part of overall operational profitability, risk and resource use.
                            </p>
                            <hr />
                            <h5><strong>Q - How do you model multiple forward sales contracts for the same crop?</strong></h5>
                            <p><b>Ans</b> -
                                You can create an individual crop for each forward sale. Enter the contract price, expected yield and production costs.
                                If you’ve committed to, or plan to commit to the contract, set a Minimum crop acreage limit for the contract crop so Planting Profits will assign at least that many acres to the contract crop.
                                If you have not committed to the contract and want to evaluate it, don’t use a Minimum crop acreage limit.
                                Planting Profits will only assign acreage to the proposed contract crop if it is more profitable than an alternative crop.
                                Since the contract provides price certainty there are other considerations beyond estimated income.
                            </p>
                            <hr />
                            <h5><strong>Q - I have limits on equipment, can the software help me?</strong> </h5>
                            <p><b>Ans</b> -
                                Yes. Planting Profits can model equipment utilization in several ways.
                                If you have a piece of equipment that is or may be a constraint on crop acreage you can set it up as a resource then specify how many hours per week it is available and how many hours per week each crop uses.
                                This will show the impact of a piece of equipment on crop acreage assignments as well as how much estimated income will increase or decrease as equipment availability increases or decreases.
                                More than one piece of equipment can be included in an analysis.
                                It is recommended that you start with two resources:  land and capital. Then incrementally add additional resources to the model.
                            </p>
                            <hr />
                            <h5><strong>Q - Some of my land I own and some is cash rent, does the software consider that and how?</strong></h5>
                            <p><b>Ans</b> -
                                Yes. Planting Profits factors in cash rent when helping to decide how many acres of each crop to plant and, specifically, what to plant on rented ground. If you are considering planning corn on your own land and the rental land, create two different corn crops.
                                These two corn crops will have different expenses and different profit per acre.  Let’s call the corn on your ground “corn” and corn on the rented ground “corn_cash_rent”.
                                The variable production costs for “corn_cash_rent” should include the per acre cash rent as well as the base variable production costs.  Enter the forecasted yield for the corn_cash_rent and the forecasted price. Now enter the additional acreage.
                                If you are planning by Fields, create new fields and indicate which of the crops on rented ground you are considering planting in which fields.
                                If you are planning by Acres, put a Maximum crop acreage limit on each of the crops on rented ground equal to the amount of additional acreage.
                            </p>
                            <hr />
                            <h5><strong>Q - How are government programs factored in, e.g. CRP?</strong></h5>
                            <p><b>Ans</b> -
                                Government programs increase revenue of certain crops based on the amount of acreage planted or quantity produced. This can be modeled by adding the additional revenue to the crop.
                                Conservation crops can be created and the revenue and production cost modeled similar to any other row crop.
                            </p>
                            <hr />
                            <h5><strong>Q - What do I do after I’ve entered all of my data and have a Baseline strategy?</strong></h5>
                            <p><b>Ans</b> -
                                There are three paths you can go down.<br />
                                #1 To evaluate ways to increase estimated income view Training Module #7.<br />
                                #2 To evaluate ways to analyze market and production risk view Training Module #8.<br />
                                #3 To evaluate ways to analyze conservation practices view Training Module #9.<br />
                                Please review the training video.
                            </p>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                                Production Planning Service
                            </a>
                        </h4>
                    </div>
                    <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <h5><strong>Q - Can you describe the service and how it works?</strong></h5>
                            <p><b>Ans</b> -
                                Our consultant works with you over the Internet.<br />
                                We ask some questions about your operation and capture some basic farm data like crops, fields, rotations, using a set of worksheets. This usually takes 15-30 minutes.<br />
                                Next the consultant enters the information into Planting Profits with the client watching.  This takes about 15 minutes.<br />
                                Next a baseline strategy is generated. This is the most profitable combination of crops/fields/acreage for the operation based on the information provided.<br />
                                This can be compared to grower intentions if you already have a plan.<br />
                                The consultant and the grower determine which management levers to change then re-analyze the model and repeat.<br />
                                A couple of the best crop mixes are saved and compared in terms of profitability, risk, and asset utilization.<br />
                                Finally, a set of scenarios are created to determine which crop mixes hold up best given uncertainty in prices and yields.<br />
                                Now you’ve got additional useful information to help make your production decisions.
                            </p>
                            <hr />
                            <h5><strong>Q - What do I get at the end of the consultation?</strong></h5>
                            <p><b>Ans - </b>
                                At the end of the consultation the client receives a report that shows the most profitable combinations of crops/fields/ acreage and the underlying data that generated these crop mixes.
                                The report also shows the likely impact of various price and yield scenarios.
                            </p>
                            <hr />
                            <h5><strong>Q - How long does it take? </strong></h5>
                            <p><b>Ans</b> -
                                The consultation normally takes between 1 to 2 hours.
                                A consultation can be done in one session or two sessions.
                            </p>
                            <hr />
                            <h5><strong>Q - What if something changes that affects my plan? </strong></h5>
                            <p><b>Ans</b> -
                                Changes to plans are expected.  As better information becomes available this should be factored in as it may change your plan.<br />
                                Contact us and we can help you make changes to your plan.
                                Or, get on-line and use the software yourself.
                            </p>
                            <hr />
                            <h5><strong>Q - How much does it cost? </strong></h5>
                            <p>
                                A quick support call that is less than 10 minutes is free.<br />
                                A two-hour support package is $300.<br />
                                A four-hour support package is $500.<br />
                            </p>
                            <hr />
                            <h5><strong>Q - What’s the benefit of the on-line service vs. going on-line and using the app myself? </strong></h5>
                            <p><b>Ans</b> -
                                The on-line service provides one-on-one expertise from one of Planting Profits certified consultants.  The consultant can help you to quickly learn and navigate the software while working with you to build your farm model and generate a production plan.<br />
                                Alternatively, you can use the application on-line for free. There is a library of free on-line training videos. Every page has built in help text. There are webinars where you can learn more about advanced topics. Finally, Planting Profits provides free support to address specific questions.
                            </p>
                            <hr />
                            <h5><strong>Q - What if I’m not satisfied with your service? </strong></h5>
                            <p><b>Ans</b> -
                                If you are not completely satisfied with our consulting service we will waive our fee.
                            </p>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="true" aria-controls="collapseThree">
                               Data Ownership and Privacy
                            </a>
                        </h4>
                    </div>
                    <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                        <div class="panel-body">
                            <h5><strong>Q - What happens to my data when I use the software?</strong></h5>
                            <p><b>Ans</b> -
                                Your data is securely stored and is not accessible or disclosed to any third parties without the account owner’s explicit permission.  Planting Profits does not sell customer data.
                            </p>
                            <hr />
                            <h5><b>Q - Who has access to my data?  </b></h5>
                            <p><b>Ans</b> -
                                Growers can grant access to their account to certified professional consultants.
                            </p>
                            <hr />
                            <h5><strong>Q - Is my data ever shared with anyone?  </strong></h5>
                            <p><b>Ans</b> -
                                No.
                                See http://dev.plantingprofits.com/P_Profit/privacy-policy.htm
                            </p>
                            <hr />
                            <h5><strong>Q - How is my data protected?  </strong></h5>
                            <p><b>Ans</b> -
                                Planting Profits is hosted on Amazon Web Services (AWS).  AWS is a leading application hosting services with the highest levels of data security.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
</section>


<script>
    $(function(){
        $('body').addClass('learning-bg')
    })
</script>

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

