package com.decipher.agriculture.data.account;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by abhishek on 7/4/16.
 */
/**
 * @added - Abhishek
 * @date - 13-04-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity
@Table(name = "ACCOUNT_DOCUMENTS")
public class AccountDocuments implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DOCUMENT_ID")
    private Integer id;

    @Column(name = "DOCUMENT_PATH")
    private String documentPath;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account documentHolder;

    @Column(name = "DOCUMENT_TYPE")
    private AccountDocumentsType accountDocumentsType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public Account getDocumentHolder() {
        return documentHolder;
    }

    public void setDocumentHolder(Account documentHolder) {
        this.documentHolder = documentHolder;
    }

    public AccountDocumentsType getAccountDocumentsType() {
        return accountDocumentsType;
    }

    public void setAccountDocumentsType(AccountDocumentsType accountDocumentsType) {
        this.accountDocumentsType = accountDocumentsType;
    }
}
