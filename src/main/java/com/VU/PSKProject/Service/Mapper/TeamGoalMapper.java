package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Service.Model.Team.TeamGoalDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class TeamGoalMapper {
    @Autowired
    private ModelMapper modelMapper;

    public TeamGoal fromDTO(TeamGoalDTO teamGoalDTO)
    {
        return modelMapper.map(teamGoalDTO, TeamGoal.class);
    }

    public TeamGoalDTO toDto(TeamGoal teamGoal){
        return modelMapper.map(teamGoal, TeamGoalDTO.class);
    }
}
