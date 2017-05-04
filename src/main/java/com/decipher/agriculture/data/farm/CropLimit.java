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
@Entity(name = "CropLimit")
@Table(name = "CROP_LIMIT", uniqueConstraints = @UniqueConstraint(columnNames = "CROP_LIMIT_ID"))
public class CropLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CROP_LIMIT_ID")
    private Integer id;
    @Column(name = "MINIMUM_ACRES")
    private String minimumAcres;
    @Column(name = "MAXIMUM_ACRES")
    private String maximumAcres;

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

    public String getMinimumAcres() {
        return minimumAcres;
    }

    public void setMinimumAcres(String minimumAcres) {
        this.minimumAcres = minimumAcres;
    }

    public String getMaximumAcres() {
        return maximumAcres;
    }

    public void setMaximumAcres(String maximumAcres) {
        this.maximumAcres = maximumAcres;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }
}
