package com.hujiya.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AesEcbUtils {
    private static final Logger logger = LoggerFactory.getLogger(AesEcbUtils.class);

    public static String encrypt(String content, String key) {
        if (StringUtils.isBlank(content)) {
            logger.warn("encrypt: content is blank.");
            return null;
        } else {

            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("UTF-8"), "AES"));
                byte[] encrypted = cipher.doFinal(content.getBytes("UTF-8"));
                return Base64.getEncoder().encodeToString(encrypted);
            } catch (Exception var7) {
                logger.warn("encrypt: catch e.msg={}", var7.getMessage());
                throw new IllegalStateException("failed to encrypt the secure field.");
            }
        }
    }


    public static String decrypt(String content, String key) {
        if (StringUtils.isBlank(content)) {
            logger.warn("decrypt: content is blank.");
            return null;
        } else {

            try {
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("UTF-8"), "AES"));
                byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(content));
                return new String(decrypted, "UTF-8");
            } catch (Exception var8) {
                logger.warn("decrypt: catch e.msg={}", var8.getMessage());
                throw new IllegalStateException("failed to decrypt the secure field.");
            }
        }
    }


    // 加密的算法名称
    public static final String ALGORITHM_SHA1 = "SHA1WithRSA";
    public static final String ALGORITHM_SHA256 = "SHA256WithRSA";

    public static void main(String[] args) throws Exception {
        String publicKey = RSAUtils.readFile("/Users/vivian/Downloads/partner_publickey.pem");
        String privateKey =RSAUtils.readFile("/Users/vivian/Downloads/partner_privatekey.pem");
        String KEY = "字符串转二进制可以直接转，而二进制转字符串不可以，其中间包含的各种特殊符号，转成字符串时会出现问题，需要将二进制进行base64编码，并且需要在结尾加上#0表示结尾，然后转成字符串";
        String content = "com/hujiya/test";

//        String token = RSAUtils.publicEncrypt(KEY, RSAUtils.getPublicKey(publicKey));

//        System.out.println(token);
//        String miwen = AesEcbUtils.encrypt(content, KEY);
//        System.out.println(token + "\n\r" + miwen + "\r");
//        String newkey = RSAUtils.privateDecrypt(token, RSAUtils.getPrivateKey(privateKey));
//        System.out.println(newkey);
//        String neirong = AesEcbUtils.decrypt(miwen, newkey);
//        System.out.println(newkey + "\n\r" + neirong);


        String signature1 = RSAUtils.signWithSHA1(RSAUtils.getPrivateKey(privateKey), KEY, ALGORITHM_SHA1);
        System.out.println(RSAUtils.verify(KEY,signature1,RSAUtils.getPublicKey(publicKey),ALGORITHM_SHA1));

        String signature2 = RSAUtils.signWithSHA1(RSAUtils.getPrivateKey(privateKey), KEY, ALGORITHM_SHA256);
        System.out.println(RSAUtils.verify(KEY,signature2,RSAUtils.getPublicKey(publicKey),ALGORITHM_SHA256));

        String signature3 = RSAUtils.signWithSHA1(RSAUtils.getPrivateKey(privateKey), KEY, ALGORITHM_SHA1);
        System.out.println(RSAUtils.verify(KEY,signature3,RSAUtils.getPublicKey(publicKey),ALGORITHM_SHA256));

    }
}
