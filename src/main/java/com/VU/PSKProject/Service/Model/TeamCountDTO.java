package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TeamCountDTO {
    private Long id;
    private String name;

    private int learnedCount;
    private int planningCount;
    private int dreamingCount;

}
