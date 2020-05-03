package com.VU.PSKProject.Service.Model;

import com.VU.PSKProject.Entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String email; //TODO: add email regex
    private String password;
    private UserRole userRole;
}