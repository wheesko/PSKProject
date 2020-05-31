package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleGoalDTO {
    private Long id;
    private RoleDTO role;
    private TopicDTO topic;

    public RoleGoalDTO(){

    }
    public RoleGoalDTO(Long id, RoleDTO role, TopicDTO topic){
        this.id = id;
        this.role = role;
        this.topic = topic;
    }
}
