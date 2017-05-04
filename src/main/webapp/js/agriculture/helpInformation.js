/**
 * @changed - date - 29-12-2015
 * @description - Changed all help text(Commented by multiline comment)
 */
/*var farm_Information_planBy_msg1 = "Specify whether you manage your farm as distinct fields or as a single tract of land or you can evaluate both options.";*/
var farm_Information_planBy_msg1 = "You can plan by <b>Fields</b> or by <b>Acres</b>.";
/*var farm_Information_planBy_msg2 = "Select Fields if you manage your farm by fields and do not want to mix crops in a field. <span id='more_content' class='more_content' onclick=\"callMore('more_content')\">More</span> ";*/
var farm_Information_planBy_msg2 = "Select <b>Fields</b> if you manage your farm by fields and only want one crop assigned per field. <span id='more_content' class='more_content' onclick=\"callMore('more_content')\">More</span> ";
/*var farm_Information_planBy_msg3 = "Select Acres if you would like to consider your farm as a single tract that can be subdivided into various combinations of crops and acreage. <span id='more_content' class='more_content1' onclick=\"callMore('more_content1')\">More</span> ";*/
var farm_Information_planBy_msg3 = "Select <b>Acres</b> if you would like to develop cropping strategies without regard to fields.. <span id='more_content' class='more_content1' onclick=\"callMore('more_content1')\">More</span> ";

/*var farm_Information_planBy_msg4 = "Select Both if you would like to plan by Fields and by Acres. <span id='more_content' class='more_content2' onclick=\"callMore('more_content2')\">More</span> ";*/

/*var farm_Information_planBy_msg5 = "<div id='more_div' style='display:none' class='more_div'>PlantingProfits® will generate strategies that assign crops to specific fields based your field/crop options.  You can split fields during the analysis to create more crop/field options, greater flexibility and higher Estimated Income.</div>";*/
var farm_Information_planBy_msg5 = "<div id='more_div' style='display:none' class='more_div'>PlantingProfits® will generate strategies that assign crops to specific fields based your field/crop options and other considerations such as crop acreage limits. You can split fields during the analysis to create more crop/field options and perhaps higher Estimated Income – depending on other limitations. </div>";
/*var farm_Information_planBy_msg6 = "<div id='more_div' style='display:none' class='more_div1'>PlantingProfits® will treat your farm as a single field capable of growing several different crops.  Cropping strategies will be generated without assigning crops to specific fields.  This assignment will be left up to you based on which crops will do best in which fields in a given year and logistical considerations.  This is the most flexible way to plan.  After planning by acres you can plan by fields.</div>";*/
var farm_Information_planBy_msg6 = "<div id='more_div' style='display:none' class='more_div1'>PlantingProfits® will generate cropping strategies in terms of acreage without assigning crops to specific fields. This assignment will be left up to you based on which crops will do best in which fields in a given year and logistical considerations. This is the most flexible way to plan.  After planning by acres you can plan by fields and compare the results.  This can help you decide if it’s worth splitting one or more fields.</div>";


/*var farm_Information_planBy_msg7 = "<div id='more_div' style='display:none' class='more_div2'>You can create strategies in terms of fields and acres and compare the results in terms of Estimated Income, crops/acreage, resources and risk factors to better understand the effects of splitting or not splitting one or more fields.</div>";*/

var farm_Information_irrigate_msg1 = "Indicate if you irrigate none, all or some of your farm. <span id='more_content' class='more_content3' onclick=\"callMore('more_content3')\">More</span> ";
var farm_Information_irrigate_msg2 = "<div id='more_div' style='display:none' class='more_div3'><p>If you irrigate, you can create crop strategies that maximize profit and minimize the amount of water used or a quantity of water that does not exceed your water allocation.</p>" +
	"<p>Similarly irrigation capacity can be evaluated and traded-off against profit.</p>" +
	"<p>You can consider irrigation at the start of the planning process or factor it in after you have developed one or two strategies that do not take into consideration water availability.</p></div>";

var farm_Information_storage_msg1 = "Indicate if you have on farm storage.  <span id='more_content' class='more_content4' onclick=\"callMore('more_content4')\">More</span> ";
var farm_Information_storage_msg2 = "<div id='more_div' style='display:none' class='more_div4'>If you have on-farm storage, you can create crop strategies that take into account the amount of on-farm crop storage and the crops to be stored.</div>";

var farm_Information_forward_msg1 = "Select Yes if you would like PlantingProfits® to help you evaluate crop forward sales. <span id='more_content' class='more_content5' onclick=\"callMore('more_content5')\">More</span> ";
var farm_Information_forward_msg2 = "<div id='more_div' style='display:none' class='more_div5'><p>Select Yes you have already sold some of your crop.</p><p>Select Yes if you are considering forward selling some or more of your crop.</p><p>Select No if you are not considering forward selling any (or any more) of your crop.</p></div>";

var farm_Information_insurance_msg1 = "Select Yes if you would like PlantingProfits® to help you evaluate crop insurance products.  <span id='more_content' class='more_content6' onclick=\"callMore('more_content6')\">More</span> ";
var farm_Information_insurance_msg2 = "<div id='more_div' style='display:none' class='more_div6'><p>Select Yes you have already signed up for crop insurance for the upcoming season or are planning to.  PlantingProfits® will help you evaluate the best type of crop insurance for the upcoming season.</p><p>Select No if you are not considering enrolling in a crop insurance.</p></div>";

/*var Crops_and_Crop_Information_msg1 = "Select which crops you are considering growing during the upcoming season.";*/
var Crops_and_Crop_Information_msg1 = "Select the crops you are considering growing for the upcoming season.";
var Crops_and_Crop_Information_msg2 = "Crops are grouped into two categories:  Field Crops and Fruit/Vegetable Crops.";
/*var Crops_and_Crop_Information_msg3 = "You can add/create any type of crop.  <span id='more_content' class='more_content7' onclick=\"callMore('more_content7')\">More</span> ";*/
var Crops_and_Crop_Information_msg3 = "You can define and add any type of crop.  <span id='more_content' class='more_content7' onclick=\"callMore('more_content7')\">More</span> ";
/*var Crops_and_Crop_Information_msg4 = "<div id='more_div' style='display:none' class='more_div7'><p>In PlantingProfits® different varieties of the same crop with different expected yields are considered different crops since expected revenue is different.</p><p>Similarly, different varieties of the same crop with a different expected price is considered a different crop since the expected revenue is different.</p>
<p>Use of different inputs (products or amounts) for a given crop type may produce different yields and have different production costs and so are considered different crops.</p><p>Similarly, different tillage patterns could be treated as different crops.</p><p>Crops grown using conservation practices can be considered a different crop than conventional practices.</p><p>An irrigated crop is considered a different crop than a dryland crop since the expected yield, yield variance, variable production cost and use of water resource are all different.</p></div>";*/
var Crops_and_Crop_Information_msg4 = "<div id='more_div' style='display:none' class='more_div7'><p>In PlantingProfits® each crop can have many variations that makes them different.  You can define an individual crop if there is a difference in any one of the following:</p>" +
	"<ul>" +
	"<li>Expected yield and minimum and maximum yields due to different varieties, crop protection products/practices, rotations, planting dates</li>" +
	"<li>Expected price and minimum and maximum prices due to quality, variety, forward sales</li>" +
	"<li>Differences in per acre production costs</li>" +
	"<li>Conservation practices versus a different tillage practice</li>" +
	"<li>Crops on irrigated land vs. dryland</li>" +
	"<li>Crop on your land vs. rented land</li>" +
	"<li>Insured crops vs. uninsured crops and level of insurance</li>" +
	"<li>Need for/use of storage</li>" +
	"<li>Or almost any other type of discriminator</li>" +
	"</ul></div>";

/*var Plan_by_Fields_msg1 = "You indicated that you manage your farm by fields.";*/
/**
 * @added - Jyoti
 * @date - 28-12-2016
 * @desc - added according to slide# 9 of 12222016
 */
var Plan_by_Fields_msg1 = "Enter your field information.";
/*var Plan_by_Fields_msg2 = "Planting Profits will generate the most profitable strategy by assigning one crop to each field based on your preferences and constraints.";*/
/*var Plan_by_Fields_msg2 = "PlantingProfits® will generate the most profitable strategy by assigning one crop to each field based on your preferences and constraints.  It will not split the fields.";*/
var Plan_by_Fields_msg2 = "Since you selected to plant by fields, Planting Profits will generate the most profitable combination of crops and fields and assign one crop to each field based on your preferences and constrains. It will not split fields.";
/*var Plan_by_Fields_msg3 = "Indicate the last crop planted in each field.  This is optional.  It will display when you are in the next screen so you can see last year's crop-field combinations.  This may help you decide which crops you would like to consider planting in each field.";*/
/**
 * @changed : Jyoti Verma
 * @date : 24-04-2017
 * @type {add 'an' before indicator word}
 */
var Plan_by_Fields_msg3 = "Optional:  Indicate the last crop planted in each field. It will display an indicator on the next screen so you can see last year's crop-field combinations as a reference.";
/*var Plan_by_Fields_msg4 = "Mark the field as Fallow, if you do not want to make the field available for planting.  You can unmark the field and make it available for planting to see the impact on your profits  of fallowing the field";*/
var Plan_by_Fields_msg4 = "Check the box Fallow, if you do not want to make the field available for planting this season. You can unmark the field and make it available for planting to see the impact on your profits from fallowing the field.";
/*var Plan_by_Fields_msg5 = "Mark the field as Divide, if you would consider splitting the field in order to increase profits or to create physical separation.";*/
var Plan_by_Fields_msg5 = "Optional:  Mark the field as Divide, if you would consider splitting the field in order to increase profits or to create physical separation.";
var Plan_by_Fields_msg6 = "To split a field, click Add Field to add a field, give it a name and divide up the acres between the field you are splitting and the new field you are creating. The total acres should remain the same.";
/*var Plan_by_Fields_msg7 = "Indicate if the field is irrigated.<P>This will be used later in the analysis of you want to include water as a resource during planning.</P>";*/
/**
 * @changed : Jyoti Verma
 * @date : 24-04-2017
 * @type {Reword : the analysis 'if' as slide G027 of 04232017}
 */
var Plan_by_Fields_msg7 = "Optional:  Indicate if the field is irrigated.<P>This information will be used later in the analysis if you want to include water as a resource during planning.</P>";

/*var Crop_Information_Details_msg1 = "For each crop enter the Expected Yield.";
var Crop_Information_Details_msg2 = "You can also enter the Minimum and Maximum yield range for use in risk analysis.<P>You can enter the Minimum and Maximum yields now or later.</P>";*/
// var Crop_Information_Details_msg1 = "Enter the Expected Yield for each crop.  This is your yield forecast and is used to compute a crop’s estimated Profit per acre.";
// var Crop_Information_Details_msg2 = "<p>You can simulate the variability of yield by entering Min and Max Yields.  Yield is algorithmically varied thousand of times between the Min or Max Yields to factor into Estimated Profit those unknowns associated with yield forecasting.</p>";

var Crop_Information_Details_yield = "<p>Yield forecasts are used to compute estimated profit per acre.  There are two types of yield forecasts.</p>" +
	"<p>1.  Enter the Expected Yield for each crop.</p>" +
	"<p>2.  Enter Expected, Min and Max Yields to simulate yield variability. Yield is automatically varied 10,000x between the Min and Max values to generate an estimated yield that incorporates yield variance.</p>";

/*var Crop_Information_Details_msg3 = "For each crop enter the Expected Price.";
var Crop_Information_Details_msg4 = "You can also enter the Minimum and Maximum price range for use in risk analysis.<p>You can enter the Minimum and Maximum price now or later.</p>";*/
// var Crop_Information_Details_msg3 = "Enter the Expected Price for each crop.  This is your price forecast and is used to compute a crop’s estimated Profit per acre.";
// var Crop_Information_Details_msg4 = "<p>You can simulate the variability of price by entering Min and Max Prices.  Price is algorithmically varied thousand of times between the Min or Max Yields to factor into Estimated Profit those unknowns associated with price forecasting.</p>";

var Crop_Information_Details_price = "<p>Price forecasts are used to compute estimated profit per acre.  There are two types of price forecasts.</p>" +
	"<p>1.  Enter the Expected Price for each crop.</p>" +
	"<p>2.  Enter Min and Max Prices to simulate price variability.  Price is automatically varied 10,000x between the Min and Max values to generate an estimated price that incorporates price variance.</p>";

var Crop_Information_Details_msg5 = "For each crop enter the Expected Price.";
var Crop_Information_Details_msg6 = "You can also enter the Minimum and Maximum price range for use in risk analysis.";

var physical_location = "Specifies the farm location.  Determines which state and county so we can provide you a reference to local agricultural statistics if you need to verify price and yield verifications.";

/*var plan_by_acres = "<p>When you plan by Acres, Planting Profits considers your farm as a single tract that can be subdivided into various combinations of crops and acreage to maximize profitability.</p><p>Planting Profits will treat your farm as a single field capable of growing several different crops.</p>
<p>Cropping strategies are generated without assigning crops to specific fields. (To plan by Fields return to the Farm Information page; Plan by:  Fields) Planning by Acres is the most flexible way to make a first pass in planning.</p>
<p>If you don't plan by fields, the exact crop/acreage assignment will be left up to you based on which crops do best in which fields in a given year and other logistical considerations.</p><p>After planning by acres you can plan by fields and compare.</p>";*/
var plan_by_acres = "<p>When you plan <b>by Acres</b>, PlantingProfits® considers your farm as a single tract that can be subdivided into various combinations of crops and acreage to maximize profitability.</p>" +
	"<p>PlantingProfits® will treat your farm as a single field capable of growing several different crops.</p><p>Cropping strategies are generated without assigning crops to specific fields.  Planning <b>by Acres</b> is the most flexible way to make a first pass in planning.</p>" +
	"<p>If you don't plan by fields, the exact crop/acreage geo-placements are not specified and are left up to you based on which crops do best in which fields in a given year, based on last year and other logistical considerations.</p>" +
	"<p>After planning <b>by Acres</b> you can plan <b>by Fields</b> and compare.  If there are big differences in expected income you may want to consider splitting one or more fields.</p>";

/*var variable_production_cost = "For each crop enter the Variable Production Cost.Variable Production Costs are the per acre costs associated with planting/raising/harvesting a specific crop.They do not include land costs, equipment payments, etc.<span id='more_content' class='more_content8' onclick=\"callMore('more_content8')\">More</span> ";
var variable_production_cost_msg1 = "<div id='more_div' style='display:none' class='more_div8'><p>You should be able to get an estimate of your variable production costs from your farm records.You can also consult county extension or your local input supply dealer for estimated variable production costs for each crop grown in your area.</p>
<p>Remember that these costs can differ based or your farm, seed varieties, crop protection programs, etc.</p>
<p>Planting Profits also provides you a worksheet to calculate variable production costs for each crop.</p></div>";*/
var variable_production_cost = "Enter the Variable Production Cost for each crop. Variable Production Costs are the per acre costs associated with planting/raising/harvesting each crop. It does not include fixed costs like land payments, equipment payments, etc. <span id='more_content' class='more_content8' onclick=\"callMore('more_content8')\">More</span> ";
var variable_production_cost_msg1 = "<div id='more_div' style='display:none' class='more_div8'><p>You should be able to get a good estimate of your variable production costs from your farm records. You can also consult county extension or your local input supply dealer for estimated variable production costs for crops/varieties grown in your area.</p>" +
	"<p>Remember that these costs can differ based or your farm, seed varieties, crop protection programs, etc.</p><p>Crop insurance premiums should also be included.</p>" +
	"<p>PlantingProfits® also provides you a worksheet to calculate variable production costs for each crop. </p></div>";

/*var profit_per_acre = "Profit per acre is calculated as follows:</br>(Expected Yield x Expected Price) – Variable Production Cost</br><p>PlantingProfits® will always try to maximize the acreage assigned to the most profitable crop.</p>" +
	"<p>If a crop has an Expected Profit less than $0 (i.e. a per acre loss) PlantingProfits® will not assign any acreage to that crop unless there is a minimum acreage limit</p>";*/
var profit_per_acre = "Estimated Income per acre is calculated as follows:</br>(Expected Yield x Expected Price) – Variable Production Cost</br><p>PlantingProfits® will always try to maximize the acreage assigned to the most profitable crop.</p>" +
	"<p>If a crop has an Estimated Income less than $0 (i.e. a per acre loss) PlantingProfits® will not assign any acreage to that crop unless there is a minimum acreage limit.</p>";

var Optional_Crop_Production_Costs_Details = "<p>This is an optional worksheet to use if you don’t know your variable production costs.  You can use the worksheet to estimate the cost per acre for a given crop.</p>" +
	"<p>It does not include fixed costs like land payments, equipment payments, etc..  These costs are independent of the amount of a crop’s acreage.</p>" +
	"<p>You can add or delete any cost component.</p>";

var crop_field_choices = "<p>Indicate which crops you are considering planting in which fields.</p>" +
	"<p>You can make all crops eligible to be planted in all fields or you can match your rotations or other management considerations to specific crops and specific fields.</p>" +
	"<p>If a crop does not do well in a particular field you can take it out of consideration for planting a particular crop.</p>" +
	"<p>If for a given field there is only one crop choice just select that one crop and PlantingProfits® will not consider any other crops for that field.</p>" +
	"<p>If there are no crops selected as candidates for a field, you will receive a warning message.  This is similar to setting a field to fallow, PlantingProfits® will not assign any crops to the field.</p>";

/*var resourse_manage = "<p>Resources are needed to grow crops.  Planting Profits will quickly identify the most profitable assignment of crops to acres/fields based on available resources (and other considerations).</p><p>In Planting Profits, resources create constraints.  Constraints are often a limiting factor in profitability.</p><p>Resources generally include land, capital, labor, water, equipment or any other controllable input that may limit what you plant.  Resources are generally controllable.</p>
<p>Resources other than land and capital are optional and can be omitted from the planning process.</p><p>Or you can begin planning by only considering land and capital and add other resources as your plan evolves.</p><p>Resource are a powerful part of using Planning Profits.  Planning Profits will help insure that your farm resources (land, capital, etc.) are used most efficiently. <span id='more_content' class='more_content9' onclick=\"callMore('more_content9')\">More</span> </p>";
var resourse_manage_msg1 = "<div id='more_div' style='display:none' class='more_div9'><p>Planting Profits has two pages for defining resources.</p><p>The Resource page comes first.  It is used to select which resources to include in the planning process.</p><p>Land and Capital are mandatory resources and are used in all cases.</p><p>Other resources can be added initially or later.</p><p>Resources and resource quantities can be added, changed or deleted during the analysis.</p>
<p>Planting Profits will develop different strategies with different amounts of income and different crop and acreage/field assignments based on the available resources. Consider including resources that generally limit how much of each crop you plant or your overall crop mix.</p><p>Keep in mind that if you develop a particular assignment of crops and acreage/field and you change the available resources you must keep this in mind when comparing these different crop mixes to make a fair comparison.</p><p>Available resources are expressed in the appropriate amount (i.e. unit of measure such as dollars, acres for capital and land, respectively).
 Resource amounts can be fixed over the length of the growing season such as with land or they can be over a shorter period such as the amount of available labor each week or equipment (combine) capacity each week or a quantity of water available over the growing season.</p><p>Capital is an important resource and is mandatory so additional information is provided.  In Planting Profits, Capital is the amount of</p><p>Planting Profits can create resources for any controllable resource such as crop storage, available water, irrigation capacity, labor, equipment.</p></div>";*/
var resourse_manage = "Resources are needed to grow crops. PlantingProfits® will quickly identify the most profitable assignment of crops to acres/fields based on available resources (and other considerations).<span id='more_content' class='more_content9' onclick=\"callMore('more_content9')\">More</span> </p>";
var resourse_manage_msg1 = "<div id='more_div' style='display:none' class='more_div9'><p>In PlantingProfits® , resources create constraints which are often a limiting factor in profitability.</p>" +
	"<p>Resources generally include land, capital, labor, water, equipment or any other controllable input that may limit what crops and how much of a crop you plant. Resources are generally controllable.</p>" +
	"<p>Land and Capital are the minimum resources needed to model the farming operation and are required inputs.</p><p>Resources other than Land and Capital are optional and can be omitted completely from the planning process, or you can begin your planning by only considering Land and Capital and then progressively add other resources as your plan evolves.</p>" +
	"<p>You can <b>Add</b> any type of resource.  To add a resource you need: 1) the amount available, 2) the unit of measure (e.g. bu, hrs, hrs/wk, in/yr) and 3) the per acre use of the resource by one or more of the crops under consideration.</p><p>Resource can be used in the analysis to help insure that your farm resources (land, capital, labor, etc.) are used most efficiently.</p></div>";

var amount_Available = "<p>Enter the available amount of the resource.</p><p>You don’t need to enter the amount of Land since this was previously entered either as:  1) a single amount of acreage or 2) when you defined the individual fields.  It cannot be changed on this screen.  To change the amount of available Land you will need to go back to the <b>Plan by Acres</b> screen (if planning by acres) or to the <b>Field Information</b> screen (if planning by fields).</p>" +
	"<p>Capital is the amount of money available for the cropping the season. It can be the amount of your line of credit, last year’s budget, or some other amount either low or high.  It can be easily changed.  PlantingProfits® will identify combinations of crops/acreage that maximize profit based on the amount of capital available and let you know if it is limiting profitability and by how much (based on your crop choices, rotation options, acreage limits and other factors).</p><p>Since resources are controllable their amounts can be changed during the analysis.</p>";

/**
 * @added - Abhishek
 * @date - 23-01-2016
 * @desc - added according to slide# 4 of 01042015
 */
var resourceUnitOfmeasure = "<p>Select the unit of measure for the quantity of the resource.  For example, if the resource is storage it could be bushels (bu).  For weekly labor availability, it could be hrs/wk.</p>";

/*var resourse_usage = "<p>Crop Resource Usage is the quantity of each resource (from the previous page) needed to produce one acre of each crop.</p><p>The second Resource page shows the crops in the rows and the crop resource use in the columns.  The value is how much of the resource is used to grow the crops.</p><p>On the Crop Resource Usage page, there is a table that contains one row for each crop and one a column for each resource included in the analysis.  <span id='more_content' class='more_content10' onclick=\"callMore('more_content10')\">More</span> </p>";
var resourse_usage_msg1 = "<div id='more_div' style='display:none' class='more_div10'><p>A column for Capital is always present since Capital is always included in the analysis.</p><p>For the Capital resource, the amount used per crop is the Variable Production Cost which was previously entered with the other crop information.  The Variable Production Cost is the  is the amount of money required to grow one acre of the crop.  It includes seed, fertilizer, crop protection products and any other cost incurred in growing that particular crop.
It does not include fixed costs such as land costs, irrigation systems, buildings and equipment which are fixed and depreciated regardless of what crops you plant.</p><p>The Variable Production Cost cannot be changed on the Crop Resource Usage page. Go to the Detailed Crop Information page to change it for one or more crops.</p></div>";*/
var resourse_usage = "<p>Crop Resource Usage is the quantity of each resource (from the previous page) needed to produce one acre of each crop.</p><p>The <b>Crop Resource Usage</b> screen provides a table that contains one row for each crop choice and one  column for each resource from the <b>Resources</b> screen.</p>" +
	"<p>Capital is always the first column in the table and is always included in the analysis.  (All other resources are optional.) <span id='more_content' class='more_content10' onclick=\"callMore('more_content10')\">More</span> </p>";
var resourse_usage_msg1 = "<div id='more_div' style='display:none' class='more_div10'>" +
	"<p>For the Capital resource, the amount used per crop is the Variable Production Cost which was previously entered with the price and yield crop information. The Variable Production Cost is the cost to grow one acre of the crop. It includes inputs such as seed, fertilizer, crop protection products and any other costs incurred in growing that particular crop. It does not include fixed costs such as land costs, irrigation systems, buildings and equipment which are fixed and depreciated regardless of what crops you plant. " +
	" The Variable Production Cost cannot be changed on the Crop Resource Usage page.  Go to the Detailed Crop Information page to change the variable production costs for one or more crops.</p></div>";

/**
 * @changed - Abhishek
 * @date - 23-01-2016
 * @desc - changed according to slide# 5 in file 01042015
 */
/*var variable_production_cost_resourse = "<p>A column for Capital is always present since Capital is always included in the analysis.</p><p>For the Capital resource, the amount used per crop is the Variable Production Cost which was previously entered with the other crop information.  The Variable Production Cost is the  is the amount of money required to grow one acre of the crop.  It includes seed, fertilizer, crop protection products and any other cost incurred in growing that particular crop.
It does not include fixed costs such as land costs, irrigation systems, buildings and equipment which are fixed and depreciated regardless of what crops you plant.</p>
<p>The Variable Production Cost cannot be changed on the Crop Resource Usage page.
Go to the Detailed Crop Information page to change it for one or more crops.</p>";*/
var variable_production_cost_resourse = "<p>These are the Variable Production Costs ($/acre) entered previously for each crop</p>";
/**
 * @added - Abhishek
 * @date - 23-01-2016
 * @desc - added according to slide# 5 in file 01042015
 */
var fieldDifference = "<p>If there are material differences crop yields across fields select Field Differences to enter the variability.</p><p>If there are material differences in resource usage for a given crop in a given field select Field Differences to enter the variability.</p>";


var amount_variablity_yield = "<p>If there are material differences in crop yields or crop resource use between fields you can account for this here.</p><p>PlantingProfits® will consider the differences in expected profit on a field-by-field basis based on differences in yield (which impacts revenue) and variable production costs.  PlantingProfits® will make the most profitable crop/field assignments based on this information as well as other factors such as crop acreage limits.</p>" +
	"<p>Note:  You will only see combinations of crops and fields that are identified on the Crop/Field Choices page.</p><p>To use Field Differences you will need some field-specific information.</p>";

/**
 * @changed - Abhishek
 * @date - 23-01-2016
 * @desc - changed according to slide# 3 in file 01042015
 */
/*var yield_Difference = "If there are material differences in crop yields between fields you can account for them here.";
var resource_usage_Difference = "If there are material differences in crop resource use you can account for them here.";*/
var yield_Difference = "<p>If there are material differences in estimated crop yields for a given crop in a given field, you can account for this here.</p>";
var resource_usage_Difference = "<p>If there are material differences in crop resource use for a given crop in a given field, you can account for this here.</p>";

/*var forward_sale = "<p>Forward sales contracts are an important management tool that can be modeled in Planting Profits.  Contracts can be evaluated as either Firm or proposed.  Proposed means not firm.  </p><p>For Firm contracts, Planting Profits will always assign at least the required amount of acreage to the contracted crop even if it is more profitable than planting other crops.
For Firm contracts, there must enter adequate Resources (such as land and capital) to produce the contract quantity of the crop. </p><p>For proposed contracts not marked Firm, Planting Profits will only assign acreage to the contracted crop if it is more profitable than another crop.
 Proposed contracts can be partially filled as part of the most profitable combination of crops and acreage/fields.  <span id='more_content' class='more_content11' onclick=\"callMore('more_content11')\">More</span> </p> ";
var forward_sale_msg1 = "<div id='more_div' style='display:none' class='more_div11'><p>To create a contract:</p><p>  Enter the proposed price.</p><p>Enter the proposed amount to be sold in either quantity or acres. Planting Profits will automatically calculate either the quantity or acreage, using the Expected Yield previously entered.</p><p>Check Firm if you are committed to the contract or you have already sold some of your crop to be grown during the planning period.</p>
<p>Enter an Upper Limit for the amount of contract crop or acreage.  This allows Planting Profits to assign an amount of the crop beyond the contract quantity (by the % in the Upper Limit).  This provides flexibility for Planting Profits to fit the contract quantity into the most profitable combination of fields given all of your other competing considerations.</p> </div>";*/
var forward_sale = "<p>Forward Sales is an important risk management tool for many growers that can be modeled in PlantingProfits®. </p>" +
	"<p>Forward Sales can be evaluated as either <b>Firm</b> or <b>Proposed</b>.  For a given crop type (e.g. corn) forward sales are considered a different crop in PlantingProfits®.  " +
	"The forward sales contract reduces the price variance of a similar crop that is not contracted.  The impact on estimated income and risk profile can be evaluated for each strategy. </p>" +
	"<p>For <b>Firm</b> contracts, PlantingProfits® will always assign at least the required amount of acreage to the crop for forward sales even if it is less profitable than planting another crop. " +
	"For <b>Firm</b> contracts (like minimum crop acreage), there must be adequate Resources (such as land and capital) to produce the contract quantity of the crop.</p>" +
	// "<p><b>Proposed</b> forward sales means a not <b>Firm</b>.</p>" +
	"<p><b>Proposed</b> forward sales represents forward sales opportunities to be evaluated.</p>" +
	"<p>For <b>Proposed</b> forward sales, PlantingProfits® will only assign acreage to a forward sold crop if it is more profitable than another crop. " +
	"<b>Proposed</b> forward sales can be partially filled as part of the most profitable combination of crops and acreage/fields. <span id='more_content' class='more_content11' onclick=\"callMore('more_content11')\">More</span></p>";
/**
 * @changed - Abhishek
 * @date - 03-02-2016
 * @desc - chanegd according to slide#10 of 01282016
 */
/*var forward_sale_msg1 = "<div id='more_div' style='display:none;' class='more_div11'><p>To create a contract:</p><p>  Enter the proposed price.</p><p style='color: #ff0000'>Test this.</p><p style='color: #ff0000'>Enter an Upper Limit for the amount of contract crop or acreage. This allows Planting Profits to assign an amount of the crop beyond the contract quantity (by the % in the Upper Limit).
This provides flexibility for Planting Profits to fit the contract quantity into the most profitable combination of fields given all of your other competing considerations.</p></div>";*/
var forward_sale_msg1 = "<div id='more_div' style='display:none;' class='more_div11'><p>To create a <b>Firm</b> or a <b>Proposed</b> contract:</p><p>  Enter the proposed price.</p><p>Enter either the amount based on yield units (such as bushels) or the acres.</p></div>";

/*var forward_sale_proposed = "<p>For signed contracts, Enter the contract price.  Check to make sure the Yield Units are consistent with the contract price.</p><p>For Firm or proposed contracts, Enter the offered price.  Check to make sure the Yield Units are consistent with the contract price.</p>";*/
var forward_sale_price = "<p>For Firm or Proposed forward sales enter the <b>Expected Price</b>.  Check to make sure the <b>Yield Units</b> are consistent with the contract price units.</p>";
/**
 * @changed - Abhishek
 * @date - 23-01-2016
 * @desc - changed according to slide# 15 of 01042015
 */
/* var forward_sale_amount = "<p>Enter the amount of crop for forward sales in either Quantity or Acres.</p><p>If a Quantity is entered, PlantingProfits® will automatically calculate the acreage using the Expected Yield previously entered.</p>";
var forward_sale_acres = "<p>Enter the amount of crop for forward sales in either Quantity or Acres.</p><p>If Acres is entered, PlantingProfits® will automatically calculate the quantity of the crop using the Expected Yield previously entered.</p>";
*/
var forward_sale_amount = "<p>Enter the quantity of crop for forward sales in Amount or Acres.</p><p>If an Amount is entered, PlantingProfits® will automatically calculate the Acres using the Expected Yield previously entered.</p>";
var forward_sale_acres = "<p>Enter the quantity of crop for forward sales in Amount or Acres.</p><p>If Acres is entered, PlantingProfits® will automatically calculate the Amount using the Expected Yield previously entered.</p>";


/**
 * @changed - Abhishek
 * @date - 23-01-2016
 * @desc - changed according to slide# 7 of 01042015
 */
/*var forward_sale_proposed = "<p>If you are would like to evaluate a forward sales opportunity check the box labeled <b>Proposed</b>.  For <b>Proposed</b> sales, PlantingProfits® will only assign acreage to the crop if it is more profitable than another crop as well as other considerations (such as crop/field choices).</p><p style='color: #ff0000'>Test this</p><p style='color: #ff0000'>Proposed forward sales can be partially filled as part of the most profitable combination of crops and acreage/fields.</p>";*/
/**
 * @changed - Jyoti Verma
 * @date - 24-04-2017
 * @type {changed according to slide G032 of 04232017}
 */
var forward_sale_proposed = "<p>If you would like to evaluate a forward sales opportunity check the box labeled <b>Proposed</b>.  For <b>Proposed</b> sales, PlantingProfits® will only assign acreage to the crop if it is more profitable than another crop as well as other considerations (such as crop/field choices).</p>";
/**
 * @changed - Abhishek
 * @date - 23-01-2016
 * @desc - changed according to slide# 14 of 01042015
 */
/*var forward_sale_firm_td = "<p>If you are committed to the sale or you have already made a forward sale of your crop, check the box labeled <b>Firm</b> for the crop to be forward sold.</p>";*/
var forward_sale_firm_td = "<p>If you are committed to the sale or you have already made a forward sale of your crop, check the box labeled <b>Firm</b> for the crop to be forward sold.</p>" +
	"<p>Remember:  You can always re-evaluate this decision at any time during the analysis.</p>";

var forward_sale_firm = "<p>Firm Contracts - If you intend to forward sell a crop (or if you have already signed one or more contracts, enter the contract price, quantity or acreage and check Firm. </p>" +
	"<p>PlantingProfits® uses the Expected Yield previously entered to calculate either quantity or acres depending on which one you enter.</p>" +
	"<p>All cropping strategies developed will include enough acreage of that crop to fill the contract.</p>" +
	"<p>There must be adequate Resources (such as land and capital) available to produce the contract quantity of the crop.</p>" +
	"<p>For Firm Contracts you should include an Upper Bound. This allows PlantingProfits® to assign an amount of the crop beyond the contract quantity (by the % in the Upper Limit).  This provides flexibility for PlantingProfits® to fit the contract quantity into the most profitable combination of fields given all of the other competing considerations.</p>" +
	"<p>If the Upper Limit entered is insufficient to match a contract quantity to a field or combination of fields, PlantingProfits® returns a message that it cannot find a combination of crops to acreage/fields that will meet your management objective, i.e. your desired contract quantity without splitting one or more fields and given which crops are able to be planted in which fields.</p>" +
	"<p>If you get this message you can provide PlantingProfits® more latitude by:  1) increasing the Upper Limit, 2) splitting one or more fields by going back to Field Information and dividing one or more fields.  This should create the flexibility to match contracts with acreage within the overall most profitable assignment of crops to acreage/fields.</p>" +
	"<p>Proposed Contracts - If you would like to evaluate forward sales, the contract price, quantity or acreage.  Do not check Firm.</p>" +
	"<p>PlantingProfits® uses the Expected Yield previously entered to calculate either quantity or acres depending on which one you enter.  </p>" +
	"<p>PlantingProfits® treats proposed contracts like any other (non Firm contracted crop).  It will only select the contracted crop if it is part of the most profitable assignment of crops and acreage/field.  Otherwise it will not select the proposed contract. </p>" +
	"<p>Comparing a strategy with a firm contract against one with a proposed contract you can evaluate the upside and downside of the contract, i.e. reducing price uncertainty in exchange for an obligation to deliver and a limit on the upside.  (Both of these disadvantages of forward sales can be alleviated with crop insurance and hedging which can be modeled by PlantingProfits® advanced topics.</p>";
/*var forward_sale_upper_limit = "<p>For Firm Contracts you should include an Upper Bound. This allows Planting Profits to assign an amount of the crop beyond the contract quantity (by the % in the Upper Limit).  This provides flexibility for Planting Profits to fit the contract quantity into the most profitable combination of fields given all of the other competing considerations.</p>
<p>If the Upper Limit entered is insufficient to match a contract quantity to a field or combination of fields, Planting Profits returns a message that it cannot find a combination of crops to acreage/fields that will meet your management objective, i.e. your desired contract quantity without splitting one or more fields and given which crops are able to be planted in which fields.</p>
<p>If you get this message you can provide Planting Profits more latitude by:  1) increasing the Upper Limit, 2) splitting one or more fields by going back to Field Information and dividing one or more fields.  This should create the flexibility to match contracts with acreage within the overall most profitable assignment of crops to acreage/fields.</p>";*/
var forward_sale_upper_limit = "<p>Upper Limit to-be deleted.</p>";

/*var crop_limit = "<p>Crop Limits are used to limit the acreage of specific crops or group of crops.</p><p>Crop Limits can be used whether you are planning by acres or fields.</p><p>Crop Limits help you formulate and evaluate diversified cropping strategies.</p><p>Crop Limits are used to make sure that strategies include the minimum and maximum acres of a particular crop or group of crops. <span id='more_content' class='more_content12' onclick=\"callMore('more_content12')\">More</span></p>";
var crop_limit_msg1 = "<div id='more_div' style='display:none' class='more_div12'><p>Crop Acreage Limits are very useful but they must be used carefully.  If you specify a minimum acreage limit but do not provide enough of the required resources to meet the minimum, Planting Profits will not be able to generate the most profitable combination of crops and acreage/fields.  In this case you will be prompted to either decrease the minimum acreage limit or increase the limiting resource.</p>
<p>Crop Acreage Limits can sometimes cause conflicts when planning by Fields.  For example, if you have a minimum acreage limit on a crop,
but you did not allow enough fields and acreage to meet the minimum acreage limit, Planting Profits will not be able to generate the most profitable combination of crops and acreage/fields.
You will be promoted to either decrease the minimum acreage limit, split fields and allow the crop to be considered for planting in additional fields.</p><p>You can set Minimum and Maximum crop acreage limits on a given crop or group of crops.  This will bracket the acreage of the crop or group of crops between the minimum and maximum.  </p><p>If you set the Minimum and Maximum crop acreage limits equal then Planting Profits will generate the most profitable combination of crops and acreage with exactly the specified acreage.  This should only be used when planning by Acres.
If you set the Minimum and Maximum crop acreage limits equal when planning by Fields, Planting Profits will not be able to assign the crop or group of crops to a set of fields that exactly match the acreage limits.</p> </div>";*/
var crop_limit = "<p>Crop Acreage Limits are used to limit the acreage of specific crops or group of crops.  They can be used whether you are planning by acres or fields.</p>" +
	"<p>Crop Acreage Limits help you formulate and evaluate diversified cropping strategies. Since PlantingProfits® focuses on maximizing estimated profit, it will create crop strategies that contain as many acres as possible of the most profitable crop (among other considerations)  So Crop Acreage Limits are used to make sure that your strategies include the minimum and maximum acres of a particular crop or group of crops.</p>" +
	"<p>If you are planning by Fields Crop Acreage Limits interact with the Crop/Field Choices you specified. <span id='more_content' class='more_content12' onclick=\"callMore('more_content12')\">More</span></p>";
var crop_limit_msg1 = "<div id='more_div' style='display:none' class='more_div12'><p>Crop Acreage Limits are very useful but there are a couple of things to bear in mind.</p><p>First, if you specify a minimum acreage limit but do not provide enough of the required resources to meet the minimum, PlantingProfits® will not be able to generate the most profitable combination of crops and acreage/fields. In this case you will need to either:  1) decrease the minimum acreage limit or 2) increase the limiting resource.</p>" +
	"<p>Second, Crop Acreage Limits can sometimes cause conflicts when planning by Fields. For example, if you have a minimum acreage limit on a crop, but you did not allow enough fields/acreage to meet the minimum acreage limit, PlantingProfits® will not be able to generate the most profitable combination of crops and fields/acreage.  In this case you will need to either decrease the minimum acreage limit or split fields and allow the crop to be considered for planting in additional fields.</p>" +
	"<p>You can set Minimum <b>and</b> Maximum crop acreage limits on a given crop or group of crops. This will bracket the acreage of the crop or group of crops between the minimum and maximum.</p><p>If you set the Minimum and Maximum crop acreage limits equal then PlantingProfits® will generate the most profitable combination of crops and acreage with exactly the specified acreage. This should only be used when planning by Acres. " +
	"If you set the Minimum and Maximum crop acreage limits equal when planning by Fields, PlantingProfits® will likely not be able to assign the crop or group of crops to a set of fields that exactly matches the acreage limits.</p> </div>";

/*var crop_limit_max = "<p>Maximum Crop Acreage Limits are used to set an upper limit (i.e. the maximum acreage) for specific crops or group of crops.  If you set a maximum acreage limit of 50 acres on a crop, then all strategies generated by Planting Profits will have no more than 50 acres of that crop. <span id='more_content' class='more_content13' onclick=\"callMore('more_content13')\">More</span></p>";
var crop_limit_max_msg1 = "<div id='more_div' style='display:none' class='more_div13'><p>Maximum crop acreage limits are useful when a particular crop has high profitability relative to other crop choices.  Without a maximum acreage limit Planting Profits will assign as much acreage as possible to highest profit crop.</p><p>For any crop or group of crops, the default setting for Maximum Crop Acreage Limits is the maximum amount of land available for planting.  </p> </div>";*/
/**
 * @changed - Abhishek
 * @date - 23-01-2016
 * @desc - changed according to slide# 13 of 01042015
 */
/*var crop_limit_max = "Enter the Maximum acreage limit for the crop or crop group.";*/
var crop_limit_max = "Enter the Maximum acreage limit for the crop or crop group.  All strategies generated will have no more than the maximum acreage of the specified crop or crop group.";

/*var crop_limit_min = "<p>Minimum Crop Acreage Limits are used to set a lower limit (i.e. the minimum acreage) for specific crops or group of crops.  If you set a minimum acreage limit of 200 acres on a crop, then all strategies generated by Planting Profits will have more than 200 acres of that crop. <span id='more_content' class='more_content14' onclick=\"callMore('more_content14')\">More</span></p>";
var crop_limit_min_msg1 = "<div id='more_div' style='display:none' class='more_div14'><p>Minimum crop acreage limits are useful when a particular crop has a lower profitability relative to other crop choices.  Use minimum crop limits if you need to grow a minimum amount of crop or group of crops to maintain rotations, produce feed, diversify, or for any other reason. </p><p>When you are on the Forward Sales page and you select a Firm forward sales contract, Planting Profits creates a minimum crop acreage limit.</p><p>For any crop or group of crops, the default setting for Minimum Crop Acreage Limits is zero, i.e.
Planting Profits is not required to select any acreage of the crop or group of crops.</p> </div>";*/
/**
 * @changed - Abhishek
 * @date - 23-01-2016
 * @desc - changed according to slide# 13 of 01042015
 */
/*var crop_limit_min = "Enter the Minimum acreage limit for the crop or crop group.";*/
var crop_limit_min = "Enter the Minimum acreage limit for the crop or crop group.  All strategies generated will have at least the minimum acreage of the specified crop or crop group.";

/*var crop_group = "<p>Crop Groups allow you to set minimum and maximum limits for groups of crops.  You can create a crop group by selecting Add Group.  Then name it and   associate two or more crops to the group.</p><p>You can set crop acreage limits for the group of crops. <span id='more_content' class='more_content15' onclick=\"callMore('more_content15')\">More</span></p>";
var crop_group_msg1 = "<div id='more_div' style='display:none' class='more_div15'><p>Group Limits are very useful but they must be used carefully.  If you specify a minimum acreage limit for a group of crops but do not provide enough of the required resources to meet the minimum, Planting Profits will not be able to generate the most profitable combination of crops and acreage/fields.  In this case you will be prompted to either decrease the minimum acreage limit for that group or increase the limiting resource.</p>
<p>Group Limits can sometimes cause conflicts when planning by Fields.  For example, if you have a minimum acreage limit on a group of crops,
but you did not allow enough fields and acreage to meet the minimum acreage limit,
Planting Profits will not be able to generate the most profitable combination of crops and acreage/fields.  You will be promoted to either decrease the minimum acreage limit for that group, split fields and allow the crops in the group to be considered for planting in additional fields.</p><p>You can set Minimum and Maximum crop acreage limits on a group or group of crops.  This will bracket the acreage of the group of crops between the minimum and maximum.  </p>
<p>If you set the Minimum and Maximum crop acreage limits for a group of crops equal then Planting Profits will generate the most profitable combination of crops and acreage with exactly the specified acreage for the group.  This should only be used when planning by Acres.
If you set the Minimum and Maximum crop acreage limits for a group of crops equal when planning by Fields, Planting Profits will not be able to assign the group of crops to a set of fields that exactly match the acreage limits for the group.</p> </div>";*/
var crop_group = "<p>Crop Groups allow you to set limits for groups of crops.</p><p> You can create a crop group by selecting Add Group. Then name the group. Associate two or more crops to the group.  Then set crop acreage limits for the group. <span id='more_content' class='more_content15' onclick=\"callMore('more_content15')\">More</span></p>";
var crop_group_msg1 = "<div id='more_div' style='display:none' class='more_div15'><p>Like individual crop limits, Group Limits are very useful but there are a couple of things to bear in mind.</p>" +
	"<p>First, if you specify a minimum acreage limit for the group but do not provide enough of the required resources to meet the minimum, PlantingProfits® will not be able to generate the most profitable combination of crops/acreage/fields for the group. In this case you will need to either:  1) decrease the minimum acreage limit for the group or 2) increase the limiting resource.</p>" +
	"<p>Second, Group Acreage Limits can sometimes cause conflicts when planning by Fields. For example, if you have a minimum acreage limit on the group of crops, but you do not allow enough fields/acreage to meet the minimum acreage limit for the group, PlantingProfits® will not be able to generate the most profitable combination of crops/fields/acreage.  In this case you will need to either decrease the minimum acreage limit for the group or split fields and allow the crops in the group to be considered for planting in additional fields.</p>" +
	"<p>You can set Minimum <b>and</b> Maximum crop acreage limits for a group of crops. This will bracket the acreage of the group of crops between the minimum and maximum.</p>" +
	"<p>If you set the Minimum and Maximum crop acreage limits for a group equal then PlantingProfits® will generate the most profitable combination of crops and acreage with exactly the specified acreage for the group. <b>This should only be used when planning by Acres.</b> If you set the Minimum and Maximum crop acreage limits for a group equal when planning by Fields, PlantingProfits® will likely not be able to assign the group of crops to a set of fields that exactly matches the acreage limits.</p> </div>";

var add_group = "<p>Crop Groups allow you to set minimum and maximum limits for groups of crops.  You can create a crop group by selecting Add Group.  Then name it and   associate two or more crops to the group.</p><p>You can set crop acreage limits for the group of crops. <span id='more_content' class='more_content16' onclick=\"callMore('more_content16')\">More</span></p>";
var add_group_msg1 = "<div id='more_div' style='display:none' class='more_div16'><p>Group Limits are very useful but they must be used carefully.  If you specify a minimum acreage limit for a group of crops but do not provide enough of the required resources to meet the minimum, PlantingProfits® will not be able to generate the most profitable combination of crops and acreage/fields.  In this case you will be prompted to either decrease the minimum acreage limit for that group or increase the limiting resource.</p>" +
	"<p>Group Limits can sometimes cause conflicts when planning by Fields.  For example, if you have a minimum acreage limit on a group of crops, but you did not allow enough fields and acreage to meet the minimum acreage limit, PlantingProfits® will not be able to generate the most profitable combination of crops and acreage/fields.  You will be promoted to either decrease the minimum acreage limit for that group, split fields and allow the crops in the group to be considered for planting in additional fields.</p>" +
	"<p>You can set Minimum and Maximum crop acreage limits on a group or group of crops.  This will bracket the acreage of the group of crops between the minimum and maximum.  </p>" +
	"<p>If you set the Minimum and Maximum crop acreage limits for a group of crops equal then PlantingProfits® will generate the most profitable combination of crops and acreage with exactly the specified acreage for the group.  This should only be used when planning by Acres.  If you set the Minimum and Maximum crop acreage limits for a group of crops equal when planning by Fields, PlantingProfits® will not be able to assign the group of crops to a set of fields that exactly match the acreage limits for the group.</p> </div>";

/*var saForSingleResource = "<p>Select a resource.</p>";
var saForSingleResource1 = "<p>Select the amount to increase or decrease the resource.</p>";
var saForSingleResource2 = "<p>Note the change in Estimated Income and the change in the crop mix.</p>";*/

/**
 * @changed - Abhishek
 * @date - 25-02-2016
 * @desc - according to slide#13 of 02212016
 */
/**
 * @updated - Jyoti Verma
 * @date - 24-04-2017
 * @type {make resource in small letters as slide G034 of 04232017}
 */
var SAChangeInPotentialProfitResource = "<p><b>Single Resource :</b></p>" +
	"<p>PlantingProfits® will change the amount of the selected Resource and re-run the analysis.  It will do this five times automatically.</p>" +
	"<p>Select the Resource.</p>" +
	"<p>Enter the amount that the Resource will be automatically increased or decreased.</p>" +
	"<p>For instance if you select Capital, -25,000, PlantingProfits® will decrease the amount of available capital by -25,000 5 times (-25,000, - 50,000, -75,000, -100,000, -125,000) from whatever amount was originally entered.</p>" +
	"<p>This will generate five strategies that are the most profitable combination of crops/fields/acreage using these five different resource amounts.</p>" +
	"<p>You can click on any of these new strategies to drill down into the crop/field/acreage combinations and other information</p>" +
	"<p>You can save any of these strategies for further analysis.</p>" ;

/**
 * @changed : Jyoti Verma
 * @date : 24-04-2017
 * @type {Remove 'change' word from last paragraph according to slide G034}
 */
var resourceSestivityDiv = "<p>Increasing a critical resource or a combination of critical resources will increase Estimated Income.</p>" +
	"<p>You can focus on a single resource and vary it and see the impact on profitability. </p>" +
	"<p>Or you can change multiple resource simultaneously and see how this impacts profitability. </p>" +
	"<p>The graph or the table above shows resources that are impacting profitability.  Yes means that if a resource is increased, Estimated Income will increase – up to a certain point</p>" +
	"<p>Similarly, decreasing one or more critical resources will decrease Estimated Income.</p>" +
	"<p>For example, you may want to know if there is a way to decrease the amount of required operating capital without significantly decreasing profitability.</p>" +
	"<p>In PlantingProfits® crops choices, acreage, resources, crop acreage limits, etc., are all linked.  As other resources are changed, a resource that was critical may become non-critical.  Conversely, a resource that was not critical can become critical as other factors, such as crop acreage limits.</p>";

var resourceSestivityMultipleDiv = "<p><b>Multiple Resources:</b></p>" +
	"<p>You can change multiple resources simultaneously and analyze the impact. </p>" +
	"<p>Sometimes two resources may be critical.  In this case the effect of changing only one may be very small so you can more quickly see how to increase profit by increasing two resources at the same time. </p>";


/**
 * @changed - Abhishek
 * @date - 25-02-2016
 * @desc - according to slide#13 of 02212016
 */
var SAChangeInPotentialProfitCropLimits = "<p><b>Single Crop Limits:</b></p>" +
	"<p>PlantingProfits® will change the amount of the selected crop acreage limit and re-run the analysis.  It will do this five times automatically.</p>" +
	"<p>Select the crop.</p>" +
	"<p>Select whether it is a Minimum or Maximum crop acreage limit.</p>" +
	"<p>Enter the amount that the crop limit will be automatically increased or decreased.</p>" +
	"<p>For instance if  you select:  Barley, Minimum, 50 (acres), PlantingProfits® will increase the minimum acreage limit for Barley by 50 acres five times (50, 100, 150, 200, 250 acres)from whatever amount was originally entered.</p>" +
	"<p>This will generate five strategies that are the most profitable combination of crops/fields/acreage using these five different crop acreage limit amounts.</p>" +
	"<p>You can click on any of these new strategies to drill down into the crop/field/acreage combinations and other information</p>" +
	"<p>You can save any of these strategies for further analysis.</p>";

/*var cropLimitSestivityDiv = "<p>Changing crop limits or a combination of crop limits can increase or decrease Estimated Income. </p>" +
	"<p>You can focus on a single crop limit and vary it and see the impact on profitability. </p>" +
	"<p>Or you can change multiple crop limits simultaneously and see how this impacts profitability. </p>" +
	"<p>The table above identifies which crop limits are impacting profit.  Yes means that if a Maximum crop limit is increased (consider growing more) or a Minimum crop limit is decreased (consider growing less) then profitability will increase (up to a certain point).</p>" +
	"<p>For example, you may want to know if the impact of requiring that at least 30% of acreage go in to corn. Suppose there is a minimum acreage limit of 30% for corn.  If the table above indicates Yes (that the minimum acreage limit for corn is impacting profit) then you may want to decrease the limit for minimum corn acreage to 25% or 20% and see how much profitability increases.</p>" +
	"<p>In Planting Profits crops choices, acreage, resources, crop acreage limits, etc., are all linked.  As other crop limits and resources are changed, a crop limit that was critical may become non-critical.  Conversely, a crop limit that was not critical can become critical as other factors change.</p>";*/

var cropLimitSestivityDiv = "<p>Changing crop limits or a combination of crop limits can increase or decrease Estimated Income. </p>" +
	"<p>You can focus on a single crop limit and vary it and see the impact on profitability. </p>" +
	"<p>Or you can change multiple crop limits simultaneously and see how this impacts profitability. </p>" +
	"<p>To create new Crop Acreage Limits go to <a onclick=\"window.location.href = $('.edit_output_details_link > a').attr('href')\" >Change Farm Information</a></p>" +
	"<p>The table above identifies which crop limits are impacting profit. Yes means that if a Maximum crop limit is increased (consider growing more) or a Minimum crop limit is decreased (consider growing less) then profitability will increase (up to a certain point).</p>" +
	"<p>For example, you may want to know if the impact of requiring that at least 30% of acreage go in to corn. Suppose there is a minimum acreage limit of 30% for corn. If the table above indicates Yes (that the minimum acreage limit for corn is impacting profit) then you may want to decrease the limit for minimum corn acreage to 25% or 20% and see how much profitability increases.</p>";

var cropLimitSestivityMultipleDiv = "<p><b>Multiple Crop Limits:</b></p>" +
	"<p>You can change multiple crop limits simultaneously and analyze the impact.  </p>" +
	"<p>Sometimes two or more crop limits may be critical.  In this case the effect of changing only one may be very small so you can quickly see how to increase profit by increasing two or more crop limits at the same time. </p>";


var Conservation_Practice = "<p>Check the box to label a crop a as Conservation Practice crop.</p><p>This designator does not affect the analysis.</p><p>It is used to compute the amount of acreage and profit from crops labeled Conservation Practices.</p>";
var hiRisk_crop = "<p>Check the box to label a crop as a Hi-Risk crop.</p><p>This designator does not affect the analysis.</p><p>It is used to compute the amount of acreage and profit from crops labeled Hi-Risk.</p>";

var scenarioPopupIntro = "<p>Intro to Scenario Planning:</p>" +
		"<p>To create a scenario, select one or more Strategies.  <span id='more_content' class='more_content17' onclick=\"callMore('more_content17')\">More</span></p>" +
		"<div id='more_div' style='display:none' class='more_div17'>" +
		"	<p>Scenarios are possible outcomes of uncontrollable events.  Scenarios are used to explore impacts of  uncertainty on your bottom line. Scenarios are developed from existing strategies.</p>" +
		"	<p>Scenario Planning shows the impact of unplanned events on Estimated Income of each of strategy.  It applies unexpected changes in prices, yields and production costs to show the effect on Estimated Income.</p>" +
		"	<p>What uncontrollable events could impact your Estimated Income?</p>" +
		"	<p>Understanding a range of possible outcomes given uncontrollable events, for a given set of strategies, allows you to choose the best strategy in an uncertain farming environment.</p>" +
		"	<p>In other words:</p>" +
		"	<p>Price scenarios reflect market risk</p>" +
		"	<p>Yield scenarios reflect for production risk</p>" +
		"	<p>You can also build a scenario based on an unplanned event that impacts production costs.</p>" +
		"	<p>The best strategy may not necessarily be the one with the highest Estimated Income give expected conditions.  Instead it may be the one with the highest Estimated Income given some uncontrollable event that has a high likelihood of occurring.</p>" +
		"	<p>Scenario Planning will show you the effects of risk reduction efforts.  For example, if you have forward sold corn, and you create a scenario with a low corn price, the forward sold corn will not be affected since the price was locked in.  If your cotton production is insured the impact on yield from a drought will be partly mitigated by your claim payment.  " +
	"Scenario planning can show you under what circumstances it may be beneficial to go back and develop strategies with greater emphasis on risk management.  Much of risk management is about using controllable management levers such as forward sales, crop insurance, diversification, etc., to reduce risk.</p>" +
		"</div>" +
		"<p>In production agriculture there are too many uncertainties to consider them all.  In scenario planning, focus on those events that are most important in terms of probability of occurrence and potential impact.</p>" +
		"<p>Without probability of occurrence and impact, there is no risk.</p>" +
		"<p>It may be helpful to build a table to frame up possible scenarios before creating them in PlantingProfits®.  Some scenarios can impact price, yield and production costs.<span id='more_content' class='more_content18' onclick=\"callMore('more_content18')\">More</span></p></p>" +
		"<div id='more_div' style='display:none' class='more_div18'>" +
		"<p>There is no limit to the number of scenarios you can evaluate.  In most cases, three to five scenarios are probably adequate.  However, there are exceptions.  As a general rule, keep it simple and systematic and remember the differences in your scenarios.  Too many scenarios can lead to confusion. </p>" +
		"<p>Again, think in terms of probability of occurrence and potential impact.  When: 1) the probability of a bad event such as a low corn price is high and 2) the potential impact is high because a large percentage of your expected profit is in corn, this is a good scenario to analyze.</p>" +
		"</div>";

var scenarioName = "<p>Enter the name of the scenario to create.  Keep it short but descriptive to remember what uncontrollable event(s) you are analyzing</p>";

var globalCropTabHelp = "<p>Scenarios can be either Global or Crop-specific. </p>" +
		"<p>Global means that the scenario impact is applied to all crops.  For example, a global drought scenario is created that decreases all crop yields 20%.</p>" +
		"<p>Scenarios can be created to show the impact on crop prices, yields or variable production costs.</p>" +
		"<p>Crop-specific means that the scenario impacts crops differently.  For example, a late blight scenario is specific to potatoes. Late blight is an example of a scenario that could impact potato price, yield and variable production costs. </p>" +
		"<p>By default global scenario values, which apply to all crops, also appear on the crop-specific tab.  For example, if you create a global scenario with 20% increase in yield, the Crop-specific tab will show the yield for each crop 20% higher.</p>";

var globalPrices = "<p>For the given scenario, enter the percentage to increase or decrease all crop prices.  For example, to decrease the price on all crops by 10% enter -10.</p>";

var globalYield = "<p>For the given scenario, enter the percentage to increase or decrease all crop yields.  For example, to increase the yield on all crops by 10%, enter 10.   To decrease the yield on all crops by 20% enter -20.</p>";

var globalVarCost = "<p>For the given scenario, enter the percentage to increase or decrease all crop production costs.  For example, to increase the production costs on all crops by 5%, enter 5.   To decrease the production costs on all crops by 15% enter -15.</p>";

var cropSpecificPrice = "<p>For the given scenario, enter the percentage to increase or decrease a crop price.</p>";

var cropSpecificCropYeild = "<p>For the given scenario, enter the percentage to increase or decrease a crop yield.</p>";

var cropSpecificVarCost = "<p>For the given scenario, enter the percentage to increase or decrease a crop production cost.</p>";

var landProfitablityIndex = "Land Profitability Index is a relative measure of the profitability of acreage for each crop. It is the ratio of the % of profit and the % acreage.";

var landProfitablityIndexRating = "<p>Land Profitability Index rating is as follows:</p>" +
	"<p>Green : Land Profitability Index is greater than 1 </p>" +
	"<p>Yellow : Land Profitability Index is less than 1 and greater than 0.6 </p>" +
	"<p>Red : Land Profitability Index is less than 0.6" +
    "<p>Grey:  Land Profitability Index N/A if the crop was not selected for planting</p>";

var montyCarloSwitch = "<p>Estimated Income for a given crop is computed as (Yield * Price) – Variable Production Costs.  Estimated Income can be calculated in one of two ways:</p>" +
	"<p>1. Enter your expected yield and expected price forecasts for each crop. This will generate an Estimated Income using these values.</p>" +
	"<p>2. To simulate the uncertainty of yield and price, enter Min and Max Yields and Prices. </p>";

/* @author Jyoti    @date 02-01-2017  PPT NO : 12312106 Slide no : 4*/
/*var new_strategy = "<p>Save it as a new strategy if you want to continue making changes to your Baseline, but also want to keep this strategy to compare it with others now or later.</p>";*/
var new_strategy = "<p>If you want to keep this strategy to compare it with others, save it as a new strategy and continue working with your Baseline strategy.</p>";

var featuredStrategy = "<p>Featured strategy will be the main strategy in the report and show all strategy details; other strategies selected to be included in the report will be shown at a summary level for comparison to the Featured strategy.</p>"

var strategyVarianceInfo = "<p>This graph is used to simulate and illustrate the uncertainty in a given Strategy.  The graph illustrates the range of estimated income generated by changing crop prices and yields 10,000 times for each crop using the minimum and maximum price and yield data previously provided.</p>" +
	"<p>For example, a strategy containing crops with less price and yield uncertainty, due to risk management instruments such as forward sales or crop insurance will normally show less variance in expected income than a strategy with greater exposure in either price or yield.</p>";

var strategyComparisonInfo = "<p>Select which variables to compare across strategies.</p>";
var strategyVarianceInformationInfo = "<p>If checkbox is checked, then the crop’s price and / or yield variance will be used in the simulation.  If it is not checked it will be ignored.</p>";
var strategyVarianceRangeInfo = "<p>This is the price and yield information previously entered that will be used to simulate uncertainty in expected income for a given strategy.</p>" +
	"<p>To change this information, go back to the Crop Information Details page.</p>";


$(document).ready(function() {
	$(".help_Infromation_PopUp").click(function() {
		//alert($(this).attr("id").trim());
		callDiv_Show13(this);
		return false;
	});
});
function crop_resourse_usages_click(id) {
	callDiv_Show13(id.trim());
}

function showHelpPopUp(message, header) {
	// alert("showHelpPopUp");
	$("#info").show();
	$("#content_box_help").html("");
	var content = "";
	for (var i = 0; i < message.length; i++) {
		content += "<p class=\"helpPopUpContent\">" + message[i] + "</p>";
	}
	$("#content_box_help").html(content);
	$("#info").find(".popupheader").html(header);
}

function callDiv_Show13(obj) {

	var id = $(obj).attr("id");

	var contentArray = [];
	var header = "Information";
	if (id == "farm_Information_planBy") {
		contentArray.push(farm_Information_planBy_msg1);
		contentArray.push(farm_Information_planBy_msg2);
		contentArray.push(farm_Information_planBy_msg5);
		// contentArray.push(farm_Information_planBy_msg2_more);
		contentArray.push(farm_Information_planBy_msg3);
		contentArray.push(farm_Information_planBy_msg6);
		/*contentArray.push(farm_Information_planBy_msg4);*/
		/*contentArray.push(farm_Information_planBy_msg7);*/

		header = "Farm Information";

	} else if (id == "farm_Information_irrigate") {
		contentArray.push(farm_Information_irrigate_msg1);
		contentArray.push(farm_Information_irrigate_msg2);
		// contentArray.push(farm_Information_irrigate_msg3);
		// contentArray.push(farm_Information_irrigate_msg4);

	} else if (id == "farm_Information_storage") {
		contentArray.push(farm_Information_storage_msg1);
		contentArray.push(farm_Information_storage_msg2);
	} else if (id == "farm_Information_forward") {
		contentArray.push(farm_Information_forward_msg1);
		contentArray.push(farm_Information_forward_msg2);
	} else if (id == "farm_Information_insurance") {
		contentArray.push(farm_Information_insurance_msg1);
		contentArray.push(farm_Information_insurance_msg2);
	} else if (id == "Crops_and_Crop_Information") {
		contentArray.push(Crops_and_Crop_Information_msg1);
		contentArray.push(Crops_and_Crop_Information_msg2);
		contentArray.push(Crops_and_Crop_Information_msg3);
		contentArray.push(Crops_and_Crop_Information_msg4);
	} else if (id == "Crops_and_Crop_Information_add") {
		contentArray.push(Crops_and_Crop_Information_msg3);
		contentArray.push(Crops_and_Crop_Information_msg4);
	} else if (id == "Plan_by_Fields") {
		contentArray.push(Plan_by_Fields_msg1);
		contentArray.push(Plan_by_Fields_msg2);
	} else if (id == "Plan_by_Fields_Last_Crop") {
		contentArray.push(Plan_by_Fields_msg3);
	} else if (id == "Plan_by_Fields_Fallow") {
		contentArray.push(Plan_by_Fields_msg4);
	} else if (id == "Plan_by_Fields_Divide") {
		contentArray.push(Plan_by_Fields_msg5);
		contentArray.push(Plan_by_Fields_msg6);
	} else if (id == "Plan_by_Fields_Irrigate") {
		contentArray.push(Plan_by_Fields_msg7);
	} else if (id == "Crop_Information_Details_yield") {
		// contentArray.push(Crop_Information_Details_msg1);
		// contentArray.push(Crop_Information_Details_msg2);
		contentArray.push(Crop_Information_Details_yield)
	} else if (id == "Crop_Information_Details_price") {
		// contentArray.push(Crop_Information_Details_msg3);
		// contentArray.push(Crop_Information_Details_msg4);
		contentArray.push(Crop_Information_Details_price);
	} else if (id == "physical_location") {
		contentArray.push(physical_location);
	} else if (id == "plan_by_acres") {
		contentArray.push(plan_by_acres);
		header = "Plan by Acres";
	} else if (id == "variable_production_cost") {
		contentArray.push(variable_production_cost);
		contentArray.push(variable_production_cost_msg1);
	} else if (id == "crop_field_choices") {
		contentArray.push(crop_field_choices);
	} else if (id == "resourse_manage") {
		contentArray.push(resourse_manage);
		contentArray.push(resourse_manage_msg1);
	} else if (id == "resourse_usage") {
		contentArray.push(resourse_usage);
		contentArray.push(resourse_usage_msg1);
	} else if (id == "variable_production_cost_resourse") {
		contentArray.push(variable_production_cost_resourse);
	} else if (id == "forward_sale") {
		contentArray.push(forward_sale);
		contentArray.push(forward_sale_msg1);
	} else if (id == "forward_sale_proposed") {
		contentArray.push(forward_sale_proposed);
	} else if (id == "forward_sale_price") {
		contentArray.push(forward_sale_price);
	}else if (id == "forward_sale_firm") {
		contentArray.push(forward_sale_firm);
	} else if (id == "forward_sale_upper_limit") {
		contentArray.push(forward_sale_upper_limit);
	} else if (id == "crop_limit") {
		contentArray.push(crop_limit);
		contentArray.push(crop_limit_msg1);
	} else if (id == "crop_limit_max") {
		contentArray.push(crop_limit_max);
		/*contentArray.push(crop_limit_max_msg1);*/
	} else if (id == "crop_limit_min") {
		contentArray.push(crop_limit_min);
		/*contentArray.push(crop_limit_min_msg1);*/
	} else if (id == "crop_group") {
		contentArray.push(crop_group);
		contentArray.push(crop_group_msg1);
	} else if (id == "add_group") {
		contentArray.push(add_group);
		contentArray.push(add_group_msg1);
	}
	/**
	 * @changed - Abhishek
	 * @date - 25-02-2016
	 * @desc - according to slide#13 of 02212016
	 */
	else if (id == "SAChangeInPotentialProfitResource") {
		contentArray.push(SAChangeInPotentialProfitResource);
	}
	/**
	 * @changed - Abhishek
	 * @date - 25-02-2016
	 * @desc - according to slide#13 of 02212016
	 */
	else if (id == "SAChangeInPotentialProfitCropLimits") {
		contentArray.push(SAChangeInPotentialProfitCropLimits);
	} else if(id == "profit_per_acre"){
		contentArray.push(profit_per_acre);
	} else if(id == "Optional_Crop_Production_Costs_Details"){
		contentArray.push(Optional_Crop_Production_Costs_Details);
	} else if(id == "amount_Available"){
		contentArray.push(amount_Available);
	} else if(id == "amount_variablity_yield"){
		contentArray.push(amount_variablity_yield);
	} else if(id == "yield_Difference"){
		contentArray.push(yield_Difference);
	} else if(id == "resource_usage_Difference"){
		contentArray.push(resource_usage_Difference);
	} else if(id == "forward_sale_amount"){
		contentArray.push(forward_sale_amount);
	} else if(id == "forward_sale_acres"){
		contentArray.push(forward_sale_acres);
	} else if(id == "forward_sale_firm_td"){
		contentArray.push(forward_sale_firm_td);
	} else if(id == "Conservation_Practice"){
		contentArray.push(Conservation_Practice);
	} else if(id == "hiRisk_crop"){
		contentArray.push(hiRisk_crop);
	} else if(id == "resourceUnitOfmeasure"){
		contentArray.push(resourceUnitOfmeasure);
	} else if(id == "fieldDifference"){
		contentArray.push(fieldDifference);
	} else if(id == "resourceSestivityDiv"){
		contentArray.push(resourceSestivityDiv);
	} else if(id == "resourceSestivityMultipleDiv"){
		contentArray.push(resourceSestivityMultipleDiv);
	} else if(id == "cropLimitSestivityDiv"){
		contentArray.push(cropLimitSestivityDiv);
	} else if(id == "cropLimitSestivityMultipleDiv"){
		contentArray.push(cropLimitSestivityMultipleDiv);
	} else if(id == "scenarioPopupIntro"){
		contentArray.push(scenarioPopupIntro);
	} else if($(obj).hasClass("scenarioName")){
		contentArray.push(scenarioName);
	} else if($(obj).hasClass("globalCropTabHelp")){
		contentArray.push(globalCropTabHelp);
	} else if($(obj).hasClass("globalPrices")){
		contentArray.push(globalPrices);
	} else if($(obj).hasClass("globalYield")){
		contentArray.push(globalYield);
	} else if($(obj).hasClass("globalVarCost")){
		contentArray.push(globalVarCost);
	} else if($(obj).hasClass("cropSpecificPrice")){
		contentArray.push(cropSpecificPrice);
	} else if($(obj).hasClass("cropSpecificCropYeild")){
		contentArray.push(cropSpecificCropYeild);
	} else if($(obj).hasClass("cropSpecificVarCost")){
		contentArray.push(cropSpecificVarCost);
	} else if(id == "landProfitablityIndex"){
		contentArray.push(landProfitablityIndex);
	} else if(id == "landProfitablityIndexRating"){
		contentArray.push(landProfitablityIndexRating);
	} else if(id == "montyCarloSwitch"){
		contentArray.push(montyCarloSwitch);
	} else if(id == "new_strategy") {
        contentArray.push(new_strategy);
	} else if(id == "featuredStrategy") {
        contentArray.push(featuredStrategy);
	} else if(id == "strategy-comparison-info") {
        contentArray.push(strategyComparisonInfo);
	} else if(id == "strategy-variance-info") {
        contentArray.push(strategyVarianceInfo);
	} else if(id == "strategy-variance-range-info") {
        contentArray.push(strategyVarianceRangeInfo);
	} else if(id == "strategy-variance-information-info") {
        contentArray.push(strategyVarianceInformationInfo);
	}


	showHelpPopUp(contentArray, header);
}

/*
 * $(document).ready(function() { alert("hello11"); // 10-03-2015 change start
 * $(".more_content").click(function() { alert("hello"); });
 * $(".more_content").click(function() { alert("document");
 * //callDiv_Show13($(this).attr("id").trim()); }); });
 */
function callMore(cname) {
	if (cname == 'more_content') {
		$(".more_div").toggle();
	} else if (cname == 'more_content1') {
		$(".more_div1").toggle();
	} else if (cname == 'more_content2') {
		$(".more_div2").toggle();
	} else if (cname == 'more_content3') {
		$(".more_div3").toggle();
	} else if (cname == 'more_content4') {
		$(".more_div4").toggle();
	} else if (cname == 'more_content5') {
		$(".more_div5").toggle();
	} else if (cname == 'more_content6') {
		$(".more_div6").toggle();
	} else if (cname == 'more_content7') {
		$(".more_div7").toggle();
	} else if (cname == 'more_content8') {
		$(".more_div8").toggle();
	} else if (cname == 'more_content9') {
		$(".more_div9").toggle();
	} else if (cname == 'more_content10') {
		$(".more_div10").toggle();
	} else if (cname == 'more_content11') {
		$(".more_div11").toggle();
	} else if (cname == 'more_content12') {
		$(".more_div12").toggle();
	} else if (cname == 'more_content13') {
		$(".more_div13").toggle();
	} else if (cname == 'more_content14') {
		$(".more_div14").toggle();
	} else if (cname == 'more_content15') {
		$(".more_div15").toggle();
	} else if (cname == 'more_content16') {
		$(".more_div16").toggle();
	} else if (cname == 'more_content17') {
		$(".more_div17").toggle();
	} else if (cname == 'more_content18') {
		$(".more_div18").toggle();
	} else if (cname == 'more_content19') {
		$(".more_div19").toggle();
	} else if (cname == 'more_content20') {
		$(".more_div20").toggle();
	} else if (cname == 'more_content21') {
		$(".more_div21").toggle();
	} else if (cname == 'more_content22') {
		$(".more_div22").toggle();
	} else if (cname == 'more_content23') {
		$(".more_div23").toggle();
	} else if (cname == 'more_content24') {
		$(".more_div24").toggle();
	} else if (cname == 'more_content25') {
		$(".more_div25").toggle();
	}
	if ($('.' + cname).text() == 'More')
		$('.' + cname).text('Less');
	else
		$('.' + cname).text('More');
}
