package com.VU.PSKProject.Service.Model.Worker;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerToCreateDTO {
    private Long managerId; // Save this for swagger tests
    private String email;
    private Long id;
}
