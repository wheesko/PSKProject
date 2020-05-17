package com.VU.PSKProject.Service.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class LearningDayDTO {
    private Long id;
    private String name;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Vilnius")
    private Timestamp dateTimeAt;
    private LearningDayAssigneeDTO assignee;
    private Long topic;
}
