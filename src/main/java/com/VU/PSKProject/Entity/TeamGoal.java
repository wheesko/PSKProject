package com.VU.PSKProject.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class TeamGoal {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Team team;
    @ManyToOne
    private Topic topic;

}
