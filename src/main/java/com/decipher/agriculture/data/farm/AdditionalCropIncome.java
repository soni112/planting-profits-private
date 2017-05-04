package com.decipher.agriculture.data.farm;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by abhishek on 10/9/16.
 */
@Cache(region = "planting-Second-level-Cache", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity
@Table(name = "ADDITIONAL_CROP_INCOME")
public class AdditionalCropIncome implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "GOVERNMENT_PAYMENTS")
    private double governmentPayments;
    @Column(name = "GOVERNMENT_PRICE")
    private double coProductsPrice;
    @Column(name = "ADDITIONAL_VARIABLE_PRODUCTION_COST")
    private double additionalVariableProductionCost;
    @Column(name = "ADDITIONAL_INCOME")
    private double additionalIncome;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CROP_TYPE_ID")
    private CropType cropType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getGovernmentPayments() {
        return governmentPayments;
    }

    public void setGovernmentPayments(double governmentPayments) {
        this.governmentPayments = governmentPayments;
    }

    public double getCoProductsPrice() {
        return coProductsPrice;
    }

    public void setCoProductsPrice(double coProductsPrice) {
        this.coProductsPrice = coProductsPrice;
    }

    public double getAdditionalVariableProductionCost() {
        return additionalVariableProductionCost;
    }

    public void setAdditionalVariableProductionCost(double additionalVariableProductionCost) {
        this.additionalVariableProductionCost = additionalVariableProductionCost;
    }

    public double getAdditionalIncome() {
        return additionalIncome;
    }

    public void setAdditionalIncome(double additionalIncome) {
        this.additionalIncome = additionalIncome;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }
}
