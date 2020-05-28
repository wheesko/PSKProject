package com.VU.PSKProject.Service.Model.Worker;

import com.VU.PSKProject.Entity.Role;
import com.VU.PSKProject.Service.Model.WorkerRoleCreateDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerToCreateDTO {
    private String email;
    private WorkerRoleCreateDTO role;
}
