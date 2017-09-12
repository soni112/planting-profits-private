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
            <div class="jumbotron margin-top-2 pull-left m-b-2">

                <h2 class="text-center font-weight-600">Report Issue</h2>
                <br>
                <div class="col-lg-12 col-md-12 col-sm-12">
                    <form id="issue-details-form">
                        <div class="form-group form-group1">
                            <label>Name<span class="strickColor">*</span></label>
                            <input type="text" class="form-control" placeholder="Name" name="name"/>
                        </div>
                        <div class="form-group form-group1">
                            <label>Email<span class="strickColor">*</span></label>
                            <input type="text" class="form-control" placeholder="Email address" name="email"/>
                        </div>
                        <div class="form-group form-group1">
                            <label>Phone</label>
                            <input type="text" class="form-control" placeholder="Phone Number" name="phone"/>
                        </div>
                        <div class="form-group form-group1">
                            <label>Address<span class="strickColor">*</span></label>
                            <input type="text" class="form-control" placeholder=" Address" name="address"/>
                        </div>
                        <div class="form-group form-group1">
                            <label>Country<span class="strickColor">*</span></label>
                            <select class="form-control" name="country"
                                    onchange="getStatesForCountry('#issue-details-form', 'issue'); return false;">
                                <option value="" selected>Select Country</option>
                                <c:forEach var="countryCodes" items="${countryAndCodes}">
                                    <option value="${countryCodes.id}">${countryCodes.countryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group form-group1">
                            <label>State<span class="strickColor">*</span></label>
                            <select class="form-control" name="state">
                                <option value="" selected="">Select State</option>
                            </select>
                        </div>
                        <div class="form-group form-group1">
                            <label>Zipcode<span class="strickColor">*</span></label>
                            <input type="text" class="form-control" placeholder="Zipcode" name="zipcode"/>
                        </div>
                        <div class="clearfix"></div>
                        <div class="form-group" style="padding: 0% 2%;">
                            <label>Issue<span class="strickColor">*</span></label>
                            <textarea class="form-control" name="issue" rows="5" cols="8"
                                      placeholder="Write your Issue..." style="resize: none;"></textarea>
                        </div>

                        <div class="clearfix"></div>
                        <div class="form-group" style="padding: 0% 2%;">
                            <input type="button" class="alertify-button alertify-button-ok pull-right" value="Submit"
                                   onclick="createIssue(); return false;">
                            <input type="reset" class="alertify-button alertify-button-ok pull-right" value="Reset">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="<c:url value="/js/agriculture/report-issue/report-issue.js?v=1.0"/>"></script>
