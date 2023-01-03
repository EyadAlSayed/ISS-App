//package com.example.infosecuritysysapp.helper;
//
//import android.os.Build;
//
//import androidx.annotation.RequiresApi;
//
//import java.math.BigInteger;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.security.SecureRandom;
//import java.util.Base64;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.Mac;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
//public class SymmetricEncryptionTools {
//    private static final String AES = "AES";
//
//    private static final String AES_CIPHER_ALGORITHM = "AES/ECB/PKCS5PADDING";
//
//
//    public static SecretKey createAESKey() throws Exception {
//        SecureRandom secureRandom = new SecureRandom();
//        KeyGenerator keygenerator = KeyGenerator.getInstance(AES);
//
//        keygenerator.init(128, secureRandom);
//        SecretKey key = keygenerator.generateKey();
//
//        return key;
//    }
//
//
//
//    public static byte[] do_AESEncryption(String plainText, SecretKey secretKey) throws Exception
//    {
//        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
//
//        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//
//        return cipher.doFinal(plainText.getBytes());
//    }
//
//    public static String do_AESDecryption(byte[] cipherText, SecretKey secretKey) throws Exception
//    {
//        Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
//
//        cipher.init(Cipher.DECRYPT_MODE, secretKey);
//
//        byte[] result = cipher.doFinal(cipherText);
//
//        return new String(result);
//    }
//
//    public static String convertByteToHexadecimal(byte[] byteArray) {
//        String hex = "";
//
//        for (byte i : byteArray) {
//            hex += String.format("%02X", i);
//        }
//
//        return hex;
//    }
//
//    public static byte[] hexStringToByteArray(String s) {
//        int len = s.length();
//        byte[] data = new byte[len / 2];
//        for (int i = 0; i < len; i += 2) {
//            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
//                    + Character.digit(s.charAt(i+1), 16));
//        }
//        return data;
//    }
//
//    public static SecretKey retrieveSecretKey(String keyAsString){
//
//        byte[] bytes = new BigInteger("7F" + keyAsString, 16).toByteArray();
//        return new SecretKeySpec(bytes, 1, bytes.length-1, "AES");
//    }
//
//    public static String getMac(String key, String data) throws NoSuchAlgorithmException, InvalidKeyException {
//        Mac mac = Mac.getInstance("HmacSHA256");
//        mac.init(retrieveSecretKey(key));
//        byte[] macResult = mac.doFinal(hexStringToByteArray(data));
//        return convertByteToHexadecimal(macResult);
//    }
//}
