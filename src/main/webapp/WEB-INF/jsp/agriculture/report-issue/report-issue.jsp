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

                <h2 class="text-center font-weight-600">Report Issue</h2>
                <br>
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <form id="issue-details-form">
                        <div class="form-group" style="padding: 0% 2%;">
                            <label>Issue<span class="strickColor">*</span></label>
                            <textarea class="form-control" name="issue" rows="15" cols="8"
                                      placeholder="Write your Issue..." style="resize: none;"></textarea>
                        </div>

                        <div class="clearfix"></div>
                        <div class="form-group" style="padding: 0% 2%;">
                            <input type="button" class="alertify-button alertify-button-ok pull-right" value="Submit"
                                   onclick="createIssue(); return false;">
                            <input type="button" onclick="window.location ='<c:url value="/farm.htm"/>'"
                                   class="alertify-button alertify-button-ok pull-right" value="Back to Application">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/agriculture/report-issue/report-issue.js?v=1.0"/>"></script>
