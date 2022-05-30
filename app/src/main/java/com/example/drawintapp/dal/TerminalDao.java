package com.example.drawintapp.dal;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.drawintapp.domain.ApiResult;
import com.example.drawintapp.domain.bo.TerminalBO;
import com.example.drawintapp.domain.bo.TerminalRegisterBO;
import com.example.drawintapp.utils.HttpClient;

import java.util.List;

public class TerminalDao {
    public List<TerminalBO> list() {
        ApiResult<JSONArray> apiResult = HttpClient.get("/manager/terminals", JSONArray.class);
        if (!apiResult.isSuccess()) {
            throw new RuntimeException("register biz error: " + apiResult);
        }
        return apiResult.getData().toJavaList(TerminalBO.class);
    }

    public void register(TerminalRegisterBO terminalRegisterBO) {
        ApiResult<Object> apiResult = HttpClient.post("/manager/terminals", JSON.toJSONString(terminalRegisterBO));
        if (!apiResult.isSuccess()) {
            throw new RuntimeException("register biz error: " + apiResult);
        }
    }
}
