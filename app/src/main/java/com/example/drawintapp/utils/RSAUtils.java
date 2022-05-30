package com.example.drawintapp.utils;

import android.util.Base64;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;


public class RSAUtils {
    private static String encrypt(String content, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException,
            BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(AsymmetricAlgorithm.RSA_ECB_PKCS1.getValue());
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] bytes = cipher.doFinal(content.getBytes());
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static String encryptByPublicKey(String content, Key publicKey) {
        try {
            return encrypt(content, publicKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Claims unSign(String signStr, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(signStr).getBody();
    }
}
