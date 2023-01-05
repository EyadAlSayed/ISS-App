package com.example.infosecuritysysapp.helper.encryption;

import static com.example.infosecuritysysapp.helper.encryption.EncryptionConverters.convertByteToHexadecimal;

import android.util.Log;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class DigitalSignatureTools {

    private static final String SIGNING_ALGORITHM = "SHA256withRSA";

    public static byte[] createDigitalSignature(byte[] input, PrivateKey key) throws Exception {
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initSign(key);
        signature.update(input);
        return signature.sign();
    }

    public static boolean verifyDigitalSignature(byte[] input, byte[] signatureToVerify, PublicKey key) throws Exception {
        Signature signature = Signature.getInstance(SIGNING_ALGORITHM);
        signature.initVerify(key);
        signature.update(input);
        return signature.verify(signatureToVerify);
    }
}
