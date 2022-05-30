package com.example.drawintapp.controller;

import com.example.drawintapp.service.LightService;

public class LightController {
    private final LightService lightService = new LightService();

    public void switchLight() {
        lightService.switchLight();
    }
}
