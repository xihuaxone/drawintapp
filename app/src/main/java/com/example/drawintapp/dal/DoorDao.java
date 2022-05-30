package com.example.drawintapp.dal;

import com.alibaba.fastjson.JSONObject;
import com.example.drawintapp.domain.ApiResult;
import com.example.drawintapp.utils.HttpClient;

public class DoorDao {

    public void reset() {
        JSONObject body = new JSONObject();
        body.put("switchPort", 1);
        body.put("action", "RESET");
        ApiResult<Object> apiResult = HttpClient.post("index/door/", body.toJSONString());
        if (!apiResult.isSuccess()) {
            throw new RuntimeException("door openAuto biz error: " + apiResult);
        }
    }

    public void openManual() {
        // TODO
    }

    public void closeManual() {
        // TODO
    }
}
