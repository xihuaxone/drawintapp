package com.example.drawintapp.service;

import com.example.drawintapp.dal.DoorDao;

public class DoorService {
    private final DoorDao doorDao = new DoorDao();

    public void openAuto() {
        doorDao.reset();
    }
}
