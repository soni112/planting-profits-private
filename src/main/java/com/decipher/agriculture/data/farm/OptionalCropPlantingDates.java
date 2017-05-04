package com.decipher.agriculture.data.farm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Date;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "OptionalCropPlantingDates")
@Table(name = "OPT_CROP_PLANT_DAT", uniqueConstraints = @UniqueConstraint(columnNames = "OPT_CROP_PLANT_DAT_ID"))
public class OptionalCropPlantingDates {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OPT_CROP_PLANT_DAT_ID")
    private Integer id;
    @Column(name = "PREFERRED_PLANT_DATE")
    private Date preferredPlantingDate;
    @Column(name = "EARLY_PLANT_DATE")
    private Date earlyPlantingDate;
    @Column(name = "LATE_PLANT_DATE")
    private Date latePlantingDate;
    @Column(name = "LENGTH_OF_SEASON")
    private Integer lengthofSeason;
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

    public Date getPreferredPlantingDate() {
        return preferredPlantingDate;
    }

    public void setPreferredPlantingDate(Date preferredPlantingDate) {
        this.preferredPlantingDate = preferredPlantingDate;
    }

    public Date getEarlyPlantingDate() {
        return earlyPlantingDate;
    }

    public void setEarlyPlantingDate(Date earlyPlantingDate) {
        this.earlyPlantingDate = earlyPlantingDate;
    }

    public Date getLatePlantingDate() {
        return latePlantingDate;
    }

    public void setLatePlantingDate(Date latePlantingDate) {
        this.latePlantingDate = latePlantingDate;
    }

    public Integer getLengthofSeason() {
        return lengthofSeason;
    }

    public void setLengthofSeason(Integer lengthofSeason) {
        this.lengthofSeason = lengthofSeason;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }
}
