package com.decipher.agriculture.data.account;

import com.decipher.agriculture.data.farm.Farm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

/**
 * @changed - Abhishek
 * @date - 13-01-2016
 * @desc - for implementing second level hibernate cache
 */
@Cache(region = "planting-Second-level-Cache",  usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
@Entity
@Table(name = "ACCOUNT", uniqueConstraints = {
		@UniqueConstraint(columnNames = "ACCOUNT_ID"),
		@UniqueConstraint(columnNames = "EMAIL_ADDRESS")})
public class Account implements Comparable<Account>, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACCOUNT_ID")
	private Integer id;
	@Column(name = "USER_ROLE")
	private AppRole role;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "PHONE_NUMBER")
	private String phone_No;
	@Column(name = "EMAIL_ADDRESS")
	private String email_Address;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "MAILING_ADDRESS_LINE_1")
	private String mailing_Address_Line_1;
	@Column(name = "MAILING_ADDRESS_LINE_2")
	private String mailing_Address_Line_2;
	@Column(name = "MAILING_ADDRESS_CITY")
	private String mailing_Address_City;
	@Column(name = "MAILING_ADDRESS_ZIP")
	private String mailing_Address_Zip;
	@Column(name = "PHYSICAL_ADDRESS_LINE_1")
	private String physical_Address_Line_1;
	@Column(name = "PHYSICAL_ADDRESS_LINE_2")
	private String physical_Address_Line_2;
	@Column(name = "PHYSICAL_ADDRESS_CITY")
	private String physical_Address_City;
	@Column(name = "PHYSICAL_ADDRESS_ZIP")
	private String physical_Address_Zip;
	@Column(name = "REGISTRATION_TIME")
	private Date registrationTime;


	@OneToOne
	@JoinColumn(name = "MAILING_COUNTRY_ID")
	private UserCountry mailing_Address_Country;
	@OneToOne
	@JoinColumn(name = "MAILING_STATE_ID")
	private UserState mailing_Address_State;

	@OneToOne
	@JoinColumn(name = "PHYSICAL_COUNTRY_ID")
	private UserCountry physical_Address_Country;
	@OneToOne
	@JoinColumn(name = "PHYSICAL_STATE_ID")
	private UserState physical_Address_State;

	/**
	 * @added - Abhishek
	 * @date - 02-04-2016
	 * @desc - for validations for students login
	 */
	@Column(name = "EXPIRATION_TIME")
	private Date expirationDate;
	@Column(name = "ENABLED")
	private Boolean enabled = false;

	/**
	 * @changed - Abhishek
	 * @date - 07-01-2016
	 * @desc - @JsonIgnore for removing
	 * 			org.springframework.http.converter.HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)
	 * 			and reducing the size oj JSON prepared
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "account")
	@JsonIgnore
	private Set<Farm> farmList = new HashSet<>();

	/**
	 * CascadeType.Persist for persisting the parent object if child is updated or merged
	 */
	@ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "PARENT_ID")
    private Account parent;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
    private Set<Account> childrens = new HashSet<Account>();

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "SUPER_PARENT_ID")
	private Account superParent;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "superParent")
	private Set<Account> superParentChildrens = new HashSet<Account>();

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = AccountDocuments.class, mappedBy = "documentHolder")
	private Set<AccountDocuments> userDocuments;

	@Column(name = "WELCOME_STATUS")
	private Boolean welcomeStatus = Boolean.FALSE;

	@Column(name = "LAST_ACTIVE_TIME")
	private Long lastActiveTime;

	public Account() {

	}

	public Account(String firstName, String lastName, String contact,
			String email_Address, String password,
			String mailing_Address_Line_1, String mailing_Address_Line_2,
			String mailing_Address_City, String mailing_Address_State,
			String mailing_Address_Country, String mailing_Address_Zip,
			String physical_Address_Line_1, String physical_Address_Line_2,
			String physical_Address_City, String physical_Address_State,
			String physical_Address_Country, String physical_Address_Zip) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone_No = contact;
		this.email_Address = email_Address;
		this.password = password;
		this.mailing_Address_Line_1 = mailing_Address_Line_1;
		this.mailing_Address_Line_2 = mailing_Address_Line_2;
		this.mailing_Address_City = mailing_Address_City;
//		this.mailing_Address_State = mailing_Address_State;
//		this.mailing_Address_Country = mailing_Address_Country;
		this.mailing_Address_Zip = mailing_Address_Zip;
		this.physical_Address_Line_1 = physical_Address_Line_1;
		this.physical_Address_Line_2 = physical_Address_Line_2;
		this.physical_Address_City = physical_Address_City;
//		this.physical_Address_State = physical_Address_State;
//		this.physical_Address_Country = physical_Address_Country;
		this.physical_Address_Zip = physical_Address_Zip;

		/**
		 * @changed - Abhishek
		 * @date - 02-04-2016
		 * @desc - for enable and disable feature of account
		 */
		this.enabled = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone_No() {
		return phone_No;
	}

	public void setPhone_No(String phone_No) {
		this.phone_No = phone_No;
	}

	public String getEmail_Address() {
		return email_Address;
	}

	public void setEmail_Address(String email_Address) {
		this.email_Address = email_Address;
	}

	public String getMailing_Address_Line_1() {
		return mailing_Address_Line_1;
	}

	public void setMailing_Address_Line_1(String mailing_Address_Line_1) {
		this.mailing_Address_Line_1 = mailing_Address_Line_1;
	}

	public String getMailing_Address_Line_2() {
		return mailing_Address_Line_2;
	}

	public void setMailing_Address_Line_2(String mailing_Address_Line_2) {
		this.mailing_Address_Line_2 = mailing_Address_Line_2;
	}

	public String getMailing_Address_City() {
		return mailing_Address_City;
	}

	public void setMailing_Address_City(String mailing_Address_City) {
		this.mailing_Address_City = mailing_Address_City;
	}

	/*public String getMailing_Address_State() {
		return mailing_Address_State;
	}

	public void setMailing_Address_State(String mailing_Address_State) {
		this.mailing_Address_State = mailing_Address_State;
	}

	public String getMailing_Address_Country() {
		return mailing_Address_Country;
	}

	public void setMailing_Address_Country(String mailing_Address_Country) {
		this.mailing_Address_Country = mailing_Address_Country;
	}*/

	public String getMailing_Address_Zip() {
		return mailing_Address_Zip;
	}

	public void setMailing_Address_Zip(String mailing_Address_Zip) {
		this.mailing_Address_Zip = mailing_Address_Zip;
	}

	public String getPhysical_Address_Line_1() {
		return StringUtils.isEmpty(physical_Address_Line_1) ? StringUtils.EMPTY : physical_Address_Line_1;
	}

	public void setPhysical_Address_Line_1(String physical_Address_Line_1) {
		this.physical_Address_Line_1 = physical_Address_Line_1;
	}

	public String getPhysical_Address_Line_2() {
		return StringUtils.isEmpty(physical_Address_Line_2) ? StringUtils.EMPTY : physical_Address_Line_2;
	}

	public void setPhysical_Address_Line_2(String physical_Address_Line_2) {
		this.physical_Address_Line_2 = physical_Address_Line_2;
	}

	public String getPhysical_Address_City() {
		return StringUtils.isEmpty(physical_Address_City) ? StringUtils.EMPTY : physical_Address_City;
	}

	public void setPhysical_Address_City(String physical_Address_City) {
		this.physical_Address_City = physical_Address_City;
	}

	/*public String getPhysical_Address_State() {
		return physical_Address_State;
	}

	public void setPhysical_Address_State(String physical_Address_State) {
		this.physical_Address_State = physical_Address_State;
	}*/

	public String getPhysical_Address_Zip() {
		return StringUtils.isEmpty(physical_Address_Zip) ? StringUtils.EMPTY : physical_Address_Zip;
	}

	public void setPhysical_Address_Zip(String physical_Address_Zip) {
		this.physical_Address_Zip = physical_Address_Zip;
	}

	public AppRole getRole() {
		return role;
	}

	public void setRole(AppRole role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Farm> getFarmList() {
		return farmList;
	}

	public void setFarmList(Set<Farm> farmList) {
		this.farmList = farmList;
	}

	public Date getRegistrationTime() {
		return registrationTime;
	}

	public void setRegistrationTime(Date registrationTime) {
		this.registrationTime = registrationTime;
	}

	/*public String getPhysical_Address_Country() {
		Locale[] locales = Locale.getAvailableLocales();
		for (Locale locale : locales){
			if(locale.getCountry().equalsIgnoreCase(physical_Address_Country)){
				return locale.getDisplayCountry();

			}

		}

		return "";
	}

	public void setPhysical_Address_Country(String physical_Address_Country) {
		this.physical_Address_Country = physical_Address_Country;
	}

	public String getPhysical_Address_CountryCode() {

		return physical_Address_Country;
	}*/

	/**
	 * @added - Abhishek
	 * @date - 04-12-2015
	 * @return - firstname of the user
	 */

	@Override
	public String toString() {
		return this.getEmail_Address();
	}

    public Account getParent() {
        return parent;
    }

    public void setParent(Account parent) {
        this.parent = parent;
    }

    public Set<Account> getChildrens() {
        return childrens;
    }

    public void setChildrens(Set<Account> childrens) {
        this.childrens = childrens;
    }

	/**
	 * @added - Abhishek
	 * @date - 02-04-2016
	 * @desc - for validations for students login
	 */
	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<AccountDocuments> getUserDocuments() {
		return userDocuments;
	}

	public void setUserDocuments(Set<AccountDocuments> userDocuments) {
		this.userDocuments = userDocuments;
	}

	public Account getSuperParent() {
		return superParent;
	}

	public void setSuperParent(Account superParent) {
		this.superParent = superParent;
	}

	public Set<Account> getSuperParentChildrens() {
		return superParentChildrens;
	}

	public void setSuperParentChildrens(Set<Account> superParentChildrens) {
		this.superParentChildrens = superParentChildrens;
	}


	public UserCountry getMailing_Address_Country() {
		return mailing_Address_Country;
	}

	public void setMailing_Address_Country(UserCountry mailing_Address_Country) {
		this.mailing_Address_Country = mailing_Address_Country;
	}

	public UserState getMailing_Address_State() {
		return mailing_Address_State;
	}

	public void setMailing_Address_State(UserState mailing_Address_State) {
		this.mailing_Address_State = mailing_Address_State;
	}

	public UserCountry getPhysical_Address_Country() {
		if(physical_Address_Country == null){
			UserCountry userCountry = new UserCountry();
			userCountry.setCountryName("");
			userCountry.setCountryCode("");
			return userCountry;
		}
		return physical_Address_Country;
	}

	public void setPhysical_Address_Country(UserCountry physical_Address_Country) {
		this.physical_Address_Country = physical_Address_Country;
	}

	public UserState getPhysical_Address_State() {
		if(physical_Address_State == null){
			UserState userState = new UserState();
			userState.setStateName("");
			return userState;
		}
		return physical_Address_State;
	}

	public void setPhysical_Address_State(UserState physical_Address_State) {
		this.physical_Address_State = physical_Address_State;
	}

	public Boolean getWelcomeStatus() {
		return welcomeStatus;
	}

	public void setWelcomeStatus(Boolean status) {
		this.welcomeStatus = status;
	}

	public Long getLastActiveTime() {
		return lastActiveTime;
	}

	public String getLastActiveTimeFormatted() {
		if(lastActiveTime == null){
			return StringUtils.EMPTY;
		} else {
			DateTimeFormatter fmt = DateTimeFormat.forPattern("MM-dd-yyyy");
			return fmt.print(lastActiveTime);
		}

	}

	public void setLastActiveTime(Long lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

	@Override
	public int compareTo(Account account) {
		if(this.getId().equals(account.getId())){
			return 0;
		} else if(this.getId() > account.getId()){
			return 1;
		} else {
			return -1;
		}
	}
}
