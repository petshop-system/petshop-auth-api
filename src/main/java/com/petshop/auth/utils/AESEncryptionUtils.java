package com.petshop.auth.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Base64;

public class AESEncryptionUtils {

    private final IvParameterSpec aesIvParameterSpec;

    private final SecretKeySpec aesSecretKeySpec;

    private final Cipher aesCipher;

    public AESEncryptionUtils(IvParameterSpec aesIvParameterSpec,
                              SecretKeySpec aesSecretKeySpec,
                              Cipher aesCipher) {
        this.aesIvParameterSpec = aesIvParameterSpec;
        this.aesSecretKeySpec = aesSecretKeySpec;
        this.aesCipher = aesCipher;
    }

    public String encrypt(String value) {

        try {

            aesCipher.init(Cipher.ENCRYPT_MODE, aesSecretKeySpec, aesIvParameterSpec);
            byte[] encrypted = aesCipher.doFinal(value.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (InvalidKeyException |
                 InvalidAlgorithmParameterException |
                 IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt (String value) {

        try {

            aesCipher.init(Cipher.DECRYPT_MODE, aesSecretKeySpec, aesIvParameterSpec);
            byte[] decryped = aesCipher.doFinal(Base64.getDecoder()
                    .decode(value.getBytes()));
            return new String(decryped);

        } catch (InvalidKeyException |
                 InvalidAlgorithmParameterException |
                 IllegalBlockSizeException |
                 BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
