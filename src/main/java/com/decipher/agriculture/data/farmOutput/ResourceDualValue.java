package com.decipher.agriculture.data.farmOutput;

import com.decipher.agriculture.data.farm.CropResourceUsage;
import com.decipher.agriculture.data.farm.FarmInfo;
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
@Entity(name = "ResourceDualValue")
@Table(name = "RESOURCE_DUAL_VALUE", uniqueConstraints = @UniqueConstraint(columnNames = "RESOURCE_DUAL_VALUE_ID"))
public class ResourceDualValue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RESOURCE_DUAL_VALUE_ID")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "CROP_RESOURCE_USAGE_ID", referencedColumnName = "CROP_RESOURCE_USAGE_ID")
    private CropResourceUsage cropResourceUsage;

    @Column(name = "PRIMAL_VALUE")
    private Double primalValue;

    @Column(name = "DUAL_VALUE")
    private Double dualValue;

    @ManyToOne
    @JoinColumn(name = "FARM_INFO_ID")
    /**
     * @changed - Abhishek
     * @date - 07-01-2016
     * @desc - @JsonIgnore for removing org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
     */
    @JsonIgnore
    private FarmInfo farmInfo;

    transient private long profitPerUnit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CropResourceUsage getCropResourceUsage() {
        return cropResourceUsage;
    }

    public void setCropResourceUsage(CropResourceUsage cropResourceUsage) {
        this.cropResourceUsage = cropResourceUsage;
    }

    public Double getPrimalValue() {
        return primalValue;
    }

    public void setPrimalValue(Double primalValue) {
        this.primalValue = primalValue;
    }

    public Double getDualValue() {
        return dualValue;
    }

    public void setDualValue(Double dualValue) {
        this.dualValue = dualValue;
    }

    public FarmInfo getFarmInfo() {
        return farmInfo;
    }

    public void setFarmInfo(FarmInfo farmInfo) {
        this.farmInfo = farmInfo;
    }

    public long getProfitPerUnit() {
        return profitPerUnit;
    }

    public void setProfitPerUnit(long l) {
        this.profitPerUnit = l;
    }

}