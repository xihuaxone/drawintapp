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

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TerminalActionBO> getActionList() {
        return actionList;
    }

    public void setActionList(List<TerminalActionBO> actionList) {
        this.actionList = actionList;
    }
}
