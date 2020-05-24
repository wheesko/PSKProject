package com.VU.PSKProject.Service.Model.LearningDay;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class LearningDayToCreateRequest {
    private String learningEventName;
    private String learningEventComment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Vilnius")
    private Timestamp date;
    private Long learningEventTopic;
}
