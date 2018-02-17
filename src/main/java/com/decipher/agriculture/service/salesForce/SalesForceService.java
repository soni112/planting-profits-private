package com.decipher.agriculture.service.salesForce;


import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.agriculture.service.email.EmailService;
import com.decipher.agriculture.service.util.HTTPService;
import com.decipher.config.ApplicationConfig;
import com.decipher.util.PlantingProfitLogger;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String message = "<html>" +
                "<head><title>Contact For SalesForce Verification</title></head>" +
                "<body>"+
                "   <table>" +
                "       <tr>" +
                "            <td>First Name:-</td>" +
                "            <td>" + account.getFirstName() + "</td>" +
                "       </tr><tr>" +
                "            <td>Last Name:-</td>" +
                "            <td>" + account.getLastName() + "</td>" +
                "       </tr><tr>" +
                "            <td>Email:-</td>" +
                "            <td>" + account.getEmail_Address() + "</td>" +
                "       </tr><tr>" +
                "            <td>Phone no:-</td>"+
                "            <td>"+ account.getPhone_No() +"</td>"+
                "       </tr><tr>" +
                "            <td>Physical Line 1:-</td>" +
                "            <td>" + account.getPhysical_Address_Line_1() + "</td>" +
                "       </tr><tr>" +
                "       </tr><tr>" +
                "            <td>Physical Line 2:-</td>" +
                "            <td>" + account.getPhysical_Address_Line_2() + "</td>" +
                "       </tr><tr>" +
                "       </tr><tr>" +
                "            <td>Physical City:-</td>" +
                "            <td>" + account.getPhysical_Address_City() + "</td>" +
                "       </tr><tr>" +
                "       </tr><tr>" +
                "            <td>Physical State:-</td>" +
                "            <td>" + account.getPhysical_Address_State().getStateName() + "</td>" +
                "       </tr><tr>" +
                "       </tr><tr>" +
                "            <td>Physical Zip Code:-</td>" +
                "            <td>" + account.getPhysical_Address_Zip() + "</td>" +
                "       </tr><tr>" +
                "       </tr><tr>" +
                "            <td>Physical Country:-</td>" +
                "            <td>" + account.getPhysical_Address_Country().getCountryName() + "</td>" +
                "       </tr><tr>" +
                "            <td>Mailing Line 1:-</td>" +
                "            <td>" + account.getMailing_Address_Line_1() + "</td>" +
                "       </tr><tr>" +
                "       </tr><tr>" +
                "            <td>Mailing Line 2:-</td>" +
                "            <td>" + account.getMailing_Address_Line_2() + "</td>" +
                "       </tr><tr>" +
                "       </tr><tr>" +
                "            <td>Mailing City:-</td>" +
                "            <td>" + account.getMailing_Address_City() + "</td>" +
                "       </tr><tr>" +
                "       </tr><tr>" +
                "            <td>Mailing State:-</td>" +
                "            <td>" + account.getMailing_Address_State().getStateName() + "</td>" +
                "       </tr><tr>" +
                "       </tr><tr>" +
                "            <td>Mailing Country:-</td>" +
                "            <td>" + account.getMailing_Address_Country().getCountryName() + "</td>" +
                "       </tr><tr>" +
                "       </tr><tr>" +
                "            <td>mailing zip code :-</td>" +
                "            <td>" + account.getMailing_Address_Zip() + "</td>" +
                "</tr>" +
                "   </table>" +
                "   <a href='" + ApplicationConfig.getAppUrl() + "/agriculture/salesForceController/sendDetailsToSalesForce?enquiryId="+account.getId()+"'>Click here to submit the same to SalesForce web portal</a>" +
                "</body></html>";

        PlantingProfitLogger.info("SalesForce confirmation url "+ ApplicationConfig.getAppUrl());
        emailService.sendEmail(ApplicationConfig.getAppEmail(), "Planting Profits SalesForce Enquiry", message);
    }

    public boolean submitLead(int accountId){
        Account account = accountService.getUserById(accountId);

        if (account != null){

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

        try {
            httpService.sendPost("https://webto.salesforce.com/servlet/servlet.WebToLead?encoding=UTF-8", params);
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        }
        PlantingProfitLogger.info("Completed Sending request to SalesForce lead code");
        return true;
    } else{
            PlantingProfitLogger.info("Account is null for this SalesForce request.");
            return false;
        }
    }


}
