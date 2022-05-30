package com.example.drawintapp.dal;

import com.example.drawintapp.domain.ApiResult;
import com.example.drawintapp.domain.bo.TerminalBO;
import com.example.drawintapp.utils.HttpClient;

import java.util.List;

public class TerminalDao {
    public List<TerminalBO> list() {
        ApiResult<List<TerminalBO>> apiResult = HttpClient.get("/manager/terminals");
        if (!apiResult.isSuccess()) {
            throw new RuntimeException("register biz error: " + apiResult);
        }
        return apiResult.getData();
    }
}
