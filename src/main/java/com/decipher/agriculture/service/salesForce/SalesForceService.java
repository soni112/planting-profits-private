package com.decipher.agriculture.service.salesForce;


import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.agriculture.service.email.EmailService;
import com.decipher.agriculture.service.util.HTTPService;
import com.decipher.config.ApplicationConfig;
import com.decipher.util.PlantingProfitLogger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesForceService {
    @Autowired
    private EmailService emailService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private HTTPService httpService;

    public void createLead(Account account){

        InputStream inputStream = SalesForceService.class.getResourceAsStream("/email-templates/salesforce-email-template.html");
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer, "UTF-8");
        } catch (IOException e) {
            PlantingProfitLogger.error(e.getMessage(), e);
        }
        String body = writer.toString();

        body = body.replaceAll("==account-FirstName==", account.getFirstName());
        body = body.replaceAll("==account-LastName==", account.getLastName());
        body = body.replaceAll("==account-Email_Address==", account.getEmail_Address());
        body = body.replaceAll("==account-Phone_No==", account.getPhone_No());

        body = body.replaceAll("==account-Physical_Address_Line_1==", account.getPhysical_Address_Line_1());
        body = body.replaceAll("==account-Physical_Address_Line_2==", account.getPhysical_Address_Line_2());
        body = body.replaceAll("==account-Physical_Address_City==", account.getPhysical_Address_City());
        body = body.replaceAll("==account-Physical_Address_State==", account.getPhysical_Address_State() != null ? account.getPhysical_Address_State().getStateName() : StringUtils.EMPTY);
        body = body.replaceAll("==account-Physical_Address_Country==", account.getPhysical_Address_Country() != null ? account.getPhysical_Address_Country().getCountryName() : StringUtils.EMPTY);
        body = body.replaceAll("==account-Physical_Address_Zip==", account.getPhysical_Address_Zip());

        body = body.replaceAll("==account-Mailing_Address_Line_1==", account.getMailing_Address_Line_1());
        body = body.replaceAll("==account-Mailing_Address_Line_2==", account.getMailing_Address_Line_2());
        body = body.replaceAll("==account-Mailing_Address_City==", account.getMailing_Address_City());
        body = body.replaceAll("==account-Mailing_Address_State==", account.getMailing_Address_State() != null ?account.getMailing_Address_State().getStateName() : StringUtils.EMPTY);
        body = body.replaceAll("==account-Mailing_Address_Country==", account.getMailing_Address_Country() != null ? account.getMailing_Address_Country().getCountryName() : StringUtils.EMPTY);
        body = body.replaceAll("==account-Mailing_Address_Zip==", account.getMailing_Address_Zip());

        body = body.replaceAll("###applicationConfig-AppUrl###", ApplicationConfig.getAppUrl());
        body = body.replaceAll("###account-Id###", account.getId().toString());

        PlantingProfitLogger.info("SalesForce confirmation url "+ ApplicationConfig.getAppEmail());
        emailService.sendEmail(ApplicationConfig.getAppEmail(), "Planting Profits SalesForce Enquiry", body);
    }

    public boolean submitLead(int accountId){
        Account account = accountService.getUserById(accountId);

        if (account != null) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("oid", "00D36000000jISU"));
            params.add(new BasicNameValuePair("00N3600000SnyFh", "1"));
            params.add(new BasicNameValuePair("retURL", ""));
            params.add(new BasicNameValuePair("first_name", account.getFirstName()));
            params.add(new BasicNameValuePair("last_name", account.getLastName()));
            params.add(new BasicNameValuePair("email", account.getEmail_Address()));
            params.add(new BasicNameValuePair("mobile", account.getPhone_No()));

            params.add(new BasicNameValuePair("street", account.getPhysical_Address_Line_1()));
            params.add(new BasicNameValuePair("00N3600000SnyfQ", account.getPhysical_Address_Line_2()));
            params.add(new BasicNameValuePair("city", account.getPhysical_Address_City()));
            params.add(new BasicNameValuePair("state", account.getPhysical_Address_State() != null ? account.getPhysical_Address_State().getStateName() : ""));
            params.add(new BasicNameValuePair("country", account.getPhysical_Address_Country() != null ? account.getPhysical_Address_Country().getCountryName() : ""));
            params.add(new BasicNameValuePair("zip", account.getPhysical_Address_Zip()));

            params.add(new BasicNameValuePair("00N3600000SnyfM", account.getMailing_Address_Line_1()));
            params.add(new BasicNameValuePair("00N3600000SnyfN", account.getMailing_Address_Line_2()));
            params.add(new BasicNameValuePair("00N3600000SnyfK", account.getMailing_Address_City()));
            params.add(new BasicNameValuePair("00N3600000SnyfO", account.getMailing_Address_State() != null ? account.getMailing_Address_State().getStateName() : ""));
            params.add(new BasicNameValuePair("00N3600000SnyfL", account.getMailing_Address_Country() != null ? account.getMailing_Address_Country().getCountryName() : ""));
            params.add(new BasicNameValuePair("00N3600000SnyfP", account.getMailing_Address_Zip()));
            params.add(new BasicNameValuePair("debug", "1"));

            try {
                httpService.sendPost("http://webto.salesforce.com/servlet/servlet.WebToLead?encoding=UTF-8", params);
            } catch (Exception e) {
                PlantingProfitLogger.error(e);
            }
            PlantingProfitLogger.info("Completed Sending request to SalesForce lead code");
            return true;
        } else {
            PlantingProfitLogger.info("Account is null for this SalesForce request.");
            return false;
        }
    }


}
