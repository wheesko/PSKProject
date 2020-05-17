package com.VU.PSKProject.Service.Model;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamToCreateDTO {
    private Long id;
    private String name;
    private Long managerId;
}
