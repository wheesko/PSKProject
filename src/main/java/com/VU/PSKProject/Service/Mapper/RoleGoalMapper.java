package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.RoleGoal;
import com.VU.PSKProject.Service.Model.RoleGoalDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleGoalMapper {
    @Autowired
    private ModelMapper modelMapper;

    public RoleGoal fromDTO(RoleGoalDTO roleGoalDTO)
    {
        return modelMapper.map(roleGoalDTO, RoleGoal.class);
    }

    public RoleGoalDTO toDto(RoleGoal roleGoal){
        return modelMapper.map(roleGoal, RoleGoalDTO.class);
    }
}
