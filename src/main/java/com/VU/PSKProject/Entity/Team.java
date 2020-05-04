package com.VU.PSKProject.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(
            mappedBy = "team"
    )
    private List<Worker> teamMembers;

    @OneToMany(
            mappedBy = "team"
    )
    private List<TeamGoal> teamGoals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
