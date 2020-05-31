package com.VU.PSKProject.Service.Model.LearningDay;

import com.VU.PSKProject.Service.Model.TopicToReturnDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToGetDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class LearningDayToReturnDTO {
    private Long id;
    private String name;
    private String comment;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Vilnius")
    private Timestamp dateTimeAt;
    private WorkerToGetDTO assignee;
    private TopicToReturnDTO topic;
    private boolean learned;
}
