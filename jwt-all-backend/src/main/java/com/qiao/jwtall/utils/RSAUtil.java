package com.qiao.jwtall.utils;

import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import org.apache.commons.codec.Encoder;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

    public static void main(String[] args) throws Exception {
//        RsaKeyPair rsaKeyPair = generateKeyPair();
//        System.out.println("private key: " + rsaKeyPair.getPrivateKey());
//        System.out.println("---------------");
//        System.out.println("public key: " + rsaKeyPair.getPublicKey());
//        System.out.println("---------------");
//
//        String rawText = "Hello, this is CIA";
//        String encryptedText = encryptByPrivateKey(rsaKeyPair.getPrivateKey(), rawText);
//        String decryptedText = decryptByPublicKey(rsaKeyPair.getPublicKey(), encryptedText);
//        System.out.println("decrypted text: " + decryptedText);
//
//        rawText = "Hello, this is FBI";
//        encryptedText = encryptByPublicKey(rsaKeyPair.getPublicKey(), rawText);
//        System.out.println("Encrypted Text: " + encryptedText);
//        decryptedText = decryptByPrivateKey(rsaKeyPair.getPrivateKey(), encryptedText);
//        System.out.println("decrypted text: " + decryptedText);
//
//        byte[] bytes = Decoders.BASE64.decode("ZmQ0ZGI5NjQ0MDQwY2I4MjMxY2Y3ZmI3MjdhN2ZmMjNhODViOTg1ZGE0NTBjMGM4NDA5NzYxMjdjOWMwYWRmZTBlZjlhNGY3ZTg4Y2U3YTE1ODVkZDU5Y2Y3OGYwZWE1NzUzNWQ2YjFjZDc0NGMxZWU2MmQ3MjY1NzJmNTE0MzI=");
//        System.out.println(new String(bytes));

        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCF+mpfOuHXm2O0o3kGRN/KbBaxIyzMVx1Me5NhXErXZKNsfjEdjtHTyLraIJSY7DetrSH1P3Vwnbb/cBwtz84XP0j95mghZCGVE2HwQDcEaKDfg7Yn3BsLBeCv8kvWxU+P374hcvPRZ28HbGwHtPPEpeYhaFpjg9gh/c9v8VEg5QIDAQAB";
        System.out.println(decryptByPublicKey(publicKey, "SmyuRZUXqn0E0daC4In_qyMuHEXb5UiouCWZnJaUCd0"));
        System.out.println(new String(TextCodec.BASE64URL.decode("eyJhbGciOiJIUzI1NiJ9")));
        System.out.println(new String(TextCodec.BASE64URL.decode("eyJzdWIiOiJhZG1pbiIsInVzZXIiOnsidXNlcm5hbWUiOiJhZG1pbiIsImVtYWlsIjoiMTIzNDU2IiwicGFzc3dvcmQiOm51bGx9LCJpYXQiOjE2NDYwNTkyMTQsImV4cCI6MTY0NjA1OTUxNH0")));
        System.out.println(new String(TextCodec.BASE64URL.decode("SmyuRZUXqn0E0daC4In_qyMuHEXb5UiouCWZnJaUCd0")));
//        String encodedKey = Encoders.BASE64.encode("TUlJQ2RRSUJBREFOQmdrcWhraUc5dzBCQVFFRkFBU0NBbDh3Z2dKYkFnRUFBb0dCQUlYNmFsODY0ZGViWTdTamVRWkUzOHBzRnJFakxNeFhIVXg3azJGY1N0ZGtvMngrTVIyTzBkUEl1dG9nbEpqc042MnRJZlUvZFhDZHR2OXdIQzNQemhjL1NQM21hQ0ZrSVpVVFlmQkFOd1Jvb04rRHRpZmNHd3NGNEsveVM5YkZUNC9mdmlGeTg5Rm5id2RzYkFlMDg4U2w1aUZvV21PRDJDSDl6Mi94VVNEbEFnTUJBQUVDZ1lBL1lrcEN4VnVsSWptWWN5SkZpZjRLQ1BydlVNSFg1bnJRL1VHcmRQdGZUWmhYTXBoUDhJcHFWSE81MEdJY3V0eFlpU2tnbXFZWDRqVjZRWExEWmFXTS95ZVVscmx2ZHdmQzBjVzQveHR5UWFZS3ExYzlCRjYxY045dkVMMjV5VkNCMzE2ODZoVjhCaE5xVzVudHRvaG11SjdQeGxTWTNlWjVVMFBod282TllRSkJBTlFHMXJVNTRHR1h5d2hobFpSMC83emJLQUhxS2ZnTEZkQ1JlZjhwcU5jUkJIUTBTWjFEMW1Dc2M3ZHphdEVpVDNtRXZ6NmxKdXFhYU5BcHJQQkZ6YjBDUVFDaHc3eURPa1l3K09TZ0VGL0lneTUzUFpQQ3JkT3p3MEVkcDZoSm02WHF5bU1wU0FqYlBheG8wRHJqbkc2ZGlUTncreS9GV2tFUHR0S3ZmYjI3ajY1SkFrQm55NjFDYk1IbXBOdTFENmlkVTZWNmU4TWJKTnBFM3BCZ1dVZGkzYThWRWZTZk9wV3JFbkdaUS9NbUpLOXZFQi9kK2xseFBvSk5xU2VVZUVKOElYLzFBa0FuREdUWkdKd1Eva1BTRDZIUGVUc2lOWThSazN4akRHTVBrWVVpSDV4eGpPRmErYlNKbDkyaWY0RjNxQzliZjk4WENzdUJURVFnVXZXUXZ6MnBwUWxoQWtCUjRhM3dUWWxkRmVxU2p0bTRMbjlIRk1SQndiaFVwVUlRUzhHZW1OVlcxY0RjNktoN28vWjdzNkhYRlp3MEthSXFKODFZY1VVUGs1a3RVdFhWbGtjNg==".getBytes(StandardCharsets.UTF_8));
//        System.out.println(encodedKey);

    }

    /**
     * decrypt by public key
     *
     * @param publicKeyText public key
     * @param encryptedText
     * @return /
     * @throws Exception /
     */
    public static String decryptByPublicKey(String publicKeyText, String encryptedText) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyText);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(encryptedText));
        return new String(result);
    }

    /**
     * encrypt by private key
     *
     * @param privateKeyText private key
     * @param rawText raw text
     * @return /
     * @throws Exception /
     */
    public static String encryptByPrivateKey(String privateKeyText, String rawText) throws Exception {
        PrivateKey privateKey = getPrivateKey(privateKeyText);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(rawText.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * decrypt by private key
     *
     * @param privateKeyText private key
     * @param encryptedText encrypted text
     * @return /
     * @throws Exception /
     */
    public static String decryptByPrivateKey(String privateKeyText, String encryptedText) throws Exception {
        PrivateKey privateKey = getPrivateKey(privateKeyText);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(encryptedText));
        return new String(result);
    }

    /**
     * encrypt by public key
     *
     * @param publicKeyText public key
     * @param rawText raw text
     * @return /
     */
    public static String encryptByPublicKey(String publicKeyText, String rawText) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyText);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(rawText.getBytes());
        return Base64.encodeBase64String(result);
    }

    /**
     * Get public key
     * @param publicKeyText
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKeyText) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyText.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String privateKeyText) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyText));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        return privateKey;
    }


    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        return new RsaKeyPair(publicKeyString, privateKeyString);
    }

    public static class RsaKeyPair {

        private final String publicKey;
        private final String privateKey;

        public RsaKeyPair(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

    }

}
