package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerLoginResponseModel {
    private Long userId;
    private Long workerId;
    private String email; //TODO: add email regex
    private String userAuthority;
    private Long workingTeamId;
    private String name;
    private String surname;
    private String role;
}
