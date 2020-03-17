package com.VU.PSKProject.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
public class LearningEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String comment;

    // enforce validation of 24h timeframe
    private Long startDate;
    private Long endDate;
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Worker assignedWorker;

    // "would be nice to have" functionality
    // will have to be implemented with the help of a separate entity
    // private int repeatingPeriod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Worker getAssignedWorker() {
        return assignedWorker;
    }

    public void setAssignedWorker(Worker assignedWorker) {
        this.assignedWorker = assignedWorker;
    }
}
