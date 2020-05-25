package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Service.Mapper.TeamMapper;
import com.VU.PSKProject.Service.Model.Team.*;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.TeamService;
import com.VU.PSKProject.Service.UserService;
import com.VU.PSKProject.Service.WorkerGoalService;
import com.VU.PSKProject.Service.WorkerService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private WorkerGoalService workerGoalService;
    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<TeamToGetDTO>> getTeams(){
        List<Team> teams = teamService.getAllTeams();
        List<TeamToGetDTO> teamDTOS = new ArrayList<>();
        for (Team t: teams) {
            TeamToGetDTO teamDTO = teamMapper.toDto(t);
            teamDTOS.add(teamDTO);
        }
        return ResponseEntity.ok(teamDTOS);
    }

    @GetMapping("/getByTopic/{id}")
    public ResponseEntity<List<TeamToGetDTO>> getTeamsByTopic(@PathVariable Long id, Principal principal){
        UserDTO user = userService.getUserByEmail(principal.getName());
        List<TeamToGetDTO> teamDTOS = teamService.getTeamsByTopicId(id, user).stream().map(teamMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOS);
    }
    @GetMapping("/getByTopicIds/{ids}")
    public ResponseEntity<List<TeamToGetDTO>> getTeamsByTopics(@PathVariable List<Long> ids, Principal principal){
        UserDTO user = userService.getUserByEmail(principal.getName());
        List<TeamToGetDTO> teamDTOS = teamService.getTeamsByTopicIds(ids, user).stream().map(teamMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOS);
    }

    @GetMapping("/getTeamsCountByTopics/{teamIds}/{topicIds}")
    public ResponseEntity<List<TeamCountDTO>> getTeamsCountByTopics(@PathVariable List<Long> topicIds,
                                                                    @PathVariable List<Long> teamIds,
                                                                    Principal principal){
        UserDTO user = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(teamService.getTeamsCountDTOByTopics(topicIds,teamIds, user));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TeamToGetDTO> getTeam(@PathVariable Long id){
        Optional<Team> team = teamService.getTeam(id);
        if(team.isPresent()){
            TeamToGetDTO teamDTO = teamMapper.toDto(team.get());
            return ResponseEntity.ok(teamDTO);
        }else
        {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "Team with id " + id + " could not be found");
            return ResponseEntity.notFound().headers(headers).build();
        }
    }

    @PostMapping("/create")
    public void createTeam(@RequestBody TeamToCreateDTO teamDto){
        Team team = teamMapper.fromDTO(teamDto);

        workerService.getWorker(team.getManager().getId()).ifPresent(w -> {
            w.setManagedTeam(team);
            teamService.createTeam(team);
        });
    }

    @PutMapping("/update/{id}")
    public void updateTeam(@RequestBody TeamToUpdateDTO teamToUpdateDto, @PathVariable Long id){
        teamService.getTeam(id).ifPresent(w ->
        {
            PropertyUtils.customCopyProperties(teamToUpdateDto, w);
            teamService.updateTeam(id, w);
        });
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
    }
}
