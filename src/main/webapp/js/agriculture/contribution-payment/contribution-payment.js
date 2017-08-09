/**
 * Created by abhishek on 15/9/16.
 */
$(function () {
    $('body').addClass('contribute-bg');
    $('#header-user-nav').hide();
    $('#header-donation-button').hide();

    $('#horizontalTab').easyResponsiveTabs({
        type: 'default', //Types: default, vertical, accordion
        width: 'auto', //auto or any width like 600px
        fit: true   // 100% fit in a container
    });

    // _navigateState = true;

    _navigateState = localStorage.getItem('navigateFlag');

    if(_navigateState == null || typeof _navigateState == 'undefined'){
        _navigateState = false;
    } else {
        _navigateState = _navigateState == "true" ? true : false;
        localStorage.removeItem('navigateFlag');
    }

    $(window).on('beforeunload', windowUnload);

    Stripe.setPublishableKey(stripePublishKey);
    buildInputMask();
    init();
});

function init(){
    $('#amount-to-be-donated').find('button.donation-amt-btn').click(function () {
        var amt = $(this).val();
        $('#payment-amount').val(amt);
        $('#modified-amt').val(amt);
    });
}

function buildInputMask() {
    var cardNumberMask = new Inputmask("9999-9999-9999-9999");
    cardNumberMask.mask($('input[name="cardNumber"]'));

    var cvvMask = new Inputmask("999");
    cvvMask.mask($('input[name="cvv"]'));

    var dateMask = new Inputmask("99");
    dateMask.mask($('input[name="expMonth"]'));
    dateMask.mask($('input[name="expYear"]'));

}

function applyValidation(object) {
    $(object).css("border", "1px solid red");
    $(object).click(function() {
        $(this).css("border", "1px solid #cccccc");
    });
}

function validateCardDetails(obj) {
    var status = true;

    if($('#payment-amount').val() === '0'){
        customAlerts('Please specify contribution amount before proceeding further', "error", 0);
        status = false;
    } else if($(obj).find('input[name="nameOnCard"]').val().length === 0) {
        customAlerts("Please enter the name on the card.", "error", 0);
        applyValidation($(obj).find('input[name="nameOnCard"]'));
        status = false;
    } else if($('input[name="cardNumber"]').val().length === 0) {
        customAlerts("Please enter the card number", "error", 0);
        applyValidation($(obj).find('input[name="cardNumber"]'));
        status = false;
    } else if($('input[name="expMonth"]').val().length === 0) {
        customAlerts("Please enter the card expiry month", "error", 0);
        applyValidation($(obj).find('input[name="expMonth"]'));
        status = false;
    } else if($('input[name="expYear"]').val().length === 0) {
        customAlerts("Please enter the card expiry year", "error", 0);
        applyValidation($(obj).find('input[name="expYear"]'));
        status = false;
    } else if($('input[name="cvv"]').val().length === 0) {
        customAlerts("Please enter the cvv number.", "error", 0);
        applyValidation($(obj).find('input[name="cvv"]'));
        status = false;
    }

    return status;
}

var tmp;
function submitPaymentForm(currentRef) {

    // $form = getCardDetailsFromHtml('.'+ currentRef);
    tmp = currentRef;
    var status = validateCardDetails(currentRef);
    if (status) {
        var $form = $(currentRef);

        // Disable the submit button to prevent repeated clicks:
        // $form.find('.submit').prop('disabled', true);

        // Request a token from Stripe:
        showLoadingImage();
        Stripe.card.createToken($form, stripeResponseHandler);
    }

}

function stripeResponseHandler(status, response) {

    if (response.error) { // Problem!

        // Show the errors on the form:
        customAlerts(response.error.message, "error", 0);
        // $form.find('.payment-errors').text(response.error.message);
        // $form.find('.submit').prop('disabled', false); // Re-enable submission

        hideLoadingImage();

    } else {
        // Token was created!

        // Get the token ID:
        var token = response.id;
        var $form = getCardDetailsFromHtml(tmp);

        // Insert the token ID into the form so it gets submitted to the server:
        $form.append('stripeToken', token);
        $form.append('paymentAmount', removeAllCommasAndDollar($.trim($('#payment-amount').val())));

        $.ajax({
            url: appContext + '/ajaxRequest/acceptPayment',
            type: 'POST',
            beforeSend: showLoadingImage(),
            contentType: false,
            processData: false,
            data: $form,
            success: function (response) {
                var status = response.status;
                var result = response.result;
                if(status == "success"){
                    customAlerts('We are glad to have you. Thanks for your contribution.', "success", 0);
                    hideLoadingImage();
                    setTimeout(function () {
                        $('#logout-btn').trigger('click');
                    }, 2000);
                } else {
                    customAlerts('Error occurred while processing your request. If the payment was deducted from your account it will be refunded automatically', "error", 0);
                    hideLoadingImage();
                }
            },
            error: function (a, b, c) {

            },
            complete: function () {
                // hideLoadingImage();
            }
        });


    }
}

function getCardDetailsFromHtml(container) {

    var formData = new FormData();

    formData.append('cardName', $.trim($(container).find('input[name="nameOnCard"]').val()));
    formData.append('cardNumber', $.trim($(container).find('input[name="cardNumber"]').val()));
    formData.append('expMonth', $.trim($(container).find('input[name="expMonth"]').val()));
    formData.append('expYear', $.trim($(container).find('input[name="expYear"]').val()));
    formData.append('cvv', $.trim($(container).find('input[name="cvv"]').val()));

    return formData;

}

function showPaymentOptions(currentRef){
    currentRef = $(currentRef);
    if(currentRef.attr('data-value') === 'other'){
        $('#payment-amount').val('0');
        $('#manual-amt-div').slideDown(500);
    } else {
        $('#payment-amount').val(addCommaSignWithDollarForTextWithOutId(currentRef.attr('data-value')));
        $('#donation-amount-div').hide(500);
        $('#payment-options-div').show(500);
    }



    /*if($('#payment-amount').val() === ''){
        customAlerts('Please specify contribution amount before proceeding further', "error", 0);
    } else {
        $('#donation-amount-div').hide(500);
        $('#payment-options-div').show(500);
    }*/

}

function showContributionScreen(){

    $('#donation-amount-div').show(500);
    $('#payment-options-div').hide(500);

}

function processLogout(url){
    window.location.href = url;
}

function windowUnload(){

    if (_navigateState) {
        /*$.ajax({
            url: appContext + 'j_spring_security_logout',
            async: false,
            success: function (response) {

            },
            error: function (a, b, c) {

            }

        });*/
    }

}

function navigateBackToApplication() {
    _navigateState = false;
    history.back();
}