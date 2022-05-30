package com.example.drawintapp.domain.bo;

import lombok.Data;

@Data
public class TerminalActionBO {
    private Long id;

    private Long tmId;

    private String name;

    private String code;

    private Byte concurrencyLevel;

    private Integer interval;
}
