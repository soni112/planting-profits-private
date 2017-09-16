package com.decipher.agriculture.controller;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.account.UserCountry;
import com.decipher.agriculture.data.account.UserState;
import com.decipher.agriculture.data.reportissue.Issue;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.agriculture.service.reportissue.IssueService;
import com.decipher.util.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 9/9/17 5:27 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@RestController
@RequestMapping("/issue")
public class IssueController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private IssueService issueService;

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public JsonResponse createIssue(@RequestParam(value = "name") String name,
                                    @RequestParam(value = "email") String email,
                                    @RequestParam(value = "phone", required = false) String phone,
                                    @RequestParam(value = "address", required = false) String address,
                                    @RequestParam(value = "state", required = false) Integer state,
                                    @RequestParam(value = "country", required = false) Integer country,
                                    @RequestParam(value = "zipcode", required = false) String zipcode,
                                    @RequestParam(value = "issue") String issue){
        JsonResponse jsonResponse = new JsonResponse();

        Issue issueRaised = new Issue();
        issueRaised.setName(name);
        issueRaised.setEmail(email);
        issueRaised.setPhoneNo(phone);
        issueRaised.setAddress(address);
        issueRaised.setZipcode(zipcode);
        issueRaised.setIssueRaised(issue);

        UserState userState = accountService.getState(state);
        if (userState != null) {
            issueRaised.setState(userState);
        }
        UserCountry userCountry = accountService.getCountry(country);
        if (userCountry != null) {
            issueRaised.setCountry(userCountry);
        }

        Account currentUser = accountService.getCurrentUser();
        if (currentUser != null){
            issueRaised.setAccount(currentUser);
            issueRaised.setTypeOfUser("Application User");
        } else {
            issueRaised.setTypeOfUser("Anonymous User");
        }

        issueService.createIssue(issueRaised);

        jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);

        return jsonResponse;
    }

}
