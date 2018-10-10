package com.decipher.agriculture.data.farm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "InternalVariableCropProductionCosts")
@Table(name = "INT_VAR_CROP_PRO_COSTS", uniqueConstraints = @UniqueConstraint(columnNames = "INT_VAR_CROP_PRO_COSTS_ID"))
public class InternalVariableCropProductionCosts implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INT_VAR_CROP_PRO_COSTS_ID")
    private Integer id;
    @Column(name = "SUMMARY_VARIABLE_PRODUCTION_COST", columnDefinition = "Decimal(19,4)")
    private BigDecimal summaryVariableProductionCost;
    @Column(name = "CALCULATED_VARIABLE_PRODUCTION_COST", columnDefinition = "Decimal(19,4)")
    private BigDecimal calculatedVariableProductionCost;

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

    public BigDecimal getSummaryVariableProductionCost() {
        return summaryVariableProductionCost;
    }

    public void setSummaryVariableProductionCost(
            BigDecimal summaryVariableProductionCost) {
        this.summaryVariableProductionCost = summaryVariableProductionCost;
    }

    public BigDecimal getCalculatedVariableProductionCost() {
        return calculatedVariableProductionCost;
    }

    public void setCalculatedVariableProductionCost(BigDecimal calculatedVariableProductionCost) {
        this.calculatedVariableProductionCost = calculatedVariableProductionCost;
    }

    public CropType getCropType() {
        return cropType;
    }

    public void setCropType(CropType cropType) {
        this.cropType = cropType;
    }

}
