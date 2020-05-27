package com.VU.PSKProject.Service.Model.Worker;

import com.VU.PSKProject.Service.Model.RoleDTO;
import com.opencsv.bean.CsvIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkerToGetDTO {
    private Long id;
    private Long managerId;
    //private WorkerToUserDTO userId;

    private String name;
    private String surname;
    private String email;

    private RoleDTO role;
    private WorkerManagedTeamDTO managedTeam;
    private WorkerWorkingTeamDTO workingTeam;

    private Integer quarterLearningDayLimit;
    private Integer consecutiveLearningDayLimit;

    //private List<LearningDayDTO> learningDays;
    private List<WorkerGoalDTO> goals;
}
