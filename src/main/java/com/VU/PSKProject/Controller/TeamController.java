package com.VU.PSKProject.Controller;

import com.VU.PSKProject.Entity.*;
import com.VU.PSKProject.Service.Mapper.TeamMapper;
import com.VU.PSKProject.Service.Mapper.UserMapper;
import com.VU.PSKProject.Service.Model.Team.*;
import com.VU.PSKProject.Service.Model.UserDTO;
import com.VU.PSKProject.Service.TeamGoalService;
import com.VU.PSKProject.Service.TeamService;
import com.VU.PSKProject.Service.UserService;
import com.VU.PSKProject.Service.WorkerService;
import com.VU.PSKProject.Utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
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
    private UserService userService;

    @Autowired
    private TeamGoalService teamGoalService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/getAll")
    public ResponseEntity<List<TeamToGetDTO>> getTeams(Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        if (!userService.checkIfManager(user))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/getByTopic/{id}")
    public ResponseEntity<List<TeamToGetDTO>> getTeamsByTopic(@PathVariable Long id, Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        if (!userService.checkIfManager(user))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        List<TeamToGetDTO> teamDTOS = teamService.getTeamsByTopicId(id, user).stream().map(teamMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOS);
    }

    @GetMapping("/getByTopicIds/{ids}")
    public ResponseEntity<List<TeamToGetDTO>> getTeamsByTopics(@PathVariable List<Long> ids, Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        if (!userService.checkIfManager(user))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        List<TeamToGetDTO> teamDTOS = teamService.getTeamsByTopicIds(ids, user).stream().map(teamMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(teamDTOS);
    }

    @GetMapping("/getTeamsCountByTopics/{teamIds}/{topicIds}")
    public ResponseEntity<List<TeamCountDTO>> getTeamsCountByTopics(@PathVariable List<Long> topicIds,
                                                                    @PathVariable List<Long> teamIds,
                                                                    Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        if (!userService.checkIfManager(user))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(teamService.getTeamsCountDTOByTopics(topicIds, teamIds, user));
    }

    @GetMapping("/exportTeamsCountByTopics/{teamIds}/{topicIds}")
    public void exportCSV(@PathVariable List<Long> topicIds,
                          @PathVariable List<Long> teamIds,
                          HttpServletResponse response, Principal principal) throws Exception {
        UserDTO user = userService.getUserByEmail(principal.getName());
            List<TeamCountDTO> teams = teamService.getTeamsCountDTOByTopics(topicIds, teamIds, user);
        teamService.exportToCSV(teams, response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TeamToGetDTO> getTeam(@PathVariable Long id, Principal principal) {
        UserDTO user = userService.getUserByEmail(principal.getName());
        if (!userService.checkIfManager(user))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Optional<Team> team = teamService.getTeam(id, user);
        if(team.isPresent()){
            TeamToGetDTO teamDTO = teamMapper.toDto(team.get());
            return ResponseEntity.ok(teamDTO);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", "Team with id " + id + " could not be found");
            return ResponseEntity.notFound().headers(headers).build();
        }
    }

    @PostMapping("/create")
    public void createTeam(@RequestBody TeamToCreateDTO teamDto, Principal principal) {
        Team team = teamMapper.fromDTO(teamDto);
        UserDTO userDTO = userService.getUserByEmail(principal.getName());
        Worker worker = workerService.getWorkerByUserId(userDTO.getId());
        worker.setManagedTeam(team);
        User user = userMapper.fromDTO(userDTO);
        user.setUserAuthority(UserAuthority.LEAD);
        teamService.createTeam(team);
        workerService.updateWorker(worker.getId(), worker);
        userService.updateUser(user);
    }

    @PutMapping("/update/{id}")
    public void updateTeam(@RequestBody TeamToUpdateDTO teamToUpdateDto, @PathVariable Long id) {
        teamService.getTeam(id).ifPresent(w ->
        {
            PropertyUtils.customCopyProperties(teamToUpdateDto, w);
            teamService.updateTeam(id, w);
        });
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
    }
}
