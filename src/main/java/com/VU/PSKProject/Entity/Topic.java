package com.VU.PSKProject.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String name;
    @ManyToMany
    private List<Topic> childrenTopics;
    private String description;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "topic"
    )
    private List<TeamGoal> teamGoals;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "topic"
    )
    private List<WorkerGoal> workerGoals;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "topic"
    )
    private List<RoleGoal> roleGoals;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<LearningDay> learningDays;

    public Topic(){

    }

    public Topic(String name, List<Topic> childrenTopics, String description, List<TeamGoal> goals){
        this.name = name;
        this.childrenTopics = childrenTopics;
        this.description = description;
        this.teamGoals = goals;
    }
}
