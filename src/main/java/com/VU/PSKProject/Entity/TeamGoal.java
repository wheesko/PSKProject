package com.VU.PSKProject.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class TeamGoal {
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Team team;
    @ManyToOne
    private Topic topic;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
