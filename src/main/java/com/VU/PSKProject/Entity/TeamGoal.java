package com.VU.PSKProject.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class TeamGoal {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Team team;
    @ManyToOne
    private Topic topic;

}
