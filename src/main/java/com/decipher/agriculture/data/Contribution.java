package com.decipher.agriculture.data;

import com.decipher.agriculture.data.account.Account;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by Raja Dushyant Vashishtha
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "Contribution")
@Table(name = "CONTRIBUTION")
public class Contribution{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
    @Column(name = "CHARGE_ID")
    private String chargeId;
    @Column(name = "AMOUNT")
    private Integer amount;
    @Column(name = "AMOUNT_REFUND")
    private Integer amountRefunded;
    @Column(name = "APPLICAITON_FEE")
    private String applicationFee;
    @Column(name = "BALANCE_TRANSACTION")
    private String balanceTransaction;
    @Column(name = "CAPTURED")
    private Boolean captured;
    @Column(name = "CREATED")
    private Long created;
    @Column(name = "CURRENCY")
    private String currency;
    @Column(name = "CUSTOMER")
    private String customer;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "DESTINATION")
    private String destination;
    @Column(name = "FAILURE_CODE")
    private String failureCode;
    @Column(name = "FAILURE_MESSAGE")
    private String failureMessage;
    @Column(name = "INVOICE")
    private String invoice;
    @Column(name = "LIVE_MODE")
    private Boolean livemode;
    @Column(name = "ORDER_ID")
    private String order;
    @Column(name = "PAID")
    private Boolean paid;
    @Column(name = "RECEIPT_EMAIL")
    private String receiptEmail;
    @Column(name = "RECEIPT_NUMBER")
    private String receiptNumber;
    @Column(name = "REFUNDED")
    private Boolean refunded;
    @Column(name = "SOURCE_TRANSFER")
    private String sourceTransfer;
    @Column(name = "STATEMENT_DESCRIPTION")
    private String statementDescriptor;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "TRANSFER")
    private String transfer;

    public Long getId() {
        return id;
    }

    public void setId(Long contributionId) {
        this.id = contributionId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmountRefunded() {
        return amountRefunded;
    }

    public void setAmountRefunded(Integer amountRefunded) {
        this.amountRefunded = amountRefunded;
    }

    public String getApplicationFee() {
        return applicationFee;
    }

    public void setApplicationFee(String applicationFee) {
        this.applicationFee = applicationFee;
    }

    public String getBalanceTransaction() {
        return balanceTransaction;
    }

    public void setBalanceTransaction(String balanceTransaction) {
        this.balanceTransaction = balanceTransaction;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public void setCaptured(Boolean captured) {
        this.captured = captured;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public String getReceiptEmail() {
        return receiptEmail;
    }

    public void setReceiptEmail(String receiptEmail) {
        this.receiptEmail = receiptEmail;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Boolean getRefunded() {
        return refunded;
    }

    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    public String getSourceTransfer() {
        return sourceTransfer;
    }

    public void setSourceTransfer(String sourceTransfer) {
        this.sourceTransfer = sourceTransfer;
    }

    public String getStatementDescriptor() {
        return statementDescriptor;
    }

    public void setStatementDescriptor(String statementDescriptor) {
        this.statementDescriptor = statementDescriptor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }
}
