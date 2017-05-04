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
@Entity(name = "OptionalCropProductionCostsDetails")
@Table(name = "OPT_CROP_PRO_COST_DET", uniqueConstraints = @UniqueConstraint(columnNames = "OPT_CROP_PRO_COST_DET_ID"))
public class OptionalCropProductionCostsDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OPT_CROP_PRO_COST_DET_ID")
    private Integer id;
    @Column(name = "COST_COMP_NAME")
    private String CostComponentsName;
    @Column(name = "UNITS_PER_ACRE", columnDefinition = "Decimal(19,4)")
    private BigDecimal unitsPerAcre;
    @Column(name = "UNIT_PRICES", columnDefinition = "Decimal(19,4)")
    private BigDecimal unitPrices;
    @Column(name = "UNIT_TOTAL", columnDefinition = "Decimal(19,4)")
    private BigDecimal unitTotal;
    @ManyToOne
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

    public String getCostComponentsName() {
        return CostComponentsName;
    }

    public void setCostComponentsName(String costComponentsName) {
        CostComponentsName = costComponentsName;
    }

    public BigDecimal getUnitsPerAcre() {
        return unitsPerAcre;
    }

    public void setUnitsPerAcre(BigDecimal unitsPerAcre) {
        this.unitsPerAcre = unitsPerAcre;
    }

    public BigDecimal getUnitPrices() {
        return unitPrices;
    }

    public void setUnitPrices(BigDecimal unitPrices) {
        this.unitPrices = unitPrices;
    }

    public BigDecimal getUnitTotal() {
        return unitTotal;
    }

    public void setUnitTotal(BigDecimal unitTotal) {
        this.unitTotal = unitTotal;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

}
