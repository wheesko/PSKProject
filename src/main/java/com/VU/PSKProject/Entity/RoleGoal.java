package com.VU.PSKProject.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "role_goal")
@Setter
@Getter
public class RoleGoal {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Topic topic;
    public RoleGoal(){
    }
    public RoleGoal(Role role, Topic topic){
        this.role = role;
        this.topic = topic;
    }
}
