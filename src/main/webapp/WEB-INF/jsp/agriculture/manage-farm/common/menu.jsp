
<c:set value="${model.farm.farmId}" var="farmId"/>
<navbar class="nav_after_login">
<div class="nav">
	<div class="wrap clearfix">
		<div class="col-lg-4 col-sm-4">
			<ul class="nav navbar-nav list-inline text-center pull-left" id="upperNavigationBar">
				<li class="active" id="menu-farm"><a href="<c:url value="/farm.htm"/>">Farms</a></li>
				<%--<li id="menu-review"><a>Review</a></li>--%>
				<%--<li id="menu-analysis"><a>Analysis</a></li>			--%>
				<%--<li id="menu-strategies"><a href="view-farm-strategy.htm">Strategies</a></li>--%>
				<li id="menu-strategies"><a href="javascript:;">Strategies</a></li>
				<li id="menu-scenarios"><a href="<c:url value="/view-farm-scenarios.htm?farmId="/>${farmId}">Scenarios</a></li>
				<sec:authorize access="hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_PROFESSIONAL', 'ROLE_GROWER')">
					<li id="menu-management" style="display: none"><a href="management.htm">Account Management</a></li>
				</sec:authorize>
			</ul>
		</div>
		<div class="col-lg-4 col-sm-4 text-center">
			<span id="header-farm-name" class="header-farm-name"></span>
		</div>
		<div class="col-lg-4 col-sm-4">

		</div>

	</div>
</div>
</navbar>

<!-- 
  @added - Abhishek
  @date - 25-11-2015
  -->
<script type="text/javascript">
$(function(){

	$("#menu-strategies a").click(function(){
		$.ajax({
			url : "<c:url value="/ajaxRequest/checkCustomStrategyForUser"/>",
			data :{
				farmId : '${farmId}'
			},
			type : "get",
			beforeSend: showLoadingImage(),
			success : function(response){
				if(response.status == "failed"){
					customAlerts("You have not saved any custom strategies for any farm", "warning", 0);
				} else if(response.status == "success") {
					window.location = '<c:out value="${pageContext.servletContext.contextPath}" />/view-farm-strategy.htm?farmId=${farmId}';
				}
			},
			error : function(response){
				customAlerts("Error while getting status for custom strategy", "error", 0);
				hideLoadingImage();
			},
			complete: function () {
//                hideLoadingImage();
            }
		});
	});
})

</script>
