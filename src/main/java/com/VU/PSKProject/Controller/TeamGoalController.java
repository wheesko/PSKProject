package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Service.Model.TeamGoalDTO;
import com.VU.PSKProject.Service.TeamGoalService;
import com.VU.PSKProject.Service.TeamService;
import com.VU.PSKProject.Service.TopicService;
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
    @Autowired
    private TeamService teamService;
    @Autowired
    private TopicService topicService;

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
        BeanUtils.copyProperties(teamGoalDto, teamGoal);
        teamService.getTeam(teamGoalDto.getTeam()).ifPresent(teamGoal::setTeam);
        topicService.getTopic(teamGoalDto.getTopic()).ifPresent(teamGoal::setTopic);
        teamGoalService.createTeamGoal(teamGoal);
    }

    @PutMapping("/update/{id}")
    public void updateTeamGoal(@RequestBody TeamGoalDTO teamGoalDto, @PathVariable Long id){
        TeamGoal teamGoal = new TeamGoal();
        BeanUtils.copyProperties(teamGoalDto, teamGoal);
        teamGoalService.updateTeamGoal(id, teamGoal);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteTeamGoal(@PathVariable Long id){
        teamGoalService.deleteTeamGoal(id);
    }
}
