<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<script>
    $(document).ready(function () {
        /*$(".dropdown_toggle").hide();*/
        $("#show_changepwd").click(function () {
            $(".dropdown_toggle").toggle();
        });
        $("#show_changepwd").click(function (e) {
            e.stopPropagation(); // This is the preferred method.
            return false;        // This should not be used unless you do not want
                                 // any click events registering inside the div
        });
        $(document).click(function () {
            $(".dropdown_toggle").hide();
        });
    });

</script>
<header>
    <div class="topbar">
        <div class="wrap clearfix">
            <div class="col-lg-12 col-md-12 col-sm-12">
                <div class="logo col-lg-4 col-md-4 col-sm-6 col-xs-5">
                    <a href="<c:url value="/home.htm"/>" class="pull-left">
                        <img src="<c:url value="/images/logo.png"/> ">
                    </a>
                    <span style="margin-left: 85px"><b>Patent Pending</b></span>
                </div>
                <div class="">

                    <ul id="header-user-nav" class="list-inline login top_head_nav pull-right">
                        <sec:authorize access="isAnonymous()">
                            <li><a id="popup" onclick="div_show()">Login</a></li>
                            <li><a id="popup" onclick="div_show2()">Register</a></li>
                        </sec:authorize>
                        <sec:authorize access="isAuthenticated()">
                            <sec:authentication var="principal" property="principal"/>
                            <li><a id="show_changepwd"> ${principal.name} <span class="caret">&nbsp;</span></a>
                                <ul class="dropdown_toggle">
                                    <li><a onclick="div_show9()"> Change Password</a></li>
                                    <%--<li><a href="<c:url value="/j_spring_security_logout" />">Logout</a></li>--%>
                                    <li><a href="#" onclick="navigateToContributionPage('<c:url value="/contribution.htm"/>')">Logout</a></li>
                                </ul>

                            </li>


                        </sec:authorize>
                        <%-- <sec:authorize
                            access="hasAnyRole( 'USER' )">
                            <li>Welcome : ${model.userName}</li>
                            <li><a href="j_spring_security_logout">Logout</a></li>
                        </sec:authorize> --%>
                        <%-- <sec:authorize
                            access="!hasAnyRole( 'USER')">
                            <li><a id="popup" onclick="div_show()">Login</a></li>
                            <li><a id="popup" onclick="div_show2()">Register</a></li>
                        </sec:authorize> --%>


                    </ul>

                    <sec:authorize access="isAuthenticated()">
                        <a class="alertify-button alertify-button-ok pull-right remove-text-deco"
                           id="header-donation-button"
                           style="margin: 24px 10px; font-weight: 600;"
                           href="<c:url value="/contribution.htm"/> "
                            <%--onclick="navigateToContributionPage('<c:url value="/contribution.htm"/>')"--%>
                            >Make a contribution</a>
                    </sec:authorize>

                </div>
            </div>
        </div>
    </div>
    <div id="loadingImage">
        <img alt="Processing..." src="<c:url value="/images/planting_profit_loading.gif" />" style="margin-left: 47%;margin-top: 18%;">
    </div>
    <div id="loadingImageForStrategy">
        <img alt="Processing..." src="<c:url value="/images/planting_profit_loading.gif" />" style="margin-left: 47%;margin-top: 18%;">
        <p class="strategy-loading-content">
            <span class="strategy-loading-content" id="loading-strategy-content" >Generating strategy…</span>
            <br/>
            <span class="strategy-loading-content">Please be patient.</span>
        </p>
        <div id="stopAjaxRequestDiv" style="text-align: center;"></div>
    </div>

</header>
<%@ include file="change-password.jsp" %>