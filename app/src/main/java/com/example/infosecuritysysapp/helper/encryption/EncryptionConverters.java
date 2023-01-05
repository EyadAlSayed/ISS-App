package com.example.infosecuritysysapp.helper.encryption;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class EncryptionConverters {

    public static String convertByteToHexadecimal(byte[] byteArray) {
        String hex = "";

        // Iterating through each byte in the array
        for (byte i : byteArray) {
            hex += String.format("%02X", i);
        }

        return hex;
    }

    public static byte[] hexStringToByteArray(String s) {

        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static SecretKey retrieveSymmetricSecretKey(String keyAsString){

        byte[] bytes = new BigInteger("7F" + keyAsString, 16).toByteArray();
        return new SecretKeySpec(bytes, 1, bytes.length-1, "AES");
    }

    public static PublicKey retrievePublicKey(String keyAsString) throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] byteKey = hexStringToByteArray(keyAsString);
        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePublic(new X509EncodedKeySpec(byteKey));
    }

    public static PrivateKey retrievePrivateKey(String keyAsString) throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] keyData = hexStringToByteArray(keyAsString);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(new PKCS8EncodedKeySpec(keyData));
    }
}
