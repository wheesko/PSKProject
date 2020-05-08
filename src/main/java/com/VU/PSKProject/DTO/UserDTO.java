package com.VU.PSKProject.DTO;

import com.VU.PSKProject.Entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Long id;

    private String email;
    private String password;
    private UserRole userRole;
}
