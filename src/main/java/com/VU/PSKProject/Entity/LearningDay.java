package com.VU.PSKProject.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity(name = "learning_day")
@Setter
@Getter
public class LearningDay implements Comparable<LearningDay> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp dateTimeAt;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Worker assignee;

    @Override
    public int compareTo(LearningDay learningDay) {
        return this.getDateTimeAt().compareTo(learningDay.dateTimeAt);
    }

}
