package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleGoalDTO {
    private Long id;
    private RoleDTO role;
    private TopicDTO topic;
}
