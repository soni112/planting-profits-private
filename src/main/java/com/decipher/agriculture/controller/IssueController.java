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
    public JsonResponse createIssue(@RequestParam(value = "issue") String issue) {
        JsonResponse jsonResponse = new JsonResponse();

        Account currentUser = accountService.getCurrentUser();

        Issue issueRaised = new Issue();
        issueRaised.setName(currentUser.getFirstName() + " " + currentUser.getLastName());
        issueRaised.setEmail(currentUser.getEmail_Address());
        issueRaised.setPhoneNo(currentUser.getPhone_No());
        issueRaised.setAddress(currentUser.getPhysical_Address_Line_1() + " " + currentUser.getPhysical_Address_Line_2() + " " +
                currentUser.getPhysical_Address_Country().getCountryName() + " " + currentUser.getPhysical_Address_State().getStateName() + " " + currentUser.getPhysical_Address_City());
        issueRaised.setZipcode(currentUser.getPhysical_Address_Zip());
        issueRaised.setIssueRaised(issue);
        issueRaised.setState(currentUser.getPhysical_Address_State());
        issueRaised.setCountry(currentUser.getPhysical_Address_Country());
        issueRaised.setAccount(currentUser);
        issueRaised.setTypeOfUser("Application User");

        issueService.createIssue(issueRaised);

        jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);

        return jsonResponse;
    }

}
