package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopicDTO {
    private Long id;
    private String name;
    private List<Long> childrenTopics;
    private String description;
    private List<Long> teamGoals;
    private List<Long> workerGoals;
    private List<Long> roleGoals;
}
