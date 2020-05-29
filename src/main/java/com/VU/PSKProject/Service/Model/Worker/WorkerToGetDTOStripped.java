package com.VU.PSKProject.Service.Model.Worker;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter@Setter
public class WorkerToGetDTOStripped {
    private Long id;
    private Long managerId;
    private String name;
    private String surname;
    private String email;

    private Long role;
    private Long managedTeam;
    private Long workingTeam;

    private Integer quarterLearningDayLimit;
    private Integer consecutiveLearningDayLimit;

    private List<Long> learningDays;
    private List<Long> goals;

    public WorkerToGetDTOStripped(Long id, String name, String surname, String email, Long role, Integer quarterLearningDayLimit, Integer consecutiveLearningDayLimit) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
        this.quarterLearningDayLimit = quarterLearningDayLimit;
        this.consecutiveLearningDayLimit = consecutiveLearningDayLimit;
    }
    public WorkerToGetDTOStripped(){

    }
}
