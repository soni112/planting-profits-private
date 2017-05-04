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
@Entity(name = "SummaryCropInfo")
@Table(name = "SUMMARY_CROP_INFO", uniqueConstraints = @UniqueConstraint(columnNames = "SUMMARY_CROP_INFO_ID"))
public class SummaryCropInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUMMARY_CROP_INFO_ID")
    private Integer id;
    @Column(name = "UOM_CROP")
    private String uomCrop;
    @Column(name = "FIELD_CROP")
    private String fieldCrop;
    @Column(name = "VEG_OR_FRUIT_CROP")
    private String vegOrFruitCrop;
    @Column(name = "HI_RISK_CROP")
    private String hiRiskCrop;
    @Column(name = "CONSERVATION_CROP")
    private String conservation_Crop;
    @Column(name = "IRRIGATED")
    private String irrigated;
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

    public String getUomCrop() {
        return uomCrop;
    }

    public void setUomCrop(String uomCrop) {
        this.uomCrop = uomCrop;
    }

    public String getFieldCrop() {
        return fieldCrop;
    }

    public void setFieldCrop(String fieldCrop) {
        this.fieldCrop = fieldCrop;
    }

    public String getVegOrFruitCrop() {
        return vegOrFruitCrop;
    }

    public void setVegOrFruitCrop(String vegOrFruitCrop) {
        this.vegOrFruitCrop = vegOrFruitCrop;
    }

    public String getHiRiskCrop() {
        return hiRiskCrop;
    }

    public void setHiRiskCrop(String hiRiskCrop) {
        this.hiRiskCrop = hiRiskCrop;
    }

    public String getConservation_Crop() {
        return conservation_Crop;
    }

    public void setConservation_Crop(String conservation_Crop) {
        this.conservation_Crop = conservation_Crop;
    }

    public String getIrrigated() {
        return irrigated;
    }

    public void setIrrigated(String irrigated) {
        this.irrigated = irrigated;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }
}
