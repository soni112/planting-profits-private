package com.decipher.view.form.farmDetails;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.decipher.agriculture.data.farm.ExternalVariableCropProductionCostComponentPrices;

public class ExternalVariableCropProductionCostComponentPricesView  implements Serializable {
    private Integer id;
    private BigDecimal product;
    private BigDecimal usagePerUoMLand;
    private BigDecimal extUnitProductPrice;
    private String offeror_Source;
    private Date date;
    private Date expiration;

    public ExternalVariableCropProductionCostComponentPricesView() {

    }

    public ExternalVariableCropProductionCostComponentPricesView(
            ExternalVariableCropProductionCostComponentPrices componentPrices) {
        this.id = componentPrices.getId();
        this.product = componentPrices.getProduct();
        this.usagePerUoMLand = componentPrices.getUsagePerUoMLand();
        this.extUnitProductPrice = componentPrices.getExtUnitProductPrice();
        this.offeror_Source = componentPrices.getOfferor_Source();
        this.date = componentPrices.getDate();
        this.expiration = componentPrices.getExpiration();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getProduct() {
        return product;
    }

    public void setProduct(BigDecimal product) {
        this.product = product;
    }

    public BigDecimal getUsagePerUoMLand() {
        return usagePerUoMLand;
    }

    public void setUsagePerUoMLand(BigDecimal usagePerUoMLand) {
        this.usagePerUoMLand = usagePerUoMLand;
    }

    public BigDecimal getExtUnitProductPrice() {
        return extUnitProductPrice;
    }

    public void setExtUnitProductPrice(BigDecimal extUnitProductPrice) {
        this.extUnitProductPrice = extUnitProductPrice;
    }

    public String getOfferor_Source() {
        return offeror_Source;
    }

    public void setOfferor_Source(String offeror_Source) {
        this.offeror_Source = offeror_Source;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

}
