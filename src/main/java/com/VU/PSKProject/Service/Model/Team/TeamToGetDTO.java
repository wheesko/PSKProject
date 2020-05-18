package com.VU.PSKProject.Service.Model.Team;

import com.VU.PSKProject.Service.Model.TeamGoalDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class TeamToGetDTO {
    private Long id;
    private String name;
    @JsonProperty("TeamLead")
    private WorkerDTO managerId;
    private List<WorkerDTO> workers;
    @JsonProperty("TeamGoals")
    private List<TeamGoalDTO> goals;
}
