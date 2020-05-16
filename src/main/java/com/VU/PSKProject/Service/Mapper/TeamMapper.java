package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Service.Model.TeamDTO;
import com.VU.PSKProject.Service.Model.TeamDTOFull;
import com.VU.PSKProject.Service.Model.TeamToCreateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class TeamMapper {
    @Autowired
    private ModelMapper modelMapper;


    public Team fromDTO(TeamDTO teamDTO) {
        return modelMapper.map(teamDTO, Team.class);
    }

    public Team fromDTO(TeamToCreateDTO teamToCreateDTO) {
        Team team = modelMapper.map(teamToCreateDTO, Team.class);
        team.setWorkers(new LinkedList<>());
        team.setGoals(new LinkedList<>());
        return team;
    }

    public TeamDTOFull toDto(Team team) {
        return modelMapper.map(team, TeamDTOFull.class);
    }
}