package com.VU.PSKProject.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Setter
@Getter
public class RoleGoal {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Role role;
    @ManyToOne
    private Topic topic;
}
