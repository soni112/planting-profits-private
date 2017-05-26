package com.decipher.agriculture.data.farm;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.strategy.FarmCustomStrategy;
import com.decipher.view.form.farmDetails.FarmInfoView;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by abhishek on 30/11/16.
 */
@Cache(region = "planting-Second-level-Cache", usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity(name = "Farm")
@Table(name = "FARM")
public class Farm implements Serializable, Comparable<Farm>{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FARM_ID")
    private Integer farmId;
    @Column(name = "FARM_NAME")
    private String farmName;
    @Column(name = "PHYSICAL_LOCATION")
    private String physicalLocation;
    @Column(name = "FARM_CREATED_TIME")
    private Date farmCreatedTime;
    @Column(name = "SAVE_FLAG")
    private Boolean saveFlag = false;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "farm")
    private Set<FarmInfo> farmInfoList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "farm")
    private Set<FarmCustomStrategy> farmCustomStrategy;

    public Integer getFarmId() {
        return farmId;
    }

    public void setFarmId(Integer farmId) {
        this.farmId = farmId;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getPhysicalLocation() {
        return physicalLocation;
    }

    public void setPhysicalLocation(String physicalLocation) {
        this.physicalLocation = physicalLocation;
    }

    public Date getFarmCreatedTime() {
        return farmCreatedTime;
    }

    public void setFarmCreatedTime(Date farmCreatedTime) {
        this.farmCreatedTime = farmCreatedTime;
    }

    public Boolean getSaveFlag() {
        return saveFlag;
    }

    public void setSaveFlag(Boolean saveFlag) {
        this.saveFlag = saveFlag;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<FarmInfo> getFarmInfoList() {
        return farmInfoList;
    }

    public void setFarmInfoList(Set<FarmInfo> farmInfoList) {
        this.farmInfoList = farmInfoList;
    }

    public Set<FarmCustomStrategy> getFarmCustomStrategy() {
        return farmCustomStrategy;
    }

    public void setFarmCustomStrategy(Set<FarmCustomStrategy> farmCustomStrategy) {
        this.farmCustomStrategy = farmCustomStrategy;
    }

    @Override
    public int compareTo(Farm farm) {
        if(this.getFarmId().equals(farm.getFarmId())){
            return 0;
        } else if(this.getFarmId() > farm.getFarmId()){
            return 1;
        } else {
            return -1;
        }
    }
}
