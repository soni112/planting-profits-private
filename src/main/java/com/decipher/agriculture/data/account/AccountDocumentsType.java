package com.decipher.agriculture.data.account;

import java.io.Serializable;

/**
 * Created by abhishek on 7/4/16.
 */
public enum AccountDocumentsType implements Serializable {

    LOGO(0),
    PROFILE_AVATAR(1);

    Integer typeId;
    AccountDocumentsType(Integer type){
        this.typeId = type;
    }

}
