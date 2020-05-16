package com.VU.PSKProject.Service.Model;

import com.VU.PSKProject.Entity.Worker;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class TeamDTOFull {
    private Long id;
    private String name;
    @JsonProperty("TeamLead")
    private WorkerDTO managerId;
    private List<WorkerDTO> workers;
    @JsonProperty("TeamGoals")
    private List<TeamGoalDTO> goals;
}
