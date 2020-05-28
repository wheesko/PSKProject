package com.VU.PSKProject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity(name = "team")
@Setter
@Getter
public class Team {

    public Team() {

    }

    public Team(String name,  Worker manager, List<Worker> workers, List<TeamGoal> goals) {
        this.name = name;
        this.manager = manager;
        this.workers = workers;
        this.goals = goals;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(mappedBy = "managedTeam")
    @JsonIgnore
    private Worker manager;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workingTeam")
    @JsonIgnore
    private List<Worker> workers;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "team")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<TeamGoal> goals;
    @Version
    private int version;
}
