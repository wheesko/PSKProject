package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Service.Model.TeamDTO;
import com.VU.PSKProject.Service.TeamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping("/getAll")
    public List<Team> getTeams(){
        return teamService.getAllTeams();
    }

    @GetMapping("/get/{id}")
    public Optional<Team> getTeam(@PathVariable Long id){
        return teamService.getTeam(id);
    }

    @PostMapping("/create")
    public void createTeam(@RequestBody TeamDTO teamDto){
        Team team = new Team();
        BeanUtils.copyProperties(teamDto, team);
        teamService.createTeam(team);
    }

    @PutMapping("/update/{id}")
    public void updateTeam(@RequestBody TeamDTO teamDto, @PathVariable Long id){
        Team team = new Team();
        BeanUtils.copyProperties(teamDto, team);
        teamService.updateTeam(id, team);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
    }
}
