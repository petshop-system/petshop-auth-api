package com.petshop.auth.configuration.encrypt;

import com.petshop.auth.utils.AESEncryptionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Configuration
public class EncryptConfiguration {

    @Bean("BCryptPasswordEncoder")
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    @ConfigurationProperties("encryption.aes")
    AESProperties aesProperties() {
        return new AESProperties();
    }

    @Bean("aesIvParameterSpec")
    IvParameterSpec aesIvParameterSpec(AESProperties aesProperties) {
        byte[] iv = aesProperties
                .getInitialVector()
                .getBytes(StandardCharsets.UTF_8);
        return new IvParameterSpec(iv);
    }

    @Bean
    SecretKeySpec aesSecretKeySpec(AESProperties aesProperties) {
        return new SecretKeySpec(aesProperties.getKey()
                .getBytes(StandardCharsets.UTF_8), "AES");
    }

    @Bean
    Cipher aesCipher(AESProperties aesProperties) {

        try {

            return Cipher.getInstance(aesProperties.getAlgorithmic());

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }

    }

    @Bean
    AESEncryptionUtils aesEncryptionUtils(@Qualifier("aesIvParameterSpec") IvParameterSpec aesIvParameterSpec,
                                           SecretKeySpec aesSecretKeySpec,
                                           Cipher aesCipher) {
        return new AESEncryptionUtils(aesIvParameterSpec, aesSecretKeySpec, aesCipher);
    }

}
