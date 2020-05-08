package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TopicDTO {
    private Long id;
    private String name;
    private TopicDTO parentTopic;
    private String description;
    private List<TeamGoalDTO> teamGoals;
}
