package com.VU.PSKProject.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "journal")
@Setter
@Getter
public class MethodJournalRecord {
    @Id
    @GeneratedValue
    private long id;

    private Long teamId;

    private String userNameSurname;

    private String time;

    private String methodName;

    private String className;

}
