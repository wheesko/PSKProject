package com.VU.PSKProject.Service.Model.LearningDay;

import com.VU.PSKProject.Service.Model.TopicDTO;
import com.VU.PSKProject.Service.Model.TopicToReturnDTO;
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
    private TopicToReturnDTO topic;
    private boolean learned;

    public LearningDayDTO(Long id, String name, String comment, Timestamp dateTimeAt, boolean learned) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.dateTimeAt = dateTimeAt;
        this.learned = learned;
    }
    public LearningDayDTO(){

    }
}
