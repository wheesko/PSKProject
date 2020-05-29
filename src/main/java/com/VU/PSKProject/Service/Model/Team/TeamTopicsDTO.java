package com.VU.PSKProject.Service.Model.Team;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class TeamTopicsDTO {
    private Long id;
    private String name;

    private Long manager;

    private List<String> topicsPast = new ArrayList<>();
    private List<String> topicsFuture = new ArrayList<>();

    public TeamTopicsDTO(Long id, String name, Long manager){
        this.id = id;
        this.name = name;
        this.manager = manager;
    }
    public void setTopicPast(String topic){
        topicsPast.add(topic);
    }
    public void setTopicFuture(String topic){
        topicsFuture.add(topic);
    }

    public TeamTopicsDTO(){

    }
}
