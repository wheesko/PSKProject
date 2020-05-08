package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerGoalDTO {
    private Long id;

    private WorkerDTO worker;
    private TopicDTO topic;
}
