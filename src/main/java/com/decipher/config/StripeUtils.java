package com.decipher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by abhishek on 5/10/16.
 */
@Component
public class StripeUtils {

    private static String stripePaymentServerKey;
    private static String stripePaymentPublishKey;

    public static String getStripePaymentServerKey() {
        return stripePaymentServerKey;
    }
    @Value("${stripe.payment.serverKey}")
    public void setStripePaymentServerKey(String stripePaymentServerKey) {
        StripeUtils.stripePaymentServerKey = stripePaymentServerKey;
    }

    public static String getStripePaymentPublishKey() {
        return stripePaymentPublishKey;
    }
    @Value("${stripe.payment.publishKey}")
    public void setStripePaymentPublishKey(String stripePaymentPublishKey) {
        StripeUtils.stripePaymentPublishKey = stripePaymentPublishKey;
    }
}
