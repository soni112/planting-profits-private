/**
 * Created by abhishek on 22/3/16.
 */

$(function(){

    addActiveClass("#menu-management");

    registerTemplates();
    applyTabbing();
    var today = new Date().toISOString().split('T')[0];

    $(".expiryDate").attr('min', today);
    bindProfessionalCreationChange();

    associationObject = {};

    $('#menu-strategies, #menu-scenarios').hide();
    getAllRolesData();

    $('#menu-management').show();

});

/* *************************** Registering functions and templates *************************** */

function registerTemplates(){

    $.template("selectOptionsTemplate", $('#selectOptionsTemplate'));
    $.template("childrenListTbodyTemplate", $('#childrenListTbodyTemplate'));
    $.template("roleListTbodyTemplate", $('#roleListTbodyTemplate'));
    $.template("growerListTbodyTemplate", $('#growerListTbodyTemplate'));
    $.template("profChildListTbodyTemplate", $('#profChildListTbodyTemplate'));
    $.template("studentListTemplate", $('#studentListTemplate'));
    $.template("professionalsForGrowerTemplate", $('#professionalsForGrowerTemplate'));
}

function applyTabbing(){
    $('#sidemenu a').on('click', function (e) {
        e.preventDefault();

        if ($(this).hasClass('open')) {
            // do nothing because the link is already open
        } else {
            var oldcontent = $('#sidemenu a.open').attr('href');
            var newcontent = $(this).attr('href');

            $(oldcontent).fadeOut('fast', function () {
                $(newcontent).fadeIn().removeClass('hidden');
                $(oldcontent).addClass('hidden');
            });

            $('#sidemenu a').removeClass('open');
            $(this).addClass('open');
        }
    });

    //  for handling of tabing on page
    $(".tabs-menu a").click(function (event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".tab-content").not(tab).removeClass("hidden");
        $(tab).fadeIn();
        $(tab).siblings().hide();

    });
}

function bindProfessionalCreationChange(){
    if(currentUserRole == "ROLE_SUPER_ADMIN"){
        $(".user-account-type").change(function(){
            if($(this).val() == "ROLE_PROFESSIONAL" || $(this).val() == "ROLE_GROWER"){
                $('.professional-admin-parent').show();
            } else {
                $('.professional-admin-parent').hide();
            }
        });
    }

}

var selectizeContainer = {}

function bindSelectizeToSelect(id, object){
    var REGEX_EMAIL = '([a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*@' +
        '(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?)';

    var formatName = function(item) {
        return $.trim((item.firstName || '') + ' ' + (item.lastName || ''));
    };

    // disabling copy paste for email address
    $(id).unbind("cut copy paste");
    $(id).bind("cut copy paste",function(e) {
        e.preventDefault();
    });

    $(id).change(function(){
        // console.log(id + " " + $(this).val());
    });


    if (selectizeContainer[id] && selectizeContainer[id][0].selectize) {
        selectizeContainer[id][0].selectize.destroy();
        selectizeContainer[id] = "";
    }


    selectizeContainer[id] = $(id).selectize({
        persist: false,
        maxItems: null,
        valueField: 'id',
        labelField: 'name',
        searchField: ['firstName', 'lastName', 'email_Address'],
        sortField: [
            {field: 'firstName', direction: 'asc'},
            {field: 'lastName', direction: 'asc'}
        ],
        options : object,
        /*options: [
         {email_Address: 'nikola@tesla.com', firstName: 'Nikola', lastName: 'Tesla'},
         {email_Address: 'brian@thirdroute.com', firstName: 'Brian', lastName: 'Reavis'},
         {email_Address: 'someone@gmail.com'}
         ],*/
        render: {
            item: function(item, escape) {
                var name = formatName(item);
                return '<div>' +
                    (name ? '<span class="name">' + escape(name) + '</span>' : '') +
                    (item.email ? '<span class="email"> (' + escape(item.email_Address) + ') </span>' : '') +
                    '</div>';
            },
            option: function(item, escape) {
                var name = formatName(item);
                var label = name || item.email_Address;
                var caption = name ? item.email_Address : null;
                return '<div>' +
                    '<span class="label">' + escape(label) + '</span>' +
                    (caption ? '<span class="caption">' + escape(caption) + '</span>' : '') +
                    '</div>';
            }
        },
        createFilter: function(input) {
            var regexpA = new RegExp('^' + REGEX_EMAIL + '$', 'i');
            var regexpB = new RegExp('^([^<]*)\<' + REGEX_EMAIL + '\>$', 'i');
            return regexpA.test(input) || regexpB.test(input);
        },
        create: function(input) {
            if ((new RegExp('^' + REGEX_EMAIL + '$', 'i')).test(input)) {
                return {email: input};
            }
            var match = input.match(new RegExp('^([^<]*)\<' + REGEX_EMAIL + '\>$', 'i'));
            if (match) {
                var name       = $.trim(match[1]);
                var pos_space  = name.indexOf(' ');
                var first_name = name.substring(0, pos_space);
                var last_name  = name.substring(pos_space + 1);

                return {
                    email: match[2],
                    firstName: first_name,
                    lastName: last_name
                };
            }
            return false;
        }
    });
}

function applyDetailsThroughTemplate(templateId, dataList, targetId){

    if (dataList.length > 0) {
        $(targetId).html("");

        if (targetId != ".professional-admin-specific") {
            $(templateId).tmpl(dataList).appendTo(targetId);
        } else if(targetId == ".professional-admin-specific"){
            $(targetId).html('<option value="0">Select Admin </option>');
            $(templateId).tmpl(dataList).appendTo(targetId);
        }

    }


}

/* *************************** Popup Specific *************************** */

function openChildrenListPopup(userID, userRole){

    associationObject["parentID"] = userID;
    associationObject["parentRole"] = userRole;

    if(userRole == "ROLE_ADMIN"){
        $("#professionalList").show();
        $("#growerList").hide();
        $("#popup-header, .current-children").text("Professionals");

    } else if(userRole == "ROLE_PROFESSIONAL"){
        $("#professionalList").hide();
        $("#growerList").show();
        $("#current-children").hide();
        $("#popup-header, .current-children").text("Growers");

    }

    getChildrenAndVacantUsers(userID, userRole);
    $('#childrenList-popup').show();

}

function closeChildrenListPopup(){
    $('#childrenList-popup').hide();
}


function openUserDetailsPopup(userId){
    getUserDetailsFromDB(userId, "#userDetails");
    $("#userDetail-popup").show();
    $("#userDetails").find('.user-account-type').parent().show();
    $("#userDetails").find('.expiryDate').parent().show();
}

function closeUserDetailsPopup(){

    $("#userDetail-popup").hide();

}


function openUpdateProfilePopup(userId){
    // getUserDetailsFromDB(userId, "#user-Details-to-update");
    // $("#updateUserDetails-popup").show()
    openUserDetailsPopup(userId);
    $("#userDetails").find('.user-account-type').parent().hide();
    $("#userDetails").find('.expiryDate').parent().hide();

}

function closeUpdateProfilePopup(){
    // $('#updateUserDetails-popup').hide();
    closeUserDetailsPopup();
}


function openSwitchGrowerPopup(){
    $('#switch-grower-popup').show();
}

function closeSwitchGrowerPopup(){
    $('#switch-grower-popup').hide();
}

function toggleProfessionalListForGrower(){
    $("#professionalListForGrowerDiv").slideToggle(300);
}


/* *************************** getting all accounts details *************************** */

function getProfessionalListForGrower(){
    $.ajax({
        url: "ajaxRequest/managementController/getProfessionalListForGrower",
        type: "POST",
        data: {
            userId : currentUserId,
            userRole : currentUserRole
        },
        beforeSend: showLoadingImage(),
        success: function (response) {
            var status = response.status;
            if (status == 'success') {
                /*$("#growerListTbody").html("");
                $("#professionalsForGrowerTemplate").tmpl(response.result).appendTo("#professional-list-for-grower");*/
                applyDetailsThroughTemplate("#professionalsForGrowerTemplate", response.result, "#professional-list-for-grower")

            }
        },
        error: function (jqXHR, textStatus, errorThrow) {
            // console.log("jqXHR : " + jqXHR);
            // console.log("textStatus : " + textStatus);
            // console.log("errorThrow : " + errorThrow);
        },
        complete: function () {
            hideLoadingImage();
        }
    });
}

function getAllRolesData(){
    $.ajax({
        url: "ajaxRequest/managementController/getAllRolesData",
        type: "POST",
        data: {
            userId : currentUserId
        },
        beforeSend: showLoadingImage(),
        success: function (response) {
            var status = response.status;
            var result = response.result;
            if (status == 'success') {
                if (currentUserRole == "ROLE_SUPER_ADMIN"){
                    //  Admin
                    applyDetailsThroughTemplate("#selectOptionsTemplate", result.allAdminList, ".professional-admin-specific");
                    applyDetailsThroughTemplate("#roleListTbodyTemplate", result.allAdminList, "#adminListTbody");

                    //  Professional
                    applyDetailsThroughTemplate("#roleListTbodyTemplate", result.allProfessionalList, "#professionalListTbody");

                    //  Grower
                    applyDetailsThroughTemplate("#growerListTbodyTemplate", result.allGrowerList, "#growerListTbody");

                    //  Student
                    applyDetailsThroughTemplate("#studentListTemplate", result.allStudentList, "#studentListTbody");

                } else if(currentUserRole == "ROLE_ADMIN"){
                    //  Professional
                    applyDetailsThroughTemplate("#roleListTbodyTemplate", result.allChildrenList, "#professionalListTbody");

                    //  Grower
                    applyDetailsThroughTemplate("#growerListTbodyTemplate", result.allGrowerList, "#growerListTbody");

                    //  Student
                    applyDetailsThroughTemplate("#studentListTemplate", result.allStudentList, "#studentListTbody");

                } else if (currentUserRole === "ROLE_PROFESSIONAL"){
                    //  grower
                    applyDetailsThroughTemplate("#profChildListTbodyTemplate", result.allChildrenList, "#professionalChildListTbody");
                } else if(currentUserRole === "ROLE_GROWER"){
                    // Professional list for grower
                    applyDetailsThroughTemplate("#professionalsForGrowerTemplate", result.allProfessionalList, "#professionalListForGrower")
                }

            }
        },
        error: function (jqXHR, textStatus, errorThrow) {
            // console.log("jqXHR : " + jqXHR);
            // console.log("textStatus : " + textStatus);
            // console.log("errorThrow : " + errorThrow);
        },
        complete: function () {
            hideLoadingImage();
        }
    });
}

function getChildrenAndVacantUsers(userId, userRole){

    var role = "", option;
    if(userRole == "ROLE_ADMIN"){
        role = "ROLE_PROFESSIONAL";
        option = "Professionals";
    } else if(userRole == "ROLE_PROFESSIONAL"){
        role = "ROLE_GROWER";
        option = "Growers";
    }

    $.ajax({
        url: "ajaxRequest/managementController/getChildrenAndVacantUsers",
        type: "POST",
        data: {
            userId : userId,
            userRole : role
        },
        beforeSend: showLoadingImage(),
        success: function (response) {
            var status = response.status;
            var result = response.result;
            if (status == 'success') {
                if($(result.childrenList).length != 0){
                    //$("#childrenListTbody").html("");
                    //$("#childrenListTbodyTemplate").tmpl(result.childrenList).appendTo("#childrenListTbody");
                    applyDetailsThroughTemplate("#childrenListTbodyTemplate", result.childrenList, "#childrenListTbody")
                } else {
                    $("#childrenListTbody").html("<tr><td colspan='2' class='success infotext '>No " + option +" Assigned</td></tr>");
                }

                if(userRole == "ROLE_ADMIN"){

                    bindSelectizeToSelect("#freeProfessionals", result.vacantList);

                } else if(userRole == "ROLE_PROFESSIONAL"){
                    bindSelectizeToSelect("#freeGrower", result.vacantList);

                }
            }
        },
        error: function (jqXHR, textStatus, errorThrow) {
            // console.log("jqXHR : " + jqXHR);
            // console.log("textStatus : " + textStatus);
            // console.log("errorThrow : " + errorThrow);
        },
        complete: function () {
            hideLoadingImage();
        }
    });


}


/* *************************** User Specific function *************************** */

function saveUser(){

    var object = $('#add-users');

    var isFormValid = validateDetails(object);

    if (isFormValid) {

        var userDetails = getUserDetailsFromHtml(object);
        userDetails.append("parentId", currentUserId);
        $.ajax({
            url: "ajaxRequest/managementController/saveUser",
            type: "POST",
            processData : false,
            contentType : false,
            data: userDetails,
            beforeSend: showLoadingImage(),
            success: function (response) {
                var status = response.status;
                if (status == 'success') {
                    customAlerts("Your account has been created and verification mail send to email", "success", 0);
                    setTimeout(function(){
                        window.location.reload();
                    }, 2000);
                } else if (status == 'Already exists') {
                    customAlerts("Email id is already registered. <br> Please choose another email.", "error", 0);
                }
            },
            error: function (jqXHR, textStatus, errorThrow) {
                // console.log("jqXHR : " + jqXHR);
                // console.log("textStatus : " + textStatus);
                // console.log("errorThrow : " + errorThrow);
            },
            complete: function () {
                hideLoadingImage();
            }
        });
    }

}

function validateDetails(object) {
    var isFormValidated = true;
    var accountType = $.trim($(object).find('.user-account-type').val());
    var userName = $.trim($(object).find('.first-name').val());
    var contact = $.trim($(object).find('.contact-no').val());
    var email = $.trim($(object).find('.email-id').val());
    var country = $.trim($(object).find('.physical-address-country').val());

    if (accountType == "0") {
        if (isFormValidated) {
            customAlerts("Please Select account type", "error", 0);
            isFormValidated = false;
        }
        applyValidation($(object).find('.user-account-type'));
    }
    if (userName == "") {
        if (isFormValidated) {
            customAlerts("Please enter your first name", "error", 0);
            isFormValidated = false;
        }
        applyValidation($(object).find('.first-name'));
    }
    if (contact == "") {
        if (isFormValidated) {
            customAlerts("Please enter your contact number", "error", 0);
            isFormValidated = false;
        }
        applyValidation($(object).find('.contact-no'));
    }
    if ((""+contact).length > 13) {
        if (isFormValidated) {
            customAlerts("Please enter valid contact number", "error", 0);
            isFormValidated = false;
        }
        applyValidation($(object).find('.contact-no'));
    }
    if (email == "") {
        if (isFormValidated) {
            customAlerts("Please enter your email address", "error", 0);
            isFormValidated = false;
        }
        applyValidation($(object).find('.email-id').val());
    }
    var emailValidated = validateEmailAddress(email);
    if (!emailValidated) {
        if (isFormValidated) {
            customAlerts("Please enter valid email", "error", 0);
            applyValidation($(object).find('.email-id').val());
            isFormValidated = false;
        }
    }

    if (country == "") {
        if (isFormValidated) {
            customAlerts("Please select physical address country", "error", 0);
            applyValidation($(object).find('.physical-address-country'));
            isFormValidated = false;
        }
    }

    if(currentUserRole == "ROLE_SUPER_ADMIN" && accountType == "ROLE_GROWER"){
        var growerAdmin = $.trim($(object).find('.professional-admin-specific').val());
        if (growerAdmin == "0") {
            if (isFormValidated) {
                customAlerts("Please select admin for grower", "error", 0);
                applyValidation($(object).find('.professional-admin-specific'));
                isFormValidated = false;
            }
        }
    }


    return isFormValidated;
}

function applyValidation(object) {
    $(object).css("border", "1px solid red");
    $(object).click(function() {
        $(this).css("border", "1px solid #cccccc");
    });
}

function getUserDetailsFromHtml(objectReference){

    var userDetails = new FormData();
    var accountType = $.trim($(objectReference).find('.user-account-type').val());
    userDetails.append("accountType", accountType);
    userDetails.append("firstName", $.trim($(objectReference).find('.first-name').val()));
    userDetails.append("lastName", $.trim($(objectReference).find('.last-name').val()));
    userDetails.append("contact", $.trim($(objectReference).find('.contact-no').val()));
    userDetails.append("email", $.trim($(objectReference).find('.email-id').val()));

    userDetails.append("mailing_Address_Line1", $.trim($(objectReference).find('.mailing-address-line1').val()));
    userDetails.append("mailing_Address_Line2", $.trim($(objectReference).find('.mailing-address-line2').val()));
    userDetails.append("mailing_Address_City", $.trim($(objectReference).find('.mailing-address-city').val()));
    userDetails.append("mailing_Address_State", $.trim($(objectReference).find('.mailing-address-state').val()));
    userDetails.append("mailing_Address_Country", $.trim($(objectReference).find('.mailing-address-country').val()));
    userDetails.append("mailing_Zip", $.trim($(objectReference).find('.mailing-zip').val()));

    userDetails.append("physical_Address_Line1", $.trim($(objectReference).find('.physical-address-line1').val()));
    userDetails.append("physical_Address_Line2", $.trim($(objectReference).find('.physical-address-line2').val()));
    userDetails.append("physical_Address_City", $.trim($(objectReference).find('.physical-address-city').val()));
    userDetails.append("physical_Address_State", $.trim($(objectReference).find('.physical-address-state').val()));
    userDetails.append("physical_Address_Country", $.trim($(objectReference).find('.physical-address-country').val()));
    userDetails.append("physical_Zip", $.trim($(objectReference).find('.physical-zip').val()));
    userDetails.append("expiryDate", $.trim($(objectReference).find('.expiryDate').val()));

    if(accountType == "ROLE_PROFESSIONAL" || accountType == "ROLE_GROWER"){
        var adminId = $.trim($(objectReference).find('.professional-admin-specific').val());
        if (adminId != 0) {
            userDetails.append("selectedAdminParent", adminId);
        }
    }



    return userDetails;

}

function deleteUser(userId){

    $.ajax({
        url: "ajaxRequest/managementController/deleteUser",
        type: "POST",
        data: {
            userId : userId
        },
        beforeSend: showLoadingImage(),
        success: function (response) {
            var status = response.status;
            if (status == 'success') {
                //customAlerts("User successfully unassigned", "success", 0);
                /*if (associationObject["parentID"]) {
                 getChildrenAndVacantUsers(associationObject["parentID"], associationObject["parentRole"]);

                 }
                 getGrowerList();*/

            }
        },
        error: function (jqXHR, textStatus, errorThrow) {
            // console.log("jqXHR : " + jqXHR);
            // console.log("textStatus : " + textStatus);
            // console.log("errorThrow : " + errorThrow);
        },
        complete: function () {
            hideLoadingImage();
        }
    });


}

function getUserDetailsFromDB(userId, target){

    $.ajax({
        url: "ajaxRequest/managementController/getUserDetails",
        type: "POST",
        data: {
            userId : userId
        },
        beforeSend: showLoadingImage(),
        success: function (response) {
            var status = response.status;
            if (status == 'success') {

                applyUserDetailsToHtml(response.result, target);
                $(target).find('input, select').trigger('click');

            }
        },
        error: function (jqXHR, textStatus, errorThrow) {
            //console.log("jqXHR : " + jqXHR);
            // console.log("textStatus : " + textStatus);
            // console.log("errorThrow : " + errorThrow);
        },
        complete: function () {
            hideLoadingImage();
        }
    });


}

function applyUserDetailsToHtml(dataList, objectReference){

    $(objectReference).find('.user-identification').val(dataList.id);
    $(objectReference).find('.user-account-type').val(dataList.role);

    $(objectReference).find('.first-name').val(dataList.firstName);
    $(objectReference).find('.last-name').val(dataList.lastName);
    $(objectReference).find('.contact-no').val(dataList.phone_No);
    $(objectReference).find('.email-id').val(dataList.email_Address);

    $(objectReference).find('.mailing-address-line1').val(dataList.mailing_Address_Line_1);
    $(objectReference).find('.mailing-address-line2').val(dataList.mailing_Address_Line_2);
    $(objectReference).find('.mailing-address-city').val(dataList.mailing_Address_City);

    if(dataList.mailing_Address_State != null)
        $(objectReference).find('.mailing-address-state').val(dataList.mailing_Address_State.id);

    if(dataList.mailing_Address_Country!= null)
        $(objectReference).find('.mailing-address-country').val(dataList.mailing_Address_Country.id).trigger('change');
    else
        $(objectReference).find('.mailing-address-country').val('').trigger('change');

    $(objectReference).find('.mailing-zip').val(dataList.mailing_Address_Zip);

    $(objectReference).find('.physical-address-line1').val(dataList.physical_Address_Line_1);
    $(objectReference).find('.physical-address-line2').val(dataList.physical_Address_Line_2);
    $(objectReference).find('.physical-address-city').val(dataList.physical_Address_City);
    if(dataList.physical_Address_State != null)
        $(objectReference).find('.physical-address-state').val(dataList.physical_Address_State.id);
    if(dataList.physical_Address_Country != null)
        $(objectReference).find('.physical-address-country').val(dataList.physical_Address_Country.id).trigger('change');
    else
        $(objectReference).find('.physical-address-country').val('').trigger('change');
    $(objectReference).find('.physical-zip').val(dataList.physical_Address_Zip);
    $(objectReference).find('.expiryDate').val(dataList.expirationDate);

}

function updateUserDetails(container){
    var object = $(container);

    var isFormValid = validateDetails(object);

    if (isFormValid) {
        var accountType = $(object).find('.user-account-type').val();
        var userDetails = getUserDetailsFromHtml(object);
        userDetails.append("userId", $(object).find('.user-identification').val());
        userDetails.append("parentId", currentUserId);

        if (accountType == "ROLE_PROFESSIONAL") {
            try {
                userDetails.append("logo", $(object).find(".logo-specific")[0].files[0]);
            } catch (e) {
            }
        }

        $.ajax({
            url: "ajaxRequest/managementController/updateUser",
            type: "POST",
            processData : false,
            contentType : false,
            data: userDetails,
            beforeSend: showLoadingImage(),
            success: function (response) {
                var status = response.status;
                if (status == 'success') {
                    customAlerts("Your account has successfully Updated", "success", 0);
                    if (container == '#userDetails') {
                        getAllRolesData();
                        //getGrowerList();
                        closeUserDetailsPopup();
                    } else if(container == '#user-Details-to-update'){
                        closeUpdateProfilePopup();
                    }
                }
            },
            error: function (jqXHR, textStatus, errorThrow) {
                // console.log("jqXHR : " + jqXHR);
                // console.log("textStatus : " + textStatus);
                // console.log("errorThrow : " + errorThrow);
            },
            complete: function () {
                hideLoadingImage();
            }
        });
    }
}

/* *************************** applying children users *************************** */

function associateChildren(){

    var formData = new FormData();
    var messageParent = "";
    var container;
    if(associationObject["parentRole"] == "ROLE_ADMIN"){
        container = $("#freeProfessionals");
        messageParent = "Professional";
    } else if (associationObject["parentRole"] == "ROLE_PROFESSIONAL") {
        container = $("#freeGrower");
        messageParent = "Grower";
    } else{
       container = null;
    }

    if (container != null && $(container).val() != null) {


        formData.append("parentId", associationObject["parentID"]);
        formData.append("childIdArray", $(container).val());

        $.ajax({
            url: "ajaxRequest/managementController/associateChildren",
            type: "POST",
            contentType: false,
            processData: false,
            data: formData,
            beforeSend: showLoadingImage(),
            success: function (response) {
                var status = response.status;
                if (status == 'success') {
                    getChildrenAndVacantUsers(associationObject["parentID"], associationObject["parentRole"]);

                    getAllRolesData();
                }
            },
            error: function (jqXHR, textStatus, errorThrow) {
                // console.log("jqXHR : " + jqXHR);
                // console.log("textStatus : " + textStatus);
                // console.log("errorThrow : " + errorThrow);
            },
            complete: function () {
                //hideLoadingImage();
            }
        });
    } else {
        customAlerts("Select minimum of one " + messageParent, "error", 0);
    }
}

function unassignUser(userId, parentId){

    $.ajax({
        url: "ajaxRequest/managementController/unassignChildren",
        type: "POST",
        data: {
            childId : userId,
            parentId : parentId
        },
        beforeSend: showLoadingImage(),
        success: function (response) {
            var status = response.status;
            if (status == 'success') {
                //customAlerts("User successfully unassigned", "success", 0);
                if (associationObject["parentID"]) {
                    getChildrenAndVacantUsers(associationObject["parentID"], associationObject["parentRole"]);

                }
                getAllRolesData();

            }
        },
        error: function (jqXHR, textStatus, errorThrow) {
            // console.log("jqXHR : " + jqXHR);
            // console.log("textStatus : " + textStatus);
            // console.log("errorThrow : " + errorThrow);
        },
        complete: function () {
            //hideLoadingImage();
        }
    });


}

function assignProfessionalToGrower(){
    if ($('input[name="professional"]').length != 0) {
        var selectedProfessional = $('input[name="professional"]:checked');
        if ($(selectedProfessional).length != 0) {
            var parentId = $(selectedProfessional).val();


            $.ajax({
                url: "ajaxRequest/managementController/assignProfessionalToGrower",
                type: "POST",
                data: {
                    growerId: currentUserId,
                    parentId: parentId
                },
                beforeSend: showLoadingImage(),
                success: function (response) {
                    var status = response.status;
                    var result = response.result;
                    if (status == 'success') {
                        customAlerts("Professional successfully assigned", "success", 0);
                        $("#current-professional").html("You are currently managed by : " + result.firstName + " " + result.lastName)
                        toggleProfessionalListForGrower();
                    }
                },
                error: function (jqXHR, textStatus, errorThrow) {
                    // console.log("jqXHR : " + jqXHR);
                    // console.log("textStatus : " + textStatus);
                    // console.log("errorThrow : " + errorThrow);
                },
                complete: function () {
                    hideLoadingImage();
                }
            });
        } else {
            customAlerts("Please select a professional to assist", "error", 0);
        }
    } else {
        customAlerts("No Professionals to assist", "error", 0);
    }
}



