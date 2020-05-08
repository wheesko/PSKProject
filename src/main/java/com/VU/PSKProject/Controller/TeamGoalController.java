package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Service.TeamGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teamGoals")
public class TeamGoalController {
    @Autowired
    private TeamGoalService teamGoalService;

    @GetMapping("/getAll")
    public List<TeamGoal> getTeamGoals(){
        return teamGoalService.getAllTeamGoals();
    }

    @GetMapping("/get/{id}")
    public Optional<TeamGoal> getTeamGoal(@PathVariable Long id){
        return teamGoalService.getTeamGoal(id);
    }

    @PostMapping("/create")
    public void createTeamGoal(@RequestBody TeamGoal teamGoal){ teamGoalService.createTeamGoal(teamGoal); }

    @PutMapping("/update/{id}")
    public void updateTeamGoal(@RequestBody TeamGoal teamGoal, @PathVariable Long id){
        teamGoalService.updateTeamGoal(id, teamGoal);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteTeamGoal(@PathVariable Long id){
        teamGoalService.deleteTeamGoal(id);
    }
}
