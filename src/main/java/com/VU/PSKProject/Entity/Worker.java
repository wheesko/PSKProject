package com.VU.PSKProject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "worker")
@Setter
@Getter
public class Worker {

    public Worker() {

    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Team managedTeam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Team workingTeam;

    private int quarterLearningDayLimit;

    private int consecutiveLearningDayLimit;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<LearningDay> learningDays = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "worker")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<WorkerGoal> goals = new ArrayList<>();
}
