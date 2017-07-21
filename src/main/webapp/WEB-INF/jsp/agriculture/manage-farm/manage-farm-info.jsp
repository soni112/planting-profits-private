<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<link href="<c:url value="/css/bootstrap-multiselect.css"/>" rel="stylesheet"
	type="text/css" />
<script>
	showLoadingImage();
	var farmId = '<c:out value="${model.farm.farmId}" />';
	var farmName = '<c:out value="${model.farm.farmName}" />';
	$(document).ready(function() {
		$('#header-farm-name').html(farmName);
		addActiveClass($("#menu-farm"));
	});
	var manageStep1 = false;
	var manageStep2 = false;
	var manageStep3 = false;
	var manageStep4 = false;
	var manageStep5 = false;
	var manageStep6 = false;
	var oldStrategy = "";
	var strategy = "";
	var availableLand = 0;
	var globalGroupArray = new Array();
</script>
<!-- <script src="js/common/customAlertMessages.js" type="text/javascript"></script> -->
<script src="<c:url value="/js/agriculture/farm/manage_farm.js?v=0.1" />" type="text/javascript"></script>
<script src="<c:url value="/js/agriculture/farm-info-slider.js" />" type="text/javascript"></script>
<script src="<c:url value="/js/plugins/bootstrap-multiselect.js" />" type="text/javascript"></script>
<div class="leftside">
	<%@ include file="common/menu.jsp"%>
	<div class="mainsection farm_section farm_innerpages">
		<section>
			<div class="wrap clearfix">
				<%@ include file="common/left_slider.jsp"%>
				<div
					class="col-lg-9 col-sm-9 col-md-9 padding-right-none padding-left-none">
					<div class="right_farm_form_filled">
						<div class="progress_bar">
							<img id="image_bar" src="images/progress_bar/progress-bar1.png">
						</div>
						<div id="farm-info" class="show_hide_class visited">
							<div class="form_area">
								<!-- @changed - Abhishek @date - 30-12-2015 -->
								<%--<h2 class="form-heading">Farm Information</h2>--%>
								<h2 class="form-heading">Planning Mode</h2>
								<div class="ques">
									<div class="col-lg-4 col-md-12 col-sm-12 padding-left-none">
										<span class="filled_question">Plan By:</span>
									</div>
									<div
										class="col-lg-2 col-md-3 col-sm-3 padding-left-none option_selection">
										<label> <input type="radio" name="plan_by_farm" class="strategy" value="fields"><span>Fields</span></label>
									</div>
									<div class="col-lg-2 col-md-3 col-sm-3 option_selection">
										<label> <input type="radio" name="plan_by_farm" class="strategy" value="acres"><span>Acres</span></label>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-4 option_selection">
										<!-- <label> <input type="radio" name="plan_by_farm"
											class="strategy" value="both"><span>Both</span></label> -->
									</div>
									<div class="col-lg-1 col-md-2 col-sm-2 option_selection">
										<a id="farm_Information_planBy" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a>
									</div>
								</div>
								<div class="clearfix"></div>
								<%--	@Abhishek	@date - 20-01-2016	@desc - applied hidden class as per client request--%>
								<%--<div class="ques">--%>
								<div class="ques hidden">
									<div class="col-lg-4 col-md-12 col-sm-12 padding-left-none">
										<span class="filled_question">Do you irrigate any of your land? </span>
									</div>
									<div
										class="col-lg-2 col-md-3 col-sm-2 padding-left-none option_selection">
										<label> <input type="radio" name="irrigate" value="no"><span>No</span></label>
									</div>
									<div class="col-lg-2 col-md-3 col-sm-3 option_selection">
										<label> <input type="radio" name="irrigate"
											value="yes"><span>Yes</span></label>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-5 option_selection">
										<!-- <label> <input type="radio" name="irrigate"
											value="sometimes"><span>Sometimes</span></label> -->
									</div>
									<div class="col-lg-1 col-md-2 col-sm-2 option_selection">
										<a id="farm_Information_irrigate" class="help_Infromation_PopUp" href="#">
										<img src="images/i-icon.png"></a>
									</div>
								</div>
								<!-- start by rohit on 29-04-15 -->
								<div class="clearfix"></div>
								<%--	@Abhishek	@date - 20-01-2016	@desc - applied hidden class as per client request--%>
								<%--<div class="ques">--%>
								<div class="ques hidden">
									<div class="col-lg-4 col-md-12 col-sm-12 padding-left-none">
										<span class="filled_question">Do you have on-farm storage? </span>
									</div>
									<div class="col-lg-2 col-md-3 col-sm-3 padding-left-none option_selection">
										<label> <input type="radio"
											name="evaluate_storage_sales" value="false"><span>No</span></label>
									</div>
									<div class="col-lg-2 col-md-3 col-sm-3 option_selection">
										<label> <input type="radio"
											name="evaluate_storage_sales" value="true"><span>Yes</span></label>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-4 option_selection">
										<label></label>
									</div>
									<div class="col-lg-1 col-md-2 col-sm-2 option_selection">
										<a id="farm_Information_storage"
											class="help_Infromation_PopUp" href="#"><img
											src="images/i-icon.png"></a>
									</div>
								</div>
								<!-- end by rohit on 29-04-15 -->
								<div class="clearfix"></div>
								<%--	@Abhishek	@date - 20-01-2016	@desc - applied hidden class as per client request--%>
								<%--<div class="ques">--%>
								<div class="ques hidden">
									<div class="col-lg-4 col-md-12 col-sm-12 padding-left-none">
										<span class="filled_question">Would you like to evaluate forward sales?</span>
									</div>
									<div
										class="col-lg-2 col-md-3 col-sm-3 padding-left-none option_selection">
										<label> <input type="radio"
											name="evaluate_forward_sales" value="false"><span>No</span></label>
									</div>
									<div class="col-lg-2 col-md-3 col-sm-3 option_selection">
										<label> <input type="radio"
											name="evaluate_forward_sales" value="true"><span>Yes</span></label>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-4 option_selection">
										<label></label>
									</div>
									<div class="col-lg-1 col-md-2 col-sm-2 option_selection">
										<a id="farm_Information_forward"
											class="help_Infromation_PopUp" href="#"><img
											src="images/i-icon.png"></a>
									</div>
								</div>
								<div class="clearfix"></div>
								<%--	@Abhishek	@date - 20-01-2016	@desc - applied hidden class as per client request--%>
								<%--<div class="ques">--%>
								<div class="ques hidden">
									<div class="col-lg-4 col-md-12 col-sm-12 padding-left-none">
										<span class="filled_question">Would you like to evaluate crop insurance?</span>
									</div>
									<div
										class="col-lg-2 col-md-3 col-sm-3  padding-left-none option_selection">
										<label> <input type="radio" name="evaluate_crop_insurance" value="false"><span>No</span></label>
									</div>
									<div class="col-lg-2 col-md-3 col-sm-3 option_selection">
										<label> <input type="radio" name="evaluate_crop_insurance" value="true"><span>Yes</span></label>
									</div>
									<div class="col-lg-3 col-md-4 col-sm-4 option_selection">
										<label></label>
									</div>
									<div class="col-lg-1 col-md-2 col-sm-2 option_selection">
										<a id="farm_Information_insurance" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a>
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="yellobtn pre_next">
									<a id="submit popup" onclick="nextFarmInformation()"> <!-- onclick="div_show3()" -->Next
									</a>
								</div>
								<!-- <div class="yellobtn pre_next">
<a id="submit" href="#">Previous</a>
</div> -->
							</div>
							<!-- Popup Div Starts Here -->
							<div id="select-strategies-pop-up"
								class="common-pop-class-for-all">
								<div id="popupContact">
									<!-- Planning Form -->
									<div class="popup_section">
										<div class="popupform messagepopup">
											<p class="planningtext">You indicated that you would like
												to plan by both fields and total acres (i.e. without regard
												to fields). To plan by fields you will need to enter
												information on your farm's fields.</p>
											<p>Would you like to begin planning by fields or acres?</p>
											<label> <input type="radio" name="plan_by_farm_name"
												value="fields"><span class="planninghead">Fields</span></label>
											<label> <input type="radio" name="plan_by_farm_name"
												value="acres"><span class="planninghead">Acres</span></label>
											<div class="clearfix">
												<div class="yellobtn submit okbutton">
													<a id="submit" onclick="selectStrategyInCaseOfBoth()">
														<!-- onclick="showMyNextPage()" -->Ok
													</a>
												</div>
											</div>

										</div>
									</div>
								</div>

							</div>
							<!-- Popup Div Ends Here -->
						</div>
						<!-- @end #farm-info -->
						<div id="plan_by_fields" class="show_hide_class hidden">
							<div class="form_area">
								<h2 class="field-heading">
									<%--Plan by Fields --%>
									Field Information
									<a id="Plan_by_Fields" class="help_Infromation_PopUp" href="#">
										<img src="images/i-icon.png">
									</a>
								<span id="land_acres_planningbyfield"></span>
								</h2>
								<div class="clearfix"></div>
								<div style="margin-bottom: 8px;" class="all_buttons">
									<div class="top_buttons addremove-field">
										<a onclick="div_show4ForAddField()"><img
											src="images/add_field.png"></a>
									</div>
									<div class="top_buttons addremove-field">
										<a onclick="modifyExistingField()"><img
											src="images/modify_field.png"></a>
									</div>
									<div class="top_buttons addremove-field padding-left-none">
										<a onclick="removeField()"><img src="images/remove.png"></a>
									</div>
								</div>
								<div class="clearfix"></div>


								<div class="ques">
									<div class="table-responsive" <%--style="max-height: 260px;"--%>>
										<table id="Plan_by_Fields_table"
											class="table table-striped tbl-bordr  tblbrdr tbl-fixd-hdr"
											cellspacing="0" width="100%">
											<thead class="tbl-header-scrolll-width">
												<tr class="tblhd text-center add-fieldi">
													<td>Modify</td>
													<td class="tblbrdr text-center add-fieldi">Name</td>
													<td class="text-center add-fieldi">Size<br>(In Acres)</td>
													<td class="text-center">Last Crop <a
														id="Plan_by_Fields_Last_Crop"
														class="help_Infromation_PopUp" href="#"><span
															class="add-fieldi"><img src="images/i-img.png"></a></span></td>
													<td class="text-center">Fallow <a
														id="Plan_by_Fields_Fallow" class="help_Infromation_PopUp"
														href="#"><span class="add-fieldi"><img
																src="images/i-img.png"></a></span></td>
													<td class="text-center">Divide <a
														id="Plan_by_Fields_Divide" class="help_Infromation_PopUp"
														href="#"><span class="add-fieldi"><img
																src="images/i-img.png"></a></span></td>
													<td class="text-center">Irrigate <a
														id="Plan_by_Fields_Irrigate"
														class="help_Infromation_PopUp" href="#"><span
															class="add-fieldi"><img src="images/i-img.png"></a></span></td>
												</tr>
											</thead>
											<tbody></tbody>
											<tfoot>
												<tr id="total-field-last-row" class="tblft text-center">
													<td class="tblft1">Total acres</td>
													<td style="text-align: left" colspan="6"
														id="total-acres-value">0</td>
												</tr>
											</tfoot>
										
										</table>
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="yellobtn pre_next">
									<a id="submit popup" onclick="nextPlanByField()"> <!-- onclick="showMyNextPage1()" -->Next
									</a>
								</div>
								<div class="yellobtn pre_next">
									<a id="submit" onclick="callMethodForPageChangeAndProgressBarImage(3,4)">Previous</a>
								</div>
								<div id="add-new-crop-pop-up" class="common-pop-class-for-all">
									<div id="popupContact">
										<!-- Planning Form -->
										<div class="popup_section">
											<img onclick="div_hide4()" src="images/cross.png" id="close">
											<h2
												style="padding: 0 0 0 22px; color: #BE922F; font-weight: bold;"
												class="popupheadercrop">
												<span id="add-field-span-dynamic">Add Field</span>
											</h2>
											<div style="padding: 5px 20px;"
												class="popupform messagepopup">
												<div class="clearfix"></div>
												<div class="addcroppup">
													<label>Field Name</label> <input type="text"
														id="pop-up-field-name" />
												</div>
												<div class="addcroppup">
													<label>Field Size (In Acres)</label> <input type="text"
														id="pop-up-field-size"
														onchange="addCommaSignWithOutDollarDot(this)"
														onkeypress="return isValidNumberValueForWithOutDot(event)" />
												</div>
												<div class="clearfix"></div>
												<div style="margin: 10px 0px;" class="yellobtn submit"
													id="add_field_button">
													<a id="submit" onclick="addNewField()">Add Field</a>
												</div>
												<div style="margin: 10px 0px;" class="yellobtn submit"
													id="modify_field_button">
													<a id="submit" onclick="modifyField()">Update Field</a>
												</div>
											</div>

										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- @end #plan_by_fields -->
						<div id="planbyacres" class="show_hide_class hidden">
							<div class="form_area">
								<h2 class="field-heading">
									Plan by Acres <span><a id="plan_by_acres"
										class="help_Infromation_PopUp" href="#"><img
											src="images/i-icon.png"></a></span>
								</h2>
								<div class="clearfix"></div>
								<div class="ques">
									<span class="plant">Available land for planting:
										<input type="text" id="acres_value" maxlength="8"
										onkeypress="return isValidNumberValueForWithOutDot(event)"
										onchange="addCommaSignForAcres(this);onChangeOfAcresWhenPlanningByAcre(this)" />
										&nbsp;&nbsp;<span> Acres</span><!-- <select reqired>
                      <option>Acres</option>
                    </select> --></span>
								</div>
								
								<div class="clearfix"></div>
								<div class="yellobtn pre_next">
									<a id="submit" onclick="nextPlanByAcres()">Next</a>
								</div>
								<div class="yellobtn pre_next">
									<a id="submit" onclick="callMethodForPageChangeAndProgressBarImage(1,1)">Previous</a>
								</div>
							</div>
						</div>
						<!-- @end #planbyacres -->
						<div id="crop_cropinfo" class="show_hide_class hidden">
							<div class="form_area">
								<h2 class="field-heading">
									Crops and Crop Information <a id="Crops_and_Crop_Information"
										class="help_Infromation_PopUp" href="#"><img
										src="images/i-icon.png"></a>
								</h2>
								<div class="clearfix"></div>
								<div class="all_buttons" style="margin-bottom: 8px;">
									<div class="top_buttons addremove-field">
										<a onclick="div_show5()"><img src="images/add-crop.png"></a>
									</div>
									<div class="top_buttons addremove-field padding-left-none">
										<a onclick="removeCrops()"><img src="images/remove-crops.png"></a>
									</div>
									<div class="top_buttons addremove-field padding-left-none">
										<a onclick="selectAllContacts()"><img id="select-unselect-img" src="images/select_all.png"></a>
									</div>
									<a id="Crops_and_Crop_Information_add" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a>
								</div>

								<!---------------------start tab------------------------------->
								<div class="addcrop">
									<p class="addcroptext">Select the crops you are considering planting.</p>
									<div class="clearfix"></div>
									<div id="tabs-container">
										<ul class="tabs-menu list-inline">
											<li class="current"><a href="#tab-1">Field Crops</a></li>
											<li><a href="#tab-2">Vegetable/Fruit Crops</a></li>
										</ul>
										<div class="tab">
											<div id="tab-1" class="tab-content">
												<div class="ques">
													<div
														class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">
														<ul class="list-unstyled text-left" id="crop_normal">

															<!-- Code commented as per clients requirement
	By Harshit Gupta
	01-04-2015
	Start -->
															<!-- <li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Barley"> &nbsp;&nbsp;<span>Barley</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Canola"> &nbsp;&nbsp;<span>Canola</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Corn feed"> &nbsp;&nbsp;<span>Corn (feed)</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Corn grain"> &nbsp;&nbsp;<span>Corn (grain)</span></li> -->
															<!--  
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Alfalfa"> &nbsp;&nbsp;<span>Alfalfa</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Barley"> &nbsp;&nbsp;<span>Barley</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Beets"> &nbsp;&nbsp;<span>Beets</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Cabbage"> &nbsp;&nbsp;<span>Cabbage</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Celery"> &nbsp;&nbsp;<span>Celery</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Corn"> &nbsp;&nbsp;<span>Corn</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Corn feed"> &nbsp;&nbsp;<span>Corn feed</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Corn grain"> &nbsp;&nbsp;<span>Corn grain</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Corn sweet"> &nbsp;&nbsp;<span>Corn sweet</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Cotton Pima"> &nbsp;&nbsp;<span>Cotton Pima</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Cotton Upland"> &nbsp;&nbsp;<span>Cotton Upland</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Cucumbers"> &nbsp;&nbsp;<span>Cucumbers</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Garlic"> &nbsp;&nbsp;<span>Garlic</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Hay"> &nbsp;&nbsp;<span>Hay</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Lentils"> &nbsp;&nbsp;<span>Lentils</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Oats"> &nbsp;&nbsp;<span>Oats</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Peas"> &nbsp;&nbsp;<span>Peas</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Rice"> &nbsp;&nbsp;<span>Rice</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Sorghum"> &nbsp;&nbsp;<span>Sorghum</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Soya beans"> &nbsp;&nbsp;<span>Soya beans</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Spinach"> &nbsp;&nbsp;<span>Spinach</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Tobacco"> &nbsp;&nbsp;<span>Tobacco</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Wheat"> &nbsp;&nbsp;<span>Wheat</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Wheat Spring"> &nbsp;&nbsp;<span>Wheat Spring</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Wheat winter"> &nbsp;&nbsp;<span>Wheat winter</span></li>
-->
															<!-- End -->
															<!-- start by rohit on 01-06-15-->
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Alfalfa">
																&nbsp;&nbsp;<span>Alfalfa</span></lable></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Barley">
																&nbsp;&nbsp;<span>Barley</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Canola">
																&nbsp;&nbsp;<span>Canola</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Corn (feed)">
																&nbsp;&nbsp;<span>Corn (feed)</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Corn (grain)">
																&nbsp;&nbsp;<span>Corn (grain)</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Corn (silage)">
																&nbsp;&nbsp;<span>Corn (silage)</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Corn (sweet)">
																&nbsp;&nbsp;<span>Corn (sweet)</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Cotton (Pima)">
																&nbsp;&nbsp;<span>Cotton (Pima)</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();"
																value="Cotton (upland)"> &nbsp;&nbsp;<span>Cotton (upland)</span></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Lentils">
																&nbsp;&nbsp;<span>Lentils</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Oats">
																&nbsp;&nbsp;<span>Oats</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Peanuts">
																&nbsp;&nbsp;<span>Peanuts</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Potatoes">
																&nbsp;&nbsp;<span>Potatoes</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Rice">
																&nbsp;&nbsp;<span>Rice</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Rye">
																&nbsp;&nbsp;<span>Rye</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Sorghum">
																&nbsp;&nbsp;<span>Sorghum</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Soybeans">
																&nbsp;&nbsp;<span>Soybeans</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Sunflowers">
																&nbsp;&nbsp;<span>Sunflowers</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Tobacco">
																&nbsp;&nbsp;<span>Tobacco</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Wheat (spring)">
																&nbsp;&nbsp;<span>Wheat (spring)</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Wheat (winter)">
																&nbsp;&nbsp;<span>Wheat (winter)</span></label></li>
															<!-- end -->
														</ul>
													</div>
													<!-- <div class="col-lg-4 col-md-4 col-sm-6 padding-left-none tabcontaint">
<ul class="list-unstyled text-center" >
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Beans">&nbsp;&nbsp; <span>Beans</span></li>
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Corn sweet"> &nbsp;&nbsp;<span>Corn (sweet)</span></li>
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Cotton upland"> &nbsp;&nbsp;<span>Cotton (upland)</span></li>
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Cotton pima">&nbsp;&nbsp; <span>Cotton (pima)</span></li>
</div>
<div class="col-lg-4 col-md-4 col-sm-6 padding-left-none tabcontaint">
<ul class="list-unstyled text-right">
<li><img src="images/cropimg.png">&nbsp;&nbsp; <input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Hay alfalfa">&nbsp;&nbsp; <span>Hay (alfalfa)</span></li>
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Hay other">&nbsp;&nbsp; <span>Hay (other)</span></li>
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Oats">&nbsp;&nbsp; <span>Oats</span></li>
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="field_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Peanuts">&nbsp;&nbsp; <span>Peanuts</span></li>
</div> -->
												</div>
											</div>
											<div id="tab-2" class="tab-content">
												<div class="ques">
													<div
														class="col-lg-12 col-md-12 col-sm-12 padding-left-none tabcontaint">
														<ul class="list-unstyled text-left" id="crop_vegitable">
															<!-- Code commented as per clients requirement
	By Harshit Gupta
	01-04-2015
	Start -->
															<!-- <li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Apple"> &nbsp;&nbsp;<span>Apple</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Beet"> &nbsp;&nbsp;<span>Beet</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Broccoli"> &nbsp;&nbsp;<span>Broccoli</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Cabbage"> &nbsp;&nbsp;<span>Cabbage</span></li> -->

															<!-- <li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Beans"> &nbsp;&nbsp;<span>Beans</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Broccoli"> &nbsp;&nbsp;<span>Broccoli</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Cantaloupes"> &nbsp;&nbsp;<span>Cantaloupes</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Carrots"> &nbsp;&nbsp;<span>Carrots</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Cauliflower"> &nbsp;&nbsp;<span>Cauliflower</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Honeydews"> &nbsp;&nbsp;<span>Honeydews</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Lettuce"> &nbsp;&nbsp;<span>Lettuce</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Lettuce Leaf"> &nbsp;&nbsp;<span>Lettuce Leaf</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Onions"> &nbsp;&nbsp;<span>Onions</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Peppers"> &nbsp;&nbsp;<span>Peppers</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Potatoes"> &nbsp;&nbsp;<span>Potatoes</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Strawberries"> &nbsp;&nbsp;<span>Strawberries</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Tomatoes"> &nbsp;&nbsp;<span>Tomatoes</span></li>
<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Watermelons"> &nbsp;&nbsp;<span>Watermelons</span></li>
 -->
															<!-- End -->
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Beans">
																&nbsp;&nbsp;<span>Beans</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Broccoli">
																&nbsp;&nbsp;<span>Broccoli</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Cabbage">
																&nbsp;&nbsp;<span>Cabbage</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();"
																value="Cantaloupes"> &nbsp;&nbsp;<span>Cantaloupes</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Carrots">
																&nbsp;&nbsp;<span>Carrots</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();"
																value="Cauliflower"> &nbsp;&nbsp;<span>Cauliflower</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Celery">
																&nbsp;&nbsp;<span>Celery</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();"
																value="Cucumbers"> &nbsp;&nbsp;<span>Cucumbers</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Garlic">
																&nbsp;&nbsp;<span>Garlic</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();"
																value="Honeydews"> &nbsp;&nbsp;<span>Honeydews</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();"
																value="Lettuce (iceberg)"> &nbsp;&nbsp;<span>Lettuce
																	(iceberg)</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();"
																value="Lettuce (leaf)"> &nbsp;&nbsp;<span>Lettuce
																	(leaf)</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Onions">
																&nbsp;&nbsp;<span>Onions</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Peppers">
																&nbsp;&nbsp;<span>Peppers</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Spinach">
																&nbsp;&nbsp;<span>Spinach</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();"
																value="Strawberries"> &nbsp;&nbsp;<span>Strawberries</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();" value="Tomatoes">
																&nbsp;&nbsp;<span>Tomatoes</span></label></li>
															<li class="col-lg-4 col-md-4 col-sm-6 padding-left-none"><img
																src="images/cropimg.png"> &nbsp;&nbsp;<label class="labelForCrops"><input
																type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this); changeSelectAllImage();"
																value="Watermelons"> &nbsp;&nbsp;<span>Watermelons</span></label></li>
														</ul>
													</div>
													<!-- <div class="col-lg-4 col-md-4 col-sm-6 padding-left-none tabcontaint">
<ul class="list-unstyled text-center">
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Banana">&nbsp;&nbsp; <span>Banana</span></li>
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Carrot">&nbsp;&nbsp; <span>Carrot</span></li>
<li><img src="images/cropimg.png">&nbsp;&nbsp; <input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Celery">&nbsp;&nbsp; <span>Celery</span></li>
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Chard">&nbsp;&nbsp; <span>Chard</span></li>
</div>
<div class="col-lg-4 col-md-4 col-sm-6 padding-left-none tabcontaint">
<ul class="list-unstyled text-right">
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Orange">&nbsp;&nbsp; <span>Orange</span></li>
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Garlic">&nbsp;&nbsp; <span>Garlic</span></li>
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Apricot">&nbsp;&nbsp; <span>Apricot</span></li>
<li><img src="images/cropimg.png"> &nbsp;&nbsp;<input type="checkbox" name="vegitable_crop[]" class="crops" onchange="onCropSelectedOrRemoved(this)" value="Date">&nbsp;&nbsp; <span>Date</span></li>
</div> -->
												</div>

											</div>


										</div>
									</div>
								</div>
								<!--------------end tab------------------------->

								<div class="yellobtn pre_next">
									<a id="submit popup" onclick="nextCropsAndCropsInformation()">Next</a>
								</div>
								<div class="yellobtn pre_next">
									<a id="submit" onclick="callMethodForPageChangeAndProgressBarImage(1,2)">Previous</a>
								</div>
								<!-- Popup Div Starts Here -->
								<div id="add-new-crop-and-crop-info"
									class="common-pop-class-for-all">
									<div id="popupContact">
										<!-- Planning Form -->
										<div class="popup_section">
											<img id="close" src="images/cross.png" onclick="div_hide5()">
											<h2 class="popupheadercrop"
												style="padding: 0 0 0 22px; color: #BE922F; font-weight: bold;">Add
												Crop</h2>
											<div class="popupform messagepopup"
												style="padding: 5px 20px;">
												<p class="addcroppopuptext">Choose area where you want
													to add Crop :</p>
												<div class="clearfix"></div>
												<label class="labelForCrops"><input type="radio"
													name="crop_type" value="Field Crops"><span
													class="planninghead">Field Crops</span></label> <label
													class="labelForCrops"><input type="radio"
													name="crop_type" value="Vegetable or Fruit Crops"
													class="planningradio"><span class="planninghead">Vegetable/Fruit
														Crops</span></label>
												<div class="clearfix"></div>
												<div class="addcroppup">
													<label>Crop Name</label> <input type="text" id="crop_name"
														name="Crop Name">
												</div>
												<div class="clearfix"></div>
												<div class="yellobtn submit" style="margin: 10px 0px;">
													<a onclick="addNewCrop()" id="submit">Add Crop</a>
												</div>
											</div>

										</div>
									</div>
								</div>
								<!-- Popup Div Ends Here -->


							</div>
						</div>
						<!-- @end #add_crop -->
						<div id="cropinfodetail" class="show_hide_class hidden">
							<div class="form_area">
								<h2 class="info-heading">Crop Information Details	</h2>

								<div class="clearfix"></div>
								<div class="info">Enter the information for each crop.</div>
								<!--	@changed - Abhishek		@date - 29-12-2015 	 -->
								<div class="clearfix"></div>
								<div>* Required</div>
								<div class="clearfix"></div>
								<div class="pull-right">
									<%--<label>Vary price and yield between Min and Max values to compute expected profit per acre--%>
									<label>Check the box to vary price and yield to compute Estimated Income
										<input type="checkBox" name="montyCarloSwitch" onchange="enableDisableMontyCarloAnalysis(); return false;">
									</label>
									<a id="montyCarloSwitch" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a>
								</div>

								<div class="ques">
									<div class="table-responsive" <%--style="max-height: 463px;"--%>>
										<table class="table table-striped tbl-bordr  tblbrdr table-fixed"
											cellspacing="0" width="100%" id="cropInformationDetailFirstTable">
											<thead>
												<tr class="tblhd text-center add-fieldi">
													<td class="tblbrdr text-center add-fieldi">Crop</td>
													<td class="text-center add-fieldi uom_width">UoM</td>
													<td class="text-center" colspan="3"><a
														id="Crop_Information_Details_yield"
														class="help_Infromation_PopUp" href="#"><span
															class="add-fieldi">Yields (UoM/acre) <img
																src="images/i-img.png"></a></span><br> <span
														class="infosubhead expected_range">Expected*</span> <span
														class="infosubhead max_range">Max</span> <span
														class="infosubhead min_range">Min</span></td>
													<td class="text-center" colspan="3"><a
														id="Crop_Information_Details_price"
														class="help_Infromation_PopUp" href="#"><span
															class="add-fieldi">Prices ($/UoM) <img
																src="images/i-img.png"></a></span><br> <span
														class="infosubhead expected_range">Expected*</span> <span
														class="infosubhead max_range">Max</span> <span
														class="infosubhead min_range">Min</span></td>
													<td class="text-center">Variable Production Costs <span
														class="infosubhead">($/acre)*</span><a
														id="variable_production_cost"
														class="help_Infromation_PopUp" href="#"><img
															src="images/i-icon.png"></a></span>
													</td>
													<td class="text-center">Est. Income <span
														class="infosubhead">($/acre)</span><a
														id="profit_per_acre"
														class="help_Infromation_PopUp" href="#"><img
															src="images/i-icon.png"></a>
													</td>
												</tr>
											</thead>
											<tbody></tbody>
										</table>
										<span class="pull-right">** Additional net income</span>
									</div>
								</div>
								<div class="clearfix"></div>

								<div class="panel-group" id="accordion">
									<div class="panel panel-default">
										<div class="panel-heading">
											<a class="remove-text-deco" data-toggle="collapse" data-parent="#accordion" href="#collapseOptionalCropInfo">
												<h4 class="panel-title">
													Optional crop information <i class="fa fa-chevron-down pull-right"></i>
												</h4>
											</a>
										</div>
										<div id="collapseOptionalCropInfo" class="panel-collapse collapse">
											<div class="panel-body">
												<div class="table-responsive" style="max-height: 225px;">
													<table class="table table-striped tbl-bordr  tblbrdr"
														   cellspacing="0" width="100%" id="cropInformationDetailSecondTable">
														<thead>
														<tr class="tblhd text-center add-fieldi">
															<td class="tblbrdr text-center add-fieldi">Crop</td>
															<!--	@changed - Abhishek		@date - 30-12-2015 -->
															<td class="text-center add-fieldi hidden">Irrigated</td>
															<td class="text-center">Conservation Practice <a id="Conservation_Practice" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></td>
															<td class="text-center">Hi-Risk Crop <a id="hiRisk_crop" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></td>
														</tr>
														</thead>
														<tbody></tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
									<div class="panel panel-default">
										<div class="panel-heading">
											<a class="remove-text-deco" data-toggle="collapse" data-parent="#accordion" href="#collapseOptionalPlanningDate">
												<h4 class="panel-title">
													Optional planting dates (for cashflow analysis) <i class="fa fa-chevron-down pull-right"></i>
												</h4>
											</a>
										</div>
										<div id="collapseOptionalPlanningDate" class="panel-collapse collapse">
											<div class="panel-body">
												<div class="table-responsive" style="max-height: 225px;">
													<table class="table table-striped tbl-bordr  tblbrdr hidden"
														   cellspacing="0" width="100%" id="cropInformationDetailThirdTable" >
														<thead>
														<tr class="tblhd text-center add-fieldi">
															<td class="tblbrdr text-center add-fieldi">Crop</td>
															<td class="text-center add-fieldi">Preferred Planting Date</td>
															<td class="text-center">Early Planting Date</td>
															<td class="text-center">Late Planting Date</td>
															<td class="text-center">Length of Season(Days)</td>
														</tr>
														</thead>
														<tbody></tbody>
													</table>
													<table width="100%" cellspacing="0" class="table table-striped tbl-bordr  tblbrdr">
														<tbody>
														<tr class="tblgrn text-center">
															<%--<td class="success infotext">Under construction.  Planting dates not needed at this time.</td>--%>
															<td class="success infotext">Cashflow Analysis Under Construction</td>
														</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
									<div class="panel panel-default">
										<div class="panel-heading">
											<a class="remove-text-deco" data-toggle="collapse" data-parent="#accordion" href="#collapseAdditionlIncome">
												<h4 class="panel-title">
													Additional Crop Income <i class="fa fa-chevron-down pull-right"></i>
												</h4>
											</a>
										</div>
										<div id="collapseAdditionlIncome" class="panel-collapse collapse">
											<div class="panel-body">
												<div class="table-responsive" style="max-height: 225px;">
													<table class="table table-striped tbl-bordr tblbrdr"
														   cellspacing="0" width="100%" id="" >
														<thead>
														<tr class="tblhd text-center add-fieldi">
															<td class="text-center add-fieldi">Crop Name</td>
															<td class="text-center">Government Payments ($/acre)</td>
															<td class="text-center">Co-products ($/acre)</td>
															<td class="text-center">Add’l Variable Production Cost ($/acre)</td>
															<td class="text-center">Add’l Income ($/acre)</td>
														</tr>
														</thead>
														<tbody id="crop-info-additional-income-tbody"></tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
								</div>

								<%--<!-- Collapsible Panels - START -->
								<div id="panel-first" class="panel panel-default">
									<div id="panel-heading-first" class="panel-heading">
										<!--	@changed - Abhishek		@date - 30-12-2015 -->
										&lt;%&ndash;<h3 class="panel-title">Optional crop information (for risk and conservation analysis)</h3>&ndash;%&gt;
										<h3 class="panel-title">Optional crop information</h3>

										<span id="collapsed-first" class="pull-right clickable"><i class="fa fa-chevron-down"></i></span>
									</div>
									<div id="panel-body-first" class="panel-body">
										<div class="table-responsive" style="max-height: 225px;">
											<table class="table table-striped tbl-bordr  tblbrdr"
												cellspacing="0" width="100%" id="cropInformationDetailSecondTable">
												<thead>
													<tr class="tblhd text-center add-fieldi">
														<td class="tblbrdr text-center add-fieldi">Crop</td>
														<!--	@changed - Abhishek		@date - 30-12-2015 -->
														<td class="text-center add-fieldi hidden">Irrigated</td>
														<td class="text-center">Conservation Practice <a id="Conservation_Practice" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></td>
														<td class="text-center">Hi-Risk Crop <a id="hiRisk_crop" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></td>
													</tr>
												</thead>
												<tbody></tbody>
											</table>
										</div>
									</div>
								</div>
								<!---------------------second------------------------------------------------->
								<div id="panel-second" class="panel panel-default">
									<div id="panel-heading-second" class="panel-heading">
										<h3 class="panel-title">Optional planting dates (for cashflow analysis)</h3>
										<span id="collapsed-second" class="pull-right clickable"><i class="fa fa-chevron-down"></i></span>
									</div>
									<div id="panel-body-second" class="panel-body">
										<div class="table-responsive" style="max-height: 225px;">
											<table class="table table-striped tbl-bordr  tblbrdr hidden"
												cellspacing="0" width="100%" id="cropInformationDetailThirdTable" >
												<thead>
													<tr class="tblhd text-center add-fieldi">
														<td class="tblbrdr text-center add-fieldi">Crop</td>
														<td class="text-center add-fieldi">Preferred Planting Date</td>
														<td class="text-center">Early Planting Date</td>
														<td class="text-center">Late Planting Date</td>
														<td class="text-center">Length of Season(Days)</td>
													</tr>
												</thead>
												<tbody></tbody>
											</table>
											<table width="100%" cellspacing="0" class="table table-striped tbl-bordr  tblbrdr">
												<tbody>
												<tr class="tblgrn text-center">
													<td class="success infotext">Under construction.  Planting dates not needed at this time.</td>
												</tr>
												</tbody>
											</table>
										</div>
									</div>
								</div>--%>



								<div class="clearfix"></div>
								<div class="yellobtn pre_next">
									<a id="submit popup" onclick="nextCropsInformationDetails()">Next</a>
								</div>
								<div class="yellobtn pre_next">
									<a id="submit" onclick="callMethodForPageChangeAndProgressBarImage(2,3)">Previous</a>
									<!-- showCroAndCropInfoPage rohit 3-4-15 -->
								</div>

							</div>
						</div>
						<!-- @end #cropinfodetail -->
						<div id="optional_crop" class="show_hide_class hidden">
							<div class="form_area">
								<h2 class="field-heading">
									Optional Crop Production Costs Details <span><img
										src="images/i-icon.png"></span>
								</h2>
								<div class="all_buttons">
									<div class="top_buttons addremove-field">
										<a id="add_production_cost_field"><img
											src="images/add-componentbutton.png"></a>
									</div>
									<div class="top_buttons addremove-field">
										<a id="modify_production_cost_field"><img
											src="images/modify.png"></a>
									</div>
									<div class="top_buttons add-field padding-right-none">
										<a id="remove_production_cost_field"><img
											src="images/remove-component.png"></a>
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="info">
									To calculate the Variable Production cost of <span id="crop_name_dynamic"></span>,
								</div>
								<div class="clearfix"></div>
								<%-- @author Jyoti    @date 02-01-2017  PPT NO : 12312106 Slide no : 16--%>
								<%--<div class="optionsub pull-left">Enter the quantity and price or
									cost for each cost component used to raise the crop.</div>--%>
								<div class="optionsub pull-left">enter the quantity and cost for each input used to raise the crop.</div>

								<div class="ques" id="optional_crop_dynamic_div"></div>
								<div class="clearfix"></div>
								<div class="yellobtn pre_next">
									<a id="submit popup" onclick="setTotalOfOptionalCropInformationToVariableProductionCost()">OK</a>
								</div></div>
						</div>
						<!-- @end #optional_crop -->
						<div id="crop_field_choice" class="show_hide_class hidden">
							<div class="form_area">
								<h2 class="field-heading">Crop/Field Choices</h2>
								<div class="clearfix"></div>
								<!-- <div class="yellobtn pre_next">
<a id="submit" onclick="allCheckboxSelect()">All</a>
</div>
<div class="yellobtn pre_next">
<a onclick="allCheckboxNone()">None</a>
</div>
-->
								<!-- add button by rohit 6-4-15-->
								<div style="margin-bottom: 8px;" class="all_buttons">
									<div class="top_buttons addremove-field">
										<a onclick="allCheckboxNone()"><img
											src="images/select-none.png"></a>
									</div>
									<div class="top_buttons addremove-field padding-left-none">
										<a onclick="allCheckboxSelect()"><img
											src="images/select-all.png"></a>
									</div>
								</div>
								<!-- <div>
 <div style="float: right; width: 19%;" class="col-lg-3 col-md-5 col-sm-5  addremove-field padding-left-none"><a onclick="allCheckboxNone()"><img src="images/select-none.png"></a></div>
<div style="float: right; width: 19%;" class="col-lg-3 col-md-5 col-sm-5  addremove-field"><a onclick="allCheckboxSelect()"><img src="images/select-all.png"></a></div>
 </div> -->
								<div class="info">
									Select which crops you are considering planting in each field.
									<span><a id="crop_field_choices"
										class="help_Infromation_PopUp" href="#"><img
											src="images/i-icon.png"></a></span>
								</div>
								<div class="ques">
									<div class="table-responsive">
										<%--style="overflow: auto; max-height: 265px;"--%>
										<table class="table table-striped tbl-bordr tblbrdr fld-chc-tbl-fixd-hdr"
											cellspacing="0" width="100%" id="field_choice_crop_table">
											<thead><tr class="tblhd text-center add-fieldi"><td class="tblbrdr text-center add-fieldi">Field/Crop</td></tr></thead>
											<tbody></tbody>
										</table>
									</div>
								</div>
								<div class="clearfix"></div>

								<div class="yellobtn pre_next">
									<a onclick="nextCropFieldChoice()">Next</a>
								</div>
								<div class="yellobtn pre_next">
									<a id="submit" onclick="callMethodForPageChangeAndProgressBarImage(4,0)">Previous</a>
								</div>



							</div>
						</div>
						<!-- @end #crop_field_choice -->
						<div id="resources_info" class="show_hide_class hidden">
							<div class="form_area">
								<h2 class="field-heading">Resources</h2>
								<div class="clearfix"></div>
								<div style="margin-bottom: 8px;" class="all_buttons">
									<div class="top_buttons addremove-field">
										<a onclick="div_show6()"><img
											src="images/add_resource.png"></a>
									</div>
									<div class="top_buttons addremove-field padding-left-none">
										<a onclick="removeResource()"><img
											src="images/remove_resources.png"></a>
									</div>
								</div>
								<div class="clearfix"></div>
								<!-- <div class="col-lg-6 col-md-2 col-sm-2 text-left"></div>
<div class="col-lg-3 col-md-5 col-sm-5  addremove-field"><a onclick="div_show6()"><img src="images/add_resource.png"></a></div>
<div class="col-lg-3 col-md-5 col-sm-5  addremove-field padding-left-none"><a onclick="removeResourse()"><img src="images/remove_resources.png"></a></div> -->
								<div class="clearfix"></div>
								<div class="info" style="margin-top: 5px;">
									Select which resources you want to manage.
									<span>
										<a id="resourse_manage" class="help_Infromation_PopUp" href="#">
											<img src="images/i-icon.png">
										</a>
									</span>
								</div>


								<div class="ques">
									<div class="maintbl">  <%--style="max-height: 214px;"--%>
										<table class="table tbl table-striped tbl-bordr  tblbrdr resources-tbl-fixd-hdr" id="manage_resource"
											cellspacing="0" width="100%">
											<thead>
												<tr class="tblhd text-center add-fieldi">
													<td class="tblbrdr text-center add-fieldi">Manage</td>
													<td class="text-center add-fieldi">Resource Name</td>
													<!--	@changed - Abhishek		@date - 23-01-2016 		@desc - Added help information functionality-->
													<%--<td class="text-center add-fieldi">Unit of Measure</td>--%>
													<td class="text-center add-fieldi">Unit of Measure<a id="resourceUnitOfmeasure" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></td>
													<!--	@changed - Abhishek		@date - 29-12-2015 		@desc - Added help information functionality-->
													<%--<td class="text-center">Amount Available <img src="images/i-img.png"></td>--%>
													<td class="text-center">Amount Available <a id="amount_Available" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></td>
												</tr>
											</thead>
											<tbody id="manage_resource_tbody">
												<tr class="tblgrn text-center">
													<td class="tblft1"><input type="checkbox"
														checked="checked" disabled="disabled" /></td>
													<td class="success croplimi">Land</td>
													<td class="success croplimit">Acres</td>
													<td id="total_land_available" class="success croplimit"></td>
												</tr>
												<tr class="tblbclgrnd text-center">
													<td class="tblft1"><input type="checkbox"
														checked="checked" disabled="disabled" /></td>
													<td class="croplimi">Working Capital</td>
													<td class=" croplimit">$</td>
													<td class="croplimit"><input type="text"
													 onkeypress="return isValidNumberValueForWithOutDot(event)" onchange="addCommaSignInCapital(this)"
														id="total_capital_available"/></td>
												</tr>
												<%--	@Abhishek	@date - 20-01-2016	@desc - applied hidden class as per client request--%>
												<%--<tr class="tblgrn text-center">--%>
												<tr class="tblgrn text-center hidden">
													<td class="tblft1"><input type="checkbox"
														name="resourceSelection" onchange="onResourceSelectedOrRemoved(this)"></td>
													<td class="success croplimit">Labor</td>
													<td class="success croplimit">Hours</td>
													<td class="success croplimit"><input type="text" 
													onkeypress="return isValidNumberValueForWithOutDot(event)" onchange="addCommaSignWithOutDollarDot(this)"/></td>
												</tr>
												<%--	@Abhishek	@date - 20-01-2016	@desc - applied hidden class as per client request--%>
												<%--<tr class="tblgrn text-center">--%>
												<tr class="tblgrn text-center hidden">
													<td class="tblft1"><input type="checkbox"
														name="resourceSelection" onchange="onResourceSelectedOrRemoved(this)"></td>
													<td class="croplimit">Water</td>
													<td class="croplimit">Gallons</td>
													<td class="croplimit"><input type="text" 
													onkeypress="return isValidNumberValueForWithOutDot(event)" onchange="addCommaSignWithOutDollarDot(this)"/></td>
												</tr>
												<%--	@Abhishek	@date - 20-01-2016	@desc - applied hidden class as per client request--%>
												<%--<tr class="tblgrn text-center">--%>
												<tr class="tblgrn text-center hidden">
													<td class="tblft1"><input type="checkbox"
														name="resourceSelection" onchange="onResourceSelectedOrRemoved(this)"></td>
													<td class="success croplimit">Equipment</td>
													<td class="success croplimit">Hours</td>
													<td class="success croplimit"><input type="text"
													onkeypress="return isValidNumberValueForWithOutDot(event)" onchange="addCommaSignWithOutDollarDot(this)"/></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div class="clearfix"></div>


								<div class="yellobtn pre_next">
									<a onclick="nextResources()">Next</a>
								</div>
								<div class="yellobtn pre_next">
									<a id="submit" onclick="callMethodForPageChangeAndProgressBarImage(5, 4)">Previous</a>
								</div>

							</div>
						</div>
						<!-- @end #resources -->
						<div id="resources_usage" class="show_hide_class hidden">
							<div class="form_area">
								<h2 class="field-heading">Crop Resources Usage</h2>
								<div class="clearfix"></div>
								<div class="info">
									Enter the amount of each resource used per acre. <span><a
										id="resourse_usage" class="help_Infromation_PopUp" href="#"><img
											src="images/i-icon.png"></a></span>
								</div>


								<div class="ques">
									<div class="maintbl" style="max-height: 250px;">
										<table id="crop_resource_usage"
											class="table tbl table-striped tbl-bordr  tblbrdr Crop_Resources_Usage_table"
											cellspacing="0" width="100%">
											<thead>
												<tr class="tblhd text-center add-fieldi">
													<td class="tblbrdr text-center tittle-uppercase">Crop</td>
													<td class="text-center tittle-uppercase UOMCropResourceUsage">Yields<br><span class="resub">(UoM acre)</span></td>
													<td class="text-center tittle-uppercase">Variable production cost<br>
														<span class="resub">($/acre*)</span>
														<span><a class="help_Infromation_PopUp" id="variable_production_cost_resourse"><img src="images/i-icon.png"></a></span>
													</td>
												</tr>
											</thead>
											<tbody></tbody>
										</table>
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="ques" id="show_hide_field_variance_button">
									<%--	@changed - Abhishek 	@date - 23-01-2016		@desc - chanegd according to slide# 5 of 01042015	--%>
									<%--<span class="cropbtn pre_pos"><a onclick="showFieldVariencePage()">Field Differences<img src="images/i-icon.png"></a></span>--%>
									<span class="cropbtn pre_pos"><a onclick="showFieldVariencePage()">Field Differences</a></span>
									<span><a class="help_Infromation_PopUp" id="fieldDifference"><img src="<c:url value="/images/i-icon.png"/>" style="margin-top: 7px;"></a></span>
								</div>

								<div class="yellobtn pre_next">
									<!-- <a onclick="showFieldVariencePage()">Next</a> -->
									<!-- change 09-03-2015 -->
									<!-- <a onclick="showForwardSalesPage()">Next</a> -->
									<a onclick="nextCropResourceUsage()">Next</a>
									<!-- change 09-03-2015 -->
								</div>
								<div class="yellobtn pre_next">
									<a id="submit" onclick="callMethodForPageChangeAndProgressBarImage(6, 5)">Previous</a>
								</div>

							</div>
						</div>
						<!-- @end #resources_usage -->
						<div id="field_varience" class="show_hide_class hidden">
							<div class="form_area">
								<h2 class="field-heading">Field Differences</h2>
								<div class="clearfix"></div>
								<div class="info">
									Account for variability of yields and resource use among fields.
									<a id="yield_Difference" class="help_Infromation_PopUp" href="#"><img src="<c:url value="/images/i-icon.png"/>"></a>
								</div>
								<div class="clearfix"></div>
								<div class="ques">
									<div class="col-lg-6 col-md-6 col-sm-6 pull-left text-right">
										<select class="variance"
											onchange="fieldSelectFieldVarience(this)"
											id="field_select_drop_down">
											<option value="0">Select Field</option>
										</select>
									</div>
									<div class="col-lg-6 col-md-6 col-sm-6 pull-right text-left">
										<select onchange="getFieldYieldDiffence(this)"
											class="variance" id="crop_select_drop_down">
											<option value="0">Select Crop</option>
											</select>
									</div>
									<div class="clearfix"></div>
									<div class="ques">
										<div class="col-lg-6 col-md-6 col-sm-6">
											<p class="text-left variancesub">
												Yield Difference <img src="<c:url value="/images/i-icon.png"/>">
											</p>
											<div class="table-responsive">
												<table class="table table-striped tbl-bordr  tblbrdr"
													cellspacing="0" width="100%">
													<thead>
														<tr class="tblhd text-center add-fieldi">
															<td class="tblbrdr text-center"></td>
															<%--@changed - Abhishek 	@date - 30-12-2015	--%>
															<%--<td class="text-center">Previously Entered</td>--%>
															<td class="text-center">Default</td>
															<%--@changed - Abhishek 	@date - 30-12-2015	--%>
															<%--<td class="text-center">Override</td>--%>
															<td class="text-center">Field-Specific</td>
														</tr>
													</thead>
													<tbody id="yield-difference-tbody">
														<tr class="tblgrn text-center">
															<td class="tblft1">Expected</td>
															<td class="success infotext"
																id="crop_Yield_Difference_Expected"></td>
															<td class="success infotext"><input type="text"
																onchange="addCommaSignWithForOnePoint(this)"
																onkeypress="return isValidNumberValue(event)"
																id="field_difference_exp" name=""></td>
														</tr>
														<tr class="tblbclgrnd text-center">
															<td class="tblft1">Minimum</td>
															<td class="infotext" id="crop_Yield_Difference_Minimum"></td>
															<td class="infotext"><input type="text"
																onchange="addCommaSignWithForOnePoint(this)"
																onkeypress="return isValidNumberValue(event)"
																id="field_difference_min" name=""></td>
														</tr>
														<tr class="tblgrn text-center">
															<td class="tblft1">Maximum</td>
															<td class="success infotext"
																id="crop_Yield_Difference_Maximum"></td>
															<td class="success infotext"><input type="text"
																onchange="addCommaSignWithForOnePoint(this)"
																onkeypress="return isValidNumberValue(event)"
																id="field_difference_max" name=""></td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>

										<div class="col-lg-6 col-md-6 col-sm-6">
											<p class="text-left variancesub">
												Resource Usage Difference <img src="images/i-icon.png">
											</p>
											<div class="table-responsive">
												<table class="table table-striped tbl-bordr  tblbrdr"
													cellspacing="0" width="100%">
													<thead>
														<tr class="tblhd text-center add-fieldi">
															<%--@changed - Abhishek 	@date - 30-12-2015	--%>
															<%--<td class="tblbrdr text-center">Crop</td>--%>
															<td class="tblbrdr text-center">Resource</td>
															<%--@changed - Abhishek 	@date - 30-12-2015	--%>
															<%--<td class="text-center">Previously Entered</td>--%>
															<td class="text-center">Default</td>
															<%--@changed - Abhishek 	@date - 30-12-2015	--%>
															<%--<td class="text-center">Override</td>--%>
															<td class="text-center">Field-Specific</td>
														</tr>
														<tr class="tblgrn text-center"
															id="resources_usages_production_row">
															<td class="tblft1 tittle-uppercase"
																id="resources_usages_difference_row_resource_name">Variable
																Production Cost</td>
															<td class="success infotext"
																id="resources_usages_production_resource_default"></td>
															<td class="success infotext"><input type="text"
																onchange="addCommaSignWithDollar(this)"
																onkeypress="return isValidNumberValue(event)"
																id="resources_usages_production_cost_resource_override"></td>
														</tr>
													</thead>
													<tbody id="crop_resources_usages_difference_tbody">


														<!-- <tr class="tblgrn text-center" id="resources_usages_difference_row__1">
                <td class="tblft1" id="resources_usages_difference_row__1_resource_name" >Capital</td>
                <td class="success infotext" id="resources_usages_difference_row__1_resource_default">Pre-populated fr Resource Use</td>
                <td class="success infotext"><input type="text" id="resources_usages_difference_row__1_resource_override" ></td>
            </tr>
            <tr class="tblbclgrnd text-center">
                <td class="tblft1">Resource 1</td>
                <td class="infotext">Pre-populated fr Resource Use</td>
                <td class="infotext"><input type="text" name=""></td>
            </tr>
            <tr class="tblgrn text-center">
                <td class="tblft1">Resource 2</td>
                <td class="success infotext">Pre-populated fr Resource Use</td>
                <td class="success infotext"><input type="text" name=""></td>
                  </tr>
                   <tr class="tblbclgrnd text-center">
                <td class="tblft1">Resource 3</td>
                <td class="infotext">Pre-populated fr Resource Use</td>
                <td class="infotext"></td>
                  </tr>
                   <tr class="tblgrn text-center">
                <td class="tblft1">Resource n</td>
                <td class="success infotext">Pre-populated fr Resource Use</td>
                <td class="success infotext"></td>
                  </tr> -->
													</tbody>
												</table>
											</div>
										</div>
									</div>
									<div class="clearfix"></div>


									<!-- <div class="yellobtn pre_next">
<a onclick="showForwardSalesPage()">Next</a>
</div>
<div class="yellobtn pre_next">
<a id="submit" onclick="showResourcesUsagePage()">Previous</a>
</div> -->
									<div class="yellobtn pre_next"
										id="dynamic_button_for_yeild_differnce">
										<a onclick="nextFieldDifference()">Ok</a>
									</div>
								</div>
							</div>
						</div>
						<!-- @end #field_varience -->
						<div id="forward_sales_div" class="show_hide_class hidden">
							<div class="form_area">
								<h2 class="field-heading">
									Forward Sales <span><a id="forward_sale"
										class="help_Infromation_PopUp" href="#"><img
											src="images/i-icon.png"></a></span>
								</h2>
								<div class="clearfix"></div>
								<div class="info">
									Enter information on firm or proposed forward sales.
									<%--@changed - Abhishek 	@date - 29-12-2015	--%>
									<%--<a id="forward_sale_proposed" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png" class="add-fieldi"></a>--%>
								</div>
								<div class="ques">
									<div class="table-responsive">  <%--style="max-height: 306px;"--%>
										<table class="table table-striped tbl-bordr  tblbrdr forward-sales-tbl-fixd-hdr" id="forward_sales_information"
											cellspacing="0" width="100%">
											<thead>
												<!--	@changed - Abhishek		@date - 29-12-2015 		@desc - Added help information functionality-->
												<tr class="tblhd text-center add-fieldi">
													<td class="tblbrdr text-center add-fieldi">Crop</td>
													<td class="text-center add-fieldi">Yield Units</td>
													<td class="text-center add-fieldi">Price <a id="forward_sale_price" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></td>
													<td class="text-center add-fieldi">Amount <a id="forward_sale_amount" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></td>
													<td class="text-center add-fieldi">Acres <a id="forward_sale_acres" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></td>
													<td class="text-center">Proposed <a id="forward_sale_proposed" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></td>
													<td class="text-center">Firm <a id="forward_sale_firm_td" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></td>
													<td class="text-center hidden">Upper Limit (%) <a id="forward_sale_upper_limit" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></td>
												</tr>
											</thead>
											<tbody></tbody>
										</table>
									</div>
								</div>
								<div class="clearfix"></div>
								<div class="ques">
									<!-- 
Commented as per client requirement
<span class="pull-left salestext">Contract Identifier</span> <span class="sale"><input type="text" id="contact_identifier" name="" ></span>  <img src="images/i-icon.png" class="salesimg"> -->
								</div>

								<div class="yellobtn pre_next">
									<a onclick="nextForwardSales()">Next</a>
								</div>
								<div class="yellobtn pre_next">
									<!-- change 09-03-2015 -->
									<!-- <a id="submit" onclick="showFieldVariencePage()">Previous</a> -->
									<!-- change 09-03-2015 -->
									<!-- <a id="submit" onclick="showResourcesUsagePage()">Previous</a> -->
									<a id="submit" onclick="callMethodForPageChangeAndProgressBarImage(7, 6)">Previous</a>
								</div>

							</div>
						</div>
						<!-- @end #forward_sales -->
						<div id="crop_limits_div" class="show_hide_class hidden">
							<div class="form_area">
								<h2 class="field-heading">Crop Limits</h2>
								<div class="clearfix"></div>
								<div class="ques">
									<!-- @changed - Abhishek 	@date - 05-12-2015 		@updated - 30-12-2015-->
									<p class="croptext pull-right" style=" margin-bottom: 10px; ">
										Create a group of crops and enter minimum or maximum acreage limits for the group
										<a id="crop_group" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a>
									</p>

									<div class="clearfix"></div>

									<div style="float: right;">
										<span>
											<!-- @changed - Abhishek 	@date - 30-12-2015-->
											<%--<a id="crop_group" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a>--%>
											<a onclick="deSelectAllCropsInGroupOptionAndRebuild(); div_show11()"><img src="<c:url value="/images/add-group.png"/>"></a>
										</span>
										<span><a onclick="getGroupForModify()"><img src="<c:url value="/images/modify_group.png"/>"></a></span>
										<span><a onclick="removeGroup()"><img src="<c:url value="/images/remove_group.png"/>"></a></span>
									</div>

									<div class="clearfix"></div>
									<%--	@changed - Abhishek 	@date - 26-01-2016		@desc - changed according to silde# 6 of 01042016	--%>
									<%--<div class="info pull-left" style="margin-top: 1.5px"> Enter minimum and maximum acreage limit for each crop--%>
									<div class="info pull-left" style="margin-top: 1.5px"> Enter minimum and/or maximum acreage limits for any crop.
										<a id="crop_limit" class="help_Infromation_PopUp" href="#">
											<img src="images/i-icon.png"></a>
									</div>

								</div>
								<div class="ques">
									<div class="table-responsive" style="max-height: 230px;">  <%--crop-limits-tbl-fixd-hdr"--%>
										<table class="table table-striped tbl-bordr  tblbrdr" id="crop_limits_table" cellspacing="0" width="100%">
											<thead>
												<tr class="tblhd text-center add-fieldi">
													<td class="tblbrdr text-center add-fieldi">Modify</td>
													<td class="tblbrdr text-center add-fieldi">Crop</td>
													<td class="text-center add-fieldi">Minimum Acres
														<a id="crop_limit_min" class="help_Infromation_PopUp" href="#"><img src="images/i-img.png"></a>
													</td>
													<td class="text-center add-fieldi">Minimum Acres %
														<%--<a id="crop_limit_min_percentage" class="help_Infromation_PopUp" href="javascript:;"><img src="<c:url value="/images/i-img.png"/>"></a>--%>
													</td>
													<td class="text-center">Maximum Acres
														<a id="crop_limit_max" class="help_Infromation_PopUp" href="#"><img src="images/i-img.png"></a>
													</td>
													<td class="text-center">Maximum Acres %
														<%--<a id="crop_limit_max_percentage" class="help_Infromation_PopUp" href="javascript:;"><img src="<c:url value="/images/i-img.png"/>"></a>--%>
													</td>
												</tr>
											</thead>
											<tbody id="crop_limits_table_tbody"></tbody>
											<tbody id="crop_contract_table_tbody" style="border-top: 0px"></tbody>
											<tbody id="group_table_tbody" style="border-top: 0px"></tbody>
										</table>
									</div>
								</div>
								<div class="clearfix"></div>
								<!-- <div class="ques">
<p class="croptext">Create a group of crops and then put minimum or maximum acreage limits on the group :</p>
<span><a onclick="div_show11()"><img src="images/add-group.png"></a></span>
<span><a onclick="div_show11()"><img src="images/modify_group.png"></a></span>
<span><a onclick="div_show11()"><img src="images/remove_group.png"></a></span>
</div> -->

								<div class="yellobtn pre_next">
									<!-- <a onclick="showCropInsurancePage()">Next</a> -->
									<a onclick="saveAllFarmInformation()">Analyze</a>
								</div>
								<div class="yellobtn pre_next">
									<a id="previousOfMinMaxAcre" onclick="callMethodForPageChangeAndProgressBarImage(8, 7)">Previous</a>
								</div>
								<!-- <div class="yellobtn pre_next">
<a id="submit" >Add Additional Group</a>
</div> -->
							</div>
						</div>
						<!-- @end #forward_sales -->
						<div id="crop_insurance_div" class="show_hide_class hidden">
							<div class="form_area">
								<h2 class="field-heading">Crop Insurance</h2>
								<div class="clearfix"></div>
								<div class="info">
									Enter information for each of these crops. <img
										src="images/i-icon.png">
								</div>
								<div class="ques">
									<div class="table-responsive">
										<table class="table table-striped tbl-bordr  tblbrdr"
											cellspacing="0" width="100%">
											<thead>
												<tr class="tblhd text-center add-fieldi">
													<td class="tblbrdr text-center add-fieldi">Crop</td>
													<td class="text-center add-fieldi">Crop Insurance</td>
													<td class="text-center">Type 1</td>
													<td class="text-center">Type 2</td>
													<td class="text-center">Type 3</td>
													<td class="text-center">Acres</td>
													<td class="text-center">Premium</td>
												</tr>
											</thead>
											<tbody id="crop_insurance_table_tbody">
												<!-- <tr class="tblgrn text-center" id="crop_insurance_table_tbody_row__1">
                <td class="tblft1" id="crop_insurance_table_tbody_row_crop_name__1">Crop1</td>
                 <td class="success infotext"><input type="checkbox" id="crop_insurance_table_crop_insurance__1" /></td>
                 <td class="success infotext"><input type="text" id="crop_insurance_table_crop_type_1__1" /></td>
                  <td class="success infotext"><input type="text" id="crop_insurance_table_crop_type_2__1" /></td>
                  <td class="success infotext"><input type="text" id="crop_insurance_table_crop_type_3__1" /></td>
                   <td class="success cinfotext" id="crop_insurance_table_crop_arces__1" ></td>
                   <td class="success infotext" id="crop_insurance_table_crop_premium__1" ></td>
            </tr>
            <tr class="tblbclgrnd text-center">
                <td class="tblft1">Crop2</td>
                <td class="infotext"><input type="checkbox" name=""></td>
                 <td class="infotext"><input type="text"></td>
                  <td class="infotext"><input type="text"></td>
                  <td class="infotext"><input type="text"></td>
                  <td class="infotext"></td>
                  <td class="infotext"></td>
            </tr>
            <tr class="tblgrn text-center">
                <td class="tblft1">Crop3</td>
                 <td class="success infotext"><input type="checkbox"></td>
                 <td class="success infotext"><input type="text"></td>
                  <td class="success infotext"><input type="text"></td>
                  <td class="success infotext"><input type="text"></td>
                  <td class="success infotext"></td>
                  <td class="success infotext"></td>
            </tr>
            <tr class="tblbclgrnd text-center">
                <td class="tblft1">Crop4</td>
                 <td class="infotext"><input type="checkbox"></td>
                 <td class="infotext"><input type="text"></td>
                  <td class="infotext"><input type="text"></td>
                  <td class="infotext"><input type="text"></td>
                  <td class="infotext"></td>
                  <td class="infotext"></td>
            </tr>
            <tr class="tblgrn text-center">
                <td class="tblft1">Cropn</td>
                 <td class="success infotext"><input type="checkbox"></td>
                 <td class="success infotext"><input type="text"></td>
                  <td class="success infotext"><input type="text"></td>
                  <td class="success infotext"><input type="text"></td>
                  <td class="success infotext"></td>
                  <td class="success infotext"></td>
            </tr> -->

											</tbody>
										</table>
									</div>
								</div>
								<div class="clearfix"></div>

								<div class="yellobtn pre_next">
									<a onclick="saveAllFarmInformation()">Analyze</a>
								</div>
								<div class="yellobtn pre_next">
									<a id="submit" onclick="showCropLimitPage()">Previous</a>
								</div>

							</div>
						</div>
						<!-- @end #crop_insurance -->
					</div>
				</div>
		</section>

	</div>
</div>
<div id="add-newresource-popup" class="common-pop-class-for-all">
	<div id="popupContact">
		<!-- Planning Form -->
		<div class="popup_section">
			<img onclick="div_hide6()" src="images/cross.png" id="close">
			<h2 style="padding: 0 0 0 22px; color: #BE922F; font-weight: bold;"
				class="popupheadercrop">Add Resource</h2>
			<div style="padding: 5px 20px;" class="popupform messagepopup">
				<div class="clearfix"></div>
				<div class="addcroppup">
					<label>Resource Name</label> <input type="text" id="resourse_name" />
				</div>
				<div class="addcroppup">
					<label>Unit of measure </label> 
					<!-- <input type="text" id="resourse_unit_name" /> -->
					<div class="select_uom">
					<select id="resourse_unit_name">
						<option value="">--Select UOM--</option>
						<option value="acre-ft">acre-ft</option>
						<option value="acres/hr">acres/hr</option>
						<option value="acre-in">acre-in</option>
						<option value="bales">bales</option>
						<option value="bu">bu</option>
						<option value="cwt">cwt</option>
						<option value="gal/hr">gal/hr</option>
						<option value="gal/acre">gal/acre</option>
						<option value="hrs/mo">hrs/mo</option>
						<option value="hrs/wk">hrs/wk</option>
						<option value="lbs/acre">lbs/acre</option>
						<option value="lit/ha">lit/ha</option>
					</select>
					</div>
				</div>
				<div class="clearfix"></div>
				<div style="margin: 10px 0px;" class="yellobtn submit">
					<a id="submit" onclick="addNewResource()">Add resource</a>
				</div>
			</div>

		</div>
	</div>
</div>
<div id="add-newCropProductionCostsDetails-popup"
	class="common-pop-class-for-all">
	<div id="popupContact">
		<!-- Planning Form -->
		<div class="popup_section">
			<img onclick="div_hide10()" src="images/cross.png" id="close">
			<h2 style="padding: 0 0 0 22px; color: #BE922F; font-weight: bold;"
				class="popupheadercrop">
				<span id="add-cost-component-span-dynamic">Add Cost Component</span>
			</h2>
			<div style="padding: 5px 20px;" class="popupform messagepopup">
				<div class="clearfix"></div>
				<div class="addcroppup">
					<label>Cost Component</label> <input type="text"
						id="crop-optional-cost-components" />
				</div>
				<div class="clearfix"></div>
				<div style="margin: 10px 0px;" class="yellobtn submit">
					<a id="add-new-componemt-button" onclick="addNewCostComponents(1)">Add</a>
				</div>
				<div style="margin: 10px 0px;" class="yellobtn submit">
					<a id="update-componemt-button" onclick="updateCostComponents(2)">Update</a>
				</div>
			</div>

		</div>
	</div>
</div>
<!-- add group popup -->
<div class="common-pop-class-for-all" id="add-new-group-and-crop">
	<div id="popupContact">
		<!-- Planning Form -->
		<!-- @changed - Abhishek	@date - 05-12-2015 -->
		<div class="popup_section">
			<img onclick="div_hide11()" src="images/cross.png" id="close">
			<h2 style="padding: 0 0 0 22px; color: #BE922F; font-weight: bold;" class="popupheadercrop" id="add_group">
				Add Crop Group <span><a id="add_group" class="help_Infromation_PopUp" href="#"><img src="images/i-icon.png"></a></span>
			</h2>
			<h2 style="padding: 0 0 0 22px; color: #BE922F; font-weight: bold;" class="popupheadercrop" id="modify_group">Modify Group</h2>
			<div style="padding: 5px 20px;" class="popupform messagepopup">
				<div class="addcroppup">
					<!-- @New - Abhishek 25-11-2015 -->
					<style type="text/css">
						.yellobtn a{
							padding: 5px 15px;
						}
					</style>

					<label>Group Name</label> <input type="text" name="Grop Name" id="group_name">
					<div class="clearfix"></div>
					<label style="margin-top: 5px;">Select crops for this group</label>
					<div class="group-button-select-fix">
						<div class="multiselect_crop" style="float: left;width: 70%;">
							<select class="multiselect" id="gropofcrop" multiple="multiple"></select>
							<%--<script type="text/javascript">
								$(document).ready(function() {
									$(".multiselect", this.el).multiselect({
										includeSelectAllOption : true
									});
								});
							</script>
							<script type="text/javascript">
								$(document).ready(function() {
									$(".btn-group").click(function() {
										$(this).toggleClass("open");
									});
								});
							</script>--%>
						</div>

						<div class="yellobtn submit">
							<a id="add_group_button" onclick="addNewGroup()">Add Group</a>
							<a id="modify_group_button" onclick="modifyGroup()">Update Group</a>
						</div>
					</div>
				</div>


			</div>

		</div>
	</div>
</div>
<!-- Raja - 14 Dec, 2015 - Add popup on mouseover if Estimated Income is negative -->
<div style="display: none;" id="critical-message-pop-up">
	<div id="popupContact">
		<!-- Planning Form -->
		<div class="popup_section">
			<img src="images/cross.png" onclick="hidePotentialProfitCriticalMessagePopup()" id="close">
			<div class="popupform messagepopup potencial_profit_popup">
				<div class="increase_profit">
					<p>
						<span class="cropNameForPopup"></span> profit per acre (<span id="potentialProfitForPopup"></span>) is less than zero.<br>
						<span class="cropNameForPopup"></span> will not be included in the strategy since it has a negative profit per acre unless you enter a minimum acreage limit for <span class="cropNameForPopup"></span>.
					</p>
				</div>
				<!-- <div class="decrease_profit">
					<p>Decreasing <span id="resourceNameDec"></span> will decreaseEstimated Income by <span id="lossBy1Dollar"></span> for eachdollar removed down to <span id="downResourceLimit"></span></p>
				</div> -->
			</div>
		</div>
	</div>
</div>
<%@ include file="common/right_slider.jsp"%>

<script type="x-jQuery-tmpl" id="additional-crop-income-tbody-tmpl">
    <tr class="tblbclgrnd text-center">
        <td class="tblft1 cropNameSpecific">{{= cropName}}</td>
        <td class="success infotext"><input type="text" class="governmentPaymentsSpecific" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this); calculateAdditionalCropProfit(this, true); return false;"></td>
        <td class="success infotext"><input type="text" class="coProductsSpecific" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this); calculateAdditionalCropProfit(this, true); return false;"></td>
        <td class="success infotext"><input type="text" class="additionalVariableCostSpecific" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this); calculateAdditionalCropProfit(this, true); return false;"></td>
        <td class="success infotext"><input type="text" class="additionalIncomeSpecific" onkeypress="return isValidNumberValue(event)" onchange="addCommaSignWithDollar(this); return false;" disabled></td>
    </tr>
</script>

<script type="x-jQuery-tmpl" id="optional-crop-info-table-tmpl">
	<div class="table-responsive" name="optionalCropInformationDetail_{{= cropName}}">
		<table class="table table-striped tbl-bordr  tblbrdr" cellspacing="0" width="100%">
			<thead>
			   <tr class="tblhd add-fieldi">
				   <td class="text-center">Modify</td>
				   <td class="tblbrdr add-fieldi">Component</td>
				   <td class="text-center add-fieldi">Units</td>
				   <td class="text-center">$ per Unit</td>
				   <td class="text-center">$ per Acre</td>
			   </tr>
			</thead>
			<tbody>
				{{each(key, optionalCropInfoHeads) optionalCropInfoHeadsArr}}
				   <tr class="tblgrn">
					   <td class="tblft1 text-center"><input type="checkbox"></td>
					   <td class="tblft1 text-left">{{= optionalCropInfoHeads}}</td>
					   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td>
					   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td>
					   <td class="success infotext"></td>
				   </tr>
				{{/each}}
               <%--<tr class="tblbclgrnd">
                   <td class="tblft1 text-center"><input type="checkbox"></td>
                   <td class="tblft1">Equipment</td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td>
                   <td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this); addCommaSignWithDollar(this)"></td>
                   <td class="infotext"></td>
               </tr><tr class="tblgrn">
                   <td class="tblft1 text-center"><input type="checkbox"></td>
                   <td class="tblft1">Fertilizer</td><td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td>
                   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td>
                   <td class="success infotext"></td>
               </tr><tr class="tblbclgrnd">
                   <td class="tblft1 text-center"><input type="checkbox"></td><td class="tblft1">Financing</td>
                   <td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td>
                   <td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td>
                   <td class="infotext" ></td>
               </tr><tr  class="tblgrn">
                   <td class="tblft1 text-center"><input type="checkbox" ></td>
                   <td class="tblft1" >Herbicide</td>
                   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td>
                   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)" ></td>
                   <td class="success infotext"></td>
               </tr><tr class="tblbclgrnd">
                   <td class="tblft1 text-center"><input type="checkbox" ></td>
                   <td class="tblft1" >Insecticide</td>
                   <td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td>
                   <td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)" ></td>
                   <td class="infotext"></td>
               </tr><tr class="tblgrn">
                   <td class="tblft1 text-center"><input type="checkbox" ></td>
                   <td class="tblft1">Irrigation</td>
                   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)" ></td>
                   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td>
                   <td class="success infotext" ></td>
               </tr><tr class="tblbclgrnd">
                   <td class="tblft1 text-center"><input type="checkbox" ></td>
                   <td class="tblft1" >Labor</td>
                   <td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)" ></td>
                   <td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)" ></td>
                   <td class="infotext" ></td>
               </tr><tr class="tblgrn">
                   <td class="tblft1 text-center"><input type="checkbox" ></td>
                   <td class="tblft1">Micro Nutrients</td>
                   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)" ></td>
                   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)" ></td>
                   <td class="success infotext" ></td>
               </tr><tr class="tblbclgrnd">
                   <td class="tblft1 text-center"><input type="checkbox" ></td>
                   <td class="tblft1" >Professional Services</td>
                   <td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td>
                   <td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)" ></td>
                   <td class="infotext"></td>
               </tr><tr class="tblgrn">
                   <td class="tblft1 text-center"><input type="checkbox"></td>
                   <td class="tblft1">Rent</td>
                   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td>
                   <td class="success infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td>
                   <td class="success infotext"></td>
               </tr><tr class="tblbclgrnd">
                   <td class="tblft1 text-center"><input type="checkbox"></td>
                   <td class="tblft1">Seed</td><td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithOutDollar(this)"></td>
                   <td class="infotext"><input type="text" name="Crop" onkeypress="return isValidNumberValue(event)" onchange="getCalculateValue(this);addCommaSignWithDollar(this)"></td>
                   <td class="infotext"></td>
               </tr>--%>
			</tbody>
			<tfoot>
				<tr class="tblgrn">
				   <td class="tblft1 optncal" colspan="2">Calculate</td>
				   <td class="success"></td>
				   <td class="success"></td>
				   <td class="success"></td>
				</tr><tr class="tblft text-center">
				   <td class="tblft1" colspan="2">Total Variable Cost per Acre: </td>
				   <td><input type="hidden"> </td>
				   <td></td>
				   <td class="optndoller">$0.00</td>
				</tr>
			</tfoot>
        </table>
        </div>
	
</script>