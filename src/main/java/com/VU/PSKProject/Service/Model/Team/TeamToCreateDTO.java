package com.VU.PSKProject.Service.Model.Team;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamToCreateDTO {
    private String name;
    private String goal;
}
