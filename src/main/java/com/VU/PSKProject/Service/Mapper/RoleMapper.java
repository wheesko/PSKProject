package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.Role;
import com.VU.PSKProject.Entity.RoleGoal;
import com.VU.PSKProject.Service.Model.RoleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleMapper {
    @Autowired
    private ModelMapper modelMapper;

    public RoleDTO toDto(Role role){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        List<Long> roleGoals = new ArrayList<>();
        for (RoleGoal rg: role.getRoleGoals()) {
            roleGoals.add(rg.getId());
        }
        roleDTO.setRoleGoals(roleGoals);
        return roleDTO;
    }
}
