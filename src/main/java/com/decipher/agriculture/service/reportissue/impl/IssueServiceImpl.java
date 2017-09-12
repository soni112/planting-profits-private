package com.decipher.agriculture.service.reportissue.impl;

import com.decipher.agriculture.dao.reportissue.IssueDao;
import com.decipher.agriculture.data.reportissue.Issue;
import com.decipher.agriculture.service.reportissue.IssueService;
import com.decipher.util.email.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 9/9/17 5:26 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private SendEmail sendEmail;

    @Override
    public void createIssue(Issue issue) {
        issueDao.createIssue(issue);
        sendEmail.sendEmail("issue@plantingprofit.com",issue.getEmail(),issue.getIssueRaised());
    }
}
