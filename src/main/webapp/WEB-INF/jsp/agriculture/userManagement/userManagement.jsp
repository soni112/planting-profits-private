<%--
  Created by IntelliJ IDEA.
  User: abhishek
  Date: 22/3/16
  Time: 12:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<link rel="stylesheet" href="css/selectize.bootstrap3.css" type="text/css" media="all">
<%--    @added - Abhishek   @date - 05-04-2016    @desc - for adding panel for graphs and displaying    --%>
<link rel="stylesheet" href="css/sb-admin-2.css" type="text/css" media="all">
<style>
    .panel-body{
        display: block;
    }
</style>
<div class="leftside">
    <%@ include file="../manage-farm/common/menu.jsp" %>

    <div class="right_farm_form_filled">

        <div class="clearfix"></div>
        <div class="output_base">
            <h3>Management
                <div class="pull-right">
                    <a href="#" class="override-a" onclick="openUpdateProfilePopup(currentUserId); return false;" >Update Profile</a>
                </div>
            </h3>
            <div class="base_white">
                <sec:authorize access="hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')">
                    <div class="col-lg-12 col-md-12 col-sm-12 no-padding">
                        <div class="clearfix"></div>
                        <!---------------------start tab------------------------------->
                        <ul class="tabs-menu list-inline">

                            <li class="selected current">
                                <a href="#add-users"><span class="icon"><img src="images/add-user.png"></span> &nbsp;Add Users</a>
                            </li>
                            <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                                <li class="">
                                    <a href="#admin-manage"><span class="icon"><img src="images/manage.png"></span> &nbsp;View/Manage Admins</a>
                                </li>
                            </sec:authorize>

                            <li class="">
                                <a href="#professional-manage"><span class="icon"><img src="images/manage.png"></span> &nbsp;View/Manage Professionals</a>
                            </li>
                            <li class="">
                                <a href="#grower-manage"><span class="icon"><img src="images/manage.png"></span> &nbsp;View/Manage Growers</a>
                            </li>
                            <li class="">
                                <a href="#student-manage"><span class="icon"><img src="images/manage.png"></span> &nbsp;View/Manage Students</a>
                            </li>
                        </ul>
                        <div class="tab">

                            <div class="tab-content" id="add-users" style="display: block;">

                                <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none sec_mrgn_bottom medium-height-overflow" >

                                    <form>
                                        <div class="form-group form-group1">
                                            <label>Account Type<span class="strickColor">*</span></label>
                                            <select name="user-account-type" class="form-control user-account-type">
                                                <option value="0">--Select Account Type--</option>
                                                <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                                                    <option value="ROLE_ADMIN">Admin</option>
                                                </sec:authorize>
                                                <option value="ROLE_PROFESSIONAL">Professionals</option>
                                                <option value="ROLE_GROWER">Grower</option>
                                                <option value="ROLE_STUDENT">Student</option>
                                            </select>
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>First Name<span class="strickColor">*</span></label>
                                            <input type="text" class="form-control first-name"
                                                   placeholder="First-Name">
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Last Name</label>
                                            <input type="text" class="form-control last-name"
                                                   placeholder="Last-Name">
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Contact No<span class="strickColor">*</span></label>
                                            <input type="text" class="form-control contact-no"
                                                   placeholder="Contact number"
                                                   onkeypress="return isValidNumberValue(event)">
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Email id<span class="strickColor">*</span></label>
                                            <input type="text" class="form-control email-id"
                                                   placeholder="Email address">
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Mailing Address Line1</label>
                                            <input type="text" class="form-control mailing-address-line1"
                                                   placeholder="Mailing Address Line1">
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Mailing Address Line2</label>
                                            <input type="text" class="form-control mailing-address-line2"
                                                   placeholder="Mailing Address Line2">
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Mailing Address Country</label>
                                                <%--<input type="text" class="form-control mailing-address-country"
                                                       placeholder="Mailing Address Country">--%>
                                            <select class="form-control mailing-address-country"
                                                    onchange="getStatesForCountry('#add-users', 'mailing'); return false;">
                                                <option value="" selected>Select Country</option>
                                                <c:forEach var="countryCodes" items="${model.countryAndCodes}">
                                                    <option value="${countryCodes.id}">${countryCodes.countryName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Mailing Address State</label>
                                            <%--<input type="text" class="form-control mailing-address-state"
                                                   placeholder="Mailing Address State">--%>
                                            <select class="form-control mailing-address-state">
                                                <option value="" selected>Select Country First</option>
                                            </select>
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Mailing Address City/Town</label>
                                            <input type="text" class="form-control mailing-address-city"
                                                   placeholder="Mailing Address City/Town">
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Mailing Zip</label>
                                            <input type="text" class="form-control mailing-zip"
                                                   placeholder="Mailing Zip">
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Physical Address Line1</label>
                                            <input type="text" class="form-control physical-address-line1"
                                                   placeholder="Physical Address Line1">
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Physical Address Line2</label>
                                            <input type="text" class="form-control physical-address-line2"
                                                   placeholder="Physical Address Line2">
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Physical Address Country<span
                                                    class="strickColor">*</span></label>
                                                <%--<select class="form-control physical-address-country">
                                                    <c:forEach var="countryCodes"
                                                               items="${model.countryAndCodes.entrySet()}">
                                                        <c:if test='${countryCodes.getValue() ne ""}'>
                                                            <option value="${countryCodes.getValue()}">${countryCodes.getKey()}</option>
                                                        </c:if>
                                                    </c:forEach>
                                                </select>--%>
                                            <select class="form-control physical-address-country"
                                                    onchange="getStatesForCountry('#add-users', 'physical'); return false;">
                                                <option value="" selected>Select Country</option>
                                                <c:forEach var="countryCodes" items="${model.countryAndCodes}">
                                                    <option value="${countryCodes.id}">${countryCodes.countryName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Physical Address State</label>
                                            <%--<input type="text" class="form-control physical-address-state"
                                                   placeholder="Physical Address State">--%>
                                            <select class="form-control physical-address-state">
                                                <option value="" selected>Select Country First</option>
                                            </select>
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Physical Address City/Town</label>
                                            <input type="text" class="form-control physical-address-city"
                                                   placeholder="Physical Address City/Town">
                                        </div>
                                        <div class="form-group form-group1">
                                            <label>Physical Zip</label>
                                            <input type="text" class="form-control physical-zip"
                                                   placeholder="Physical Zip">
                                        </div>
                                        <!--	@changed - Abhishek		@date - 02-04-2016 -->
                                        <div class="form-group form-group1">
                                            <label>Exipry Date</label>
                                            <input type="date" class="form-control expiryDate"
                                                   placeholder="Expiry Date" >
                                        </div>

                                        <div class="form-group form-group1 professional-admin-parent"
                                             style="display: none;">
                                            <label>Select Admin</label>
                                            <select name="professional-admin" class="form-control professional-admin-specific">
                                                <option value="0">Select Admin </option>
                                            </select>
                                        </div>
                                        <div class="clearfix"></div>
                                        <input type="button" class="alertify-button alertify-button-ok pull-right"
                                               value="Save Details"
                                               onclick="saveUser(); return false;">
                                        <input type="reset" class="alertify-button alertify-button-ok pull-right"
                                               value="Reset">
                                    </form>

                                </div>

                            </div>

                            <div id="admin-manage" style="display: none;">

                                <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">

                                    <div class="panel-body">
                                        <div class="table-responsive medium-height-overflow">
                                            <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr">
                                                <thead>
                                                    <tr class="tblhd text-center add-fieldi">
                                                        <td class="text-center add-fieldi width-70"> Administrator</td>
                                                        <td class="text-center add-fieldi"> Action</td>
                                                    </tr>
                                                </thead>
                                                <tbody id="adminListTbody">
                                                    <tr class="success infotext">
                                                        <td colspan="2" >No Administrators</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>

                                </div>

                            </div>

                            <div id="professional-manage" style="display: none;">


                                <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">

                                    <div class="panel-body">
                                        <div class="table-responsive medium-height-overflow">
                                            <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr">
                                                <thead>
                                                    <tr class="tblhd text-center add-fieldi">
                                                        <td class="text-center add-fieldi width-70"> Professional</td>
                                                        <td class="text-center add-fieldi"> Action</td>
                                                    </tr>
                                                </thead>
                                                <tbody id="professionalListTbody">
                                                    <tr class="success infotext">
                                                        <td colspan="2">No Professionals</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>

                                </div>
                            </div>

                            <div id="grower-manage" style="display: none;">
                                <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">

                                    <div class="panel-body">
                                        <div class="table-responsive medium-height-overflow">
                                            <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr grower-list-table">
                                                <thead>
                                                    <tr class="tblhd text-center add-fieldi">
                                                        <td class="text-center add-fieldi"> Grower</td>
                                                        <td class="text-center add-fieldi"> Professional</td>
                                                        <td class="text-center add-fieldi"> Admin</td>
                                                        <td class="text-center add-fieldi"> Action</td>
                                                    </tr>
                                                </thead>
                                                <tbody id="growerListTbody">
                                                    <tr class="success infotext">
                                                        <td colspan="4">No Growers</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>

                                </div>
                            </div>

                            <div id="student-manage" style="display: none">
                                <div class="col-lg-12 col-md-12 col-sm-12 no-padding">
                                    <div class="clearfix"></div>
                                    <div class="panel-body">
                                        <div class="table-responsive medium-height-overflow">
                                            <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr">
                                                <thead>
                                                    <tr class="tblhd add-fieldi">
                                                        <td class="add-fieldi">Student</td>
                                                        <td>Action</td>
                                                    </tr>
                                                </thead>
                                                <tbody id="studentListTbody">
                                                    <tr class="success infotext">
                                                        <td colspan="2">No Students</td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <!--------------end tab------------------------->

                    </div>
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_PROFESSIONAL')">
                <div class="col-lg-12 col-md-12 col-sm-12 no-padding">
                    <div class="clearfix"></div>
                    <div class="panel-body">
                        <div class="table-responsive medium-height-overflow">
                            <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr">
                                <thead>
                                    <tr class="tblhd add-fieldi">
                                        <td class="add-fieldi">Grower</td>
                                        <td>Action</td>
                                    </tr>
                                </thead>
                                <tbody id="professionalChildListTbody">
                                    <tr class="success infotext">
                                        <td class="success infotext" colspan="2"> No Growers Assigned</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
                    <!-- @end #Crop-Limits -->
                </sec:authorize>

                <sec:authorize access="hasRole('ROLE_GROWER')">
                    <div class="col-lg-12 col-md-12 col-sm-12 marginTop-1-percent">

                        <div class="panel panel-yellow">
                            <div class="panel-heading">
                                <label class="panel-heading-label" id="current-professional">
                                    <c:choose>
                                        <c:when test="${model.currentUser.parent ne null}">
                                            You are currently managed by : ${model.currentUser.parent.firstName} ${model.currentUser.parent.lastName}
                                        </c:when>
                                        <c:otherwise>
                                            Currently No Professional Assigned
                                        </c:otherwise>
                                    </c:choose>
                                </label>
                                <label class="panel-heading-label panel-heading-label-initial pull-right" style="cursor: pointer" onclick="toggleProfessionalListForGrower(); return false;">Change</label>
                            </div>
                            <div class="panel-body" id="professionalListForGrowerDiv" style="display: none">
                                <div class="medium-height-overflow" id="professionalListForGrower">
                                    <div class="form-group">
                                        <label class="growerSpecific">
                                            <h4 style="color : #337ab7">Currently No Professionals Are Available</h4>
                                        </label>
                                    </div>
                                </div>
                                <div class="pull-right">
                                    <button class="alertify-button alertify-button-cancel pull-right"
                                            onclick="toggleProfessionalListForGrower(); return false;">Cancel</button>
                                    <button class="alertify-button alertify-button-ok pull-right"
                                            onclick="assignProfessionalToGrower(); return false;">Update Professional</button>
                                </div>
                            </div>

                        </div>
                        <div class="clearfix"></div>

                    </div>
                </sec:authorize>
            </div>
        </div>
    </div>
</div>

<%@ include file="../manage-farm/common/right_slider.jsp" %>

<div style="display: none;" id="childrenList-popup" class="pop-up">
    <div class="pop-up-body">
        <!-- Planning Form -->
        <div class="popup_section">
            <img onclick="closeChildrenListPopup()" src="images/cross.png" class="img-close">
            <div class="potencial_profit_popup">
                <div class="panel panel-yellow">
                    <div class="panel-heading text-center">
                        <label style="cursor: pointer; text-transform: capitalize;">
                            Assign <span id="popup-header">Professional</span>
                        </label>
                    </div>

                    <div class="panel-body" style="display: block">
                        <%--<div class="width-70 pull-left" id="adminList" style="display: none">
                            <select class="form-control selectizeSpecific" id="freeAdmin" multiple
                                    placeholder="Select Admin..."
                                    appRole="ROLE_ADMIN">
                            </select>
                        </div>--%>
                        <div class="width-70 pull-left" id="professionalList" style="display: none">
                            <select class="form-control selectizeSpecific" id="freeProfessionals" multiple
                                    placeholder="Select Professional..."
                                    appRole="ROLE_PROFESSIONAL">
                            </select>
                        </div>
                        <div class="width-70 pull-left" id="growerList" style="display: none">
                            <select class="form-control selectizeSpecific" id="freeGrower" multiple
                                    placeholder="Select Grower..."
                                    appRole="ROLE_GROWER">
                            </select>
                        </div>
                        <div class="pull-right">
                            <button class="alertify-button alertify-button-cancel" onclick="associateChildren(); return false"> Assign</button>
                        </div>
                        <div class="clearfix"></div>
                        <div class="">
                            <div class="table-responsive medium-height-overflow">
                                <table cellspacing="0" class="table table-striped tbl-bordr tblbrdr">
                                    <thead>
                                        <tr class="tblhd text-center add-fieldi">
                                            <td class="text-center add-fieldi width-70 current-children" > Professional</td>
                                            <td class="text-center add-fieldi"> Action</td>
                                        </tr>
                                    </thead>
                                    <tbody id="childrenListTbody">

                                    </tbody>
                                </table>
                            </div>
                        </div>

                    </div>

                </div>

            </div>
        </div>
    </div>
</div>

<div style="display: none;" id="userDetail-popup" class="pop-up">
    <div class="pop-up-body userdetails-popupBody">
        <!-- Planning Form -->
        <div class="popup_section">
            <img onclick="closeUserDetailsPopup()" src="images/cross.png" class="img-close">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="row">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading text-center">
                                User Details
                            </div>

                            <div class="panel-body" id="userDetails">
                                <div class="panel-group" id="accordion">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" class="">Basic-required info</a>
                                            </h4>
                                        </div>
                                        <div id="collapseOne" class="panel-collapse collapse in" aria-expanded="true">
                                            <div class="panel-body">
                                                <div class="form-group form-group1">
                                                    <label>Account Type<span class="strickColor">*</span></label>
                                                    <select name="user-account-type" class="form-control user-account-type">
                                                        <option value="0">--Select Account Type--</option>
                                                        <sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
                                                            <option value="ROLE_ADMIN">Admin</option>
                                                        </sec:authorize>
                                                        <option value="ROLE_PROFESSIONAL">Professionals</option>
                                                        <option value="ROLE_GROWER">Grower</option>
                                                        <option value="ROLE_STUDENT">Student</option>
                                                    </select>
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>First Name<span class="strickColor">*</span></label>
                                                    <input type="text" class="form-control first-name"
                                                           placeholder="First-Name">
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Last Name</label>
                                                    <input type="text" class="form-control last-name"
                                                           placeholder="Last-Name">
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Contact No<span class="strickColor">*</span></label>
                                                    <input type="text" class="form-control contact-no"
                                                           placeholder="Contact number"
                                                           onkeypress="return isValidNumberValue(event)">
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Email id<span class="strickColor">*</span></label>
                                                    <input type="text" class="form-control email-id" disabled placeholder="Email address">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" class="collapsed" aria-expanded="false">Physical address info</a>
                                            </h4>
                                        </div>
                                        <div id="collapseThree" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
                                            <div class="panel-body">
                                                <div class="form-group form-group1">
                                                    <label>Physical Address Line1</label>
                                                    <input type="text" class="form-control physical-address-line1" placeholder="Physical Address Line1">
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Physical Address Line2</label>
                                                    <input type="text" class="form-control physical-address-line2" placeholder="Physical Address Line2">
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Physical Address Country<span class="strickColor">*</span></label>
                                                    <%--<select class="form-control physical-address-country">
                                                        <option value="" selected>Select Country</option>
                                                        <c:forEach var="countryCodes" items="${model.countryAndCodes.entrySet()}">
                                                            <c:if test='${countryCodes.getValue() ne ""}'>
                                                                <option value="${countryCodes.getValue()}">${countryCodes.getKey()}</option>
                                                            </c:if>
                                                        </c:forEach>
                                                    </select>--%>

                                                    <select class="form-control physical-address-country"
                                                            onchange="getStatesForCountry('#collapseThree', 'physical')">
                                                            <option value="" selected>Select Country</option>
                                                            <c:forEach var="countryCodes" items="${model.countryAndCodes}">
                                                                <option value="${countryCodes.id}">${countryCodes.countryName}</option>
                                                            </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Physical Address State</label>
                                                    <%--<input type="text" class="form-control physical-address-state" placeholder="Physical Address State">--%>
                                                    <select class="form-control physical-address-state">
                                                        <option value="" selected>Select Country First</option>
                                                    </select>
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Physical Address City/Town</label>
                                                    <input type="text" class="form-control physical-address-city" placeholder="Physical Address City/Town">
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Physical Zip</label>
                                                    <input type="text" class="form-control physical-zip" placeholder="Physical Zip">
                                                </div>
                                                <!--	@changed - Abhishek		@date - 02-04-2016 -->
                                                <div class="form-group form-group1">
                                                    <label>Exipry Date</label>
                                                    <input type="date" class="form-control expiryDate" placeholder="Expiry Date" >
                                                    <input type="hidden" class="user-identification">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="panel panel-default">
                                        <div class="panel-heading">
                                            <h4 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" class="collapsed" aria-expanded="false">Mailing address info</a>
                                            </h4>
                                        </div>
                                        <div id="collapseTwo" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
                                            <div class="panel-body">
                                                <div class="form-group form-group1">
                                                    <label>Mailing Address Line1</label>
                                                    <input type="text" class="form-control mailing-address-line1" placeholder="Mailing Address Line1">
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Mailing Address Line2</label>
                                                    <input type="text" class="form-control mailing-address-line2" placeholder="Mailing Address Line2">
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Mailing Address Country</label>
                                                    <%--<input type="text" class="form-control mailing-address-country" placeholder="Mailing Address Country">--%>
                                                    <select class="form-control mailing-address-country"
                                                            onchange="getStatesForCountry('#collapseThree', 'physical')">
                                                        <option value="" selected>Select Country</option>
                                                        <c:forEach var="countryCodes" items="${model.countryAndCodes}">
                                                            <option value="${countryCodes.id}">${countryCodes.countryName}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Mailing Address State</label>
                                                    <%--<input type="text" class="form-control mailing-address-state" placeholder="Mailing Address State">--%>
                                                    <select class="form-control mailing-address-state">
                                                        <option value="" selected>Select Country First</option>
                                                    </select>
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Mailing Address City/Town</label>
                                                    <input type="text" class="form-control mailing-address-city" placeholder="Mailing Address City/Town">
                                                </div>
                                                <div class="form-group form-group1">
                                                    <label>Mailing Zip</label>
                                                    <input type="text" class="form-control mailing-zip" placeholder="Mailing Zip">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                            <!-- .panel-body -->
                        </div>
                        <!-- /.panel -->
                    </div>
                    <!-- /.col-lg-12 -->

                    <div class="clearfix"></div>
                    <button class="alertify-button alertify-button-ok pull-right"
                            onclick="updateUserDetails('#userDetails'); return false;">Update Details</button>


                </div>
            </div>
        </div>
    </div>
</div>

<%--<div style="display: none;" id="updateUserDetails-popup" class="pop-up">
    <div class="pop-up-body userdetails-popupBody">
        <!-- Planning Form -->
        <div class="popup_section">
            <img onclick="closeUpdateProfilePopup(); return false;" src="images/cross.png" id="close">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="panel panel-default">
                    <div class="panel-heading text-center">
                        <label style="cursor: pointer; text-transform: capitalize;">Update Profile</label>
                    </div>

                    <div class="panel-body" id="user-Details-to-update">
                        <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none medium-height-overflow medium-height-overRide" >

                            <form>
                                <div class="form-group form-group1">
                                    <label>First Name<span class="strickColor">*</span></label>
                                    <input type="text" class="form-control first-name"
                                           placeholder="First-Name">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Last Name</label>
                                    <input type="text" class="form-control last-name"
                                           placeholder="Last-Name">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Contact No<span class="strickColor">*</span></label>
                                    <input type="text" class="form-control contact-no"
                                           placeholder="Contact number"
                                           onkeypress="return isValidNumberValue(event)">
                                </div>
                                <div class="form-group form-group1 hidden">
                                    <label>Email id<span class="strickColor">*</span></label>
                                    <input type="text" class="form-control email-id" disabled placeholder="Email address">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Mailing Address Line1</label>
                                    <input type="text" class="form-control mailing-address-line1" placeholder="Mailing Address Line1">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Mailing Address Line2</label>
                                    <input type="text" class="form-control mailing-address-line2" placeholder="Mailing Address Line2">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Mailing Address City/Town</label>
                                    <input type="text" class="form-control mailing-address-city" placeholder="Mailing Address City/Town">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Mailing Address State</label>
                                    <input type="text" class="form-control mailing-address-state" placeholder="Mailing Address State">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Mailing Address Country</label>
                                    <input type="text" class="form-control mailing-address-country" placeholder="Mailing Address Country">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Mailing Zip</label>
                                    <input type="text" class="form-control mailing-zip" placeholder="Mailing Zip">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Physical Address Line1</label>
                                    <input type="text" class="form-control physical-address-line1" placeholder="Physical Address Line1">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Physical Address Line2</label>
                                    <input type="text" class="form-control physical-address-line2" placeholder="Physical Address Line2">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Physical Address City/Town</label>
                                    <input type="text" class="form-control physical-address-city" placeholder="Physical Address City/Town">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Physical Address State</label>
                                    <input type="text" class="form-control physical-address-state" placeholder="Physical Address State">
                                </div>
                                <div class="form-group form-group1">
                                    <label>Physical Address Country<span class="strickColor">*</span></label>
                                    <select class="form-control physical-address-country">
                                        <c:forEach var="countryCodes" items="${model.countryAndCodes}">
                                            &lt;%&ndash;<option value="${countryCodes.id}">${countryCodes.countryName}</option>&ndash;%&gt;
                                            <option value="${countryCodes.countryCode}">${countryCodes.countryName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group form-group1">
                                    <label>Physical Zip</label>
                                    <input type="text" class="form-control physical-zip" placeholder="Physical Zip">
                                    <input type="hidden" class="user-identification">
                                </div>

                                <sec:authorize access="hasRole('ROLE_PROFESSIONAL')">
                                    <div class="form-group form-group1">
                                        <label>Logo</label>
                                        <input type="file" class="form-control logo-specific" placeholder="Company Logo">

                                    </div>
                                </sec:authorize>

                                <div class="clearfix"></div>
                                <button class="alertify-button alertify-button-ok pull-right"
                                        onclick="updateUserDetails('#user-Details-to-update'); return false;">Update Details</button>

                            </form>

                        </div>
                    </div>

                </div>

            </div>
        </div>
    </div>
</div>--%>



<div style="display: none;" id="switch-grower-popup" class="pop-up">
    <div class="pop-up-body">
        <!-- Planning Form -->
        <div class="popup_section">
            <img onclick="closeSwitchGrowerPopup()" src="images/cross.png" class="img-close">
            <div class="popupform messagepopup potencial_profit_popup">
                <div class="panel panel-yellow">
                    <div class="panel-heading text-center">
                        <label style="cursor: pointer; text-transform: capitalize;">
                            Switch Growers
                        </label>
                    </div>

                    <div class="panel-body" style="display: block">
                        <div class="col-lg-12 col-md-12 col-sm-12 padding-left-none medium-height-overflow medium-height-overRide" >
                            <div class="form-group form-group1">
                                <div class="panel-heading text-center">
                                    <label>Growers under</label>
                                </div>
                                <label class="growerSpecific">Occupied Grower</label><br>
                                <label class="growerSpecific">Occupied Grower</label><br>
                                <label class="growerSpecific">Occupied Grower</label><br>
                                <label class="growerSpecific">Occupied Grower</label><br>
                                <label class="growerSpecific">Occupied Grower</label><br>
                                <label class="growerSpecific">Occupied Grower</label><br>
                                <label class="growerSpecific">Occupied Grower</label><br>

                            </div>
                            <div class="form-group form-group1">
                                <div class="panel-heading text-center">
                                    <label>Vacant List</label>
                                </div>
                                <label class="growerSpecific">Vacant Grower</label><br>
                                <label class="growerSpecific">Vacant Grower</label><br>
                                <label class="growerSpecific">Vacant Grower</label><br>
                                <label class="growerSpecific">Vacant Grower</label><br>
                                <label class="growerSpecific">Vacant Grower</label><br>
                                <label class="growerSpecific">Vacant Grower</label><br>
                                <label class="growerSpecific">Vacant Grower</label><br>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
        </div>
    </div>
</div>

<script>
    var currentUserId = '${model.currentUser.id}';
    var currentUserRole = '${model.currentUser.role}';
    var currentUserParent = '${model.currentUser.parent.id}';
</script>

<%--<script type="text/javascript" src="js/plugins/jquery.tmpl.min.js"></script>--%>
<script type="text/javascript" src="js/plugins/selectize.js"></script>
<script type="text/javascript" src="js/agriculture/userManagement/userManagement.js"></script>

<%-- <button class="alertify-button alertify-button-cancel" onclick="deleteUser('{{= id}}'); return false;">Delete</button> --%>
<script type="text/x-jQuery-tmpl" id="roleListTbodyTemplate">
    <tr class="tblgrn text-center" >
        <td class="success infotext width-70">
            <h4 style="color : #337ab7; cursor : pointer" onclick="openUserDetailsPopup({{= id}}); return false;">{{= firstName}} {{= lastName}} ({{= role}})</h4>
            <p>Phone Number : {{= phone_No}}</p>
            <p>Email : {{= email_Address}}</p>
        </td>
        <td class="success infotext text-center vertical-middle" >

            <button class="alertify-button alertify-button-ok" onclick="openChildrenListPopup('{{= id}}', '{{= role}}'); return false;" >
                Assign/Unassign {{if role == "ROLE_ADMIN" }}
                        Professional
                            {{else role == "ROLE_PROFESSIONAL" }}
                                Grower
                       {{/if}}
            </button>

        </td>
    </tr>
</script>

<script type="text/x-jQuery-tmpl" id="childrenListTbodyTemplate">
    <tr class="tblgrn text-center" >
        <td class="success infotext width-70">
            <h4 style="color : #337ab7">{{= firstName}} {{= lastName}} ({{= role}})</h4>
            <p>Phone Number : {{= phone_No}}</p>
            <p>Email : {{= email_Address}}</p>
        </td>
        <td class="success infotext text-center vertical-middle" >
            <button class="alertify-button alertify-button-cancel" onclick="unassignUser('{{= id}}', '{{= parent.id}}'); return false;">Unassign</button>
        </td>
    </tr>
</script>

<script type="text/x-jQuery-tmpl" id="growerListTbodyTemplate">
    <tr class="tblgrn text-center" >
        <td class="success infotext ">
            <h4 style="color : #337ab7; cursor : pointer" onclick="openUserDetailsPopup({{= id}}); return false;">{{= firstName}} {{= lastName}} ({{= role}})</h4>
            <p>Phone Number : {{= phone_No}}</p>
            <p>Email : {{= email_Address}}</p>
        </td>
        <td class="success infotext ">
            {{if parent != null}}
                <h4 style="color : #337ab7">{{= parent.firstName}} {{= parent.lastName}} ({{= parent.role}})</h4>
                <p>Phone Number : {{= parent.phone_No}}</p>
                <p>Email : {{= parent.email_Address}}</p>
                {{else}}
                    <h4 style="color : red">Unassigned</h4>
            {{/if}}
        </td>
        <td class="success infotext ">
            {{if parent != null && parent.parent != null}}
                <h4 style="color : #337ab7">{{= superParent.firstName}} {{= superParent.lastName}} ({{= superParent.role}})</h4>
                <p>Phone Number : {{= superParent.phone_No}}</p>
                <p>Email : {{= superParent.email_Address}}</p>
                {{else}}
                    <h4 style="color : red">Unassigned</h4>
            {{/if}}
        </td>
        <td class="success infotext">
            <h4><a href="farm.htm?growerId={{= id}}">View Farm Details</a></h4>
        </td>
    </tr>
</script>

<script type="text/x-jQuery-tmpl" id="profChildListTbodyTemplate">
    <tr class="success infotext">
        <td class="success infotext">{{= firstName}} {{= lastName}}</td>
        <td class="success infotext"><a href="farm.htm?growerId={{= id}}">View Farm</a></td>
    </tr>
</script>

<script type="text/x-jQuery-tmpl" id="studentListTemplate">
    <tr class="success infotext">
        <td class="success infotext">
            <h4 style="color : #337ab7; cursor : pointer" onclick="openUserDetailsPopup({{= id}}); return false;">{{= firstName}} {{= lastName}} ({{= role}})</h4>
        </td>
        <td class="success infotext">
            <h4><a href="farm.htm?growerId={{= id}}">View Farm Details</a></h4>
        </td>
    </tr>
</script>

<script type="text/x-jQuery-tmpl" id="selectOptionsTemplate">
    <option value="{{= id}}">{{= firstName}} {{= lastName}}</option>
</script>

<script type="text/x-jQuery-tmpl" id="professionalsForGrowerTemplate">
    <div class="form-group form-group1">

        <label class="growerSpecific">
            <h4 style="color : #337ab7">{{= firstName}} {{= lastName}} ({{= role}})</h4>
            <span>
                <input type="radio" name="professional" value="{{= id}}"
                    {{if currentUserParent == id}}
                        checked
                    {{/if}} > &nbsp;
                    Phone Number : {{= phone_No}}
            </span><br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <span>Email : {{= email_Address}}</span>
        </label>

    </div>
</script>