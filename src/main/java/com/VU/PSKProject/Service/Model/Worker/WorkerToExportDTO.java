package com.VU.PSKProject.Service.Model.Worker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerToExportDTO {
    private String name;
    private String surname;
    private String email;
    private String role;
    private String workingTeam;
    private int quarterLearningDayLimit;
    private int consecutiveLearningDayLimit;
}
