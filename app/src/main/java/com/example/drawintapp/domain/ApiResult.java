package com.example.drawintapp.domain;

import lombok.Data;

@Data
public class ApiResult <T> {
    private Integer code;

    private boolean success;

    private String errMsg;

    private T data;

    private String authorization;

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", success=" + success +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
