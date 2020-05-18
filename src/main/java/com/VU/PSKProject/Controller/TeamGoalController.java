package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Service.Model.TeamGoalDTO;
import com.VU.PSKProject.Service.TeamGoalService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.BeanUtils;
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
    public void createTeamGoal(@RequestBody TeamGoalDTO teamGoalDto){
        TeamGoal teamGoal = new TeamGoal();
        PropertyUtils.customCopyProperties(teamGoalDto, teamGoal);
        teamGoalService.createTeamGoal(teamGoal);
    }

    @PutMapping("/update/{id}")
    public void updateTeamGoal(@RequestBody TeamGoalDTO teamGoalDto, @PathVariable Long id){
        TeamGoal teamGoal = new TeamGoal();
        PropertyUtils.customCopyProperties(teamGoalDto, teamGoal);
        teamGoalService.updateTeamGoal(id, teamGoal);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteTeamGoal(@PathVariable Long id){
        teamGoalService.deleteTeamGoal(id);
    }
}
