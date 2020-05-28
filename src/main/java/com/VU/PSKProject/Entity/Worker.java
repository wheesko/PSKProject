package com.VU.PSKProject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "worker")
//@DynamicUpdate
@Setter
@Getter
public class Worker {

    public Worker() {

    }

    public Worker(String name, String surname, User user, Team managedTeam, Team workingTeam, Role role,
                  int quarterLearningDayLimit, int consecutiveLearningDayLimit, List<LearningDay> learningDays, List<WorkerGoal> workerGoals)
    {
        this.name = name;
        this.surname = surname;
        this.managedTeam = managedTeam;
        this.workingTeam = workingTeam;
        this.user = user;
        this.role = role;
        this.quarterLearningDayLimit = quarterLearningDayLimit;
        this.consecutiveLearningDayLimit = consecutiveLearningDayLimit;
        this.learningDays = learningDays;
        this.goals = workerGoals;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String surname;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    private Team managedTeam;

    @ManyToOne(fetch = FetchType.EAGER)
    private Team workingTeam;

    @OneToOne(fetch = FetchType.EAGER)
    private Role role;

    private int quarterLearningDayLimit;

    private int consecutiveLearningDayLimit;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<LearningDay> learningDays = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<WorkerGoal> goals = new ArrayList<>();

    @Version
    private int version;
}
