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
@Entity(name = "CropResourceUsageFieldVariances")
@Table(name = "CROP_RES_USAGE_FIELD_VARI", uniqueConstraints = @UniqueConstraint(columnNames = "CROP_RES_USAGE_FIELD_VARI_ID"))
public class CropResourceUsageFieldVariances implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CROP_RES_USAGE_FIELD_VARI_ID")
    private Integer id;
    @Column(name = "CROP_FIELD_RESOURCE_USE")
    private String cropFieldResourceUse;

    @Column(name = "CROP_RESOURCE_AMOUNT")
    private String cropResourceAmount;

    @ManyToOne
    @JoinColumn(name = "CROP_TYPE_ID")
    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @JsonIgnore
    private CropType cropType;

    /*
     * @OneToOne
     *
     * @JoinColumn(name = "CROP_RESOURCE_USAGE_ID") private CropResourceUsage
     * cropResourceUsage;
     */
    public String getCropResourceAmount() {
        return cropResourceAmount;
    }

    public void setCropResourceAmount(String cropResourceAmount) {
        this.cropResourceAmount = cropResourceAmount;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCropFieldResourceUse() {
        return cropFieldResourceUse;
    }

    public void setCropFieldResourceUse(String cropFieldResourceUse) {
        this.cropFieldResourceUse = cropFieldResourceUse;
    }
    /*
	 * public CropResourceUsage getCropResourceUsage() { return
	 * cropResourceUsage; } public void setCropResourceUsage(CropResourceUsage
	 * cropResourceUsage) { this.cropResourceUsage = cropResourceUsage; }
	 */

}
