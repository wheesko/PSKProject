package com.VU.PSKProject.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class WorkerGoal {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Worker worker;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topic topic;
}
