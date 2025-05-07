package com.baxdigital.steganoapp.util;

import javax.crypto.Cipher;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class FileEncryptor {


    private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY_FACTORY = "PBKDF2WithHmacSHA256";
    private static final String SALT = "stegano-salt"; // kamu bisa ganti ini

    // Generate SecretKey dari password
    private static SecretKeySpec getKeyFromPassword(String password) throws Exception {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT.getBytes(), 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_FACTORY);
        SecretKey secretKey = factory.generateSecret(spec);
        return new SecretKeySpec(secretKey.getEncoded(), ALGORITHM);
    }

    public static byte[] encrypt(byte[] data, String password) throws Exception {
        SecretKeySpec key = getKeyFromPassword(password);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] encryptedData, String password) throws Exception {
        SecretKeySpec key = getKeyFromPassword(password);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encryptedData);
    }
}
