package com.decipher.agriculture.data.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
import java.util.List;

/**
 * Created by abhishek on 26/8/16.
 */
@Entity
@Table(name = "USER_STATE")
public class UserState implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STATE_ID")
    private Integer id;

    @Column(name = "STATE_NAME")
    private String stateName;

    @ManyToOne
    @JoinColumn(name="COUNTRY_ID")
    @JsonIgnore
    private UserCountry userCountry;

    @OneToMany(targetEntity = UserCity.class, mappedBy = "userState", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<UserCity> userCityList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public UserCountry getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(UserCountry userCountry) {
        this.userCountry = userCountry;
    }

    public List<UserCity> getUserCityList() {
        return userCityList;
    }

    public void setUserCityList(List<UserCity> userCityList) {
        this.userCityList = userCityList;
    }
}
