package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Service.Mapper.TeamMapper;
import com.VU.PSKProject.Service.Model.TeamDTO;
import com.VU.PSKProject.Service.Model.TeamDTOFull;
import com.VU.PSKProject.Service.Model.TeamToCreateDTO;
import com.VU.PSKProject.Service.TeamService;
import com.VU.PSKProject.Service.WorkerService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getAll")
    public ResponseEntity<List<TeamDTOFull>> getTeams(){
        List<Team> teams = teamService.getAllTeams();
        List<TeamDTOFull> teamDTOS = teams.stream().map(teamMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOS);
    }
    @GetMapping("/getByTopic/{id}")
    public ResponseEntity<List<TeamDTOFull>> getTeamsByTopic(@PathVariable Long id){
        List<Team> teams = teamService.getTeamsByTopicId(id);
        List<TeamDTOFull> teamDTOS = teams.stream().map(teamMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOS);
    }
    @GetMapping("/getByTopicIds/{ids}")
    public ResponseEntity<List<TeamDTOFull>> getTeamsByTopic(@PathVariable List<Long> ids){
        List<Team> teams = teamService.getTeamsByTopicIds(ids);
        List<TeamDTOFull> teamDTOS = teams.stream().map(teamMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOS);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TeamDTOFull> getTeam(@PathVariable Long id){
        Optional<Team> team = teamService.getTeam(id);
        if(team.isPresent()){
            TeamDTOFull teamDTO = teamMapper.toDto(team.get());
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
    public void updateTeam(@RequestBody TeamDTO teamDto, @PathVariable Long id){
        teamService.getTeam(id).ifPresent(w ->
        {
            PropertyUtils.customCopyProperties(teamDto, w);
            teamService.updateTeam(id, w);
        });
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
    }
}
