package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.TeamGoal;
import com.VU.PSKProject.Service.Model.Team.TeamGoalDTO;
import com.VU.PSKProject.Service.TeamGoalService;
import com.VU.PSKProject.Utils.PropertyUtils;
import com.VU.PSKProject.Service.TeamService;
import com.VU.PSKProject.Service.TopicService;
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
            TeamGoalDTO teamGoalDTO = new TeamGoalDTO(teamGoal.getId(),teamGoal.getTeam().getId(), teamGoal.getTopic().getId());
            teamGoalDTOS.add(teamGoalDTO);
        }
        return ResponseEntity.ok(teamGoalDTOS);
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<TeamGoalDTO> getTeamGoal(@PathVariable Long id){
        Optional<TeamGoal> teamGoal = teamGoalService.getTeamGoal(id);
        if(teamGoal.isPresent()){
            TeamGoalDTO teamGoalDTO = new TeamGoalDTO(teamGoal.get().getId(),teamGoal.get().getTeam().getId(), teamGoal.get().getTopic().getId());
            return ResponseEntity.ok(teamGoalDTO);
        }
        else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "TeamGoal with id " + id + " could not be found");
            return ResponseEntity.notFound().headers(headers).build();
        }
    }
    @PostMapping("/create")
    public ResponseEntity<String> createTeamGoal(@RequestBody TeamGoalDTO teamGoalDto){
        TeamGoal teamGoal = new TeamGoal();
        PropertyUtils.customCopyProperties(teamGoalDto, teamGoal);
        teamService.getTeam(teamGoalDto.getTeam()).ifPresent(teamGoal::setTeam);
        topicService.getTopic(teamGoalDto.getTopic()).ifPresent(teamGoal::setTopic);

        List<TeamGoal> teamGoals = teamGoalService.getAllTeamGoals();

        for (TeamGoal teamg: teamGoals) {
            if (teamGoalDto.getTopic().equals(teamg.getTopic().getId()) && teamg.getTeam().getId().equals(teamGoalDto.getTeam())){
                HttpHeaders headers = new HttpHeaders();
                headers.add("Message", "identical goal already exists");
                return ResponseEntity.badRequest().headers(headers).build();
            }
        }

        if (teamGoalService.checkIfTeamAndTopicExist(teamGoalDto.getTeam(), teamGoalDto.getTopic())) {
            teamGoalService.createTeamGoal(teamGoal);
            return ResponseEntity.ok("ok created");
        }
        else{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "bad team or topic");
            return ResponseEntity.badRequest().headers(headers).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTeamGoal(@PathVariable Long id){
        teamGoalService.deleteTeamGoal(id);
    }
}
