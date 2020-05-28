package com.VU.PSKProject.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.jdbc.Work;

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

    private boolean learned;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Timestamp dateTimeAt;

    @NotNull
    @ManyToOne
    private Worker assignee;

    @Override
    public int compareTo(LearningDay learningDay) {
        return this.getDateTimeAt().compareTo(learningDay.dateTimeAt);
    }

    @ManyToOne
    private Topic topic;

    public LearningDay(){}

    public LearningDay(String name, String comment, Timestamp dateTimeAt, Worker assignee, Topic topic)
    {
        this.name = name;
        this.comment = comment;
        this.assignee = assignee;
        this.dateTimeAt = dateTimeAt;
        this.topic = topic;
    }
    @Version
    private int version;
}
