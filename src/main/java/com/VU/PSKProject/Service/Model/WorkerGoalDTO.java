package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerGoalDTO {
    private Long id;
    private Long worker;
    private Long topic;

    public WorkerGoalDTO(){

    }
    public WorkerGoalDTO(Long id, Long worker, Long topic){
        this.id = id;
        this.worker = worker;
        this.topic = topic;
    }
}
