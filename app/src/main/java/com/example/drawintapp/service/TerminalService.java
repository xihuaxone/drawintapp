package com.example.drawintapp.service;

import com.example.drawintapp.dal.TerminalDao;
import com.example.drawintapp.domain.bo.TerminalBO;
import com.example.drawintapp.domain.bo.TerminalRegisterBO;

import java.util.List;

public class TerminalService {
    TerminalDao terminalDao = new TerminalDao();

    public List<TerminalBO> list() {
        return terminalDao.list();
    }

    public void register(TerminalRegisterBO terminalRegisterBO) {
        terminalDao.register(terminalRegisterBO);
    }
}
