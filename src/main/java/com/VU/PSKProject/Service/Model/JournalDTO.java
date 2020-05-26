package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JournalDTO {
    private String team;

    private String userNameSurname;

    private String time;

    private String methodName;

    private String className;

    public JournalDTO(String team, String userNameSurname, String time, String methodName, String className) {
        this.team = team;
        this.userNameSurname = userNameSurname;
        this.time = time;
        this.methodName = methodName;
        this.className = className;
    }
}
