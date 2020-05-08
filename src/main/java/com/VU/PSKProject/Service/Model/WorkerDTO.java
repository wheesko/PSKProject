package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkerDTO {
    private Long id;

    private String name;
    private String surname;

    private TeamDTO managedTeam;
    private TeamDTO workingTeam;

    private int quarterLearningDayLimit;
    private int consecutiveLearningDayLimit;

    private List<LearningDayDTO> learningDays;
    private List<WorkerGoalDTO> goals;
}
