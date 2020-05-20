package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamGoalDTO {
    private Long id;
    private Long team;
    private Long topic;

    public TeamGoalDTO(){

    }
    public TeamGoalDTO(Long id, Long team, Long topic){
        this.id = id;
        this.team = team;
        this.topic = topic;
    }
}
