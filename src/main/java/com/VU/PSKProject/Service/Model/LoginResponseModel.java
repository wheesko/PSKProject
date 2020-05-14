package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseModel {
    private Long userId;
    private Long workerId;
    private String email; //TODO: add email regex
    private String userAuthority;
}
