package com.VU.PSKProject.Entity;

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

    private Timestamp startAt;

    private Timestamp endAt;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Worker assignee;

    // "would be nice to have" functionality
    // will have to be implemented with the help of a separate entity
    // private int repeatingPeriod;
}
