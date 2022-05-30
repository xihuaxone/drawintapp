package com.example.drawintapp.controller;

import com.example.drawintapp.service.DoorService;

public class DoorController {
    private final DoorService doorService = new DoorService();

    public void openAuto() {
        doorService.openAuto();
    }

    public Thread openAutoAsync() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                openAuto();
            }
        });
        thread.start();
        return thread;
    }
}
