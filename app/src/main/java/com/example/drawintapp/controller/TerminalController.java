package com.example.drawintapp.controller;

import com.example.drawintapp.domain.bo.TerminalBO;
import com.example.drawintapp.domain.bo.TerminalRegisterBO;
import com.example.drawintapp.service.TerminalService;

import java.util.List;

public class TerminalController {
    TerminalService terminalService = new TerminalService();

    public void callAction(Long tmId, String actionCode) {
        terminalService.callAction(tmId, actionCode);
    }
}
