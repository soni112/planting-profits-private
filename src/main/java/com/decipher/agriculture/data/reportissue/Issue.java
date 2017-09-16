package com.decipher.agriculture.data.reportissue;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.account.UserCountry;
import com.decipher.agriculture.data.account.UserState;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 9/9/17 2:45 PM by Abhishek Samuel
 * Software Engineer
 * abhisheks@decipherzone.com
 * Decipher Zone Softwares LLP
 * www.decipherzone.com
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity
@Table(name = "ISSUE")
public class Issue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ISSUE_ID")
    private Integer issueId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNo;

    @Column(name = "ADDRESS")
    private String address;

    @OneToOne
    @JoinColumn(name = "COUNTY")
    private UserCountry country;

    @OneToOne
    @JoinColumn(name = "STATE")
    private UserState state;

    @Column(name = "ZIPCODE")
    private String zipcode;

    @Column(name = "ISSUERAISED", columnDefinition="TEXT")
    private String issueRaised;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @OneToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @Column(name = "TYPE_OF_USER")
    private String typeOfUser;

    public Issue() {
        createdDate = new Date();
    }

    public Integer getIssueId() {
        return issueId;
    }

    public void setIssueId(Integer issueId) {
        this.issueId = issueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserCountry getCountry() {
        return country;
    }

    public void setCountry(UserCountry country) {
        this.country = country;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getIssueRaised() {
        return issueRaised;
    }

    public void setIssueRaised(String issueRaised) {
        this.issueRaised = issueRaised;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }
}
