package com.VU.PSKProject.Service.Model.Worker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerManagedTeamDTO {
    private Long id;
    private String name;

    public WorkerManagedTeamDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public WorkerManagedTeamDTO(){

    }
}
