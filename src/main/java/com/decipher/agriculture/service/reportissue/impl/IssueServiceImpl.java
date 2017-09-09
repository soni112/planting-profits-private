package com.decipher.agriculture.service.reportissue.impl;

import com.decipher.agriculture.dao.reportissue.IssueDao;
import com.decipher.agriculture.data.reportissue.Issue;
import com.decipher.agriculture.service.reportissue.IssueService;
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

    @Override
    public void createIssue(Issue issue) {
        issueDao.createIssue(issue);
    }
}
