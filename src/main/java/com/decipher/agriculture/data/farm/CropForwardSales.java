package com.decipher.agriculture.data.farm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "CropForwardSales")
@Table(name = "CROP_FORWARD_SALES", uniqueConstraints = @UniqueConstraint(columnNames = "CROP_FORWARD_SALES_ID"))
public class CropForwardSales {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CROP_FORWARD_SALES_ID")
    private Integer id;
    @Column(name = "PRICE")
    private String price;
    @Column(name = "QUANTITY")
    private String quantity;
    @Column(name = "PROPOSED_CHECKED")
    private Boolean proposedchecked;
    @Column(name = "FIRM_CHECKED")
    private String firmchecked;
    @Column(name = "CONTACT_IDENTIFIER")
    // change by rohit
    private String contactIdentifier;
    @Column(name = "ACRES")
    private double acres;
    @Column(name = "UPPER_LIMIT")
    private double upperLimit;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CROP_TYPE_ID")
    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @JsonIgnore
    private CropType cropType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFirmchecked() {
        return firmchecked;
    }

    public void setFirmchecked(String firmchecked) {
        this.firmchecked = firmchecked;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    public String getContactIdentifier() {
        return contactIdentifier;
    }

    public void setContactIdentifier(String contactIdentifier) {
        this.contactIdentifier = contactIdentifier;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public double getAcres() {
        return acres;
    }

    public void setAcres(double acres) {
        this.acres = acres;
    }

    public Boolean getProposedchecked() {
        return proposedchecked;
    }

    public void setProposedchecked(Boolean proposedchecked) {
        this.proposedchecked = proposedchecked;
    }

}
