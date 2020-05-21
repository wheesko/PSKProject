package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleGoalDTO {
    private Long id;
    private Long role;
    private Long topic;

    public RoleGoalDTO(){

    }
    public RoleGoalDTO(Long id, Long role, Long topic){
        this.id = id;
        this.role = role;
        this.topic = topic;
    }
}
