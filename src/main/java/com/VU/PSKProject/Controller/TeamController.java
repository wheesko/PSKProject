package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping
    public List<Team> getTeams(){
        return teamService.getAllTeams();
    }

    @GetMapping("{id}")
    public Optional<Team> getTeam(@PathVariable Long id){
        return teamService.getTeam(id);
    }

    @PostMapping
    public void createTeam(@RequestBody Team team){
        teamService.createTeam(team);
    }

    @PutMapping("{id}")
    public void updateTeam(@RequestBody Team team, @PathVariable Long id){
        teamService.updateTeam(id, team);
    }

    @DeleteMapping("{id}")
    public void deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
    }
}
