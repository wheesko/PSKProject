package com.VU.PSKProject.Entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Role")
public class Role {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "role"
    )
    private List<RoleGoal> roleGoals;

    public Role(){

    }
    public Role(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
