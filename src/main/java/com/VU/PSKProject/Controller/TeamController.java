package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.Team;
import com.VU.PSKProject.Service.Mapper.TeamMapper;
import com.VU.PSKProject.Service.Model.TeamDTO;
import com.VU.PSKProject.Service.TeamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamMapper teamMapper;

    @GetMapping("/getAll")
    public ResponseEntity<List<TeamDTO>> getTeams(){
        List<Team> teams = teamService.getAllTeams();
        List<TeamDTO> teamDTOS = new ArrayList<>();
        for (Team t: teams) {
            TeamDTO teamDTO = teamMapper.toDto(t);
            teamDTOS.add(teamDTO);
        }
        return ResponseEntity.ok(teamDTOS);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TeamDTO> getTeam(@PathVariable Long id){
        Optional<Team> team = teamService.getTeam(id);
        if(team.isPresent()){
            TeamDTO teamDTO = teamMapper.toDto(team.get());
            return ResponseEntity.ok(teamDTO);
        }else
        {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "Team with id " + id + " could not be found");
            return ResponseEntity.notFound().headers(headers).build();
        }
    }

    @PostMapping("/create")
    public void createTeam(@RequestBody TeamDTO teamDto){
        Team team = new Team();
        BeanUtils.copyProperties(teamDto, team);
        teamService.createTeam(team);
    }

    @PutMapping("/update/{id}")
    public void updateTeam(@RequestBody TeamDTO teamDto, @PathVariable Long id){
        Team team = new Team();
        BeanUtils.copyProperties(teamDto, team);
        teamService.updateTeam(id, team);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTeam(@PathVariable Long id){
        teamService.deleteTeam(id);
    }
}
