package com.decipher.agriculture.data.farm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "InternalCropPricesInfo")
@Table(name = "INTERNAL_CROP_PRICES_INFO", uniqueConstraints = @UniqueConstraint(columnNames = "INTERNAL_CROP_PRICES_INFO_ID"))
public class InternalCropPricesInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INTERNAL_CROP_PRICES_INFO_ID")
    private Integer id;
    @Column(name = "INT_EXP_CROP_PRICE", columnDefinition = "Decimal(19,4)")
    private BigDecimal intExpCropPrice;
    @Column(name = "INT_MIN_CROP_PRICE", columnDefinition = "Decimal(19,4)")
    private BigDecimal intMinCropPrice;
    @Column(name = "INT_MAX_CROP_PRICE", columnDefinition = "Decimal(19,4)")
    private BigDecimal intMaxCropPrice;
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

    public BigDecimal getIntExpCropPrice() {
        return intExpCropPrice;
    }

    public void setIntExpCropPrice(BigDecimal intExpCropPrice) {
        this.intExpCropPrice = intExpCropPrice;
    }

    public BigDecimal getIntMinCropPrice() {
        return intMinCropPrice;
    }

    public void setIntMinCropPrice(BigDecimal intMinCropPrice) {
        this.intMinCropPrice = intMinCropPrice;
    }

    public BigDecimal getIntMaxCropPrice() {
        return intMaxCropPrice;
    }

    public void setIntMaxCropPrice(BigDecimal intMaxCropPrice) {
        this.intMaxCropPrice = intMaxCropPrice;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

}
