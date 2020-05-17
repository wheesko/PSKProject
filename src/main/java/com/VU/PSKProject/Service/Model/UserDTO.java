package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String email; //TODO: add email regex
    private String password;
    private Long userRole;
}