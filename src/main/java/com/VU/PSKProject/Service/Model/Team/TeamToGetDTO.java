package com.VU.PSKProject.Service.Model.Team;

import com.VU.PSKProject.Service.Model.Worker.WorkerToGetDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.List;
@Getter
@Setter
public class TeamToGetDTO {
    private Long id;
    private String name;
    @JsonProperty("TeamLead")
    private WorkerToGetDTO managerId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<WorkerToGetDTO> workers;
    @JsonProperty("TeamGoals")
    private List<TeamGoalDTO> goals;
}
