package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkerDTO {
    private Long id;
    //private WorkerToUserDTO userId;

    private String name;
    private String surname;
    private String email;

    private RoleDTO role;
    private WorkerManagedTeamDTO managedTeam;
    private WorkerWorkingTeamDTO workingTeam;

    private int quarterLearningDayLimit;
    private int consecutiveLearningDayLimit;

    //private List<LearningDayDTO> learningDays;
    private List<WorkerGoalDTO> goals;
}
