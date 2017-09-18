package com.decipher.agriculture.service.reportissue.impl;

import com.decipher.agriculture.dao.reportissue.IssueDao;
import com.decipher.agriculture.data.reportissue.Issue;
import com.decipher.agriculture.service.reportissue.IssueService;
import com.decipher.agriculture.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created on 9/9/17 5:26 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@Service
public class IssueServiceImpl implements IssueService {

    private static final String ISSUE_EMAIL = "issue@plantingprofits.com";
    private static final String ISSUE_SUBJECT = "Issue Raised in Planting profits";

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private EmailService emailService;

    @Override
    public void createIssue(Issue issue) {
        issueDao.createIssue(issue);

        String messageBody = "<table>" +
                "<tr><td colspan='2'><b> Planting Profits Issue Raised </b></td></tr>" +
                "<tr>" +
                "   <td><b>Date : </b></td>" +
                "   <td>" + new Date() + "</td>" +
                "</tr>" +
                "<tr>" +
                "   <td><b>Type of User : </b></td>" +
                "   <td>"+ issue.getTypeOfUser() +"</td>" +
                "</tr>" +
                "<tr>" +
                "   <td><b>Name : </b></td>" +
                "   <td>" + issue.getName() + "</td>" +
                "</tr>" +
                "<tr>" +
                "   <td><b>Address : </b></td>" +
                "   <td>" + issue.getAddress() + "</td>" +
                "</tr>" +
                "<tr>" +
                "   <td><b>Phone Number : </b></td>" +
                "   <td>" + issue.getPhoneNo() + "</td>" +
                "</tr>" +
                "<tr>" +
                "   <td><b>Email : </b></td>" +
                "   <td>" + issue.getEmail() + "</td>" +
                "</tr>" +
                "<tr>" +
                "   <td><b>Issue Raised : </b></td>" +
                "   <td>" + issue.getIssueRaised() + "</td>" +
                "</tr>" +
                "</table>";


        emailService.sendEmail(ISSUE_EMAIL, ISSUE_SUBJECT, messageBody);
    }
}
