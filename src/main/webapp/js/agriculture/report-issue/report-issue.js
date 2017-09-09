/**
 * Created on 9/9/17 2:39 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */

$(function (){

});

function validateIssueForm(obj) {
    var status = true;

    if($.trim($(obj).find('input[name="name"]').val()).length === 0){
        customAlerts('Please specify you name', "error", 0);
        applyValidation($(obj).find('input[name="name"]'));
        status = false;
    } else if($.trim($(obj).find('input[name="email"]').val()).length === 0){
        customAlerts('Please specify you email', "error", 0);
        applyValidation($(obj).find('input[name="email"]'));
        status = false;
    } else if($.trim($(obj).find('input[name="address"]').val()).length === 0){
        customAlerts('Please specify you address', "error", 0);
        applyValidation($(obj).find('input[name="address"]'));
        status = false;
    } else if($.trim($(obj).find('select[name="country"]').val()).length === 0){
        customAlerts('Please specify you country', "error", 0);
        applyValidation($(obj).find('input[name="country"]'));
        status = false;
    } else if($.trim($(obj).find('select[name="state"]').val()).length === 0){
        customAlerts('Please specify you state', "error", 0);
        applyValidation($(obj).find('input[name="state"]'));
        status = false;
    } else if($.trim($(obj).find('input[name="zipcode"]').val()).length === 0){
        customAlerts('Please specify you zipcode', "error", 0);
        applyValidation($(obj).find('input[name="zipcode"]'));
        status = false;
    } else if($.trim($(obj).find('textarea[name="issue"]').val()).length === 0){
        customAlerts('Please specify the issue', "error", 0);
        applyValidation($(obj).find('textarea[name="issue"]'));
        status = false;
    }

    return status;
}

function createIssue(){
    var target = $('#issue-details-form');
    if(validateIssueForm(target)){
        var data = getDetails(target);

        $.ajax({
            url:'agriculture/issue/create',
            type:'POST',
            contentType: false,
            processData: false,
            beforeSend: function(){
                showLoadingImage();
            },
            data: data,
            success: function (response) {
                if(response.status){
                    customAlerts("Your issue is successfully submitted. Our team will contact you soon", 'success', 0);
                    // target.reset();
                    window.location.reload();
                    hideLoadingImage();
                }
            },
            error: function (a, b, c) {
                customAlerts("Something happened while processing your request", 'error', 0);
                hideLoadingImage();
            },
            complete: function () {
                hideLoadingImage();
            }

        })
    }

}

function getDetails(obj){
    var fd = new FormData();

    fd.append('name', $.trim($(obj).find('input[name="name"]').val()));
    fd.append('email', $.trim($(obj).find('input[name="email"]').val()));
    fd.append('phone', $.trim($(obj).find('input[name="phone"]').val()));
    fd.append('address', $.trim($(obj).find('input[name="address"]').val()));
    fd.append('country', $.trim($(obj).find('select[name="country"]').val()));
    fd.append('state', $.trim($(obj).find('select[name="state"]').val()));
    fd.append('zipcode', $.trim($(obj).find('input[name="zipcode"]').val()));
    fd.append('issue', $.trim($(obj).find('textarea[name="issue"]').val()));

    return fd;
}

function applyValidation(object) {
    $(object).css("border", "1px solid red");
    $(object).click(function() {
        $(this).css("border", "1px solid #cccccc");
    });
}