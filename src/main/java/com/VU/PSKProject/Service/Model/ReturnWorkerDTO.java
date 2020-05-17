package com.VU.PSKProject.Service.Model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReturnWorkerDTO {
    private Long id;
    private Long teamId;
    private Long managedTeamId;
    private String name;
    private String surname;
    private String role;
    private String email;
    private List<Long> goals; // let's stick with goal ids for now
    private int consecutiveLearningDayLimit;
    private int quarterLearningDayLimit;
}
