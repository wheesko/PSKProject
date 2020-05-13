package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkerDTO {
    private Long id;
    private Long userId;

    private String name;
    private String surname;
    private String email;

    private Long managedTeam;
    private Long workingTeam;

    private int quarterLearningDayLimit;
    private int consecutiveLearningDayLimit;

    private List<Long> learningDays;
    private List<Long> goals;
}
