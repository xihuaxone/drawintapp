package com.example.drawintapp.service;

import com.example.drawintapp.dal.UserIdentifyDao;
import com.example.drawintapp.domain.UserLoginBO;
import com.example.drawintapp.domain.po.UserLoginPO;
import com.example.drawintapp.domain.po.UserRegisterPO;
import com.example.drawintapp.utils.RSAUtils;
import com.example.drawintapp.utils.SHA256Util;

public class UserService {
    private String signature;

    private final UserIdentifyDao userIdentifyDao = new UserIdentifyDao();


    public UserService() {}

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void login(UserLoginBO userLoginBO) {
        UserLoginPO userLoginPO = new UserLoginPO();
        userLoginPO.setAccount(userLoginBO.getAccount());
        userLoginPO.setPassword(
                RSAUtils.encryptByPublicKey(SHA256Util.encrypt(userLoginBO.getPassword()),
                        new UserIdentifyDao().getPublicKey()));
        userLoginPO.setAccountType(userLoginBO.getAccountType());
        userIdentifyDao.login(userLoginPO);
    }

    public void logout() {
        userIdentifyDao.logout();
    }

    public void register(UserRegisterPO userRegisterPO) {
        userRegisterPO.setPassword(RSAUtils.encryptByPublicKey(SHA256Util.encrypt(userRegisterPO.getPassword()),
                new UserIdentifyDao().getPublicKey()));
        if (userRegisterPO.getAccount().isEmpty()) {
            userRegisterPO.setAccount(userRegisterPO.getName());
        }
        userIdentifyDao.register(userRegisterPO);
    }

    public boolean isLogin() {
        return userIdentifyDao.isLogin();
    }

    public String getCurrentUser() {
        return userIdentifyDao.getLoginName();
    }
}
