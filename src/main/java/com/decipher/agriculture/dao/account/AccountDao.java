package com.decipher.agriculture.dao.account;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.account.AppRole;
import com.decipher.agriculture.data.account.UserCity;
import com.decipher.agriculture.data.account.UserCountry;
import com.decipher.agriculture.data.account.UserState;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Manoj
 */
public interface AccountDao {

    Account getUserByEmail(String email);

    Account getEnabledUserByEmail(String email);

    boolean isEmailAddressExists(String email);

    int saveUser(Account account);

    boolean updateUser(Account account);

    /**
     * @added - Abhishek
     * @date - 23-03-2016
     * @desc - for getting user according to userID
     */
    Account getUserById(int userID);

    List<Account> getAllChildren(Account parent, AppRole approle);

    List<Account> getAllUsersUnderRole(AppRole approle);

    List<Account> getAllVacantUsersUnderRole(AppRole approle);

    List<Account> getAllVacantUsersUnderSuperParent(Account superParent);

    /**
     * @changed - Abhishek
     * @date - 02-04-2016
     * @desc - for enable and disable feature of account
     */
    List<Account> getAllUsersToDisable(Date currentDate);

    void deleteUser(int userID);

    Set<?> initializeLazy(Account account, String key);

    List<Account> getAllGrowerUnderAdmin(Account admin);

    /**
     * @changed - Abhishek
     * @date - 05-05-2016
     * @desc - for implementing middleware for optimization process
     */
    Set<Account> getAllUsers();

    UserCountry getCountry(int countryId);

    UserState getState(int stateId);

    UserCity getCity(int cityId);

    List<UserCountry> getAllCountriesList();

    List<UserState> getStatesForCountry(int countryId);

    List<UserCity> getCitiesForState(int stateId);

}
