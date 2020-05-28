package com.VU.PSKProject.Service.Model.Worker;

import com.VU.PSKProject.Service.Model.LearningDay.LearningDayDTO;
import com.VU.PSKProject.Service.Model.RoleDTO;
import com.VU.PSKProject.Service.Model.TopicToReturnDTO;
import com.VU.PSKProject.Service.Model.WorkerGoalDTOtoGet;
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

    private WorkerToGetDTOManagerDTO manager;

    private List<LearningDayDTO> learningDays;
    private List<TopicToReturnDTO> goals;

    public WorkerToGetDTO(Long id, String name, String surname, String email, Integer quarterLearningDayLimit, Integer consecutiveLearningDayLimit) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.quarterLearningDayLimit = quarterLearningDayLimit;
        this.consecutiveLearningDayLimit = consecutiveLearningDayLimit;
    }
    public WorkerToGetDTO(){

    }

}
