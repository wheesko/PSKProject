package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Service.Model.Team.TeamToUpdateDTO;
import com.VU.PSKProject.Service.Model.Team.TeamToGetDTO;
import com.VU.PSKProject.Service.Model.Team.TeamToCreateDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class TeamMapper {
    @Autowired
    private ModelMapper modelMapper;


    public Team fromDTO(TeamToUpdateDTO teamToUpdateDTO) {
        return modelMapper.map(teamToUpdateDTO, Team.class);
    }

    public Team fromDTO(TeamToCreateDTO teamToCreateDTO) {
        Team team = modelMapper.map(teamToCreateDTO, Team.class);
        team.setWorkers(new LinkedList<>());
        team.setGoals(new LinkedList<>());
        return team;
    }

    public TeamToGetDTO toDto(Team team) {
        return modelMapper.map(team, TeamToGetDTO.class);
    }
}