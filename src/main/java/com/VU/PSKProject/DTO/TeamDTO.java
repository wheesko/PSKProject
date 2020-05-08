package com.VU.PSKProject.DTO;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class TeamDTO {
    private Long id;
    private String name;
    private Long managerId;
    private List<Long> workers;
    private List<Long> goals;
}
