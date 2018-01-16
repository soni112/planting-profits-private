package com.decipher.agriculture.service.account.impl;

import com.decipher.agriculture.data.account.AccountDocuments;
import com.decipher.agriculture.data.account.AccountDocumentsType;
import com.decipher.agriculture.data.account.AppRole;
import com.decipher.agriculture.data.account.UserCity;
import com.decipher.agriculture.data.account.UserCountry;
import com.decipher.agriculture.data.account.UserState;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.config.ApplicationConfig;
import com.decipher.util.CryptographyUtils;
import com.decipher.util.PlantingProfitLogger;
import com.decipher.agriculture.service.email.EmailService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.account.AccountDao;
import com.decipher.agriculture.data.account.Account;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
@Transactional
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDao accountDAO;
	@Autowired
	private HttpSession httpSession;

	@Autowired
	private EmailService emailService;

	@Override
	public Account getCurrentUser() {

		SecurityContext context = SecurityContextHolder.getContext();

		final String currentUserName = context.getAuthentication().getName();

		PlantingProfitLogger.info("currentUserName---->>>" + currentUserName);
		Account currentUser = null;
		if (currentUserName != null) {
			currentUser = accountDAO.getEnabledUserByEmail(currentUserName);

			/**
			 * @changed - Abhishek
			 * @date - 29-03-2016
			 * @desc - for getting user if super user requesting for grower data
			 */
			Object growerId = httpSession.getAttribute("growerId");
			if(growerId != null && Integer.parseInt(growerId.toString()) != currentUser.getId()){
				currentUser = accountDAO.getUserById(Integer.parseInt(growerId.toString()));
			}

		}
		return currentUser;

	}

	@Override
	public Account getUserByEmail(String email) {
		return  accountDAO.getUserByEmail(email);
	}

	@Override
	public Account getEnabledUserByEmail(String email) {
		return accountDAO.getEnabledUserByEmail(email);
	}

	@Override
	public boolean isEmailAddressExists(String email) {
		return accountDAO.isEmailAddressExists(email);
	}

	@Override
	public int saveUser(Account account) {
		return accountDAO.saveUser(account);
	}

	@Override
	public boolean UpdateUser(Account account) {
		return accountDAO.UpdateUser(account);
	}

	/**
	 * @added - Abhishek
	 * @date - 23-03-2016
	 * @desc - for getting user according to userID
	 */
	@Override
	public Account getUserById(int userID) {
		return accountDAO.getUserById(userID);
	}

	@Override
	public List<Account> getAllChildren(Account parent, AppRole approle) {
		return accountDAO.getAllChildren(parent, approle);
	}

	@Override
	public List<Account> getAllUsersUnderRole(AppRole approle) {
		return accountDAO.getAllUsersUnderRole(approle);
	}

	@Override
	public List<Account> getAllVacantUsersUnderRole(AppRole approle) {
		return accountDAO.getAllVacantUsersUnderRole(approle);
	}

	@Override
	public List<Account> getAllVacantUsersUnderSuperParent(Account superParent) {
		return accountDAO.getAllVacantUsersUnderSuperParent(superParent);
	}

	/**
	 * @changed - Abhishek
	 * @date - 02-04-2016
	 * @desc - for enable and disable feature of account
	 */
	@Override
	public List<Account> getAllUsersToDisable(Date currentDate) {
		return accountDAO.getAllUsersToDisable(currentDate);
	}

	@Override
	public void deleteUser(int userId) {

		accountDAO.deleteUser(userId);


	}

	@Override
	public void uploadDocument(Account account, MultipartFile multipartFile, AccountDocumentsType accountDocumentsType) {
		if (!multipartFile.isEmpty()) {
			try {
				byte[] bytes = multipartFile.getBytes();

				// Creating the directory to store file
//                System.getProperty("user.home") + "/Planting_Profit_Data/UserData/"+ accountView.getId() +"/logo/" + accountView.getLogo();
				String rootPath = System.getProperty("user.home") + "/Planting_Profit_Data/UserData/"+ account.getId() +"/logo/";
				File dir = new File(rootPath);
				if (!dir.exists()) {
					dir.mkdirs();
				} else {
					FileUtils.deleteDirectory(dir);
					dir.mkdirs();
					PlantingProfitLogger.info("Deleting directory");
				}
				String filename = multipartFile.getOriginalFilename();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + filename);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				Set<AccountDocuments> userDocumentsList = getUserDocuments(account);

				boolean listFlag = true;
				for (AccountDocuments document : userDocumentsList) {
					if (Objects.equals(document.getAccountDocumentsType(), AccountDocumentsType.LOGO)){
						document.setDocumentPath(filename);
						listFlag = false;
					}
				}

				if (listFlag){
					AccountDocuments accountDocuments = new AccountDocuments();
					accountDocuments.setAccountDocumentsType(accountDocumentsType);
					accountDocuments.setDocumentPath(filename);
					accountDocuments.setDocumentHolder(account);
					userDocumentsList.add(accountDocuments);
				}

				account.setUserDocuments(userDocumentsList);

				/*PlantingProfitLogger.info("before initialize lazy for account");
				account = initializeLazy(account);
				PlantingProfitLogger.info("After initialize lazy for account");
				account.getUserDocuments().add(accountDocuments);
				PlantingProfitLogger.info("After initialize lazy for account after adding documents");*/


				PlantingProfitLogger.info("Server File Location =" + serverFile.getAbsolutePath());

				PlantingProfitLogger.info("You successfully uploaded file=" + multipartFile.getOriginalFilename());
			} catch (Exception e) {
				PlantingProfitLogger.info("You failed to upload " + multipartFile.getOriginalFilename() + " => " + e.getMessage());
			}
		} else {
			PlantingProfitLogger.info("You failed to upload " + multipartFile.getOriginalFilename() + " because the file was empty.");
		}
	}

	@Override
	public void sendRegistrationMail(Account account, String pwd) {
		String encodedEmail = CryptographyUtils.encryptData(account.getEmail_Address());
		String applicationID = ApplicationConfig.getAppUrl();
		String linkTxt = "<a  target=\"_blank\" href=\"" + applicationID
				+ "/verifyAccount.htm?uid=" + encodedEmail + "\" >"
				+ applicationID + "/verifyAccount.htm?uid=" + encodedEmail
				+ "</a>";


		String msgText = "Dear "
				+ account.getFirstName() + " " + account.getLastName()
				+ "<br/><br/>You have successfully registered with Planting Profit Application and Your Account Details are following : <br>"
				+ "<br/><b>Email Id : " + account.getEmail_Address() + "<br>" + "<b>Password : "
				+ pwd + "<br>"
				+ "<br/><br/><br/>To verify your account  <b>" + account.getEmail_Address()
				+ "</b><br/><br/>Please click on below link to activate your account :<br/>"
				+ linkTxt + "<br><br><br>Regards" + " :  "
				+ "Planting Profit Application Service Team ";
		emailService.sendEmail(account.getEmail_Address(), "Planting Profit Verification", msgText);
	}

	@Override
	public List<Account> getAllGrowerUnderAdmin(Account admin) {
		return accountDAO.getAllGrowerUnderAdmin(admin);
	}

	@Override
	public Set<AccountDocuments> getUserDocuments(Account account) {
//		Map<String, Object> lazyCollections = accountDAO.initializeLazy(account, "Documents");

		return (Set<AccountDocuments>)accountDAO.initializeLazy(account, "Documents");


	}

	/**
	 * @changed - Abhishek
	 * @date - 05-05-2016
	 * @desc - for implementing middleware for optimization process
	 */
	@Override
	public Set<Account> getAllUsers() {
		return accountDAO.getAllUsers();
	}

	@Override
	public UserCountry getCountry(int countryId) {
		return accountDAO.getCountry(countryId);
	}

	@Override
	public UserState getState(int stateId) {
		return accountDAO.getState(stateId);
	}

	@Override
	public UserCity getCity(int cityId) {
		return accountDAO.getCity(cityId);
	}

	@Override
	public List<UserCountry> getAllCountriesList() {
		List<UserCountry> allCountriesList = accountDAO.getAllCountriesList();
		List<UserCountry> userCountries = new LinkedList<>();
		for (UserCountry userCountry : allCountriesList) {
			if(userCountry.getCountryName().contains("United States")){
				userCountries.add(userCountry);
			}
		}

		for (UserCountry userCountry : allCountriesList) {
			if(!userCountry.getCountryName().contains("United States")){
				userCountries.add(userCountry);
			}
		}

		return userCountries;
	}

	@Override
	public List<UserState> getStatesForCountry(int countryId) {
		return accountDAO.getStatesForCountry(countryId);
	}

	@Override
	public List<UserCity> getCitiesForState(int stateId) {
		return accountDAO.getCitiesForState(stateId);
	}


}
