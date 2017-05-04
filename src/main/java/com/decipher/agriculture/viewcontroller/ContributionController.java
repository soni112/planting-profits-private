package com.decipher.agriculture.viewcontroller;

import com.decipher.util.AgricultureStandardUtils;
import com.decipher.util.JsonResponse;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.util.appStandard.StripeUtils;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abhishek on 9/9/16.
 */
@Controller
public class ContributionController {

    @Autowired
    private HttpSession httpSession;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(name = "/contribution.htm", method = {RequestMethod.GET})
    public ModelAndView renderStripePayment() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("contribution-payment");

        Object obj = httpSession.getAttribute("solverCount");

        if(obj != null){
            double solverCount = Integer.parseInt(obj.toString()) / 100;
            modelAndView.addObject("solverCount", "$" + AgricultureStandardUtils.commaSeparaterForDoublePrice(solverCount));
        } else{
            modelAndView.addObject("solverCount", "$1");
        }

        modelAndView.addObject("stripePublishKey", StripeUtils.getStripePaymentPublishKey());

        return modelAndView;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_PROFESSIONAL", "ROLE_GROWER", "ROLE_STUDENT"})
    @RequestMapping(name = "/acceptPayment", method = {RequestMethod.POST})
    public @ResponseBody JsonResponse acceptPayment(@RequestParam(value = "stripeToken")String stripeToken,
                               @RequestParam(value = "cardName")String cardName,
                               @RequestParam(value = "cardNumber")String cardNumber,
                               @RequestParam(value = "expMonth")String expMonth,
                               @RequestParam(value = "expYear")String expYear,
                               @RequestParam(value = "cvv")String cvv,
                               @RequestParam(value = "paymentAmount")String paymentAmount) {
        PlantingProfitLogger.info("Starting to process payment");
        JsonResponse response = new JsonResponse();

        // your secret key: remember to change this to your live secret key in production
        // See your keys here: https://dashboard.stripe.com/account/apikeys
        Stripe.apiKey = StripeUtils.getStripePaymentServerKey();

        Double amt = Double.parseDouble(paymentAmount) * 100;

        // Create a charge: this will charge the user's card
        try {
            Map<String, Object> chargeParams = new HashMap<String, Object>();
            chargeParams.put("amount", amt.intValue()); // Amount in cents
            chargeParams.put("currency", "usd");
            chargeParams.put("source", stripeToken);
            chargeParams.put("description", "Example charge");

            Charge charge = Charge.create(chargeParams);

            response.setStatus(JsonResponse.RESULT_SUCCESS);

            PlantingProfitLogger.info("Payment complete");
        } catch (Exception e) {
            // The card has been declined
            response.setStatus(JsonResponse.RESULT_FAILED);
            PlantingProfitLogger.error(e);
        }

        return response;
    }


}
