package com.VU.PSKProject.Service.Model.LearningDay;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LearningDayAssigneeDTO {
    private Long id;

    public LearningDayAssigneeDTO(Long id) {
        this.id = id;
    }
    public LearningDayAssigneeDTO(){

    }
}

