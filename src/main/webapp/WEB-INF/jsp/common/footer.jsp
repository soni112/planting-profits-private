<%@ include file="/WEB-INF/jsp/include.jsp" %>

<footer>
    <sec:authorize access="isAuthenticated()">
        <div class="reportIssue">
            <a href="<c:url value="/report-issue.htm"/>" data-toggle="tooltip" data-placement="right"
               title="Flag an Issue">
                <i class="fa fa-flag"></i>
            </a>
        </div>
    </sec:authorize>
    <div class="ftr">
        <div class="wrap clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12">
                <!-- END -->
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-center">
                    <a href="<c:url value="/learning-center.htm"/>" target="_blank">
                        <img src="<c:url value="/images/ic_learning.png"/>" width="100px">
                        <%--<h4 class="">Learning Center</h4>--%>
                    </a>
                </div>

                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-center">
                    <a href="<c:url value="/consultant-corner.htm"/>" target="_blank">
                        <img src="<c:url value="/images/ic_consultant.png"/>" width="100px">
                        <%--<h4 class="" style="padding-top: 21.5%;">Consultant's Corner</h4>--%>
                    </a>
                </div>

                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-center">
                    <a href="<c:url value="/professional-partners.htm"/>" target="_blank">
                        <img src="<c:url value="/images/professional_partners.png"/>" width="100px">
                        <%--<h4 class="">Professional Partners</h4>--%>
                    </a>
                </div>

                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-center">
                    <a href="<c:url value="/farm-data.htm"/>" target="_blank">
                        <img src="<c:url value="/images/ic_farmData.png"/>" width="100px">
                        <%--<h4 class="">Farm Data</h4>--%>
                    </a>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-center">
                    <a href="<c:url value="/educators.htm"/>" target="_blank">
                        <img src="<c:url value="/images/educator.png"/>" width="100px">
                        <%--<h4 class="">Educators</h4>--%>
                    </a>
                </div>
                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 text-center">
                    <a href="<c:url value="/contact.htm"/>" target="_blank">
                        <img src="<c:url value="/images/ic_contact.png"/>" width="100px">
                        <%--<h4 class="">Contact</h4>--%>
                    </a>
                </div>
                <%--<div class="pull-left cons col-lg-2 col-md-2 col-sm-2">
                    <a href=""><h4 class="const">Learning Center</h4></a>
                    <ul class="list-unstyled">
                        <li>- <a href="<c:url value="/documents/Planting%20Profits%20Data%20Collection%20Worksheet.zip"/>"
                                 class="remove-text-deco"
                                 target="_blank">Farm Data Worksheets</a></li>
                        <li>- <a href="<c:url value="/training.htm"/>"
                                 class="remove-text-deco"
                                 target="_blank">Training Modules</a></li>
                        <li>- <a href="javascript:;" onclick="$('#view-planning-process-popup').show();"
                                 class="remove-text-deco">Production Planning Process</a></li>
                    </ul>
                </div>

                <div class="pull-left cons col-lg-3 col-md-3 col-sm-3">
                    <a href=""><h4 class="const">Consultant's Corner</h4></a>
                    <ul class="list-unstyled">
                        <li>- Video/chat for online consulting</li>
                        <li>- Find a consultant</li>
                        <li>- <a href="https://appointiv.herokuapp.com/appointivwebform/plantingprofits/booking"
                                 class="remove-text-deco"
                                 target="_blank">Schedule Appointment</a></li>
                    </ul>
                </div>

                <div class="add col-lg-3 col-md-3 col-sm-3">
                    <h4 class="address">Address</h4>
                    <div class="text-left">
                        Planting Profits, LLC <br> PO Box 201 &nbsp; Masonville, CO
                        80541
                    </div>
                </div>

                <div class="eml col-lg-2 col-md-2 col-sm-2">
                    <h4 class="email">Email-Address</h4>
                    <div class="text-left">
                        <i class="fa fa-envelope"></i> <a
                            href="mailto:info@plantingprofits.com" style="color: white;">info@plantingprofits.com</a>
                    </div>
                </div>

                <div class="cot col-lg-2 col-md-2 col-sm-2">
                    <h4 class="contact">Contact-info</h4>
                    <div class="text-left">
                        <i class="fa fa-mobile-phone"></i> 970.420.1656
                    </div>
                </div>--%>
            </div>
        </div>
    </div>

    <div class="ftbottom">
        <div class="container clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12 text-center">
                <div class="copy">
                    Copyright &copy; 2015 |
                    <span class="policy">
						<a href="<c:url value="/privacy-policy.htm"/>" target="_blank">Privacy Policy</a>
					</span> | Patent Pending

                </div>
            </div>
        </div>
    </div>
</footer>

