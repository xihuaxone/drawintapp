package com.example.drawintapp.dal;

import com.alibaba.fastjson.JSON;
import com.example.drawintapp.domain.ApiResult;
import com.example.drawintapp.domain.UserLoginBO;
import com.example.drawintapp.domain.po.UserLoginPO;
import com.example.drawintapp.domain.po.UserRegisterPO;
import com.example.drawintapp.utils.HttpClient;
import android.util.Base64;
import com.example.drawintapp.utils.RSAUtils;
import io.jsonwebtoken.Claims;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class UserIdentifyDao {
    public UserIdentifyDao() {}

    public void login(UserLoginPO userLoginPO) {
        ApiResult<Void> apiResult = HttpClient.post("tokens/", JSON.toJSONString(userLoginPO));
        if (!apiResult.isSuccess()) {
            throw new RuntimeException("login biz error: " + apiResult);
        }
        HttpClient.refreshToken(apiResult.getAuthorization());
    }

    public void register(UserRegisterPO userRegisterPO) {
        ApiResult<Void> apiResult = HttpClient.post("users/", JSON.toJSONString(userRegisterPO));
        if (!apiResult.isSuccess()) {
            throw new RuntimeException("register biz error: " + apiResult);
        }
    }

    public RSAPublicKey getPublicKey() {
        ApiResult<String> apiResult = HttpClient.get("tokens/rsa_public_key", String.class);
        if (!apiResult.isSuccess()) {
            throw new RuntimeException("get publicKey failed: " + apiResult);
        }
        String publicKeyStr = apiResult.getData();
        try {
            return getPublicKey(publicKeyStr);
        } catch (Exception e) {
            throw new RuntimeException("transfer RSA public key error.");
        }
    }

    public boolean isLogin() {
        return !HttpClient.isTokenEmpty();
    }

    public void logout() {
        ApiResult<Object> apiResult = HttpClient.delete("tokens/");
        if (!apiResult.isSuccess()) {
            throw new RuntimeException("logout failed: " + apiResult);
        }
        HttpClient.refreshToken(null);
    }

    public String getLoginName() {
        if (HttpClient.isTokenEmpty()) {
            return "";
        }
        String token = HttpClient.getToken();
        Claims claims = RSAUtils.unSign(token, getPublicKey());
        return claims.get("loginAccount", String.class);
    }

    private RSAPublicKey getPublicKey(String publicKeyStr) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decode(publicKeyStr, Base64.DEFAULT));
        return (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
    }
}
