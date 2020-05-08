package com.VU.PSKProject.Service.Model;

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
    private Long assignee;
}
