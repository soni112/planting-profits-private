package com.decipher.agriculture.data.farm;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;

@Cacheable
@Entity(name = "FarmData")
@Table(name = "FARM_DATA", uniqueConstraints = @UniqueConstraint(columnNames = "FARM_DATA_ID"))
public class FarmData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "FARM_DATA_ID")
    private Integer id;
    @Column(name = "STATE")
    private String state;
    @Column(name = "STATE_AG_STATISTICS")
    private String stateAgStatistics;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateAgStatistics() {
        return stateAgStatistics;
    }

    public void setStateAgStatistics(String stateAgStatistics) {
        this.stateAgStatistics = stateAgStatistics;
    }
}
