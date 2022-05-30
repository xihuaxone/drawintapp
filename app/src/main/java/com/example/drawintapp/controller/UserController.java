package com.example.drawintapp.controller;

import com.example.drawintapp.domain.UserLoginBO;
import com.example.drawintapp.domain.po.UserRegisterPO;
import com.example.drawintapp.service.UserService;

public class UserController {
    private final UserService userService = new UserService();

    public UserController() {
    }

    public void login(UserLoginBO userLoginBO) {
        userService.login(userLoginBO);
    }

    public void logout() {
        userService.logout();
    }

    public void register(UserRegisterPO userRegisterPO) {
        userService.register(userRegisterPO);
    }

    public boolean isLogin() {
        return userService.isLogin();
    }

    public String getCurrentUser() {
        return userService.getCurrentUser();
    }
}
