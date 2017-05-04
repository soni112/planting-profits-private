$(document).ready(function () {
    $("#home-page-main-section").show();
    $("#services-main-section").hide();
    $("#benifits-main-section").hide();
    $("#partenership-main-section").hide();
    $("#contact-us-main-section").hide();


    if (loginError == 'true') {
        $("#login-validation-error-msg").html("Invalid Email or Password.");
        div_show();
    }
    if (loginSuccess == 'true') {

        customAlerts("You have successfully logged in", "success", 0);
    }
    if (userEmailId != "") {
        $("#userEncodeduserEmailAddress").val(userEncodedEmailId);
        $("#userEmailAddress").val(userEmailId);
        div_show8();
    }
});
function showServicesPage() {
    $("#index-menu-list-option").find("li").removeClass("active");
    $("#services_link").addClass("active");
    $("#home-page-main-section").hide();
    $("#services-main-section").show();
    $("#benifits-main-section").hide();
    $("#partenership-main-section").hide();
    $("#contact-us-main-section").hide();
}
function showBenifitsPage() {
    $("#index-menu-list-option").find("li").removeClass("active");
    $("#benifits_link").addClass("active");
    $("#home-page-main-section").hide();
    $("#services-main-section").hide();
    $("#benifits-main-section").show();
    $("#partenership-main-section").hide();
    $("#contact-us-main-section").hide();
}
function showPartnerShipPage() {
    $("#index-menu-list-option").find("li").removeClass("active");
    $("#partnership_link").addClass("active");
    $("#home-page-main-section").hide();
    $("#services-main-section").hide();
    $("#benifits-main-section").hide();
    $("#partenership-main-section").show();
    $("#contact-us-main-section").hide();
}
function showContactUsPage() {
    $("#index-menu-list-option").find("li").removeClass("active");
    $("#contactus_link").addClass("active");
    $("#home-page-main-section").hide();
    $("#services-main-section").hide();
    $("#benifits-main-section").hide();
    $("#partenership-main-section").hide();
    $("#contact-us-main-section").show();
}
