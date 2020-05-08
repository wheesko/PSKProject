package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleDTO {
    private Long id;
    private String name;
    private List<Long> roleGoals;
}
