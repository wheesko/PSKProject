package com.VU.PSKProject.Service.Model.Worker;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WorkerTopicsDTO {

    private Long id;
    private String name;
    private String surname;

    private Long manager;

    private List<String> topicsPast = new ArrayList<>();
    private List<String> topicsFuture = new ArrayList<>();

    public WorkerTopicsDTO(Long id, String name, String surname, Long manager){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.manager = manager;
    }
    public void setTopicPast(String topic){
        topicsPast.add(topic);
    }
    public void setTopicFuture(String topic){
        topicsFuture.add(topic);
    }
}
