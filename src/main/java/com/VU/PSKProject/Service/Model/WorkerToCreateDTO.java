package com.VU.PSKProject.Service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerToCreateDTO {
    private Long managerId;
    private String email;
    private Long teamId;
}
