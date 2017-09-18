package com.decipher.agriculture.service.account;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.account.AccountDocuments;
import com.decipher.agriculture.data.account.AccountDocumentsType;
import com.decipher.agriculture.data.account.AppRole;
import com.decipher.agriculture.data.account.UserCity;
import com.decipher.agriculture.data.account.UserCountry;
import com.decipher.agriculture.data.account.UserState;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface AccountService {
    Account getCurrentUser();

    Account getUserByEmail(String email);

    Account getEnabledUserByEmail(String email);

    boolean isEmailAddressExists(String email);

    int saveUser(Account account);

    boolean UpdateUser(Account account);

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

    List<Account> getAllGrowerUnderAdmin(Account admin);

    /**
     * @changed - Abhishek
     * @date - 02-04-2016
     * @desc - for enable and disable feature of account
     */
    List<Account> getAllUsersToDisable(Date currentDate);

    void deleteUser(int userId);

    Set<AccountDocuments> getUserDocuments(Account account);

    void uploadDocument(Account account, MultipartFile multipartFile, AccountDocumentsType accountDocumentsType);

    void sendRegistrationMail(Account account, String pwd);

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
