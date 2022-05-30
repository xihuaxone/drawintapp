package com.example.drawintapp.service;

import com.example.drawintapp.dal.LightDao;

public class LightService {
    private final LightDao lightDao = new LightDao();

    public void switchLight() {
        lightDao.switchLight();
    }
}
