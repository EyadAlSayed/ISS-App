package com.example.infosecuritysysapp.helper.encryption;

import android.annotation.SuppressLint;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;

public class EncryptionTools {

    private static final String AES_CIPHER_ALGORITHM = "AES/ECB/PKCS5PADDING";

    private static final String RSA = "RSA";

    public static byte[] do_AESEncryption(String plainText, SecretKey secretKey/*, byte[] initializationVector*/) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, secretKey/*, ivParameterSpec*/);

        return cipher.doFinal(plainText.getBytes());
    }

    public static String do_AESDecryption(byte[] cipherText, SecretKey secretKey/*, byte[] initializationVector*/) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);

        cipher.init(Cipher.DECRYPT_MODE, secretKey/*, ivParameterSpec*/);

        byte[] result = cipher.doFinal(cipherText);

        return new String(result);
    }

    public static byte[] do_RSAEncryption(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(plainText.getBytes());
    }
    public static byte[] do_RSAEncryptionB(byte[] plainText, PublicKey publicKey) throws Exception {
        @SuppressLint("DeprecatedProvider") Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPwithSHA1andMGF1Padding","BC");

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(plainText);
    }


    public static String do_RSADecryption(byte[] cipherText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(cipherText);

        return new String(result);
    }
}
