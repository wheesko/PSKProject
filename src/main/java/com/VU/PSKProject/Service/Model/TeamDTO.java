package com.VU.PSKProject.Service.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TeamDTO {
    private Long id;
    private String name;
    private WorkerDTOStripped managerId;
    private List<WorkerDTOStripped> workers;
    private List<WorkerDTOStripped> goals;
}
