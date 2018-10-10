package com.decipher.view.form.account;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.account.AppRole;
import com.decipher.agriculture.data.account.UserCountry;
import com.decipher.agriculture.data.account.UserState;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class AccountView implements Serializable
{
	private Integer id;
	private AppRole role;
	private String firstName;
	private String lastName;
	private String phone_No;
	private String email_Address;
	private String password;
	private String mailing_Address_Line_1;
	private String mailing_Address_Line_2;
	private String mailing_Address_City;
	private UserState mailing_Address_State;
	private UserCountry mailing_Address_Country;
	private String mailing_Address_Zip;
	private String physical_Address_Line_1;
	private String physical_Address_Line_2;
	private String physical_Address_City;
	private UserCountry physical_Address_Country;
	private UserState physical_Address_State;
	private String physical_Address_Zip;

	private String logo;

	@JsonIgnore
	private Account account;

	public AccountView(){
		
	}

	public AccountView(Account account){
		this.id = account.getId();
		this.role = account.getRole();
		this.firstName = account.getFirstName();
		this.lastName = account.getLastName();
		this.phone_No = account.getPhone_No();
		this.email_Address = account.getEmail_Address();
		this.password = account.getPassword();
		this.mailing_Address_Line_1 = account.getMailing_Address_Line_1();
		this.mailing_Address_Line_2 = account.getMailing_Address_Line_2();
		this.mailing_Address_City = account.getMailing_Address_City();
		this.mailing_Address_State = account.getMailing_Address_State();
		this.mailing_Address_Country = account.getMailing_Address_Country();
		this.mailing_Address_Zip = account.getMailing_Address_Zip();

		this.account = account;


	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public AppRole getRole()
	{
		return role;
	}

	public void setRole(AppRole role)
	{
		this.role = role;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getPhone_No()
	{
		return phone_No;
	}

	public void setPhone_No(String phone_No)
	{
		this.phone_No = phone_No;
	}

	public String getEmail_Address()
	{
		return email_Address;
	}

	public void setEmail_Address(String email_Address)
	{
		this.email_Address = email_Address;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getMailing_Address_Line_1()
	{
		return mailing_Address_Line_1;
	}

	public void setMailing_Address_Line_1(String mailing_Address_Line_1)
	{
		this.mailing_Address_Line_1 = mailing_Address_Line_1;
	}

	public String getMailing_Address_Line_2()
	{
		return mailing_Address_Line_2;
	}

	public void setMailing_Address_Line_2(String mailing_Address_Line_2)
	{
		this.mailing_Address_Line_2 = mailing_Address_Line_2;
	}

	public String getMailing_Address_City()
	{
		return mailing_Address_City;
	}

	public void setMailing_Address_City(String mailing_Address_City)
	{
		this.mailing_Address_City = mailing_Address_City;
	}

	/*public String getMailing_Address_State()
	{
		return mailing_Address_State;
	}

	public void setMailing_Address_State(String mailing_Address_State)
	{
		this.mailing_Address_State = mailing_Address_State;
	}

	public String getMailing_Address_Country()
	{
		return mailing_Address_Country;
	}

	public void setMailing_Address_Country(String mailing_Address_Country)
	{
		this.mailing_Address_Country = mailing_Address_Country;
	}*/

	public String getMailing_Address_Zip()
	{
		return mailing_Address_Zip;
	}

	public void setMailing_Address_Zip(String mailing_Address_Zip)
	{
		this.mailing_Address_Zip = mailing_Address_Zip;
	}

	public String getPhysical_Address_Line_1()
	{
		return physical_Address_Line_1;
	}

	public void setPhysical_Address_Line_1(String physical_Address_Line_1)
	{
		this.physical_Address_Line_1 = physical_Address_Line_1;
	}

	public String getPhysical_Address_Line_2()
	{
		return physical_Address_Line_2;
	}

	public void setPhysical_Address_Line_2(String physical_Address_Line_2)
	{
		this.physical_Address_Line_2 = physical_Address_Line_2;
	}

	public String getPhysical_Address_City()
	{
		return physical_Address_City;
	}

	public void setPhysical_Address_City(String physical_Address_City)
	{
		this.physical_Address_City = physical_Address_City;
	}

	/*public String getPhysical_Address_State()
	{
		return physical_Address_State;
	}

	public void setPhysical_Address_State(String physical_Address_State)
	{
		this.physical_Address_State = physical_Address_State;
	}*/

	public String getPhysical_Address_Zip()
	{
		return physical_Address_Zip;
	}

	public void setPhysical_Address_Zip(String physical_Address_Zip)
	{
		this.physical_Address_Zip = physical_Address_Zip;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}


	public UserState getMailing_Address_State() {
		return mailing_Address_State;
	}

	public void setMailing_Address_State(UserState mailing_Address_State) {
		this.mailing_Address_State = mailing_Address_State;
	}

	public UserCountry getMailing_Address_Country() {
		return mailing_Address_Country;
	}

	public void setMailing_Address_Country(UserCountry mailing_Address_Country) {
		this.mailing_Address_Country = mailing_Address_Country;
	}

	public UserCountry getPhysical_Address_Country() {
		return physical_Address_Country;
	}

	public void setPhysical_Address_Country(UserCountry physical_Address_Country) {
		this.physical_Address_Country = physical_Address_Country;
	}

	public void setPhysical_Address_State(UserState physical_Address_State) {
		this.physical_Address_State = physical_Address_State;
	}
}
