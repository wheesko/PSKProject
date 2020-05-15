package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Service.Model.TeamDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Team fromDTO(TeamDTO teamDTO){
        return modelMapper.map(teamDTO, Team.class);
    }

    public TeamDTO toDto(Team team){
        return modelMapper.map(team, TeamDTO.class);
    }
}
