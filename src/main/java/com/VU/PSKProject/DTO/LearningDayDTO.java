package com.VU.PSKProject.DTO;

import com.VU.PSKProject.Entity.Worker;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class LearningDayDTO {
    private Long id;
    private String name;
    private String comment;
    private Timestamp startAt;
    private Timestamp endAt;
    private Long assigneeId;
}
