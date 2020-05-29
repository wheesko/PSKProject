package com.VU.PSKProject.Service.Model.Worker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerWorkingTeamDTO {
    private Long id;
    private String name;

    public WorkerWorkingTeamDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public WorkerWorkingTeamDTO() {

    }
}
