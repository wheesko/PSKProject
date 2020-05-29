package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Service.Model.Team.TeamGoalDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamGoalMapper {
    @Autowired
    private ModelMapper modelMapper;

    public TeamGoal fromDTO(TeamGoalDTO teamGoalDTO)
    {
        return modelMapper.map(teamGoalDTO, TeamGoal.class);
    }

    public TeamGoalDTO toDto(TeamGoal teamGoal){
        TeamGoalDTO tg = new TeamGoalDTO();
        tg.setId(teamGoal.getId());
        tg.setTeam(teamGoal.getTeam().getId());
        tg.setTopic(teamGoal.getTopic().getId());
        return tg;
    }
}
