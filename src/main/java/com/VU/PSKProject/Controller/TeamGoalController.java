package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Service.Model.TeamGoalDTO;
import com.VU.PSKProject.Service.TeamGoalService;
import com.VU.PSKProject.Service.TeamService;
import com.VU.PSKProject.Service.TopicService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<TeamGoalDTO>> getTeamGoals(){

        List<TeamGoal> teamGoals = teamGoalService.getAllTeamGoals();
        List<TeamGoalDTO> teamGoalDTOS = new ArrayList<>();
        for (TeamGoal teamGoal : teamGoals) {
            TeamGoalDTO teamGoalDTO = new TeamGoalDTO();

            teamGoalDTO.setId(teamGoal.getId());
            teamGoalDTO.setTopic(teamGoal.getTopic().getId());
            teamGoalDTO.setTeam(teamGoal.getTeam().getId());

            teamGoalDTOS.add(teamGoalDTO);
        }
        return ResponseEntity.ok(teamGoalDTOS);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<TeamGoalDTO> getTeamGoal(@PathVariable Long id){
        Optional<TeamGoal> teamGoal = teamGoalService.getTeamGoal(id);
        if(teamGoal.isPresent()){
            TeamGoalDTO teamGoalDTO = new TeamGoalDTO();

            teamGoalDTO.setId(teamGoal.get().getId());
            teamGoalDTO.setTopic(teamGoal.get().getTopic().getId());
            teamGoalDTO.setTeam(teamGoal.get().getTeam().getId());
            return ResponseEntity.ok(teamGoalDTO);
        }
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "TeamGoal with id " + id + " could not be found");
            return ResponseEntity.notFound().headers(headers).build();
        }
    }

    @PostMapping("/create")
    public void createTeamGoal(@RequestBody TeamGoalDTO teamGoalDto){
        TeamGoal teamGoal = new TeamGoal();
        PropertyUtils.customCopyProperties(teamGoalDto, teamGoal);
        teamService.getTeam(teamGoalDto.getTeam()).ifPresent(teamGoal::setTeam);
        topicService.getTopic(teamGoalDto.getTopic()).ifPresent(teamGoal::setTopic);
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
