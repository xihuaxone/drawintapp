package com.example.drawintapp.controller;

import com.example.drawintapp.domain.bo.TerminalBO;
import com.example.drawintapp.domain.bo.TerminalRegisterBO;
import com.example.drawintapp.service.TerminalService;

import java.util.List;

public class TerminalController {
    UserController userController = new UserController();
    TerminalService terminalService = new TerminalService();

    public List<TerminalBO> list() {
        return terminalService.list();
    }

    public void register(TerminalRegisterBO terminalRegisterBO) {
        terminalService.register(terminalRegisterBO);
    }
}
