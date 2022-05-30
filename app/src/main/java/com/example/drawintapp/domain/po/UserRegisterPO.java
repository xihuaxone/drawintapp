package com.example.drawintapp.domain.po;

import lombok.Data;

@Data
public class UserRegisterPO {

    private String name;

    private String gender;

    private String accountType;

    private String account;

    private String password;
}
