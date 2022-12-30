package com.example.infosecuritysysapp.helper.encryption;

import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class EncryptionKeysUtils {

    private final static String AES = "AES";

    public static SecretKey createAESKey() throws Exception {
        SecureRandom securerandom = new SecureRandom();
        KeyGenerator keygenerator = KeyGenerator.getInstance(AES);

        keygenerator.init(128, securerandom);
        return keygenerator.generateKey();
    }
}
