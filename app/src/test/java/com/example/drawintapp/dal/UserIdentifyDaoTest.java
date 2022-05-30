package com.example.drawintapp.dal;

import org.junit.Test;

import java.security.interfaces.RSAPublicKey;

public class UserIdentifyDaoTest {

    @Test
    public void testGetPublicKey() {
        RSAPublicKey publicKey = new UserIdentifyDao().getPublicKey();
        System.out.println(publicKey);
    }
}
