package com.VU.PSKProject.Service.Model.Worker;

import com.VU.PSKProject.Entity.UserAuthority;
import com.VU.PSKProject.Service.Model.RoleDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserToRegisterDTO {
    public UserAuthority userAuthority;
    private String name;
    private String surname;
    private String email;

    private RoleDTO role;
    private WorkerManagedTeamDTO managedTeam;
    private WorkerWorkingTeamDTO workingTeam;

    private Integer quarterLearningDayLimit;
    private Integer consecutiveLearningDayLimit;

    //private List<LearningDayDTO> learningDays;
    //private List<WorkerWithoutGoalsDTO> goals;
}
