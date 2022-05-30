package com.example.drawintapp.service;

import com.example.drawintapp.dal.TerminalDao;
import com.example.drawintapp.domain.bo.TerminalBO;

import java.util.List;

public class TerminalService {
    TerminalDao terminalDao = new TerminalDao();

    public List<TerminalBO> list() {
        return terminalDao.list();
    }
}
