package com.decipher.agriculture.controller;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.data.account.AccountDocumentsType;
import com.decipher.agriculture.data.account.AppRole;
import com.decipher.agriculture.data.account.UserCountry;
import com.decipher.agriculture.service.account.AccountService;
import com.decipher.util.JsonResponse;
import com.decipher.util.PlantingProfitLogger;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by abhishek on 23/3/16.
 */
@Controller
@RequestMapping(value = "/managementController")
public class UserManagementController {

    @Autowired
    private HttpSession httpSession;
    @Autowired
    private AccountService accountService;



    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public @ResponseBody JsonResponse addUser(@RequestParam(value = "accountType", required = true) String accountType,
                                              @RequestParam(value = "firstName", required = true) String firstName,
                                              @RequestParam(value = "lastName", required = false) String lastName,
                                              @RequestParam(value = "contact", required = true) String contact,
                                              @RequestParam(value = "email", required = true) String email,
                                              @RequestParam(value = "mailing_Address_Line1", required = false) String mailing_Address_Line1,
                                              @RequestParam(value = "mailing_Address_Line2", required = false) String mailing_Address_Line2,
                                              @RequestParam(value = "mailing_Address_City", required = false) String mailing_Address_City,
                                              @RequestParam(value = "mailing_Address_State", required = false) String mailing_Address_State,
                                              @RequestParam(value = "mailing_Address_Country", required = false) String mailing_Address_Country,
                                              @RequestParam(value = "mailing_Zip", required = true) String mailing_Zip,
                                              @RequestParam(value = "physical_Address_Line1", required = false) String physical_Address_Line1,
                                              @RequestParam(value = "physical_Address_Line2", required = false) String physical_Address_Line2,
                                              @RequestParam(value = "physical_Address_City", required = false) String physical_Address_City,
                                              @RequestParam(value = "physical_Address_State", required = false) String physical_Address_State,
                                              @RequestParam(value = "physical_Address_Country", required = false) String physical_Address_Country,
                                              @RequestParam(value = "physical_Zip", required = false) String physical_Zip,
                                              @RequestParam(value = "expiryDate", required = false) String expiryDate,
                                              @RequestParam(value = "selectedAdminParent", required = false) String selectedAdminParent,
                                              @RequestParam(value = "parentId", required = false) String parentId) throws ParseException {


        PlantingProfitLogger.debug("inside saveUSer of User Management " + firstName + " " + lastName);
        JsonResponse jsonResponse = new JsonResponse();
        boolean exists = accountService.isEmailAddressExists(email.trim());
        if (!exists) {
            String password = RandomStringUtils.randomAlphanumeric(8);

            PasswordEncoder encoder = new Md5PasswordEncoder();
            String encodedNewPassword = encoder.encodePassword(password, null);
            /*Account account = new Account(firstName, lastName, contact,
                    email.trim(), encodedNewPassword, mailing_Address_Line1,
                    mailing_Address_Line2, mailing_Address_City,
                    mailing_Address_State, mailing_Address_Country,
                    mailing_Zip, physical_Address_Line1,
                    physical_Address_Line2, physical_Address_City,
                    physical_Address_State, physical_Address_Country, physical_Zip);*/


            Account account = new Account();

            account.setFirstName(firstName);
            account.setLastName(lastName);
            account.setPhone_No(contact);
            account.setEmail_Address(email.trim());
            account.setPassword(encodedNewPassword);
            account.setPhysical_Address_Line_1(physical_Address_Line1);
            account.setPhysical_Address_Line_2(physical_Address_Line2);
            account.setPhysical_Address_City(physical_Address_City);
            account.setPhysical_Address_Zip(physical_Zip);
            account.setMailing_Address_Line_1(mailing_Address_Line1);
            account.setMailing_Address_Line_2(mailing_Address_Line2);
            account.setMailing_Address_City(mailing_Address_City);
            account.setMailing_Address_Zip(mailing_Zip);


//            account.setPhysical_Address_Country(physical_Address_Country.equals("") ? null : accountService.getCountry(Integer.parseInt(physical_Address_Country)));
//            account.setPhysical_Address_State(physical_Address_State.equals("") ? null : accountService.getState(Integer.parseInt(physical_Address_State)));
//            account.setMailing_Address_Country(mailing_Address_Country.equals("") ? null : accountService.getCountry(Integer.parseInt(mailing_Address_Country)));
//            account.setMailing_Address_State(mailing_Address_State.equals("") ? null : accountService.getState(Integer.parseInt(mailing_Address_State)));

            if(!physical_Address_Country.equalsIgnoreCase(""))
                account.setPhysical_Address_Country(accountService.getCountry(Integer.parseInt(physical_Address_Country)));
            if(!physical_Address_State.equalsIgnoreCase(""))
                account.setPhysical_Address_State(accountService.getState(Integer.parseInt(physical_Address_State)));
            if(!mailing_Address_Country.equalsIgnoreCase(""))
                account.setMailing_Address_Country(accountService.getCountry(Integer.parseInt(mailing_Address_Country)));
            if(!mailing_Address_State.equalsIgnoreCase(""))
                account.setMailing_Address_State(accountService.getState(Integer.parseInt(mailing_Address_State)));

            /**
             * @added - Abhishek
             * @date - 02-04-2016
             * @desc - for validations for students login
             */
            if (expiryDate != null && !Objects.equals(expiryDate, "")) {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatter.parse(expiryDate);
                account.setExpirationDate(date);
            }

            account.setRegistrationTime(new java.sql.Date(new Date().getTime()));

            if (accountType.equalsIgnoreCase("ROLE_ADMIN")){
                account.setRole(AppRole.ROLE_ADMIN);
                account.setParent(accountService.getUserById(Integer.parseInt(parentId)));
            } else if (accountType.equalsIgnoreCase("ROLE_PROFESSIONAL")){
                account.setRole(AppRole.ROLE_PROFESSIONAL);

                if (selectedAdminParent != null && !Objects.equals(selectedAdminParent, "")) {
                    account.setParent(accountService.getUserById(Integer.parseInt(selectedAdminParent)));
                }

            } else if (accountType.equalsIgnoreCase("ROLE_GROWER")){
                account.setRole(AppRole.ROLE_GROWER);
                Account currentUser = accountService.getCurrentUser();
                if(Objects.equals(currentUser.getRole(), AppRole.ROLE_ADMIN)){
                    account.setSuperParent(currentUser);
                } else {
                    account.setSuperParent(accountService.getUserById(Integer.parseInt(selectedAdminParent)));
                }

            } else if (accountType.equalsIgnoreCase("ROLE_STUDENT")){
                account.setRole(AppRole.ROLE_STUDENT);
            }

            int userId = accountService.saveUser(account);

            accountService.sendRegistrationMail(account, password);

            jsonResponse.setStatus(JsonResponse.RESULT_SUCCESS);
        } else {
            jsonResponse.setStatus(JsonResponse.RESULT_ALREADY_EXISTS);
        }

        return jsonResponse;



    }


    @RequestMapping(value = "/getAllRolesData", method = RequestMethod.POST)
    public @ResponseBody JsonResponse getAllRolesData(@RequestParam(value = "userId", required = true) String userId){

        Account currentUser = accountService.getUserById(Integer.parseInt(userId));
        PlantingProfitLogger.info("getting all details for user role : " + currentUser.getRole());

        HashMap<String, Object> vacantList = new HashMap<String, Object>();
        JsonResponse response = new JsonResponse();

        if(Objects.equals(currentUser.getRole(), AppRole.ROLE_SUPER_ADMIN)){
            vacantList.put("allAdminList", accountService.getAllUsersUnderRole(AppRole.ROLE_ADMIN));
            vacantList.put("allProfessionalList", accountService.getAllUsersUnderRole(AppRole.ROLE_PROFESSIONAL));
            vacantList.put("allGrowerList", accountService.getAllUsersUnderRole(AppRole.ROLE_GROWER));
            vacantList.put("allStudentList", accountService.getAllUsersUnderRole(AppRole.ROLE_STUDENT));

        } else if(Objects.equals(currentUser.getRole(), AppRole.ROLE_ADMIN)){
            Set<Account> professionalList = currentUser.getChildrens();

            List<Account> allGrowerList = accountService.getAllGrowerUnderAdmin(currentUser);
            List<Account> vacantGrowerList = new ArrayList<>();
            for (Account grower : allGrowerList) {
                if (grower.getParent() == null) {
                    vacantGrowerList.add(grower);
                }
            }

            vacantList.put("allChildrenList", professionalList);
            vacantList.put("allGrowerList", allGrowerList);
            vacantList.put("vacantGrowerList", vacantGrowerList);
            vacantList.put("allStudentList", accountService.getAllUsersUnderRole(AppRole.ROLE_STUDENT));

        } else if(Objects.equals(currentUser.getRole(), AppRole.ROLE_PROFESSIONAL)){
            vacantList.put("allChildrenList", accountService.getAllChildren(currentUser, AppRole.ROLE_GROWER));

        } else if(Objects.equals(currentUser.getRole(), AppRole.ROLE_GROWER)){

            Set<Account> children = new HashSet<>();

            if(currentUser.getSuperParent() != null){
                children = currentUser.getSuperParent().getChildrens();
            }

            vacantList.put("allProfessionalList", children);

        }

        response.setStatus(JsonResponse.RESULT_SUCCESS);
        response.setResult(vacantList);


        return response;

    }


    @RequestMapping(value = "/getChildrenAndVacantUsers", method = RequestMethod.POST)
    public @ResponseBody JsonResponse getChildrenAndVacantUsers(@RequestParam(value = "userId", required = true) String requestedUserId,
                                                            @RequestParam(value = "userRole", required = true) String userRole ){

        PlantingProfitLogger.info("Getting children and vacant list");
        Account requestedUser = accountService.getUserById(Integer.parseInt(requestedUserId));

        JsonResponse response = new JsonResponse();

        if(requestedUser != null){

            Account currentUser = accountService.getCurrentUser();

            Map<String, Object> accountResponseHashMap = new HashMap<String, Object>();
            accountResponseHashMap.put("childrenList", requestedUser.getChildrens());
            if (Objects.equals(currentUser.getRole(), AppRole.ROLE_SUPER_ADMIN)) {
                accountResponseHashMap.put("vacantList", accountService.getAllVacantUsersUnderRole(AppRole.valueOf(userRole)));

            } else if (Objects.equals(currentUser.getRole(), AppRole.ROLE_ADMIN)){
                accountResponseHashMap.put("vacantList", accountService.getAllVacantUsersUnderSuperParent(currentUser));
            }

            response.setStatus(JsonResponse.RESULT_SUCCESS);
            response.setResult(accountResponseHashMap);

        } else {
            response.setStatus(JsonResponse.RESULT_FAILED);
        }

        return response;

    }

    @RequestMapping(value = "/associateChildren", method = RequestMethod.POST)
    public @ResponseBody JsonResponse associateChildren(@RequestParam(value = "parentId", required = true) String parentID,
                                                   @RequestParam(value = "childIdArray", required = true) String[] childrenArray ){

        PlantingProfitLogger.info("Updating children with id : " + Arrays.toString(childrenArray));
        Account parent = accountService.getUserById(Integer.parseInt(parentID));
        Account superParent = null;

        if (Objects.equals(parent.getRole(), AppRole.ROLE_PROFESSIONAL)){
            superParent = parent.getParent();
        }

        for (String childId : childrenArray) {

            Account child = accountService.getUserById(Integer.parseInt(childId));
            child.setParent(parent);
            if(Objects.equals(child.getRole(), AppRole.ROLE_GROWER)){
                child.setSuperParent(superParent);
            }

            accountService.UpdateUser(child);

        }

        JsonResponse response = new JsonResponse();
        response.setStatus(JsonResponse.RESULT_SUCCESS);

        return response;

    }

    @RequestMapping(value = "/unassignChildren", method = RequestMethod.POST)
    public @ResponseBody JsonResponse unassignChildren(@RequestParam(value = "childId", required = true) String childId,
                                                       @RequestParam(value = "parentId", required = true) String parentId){

        JsonResponse response = new JsonResponse();

        Account currentUser = accountService.getCurrentUser();
        try {
            Account child = accountService.getUserById(Integer.parseInt(childId));
            child.setParent(null);
            /*if (Objects.equals(currentUser.getRole(), AppRole.ROLE_SUPER_ADMIN)){
                child.setSuperParent(null);
            }*/

            /*Account parent = accountService.getUserById(Integer.parseInt(parentId));
            parent.getChildrens().remove(child);

            accountService.UpdateUser(parent);*/
            accountService.UpdateUser(child);

            response.setStatus(JsonResponse.RESULT_SUCCESS);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.setStatus(JsonResponse.RESULT_FAILED);
        }

        return response;

    }


    @RequestMapping(value = "/getUserDetails", method = RequestMethod.POST)
    public @ResponseBody JsonResponse getUserDetails(@RequestParam(value = "userId", required = true) String userId){

        JsonResponse response = new JsonResponse();

        Account account = accountService.getUserById(Integer.parseInt(userId));

        if(account != null){
            response.setStatus(JsonResponse.RESULT_SUCCESS);
            response.setResult(account);
        } else {
            response.setStatus(JsonResponse.RESULT_FAILED);
        }

        return response;

    }


    @RequestMapping(value = "/assignProfessionalToGrower", method = RequestMethod.POST)
    public @ResponseBody JsonResponse assignProfessionalToGrower(@RequestParam(value = "parentId", required = true) String parentID,
                                                        @RequestParam(value = "growerId", required = true) String growerId ){

        JsonResponse response = new JsonResponse();

        Account grower = accountService.getUserById(Integer.parseInt(growerId));
        Account parent = accountService.getUserById(Integer.parseInt(parentID));

        PlantingProfitLogger.info("Assigning professional : " + parent.getEmail_Address() + " to grower : " + grower.getEmail_Address());

        grower.setParent(parent);

        boolean updateUserFlag = accountService.UpdateUser(grower);

        if (updateUserFlag){
            PlantingProfitLogger.info("Successfully assigned professional : " + parent.getEmail_Address() + " to grower : " + grower.getEmail_Address());
            response.setResult(parent);
            response.setStatus(JsonResponse.RESULT_SUCCESS);
        } else {
            PlantingProfitLogger.info("Error while assigning professional : " + parent.getEmail_Address() + " to grower : " + grower.getEmail_Address());
            response.setStatus(JsonResponse.RESULT_FAILED);
        }

        return response;
    }


    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public @ResponseBody JsonResponse deleteUser(@RequestParam(value = "userId", required = true) String userId) {

        PlantingProfitLogger.info("Deleting user for ID: " + userId);
        JsonResponse response = new JsonResponse();

        accountService.deleteUser(Integer.parseInt(userId));

        response.setStatus(JsonResponse.RESULT_SUCCESS);

        return response;
    }


    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public @ResponseBody JsonResponse updateUser(@RequestParam(value = "accountType", required = true) String accountType,
                                              @RequestParam(value = "firstName", required = true) String firstName,
                                              @RequestParam(value = "lastName", required = false) String lastName,
                                              @RequestParam(value = "contact", required = true) String contactNo,
                                              @RequestParam(value = "mailing_Address_Line1", required = false) String mailing_Address_Line1,
                                              @RequestParam(value = "mailing_Address_Line2", required = false) String mailing_Address_Line2,
                                              @RequestParam(value = "mailing_Address_City", required = false) String mailing_Address_City,
                                              @RequestParam(value = "mailing_Address_State", required = false) String mailing_Address_State,
                                              @RequestParam(value = "mailing_Address_Country", required = false) String mailing_Address_Country,
                                              @RequestParam(value = "mailing_Zip", required = true) String mailing_Zip,
                                              @RequestParam(value = "physical_Address_Line1", required = false) String physical_Address_Line1,
                                              @RequestParam(value = "physical_Address_Line2", required = false) String physical_Address_Line2,
                                              @RequestParam(value = "physical_Address_City", required = false) String physical_Address_City,
                                              @RequestParam(value = "physical_Address_State", required = false) String physical_Address_State,
                                              @RequestParam(value = "physical_Address_Country", required = false) String physical_Address_Country,
                                              @RequestParam(value = "physical_Zip", required = false) String physical_Zip,
                                              @RequestParam(value = "expiryDate", required = false) String expiryDate,
                                              @RequestParam(value = "userId", required = false) String userId,
                                              @RequestParam(value = "parentId", required = false) String parentId,
                                              @RequestParam(value = "logo", required = false) MultipartFile logo) throws ParseException {


        JsonResponse response = new JsonResponse();

        Account userToUpdate = accountService.getUserById(Integer.parseInt(userId));

        PlantingProfitLogger.info("Updating user : " + userToUpdate.getEmail_Address());

        userToUpdate.setFirstName(firstName);
        userToUpdate.setLastName(lastName);
        userToUpdate.setPhone_No(contactNo);

        userToUpdate.setMailing_Address_Line_1(mailing_Address_Line1);
        userToUpdate.setMailing_Address_Line_2(mailing_Address_Line2);
        userToUpdate.setMailing_Address_City(mailing_Address_City);
//        userToUpdate.setMailing_Address_State(mailing_Address_State);
//        userToUpdate.setMailing_Address_Country(mailing_Address_Country);
        userToUpdate.setMailing_Address_Zip(mailing_Zip);

        userToUpdate.setPhysical_Address_Line_1(physical_Address_Line1);
        userToUpdate.setPhysical_Address_Line_2(physical_Address_Line2);
        userToUpdate.setPhysical_Address_City(physical_Address_City);
//        userToUpdate.setPhysical_Address_State(physical_Address_State);
//        userToUpdate.setPhysical_Address_Country(physical_Address_Country);
        userToUpdate.setPhysical_Address_Zip(physical_Zip);

        if(!physical_Address_Country.equalsIgnoreCase(""))
            userToUpdate.setPhysical_Address_Country(accountService.getCountry(Integer.parseInt(physical_Address_Country)));
        if(!physical_Address_State.equalsIgnoreCase(""))
            userToUpdate.setPhysical_Address_State(accountService.getState(Integer.parseInt(physical_Address_State)));
        if(!mailing_Address_Country.equalsIgnoreCase(""))
            userToUpdate.setMailing_Address_Country(accountService.getCountry(Integer.parseInt(mailing_Address_Country)));
        if(!mailing_Address_State.equalsIgnoreCase(""))
            userToUpdate.setMailing_Address_State(accountService.getState(Integer.parseInt(mailing_Address_State)));


        if (expiryDate != null && !Objects.equals(expiryDate, "")) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(expiryDate);
            userToUpdate.setExpirationDate(date);
        }

        if (!Objects.equals(accountType, "")) {
            if (!Objects.equals(userToUpdate.getRole(), AppRole.valueOf(accountType)) && accountType.equalsIgnoreCase("ROLE_ADMIN")){
                userToUpdate.setRole(AppRole.ROLE_ADMIN);
                setNullForChildren(userToUpdate);
                userToUpdate.setChildrens(null);
                userToUpdate.setParent(accountService.getUserById(Integer.parseInt(parentId)));
            } else if (!Objects.equals(userToUpdate.getRole(), AppRole.valueOf(accountType)) && accountType.equalsIgnoreCase("ROLE_PROFESSIONAL")){
                userToUpdate.setRole(AppRole.ROLE_PROFESSIONAL);
                setNullForChildren(userToUpdate);
                userToUpdate.setParent(null);
                userToUpdate.setChildrens(null);
            } else if (!Objects.equals(userToUpdate.getRole(), AppRole.valueOf(accountType)) && accountType.equalsIgnoreCase("ROLE_GROWER")){
                userToUpdate.setRole(AppRole.ROLE_GROWER);
                setNullForChildren(userToUpdate);
                userToUpdate.setParent(null);
                userToUpdate.setChildrens(null);
            } else if (!Objects.equals(userToUpdate.getRole(), AppRole.valueOf(accountType)) && accountType.equalsIgnoreCase("ROLE_STUDENT")){
                userToUpdate.setRole(AppRole.ROLE_STUDENT);
                setNullForChildren(userToUpdate);
                userToUpdate.setParent(null);
                userToUpdate.setChildrens(null);
            }
        }

        if (Objects.equals(userToUpdate.getRole(), AppRole.ROLE_PROFESSIONAL) && logo!= null &&!logo.isEmpty()){
            accountService.uploadDocument(userToUpdate, logo, AccountDocumentsType.LOGO);
        }


        boolean saveStatus = accountService.UpdateUser(userToUpdate);

        if (saveStatus){
            PlantingProfitLogger.info("User updated");
            response.setStatus(JsonResponse.RESULT_SUCCESS);

        } else {
            PlantingProfitLogger.debug("User update failed");
            response.setStatus(JsonResponse.RESULT_FAILED);
        }

        return response;

    }


    private void setNullForChildren(Account account){
        PlantingProfitLogger.info("Updating children for " + account.getEmail_Address());
        Set<Account> children = account.getChildrens();
        PlantingProfitLogger.info("# of children for update : " + children.size());
        for (Account child : children) {

            child.setParent(null);

            accountService.UpdateUser(child);

        }

        PlantingProfitLogger.info("Update of children complete for " + account.getEmail_Address());
    }

}
