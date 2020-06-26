package com.wequan.bu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * @author ChrisChen
 */
public class GeneralTool {

    private static final Logger log = LoggerFactory.getLogger(GeneralTool.class);

    private static final String salt = "wequan@bu";
    private static final String secretKey = "https://bubbs.cc";
    private static final Pattern emailPattern = Pattern.compile("^([a-zA-Z0-9]+[_|.]?)+[a-zA-Z0-9]@([a-zA-Z0-9]+(-[a-zA-Z0-9]+)?\\.)+[a-zA-Z]{2,}$");
    private static final Pattern passwordPattern = Pattern.compile("");

    public static String encrypt(String text) {
        String encryptedText = null;
        try {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            byte[] bytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            encryptedText = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            log.error("encrypt exception for text: " + text, e);
        }
        return encryptedText;
    }

    public static String decrypt(String text) {
        String decryptedText = null;
        try {
            byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(text));
            decryptedText = new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("decrypt exception for text: " + text, e);
        }
        return decryptedText;
    }

    public static boolean checkUsername(String userName){
        return StringUtils.hasText(userName);
    }

    public static boolean checkEmail(String email) {
        return StringUtils.hasText(email) && emailPattern.matcher(email).matches();
    }

    public static boolean checkPassword(String password) {
        return StringUtils.hasText(password);
    }

}
