package com.VU.PSKProject.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WorkerDTO {
    private Long id;
    private String name;
    private String surname;

    private Long managedTeamId;
    private Long workingTeamId;

    private int quarterLearningDayLimit;
    private int consecutinveLearningDayLimit;

    private List<Long> learningDays;
    private List<Long> goals;
}
