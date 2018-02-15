<%--
  Created by IntelliJ IDEA.
  User: abhishek
  Date: 3/2/18
  Time: 12:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<link rel="stylesheet" href="<c:url value="/css/sb-admin-2.css"/>" type="text/css" media="all">

<div class="container-fluid">
    <div class="row">
        <div class="leftside common-bg">
                <section class="body-content-middile margin-top-3 welcomeBackUser">
                    <div class="m-b-2">
                        <h1 class="weight-700 text-center" style="color: #1f2836">
                            <em>Welcome Back!<br/><a href="<c:url value="/farm.htm"/>" style="text-decoration: underline; font-size: 38px">Click here to go inside...</a></em>
                        </h1>
                    </div><!-- /.col-12 -->

                    <aside class="left-nav-sponsers p-r-0">
                        <div class="left-nav-info" style="overflow-y: auto">
                            <h2 class="weight-600 text-center">Grower</h2>
                            <ul style="float: none;list-style: none">
                                <c:forEach var="farmdetails" items="${model.allFarmsForUser}">
                                    <li>
                                        <a href="<c:url value="/output-edit-farm-info.htm?farmId=${farmdetails.farmId}"/>">
                                            Farm : ${farmdetails.farmName} / ${farmdetails.physicalLocation}
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>

                        </div><!-- /.Grower -->

                    </aside>

                    <aside class="left-nav-sponsers p-r-0" style="top: 55%;">
                        <div class="left-nav-info">
                            <h2 class="weight-600 text-center">Latest News</h2>
                        </div><!-- /.Latest News -->
                    </aside>

                </section>

        </div>


        <%@ include file="../manage-farm/common/right_slider.jsp" %>
    </div>
</div>

<%--
<script type="text/javascript">
    $(function(){
        $('body').addClass('common-bg');
    })
</script>--%>
