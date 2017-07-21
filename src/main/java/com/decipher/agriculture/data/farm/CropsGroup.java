package com.decipher.agriculture.data.farm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "CropsGroup")
@Table(name = "CROPS_GROUP", uniqueConstraints = @UniqueConstraint(columnNames = "CROPS_GROUP_ID"))
public class CropsGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CROPS_GROUP_ID")
    private Integer id;
    @Column(name = "CROPS_GROUP_NAME")
    private String cropsGroupName;
    @Column(name = "MINIMUM_ACRES")
    private String minimumAcres;
    @Column(name = "MINIMUM_ACRES_PERCENTAGE")
    private String minimumAcresPercentage;
    @Column(name = "MAXIMUM_ACRES")
    private String maximumAcres;
    @Column(name = "MAXIMUM_ACRES_PERCENTAGE")
    private String maximumAcresPercentage;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "GROUP_CROPS", joinColumns = {@JoinColumn(name = "CROPS_GROUP_ID")}, inverseJoinColumns = {@JoinColumn(name = "CROP_TYPE_ID")})
    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @JsonIgnore
    private Set<CropType> cropTypes = new HashSet<CropType>();

    @ManyToOne
    @JoinColumn(name = "FARM_INFO_ID")
    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @JsonIgnore
    private FarmInfo farmInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCropsGroupName() {
        return cropsGroupName;
    }

    public void setCropsGroupName(String cropsGroupName) {
        this.cropsGroupName = cropsGroupName;
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

    public Set<CropType> getCropTypes() {
        return cropTypes;
    }

    public void setCropTypes(Set<CropType> cropTypes) {
        this.cropTypes = cropTypes;
    }

    public FarmInfo getFarmInfo() {
        return farmInfo;
    }

    public void setFarmInfo(FarmInfo farmInfo) {
        this.farmInfo = farmInfo;
    }

    public int getMinimumAcresPercentage() {
        if (!minimumAcresPercentage.equals("")) {
            return Integer.parseInt(minimumAcresPercentage);
        } else
            return 0;
    }

    public int getMaximumAcresPercentage() {
        if (!maximumAcresPercentage.equals("")) {
            return Integer.parseInt(maximumAcresPercentage);
        } else
            return 0;
    }

    public String getMinimumAcresPercentageStr() {
        if(minimumAcresPercentage == null)
            return "";
        return minimumAcresPercentage.equals("") || minimumAcresPercentage.equals("0") ? "" : minimumAcresPercentage;
    }

    public String getMaximumAcresPercentageStr() {
        if(maximumAcresPercentage == null)
            return "";
        return maximumAcresPercentage.equals("") || maximumAcresPercentage.equals("0") ? "" : maximumAcresPercentage;
    }

    public void setMinimumAcresPercentage(String minimumAcresPercentage) {
        this.minimumAcresPercentage = minimumAcresPercentage;
    }

    public void setMaximumAcresPercentage(String maximumAcresPercentage) {
        this.maximumAcresPercentage = maximumAcresPercentage;
    }
}
