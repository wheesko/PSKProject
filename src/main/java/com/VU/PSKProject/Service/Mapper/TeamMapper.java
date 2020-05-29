package com.VU.PSKProject.Service.Mapper;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.Model.Team.TeamGoalDTO;
import com.VU.PSKProject.Service.Model.Team.TeamToUpdateDTO;
import com.VU.PSKProject.Service.Model.Team.TeamToGetDTO;
import com.VU.PSKProject.Service.Model.Team.TeamToCreateDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToGetDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class TeamMapper {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WorkerMapper workerMapper;
    @Autowired
    private TeamGoalMapper teamGoalMapper;


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
        TeamToGetDTO teamToGetDTO = new TeamToGetDTO();
        teamToGetDTO.setId(team.getId());
        teamToGetDTO.setName(team.getName());
        teamToGetDTO.setManagerId(workerMapper.toDTO(team.getManager()));
        List<WorkerToGetDTO> workers = new ArrayList<>();
        // if there are workers in a team, map them
        // else return a new empty worker array
        if (team.getWorkers().size() > 0) {
            for (Worker w : team.getWorkers()) {
                workers.add(workerMapper.toDTO(w));
            }
            teamToGetDTO.setWorkers(workers);
        } else teamToGetDTO.setWorkers(new ArrayList<WorkerToGetDTO>());
        List<TeamGoalDTO> teamGoals = new ArrayList<>();
        for (TeamGoal g : team.getGoals()) {
            teamGoals.add(teamGoalMapper.toDto(g));
        }
        teamToGetDTO.setGoals(teamGoals);
        return teamToGetDTO;
    }
}