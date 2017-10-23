<%--
  Created by IntelliJ IDEA.
  User: Sushil
  Date: 08-Sep-17
  Time: 6:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<div class="container">
    <div class="row">
        <div class="col-lg-12 col-md-12">
            <div class="jumbotron margin-top-2 pull-left m-b-2" style="width: 100%">

                <h2 class="text-center font-weight-600">Flag an Issue</h2>
                <br>
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <form id="issue-details-form">
                        <div class="form-group" style="padding: 0% 2%;">
                            <textarea class="form-control" name="issue" rows="15" cols="8"
                                      placeholder="Please write down the issue..." style="resize: none;"></textarea>
                        </div>

                        <div class="clearfix"></div>
                        <div class="form-group" style="padding: 0% 2%;">
                            <input type="button" class="alertify-button alertify-button-ok pull-right" value="Submit"
                                   onclick="createIssue(); return false;">
                            <input type="button" onclick="history.back()"
                                   class="alertify-button alertify-button-ok pull-right" value="Back">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/agriculture/report-issue/report-issue.js?v=1.0"/>"></script>

<div style="display: none;" id="issue-submitted-popup" class="pop-up">
    <div id="popupContact">
        <div class="popup_section">
            <img onclick="history.back();" src="<c:url value="/images/cross.png"/> " class="img-close">
            <div class="popupform messagepopup potencial_profit_popup" style="text-align: center;">
                <div class="increase_profit">
                    <p>Thanks for your feedback</p>
                </div>
            </div>
        </div>
    </div>
</div>
