package com.decipher.agriculture.controller;

import javax.servlet.http.HttpServletRequest;

import com.decipher.agriculture.data.account.UserState;
import com.decipher.agriculture.service.util.HTTPService;
import com.decipher.config.ApplicationConfig;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.account.AppRole;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.util.CryptographyUtils;
import com.decipher.util.JsonResponse;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.agriculture.service.email.EmailService;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/accountController")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
	private EmailService emailService;
    @Autowired
    private HTTPService httpService;

    @RequestMapping(value = "registerUser", method = RequestMethod.POST)
    public JsonResponse registerUser(
            HttpServletRequest request,
            @RequestParam(value = "accountType", required = true) String accountType,
            @RequestParam(value = "firstName", required = true) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "contact", required = true) String contact,
            @RequestParam(value = "email", required = true) String email,
            @RequestParam(value = "mailing_Address_Line1", required = false) String mailing_Address_Line1,
            @RequestParam(value = "mailing_Address_Line2", required = false) String mailing_Address_Line2,
            @RequestParam(value = "mailing_Address_City", required = false) String mailing_Address_City,
            @RequestParam(value = "mailing_Address_State", required = false) String mailing_Address_State,
            @RequestParam(value = "mailing_Address_Country", required = false) String mailing_Address_Country,
            @RequestParam(value = "mailing_Zip", required = true) String mailing_Zip,
            @RequestParam(value = "physical_Address_Line1", required = false) String physical_Address_Line1,
            @RequestParam(value = "physical_Address_Line2", required = false) String physical_Address_Line2,
            @RequestParam(value = "physical_Address_City", required = false) String physical_Address_City,
            @RequestParam(value = "physical_Address_State", required = false) String physical_Address_State,
            @RequestParam(value = "physical_Address_Country", required = false) String physical_Address_Country,
            @RequestParam(value = "physical_Zip", required = false) String physical_Zip){
        /**
         * @added - Abhishek
         * @date - 02-04-2016
         * @desc - added expiry date for validations for students login
         */

        PlantingProfitLogger.debug("inside registerUser " + firstName + " " + lastName);
        JsonResponse jsonResponse = new JsonResponse();
        boolean exists = accountService.isEmailAddressExists(email.trim());
        if (!exists) {
            String password = RandomStringUtils.randomAlphanumeric(8);

            PasswordEncoder encoder = new Md5PasswordEncoder();
            String encodedNewPassword = encoder.encodePassword(password, null);
            /*Account account = new Account(firstName, lastName, contact,
                    email.trim(), encodedNewPassword, mailing_Address_Line1,
                    mailing_Address_Line2, mailing_Address_City,
                    mailing_Address_State, mailing_Address_Country,
                    mailing_Zip, physical_Address_Line1,
                    physical_Address_Line2, physical_Address_City,
                    physical_Address_State, physical_Address_Country, physical_Zip);*/

            Account account = new Account();

            account.setFirstName(firstName);
            account.setLastName(lastName);
            account.setPhone_No(contact);
            account.setEmail_Address(email.trim());
            account.setPassword(encodedNewPassword);
            account.setPhysical_Address_Line_1(physical_Address_Line1);
            account.setPhysical_Address_Line_2(physical_Address_Line2);
            account.setPhysical_Address_City(physical_Address_City);
            account.setPhysical_Address_Zip(physical_Zip);
            account.setMailing_Address_Line_1(mailing_Address_Line1);
            account.setMailing_Address_Line_2(mailing_Address_Line2);
            account.setMailing_Address_City(mailing_Address_City);
            account.setMailing_Address_Zip(mailing_Zip);


            if(!physical_Address_Country.equalsIgnoreCase(""))
                account.setPhysical_Address_Country(accountService.getCountry(Integer.parseInt(physical_Address_Country)));
            if(!physical_Address_State.equalsIgnoreCase(""))
                account.setPhysical_Address_State(accountService.getState(Integer.parseInt(physical_Address_State)));
            if(!mailing_Address_Country.equalsIgnoreCase(""))
                account.setMailing_Address_Country(accountService.getCountry(Integer.parseInt(mailing_Address_Country)));
            if(!mailing_Address_State.equalsIgnoreCase(""))
                account.setMailing_Address_State(accountService.getState(Integer.parseInt(mailing_Address_State)));


            account.setRegistrationTime(new java.sql.Date(new java.util.Date().getTime()));

            if (accountType.equalsIgnoreCase("Grower")) {
                account.setRole(AppRole.ROLE_GROWER);
            }
            int userId = accountService.saveUser(account);
            String encodedEmail = CryptographyUtils.encryptData(email);
            String applicationID = ApplicationConfig.getAppUrl();
            String linkTxt = "<a  target=\"_blank\" href=\"" + applicationID
                    + "/verifyAccount.htm?uid=" + encodedEmail + "\" >"
                    + "click here"
                    + "</a>";

            String msgText = "Dear "
                    + firstName + " " + lastName
                    + "<br/><br/>You have successfully registered with Planting Profits. Your account details are as follows : <br>"
                    + "<br/><b>Email Id : " + email + "<br>" + "<b>Password : " + password + "<br>"
                    + "<br/><br/><br/>To verify your account  <b>" + email
//                    + "</b><br/><br/>Please click on below link to activate your account :<br/>"
                    + "</b><br/><br/>Please "+ linkTxt + " to activate your account<br/>"
                    + "<br><br><br>Regards" + " :  "
                    + "Planting Profits Application Service Team ";
            emailService.sendEmail(email, "Planting Profit Verification", msgText);


            PlantingProfitLogger.info("Sending request to Salesforce lead code");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("oid", "00D36000000jISU"));
            params.add(new BasicNameValuePair("00N3600000SnyFh","1"));
            params.add(new BasicNameValuePair("retURL", ""));
            params.add(new BasicNameValuePair("first_name", account.getFirstName()));
            params.add(new BasicNameValuePair("last_name", account.getLastName()));
            params.add(new BasicNameValuePair("email", account.getEmail_Address()));
            params.add(new BasicNameValuePair("mobile", account.getPhone_No()));
            params.add(new BasicNameValuePair("street", account.getPhysical_Address_Line_1()!=null?account.getPhysical_Address_Line_1():""));
            params.add(new BasicNameValuePair("00N3600000SnyfQ",account.getPhysical_Address_Line_2()!=null?account.getPhysical_Address_Line_2():"" ));
            params.add(new BasicNameValuePair("city", account.getPhysical_Address_City()!=null?account.getPhysical_Address_City() :""));
            params.add(new BasicNameValuePair("state", account.getPhysical_Address_State()!=null?account.getPhysical_Address_State().getStateName(): ""));
            params.add(new BasicNameValuePair("zip", account.getPhysical_Address_Zip()));
            params.add(new BasicNameValuePair("country", account.getPhysical_Address_Country()!=null?account.getPhysical_Address_Country().getCountryName():""));

            params.add(new BasicNameValuePair("00N3600000SnyfM", account.getMailing_Address_Line_1()!=null?account.getMailing_Address_Line_1():""));
            params.add(new BasicNameValuePair("00N3600000SnyfN",account.getMailing_Address_Line_2()!=null?account.getMailing_Address_Line_2():"" ));
            params.add(new BasicNameValuePair("00N3600000SnyfK", account.getMailing_Address_City()!=null?account.getMailing_Address_City():""));
            params.add(new BasicNameValuePair("00N3600000SnyfO", account.getMailing_Address_State()!=null?account.getMailing_Address_State().getStateName(): ""));
            params.add(new BasicNameValuePair("00N3600000SnyfL", account.getMailing_Address_Country()!=null?account.getMailing_Address_Country().getCountryName():""));
            params.add(new BasicNameValuePair("00N3600000SnyfP", account.getMailing_Address_Zip()));


//            params.add(new BasicNameValuePair("00N3600000SnyFh", "1"));

            try {

                httpService.sendPost("https://webto.salesforce.com/servlet/servlet.WebToLead?encoding=UTF-8", params);

            } catch (Exception e) {
                PlantingProfitLogger.error(e);
            }

            PlantingProfitLogger.info("Completed Sending request to Salesforce lead code");

            jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_ALREADY_EXISTS);
        }
        return jsonResponse;

    }

    @RequestMapping(value = "changeUserPassword", method = RequestMethod.POST)
    public JsonResponse changeUserPassword(
            HttpServletRequest request,
            @RequestParam(value = "currentPassword", required = true) String currentPassword,
            @RequestParam(value = "newPassword", required = true) String newPassword) {
        PlantingProfitLogger.info("inside changeUserPassword " + currentPassword + " newPassword : " + newPassword);
        JsonResponse jsonResponse = new JsonResponse();
        PasswordEncoder encoder = new Md5PasswordEncoder();
        String encodedCurrentPassword = encoder.encodePassword(currentPassword, null);
        Account user = accountService.getCurrentUser();

        if (user != null) {
            String presentPassword = user.getPassword();
            if (presentPassword.equals(encodedCurrentPassword)) {
                PlantingProfitLogger.info("password matched ");
                String encodedNewPassword = encoder.encodePassword(newPassword, null);
                user.setPassword(encodedNewPassword);
                boolean check = accountService.UpdateUser(user);
                if (check) {
                    jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
                } else {
                    jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
                }

            } else {
                PlantingProfitLogger.info("password not matched ");
                jsonResponse.setStatus(JsonResponse.RESULT_NOT_EXISTS);
            }
        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_INVALID_USER_NOT_EXISTS);
        }

        return jsonResponse;

    }


    @RequestMapping(value = "getStatesForCountry", method = RequestMethod.POST)
    public JsonResponse getStatesForCountry(@RequestParam(value = "countryId") String countryId) {
        JsonResponse jsonResponse = new JsonResponse();

        List<UserState> statesForCountry = accountService.getStatesForCountry(Integer.parseInt(countryId));
        PlantingProfitLogger.info("Total states for country are : " + statesForCountry.size());
        if (statesForCountry.size() == 0) {
            jsonResponse.setStatus(JsonResponse.RESULT_FAILED);
        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
            jsonResponse.setResult(statesForCountry);
        }


        return jsonResponse;
    }

}
