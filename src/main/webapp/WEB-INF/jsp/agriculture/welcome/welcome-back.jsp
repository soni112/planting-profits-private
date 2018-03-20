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
        <aside class="left-body">
            <article class="left-nav-sponsers p-r-0">
                <div class="left-nav-info" style="overflow-y: auto">
                    <%--<h2 class="weight-600 text-center">Grower</h2>--%>
                    <div class="clearfix"></div>
                    <ul class="col-lg-12 col-offset-1 col-md-12 col-offset-1 list-unstyled pt-10">
                        <li>
                            <span class="farm-info">${model.userdetail.name}</span>
                            <span class="farm-info">${model.userdetail.location}</span>
                            <span class="farm-info">Number of Farms : ${model.userdetail.farmcount}</span>
                            <span class="farm-info">Last activity: ${model.userdetail.lastactivity}</span>
                            <span class="farm-info">${model.userdetail.contribution}</span>
                        </li>
                    </ul>

                </div><!-- /.Grower -->
            </article>

            <article class="left-nav-sponsers p-r-0">
                <div class="left-nav-info">
                    <h2 class="col-lg-12 col-md-12  text-left">Latest News</h2>
                </div><!-- /.Latest News -->
            </article>

            <article class="left-nav-sponsers p-r-0">
                <div class="left-nav-info">
                    <h2 class="col-lg-12 col-md-12  text-left">Local Farm Data</h2>
                </div><!-- /.Latest News -->
            </article>
        </aside>
        <!-- Main body -->
        <div class="leftside common-bg main-body">
            <section class="body-content-middile margin-top-3 welcomeBackUser">
                <div class="m-b-2">
                    <h1 class="weight-700 text-center text-white">Welcome Back!</h1>
                    <div class="col-lg-12 col-md-12 col-sm-12 text-center">
                        <a class="alertify-button alertify-button-ok remove-text-deco btn-enter" href="<c:url value="/farm.htm"/>" >Enter</a>
                    </div>
                </div><!-- /.col-12 -->
            </section>
        </div>


        <%--<%@ include file="../manage-farm/common/right_slider.jsp" %>--%>
        <aside>
            <!-- <div class="advertisement">
        <h2>Advertisement Here</h2>
        </div> -->
            <div class="right_side_add right-body">
                <article class="welcome-right-nav-sponsers p-r-0">
                    <div class="welcome-left-nav-info">
                        <div class="adver-margin-top text-left"><h3>Today&#39;s Sponsor</h3></div>
                    </div><!-- /.Latest News -->
                </article>
                <article class="welcome-right-nav-sponsers p-r-0">
                    <div class="welcome-left-nav-info">
                        <div class="adver-margin-top"><h3 style="font-size: 17px">Offers; page specific (crop info, resources, forward sales)</h3></div>

                    </div><!-- /.Latest News -->
                </article>
                <article class="welcome-right-nav-sponsers p-r-0">
                    <div class="welcome-left-nav-info">
                        <div class="adver-margin-top"><h3>&lt Ad Sense Ad &gt</h3></div>
                    </div><!-- /.Latest News -->
                </article>
                <article class="welcome-right-nav-sponsers p-r-0">
                    <div class="welcome-left-nav-info">
                        <%--<div class="adver-margin-top"><h3>Today&#39;s Sponsor</h3></div>--%>
                            <div class="adver-margin-top"><h3>&lt Ad Sense Ad &gt</h3></div>
                    </div><!-- /.Latest News -->
                </article>
            </div>

        </aside>
    </div>
</div>

<%--
<script type="text/javascript">
    $(function(){
        $('body').addClass('common-bg');
    })
</script>--%>
