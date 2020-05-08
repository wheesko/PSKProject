package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Service.TeamService;
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
    public void createTeam(@RequestBody Team team){
        teamService.createTeam(team);
    }

    @PutMapping("/update/{id}")
    public void updateTeam(@RequestBody Team team, @PathVariable Long id){
        teamService.updateTeam(id, team);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
    }
}
