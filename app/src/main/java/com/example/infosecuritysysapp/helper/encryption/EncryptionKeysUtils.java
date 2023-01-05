package com.example.infosecuritysysapp.helper.encryption;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptionKeysUtils {

    private final static String AES = "AES";

    public static SecretKey createAESKey() throws Exception {
        SecureRandom securerandom = SecureRandom.getInstance("SHA1PRNG");
        KeyGenerator keygenerator = KeyGenerator.getInstance(AES);

        keygenerator.init(128, securerandom);
        return keygenerator.generateKey();
    }



    public static KeyPair generateRSAKeyPair() throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(2048, secureRandom);

        return keyPairGenerator.generateKeyPair();
    }
}
