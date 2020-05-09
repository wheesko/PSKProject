package com.VU.PSKProject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class Team {

    public Team() {

    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(mappedBy = "managedTeam")
    @JsonIgnore
    private Worker manager;

    @OneToMany(mappedBy = "workingTeam")
    @JsonIgnore
    private List<Worker> workers;

    @OneToMany(mappedBy = "team")
    private List<TeamGoal> goals;
}
