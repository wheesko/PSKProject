package com.VU.PSKProject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Role")
@Setter
@Getter
public class Role {

    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<RoleGoal> roleGoals;

    public Role(){
    }

    public Role(String name, List<RoleGoal> roleGoals){
        this.name = name;
        this.roleGoals = roleGoals;
    }
}
