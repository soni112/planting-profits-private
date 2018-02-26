package com.decipher.agriculture.dao.account.impl;

import com.decipher.agriculture.dao.account.AccountDao;
import com.decipher.agriculture.data.account.AppRole;
import com.decipher.agriculture.data.account.UserCity;
import com.decipher.agriculture.data.account.UserCountry;
import com.decipher.agriculture.data.account.UserState;
import com.decipher.util.PlantingProfitLogger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.data.account.Account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Manoj
 *
 */
@Repository
@Transactional
public class AccountDaoImpl implements AccountDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Account getUserByEmail(String email) {
		PlantingProfitLogger.info("inside getUserByEmail email : " + email);
		Account account = null;

		Session session = sessionFactory.openSession();
		try {
			Query userQuery = session.createQuery("from Account where email_Address=:email");
			userQuery.setParameter("email", email);
			Object object = userQuery.uniqueResult();

			if (object != null) {
				if (object instanceof Account) {
					account = (Account) object;

					Hibernate.initialize(account.getFarmList());
					Hibernate.initialize(account.getChildrens());
					Hibernate.initialize(account.getSuperParentChildrens());
					PlantingProfitLogger.info("User exist with : " + account.getEmail_Address());

				} else {
					PlantingProfitLogger.info("User does not exist with : " + email);
					account = null;
				}

			} else {
				PlantingProfitLogger.info("User does not exist with : " + email);
				account = null;
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			account = null;
		} finally {
			session.close();
		}
		return account;
	}

	@Override
	public Account getEnabledUserByEmail(String email) {
		PlantingProfitLogger.info("inside getEnabledUserByEmail email : " + email);
		Account account = null;

		Session session = sessionFactory.openSession();
		try {
			Query userQuery = session.createQuery("from Account where email_Address=:email and enabled = true");
			userQuery.setParameter("email", email);
			Object object = userQuery.uniqueResult();

			if (object != null) {
				if (object instanceof Account) {
					account = (Account) object;

					Hibernate.initialize(account.getFarmList());
					Hibernate.initialize(account.getChildrens());
					Hibernate.initialize(account.getSuperParentChildrens());


					PlantingProfitLogger.info("User exist with : " + account.getEmail_Address());
				} else {
					PlantingProfitLogger.info("User does not exist with : " + email);
					account = null;
				}

			} else {
				PlantingProfitLogger.info("User does not exist with : " + email);
				account = null;
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			account = null;
		} finally {
			session.close();
		}
		return account;

	}

	@Override
	public boolean isEmailAddressExists(String email) {
		PlantingProfitLogger.info("inside isEmailAddress email : " + email);
		boolean toRet = true;
		Account account = null;

		Session session = sessionFactory.openSession();
		try {
			Query userQuery = session.createQuery("from Account where lower(email_Address)=:email");
			userQuery.setParameter("email", email.toLowerCase());
			Object object = userQuery.uniqueResult();

			if (object != null) {
				if (object instanceof Account) {
					account = (Account) object;
					PlantingProfitLogger.info("User exist with : "
							+ account.getEmail_Address());
					toRet = true;
				} else {
					PlantingProfitLogger.info("User does not exist with : " + email);
					toRet = false;
				}

			} else {
				PlantingProfitLogger.info("Object is Null  ");
				toRet = false;
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			toRet = false;
		} finally {
			session.close();
		}

		return toRet;

	}

	@Override
	public int saveUser(Account account) {
		PlantingProfitLogger.info("inside saveUser..");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		int id=0;
		try {
			account.setEmail_Address(account.getEmail_Address().toLowerCase());
			tx = session.beginTransaction();
			id=(int) session.save(account);
			tx.commit();
			return id;
		} catch (Exception e) {
			id=0;
			tx.rollback();
			PlantingProfitLogger.error(e);
			return id;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean updateUser(Account account) {
		PlantingProfitLogger.info("inside updateUser.. Updating : " + account.getEmail_Address());
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();
		boolean result = false;
		try {
			transaction.begin();

			session.merge(account);

            transaction.commit();

            result = true;
		} catch (Exception e) {

            transaction.rollback();

            PlantingProfitLogger.error(e);
			result = false;
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * @added - Abhishek
	 * @date - 23-03-2016
	 * @desc - for getting user according to userID
	 */
	@Override
	public Account getUserById(int userID) {
		PlantingProfitLogger.info("inside getUserById userID : " + userID);
		Account account = null;

		Session session = sessionFactory.openSession();
		try {
			Query userQuery = session.createQuery("from Account where id=:userId");
			userQuery.setParameter("userId", userID);
			Object object = userQuery.uniqueResult();

			if (object != null) {
				if (object instanceof Account) {
					account = (Account) object;

					Hibernate.initialize(account.getFarmList());
					Hibernate.initialize(account.getChildrens());
					Hibernate.initialize(account.getSuperParentChildrens());


					PlantingProfitLogger.info("User exist with : " + account.getId());
				} else {
					PlantingProfitLogger.info("User does not exist with : " + userID);
					account = null;
				}

			} else {
				PlantingProfitLogger.info("User does not exist with : " + userID);
				account = null;
			}

		} catch (Exception e) {
			PlantingProfitLogger.error(e);
			account = null;
		} finally {
			session.close();
		}
		return account;
	}

	@Override
	public List<Account> getAllChildren(Account parent, AppRole approle) {
		PlantingProfitLogger.info("Getting list of children under : " + parent.getEmail_Address() + " with user role of " + approle.toString());
		Session session = sessionFactory.openSession();
		List<Account> childrenList =new ArrayList<Account>();
		Transaction transaction = session.getTransaction();
		try{
			transaction.begin();
			Query userQuery = session.createQuery("from Account where parent=:parent and role=:approle");
			userQuery.setParameter("approle", approle);
			userQuery.setParameter("parent", parent);

			childrenList = userQuery.list();
			transaction.commit();
		} catch (Exception e){
			transaction.rollback();
			PlantingProfitLogger.error(e);
			childrenList = null;
		} finally {
			session.close();
		}

		return childrenList;
	}

	@Override
	public List<Account> getAllUsersUnderRole(AppRole approle) {
		PlantingProfitLogger.info("Getting all users under : " + approle.toString());
		Session session = sessionFactory.openSession();
		List<Account> parentList =new ArrayList<Account>();
		Transaction transaction = session.getTransaction();

		try{
			transaction.begin();

			Query userQuery = session.createQuery("from Account where role=:approle");
			userQuery.setParameter("approle", approle);

			parentList = userQuery.list();




			transaction.commit();
		} catch (Exception e){
			transaction.rollback();
			PlantingProfitLogger.error(e);
			parentList = null;
		} finally {
			session.close();
		}

		return parentList;
	}

	@Override
	public List<Account> getAllVacantUsersUnderRole(AppRole approle) {
		PlantingProfitLogger.info("Getting list of vacant users under approle : " + approle);
		Session session = sessionFactory.openSession();
		List<Account> vacantList =new ArrayList<Account>();
		Transaction transaction = session.getTransaction();
		try{

			transaction.begin();

			Query vacantUsersQuery = session.createQuery("from Account where parent = null and role = :approle");
			vacantUsersQuery.setParameter("approle", approle);

			vacantList = vacantUsersQuery.list();

			transaction.commit();
		} catch (Exception e){
			transaction.rollback();
			PlantingProfitLogger.error(e);
			vacantList = null;
		} finally {
			session.close();
		}

		return vacantList;
	}

	@Override
	public List<Account> getAllVacantUsersUnderSuperParent(Account superParent) {
		PlantingProfitLogger.info("Getting list of users under superParent : " + superParent.getEmail_Address());
		Session session = sessionFactory.openSession();
		List<Account> accountList =new ArrayList<Account>();
		Transaction transaction = session.getTransaction();
		try{

			transaction.begin();

			Query allUsersUnderSuperParentQuery = session.createQuery("from Account where superParent = :superParent and parent is null");
			allUsersUnderSuperParentQuery.setParameter("superParent", superParent);

			accountList = allUsersUnderSuperParentQuery.list();

			transaction.commit();
		} catch (Exception e){
			transaction.rollback();
			PlantingProfitLogger.error(e);
			accountList = null;
		} finally {
			session.close();
		}

		return accountList;
	}

	/**
	 * @changed - Abhishek
	 * @date - 02-04-2016
	 * @desc - for enable and disable feature of account
	 */
	@Override
	public List<Account> getAllUsersToDisable(Date currentDate) {
		PlantingProfitLogger.info("Getting all accounts which will be disabled by " + currentDate);
		Session session = sessionFactory.openSession();

		List<Account> accountList = new ArrayList<Account>();

		try{

			Query query = session.createQuery("from Account where expirationDate=:expiryData");
			query.setParameter("expiryData", currentDate);
			accountList = query.list();

		} catch (Exception e){
			PlantingProfitLogger.error(e);
			accountList = null;
		} finally {
			session.close();
		}

		return accountList;
	}

	@Override
	public void deleteUser(int userID) {
		PlantingProfitLogger.info("Deleting user with ID:  " + userID);
		Session session = sessionFactory.openSession();

		Transaction deleteTransaction = session.getTransaction();

		try{

			deleteTransaction.begin();

			/*Query query = session.createQuery("delete from Account where id=:userID");
			query.setParameter("userID", userID);
			query.executeUpdate();*/

			session.delete(session.load(Account.class, userID));

			deleteTransaction.commit();

		} catch (Exception e){
			PlantingProfitLogger.error(e);
			deleteTransaction.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public Set<?> initializeLazy(Account account, String key) {
		Set<?> lazyCollections = new HashSet<>();
		Session session = sessionFactory.openSession();

		Transaction transaction = session.getTransaction();

		try {
			transaction.begin();

			PlantingProfitLogger.info("before session.contains(account) : " + session.contains(account));
			account = (Account)session.merge(account);
			PlantingProfitLogger.info("after session.contains(account) : " + session.contains(account));

			if(key.equalsIgnoreCase("Documents")){
				Hibernate.initialize(account.getUserDocuments());
				lazyCollections = account.getUserDocuments();

			} else if(key.equalsIgnoreCase("FarmList")){
				Hibernate.initialize(account.getFarmList());
				lazyCollections = account.getFarmList();

			} else if(key.equalsIgnoreCase("Children")){
				Hibernate.initialize(account.getChildrens());
				lazyCollections = account.getChildrens();

			} else if(key.equalsIgnoreCase("SuperChildren")){
				Hibernate.initialize(account.getSuperParentChildrens());
				lazyCollections = account.getSuperParentChildrens();

			}

			transaction.commit();

			PlantingProfitLogger.info("lazy size : " + account.getUserDocuments().size());

		} catch (Exception e){
			transaction.rollback();
			PlantingProfitLogger.error(e);
		} finally {
			session.close();
		}
	   return lazyCollections;
	}

	@Override
	public List<Account> getAllGrowerUnderAdmin(Account admin) {
		PlantingProfitLogger.info("Getting all growers under : " + admin.getEmail_Address());
		Session session = sessionFactory.openSession();

		List<Account> growerList = new ArrayList<>();

		Transaction transaction = session.getTransaction();

		try {
			transaction.begin();

			Query query = session.createQuery("from Account where superParent=:superParent");
			query.setParameter("superParent", admin);
			growerList = query.list();

			transaction.commit();

		} catch (Exception e){
			PlantingProfitLogger.error(e);
			transaction.rollback();
		} finally {
			session.close();
		}

		return growerList;
	}

	/**
	 * @changed - Abhishek
	 * @date - 05-05-2016
	 * @desc - for implementing middleware for optimization process
	 */
	@Override
	public Set<Account> getAllUsers() {
		Session session = sessionFactory.openSession();

		List<Account> accountList;

		Transaction transaction = session.getTransaction();

		try {
			transaction.begin();

			Query query = session.createQuery("from Account");

			accountList = (List<Account>)query.list();

			transaction.commit();
		}catch (Exception e){
			transaction.rollback();
			PlantingProfitLogger.error(e);
			accountList = null;
		} finally {
			session.close();
		}

		return Collections.synchronizedSet(new HashSet<Account>(accountList));
	}

	@Override
	public UserCountry getCountry(int countryId) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();

		UserCountry userCountry = null;

		try {
			transaction.begin();

			Query query = session.createQuery("from UserCountry where id=:countryId");
			query.setParameter("countryId", countryId);

			userCountry = (UserCountry)query.uniqueResult();

			session.flush();
			transaction.commit();
		} catch(Exception e){
			transaction.rollback();
			PlantingProfitLogger.error(e);
		} finally {
			session.close();
		}

		return userCountry;

	}

	@Override
	public UserState getState(int stateId) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();

		UserState userState = null;

		try {
			transaction.begin();

			Query query = session.createQuery("from UserState where id=:stateId");
			query.setParameter("stateId", stateId);

			userState = (UserState)query.uniqueResult();

			session.flush();
			transaction.commit();
		} catch(Exception e){
			transaction.rollback();
			PlantingProfitLogger.error(e);
		} finally {
			session.close();
		}

		return userState;
	}

	@Override
	public UserCity getCity(int cityId) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();

		UserCity userCity = null;

		try {
			transaction.begin();

			Query query = session.createQuery("from UserCity where id=:cityId");
			query.setParameter("cityId", cityId);

			userCity = (UserCity)query.uniqueResult();

			session.flush();
			transaction.commit();
		} catch(Exception e){
			transaction.rollback();
			PlantingProfitLogger.error(e);
		} finally {
			session.close();
		}

		return userCity;
	}

	@Override
	public List<UserCountry> getAllCountriesList() {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();

		List<UserCountry> userCountriesList = new ArrayList<>();

		try {
			transaction.begin();

			Query query = session.createQuery("from UserCountry");
			userCountriesList = query.list();

			session.flush();
			transaction.commit();
		} catch(Exception e){
			transaction.rollback();
			PlantingProfitLogger.error(e);
		} finally {
			session.close();
		}

		return userCountriesList;

	}

	@Override
	public List<UserState> getStatesForCountry(int countryId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();

		List<UserState> userStateList = new ArrayList<>();

		try {
			transaction.begin();

			Query query = session.createQuery("from UserState where userCountry.id=:countryId");

			query.setParameter("countryId", countryId);

			userStateList = query.list();

			session.flush();
			transaction.commit();
		} catch(Exception e){
			transaction.rollback();
			PlantingProfitLogger.error(e);
		} finally {
			session.close();
		}

		return userStateList;
	}

	@Override
	public List<UserCity> getCitiesForState(int stateId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.getTransaction();

		List<UserCity> userCityList = new ArrayList<>();

		try {
			transaction.begin();

			Query query = session.createQuery("from UserCity where userState.id=:stateId");

			query.setParameter("stateId", stateId);

			userCityList = query.list();

			session.flush();
			transaction.commit();
		} catch(Exception e){
			transaction.rollback();
			PlantingProfitLogger.error(e);
		} finally {
			session.close();
		}

		return userCityList;
	}

}
