package com.VU.PSKProject.Service.Model.Team;

import com.VU.PSKProject.Service.Model.Worker.WorkerDTOStripped;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TeamToUpdateDTO {
    private Long id;
    private String name;
    private WorkerDTOStripped teamLead;
    private List<WorkerDTOStripped> workers;
    private List<WorkerDTOStripped> goals;
}
