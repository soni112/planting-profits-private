/**
 * Created on 9/9/17 2:39 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */

$(function (){
    $("#header-user-nav").hide();
});

function validateIssueForm(obj) {
    var status = true;

    if($.trim($(obj).find('textarea[name="issue"]').val()).length === 0){
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
                    customAlerts("Thanks you for your feedback", 'success', 0);
                    target.find('textarea[name="issue"]').val('');
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

    fd.append('issue', $.trim($(obj).find('textarea[name="issue"]').val()));

    return fd;
}

function applyValidation(object) {
    $(object).css("border", "1px solid red");
    $(object).click(function() {
        $(this).css("border", "1px solid #cccccc");
    });
}