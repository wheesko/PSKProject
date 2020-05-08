package com.VU.PSKProject.DTO;

import com.sun.tools.javac.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamDTO {
    private Long id;
    private String name;
    private Long managerId;
    private List<Long> workers;
    private List<Long> goals;
}
