package com.VU.PSKProject.Service.Model;

import com.VU.PSKProject.Service.Model.Worker.WorkerToGetDTOManagerDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerProfileDTO {
    private Integer consecutiveLearningDayLimit;
    private Integer quarterlyLearningDayLimit;
    private WorkerToGetDTOManagerDTO manager;
}
