<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<script>
	var loginError = '<c:out value="${model.error}" />';
	var loginSuccess = '<c:out value="${model.success}" />';
	var userEmailId = '<c:out value="${model.userEmailAddress}" />';
	var userEncodedEmailId = '<c:out value="${model.userEncodeduserEmailAddress}" />';
</script>
<style>
	.panel-body{
		display: block;
	}
</style>


<div class="leftside">
	<navbar class="mainscreen-nav">
	<div class="nav">
		<div class="wrap clearfix">
			<ul class="nav navbar-nav list-inline text-center pull-left" id="index-menu-list-option">
				<li class="active"><a href="<c:url value="/"/>">Home</a></li>
				<li id="services_link" style="display: none"><a onclick="showServicesPage()">Services</a></li>
				<li id="benifits_link" style="display: none"><a onclick="showBenifitsPage()">Benefits</a></li>
				<li id="partnership_link" style="display: none"><a onclick="showPartnerShipPage()">Partnership</a></li>
				<li id="contactus_link" style="display: none"><a onclick="showContactUsPage()">Contact Us</a></li>
				<sec:authorize access="hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_PROFESSIONAL', 'ROLE_GROWER', 'ROLE_STUDENT')">
					<%--<li><a href="farm.htm">Click here to go inside</a></li>--%>
					<li><a href="agriculture/redirectUser">Click here to go inside</a></li>
				</sec:authorize>

			</ul>
		</div>
	</div>
	</navbar>

	<!-- Home page Main Section Start -->

	<div class="mainsection" style="padding-bottom: 20px;"
		id="home-page-main-section">
		<section>
			<div class="wrap clearfix">
				<div class="pull-left slider">
					<ul class="bxslider">
						<li><img src="<c:url value="/images/slider-4.jpg" />" /></li>
						<li><img src="<c:url value="/images/slider-3.jpg" />" /></li>
						<li><img src="<c:url value="/images/slider-2.jpg" />" /></li>
						<li><img src="<c:url value="/images/slider-1.jpg" />" /></li>
					</ul>
				</div>
				<!--end slider_wrapper-->
			</div>
		</section>
		<section>
			<div class="wrap clearfix">
				<div class="col-lg-12 col-sm-12 col-md-12 pull-left">
					<h2 class="welcm">Welcome</h2>
					<p class="abt" style="padding-right: 15px;">
						<span <%--class="ag"--%>>Planting Profits<sup>&reg;</sup></span> assists producers with
						critical crop production planning decisions.<br />
						<br /> Planting Profits<sup>&reg;</sup> helps producers decide how many acres of
						each crop to grow and which fields to plant which crops. Planting
						Profits is a new planning tool. There’s nothing else like.
						Planting Profits<sup>&reg;</sup> is not recordkeeping, but it turns your farm
						records into profit. Planting Profits<sup>&reg;</sup> will save you time, make you
						more money and help you to better manage farming risks. <br />
						<br /> Our innovative production planning software and on-line
						consulting service helps producers quickly develop and analyze
						production plans that maximize profit while managing marketing and
						production risks. It's way beyond recordkeeping. There's nothing
						else like it!<br />
						<br /> Our advanced farm planning software is available to you
						over the Internet. There is no software or hardware to buy or
						maintain.
					</p>
				</div>
			</div>
		</section>
		<section>
			<div class="wrap gap clearfix">
				<div class="col-lg-4 col-md-4 col-sm-4 pull-left padding-right-none">
					<img src="images/BOTTOMimg.jpg" class="img-responsive frame">
				</div>
				<div class="col-lg-8 col-md-8 col-sm-8">
					<div class="col-lg-12 col-md-12 col-sm-12">
						<p class="abt">
							<span class="product">Production planning software.</span><br />
							Use of our Planting Profits<sup>&reg;</sup> software is available to producers
							free of charge. We offer free customer support for quick calls
							and support packages for customers with greater support needs.
						</p>
					</div>
					<div class="col-lg-12 col-md-12 col-sm-12">
						<p class="abt">
							<span class="onlinefarm">On-line farm planning consulting
								service.</span><br /> We gather and enter required farm information
							into our Planting Profits<sup>&reg;</sup> software and work with you to develop
							alternative crop-acreage strategies.Then we work with you to
							analyze and compare strategies so you can select the one that is
							best for you.
						</p>
					</div>
				</div>
			</div>
		</section>
	</div>

	<!-- Home Page Main Section End -->

	<!-- Services Page Main Section Start -->

	<div class="mainsection" id="services-main-section">
		<div class="wrap clearfix">
			<section>

				<div class="page-heading">
					<div class="col-lg-12 col-md-12 col-sm-12">
						<h1>Services</h1>
					</div>
				</div>

			</section>
			<section>
				<div class="col-lg-6 col-md-6 col-sm-6 text-center">
					<div class="tree">
						<img src="images/tree.png" alt="image_tree">
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 pull-right">
					<div class="ourservice">Our service will:</div>
					<ul class="list-unstyled checktext">
						<li><i class="fa fa-check"></i> <span><strong>Identify</strong>
								the most profitable crop/field/acreage combinations based on
								your management objectives, resource limits and risk tolerance..</span></li>
						<li><i class="fa fa-check"></i> <span><strong>Determine</strong>
								how farm income can be increased by increasing or decreasing
								controllable decision variables such as operating capital,
								available acreage, minimum and maximum acreage limits, labor
								machinery, water or any other farm input</span></li>
						<li><i class="fa fa-check"></i> <span><strong>Analyze</strong>
								the potential effects of uncontrollable variables such as crop
								prices and yields.</span></li>
						<li><i class="fa fa-check"></i> <span><strong>Compare</strong>
								income, borrowing and resource needs and risk profiles given
								different combinations of crop mixes and risk management
								strategies, i.e. crop diversification, forward contracting, and
								crop insurance.</span></li>
					</ul>
				</div>

			</section>
		</div>
		<article>
			<div class="wrap clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 structuredmainframe">
					<div
						class="col-lg-12 col-md-12 col-sm-12 text-center structureheading">
						Using Planting Profits<sup>&reg;</sup> is an easy step-by-step process.<br> <a
							href="#">You can also work with one of our on-line consultant
							who will lead you through the planning process.</a>
					</div>
					<div class="col-lg-4 col-md-4 col-sm-12">
						<div class="structureheadingname text-center gatherinfo">
							Gather Farm Data<br /> & Information
						</div>
						<div class="structurecolor">
							<ul class="list-unstyled">
								<li>Identify Farm Goals</li>
								<li>Collect Farm Data</li>
								<li>Determine Scenarios</li>
							</ul>
						</div>
					</div>

					<div class="col-lg-4 col-md-4 col-sm-12">
						<div class="structureheadingname text-center Scenarios">
							Build Your Farm<br /> Model & Scenarios
						</div>
						<div class="structurecolor">
							<ul class="list-unstyled">
								<li>Input Farm Data</li>
								<li>Run Baseline Scenario & Additional Scenarios</li>
								<li>Provide Preliminary Results</li>
							</ul>
						</div>
					</div>

					<div class="col-lg-4 col-md-4 col-sm-12">
						<div class="structureheadingname text-center On-lineAnalysis ">
							Inter-active On-line<br /> Analysis
						</div>
						<div class="structurecolor">
							<ul class="list-unstyled">
								<li>Review Scenarios</li>
								<li>Run Additional Scenarios</li>
								<li>Deliver Final Production Plan</li>
								<li>Update the plan up until final planting decisions</li>
							</ul>
						</div>
					</div>

				</div>
			</div>
		</article>

	</div>
	<!-- Services Page Main Section End -->


	<!-- Benifits Page Main Section Start -->

	<div class="mainsection" id="benifits-main-section">
		<section>
			<div class="wrap clearfix">
				<div class="page-heading">
					<div class="col-lg-12 col-md-12 col-sm-12">
						<h1>Benefits</h1>
					</div>
				</div>
				<div class="col-lg-6 col-md-6 col-sm-6 pull-left">
					<div class="ourservice">Our service will:</div>
					<ul class="list-unstyled checktext">
						<li><i class="fa fa-check"></i><span> Identify the
								most profitable crop/field/acreage combinations Help you get the
								best terms on your operating loan by showing your lender a
								professional and detailed production plan that includes
								comprehensive risk analysis.</span></li>
						<li><i class="fa fa-check"></i><span> Save you time by
								providing the tools, the methods and the professional expertise
								to quickly assess all of your production alternatives.</span></li>
						<li><i class="fa fa-check"></i><span> Provide you
								peace of mind that the you've evaluated all possible crop
								production scenarios and selected the one that's best for you</span></li>
					</ul>
				</div>
				<div
					class="col-lg-6 col-md-6 col-sm-6 pull-right padding-right-none graph-img">
					<img src="images/graph.png" alt="graph" class="img-responsive">
				</div>
			</div>
		</section>

		<article>
			<div class="wrap clearfix">
				<div class="col-lg-12 col-md-12 col-sm-12 grow">
					<div
						class="col-lg-12 col-md-12 col-sm-12 text-left decisionheading">If
						you are a grower with some flexibility in your rotations, in less
						than 30 minutes Planting Profits<sup>&reg;</sup> can show you how to significantly
						increase your profitability and lower your risk.</div>
					<div class="col-lg-4 col-md-4 col-sm-4">
						<img src="images/work_graph.png" alt="work_graph"
							class="img-responsive">
					</div>
					<div class="col-lg-8 col-md-8 col-sm-8">
						<div class="decision">
							<div class="decisionsubheading">Here are some of the
								inter-related decisions our software and consulting service
								helps you make:</div>
							<ul class="list-unstyled text-left points">
								<li>What is the best allocation of crops to fields?</li>
								<li>How can I generate the most income with the least risk?</li>
								<li>How can diversification affect my production and market
									risk?</li>
								<li>How much production contracting should I commit to?</li>
								<li>Can I improve profitability by changing rotations?</li>
								<li>What is the 'best' type and amount of crop insurance
									given my risk profile?</li>
								<li>Should I rent additional land (or rent some of my land)
									and if so what should I pay, what should I plant and what is
									the impact on my labor, equipment, credit needs and risk
									profile?</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</article>
	</div>
	<!-- Benifits Page Main Section End -->

	<!-- Partenership Page Main Section Start -->
	<div class="mainsection" id="partenership-main-section">
		<div class="wrap clearfix">
			<section>
				<div class="page-heading">
					<div class="col-lg-12 col-md-12 col-sm-12">
						<h1>Partnerships</h1>
						<p>
							Planting Profits<sup>&reg;</sup> can be delivered by trained professionals. Your
							grower clients and farm managers will value the service.<br>
							We are looking for ag professionals with strong local knowledge
							in production agriculture.<br> We provide you with the
							tools, knowledge, training and support to succeed.
						</p>
					</div>
				</div>
				<div class="prof_incl gap">
					<div class="col-lg-3 col-sm-12 col-md-3 text-center">
						<div class="type_professional">
							<img src="<c:url value="/images/retailers.png" />">
							<p>Input supply retailers</p>
						</div>
					</div>
					<div class="col-lg-3 col-sm-12 col-md-3 text-center">
						<div class="type_professional">
							<img src="<c:url value="/images/consultants.png" />">
							<p>Independent crop consultants</p>
						</div>
					</div>
					<div class="col-lg-3 col-sm-12 col-md-3 text-center">
						<div class="type_professional">
							<img src="<c:url value="/images/advisors.png" />">
							<p>Farm management advisors</p>
						</div>
					</div>
					<div class="col-lg-3 col-sm-12 col-md-3 text-center">
						<div class="type_professional">
							<img src="<c:url value="/images/insurance_agents.png" />">
							<p>Crop insurance agents</p>
						</div>
					</div>
					<div class="col-lg-3 col-sm-12 col-md-3 text-center">
						<div class="type_professional">
							<img src="<c:url value="/images/lenders.png" />">
							<p>Ag lenders</p>
						</div>
					</div>
					<div class="col-lg-3 col-sm-12 col-md-3 text-center">
						<div class="type_professional">
							<img src="<c:url value="/images/Marketing-advisors.png" />">
							<p>Marketing advisors</p>
						</div>
					</div>
					<div class="col-lg-3 col-sm-12 col-md-3 text-center">
						<div class="type_professional">
							<img src="<c:url value="/images/dealers.png" />">
							<p>Equipment dealers</p>
						</div>
					</div>
					<div class="col-lg-3 col-sm-12 col-md-3 text-center">
						<div class="type_professional">
							<img src="<c:url value="/images/AgCPAs.png" />">
							<p>Ag CPAs</p>
						</div>
					</div>
				</div>
			</section>

			<section>
				<div
					class="professional_section col-lg-12 col-sm-12 col-md-12 professional_benifits">
					<h3>As a Planting Profits<sup>&reg;</sup>’ certified planning specialist, you
						can reap significant benefits</h3>
				</div>
				<div class="col-lg-4 col-sm-12 col-md-4 text-center">
					<div class="planning_specialist business">
						<span> &nbsp; </span>
						<h5>Synergy w/core business</h5>
						<p>Working with producers to develop Production Plans better
							positions you to sell core products and services.</p>
					</div>
				</div>
				<div class="col-lg-4 col-sm-12 col-md-4 text-center">
					<div class="planning_specialist relationship">
						<span> &nbsp; </span>
						<h5>Improved customer relationships</h5>
						<p>Assisting your best customers with production planning is a
							valuable service that can give you a strong competitive edge and
							rather than competing on price.</p>
					</div>
				</div>
				<div class="col-lg-4 col-sm-12 col-md-4 text-center">
					<div class="planning_specialist revenue">
						<span> &nbsp; </span>
						<h5>New revenue source</h5>
						<p>Offering Production Planning services generates additional
							revenue for your business; the planning period normally occurs in
							the off-season and provides revenue during slow times.</p>
					</div>
				</div>
			</section>
		</div>
	</div>

	<!-- Partenership Page Main Section End -->

	<!-- Contact Us Page Main Section Start -->
	<div class="mainsection" id="contact-us-main-section">
		<div class="wrap clearfix">
			<section>
				<div class="page-heading">
					<div class="col-lg-12 col-md-12 col-sm-12">
						<h1>Contact us</h1>
					</div>
				</div>
				<div class="contactus text-center">
					<p>
						Before you make your 2017 production decisions give us a call. <br>
						Our production planning software and service is revolutionary and
						unlike anything available. <br> It is likely to be one of the
						best investments you will ever make in your operation.
					</p>
					<h1>Give us a call or drop us a line today....</h1>
					<p>In just a couple of minutes we can explain how our software
						and service will help you develop the best production plan given
						this year's uncertainties.</p>
					<h1>One of the best tools you’ll ever use.</h1>
				</div>
			</section>


		</div>

	</div>

	<!-- Contact Us Page Main Section End -->

</div>

<aside>
	<!-- <div class="advertisement">
<h2>Advertisement Here</h2>
</div> -->
		<div class="right_side_add">
			<%--<ul>--%>
				<!-- <li><img style="width:250px;height: 230px;"  src="images/advertised_image/image1sliderhome.jpg"> </li>
<li><img style="width:250px;height: 230px;"  src="images/advertised_image/image2sliderhome.jpg"> </li>
-->
				<!--	@changed - Abhishek		@date - 12-02-2016		@desc - according to slide#1 of 02112016 -->
				<%--<li><img src="images/advertised_image/image1.jpg"></li>--%>
				<%--<li><h3>Sponsored By</h3></li>
				<li><h1>Your Logo</h1></li>
				<li><h1>Your Logo</h1></li>
				<li><h3>Please thank our Sponsors</h3></li>--%>
				<div class="adver-margin-top"><h3>Sponsored By</h3></div>
				<div class="adver-margin-middile"><h1>Your Logo</h1></div>
				<div class="adver-margin-bottom"><h4>Please click here to thank our Sponsorss</h4></div>

			<%--</ul>--%>
		</div>

</aside>

<!-- Popup Div Starts Here -->
<div id="abc">
	<div id="popupContact">
		<!-- Contact Us Form -->
		<div class="popup_section">
			<img id="close" src="images/cross.png" onclick="div_hide()">
			<h2 class="popupheader">Login</h2>
			<form id="user-login-form" action="j_spring_security_check"
				  accept-charset="UTF-8" method="post">
				<div class="popupform">
					<div class="form-group" id="login-validation-error-msg"></div>
					<div class="form-group">
						<label>Email</label> <input type="text" id="user-email-id"
													name="j_username" placeholder="Email" class="form-control" />
					</div>
					<div class="form-group">
						<label>Password</label> <input type="password"
													   class="form-control" id="user-password" placeholder="Password"
													   name="j_password" />
					</div>
					<!-- <div class="checkbox">
						<label>
						<input type="checkbox" value="remember-me">
						Remember me
						</label>
						</div> -->
					<div class="yellobtn submit">
						<a id="submit" onclick="submitLoginForm()">Login</a>
					</div>
				</div>
			</form>
			<hr>
			<a class="redlink fgt_pwd" onclick="div_hide();div_show7();">Forgot
				Password ?</a> <a class="redlink signin"
								  onclick="div_hide();div_show2();">Sign Up</a>
		</div>
	</div>
</div>
<!-- Popup Div Ends Here -->

<!-- Popup Div Starts Here register -->
<div id="register">
	<div id="popupRegistration">
		<!-- Contact Us Form -->
		<div class="popup_section">
			<img id="close" src="images/cross.png" onclick="div_hide2()">
			<div class="popupform popupformforRegistration"
				 style="max-height: 450px; overflow-y: auto;">
				<div class="row">
					<div class="col-lg-12">
						<div class="panel panel-default">
							<div class="panel-heading text-center">Create Your Account</div>
							<!-- .panel-heading -->
							<div class="panel-body">
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
													<select name="user-account-type" id="user-account-type"
														class="form-control user-account-type">
													<option>--Select Account Type--</option>
													<option>Grower</option>
													<!-- <option>Consultancy</option>
														<option>Consultant</option>
														<option>Sponsor</option> -->
												</select>
												</div>
												<div class="form-group form-group1">
													<label>First Name<span class="strickColor">*</span></label>
													<input type="text" class="form-control first-name"
														   id="first-name"
														   placeholder="First-Name" />
												</div>
												<div class="form-group form-group1">
													<label>Last Name<span class="strickColor">*</span></label>
													<input type="text" class="form-control last-name"
														   id="last-name" placeholder="Last-Name" />
												</div>
												<div class="form-group form-group1">
													<label>Contact No<span class="strickColor">*</span></label>
													<input type="text" class="form-control contact-no"
															   id="contact-no"
															   placeholder="Contact number"
															   onkeypress="return isValidNumberValue(event)" />
												</div>
												<div class="form-group form-group1">
													<label>Email id<span class="strickColor">*</span></label>
													<input type="text" class="form-control email-id"
															   id="email-id"
															   placeholder="Email address" />
												</div>
											</div>
										</div>
									</div>

									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">
												<a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" class="collapsed" aria-expanded="false">Physical address info<span class="strickColor">*</span></a>
											</h4>
										</div>
										<div id="collapseThree" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
											<div class="panel-body">
												<div class="form-group form-group1">
													<label>Physical Address Line1</label>
													<input type="text"
														   class="form-control physical-address-line1" id="physical-address-line1"
														   placeholder="Physical Address Line1" />
												</div>
												<div class="form-group form-group1">
													<label>Physical Address Line2</label>
													<input type="text"
														   class="form-control physical-address-line2" id="physical-address-line2"
														   placeholder="Physical Address Line2" />
												</div>
												<div class="form-group form-group1">

													<%--	@changed - Abhishek		@date - 08-12-2015	--%>
													<label>Physical Address Country<span class="strickColor">*</span></label>

													<%--<input type="text" class="form-control" id="physical-address-country" placeholder="Physical Address Country" />--%>
													<%--<select class="form-control physical-address-country" id="physical-address-country">
														<option value="" selected>Select Country</option>
														<c:forEach var="countryCodes" items="${model.countryAndCodes}">
															<option value="${countryCodes.countryCode}">${countryCodes.countryName}</option>
														</c:forEach>
													</select>--%>
													<select class="form-control physical-address-country"
															id="physical-address-country"
															onchange="getStatesForCountry('#collapseThree', 'physical')">
														<option value="" selected>Select Country</option>
														<c:forEach var="countryCodes" items="${model.countryAndCodes}">
															<option value="${countryCodes.id}">${countryCodes.countryName}</option>
														</c:forEach>
													</select>

												</div>
												<div class="form-group form-group1">
													<label>Physical Address State</label>
													<%--<input type="text"
														   class="form-control" id="physical-address-state"
														   placeholder="Physical Address State" />--%>
													<select class="form-control physical-address-state"
															id="physical-address-state">
														<option value="" selected>Select Country First</option>
													</select>
												</div>
												<div class="form-group form-group1">
													<label>Physical Address City/Town</label>
													<input type="text"
														   class="form-control physical-address-city" id="physical-address-city"
														   placeholder="Physical Address City/Town" />
												</div>
												<div class="form-group form-group1">
													<label>Physical Zip<span class="strickColor">*</span></label>
													<input type="text"
														   class="form-control physical-zip"
														   id="physical-zip" placeholder="Physical Zip" />
												</div>
											</div>
										</div>
									</div>

									<div class="panel panel-default">
										<div class="panel-heading">
											<h4 class="panel-title">
												<a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" class="collapsed" aria-expanded="false">
													Mailing address info
												</a>
												<label class="pull-right">
													<input type="checkbox"
														   name="physicalSameCheckBox"
														   onchange="physicalAddressSameForMailing('#accordion'); return false;"/>
													Same as Physical Address
												</label>
											</h4>
										</div>
										<div id="collapseTwo" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
											<div class="panel-body">
												<div class="form-group form-group1">
													<label>Mailing Address Line1</label>
													<input type="text"
														   class="form-control mailing-address-line1" id="mailing-address-line1"
														   placeholder="Mailing Address Line1" />
												</div>
												<div class="form-group form-group1">
													<label>Mailing Address Line2</label>
													<input type="text"
														   class="form-control mailing-address-line2" id="mailing-address-line2"
														   placeholder="Mailing Address Line2" />
												</div>
												<div class="form-group form-group1">
													<label>Mailing Address Country</label>
													<%--<input type="text"
														   class="form-control" id="mailing-address-country"
														   placeholder="Mailing Address Country" />--%>

													<%--<select class="form-control mailing-address-country" id="mailing-address-country">
														<option value="" selected>Select Country</option>
														<c:forEach var="countryCodes" items="${model.countryAndCodes}">
															<option value="${countryCodes.countryCode}">${countryCodes.countryName}</option>
														</c:forEach>
													</select>--%>
													<select class="form-control mailing-address-country"
															id="mailing-address-country"
															onchange="getStatesForCountry('#collapseTwo', 'mailing')">
														<option value="" selected>Select Country</option>
														<c:forEach var="countryCodes" items="${model.countryAndCodes}">
															<option value="${countryCodes.id}">${countryCodes.countryName}</option>
														</c:forEach>
													</select>
												</div>
												<div class="form-group form-group1">
													<label>Mailing Address State</label>
													<%--<input type="text"
														   class="form-control" id="mailing-address-state"
														   placeholder="Mailing Address State" />--%>
													<select class="form-control mailing-address-state"
															id="mailing-address-state">
														<option value="" selected>Select Country First</option>
													</select>
												</div>
												<div class="form-group form-group1">
													<label>Mailing Address City/Town</label>
													<input type="text"
														   class="form-control mailing-address-city" id="mailing-address-city"
														   placeholder="Mailing Address City/Town" />
												</div>
												<div class="form-group form-group1">
													<label>Mailing Zip</label>
													<input type="text" class="form-control mailing-zip"
														   id="mailing-zip" placeholder="Mailing Zip" />
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
				</div>

				<div class="clr"></div>

				<div class="row">
					<div class="col-lg-12 col-md-12 col-sm-12">
						<h4 class="col-lg-12 col-md-12 col-sm-12">AWS Customer Agreement</h4>
						<label>
							<span class="col-lg-1 col-md-1 col-sm-1 text-center padding-right-0">
								<input type="checkbox" name="license-agreement-checkbox"/>
							</span>
							<p class="col-lg-11 col-md-11 col-sm-11 text-left padding-left-0">
								Check here to indicate that you have read and agreed to the terms of the
								<a href="<c:url value="/license-agreement.htm"/>" target="_blank">AWS Customer Agreement</a>
							</p>
						</label>
					</div>
				</div>

				<div class="text-center">
						<button class="alertify-button alertify-button-ok col-lg-12 col-md-12 col-sm-12"
								onclick="registerUser()">Create Account and Continue</button>
				</div>
				<div class="row col-lg-12 col-md-12 col-sm-12 ready text-center margin-top-2">
					Already have an account ?
					<span class="log"><a onclick="div_hide2();div_show();">Login Here</a> </span>
				</div>
				<%--<div class="ready readyRegistration">
					Already have an account ?
					<span class="log"><a onclick="div_hide2();div_show();">Login Here</a> </span>
				</div>--%>
				<%--<div class="yellobtn submit yellobtnRegistration">
					<a id="submit" onclick="registerUser()">Register</a>
				</div>--%>
			</div>

		</div>
	</div>
</div>
<!-- Popup Div Ends Here Register -->

<!-- Popup Div Start Here Forget Password -->
<div id="forgetPassword">
	<div id="popupContact">
		<!-- Contact Us Form -->
		<div class="popup_section">
			<img onclick="div_hide7()" src="images/cross.png" id="close">
			<h2 class="popupheader">Password Recovery</h2>

			<div class="popupform">

				<div class="form-group">
					<label>Email</label> <input type="text" class="form-control"
												placeholder="Email" id="user-email-id-password-recovery">
				</div>

				<div class="yellobtn submit">
					<a onclick="recoverPassword()">Recover</a>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Popup Div End Here Forget Password -->

<!-- Popup Div Start Here Forget Password -->
<div id="AccountRecoveryPopUp">
	<div id="popupContact">
		<!-- Contact Us Form -->
		<div class="popup_section">
			<img onclick="div_hide8()" src="images/cross.png" id="close">
			<h2 class="popupheader">Account Recovery</h2>

			<div class="popupform">

				<div class="form-group">
					<label>Email</label> <input type="hidden"
												id="userEncodeduserEmailAddress" /> <input type="text"
																						   class="form-control" disabled="disabled" id="userEmailAddress" />
				</div>
				<div class="form-group">
					<label>Password</label> <input type="password" class="form-control"
												   placeholder="Password" id="userPassword" />
				</div>
				<div class="form-group">
					<label>Confirm Password</label> <input type="password"
														   class="form-control" placeholder="Confirm Password"
														   id="userConfirmPassword" />
				</div>
				<div class="yellobtn submit">
					<a onclick="updateUserPassword()">Reset</a>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- Popup Div End Here Forget Password -->


<script type="text/javascript" src="<c:url value="/js/agriculture/user-registration.js" />"></script>

<script type="text/javascript" src="<c:url value="/js/agriculture/home.js" />"></script>

<script>
    if(location.search === '?loginToPrivate=yes'){
        document.getElementById('popup').click();
    }
</script>