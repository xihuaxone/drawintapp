package com.example.drawintapp.domain.bo;


import java.util.List;

public class TerminalBO {
    private Long id;

    private String topic;

    private String name;

    private List<TerminalActionBO> actionList;

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getName() {
        return name;
    }

    public List<TerminalActionBO> getActionList() {
        return actionList;
    }
}
