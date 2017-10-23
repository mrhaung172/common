package com.util;

import ch.qos.logback.core.encoder.ByteArrayUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class EncryptUtils {
    private static final Logger logger = Logger.getLogger(EncryptUtils.class);

    public static String encrypt3DES(String key, String content) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return new String(Base64.encodeBase64(result)); // 加密
        } catch (Exception e) {
            logger.error("encrypt3DES 加密错误 key:" + key + " content: " + content);
        }
        return null;
    }

    public static String decryptAES(String content, String key) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, secretKey);// 初始化
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result).trim(); // 加密
        } catch (Exception e) {
            logger.error("decryptAES 加密错误 key:" + key + " content: " + content);
        }
        return null;
    }

    public static String encryptMd5(String content) {
        return DigestUtils.md5Hex(content);
    }

    /**
     * 加密
     *
     * @param keybyte 加密密钥，长度为24字节
     * @param content 被加密的数据缓冲区（源）
     * @return
     */
    public static byte[] encryptMode(byte[] keybyte, byte[] content) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");

            // 加密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(content);
        } catch (Exception e3) {
            logger.error("encryptMode 加密错误");
        }
        return null;
    }

    public static String encryptDESede(String content, String key) {
        return Base64Utils.encodeToString(encryptMode(key.getBytes(), content.getBytes()));
    }

    /**
     * 解密
     *
     * @param keybyte 加密密钥，长度为24字节
     * @param src     加密后的缓冲区
     * @return
     */
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, "DESede");

            // 解密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        throw new RuntimeException();
    }

    public static String decryotDESede(String key, String content) {
        return new String(decryptMode(key.getBytes(), Base64.decodeBase64(content)));
    }

    public static byte[] encryptSHA256(byte[] content) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256").digest(content);
    }

    public static String sha256(String content) throws NoSuchAlgorithmException {
        return ByteArrayUtil.toHexString(encryptSHA256(content.getBytes()));

    }

    /****************************************************************/
    public static String encrypt(String content, String key, String cipherAlgorithm, String charset) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), cipherAlgorithm.substring(0, cipherAlgorithm.indexOf("/")));
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);// 创建密码器
            byte[] byteContent = content.getBytes(charset);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return new String(Base64.encodeBase64(result)); // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String content, String key, String cipherAlgorithm) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), cipherAlgorithm.substring(0, cipherAlgorithm.indexOf("/")));
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, secretKey);// 初始化
            byte[] result = cipher.doFinal(Base64.decodeBase64(content));
            return new String(result).trim(); // 加密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
