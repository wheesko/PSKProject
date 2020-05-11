package com.VU.PSKProject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    private List<TeamGoal> goals;
}
