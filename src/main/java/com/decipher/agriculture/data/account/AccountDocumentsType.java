package com.decipher.agriculture.data.account;

/**
 * Created by abhishek on 7/4/16.
 */
public enum AccountDocumentsType {

    LOGO(0),
    PROFILE_AVATAR(1);

    Integer typeId;
    AccountDocumentsType(Integer type){
        this.typeId = type;
    }

}
