package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamGoalDTO {
    private Long id;
    private TeamDTO team;
    private TopicDTO topic;
}