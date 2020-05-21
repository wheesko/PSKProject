package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.Mapper.TeamMapper;
import com.VU.PSKProject.Service.Model.Team.TeamToUpdateDTO;
import com.VU.PSKProject.Service.Model.Team.TeamToGetDTO;
import com.VU.PSKProject.Service.Model.Team.TeamToCreateDTO;
import com.VU.PSKProject.Service.Model.Team.TeamCountDTO;
import com.VU.PSKProject.Service.TeamService;
import com.VU.PSKProject.Service.WorkerGoalService;
import com.VU.PSKProject.Service.WorkerService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getByTopic/{id}/{managerId}")
    public ResponseEntity<List<TeamToGetDTO>> getTeamsByTopic(@PathVariable Long id, @PathVariable Long managerId){
        List<Team> teams = teamService.getTeamsByTopicId(id, managerId);
        List<TeamToGetDTO> teamDTOS = teams.stream().map(teamMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOS);
    }
    @GetMapping("/getByTopicIds/{ids}/{managerId}")
    public ResponseEntity<List<TeamToGetDTO>> getTeamsByTopics(@PathVariable List<Long> ids, @PathVariable Long managerId){
        List<Team> teams = teamService.getTeamsByTopicIds(ids, managerId);
        List<TeamToGetDTO> teamDTOS = teams.stream().map(teamMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOS);
    }

    @GetMapping("/getTeamsCountByTopics/{teamIds}/{topicIds}/{managerId}")
    public ResponseEntity<List<TeamCountDTO>> getTeamsCountByTopics(@PathVariable List<Long> topicIds,
                                                                    @PathVariable List<Long> teamIds,
                                                                    @PathVariable Long managerId){
        // for this to werk we need to know manager id
        Optional<Worker> manager = workerService.getWorker(managerId);

        List<Team> teams = teamService.getAllTeams();
        List<TeamCountDTO> teamCountDTOS = new ArrayList<>();
        for (Team team: teams) {
            if(teamIds.contains(team.getId()) && manager.get().getManagedTeam().getId().equals(team.getId())){
                TeamCountDTO teamCountDTO = new TeamCountDTO();

                teamCountDTO.setId(team.getId());
                teamCountDTO.setName(team.getName());

                // false time means PAST, true means FUTURE
                teamCountDTO.setLearnedCount(workerService.getWorkersByTopicsTeamManager
                        (team.getId(), topicIds, manager.get(), false).size());

                teamCountDTO.setPlanningCount(workerService.getWorkersByTopicsTeamManager
                        (team.getId(), topicIds, manager.get(), true).size());

                teamCountDTO.setDreamingCount(workerGoalService.getWorkersByGoalsTeamManager
                        (team.getId(), topicIds, manager.get()).size());


                teamCountDTOS.add(teamCountDTO);
            }
        }
        return ResponseEntity.ok(teamCountDTOS);
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
