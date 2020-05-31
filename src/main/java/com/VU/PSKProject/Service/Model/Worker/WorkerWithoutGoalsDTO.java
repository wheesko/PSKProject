package com.VU.PSKProject.Service.Model.Worker;

import com.VU.PSKProject.Service.Model.RoleDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerWithoutGoalsDTO {
    private Long id;
    //private WorkerToUserDTO userId;

    private String name;
    private String surname;
    private String email;

    private RoleWithoutGoalDTO role;
    private WorkerManagedTeamDTO managedTeam;
    private WorkerWorkingTeamDTO workingTeam;

    private Integer quarterLearningDayLimit;
    private Integer consecutiveLearningDayLimit;
}
