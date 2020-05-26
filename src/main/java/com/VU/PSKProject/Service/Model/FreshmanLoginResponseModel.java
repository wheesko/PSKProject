package com.VU.PSKProject.Service.Model;

import com.VU.PSKProject.Entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreshmanLoginResponseModel {
    private Long userId;
    private Long workerId;
    private String email; //TODO: add email regex
    private String userAuthority;
    private Long managedTeamId;
    private Long workingTeamId;
    private String name;
    private String surname;
    private Role role;
}
