package com.example.drawintapp.dal;

import com.example.drawintapp.domain.ApiResult;
import com.example.drawintapp.utils.HttpClient;

public class LightDao {

    public void switchLight() {
        ApiResult<Void> apiResult = HttpClient.get("index/light_switch/", Void.class);
        if (!apiResult.isSuccess()) {
            throw new RuntimeException("light switch biz error: " + apiResult);
        }
    }
}
