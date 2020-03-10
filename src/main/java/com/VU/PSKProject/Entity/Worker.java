package com.VU.PSKProject.Entity;

import javax.persistence.*;

@Entity(name = "Worker")
public class Worker {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;
    private int quarterLearningDayLimit;
    private int consecutiveLearningDayLimit;
    // private Role role;
    // private List<WorkerGoals> workerGoals;
    // private List<LearningDays> learningDays;

    // complete assigning CORRECT relationships to other entities
    @ManyToOne(fetch = FetchType.LAZY)
    private Worker manager;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuarterLearningDayLimit() {
        return quarterLearningDayLimit;
    }

    public void setQuarterLearningDayLimit(int quarterLearningDayLimit) {
        this.quarterLearningDayLimit = quarterLearningDayLimit;
    }

    public int getConsecutiveLearningDayLimit() {
        return consecutiveLearningDayLimit;
    }

    public void setConsecutiveLearningDayLimit(int consecutiveLearningDayLimit) {
        this.consecutiveLearningDayLimit = consecutiveLearningDayLimit;
    }
}
