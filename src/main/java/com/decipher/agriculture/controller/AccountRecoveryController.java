package com.decipher.agriculture.controller;

import javax.servlet.http.HttpServletRequest;

import com.decipher.config.ApplicationConfig;
import com.decipher.util.PlantingProfitLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.util.CryptographyUtils;
import com.decipher.util.JsonResponse;
import com.decipher.agriculture.service.email.EmailService;

/**
 * @author Manoj
 */
@Controller
@RequestMapping("/accountRecoveryController")
public class AccountRecoveryController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "sendRequestForAccountRecovery", method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse sendRequestForAccountRecovery(
            HttpServletRequest request,
            @RequestParam(value = "email", required = true) String email
    ) {
        PlantingProfitLogger.info("inside sendRequestForAccountRecovery controller  ....>>" + email);
        JsonResponse jsonResponse = new JsonResponse();
        Account user = accountService.getUserByEmail(email);
        if (user != null) {
            jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
            String encodedEmail = CryptographyUtils.encryptData(email);
            PlantingProfitLogger.info("<< encoded mail >>>" + encodedEmail);
            String applicationID = ApplicationConfig.getAppUrl();
            PlantingProfitLogger.info("<<<<<<<<< application id :>>>>>" + applicationID);

            String linkTxt = "<a  target=\"_blank\" href=\"" + applicationID + "/accountRecovery.htm?uid=" + encodedEmail + "\" >" + applicationID + "/accountRecovery.htm?uid=" + encodedEmail + "</a>";
            PlantingProfitLogger.info("Link ->>" + linkTxt);
            String userName = user.getFirstName();
            if (userName.trim().equals("")) {
                userName = user.getEmail_Address();
            }
            String msgText = "Dear : " + userName +
                    "<br/><br/><br/>To reset the password for your Planting Profits account, please click the link below <br/> <b>" + userName +
                    "</b> , Please click the link below:<br/>" +
                    linkTxt
                    + "<br><br><br>Regards"
                    + " :  "
                    + "Thanks from Planting Profits Service Team ";
            emailService.sendEmail(email, "Planting Profit Service Account Recovery", msgText);
        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_INVALID_USER_NOT_EXISTS);
        }
        return jsonResponse;
    }

    @RequestMapping(value = "/changeUserPassword", method = RequestMethod.POST)
    public @ResponseBody
    JsonResponse setNewPassword(@RequestParam String email, @RequestParam String password) {
        JsonResponse jsonResponse = new JsonResponse();
        String decodedEmailId = CryptographyUtils.decryptData(email);
        PlantingProfitLogger.info("inside setNewPassword decodedEmailId :" + decodedEmailId);
        Account user = accountService.getUserByEmail(decodedEmailId);
        if (user != null) {
            PasswordEncoder encoder = new Md5PasswordEncoder();
            password = encoder.encodePassword(password, null);
            user.setPassword(password);
            boolean isUpdated = accountService.updateUser(user);
            PlantingProfitLogger.info("is user Updated " + isUpdated);
            jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
        }
        return jsonResponse;
    }
}