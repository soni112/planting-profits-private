$(document).ready(
		function() {
			$('.bxslider2').bxSlider({
				nextSelector : '#slider-next',
				prevSelector : '#slider-prev',
				nextText : 'Onward â†’',
				prevText : 'â†� Go back'
			});

			$('a[data-toggle="collapse"]').click(function(){
				var icon = $(this).find('i');
				if(icon.hasClass('fa-chevron-down')){
					icon.removeClass('fa-chevron-down').addClass('fa-chevron-up');
					$(this).parents().eq(1).siblings().each(function(){
						$(this).find('i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
					});

				} else {
					icon.removeClass('fa-chevron-up').addClass('fa-chevron-down');
				}
			});

			/*$('#panel-heading-first span.clickable').on("click", function(e) {
				if ($(this).hasClass('panel-collapsed')) {
					// expand the panel
					$(this).parents('#panel-first').find('.panel-body').slideDown();
					$('#panel-body-second').hide();
					$('#collapsed-second').find('i').removeClass('fa fa-chevron-up').addClass('fa fa-chevron-down');

					$(this).removeClass('panel-collapsed');
					$(this).find('i').removeClass('fa fa-chevron-down').addClass('fa fa-chevron-up');
				} else {
					// collapse the panel
					$(this).parents('.panel').find('.panel-body').slideUp();
					$(this).addClass('panel-collapsed');
					$(this).find('i').removeClass('fa fa-chevron-up').addClass('fa fa-chevron-down');
				}
			});

			$('#panel-heading-second span.clickable').on("click", function(e) {
				if ($(this).hasClass('panel-collapsed')) {
					// expand the panel
					$(this).parents('#panel-second').find('.panel-body').slideDown();
					$('#panel-body-first').hide();
					$('#collapsed-first').find('i').removeClass('fa fa-chevron-up').addClass('fa fa-chevron-down');
					$(this).removeClass('panel-collapsed');
					$(this).find('i').removeClass('fa fa-chevron-down').addClass('fa fa-chevron-up');
				} else {
					// collapse the panel
					$(this).parents('.panel').find('.panel-body').slideUp();
					$(this).addClass('panel-collapsed');
					$(this).find('i').removeClass('fa fa-chevron-up').addClass('fa fa-chevron-down');
				}
			});*/

			$('#sidemenu li').on('click', function(e) {
				e.preventDefault();

				if ($(this).hasClass('active')) {
					// do nothing because the link is already open
				} else {
					var oldcontent = $('#sidemenu li.active').attr('href');
					var newcontent = $(this).attr('href');

					$(oldcontent).fadeOut('fast', function() {
						$(newcontent).fadeIn().removeClass('hidden');
						$(oldcontent).addClass('hidden');
					});

					$('#sidemenu li').removeClass('active');
					$(this).addClass('active');
				}
			});

			$(".tabs-menu a").click(function(event) {
				event.preventDefault();
				$(this).parent().addClass("current");
				$(this).parent().siblings().removeClass("current");
				var tab = $(this).attr("href");
				$(tab).siblings().css("display", "none");
				$(tab).fadeIn();
			});
		});
function showMyNextPage() {
	div_hide3();
	$(".show_hide_class").addClass("hidden");
	$("#plan_by_fields").removeClass("hidden");

}
function showMyNextPage1() {
	$(".show_hide_class").addClass("hidden");
	$("#planbyacres").removeClass("hidden");
}
function showMyNextPage2() {
	div_hide5();
	$(".show_hide_class").addClass("hidden");
	$("#add_crop").removeClass("hidden");

}
function showMyNextPage3() {
	$(".show_hide_class").addClass("hidden");
	$("#cropinfodetail").removeClass("hidden");

}
function showMyNextPage4() {
	$(".show_hide_class").addClass("hidden");
	$("#optional_crop").removeClass("hidden");

}
function showMyNextPage5() {
	$(".show_hide_class").addClass("hidden");
	$("#resources_usage").removeClass("hidden");

}
function showMyNextPage6() {
	$(".show_hide_class").addClass("hidden");
	$("#field_varience").removeClass("hidden");

}
function showFramInformationTab() {
	callMethodForPageChangeAndProgressBarImage(1, 1);
}
function showCropsAndCropInformationTab() {
	if (manageStep2) {
		callMethodForPageChangeAndProgressBarImage(2, 3);
	}
}
function showCropFieldChoicesTab() {
	if (strategy == "fields") {
		if (manageStep3) {
			callMethodForPageChangeAndProgressBarImage(4, 5);
		}

	}
}

function showResourcesTab() {
	if (manageStep4) {
		callMethodForPageChangeAndProgressBarImage(6, 5);
	}
}
function showForwardSalesTab() {
	if (manageStep5) {
		callMethodForPageChangeAndProgressBarImage(8, 7);
	}
}

function showCropLimitsTab() {
	if (manageStep6) {
		/* Added for Adding available crops to array for Group by Harshit Gupta */
		/* insertCropsInAvailableCropArray(); */
		callMethodForPageChangeAndProgressBarImage(9, 8);
	}
}
function showCropInsuranceTab() {
	if (manageStep6) {
		$(".show_hide_class").addClass("hidden");
		$(".active").removeClass("active parent");
		$("#crop-insurance").addClass("active parent");
		$("#crop_insurance_div").removeClass("hidden");
		$('#image_bar').attr("src", "images/progress-bar-15.png");
	}

}
