package com.decipher.util;

import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import com.decipher.util.PlantingProfitLogger;


public final class CryptographyUtils {


    private static final String ALGO = "AES";
    private static final byte[] KEY_VALUE =
            new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    private CryptographyUtils() {

    }

    public static String encryptData(String data) {
        String encryptData = "";
        try {
            Key key = generateKey();
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encVal = c.doFinal(data.getBytes());
            byte[] encryptedValue = new Base64().encode(encVal);
            encryptData = new String(encryptedValue);
        } catch (Exception e) {

            PlantingProfitLogger.error(e);
        }

        return encryptData;
    }

    public static String decryptData(String encryptedData) {
        String decryptedData = "";
        try {
            Key key = generateKey();
            PlantingProfitLogger.info("key : " + key);
            Cipher c = Cipher.getInstance(ALGO);
            c.init(Cipher.DECRYPT_MODE, key);

            /**
             * @added - abhishek
             * @desc - for removing white spaces in the encoded email
             */
            encryptedData = encryptedData.replaceAll(" ","+");


            byte[] decordedValue = new Base64().decode(encryptedData.getBytes());
            byte[] decValue = c.doFinal(decordedValue);

            String decryptedValue = new String(decValue);
            PlantingProfitLogger.info("inside decrypt date >>>>" + decryptedValue);
            decryptedData = decryptedValue.trim();
            PlantingProfitLogger.info("<<<<< after trim >>>>>>>" + decryptedData);
        } catch (Exception e) {
            PlantingProfitLogger.error(e);
        }
        return decryptedData;
    }

    private static Key generateKey() {
        Key key = null;
        try {
            key = new SecretKeySpec(KEY_VALUE, ALGO);
        } catch (Exception e) {

        }
        return key;
    }

    public static void main(String[] args) {
        //CryptographyUtils cu=new CryptographyUtils();
        String email = "manoj.pandya2911@gmail.com";
        PlantingProfitLogger.info("email : " + email);
        String encryptedEmail = CryptographyUtils.encryptData(email);
        PlantingProfitLogger.info("encryptedEmail : " + encryptedEmail);
        PlantingProfitLogger.info(CryptographyUtils.decryptData(encryptedEmail));
    }


}
