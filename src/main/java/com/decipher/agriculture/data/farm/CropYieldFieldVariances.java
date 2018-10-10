package com.decipher.agriculture.data.farm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "CropYieldFieldVariances")
@Table(name = "CROP_YIELD_FIELD_VARIANCES", uniqueConstraints = @UniqueConstraint(columnNames = "CROP_YIELD_FIELD_VARIANCES_ID"))
public class CropYieldFieldVariances implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CROP_YIELD_FIELD_VARIANCES_ID")
    private Integer id;
    @Column(name = "EXP_CROP_YIELD_FIELD")
    private Double expCropYieldField;
    @Column(name = "MIN_CROP_YIELD_FIELD")
    private Double minCropYieldField;
    @Column(name = "MAX_CROP_YIELD_FIELD")
    private Double maxCropYieldField;

    @Column(name = "VAR_PROD_COST")
    private Double varProductionCost;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CROP_TYPE_ID")
    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @JsonIgnore
    private CropType cropType;

    @OneToOne
    @JoinColumn(name = "FIELD_INFO_ID")

    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @JsonIgnore
    private FieldInfo fieldInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getExpCropYieldField() {
        return expCropYieldField;
    }

    public void setExpCropYieldField(Double expCropYieldField) {
        this.expCropYieldField = expCropYieldField;
    }

    public Double getMinCropYieldField() {
        return minCropYieldField;
    }

    public void setMinCropYieldField(Double minCropYieldField) {
        this.minCropYieldField = minCropYieldField;
    }

    public Double getMaxCropYieldField() {
        return maxCropYieldField;
    }

    public void setMaxCropYieldField(Double maxCropYieldField) {
        this.maxCropYieldField = maxCropYieldField;
    }

    public Double getVarProductionCost() {
        return varProductionCost;
    }

    public void setVarProductionCost(Double varProductionCost) {
        this.varProductionCost = varProductionCost;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    public FieldInfo getFieldInfo() {
        return fieldInfo;
    }

    public void setFieldInfo(FieldInfo fieldInfo) {
        this.fieldInfo = fieldInfo;
    }

}
