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
public class LearningDay {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String comment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp startAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp endAt;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Worker assignee;

    // "would be nice to have" functionality
    // will have to be implemented with the help of a separate entity
    // private int repeatingPeriod;
}
